/*
 * Javolution - Java(TM) Solution for Real-Time and Embedded Systems
 * Copyright (C) 2012 - Javolution (http://javolution.org/)
 * All rights reserved.
 *
 * Permission to use, copy, modify, and distribute this software is
 * freely granted, provided that this notice is preserved.
 */
package _templates.javolution.util.internal.collection;
import _templates.java.lang.IllegalStateException;
import _templates.java.util.Collection;
import _templates.java.util.Iterator;
import _templates.java.util.List;
import _templates.java.util.ListIterator;
import _templates.java.util.NoSuchElementException;
import _templates.javolution.lang.Reusable;
import _templates.javolution.util.FastList;
import _templates.javolution.util.concurrent.locks.ReadWriteLock;
import _templates.javolution.util.concurrent.locks.ReentrantWriterPreferenceReadWriteLock;
import _templates.javolution.util.concurrent.locks.Sync;
/**
 * A shared view over a {@code FastList} (reads-write locks).
 */
public final class SharedFastList/*<E>*/ extends FastList/*<E>*/ implements List/*<E>*/, Reusable {
	private final ReadWriteLock _lock;
	public SharedFastList(FastList/*<E>*/ inner) {
		super(inner);
		_lock = new ReentrantWriterPreferenceReadWriteLock();
	}
	public boolean add(Object/*{E}*/ o) {
		boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
		try {
			final Sync w = _lock.writeLock();
			for(;;) {
				try {
					w.acquire();
					try {
						return super.add(o);
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
	public boolean remove(Object o) {
		boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
		try {
			final Sync w = _lock.writeLock();
			for(;;) {
				try {
					w.acquire();
					try {
						return super.remove(o);
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
	public void add(int index, Object/*{E}*/ element) {
		boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
		try {
			final Sync w = _lock.writeLock();
			for(;;) {
				try {
					w.acquire();
					try {
						super.add(index, element);
					}
					finally {
						w.release();
					}
					break;
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
	public boolean addAll(Collection/*<? extends E>*/ values) {
		boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
		try {
			final Sync w = _lock.writeLock();
			for(;;) {
				try {
					w.acquire();
					try {
						return super.addAll(values);
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
	public boolean addAll(int index, Collection/*<? extends E>*/ values) {
		boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
		try {
			final Sync w = _lock.writeLock();
			for(;;) {
				try {
					w.acquire();
					try {
						return super.addAll(index, values);
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
	public void clear() {
		boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
		try {
			final Sync w = _lock.writeLock();
			for(;;) {
				try {
					w.acquire();
					try {
						super.clear();
					}
					finally {
						w.release();
					}
					break;
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
	public boolean contains(Object o) {
		boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
		try {
			final Sync r = _lock.readLock();
			for(;;) {
				try {
					r.acquire();
					try {
						return super.contains(o);
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
	public boolean containsAll(Collection/*<?>*/ values) {
		boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
		try {
			final Sync r = _lock.readLock();
			for(;;) {
				try {
					r.acquire();
					try {
						return super.containsAll(values);
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
	public void delete(Record record) {
		boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
		try {
			final Sync w = _lock.writeLock();
			for(;;) {
				try {
					w.acquire();
					try {
						super.delete(record);
					}
					finally {
						w.release();
					}
					break;
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
	public Object/*{E}*/ get(int index) {
		boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
		try {
			final Sync r = _lock.readLock();
			for(;;) {
				try {
					r.acquire();
					try {
						return super.get(index);
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
	public Record/*Node<E>*/ head() {
		boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
		try {
			final Sync r = _lock.readLock();
			for(;;) {
				try {
					r.acquire();
					try {
						return super.head();
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
	public int indexOf(Object o) {
		boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
		try {
			final Sync r = _lock.readLock();
			for(;;) {
				try {
					r.acquire();
					try {
						return super.indexOf(o);
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
	public boolean isEmpty() {
		boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
		try {
			final Sync r = _lock.readLock();
			for(;;) {
				try {
					r.acquire();
					try {
						return super.isEmpty();
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
	public int lastIndexOf(Object o) {
		boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
		try {
			final Sync r = _lock.readLock();
			for(;;) {
				try {
					r.acquire();
					try {
						return super.lastIndexOf(o);
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
	public Iterator/*<E>*/ iterator() {
		return listIterator(0);
	}
	public ListIterator/*<E>*/ listIterator() {
		return listIterator(0);
	}
	public ListIterator/*<E>*/ listIterator(int index) {
		boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
		try {
			final Sync r = _lock.readLock();
			for(;;) {
				try {
					r.acquire();
					try {
						final int sz = super.size();
						if(index < 0 || index > sz)
							throw new IndexOutOfBoundsException("index: " + index + ", size: " + sz);
						return new SharedListIterator(index);
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
	public Object/*{E}*/ remove(int index) {
		boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
		try {
			final Sync w = _lock.writeLock();
			for(;;) {
				try {
					w.acquire();
					try {
						return super.remove(index);
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
	public boolean removeAll(Collection/*<?>*/ values) {
		boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
		try {
			final Sync w = _lock.writeLock();
			for(;;) {
				try {
					w.acquire();
					try {
						return super.removeAll(values);
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
	public void reset() {
		boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
		try {
			final Sync w = _lock.writeLock();
			for(;;) {
				try {
					w.acquire();
					try {
						super.reset();
					}
					finally {
						w.release();
					}
					break;
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
	public boolean retainAll(Collection/*<?>*/ values) {
		boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
		try {
			final Sync w = _lock.writeLock();
			for(;;) {
				try {
					w.acquire();
					try {
						return super.retainAll(values);
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
	public Object/*{E}*/ set(int index, Object/*{E}*/ element) {
		boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
		try {
			final Sync w = _lock.writeLock();
			for(;;) {
				try {
					w.acquire();
					try {
						return super.set(index, element);
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
	public int size() {
		boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
		try {
			final Sync r = _lock.readLock();
			for(;;) {
				try {
					r.acquire();
					try {
						return super.size();
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
	public List/*<E>*/ subList(int fromIndex, int toIndex) {
		boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
		try {
			final Sync w = _lock.readLock();
			for(;;) {
				try {
					w.acquire();
					try {
						return super.subList(fromIndex, toIndex);
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
	public Record/*Node<E>*/ tail() {
		boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
		try {
			final Sync r = _lock.readLock();
			for(;;) {
				try {
					r.acquire();
					try {
						return super.tail();
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
	public Object[] toArray() {
		boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
		try {
			final Sync r = _lock.readLock();
			for(;;) {
				try {
					r.acquire();
					try {
						return super.toArray();
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
	public Object/*{<T> T}*/[] toArray(Object/*{T}*/[] array) {
		boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
		try {
			final Sync r = _lock.readLock();
			for(;;) {
				try {
					r.acquire();
					try {
						return super.toArray(array);
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
	public Object/*{E}*/ valueOf(Record record) {
		boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
		try {
			final Sync r = _lock.readLock();
			for(;;) {
				try {
					r.acquire();
					try {
						return super.valueOf(record);
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
	/**
	 * A thread-safe list iterator that performs per-operation locking.
	 * Read operations ({@code hasNext}, {@code next}, {@code hasPrevious},
	 * {@code previous}) acquire the read lock. Write operations
	 * ({@code remove}, {@code set}, {@code add}) acquire the write lock.
	 *
	 * <p>This iterator operates directly on the live collection and is
	 * <i>weakly consistent</i>: modifications by other threads between
	 * iterator method calls may affect iteration order and element visibility.
	 * Each individual operation is atomic.</p>
	 */
	private final class SharedListIterator implements ListIterator/*<E>*/ {
		private int _nextIndex;
		private int _currentIndex; // -1 when no current element
		private SharedListIterator(int startIndex) {
			_nextIndex = startIndex;
			_currentIndex = -1;
		}
		public boolean hasNext() {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync r = _lock.readLock();
				for(;;) {
					try {
						r.acquire();
						try {
							return _nextIndex < SharedFastList.super.size();
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
		public Object/*{E}*/ next() {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync r = _lock.readLock();
				for(;;) {
					try {
						r.acquire();
						try {
							if(_nextIndex >= SharedFastList.super.size())
								throw new NoSuchElementException();
							_currentIndex = _nextIndex;
							return SharedFastList.super.get(_nextIndex++);
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
		public boolean hasPrevious() {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync r = _lock.readLock();
				for(;;) {
					try {
						r.acquire();
						try {
							return _nextIndex > 0 && _nextIndex <= SharedFastList.super.size();
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
		public Object/*{E}*/ previous() {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync r = _lock.readLock();
				for(;;) {
					try {
						r.acquire();
						try {
							if(_nextIndex <= 0)
								throw new NoSuchElementException();
							final int idx = --_nextIndex;
							if(idx >= SharedFastList.super.size())
								throw new NoSuchElementException();
							_currentIndex = idx;
							return SharedFastList.super.get(idx);
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
		public int nextIndex() {
			return _nextIndex;
		}
		public int previousIndex() {
			return _nextIndex - 1;
		}
		public void remove() {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync w = _lock.writeLock();
				for(;;) {
					try {
						w.acquire();
						try {
							if(_currentIndex < 0)
								throw new IllegalStateException();
							SharedFastList.super.remove(_currentIndex);
							if(_currentIndex < _nextIndex) {
								--_nextIndex;
							}
							_currentIndex = -1;
						}
						finally {
							w.release();
						}
						break;
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
		public void set(Object/*{E}*/ value) {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync w = _lock.writeLock();
				for(;;) {
					try {
						w.acquire();
						try {
							if(_currentIndex < 0)
								throw new IllegalStateException();
							SharedFastList.super.set(_currentIndex, value);
						}
						finally {
							w.release();
						}
						break;
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
		public void add(Object/*{E}*/ value) {
			boolean wasInterrupted = /*@JVM-1.1+@ true ? Thread.interrupted() : /**/false;
			try {
				final Sync w = _lock.writeLock();
				for(;;) {
					try {
						w.acquire();
						try {
							SharedFastList.super.add(_nextIndex++, value);
							_currentIndex = -1;
						}
						finally {
							w.release();
						}
						break;
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
	}
}