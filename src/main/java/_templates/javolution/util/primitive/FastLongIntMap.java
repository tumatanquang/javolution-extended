/*
 * Javolution - Java(TM) Solution for Real-Time and Embedded Systems
 * Copyright (C) 2005 - Javolution (http://javolution.org/)
 * All rights reserved.
 *
 * Permission to use, copy, modify, and distribute this software is
 * freely granted, provided that this notice is preserved.
 */
package _templates.javolution.util.primitive;
import java.io.IOException;
import _templates.java.io.ObjectInputStream;
import _templates.java.io.ObjectOutputStream;
import _templates.java.io.Serializable;
import _templates.java.lang.CloneNotSupportedException;
import _templates.java.lang.Cloneable;
import _templates.java.lang.IllegalStateException;
import _templates.java.lang.UnsupportedOperationException;
import _templates.java.util.NoSuchElementException;
import _templates.javax.realtime.MemoryArea;
import _templates.javolution.lang.Reusable;
import _templates.javolution.util.concurrent.locks.ReadWriteLock;
import _templates.javolution.util.concurrent.locks.ReentrantWriterPreferenceReadWriteLock;
import _templates.javolution.util.concurrent.locks.Sync;
/**
 * <p> This class represents a high-performance, open-addressing hash map from
 *     <code>long</code> primitive keys to <code>int</code> primitive values
 *     with linear probing.</p>
 *
 * <p> Unlike standard <code>java.util.HashMap&lt;Long, Integer&gt;</code> or
 *     <code>FastMap&lt;Long, Integer&gt;</code> which allocate wrapping objects
 *     and entry nodes for every mapping, {@link FastLongIntMap} stores keys and
 *     values in flat contiguous primitive arrays without any boxing overhead.
 *     Memory consumption is reduced by 80-90% and cache locality is maximized.</p>
 *
 * <p> Absent-key lookup and removal follow Android <code>LongSparseArray</code>
 *     default-value conventions (returning <code>0</code> or a caller-supplied
 *     fallback), while {@link #putIfAbsent(long, int)}, {@link #removeKey(long)},
 *     and {@link #removeEntry(long, int)} distinguish missing keys explicitly.
 *     Hash table iteration order is unspecified and may change on rehash.</p>
 *
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @version 7.1.0, November 22, 2026
 */
