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
import _templates.java.util.RandomAccess;
import _templates.javolution.lang.Reusable;
/**
 * <p> This class represents the abstract base class for all
 *     primitive array lists.</p>
 *
 * @version 5.7.8, July 27, 2026
 */
public abstract class FastPrimitiveList
		implements Cloneable, RandomAccess, Reusable, Serializable {
	/**
	 * Default initial capacity for newly constructed primitive array lists.
	 */
	static final int DEFAULT_INITIAL_CAPACITY = 10;
	/**
	 * The current number of elements contained in this list.
	 */
	int size;
	/**
	 * Returns the number of elements in this list.
	 *
	 * @return the number of elements in this list.
	 */
	public int size() {
		return size;
	}
	/**
	 * Indicates if this list contains no elements.
	 *
	 * @return <code>true</code> if this list contains no elements;
	 *         <code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	/**
	 * Removes all elements from this list.
	 */
	public void clear() {
		size = 0;
	}
	/**
	 * Resets this list for object pooling / recycling by clearing its elements.
	 */
	public void reset() {
		size = 0;
	}
	/**
	 * Trims the capacity of this list instance to be the list's current size.
	 */
	public abstract void trimToSize();
	/**
	 * Increases the capacity of this list instance, if necessary, to ensure
	 * that it can hold at least the number of elements specified by the
	 * minimum capacity argument.
	 *
	 * @param min the desired minimum capacity.
	 */
	public abstract void ensureCapacity(int min);
	/**
	 * Returns an unmodifiable view over this primitive list.
	 *
	 * @return an unmodifiable view over this list.
	 */
	public abstract FastPrimitiveList unmodifiable();
	/**
	 * Returns a thread-safe (read-write locked) view over this primitive list.
	 *
	 * @return a shared thread-safe view over this list.
	 */
	public abstract FastPrimitiveList shared();
	/**
	 * Returns a primitive iterator over the elements in this list.
	 *
	 * @return a primitive iterator over this list's elements.
	 */
	public abstract FastPrimitiveIterator iterator();
}