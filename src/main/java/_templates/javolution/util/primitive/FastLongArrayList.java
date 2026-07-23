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
import _templates.java.util.RandomAccess;
/**
 * Implement <code>FastArrayList</code> for the <code>long</code> primitive data type.
 * @since 5.7.1
 */
public class FastLongArrayList
		implements Cloneable, RandomAccess, Serializable {
	public static final int DEFAULT_INITIAL_CAPACITY = 10;
	private transient long[] elementData;
	private int size;
	public FastLongArrayList() {
		this(DEFAULT_INITIAL_CAPACITY);
	}
	public FastLongArrayList(int initialCapacity) {
		if(initialCapacity < 0)
			throw new IllegalArgumentException(
					"Illegal capacity: " + initialCapacity);
		elementData = new long[initialCapacity];
	}
	public FastLongArrayList(long[] src) {
		if(src == null)
			throw new NullPointerException("source array is null");
		size = src.length;
		elementData = new long[size];
		System.arraycopy(src, 0, elementData, 0, size);
	}
	public void trimToSize() {
		if(size >= elementData.length)
			return;
		final long[] tmp = new long[size];
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
		final long[] tmp = new long[newCap];
		System.arraycopy(elementData, 0, tmp, 0, size);
		elementData = tmp;
	}
	public int size() {
		return size;
	}
	public boolean isEmpty() {
		return size == 0;
	}
	public boolean contains(long value) {
		return indexOf(value) >= 0;
	}
	public int indexOf(long value) {
		if(size == 0)
			return -1;
		for(int i = -1; ++i < size;) {
			if(elementData[i] == value)
				return i;
		}
		return -1;
	}
	public int lastIndexOf(long value) {
		if(size == 0)
			return -1;
		for(int i = size; --i >= 0;) {
			if(elementData[i] == value)
				return i;
		}
		return -1;
	}
	public Object/*FastLongArrayList*/ clone()
			throws CloneNotSupportedException {
		/*@JVM-1.1+@
		if(true) {
			final FastLongArrayList c = (FastLongArrayList) super.clone();
			c.elementData = new long[elementData.length];
			System.arraycopy(elementData, 0, c.elementData, 0, size);
			return c;
		}
		/**/
		throw new UnsupportedOperationException("J2ME Not Supported Yet");
	}
	public long[] toArray() {
		final long[] a = new long[size];
		System.arraycopy(elementData, 0, a, 0, size);
		return a;
	}
	public long get(int index) {
		if(index < 0 || index >= size)
			throw new ArrayIndexOutOfBoundsException(
					"Index: " + index + ", Size: " + size);
		return elementData[index];
	}
	public long set(int index, long value) {
		if(index < 0 || index >= size)
			throw new ArrayIndexOutOfBoundsException(
					"Index: " + index + ", Size: " + size);
		final long old = elementData[index];
		elementData[index] = value;
		return old;
	}
	public boolean add(long value) {
		ensureCapacity(size + 1);
		elementData[size++] = value;
		return true;
	}
	public void add(int index, long element) {
		if(index < 0 || index > size)
			throw new ArrayIndexOutOfBoundsException(
					"Index: " + index + ", Size: " + size);
		ensureCapacity(size + 1);
		System.arraycopy(elementData, index, elementData, index + 1,
				size - index);
		elementData[index] = element;
		++size;
	}
	public long remove(int index) {
		if(index < 0 || index >= size)
			throw new ArrayIndexOutOfBoundsException(
					"Index: " + index + ", Size: " + size);
		final long old = elementData[index];
		final int moved = size - index - 1;
		if(moved > 0) {
			System.arraycopy(elementData, index + 1, elementData, index, moved);
		}
		--size;
		return old;
	}
	public boolean removeElement(long value) {
		for(int i = -1; ++i < size;) {
			if(elementData[i] == value) {
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
	public void clear() {
		size = 0;
	}
	public boolean addAll(long[] values) {
		if(values == null || values.length == 0)
			return false;
		ensureCapacity(size + values.length);
		System.arraycopy(values, 0, elementData, size, values.length);
		size += values.length;
		return true;
	}
	public boolean addAll(int index, long[] values) {
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
	public boolean addAll(FastLongArrayList values) {
		if(values == null || values.size == 0)
			return false;
		ensureCapacity(size + values.size);
		System.arraycopy(values.elementData, 0, elementData, size, values.size);
		size += values.size;
		return true;
	}
	public boolean addAll(int index, FastLongArrayList values) {
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
			s.writeLong(elementData[i]);
		}
	}
	private void readObject(ObjectInputStream s)
			throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		elementData = new long[s.readInt()];
		for(int i = -1; ++i < size;) {
			elementData[i] = s.readLong();
		}
	}
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o == null || getClass() != o.getClass())
			return false;
		final FastLongArrayList that = (FastLongArrayList) o;
		if(size != that.size)
			return false;
		for(int i = -1; ++i < size;) {
			if(elementData[i] != that.elementData[i])
				return false;
		}
		return true;
	}
	/**
	 * Long.hashCode(): (int)(value ^ (value >>> 32))
	 */
	public int hashCode() {
		int h = 1;
		for(int i = -1; ++i < size;) {
			final long v = elementData[i];
			h = 31 * h + (int) (v ^ v >>> 32);
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
}