public class FastLongIntMap extends FastPrimitiveMap
		implements Cloneable, Reusable, Serializable {
	private static final byte FREE = 0;
	private static final byte OCCUPIED = 1;
	private static final byte REMOVED = 2;
	private static final int DEFAULT_CAPACITY = 16;
	private static final int MAX_CAPACITY = 1 << 30;
	private static final float LOAD_FACTOR = 0.75f;
	/**
	 * Holds the table keys.
	 */
	private transient long[] _keys;
	/**
	 * Holds the table values.
	 */
	private transient int[] _values;
	/**
	 * Holds the state of each slot in the table (FREE, OCCUPIED, or REMOVED).
	 */
	private transient byte[] _state;
	/**
	 * Holds the bitmask for hash modulo calculations.
	 */
	private transient int _mask;
	/**
	 * Holds the resize threshold.
	 */
	private transient int _threshold;
	/**
	 * Holds the count of used slots (OCCUPIED + REMOVED).
	 */
	private transient int _used;
	/**
	 * Package-private view constructor; skips backing array allocation when
	 * <code>allocate</code> is <code>false</code>.
	 *
	 * @param allocate <code>true</code> to allocate default capacity arrays.
	 */
	FastLongIntMap(boolean allocate) {
		if(allocate) {
			initCapacity(DEFAULT_CAPACITY);
		}
	}
	/**
	 * Constructs an empty map with default initial capacity (16).
	 */
	public FastLongIntMap() {
		this(DEFAULT_CAPACITY);
	}
	/**
	 * Constructs an empty map with the specified initial capacity.
	 *
	 * @param capacity the initial capacity.
	 */
	public FastLongIntMap(int capacity) {
		int cap = DEFAULT_CAPACITY;
		final int min = Math.max(DEFAULT_CAPACITY, capacity);
		while(cap < min && cap < MAX_CAPACITY) {
			cap <<= 1;
		}
		initCapacity(cap);
	}
	/**
	 * Constructs a map initialized with parallel arrays of keys and values.
	 *
	 * @param keys the array of initial keys.
	 * @param values the array of initial values.
	 * @throws NullPointerException if <code>keys</code> or <code>values</code> is <code>null</code>.
	 * @throws IllegalArgumentException if <code>keys.length != values.length</code>.
	 */
	public FastLongIntMap(long[] keys, int[] values) {
		if(keys == null)
			throw new NullPointerException("keys is null");
		if(values == null)
			throw new NullPointerException("values is null");
		if(keys.length != values.length)
			throw new IllegalArgumentException(
					"keys and values must have equal length");
		initCapacity(capacityForSize(keys.length));
		putAllInternal(keys, values);
	}
	/**
	 * Constructs a map containing the mappings of the specified map.
	 *
	 * @param map the map whose mappings are to be copied.
	 * @throws NullPointerException if <code>map</code> is <code>null</code>.
	 */
	public FastLongIntMap(FastLongIntMap map) {
		if(map == null)
			throw new NullPointerException("map is null");
		initCapacity(capacityForSize(map.size()));
		putAllInternal(map);
	}
	private static final int capacityForSize(int size) {
		if(size <= 0)
			return DEFAULT_CAPACITY;
		int cap = DEFAULT_CAPACITY;
		while(cap * LOAD_FACTOR <= size && cap < MAX_CAPACITY) {
			cap <<= 1;
		}
		return cap;
	}
	private final void initCapacity(int cap) {
		_keys = new long[cap];
		_values = new int[cap];
		_state = new byte[cap];
		_mask = cap - 1;
		_threshold = (int) (cap * LOAD_FACTOR);
	}
	private final int hash(long key) {
		long h = key * 0x9E3779B97F4A7C15L;
		h ^= h >>> 32;
		return (int) h & _mask;
	}
	private final void rehash(final int newCapacity) {
		MemoryArea.getMemoryArea(this).executeInArea(new Runnable() {
			public final void run() {
				final long[] oldKeys = _keys;
				final int[] oldValues = _values;
				final byte[] oldState = _state;
				final int oldCap = oldKeys.length;
				_keys = new long[newCapacity];
				_values = new int[newCapacity];
				_state = new byte[newCapacity];
				_mask = newCapacity - 1;
				_threshold = (int) (newCapacity * LOAD_FACTOR);
				_used = 0;
				for(int i = -1; ++i < oldCap;) {
					if(oldState[i] == OCCUPIED) {
						final long key = oldKeys[i];
						int idx = hash(key);
						while(_state[idx] != FREE) {
							idx = idx + 1 & _mask;
						}
						_keys[idx] = key;
						_values[idx] = oldValues[i];
						_state[idx] = OCCUPIED;
						++_used;
					}
				}
			}
		});
	}
	private final void ensureCapacityInternal(int minCapacity) {
		final int length;
		if(minCapacity >= _threshold
				&& (length = _keys.length) < MAX_CAPACITY) {
			int newCap = length;
			while(newCap * LOAD_FACTOR <= minCapacity
					&& newCap < MAX_CAPACITY) {
				newCap <<= 1;
			}
			if(newCap > length) {
				rehash(newCap);
			}
		}
	}
	private final int indexOf(long key) {
		if(_size == 0)
			return -1;
		final int length = _keys.length;
		int idx = hash(key);
		for(int probes = -1; ++probes < length;) {
			final byte state = _state[idx];
			if(state == FREE)
				return -1;
			if(state == OCCUPIED && _keys[idx] == key)
				return idx;
			idx = idx + 1 & _mask;
		}
		return -1;
	}
	private final int findSlotForPut(long key) {
		final int length = _keys.length;
		int idx = hash(key), firstRemoved = -1, free = -1;
		for(int probes = -1; ++probes < length;) {
			final byte state = _state[idx];
			if(state == FREE) {
				free = idx;
				break;
			}
			if(state == OCCUPIED) {
				if(_keys[idx] == key)
					return idx;
			}
			else if(firstRemoved < 0) {
				firstRemoved = idx;
			}
			idx = idx + 1 & _mask;
		}
		final int targetIdx = firstRemoved >= 0 ? firstRemoved : free;
		if(targetIdx < 0) {
			if(_size + 1 >= _threshold && length < MAX_CAPACITY) {
				rehash(length << 1);
			}
			else if(_used > _size) {
				rehash(length);
			}
			else
				throw new IllegalStateException("Map capacity limit exceeded");
			return findSlotForPut(key);
		}
		if(_size + 1 >= _threshold && length < MAX_CAPACITY) {
			rehash(length << 1);
			return findSlotForPut(key);
		}
		if(_used + (_state[targetIdx] == FREE ? 1 : 0) >= _threshold) {
			rehash(length);
			return findSlotForPut(key);
		}
		return ~targetIdx;
	}
	private final void insertAt(int targetIdx, long key, int value) {
		_keys[targetIdx] = key;
		_values[targetIdx] = value;
		if(_state[targetIdx] == FREE) {
			++_used;
		}
		_state[targetIdx] = OCCUPIED;
		++_size;
	}
	private final void removeAt(int idx) {
		_state[idx] = REMOVED;
		--_size;
	}
	private final boolean putEntry(long key, int value) {
		final int slot = findSlotForPut(key);
		if(slot >= 0) {
			if(_values[slot] == value)
				return false;
			_values[slot] = value;
			return true;
		}
		insertAt(~slot, key, value);
		return true;
	}
	private final boolean putAllInternal(long[] keys, int[] values) {
		if(keys == null && values == null)
			return false;
		if(keys == null || values == null || keys.length != values.length)
			throw new IllegalArgumentException(
					"keys and values must be non-null and of equal length");
		final int length = keys.length;
		if(length == 0)
			return false;
		ensureCapacityInternal(_size + length);
		boolean modified = false;
		for(int i = -1; ++i < length;) {
			if(putEntry(keys[i], values[i])) {
				modified = true;
			}
		}
		return modified;
	}
	private final boolean putAllInternal(FastLongIntMap map) {
		if(map == null || map.isEmpty() || map == this)
			return false;
		ensureCapacityInternal(_size + map.size());
		boolean modified = false;
		final FastLongIterator it = (FastLongIterator) map.keyIterator();
		while(it.hasNext()) {
			final long k = it.next();
			if(putEntry(k, map.get(k))) {
				modified = true;
			}
		}
		return modified;
	}
	/**
	 * Returns the value mapped to the specified key, or <code>0</code> if
	 * this map contains no mapping for the key.
	 *
	 * @param key the key whose associated value is to be returned.
	 * @return the value mapped to <code>key</code>, or <code>0</code> if absent.
	 */
	public int get(long key) {
		return get(key, 0);
	}
	/**
	 * Returns the value mapped to the specified key, or
	 * <code>valueIfKeyNotFound</code> if this map contains no mapping for the key.
	 *
	 * @param key the key whose associated value is to be returned.
	 * @param valueIfKeyNotFound the fallback value to return when the key is absent.
	 * @return the value mapped to <code>key</code>, or <code>valueIfKeyNotFound</code>.
	 */
	public int get(long key, int valueIfKeyNotFound) {
		final int idx = indexOf(key);
		return idx < 0 ? valueIfKeyNotFound : _values[idx];
	}
	/**
	 * Returns <code>true</code> if this map contains a mapping for the specified key.
	 *
	 * @param key the key whose presence in this map is to be tested.
	 * @return <code>true</code> if this map contains a mapping for <code>key</code>.
	 */
	public boolean containsKey(long key) {
		return indexOf(key) >= 0;
	}
	/**
	 * Returns <code>true</code> if this map maps one or more keys to the specified value.
	 *
	 * @param value the value whose presence in this map is to be tested.
	 * @return <code>true</code> if this map contains at least one mapping to <code>value</code>.
	 */
	public boolean containsValue(int value) {
		if(_size == 0)
			return false;
		final int length = _keys.length;
		for(int i = -1; ++i < length;) {
			if(_state[i] == OCCUPIED && _values[i] == value)
				return true;
		}
		return false;
	}
	/**
	 * Associates the specified value with the specified key in this map.
	 * If the map previously contained a mapping for the key, the old value is replaced.
	 *
	 * @param key the key with which the specified value is to be associated.
	 * @param value the value to be associated with the specified key.
	 * @return the previous value associated with <code>key</code>, or <code>0</code>
	 *         if there was no mapping for <code>key</code>.
	 */
	public int put(long key, int value) {
		final int slot = findSlotForPut(key);
		if(slot >= 0) {
			final int old = _values[slot];
			_values[slot] = value;
			return old;
		}
		insertAt(~slot, key, value);
		return 0;
	}
	/**
	 * Associates the specified value with the specified key if the key is not
	 * already present in this map.
	 *
	 * @param key the key with which the specified value is to be associated.
	 * @param value the value to be associated if the key is absent.
	 * @return <code>true</code> if the mapping was inserted; <code>false</code>
	 *         if the key was already present.
	 */
	public boolean putIfAbsent(long key, int value) {
		final int slot = findSlotForPut(key);
		if(slot >= 0)
			return false;
		insertAt(~slot, key, value);
		return true;
	}
	/**
	 * Copies all key-value pairs from the specified parallel arrays into this map.
	 *
	 * @param keys the array of keys to insert.
	 * @param values the array of values corresponding to <code>keys</code>.
	 * @return <code>true</code> if this map changed as a result of the call.
	 * @throws IllegalArgumentException if only one array is <code>null</code>
	 *         or <code>keys.length != values.length</code>.
	 */
	public boolean putAll(long[] keys, int[] values) {
		return putAllInternal(keys, values);
	}
	/**
	 * Copies all mappings from the specified map into this map.
	 *
	 * @param map the map whose mappings are to be stored in this map.
	 * @return <code>true</code> if this map changed as a result of the call.
	 */
	public boolean putAll(FastLongIntMap map) {
		return putAllInternal(map);
	}
	/**
	 * Removes the mapping for the specified key from this map if present.
	 *
	 * @param key the key whose mapping is to be removed.
	 * @return the previous value associated with <code>key</code>, or <code>0</code>
	 *         if there was no mapping for <code>key</code>.
	 */
	public int remove(long key) {
		return remove(key, 0);
	}
	/**
	 * Removes the mapping for the specified key from this map if present, returning
	 * <code>valueIfKeyNotFound</code> when the key is absent.
	 *
	 * @param key the key whose mapping is to be removed.
	 * @param valueIfKeyNotFound the fallback value to return when the key is absent.
	 * @return the previous value associated with <code>key</code>, or
	 *         <code>valueIfKeyNotFound</code> if there was no mapping for <code>key</code>.
	 */
	public int remove(long key, int valueIfKeyNotFound) {
		final int idx = indexOf(key);
		if(idx < 0)
			return valueIfKeyNotFound;
		final int old = _values[idx];
		removeAt(idx);
		return old;
	}
	/**
	 * Removes the mapping for the specified key from this map if present.
	 *
	 * @param key the key whose mapping is to be removed.
	 * @return <code>true</code> if this map contained a mapping for <code>key</code>.
	 */
	public boolean removeKey(long key) {
		final int idx = indexOf(key);
		if(idx < 0)
			return false;
		removeAt(idx);
		return true;
	}
	/**
	 * Removes the entry for the specified key only if it is currently mapped to
	 * the specified value.
	 *
	 * @param key the key with which the specified value is associated.
	 * @param value the value expected to be associated with the specified key.
	 * @return <code>true</code> if the mapping was removed.
	 */
	public boolean removeEntry(long key, int value) {
		final int idx = indexOf(key);
		if(idx < 0 || _values[idx] != value)
			return false;
		removeAt(idx);
		return true;
	}
	/**
	 * Removes from this map all mappings whose keys appear in the specified array.
	 *
	 * @param keys the array containing keys to be removed from this map.
	 * @return <code>true</code> if this map changed as a result of the call.
	 */
	public boolean removeAllKeys(long[] keys) {
		final int length;
		if(keys == null || (length = keys.length) == 0 || _size == 0)
			return false;
		boolean modified = false;
		for(int i = -1; ++i < length;) {
			if(removeKey(keys[i])) {
				modified = true;
			}
		}
		return modified;
	}
	/**
	 * Removes from this map all mappings whose keys are contained in the specified set.
	 *
	 * @param keys the set containing keys to be removed from this map.
	 * @return <code>true</code> if this map changed as a result of the call.
	 */
	public boolean removeAllKeys(FastLongSet keys) {
		if(keys == null || keys.isEmpty() || _size == 0)
			return false;
		boolean modified = false;
		final FastLongIterator it = (FastLongIterator) keys.iterator();
		while(it.hasNext()) {
			if(removeKey(it.next())) {
				modified = true;
			}
		}
		return modified;
	}
	/**
	 * Retains only the mappings in this map whose keys are contained in the
	 * specified set.
	 *
	 * @param keys the set containing keys to be retained in this map.
	 * @return <code>true</code> if this map changed as a result of the call.
	 */
	public boolean retainAllKeys(FastLongSet keys) {
		if(keys == null || _size == 0)
			return false;
		final int length = _keys.length;
		boolean modified = false;
		for(int i = -1; ++i < length;) {
			if(_state[i] == OCCUPIED && !keys.contains(_keys[i])) {
				removeAt(i);
				modified = true;
			}
		}
		return modified;
	}
	/**
	 * {@inheritDoc}
	 */
	public void clear() {
		if(_used == 0)
			return;
		final int length = _state.length;
		for(int i = -1; ++i < length;) {
			_state[i] = FREE;
		}
		_size = 0;
		_used = 0;
	}
	/**
	 * {@inheritDoc}
	 */
	public void reset() {
		clear();
	}
	/**
	 * {@inheritDoc}
	 */
	public void trimToSize() {
		int minCap = DEFAULT_CAPACITY;
		while(minCap * LOAD_FACTOR <= _size && minCap < MAX_CAPACITY) {
			minCap <<= 1;
		}
		if(minCap < _keys.length) {
			rehash(minCap);
		}
		else if(_used > _size) {
			rehash(_keys.length);
		}
	}
	/**
	 * {@inheritDoc}
	 */
	public void ensureCapacity(int minCapacity) {
		ensureCapacityInternal(minCapacity);
	}
	/**
	 * Returns an array containing all keys stored in this map.
	 *
	 * @return a newly allocated array of all keys in this map.
	 */
	public long[] keys() {
		final long[] result = new long[_size];
		if(_size == 0)
			return result;
		final int length = _keys.length;
		for(int i = -1, j = -1; ++i < length;) {
			if(_state[i] == OCCUPIED) {
				result[++j] = _keys[i];
			}
		}
		return result;
	}
	/**
	 * Returns an array containing all values stored in this map.
	 *
	 * @return a newly allocated array of all values in this map.
	 */
	public int[] values() {
		final int[] result = new int[_size];
		if(_size == 0)
			return result;
		final int length = _keys.length;
		for(int i = -1, j = -1; ++i < length;) {
			if(_state[i] == OCCUPIED) {
				result[++j] = _values[i];
			}
		}
		return result;
	}
	/**
	 * Returns a newly allocated {@link FastLongSet} containing a copy of all
	 * keys currently in this map.
	 *
	 * @return a {@link FastLongSet} copy of this map's keys.
	 */
	public FastLongSet keySet() {
		if(_size == 0)
			return new FastLongSet();
		final FastLongSet set = new FastLongSet(_size + (_size >> 1) + 2);
		final int length = _keys.length;
		for(int i = -1; ++i < length;) {
			if(_state[i] == OCCUPIED) {
				set.add(_keys[i]);
			}
		}
		return set;
	}
	/**
	 * {@inheritDoc}
	 */
	public FastPrimitiveIterator/*FastLongIterator*/ keyIterator() {
		return new KeyIterator(this);
	}
	/**
	 * Returns a primitive iterator over the values in this map.
	 *
	 * @return a primitive iterator over this map's values.
	 */
	public FastPrimitiveIterator/*FastIntIterator*/ valueIterator() {
		return new ValueIterator(this);
	}
	/**
	 * {@inheritDoc}
	 */
	public FastPrimitiveMap/*FastLongIntMap*/ unmodifiable() {
		return new Unmodifiable(this);
	}
	/**
	 * {@inheritDoc}
	 */
	public FastPrimitiveMap/*FastLongIntMap*/ shared() {
		return new Shared(this);
	}
	/**
	 * Returns a copy of this map.
	 *
	 * @return a clone of this instance.
	 * @throws CloneNotSupportedException if cloning is not supported.
	 */
	public Object/*FastLongIntMap*/ clone() throws CloneNotSupportedException {
		/*@JVM-1.1+@
		if(true) {
			final FastLongIntMap c = (FastLongIntMap) super.clone();
			c._keys = new long[_keys.length];
			c._values = new int[_values.length];
			c._state = new byte[_state.length];
			System.arraycopy(_keys, 0, c._keys, 0, _keys.length);
			System.arraycopy(_values, 0, c._values, 0, _values.length);
			System.arraycopy(_state, 0, c._state, 0, _state.length);
			c._mask = _mask;
			c._threshold = _threshold;
			c._used = _used;
			c._size = _size;
			return c;
		}
		/**/
		throw new UnsupportedOperationException("J2ME Not Supported Yet");
	}
	private final void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();
		s.writeInt(_size);
		if(_size == 0 || _keys == null)
			return;
		final int length = _keys.length;
		for(int i = -1; ++i < length;) {
			if(_state[i] == OCCUPIED) {
				s.writeLong(_keys[i]);
				s.writeInt(_values[i]);
			}
		}
	}
	private final void readObject(ObjectInputStream s)
			throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		final int count = s.readInt();
		initCapacity(capacityForSize(count));
		_size = 0;
		_used = 0;
		for(int i = -1; ++i < count;) {
			putEntry(s.readLong(), s.readInt());
		}
	}
	/**
	 * Compares the specified object with this map for equality.
	 *
	 * @param o the object to be compared for equality with this map.
	 * @return <code>true</code> if the specified object is equal to this map.
	 */
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(!(o instanceof FastLongIntMap))
			return false;
		final FastLongIntMap that = (FastLongIntMap) o;
		if(_size != that.size())
			return false;
		if(_size == 0)
			return true;
		final int length = _keys.length;
		for(int i = -1; ++i < length;) {
			if(_state[i] == OCCUPIED) {
				final long key = _keys[i];
				if(!that.containsKey(key) || _values[i] != that.get(key))
					return false;
			}
		}
		return true;
	}
	/**
	 * Returns the hash code value for this map.
	 *
	 * @return the hash code value for this map.
	 */
	public int hashCode() {
		if(_size == 0)
			return 0;
		int h = 0;
		final int length = _keys.length;
		for(int i = -1; ++i < length;) {
			if(_state[i] == OCCUPIED) {
				final long k = _keys[i];
				h += (int) (k ^ k >>> 32) ^ _values[i];
			}
		}
		return h;
	}
	/**
	 * Returns a string representation of this map.
	 *
	 * @return a string representation of this map.
	 */
	public String toString() {
		if(_size == 0)
			return "{}";
		final StringBuffer/*StringBuilder*/ sb = new StringBuffer/*StringBuilder*/();
		sb.append('{');
		boolean first = true;
		final int length = _keys.length;
		for(int i = -1; ++i < length;) {
			if(_state[i] == OCCUPIED) {
				if(!first) {
					sb.append(',').append(' ');
				}
				sb.append(_keys[i]).append('=').append(_values[i]);
				first = false;
			}
		}
		return sb.append('}').toString();
	}
	private static final class KeyIterator implements FastLongIterator {
		private final FastLongIntMap _map;
		private int _cursor;
		private long _lastKey;
		private boolean _canRemove;
		private KeyIterator(FastLongIntMap map) {
			_map = map;
			if(map._size == 0) {
				_cursor = map._keys.length;
				return;
			}
			advance();
		}
		private final void advance() {
			for(final int length = _map._keys.length; _cursor < length
					&& _map._state[_cursor] != OCCUPIED;) {
				++_cursor;
			}
		}
		public final boolean hasNext() {
			return _cursor < _map._keys.length;
		}
		public final long next() {
			if(!hasNext())
				throw new NoSuchElementException();
			final long key = _map._keys[_cursor++];
			_lastKey = key;
			_canRemove = true;
			advance();
			return key;
		}
		public final void remove() {
			if(!_canRemove)
				throw new IllegalStateException();
			_map.removeKey(_lastKey);
			_canRemove = false;
		}
	}
	private static final class ValueIterator implements FastIntIterator {
		private final FastLongIntMap _map;
		private int _cursor;
		private long _lastKey;
		private boolean _canRemove;
		private ValueIterator(FastLongIntMap map) {
			_map = map;
			if(map._size == 0) {
				_cursor = map._keys.length;
				return;
			}
			advance();
		}
		private final void advance() {
			for(final int length = _map._keys.length; _cursor < length
					&& _map._state[_cursor] != OCCUPIED;) {
				++_cursor;
			}
		}
		public final boolean hasNext() {
			return _cursor < _map._keys.length;
		}
		public final int next() {
			if(!hasNext())
				throw new NoSuchElementException();
			final int idx = _cursor++, val = _map._values[idx];
			_lastKey = _map._keys[idx];
			_canRemove = true;
			advance();
			return val;
		}
		public final void remove() {
			if(!_canRemove)
				throw new IllegalStateException();
			_map.removeKey(_lastKey);
			_canRemove = false;
		}
	}
	private static final class Unmodifiable extends FastLongIntMap
			implements Cloneable, Reusable, Serializable {
		private final FastLongIntMap _map;
		private Unmodifiable(FastLongIntMap map) {
			super(false);
			_map = map;
		}
		public final FastPrimitiveMap/*FastLongIntMap*/ unmodifiable() {
			return this;
		}
		public final FastPrimitiveMap/*FastLongIntMap*/ shared() {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		public final int size() {
			return _map.size();
		}
		public final boolean isEmpty() {
			return _map.isEmpty();
		}
		public final void trimToSize() {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		public final void ensureCapacity(int min) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		public final int get(long key) {
			return _map.get(key);
		}
		public final int get(long key, int valueIfKeyNotFound) {
			return _map.get(key, valueIfKeyNotFound);
		}
		public final boolean containsKey(long key) {
			return _map.containsKey(key);
		}
		public final boolean containsValue(int value) {
			return _map.containsValue(value);
		}
		public final int put(long key, int value) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		public final boolean putIfAbsent(long key, int value) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		public final boolean putAll(long[] keys, int[] values) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		public final boolean putAll(FastLongIntMap map) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		public final int remove(long key) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		public final int remove(long key, int valueIfKeyNotFound) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		public final boolean removeKey(long key) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		public final boolean removeEntry(long key, int value) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		public final boolean removeAllKeys(long[] keys) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		public final boolean removeAllKeys(FastLongSet keys) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		public final boolean retainAllKeys(FastLongSet keys) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		public final void clear() {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		public final void reset() {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		public final long[] keys() {
			return _map.keys();
		}
		public final int[] values() {
			return _map.values();
		}
		public final FastLongSet keySet() {
			return _map.keySet();
		}
		public final Object/*FastLongIntMap*/ clone()
				throws CloneNotSupportedException {
			return _map.clone();
		}
		public final boolean equals(Object o) {
			return _map.equals(o);
		}
		public final int hashCode() {
			return _map.hashCode();
		}
		public final String toString() {
			return _map.toString();
		}
		public final FastPrimitiveIterator/*FastLongIterator*/ keyIterator() {
			final FastLongIterator iterator = (FastLongIterator) _map
					.keyIterator();
			return new FastLongIterator() {
				public final boolean hasNext() {
					return iterator.hasNext();
				}
				public final long next() {
					return iterator.next();
				}
				public final void remove() {
					throw new UnsupportedOperationException("Unmodifiable");
				}
			};
		}
		public final FastPrimitiveIterator/*FastIntIterator*/ valueIterator() {
			final FastIntIterator iterator = (FastIntIterator) _map
					.valueIterator();
			return new FastIntIterator() {
				public final boolean hasNext() {
					return iterator.hasNext();
				}
				public final int next() {
					return iterator.next();
				}
				public final void remove() {
					throw new UnsupportedOperationException("Unmodifiable");
				}
			};
		}
	}
	private static final class Shared extends FastLongIntMap
			implements Cloneable, Reusable, Serializable {
		private final FastLongIntMap _map;
		private final ReadWriteLock _lock;
		private Shared(FastLongIntMap map) {
			super(false);
			_map = map;
			_lock = new ReentrantWriterPreferenceReadWriteLock();
		}
		public final FastPrimitiveMap/*FastLongIntMap*/ unmodifiable() {
			return new Unmodifiable(this);
		}
		public final FastPrimitiveMap/*FastLongIntMap*/ shared() {
			return this;
		}
		public final int size() {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync r = _lock.readLock();
				for(;;) {
					try {
						r.acquire();
						try {
							return _map.size();
						}
						finally {
							r.release();
						}
					}
					catch(final InterruptedException ex) {
						wasInterrupted = true;
					}
				}
			}
			finally {
				if(wasInterrupted) {
					Thread.currentThread().interrupt();
				}
			}
		}
		public final boolean isEmpty() {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync r = _lock.readLock();
				for(;;) {
					try {
						r.acquire();
						try {
							return _map.isEmpty();
						}
						finally {
							r.release();
						}
					}
					catch(final InterruptedException ex) {
						wasInterrupted = true;
					}
				}
			}
			finally {
				if(wasInterrupted) {
					Thread.currentThread().interrupt();
				}
			}
		}
		public final void trimToSize() {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync w = _lock.writeLock();
				for(;;) {
					try {
						w.acquire();
						try {
							_map.trimToSize();
							return;
						}
						finally {
							w.release();
						}
					}
					catch(final InterruptedException ex) {
						wasInterrupted = true;
					}
				}
			}
			finally {
				if(wasInterrupted) {
					Thread.currentThread().interrupt();
				}
			}
		}
		public final void ensureCapacity(int min) {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync w = _lock.writeLock();
				for(;;) {
					try {
						w.acquire();
						try {
							_map.ensureCapacity(min);
							return;
						}
						finally {
							w.release();
						}
					}
					catch(final InterruptedException ex) {
						wasInterrupted = true;
					}
				}
			}
			finally {
				if(wasInterrupted) {
					Thread.currentThread().interrupt();
				}
			}
		}
		public final int get(long key) {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync r = _lock.readLock();
				for(;;) {
					try {
						r.acquire();
						try {
							return _map.get(key);
						}
						finally {
							r.release();
						}
					}
					catch(final InterruptedException ex) {
						wasInterrupted = true;
					}
				}
			}
			finally {
				if(wasInterrupted) {
					Thread.currentThread().interrupt();
				}
			}
		}
		public final int get(long key, int valueIfKeyNotFound) {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync r = _lock.readLock();
				for(;;) {
					try {
						r.acquire();
						try {
							return _map.get(key, valueIfKeyNotFound);
						}
						finally {
							r.release();
						}
					}
					catch(final InterruptedException ex) {
						wasInterrupted = true;
					}
				}
			}
			finally {
				if(wasInterrupted) {
					Thread.currentThread().interrupt();
				}
			}
		}
		public final boolean containsKey(long key) {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync r = _lock.readLock();
				for(;;) {
					try {
						r.acquire();
						try {
							return _map.containsKey(key);
						}
						finally {
							r.release();
						}
					}
					catch(final InterruptedException ex) {
						wasInterrupted = true;
					}
				}
			}
			finally {
				if(wasInterrupted) {
					Thread.currentThread().interrupt();
				}
			}
		}
		public final boolean containsValue(int value) {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync r = _lock.readLock();
				for(;;) {
					try {
						r.acquire();
						try {
							return _map.containsValue(value);
						}
						finally {
							r.release();
						}
					}
					catch(final InterruptedException ex) {
						wasInterrupted = true;
					}
				}
			}
			finally {
				if(wasInterrupted) {
					Thread.currentThread().interrupt();
				}
			}
		}
		public final int put(long key, int value) {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync w = _lock.writeLock();
				for(;;) {
					try {
						w.acquire();
						try {
							return _map.put(key, value);
						}
						finally {
							w.release();
						}
					}
					catch(final InterruptedException ex) {
						wasInterrupted = true;
					}
				}
			}
			finally {
				if(wasInterrupted) {
					Thread.currentThread().interrupt();
				}
			}
		}
		public final boolean putIfAbsent(long key, int value) {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync w = _lock.writeLock();
				for(;;) {
					try {
						w.acquire();
						try {
							return _map.putIfAbsent(key, value);
						}
						finally {
							w.release();
						}
					}
					catch(final InterruptedException ex) {
						wasInterrupted = true;
					}
				}
			}
			finally {
				if(wasInterrupted) {
					Thread.currentThread().interrupt();
				}
			}
		}
		public final boolean putAll(long[] keys, int[] values) {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync w = _lock.writeLock();
				for(;;) {
					try {
						w.acquire();
						try {
							return _map.putAll(keys, values);
						}
						finally {
							w.release();
						}
					}
					catch(final InterruptedException ex) {
						wasInterrupted = true;
					}
				}
			}
			finally {
				if(wasInterrupted) {
					Thread.currentThread().interrupt();
				}
			}
		}
		public final boolean putAll(FastLongIntMap map) {
			if(map == null || map == this)
				return false;
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync w = _lock.writeLock();
				for(;;) {
					try {
						w.acquire();
						try {
							return _map.putAll(map);
						}
						finally {
							w.release();
						}
					}
					catch(final InterruptedException ex) {
						wasInterrupted = true;
					}
				}
			}
			finally {
				if(wasInterrupted) {
					Thread.currentThread().interrupt();
				}
			}
		}
		public final int remove(long key) {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync w = _lock.writeLock();
				for(;;) {
					try {
						w.acquire();
						try {
							return _map.remove(key);
						}
						finally {
							w.release();
						}
					}
					catch(final InterruptedException ex) {
						wasInterrupted = true;
					}
				}
			}
			finally {
				if(wasInterrupted) {
					Thread.currentThread().interrupt();
				}
			}
		}
		public final int remove(long key, int valueIfKeyNotFound) {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync w = _lock.writeLock();
				for(;;) {
					try {
						w.acquire();
						try {
							return _map.remove(key, valueIfKeyNotFound);
						}
						finally {
							w.release();
						}
					}
					catch(final InterruptedException ex) {
						wasInterrupted = true;
					}
				}
			}
			finally {
				if(wasInterrupted) {
					Thread.currentThread().interrupt();
				}
			}
		}
		public final boolean removeKey(long key) {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync w = _lock.writeLock();
				for(;;) {
					try {
						w.acquire();
						try {
							return _map.removeKey(key);
						}
						finally {
							w.release();
						}
					}
					catch(final InterruptedException ex) {
						wasInterrupted = true;
					}
				}
			}
			finally {
				if(wasInterrupted) {
					Thread.currentThread().interrupt();
				}
			}
		}
		public final boolean removeEntry(long key, int value) {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync w = _lock.writeLock();
				for(;;) {
					try {
						w.acquire();
						try {
							return _map.removeEntry(key, value);
						}
						finally {
							w.release();
						}
					}
					catch(final InterruptedException ex) {
						wasInterrupted = true;
					}
				}
			}
			finally {
				if(wasInterrupted) {
					Thread.currentThread().interrupt();
				}
			}
		}
		public final boolean removeAllKeys(long[] keys) {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync w = _lock.writeLock();
				for(;;) {
					try {
						w.acquire();
						try {
							return _map.removeAllKeys(keys);
						}
						finally {
							w.release();
						}
					}
					catch(final InterruptedException ex) {
						wasInterrupted = true;
					}
				}
			}
			finally {
				if(wasInterrupted) {
					Thread.currentThread().interrupt();
				}
			}
		}
		public final boolean removeAllKeys(FastLongSet keys) {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync w = _lock.writeLock();
				for(;;) {
					try {
						w.acquire();
						try {
							return _map.removeAllKeys(keys);
						}
						finally {
							w.release();
						}
					}
					catch(final InterruptedException ex) {
						wasInterrupted = true;
					}
				}
			}
			finally {
				if(wasInterrupted) {
					Thread.currentThread().interrupt();
				}
			}
		}
		public final boolean retainAllKeys(FastLongSet keys) {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync w = _lock.writeLock();
				for(;;) {
					try {
						w.acquire();
						try {
							return _map.retainAllKeys(keys);
						}
						finally {
							w.release();
						}
					}
					catch(final InterruptedException ex) {
						wasInterrupted = true;
					}
				}
			}
			finally {
				if(wasInterrupted) {
					Thread.currentThread().interrupt();
				}
			}
		}
		public final void clear() {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync w = _lock.writeLock();
				for(;;) {
					try {
						w.acquire();
						try {
							_map.clear();
							return;
						}
						finally {
							w.release();
						}
					}
					catch(final InterruptedException ex) {
						wasInterrupted = true;
					}
				}
			}
			finally {
				if(wasInterrupted) {
					Thread.currentThread().interrupt();
				}
			}
		}
		public final void reset() {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync w = _lock.writeLock();
				for(;;) {
					try {
						w.acquire();
						try {
							_map.reset();
							return;
						}
						finally {
							w.release();
						}
					}
					catch(final InterruptedException ex) {
						wasInterrupted = true;
					}
				}
			}
			finally {
				if(wasInterrupted) {
					Thread.currentThread().interrupt();
				}
			}
		}
		public final long[] keys() {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync r = _lock.readLock();
				for(;;) {
					try {
						r.acquire();
						try {
							return _map.keys();
						}
						finally {
							r.release();
						}
					}
					catch(final InterruptedException ex) {
						wasInterrupted = true;
					}
				}
			}
			finally {
				if(wasInterrupted) {
					Thread.currentThread().interrupt();
				}
			}
		}
		public final int[] values() {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync r = _lock.readLock();
				for(;;) {
					try {
						r.acquire();
						try {
							return _map.values();
						}
						finally {
							r.release();
						}
					}
					catch(final InterruptedException ex) {
						wasInterrupted = true;
					}
				}
			}
			finally {
				if(wasInterrupted) {
					Thread.currentThread().interrupt();
				}
			}
		}
		public final FastLongSet keySet() {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync r = _lock.readLock();
				for(;;) {
					try {
						r.acquire();
						try {
							return _map.keySet();
						}
						finally {
							r.release();
						}
					}
					catch(final InterruptedException ex) {
						wasInterrupted = true;
					}
				}
			}
			finally {
				if(wasInterrupted) {
					Thread.currentThread().interrupt();
				}
			}
		}
		public final Object/*FastLongIntMap*/ clone()
				throws CloneNotSupportedException {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync r = _lock.readLock();
				for(;;) {
					try {
						r.acquire();
						try {
							return _map.clone();
						}
						finally {
							r.release();
						}
					}
					catch(final InterruptedException ex) {
						wasInterrupted = true;
					}
				}
			}
			finally {
				if(wasInterrupted) {
					Thread.currentThread().interrupt();
				}
			}
		}
		public final boolean equals(Object o) {
			if(this == o)
				return true;
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync r = _lock.readLock();
				for(;;) {
					try {
						r.acquire();
						try {
							return _map.equals(o);
						}
						finally {
							r.release();
						}
					}
					catch(final InterruptedException ex) {
						wasInterrupted = true;
					}
				}
			}
			finally {
				if(wasInterrupted) {
					Thread.currentThread().interrupt();
				}
			}
		}
		public final int hashCode() {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync r = _lock.readLock();
				for(;;) {
					try {
						r.acquire();
						try {
							return _map.hashCode();
						}
						finally {
							r.release();
						}
					}
					catch(final InterruptedException ex) {
						wasInterrupted = true;
					}
				}
			}
			finally {
				if(wasInterrupted) {
					Thread.currentThread().interrupt();
				}
			}
		}
		public final String toString() {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync r = _lock.readLock();
				for(;;) {
					try {
						r.acquire();
						try {
							return _map.toString();
						}
						finally {
							r.release();
						}
					}
					catch(final InterruptedException ex) {
						wasInterrupted = true;
					}
				}
			}
			finally {
				if(wasInterrupted) {
					Thread.currentThread().interrupt();
				}
			}
		}
		public final FastPrimitiveIterator/*FastLongIterator*/ keyIterator() {
			FastLongIterator it;
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync r = _lock.readLock();
				for(;;) {
					try {
						r.acquire();
						try {
							it = (FastLongIterator) _map.keyIterator();
							break;
						}
						finally {
							r.release();
						}
					}
					catch(final InterruptedException ex) {
						wasInterrupted = true;
					}
				}
			}
			finally {
				if(wasInterrupted) {
					Thread.currentThread().interrupt();
				}
			}
			final FastLongIterator iterator = it;
			return new FastLongIterator() {
				public final boolean hasNext() {
					boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
					try {
						final Sync r = _lock.readLock();
						for(;;) {
							try {
								r.acquire();
								try {
									return iterator.hasNext();
								}
								finally {
									r.release();
								}
							}
							catch(final InterruptedException ex) {
								wasInterrupted = true;
							}
						}
					}
					finally {
						if(wasInterrupted) {
							Thread.currentThread().interrupt();
						}
					}
				}
				public final long next() {
					boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
					try {
						final Sync r = _lock.readLock();
						for(;;) {
							try {
								r.acquire();
								try {
									return iterator.next();
								}
								finally {
									r.release();
								}
							}
							catch(final InterruptedException ex) {
								wasInterrupted = true;
							}
						}
					}
					finally {
						if(wasInterrupted) {
							Thread.currentThread().interrupt();
						}
					}
				}
				public final void remove() {
					boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
					try {
						final Sync w = _lock.writeLock();
						for(;;) {
							try {
								w.acquire();
								try {
									iterator.remove();
									return;
								}
								finally {
									w.release();
								}
							}
							catch(final InterruptedException ex) {
								wasInterrupted = true;
							}
						}
					}
					finally {
						if(wasInterrupted) {
							Thread.currentThread().interrupt();
						}
					}
				}
			};
		}
		public final FastPrimitiveIterator/*FastIntIterator*/ valueIterator() {
			FastIntIterator it;
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync r = _lock.readLock();
				for(;;) {
					try {
						r.acquire();
						try {
							it = (FastIntIterator) _map.valueIterator();
							break;
						}
						finally {
							r.release();
						}
					}
					catch(final InterruptedException ex) {
						wasInterrupted = true;
					}
				}
			}
			finally {
				if(wasInterrupted) {
					Thread.currentThread().interrupt();
				}
			}
			final FastIntIterator iterator = it;
			return new FastIntIterator() {
				public final boolean hasNext() {
					boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
					try {
						final Sync r = _lock.readLock();
						for(;;) {
							try {
								r.acquire();
								try {
									return iterator.hasNext();
								}
								finally {
									r.release();
								}
							}
							catch(final InterruptedException ex) {
								wasInterrupted = true;
							}
						}
					}
					finally {
						if(wasInterrupted) {
							Thread.currentThread().interrupt();
						}
					}
				}
				public final int next() {
					boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
					try {
						final Sync r = _lock.readLock();
						for(;;) {
							try {
								r.acquire();
								try {
									return iterator.next();
								}
								finally {
									r.release();
								}
							}
							catch(final InterruptedException ex) {
								wasInterrupted = true;
							}
						}
					}
					finally {
						if(wasInterrupted) {
							Thread.currentThread().interrupt();
						}
					}
				}
				public final void remove() {
					boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
					try {
						final Sync w = _lock.writeLock();
						for(;;) {
							try {
								w.acquire();
								try {
									iterator.remove();
									return;
								}
								finally {
									w.release();
								}
							}
							catch(final InterruptedException ex) {
								wasInterrupted = true;
							}
						}
					}
					finally {
						if(wasInterrupted) {
							Thread.currentThread().interrupt();
						}
					}
				}
			};
		}
	}
}