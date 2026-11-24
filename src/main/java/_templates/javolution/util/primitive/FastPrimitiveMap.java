/*
 * Javolution - Java(TM) Solution for Real-Time and Embedded Systems
 * Copyright (C) 2005 - Javolution (http://javolution.org/)
 * All rights reserved.
 *
 * Permission to use, copy, modify, and distribute this software is
 * freely granted, provided that this notice is preserved.
 */
package _templates.javolution.util.primitive;
import _templates.java.io.Serializable;
import _templates.java.lang.Cloneable;
import _templates.javolution.lang.Reusable;
/**
 * <p> This class represents the abstract base class for all
 *     primitive maps.</p>
 *
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @version 7.1.0, November 22, 2026
 */
public abstract class FastPrimitiveMap
		implements Cloneable, Reusable, Serializable {
	/**
	 * The current number of key-value mappings contained in this map.
	 */
	int _size;
	/**
	 * Returns the number of key-value mappings in this map.
	 *
	 * @return the number of key-value mappings in this map.
	 */
	public int size() {
		return _size;
	}
	/**
	 * Indicates if this map contains no key-value mappings.
	 *
	 * @return <code>true</code> if this map contains no key-value mappings;
	 *         <code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		return _size == 0;
	}
	/**
	 * Removes all key-value mappings from this map.
	 */
	public void clear() {
		_size = 0;
	}
	/**
	 * Resets this map for object pooling / recycling by clearing its mappings.
	 */
	public void reset() {
		_size = 0;
	}
	/**
	 * Trims the capacity of this map instance to fit the map's current size.
	 */
	public abstract void trimToSize();
	/**
	 * Increases the capacity of this map instance, if necessary, to ensure
	 * that it can hold at least the number of mappings specified by the
	 * minimum capacity argument.
	 *
	 * @param min the desired minimum capacity.
	 */
	public abstract void ensureCapacity(int min);
	/**
	 * Returns an unmodifiable live view over this primitive map.
	 *
	 * @return an unmodifiable view over this map.
	 */
	public abstract FastPrimitiveMap unmodifiable();
	/**
	 * Returns a thread-safe (read-write locked) live view over this primitive map.
	 *
	 * @return a shared thread-safe view over this map.
	 */
	public abstract FastPrimitiveMap shared();
	/**
	 * Returns a primitive iterator over the keys in this map.
	 *
	 * @return a primitive iterator over this map's keys.
	 */
	public abstract FastPrimitiveIterator keyIterator();
}