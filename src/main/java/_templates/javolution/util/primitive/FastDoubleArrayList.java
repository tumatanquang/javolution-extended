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
import _templates.java.lang.UnsupportedOperationException;
import _templates.java.util.NoSuchElementException;
import _templates.java.util.RandomAccess;
import _templates.javolution.lang.Reusable;
import _templates.javolution.util.concurrent.locks.ReadWriteLock;
import _templates.javolution.util.concurrent.locks.ReentrantWriterPreferenceReadWriteLock;
import _templates.javolution.util.concurrent.locks.Sync;
/**
 * Implement <code>FastArrayList</code> for the <code>double</code> primitive data type.
 *
 * @version 5.7.8, July 27, 2026
 */
public strictfp class FastDoubleArrayList extends FastPrimitiveList
		implements Cloneable, RandomAccess, Serializable {
	private transient double[] elementData;
	public FastDoubleArrayList() {
		this(DEFAULT_INITIAL_CAPACITY);
	}
	public FastDoubleArrayList(int initialCapacity) {
		if(initialCapacity < 0)
			throw new IllegalArgumentException(
					"Illegal capacity: " + initialCapacity);
		elementData = new double[initialCapacity];
	}
	public FastDoubleArrayList(double[] src) {
		if(src == null)
			throw new NullPointerException("source array is null");
		size = src.length;
		elementData = new double[size];
		System.arraycopy(src, 0, elementData, 0, size);
	}
	public void trimToSize() {
		if(size >= elementData.length)
			return;
		final double[] tmp = new double[size];
		System.arraycopy(elementData, 0, tmp, 0, size);
		elementData = tmp;
	}
	public void ensureCapacity(int min) {
		if(min <= elementData.length)
			return;
		int newCap = (elementData.length * 3 >> 1) + 1;
		if(newCap < min) {
			newCap = min;
		}
		final double[] tmp = new double[newCap];
		System.arraycopy(elementData, 0, tmp, 0, size);
		elementData = tmp;
	}
	public FastPrimitiveList/*FastDoubleArrayList*/ unmodifiable() {
		return new Unmodifiable(this);
	}
	public FastPrimitiveList/*FastDoubleArrayList*/ shared() {
		return new Shared(this);
	}
	/**
	 * Returns an iterator over the <code>double</code> elements in this list.
	 *
	 * @return an iterator over this list's elements.
	 */
	public FastPrimitiveIterator/*FastDoubleIterator*/ iterator() {
		return new DoubleIterator(this);
	}
	public boolean contains(double value) {
		return indexOf(value) >= 0;
	}
	public int indexOf(double value) {
		final long bits = Double.doubleToLongBits(value);
		for(int i = -1; ++i < size;) {
			if(Double.doubleToLongBits(elementData[i]) == bits)
				return i;
		}
		return -1;
	}
	public int lastIndexOf(double value) {
		final long bits = Double.doubleToLongBits(value);
		for(int i = size; --i >= 0;) {
			if(Double.doubleToLongBits(elementData[i]) == bits)
				return i;
		}
		return -1;
	}
	public Object/*FastDoubleArrayList*/ clone()
			throws CloneNotSupportedException {
		/*@JVM-1.1+@
		if(true) {
			final FastDoubleArrayList c = (FastDoubleArrayList) super.clone();
			c.elementData = new double[elementData.length];
			System.arraycopy(elementData, 0, c.elementData, 0, size);
			return c;
		}
		/**/
		throw new UnsupportedOperationException("J2ME Not Supported Yet");
	}
	public double[] toArray() {
		final double[] a = new double[size];
		System.arraycopy(elementData, 0, a, 0, size);
		return a;
	}
	public double get(int index) {
		if(index < 0 || index >= size)
			throw new ArrayIndexOutOfBoundsException(
					"Index: " + index + ", Size: " + size);
		return elementData[index];
	}
	public double set(int index, double value) {
		if(index < 0 || index >= size)
			throw new ArrayIndexOutOfBoundsException(
					"Index: " + index + ", Size: " + size);
		final double old = elementData[index];
		elementData[index] = value;
		return old;
	}
	public boolean add(double value) {
		ensureCapacity(size + 1);
		elementData[size++] = value;
		return true;
	}
	public void add(int index, double element) {
		if(index < 0 || index > size)
			throw new ArrayIndexOutOfBoundsException(
					"Index: " + index + ", Size: " + size);
		ensureCapacity(size + 1);
		System.arraycopy(elementData, index, elementData, index + 1,
				size - index);
		elementData[index] = element;
		++size;
	}
	public double remove(int index) {
		if(index < 0 || index >= size)
			throw new ArrayIndexOutOfBoundsException(
					"Index: " + index + ", Size: " + size);
		final double old = elementData[index];
		final int moved = size - index - 1;
		if(moved > 0) {
			System.arraycopy(elementData, index + 1, elementData, index, moved);
		}
		--size;
		return old;
	}
	public boolean removeElement(double value) {
		final long bits = Double.doubleToLongBits(value);
		for(int i = -1; ++i < size;) {
			if(Double.doubleToLongBits(elementData[i]) == bits) {
				final int moved = size - i - 1;
				if(moved > 0) {
					System.arraycopy(elementData, i + 1, elementData, i, moved);
				}
				--size;
				return true;
			}
		}
		return false;
	}
	public boolean addAll(double[] values) {
		if(values == null || values.length == 0)
			return false;
		ensureCapacity(size + values.length);
		System.arraycopy(values, 0, elementData, size, values.length);
		size += values.length;
		return true;
	}
	public boolean addAll(int index, double[] values) {
		if(index < 0 || index > size)
			throw new ArrayIndexOutOfBoundsException(
					"Index: " + index + ", Size: " + size);
		if(values == null || values.length == 0)
			return false;
		ensureCapacity(size + values.length);
		final int moved = size - index;
		if(moved > 0) {
			System.arraycopy(elementData, index, elementData,
					index + values.length, moved);
		}
		System.arraycopy(values, 0, elementData, index, values.length);
		size += values.length;
		return true;
	}
	public boolean addAll(FastDoubleArrayList values) {
		if(values == null || values.size == 0)
			return false;
		ensureCapacity(size + values.size);
		System.arraycopy(values.elementData, 0, elementData, size, values.size);
		size += values.size;
		return true;
	}
	public boolean addAll(int index, FastDoubleArrayList values) {
		if(index < 0 || index > size)
			throw new ArrayIndexOutOfBoundsException(
					"Index: " + index + ", Size: " + size);
		if(values == null || values.size == 0)
			return false;
		ensureCapacity(size + values.size);
		final int moved = size - index;
		if(moved > 0) {
			System.arraycopy(elementData, index, elementData,
					index + values.size, moved);
		}
		System.arraycopy(values.elementData, 0, elementData, index,
				values.size);
		size += values.size;
		return true;
	}
	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();
		s.writeInt(elementData.length);
		for(int i = -1; ++i < size;) {
			s.writeDouble(elementData[i]);
		}
	}
	private void readObject(ObjectInputStream s)
			throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		elementData = new double[s.readInt()];
		for(int i = -1; ++i < size;) {
			elementData[i] = s.readDouble();
		}
	}
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o == null || getClass() != o.getClass())
			return false;
		final FastDoubleArrayList that = (FastDoubleArrayList) o;
		if(size != that.size)
			return false;
		for(int i = -1; ++i < size;) {
			if(Double.doubleToLongBits(elementData[i]) != Double
					.doubleToLongBits(that.elementData[i]))
				return false;
		}
		return true;
	}
	/**
	 * Double.hashCode(): (int)(bits ^ (bits >>> 32))
	 */
	public int hashCode() {
		int h = 1;
		for(int i = -1; ++i < size;) {
			final long bits = Double.doubleToLongBits(elementData[i]);
			h = 31 * h + (int) (bits ^ bits >>> 32);
		}
		return h;
	}
	public String toString() {
		if(size == 0)
			return "[]";
		final StringBuffer/*StringBuilder*/ sb = new StringBuffer/*StringBuilder*/();
		sb.append('[');
		for(int i = -1; ++i < size;) {
			if(i > 0) {
				sb.append(',').append(' ');
			}
			sb.append(elementData[i]);
		}
		return sb.append(']').toString();
	}
	/**
	 * An unmodifiable view over a {@code FastDoubleArrayList}.
	 */
	private static final class Unmodifiable extends FastDoubleArrayList
			implements Cloneable, RandomAccess, Reusable, Serializable {
		private final FastDoubleArrayList _list;
		private Unmodifiable(FastDoubleArrayList list) {
			super(0);
			_list = list;
		}
		public final FastPrimitiveList/*FastDoubleArrayList*/ unmodifiable() {
			return this;
		}
		public final FastPrimitiveList/*FastDoubleArrayList*/ shared() {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		public final int size() {
			return _list.size();
		}
		public final boolean isEmpty() {
			return _list.isEmpty();
		}
		public final void trimToSize() {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		public final void ensureCapacity(int min) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		public final boolean contains(double value) {
			return _list.contains(value);
		}
		public final int indexOf(double value) {
			return _list.indexOf(value);
		}
		public final int lastIndexOf(double value) {
			return _list.lastIndexOf(value);
		}
		public final Object/*FastDoubleArrayList*/ clone()
				throws CloneNotSupportedException {
			return _list.clone();
		}
		public final double[] toArray() {
			return _list.toArray();
		}
		public final double get(int index) {
			return _list.get(index);
		}
		public final double set(int index, double value) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		public final boolean add(double value) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		public final void add(int index, double element) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		public final double remove(int index) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		public final boolean removeElement(double value) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		public final void clear() {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		public final boolean addAll(double[] values) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		public final boolean addAll(int index, double[] values) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		public final boolean addAll(FastDoubleArrayList values) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		public final boolean addAll(int index, FastDoubleArrayList values) {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		public final boolean equals(Object o) {
			return _list.equals(o);
		}
		public final int hashCode() {
			return _list.hashCode();
		}
		public final String toString() {
			return _list.toString();
		}
		public final void reset() {
			throw new UnsupportedOperationException("Unmodifiable");
		}
		public final FastPrimitiveIterator/*FastDoubleIterator*/ iterator() {
			return new FastDoubleIterator() {
				private final FastDoubleIterator _it = (FastDoubleIterator) _list
						.iterator();
				public boolean hasNext() {
					return _it.hasNext();
				}
				public double next() {
					return _it.next();
				}
				public void remove() {
					throw new UnsupportedOperationException("Unmodifiable");
				}
			};
		}
	}
	/**
	 * A shared view over a {@code FastDoubleArrayList} (reads-write locks).
	 */
	private static final class Shared extends FastDoubleArrayList
			implements Cloneable, RandomAccess, Reusable, Serializable {
		private final FastDoubleArrayList _list;
		private final ReadWriteLock _lock;
		private Shared(FastDoubleArrayList list) {
			super(0);
			_list = list;
			_lock = new ReentrantWriterPreferenceReadWriteLock();
		}
		public final FastPrimitiveList/*FastDoubleArrayList*/ unmodifiable() {
			return _list.unmodifiable();
		}
		public final FastPrimitiveList/*FastDoubleArrayList*/ shared() {
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
							return _list.size();
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
							return _list.isEmpty();
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
							_list.trimToSize();
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
							_list.ensureCapacity(min);
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
		public final boolean contains(double value) {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync r = _lock.readLock();
				for(;;) {
					try {
						r.acquire();
						try {
							return _list.contains(value);
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
		public final int indexOf(double value) {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync r = _lock.readLock();
				for(;;) {
					try {
						r.acquire();
						try {
							return _list.indexOf(value);
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
		public final int lastIndexOf(double value) {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync r = _lock.readLock();
				for(;;) {
					try {
						r.acquire();
						try {
							return _list.lastIndexOf(value);
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
		public final Object/*FastDoubleArrayList*/ clone()
				throws CloneNotSupportedException {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync r = _lock.readLock();
				for(;;) {
					try {
						r.acquire();
						try {
							return _list.clone();
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
		public final double[] toArray() {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync r = _lock.readLock();
				for(;;) {
					try {
						r.acquire();
						try {
							return _list.toArray();
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
		public final double get(int index) {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync r = _lock.readLock();
				for(;;) {
					try {
						r.acquire();
						try {
							return _list.get(index);
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
		public final double set(int index, double value) {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync w = _lock.writeLock();
				for(;;) {
					try {
						w.acquire();
						try {
							return _list.set(index, value);
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
		public final boolean add(double value) {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync w = _lock.writeLock();
				for(;;) {
					try {
						w.acquire();
						try {
							return _list.add(value);
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
		public final void add(int index, double element) {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync w = _lock.writeLock();
				for(;;) {
					try {
						w.acquire();
						try {
							_list.add(index, element);
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
		public final double remove(int index) {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync w = _lock.writeLock();
				for(;;) {
					try {
						w.acquire();
						try {
							return _list.remove(index);
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
		public final boolean removeElement(double value) {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync w = _lock.writeLock();
				for(;;) {
					try {
						w.acquire();
						try {
							return _list.removeElement(value);
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
							_list.clear();
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
			clear();
		}
		public final boolean addAll(double[] values) {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync w = _lock.writeLock();
				for(;;) {
					try {
						w.acquire();
						try {
							return _list.addAll(values);
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
		public final boolean addAll(int index, double[] values) {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync w = _lock.writeLock();
				for(;;) {
					try {
						w.acquire();
						try {
							return _list.addAll(index, values);
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
		public final boolean addAll(FastDoubleArrayList values) {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync w = _lock.writeLock();
				for(;;) {
					try {
						w.acquire();
						try {
							return _list.addAll(values);
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
		public final boolean addAll(int index, FastDoubleArrayList values) {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync w = _lock.writeLock();
				for(;;) {
					try {
						w.acquire();
						try {
							return _list.addAll(index, values);
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
		public final boolean equals(Object o) {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync r = _lock.readLock();
				for(;;) {
					try {
						r.acquire();
						try {
							return _list.equals(o);
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
							return _list.hashCode();
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
							return _list.toString();
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
		public final FastPrimitiveIterator/*FastDoubleIterator*/ iterator() {
			return new FastDoubleIterator() {
				private int _cursor;
				private int _lastRet = -1;
				public boolean hasNext() {
					boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
					try {
						final Sync r = _lock.readLock();
						for(;;) {
							try {
								r.acquire();
								try {
									return _cursor < _list.size();
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
				public double next() {
					boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
					try {
						final Sync r = _lock.readLock();
						for(;;) {
							try {
								r.acquire();
								try {
									if(_cursor >= _list.size())
										throw new NoSuchElementException();
									_lastRet = _cursor;
									return _list.get(_cursor++);
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
				public void remove() {
					if(_lastRet < 0)
						throw new IllegalStateException();
					Shared.this.remove(_lastRet);
					_cursor = _lastRet;
					_lastRet = -1;
				}
			};
		}
	}
	/**
	 * An iterator over a {@code FastDoubleArrayList}.
	 */
	private static final class DoubleIterator implements FastDoubleIterator {
		private final FastDoubleArrayList _list;
		private int _cursor;
		private int _lastRet = -1;
		private DoubleIterator(FastDoubleArrayList list) {
			_list = list;
		}
		public boolean hasNext() {
			return _cursor < _list.size();
		}
		public double next() {
			if(_cursor >= _list.size())
				throw new NoSuchElementException();
			_lastRet = _cursor;
			return _list.get(_cursor++);
		}
		public void remove() {
			if(_lastRet < 0)
				throw new IllegalStateException();
			_list.remove(_lastRet);
			_cursor = _lastRet;
			_lastRet = -1;
		}
	}
}