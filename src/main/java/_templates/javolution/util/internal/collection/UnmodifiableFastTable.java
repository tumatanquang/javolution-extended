/*
 * Javolution - Java(TM) Solution for Real-Time and Embedded Systems
 * Copyright (C) 2012 - Javolution (http://javolution.org/)
 * All rights reserved.
 *
 * Permission to use, copy, modify, and distribute this software is
 * freely granted, provided that this notice is preserved.
 */
package _templates.javolution.util.internal.collection;
import _templates.java.lang.UnsupportedOperationException;
import _templates.java.util.Collection;
import _templates.java.util.Iterator;
import _templates.java.util.List;
import _templates.java.util.ListIterator;
import _templates.java.util.RandomAccess;
import _templates.javolution.lang.Reusable;
import _templates.javolution.util.FastTable;
/**
 * An unmodifiable view over a {@code FastTable}.
 */
public final class UnmodifiableFastTable/*<E>*/ extends FastTable
		/*<E>*/ implements List/*<E>*/, Reusable, RandomAccess {
	public UnmodifiableFastTable(FastTable/*<E>*/ inner) {
		super(inner);
	}
	public boolean add(Object/*{E}*/ element) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	public void add(int index, Object/*{E}*/ element) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	public boolean addAll(Collection/*<? extends E>*/ values) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	public boolean addAll(int index, Collection/*<? extends E>*/ values) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	public void clear() {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	public boolean contains(Object value) {
		return super.contains(value);
	}
	public boolean containsAll(Collection/*<?>*/ values) {
		return super.containsAll(values);
	}
	public void delete(Record record) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	public Object/*{E}*/ get(int index) {
		return super.get(index);
	}
	public Record head() {
		return super.head();
	}
	public int indexOf(Object o) {
		return super.indexOf(o);
	}
	public boolean isEmpty() {
		return super.isEmpty();
	}
	public int lastIndexOf(Object o) {
		return super.lastIndexOf(o);
	}
	public Iterator/*<E>*/ iterator() {
		return new Iterator() {
			private final Iterator/*<E>*/ i = UnmodifiableFastTable.super.iterator();
			public boolean hasNext() {
				return i.hasNext();
			}
			public Object next() {
				return i.next();
			}
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	public ListIterator/*<E>*/ listIterator() {
		throw new UnsupportedOperationException("List iterator not supported for unmodifiable collection"); // Must be manually synched by user!
	}
	public ListIterator/*<E>*/ listIterator(int index) {
		throw new UnsupportedOperationException("List iterator not supported for unmodifiable collection"); // Must be manually synched by user!
	}
	public Object/*{E}*/ remove(int index) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	public boolean remove(Object value) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	public boolean removeAll(Collection/*<?>*/ values) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	public void reset() {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	public boolean retainAll(Collection/*<?>*/ values) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	public Object/*{E}*/ set(int index, Object/*{E}*/ element) {
		throw new UnsupportedOperationException("Unmodifiable");
	}
	public int size() {
		return super.size();
	}
	public List/*<E>*/ subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException("Sub-List not supported for unmodifiable collection");
	}
	public Record tail() {
		return super.tail();
	}
	public Object[] toArray() {
		return super.toArray();
	}
	public Object/*{<T> T}*/[] toArray(Object/*{T}*/[] array) {
		return super.toArray(array);
	}
	public Object/*{E}*/ valueOf(Record record) {
		return super.valueOf(record);
	}
}