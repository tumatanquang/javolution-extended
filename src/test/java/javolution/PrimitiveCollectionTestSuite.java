/*
 * Javolution - Java(TM) Solution for Real-Time and Embedded Systems
 * Copyright (C) 2026 - Javolution
 * All rights reserved.
 *
 * Permission to use, copy, modify, and distribute this software is
 * freely granted, provided that this notice is preserved.
 */
package javolution;
import _templates.javolution.testing.TestCase;
import _templates.javolution.testing.TestContext;
import _templates.java.lang.CloneNotSupportedException;
import _templates.java.lang.IllegalStateException;
import _templates.java.lang.UnsupportedOperationException;
import _templates.java.util.Iterator;
import _templates.java.util.NoSuchElementException;
import _templates.javolution.testing.TestSuite;
import _templates.javolution.util.primitive.FastBooleanIterator;
import _templates.javolution.util.primitive.FastIntBooleanMap;
import _templates.javolution.util.primitive.FastIntByteMap;
import _templates.javolution.util.primitive.FastIntCharMap;
import _templates.javolution.util.primitive.FastIntDoubleMap;
import _templates.javolution.util.primitive.FastIntFloatMap;
import _templates.javolution.util.primitive.FastIntIntMap;
import _templates.javolution.util.primitive.FastIntLongMap;
import _templates.javolution.util.primitive.FastIntObjectMap;
import _templates.javolution.util.primitive.FastIntShortMap;
import _templates.javolution.util.primitive.FastLongBooleanMap;
import _templates.javolution.util.primitive.FastLongByteMap;
import _templates.javolution.util.primitive.FastLongCharMap;
import _templates.javolution.util.primitive.FastLongDoubleMap;
import _templates.javolution.util.primitive.FastLongFloatMap;
import _templates.javolution.util.primitive.FastLongIntMap;
import _templates.javolution.util.primitive.FastLongLongMap;
import _templates.javolution.util.primitive.FastLongObjectMap;
import _templates.javolution.util.primitive.FastLongShortMap;
import _templates.javolution.util.primitive.FastBooleanSet;
import _templates.javolution.util.primitive.FastBooleanTable;
import _templates.javolution.util.primitive.FastByteIterator;
import _templates.javolution.util.primitive.FastByteSet;
import _templates.javolution.util.primitive.FastByteTable;
import _templates.javolution.util.primitive.FastCharIterator;
import _templates.javolution.util.primitive.FastCharSet;
import _templates.javolution.util.primitive.FastCharTable;
import _templates.javolution.util.primitive.FastDoubleIterator;
import _templates.javolution.util.primitive.FastDoubleSet;
import _templates.javolution.util.primitive.FastDoubleTable;
import _templates.javolution.util.primitive.FastFloatIterator;
import _templates.javolution.util.primitive.FastFloatSet;
import _templates.javolution.util.primitive.FastFloatTable;
import _templates.javolution.util.primitive.FastIntIterator;
import _templates.javolution.util.primitive.FastIntSet;
import _templates.javolution.util.primitive.FastIntTable;
import _templates.javolution.util.primitive.FastLongIterator;
import _templates.javolution.util.primitive.FastLongSet;
import _templates.javolution.util.primitive.FastLongTable;
import _templates.javolution.util.primitive.FastShortIterator;
import _templates.javolution.util.primitive.FastShortSet;
import _templates.javolution.util.primitive.FastShortTable;
/**
 * Regression coverage for all primitive table, set, and map families.
 */
public final class PrimitiveCollectionTestSuite extends TestSuite {
	private static final int[] CAPACITIES = { 16, 1024, 1025, 2048, 4096 };
	public PrimitiveCollectionTestSuite() {
		addTest(new TableCapacity());
		addTest(new TableSelfInsertion());
		addTest(new SetTombstoneChurn());
		addTest(new SetEmptyFastPaths());
		addTest(new SharedViews());
		addTest(new MapPutGetRemove());
		addTest(new MapTombstoneChurn());
		addTest(new MapEmptyFastPaths());
		addTest(new MapViewsAndIterators());
	}
	private final class TableCapacity extends TestCase {
		public String getName() {
			return "Primitive tables: segmented capacity and trim";
		}
		public void execute() {}
		public void validate() {
			for(int i = 0; i < CAPACITIES.length; ++i) {
				final int capacity = CAPACITIES[i];
				checkBooleanCapacity(capacity);
				checkByteCapacity(capacity);
				checkCharCapacity(capacity);
				checkDoubleCapacity(capacity);
				checkFloatCapacity(capacity);
				checkIntCapacity(capacity);
				checkLongCapacity(capacity);
				checkShortCapacity(capacity);
			}
		}
	}
	private final class TableSelfInsertion extends TestCase {
		public String getName() {
			return "Primitive tables: self insertion";
		}
		public void execute() {}
		public void validate() {
			final int length = 1030;
			final int middle = length >> 2;
			checkBooleanSelfInsertion(0);
			checkBooleanSelfInsertion(middle);
			checkBooleanSelfInsertion(length);
			checkByteSelfInsertion(0);
			checkByteSelfInsertion(middle);
			checkByteSelfInsertion(length);
			checkCharSelfInsertion(0);
			checkCharSelfInsertion(middle);
			checkCharSelfInsertion(length);
			checkDoubleSelfInsertion(0);
			checkDoubleSelfInsertion(middle);
			checkDoubleSelfInsertion(length);
			checkFloatSelfInsertion(0);
			checkFloatSelfInsertion(middle);
			checkFloatSelfInsertion(length);
			checkIntSelfInsertion(0);
			checkIntSelfInsertion(middle);
			checkIntSelfInsertion(length);
			checkLongSelfInsertion(0);
			checkLongSelfInsertion(middle);
			checkLongSelfInsertion(length);
			checkShortSelfInsertion(0);
			checkShortSelfInsertion(middle);
			checkShortSelfInsertion(length);
		}
	}
	private final class SetTombstoneChurn extends TestCase {
		public String getName() {
			return "Primitive sets: bounded probing after tombstone churn";
		}
		public void execute() {}
		public void validate() {
			checkByteSetChurn();
			checkCharSetChurn();
			checkDoubleSetChurn();
			checkFloatSetChurn();
			checkIntSetChurn();
			checkLongSetChurn();
			checkShortSetChurn();
			checkFloatingPointSetSemantics();
		}
	}
	private final class SetEmptyFastPaths extends TestCase {
		public String getName() {
			return "Primitive sets: empty fast paths";
		}
		public void execute() {}
		public void validate() {
			checkBooleanSetEmptyFastPaths();
			checkByteSetEmptyFastPaths();
			checkCharSetEmptyFastPaths();
			checkDoubleSetEmptyFastPaths();
			checkFloatSetEmptyFastPaths();
			checkIntSetEmptyFastPaths();
			checkLongSetEmptyFastPaths();
			checkShortSetEmptyFastPaths();
		}
	}
	private final class SharedViews extends TestCase {
		public String getName() {
			return "Primitive shared views: iterator locking and concurrent writes";
		}
		public void execute() {}
		public void validate() throws Exception {
			checkBooleanSharedTable();
			checkBooleanSharedSet();
			checkByteSharedTable();
			checkByteSharedSet();
			checkCharSharedTable();
			checkCharSharedSet();
			checkDoubleSharedTable();
			checkDoubleSharedSet();
			checkFloatSharedTable();
			checkFloatSharedSet();
			checkIntSharedTable();
			checkIntSharedSet();
			checkLongSharedTable();
			checkLongSharedSet();
			checkShortSharedTable();
			checkShortSharedSet();
			checkConcurrentIntTableWrites();
		}
	}
	private void checkBooleanCapacity(int capacity) {
		final FastBooleanTable table = new FastBooleanTable(capacity);
		for(int i = 0; i < capacity; ++i) {
			table.add((i & 1) != 0);
		}
		TestContext.assertEquals(capacity, table.size());
		for(int i = 0; i < capacity; ++i) {
			TestContext.assertTrue(table.get(i) == ((i & 1) != 0));
		}
		table.trimToSize();
		TestContext.assertEquals(capacity, table.size());
	}
	private void checkBooleanSelfInsertion(int index) {
		final int originalSize = 1030;
		final boolean[] values = new boolean[originalSize];
		for(int i = 0; i < originalSize; ++i) {
			values[i] = (i & 1) != 0;
		}
		final FastBooleanTable table = new FastBooleanTable(values);
		TestContext.assertTrue(table.addAll(index, table));
		TestContext.assertEquals(originalSize << 1, table.size());
		for(int i = 0; i < table.size(); ++i) {
			final int source = i < index ? i
					: i < index + originalSize ? i - index : i - originalSize;
			TestContext.assertTrue(table.get(i) == values[source]);
		}
		table.trimToSize();
	}
	private void checkBooleanSharedTable() {
		final FastBooleanTable table = new FastBooleanTable();
		table.add(false);
		table.add(true);
		final FastBooleanTable shared = (FastBooleanTable) table.shared();
		final FastBooleanIterator iterator = (FastBooleanIterator) shared.iterator();
		TestContext.assertTrue(iterator.hasNext());
		TestContext.assertTrue(iterator.next() == false);
		iterator.remove();
		TestContext.assertEquals(1, shared.size());
		TestContext.assertTrue(shared.get(0) == true);
		final FastBooleanTable source = new FastBooleanTable(new boolean[] { false, true });
		final FastBooleanTable unmodifiable = (FastBooleanTable) source.unmodifiable();
		final FastBooleanTable fromView = new FastBooleanTable();
		TestContext.assertTrue(fromView.addAll(unmodifiable));
		TestContext.assertTrue(source.equals(unmodifiable));
		TestContext.assertTrue(unmodifiable.equals(source));
		final FastBooleanTable sourceShared = (FastBooleanTable) source.shared();
		source.add(false);
		TestContext.assertEquals(3, unmodifiable.size());
		TestContext.assertEquals(3, sourceShared.size());
		TestContext.assertTrue(unmodifiable.get(2) == false);
		TestContext.assertTrue(sourceShared.get(2) == false);
		source.remove(2);
		TestContext.assertEquals(2, unmodifiable.size());
		TestContext.assertEquals(2, sourceShared.size());
		final FastBooleanTable fromShared = new FastBooleanTable();
		TestContext.assertTrue(fromShared.addAll((FastBooleanTable) source.shared()));
		final FastBooleanTable unmodifiableOwner = new FastBooleanTable(new boolean[] { false, true });
		TestContext.assertTrue(unmodifiableOwner.addAll(1,
				(FastBooleanTable) unmodifiableOwner.unmodifiable()));
		TestContext.assertTrue(unmodifiableOwner.equals(new FastBooleanTable(new boolean[] { false, false, true, true })));
		final FastBooleanTable sharedOwner = new FastBooleanTable(new boolean[] { false, true });
		final FastBooleanTable ownedShared = (FastBooleanTable) sharedOwner.shared();
		TestContext.assertTrue(sharedOwner.addAll(1,
				ownedShared));
		TestContext.assertTrue(sharedOwner.equals(new FastBooleanTable(new boolean[] { false, false, true, true })));
		final FastBooleanTable receiverBacking = new FastBooleanTable(
				new boolean[] { false, true });
		final FastBooleanTable receiverShared = (FastBooleanTable) receiverBacking.shared();
		TestContext.assertTrue(receiverShared.addAll(1, receiverBacking));
		TestContext.assertTrue(receiverShared.equals(new FastBooleanTable(
				new boolean[] { false, false, true, true })));
	}
	private void checkBooleanSharedSet() {
		final FastBooleanSet set = new FastBooleanSet();
		set.add(false);
		set.add(true);
		final FastBooleanSet shared = (FastBooleanSet) set.shared();
		final FastBooleanIterator iterator = (FastBooleanIterator) shared.iterator();
		TestContext.assertTrue(iterator.hasNext());
		final boolean removed = iterator.next();
		iterator.remove();
		TestContext.assertFalse(shared.contains(removed));
		TestContext.assertEquals(1, shared.size());
		final FastBooleanSet source = new FastBooleanSet(new boolean[] { false, true });
		final FastBooleanSet unmodifiable = (FastBooleanSet) source.unmodifiable();
		final FastBooleanSet copied = new FastBooleanSet(unmodifiable);
		TestContext.assertTrue(copied.equals(source));
		final FastBooleanSet sourceShared = (FastBooleanSet) source.shared();
		TestContext.assertTrue(source.remove(false));
		TestContext.assertFalse(unmodifiable.contains(false));
		TestContext.assertFalse(sourceShared.contains(false));
		TestContext.assertTrue(source.add(false));
		TestContext.assertTrue(unmodifiable.contains(false));
		TestContext.assertTrue(sourceShared.contains(false));
		final FastBooleanSet target = (FastBooleanSet) new FastBooleanSet().shared();
		TestContext.assertTrue(target.addAll(unmodifiable));
		TestContext.assertTrue(target.containsAll(unmodifiable));
		TestContext.assertTrue(target.equals(unmodifiable));
		TestContext.assertTrue(unmodifiable.equals(target));
		TestContext.assertTrue(target.removeAll((FastBooleanSet) source.shared()));
		TestContext.assertEquals(0, target.size());
		TestContext.assertTrue(target.addAll(unmodifiable));
		final FastBooleanSet retained = new FastBooleanSet(new boolean[] { true });
		TestContext.assertTrue(target.retainAll((FastBooleanSet) retained.shared()));
		TestContext.assertEquals(1, target.size());
		TestContext.assertTrue(target.contains(true));
	}
	private void checkByteCapacity(int capacity) {
		final FastByteTable table = new FastByteTable(capacity);
		for(int i = 0; i < capacity; ++i) {
			table.add((byte) i);
		}
		TestContext.assertEquals(capacity, table.size());
		for(int i = 0; i < capacity; ++i) {
			TestContext.assertTrue(table.get(i) == (byte) i);
		}
		table.trimToSize();
		TestContext.assertEquals(capacity, table.size());
	}
	private void checkByteSelfInsertion(int index) {
		final int originalSize = 1030;
		final byte[] values = new byte[originalSize];
		for(int i = 0; i < originalSize; ++i) {
			values[i] = (byte) i;
		}
		final FastByteTable table = new FastByteTable(values);
		TestContext.assertTrue(table.addAll(index, table));
		TestContext.assertEquals(originalSize << 1, table.size());
		for(int i = 0; i < table.size(); ++i) {
			final int source = i < index ? i
					: i < index + originalSize ? i - index : i - originalSize;
			TestContext.assertTrue(table.get(i) == values[source]);
		}
		table.trimToSize();
	}
	private void checkByteSharedTable() {
		final FastByteTable table = new FastByteTable();
		table.add((byte) 1);
		table.add((byte) 2);
		final FastByteTable shared = (FastByteTable) table.shared();
		final FastByteIterator iterator = (FastByteIterator) shared.iterator();
		TestContext.assertTrue(iterator.hasNext());
		TestContext.assertTrue(iterator.next() == (byte) 1);
		iterator.remove();
		TestContext.assertEquals(1, shared.size());
		TestContext.assertTrue(shared.get(0) == (byte) 2);
		final FastByteTable source = new FastByteTable(new byte[] { (byte) 1, (byte) 2 });
		final FastByteTable unmodifiable = (FastByteTable) source.unmodifiable();
		final FastByteTable fromView = new FastByteTable();
		TestContext.assertTrue(fromView.addAll(unmodifiable));
		TestContext.assertTrue(source.equals(unmodifiable));
		TestContext.assertTrue(unmodifiable.equals(source));
		final FastByteTable sourceShared = (FastByteTable) source.shared();
		source.add((byte) 3);
		TestContext.assertEquals(3, unmodifiable.size());
		TestContext.assertEquals(3, sourceShared.size());
		TestContext.assertTrue(unmodifiable.get(2) == (byte) 3);
		TestContext.assertTrue(sourceShared.get(2) == (byte) 3);
		source.remove(2);
		TestContext.assertEquals(2, unmodifiable.size());
		TestContext.assertEquals(2, sourceShared.size());
		final FastByteTable fromShared = new FastByteTable();
		TestContext.assertTrue(fromShared.addAll((FastByteTable) source.shared()));
		final FastByteTable unmodifiableOwner = new FastByteTable(new byte[] { (byte) 1, (byte) 2 });
		TestContext.assertTrue(unmodifiableOwner.addAll(1,
				(FastByteTable) unmodifiableOwner.unmodifiable()));
		TestContext.assertTrue(unmodifiableOwner.equals(new FastByteTable(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 2 })));
		final FastByteTable sharedOwner = new FastByteTable(new byte[] { (byte) 1, (byte) 2 });
		final FastByteTable ownedShared = (FastByteTable) sharedOwner.shared();
		TestContext.assertTrue(sharedOwner.addAll(1,
				ownedShared));
		TestContext.assertTrue(sharedOwner.equals(new FastByteTable(new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 2 })));
		final FastByteTable receiverBacking = new FastByteTable(
				new byte[] { (byte) 1, (byte) 2 });
		final FastByteTable receiverShared = (FastByteTable) receiverBacking.shared();
		TestContext.assertTrue(receiverShared.addAll(1, receiverBacking));
		TestContext.assertTrue(receiverShared.equals(new FastByteTable(
				new byte[] { (byte) 1, (byte) 1, (byte) 2, (byte) 2 })));
	}
	private void checkByteSharedSet() {
		final FastByteSet set = new FastByteSet();
		set.add((byte) 1);
		set.add((byte) 2);
		final FastByteSet shared = (FastByteSet) set.shared();
		final FastByteIterator iterator = (FastByteIterator) shared.iterator();
		TestContext.assertTrue(iterator.hasNext());
		final byte removed = iterator.next();
		iterator.remove();
		TestContext.assertFalse(shared.contains(removed));
		TestContext.assertEquals(1, shared.size());
		final FastByteSet source = new FastByteSet(new byte[] { (byte) 1, (byte) 2 });
		final FastByteSet unmodifiable = (FastByteSet) source.unmodifiable();
		final FastByteSet copied = new FastByteSet(unmodifiable);
		TestContext.assertTrue(copied.equals(source));
		final FastByteSet sourceShared = (FastByteSet) source.shared();
		TestContext.assertTrue(source.remove((byte) 1));
		TestContext.assertFalse(unmodifiable.contains((byte) 1));
		TestContext.assertFalse(sourceShared.contains((byte) 1));
		TestContext.assertTrue(source.add((byte) 1));
		TestContext.assertTrue(unmodifiable.contains((byte) 1));
		TestContext.assertTrue(sourceShared.contains((byte) 1));
		final FastByteSet target = (FastByteSet) new FastByteSet().shared();
		TestContext.assertTrue(target.addAll(unmodifiable));
		TestContext.assertTrue(target.containsAll(unmodifiable));
		TestContext.assertTrue(target.equals(unmodifiable));
		TestContext.assertTrue(unmodifiable.equals(target));
		TestContext.assertTrue(target.removeAll((FastByteSet) source.shared()));
		TestContext.assertEquals(0, target.size());
		TestContext.assertTrue(target.addAll(unmodifiable));
		final FastByteSet retained = new FastByteSet(new byte[] { (byte) 2 });
		TestContext.assertTrue(target.retainAll((FastByteSet) retained.shared()));
		TestContext.assertEquals(1, target.size());
		TestContext.assertTrue(target.contains((byte) 2));
	}
	private void checkCharCapacity(int capacity) {
		final FastCharTable table = new FastCharTable(capacity);
		for(int i = 0; i < capacity; ++i) {
			table.add((char) i);
		}
		TestContext.assertEquals(capacity, table.size());
		for(int i = 0; i < capacity; ++i) {
			TestContext.assertTrue(table.get(i) == (char) i);
		}
		table.trimToSize();
		TestContext.assertEquals(capacity, table.size());
	}
	private void checkCharSelfInsertion(int index) {
		final int originalSize = 1030;
		final char[] values = new char[originalSize];
		for(int i = 0; i < originalSize; ++i) {
			values[i] = (char) i;
		}
		final FastCharTable table = new FastCharTable(values);
		TestContext.assertTrue(table.addAll(index, table));
		TestContext.assertEquals(originalSize << 1, table.size());
		for(int i = 0; i < table.size(); ++i) {
			final int source = i < index ? i
					: i < index + originalSize ? i - index : i - originalSize;
			TestContext.assertTrue(table.get(i) == values[source]);
		}
		table.trimToSize();
	}
	private void checkCharSharedTable() {
		final FastCharTable table = new FastCharTable();
		table.add((char) 1);
		table.add((char) 2);
		final FastCharTable shared = (FastCharTable) table.shared();
		final FastCharIterator iterator = (FastCharIterator) shared.iterator();
		TestContext.assertTrue(iterator.hasNext());
		TestContext.assertTrue(iterator.next() == (char) 1);
		iterator.remove();
		TestContext.assertEquals(1, shared.size());
		TestContext.assertTrue(shared.get(0) == (char) 2);
		final FastCharTable source = new FastCharTable(new char[] { (char) 1, (char) 2 });
		final FastCharTable unmodifiable = (FastCharTable) source.unmodifiable();
		final FastCharTable fromView = new FastCharTable();
		TestContext.assertTrue(fromView.addAll(unmodifiable));
		TestContext.assertTrue(source.equals(unmodifiable));
		TestContext.assertTrue(unmodifiable.equals(source));
		final FastCharTable sourceShared = (FastCharTable) source.shared();
		source.add((char) 3);
		TestContext.assertEquals(3, unmodifiable.size());
		TestContext.assertEquals(3, sourceShared.size());
		TestContext.assertTrue(unmodifiable.get(2) == (char) 3);
		TestContext.assertTrue(sourceShared.get(2) == (char) 3);
		source.remove(2);
		TestContext.assertEquals(2, unmodifiable.size());
		TestContext.assertEquals(2, sourceShared.size());
		final FastCharTable fromShared = new FastCharTable();
		TestContext.assertTrue(fromShared.addAll((FastCharTable) source.shared()));
		final FastCharTable unmodifiableOwner = new FastCharTable(new char[] { (char) 1, (char) 2 });
		TestContext.assertTrue(unmodifiableOwner.addAll(1,
				(FastCharTable) unmodifiableOwner.unmodifiable()));
		TestContext.assertTrue(unmodifiableOwner.equals(new FastCharTable(new char[] { (char) 1, (char) 1, (char) 2, (char) 2 })));
		final FastCharTable sharedOwner = new FastCharTable(new char[] { (char) 1, (char) 2 });
		final FastCharTable ownedShared = (FastCharTable) sharedOwner.shared();
		TestContext.assertTrue(sharedOwner.addAll(1,
				ownedShared));
		TestContext.assertTrue(sharedOwner.equals(new FastCharTable(new char[] { (char) 1, (char) 1, (char) 2, (char) 2 })));
		final FastCharTable receiverBacking = new FastCharTable(
				new char[] { (char) 1, (char) 2 });
		final FastCharTable receiverShared = (FastCharTable) receiverBacking.shared();
		TestContext.assertTrue(receiverShared.addAll(1, receiverBacking));
		TestContext.assertTrue(receiverShared.equals(new FastCharTable(
				new char[] { (char) 1, (char) 1, (char) 2, (char) 2 })));
	}
	private void checkCharSharedSet() {
		final FastCharSet set = new FastCharSet();
		set.add((char) 1);
		set.add((char) 2);
		final FastCharSet shared = (FastCharSet) set.shared();
		final FastCharIterator iterator = (FastCharIterator) shared.iterator();
		TestContext.assertTrue(iterator.hasNext());
		final char removed = iterator.next();
		iterator.remove();
		TestContext.assertFalse(shared.contains(removed));
		TestContext.assertEquals(1, shared.size());
		final FastCharSet source = new FastCharSet(new char[] { (char) 1, (char) 2 });
		final FastCharSet unmodifiable = (FastCharSet) source.unmodifiable();
		final FastCharSet copied = new FastCharSet(unmodifiable);
		TestContext.assertTrue(copied.equals(source));
		final FastCharSet sourceShared = (FastCharSet) source.shared();
		TestContext.assertTrue(source.remove((char) 1));
		TestContext.assertFalse(unmodifiable.contains((char) 1));
		TestContext.assertFalse(sourceShared.contains((char) 1));
		TestContext.assertTrue(source.add((char) 1));
		TestContext.assertTrue(unmodifiable.contains((char) 1));
		TestContext.assertTrue(sourceShared.contains((char) 1));
		final FastCharSet target = (FastCharSet) new FastCharSet().shared();
		TestContext.assertTrue(target.addAll(unmodifiable));
		TestContext.assertTrue(target.containsAll(unmodifiable));
		TestContext.assertTrue(target.equals(unmodifiable));
		TestContext.assertTrue(unmodifiable.equals(target));
		TestContext.assertTrue(target.removeAll((FastCharSet) source.shared()));
		TestContext.assertEquals(0, target.size());
		TestContext.assertTrue(target.addAll(unmodifiable));
		final FastCharSet retained = new FastCharSet(new char[] { (char) 2 });
		TestContext.assertTrue(target.retainAll((FastCharSet) retained.shared()));
		TestContext.assertEquals(1, target.size());
		TestContext.assertTrue(target.contains((char) 2));
	}
	private void checkDoubleCapacity(int capacity) {
		final FastDoubleTable table = new FastDoubleTable(capacity);
		for(int i = 0; i < capacity; ++i) {
			table.add((double) i);
		}
		TestContext.assertEquals(capacity, table.size());
		for(int i = 0; i < capacity; ++i) {
			TestContext.assertTrue(table.get(i) == (double) i);
		}
		table.trimToSize();
		TestContext.assertEquals(capacity, table.size());
	}
	private void checkDoubleSelfInsertion(int index) {
		final int originalSize = 1030;
		final double[] values = new double[originalSize];
		for(int i = 0; i < originalSize; ++i) {
			values[i] = (double) i;
		}
		final FastDoubleTable table = new FastDoubleTable(values);
		TestContext.assertTrue(table.addAll(index, table));
		TestContext.assertEquals(originalSize << 1, table.size());
		for(int i = 0; i < table.size(); ++i) {
			final int source = i < index ? i
					: i < index + originalSize ? i - index : i - originalSize;
			TestContext.assertTrue(table.get(i) == values[source]);
		}
		table.trimToSize();
	}
	private void checkDoubleSharedTable() {
		final FastDoubleTable table = new FastDoubleTable();
		table.add(1.0);
		table.add(2.0);
		final FastDoubleTable shared = (FastDoubleTable) table.shared();
		final FastDoubleIterator iterator = (FastDoubleIterator) shared.iterator();
		TestContext.assertTrue(iterator.hasNext());
		TestContext.assertTrue(iterator.next() == 1.0);
		iterator.remove();
		TestContext.assertEquals(1, shared.size());
		TestContext.assertTrue(shared.get(0) == 2.0);
		final FastDoubleTable source = new FastDoubleTable(new double[] { 1.0, 2.0 });
		final FastDoubleTable unmodifiable = (FastDoubleTable) source.unmodifiable();
		final FastDoubleTable fromView = new FastDoubleTable();
		TestContext.assertTrue(fromView.addAll(unmodifiable));
		TestContext.assertTrue(source.equals(unmodifiable));
		TestContext.assertTrue(unmodifiable.equals(source));
		final FastDoubleTable sourceShared = (FastDoubleTable) source.shared();
		source.add(3.0);
		TestContext.assertEquals(3, unmodifiable.size());
		TestContext.assertEquals(3, sourceShared.size());
		TestContext.assertTrue(unmodifiable.get(2) == 3.0);
		TestContext.assertTrue(sourceShared.get(2) == 3.0);
		source.remove(2);
		TestContext.assertEquals(2, unmodifiable.size());
		TestContext.assertEquals(2, sourceShared.size());
		final FastDoubleTable fromShared = new FastDoubleTable();
		TestContext.assertTrue(fromShared.addAll((FastDoubleTable) source.shared()));
		final FastDoubleTable unmodifiableOwner = new FastDoubleTable(new double[] { 1.0, 2.0 });
		TestContext.assertTrue(unmodifiableOwner.addAll(1,
				(FastDoubleTable) unmodifiableOwner.unmodifiable()));
		TestContext.assertTrue(unmodifiableOwner.equals(new FastDoubleTable(new double[] { 1.0, 1.0, 2.0, 2.0 })));
		final FastDoubleTable sharedOwner = new FastDoubleTable(new double[] { 1.0, 2.0 });
		final FastDoubleTable ownedShared = (FastDoubleTable) sharedOwner.shared();
		TestContext.assertTrue(sharedOwner.addAll(1,
				ownedShared));
		TestContext.assertTrue(sharedOwner.equals(new FastDoubleTable(new double[] { 1.0, 1.0, 2.0, 2.0 })));
		final FastDoubleTable receiverBacking = new FastDoubleTable(
				new double[] { 1.0, 2.0 });
		final FastDoubleTable receiverShared = (FastDoubleTable) receiverBacking.shared();
		TestContext.assertTrue(receiverShared.addAll(1, receiverBacking));
		TestContext.assertTrue(receiverShared.equals(new FastDoubleTable(
				new double[] { 1.0, 1.0, 2.0, 2.0 })));
	}
	private void checkDoubleSharedSet() {
		final FastDoubleSet set = new FastDoubleSet();
		set.add(1.0);
		set.add(2.0);
		final FastDoubleSet shared = (FastDoubleSet) set.shared();
		final FastDoubleIterator iterator = (FastDoubleIterator) shared.iterator();
		TestContext.assertTrue(iterator.hasNext());
		final double removed = iterator.next();
		iterator.remove();
		TestContext.assertFalse(shared.contains(removed));
		TestContext.assertEquals(1, shared.size());
		final FastDoubleSet source = new FastDoubleSet(new double[] { 1.0, 2.0 });
		final FastDoubleSet unmodifiable = (FastDoubleSet) source.unmodifiable();
		final FastDoubleSet copied = new FastDoubleSet(unmodifiable);
		TestContext.assertTrue(copied.equals(source));
		final FastDoubleSet sourceShared = (FastDoubleSet) source.shared();
		TestContext.assertTrue(source.remove(1.0));
		TestContext.assertFalse(unmodifiable.contains(1.0));
		TestContext.assertFalse(sourceShared.contains(1.0));
		TestContext.assertTrue(source.add(1.0));
		TestContext.assertTrue(unmodifiable.contains(1.0));
		TestContext.assertTrue(sourceShared.contains(1.0));
		final FastDoubleSet target = (FastDoubleSet) new FastDoubleSet().shared();
		TestContext.assertTrue(target.addAll(unmodifiable));
		TestContext.assertTrue(target.containsAll(unmodifiable));
		TestContext.assertTrue(target.equals(unmodifiable));
		TestContext.assertTrue(unmodifiable.equals(target));
		TestContext.assertTrue(target.removeAll((FastDoubleSet) source.shared()));
		TestContext.assertEquals(0, target.size());
		TestContext.assertTrue(target.addAll(unmodifiable));
		final FastDoubleSet retained = new FastDoubleSet(new double[] { 2.0 });
		TestContext.assertTrue(target.retainAll((FastDoubleSet) retained.shared()));
		TestContext.assertEquals(1, target.size());
		TestContext.assertTrue(target.contains(2.0));
	}
	private void checkFloatCapacity(int capacity) {
		final FastFloatTable table = new FastFloatTable(capacity);
		for(int i = 0; i < capacity; ++i) {
			table.add((float) i);
		}
		TestContext.assertEquals(capacity, table.size());
		for(int i = 0; i < capacity; ++i) {
			TestContext.assertTrue(table.get(i) == (float) i);
		}
		table.trimToSize();
		TestContext.assertEquals(capacity, table.size());
	}
	private void checkFloatSelfInsertion(int index) {
		final int originalSize = 1030;
		final float[] values = new float[originalSize];
		for(int i = 0; i < originalSize; ++i) {
			values[i] = (float) i;
		}
		final FastFloatTable table = new FastFloatTable(values);
		TestContext.assertTrue(table.addAll(index, table));
		TestContext.assertEquals(originalSize << 1, table.size());
		for(int i = 0; i < table.size(); ++i) {
			final int source = i < index ? i
					: i < index + originalSize ? i - index : i - originalSize;
			TestContext.assertTrue(table.get(i) == values[source]);
		}
		table.trimToSize();
	}
	private void checkFloatSharedTable() {
		final FastFloatTable table = new FastFloatTable();
		table.add(1.0f);
		table.add(2.0f);
		final FastFloatTable shared = (FastFloatTable) table.shared();
		final FastFloatIterator iterator = (FastFloatIterator) shared.iterator();
		TestContext.assertTrue(iterator.hasNext());
		TestContext.assertTrue(iterator.next() == 1.0f);
		iterator.remove();
		TestContext.assertEquals(1, shared.size());
		TestContext.assertTrue(shared.get(0) == 2.0f);
		final FastFloatTable source = new FastFloatTable(new float[] { 1.0f, 2.0f });
		final FastFloatTable unmodifiable = (FastFloatTable) source.unmodifiable();
		final FastFloatTable fromView = new FastFloatTable();
		TestContext.assertTrue(fromView.addAll(unmodifiable));
		TestContext.assertTrue(source.equals(unmodifiable));
		TestContext.assertTrue(unmodifiable.equals(source));
		final FastFloatTable sourceShared = (FastFloatTable) source.shared();
		source.add(3.0f);
		TestContext.assertEquals(3, unmodifiable.size());
		TestContext.assertEquals(3, sourceShared.size());
		TestContext.assertTrue(unmodifiable.get(2) == 3.0f);
		TestContext.assertTrue(sourceShared.get(2) == 3.0f);
		source.remove(2);
		TestContext.assertEquals(2, unmodifiable.size());
		TestContext.assertEquals(2, sourceShared.size());
		final FastFloatTable fromShared = new FastFloatTable();
		TestContext.assertTrue(fromShared.addAll((FastFloatTable) source.shared()));
		final FastFloatTable unmodifiableOwner = new FastFloatTable(new float[] { 1.0f, 2.0f });
		TestContext.assertTrue(unmodifiableOwner.addAll(1,
				(FastFloatTable) unmodifiableOwner.unmodifiable()));
		TestContext.assertTrue(unmodifiableOwner.equals(new FastFloatTable(new float[] { 1.0f, 1.0f, 2.0f, 2.0f })));
		final FastFloatTable sharedOwner = new FastFloatTable(new float[] { 1.0f, 2.0f });
		final FastFloatTable ownedShared = (FastFloatTable) sharedOwner.shared();
		TestContext.assertTrue(sharedOwner.addAll(1,
				ownedShared));
		TestContext.assertTrue(sharedOwner.equals(new FastFloatTable(new float[] { 1.0f, 1.0f, 2.0f, 2.0f })));
		final FastFloatTable receiverBacking = new FastFloatTable(
				new float[] { 1.0f, 2.0f });
		final FastFloatTable receiverShared = (FastFloatTable) receiverBacking.shared();
		TestContext.assertTrue(receiverShared.addAll(1, receiverBacking));
		TestContext.assertTrue(receiverShared.equals(new FastFloatTable(
				new float[] { 1.0f, 1.0f, 2.0f, 2.0f })));
	}
	private void checkFloatSharedSet() {
		final FastFloatSet set = new FastFloatSet();
		set.add(1.0f);
		set.add(2.0f);
		final FastFloatSet shared = (FastFloatSet) set.shared();
		final FastFloatIterator iterator = (FastFloatIterator) shared.iterator();
		TestContext.assertTrue(iterator.hasNext());
		final float removed = iterator.next();
		iterator.remove();
		TestContext.assertFalse(shared.contains(removed));
		TestContext.assertEquals(1, shared.size());
		final FastFloatSet source = new FastFloatSet(new float[] { 1.0f, 2.0f });
		final FastFloatSet unmodifiable = (FastFloatSet) source.unmodifiable();
		final FastFloatSet copied = new FastFloatSet(unmodifiable);
		TestContext.assertTrue(copied.equals(source));
		final FastFloatSet sourceShared = (FastFloatSet) source.shared();
		TestContext.assertTrue(source.remove(1.0f));
		TestContext.assertFalse(unmodifiable.contains(1.0f));
		TestContext.assertFalse(sourceShared.contains(1.0f));
		TestContext.assertTrue(source.add(1.0f));
		TestContext.assertTrue(unmodifiable.contains(1.0f));
		TestContext.assertTrue(sourceShared.contains(1.0f));
		final FastFloatSet target = (FastFloatSet) new FastFloatSet().shared();
		TestContext.assertTrue(target.addAll(unmodifiable));
		TestContext.assertTrue(target.containsAll(unmodifiable));
		TestContext.assertTrue(target.equals(unmodifiable));
		TestContext.assertTrue(unmodifiable.equals(target));
		TestContext.assertTrue(target.removeAll((FastFloatSet) source.shared()));
		TestContext.assertEquals(0, target.size());
		TestContext.assertTrue(target.addAll(unmodifiable));
		final FastFloatSet retained = new FastFloatSet(new float[] { 2.0f });
		TestContext.assertTrue(target.retainAll((FastFloatSet) retained.shared()));
		TestContext.assertEquals(1, target.size());
		TestContext.assertTrue(target.contains(2.0f));
	}
	private void checkIntCapacity(int capacity) {
		final FastIntTable table = new FastIntTable(capacity);
		for(int i = 0; i < capacity; ++i) {
			table.add(i);
		}
		TestContext.assertEquals(capacity, table.size());
		for(int i = 0; i < capacity; ++i) {
			TestContext.assertTrue(table.get(i) == i);
		}
		table.trimToSize();
		TestContext.assertEquals(capacity, table.size());
	}
	private void checkIntSelfInsertion(int index) {
		final int originalSize = 1030;
		final int[] values = new int[originalSize];
		for(int i = 0; i < originalSize; ++i) {
			values[i] = i;
		}
		final FastIntTable table = new FastIntTable(values);
		TestContext.assertTrue(table.addAll(index, table));
		TestContext.assertEquals(originalSize << 1, table.size());
		for(int i = 0; i < table.size(); ++i) {
			final int source = i < index ? i
					: i < index + originalSize ? i - index : i - originalSize;
			TestContext.assertTrue(table.get(i) == values[source]);
		}
		table.trimToSize();
	}
	private void checkIntSharedTable() {
		final FastIntTable table = new FastIntTable();
		table.add(1);
		table.add(2);
		final FastIntTable shared = (FastIntTable) table.shared();
		final FastIntIterator iterator = (FastIntIterator) shared.iterator();
		TestContext.assertTrue(iterator.hasNext());
		TestContext.assertTrue(iterator.next() == 1);
		iterator.remove();
		TestContext.assertEquals(1, shared.size());
		TestContext.assertTrue(shared.get(0) == 2);
		final FastIntTable source = new FastIntTable(new int[] { 1, 2 });
		final FastIntTable unmodifiable = (FastIntTable) source.unmodifiable();
		final FastIntTable fromView = new FastIntTable();
		TestContext.assertTrue(fromView.addAll(unmodifiable));
		TestContext.assertTrue(source.equals(unmodifiable));
		TestContext.assertTrue(unmodifiable.equals(source));
		final FastIntTable sourceShared = (FastIntTable) source.shared();
		source.add(3);
		TestContext.assertEquals(3, unmodifiable.size());
		TestContext.assertEquals(3, sourceShared.size());
		TestContext.assertTrue(unmodifiable.get(2) == 3);
		TestContext.assertTrue(sourceShared.get(2) == 3);
		source.remove(2);
		TestContext.assertEquals(2, unmodifiable.size());
		TestContext.assertEquals(2, sourceShared.size());
		final FastIntTable fromShared = new FastIntTable();
		TestContext.assertTrue(fromShared.addAll((FastIntTable) source.shared()));
		final FastIntTable unmodifiableOwner = new FastIntTable(new int[] { 1, 2 });
		TestContext.assertTrue(unmodifiableOwner.addAll(1,
				(FastIntTable) unmodifiableOwner.unmodifiable()));
		TestContext.assertTrue(unmodifiableOwner.equals(new FastIntTable(new int[] { 1, 1, 2, 2 })));
		final FastIntTable sharedOwner = new FastIntTable(new int[] { 1, 2 });
		final FastIntTable ownedShared = (FastIntTable) sharedOwner.shared();
		TestContext.assertTrue(sharedOwner.addAll(1,
				ownedShared));
		TestContext.assertTrue(sharedOwner.equals(new FastIntTable(new int[] { 1, 1, 2, 2 })));
		final FastIntTable receiverBacking = new FastIntTable(
				new int[] { 1, 2 });
		final FastIntTable receiverShared = (FastIntTable) receiverBacking.shared();
		TestContext.assertTrue(receiverShared.addAll(1, receiverBacking));
		TestContext.assertTrue(receiverShared.equals(new FastIntTable(
				new int[] { 1, 1, 2, 2 })));
	}
	private void checkIntSharedSet() {
		final FastIntSet set = new FastIntSet();
		set.add(1);
		set.add(2);
		final FastIntSet shared = (FastIntSet) set.shared();
		final FastIntIterator iterator = (FastIntIterator) shared.iterator();
		TestContext.assertTrue(iterator.hasNext());
		final int removed = iterator.next();
		iterator.remove();
		TestContext.assertFalse(shared.contains(removed));
		TestContext.assertEquals(1, shared.size());
		final FastIntSet source = new FastIntSet(new int[] { 1, 2 });
		final FastIntSet unmodifiable = (FastIntSet) source.unmodifiable();
		final FastIntSet copied = new FastIntSet(unmodifiable);
		TestContext.assertTrue(copied.equals(source));
		final FastIntSet sourceShared = (FastIntSet) source.shared();
		TestContext.assertTrue(source.remove(1));
		TestContext.assertFalse(unmodifiable.contains(1));
		TestContext.assertFalse(sourceShared.contains(1));
		TestContext.assertTrue(source.add(1));
		TestContext.assertTrue(unmodifiable.contains(1));
		TestContext.assertTrue(sourceShared.contains(1));
		final FastIntSet target = (FastIntSet) new FastIntSet().shared();
		TestContext.assertTrue(target.addAll(unmodifiable));
		TestContext.assertTrue(target.containsAll(unmodifiable));
		TestContext.assertTrue(target.equals(unmodifiable));
		TestContext.assertTrue(unmodifiable.equals(target));
		TestContext.assertTrue(target.removeAll((FastIntSet) source.shared()));
		TestContext.assertEquals(0, target.size());
		TestContext.assertTrue(target.addAll(unmodifiable));
		final FastIntSet retained = new FastIntSet(new int[] { 2 });
		TestContext.assertTrue(target.retainAll((FastIntSet) retained.shared()));
		TestContext.assertEquals(1, target.size());
		TestContext.assertTrue(target.contains(2));
	}
	private void checkLongCapacity(int capacity) {
		final FastLongTable table = new FastLongTable(capacity);
		for(int i = 0; i < capacity; ++i) {
			table.add((long) i);
		}
		TestContext.assertEquals(capacity, table.size());
		for(int i = 0; i < capacity; ++i) {
			TestContext.assertTrue(table.get(i) == (long) i);
		}
		table.trimToSize();
		TestContext.assertEquals(capacity, table.size());
	}
	private void checkLongSelfInsertion(int index) {
		final int originalSize = 1030;
		final long[] values = new long[originalSize];
		for(int i = 0; i < originalSize; ++i) {
			values[i] = (long) i;
		}
		final FastLongTable table = new FastLongTable(values);
		TestContext.assertTrue(table.addAll(index, table));
		TestContext.assertEquals(originalSize << 1, table.size());
		for(int i = 0; i < table.size(); ++i) {
			final int source = i < index ? i
					: i < index + originalSize ? i - index : i - originalSize;
			TestContext.assertTrue(table.get(i) == values[source]);
		}
		table.trimToSize();
	}
	private void checkLongSharedTable() {
		final FastLongTable table = new FastLongTable();
		table.add(1L);
		table.add(2L);
		final FastLongTable shared = (FastLongTable) table.shared();
		final FastLongIterator iterator = (FastLongIterator) shared.iterator();
		TestContext.assertTrue(iterator.hasNext());
		TestContext.assertTrue(iterator.next() == 1L);
		iterator.remove();
		TestContext.assertEquals(1, shared.size());
		TestContext.assertTrue(shared.get(0) == 2L);
		final FastLongTable source = new FastLongTable(new long[] { 1L, 2L });
		final FastLongTable unmodifiable = (FastLongTable) source.unmodifiable();
		final FastLongTable fromView = new FastLongTable();
		TestContext.assertTrue(fromView.addAll(unmodifiable));
		TestContext.assertTrue(source.equals(unmodifiable));
		TestContext.assertTrue(unmodifiable.equals(source));
		final FastLongTable sourceShared = (FastLongTable) source.shared();
		source.add(3L);
		TestContext.assertEquals(3, unmodifiable.size());
		TestContext.assertEquals(3, sourceShared.size());
		TestContext.assertTrue(unmodifiable.get(2) == 3L);
		TestContext.assertTrue(sourceShared.get(2) == 3L);
		source.remove(2);
		TestContext.assertEquals(2, unmodifiable.size());
		TestContext.assertEquals(2, sourceShared.size());
		final FastLongTable fromShared = new FastLongTable();
		TestContext.assertTrue(fromShared.addAll((FastLongTable) source.shared()));
		final FastLongTable unmodifiableOwner = new FastLongTable(new long[] { 1L, 2L });
		TestContext.assertTrue(unmodifiableOwner.addAll(1,
				(FastLongTable) unmodifiableOwner.unmodifiable()));
		TestContext.assertTrue(unmodifiableOwner.equals(new FastLongTable(new long[] { 1L, 1L, 2L, 2L })));
		final FastLongTable sharedOwner = new FastLongTable(new long[] { 1L, 2L });
		final FastLongTable ownedShared = (FastLongTable) sharedOwner.shared();
		TestContext.assertTrue(sharedOwner.addAll(1,
				ownedShared));
		TestContext.assertTrue(sharedOwner.equals(new FastLongTable(new long[] { 1L, 1L, 2L, 2L })));
		final FastLongTable receiverBacking = new FastLongTable(
				new long[] { 1L, 2L });
		final FastLongTable receiverShared = (FastLongTable) receiverBacking.shared();
		TestContext.assertTrue(receiverShared.addAll(1, receiverBacking));
		TestContext.assertTrue(receiverShared.equals(new FastLongTable(
				new long[] { 1L, 1L, 2L, 2L })));
	}
	private void checkLongSharedSet() {
		final FastLongSet set = new FastLongSet();
		set.add(1L);
		set.add(2L);
		final FastLongSet shared = (FastLongSet) set.shared();
		final FastLongIterator iterator = (FastLongIterator) shared.iterator();
		TestContext.assertTrue(iterator.hasNext());
		final long removed = iterator.next();
		iterator.remove();
		TestContext.assertFalse(shared.contains(removed));
		TestContext.assertEquals(1, shared.size());
		final FastLongSet source = new FastLongSet(new long[] { 1L, 2L });
		final FastLongSet unmodifiable = (FastLongSet) source.unmodifiable();
		final FastLongSet copied = new FastLongSet(unmodifiable);
		TestContext.assertTrue(copied.equals(source));
		final FastLongSet sourceShared = (FastLongSet) source.shared();
		TestContext.assertTrue(source.remove(1L));
		TestContext.assertFalse(unmodifiable.contains(1L));
		TestContext.assertFalse(sourceShared.contains(1L));
		TestContext.assertTrue(source.add(1L));
		TestContext.assertTrue(unmodifiable.contains(1L));
		TestContext.assertTrue(sourceShared.contains(1L));
		final FastLongSet target = (FastLongSet) new FastLongSet().shared();
		TestContext.assertTrue(target.addAll(unmodifiable));
		TestContext.assertTrue(target.containsAll(unmodifiable));
		TestContext.assertTrue(target.equals(unmodifiable));
		TestContext.assertTrue(unmodifiable.equals(target));
		TestContext.assertTrue(target.removeAll((FastLongSet) source.shared()));
		TestContext.assertEquals(0, target.size());
		TestContext.assertTrue(target.addAll(unmodifiable));
		final FastLongSet retained = new FastLongSet(new long[] { 2L });
		TestContext.assertTrue(target.retainAll((FastLongSet) retained.shared()));
		TestContext.assertEquals(1, target.size());
		TestContext.assertTrue(target.contains(2L));
	}
	private void checkShortCapacity(int capacity) {
		final FastShortTable table = new FastShortTable(capacity);
		for(int i = 0; i < capacity; ++i) {
			table.add((short) i);
		}
		TestContext.assertEquals(capacity, table.size());
		for(int i = 0; i < capacity; ++i) {
			TestContext.assertTrue(table.get(i) == (short) i);
		}
		table.trimToSize();
		TestContext.assertEquals(capacity, table.size());
	}
	private void checkShortSelfInsertion(int index) {
		final int originalSize = 1030;
		final short[] values = new short[originalSize];
		for(int i = 0; i < originalSize; ++i) {
			values[i] = (short) i;
		}
		final FastShortTable table = new FastShortTable(values);
		TestContext.assertTrue(table.addAll(index, table));
		TestContext.assertEquals(originalSize << 1, table.size());
		for(int i = 0; i < table.size(); ++i) {
			final int source = i < index ? i
					: i < index + originalSize ? i - index : i - originalSize;
			TestContext.assertTrue(table.get(i) == values[source]);
		}
		table.trimToSize();
	}
	private void checkShortSharedTable() {
		final FastShortTable table = new FastShortTable();
		table.add((short) 1);
		table.add((short) 2);
		final FastShortTable shared = (FastShortTable) table.shared();
		final FastShortIterator iterator = (FastShortIterator) shared.iterator();
		TestContext.assertTrue(iterator.hasNext());
		TestContext.assertTrue(iterator.next() == (short) 1);
		iterator.remove();
		TestContext.assertEquals(1, shared.size());
		TestContext.assertTrue(shared.get(0) == (short) 2);
		final FastShortTable source = new FastShortTable(new short[] { (short) 1, (short) 2 });
		final FastShortTable unmodifiable = (FastShortTable) source.unmodifiable();
		final FastShortTable fromView = new FastShortTable();
		TestContext.assertTrue(fromView.addAll(unmodifiable));
		TestContext.assertTrue(source.equals(unmodifiable));
		TestContext.assertTrue(unmodifiable.equals(source));
		final FastShortTable sourceShared = (FastShortTable) source.shared();
		source.add((short) 3);
		TestContext.assertEquals(3, unmodifiable.size());
		TestContext.assertEquals(3, sourceShared.size());
		TestContext.assertTrue(unmodifiable.get(2) == (short) 3);
		TestContext.assertTrue(sourceShared.get(2) == (short) 3);
		source.remove(2);
		TestContext.assertEquals(2, unmodifiable.size());
		TestContext.assertEquals(2, sourceShared.size());
		final FastShortTable fromShared = new FastShortTable();
		TestContext.assertTrue(fromShared.addAll((FastShortTable) source.shared()));
		final FastShortTable unmodifiableOwner = new FastShortTable(new short[] { (short) 1, (short) 2 });
		TestContext.assertTrue(unmodifiableOwner.addAll(1,
				(FastShortTable) unmodifiableOwner.unmodifiable()));
		TestContext.assertTrue(unmodifiableOwner.equals(new FastShortTable(new short[] { (short) 1, (short) 1, (short) 2, (short) 2 })));
		final FastShortTable sharedOwner = new FastShortTable(new short[] { (short) 1, (short) 2 });
		final FastShortTable ownedShared = (FastShortTable) sharedOwner.shared();
		TestContext.assertTrue(sharedOwner.addAll(1,
				ownedShared));
		TestContext.assertTrue(sharedOwner.equals(new FastShortTable(new short[] { (short) 1, (short) 1, (short) 2, (short) 2 })));
		final FastShortTable receiverBacking = new FastShortTable(
				new short[] { (short) 1, (short) 2 });
		final FastShortTable receiverShared = (FastShortTable) receiverBacking.shared();
		TestContext.assertTrue(receiverShared.addAll(1, receiverBacking));
		TestContext.assertTrue(receiverShared.equals(new FastShortTable(
				new short[] { (short) 1, (short) 1, (short) 2, (short) 2 })));
	}
	private void checkShortSharedSet() {
		final FastShortSet set = new FastShortSet();
		set.add((short) 1);
		set.add((short) 2);
		final FastShortSet shared = (FastShortSet) set.shared();
		final FastShortIterator iterator = (FastShortIterator) shared.iterator();
		TestContext.assertTrue(iterator.hasNext());
		final short removed = iterator.next();
		iterator.remove();
		TestContext.assertFalse(shared.contains(removed));
		TestContext.assertEquals(1, shared.size());
		final FastShortSet source = new FastShortSet(new short[] { (short) 1, (short) 2 });
		final FastShortSet unmodifiable = (FastShortSet) source.unmodifiable();
		final FastShortSet copied = new FastShortSet(unmodifiable);
		TestContext.assertTrue(copied.equals(source));
		final FastShortSet sourceShared = (FastShortSet) source.shared();
		TestContext.assertTrue(source.remove((short) 1));
		TestContext.assertFalse(unmodifiable.contains((short) 1));
		TestContext.assertFalse(sourceShared.contains((short) 1));
		TestContext.assertTrue(source.add((short) 1));
		TestContext.assertTrue(unmodifiable.contains((short) 1));
		TestContext.assertTrue(sourceShared.contains((short) 1));
		final FastShortSet target = (FastShortSet) new FastShortSet().shared();
		TestContext.assertTrue(target.addAll(unmodifiable));
		TestContext.assertTrue(target.containsAll(unmodifiable));
		TestContext.assertTrue(target.equals(unmodifiable));
		TestContext.assertTrue(unmodifiable.equals(target));
		TestContext.assertTrue(target.removeAll((FastShortSet) source.shared()));
		TestContext.assertEquals(0, target.size());
		TestContext.assertTrue(target.addAll(unmodifiable));
		final FastShortSet retained = new FastShortSet(new short[] { (short) 2 });
		TestContext.assertTrue(target.retainAll((FastShortSet) retained.shared()));
		TestContext.assertEquals(1, target.size());
		TestContext.assertTrue(target.contains((short) 2));
	}
	private void checkBooleanSetEmptyFastPaths() {
		final boolean[] emptyValues = new boolean[0];
		final boolean[] presentValues = new boolean[] { true };
		final FastBooleanSet emptySource = new FastBooleanSet();
		final FastBooleanSet source = new FastBooleanSet(presentValues);
		final FastBooleanSet set = new FastBooleanSet();
		TestContext.assertTrue(set.containsAll((boolean[]) null));
		TestContext.assertTrue(set.containsAll(emptyValues));
		TestContext.assertTrue(set.containsAll((FastBooleanSet) null));
		TestContext.assertTrue(set.containsAll(emptySource));
		TestContext.assertFalse(set.containsAll(presentValues));
		TestContext.assertFalse(set.containsAll(source));
		TestContext.assertFalse(set.contains(true));
		TestContext.assertFalse(set.remove(true));
		set.clear();
		final boolean[] firstEmptyArray = set.toArray();
		TestContext.assertEquals(0, firstEmptyArray.length);
		TestContext.assertFalse(firstEmptyArray == set.toArray());
		TestContext.assertEquals(0, set.hashCode());
		TestContext.assertFalse(set.iterator().hasNext());
		final FastBooleanSet shared = (FastBooleanSet) set.shared();
		final FastBooleanSet unmodifiable = (FastBooleanSet) set.unmodifiable();
		final FastBooleanSet emptyView = (FastBooleanSet) emptySource.shared();
		final FastBooleanSet sourceView = (FastBooleanSet) source.unmodifiable();
		TestContext.assertTrue(shared.containsAll((boolean[]) null));
		TestContext.assertTrue(unmodifiable.containsAll((FastBooleanSet) null));
		TestContext.assertTrue(shared.containsAll(emptyValues));
		TestContext.assertTrue(unmodifiable.containsAll(emptyView));
		TestContext.assertFalse(shared.containsAll(presentValues));
		TestContext.assertFalse(unmodifiable.containsAll(sourceView));
		TestContext.assertFalse(shared.contains(true));
		TestContext.assertFalse(shared.remove(true));
		TestContext.assertFalse(unmodifiable.contains(true));
		TestContext.assertEquals(0, shared.toArray().length);
		TestContext.assertEquals(0, unmodifiable.toArray().length);
		TestContext.assertEquals(0, shared.hashCode());
		TestContext.assertEquals(0, unmodifiable.hashCode());
		TestContext.assertFalse(shared.iterator().hasNext());
		TestContext.assertFalse(unmodifiable.iterator().hasNext());
		shared.clear();
		TestContext.assertTrue(shared.add(true));
		TestContext.assertTrue(set.containsAll(presentValues));
		TestContext.assertTrue(shared.containsAll(sourceView));
		TestContext.assertTrue(unmodifiable.containsAll(sourceView));
	}
	private void checkByteSetEmptyFastPaths() {
		final byte[] emptyValues = new byte[0];
		final byte[] presentValues = new byte[] { (byte) 7 };
		final FastByteSet emptySource = new FastByteSet();
		final FastByteSet source = new FastByteSet(presentValues);
		final FastByteSet set = new FastByteSet(16);
		TestContext.assertTrue(set.containsAll((byte[]) null));
		TestContext.assertTrue(set.containsAll(emptyValues));
		TestContext.assertTrue(set.containsAll((FastByteSet) null));
		TestContext.assertTrue(set.containsAll(emptySource));
		TestContext.assertFalse(set.containsAll(presentValues));
		TestContext.assertFalse(set.containsAll(source));
		TestContext.assertFalse(set.contains((byte) 7));
		TestContext.assertFalse(set.remove((byte) 7));
		set.clear();
		final byte[] firstEmptyArray = set.toArray();
		TestContext.assertEquals(0, firstEmptyArray.length);
		TestContext.assertFalse(firstEmptyArray == set.toArray());
		TestContext.assertEquals(0, set.hashCode());
		TestContext.assertFalse(set.iterator().hasNext());
		TestContext.assertTrue(set.add((byte) 1));
		TestContext.assertTrue(set.add((byte) 2));
		TestContext.assertTrue(set.remove((byte) 1));
		TestContext.assertTrue(set.remove((byte) 2));
		TestContext.assertEquals(0, set.size());
		TestContext.assertFalse(set.contains((byte) 7));
		TestContext.assertFalse(set.remove((byte) 7));
		TestContext.assertEquals(0, set.toArray().length);
		TestContext.assertEquals(0, set.hashCode());
		TestContext.assertFalse(set.iterator().hasNext());
		final FastByteSet shared = (FastByteSet) set.shared();
		final FastByteSet unmodifiable = (FastByteSet) set.unmodifiable();
		final FastByteSet emptyView = (FastByteSet) emptySource.shared();
		final FastByteSet sourceView = (FastByteSet) source.unmodifiable();
		TestContext.assertTrue(shared.containsAll((byte[]) null));
		TestContext.assertTrue(unmodifiable.containsAll((FastByteSet) null));
		TestContext.assertTrue(shared.containsAll(emptyValues));
		TestContext.assertTrue(unmodifiable.containsAll(emptyView));
		TestContext.assertFalse(shared.containsAll(presentValues));
		TestContext.assertFalse(unmodifiable.containsAll(sourceView));
		TestContext.assertFalse(shared.contains((byte) 7));
		TestContext.assertFalse(shared.remove((byte) 7));
		TestContext.assertFalse(unmodifiable.contains((byte) 7));
		TestContext.assertEquals(0, shared.toArray().length);
		TestContext.assertEquals(0, unmodifiable.toArray().length);
		TestContext.assertEquals(0, shared.hashCode());
		TestContext.assertEquals(0, unmodifiable.hashCode());
		TestContext.assertFalse(shared.iterator().hasNext());
		TestContext.assertFalse(unmodifiable.iterator().hasNext());
		shared.clear();
		TestContext.assertTrue(shared.add((byte) 7));
		TestContext.assertTrue(set.containsAll(presentValues));
		TestContext.assertTrue(shared.containsAll(sourceView));
		TestContext.assertTrue(unmodifiable.containsAll(sourceView));
	}
	private void checkCharSetEmptyFastPaths() {
		final char[] emptyValues = new char[0];
		final char[] presentValues = new char[] { (char) 7 };
		final FastCharSet emptySource = new FastCharSet();
		final FastCharSet source = new FastCharSet(presentValues);
		final FastCharSet set = new FastCharSet(16);
		TestContext.assertTrue(set.containsAll((char[]) null));
		TestContext.assertTrue(set.containsAll(emptyValues));
		TestContext.assertTrue(set.containsAll((FastCharSet) null));
		TestContext.assertTrue(set.containsAll(emptySource));
		TestContext.assertFalse(set.containsAll(presentValues));
		TestContext.assertFalse(set.containsAll(source));
		TestContext.assertFalse(set.contains((char) 7));
		TestContext.assertFalse(set.remove((char) 7));
		set.clear();
		final char[] firstEmptyArray = set.toArray();
		TestContext.assertEquals(0, firstEmptyArray.length);
		TestContext.assertFalse(firstEmptyArray == set.toArray());
		TestContext.assertEquals(0, set.hashCode());
		TestContext.assertFalse(set.iterator().hasNext());
		TestContext.assertTrue(set.add((char) 1));
		TestContext.assertTrue(set.add((char) 2));
		TestContext.assertTrue(set.remove((char) 1));
		TestContext.assertTrue(set.remove((char) 2));
		TestContext.assertEquals(0, set.size());
		TestContext.assertFalse(set.contains((char) 7));
		TestContext.assertFalse(set.remove((char) 7));
		TestContext.assertEquals(0, set.toArray().length);
		TestContext.assertEquals(0, set.hashCode());
		TestContext.assertFalse(set.iterator().hasNext());
		final FastCharSet shared = (FastCharSet) set.shared();
		final FastCharSet unmodifiable = (FastCharSet) set.unmodifiable();
		final FastCharSet emptyView = (FastCharSet) emptySource.shared();
		final FastCharSet sourceView = (FastCharSet) source.unmodifiable();
		TestContext.assertTrue(shared.containsAll((char[]) null));
		TestContext.assertTrue(unmodifiable.containsAll((FastCharSet) null));
		TestContext.assertTrue(shared.containsAll(emptyValues));
		TestContext.assertTrue(unmodifiable.containsAll(emptyView));
		TestContext.assertFalse(shared.containsAll(presentValues));
		TestContext.assertFalse(unmodifiable.containsAll(sourceView));
		TestContext.assertFalse(shared.contains((char) 7));
		TestContext.assertFalse(shared.remove((char) 7));
		TestContext.assertFalse(unmodifiable.contains((char) 7));
		TestContext.assertEquals(0, shared.toArray().length);
		TestContext.assertEquals(0, unmodifiable.toArray().length);
		TestContext.assertEquals(0, shared.hashCode());
		TestContext.assertEquals(0, unmodifiable.hashCode());
		TestContext.assertFalse(shared.iterator().hasNext());
		TestContext.assertFalse(unmodifiable.iterator().hasNext());
		shared.clear();
		TestContext.assertTrue(shared.add((char) 7));
		TestContext.assertTrue(set.containsAll(presentValues));
		TestContext.assertTrue(shared.containsAll(sourceView));
		TestContext.assertTrue(unmodifiable.containsAll(sourceView));
	}
	private void checkDoubleSetEmptyFastPaths() {
		final double[] emptyValues = new double[0];
		final double[] presentValues = new double[] { 7.25 };
		final FastDoubleSet emptySource = new FastDoubleSet();
		final FastDoubleSet source = new FastDoubleSet(presentValues);
		final FastDoubleSet set = new FastDoubleSet(16);
		TestContext.assertTrue(set.containsAll((double[]) null));
		TestContext.assertTrue(set.containsAll(emptyValues));
		TestContext.assertTrue(set.containsAll((FastDoubleSet) null));
		TestContext.assertTrue(set.containsAll(emptySource));
		TestContext.assertFalse(set.containsAll(presentValues));
		TestContext.assertFalse(set.containsAll(source));
		TestContext.assertFalse(set.contains(7.25));
		TestContext.assertFalse(set.remove(7.25));
		set.clear();
		final double[] firstEmptyArray = set.toArray();
		TestContext.assertEquals(0, firstEmptyArray.length);
		TestContext.assertFalse(firstEmptyArray == set.toArray());
		TestContext.assertEquals(0, set.hashCode());
		TestContext.assertFalse(set.iterator().hasNext());
		TestContext.assertTrue(set.add(1.25));
		TestContext.assertTrue(set.add(2.25));
		TestContext.assertTrue(set.remove(1.25));
		TestContext.assertTrue(set.remove(2.25));
		TestContext.assertEquals(0, set.size());
		TestContext.assertFalse(set.contains(7.25));
		TestContext.assertFalse(set.remove(7.25));
		TestContext.assertEquals(0, set.toArray().length);
		TestContext.assertEquals(0, set.hashCode());
		TestContext.assertFalse(set.iterator().hasNext());
		final FastDoubleSet shared = (FastDoubleSet) set.shared();
		final FastDoubleSet unmodifiable = (FastDoubleSet) set.unmodifiable();
		final FastDoubleSet emptyView = (FastDoubleSet) emptySource.shared();
		final FastDoubleSet sourceView = (FastDoubleSet) source.unmodifiable();
		TestContext.assertTrue(shared.containsAll((double[]) null));
		TestContext.assertTrue(unmodifiable.containsAll((FastDoubleSet) null));
		TestContext.assertTrue(shared.containsAll(emptyValues));
		TestContext.assertTrue(unmodifiable.containsAll(emptyView));
		TestContext.assertFalse(shared.containsAll(presentValues));
		TestContext.assertFalse(unmodifiable.containsAll(sourceView));
		TestContext.assertFalse(shared.contains(7.25));
		TestContext.assertFalse(shared.remove(7.25));
		TestContext.assertFalse(unmodifiable.contains(7.25));
		TestContext.assertEquals(0, shared.toArray().length);
		TestContext.assertEquals(0, unmodifiable.toArray().length);
		TestContext.assertEquals(0, shared.hashCode());
		TestContext.assertEquals(0, unmodifiable.hashCode());
		TestContext.assertFalse(shared.iterator().hasNext());
		TestContext.assertFalse(unmodifiable.iterator().hasNext());
		shared.clear();
		TestContext.assertTrue(shared.add(7.25));
		TestContext.assertTrue(set.containsAll(presentValues));
		TestContext.assertTrue(shared.containsAll(sourceView));
		TestContext.assertTrue(unmodifiable.containsAll(sourceView));
	}
	private void checkFloatSetEmptyFastPaths() {
		final float[] emptyValues = new float[0];
		final float[] presentValues = new float[] { 7.25f };
		final FastFloatSet emptySource = new FastFloatSet();
		final FastFloatSet source = new FastFloatSet(presentValues);
		final FastFloatSet set = new FastFloatSet(16);
		TestContext.assertTrue(set.containsAll((float[]) null));
		TestContext.assertTrue(set.containsAll(emptyValues));
		TestContext.assertTrue(set.containsAll((FastFloatSet) null));
		TestContext.assertTrue(set.containsAll(emptySource));
		TestContext.assertFalse(set.containsAll(presentValues));
		TestContext.assertFalse(set.containsAll(source));
		TestContext.assertFalse(set.contains(7.25f));
		TestContext.assertFalse(set.remove(7.25f));
		set.clear();
		final float[] firstEmptyArray = set.toArray();
		TestContext.assertEquals(0, firstEmptyArray.length);
		TestContext.assertFalse(firstEmptyArray == set.toArray());
		TestContext.assertEquals(0, set.hashCode());
		TestContext.assertFalse(set.iterator().hasNext());
		TestContext.assertTrue(set.add(1.25f));
		TestContext.assertTrue(set.add(2.25f));
		TestContext.assertTrue(set.remove(1.25f));
		TestContext.assertTrue(set.remove(2.25f));
		TestContext.assertEquals(0, set.size());
		TestContext.assertFalse(set.contains(7.25f));
		TestContext.assertFalse(set.remove(7.25f));
		TestContext.assertEquals(0, set.toArray().length);
		TestContext.assertEquals(0, set.hashCode());
		TestContext.assertFalse(set.iterator().hasNext());
		final FastFloatSet shared = (FastFloatSet) set.shared();
		final FastFloatSet unmodifiable = (FastFloatSet) set.unmodifiable();
		final FastFloatSet emptyView = (FastFloatSet) emptySource.shared();
		final FastFloatSet sourceView = (FastFloatSet) source.unmodifiable();
		TestContext.assertTrue(shared.containsAll((float[]) null));
		TestContext.assertTrue(unmodifiable.containsAll((FastFloatSet) null));
		TestContext.assertTrue(shared.containsAll(emptyValues));
		TestContext.assertTrue(unmodifiable.containsAll(emptyView));
		TestContext.assertFalse(shared.containsAll(presentValues));
		TestContext.assertFalse(unmodifiable.containsAll(sourceView));
		TestContext.assertFalse(shared.contains(7.25f));
		TestContext.assertFalse(shared.remove(7.25f));
		TestContext.assertFalse(unmodifiable.contains(7.25f));
		TestContext.assertEquals(0, shared.toArray().length);
		TestContext.assertEquals(0, unmodifiable.toArray().length);
		TestContext.assertEquals(0, shared.hashCode());
		TestContext.assertEquals(0, unmodifiable.hashCode());
		TestContext.assertFalse(shared.iterator().hasNext());
		TestContext.assertFalse(unmodifiable.iterator().hasNext());
		shared.clear();
		TestContext.assertTrue(shared.add(7.25f));
		TestContext.assertTrue(set.containsAll(presentValues));
		TestContext.assertTrue(shared.containsAll(sourceView));
		TestContext.assertTrue(unmodifiable.containsAll(sourceView));
	}
	private void checkIntSetEmptyFastPaths() {
		final int[] emptyValues = new int[0];
		final int[] presentValues = new int[] { 7 };
		final FastIntSet emptySource = new FastIntSet();
		final FastIntSet source = new FastIntSet(presentValues);
		final FastIntSet set = new FastIntSet(16);
		TestContext.assertTrue(set.containsAll((int[]) null));
		TestContext.assertTrue(set.containsAll(emptyValues));
		TestContext.assertTrue(set.containsAll((FastIntSet) null));
		TestContext.assertTrue(set.containsAll(emptySource));
		TestContext.assertFalse(set.containsAll(presentValues));
		TestContext.assertFalse(set.containsAll(source));
		TestContext.assertFalse(set.contains(7));
		TestContext.assertFalse(set.remove(7));
		set.clear();
		final int[] firstEmptyArray = set.toArray();
		TestContext.assertEquals(0, firstEmptyArray.length);
		TestContext.assertFalse(firstEmptyArray == set.toArray());
		TestContext.assertEquals(0, set.hashCode());
		TestContext.assertFalse(set.iterator().hasNext());
		TestContext.assertTrue(set.add(1));
		TestContext.assertTrue(set.add(2));
		TestContext.assertTrue(set.remove(1));
		TestContext.assertTrue(set.remove(2));
		TestContext.assertEquals(0, set.size());
		TestContext.assertFalse(set.contains(7));
		TestContext.assertFalse(set.remove(7));
		TestContext.assertEquals(0, set.toArray().length);
		TestContext.assertEquals(0, set.hashCode());
		TestContext.assertFalse(set.iterator().hasNext());
		final FastIntSet shared = (FastIntSet) set.shared();
		final FastIntSet unmodifiable = (FastIntSet) set.unmodifiable();
		final FastIntSet emptyView = (FastIntSet) emptySource.shared();
		final FastIntSet sourceView = (FastIntSet) source.unmodifiable();
		TestContext.assertTrue(shared.containsAll((int[]) null));
		TestContext.assertTrue(unmodifiable.containsAll((FastIntSet) null));
		TestContext.assertTrue(shared.containsAll(emptyValues));
		TestContext.assertTrue(unmodifiable.containsAll(emptyView));
		TestContext.assertFalse(shared.containsAll(presentValues));
		TestContext.assertFalse(unmodifiable.containsAll(sourceView));
		TestContext.assertFalse(shared.contains(7));
		TestContext.assertFalse(shared.remove(7));
		TestContext.assertFalse(unmodifiable.contains(7));
		TestContext.assertEquals(0, shared.toArray().length);
		TestContext.assertEquals(0, unmodifiable.toArray().length);
		TestContext.assertEquals(0, shared.hashCode());
		TestContext.assertEquals(0, unmodifiable.hashCode());
		TestContext.assertFalse(shared.iterator().hasNext());
		TestContext.assertFalse(unmodifiable.iterator().hasNext());
		shared.clear();
		TestContext.assertTrue(shared.add(7));
		TestContext.assertTrue(set.containsAll(presentValues));
		TestContext.assertTrue(shared.containsAll(sourceView));
		TestContext.assertTrue(unmodifiable.containsAll(sourceView));
	}
	private void checkLongSetEmptyFastPaths() {
		final long[] emptyValues = new long[0];
		final long[] presentValues = new long[] { 7L };
		final FastLongSet emptySource = new FastLongSet();
		final FastLongSet source = new FastLongSet(presentValues);
		final FastLongSet set = new FastLongSet(16);
		TestContext.assertTrue(set.containsAll((long[]) null));
		TestContext.assertTrue(set.containsAll(emptyValues));
		TestContext.assertTrue(set.containsAll((FastLongSet) null));
		TestContext.assertTrue(set.containsAll(emptySource));
		TestContext.assertFalse(set.containsAll(presentValues));
		TestContext.assertFalse(set.containsAll(source));
		TestContext.assertFalse(set.contains(7L));
		TestContext.assertFalse(set.remove(7L));
		set.clear();
		final long[] firstEmptyArray = set.toArray();
		TestContext.assertEquals(0, firstEmptyArray.length);
		TestContext.assertFalse(firstEmptyArray == set.toArray());
		TestContext.assertEquals(0, set.hashCode());
		TestContext.assertFalse(set.iterator().hasNext());
		TestContext.assertTrue(set.add(1L));
		TestContext.assertTrue(set.add(2L));
		TestContext.assertTrue(set.remove(1L));
		TestContext.assertTrue(set.remove(2L));
		TestContext.assertEquals(0, set.size());
		TestContext.assertFalse(set.contains(7L));
		TestContext.assertFalse(set.remove(7L));
		TestContext.assertEquals(0, set.toArray().length);
		TestContext.assertEquals(0, set.hashCode());
		TestContext.assertFalse(set.iterator().hasNext());
		final FastLongSet shared = (FastLongSet) set.shared();
		final FastLongSet unmodifiable = (FastLongSet) set.unmodifiable();
		final FastLongSet emptyView = (FastLongSet) emptySource.shared();
		final FastLongSet sourceView = (FastLongSet) source.unmodifiable();
		TestContext.assertTrue(shared.containsAll((long[]) null));
		TestContext.assertTrue(unmodifiable.containsAll((FastLongSet) null));
		TestContext.assertTrue(shared.containsAll(emptyValues));
		TestContext.assertTrue(unmodifiable.containsAll(emptyView));
		TestContext.assertFalse(shared.containsAll(presentValues));
		TestContext.assertFalse(unmodifiable.containsAll(sourceView));
		TestContext.assertFalse(shared.contains(7L));
		TestContext.assertFalse(shared.remove(7L));
		TestContext.assertFalse(unmodifiable.contains(7L));
		TestContext.assertEquals(0, shared.toArray().length);
		TestContext.assertEquals(0, unmodifiable.toArray().length);
		TestContext.assertEquals(0, shared.hashCode());
		TestContext.assertEquals(0, unmodifiable.hashCode());
		TestContext.assertFalse(shared.iterator().hasNext());
		TestContext.assertFalse(unmodifiable.iterator().hasNext());
		shared.clear();
		TestContext.assertTrue(shared.add(7L));
		TestContext.assertTrue(set.containsAll(presentValues));
		TestContext.assertTrue(shared.containsAll(sourceView));
		TestContext.assertTrue(unmodifiable.containsAll(sourceView));
	}
	private void checkShortSetEmptyFastPaths() {
		final short[] emptyValues = new short[0];
		final short[] presentValues = new short[] { (short) 7 };
		final FastShortSet emptySource = new FastShortSet();
		final FastShortSet source = new FastShortSet(presentValues);
		final FastShortSet set = new FastShortSet(16);
		TestContext.assertTrue(set.containsAll((short[]) null));
		TestContext.assertTrue(set.containsAll(emptyValues));
		TestContext.assertTrue(set.containsAll((FastShortSet) null));
		TestContext.assertTrue(set.containsAll(emptySource));
		TestContext.assertFalse(set.containsAll(presentValues));
		TestContext.assertFalse(set.containsAll(source));
		TestContext.assertFalse(set.contains((short) 7));
		TestContext.assertFalse(set.remove((short) 7));
		set.clear();
		final short[] firstEmptyArray = set.toArray();
		TestContext.assertEquals(0, firstEmptyArray.length);
		TestContext.assertFalse(firstEmptyArray == set.toArray());
		TestContext.assertEquals(0, set.hashCode());
		TestContext.assertFalse(set.iterator().hasNext());
		TestContext.assertTrue(set.add((short) 1));
		TestContext.assertTrue(set.add((short) 2));
		TestContext.assertTrue(set.remove((short) 1));
		TestContext.assertTrue(set.remove((short) 2));
		TestContext.assertEquals(0, set.size());
		TestContext.assertFalse(set.contains((short) 7));
		TestContext.assertFalse(set.remove((short) 7));
		TestContext.assertEquals(0, set.toArray().length);
		TestContext.assertEquals(0, set.hashCode());
		TestContext.assertFalse(set.iterator().hasNext());
		final FastShortSet shared = (FastShortSet) set.shared();
		final FastShortSet unmodifiable = (FastShortSet) set.unmodifiable();
		final FastShortSet emptyView = (FastShortSet) emptySource.shared();
		final FastShortSet sourceView = (FastShortSet) source.unmodifiable();
		TestContext.assertTrue(shared.containsAll((short[]) null));
		TestContext.assertTrue(unmodifiable.containsAll((FastShortSet) null));
		TestContext.assertTrue(shared.containsAll(emptyValues));
		TestContext.assertTrue(unmodifiable.containsAll(emptyView));
		TestContext.assertFalse(shared.containsAll(presentValues));
		TestContext.assertFalse(unmodifiable.containsAll(sourceView));
		TestContext.assertFalse(shared.contains((short) 7));
		TestContext.assertFalse(shared.remove((short) 7));
		TestContext.assertFalse(unmodifiable.contains((short) 7));
		TestContext.assertEquals(0, shared.toArray().length);
		TestContext.assertEquals(0, unmodifiable.toArray().length);
		TestContext.assertEquals(0, shared.hashCode());
		TestContext.assertEquals(0, unmodifiable.hashCode());
		TestContext.assertFalse(shared.iterator().hasNext());
		TestContext.assertFalse(unmodifiable.iterator().hasNext());
		shared.clear();
		TestContext.assertTrue(shared.add((short) 7));
		TestContext.assertTrue(set.containsAll(presentValues));
		TestContext.assertTrue(shared.containsAll(sourceView));
		TestContext.assertTrue(unmodifiable.containsAll(sourceView));
	}

	private void checkByteSetChurn() {
		final FastByteSet set = new FastByteSet(16);
		for(int round = 0; round < 256; ++round) {
			for(int i = 0; i < 8; ++i) {
				final byte value = (byte) (round * 13 + i);
				TestContext.assertTrue(set.add(value));
			}
			for(int i = 0; i < 8; ++i) {
				final byte value = (byte) (round * 13 + i);
				TestContext.assertTrue(set.remove(value));
			}
			TestContext.assertFalse(set.contains((byte) (round * 13 + 31)));
		}
		TestContext.assertEquals(0, set.size());
		TestContext.assertTrue(set.add((byte) 7));
		TestContext.assertTrue(set.add((byte) 9));
		final FastByteIterator iterator = (FastByteIterator) set.iterator();
		TestContext.assertTrue(iterator.hasNext());
		final byte returned = iterator.next();
		set.ensureCapacity(128);
		final int sizeBeforeRemove = set.size();
		iterator.remove();
		TestContext.assertFalse(set.contains(returned));
		TestContext.assertEquals(sizeBeforeRemove - 1, set.size());
	}
	private void checkCharSetChurn() {
		final FastCharSet set = new FastCharSet(16);
		for(int round = 0; round < 256; ++round) {
			for(int i = 0; i < 8; ++i) {
				final char value = (char) (round * 13 + i);
				TestContext.assertTrue(set.add(value));
			}
			for(int i = 0; i < 8; ++i) {
				final char value = (char) (round * 13 + i);
				TestContext.assertTrue(set.remove(value));
			}
			TestContext.assertFalse(set.contains((char) (round * 13 + 31)));
		}
		TestContext.assertEquals(0, set.size());
		TestContext.assertTrue(set.add((char) 7));
		TestContext.assertTrue(set.add((char) 9));
		final FastCharIterator iterator = (FastCharIterator) set.iterator();
		TestContext.assertTrue(iterator.hasNext());
		final char returned = iterator.next();
		set.ensureCapacity(128);
		final int sizeBeforeRemove = set.size();
		iterator.remove();
		TestContext.assertFalse(set.contains(returned));
		TestContext.assertEquals(sizeBeforeRemove - 1, set.size());
	}
	private void checkDoubleSetChurn() {
		final FastDoubleSet set = new FastDoubleSet(16);
		for(int round = 0; round < 256; ++round) {
			for(int i = 0; i < 8; ++i) {
				final double value = round * 13.0 + i + 0.25;
				TestContext.assertTrue(set.add(value));
			}
			for(int i = 0; i < 8; ++i) {
				final double value = round * 13.0 + i + 0.25;
				TestContext.assertTrue(set.remove(value));
			}
			TestContext.assertFalse(set.contains(round * 13.0 + 31.25));
		}
		TestContext.assertEquals(0, set.size());
		TestContext.assertTrue(set.add(7.25));
		TestContext.assertTrue(set.add(9.25));
		final FastDoubleIterator iterator = (FastDoubleIterator) set.iterator();
		TestContext.assertTrue(iterator.hasNext());
		final double returned = iterator.next();
		set.ensureCapacity(128);
		final int sizeBeforeRemove = set.size();
		iterator.remove();
		TestContext.assertFalse(set.contains(returned));
		TestContext.assertEquals(sizeBeforeRemove - 1, set.size());
	}
	private void checkFloatSetChurn() {
		final FastFloatSet set = new FastFloatSet(16);
		for(int round = 0; round < 256; ++round) {
			for(int i = 0; i < 8; ++i) {
				final float value = round * 13.0f + i + 0.25f;
				TestContext.assertTrue(set.add(value));
			}
			for(int i = 0; i < 8; ++i) {
				final float value = round * 13.0f + i + 0.25f;
				TestContext.assertTrue(set.remove(value));
			}
			TestContext.assertFalse(set.contains(round * 13.0f + 31.25f));
		}
		TestContext.assertEquals(0, set.size());
		TestContext.assertTrue(set.add(7.25f));
		TestContext.assertTrue(set.add(9.25f));
		final FastFloatIterator iterator = (FastFloatIterator) set.iterator();
		TestContext.assertTrue(iterator.hasNext());
		final float returned = iterator.next();
		set.ensureCapacity(128);
		final int sizeBeforeRemove = set.size();
		iterator.remove();
		TestContext.assertFalse(set.contains(returned));
		TestContext.assertEquals(sizeBeforeRemove - 1, set.size());
	}
	private void checkIntSetChurn() {
		final FastIntSet set = new FastIntSet(16);
		for(int round = 0; round < 256; ++round) {
			for(int i = 0; i < 8; ++i) {
				final int value = round * 13 + i;
				TestContext.assertTrue(set.add(value));
			}
			for(int i = 0; i < 8; ++i) {
				final int value = round * 13 + i;
				TestContext.assertTrue(set.remove(value));
			}
			TestContext.assertFalse(set.contains(round * 13 + 31));
		}
		TestContext.assertEquals(0, set.size());
		TestContext.assertTrue(set.add(7));
		TestContext.assertTrue(set.add(9));
		final FastIntIterator iterator = (FastIntIterator) set.iterator();
		TestContext.assertTrue(iterator.hasNext());
		final int returned = iterator.next();
		set.ensureCapacity(128);
		final int sizeBeforeRemove = set.size();
		iterator.remove();
		TestContext.assertFalse(set.contains(returned));
		TestContext.assertEquals(sizeBeforeRemove - 1, set.size());
	}
	private void checkLongSetChurn() {
		final FastLongSet set = new FastLongSet(16);
		for(int round = 0; round < 256; ++round) {
			for(int i = 0; i < 8; ++i) {
				final long value = round * 13L + i;
				TestContext.assertTrue(set.add(value));
			}
			for(int i = 0; i < 8; ++i) {
				final long value = round * 13L + i;
				TestContext.assertTrue(set.remove(value));
			}
			TestContext.assertFalse(set.contains(round * 13L + 31L));
		}
		TestContext.assertEquals(0, set.size());
		TestContext.assertTrue(set.add(7L));
		TestContext.assertTrue(set.add(9L));
		final FastLongIterator iterator = (FastLongIterator) set.iterator();
		TestContext.assertTrue(iterator.hasNext());
		final long returned = iterator.next();
		set.ensureCapacity(128);
		final int sizeBeforeRemove = set.size();
		iterator.remove();
		TestContext.assertFalse(set.contains(returned));
		TestContext.assertEquals(sizeBeforeRemove - 1, set.size());
	}
	private void checkShortSetChurn() {
		final FastShortSet set = new FastShortSet(16);
		for(int round = 0; round < 256; ++round) {
			for(int i = 0; i < 8; ++i) {
				final short value = (short) (round * 13 + i);
				TestContext.assertTrue(set.add(value));
			}
			for(int i = 0; i < 8; ++i) {
				final short value = (short) (round * 13 + i);
				TestContext.assertTrue(set.remove(value));
			}
			TestContext.assertFalse(set.contains((short) (round * 13 + 31)));
		}
		TestContext.assertEquals(0, set.size());
		TestContext.assertTrue(set.add((short) 7));
		TestContext.assertTrue(set.add((short) 9));
		final FastShortIterator iterator = (FastShortIterator) set.iterator();
		TestContext.assertTrue(iterator.hasNext());
		final short returned = iterator.next();
		set.ensureCapacity(128);
		final int sizeBeforeRemove = set.size();
		iterator.remove();
		TestContext.assertFalse(set.contains(returned));
		TestContext.assertEquals(sizeBeforeRemove - 1, set.size());
	}
	private void checkFloatingPointSetSemantics() {
		final FastFloatSet floats = new FastFloatSet();
		TestContext.assertTrue(floats.add(Float.NaN));
		TestContext.assertFalse(floats.add(Float.intBitsToFloat(0x7f800001)));
		TestContext.assertTrue(floats.add(0.0f));
		TestContext.assertTrue(floats.add(-0.0f));
		TestContext.assertEquals(3, floats.size());
		final FastDoubleSet doubles = new FastDoubleSet();
		TestContext.assertTrue(doubles.add(Double.NaN));
		TestContext.assertFalse(doubles.add(Double.longBitsToDouble(0x7ff0000000000001L)));
		TestContext.assertTrue(doubles.add(0.0));
		TestContext.assertTrue(doubles.add(-0.0));
		TestContext.assertEquals(3, doubles.size());
	}
	private void checkConcurrentIntTableWrites() throws InterruptedException {
		final FastIntTable shared = (FastIntTable) new FastIntTable().shared();
		final Thread first = new Thread(new Runnable() {
			public void run() {
				for(int i = 0; i < 1000; ++i) {
					shared.add(i);
				}
			}
		});
		final Thread second = new Thread(new Runnable() {
			public void run() {
				for(int i = 1000; i < 2000; ++i) {
					shared.add(i);
				}
			}
		});
		first.start();
		second.start();
		first.join();
		second.join();
		TestContext.assertEquals(2000, shared.size());
	}
	private final class MapPutGetRemove extends TestCase {
		public String getName() {
			return "Primitive maps: put, get, remove, bulk and edge semantics";
		}
		public void execute() {}
		public void validate() {
			checkIntBooleanMapPutGetRemove();
			checkIntByteMapPutGetRemove();
			checkIntCharMapPutGetRemove();
			checkIntDoubleMapPutGetRemove();
			checkIntFloatMapPutGetRemove();
			checkIntIntMapPutGetRemove();
			checkIntLongMapPutGetRemove();
			checkIntShortMapPutGetRemove();
			checkIntObjectMapPutGetRemove();
			checkLongBooleanMapPutGetRemove();
			checkLongByteMapPutGetRemove();
			checkLongCharMapPutGetRemove();
			checkLongDoubleMapPutGetRemove();
			checkLongFloatMapPutGetRemove();
			checkLongIntMapPutGetRemove();
			checkLongLongMapPutGetRemove();
			checkLongShortMapPutGetRemove();
			checkLongObjectMapPutGetRemove();
			checkMapEdgeSemantics();
		}
	}
	private final class MapTombstoneChurn extends TestCase {
		public String getName() {
			return "Primitive maps: tombstone churn, capacity and iterator remove";
		}
		public void execute() {}
		public void validate() {
			checkIntBooleanMapChurn();
			checkIntByteMapChurn();
			checkIntCharMapChurn();
			checkIntDoubleMapChurn();
			checkIntFloatMapChurn();
			checkIntIntMapChurn();
			checkIntLongMapChurn();
			checkIntShortMapChurn();
			checkIntObjectMapChurn();
			checkLongBooleanMapChurn();
			checkLongByteMapChurn();
			checkLongCharMapChurn();
			checkLongDoubleMapChurn();
			checkLongFloatMapChurn();
			checkLongIntMapChurn();
			checkLongLongMapChurn();
			checkLongShortMapChurn();
			checkLongObjectMapChurn();
		}
	}
	private final class MapEmptyFastPaths extends TestCase {
		public String getName() {
			return "Primitive maps: empty fast paths and tombstone clear";
		}
		public void execute() {}
		public void validate() {
			checkIntBooleanMapEmptyFastPaths();
			checkIntByteMapEmptyFastPaths();
			checkIntCharMapEmptyFastPaths();
			checkIntDoubleMapEmptyFastPaths();
			checkIntFloatMapEmptyFastPaths();
			checkIntIntMapEmptyFastPaths();
			checkIntLongMapEmptyFastPaths();
			checkIntShortMapEmptyFastPaths();
			checkIntObjectMapEmptyFastPaths();
			checkLongBooleanMapEmptyFastPaths();
			checkLongByteMapEmptyFastPaths();
			checkLongCharMapEmptyFastPaths();
			checkLongDoubleMapEmptyFastPaths();
			checkLongFloatMapEmptyFastPaths();
			checkLongIntMapEmptyFastPaths();
			checkLongLongMapEmptyFastPaths();
			checkLongShortMapEmptyFastPaths();
			checkLongObjectMapEmptyFastPaths();
		}
	}
	private final class MapViewsAndIterators extends TestCase {
		public String getName() {
			return "Primitive maps: views, iterators and concurrent writes";
		}
		public void execute() {}
		public void validate() throws Exception {
			checkIntBooleanMapViews();
			checkIntByteMapViews();
			checkIntCharMapViews();
			checkIntDoubleMapViews();
			checkIntFloatMapViews();
			checkIntIntMapViews();
			checkIntLongMapViews();
			checkIntShortMapViews();
			checkIntObjectMapViews();
			checkLongBooleanMapViews();
			checkLongByteMapViews();
			checkLongCharMapViews();
			checkLongDoubleMapViews();
			checkLongFloatMapViews();
			checkLongIntMapViews();
			checkLongLongMapViews();
			checkLongShortMapViews();
			checkLongObjectMapViews();
			checkConcurrentMapWrites();
		}
	}
	private void checkIntBooleanMapPutGetRemove() {
		final int k1 = Integer.MIN_VALUE;
		final int k2 = 0;
		final int k3 = 1;
		final int k4 = Integer.MAX_VALUE;
		final int kAbsent = 424242;
		final boolean v1 = true;
		final boolean v2 = false;
		final boolean v3 = true;
		final boolean v4 = false;
		final boolean fallback = true;
		final boolean wrongVal = true;
		final FastIntBooleanMap map = new FastIntBooleanMap();
		TestContext.assertEquals(0, map.size());
		TestContext.assertTrue(map.isEmpty());
		TestContext.assertTrue((map.get(k1) == false));
		TestContext.assertTrue((map.get(k1, fallback) == fallback));
		TestContext.assertFalse(map.containsKey(k1));
		TestContext.assertTrue((map.put(k1, v1) == false));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.containsKey(k1));
		TestContext.assertTrue(map.containsValue(v1));
		TestContext.assertTrue((map.get(k1) == true));
		TestContext.assertTrue((map.put(k1, v2) == true));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue((map.get(k1) == false));
		TestContext.assertFalse(map.putIfAbsent(k1, v1));
		TestContext.assertTrue((map.get(k1) == false));
		TestContext.assertTrue(map.putIfAbsent(k3, v3));
		TestContext.assertEquals(2, map.size());
		TestContext.assertTrue((map.get(k3, fallback) == true));
		final int[] bulkKeys = new int[] { k2, k4 };
		final boolean[] bulkVals = new boolean[] { v2, v4 };
		TestContext.assertTrue(map.putAll(bulkKeys, bulkVals));
		TestContext.assertEquals(4, map.size());
		TestContext.assertFalse(map.putAll(bulkKeys, bulkVals));
		TestContext.assertFalse(map.putAll((int[]) null, (boolean[]) null));
		boolean threw = false;
		try {
			map.putAll(new int[] { k1 }, bulkVals);
		}
		catch(final IllegalArgumentException ex) {
			threw = true;
		}
		TestContext.assertTrue(threw);
		final FastIntBooleanMap copy = new FastIntBooleanMap(map);
		TestContext.assertTrue(map.equals(copy));
		TestContext.assertTrue(copy.equals(map));
		TestContext.assertEquals(map.hashCode(), copy.hashCode());
		TestContext.assertFalse(map.putAll(copy));
		TestContext.assertFalse(map.putAll(map));
		final FastIntBooleanMap fromArrays = new FastIntBooleanMap(bulkKeys, bulkVals);
		TestContext.assertEquals(2, fromArrays.size());
		TestContext.assertTrue(fromArrays.containsKey(k2));
		TestContext.assertTrue(fromArrays.containsKey(k4));
		TestContext.assertEquals(4, map.keys().length);
		TestContext.assertEquals(4, map.values().length);
		final FastIntSet ks = map.keySet();
		TestContext.assertEquals(4, ks.size());
		TestContext.assertTrue(ks.contains(k1));
		TestContext.assertTrue(ks.contains(k4));
		TestContext.assertTrue(map.toString().indexOf('=') >= 0);
		try {
			final FastIntBooleanMap cloned = (FastIntBooleanMap) map.clone();
			TestContext.assertTrue(map.equals(cloned));
			cloned.put(kAbsent, v1);
			TestContext.assertFalse(map.containsKey(kAbsent));
		}
		catch(final UnsupportedOperationException j2meExpected) {}
		catch(final CloneNotSupportedException ex) {
			TestContext.assertTrue(false);
		}
		TestContext.assertFalse(map.removeEntry(k1, wrongVal));
		TestContext.assertTrue(map.removeEntry(k1, v2));
		TestContext.assertFalse(map.containsKey(k1));
		TestContext.assertTrue((map.remove(kAbsent, fallback) == fallback));
		TestContext.assertTrue((map.remove(k3) == true));
		TestContext.assertTrue(map.removeKey(k2));
		TestContext.assertFalse(map.removeKey(kAbsent));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.containsKey(k4));
		TestContext.assertTrue((map.put(k1, v1) == false));
		TestContext.assertTrue(map.removeAllKeys(new int[] { k1 }));
		final FastIntSet remSet = new FastIntSet(new int[] { k4 });
		TestContext.assertTrue(map.putIfAbsent(k2, v2));
		TestContext.assertTrue(map.retainAllKeys(remSet));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.removeAllKeys(remSet));
		TestContext.assertEquals(0, map.size());
	}
	private void checkIntBooleanMapChurn() {
		final FastIntBooleanMap map = new FastIntBooleanMap(16);
		for(int round = 0; round < 256; ++round) {
			for(int i = 0; i < 8; ++i) {
				final int key = (round * 13 + i);
				final boolean val = ((i & 1) != 0);
				TestContext.assertTrue(map.putIfAbsent(key, val));
			}
			for(int i = 0; i < 8; ++i) {
				final int key = (round * 13 + i);
				TestContext.assertTrue(map.removeKey(key));
			}
			TestContext.assertFalse(map.containsKey((round * 13 + 31)));
		}
		TestContext.assertEquals(0, map.size());
		final int keep = 987654321;
		for(int i = 0; i < 50; ++i) {
			map.put(keep, true);
		}
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.removeKey(keep));
		TestContext.assertTrue(map.putIfAbsent(7, true));
		TestContext.assertTrue(map.putIfAbsent(9, false));
		final FastIntIterator it = (FastIntIterator) map.keyIterator();
		TestContext.assertTrue(it.hasNext());
		final int returned = it.next();
		map.ensureCapacity(128);
		final int sizeBeforeRemove = map.size();
		it.remove();
		TestContext.assertFalse(map.containsKey(returned));
		TestContext.assertEquals(sizeBeforeRemove - 1, map.size());
		map.trimToSize();
		TestContext.assertEquals(1, map.size());
	}
	private void checkIntBooleanMapEmptyFastPaths() {
		final boolean fallback = true;
		final int[] emptyKeys = new int[0];
		final boolean[] emptyVals = new boolean[0];
		final FastIntSet emptySet = new FastIntSet();
		final FastIntBooleanMap emptySource = new FastIntBooleanMap();
		final FastIntBooleanMap map = new FastIntBooleanMap(16);
		TestContext.assertTrue((map.get(7) == false));
		TestContext.assertTrue((map.get(7, fallback) == fallback));
		TestContext.assertFalse(map.containsKey(7));
		TestContext.assertFalse(map.containsValue(true));
		TestContext.assertTrue((map.remove(7) == false));
		TestContext.assertTrue((map.remove(7, fallback) == fallback));
		TestContext.assertFalse(map.removeKey(7));
		TestContext.assertFalse(map.removeEntry(7, true));
		TestContext.assertFalse(map.removeAllKeys((int[]) null));
		TestContext.assertFalse(map.removeAllKeys(emptyKeys));
		TestContext.assertFalse(map.removeAllKeys((FastIntSet) null));
		TestContext.assertFalse(map.removeAllKeys(emptySet));
		TestContext.assertFalse(map.retainAllKeys((FastIntSet) null));
		TestContext.assertFalse(map.retainAllKeys(emptySet));
		TestContext.assertFalse(map.putAll((FastIntBooleanMap) null));
		TestContext.assertFalse(map.putAll(emptySource));
		TestContext.assertFalse(map.putAll(emptyKeys, emptyVals));
		map.clear();
		TestContext.assertEquals(0, map.keys().length);
		TestContext.assertEquals(0, map.values().length);
		TestContext.assertTrue(map.keySet().isEmpty());
		TestContext.assertEquals(0, map.hashCode());
		TestContext.assertEquals("{}", map.toString());
		TestContext.assertFalse(map.keyIterator().hasNext());
		TestContext.assertFalse(map.valueIterator().hasNext());
		TestContext.assertTrue(map.putIfAbsent(1, true));
		TestContext.assertTrue(map.putIfAbsent(Integer.MAX_VALUE, false));
		TestContext.assertTrue(map.removeKey(1));
		TestContext.assertTrue(map.removeKey(Integer.MAX_VALUE));
		TestContext.assertEquals(0, map.size());
		TestContext.assertFalse(map.containsKey(1));
		TestContext.assertFalse(map.containsValue(true));
		TestContext.assertEquals(0, map.keys().length);
		TestContext.assertEquals(0, map.values().length);
		TestContext.assertEquals(0, map.hashCode());
		TestContext.assertEquals("{}", map.toString());
		TestContext.assertFalse(map.keyIterator().hasNext());
		TestContext.assertFalse(map.valueIterator().hasNext());
		final FastIntBooleanMap shared = (FastIntBooleanMap) map.shared();
		final FastIntBooleanMap unmodifiable = (FastIntBooleanMap) map.unmodifiable();
		TestContext.assertTrue((shared.get(7, fallback) == fallback));
		TestContext.assertTrue((unmodifiable.get(7, fallback) == fallback));
		TestContext.assertFalse(shared.containsKey(7));
		TestContext.assertFalse(unmodifiable.containsKey(7));
		TestContext.assertEquals(0, shared.keys().length);
		TestContext.assertEquals(0, unmodifiable.keys().length);
		TestContext.assertEquals("{}", shared.toString());
		TestContext.assertEquals("{}", unmodifiable.toString());
		TestContext.assertFalse(shared.keyIterator().hasNext());
		TestContext.assertFalse(unmodifiable.valueIterator().hasNext());
		shared.clear();
		TestContext.assertTrue(shared.putIfAbsent(7, true));
		TestContext.assertTrue(unmodifiable.containsKey(7));
	}
	private void checkIntBooleanMapViews() {
		final FastIntBooleanMap source = new FastIntBooleanMap();
		source.put(Integer.MIN_VALUE, true);
		source.put(1, false);
		final FastIntBooleanMap shared = (FastIntBooleanMap) source.shared();
		final FastIntBooleanMap unmodifiable = (FastIntBooleanMap) source.unmodifiable();
		final FastIntBooleanMap sharedUnmod = (FastIntBooleanMap) shared.unmodifiable();
		TestContext.assertTrue(source.equals(shared));
		TestContext.assertTrue(shared.equals(source));
		TestContext.assertTrue(source.equals(unmodifiable));
		TestContext.assertTrue(unmodifiable.equals(source));
		TestContext.assertTrue(shared.equals(sharedUnmod));
		TestContext.assertTrue(sharedUnmod.equals(shared));
		TestContext.assertEquals(source.hashCode(), shared.hashCode());
		TestContext.assertEquals(source.hashCode(), unmodifiable.hashCode());
		TestContext.assertEquals(source.hashCode(), sharedUnmod.hashCode());
		final FastIntIterator kit = (FastIntIterator) shared.keyIterator();
		boolean badRemove = false;
		try { kit.remove(); } catch(final IllegalStateException ex) { badRemove = true; }
		TestContext.assertTrue(badRemove);
		int kCount = 0;
		while(kit.hasNext()) { kit.next(); ++kCount; }
		TestContext.assertEquals(2, kCount);
		boolean noMore = false;
		try { kit.next(); } catch(final NoSuchElementException ex) { noMore = true; }
		TestContext.assertTrue(noMore);
		final FastBooleanIterator vit = (FastBooleanIterator) shared.valueIterator();
		TestContext.assertTrue(vit.hasNext());
		vit.next();
		shared.ensureCapacity(64);
		vit.remove();
		TestContext.assertEquals(1, unmodifiable.size());
		TestContext.assertEquals(1, sharedUnmod.size());
		badRemove = false;
		try { vit.remove(); } catch(final IllegalStateException ex) { badRemove = true; }
		TestContext.assertTrue(badRemove);
		final FastIntIterator ukit = (FastIntIterator) unmodifiable.keyIterator();
		ukit.next();
		int rejected = 0;
		try { ukit.remove(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		final FastBooleanIterator uvit = (FastBooleanIterator) sharedUnmod.valueIterator();
		uvit.next();
		try { uvit.remove(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.put(7, true); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putIfAbsent(7, true); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putAll(new int[] { 7 }, new boolean[] { true }); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putAll(source); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.remove(Integer.MIN_VALUE); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.remove(Integer.MIN_VALUE, true); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeKey(Integer.MIN_VALUE); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeEntry(Integer.MIN_VALUE, true); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeAllKeys(new int[] { Integer.MIN_VALUE }); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeAllKeys(new FastIntSet()); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.retainAllKeys(new FastIntSet()); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.clear(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.reset(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.trimToSize(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.ensureCapacity(64); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.shared(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		TestContext.assertEquals(18, rejected);
	}
	private void checkIntByteMapPutGetRemove() {
		final int k1 = Integer.MIN_VALUE;
		final int k2 = 0;
		final int k3 = 1;
		final int k4 = Integer.MAX_VALUE;
		final int kAbsent = 424242;
		final byte v1 = (byte) 11;
		final byte v2 = (byte) -22;
		final byte v3 = (byte) 0;
		final byte v4 = (byte) 127;
		final byte fallback = (byte) 99;
		final byte wrongVal = (byte) 55;
		final FastIntByteMap map = new FastIntByteMap();
		TestContext.assertEquals(0, map.size());
		TestContext.assertTrue(map.isEmpty());
		TestContext.assertTrue((map.get(k1) == (byte) 0));
		TestContext.assertTrue((map.get(k1, fallback) == fallback));
		TestContext.assertFalse(map.containsKey(k1));
		TestContext.assertTrue((map.put(k1, v1) == (byte) 0));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.containsKey(k1));
		TestContext.assertTrue(map.containsValue(v1));
		TestContext.assertTrue((map.get(k1) == (byte) 11));
		TestContext.assertTrue((map.put(k1, v2) == (byte) 11));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue((map.get(k1) == (byte) -22));
		TestContext.assertFalse(map.putIfAbsent(k1, v1));
		TestContext.assertTrue((map.get(k1) == (byte) -22));
		TestContext.assertTrue(map.putIfAbsent(k3, v3));
		TestContext.assertEquals(2, map.size());
		TestContext.assertTrue((map.get(k3, fallback) == (byte) 0));
		TestContext.assertFalse(map.containsValue((byte) 55));
		final int[] bulkKeys = new int[] { k2, k4 };
		final byte[] bulkVals = new byte[] { v2, v4 };
		TestContext.assertTrue(map.putAll(bulkKeys, bulkVals));
		TestContext.assertEquals(4, map.size());
		TestContext.assertFalse(map.putAll(bulkKeys, bulkVals));
		TestContext.assertFalse(map.putAll((int[]) null, (byte[]) null));
		boolean threw = false;
		try {
			map.putAll(new int[] { k1 }, bulkVals);
		}
		catch(final IllegalArgumentException ex) {
			threw = true;
		}
		TestContext.assertTrue(threw);
		final FastIntByteMap copy = new FastIntByteMap(map);
		TestContext.assertTrue(map.equals(copy));
		TestContext.assertTrue(copy.equals(map));
		TestContext.assertEquals(map.hashCode(), copy.hashCode());
		TestContext.assertFalse(map.putAll(copy));
		TestContext.assertFalse(map.putAll(map));
		final FastIntByteMap fromArrays = new FastIntByteMap(bulkKeys, bulkVals);
		TestContext.assertEquals(2, fromArrays.size());
		TestContext.assertTrue(fromArrays.containsKey(k2));
		TestContext.assertTrue(fromArrays.containsKey(k4));
		TestContext.assertEquals(4, map.keys().length);
		TestContext.assertEquals(4, map.values().length);
		final FastIntSet ks = map.keySet();
		TestContext.assertEquals(4, ks.size());
		TestContext.assertTrue(ks.contains(k1));
		TestContext.assertTrue(ks.contains(k4));
		TestContext.assertTrue(map.toString().indexOf('=') >= 0);
		try {
			final FastIntByteMap cloned = (FastIntByteMap) map.clone();
			TestContext.assertTrue(map.equals(cloned));
			cloned.put(kAbsent, v1);
			TestContext.assertFalse(map.containsKey(kAbsent));
		}
		catch(final UnsupportedOperationException j2meExpected) {}
		catch(final CloneNotSupportedException ex) {
			TestContext.assertTrue(false);
		}
		TestContext.assertFalse(map.removeEntry(k1, wrongVal));
		TestContext.assertTrue(map.removeEntry(k1, v2));
		TestContext.assertFalse(map.containsKey(k1));
		TestContext.assertTrue((map.remove(kAbsent, fallback) == fallback));
		TestContext.assertTrue((map.remove(k3) == (byte) 0));
		TestContext.assertTrue(map.removeKey(k2));
		TestContext.assertFalse(map.removeKey(kAbsent));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.containsKey(k4));
		TestContext.assertTrue((map.put(k1, v1) == (byte) 0));
		TestContext.assertTrue(map.removeAllKeys(new int[] { k1 }));
		final FastIntSet remSet = new FastIntSet(new int[] { k4 });
		TestContext.assertTrue(map.putIfAbsent(k2, v2));
		TestContext.assertTrue(map.retainAllKeys(remSet));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.removeAllKeys(remSet));
		TestContext.assertEquals(0, map.size());
	}
	private void checkIntByteMapChurn() {
		final FastIntByteMap map = new FastIntByteMap(16);
		for(int round = 0; round < 256; ++round) {
			for(int i = 0; i < 8; ++i) {
				final int key = (round * 13 + i);
				final byte val = ((byte) (round * 7 + i));
				TestContext.assertTrue(map.putIfAbsent(key, val));
			}
			for(int i = 0; i < 8; ++i) {
				final int key = (round * 13 + i);
				TestContext.assertTrue(map.removeKey(key));
			}
			TestContext.assertFalse(map.containsKey((round * 13 + 31)));
		}
		TestContext.assertEquals(0, map.size());
		final int keep = 987654321;
		for(int i = 0; i < 50; ++i) {
			map.put(keep, (byte) 11);
		}
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.removeKey(keep));
		TestContext.assertTrue(map.putIfAbsent(7, (byte) 11));
		TestContext.assertTrue(map.putIfAbsent(9, (byte) -22));
		final FastIntIterator it = (FastIntIterator) map.keyIterator();
		TestContext.assertTrue(it.hasNext());
		final int returned = it.next();
		map.ensureCapacity(128);
		final int sizeBeforeRemove = map.size();
		it.remove();
		TestContext.assertFalse(map.containsKey(returned));
		TestContext.assertEquals(sizeBeforeRemove - 1, map.size());
		map.trimToSize();
		TestContext.assertEquals(1, map.size());
	}
	private void checkIntByteMapEmptyFastPaths() {
		final byte fallback = (byte) 99;
		final int[] emptyKeys = new int[0];
		final byte[] emptyVals = new byte[0];
		final FastIntSet emptySet = new FastIntSet();
		final FastIntByteMap emptySource = new FastIntByteMap();
		final FastIntByteMap map = new FastIntByteMap(16);
		TestContext.assertTrue((map.get(7) == (byte) 0));
		TestContext.assertTrue((map.get(7, fallback) == fallback));
		TestContext.assertFalse(map.containsKey(7));
		TestContext.assertFalse(map.containsValue((byte) 11));
		TestContext.assertTrue((map.remove(7) == (byte) 0));
		TestContext.assertTrue((map.remove(7, fallback) == fallback));
		TestContext.assertFalse(map.removeKey(7));
		TestContext.assertFalse(map.removeEntry(7, (byte) 11));
		TestContext.assertFalse(map.removeAllKeys((int[]) null));
		TestContext.assertFalse(map.removeAllKeys(emptyKeys));
		TestContext.assertFalse(map.removeAllKeys((FastIntSet) null));
		TestContext.assertFalse(map.removeAllKeys(emptySet));
		TestContext.assertFalse(map.retainAllKeys((FastIntSet) null));
		TestContext.assertFalse(map.retainAllKeys(emptySet));
		TestContext.assertFalse(map.putAll((FastIntByteMap) null));
		TestContext.assertFalse(map.putAll(emptySource));
		TestContext.assertFalse(map.putAll(emptyKeys, emptyVals));
		map.clear();
		TestContext.assertEquals(0, map.keys().length);
		TestContext.assertEquals(0, map.values().length);
		TestContext.assertTrue(map.keySet().isEmpty());
		TestContext.assertEquals(0, map.hashCode());
		TestContext.assertEquals("{}", map.toString());
		TestContext.assertFalse(map.keyIterator().hasNext());
		TestContext.assertFalse(map.valueIterator().hasNext());
		TestContext.assertTrue(map.putIfAbsent(1, (byte) 11));
		TestContext.assertTrue(map.putIfAbsent(Integer.MAX_VALUE, (byte) -22));
		TestContext.assertTrue(map.removeKey(1));
		TestContext.assertTrue(map.removeKey(Integer.MAX_VALUE));
		TestContext.assertEquals(0, map.size());
		TestContext.assertFalse(map.containsKey(1));
		TestContext.assertFalse(map.containsValue((byte) 11));
		TestContext.assertEquals(0, map.keys().length);
		TestContext.assertEquals(0, map.values().length);
		TestContext.assertEquals(0, map.hashCode());
		TestContext.assertEquals("{}", map.toString());
		TestContext.assertFalse(map.keyIterator().hasNext());
		TestContext.assertFalse(map.valueIterator().hasNext());
		final FastIntByteMap shared = (FastIntByteMap) map.shared();
		final FastIntByteMap unmodifiable = (FastIntByteMap) map.unmodifiable();
		TestContext.assertTrue((shared.get(7, fallback) == fallback));
		TestContext.assertTrue((unmodifiable.get(7, fallback) == fallback));
		TestContext.assertFalse(shared.containsKey(7));
		TestContext.assertFalse(unmodifiable.containsKey(7));
		TestContext.assertEquals(0, shared.keys().length);
		TestContext.assertEquals(0, unmodifiable.keys().length);
		TestContext.assertEquals("{}", shared.toString());
		TestContext.assertEquals("{}", unmodifiable.toString());
		TestContext.assertFalse(shared.keyIterator().hasNext());
		TestContext.assertFalse(unmodifiable.valueIterator().hasNext());
		shared.clear();
		TestContext.assertTrue(shared.putIfAbsent(7, (byte) 11));
		TestContext.assertTrue(unmodifiable.containsKey(7));
	}
	private void checkIntByteMapViews() {
		final FastIntByteMap source = new FastIntByteMap();
		source.put(Integer.MIN_VALUE, (byte) 11);
		source.put(1, (byte) -22);
		final FastIntByteMap shared = (FastIntByteMap) source.shared();
		final FastIntByteMap unmodifiable = (FastIntByteMap) source.unmodifiable();
		final FastIntByteMap sharedUnmod = (FastIntByteMap) shared.unmodifiable();
		TestContext.assertTrue(source.equals(shared));
		TestContext.assertTrue(shared.equals(source));
		TestContext.assertTrue(source.equals(unmodifiable));
		TestContext.assertTrue(unmodifiable.equals(source));
		TestContext.assertTrue(shared.equals(sharedUnmod));
		TestContext.assertTrue(sharedUnmod.equals(shared));
		TestContext.assertEquals(source.hashCode(), shared.hashCode());
		TestContext.assertEquals(source.hashCode(), unmodifiable.hashCode());
		TestContext.assertEquals(source.hashCode(), sharedUnmod.hashCode());
		final FastIntIterator kit = (FastIntIterator) shared.keyIterator();
		boolean badRemove = false;
		try { kit.remove(); } catch(final IllegalStateException ex) { badRemove = true; }
		TestContext.assertTrue(badRemove);
		int kCount = 0;
		while(kit.hasNext()) { kit.next(); ++kCount; }
		TestContext.assertEquals(2, kCount);
		boolean noMore = false;
		try { kit.next(); } catch(final NoSuchElementException ex) { noMore = true; }
		TestContext.assertTrue(noMore);
		final FastByteIterator vit = (FastByteIterator) shared.valueIterator();
		TestContext.assertTrue(vit.hasNext());
		vit.next();
		shared.ensureCapacity(64);
		vit.remove();
		TestContext.assertEquals(1, unmodifiable.size());
		TestContext.assertEquals(1, sharedUnmod.size());
		badRemove = false;
		try { vit.remove(); } catch(final IllegalStateException ex) { badRemove = true; }
		TestContext.assertTrue(badRemove);
		final FastIntIterator ukit = (FastIntIterator) unmodifiable.keyIterator();
		ukit.next();
		int rejected = 0;
		try { ukit.remove(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		final FastByteIterator uvit = (FastByteIterator) sharedUnmod.valueIterator();
		uvit.next();
		try { uvit.remove(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.put(7, (byte) 11); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putIfAbsent(7, (byte) 11); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putAll(new int[] { 7 }, new byte[] { (byte) 11 }); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putAll(source); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.remove(Integer.MIN_VALUE); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.remove(Integer.MIN_VALUE, (byte) 99); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeKey(Integer.MIN_VALUE); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeEntry(Integer.MIN_VALUE, (byte) 11); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeAllKeys(new int[] { Integer.MIN_VALUE }); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeAllKeys(new FastIntSet()); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.retainAllKeys(new FastIntSet()); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.clear(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.reset(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.trimToSize(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.ensureCapacity(64); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.shared(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		TestContext.assertEquals(18, rejected);
	}
	private void checkIntCharMapPutGetRemove() {
		final int k1 = Integer.MIN_VALUE;
		final int k2 = 0;
		final int k3 = 1;
		final int k4 = Integer.MAX_VALUE;
		final int kAbsent = 424242;
		final char v1 = 'A';
		final char v2 = 'Z';
		final char v3 = '\0';
		final char v4 = 'm';
		final char fallback = '!';
		final char wrongVal = '~';
		final FastIntCharMap map = new FastIntCharMap();
		TestContext.assertEquals(0, map.size());
		TestContext.assertTrue(map.isEmpty());
		TestContext.assertTrue((map.get(k1) == '\0'));
		TestContext.assertTrue((map.get(k1, fallback) == fallback));
		TestContext.assertFalse(map.containsKey(k1));
		TestContext.assertTrue((map.put(k1, v1) == '\0'));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.containsKey(k1));
		TestContext.assertTrue(map.containsValue(v1));
		TestContext.assertTrue((map.get(k1) == 'A'));
		TestContext.assertTrue((map.put(k1, v2) == 'A'));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue((map.get(k1) == 'Z'));
		TestContext.assertFalse(map.putIfAbsent(k1, v1));
		TestContext.assertTrue((map.get(k1) == 'Z'));
		TestContext.assertTrue(map.putIfAbsent(k3, v3));
		TestContext.assertEquals(2, map.size());
		TestContext.assertTrue((map.get(k3, fallback) == '\0'));
		TestContext.assertFalse(map.containsValue('~'));
		final int[] bulkKeys = new int[] { k2, k4 };
		final char[] bulkVals = new char[] { v2, v4 };
		TestContext.assertTrue(map.putAll(bulkKeys, bulkVals));
		TestContext.assertEquals(4, map.size());
		TestContext.assertFalse(map.putAll(bulkKeys, bulkVals));
		TestContext.assertFalse(map.putAll((int[]) null, (char[]) null));
		boolean threw = false;
		try {
			map.putAll(new int[] { k1 }, bulkVals);
		}
		catch(final IllegalArgumentException ex) {
			threw = true;
		}
		TestContext.assertTrue(threw);
		final FastIntCharMap copy = new FastIntCharMap(map);
		TestContext.assertTrue(map.equals(copy));
		TestContext.assertTrue(copy.equals(map));
		TestContext.assertEquals(map.hashCode(), copy.hashCode());
		TestContext.assertFalse(map.putAll(copy));
		TestContext.assertFalse(map.putAll(map));
		final FastIntCharMap fromArrays = new FastIntCharMap(bulkKeys, bulkVals);
		TestContext.assertEquals(2, fromArrays.size());
		TestContext.assertTrue(fromArrays.containsKey(k2));
		TestContext.assertTrue(fromArrays.containsKey(k4));
		TestContext.assertEquals(4, map.keys().length);
		TestContext.assertEquals(4, map.values().length);
		final FastIntSet ks = map.keySet();
		TestContext.assertEquals(4, ks.size());
		TestContext.assertTrue(ks.contains(k1));
		TestContext.assertTrue(ks.contains(k4));
		TestContext.assertTrue(map.toString().indexOf('=') >= 0);
		try {
			final FastIntCharMap cloned = (FastIntCharMap) map.clone();
			TestContext.assertTrue(map.equals(cloned));
			cloned.put(kAbsent, v1);
			TestContext.assertFalse(map.containsKey(kAbsent));
		}
		catch(final UnsupportedOperationException j2meExpected) {}
		catch(final CloneNotSupportedException ex) {
			TestContext.assertTrue(false);
		}
		TestContext.assertFalse(map.removeEntry(k1, wrongVal));
		TestContext.assertTrue(map.removeEntry(k1, v2));
		TestContext.assertFalse(map.containsKey(k1));
		TestContext.assertTrue((map.remove(kAbsent, fallback) == fallback));
		TestContext.assertTrue((map.remove(k3) == '\0'));
		TestContext.assertTrue(map.removeKey(k2));
		TestContext.assertFalse(map.removeKey(kAbsent));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.containsKey(k4));
		TestContext.assertTrue((map.put(k1, v1) == '\0'));
		TestContext.assertTrue(map.removeAllKeys(new int[] { k1 }));
		final FastIntSet remSet = new FastIntSet(new int[] { k4 });
		TestContext.assertTrue(map.putIfAbsent(k2, v2));
		TestContext.assertTrue(map.retainAllKeys(remSet));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.removeAllKeys(remSet));
		TestContext.assertEquals(0, map.size());
	}
	private void checkIntCharMapChurn() {
		final FastIntCharMap map = new FastIntCharMap(16);
		for(int round = 0; round < 256; ++round) {
			for(int i = 0; i < 8; ++i) {
				final int key = (round * 13 + i);
				final char val = ((char) ('a' + ((round + i) % 26)));
				TestContext.assertTrue(map.putIfAbsent(key, val));
			}
			for(int i = 0; i < 8; ++i) {
				final int key = (round * 13 + i);
				TestContext.assertTrue(map.removeKey(key));
			}
			TestContext.assertFalse(map.containsKey((round * 13 + 31)));
		}
		TestContext.assertEquals(0, map.size());
		final int keep = 987654321;
		for(int i = 0; i < 50; ++i) {
			map.put(keep, 'A');
		}
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.removeKey(keep));
		TestContext.assertTrue(map.putIfAbsent(7, 'A'));
		TestContext.assertTrue(map.putIfAbsent(9, 'Z'));
		final FastIntIterator it = (FastIntIterator) map.keyIterator();
		TestContext.assertTrue(it.hasNext());
		final int returned = it.next();
		map.ensureCapacity(128);
		final int sizeBeforeRemove = map.size();
		it.remove();
		TestContext.assertFalse(map.containsKey(returned));
		TestContext.assertEquals(sizeBeforeRemove - 1, map.size());
		map.trimToSize();
		TestContext.assertEquals(1, map.size());
	}
	private void checkIntCharMapEmptyFastPaths() {
		final char fallback = '!';
		final int[] emptyKeys = new int[0];
		final char[] emptyVals = new char[0];
		final FastIntSet emptySet = new FastIntSet();
		final FastIntCharMap emptySource = new FastIntCharMap();
		final FastIntCharMap map = new FastIntCharMap(16);
		TestContext.assertTrue((map.get(7) == '\0'));
		TestContext.assertTrue((map.get(7, fallback) == fallback));
		TestContext.assertFalse(map.containsKey(7));
		TestContext.assertFalse(map.containsValue('A'));
		TestContext.assertTrue((map.remove(7) == '\0'));
		TestContext.assertTrue((map.remove(7, fallback) == fallback));
		TestContext.assertFalse(map.removeKey(7));
		TestContext.assertFalse(map.removeEntry(7, 'A'));
		TestContext.assertFalse(map.removeAllKeys((int[]) null));
		TestContext.assertFalse(map.removeAllKeys(emptyKeys));
		TestContext.assertFalse(map.removeAllKeys((FastIntSet) null));
		TestContext.assertFalse(map.removeAllKeys(emptySet));
		TestContext.assertFalse(map.retainAllKeys((FastIntSet) null));
		TestContext.assertFalse(map.retainAllKeys(emptySet));
		TestContext.assertFalse(map.putAll((FastIntCharMap) null));
		TestContext.assertFalse(map.putAll(emptySource));
		TestContext.assertFalse(map.putAll(emptyKeys, emptyVals));
		map.clear();
		TestContext.assertEquals(0, map.keys().length);
		TestContext.assertEquals(0, map.values().length);
		TestContext.assertTrue(map.keySet().isEmpty());
		TestContext.assertEquals(0, map.hashCode());
		TestContext.assertEquals("{}", map.toString());
		TestContext.assertFalse(map.keyIterator().hasNext());
		TestContext.assertFalse(map.valueIterator().hasNext());
		TestContext.assertTrue(map.putIfAbsent(1, 'A'));
		TestContext.assertTrue(map.putIfAbsent(Integer.MAX_VALUE, 'Z'));
		TestContext.assertTrue(map.removeKey(1));
		TestContext.assertTrue(map.removeKey(Integer.MAX_VALUE));
		TestContext.assertEquals(0, map.size());
		TestContext.assertFalse(map.containsKey(1));
		TestContext.assertFalse(map.containsValue('A'));
		TestContext.assertEquals(0, map.keys().length);
		TestContext.assertEquals(0, map.values().length);
		TestContext.assertEquals(0, map.hashCode());
		TestContext.assertEquals("{}", map.toString());
		TestContext.assertFalse(map.keyIterator().hasNext());
		TestContext.assertFalse(map.valueIterator().hasNext());
		final FastIntCharMap shared = (FastIntCharMap) map.shared();
		final FastIntCharMap unmodifiable = (FastIntCharMap) map.unmodifiable();
		TestContext.assertTrue((shared.get(7, fallback) == fallback));
		TestContext.assertTrue((unmodifiable.get(7, fallback) == fallback));
		TestContext.assertFalse(shared.containsKey(7));
		TestContext.assertFalse(unmodifiable.containsKey(7));
		TestContext.assertEquals(0, shared.keys().length);
		TestContext.assertEquals(0, unmodifiable.keys().length);
		TestContext.assertEquals("{}", shared.toString());
		TestContext.assertEquals("{}", unmodifiable.toString());
		TestContext.assertFalse(shared.keyIterator().hasNext());
		TestContext.assertFalse(unmodifiable.valueIterator().hasNext());
		shared.clear();
		TestContext.assertTrue(shared.putIfAbsent(7, 'A'));
		TestContext.assertTrue(unmodifiable.containsKey(7));
	}
	private void checkIntCharMapViews() {
		final FastIntCharMap source = new FastIntCharMap();
		source.put(Integer.MIN_VALUE, 'A');
		source.put(1, 'Z');
		final FastIntCharMap shared = (FastIntCharMap) source.shared();
		final FastIntCharMap unmodifiable = (FastIntCharMap) source.unmodifiable();
		final FastIntCharMap sharedUnmod = (FastIntCharMap) shared.unmodifiable();
		TestContext.assertTrue(source.equals(shared));
		TestContext.assertTrue(shared.equals(source));
		TestContext.assertTrue(source.equals(unmodifiable));
		TestContext.assertTrue(unmodifiable.equals(source));
		TestContext.assertTrue(shared.equals(sharedUnmod));
		TestContext.assertTrue(sharedUnmod.equals(shared));
		TestContext.assertEquals(source.hashCode(), shared.hashCode());
		TestContext.assertEquals(source.hashCode(), unmodifiable.hashCode());
		TestContext.assertEquals(source.hashCode(), sharedUnmod.hashCode());
		final FastIntIterator kit = (FastIntIterator) shared.keyIterator();
		boolean badRemove = false;
		try { kit.remove(); } catch(final IllegalStateException ex) { badRemove = true; }
		TestContext.assertTrue(badRemove);
		int kCount = 0;
		while(kit.hasNext()) { kit.next(); ++kCount; }
		TestContext.assertEquals(2, kCount);
		boolean noMore = false;
		try { kit.next(); } catch(final NoSuchElementException ex) { noMore = true; }
		TestContext.assertTrue(noMore);
		final FastCharIterator vit = (FastCharIterator) shared.valueIterator();
		TestContext.assertTrue(vit.hasNext());
		vit.next();
		shared.ensureCapacity(64);
		vit.remove();
		TestContext.assertEquals(1, unmodifiable.size());
		TestContext.assertEquals(1, sharedUnmod.size());
		badRemove = false;
		try { vit.remove(); } catch(final IllegalStateException ex) { badRemove = true; }
		TestContext.assertTrue(badRemove);
		final FastIntIterator ukit = (FastIntIterator) unmodifiable.keyIterator();
		ukit.next();
		int rejected = 0;
		try { ukit.remove(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		final FastCharIterator uvit = (FastCharIterator) sharedUnmod.valueIterator();
		uvit.next();
		try { uvit.remove(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.put(7, 'A'); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putIfAbsent(7, 'A'); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putAll(new int[] { 7 }, new char[] { 'A' }); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putAll(source); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.remove(Integer.MIN_VALUE); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.remove(Integer.MIN_VALUE, '!'); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeKey(Integer.MIN_VALUE); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeEntry(Integer.MIN_VALUE, 'A'); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeAllKeys(new int[] { Integer.MIN_VALUE }); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeAllKeys(new FastIntSet()); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.retainAllKeys(new FastIntSet()); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.clear(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.reset(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.trimToSize(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.ensureCapacity(64); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.shared(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		TestContext.assertEquals(18, rejected);
	}
	private void checkIntDoubleMapPutGetRemove() {
		final int k1 = Integer.MIN_VALUE;
		final int k2 = 0;
		final int k3 = 1;
		final int k4 = Integer.MAX_VALUE;
		final int kAbsent = 424242;
		final double v1 = 1.25d;
		final double v2 = -2.5d;
		final double v3 = 0.0d;
		final double v4 = 3.75d;
		final double fallback = 9.5d;
		final double wrongVal = 88.5d;
		final FastIntDoubleMap map = new FastIntDoubleMap();
		TestContext.assertEquals(0, map.size());
		TestContext.assertTrue(map.isEmpty());
		TestContext.assertTrue((Double.doubleToLongBits(map.get(k1)) == Double.doubleToLongBits(0.0d)));
		TestContext.assertTrue((Double.doubleToLongBits(map.get(k1, fallback)) == Double.doubleToLongBits(fallback)));
		TestContext.assertFalse(map.containsKey(k1));
		TestContext.assertTrue((Double.doubleToLongBits(map.put(k1, v1)) == Double.doubleToLongBits(0.0d)));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.containsKey(k1));
		TestContext.assertTrue(map.containsValue(v1));
		TestContext.assertTrue((Double.doubleToLongBits(map.get(k1)) == Double.doubleToLongBits(1.25d)));
		TestContext.assertTrue((Double.doubleToLongBits(map.put(k1, v2)) == Double.doubleToLongBits(1.25d)));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue((Double.doubleToLongBits(map.get(k1)) == Double.doubleToLongBits(-2.5d)));
		TestContext.assertFalse(map.putIfAbsent(k1, v1));
		TestContext.assertTrue((Double.doubleToLongBits(map.get(k1)) == Double.doubleToLongBits(-2.5d)));
		TestContext.assertTrue(map.putIfAbsent(k3, v3));
		TestContext.assertEquals(2, map.size());
		TestContext.assertTrue((Double.doubleToLongBits(map.get(k3, fallback)) == Double.doubleToLongBits(0.0d)));
		TestContext.assertFalse(map.containsValue(88.5d));
		final int[] bulkKeys = new int[] { k2, k4 };
		final double[] bulkVals = new double[] { v2, v4 };
		TestContext.assertTrue(map.putAll(bulkKeys, bulkVals));
		TestContext.assertEquals(4, map.size());
		TestContext.assertFalse(map.putAll(bulkKeys, bulkVals));
		TestContext.assertFalse(map.putAll((int[]) null, (double[]) null));
		boolean threw = false;
		try {
			map.putAll(new int[] { k1 }, bulkVals);
		}
		catch(final IllegalArgumentException ex) {
			threw = true;
		}
		TestContext.assertTrue(threw);
		final FastIntDoubleMap copy = new FastIntDoubleMap(map);
		TestContext.assertTrue(map.equals(copy));
		TestContext.assertTrue(copy.equals(map));
		TestContext.assertEquals(map.hashCode(), copy.hashCode());
		TestContext.assertFalse(map.putAll(copy));
		TestContext.assertFalse(map.putAll(map));
		final FastIntDoubleMap fromArrays = new FastIntDoubleMap(bulkKeys, bulkVals);
		TestContext.assertEquals(2, fromArrays.size());
		TestContext.assertTrue(fromArrays.containsKey(k2));
		TestContext.assertTrue(fromArrays.containsKey(k4));
		TestContext.assertEquals(4, map.keys().length);
		TestContext.assertEquals(4, map.values().length);
		final FastIntSet ks = map.keySet();
		TestContext.assertEquals(4, ks.size());
		TestContext.assertTrue(ks.contains(k1));
		TestContext.assertTrue(ks.contains(k4));
		TestContext.assertTrue(map.toString().indexOf('=') >= 0);
		try {
			final FastIntDoubleMap cloned = (FastIntDoubleMap) map.clone();
			TestContext.assertTrue(map.equals(cloned));
			cloned.put(kAbsent, v1);
			TestContext.assertFalse(map.containsKey(kAbsent));
		}
		catch(final UnsupportedOperationException j2meExpected) {}
		catch(final CloneNotSupportedException ex) {
			TestContext.assertTrue(false);
		}
		TestContext.assertFalse(map.removeEntry(k1, wrongVal));
		TestContext.assertTrue(map.removeEntry(k1, v2));
		TestContext.assertFalse(map.containsKey(k1));
		TestContext.assertTrue((Double.doubleToLongBits(map.remove(kAbsent, fallback)) == Double.doubleToLongBits(fallback)));
		TestContext.assertTrue((Double.doubleToLongBits(map.remove(k3)) == Double.doubleToLongBits(0.0d)));
		TestContext.assertTrue(map.removeKey(k2));
		TestContext.assertFalse(map.removeKey(kAbsent));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.containsKey(k4));
		TestContext.assertTrue((Double.doubleToLongBits(map.put(k1, v1)) == Double.doubleToLongBits(0.0d)));
		TestContext.assertTrue(map.removeAllKeys(new int[] { k1 }));
		final FastIntSet remSet = new FastIntSet(new int[] { k4 });
		TestContext.assertTrue(map.putIfAbsent(k2, v2));
		TestContext.assertTrue(map.retainAllKeys(remSet));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.removeAllKeys(remSet));
		TestContext.assertEquals(0, map.size());
	}
	private void checkIntDoubleMapChurn() {
		final FastIntDoubleMap map = new FastIntDoubleMap(16);
		for(int round = 0; round < 256; ++round) {
			for(int i = 0; i < 8; ++i) {
				final int key = (round * 13 + i);
				final double val = (round * 1.5d + i + 0.25d);
				TestContext.assertTrue(map.putIfAbsent(key, val));
			}
			for(int i = 0; i < 8; ++i) {
				final int key = (round * 13 + i);
				TestContext.assertTrue(map.removeKey(key));
			}
			TestContext.assertFalse(map.containsKey((round * 13 + 31)));
		}
		TestContext.assertEquals(0, map.size());
		final int keep = 987654321;
		for(int i = 0; i < 50; ++i) {
			map.put(keep, 1.25d);
		}
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.removeKey(keep));
		TestContext.assertTrue(map.putIfAbsent(7, 1.25d));
		TestContext.assertTrue(map.putIfAbsent(9, -2.5d));
		final FastIntIterator it = (FastIntIterator) map.keyIterator();
		TestContext.assertTrue(it.hasNext());
		final int returned = it.next();
		map.ensureCapacity(128);
		final int sizeBeforeRemove = map.size();
		it.remove();
		TestContext.assertFalse(map.containsKey(returned));
		TestContext.assertEquals(sizeBeforeRemove - 1, map.size());
		map.trimToSize();
		TestContext.assertEquals(1, map.size());
	}
	private void checkIntDoubleMapEmptyFastPaths() {
		final double fallback = 9.5d;
		final int[] emptyKeys = new int[0];
		final double[] emptyVals = new double[0];
		final FastIntSet emptySet = new FastIntSet();
		final FastIntDoubleMap emptySource = new FastIntDoubleMap();
		final FastIntDoubleMap map = new FastIntDoubleMap(16);
		TestContext.assertTrue((Double.doubleToLongBits(map.get(7)) == Double.doubleToLongBits(0.0d)));
		TestContext.assertTrue((Double.doubleToLongBits(map.get(7, fallback)) == Double.doubleToLongBits(fallback)));
		TestContext.assertFalse(map.containsKey(7));
		TestContext.assertFalse(map.containsValue(1.25d));
		TestContext.assertTrue((Double.doubleToLongBits(map.remove(7)) == Double.doubleToLongBits(0.0d)));
		TestContext.assertTrue((Double.doubleToLongBits(map.remove(7, fallback)) == Double.doubleToLongBits(fallback)));
		TestContext.assertFalse(map.removeKey(7));
		TestContext.assertFalse(map.removeEntry(7, 1.25d));
		TestContext.assertFalse(map.removeAllKeys((int[]) null));
		TestContext.assertFalse(map.removeAllKeys(emptyKeys));
		TestContext.assertFalse(map.removeAllKeys((FastIntSet) null));
		TestContext.assertFalse(map.removeAllKeys(emptySet));
		TestContext.assertFalse(map.retainAllKeys((FastIntSet) null));
		TestContext.assertFalse(map.retainAllKeys(emptySet));
		TestContext.assertFalse(map.putAll((FastIntDoubleMap) null));
		TestContext.assertFalse(map.putAll(emptySource));
		TestContext.assertFalse(map.putAll(emptyKeys, emptyVals));
		map.clear();
		TestContext.assertEquals(0, map.keys().length);
		TestContext.assertEquals(0, map.values().length);
		TestContext.assertTrue(map.keySet().isEmpty());
		TestContext.assertEquals(0, map.hashCode());
		TestContext.assertEquals("{}", map.toString());
		TestContext.assertFalse(map.keyIterator().hasNext());
		TestContext.assertFalse(map.valueIterator().hasNext());
		TestContext.assertTrue(map.putIfAbsent(1, 1.25d));
		TestContext.assertTrue(map.putIfAbsent(Integer.MAX_VALUE, -2.5d));
		TestContext.assertTrue(map.removeKey(1));
		TestContext.assertTrue(map.removeKey(Integer.MAX_VALUE));
		TestContext.assertEquals(0, map.size());
		TestContext.assertFalse(map.containsKey(1));
		TestContext.assertFalse(map.containsValue(1.25d));
		TestContext.assertEquals(0, map.keys().length);
		TestContext.assertEquals(0, map.values().length);
		TestContext.assertEquals(0, map.hashCode());
		TestContext.assertEquals("{}", map.toString());
		TestContext.assertFalse(map.keyIterator().hasNext());
		TestContext.assertFalse(map.valueIterator().hasNext());
		final FastIntDoubleMap shared = (FastIntDoubleMap) map.shared();
		final FastIntDoubleMap unmodifiable = (FastIntDoubleMap) map.unmodifiable();
		TestContext.assertTrue((Double.doubleToLongBits(shared.get(7, fallback)) == Double.doubleToLongBits(fallback)));
		TestContext.assertTrue((Double.doubleToLongBits(unmodifiable.get(7, fallback)) == Double.doubleToLongBits(fallback)));
		TestContext.assertFalse(shared.containsKey(7));
		TestContext.assertFalse(unmodifiable.containsKey(7));
		TestContext.assertEquals(0, shared.keys().length);
		TestContext.assertEquals(0, unmodifiable.keys().length);
		TestContext.assertEquals("{}", shared.toString());
		TestContext.assertEquals("{}", unmodifiable.toString());
		TestContext.assertFalse(shared.keyIterator().hasNext());
		TestContext.assertFalse(unmodifiable.valueIterator().hasNext());
		shared.clear();
		TestContext.assertTrue(shared.putIfAbsent(7, 1.25d));
		TestContext.assertTrue(unmodifiable.containsKey(7));
	}
	private void checkIntDoubleMapViews() {
		final FastIntDoubleMap source = new FastIntDoubleMap();
		source.put(Integer.MIN_VALUE, 1.25d);
		source.put(1, -2.5d);
		final FastIntDoubleMap shared = (FastIntDoubleMap) source.shared();
		final FastIntDoubleMap unmodifiable = (FastIntDoubleMap) source.unmodifiable();
		final FastIntDoubleMap sharedUnmod = (FastIntDoubleMap) shared.unmodifiable();
		TestContext.assertTrue(source.equals(shared));
		TestContext.assertTrue(shared.equals(source));
		TestContext.assertTrue(source.equals(unmodifiable));
		TestContext.assertTrue(unmodifiable.equals(source));
		TestContext.assertTrue(shared.equals(sharedUnmod));
		TestContext.assertTrue(sharedUnmod.equals(shared));
		TestContext.assertEquals(source.hashCode(), shared.hashCode());
		TestContext.assertEquals(source.hashCode(), unmodifiable.hashCode());
		TestContext.assertEquals(source.hashCode(), sharedUnmod.hashCode());
		final FastIntIterator kit = (FastIntIterator) shared.keyIterator();
		boolean badRemove = false;
		try { kit.remove(); } catch(final IllegalStateException ex) { badRemove = true; }
		TestContext.assertTrue(badRemove);
		int kCount = 0;
		while(kit.hasNext()) { kit.next(); ++kCount; }
		TestContext.assertEquals(2, kCount);
		boolean noMore = false;
		try { kit.next(); } catch(final NoSuchElementException ex) { noMore = true; }
		TestContext.assertTrue(noMore);
		final FastDoubleIterator vit = (FastDoubleIterator) shared.valueIterator();
		TestContext.assertTrue(vit.hasNext());
		vit.next();
		shared.ensureCapacity(64);
		vit.remove();
		TestContext.assertEquals(1, unmodifiable.size());
		TestContext.assertEquals(1, sharedUnmod.size());
		badRemove = false;
		try { vit.remove(); } catch(final IllegalStateException ex) { badRemove = true; }
		TestContext.assertTrue(badRemove);
		final FastIntIterator ukit = (FastIntIterator) unmodifiable.keyIterator();
		ukit.next();
		int rejected = 0;
		try { ukit.remove(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		final FastDoubleIterator uvit = (FastDoubleIterator) sharedUnmod.valueIterator();
		uvit.next();
		try { uvit.remove(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.put(7, 1.25d); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putIfAbsent(7, 1.25d); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putAll(new int[] { 7 }, new double[] { 1.25d }); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putAll(source); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.remove(Integer.MIN_VALUE); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.remove(Integer.MIN_VALUE, 9.5d); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeKey(Integer.MIN_VALUE); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeEntry(Integer.MIN_VALUE, 1.25d); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeAllKeys(new int[] { Integer.MIN_VALUE }); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeAllKeys(new FastIntSet()); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.retainAllKeys(new FastIntSet()); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.clear(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.reset(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.trimToSize(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.ensureCapacity(64); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.shared(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		TestContext.assertEquals(18, rejected);
	}
	private void checkIntFloatMapPutGetRemove() {
		final int k1 = Integer.MIN_VALUE;
		final int k2 = 0;
		final int k3 = 1;
		final int k4 = Integer.MAX_VALUE;
		final int kAbsent = 424242;
		final float v1 = 1.25f;
		final float v2 = -2.5f;
		final float v3 = 0.0f;
		final float v4 = 3.75f;
		final float fallback = 9.5f;
		final float wrongVal = 88.5f;
		final FastIntFloatMap map = new FastIntFloatMap();
		TestContext.assertEquals(0, map.size());
		TestContext.assertTrue(map.isEmpty());
		TestContext.assertTrue((Float.floatToIntBits(map.get(k1)) == Float.floatToIntBits(0.0f)));
		TestContext.assertTrue((Float.floatToIntBits(map.get(k1, fallback)) == Float.floatToIntBits(fallback)));
		TestContext.assertFalse(map.containsKey(k1));
		TestContext.assertTrue((Float.floatToIntBits(map.put(k1, v1)) == Float.floatToIntBits(0.0f)));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.containsKey(k1));
		TestContext.assertTrue(map.containsValue(v1));
		TestContext.assertTrue((Float.floatToIntBits(map.get(k1)) == Float.floatToIntBits(1.25f)));
		TestContext.assertTrue((Float.floatToIntBits(map.put(k1, v2)) == Float.floatToIntBits(1.25f)));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue((Float.floatToIntBits(map.get(k1)) == Float.floatToIntBits(-2.5f)));
		TestContext.assertFalse(map.putIfAbsent(k1, v1));
		TestContext.assertTrue((Float.floatToIntBits(map.get(k1)) == Float.floatToIntBits(-2.5f)));
		TestContext.assertTrue(map.putIfAbsent(k3, v3));
		TestContext.assertEquals(2, map.size());
		TestContext.assertTrue((Float.floatToIntBits(map.get(k3, fallback)) == Float.floatToIntBits(0.0f)));
		TestContext.assertFalse(map.containsValue(88.5f));
		final int[] bulkKeys = new int[] { k2, k4 };
		final float[] bulkVals = new float[] { v2, v4 };
		TestContext.assertTrue(map.putAll(bulkKeys, bulkVals));
		TestContext.assertEquals(4, map.size());
		TestContext.assertFalse(map.putAll(bulkKeys, bulkVals));
		TestContext.assertFalse(map.putAll((int[]) null, (float[]) null));
		boolean threw = false;
		try {
			map.putAll(new int[] { k1 }, bulkVals);
		}
		catch(final IllegalArgumentException ex) {
			threw = true;
		}
		TestContext.assertTrue(threw);
		final FastIntFloatMap copy = new FastIntFloatMap(map);
		TestContext.assertTrue(map.equals(copy));
		TestContext.assertTrue(copy.equals(map));
		TestContext.assertEquals(map.hashCode(), copy.hashCode());
		TestContext.assertFalse(map.putAll(copy));
		TestContext.assertFalse(map.putAll(map));
		final FastIntFloatMap fromArrays = new FastIntFloatMap(bulkKeys, bulkVals);
		TestContext.assertEquals(2, fromArrays.size());
		TestContext.assertTrue(fromArrays.containsKey(k2));
		TestContext.assertTrue(fromArrays.containsKey(k4));
		TestContext.assertEquals(4, map.keys().length);
		TestContext.assertEquals(4, map.values().length);
		final FastIntSet ks = map.keySet();
		TestContext.assertEquals(4, ks.size());
		TestContext.assertTrue(ks.contains(k1));
		TestContext.assertTrue(ks.contains(k4));
		TestContext.assertTrue(map.toString().indexOf('=') >= 0);
		try {
			final FastIntFloatMap cloned = (FastIntFloatMap) map.clone();
			TestContext.assertTrue(map.equals(cloned));
			cloned.put(kAbsent, v1);
			TestContext.assertFalse(map.containsKey(kAbsent));
		}
		catch(final UnsupportedOperationException j2meExpected) {}
		catch(final CloneNotSupportedException ex) {
			TestContext.assertTrue(false);
		}
		TestContext.assertFalse(map.removeEntry(k1, wrongVal));
		TestContext.assertTrue(map.removeEntry(k1, v2));
		TestContext.assertFalse(map.containsKey(k1));
		TestContext.assertTrue((Float.floatToIntBits(map.remove(kAbsent, fallback)) == Float.floatToIntBits(fallback)));
		TestContext.assertTrue((Float.floatToIntBits(map.remove(k3)) == Float.floatToIntBits(0.0f)));
		TestContext.assertTrue(map.removeKey(k2));
		TestContext.assertFalse(map.removeKey(kAbsent));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.containsKey(k4));
		TestContext.assertTrue((Float.floatToIntBits(map.put(k1, v1)) == Float.floatToIntBits(0.0f)));
		TestContext.assertTrue(map.removeAllKeys(new int[] { k1 }));
		final FastIntSet remSet = new FastIntSet(new int[] { k4 });
		TestContext.assertTrue(map.putIfAbsent(k2, v2));
		TestContext.assertTrue(map.retainAllKeys(remSet));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.removeAllKeys(remSet));
		TestContext.assertEquals(0, map.size());
	}
	private void checkIntFloatMapChurn() {
		final FastIntFloatMap map = new FastIntFloatMap(16);
		for(int round = 0; round < 256; ++round) {
			for(int i = 0; i < 8; ++i) {
				final int key = (round * 13 + i);
				final float val = (round * 1.5f + i + 0.25f);
				TestContext.assertTrue(map.putIfAbsent(key, val));
			}
			for(int i = 0; i < 8; ++i) {
				final int key = (round * 13 + i);
				TestContext.assertTrue(map.removeKey(key));
			}
			TestContext.assertFalse(map.containsKey((round * 13 + 31)));
		}
		TestContext.assertEquals(0, map.size());
		final int keep = 987654321;
		for(int i = 0; i < 50; ++i) {
			map.put(keep, 1.25f);
		}
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.removeKey(keep));
		TestContext.assertTrue(map.putIfAbsent(7, 1.25f));
		TestContext.assertTrue(map.putIfAbsent(9, -2.5f));
		final FastIntIterator it = (FastIntIterator) map.keyIterator();
		TestContext.assertTrue(it.hasNext());
		final int returned = it.next();
		map.ensureCapacity(128);
		final int sizeBeforeRemove = map.size();
		it.remove();
		TestContext.assertFalse(map.containsKey(returned));
		TestContext.assertEquals(sizeBeforeRemove - 1, map.size());
		map.trimToSize();
		TestContext.assertEquals(1, map.size());
	}
	private void checkIntFloatMapEmptyFastPaths() {
		final float fallback = 9.5f;
		final int[] emptyKeys = new int[0];
		final float[] emptyVals = new float[0];
		final FastIntSet emptySet = new FastIntSet();
		final FastIntFloatMap emptySource = new FastIntFloatMap();
		final FastIntFloatMap map = new FastIntFloatMap(16);
		TestContext.assertTrue((Float.floatToIntBits(map.get(7)) == Float.floatToIntBits(0.0f)));
		TestContext.assertTrue((Float.floatToIntBits(map.get(7, fallback)) == Float.floatToIntBits(fallback)));
		TestContext.assertFalse(map.containsKey(7));
		TestContext.assertFalse(map.containsValue(1.25f));
		TestContext.assertTrue((Float.floatToIntBits(map.remove(7)) == Float.floatToIntBits(0.0f)));
		TestContext.assertTrue((Float.floatToIntBits(map.remove(7, fallback)) == Float.floatToIntBits(fallback)));
		TestContext.assertFalse(map.removeKey(7));
		TestContext.assertFalse(map.removeEntry(7, 1.25f));
		TestContext.assertFalse(map.removeAllKeys((int[]) null));
		TestContext.assertFalse(map.removeAllKeys(emptyKeys));
		TestContext.assertFalse(map.removeAllKeys((FastIntSet) null));
		TestContext.assertFalse(map.removeAllKeys(emptySet));
		TestContext.assertFalse(map.retainAllKeys((FastIntSet) null));
		TestContext.assertFalse(map.retainAllKeys(emptySet));
		TestContext.assertFalse(map.putAll((FastIntFloatMap) null));
		TestContext.assertFalse(map.putAll(emptySource));
		TestContext.assertFalse(map.putAll(emptyKeys, emptyVals));
		map.clear();
		TestContext.assertEquals(0, map.keys().length);
		TestContext.assertEquals(0, map.values().length);
		TestContext.assertTrue(map.keySet().isEmpty());
		TestContext.assertEquals(0, map.hashCode());
		TestContext.assertEquals("{}", map.toString());
		TestContext.assertFalse(map.keyIterator().hasNext());
		TestContext.assertFalse(map.valueIterator().hasNext());
		TestContext.assertTrue(map.putIfAbsent(1, 1.25f));
		TestContext.assertTrue(map.putIfAbsent(Integer.MAX_VALUE, -2.5f));
		TestContext.assertTrue(map.removeKey(1));
		TestContext.assertTrue(map.removeKey(Integer.MAX_VALUE));
		TestContext.assertEquals(0, map.size());
		TestContext.assertFalse(map.containsKey(1));
		TestContext.assertFalse(map.containsValue(1.25f));
		TestContext.assertEquals(0, map.keys().length);
		TestContext.assertEquals(0, map.values().length);
		TestContext.assertEquals(0, map.hashCode());
		TestContext.assertEquals("{}", map.toString());
		TestContext.assertFalse(map.keyIterator().hasNext());
		TestContext.assertFalse(map.valueIterator().hasNext());
		final FastIntFloatMap shared = (FastIntFloatMap) map.shared();
		final FastIntFloatMap unmodifiable = (FastIntFloatMap) map.unmodifiable();
		TestContext.assertTrue((Float.floatToIntBits(shared.get(7, fallback)) == Float.floatToIntBits(fallback)));
		TestContext.assertTrue((Float.floatToIntBits(unmodifiable.get(7, fallback)) == Float.floatToIntBits(fallback)));
		TestContext.assertFalse(shared.containsKey(7));
		TestContext.assertFalse(unmodifiable.containsKey(7));
		TestContext.assertEquals(0, shared.keys().length);
		TestContext.assertEquals(0, unmodifiable.keys().length);
		TestContext.assertEquals("{}", shared.toString());
		TestContext.assertEquals("{}", unmodifiable.toString());
		TestContext.assertFalse(shared.keyIterator().hasNext());
		TestContext.assertFalse(unmodifiable.valueIterator().hasNext());
		shared.clear();
		TestContext.assertTrue(shared.putIfAbsent(7, 1.25f));
		TestContext.assertTrue(unmodifiable.containsKey(7));
	}
	private void checkIntFloatMapViews() {
		final FastIntFloatMap source = new FastIntFloatMap();
		source.put(Integer.MIN_VALUE, 1.25f);
		source.put(1, -2.5f);
		final FastIntFloatMap shared = (FastIntFloatMap) source.shared();
		final FastIntFloatMap unmodifiable = (FastIntFloatMap) source.unmodifiable();
		final FastIntFloatMap sharedUnmod = (FastIntFloatMap) shared.unmodifiable();
		TestContext.assertTrue(source.equals(shared));
		TestContext.assertTrue(shared.equals(source));
		TestContext.assertTrue(source.equals(unmodifiable));
		TestContext.assertTrue(unmodifiable.equals(source));
		TestContext.assertTrue(shared.equals(sharedUnmod));
		TestContext.assertTrue(sharedUnmod.equals(shared));
		TestContext.assertEquals(source.hashCode(), shared.hashCode());
		TestContext.assertEquals(source.hashCode(), unmodifiable.hashCode());
		TestContext.assertEquals(source.hashCode(), sharedUnmod.hashCode());
		final FastIntIterator kit = (FastIntIterator) shared.keyIterator();
		boolean badRemove = false;
		try { kit.remove(); } catch(final IllegalStateException ex) { badRemove = true; }
		TestContext.assertTrue(badRemove);
		int kCount = 0;
		while(kit.hasNext()) { kit.next(); ++kCount; }
		TestContext.assertEquals(2, kCount);
		boolean noMore = false;
		try { kit.next(); } catch(final NoSuchElementException ex) { noMore = true; }
		TestContext.assertTrue(noMore);
		final FastFloatIterator vit = (FastFloatIterator) shared.valueIterator();
		TestContext.assertTrue(vit.hasNext());
		vit.next();
		shared.ensureCapacity(64);
		vit.remove();
		TestContext.assertEquals(1, unmodifiable.size());
		TestContext.assertEquals(1, sharedUnmod.size());
		badRemove = false;
		try { vit.remove(); } catch(final IllegalStateException ex) { badRemove = true; }
		TestContext.assertTrue(badRemove);
		final FastIntIterator ukit = (FastIntIterator) unmodifiable.keyIterator();
		ukit.next();
		int rejected = 0;
		try { ukit.remove(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		final FastFloatIterator uvit = (FastFloatIterator) sharedUnmod.valueIterator();
		uvit.next();
		try { uvit.remove(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.put(7, 1.25f); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putIfAbsent(7, 1.25f); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putAll(new int[] { 7 }, new float[] { 1.25f }); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putAll(source); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.remove(Integer.MIN_VALUE); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.remove(Integer.MIN_VALUE, 9.5f); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeKey(Integer.MIN_VALUE); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeEntry(Integer.MIN_VALUE, 1.25f); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeAllKeys(new int[] { Integer.MIN_VALUE }); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeAllKeys(new FastIntSet()); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.retainAllKeys(new FastIntSet()); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.clear(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.reset(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.trimToSize(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.ensureCapacity(64); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.shared(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		TestContext.assertEquals(18, rejected);
	}
	private void checkIntIntMapPutGetRemove() {
		final int k1 = Integer.MIN_VALUE;
		final int k2 = 0;
		final int k3 = 1;
		final int k4 = Integer.MAX_VALUE;
		final int kAbsent = 424242;
		final int v1 = 11;
		final int v2 = -22;
		final int v3 = 0;
		final int v4 = 333;
		final int fallback = 99;
		final int wrongVal = 555;
		final FastIntIntMap map = new FastIntIntMap();
		TestContext.assertEquals(0, map.size());
		TestContext.assertTrue(map.isEmpty());
		TestContext.assertTrue((map.get(k1) == 0));
		TestContext.assertTrue((map.get(k1, fallback) == fallback));
		TestContext.assertFalse(map.containsKey(k1));
		TestContext.assertTrue((map.put(k1, v1) == 0));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.containsKey(k1));
		TestContext.assertTrue(map.containsValue(v1));
		TestContext.assertTrue((map.get(k1) == 11));
		TestContext.assertTrue((map.put(k1, v2) == 11));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue((map.get(k1) == -22));
		TestContext.assertFalse(map.putIfAbsent(k1, v1));
		TestContext.assertTrue((map.get(k1) == -22));
		TestContext.assertTrue(map.putIfAbsent(k3, v3));
		TestContext.assertEquals(2, map.size());
		TestContext.assertTrue((map.get(k3, fallback) == 0));
		TestContext.assertFalse(map.containsValue(555));
		final int[] bulkKeys = new int[] { k2, k4 };
		final int[] bulkVals = new int[] { v2, v4 };
		TestContext.assertTrue(map.putAll(bulkKeys, bulkVals));
		TestContext.assertEquals(4, map.size());
		TestContext.assertFalse(map.putAll(bulkKeys, bulkVals));
		TestContext.assertFalse(map.putAll((int[]) null, (int[]) null));
		boolean threw = false;
		try {
			map.putAll(new int[] { k1 }, bulkVals);
		}
		catch(final IllegalArgumentException ex) {
			threw = true;
		}
		TestContext.assertTrue(threw);
		final FastIntIntMap copy = new FastIntIntMap(map);
		TestContext.assertTrue(map.equals(copy));
		TestContext.assertTrue(copy.equals(map));
		TestContext.assertEquals(map.hashCode(), copy.hashCode());
		TestContext.assertFalse(map.putAll(copy));
		TestContext.assertFalse(map.putAll(map));
		final FastIntIntMap fromArrays = new FastIntIntMap(bulkKeys, bulkVals);
		TestContext.assertEquals(2, fromArrays.size());
		TestContext.assertTrue(fromArrays.containsKey(k2));
		TestContext.assertTrue(fromArrays.containsKey(k4));
		TestContext.assertEquals(4, map.keys().length);
		TestContext.assertEquals(4, map.values().length);
		final FastIntSet ks = map.keySet();
		TestContext.assertEquals(4, ks.size());
		TestContext.assertTrue(ks.contains(k1));
		TestContext.assertTrue(ks.contains(k4));
		TestContext.assertTrue(map.toString().indexOf('=') >= 0);
		try {
			final FastIntIntMap cloned = (FastIntIntMap) map.clone();
			TestContext.assertTrue(map.equals(cloned));
			cloned.put(kAbsent, v1);
			TestContext.assertFalse(map.containsKey(kAbsent));
		}
		catch(final UnsupportedOperationException j2meExpected) {}
		catch(final CloneNotSupportedException ex) {
			TestContext.assertTrue(false);
		}
		TestContext.assertFalse(map.removeEntry(k1, wrongVal));
		TestContext.assertTrue(map.removeEntry(k1, v2));
		TestContext.assertFalse(map.containsKey(k1));
		TestContext.assertTrue((map.remove(kAbsent, fallback) == fallback));
		TestContext.assertTrue((map.remove(k3) == 0));
		TestContext.assertTrue(map.removeKey(k2));
		TestContext.assertFalse(map.removeKey(kAbsent));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.containsKey(k4));
		TestContext.assertTrue((map.put(k1, v1) == 0));
		TestContext.assertTrue(map.removeAllKeys(new int[] { k1 }));
		final FastIntSet remSet = new FastIntSet(new int[] { k4 });
		TestContext.assertTrue(map.putIfAbsent(k2, v2));
		TestContext.assertTrue(map.retainAllKeys(remSet));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.removeAllKeys(remSet));
		TestContext.assertEquals(0, map.size());
	}
	private void checkIntIntMapChurn() {
		final FastIntIntMap map = new FastIntIntMap(16);
		for(int round = 0; round < 256; ++round) {
			for(int i = 0; i < 8; ++i) {
				final int key = (round * 13 + i);
				final int val = (round * 7 + i);
				TestContext.assertTrue(map.putIfAbsent(key, val));
			}
			for(int i = 0; i < 8; ++i) {
				final int key = (round * 13 + i);
				TestContext.assertTrue(map.removeKey(key));
			}
			TestContext.assertFalse(map.containsKey((round * 13 + 31)));
		}
		TestContext.assertEquals(0, map.size());
		final int keep = 987654321;
		for(int i = 0; i < 50; ++i) {
			map.put(keep, 11);
		}
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.removeKey(keep));
		TestContext.assertTrue(map.putIfAbsent(7, 11));
		TestContext.assertTrue(map.putIfAbsent(9, -22));
		final FastIntIterator it = (FastIntIterator) map.keyIterator();
		TestContext.assertTrue(it.hasNext());
		final int returned = it.next();
		map.ensureCapacity(128);
		final int sizeBeforeRemove = map.size();
		it.remove();
		TestContext.assertFalse(map.containsKey(returned));
		TestContext.assertEquals(sizeBeforeRemove - 1, map.size());
		map.trimToSize();
		TestContext.assertEquals(1, map.size());
	}
	private void checkIntIntMapEmptyFastPaths() {
		final int fallback = 99;
		final int[] emptyKeys = new int[0];
		final int[] emptyVals = new int[0];
		final FastIntSet emptySet = new FastIntSet();
		final FastIntIntMap emptySource = new FastIntIntMap();
		final FastIntIntMap map = new FastIntIntMap(16);
		TestContext.assertTrue((map.get(7) == 0));
		TestContext.assertTrue((map.get(7, fallback) == fallback));
		TestContext.assertFalse(map.containsKey(7));
		TestContext.assertFalse(map.containsValue(11));
		TestContext.assertTrue((map.remove(7) == 0));
		TestContext.assertTrue((map.remove(7, fallback) == fallback));
		TestContext.assertFalse(map.removeKey(7));
		TestContext.assertFalse(map.removeEntry(7, 11));
		TestContext.assertFalse(map.removeAllKeys((int[]) null));
		TestContext.assertFalse(map.removeAllKeys(emptyKeys));
		TestContext.assertFalse(map.removeAllKeys((FastIntSet) null));
		TestContext.assertFalse(map.removeAllKeys(emptySet));
		TestContext.assertFalse(map.retainAllKeys((FastIntSet) null));
		TestContext.assertFalse(map.retainAllKeys(emptySet));
		TestContext.assertFalse(map.putAll((FastIntIntMap) null));
		TestContext.assertFalse(map.putAll(emptySource));
		TestContext.assertFalse(map.putAll(emptyKeys, emptyVals));
		map.clear();
		TestContext.assertEquals(0, map.keys().length);
		TestContext.assertEquals(0, map.values().length);
		TestContext.assertTrue(map.keySet().isEmpty());
		TestContext.assertEquals(0, map.hashCode());
		TestContext.assertEquals("{}", map.toString());
		TestContext.assertFalse(map.keyIterator().hasNext());
		TestContext.assertFalse(map.valueIterator().hasNext());
		TestContext.assertTrue(map.putIfAbsent(1, 11));
		TestContext.assertTrue(map.putIfAbsent(Integer.MAX_VALUE, -22));
		TestContext.assertTrue(map.removeKey(1));
		TestContext.assertTrue(map.removeKey(Integer.MAX_VALUE));
		TestContext.assertEquals(0, map.size());
		TestContext.assertFalse(map.containsKey(1));
		TestContext.assertFalse(map.containsValue(11));
		TestContext.assertEquals(0, map.keys().length);
		TestContext.assertEquals(0, map.values().length);
		TestContext.assertEquals(0, map.hashCode());
		TestContext.assertEquals("{}", map.toString());
		TestContext.assertFalse(map.keyIterator().hasNext());
		TestContext.assertFalse(map.valueIterator().hasNext());
		final FastIntIntMap shared = (FastIntIntMap) map.shared();
		final FastIntIntMap unmodifiable = (FastIntIntMap) map.unmodifiable();
		TestContext.assertTrue((shared.get(7, fallback) == fallback));
		TestContext.assertTrue((unmodifiable.get(7, fallback) == fallback));
		TestContext.assertFalse(shared.containsKey(7));
		TestContext.assertFalse(unmodifiable.containsKey(7));
		TestContext.assertEquals(0, shared.keys().length);
		TestContext.assertEquals(0, unmodifiable.keys().length);
		TestContext.assertEquals("{}", shared.toString());
		TestContext.assertEquals("{}", unmodifiable.toString());
		TestContext.assertFalse(shared.keyIterator().hasNext());
		TestContext.assertFalse(unmodifiable.valueIterator().hasNext());
		shared.clear();
		TestContext.assertTrue(shared.putIfAbsent(7, 11));
		TestContext.assertTrue(unmodifiable.containsKey(7));
	}
	private void checkIntIntMapViews() {
		final FastIntIntMap source = new FastIntIntMap();
		source.put(Integer.MIN_VALUE, 11);
		source.put(1, -22);
		final FastIntIntMap shared = (FastIntIntMap) source.shared();
		final FastIntIntMap unmodifiable = (FastIntIntMap) source.unmodifiable();
		final FastIntIntMap sharedUnmod = (FastIntIntMap) shared.unmodifiable();
		TestContext.assertTrue(source.equals(shared));
		TestContext.assertTrue(shared.equals(source));
		TestContext.assertTrue(source.equals(unmodifiable));
		TestContext.assertTrue(unmodifiable.equals(source));
		TestContext.assertTrue(shared.equals(sharedUnmod));
		TestContext.assertTrue(sharedUnmod.equals(shared));
		TestContext.assertEquals(source.hashCode(), shared.hashCode());
		TestContext.assertEquals(source.hashCode(), unmodifiable.hashCode());
		TestContext.assertEquals(source.hashCode(), sharedUnmod.hashCode());
		final FastIntIterator kit = (FastIntIterator) shared.keyIterator();
		boolean badRemove = false;
		try { kit.remove(); } catch(final IllegalStateException ex) { badRemove = true; }
		TestContext.assertTrue(badRemove);
		int kCount = 0;
		while(kit.hasNext()) { kit.next(); ++kCount; }
		TestContext.assertEquals(2, kCount);
		boolean noMore = false;
		try { kit.next(); } catch(final NoSuchElementException ex) { noMore = true; }
		TestContext.assertTrue(noMore);
		final FastIntIterator vit = (FastIntIterator) shared.valueIterator();
		TestContext.assertTrue(vit.hasNext());
		vit.next();
		shared.ensureCapacity(64);
		vit.remove();
		TestContext.assertEquals(1, unmodifiable.size());
		TestContext.assertEquals(1, sharedUnmod.size());
		badRemove = false;
		try { vit.remove(); } catch(final IllegalStateException ex) { badRemove = true; }
		TestContext.assertTrue(badRemove);
		final FastIntIterator ukit = (FastIntIterator) unmodifiable.keyIterator();
		ukit.next();
		int rejected = 0;
		try { ukit.remove(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		final FastIntIterator uvit = (FastIntIterator) sharedUnmod.valueIterator();
		uvit.next();
		try { uvit.remove(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.put(7, 11); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putIfAbsent(7, 11); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putAll(new int[] { 7 }, new int[] { 11 }); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putAll(source); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.remove(Integer.MIN_VALUE); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.remove(Integer.MIN_VALUE, 99); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeKey(Integer.MIN_VALUE); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeEntry(Integer.MIN_VALUE, 11); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeAllKeys(new int[] { Integer.MIN_VALUE }); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeAllKeys(new FastIntSet()); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.retainAllKeys(new FastIntSet()); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.clear(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.reset(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.trimToSize(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.ensureCapacity(64); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.shared(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		TestContext.assertEquals(18, rejected);
	}
	private void checkIntLongMapPutGetRemove() {
		final int k1 = Integer.MIN_VALUE;
		final int k2 = 0;
		final int k3 = 1;
		final int k4 = Integer.MAX_VALUE;
		final int kAbsent = 424242;
		final long v1 = 11L;
		final long v2 = -22L;
		final long v3 = 0L;
		final long v4 = 0x200000001L;
		final long fallback = 99L;
		final long wrongVal = 555L;
		final FastIntLongMap map = new FastIntLongMap();
		TestContext.assertEquals(0, map.size());
		TestContext.assertTrue(map.isEmpty());
		TestContext.assertTrue((map.get(k1) == 0L));
		TestContext.assertTrue((map.get(k1, fallback) == fallback));
		TestContext.assertFalse(map.containsKey(k1));
		TestContext.assertTrue((map.put(k1, v1) == 0L));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.containsKey(k1));
		TestContext.assertTrue(map.containsValue(v1));
		TestContext.assertTrue((map.get(k1) == 11L));
		TestContext.assertTrue((map.put(k1, v2) == 11L));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue((map.get(k1) == -22L));
		TestContext.assertFalse(map.putIfAbsent(k1, v1));
		TestContext.assertTrue((map.get(k1) == -22L));
		TestContext.assertTrue(map.putIfAbsent(k3, v3));
		TestContext.assertEquals(2, map.size());
		TestContext.assertTrue((map.get(k3, fallback) == 0L));
		TestContext.assertFalse(map.containsValue(555L));
		final int[] bulkKeys = new int[] { k2, k4 };
		final long[] bulkVals = new long[] { v2, v4 };
		TestContext.assertTrue(map.putAll(bulkKeys, bulkVals));
		TestContext.assertEquals(4, map.size());
		TestContext.assertFalse(map.putAll(bulkKeys, bulkVals));
		TestContext.assertFalse(map.putAll((int[]) null, (long[]) null));
		boolean threw = false;
		try {
			map.putAll(new int[] { k1 }, bulkVals);
		}
		catch(final IllegalArgumentException ex) {
			threw = true;
		}
		TestContext.assertTrue(threw);
		final FastIntLongMap copy = new FastIntLongMap(map);
		TestContext.assertTrue(map.equals(copy));
		TestContext.assertTrue(copy.equals(map));
		TestContext.assertEquals(map.hashCode(), copy.hashCode());
		TestContext.assertFalse(map.putAll(copy));
		TestContext.assertFalse(map.putAll(map));
		final FastIntLongMap fromArrays = new FastIntLongMap(bulkKeys, bulkVals);
		TestContext.assertEquals(2, fromArrays.size());
		TestContext.assertTrue(fromArrays.containsKey(k2));
		TestContext.assertTrue(fromArrays.containsKey(k4));
		TestContext.assertEquals(4, map.keys().length);
		TestContext.assertEquals(4, map.values().length);
		final FastIntSet ks = map.keySet();
		TestContext.assertEquals(4, ks.size());
		TestContext.assertTrue(ks.contains(k1));
		TestContext.assertTrue(ks.contains(k4));
		TestContext.assertTrue(map.toString().indexOf('=') >= 0);
		try {
			final FastIntLongMap cloned = (FastIntLongMap) map.clone();
			TestContext.assertTrue(map.equals(cloned));
			cloned.put(kAbsent, v1);
			TestContext.assertFalse(map.containsKey(kAbsent));
		}
		catch(final UnsupportedOperationException j2meExpected) {}
		catch(final CloneNotSupportedException ex) {
			TestContext.assertTrue(false);
		}
		TestContext.assertFalse(map.removeEntry(k1, wrongVal));
		TestContext.assertTrue(map.removeEntry(k1, v2));
		TestContext.assertFalse(map.containsKey(k1));
		TestContext.assertTrue((map.remove(kAbsent, fallback) == fallback));
		TestContext.assertTrue((map.remove(k3) == 0L));
		TestContext.assertTrue(map.removeKey(k2));
		TestContext.assertFalse(map.removeKey(kAbsent));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.containsKey(k4));
		TestContext.assertTrue((map.put(k1, v1) == 0L));
		TestContext.assertTrue(map.removeAllKeys(new int[] { k1 }));
		final FastIntSet remSet = new FastIntSet(new int[] { k4 });
		TestContext.assertTrue(map.putIfAbsent(k2, v2));
		TestContext.assertTrue(map.retainAllKeys(remSet));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.removeAllKeys(remSet));
		TestContext.assertEquals(0, map.size());
	}
	private void checkIntLongMapChurn() {
		final FastIntLongMap map = new FastIntLongMap(16);
		for(int round = 0; round < 256; ++round) {
			for(int i = 0; i < 8; ++i) {
				final int key = (round * 13 + i);
				final long val = (round * 7L + i);
				TestContext.assertTrue(map.putIfAbsent(key, val));
			}
			for(int i = 0; i < 8; ++i) {
				final int key = (round * 13 + i);
				TestContext.assertTrue(map.removeKey(key));
			}
			TestContext.assertFalse(map.containsKey((round * 13 + 31)));
		}
		TestContext.assertEquals(0, map.size());
		final int keep = 987654321;
		for(int i = 0; i < 50; ++i) {
			map.put(keep, 11L);
		}
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.removeKey(keep));
		TestContext.assertTrue(map.putIfAbsent(7, 11L));
		TestContext.assertTrue(map.putIfAbsent(9, -22L));
		final FastIntIterator it = (FastIntIterator) map.keyIterator();
		TestContext.assertTrue(it.hasNext());
		final int returned = it.next();
		map.ensureCapacity(128);
		final int sizeBeforeRemove = map.size();
		it.remove();
		TestContext.assertFalse(map.containsKey(returned));
		TestContext.assertEquals(sizeBeforeRemove - 1, map.size());
		map.trimToSize();
		TestContext.assertEquals(1, map.size());
	}
	private void checkIntLongMapEmptyFastPaths() {
		final long fallback = 99L;
		final int[] emptyKeys = new int[0];
		final long[] emptyVals = new long[0];
		final FastIntSet emptySet = new FastIntSet();
		final FastIntLongMap emptySource = new FastIntLongMap();
		final FastIntLongMap map = new FastIntLongMap(16);
		TestContext.assertTrue((map.get(7) == 0L));
		TestContext.assertTrue((map.get(7, fallback) == fallback));
		TestContext.assertFalse(map.containsKey(7));
		TestContext.assertFalse(map.containsValue(11L));
		TestContext.assertTrue((map.remove(7) == 0L));
		TestContext.assertTrue((map.remove(7, fallback) == fallback));
		TestContext.assertFalse(map.removeKey(7));
		TestContext.assertFalse(map.removeEntry(7, 11L));
		TestContext.assertFalse(map.removeAllKeys((int[]) null));
		TestContext.assertFalse(map.removeAllKeys(emptyKeys));
		TestContext.assertFalse(map.removeAllKeys((FastIntSet) null));
		TestContext.assertFalse(map.removeAllKeys(emptySet));
		TestContext.assertFalse(map.retainAllKeys((FastIntSet) null));
		TestContext.assertFalse(map.retainAllKeys(emptySet));
		TestContext.assertFalse(map.putAll((FastIntLongMap) null));
		TestContext.assertFalse(map.putAll(emptySource));
		TestContext.assertFalse(map.putAll(emptyKeys, emptyVals));
		map.clear();
		TestContext.assertEquals(0, map.keys().length);
		TestContext.assertEquals(0, map.values().length);
		TestContext.assertTrue(map.keySet().isEmpty());
		TestContext.assertEquals(0, map.hashCode());
		TestContext.assertEquals("{}", map.toString());
		TestContext.assertFalse(map.keyIterator().hasNext());
		TestContext.assertFalse(map.valueIterator().hasNext());
		TestContext.assertTrue(map.putIfAbsent(1, 11L));
		TestContext.assertTrue(map.putIfAbsent(Integer.MAX_VALUE, -22L));
		TestContext.assertTrue(map.removeKey(1));
		TestContext.assertTrue(map.removeKey(Integer.MAX_VALUE));
		TestContext.assertEquals(0, map.size());
		TestContext.assertFalse(map.containsKey(1));
		TestContext.assertFalse(map.containsValue(11L));
		TestContext.assertEquals(0, map.keys().length);
		TestContext.assertEquals(0, map.values().length);
		TestContext.assertEquals(0, map.hashCode());
		TestContext.assertEquals("{}", map.toString());
		TestContext.assertFalse(map.keyIterator().hasNext());
		TestContext.assertFalse(map.valueIterator().hasNext());
		final FastIntLongMap shared = (FastIntLongMap) map.shared();
		final FastIntLongMap unmodifiable = (FastIntLongMap) map.unmodifiable();
		TestContext.assertTrue((shared.get(7, fallback) == fallback));
		TestContext.assertTrue((unmodifiable.get(7, fallback) == fallback));
		TestContext.assertFalse(shared.containsKey(7));
		TestContext.assertFalse(unmodifiable.containsKey(7));
		TestContext.assertEquals(0, shared.keys().length);
		TestContext.assertEquals(0, unmodifiable.keys().length);
		TestContext.assertEquals("{}", shared.toString());
		TestContext.assertEquals("{}", unmodifiable.toString());
		TestContext.assertFalse(shared.keyIterator().hasNext());
		TestContext.assertFalse(unmodifiable.valueIterator().hasNext());
		shared.clear();
		TestContext.assertTrue(shared.putIfAbsent(7, 11L));
		TestContext.assertTrue(unmodifiable.containsKey(7));
	}
	private void checkIntLongMapViews() {
		final FastIntLongMap source = new FastIntLongMap();
		source.put(Integer.MIN_VALUE, 11L);
		source.put(1, -22L);
		final FastIntLongMap shared = (FastIntLongMap) source.shared();
		final FastIntLongMap unmodifiable = (FastIntLongMap) source.unmodifiable();
		final FastIntLongMap sharedUnmod = (FastIntLongMap) shared.unmodifiable();
		TestContext.assertTrue(source.equals(shared));
		TestContext.assertTrue(shared.equals(source));
		TestContext.assertTrue(source.equals(unmodifiable));
		TestContext.assertTrue(unmodifiable.equals(source));
		TestContext.assertTrue(shared.equals(sharedUnmod));
		TestContext.assertTrue(sharedUnmod.equals(shared));
		TestContext.assertEquals(source.hashCode(), shared.hashCode());
		TestContext.assertEquals(source.hashCode(), unmodifiable.hashCode());
		TestContext.assertEquals(source.hashCode(), sharedUnmod.hashCode());
		final FastIntIterator kit = (FastIntIterator) shared.keyIterator();
		boolean badRemove = false;
		try { kit.remove(); } catch(final IllegalStateException ex) { badRemove = true; }
		TestContext.assertTrue(badRemove);
		int kCount = 0;
		while(kit.hasNext()) { kit.next(); ++kCount; }
		TestContext.assertEquals(2, kCount);
		boolean noMore = false;
		try { kit.next(); } catch(final NoSuchElementException ex) { noMore = true; }
		TestContext.assertTrue(noMore);
		final FastLongIterator vit = (FastLongIterator) shared.valueIterator();
		TestContext.assertTrue(vit.hasNext());
		vit.next();
		shared.ensureCapacity(64);
		vit.remove();
		TestContext.assertEquals(1, unmodifiable.size());
		TestContext.assertEquals(1, sharedUnmod.size());
		badRemove = false;
		try { vit.remove(); } catch(final IllegalStateException ex) { badRemove = true; }
		TestContext.assertTrue(badRemove);
		final FastIntIterator ukit = (FastIntIterator) unmodifiable.keyIterator();
		ukit.next();
		int rejected = 0;
		try { ukit.remove(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		final FastLongIterator uvit = (FastLongIterator) sharedUnmod.valueIterator();
		uvit.next();
		try { uvit.remove(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.put(7, 11L); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putIfAbsent(7, 11L); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putAll(new int[] { 7 }, new long[] { 11L }); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putAll(source); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.remove(Integer.MIN_VALUE); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.remove(Integer.MIN_VALUE, 99L); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeKey(Integer.MIN_VALUE); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeEntry(Integer.MIN_VALUE, 11L); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeAllKeys(new int[] { Integer.MIN_VALUE }); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeAllKeys(new FastIntSet()); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.retainAllKeys(new FastIntSet()); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.clear(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.reset(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.trimToSize(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.ensureCapacity(64); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.shared(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		TestContext.assertEquals(18, rejected);
	}
	private void checkIntShortMapPutGetRemove() {
		final int k1 = Integer.MIN_VALUE;
		final int k2 = 0;
		final int k3 = 1;
		final int k4 = Integer.MAX_VALUE;
		final int kAbsent = 424242;
		final short v1 = (short) 11;
		final short v2 = (short) -22;
		final short v3 = (short) 0;
		final short v4 = (short) 333;
		final short fallback = (short) 99;
		final short wrongVal = (short) 555;
		final FastIntShortMap map = new FastIntShortMap();
		TestContext.assertEquals(0, map.size());
		TestContext.assertTrue(map.isEmpty());
		TestContext.assertTrue((map.get(k1) == (short) 0));
		TestContext.assertTrue((map.get(k1, fallback) == fallback));
		TestContext.assertFalse(map.containsKey(k1));
		TestContext.assertTrue((map.put(k1, v1) == (short) 0));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.containsKey(k1));
		TestContext.assertTrue(map.containsValue(v1));
		TestContext.assertTrue((map.get(k1) == (short) 11));
		TestContext.assertTrue((map.put(k1, v2) == (short) 11));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue((map.get(k1) == (short) -22));
		TestContext.assertFalse(map.putIfAbsent(k1, v1));
		TestContext.assertTrue((map.get(k1) == (short) -22));
		TestContext.assertTrue(map.putIfAbsent(k3, v3));
		TestContext.assertEquals(2, map.size());
		TestContext.assertTrue((map.get(k3, fallback) == (short) 0));
		TestContext.assertFalse(map.containsValue((short) 555));
		final int[] bulkKeys = new int[] { k2, k4 };
		final short[] bulkVals = new short[] { v2, v4 };
		TestContext.assertTrue(map.putAll(bulkKeys, bulkVals));
		TestContext.assertEquals(4, map.size());
		TestContext.assertFalse(map.putAll(bulkKeys, bulkVals));
		TestContext.assertFalse(map.putAll((int[]) null, (short[]) null));
		boolean threw = false;
		try {
			map.putAll(new int[] { k1 }, bulkVals);
		}
		catch(final IllegalArgumentException ex) {
			threw = true;
		}
		TestContext.assertTrue(threw);
		final FastIntShortMap copy = new FastIntShortMap(map);
		TestContext.assertTrue(map.equals(copy));
		TestContext.assertTrue(copy.equals(map));
		TestContext.assertEquals(map.hashCode(), copy.hashCode());
		TestContext.assertFalse(map.putAll(copy));
		TestContext.assertFalse(map.putAll(map));
		final FastIntShortMap fromArrays = new FastIntShortMap(bulkKeys, bulkVals);
		TestContext.assertEquals(2, fromArrays.size());
		TestContext.assertTrue(fromArrays.containsKey(k2));
		TestContext.assertTrue(fromArrays.containsKey(k4));
		TestContext.assertEquals(4, map.keys().length);
		TestContext.assertEquals(4, map.values().length);
		final FastIntSet ks = map.keySet();
		TestContext.assertEquals(4, ks.size());
		TestContext.assertTrue(ks.contains(k1));
		TestContext.assertTrue(ks.contains(k4));
		TestContext.assertTrue(map.toString().indexOf('=') >= 0);
		try {
			final FastIntShortMap cloned = (FastIntShortMap) map.clone();
			TestContext.assertTrue(map.equals(cloned));
			cloned.put(kAbsent, v1);
			TestContext.assertFalse(map.containsKey(kAbsent));
		}
		catch(final UnsupportedOperationException j2meExpected) {}
		catch(final CloneNotSupportedException ex) {
			TestContext.assertTrue(false);
		}
		TestContext.assertFalse(map.removeEntry(k1, wrongVal));
		TestContext.assertTrue(map.removeEntry(k1, v2));
		TestContext.assertFalse(map.containsKey(k1));
		TestContext.assertTrue((map.remove(kAbsent, fallback) == fallback));
		TestContext.assertTrue((map.remove(k3) == (short) 0));
		TestContext.assertTrue(map.removeKey(k2));
		TestContext.assertFalse(map.removeKey(kAbsent));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.containsKey(k4));
		TestContext.assertTrue((map.put(k1, v1) == (short) 0));
		TestContext.assertTrue(map.removeAllKeys(new int[] { k1 }));
		final FastIntSet remSet = new FastIntSet(new int[] { k4 });
		TestContext.assertTrue(map.putIfAbsent(k2, v2));
		TestContext.assertTrue(map.retainAllKeys(remSet));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.removeAllKeys(remSet));
		TestContext.assertEquals(0, map.size());
	}
	private void checkIntShortMapChurn() {
		final FastIntShortMap map = new FastIntShortMap(16);
		for(int round = 0; round < 256; ++round) {
			for(int i = 0; i < 8; ++i) {
				final int key = (round * 13 + i);
				final short val = ((short) (round * 7 + i));
				TestContext.assertTrue(map.putIfAbsent(key, val));
			}
			for(int i = 0; i < 8; ++i) {
				final int key = (round * 13 + i);
				TestContext.assertTrue(map.removeKey(key));
			}
			TestContext.assertFalse(map.containsKey((round * 13 + 31)));
		}
		TestContext.assertEquals(0, map.size());
		final int keep = 987654321;
		for(int i = 0; i < 50; ++i) {
			map.put(keep, (short) 11);
		}
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.removeKey(keep));
		TestContext.assertTrue(map.putIfAbsent(7, (short) 11));
		TestContext.assertTrue(map.putIfAbsent(9, (short) -22));
		final FastIntIterator it = (FastIntIterator) map.keyIterator();
		TestContext.assertTrue(it.hasNext());
		final int returned = it.next();
		map.ensureCapacity(128);
		final int sizeBeforeRemove = map.size();
		it.remove();
		TestContext.assertFalse(map.containsKey(returned));
		TestContext.assertEquals(sizeBeforeRemove - 1, map.size());
		map.trimToSize();
		TestContext.assertEquals(1, map.size());
	}
	private void checkIntShortMapEmptyFastPaths() {
		final short fallback = (short) 99;
		final int[] emptyKeys = new int[0];
		final short[] emptyVals = new short[0];
		final FastIntSet emptySet = new FastIntSet();
		final FastIntShortMap emptySource = new FastIntShortMap();
		final FastIntShortMap map = new FastIntShortMap(16);
		TestContext.assertTrue((map.get(7) == (short) 0));
		TestContext.assertTrue((map.get(7, fallback) == fallback));
		TestContext.assertFalse(map.containsKey(7));
		TestContext.assertFalse(map.containsValue((short) 11));
		TestContext.assertTrue((map.remove(7) == (short) 0));
		TestContext.assertTrue((map.remove(7, fallback) == fallback));
		TestContext.assertFalse(map.removeKey(7));
		TestContext.assertFalse(map.removeEntry(7, (short) 11));
		TestContext.assertFalse(map.removeAllKeys((int[]) null));
		TestContext.assertFalse(map.removeAllKeys(emptyKeys));
		TestContext.assertFalse(map.removeAllKeys((FastIntSet) null));
		TestContext.assertFalse(map.removeAllKeys(emptySet));
		TestContext.assertFalse(map.retainAllKeys((FastIntSet) null));
		TestContext.assertFalse(map.retainAllKeys(emptySet));
		TestContext.assertFalse(map.putAll((FastIntShortMap) null));
		TestContext.assertFalse(map.putAll(emptySource));
		TestContext.assertFalse(map.putAll(emptyKeys, emptyVals));
		map.clear();
		TestContext.assertEquals(0, map.keys().length);
		TestContext.assertEquals(0, map.values().length);
		TestContext.assertTrue(map.keySet().isEmpty());
		TestContext.assertEquals(0, map.hashCode());
		TestContext.assertEquals("{}", map.toString());
		TestContext.assertFalse(map.keyIterator().hasNext());
		TestContext.assertFalse(map.valueIterator().hasNext());
		TestContext.assertTrue(map.putIfAbsent(1, (short) 11));
		TestContext.assertTrue(map.putIfAbsent(Integer.MAX_VALUE, (short) -22));
		TestContext.assertTrue(map.removeKey(1));
		TestContext.assertTrue(map.removeKey(Integer.MAX_VALUE));
		TestContext.assertEquals(0, map.size());
		TestContext.assertFalse(map.containsKey(1));
		TestContext.assertFalse(map.containsValue((short) 11));
		TestContext.assertEquals(0, map.keys().length);
		TestContext.assertEquals(0, map.values().length);
		TestContext.assertEquals(0, map.hashCode());
		TestContext.assertEquals("{}", map.toString());
		TestContext.assertFalse(map.keyIterator().hasNext());
		TestContext.assertFalse(map.valueIterator().hasNext());
		final FastIntShortMap shared = (FastIntShortMap) map.shared();
		final FastIntShortMap unmodifiable = (FastIntShortMap) map.unmodifiable();
		TestContext.assertTrue((shared.get(7, fallback) == fallback));
		TestContext.assertTrue((unmodifiable.get(7, fallback) == fallback));
		TestContext.assertFalse(shared.containsKey(7));
		TestContext.assertFalse(unmodifiable.containsKey(7));
		TestContext.assertEquals(0, shared.keys().length);
		TestContext.assertEquals(0, unmodifiable.keys().length);
		TestContext.assertEquals("{}", shared.toString());
		TestContext.assertEquals("{}", unmodifiable.toString());
		TestContext.assertFalse(shared.keyIterator().hasNext());
		TestContext.assertFalse(unmodifiable.valueIterator().hasNext());
		shared.clear();
		TestContext.assertTrue(shared.putIfAbsent(7, (short) 11));
		TestContext.assertTrue(unmodifiable.containsKey(7));
	}
	private void checkIntShortMapViews() {
		final FastIntShortMap source = new FastIntShortMap();
		source.put(Integer.MIN_VALUE, (short) 11);
		source.put(1, (short) -22);
		final FastIntShortMap shared = (FastIntShortMap) source.shared();
		final FastIntShortMap unmodifiable = (FastIntShortMap) source.unmodifiable();
		final FastIntShortMap sharedUnmod = (FastIntShortMap) shared.unmodifiable();
		TestContext.assertTrue(source.equals(shared));
		TestContext.assertTrue(shared.equals(source));
		TestContext.assertTrue(source.equals(unmodifiable));
		TestContext.assertTrue(unmodifiable.equals(source));
		TestContext.assertTrue(shared.equals(sharedUnmod));
		TestContext.assertTrue(sharedUnmod.equals(shared));
		TestContext.assertEquals(source.hashCode(), shared.hashCode());
		TestContext.assertEquals(source.hashCode(), unmodifiable.hashCode());
		TestContext.assertEquals(source.hashCode(), sharedUnmod.hashCode());
		final FastIntIterator kit = (FastIntIterator) shared.keyIterator();
		boolean badRemove = false;
		try { kit.remove(); } catch(final IllegalStateException ex) { badRemove = true; }
		TestContext.assertTrue(badRemove);
		int kCount = 0;
		while(kit.hasNext()) { kit.next(); ++kCount; }
		TestContext.assertEquals(2, kCount);
		boolean noMore = false;
		try { kit.next(); } catch(final NoSuchElementException ex) { noMore = true; }
		TestContext.assertTrue(noMore);
		final FastShortIterator vit = (FastShortIterator) shared.valueIterator();
		TestContext.assertTrue(vit.hasNext());
		vit.next();
		shared.ensureCapacity(64);
		vit.remove();
		TestContext.assertEquals(1, unmodifiable.size());
		TestContext.assertEquals(1, sharedUnmod.size());
		badRemove = false;
		try { vit.remove(); } catch(final IllegalStateException ex) { badRemove = true; }
		TestContext.assertTrue(badRemove);
		final FastIntIterator ukit = (FastIntIterator) unmodifiable.keyIterator();
		ukit.next();
		int rejected = 0;
		try { ukit.remove(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		final FastShortIterator uvit = (FastShortIterator) sharedUnmod.valueIterator();
		uvit.next();
		try { uvit.remove(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.put(7, (short) 11); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putIfAbsent(7, (short) 11); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putAll(new int[] { 7 }, new short[] { (short) 11 }); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putAll(source); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.remove(Integer.MIN_VALUE); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.remove(Integer.MIN_VALUE, (short) 99); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeKey(Integer.MIN_VALUE); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeEntry(Integer.MIN_VALUE, (short) 11); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeAllKeys(new int[] { Integer.MIN_VALUE }); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeAllKeys(new FastIntSet()); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.retainAllKeys(new FastIntSet()); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.clear(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.reset(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.trimToSize(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.ensureCapacity(64); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.shared(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		TestContext.assertEquals(18, rejected);
	}
	private void checkLongBooleanMapPutGetRemove() {
		final long k1 = Long.MIN_VALUE;
		final long k2 = 0L;
		final long k3 = 1L;
		final long k4 = 0x100000001L;
		final long kAbsent = 424242424242L;
		final boolean v1 = true;
		final boolean v2 = false;
		final boolean v3 = true;
		final boolean v4 = false;
		final boolean fallback = true;
		final boolean wrongVal = true;
		final FastLongBooleanMap map = new FastLongBooleanMap();
		TestContext.assertEquals(0, map.size());
		TestContext.assertTrue(map.isEmpty());
		TestContext.assertTrue((map.get(k1) == false));
		TestContext.assertTrue((map.get(k1, fallback) == fallback));
		TestContext.assertFalse(map.containsKey(k1));
		TestContext.assertTrue((map.put(k1, v1) == false));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.containsKey(k1));
		TestContext.assertTrue(map.containsValue(v1));
		TestContext.assertTrue((map.get(k1) == true));
		TestContext.assertTrue((map.put(k1, v2) == true));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue((map.get(k1) == false));
		TestContext.assertFalse(map.putIfAbsent(k1, v1));
		TestContext.assertTrue((map.get(k1) == false));
		TestContext.assertTrue(map.putIfAbsent(k3, v3));
		TestContext.assertEquals(2, map.size());
		TestContext.assertTrue((map.get(k3, fallback) == true));
		final long[] bulkKeys = new long[] { k2, k4 };
		final boolean[] bulkVals = new boolean[] { v2, v4 };
		TestContext.assertTrue(map.putAll(bulkKeys, bulkVals));
		TestContext.assertEquals(4, map.size());
		TestContext.assertFalse(map.putAll(bulkKeys, bulkVals));
		TestContext.assertFalse(map.putAll((long[]) null, (boolean[]) null));
		boolean threw = false;
		try {
			map.putAll(new long[] { k1 }, bulkVals);
		}
		catch(final IllegalArgumentException ex) {
			threw = true;
		}
		TestContext.assertTrue(threw);
		final FastLongBooleanMap copy = new FastLongBooleanMap(map);
		TestContext.assertTrue(map.equals(copy));
		TestContext.assertTrue(copy.equals(map));
		TestContext.assertEquals(map.hashCode(), copy.hashCode());
		TestContext.assertFalse(map.putAll(copy));
		TestContext.assertFalse(map.putAll(map));
		final FastLongBooleanMap fromArrays = new FastLongBooleanMap(bulkKeys, bulkVals);
		TestContext.assertEquals(2, fromArrays.size());
		TestContext.assertTrue(fromArrays.containsKey(k2));
		TestContext.assertTrue(fromArrays.containsKey(k4));
		TestContext.assertEquals(4, map.keys().length);
		TestContext.assertEquals(4, map.values().length);
		final FastLongSet ks = map.keySet();
		TestContext.assertEquals(4, ks.size());
		TestContext.assertTrue(ks.contains(k1));
		TestContext.assertTrue(ks.contains(k4));
		TestContext.assertTrue(map.toString().indexOf('=') >= 0);
		try {
			final FastLongBooleanMap cloned = (FastLongBooleanMap) map.clone();
			TestContext.assertTrue(map.equals(cloned));
			cloned.put(kAbsent, v1);
			TestContext.assertFalse(map.containsKey(kAbsent));
		}
		catch(final UnsupportedOperationException j2meExpected) {}
		catch(final CloneNotSupportedException ex) {
			TestContext.assertTrue(false);
		}
		TestContext.assertFalse(map.removeEntry(k1, wrongVal));
		TestContext.assertTrue(map.removeEntry(k1, v2));
		TestContext.assertFalse(map.containsKey(k1));
		TestContext.assertTrue((map.remove(kAbsent, fallback) == fallback));
		TestContext.assertTrue((map.remove(k3) == true));
		TestContext.assertTrue(map.removeKey(k2));
		TestContext.assertFalse(map.removeKey(kAbsent));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.containsKey(k4));
		TestContext.assertTrue((map.put(k1, v1) == false));
		TestContext.assertTrue(map.removeAllKeys(new long[] { k1 }));
		final FastLongSet remSet = new FastLongSet(new long[] { k4 });
		TestContext.assertTrue(map.putIfAbsent(k2, v2));
		TestContext.assertTrue(map.retainAllKeys(remSet));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.removeAllKeys(remSet));
		TestContext.assertEquals(0, map.size());
	}
	private void checkLongBooleanMapChurn() {
		final FastLongBooleanMap map = new FastLongBooleanMap(16);
		for(int round = 0; round < 256; ++round) {
			for(int i = 0; i < 8; ++i) {
				final long key = (round * 13L + i);
				final boolean val = ((i & 1) != 0);
				TestContext.assertTrue(map.putIfAbsent(key, val));
			}
			for(int i = 0; i < 8; ++i) {
				final long key = (round * 13L + i);
				TestContext.assertTrue(map.removeKey(key));
			}
			TestContext.assertFalse(map.containsKey((round * 13L + 31L)));
		}
		TestContext.assertEquals(0, map.size());
		final long keep = 9876543210123L;
		for(int i = 0; i < 50; ++i) {
			map.put(keep, true);
		}
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.removeKey(keep));
		TestContext.assertTrue(map.putIfAbsent(7L, true));
		TestContext.assertTrue(map.putIfAbsent(9L, false));
		final FastLongIterator it = (FastLongIterator) map.keyIterator();
		TestContext.assertTrue(it.hasNext());
		final long returned = it.next();
		map.ensureCapacity(128);
		final int sizeBeforeRemove = map.size();
		it.remove();
		TestContext.assertFalse(map.containsKey(returned));
		TestContext.assertEquals(sizeBeforeRemove - 1, map.size());
		map.trimToSize();
		TestContext.assertEquals(1, map.size());
	}
	private void checkLongBooleanMapEmptyFastPaths() {
		final boolean fallback = true;
		final long[] emptyKeys = new long[0];
		final boolean[] emptyVals = new boolean[0];
		final FastLongSet emptySet = new FastLongSet();
		final FastLongBooleanMap emptySource = new FastLongBooleanMap();
		final FastLongBooleanMap map = new FastLongBooleanMap(16);
		TestContext.assertTrue((map.get(7L) == false));
		TestContext.assertTrue((map.get(7L, fallback) == fallback));
		TestContext.assertFalse(map.containsKey(7L));
		TestContext.assertFalse(map.containsValue(true));
		TestContext.assertTrue((map.remove(7L) == false));
		TestContext.assertTrue((map.remove(7L, fallback) == fallback));
		TestContext.assertFalse(map.removeKey(7L));
		TestContext.assertFalse(map.removeEntry(7L, true));
		TestContext.assertFalse(map.removeAllKeys((long[]) null));
		TestContext.assertFalse(map.removeAllKeys(emptyKeys));
		TestContext.assertFalse(map.removeAllKeys((FastLongSet) null));
		TestContext.assertFalse(map.removeAllKeys(emptySet));
		TestContext.assertFalse(map.retainAllKeys((FastLongSet) null));
		TestContext.assertFalse(map.retainAllKeys(emptySet));
		TestContext.assertFalse(map.putAll((FastLongBooleanMap) null));
		TestContext.assertFalse(map.putAll(emptySource));
		TestContext.assertFalse(map.putAll(emptyKeys, emptyVals));
		map.clear();
		TestContext.assertEquals(0, map.keys().length);
		TestContext.assertEquals(0, map.values().length);
		TestContext.assertTrue(map.keySet().isEmpty());
		TestContext.assertEquals(0, map.hashCode());
		TestContext.assertEquals("{}", map.toString());
		TestContext.assertFalse(map.keyIterator().hasNext());
		TestContext.assertFalse(map.valueIterator().hasNext());
		TestContext.assertTrue(map.putIfAbsent(1L, true));
		TestContext.assertTrue(map.putIfAbsent(0x100000001L, false));
		TestContext.assertTrue(map.removeKey(1L));
		TestContext.assertTrue(map.removeKey(0x100000001L));
		TestContext.assertEquals(0, map.size());
		TestContext.assertFalse(map.containsKey(1L));
		TestContext.assertFalse(map.containsValue(true));
		TestContext.assertEquals(0, map.keys().length);
		TestContext.assertEquals(0, map.values().length);
		TestContext.assertEquals(0, map.hashCode());
		TestContext.assertEquals("{}", map.toString());
		TestContext.assertFalse(map.keyIterator().hasNext());
		TestContext.assertFalse(map.valueIterator().hasNext());
		final FastLongBooleanMap shared = (FastLongBooleanMap) map.shared();
		final FastLongBooleanMap unmodifiable = (FastLongBooleanMap) map.unmodifiable();
		TestContext.assertTrue((shared.get(7L, fallback) == fallback));
		TestContext.assertTrue((unmodifiable.get(7L, fallback) == fallback));
		TestContext.assertFalse(shared.containsKey(7L));
		TestContext.assertFalse(unmodifiable.containsKey(7L));
		TestContext.assertEquals(0, shared.keys().length);
		TestContext.assertEquals(0, unmodifiable.keys().length);
		TestContext.assertEquals("{}", shared.toString());
		TestContext.assertEquals("{}", unmodifiable.toString());
		TestContext.assertFalse(shared.keyIterator().hasNext());
		TestContext.assertFalse(unmodifiable.valueIterator().hasNext());
		shared.clear();
		TestContext.assertTrue(shared.putIfAbsent(7L, true));
		TestContext.assertTrue(unmodifiable.containsKey(7L));
	}
	private void checkLongBooleanMapViews() {
		final FastLongBooleanMap source = new FastLongBooleanMap();
		source.put(Long.MIN_VALUE, true);
		source.put(1L, false);
		final FastLongBooleanMap shared = (FastLongBooleanMap) source.shared();
		final FastLongBooleanMap unmodifiable = (FastLongBooleanMap) source.unmodifiable();
		final FastLongBooleanMap sharedUnmod = (FastLongBooleanMap) shared.unmodifiable();
		TestContext.assertTrue(source.equals(shared));
		TestContext.assertTrue(shared.equals(source));
		TestContext.assertTrue(source.equals(unmodifiable));
		TestContext.assertTrue(unmodifiable.equals(source));
		TestContext.assertTrue(shared.equals(sharedUnmod));
		TestContext.assertTrue(sharedUnmod.equals(shared));
		TestContext.assertEquals(source.hashCode(), shared.hashCode());
		TestContext.assertEquals(source.hashCode(), unmodifiable.hashCode());
		TestContext.assertEquals(source.hashCode(), sharedUnmod.hashCode());
		final FastLongIterator kit = (FastLongIterator) shared.keyIterator();
		boolean badRemove = false;
		try { kit.remove(); } catch(final IllegalStateException ex) { badRemove = true; }
		TestContext.assertTrue(badRemove);
		int kCount = 0;
		while(kit.hasNext()) { kit.next(); ++kCount; }
		TestContext.assertEquals(2, kCount);
		boolean noMore = false;
		try { kit.next(); } catch(final NoSuchElementException ex) { noMore = true; }
		TestContext.assertTrue(noMore);
		final FastBooleanIterator vit = (FastBooleanIterator) shared.valueIterator();
		TestContext.assertTrue(vit.hasNext());
		vit.next();
		shared.ensureCapacity(64);
		vit.remove();
		TestContext.assertEquals(1, unmodifiable.size());
		TestContext.assertEquals(1, sharedUnmod.size());
		badRemove = false;
		try { vit.remove(); } catch(final IllegalStateException ex) { badRemove = true; }
		TestContext.assertTrue(badRemove);
		final FastLongIterator ukit = (FastLongIterator) unmodifiable.keyIterator();
		ukit.next();
		int rejected = 0;
		try { ukit.remove(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		final FastBooleanIterator uvit = (FastBooleanIterator) sharedUnmod.valueIterator();
		uvit.next();
		try { uvit.remove(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.put(7L, true); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putIfAbsent(7L, true); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putAll(new long[] { 7L }, new boolean[] { true }); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putAll(source); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.remove(Long.MIN_VALUE); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.remove(Long.MIN_VALUE, true); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeKey(Long.MIN_VALUE); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeEntry(Long.MIN_VALUE, true); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeAllKeys(new long[] { Long.MIN_VALUE }); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeAllKeys(new FastLongSet()); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.retainAllKeys(new FastLongSet()); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.clear(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.reset(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.trimToSize(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.ensureCapacity(64); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.shared(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		TestContext.assertEquals(18, rejected);
	}
	private void checkLongByteMapPutGetRemove() {
		final long k1 = Long.MIN_VALUE;
		final long k2 = 0L;
		final long k3 = 1L;
		final long k4 = 0x100000001L;
		final long kAbsent = 424242424242L;
		final byte v1 = (byte) 11;
		final byte v2 = (byte) -22;
		final byte v3 = (byte) 0;
		final byte v4 = (byte) 127;
		final byte fallback = (byte) 99;
		final byte wrongVal = (byte) 55;
		final FastLongByteMap map = new FastLongByteMap();
		TestContext.assertEquals(0, map.size());
		TestContext.assertTrue(map.isEmpty());
		TestContext.assertTrue((map.get(k1) == (byte) 0));
		TestContext.assertTrue((map.get(k1, fallback) == fallback));
		TestContext.assertFalse(map.containsKey(k1));
		TestContext.assertTrue((map.put(k1, v1) == (byte) 0));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.containsKey(k1));
		TestContext.assertTrue(map.containsValue(v1));
		TestContext.assertTrue((map.get(k1) == (byte) 11));
		TestContext.assertTrue((map.put(k1, v2) == (byte) 11));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue((map.get(k1) == (byte) -22));
		TestContext.assertFalse(map.putIfAbsent(k1, v1));
		TestContext.assertTrue((map.get(k1) == (byte) -22));
		TestContext.assertTrue(map.putIfAbsent(k3, v3));
		TestContext.assertEquals(2, map.size());
		TestContext.assertTrue((map.get(k3, fallback) == (byte) 0));
		TestContext.assertFalse(map.containsValue((byte) 55));
		final long[] bulkKeys = new long[] { k2, k4 };
		final byte[] bulkVals = new byte[] { v2, v4 };
		TestContext.assertTrue(map.putAll(bulkKeys, bulkVals));
		TestContext.assertEquals(4, map.size());
		TestContext.assertFalse(map.putAll(bulkKeys, bulkVals));
		TestContext.assertFalse(map.putAll((long[]) null, (byte[]) null));
		boolean threw = false;
		try {
			map.putAll(new long[] { k1 }, bulkVals);
		}
		catch(final IllegalArgumentException ex) {
			threw = true;
		}
		TestContext.assertTrue(threw);
		final FastLongByteMap copy = new FastLongByteMap(map);
		TestContext.assertTrue(map.equals(copy));
		TestContext.assertTrue(copy.equals(map));
		TestContext.assertEquals(map.hashCode(), copy.hashCode());
		TestContext.assertFalse(map.putAll(copy));
		TestContext.assertFalse(map.putAll(map));
		final FastLongByteMap fromArrays = new FastLongByteMap(bulkKeys, bulkVals);
		TestContext.assertEquals(2, fromArrays.size());
		TestContext.assertTrue(fromArrays.containsKey(k2));
		TestContext.assertTrue(fromArrays.containsKey(k4));
		TestContext.assertEquals(4, map.keys().length);
		TestContext.assertEquals(4, map.values().length);
		final FastLongSet ks = map.keySet();
		TestContext.assertEquals(4, ks.size());
		TestContext.assertTrue(ks.contains(k1));
		TestContext.assertTrue(ks.contains(k4));
		TestContext.assertTrue(map.toString().indexOf('=') >= 0);
		try {
			final FastLongByteMap cloned = (FastLongByteMap) map.clone();
			TestContext.assertTrue(map.equals(cloned));
			cloned.put(kAbsent, v1);
			TestContext.assertFalse(map.containsKey(kAbsent));
		}
		catch(final UnsupportedOperationException j2meExpected) {}
		catch(final CloneNotSupportedException ex) {
			TestContext.assertTrue(false);
		}
		TestContext.assertFalse(map.removeEntry(k1, wrongVal));
		TestContext.assertTrue(map.removeEntry(k1, v2));
		TestContext.assertFalse(map.containsKey(k1));
		TestContext.assertTrue((map.remove(kAbsent, fallback) == fallback));
		TestContext.assertTrue((map.remove(k3) == (byte) 0));
		TestContext.assertTrue(map.removeKey(k2));
		TestContext.assertFalse(map.removeKey(kAbsent));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.containsKey(k4));
		TestContext.assertTrue((map.put(k1, v1) == (byte) 0));
		TestContext.assertTrue(map.removeAllKeys(new long[] { k1 }));
		final FastLongSet remSet = new FastLongSet(new long[] { k4 });
		TestContext.assertTrue(map.putIfAbsent(k2, v2));
		TestContext.assertTrue(map.retainAllKeys(remSet));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.removeAllKeys(remSet));
		TestContext.assertEquals(0, map.size());
	}
	private void checkLongByteMapChurn() {
		final FastLongByteMap map = new FastLongByteMap(16);
		for(int round = 0; round < 256; ++round) {
			for(int i = 0; i < 8; ++i) {
				final long key = (round * 13L + i);
				final byte val = ((byte) (round * 7 + i));
				TestContext.assertTrue(map.putIfAbsent(key, val));
			}
			for(int i = 0; i < 8; ++i) {
				final long key = (round * 13L + i);
				TestContext.assertTrue(map.removeKey(key));
			}
			TestContext.assertFalse(map.containsKey((round * 13L + 31L)));
		}
		TestContext.assertEquals(0, map.size());
		final long keep = 9876543210123L;
		for(int i = 0; i < 50; ++i) {
			map.put(keep, (byte) 11);
		}
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.removeKey(keep));
		TestContext.assertTrue(map.putIfAbsent(7L, (byte) 11));
		TestContext.assertTrue(map.putIfAbsent(9L, (byte) -22));
		final FastLongIterator it = (FastLongIterator) map.keyIterator();
		TestContext.assertTrue(it.hasNext());
		final long returned = it.next();
		map.ensureCapacity(128);
		final int sizeBeforeRemove = map.size();
		it.remove();
		TestContext.assertFalse(map.containsKey(returned));
		TestContext.assertEquals(sizeBeforeRemove - 1, map.size());
		map.trimToSize();
		TestContext.assertEquals(1, map.size());
	}
	private void checkLongByteMapEmptyFastPaths() {
		final byte fallback = (byte) 99;
		final long[] emptyKeys = new long[0];
		final byte[] emptyVals = new byte[0];
		final FastLongSet emptySet = new FastLongSet();
		final FastLongByteMap emptySource = new FastLongByteMap();
		final FastLongByteMap map = new FastLongByteMap(16);
		TestContext.assertTrue((map.get(7L) == (byte) 0));
		TestContext.assertTrue((map.get(7L, fallback) == fallback));
		TestContext.assertFalse(map.containsKey(7L));
		TestContext.assertFalse(map.containsValue((byte) 11));
		TestContext.assertTrue((map.remove(7L) == (byte) 0));
		TestContext.assertTrue((map.remove(7L, fallback) == fallback));
		TestContext.assertFalse(map.removeKey(7L));
		TestContext.assertFalse(map.removeEntry(7L, (byte) 11));
		TestContext.assertFalse(map.removeAllKeys((long[]) null));
		TestContext.assertFalse(map.removeAllKeys(emptyKeys));
		TestContext.assertFalse(map.removeAllKeys((FastLongSet) null));
		TestContext.assertFalse(map.removeAllKeys(emptySet));
		TestContext.assertFalse(map.retainAllKeys((FastLongSet) null));
		TestContext.assertFalse(map.retainAllKeys(emptySet));
		TestContext.assertFalse(map.putAll((FastLongByteMap) null));
		TestContext.assertFalse(map.putAll(emptySource));
		TestContext.assertFalse(map.putAll(emptyKeys, emptyVals));
		map.clear();
		TestContext.assertEquals(0, map.keys().length);
		TestContext.assertEquals(0, map.values().length);
		TestContext.assertTrue(map.keySet().isEmpty());
		TestContext.assertEquals(0, map.hashCode());
		TestContext.assertEquals("{}", map.toString());
		TestContext.assertFalse(map.keyIterator().hasNext());
		TestContext.assertFalse(map.valueIterator().hasNext());
		TestContext.assertTrue(map.putIfAbsent(1L, (byte) 11));
		TestContext.assertTrue(map.putIfAbsent(0x100000001L, (byte) -22));
		TestContext.assertTrue(map.removeKey(1L));
		TestContext.assertTrue(map.removeKey(0x100000001L));
		TestContext.assertEquals(0, map.size());
		TestContext.assertFalse(map.containsKey(1L));
		TestContext.assertFalse(map.containsValue((byte) 11));
		TestContext.assertEquals(0, map.keys().length);
		TestContext.assertEquals(0, map.values().length);
		TestContext.assertEquals(0, map.hashCode());
		TestContext.assertEquals("{}", map.toString());
		TestContext.assertFalse(map.keyIterator().hasNext());
		TestContext.assertFalse(map.valueIterator().hasNext());
		final FastLongByteMap shared = (FastLongByteMap) map.shared();
		final FastLongByteMap unmodifiable = (FastLongByteMap) map.unmodifiable();
		TestContext.assertTrue((shared.get(7L, fallback) == fallback));
		TestContext.assertTrue((unmodifiable.get(7L, fallback) == fallback));
		TestContext.assertFalse(shared.containsKey(7L));
		TestContext.assertFalse(unmodifiable.containsKey(7L));
		TestContext.assertEquals(0, shared.keys().length);
		TestContext.assertEquals(0, unmodifiable.keys().length);
		TestContext.assertEquals("{}", shared.toString());
		TestContext.assertEquals("{}", unmodifiable.toString());
		TestContext.assertFalse(shared.keyIterator().hasNext());
		TestContext.assertFalse(unmodifiable.valueIterator().hasNext());
		shared.clear();
		TestContext.assertTrue(shared.putIfAbsent(7L, (byte) 11));
		TestContext.assertTrue(unmodifiable.containsKey(7L));
	}
	private void checkLongByteMapViews() {
		final FastLongByteMap source = new FastLongByteMap();
		source.put(Long.MIN_VALUE, (byte) 11);
		source.put(1L, (byte) -22);
		final FastLongByteMap shared = (FastLongByteMap) source.shared();
		final FastLongByteMap unmodifiable = (FastLongByteMap) source.unmodifiable();
		final FastLongByteMap sharedUnmod = (FastLongByteMap) shared.unmodifiable();
		TestContext.assertTrue(source.equals(shared));
		TestContext.assertTrue(shared.equals(source));
		TestContext.assertTrue(source.equals(unmodifiable));
		TestContext.assertTrue(unmodifiable.equals(source));
		TestContext.assertTrue(shared.equals(sharedUnmod));
		TestContext.assertTrue(sharedUnmod.equals(shared));
		TestContext.assertEquals(source.hashCode(), shared.hashCode());
		TestContext.assertEquals(source.hashCode(), unmodifiable.hashCode());
		TestContext.assertEquals(source.hashCode(), sharedUnmod.hashCode());
		final FastLongIterator kit = (FastLongIterator) shared.keyIterator();
		boolean badRemove = false;
		try { kit.remove(); } catch(final IllegalStateException ex) { badRemove = true; }
		TestContext.assertTrue(badRemove);
		int kCount = 0;
		while(kit.hasNext()) { kit.next(); ++kCount; }
		TestContext.assertEquals(2, kCount);
		boolean noMore = false;
		try { kit.next(); } catch(final NoSuchElementException ex) { noMore = true; }
		TestContext.assertTrue(noMore);
		final FastByteIterator vit = (FastByteIterator) shared.valueIterator();
		TestContext.assertTrue(vit.hasNext());
		vit.next();
		shared.ensureCapacity(64);
		vit.remove();
		TestContext.assertEquals(1, unmodifiable.size());
		TestContext.assertEquals(1, sharedUnmod.size());
		badRemove = false;
		try { vit.remove(); } catch(final IllegalStateException ex) { badRemove = true; }
		TestContext.assertTrue(badRemove);
		final FastLongIterator ukit = (FastLongIterator) unmodifiable.keyIterator();
		ukit.next();
		int rejected = 0;
		try { ukit.remove(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		final FastByteIterator uvit = (FastByteIterator) sharedUnmod.valueIterator();
		uvit.next();
		try { uvit.remove(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.put(7L, (byte) 11); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putIfAbsent(7L, (byte) 11); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putAll(new long[] { 7L }, new byte[] { (byte) 11 }); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putAll(source); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.remove(Long.MIN_VALUE); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.remove(Long.MIN_VALUE, (byte) 99); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeKey(Long.MIN_VALUE); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeEntry(Long.MIN_VALUE, (byte) 11); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeAllKeys(new long[] { Long.MIN_VALUE }); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeAllKeys(new FastLongSet()); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.retainAllKeys(new FastLongSet()); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.clear(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.reset(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.trimToSize(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.ensureCapacity(64); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.shared(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		TestContext.assertEquals(18, rejected);
	}
	private void checkLongCharMapPutGetRemove() {
		final long k1 = Long.MIN_VALUE;
		final long k2 = 0L;
		final long k3 = 1L;
		final long k4 = 0x100000001L;
		final long kAbsent = 424242424242L;
		final char v1 = 'A';
		final char v2 = 'Z';
		final char v3 = '\0';
		final char v4 = 'm';
		final char fallback = '!';
		final char wrongVal = '~';
		final FastLongCharMap map = new FastLongCharMap();
		TestContext.assertEquals(0, map.size());
		TestContext.assertTrue(map.isEmpty());
		TestContext.assertTrue((map.get(k1) == '\0'));
		TestContext.assertTrue((map.get(k1, fallback) == fallback));
		TestContext.assertFalse(map.containsKey(k1));
		TestContext.assertTrue((map.put(k1, v1) == '\0'));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.containsKey(k1));
		TestContext.assertTrue(map.containsValue(v1));
		TestContext.assertTrue((map.get(k1) == 'A'));
		TestContext.assertTrue((map.put(k1, v2) == 'A'));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue((map.get(k1) == 'Z'));
		TestContext.assertFalse(map.putIfAbsent(k1, v1));
		TestContext.assertTrue((map.get(k1) == 'Z'));
		TestContext.assertTrue(map.putIfAbsent(k3, v3));
		TestContext.assertEquals(2, map.size());
		TestContext.assertTrue((map.get(k3, fallback) == '\0'));
		TestContext.assertFalse(map.containsValue('~'));
		final long[] bulkKeys = new long[] { k2, k4 };
		final char[] bulkVals = new char[] { v2, v4 };
		TestContext.assertTrue(map.putAll(bulkKeys, bulkVals));
		TestContext.assertEquals(4, map.size());
		TestContext.assertFalse(map.putAll(bulkKeys, bulkVals));
		TestContext.assertFalse(map.putAll((long[]) null, (char[]) null));
		boolean threw = false;
		try {
			map.putAll(new long[] { k1 }, bulkVals);
		}
		catch(final IllegalArgumentException ex) {
			threw = true;
		}
		TestContext.assertTrue(threw);
		final FastLongCharMap copy = new FastLongCharMap(map);
		TestContext.assertTrue(map.equals(copy));
		TestContext.assertTrue(copy.equals(map));
		TestContext.assertEquals(map.hashCode(), copy.hashCode());
		TestContext.assertFalse(map.putAll(copy));
		TestContext.assertFalse(map.putAll(map));
		final FastLongCharMap fromArrays = new FastLongCharMap(bulkKeys, bulkVals);
		TestContext.assertEquals(2, fromArrays.size());
		TestContext.assertTrue(fromArrays.containsKey(k2));
		TestContext.assertTrue(fromArrays.containsKey(k4));
		TestContext.assertEquals(4, map.keys().length);
		TestContext.assertEquals(4, map.values().length);
		final FastLongSet ks = map.keySet();
		TestContext.assertEquals(4, ks.size());
		TestContext.assertTrue(ks.contains(k1));
		TestContext.assertTrue(ks.contains(k4));
		TestContext.assertTrue(map.toString().indexOf('=') >= 0);
		try {
			final FastLongCharMap cloned = (FastLongCharMap) map.clone();
			TestContext.assertTrue(map.equals(cloned));
			cloned.put(kAbsent, v1);
			TestContext.assertFalse(map.containsKey(kAbsent));
		}
		catch(final UnsupportedOperationException j2meExpected) {}
		catch(final CloneNotSupportedException ex) {
			TestContext.assertTrue(false);
		}
		TestContext.assertFalse(map.removeEntry(k1, wrongVal));
		TestContext.assertTrue(map.removeEntry(k1, v2));
		TestContext.assertFalse(map.containsKey(k1));
		TestContext.assertTrue((map.remove(kAbsent, fallback) == fallback));
		TestContext.assertTrue((map.remove(k3) == '\0'));
		TestContext.assertTrue(map.removeKey(k2));
		TestContext.assertFalse(map.removeKey(kAbsent));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.containsKey(k4));
		TestContext.assertTrue((map.put(k1, v1) == '\0'));
		TestContext.assertTrue(map.removeAllKeys(new long[] { k1 }));
		final FastLongSet remSet = new FastLongSet(new long[] { k4 });
		TestContext.assertTrue(map.putIfAbsent(k2, v2));
		TestContext.assertTrue(map.retainAllKeys(remSet));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.removeAllKeys(remSet));
		TestContext.assertEquals(0, map.size());
	}
	private void checkLongCharMapChurn() {
		final FastLongCharMap map = new FastLongCharMap(16);
		for(int round = 0; round < 256; ++round) {
			for(int i = 0; i < 8; ++i) {
				final long key = (round * 13L + i);
				final char val = ((char) ('a' + ((round + i) % 26)));
				TestContext.assertTrue(map.putIfAbsent(key, val));
			}
			for(int i = 0; i < 8; ++i) {
				final long key = (round * 13L + i);
				TestContext.assertTrue(map.removeKey(key));
			}
			TestContext.assertFalse(map.containsKey((round * 13L + 31L)));
		}
		TestContext.assertEquals(0, map.size());
		final long keep = 9876543210123L;
		for(int i = 0; i < 50; ++i) {
			map.put(keep, 'A');
		}
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.removeKey(keep));
		TestContext.assertTrue(map.putIfAbsent(7L, 'A'));
		TestContext.assertTrue(map.putIfAbsent(9L, 'Z'));
		final FastLongIterator it = (FastLongIterator) map.keyIterator();
		TestContext.assertTrue(it.hasNext());
		final long returned = it.next();
		map.ensureCapacity(128);
		final int sizeBeforeRemove = map.size();
		it.remove();
		TestContext.assertFalse(map.containsKey(returned));
		TestContext.assertEquals(sizeBeforeRemove - 1, map.size());
		map.trimToSize();
		TestContext.assertEquals(1, map.size());
	}
	private void checkLongCharMapEmptyFastPaths() {
		final char fallback = '!';
		final long[] emptyKeys = new long[0];
		final char[] emptyVals = new char[0];
		final FastLongSet emptySet = new FastLongSet();
		final FastLongCharMap emptySource = new FastLongCharMap();
		final FastLongCharMap map = new FastLongCharMap(16);
		TestContext.assertTrue((map.get(7L) == '\0'));
		TestContext.assertTrue((map.get(7L, fallback) == fallback));
		TestContext.assertFalse(map.containsKey(7L));
		TestContext.assertFalse(map.containsValue('A'));
		TestContext.assertTrue((map.remove(7L) == '\0'));
		TestContext.assertTrue((map.remove(7L, fallback) == fallback));
		TestContext.assertFalse(map.removeKey(7L));
		TestContext.assertFalse(map.removeEntry(7L, 'A'));
		TestContext.assertFalse(map.removeAllKeys((long[]) null));
		TestContext.assertFalse(map.removeAllKeys(emptyKeys));
		TestContext.assertFalse(map.removeAllKeys((FastLongSet) null));
		TestContext.assertFalse(map.removeAllKeys(emptySet));
		TestContext.assertFalse(map.retainAllKeys((FastLongSet) null));
		TestContext.assertFalse(map.retainAllKeys(emptySet));
		TestContext.assertFalse(map.putAll((FastLongCharMap) null));
		TestContext.assertFalse(map.putAll(emptySource));
		TestContext.assertFalse(map.putAll(emptyKeys, emptyVals));
		map.clear();
		TestContext.assertEquals(0, map.keys().length);
		TestContext.assertEquals(0, map.values().length);
		TestContext.assertTrue(map.keySet().isEmpty());
		TestContext.assertEquals(0, map.hashCode());
		TestContext.assertEquals("{}", map.toString());
		TestContext.assertFalse(map.keyIterator().hasNext());
		TestContext.assertFalse(map.valueIterator().hasNext());
		TestContext.assertTrue(map.putIfAbsent(1L, 'A'));
		TestContext.assertTrue(map.putIfAbsent(0x100000001L, 'Z'));
		TestContext.assertTrue(map.removeKey(1L));
		TestContext.assertTrue(map.removeKey(0x100000001L));
		TestContext.assertEquals(0, map.size());
		TestContext.assertFalse(map.containsKey(1L));
		TestContext.assertFalse(map.containsValue('A'));
		TestContext.assertEquals(0, map.keys().length);
		TestContext.assertEquals(0, map.values().length);
		TestContext.assertEquals(0, map.hashCode());
		TestContext.assertEquals("{}", map.toString());
		TestContext.assertFalse(map.keyIterator().hasNext());
		TestContext.assertFalse(map.valueIterator().hasNext());
		final FastLongCharMap shared = (FastLongCharMap) map.shared();
		final FastLongCharMap unmodifiable = (FastLongCharMap) map.unmodifiable();
		TestContext.assertTrue((shared.get(7L, fallback) == fallback));
		TestContext.assertTrue((unmodifiable.get(7L, fallback) == fallback));
		TestContext.assertFalse(shared.containsKey(7L));
		TestContext.assertFalse(unmodifiable.containsKey(7L));
		TestContext.assertEquals(0, shared.keys().length);
		TestContext.assertEquals(0, unmodifiable.keys().length);
		TestContext.assertEquals("{}", shared.toString());
		TestContext.assertEquals("{}", unmodifiable.toString());
		TestContext.assertFalse(shared.keyIterator().hasNext());
		TestContext.assertFalse(unmodifiable.valueIterator().hasNext());
		shared.clear();
		TestContext.assertTrue(shared.putIfAbsent(7L, 'A'));
		TestContext.assertTrue(unmodifiable.containsKey(7L));
	}
	private void checkLongCharMapViews() {
		final FastLongCharMap source = new FastLongCharMap();
		source.put(Long.MIN_VALUE, 'A');
		source.put(1L, 'Z');
		final FastLongCharMap shared = (FastLongCharMap) source.shared();
		final FastLongCharMap unmodifiable = (FastLongCharMap) source.unmodifiable();
		final FastLongCharMap sharedUnmod = (FastLongCharMap) shared.unmodifiable();
		TestContext.assertTrue(source.equals(shared));
		TestContext.assertTrue(shared.equals(source));
		TestContext.assertTrue(source.equals(unmodifiable));
		TestContext.assertTrue(unmodifiable.equals(source));
		TestContext.assertTrue(shared.equals(sharedUnmod));
		TestContext.assertTrue(sharedUnmod.equals(shared));
		TestContext.assertEquals(source.hashCode(), shared.hashCode());
		TestContext.assertEquals(source.hashCode(), unmodifiable.hashCode());
		TestContext.assertEquals(source.hashCode(), sharedUnmod.hashCode());
		final FastLongIterator kit = (FastLongIterator) shared.keyIterator();
		boolean badRemove = false;
		try { kit.remove(); } catch(final IllegalStateException ex) { badRemove = true; }
		TestContext.assertTrue(badRemove);
		int kCount = 0;
		while(kit.hasNext()) { kit.next(); ++kCount; }
		TestContext.assertEquals(2, kCount);
		boolean noMore = false;
		try { kit.next(); } catch(final NoSuchElementException ex) { noMore = true; }
		TestContext.assertTrue(noMore);
		final FastCharIterator vit = (FastCharIterator) shared.valueIterator();
		TestContext.assertTrue(vit.hasNext());
		vit.next();
		shared.ensureCapacity(64);
		vit.remove();
		TestContext.assertEquals(1, unmodifiable.size());
		TestContext.assertEquals(1, sharedUnmod.size());
		badRemove = false;
		try { vit.remove(); } catch(final IllegalStateException ex) { badRemove = true; }
		TestContext.assertTrue(badRemove);
		final FastLongIterator ukit = (FastLongIterator) unmodifiable.keyIterator();
		ukit.next();
		int rejected = 0;
		try { ukit.remove(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		final FastCharIterator uvit = (FastCharIterator) sharedUnmod.valueIterator();
		uvit.next();
		try { uvit.remove(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.put(7L, 'A'); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putIfAbsent(7L, 'A'); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putAll(new long[] { 7L }, new char[] { 'A' }); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putAll(source); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.remove(Long.MIN_VALUE); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.remove(Long.MIN_VALUE, '!'); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeKey(Long.MIN_VALUE); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeEntry(Long.MIN_VALUE, 'A'); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeAllKeys(new long[] { Long.MIN_VALUE }); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeAllKeys(new FastLongSet()); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.retainAllKeys(new FastLongSet()); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.clear(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.reset(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.trimToSize(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.ensureCapacity(64); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.shared(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		TestContext.assertEquals(18, rejected);
	}
	private void checkLongDoubleMapPutGetRemove() {
		final long k1 = Long.MIN_VALUE;
		final long k2 = 0L;
		final long k3 = 1L;
		final long k4 = 0x100000001L;
		final long kAbsent = 424242424242L;
		final double v1 = 1.25d;
		final double v2 = -2.5d;
		final double v3 = 0.0d;
		final double v4 = 3.75d;
		final double fallback = 9.5d;
		final double wrongVal = 88.5d;
		final FastLongDoubleMap map = new FastLongDoubleMap();
		TestContext.assertEquals(0, map.size());
		TestContext.assertTrue(map.isEmpty());
		TestContext.assertTrue((Double.doubleToLongBits(map.get(k1)) == Double.doubleToLongBits(0.0d)));
		TestContext.assertTrue((Double.doubleToLongBits(map.get(k1, fallback)) == Double.doubleToLongBits(fallback)));
		TestContext.assertFalse(map.containsKey(k1));
		TestContext.assertTrue((Double.doubleToLongBits(map.put(k1, v1)) == Double.doubleToLongBits(0.0d)));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.containsKey(k1));
		TestContext.assertTrue(map.containsValue(v1));
		TestContext.assertTrue((Double.doubleToLongBits(map.get(k1)) == Double.doubleToLongBits(1.25d)));
		TestContext.assertTrue((Double.doubleToLongBits(map.put(k1, v2)) == Double.doubleToLongBits(1.25d)));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue((Double.doubleToLongBits(map.get(k1)) == Double.doubleToLongBits(-2.5d)));
		TestContext.assertFalse(map.putIfAbsent(k1, v1));
		TestContext.assertTrue((Double.doubleToLongBits(map.get(k1)) == Double.doubleToLongBits(-2.5d)));
		TestContext.assertTrue(map.putIfAbsent(k3, v3));
		TestContext.assertEquals(2, map.size());
		TestContext.assertTrue((Double.doubleToLongBits(map.get(k3, fallback)) == Double.doubleToLongBits(0.0d)));
		TestContext.assertFalse(map.containsValue(88.5d));
		final long[] bulkKeys = new long[] { k2, k4 };
		final double[] bulkVals = new double[] { v2, v4 };
		TestContext.assertTrue(map.putAll(bulkKeys, bulkVals));
		TestContext.assertEquals(4, map.size());
		TestContext.assertFalse(map.putAll(bulkKeys, bulkVals));
		TestContext.assertFalse(map.putAll((long[]) null, (double[]) null));
		boolean threw = false;
		try {
			map.putAll(new long[] { k1 }, bulkVals);
		}
		catch(final IllegalArgumentException ex) {
			threw = true;
		}
		TestContext.assertTrue(threw);
		final FastLongDoubleMap copy = new FastLongDoubleMap(map);
		TestContext.assertTrue(map.equals(copy));
		TestContext.assertTrue(copy.equals(map));
		TestContext.assertEquals(map.hashCode(), copy.hashCode());
		TestContext.assertFalse(map.putAll(copy));
		TestContext.assertFalse(map.putAll(map));
		final FastLongDoubleMap fromArrays = new FastLongDoubleMap(bulkKeys, bulkVals);
		TestContext.assertEquals(2, fromArrays.size());
		TestContext.assertTrue(fromArrays.containsKey(k2));
		TestContext.assertTrue(fromArrays.containsKey(k4));
		TestContext.assertEquals(4, map.keys().length);
		TestContext.assertEquals(4, map.values().length);
		final FastLongSet ks = map.keySet();
		TestContext.assertEquals(4, ks.size());
		TestContext.assertTrue(ks.contains(k1));
		TestContext.assertTrue(ks.contains(k4));
		TestContext.assertTrue(map.toString().indexOf('=') >= 0);
		try {
			final FastLongDoubleMap cloned = (FastLongDoubleMap) map.clone();
			TestContext.assertTrue(map.equals(cloned));
			cloned.put(kAbsent, v1);
			TestContext.assertFalse(map.containsKey(kAbsent));
		}
		catch(final UnsupportedOperationException j2meExpected) {}
		catch(final CloneNotSupportedException ex) {
			TestContext.assertTrue(false);
		}
		TestContext.assertFalse(map.removeEntry(k1, wrongVal));
		TestContext.assertTrue(map.removeEntry(k1, v2));
		TestContext.assertFalse(map.containsKey(k1));
		TestContext.assertTrue((Double.doubleToLongBits(map.remove(kAbsent, fallback)) == Double.doubleToLongBits(fallback)));
		TestContext.assertTrue((Double.doubleToLongBits(map.remove(k3)) == Double.doubleToLongBits(0.0d)));
		TestContext.assertTrue(map.removeKey(k2));
		TestContext.assertFalse(map.removeKey(kAbsent));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.containsKey(k4));
		TestContext.assertTrue((Double.doubleToLongBits(map.put(k1, v1)) == Double.doubleToLongBits(0.0d)));
		TestContext.assertTrue(map.removeAllKeys(new long[] { k1 }));
		final FastLongSet remSet = new FastLongSet(new long[] { k4 });
		TestContext.assertTrue(map.putIfAbsent(k2, v2));
		TestContext.assertTrue(map.retainAllKeys(remSet));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.removeAllKeys(remSet));
		TestContext.assertEquals(0, map.size());
	}
	private void checkLongDoubleMapChurn() {
		final FastLongDoubleMap map = new FastLongDoubleMap(16);
		for(int round = 0; round < 256; ++round) {
			for(int i = 0; i < 8; ++i) {
				final long key = (round * 13L + i);
				final double val = (round * 1.5d + i + 0.25d);
				TestContext.assertTrue(map.putIfAbsent(key, val));
			}
			for(int i = 0; i < 8; ++i) {
				final long key = (round * 13L + i);
				TestContext.assertTrue(map.removeKey(key));
			}
			TestContext.assertFalse(map.containsKey((round * 13L + 31L)));
		}
		TestContext.assertEquals(0, map.size());
		final long keep = 9876543210123L;
		for(int i = 0; i < 50; ++i) {
			map.put(keep, 1.25d);
		}
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.removeKey(keep));
		TestContext.assertTrue(map.putIfAbsent(7L, 1.25d));
		TestContext.assertTrue(map.putIfAbsent(9L, -2.5d));
		final FastLongIterator it = (FastLongIterator) map.keyIterator();
		TestContext.assertTrue(it.hasNext());
		final long returned = it.next();
		map.ensureCapacity(128);
		final int sizeBeforeRemove = map.size();
		it.remove();
		TestContext.assertFalse(map.containsKey(returned));
		TestContext.assertEquals(sizeBeforeRemove - 1, map.size());
		map.trimToSize();
		TestContext.assertEquals(1, map.size());
	}
	private void checkLongDoubleMapEmptyFastPaths() {
		final double fallback = 9.5d;
		final long[] emptyKeys = new long[0];
		final double[] emptyVals = new double[0];
		final FastLongSet emptySet = new FastLongSet();
		final FastLongDoubleMap emptySource = new FastLongDoubleMap();
		final FastLongDoubleMap map = new FastLongDoubleMap(16);
		TestContext.assertTrue((Double.doubleToLongBits(map.get(7L)) == Double.doubleToLongBits(0.0d)));
		TestContext.assertTrue((Double.doubleToLongBits(map.get(7L, fallback)) == Double.doubleToLongBits(fallback)));
		TestContext.assertFalse(map.containsKey(7L));
		TestContext.assertFalse(map.containsValue(1.25d));
		TestContext.assertTrue((Double.doubleToLongBits(map.remove(7L)) == Double.doubleToLongBits(0.0d)));
		TestContext.assertTrue((Double.doubleToLongBits(map.remove(7L, fallback)) == Double.doubleToLongBits(fallback)));
		TestContext.assertFalse(map.removeKey(7L));
		TestContext.assertFalse(map.removeEntry(7L, 1.25d));
		TestContext.assertFalse(map.removeAllKeys((long[]) null));
		TestContext.assertFalse(map.removeAllKeys(emptyKeys));
		TestContext.assertFalse(map.removeAllKeys((FastLongSet) null));
		TestContext.assertFalse(map.removeAllKeys(emptySet));
		TestContext.assertFalse(map.retainAllKeys((FastLongSet) null));
		TestContext.assertFalse(map.retainAllKeys(emptySet));
		TestContext.assertFalse(map.putAll((FastLongDoubleMap) null));
		TestContext.assertFalse(map.putAll(emptySource));
		TestContext.assertFalse(map.putAll(emptyKeys, emptyVals));
		map.clear();
		TestContext.assertEquals(0, map.keys().length);
		TestContext.assertEquals(0, map.values().length);
		TestContext.assertTrue(map.keySet().isEmpty());
		TestContext.assertEquals(0, map.hashCode());
		TestContext.assertEquals("{}", map.toString());
		TestContext.assertFalse(map.keyIterator().hasNext());
		TestContext.assertFalse(map.valueIterator().hasNext());
		TestContext.assertTrue(map.putIfAbsent(1L, 1.25d));
		TestContext.assertTrue(map.putIfAbsent(0x100000001L, -2.5d));
		TestContext.assertTrue(map.removeKey(1L));
		TestContext.assertTrue(map.removeKey(0x100000001L));
		TestContext.assertEquals(0, map.size());
		TestContext.assertFalse(map.containsKey(1L));
		TestContext.assertFalse(map.containsValue(1.25d));
		TestContext.assertEquals(0, map.keys().length);
		TestContext.assertEquals(0, map.values().length);
		TestContext.assertEquals(0, map.hashCode());
		TestContext.assertEquals("{}", map.toString());
		TestContext.assertFalse(map.keyIterator().hasNext());
		TestContext.assertFalse(map.valueIterator().hasNext());
		final FastLongDoubleMap shared = (FastLongDoubleMap) map.shared();
		final FastLongDoubleMap unmodifiable = (FastLongDoubleMap) map.unmodifiable();
		TestContext.assertTrue((Double.doubleToLongBits(shared.get(7L, fallback)) == Double.doubleToLongBits(fallback)));
		TestContext.assertTrue((Double.doubleToLongBits(unmodifiable.get(7L, fallback)) == Double.doubleToLongBits(fallback)));
		TestContext.assertFalse(shared.containsKey(7L));
		TestContext.assertFalse(unmodifiable.containsKey(7L));
		TestContext.assertEquals(0, shared.keys().length);
		TestContext.assertEquals(0, unmodifiable.keys().length);
		TestContext.assertEquals("{}", shared.toString());
		TestContext.assertEquals("{}", unmodifiable.toString());
		TestContext.assertFalse(shared.keyIterator().hasNext());
		TestContext.assertFalse(unmodifiable.valueIterator().hasNext());
		shared.clear();
		TestContext.assertTrue(shared.putIfAbsent(7L, 1.25d));
		TestContext.assertTrue(unmodifiable.containsKey(7L));
	}
	private void checkLongDoubleMapViews() {
		final FastLongDoubleMap source = new FastLongDoubleMap();
		source.put(Long.MIN_VALUE, 1.25d);
		source.put(1L, -2.5d);
		final FastLongDoubleMap shared = (FastLongDoubleMap) source.shared();
		final FastLongDoubleMap unmodifiable = (FastLongDoubleMap) source.unmodifiable();
		final FastLongDoubleMap sharedUnmod = (FastLongDoubleMap) shared.unmodifiable();
		TestContext.assertTrue(source.equals(shared));
		TestContext.assertTrue(shared.equals(source));
		TestContext.assertTrue(source.equals(unmodifiable));
		TestContext.assertTrue(unmodifiable.equals(source));
		TestContext.assertTrue(shared.equals(sharedUnmod));
		TestContext.assertTrue(sharedUnmod.equals(shared));
		TestContext.assertEquals(source.hashCode(), shared.hashCode());
		TestContext.assertEquals(source.hashCode(), unmodifiable.hashCode());
		TestContext.assertEquals(source.hashCode(), sharedUnmod.hashCode());
		final FastLongIterator kit = (FastLongIterator) shared.keyIterator();
		boolean badRemove = false;
		try { kit.remove(); } catch(final IllegalStateException ex) { badRemove = true; }
		TestContext.assertTrue(badRemove);
		int kCount = 0;
		while(kit.hasNext()) { kit.next(); ++kCount; }
		TestContext.assertEquals(2, kCount);
		boolean noMore = false;
		try { kit.next(); } catch(final NoSuchElementException ex) { noMore = true; }
		TestContext.assertTrue(noMore);
		final FastDoubleIterator vit = (FastDoubleIterator) shared.valueIterator();
		TestContext.assertTrue(vit.hasNext());
		vit.next();
		shared.ensureCapacity(64);
		vit.remove();
		TestContext.assertEquals(1, unmodifiable.size());
		TestContext.assertEquals(1, sharedUnmod.size());
		badRemove = false;
		try { vit.remove(); } catch(final IllegalStateException ex) { badRemove = true; }
		TestContext.assertTrue(badRemove);
		final FastLongIterator ukit = (FastLongIterator) unmodifiable.keyIterator();
		ukit.next();
		int rejected = 0;
		try { ukit.remove(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		final FastDoubleIterator uvit = (FastDoubleIterator) sharedUnmod.valueIterator();
		uvit.next();
		try { uvit.remove(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.put(7L, 1.25d); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putIfAbsent(7L, 1.25d); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putAll(new long[] { 7L }, new double[] { 1.25d }); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putAll(source); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.remove(Long.MIN_VALUE); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.remove(Long.MIN_VALUE, 9.5d); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeKey(Long.MIN_VALUE); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeEntry(Long.MIN_VALUE, 1.25d); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeAllKeys(new long[] { Long.MIN_VALUE }); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeAllKeys(new FastLongSet()); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.retainAllKeys(new FastLongSet()); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.clear(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.reset(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.trimToSize(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.ensureCapacity(64); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.shared(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		TestContext.assertEquals(18, rejected);
	}
	private void checkLongFloatMapPutGetRemove() {
		final long k1 = Long.MIN_VALUE;
		final long k2 = 0L;
		final long k3 = 1L;
		final long k4 = 0x100000001L;
		final long kAbsent = 424242424242L;
		final float v1 = 1.25f;
		final float v2 = -2.5f;
		final float v3 = 0.0f;
		final float v4 = 3.75f;
		final float fallback = 9.5f;
		final float wrongVal = 88.5f;
		final FastLongFloatMap map = new FastLongFloatMap();
		TestContext.assertEquals(0, map.size());
		TestContext.assertTrue(map.isEmpty());
		TestContext.assertTrue((Float.floatToIntBits(map.get(k1)) == Float.floatToIntBits(0.0f)));
		TestContext.assertTrue((Float.floatToIntBits(map.get(k1, fallback)) == Float.floatToIntBits(fallback)));
		TestContext.assertFalse(map.containsKey(k1));
		TestContext.assertTrue((Float.floatToIntBits(map.put(k1, v1)) == Float.floatToIntBits(0.0f)));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.containsKey(k1));
		TestContext.assertTrue(map.containsValue(v1));
		TestContext.assertTrue((Float.floatToIntBits(map.get(k1)) == Float.floatToIntBits(1.25f)));
		TestContext.assertTrue((Float.floatToIntBits(map.put(k1, v2)) == Float.floatToIntBits(1.25f)));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue((Float.floatToIntBits(map.get(k1)) == Float.floatToIntBits(-2.5f)));
		TestContext.assertFalse(map.putIfAbsent(k1, v1));
		TestContext.assertTrue((Float.floatToIntBits(map.get(k1)) == Float.floatToIntBits(-2.5f)));
		TestContext.assertTrue(map.putIfAbsent(k3, v3));
		TestContext.assertEquals(2, map.size());
		TestContext.assertTrue((Float.floatToIntBits(map.get(k3, fallback)) == Float.floatToIntBits(0.0f)));
		TestContext.assertFalse(map.containsValue(88.5f));
		final long[] bulkKeys = new long[] { k2, k4 };
		final float[] bulkVals = new float[] { v2, v4 };
		TestContext.assertTrue(map.putAll(bulkKeys, bulkVals));
		TestContext.assertEquals(4, map.size());
		TestContext.assertFalse(map.putAll(bulkKeys, bulkVals));
		TestContext.assertFalse(map.putAll((long[]) null, (float[]) null));
		boolean threw = false;
		try {
			map.putAll(new long[] { k1 }, bulkVals);
		}
		catch(final IllegalArgumentException ex) {
			threw = true;
		}
		TestContext.assertTrue(threw);
		final FastLongFloatMap copy = new FastLongFloatMap(map);
		TestContext.assertTrue(map.equals(copy));
		TestContext.assertTrue(copy.equals(map));
		TestContext.assertEquals(map.hashCode(), copy.hashCode());
		TestContext.assertFalse(map.putAll(copy));
		TestContext.assertFalse(map.putAll(map));
		final FastLongFloatMap fromArrays = new FastLongFloatMap(bulkKeys, bulkVals);
		TestContext.assertEquals(2, fromArrays.size());
		TestContext.assertTrue(fromArrays.containsKey(k2));
		TestContext.assertTrue(fromArrays.containsKey(k4));
		TestContext.assertEquals(4, map.keys().length);
		TestContext.assertEquals(4, map.values().length);
		final FastLongSet ks = map.keySet();
		TestContext.assertEquals(4, ks.size());
		TestContext.assertTrue(ks.contains(k1));
		TestContext.assertTrue(ks.contains(k4));
		TestContext.assertTrue(map.toString().indexOf('=') >= 0);
		try {
			final FastLongFloatMap cloned = (FastLongFloatMap) map.clone();
			TestContext.assertTrue(map.equals(cloned));
			cloned.put(kAbsent, v1);
			TestContext.assertFalse(map.containsKey(kAbsent));
		}
		catch(final UnsupportedOperationException j2meExpected) {}
		catch(final CloneNotSupportedException ex) {
			TestContext.assertTrue(false);
		}
		TestContext.assertFalse(map.removeEntry(k1, wrongVal));
		TestContext.assertTrue(map.removeEntry(k1, v2));
		TestContext.assertFalse(map.containsKey(k1));
		TestContext.assertTrue((Float.floatToIntBits(map.remove(kAbsent, fallback)) == Float.floatToIntBits(fallback)));
		TestContext.assertTrue((Float.floatToIntBits(map.remove(k3)) == Float.floatToIntBits(0.0f)));
		TestContext.assertTrue(map.removeKey(k2));
		TestContext.assertFalse(map.removeKey(kAbsent));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.containsKey(k4));
		TestContext.assertTrue((Float.floatToIntBits(map.put(k1, v1)) == Float.floatToIntBits(0.0f)));
		TestContext.assertTrue(map.removeAllKeys(new long[] { k1 }));
		final FastLongSet remSet = new FastLongSet(new long[] { k4 });
		TestContext.assertTrue(map.putIfAbsent(k2, v2));
		TestContext.assertTrue(map.retainAllKeys(remSet));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.removeAllKeys(remSet));
		TestContext.assertEquals(0, map.size());
	}
	private void checkLongFloatMapChurn() {
		final FastLongFloatMap map = new FastLongFloatMap(16);
		for(int round = 0; round < 256; ++round) {
			for(int i = 0; i < 8; ++i) {
				final long key = (round * 13L + i);
				final float val = (round * 1.5f + i + 0.25f);
				TestContext.assertTrue(map.putIfAbsent(key, val));
			}
			for(int i = 0; i < 8; ++i) {
				final long key = (round * 13L + i);
				TestContext.assertTrue(map.removeKey(key));
			}
			TestContext.assertFalse(map.containsKey((round * 13L + 31L)));
		}
		TestContext.assertEquals(0, map.size());
		final long keep = 9876543210123L;
		for(int i = 0; i < 50; ++i) {
			map.put(keep, 1.25f);
		}
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.removeKey(keep));
		TestContext.assertTrue(map.putIfAbsent(7L, 1.25f));
		TestContext.assertTrue(map.putIfAbsent(9L, -2.5f));
		final FastLongIterator it = (FastLongIterator) map.keyIterator();
		TestContext.assertTrue(it.hasNext());
		final long returned = it.next();
		map.ensureCapacity(128);
		final int sizeBeforeRemove = map.size();
		it.remove();
		TestContext.assertFalse(map.containsKey(returned));
		TestContext.assertEquals(sizeBeforeRemove - 1, map.size());
		map.trimToSize();
		TestContext.assertEquals(1, map.size());
	}
	private void checkLongFloatMapEmptyFastPaths() {
		final float fallback = 9.5f;
		final long[] emptyKeys = new long[0];
		final float[] emptyVals = new float[0];
		final FastLongSet emptySet = new FastLongSet();
		final FastLongFloatMap emptySource = new FastLongFloatMap();
		final FastLongFloatMap map = new FastLongFloatMap(16);
		TestContext.assertTrue((Float.floatToIntBits(map.get(7L)) == Float.floatToIntBits(0.0f)));
		TestContext.assertTrue((Float.floatToIntBits(map.get(7L, fallback)) == Float.floatToIntBits(fallback)));
		TestContext.assertFalse(map.containsKey(7L));
		TestContext.assertFalse(map.containsValue(1.25f));
		TestContext.assertTrue((Float.floatToIntBits(map.remove(7L)) == Float.floatToIntBits(0.0f)));
		TestContext.assertTrue((Float.floatToIntBits(map.remove(7L, fallback)) == Float.floatToIntBits(fallback)));
		TestContext.assertFalse(map.removeKey(7L));
		TestContext.assertFalse(map.removeEntry(7L, 1.25f));
		TestContext.assertFalse(map.removeAllKeys((long[]) null));
		TestContext.assertFalse(map.removeAllKeys(emptyKeys));
		TestContext.assertFalse(map.removeAllKeys((FastLongSet) null));
		TestContext.assertFalse(map.removeAllKeys(emptySet));
		TestContext.assertFalse(map.retainAllKeys((FastLongSet) null));
		TestContext.assertFalse(map.retainAllKeys(emptySet));
		TestContext.assertFalse(map.putAll((FastLongFloatMap) null));
		TestContext.assertFalse(map.putAll(emptySource));
		TestContext.assertFalse(map.putAll(emptyKeys, emptyVals));
		map.clear();
		TestContext.assertEquals(0, map.keys().length);
		TestContext.assertEquals(0, map.values().length);
		TestContext.assertTrue(map.keySet().isEmpty());
		TestContext.assertEquals(0, map.hashCode());
		TestContext.assertEquals("{}", map.toString());
		TestContext.assertFalse(map.keyIterator().hasNext());
		TestContext.assertFalse(map.valueIterator().hasNext());
		TestContext.assertTrue(map.putIfAbsent(1L, 1.25f));
		TestContext.assertTrue(map.putIfAbsent(0x100000001L, -2.5f));
		TestContext.assertTrue(map.removeKey(1L));
		TestContext.assertTrue(map.removeKey(0x100000001L));
		TestContext.assertEquals(0, map.size());
		TestContext.assertFalse(map.containsKey(1L));
		TestContext.assertFalse(map.containsValue(1.25f));
		TestContext.assertEquals(0, map.keys().length);
		TestContext.assertEquals(0, map.values().length);
		TestContext.assertEquals(0, map.hashCode());
		TestContext.assertEquals("{}", map.toString());
		TestContext.assertFalse(map.keyIterator().hasNext());
		TestContext.assertFalse(map.valueIterator().hasNext());
		final FastLongFloatMap shared = (FastLongFloatMap) map.shared();
		final FastLongFloatMap unmodifiable = (FastLongFloatMap) map.unmodifiable();
		TestContext.assertTrue((Float.floatToIntBits(shared.get(7L, fallback)) == Float.floatToIntBits(fallback)));
		TestContext.assertTrue((Float.floatToIntBits(unmodifiable.get(7L, fallback)) == Float.floatToIntBits(fallback)));
		TestContext.assertFalse(shared.containsKey(7L));
		TestContext.assertFalse(unmodifiable.containsKey(7L));
		TestContext.assertEquals(0, shared.keys().length);
		TestContext.assertEquals(0, unmodifiable.keys().length);
		TestContext.assertEquals("{}", shared.toString());
		TestContext.assertEquals("{}", unmodifiable.toString());
		TestContext.assertFalse(shared.keyIterator().hasNext());
		TestContext.assertFalse(unmodifiable.valueIterator().hasNext());
		shared.clear();
		TestContext.assertTrue(shared.putIfAbsent(7L, 1.25f));
		TestContext.assertTrue(unmodifiable.containsKey(7L));
	}
	private void checkLongFloatMapViews() {
		final FastLongFloatMap source = new FastLongFloatMap();
		source.put(Long.MIN_VALUE, 1.25f);
		source.put(1L, -2.5f);
		final FastLongFloatMap shared = (FastLongFloatMap) source.shared();
		final FastLongFloatMap unmodifiable = (FastLongFloatMap) source.unmodifiable();
		final FastLongFloatMap sharedUnmod = (FastLongFloatMap) shared.unmodifiable();
		TestContext.assertTrue(source.equals(shared));
		TestContext.assertTrue(shared.equals(source));
		TestContext.assertTrue(source.equals(unmodifiable));
		TestContext.assertTrue(unmodifiable.equals(source));
		TestContext.assertTrue(shared.equals(sharedUnmod));
		TestContext.assertTrue(sharedUnmod.equals(shared));
		TestContext.assertEquals(source.hashCode(), shared.hashCode());
		TestContext.assertEquals(source.hashCode(), unmodifiable.hashCode());
		TestContext.assertEquals(source.hashCode(), sharedUnmod.hashCode());
		final FastLongIterator kit = (FastLongIterator) shared.keyIterator();
		boolean badRemove = false;
		try { kit.remove(); } catch(final IllegalStateException ex) { badRemove = true; }
		TestContext.assertTrue(badRemove);
		int kCount = 0;
		while(kit.hasNext()) { kit.next(); ++kCount; }
		TestContext.assertEquals(2, kCount);
		boolean noMore = false;
		try { kit.next(); } catch(final NoSuchElementException ex) { noMore = true; }
		TestContext.assertTrue(noMore);
		final FastFloatIterator vit = (FastFloatIterator) shared.valueIterator();
		TestContext.assertTrue(vit.hasNext());
		vit.next();
		shared.ensureCapacity(64);
		vit.remove();
		TestContext.assertEquals(1, unmodifiable.size());
		TestContext.assertEquals(1, sharedUnmod.size());
		badRemove = false;
		try { vit.remove(); } catch(final IllegalStateException ex) { badRemove = true; }
		TestContext.assertTrue(badRemove);
		final FastLongIterator ukit = (FastLongIterator) unmodifiable.keyIterator();
		ukit.next();
		int rejected = 0;
		try { ukit.remove(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		final FastFloatIterator uvit = (FastFloatIterator) sharedUnmod.valueIterator();
		uvit.next();
		try { uvit.remove(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.put(7L, 1.25f); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putIfAbsent(7L, 1.25f); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putAll(new long[] { 7L }, new float[] { 1.25f }); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putAll(source); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.remove(Long.MIN_VALUE); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.remove(Long.MIN_VALUE, 9.5f); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeKey(Long.MIN_VALUE); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeEntry(Long.MIN_VALUE, 1.25f); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeAllKeys(new long[] { Long.MIN_VALUE }); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeAllKeys(new FastLongSet()); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.retainAllKeys(new FastLongSet()); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.clear(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.reset(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.trimToSize(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.ensureCapacity(64); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.shared(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		TestContext.assertEquals(18, rejected);
	}
	private void checkLongIntMapPutGetRemove() {
		final long k1 = Long.MIN_VALUE;
		final long k2 = 0L;
		final long k3 = 1L;
		final long k4 = 0x100000001L;
		final long kAbsent = 424242424242L;
		final int v1 = 11;
		final int v2 = -22;
		final int v3 = 0;
		final int v4 = 333;
		final int fallback = 99;
		final int wrongVal = 555;
		final FastLongIntMap map = new FastLongIntMap();
		TestContext.assertEquals(0, map.size());
		TestContext.assertTrue(map.isEmpty());
		TestContext.assertTrue((map.get(k1) == 0));
		TestContext.assertTrue((map.get(k1, fallback) == fallback));
		TestContext.assertFalse(map.containsKey(k1));
		TestContext.assertTrue((map.put(k1, v1) == 0));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.containsKey(k1));
		TestContext.assertTrue(map.containsValue(v1));
		TestContext.assertTrue((map.get(k1) == 11));
		TestContext.assertTrue((map.put(k1, v2) == 11));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue((map.get(k1) == -22));
		TestContext.assertFalse(map.putIfAbsent(k1, v1));
		TestContext.assertTrue((map.get(k1) == -22));
		TestContext.assertTrue(map.putIfAbsent(k3, v3));
		TestContext.assertEquals(2, map.size());
		TestContext.assertTrue((map.get(k3, fallback) == 0));
		TestContext.assertFalse(map.containsValue(555));
		final long[] bulkKeys = new long[] { k2, k4 };
		final int[] bulkVals = new int[] { v2, v4 };
		TestContext.assertTrue(map.putAll(bulkKeys, bulkVals));
		TestContext.assertEquals(4, map.size());
		TestContext.assertFalse(map.putAll(bulkKeys, bulkVals));
		TestContext.assertFalse(map.putAll((long[]) null, (int[]) null));
		boolean threw = false;
		try {
			map.putAll(new long[] { k1 }, bulkVals);
		}
		catch(final IllegalArgumentException ex) {
			threw = true;
		}
		TestContext.assertTrue(threw);
		final FastLongIntMap copy = new FastLongIntMap(map);
		TestContext.assertTrue(map.equals(copy));
		TestContext.assertTrue(copy.equals(map));
		TestContext.assertEquals(map.hashCode(), copy.hashCode());
		TestContext.assertFalse(map.putAll(copy));
		TestContext.assertFalse(map.putAll(map));
		final FastLongIntMap fromArrays = new FastLongIntMap(bulkKeys, bulkVals);
		TestContext.assertEquals(2, fromArrays.size());
		TestContext.assertTrue(fromArrays.containsKey(k2));
		TestContext.assertTrue(fromArrays.containsKey(k4));
		TestContext.assertEquals(4, map.keys().length);
		TestContext.assertEquals(4, map.values().length);
		final FastLongSet ks = map.keySet();
		TestContext.assertEquals(4, ks.size());
		TestContext.assertTrue(ks.contains(k1));
		TestContext.assertTrue(ks.contains(k4));
		TestContext.assertTrue(map.toString().indexOf('=') >= 0);
		try {
			final FastLongIntMap cloned = (FastLongIntMap) map.clone();
			TestContext.assertTrue(map.equals(cloned));
			cloned.put(kAbsent, v1);
			TestContext.assertFalse(map.containsKey(kAbsent));
		}
		catch(final UnsupportedOperationException j2meExpected) {}
		catch(final CloneNotSupportedException ex) {
			TestContext.assertTrue(false);
		}
		TestContext.assertFalse(map.removeEntry(k1, wrongVal));
		TestContext.assertTrue(map.removeEntry(k1, v2));
		TestContext.assertFalse(map.containsKey(k1));
		TestContext.assertTrue((map.remove(kAbsent, fallback) == fallback));
		TestContext.assertTrue((map.remove(k3) == 0));
		TestContext.assertTrue(map.removeKey(k2));
		TestContext.assertFalse(map.removeKey(kAbsent));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.containsKey(k4));
		TestContext.assertTrue((map.put(k1, v1) == 0));
		TestContext.assertTrue(map.removeAllKeys(new long[] { k1 }));
		final FastLongSet remSet = new FastLongSet(new long[] { k4 });
		TestContext.assertTrue(map.putIfAbsent(k2, v2));
		TestContext.assertTrue(map.retainAllKeys(remSet));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.removeAllKeys(remSet));
		TestContext.assertEquals(0, map.size());
	}
	private void checkLongIntMapChurn() {
		final FastLongIntMap map = new FastLongIntMap(16);
		for(int round = 0; round < 256; ++round) {
			for(int i = 0; i < 8; ++i) {
				final long key = (round * 13L + i);
				final int val = (round * 7 + i);
				TestContext.assertTrue(map.putIfAbsent(key, val));
			}
			for(int i = 0; i < 8; ++i) {
				final long key = (round * 13L + i);
				TestContext.assertTrue(map.removeKey(key));
			}
			TestContext.assertFalse(map.containsKey((round * 13L + 31L)));
		}
		TestContext.assertEquals(0, map.size());
		final long keep = 9876543210123L;
		for(int i = 0; i < 50; ++i) {
			map.put(keep, 11);
		}
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.removeKey(keep));
		TestContext.assertTrue(map.putIfAbsent(7L, 11));
		TestContext.assertTrue(map.putIfAbsent(9L, -22));
		final FastLongIterator it = (FastLongIterator) map.keyIterator();
		TestContext.assertTrue(it.hasNext());
		final long returned = it.next();
		map.ensureCapacity(128);
		final int sizeBeforeRemove = map.size();
		it.remove();
		TestContext.assertFalse(map.containsKey(returned));
		TestContext.assertEquals(sizeBeforeRemove - 1, map.size());
		map.trimToSize();
		TestContext.assertEquals(1, map.size());
	}
	private void checkLongIntMapEmptyFastPaths() {
		final int fallback = 99;
		final long[] emptyKeys = new long[0];
		final int[] emptyVals = new int[0];
		final FastLongSet emptySet = new FastLongSet();
		final FastLongIntMap emptySource = new FastLongIntMap();
		final FastLongIntMap map = new FastLongIntMap(16);
		TestContext.assertTrue((map.get(7L) == 0));
		TestContext.assertTrue((map.get(7L, fallback) == fallback));
		TestContext.assertFalse(map.containsKey(7L));
		TestContext.assertFalse(map.containsValue(11));
		TestContext.assertTrue((map.remove(7L) == 0));
		TestContext.assertTrue((map.remove(7L, fallback) == fallback));
		TestContext.assertFalse(map.removeKey(7L));
		TestContext.assertFalse(map.removeEntry(7L, 11));
		TestContext.assertFalse(map.removeAllKeys((long[]) null));
		TestContext.assertFalse(map.removeAllKeys(emptyKeys));
		TestContext.assertFalse(map.removeAllKeys((FastLongSet) null));
		TestContext.assertFalse(map.removeAllKeys(emptySet));
		TestContext.assertFalse(map.retainAllKeys((FastLongSet) null));
		TestContext.assertFalse(map.retainAllKeys(emptySet));
		TestContext.assertFalse(map.putAll((FastLongIntMap) null));
		TestContext.assertFalse(map.putAll(emptySource));
		TestContext.assertFalse(map.putAll(emptyKeys, emptyVals));
		map.clear();
		TestContext.assertEquals(0, map.keys().length);
		TestContext.assertEquals(0, map.values().length);
		TestContext.assertTrue(map.keySet().isEmpty());
		TestContext.assertEquals(0, map.hashCode());
		TestContext.assertEquals("{}", map.toString());
		TestContext.assertFalse(map.keyIterator().hasNext());
		TestContext.assertFalse(map.valueIterator().hasNext());
		TestContext.assertTrue(map.putIfAbsent(1L, 11));
		TestContext.assertTrue(map.putIfAbsent(0x100000001L, -22));
		TestContext.assertTrue(map.removeKey(1L));
		TestContext.assertTrue(map.removeKey(0x100000001L));
		TestContext.assertEquals(0, map.size());
		TestContext.assertFalse(map.containsKey(1L));
		TestContext.assertFalse(map.containsValue(11));
		TestContext.assertEquals(0, map.keys().length);
		TestContext.assertEquals(0, map.values().length);
		TestContext.assertEquals(0, map.hashCode());
		TestContext.assertEquals("{}", map.toString());
		TestContext.assertFalse(map.keyIterator().hasNext());
		TestContext.assertFalse(map.valueIterator().hasNext());
		final FastLongIntMap shared = (FastLongIntMap) map.shared();
		final FastLongIntMap unmodifiable = (FastLongIntMap) map.unmodifiable();
		TestContext.assertTrue((shared.get(7L, fallback) == fallback));
		TestContext.assertTrue((unmodifiable.get(7L, fallback) == fallback));
		TestContext.assertFalse(shared.containsKey(7L));
		TestContext.assertFalse(unmodifiable.containsKey(7L));
		TestContext.assertEquals(0, shared.keys().length);
		TestContext.assertEquals(0, unmodifiable.keys().length);
		TestContext.assertEquals("{}", shared.toString());
		TestContext.assertEquals("{}", unmodifiable.toString());
		TestContext.assertFalse(shared.keyIterator().hasNext());
		TestContext.assertFalse(unmodifiable.valueIterator().hasNext());
		shared.clear();
		TestContext.assertTrue(shared.putIfAbsent(7L, 11));
		TestContext.assertTrue(unmodifiable.containsKey(7L));
	}
	private void checkLongIntMapViews() {
		final FastLongIntMap source = new FastLongIntMap();
		source.put(Long.MIN_VALUE, 11);
		source.put(1L, -22);
		final FastLongIntMap shared = (FastLongIntMap) source.shared();
		final FastLongIntMap unmodifiable = (FastLongIntMap) source.unmodifiable();
		final FastLongIntMap sharedUnmod = (FastLongIntMap) shared.unmodifiable();
		TestContext.assertTrue(source.equals(shared));
		TestContext.assertTrue(shared.equals(source));
		TestContext.assertTrue(source.equals(unmodifiable));
		TestContext.assertTrue(unmodifiable.equals(source));
		TestContext.assertTrue(shared.equals(sharedUnmod));
		TestContext.assertTrue(sharedUnmod.equals(shared));
		TestContext.assertEquals(source.hashCode(), shared.hashCode());
		TestContext.assertEquals(source.hashCode(), unmodifiable.hashCode());
		TestContext.assertEquals(source.hashCode(), sharedUnmod.hashCode());
		final FastLongIterator kit = (FastLongIterator) shared.keyIterator();
		boolean badRemove = false;
		try { kit.remove(); } catch(final IllegalStateException ex) { badRemove = true; }
		TestContext.assertTrue(badRemove);
		int kCount = 0;
		while(kit.hasNext()) { kit.next(); ++kCount; }
		TestContext.assertEquals(2, kCount);
		boolean noMore = false;
		try { kit.next(); } catch(final NoSuchElementException ex) { noMore = true; }
		TestContext.assertTrue(noMore);
		final FastIntIterator vit = (FastIntIterator) shared.valueIterator();
		TestContext.assertTrue(vit.hasNext());
		vit.next();
		shared.ensureCapacity(64);
		vit.remove();
		TestContext.assertEquals(1, unmodifiable.size());
		TestContext.assertEquals(1, sharedUnmod.size());
		badRemove = false;
		try { vit.remove(); } catch(final IllegalStateException ex) { badRemove = true; }
		TestContext.assertTrue(badRemove);
		final FastLongIterator ukit = (FastLongIterator) unmodifiable.keyIterator();
		ukit.next();
		int rejected = 0;
		try { ukit.remove(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		final FastIntIterator uvit = (FastIntIterator) sharedUnmod.valueIterator();
		uvit.next();
		try { uvit.remove(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.put(7L, 11); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putIfAbsent(7L, 11); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putAll(new long[] { 7L }, new int[] { 11 }); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putAll(source); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.remove(Long.MIN_VALUE); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.remove(Long.MIN_VALUE, 99); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeKey(Long.MIN_VALUE); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeEntry(Long.MIN_VALUE, 11); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeAllKeys(new long[] { Long.MIN_VALUE }); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeAllKeys(new FastLongSet()); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.retainAllKeys(new FastLongSet()); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.clear(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.reset(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.trimToSize(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.ensureCapacity(64); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.shared(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		TestContext.assertEquals(18, rejected);
	}
	private void checkLongLongMapPutGetRemove() {
		final long k1 = Long.MIN_VALUE;
		final long k2 = 0L;
		final long k3 = 1L;
		final long k4 = 0x100000001L;
		final long kAbsent = 424242424242L;
		final long v1 = 11L;
		final long v2 = -22L;
		final long v3 = 0L;
		final long v4 = 0x200000001L;
		final long fallback = 99L;
		final long wrongVal = 555L;
		final FastLongLongMap map = new FastLongLongMap();
		TestContext.assertEquals(0, map.size());
		TestContext.assertTrue(map.isEmpty());
		TestContext.assertTrue((map.get(k1) == 0L));
		TestContext.assertTrue((map.get(k1, fallback) == fallback));
		TestContext.assertFalse(map.containsKey(k1));
		TestContext.assertTrue((map.put(k1, v1) == 0L));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.containsKey(k1));
		TestContext.assertTrue(map.containsValue(v1));
		TestContext.assertTrue((map.get(k1) == 11L));
		TestContext.assertTrue((map.put(k1, v2) == 11L));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue((map.get(k1) == -22L));
		TestContext.assertFalse(map.putIfAbsent(k1, v1));
		TestContext.assertTrue((map.get(k1) == -22L));
		TestContext.assertTrue(map.putIfAbsent(k3, v3));
		TestContext.assertEquals(2, map.size());
		TestContext.assertTrue((map.get(k3, fallback) == 0L));
		TestContext.assertFalse(map.containsValue(555L));
		final long[] bulkKeys = new long[] { k2, k4 };
		final long[] bulkVals = new long[] { v2, v4 };
		TestContext.assertTrue(map.putAll(bulkKeys, bulkVals));
		TestContext.assertEquals(4, map.size());
		TestContext.assertFalse(map.putAll(bulkKeys, bulkVals));
		TestContext.assertFalse(map.putAll((long[]) null, (long[]) null));
		boolean threw = false;
		try {
			map.putAll(new long[] { k1 }, bulkVals);
		}
		catch(final IllegalArgumentException ex) {
			threw = true;
		}
		TestContext.assertTrue(threw);
		final FastLongLongMap copy = new FastLongLongMap(map);
		TestContext.assertTrue(map.equals(copy));
		TestContext.assertTrue(copy.equals(map));
		TestContext.assertEquals(map.hashCode(), copy.hashCode());
		TestContext.assertFalse(map.putAll(copy));
		TestContext.assertFalse(map.putAll(map));
		final FastLongLongMap fromArrays = new FastLongLongMap(bulkKeys, bulkVals);
		TestContext.assertEquals(2, fromArrays.size());
		TestContext.assertTrue(fromArrays.containsKey(k2));
		TestContext.assertTrue(fromArrays.containsKey(k4));
		TestContext.assertEquals(4, map.keys().length);
		TestContext.assertEquals(4, map.values().length);
		final FastLongSet ks = map.keySet();
		TestContext.assertEquals(4, ks.size());
		TestContext.assertTrue(ks.contains(k1));
		TestContext.assertTrue(ks.contains(k4));
		TestContext.assertTrue(map.toString().indexOf('=') >= 0);
		try {
			final FastLongLongMap cloned = (FastLongLongMap) map.clone();
			TestContext.assertTrue(map.equals(cloned));
			cloned.put(kAbsent, v1);
			TestContext.assertFalse(map.containsKey(kAbsent));
		}
		catch(final UnsupportedOperationException j2meExpected) {}
		catch(final CloneNotSupportedException ex) {
			TestContext.assertTrue(false);
		}
		TestContext.assertFalse(map.removeEntry(k1, wrongVal));
		TestContext.assertTrue(map.removeEntry(k1, v2));
		TestContext.assertFalse(map.containsKey(k1));
		TestContext.assertTrue((map.remove(kAbsent, fallback) == fallback));
		TestContext.assertTrue((map.remove(k3) == 0L));
		TestContext.assertTrue(map.removeKey(k2));
		TestContext.assertFalse(map.removeKey(kAbsent));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.containsKey(k4));
		TestContext.assertTrue((map.put(k1, v1) == 0L));
		TestContext.assertTrue(map.removeAllKeys(new long[] { k1 }));
		final FastLongSet remSet = new FastLongSet(new long[] { k4 });
		TestContext.assertTrue(map.putIfAbsent(k2, v2));
		TestContext.assertTrue(map.retainAllKeys(remSet));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.removeAllKeys(remSet));
		TestContext.assertEquals(0, map.size());
	}
	private void checkLongLongMapChurn() {
		final FastLongLongMap map = new FastLongLongMap(16);
		for(int round = 0; round < 256; ++round) {
			for(int i = 0; i < 8; ++i) {
				final long key = (round * 13L + i);
				final long val = (round * 7L + i);
				TestContext.assertTrue(map.putIfAbsent(key, val));
			}
			for(int i = 0; i < 8; ++i) {
				final long key = (round * 13L + i);
				TestContext.assertTrue(map.removeKey(key));
			}
			TestContext.assertFalse(map.containsKey((round * 13L + 31L)));
		}
		TestContext.assertEquals(0, map.size());
		final long keep = 9876543210123L;
		for(int i = 0; i < 50; ++i) {
			map.put(keep, 11L);
		}
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.removeKey(keep));
		TestContext.assertTrue(map.putIfAbsent(7L, 11L));
		TestContext.assertTrue(map.putIfAbsent(9L, -22L));
		final FastLongIterator it = (FastLongIterator) map.keyIterator();
		TestContext.assertTrue(it.hasNext());
		final long returned = it.next();
		map.ensureCapacity(128);
		final int sizeBeforeRemove = map.size();
		it.remove();
		TestContext.assertFalse(map.containsKey(returned));
		TestContext.assertEquals(sizeBeforeRemove - 1, map.size());
		map.trimToSize();
		TestContext.assertEquals(1, map.size());
	}
	private void checkLongLongMapEmptyFastPaths() {
		final long fallback = 99L;
		final long[] emptyKeys = new long[0];
		final long[] emptyVals = new long[0];
		final FastLongSet emptySet = new FastLongSet();
		final FastLongLongMap emptySource = new FastLongLongMap();
		final FastLongLongMap map = new FastLongLongMap(16);
		TestContext.assertTrue((map.get(7L) == 0L));
		TestContext.assertTrue((map.get(7L, fallback) == fallback));
		TestContext.assertFalse(map.containsKey(7L));
		TestContext.assertFalse(map.containsValue(11L));
		TestContext.assertTrue((map.remove(7L) == 0L));
		TestContext.assertTrue((map.remove(7L, fallback) == fallback));
		TestContext.assertFalse(map.removeKey(7L));
		TestContext.assertFalse(map.removeEntry(7L, 11L));
		TestContext.assertFalse(map.removeAllKeys((long[]) null));
		TestContext.assertFalse(map.removeAllKeys(emptyKeys));
		TestContext.assertFalse(map.removeAllKeys((FastLongSet) null));
		TestContext.assertFalse(map.removeAllKeys(emptySet));
		TestContext.assertFalse(map.retainAllKeys((FastLongSet) null));
		TestContext.assertFalse(map.retainAllKeys(emptySet));
		TestContext.assertFalse(map.putAll((FastLongLongMap) null));
		TestContext.assertFalse(map.putAll(emptySource));
		TestContext.assertFalse(map.putAll(emptyKeys, emptyVals));
		map.clear();
		TestContext.assertEquals(0, map.keys().length);
		TestContext.assertEquals(0, map.values().length);
		TestContext.assertTrue(map.keySet().isEmpty());
		TestContext.assertEquals(0, map.hashCode());
		TestContext.assertEquals("{}", map.toString());
		TestContext.assertFalse(map.keyIterator().hasNext());
		TestContext.assertFalse(map.valueIterator().hasNext());
		TestContext.assertTrue(map.putIfAbsent(1L, 11L));
		TestContext.assertTrue(map.putIfAbsent(0x100000001L, -22L));
		TestContext.assertTrue(map.removeKey(1L));
		TestContext.assertTrue(map.removeKey(0x100000001L));
		TestContext.assertEquals(0, map.size());
		TestContext.assertFalse(map.containsKey(1L));
		TestContext.assertFalse(map.containsValue(11L));
		TestContext.assertEquals(0, map.keys().length);
		TestContext.assertEquals(0, map.values().length);
		TestContext.assertEquals(0, map.hashCode());
		TestContext.assertEquals("{}", map.toString());
		TestContext.assertFalse(map.keyIterator().hasNext());
		TestContext.assertFalse(map.valueIterator().hasNext());
		final FastLongLongMap shared = (FastLongLongMap) map.shared();
		final FastLongLongMap unmodifiable = (FastLongLongMap) map.unmodifiable();
		TestContext.assertTrue((shared.get(7L, fallback) == fallback));
		TestContext.assertTrue((unmodifiable.get(7L, fallback) == fallback));
		TestContext.assertFalse(shared.containsKey(7L));
		TestContext.assertFalse(unmodifiable.containsKey(7L));
		TestContext.assertEquals(0, shared.keys().length);
		TestContext.assertEquals(0, unmodifiable.keys().length);
		TestContext.assertEquals("{}", shared.toString());
		TestContext.assertEquals("{}", unmodifiable.toString());
		TestContext.assertFalse(shared.keyIterator().hasNext());
		TestContext.assertFalse(unmodifiable.valueIterator().hasNext());
		shared.clear();
		TestContext.assertTrue(shared.putIfAbsent(7L, 11L));
		TestContext.assertTrue(unmodifiable.containsKey(7L));
	}
	private void checkLongLongMapViews() {
		final FastLongLongMap source = new FastLongLongMap();
		source.put(Long.MIN_VALUE, 11L);
		source.put(1L, -22L);
		final FastLongLongMap shared = (FastLongLongMap) source.shared();
		final FastLongLongMap unmodifiable = (FastLongLongMap) source.unmodifiable();
		final FastLongLongMap sharedUnmod = (FastLongLongMap) shared.unmodifiable();
		TestContext.assertTrue(source.equals(shared));
		TestContext.assertTrue(shared.equals(source));
		TestContext.assertTrue(source.equals(unmodifiable));
		TestContext.assertTrue(unmodifiable.equals(source));
		TestContext.assertTrue(shared.equals(sharedUnmod));
		TestContext.assertTrue(sharedUnmod.equals(shared));
		TestContext.assertEquals(source.hashCode(), shared.hashCode());
		TestContext.assertEquals(source.hashCode(), unmodifiable.hashCode());
		TestContext.assertEquals(source.hashCode(), sharedUnmod.hashCode());
		final FastLongIterator kit = (FastLongIterator) shared.keyIterator();
		boolean badRemove = false;
		try { kit.remove(); } catch(final IllegalStateException ex) { badRemove = true; }
		TestContext.assertTrue(badRemove);
		int kCount = 0;
		while(kit.hasNext()) { kit.next(); ++kCount; }
		TestContext.assertEquals(2, kCount);
		boolean noMore = false;
		try { kit.next(); } catch(final NoSuchElementException ex) { noMore = true; }
		TestContext.assertTrue(noMore);
		final FastLongIterator vit = (FastLongIterator) shared.valueIterator();
		TestContext.assertTrue(vit.hasNext());
		vit.next();
		shared.ensureCapacity(64);
		vit.remove();
		TestContext.assertEquals(1, unmodifiable.size());
		TestContext.assertEquals(1, sharedUnmod.size());
		badRemove = false;
		try { vit.remove(); } catch(final IllegalStateException ex) { badRemove = true; }
		TestContext.assertTrue(badRemove);
		final FastLongIterator ukit = (FastLongIterator) unmodifiable.keyIterator();
		ukit.next();
		int rejected = 0;
		try { ukit.remove(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		final FastLongIterator uvit = (FastLongIterator) sharedUnmod.valueIterator();
		uvit.next();
		try { uvit.remove(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.put(7L, 11L); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putIfAbsent(7L, 11L); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putAll(new long[] { 7L }, new long[] { 11L }); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putAll(source); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.remove(Long.MIN_VALUE); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.remove(Long.MIN_VALUE, 99L); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeKey(Long.MIN_VALUE); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeEntry(Long.MIN_VALUE, 11L); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeAllKeys(new long[] { Long.MIN_VALUE }); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeAllKeys(new FastLongSet()); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.retainAllKeys(new FastLongSet()); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.clear(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.reset(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.trimToSize(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.ensureCapacity(64); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.shared(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		TestContext.assertEquals(18, rejected);
	}
	private void checkLongShortMapPutGetRemove() {
		final long k1 = Long.MIN_VALUE;
		final long k2 = 0L;
		final long k3 = 1L;
		final long k4 = 0x100000001L;
		final long kAbsent = 424242424242L;
		final short v1 = (short) 11;
		final short v2 = (short) -22;
		final short v3 = (short) 0;
		final short v4 = (short) 333;
		final short fallback = (short) 99;
		final short wrongVal = (short) 555;
		final FastLongShortMap map = new FastLongShortMap();
		TestContext.assertEquals(0, map.size());
		TestContext.assertTrue(map.isEmpty());
		TestContext.assertTrue((map.get(k1) == (short) 0));
		TestContext.assertTrue((map.get(k1, fallback) == fallback));
		TestContext.assertFalse(map.containsKey(k1));
		TestContext.assertTrue((map.put(k1, v1) == (short) 0));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.containsKey(k1));
		TestContext.assertTrue(map.containsValue(v1));
		TestContext.assertTrue((map.get(k1) == (short) 11));
		TestContext.assertTrue((map.put(k1, v2) == (short) 11));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue((map.get(k1) == (short) -22));
		TestContext.assertFalse(map.putIfAbsent(k1, v1));
		TestContext.assertTrue((map.get(k1) == (short) -22));
		TestContext.assertTrue(map.putIfAbsent(k3, v3));
		TestContext.assertEquals(2, map.size());
		TestContext.assertTrue((map.get(k3, fallback) == (short) 0));
		TestContext.assertFalse(map.containsValue((short) 555));
		final long[] bulkKeys = new long[] { k2, k4 };
		final short[] bulkVals = new short[] { v2, v4 };
		TestContext.assertTrue(map.putAll(bulkKeys, bulkVals));
		TestContext.assertEquals(4, map.size());
		TestContext.assertFalse(map.putAll(bulkKeys, bulkVals));
		TestContext.assertFalse(map.putAll((long[]) null, (short[]) null));
		boolean threw = false;
		try {
			map.putAll(new long[] { k1 }, bulkVals);
		}
		catch(final IllegalArgumentException ex) {
			threw = true;
		}
		TestContext.assertTrue(threw);
		final FastLongShortMap copy = new FastLongShortMap(map);
		TestContext.assertTrue(map.equals(copy));
		TestContext.assertTrue(copy.equals(map));
		TestContext.assertEquals(map.hashCode(), copy.hashCode());
		TestContext.assertFalse(map.putAll(copy));
		TestContext.assertFalse(map.putAll(map));
		final FastLongShortMap fromArrays = new FastLongShortMap(bulkKeys, bulkVals);
		TestContext.assertEquals(2, fromArrays.size());
		TestContext.assertTrue(fromArrays.containsKey(k2));
		TestContext.assertTrue(fromArrays.containsKey(k4));
		TestContext.assertEquals(4, map.keys().length);
		TestContext.assertEquals(4, map.values().length);
		final FastLongSet ks = map.keySet();
		TestContext.assertEquals(4, ks.size());
		TestContext.assertTrue(ks.contains(k1));
		TestContext.assertTrue(ks.contains(k4));
		TestContext.assertTrue(map.toString().indexOf('=') >= 0);
		try {
			final FastLongShortMap cloned = (FastLongShortMap) map.clone();
			TestContext.assertTrue(map.equals(cloned));
			cloned.put(kAbsent, v1);
			TestContext.assertFalse(map.containsKey(kAbsent));
		}
		catch(final UnsupportedOperationException j2meExpected) {}
		catch(final CloneNotSupportedException ex) {
			TestContext.assertTrue(false);
		}
		TestContext.assertFalse(map.removeEntry(k1, wrongVal));
		TestContext.assertTrue(map.removeEntry(k1, v2));
		TestContext.assertFalse(map.containsKey(k1));
		TestContext.assertTrue((map.remove(kAbsent, fallback) == fallback));
		TestContext.assertTrue((map.remove(k3) == (short) 0));
		TestContext.assertTrue(map.removeKey(k2));
		TestContext.assertFalse(map.removeKey(kAbsent));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.containsKey(k4));
		TestContext.assertTrue((map.put(k1, v1) == (short) 0));
		TestContext.assertTrue(map.removeAllKeys(new long[] { k1 }));
		final FastLongSet remSet = new FastLongSet(new long[] { k4 });
		TestContext.assertTrue(map.putIfAbsent(k2, v2));
		TestContext.assertTrue(map.retainAllKeys(remSet));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.removeAllKeys(remSet));
		TestContext.assertEquals(0, map.size());
	}
	private void checkLongShortMapChurn() {
		final FastLongShortMap map = new FastLongShortMap(16);
		for(int round = 0; round < 256; ++round) {
			for(int i = 0; i < 8; ++i) {
				final long key = (round * 13L + i);
				final short val = ((short) (round * 7 + i));
				TestContext.assertTrue(map.putIfAbsent(key, val));
			}
			for(int i = 0; i < 8; ++i) {
				final long key = (round * 13L + i);
				TestContext.assertTrue(map.removeKey(key));
			}
			TestContext.assertFalse(map.containsKey((round * 13L + 31L)));
		}
		TestContext.assertEquals(0, map.size());
		final long keep = 9876543210123L;
		for(int i = 0; i < 50; ++i) {
			map.put(keep, (short) 11);
		}
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.removeKey(keep));
		TestContext.assertTrue(map.putIfAbsent(7L, (short) 11));
		TestContext.assertTrue(map.putIfAbsent(9L, (short) -22));
		final FastLongIterator it = (FastLongIterator) map.keyIterator();
		TestContext.assertTrue(it.hasNext());
		final long returned = it.next();
		map.ensureCapacity(128);
		final int sizeBeforeRemove = map.size();
		it.remove();
		TestContext.assertFalse(map.containsKey(returned));
		TestContext.assertEquals(sizeBeforeRemove - 1, map.size());
		map.trimToSize();
		TestContext.assertEquals(1, map.size());
	}
	private void checkLongShortMapEmptyFastPaths() {
		final short fallback = (short) 99;
		final long[] emptyKeys = new long[0];
		final short[] emptyVals = new short[0];
		final FastLongSet emptySet = new FastLongSet();
		final FastLongShortMap emptySource = new FastLongShortMap();
		final FastLongShortMap map = new FastLongShortMap(16);
		TestContext.assertTrue((map.get(7L) == (short) 0));
		TestContext.assertTrue((map.get(7L, fallback) == fallback));
		TestContext.assertFalse(map.containsKey(7L));
		TestContext.assertFalse(map.containsValue((short) 11));
		TestContext.assertTrue((map.remove(7L) == (short) 0));
		TestContext.assertTrue((map.remove(7L, fallback) == fallback));
		TestContext.assertFalse(map.removeKey(7L));
		TestContext.assertFalse(map.removeEntry(7L, (short) 11));
		TestContext.assertFalse(map.removeAllKeys((long[]) null));
		TestContext.assertFalse(map.removeAllKeys(emptyKeys));
		TestContext.assertFalse(map.removeAllKeys((FastLongSet) null));
		TestContext.assertFalse(map.removeAllKeys(emptySet));
		TestContext.assertFalse(map.retainAllKeys((FastLongSet) null));
		TestContext.assertFalse(map.retainAllKeys(emptySet));
		TestContext.assertFalse(map.putAll((FastLongShortMap) null));
		TestContext.assertFalse(map.putAll(emptySource));
		TestContext.assertFalse(map.putAll(emptyKeys, emptyVals));
		map.clear();
		TestContext.assertEquals(0, map.keys().length);
		TestContext.assertEquals(0, map.values().length);
		TestContext.assertTrue(map.keySet().isEmpty());
		TestContext.assertEquals(0, map.hashCode());
		TestContext.assertEquals("{}", map.toString());
		TestContext.assertFalse(map.keyIterator().hasNext());
		TestContext.assertFalse(map.valueIterator().hasNext());
		TestContext.assertTrue(map.putIfAbsent(1L, (short) 11));
		TestContext.assertTrue(map.putIfAbsent(0x100000001L, (short) -22));
		TestContext.assertTrue(map.removeKey(1L));
		TestContext.assertTrue(map.removeKey(0x100000001L));
		TestContext.assertEquals(0, map.size());
		TestContext.assertFalse(map.containsKey(1L));
		TestContext.assertFalse(map.containsValue((short) 11));
		TestContext.assertEquals(0, map.keys().length);
		TestContext.assertEquals(0, map.values().length);
		TestContext.assertEquals(0, map.hashCode());
		TestContext.assertEquals("{}", map.toString());
		TestContext.assertFalse(map.keyIterator().hasNext());
		TestContext.assertFalse(map.valueIterator().hasNext());
		final FastLongShortMap shared = (FastLongShortMap) map.shared();
		final FastLongShortMap unmodifiable = (FastLongShortMap) map.unmodifiable();
		TestContext.assertTrue((shared.get(7L, fallback) == fallback));
		TestContext.assertTrue((unmodifiable.get(7L, fallback) == fallback));
		TestContext.assertFalse(shared.containsKey(7L));
		TestContext.assertFalse(unmodifiable.containsKey(7L));
		TestContext.assertEquals(0, shared.keys().length);
		TestContext.assertEquals(0, unmodifiable.keys().length);
		TestContext.assertEquals("{}", shared.toString());
		TestContext.assertEquals("{}", unmodifiable.toString());
		TestContext.assertFalse(shared.keyIterator().hasNext());
		TestContext.assertFalse(unmodifiable.valueIterator().hasNext());
		shared.clear();
		TestContext.assertTrue(shared.putIfAbsent(7L, (short) 11));
		TestContext.assertTrue(unmodifiable.containsKey(7L));
	}
	private void checkLongShortMapViews() {
		final FastLongShortMap source = new FastLongShortMap();
		source.put(Long.MIN_VALUE, (short) 11);
		source.put(1L, (short) -22);
		final FastLongShortMap shared = (FastLongShortMap) source.shared();
		final FastLongShortMap unmodifiable = (FastLongShortMap) source.unmodifiable();
		final FastLongShortMap sharedUnmod = (FastLongShortMap) shared.unmodifiable();
		TestContext.assertTrue(source.equals(shared));
		TestContext.assertTrue(shared.equals(source));
		TestContext.assertTrue(source.equals(unmodifiable));
		TestContext.assertTrue(unmodifiable.equals(source));
		TestContext.assertTrue(shared.equals(sharedUnmod));
		TestContext.assertTrue(sharedUnmod.equals(shared));
		TestContext.assertEquals(source.hashCode(), shared.hashCode());
		TestContext.assertEquals(source.hashCode(), unmodifiable.hashCode());
		TestContext.assertEquals(source.hashCode(), sharedUnmod.hashCode());
		final FastLongIterator kit = (FastLongIterator) shared.keyIterator();
		boolean badRemove = false;
		try { kit.remove(); } catch(final IllegalStateException ex) { badRemove = true; }
		TestContext.assertTrue(badRemove);
		int kCount = 0;
		while(kit.hasNext()) { kit.next(); ++kCount; }
		TestContext.assertEquals(2, kCount);
		boolean noMore = false;
		try { kit.next(); } catch(final NoSuchElementException ex) { noMore = true; }
		TestContext.assertTrue(noMore);
		final FastShortIterator vit = (FastShortIterator) shared.valueIterator();
		TestContext.assertTrue(vit.hasNext());
		vit.next();
		shared.ensureCapacity(64);
		vit.remove();
		TestContext.assertEquals(1, unmodifiable.size());
		TestContext.assertEquals(1, sharedUnmod.size());
		badRemove = false;
		try { vit.remove(); } catch(final IllegalStateException ex) { badRemove = true; }
		TestContext.assertTrue(badRemove);
		final FastLongIterator ukit = (FastLongIterator) unmodifiable.keyIterator();
		ukit.next();
		int rejected = 0;
		try { ukit.remove(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		final FastShortIterator uvit = (FastShortIterator) sharedUnmod.valueIterator();
		uvit.next();
		try { uvit.remove(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.put(7L, (short) 11); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putIfAbsent(7L, (short) 11); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putAll(new long[] { 7L }, new short[] { (short) 11 }); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putAll(source); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.remove(Long.MIN_VALUE); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.remove(Long.MIN_VALUE, (short) 99); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeKey(Long.MIN_VALUE); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeEntry(Long.MIN_VALUE, (short) 11); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeAllKeys(new long[] { Long.MIN_VALUE }); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeAllKeys(new FastLongSet()); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.retainAllKeys(new FastLongSet()); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.clear(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.reset(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.trimToSize(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.ensureCapacity(64); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.shared(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		TestContext.assertEquals(18, rejected);
	}
	private void checkIntObjectMapPutGetRemove() {
		final int k1 = Integer.MIN_VALUE;
		final int k2 = 0;
		final int k3 = 1;
		final int k4 = Integer.MAX_VALUE;
		final int kAbsent = 424242;
		final FastIntObjectMap map = new FastIntObjectMap();
		TestContext.assertEquals(0, map.size());
		TestContext.assertTrue(map.isEmpty());
		TestContext.assertNull(map.get(k1));
		TestContext.assertEquals("fb", map.get(k1, "fb"));
		TestContext.assertFalse(map.containsKey(k1));
		TestContext.assertNull(map.put(k1, "v1"));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.containsKey(k1));
		TestContext.assertTrue(map.containsValue("v1"));
		TestContext.assertEquals("v1", map.get(k1));
		TestContext.assertEquals("v1", map.put(k1, "v2"));
		TestContext.assertEquals("v2", map.putIfAbsent(k1, "v1"));
		TestContext.assertFalse(map.putKeyIfAbsent(k1, "v1"));
		TestContext.assertEquals("v2", map.get(k1));
		TestContext.assertNull(map.putIfAbsent(k2, null));
		TestContext.assertTrue(map.containsKey(k2));
		TestContext.assertTrue(map.containsValue(null));
		TestContext.assertNull(map.get(k2, "fb"));
		TestContext.assertFalse(map.putKeyIfAbsent(k2, "x"));
		TestContext.assertTrue(map.putKeyIfAbsent(k3, "v3"));
		TestContext.assertTrue(map.removeEntry(k2, null));
		TestContext.assertFalse(map.containsKey(k2));
		final int[] bulkKeys = new int[] { k2, k4 };
		final Object[] bulkVals = new Object[] { null, "v4" };
		TestContext.assertTrue(map.putAll(bulkKeys, bulkVals));
		TestContext.assertEquals(4, map.size());
		TestContext.assertFalse(map.putAll(bulkKeys, bulkVals));
		TestContext.assertFalse(map.putAll((int[]) null, (Object[]) null));
		boolean threw = false;
		try { map.putAll(new int[] { k1 }, bulkVals); } catch(final IllegalArgumentException ex) { threw = true; }
		TestContext.assertTrue(threw);
		final FastIntObjectMap copy = new FastIntObjectMap(map);
		TestContext.assertTrue(map.equals(copy));
		TestContext.assertTrue(copy.equals(map));
		TestContext.assertEquals(map.hashCode(), copy.hashCode());
		TestContext.assertFalse(map.putAll(copy));
		TestContext.assertFalse(map.putAll(map));
		final FastIntObjectMap fromArrays = new FastIntObjectMap(bulkKeys, bulkVals);
		TestContext.assertEquals(2, fromArrays.size());
		TestContext.assertEquals(4, map.keys().length);
		TestContext.assertEquals(4, map.values().length);
		TestContext.assertEquals(4, map.keySet().size());
		try {
			final FastIntObjectMap cloned = (FastIntObjectMap) map.clone();
			TestContext.assertTrue(map.equals(cloned));
			cloned.put(kAbsent, "extra");
			TestContext.assertFalse(map.containsKey(kAbsent));
		}
		catch(final UnsupportedOperationException j2meExpected) {}
		catch(final CloneNotSupportedException ex) { TestContext.assertTrue(false); }
		TestContext.assertFalse(map.removeEntry(k1, "wrong"));
		TestContext.assertTrue(map.removeEntry(k1, "v2"));
		TestContext.assertEquals("fb", map.remove(kAbsent, "fb"));
		TestContext.assertEquals("v3", map.remove(k3));
		TestContext.assertTrue(map.removeKey(k2));
		TestContext.assertFalse(map.removeKey(kAbsent));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.putKeyIfAbsent(k2, "v2"));
		final FastIntSet remSet = new FastIntSet(new int[] { k4 });
		TestContext.assertTrue(map.retainAllKeys(remSet));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.removeAllKeys(remSet));
		TestContext.assertEquals(0, map.size());
	}
	private void checkIntObjectMapChurn() {
		final FastIntObjectMap map = new FastIntObjectMap(16);
		for(int round = 0; round < 256; ++round) {
			for(int i = 0; i < 8; ++i) {
				final int key = (round * 13 + i);
				TestContext.assertTrue(map.putKeyIfAbsent(key, "v" + i));
			}
			for(int i = 0; i < 8; ++i) {
				final int key = (round * 13 + i);
				TestContext.assertTrue(map.removeKey(key));
			}
			TestContext.assertFalse(map.containsKey((round * 13 + 31)));
		}
		TestContext.assertEquals(0, map.size());
		final int keep = 987654321;
		for(int i = 0; i < 50; ++i) { map.put(keep, "x"); }
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.removeKey(keep));
		TestContext.assertTrue(map.putKeyIfAbsent(7, "a"));
		TestContext.assertTrue(map.putKeyIfAbsent(9, "b"));
		final FastIntIterator it = (FastIntIterator) map.keyIterator();
		TestContext.assertTrue(it.hasNext());
		final int returned = it.next();
		map.ensureCapacity(128);
		final int sizeBeforeRemove = map.size();
		it.remove();
		TestContext.assertFalse(map.containsKey(returned));
		TestContext.assertEquals(sizeBeforeRemove - 1, map.size());
		map.trimToSize();
		TestContext.assertEquals(1, map.size());
	}
	private void checkIntObjectMapEmptyFastPaths() {
		final int[] emptyKeys = new int[0];
		final Object[] emptyVals = new Object[0];
		final FastIntSet emptySet = new FastIntSet();
		final FastIntObjectMap emptySource = new FastIntObjectMap();
		final FastIntObjectMap map = new FastIntObjectMap(16);
		TestContext.assertNull(map.get(7));
		TestContext.assertEquals("fb", map.get(7, "fb"));
		TestContext.assertFalse(map.containsKey(7));
		TestContext.assertFalse(map.containsValue("v"));
		TestContext.assertFalse(map.containsValue(null));
		TestContext.assertNull(map.remove(7));
		TestContext.assertEquals("fb", map.remove(7, "fb"));
		TestContext.assertFalse(map.removeKey(7));
		TestContext.assertFalse(map.removeEntry(7, null));
		TestContext.assertFalse(map.removeAllKeys((int[]) null));
		TestContext.assertFalse(map.removeAllKeys(emptyKeys));
		TestContext.assertFalse(map.removeAllKeys((FastIntSet) null));
		TestContext.assertFalse(map.removeAllKeys(emptySet));
		TestContext.assertFalse(map.retainAllKeys((FastIntSet) null));
		TestContext.assertFalse(map.retainAllKeys(emptySet));
		TestContext.assertFalse(map.putAll((FastIntObjectMap) null));
		TestContext.assertFalse(map.putAll(emptySource));
		TestContext.assertFalse(map.putAll(emptyKeys, emptyVals));
		map.clear();
		TestContext.assertEquals(0, map.keys().length);
		TestContext.assertEquals(0, map.values().length);
		TestContext.assertTrue(map.keySet().isEmpty());
		TestContext.assertEquals(0, map.hashCode());
		TestContext.assertEquals("{}", map.toString());
		TestContext.assertFalse(map.keyIterator().hasNext());
		TestContext.assertFalse(map.valueIterator().hasNext());
		TestContext.assertTrue(map.putKeyIfAbsent(1, "a"));
		TestContext.assertTrue(map.putKeyIfAbsent(Integer.MAX_VALUE, null));
		TestContext.assertTrue(map.removeKey(1));
		TestContext.assertTrue(map.removeKey(Integer.MAX_VALUE));
		TestContext.assertEquals(0, map.size());
		TestContext.assertFalse(map.containsValue(null));
		TestContext.assertEquals(0, map.hashCode());
		TestContext.assertEquals("{}", map.toString());
		final FastIntObjectMap shared = (FastIntObjectMap) map.shared();
		final FastIntObjectMap unmodifiable = (FastIntObjectMap) map.unmodifiable();
		TestContext.assertEquals("fb", shared.get(7, "fb"));
		TestContext.assertEquals("fb", unmodifiable.get(7, "fb"));
		TestContext.assertFalse(shared.valueIterator().hasNext());
		TestContext.assertFalse(unmodifiable.keyIterator().hasNext());
		shared.clear();
		TestContext.assertTrue(shared.putKeyIfAbsent(7, "ok"));
		TestContext.assertEquals("ok", unmodifiable.get(7));
	}
	private void checkIntObjectMapViews() {
		final FastIntObjectMap source = new FastIntObjectMap();
		source.put(Integer.MIN_VALUE, "v1");
		source.put(1, "v2");
		final FastIntObjectMap shared = (FastIntObjectMap) source.shared();
		final FastIntObjectMap unmodifiable = (FastIntObjectMap) source.unmodifiable();
		final FastIntObjectMap sharedUnmod = (FastIntObjectMap) shared.unmodifiable();
		TestContext.assertTrue(source.equals(shared));
		TestContext.assertTrue(shared.equals(source));
		TestContext.assertTrue(source.equals(unmodifiable));
		TestContext.assertTrue(unmodifiable.equals(source));
		TestContext.assertTrue(shared.equals(sharedUnmod));
		TestContext.assertTrue(sharedUnmod.equals(shared));
		TestContext.assertEquals(source.hashCode(), shared.hashCode());
		TestContext.assertEquals(source.hashCode(), unmodifiable.hashCode());
		TestContext.assertEquals(source.hashCode(), sharedUnmod.hashCode());
		final FastIntIterator kit = (FastIntIterator) shared.keyIterator();
		boolean badRemove = false;
		try { kit.remove(); } catch(final IllegalStateException ex) { badRemove = true; }
		TestContext.assertTrue(badRemove);
		int kCount = 0;
		while(kit.hasNext()) { kit.next(); ++kCount; }
		TestContext.assertEquals(2, kCount);
		boolean noMore = false;
		try { kit.next(); } catch(final NoSuchElementException ex) { noMore = true; }
		TestContext.assertTrue(noMore);
		final Iterator vit = shared.valueIterator();
		TestContext.assertTrue(vit.hasNext());
		TestContext.assertNotNull((String) vit.next());
		shared.ensureCapacity(64);
		vit.remove();
		TestContext.assertEquals(1, unmodifiable.size());
		TestContext.assertEquals(1, sharedUnmod.size());
		badRemove = false;
		try { vit.remove(); } catch(final IllegalStateException ex) { badRemove = true; }
		TestContext.assertTrue(badRemove);
		final FastIntIterator ukit = (FastIntIterator) unmodifiable.keyIterator();
		ukit.next();
		int rejected = 0;
		try { ukit.remove(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		final Iterator uvit = sharedUnmod.valueIterator();
		uvit.next();
		try { uvit.remove(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.put(7, "x"); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putIfAbsent(7, "x"); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putKeyIfAbsent(7, "x"); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putAll(new int[] { 7 }, new Object[] { "x" }); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putAll(source); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.remove(Integer.MIN_VALUE); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.remove(Integer.MIN_VALUE, "fb"); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeKey(Integer.MIN_VALUE); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeEntry(Integer.MIN_VALUE, "v1"); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeAllKeys(new int[] { Integer.MIN_VALUE }); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeAllKeys(new FastIntSet()); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.retainAllKeys(new FastIntSet()); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.clear(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.reset(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.trimToSize(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.ensureCapacity(64); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.shared(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		TestContext.assertEquals(19, rejected);
	}
	private void checkLongObjectMapPutGetRemove() {
		final long k1 = Long.MIN_VALUE;
		final long k2 = 0L;
		final long k3 = 1L;
		final long k4 = 0x100000001L;
		final long kAbsent = 424242424242L;
		final FastLongObjectMap map = new FastLongObjectMap();
		TestContext.assertEquals(0, map.size());
		TestContext.assertTrue(map.isEmpty());
		TestContext.assertNull(map.get(k1));
		TestContext.assertEquals("fb", map.get(k1, "fb"));
		TestContext.assertFalse(map.containsKey(k1));
		TestContext.assertNull(map.put(k1, "v1"));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.containsKey(k1));
		TestContext.assertTrue(map.containsValue("v1"));
		TestContext.assertEquals("v1", map.get(k1));
		TestContext.assertEquals("v1", map.put(k1, "v2"));
		TestContext.assertEquals("v2", map.putIfAbsent(k1, "v1"));
		TestContext.assertFalse(map.putKeyIfAbsent(k1, "v1"));
		TestContext.assertEquals("v2", map.get(k1));
		TestContext.assertNull(map.putIfAbsent(k2, null));
		TestContext.assertTrue(map.containsKey(k2));
		TestContext.assertTrue(map.containsValue(null));
		TestContext.assertNull(map.get(k2, "fb"));
		TestContext.assertFalse(map.putKeyIfAbsent(k2, "x"));
		TestContext.assertTrue(map.putKeyIfAbsent(k3, "v3"));
		TestContext.assertTrue(map.removeEntry(k2, null));
		TestContext.assertFalse(map.containsKey(k2));
		final long[] bulkKeys = new long[] { k2, k4 };
		final Object[] bulkVals = new Object[] { null, "v4" };
		TestContext.assertTrue(map.putAll(bulkKeys, bulkVals));
		TestContext.assertEquals(4, map.size());
		TestContext.assertFalse(map.putAll(bulkKeys, bulkVals));
		TestContext.assertFalse(map.putAll((long[]) null, (Object[]) null));
		boolean threw = false;
		try { map.putAll(new long[] { k1 }, bulkVals); } catch(final IllegalArgumentException ex) { threw = true; }
		TestContext.assertTrue(threw);
		final FastLongObjectMap copy = new FastLongObjectMap(map);
		TestContext.assertTrue(map.equals(copy));
		TestContext.assertTrue(copy.equals(map));
		TestContext.assertEquals(map.hashCode(), copy.hashCode());
		TestContext.assertFalse(map.putAll(copy));
		TestContext.assertFalse(map.putAll(map));
		final FastLongObjectMap fromArrays = new FastLongObjectMap(bulkKeys, bulkVals);
		TestContext.assertEquals(2, fromArrays.size());
		TestContext.assertEquals(4, map.keys().length);
		TestContext.assertEquals(4, map.values().length);
		TestContext.assertEquals(4, map.keySet().size());
		try {
			final FastLongObjectMap cloned = (FastLongObjectMap) map.clone();
			TestContext.assertTrue(map.equals(cloned));
			cloned.put(kAbsent, "extra");
			TestContext.assertFalse(map.containsKey(kAbsent));
		}
		catch(final UnsupportedOperationException j2meExpected) {}
		catch(final CloneNotSupportedException ex) { TestContext.assertTrue(false); }
		TestContext.assertFalse(map.removeEntry(k1, "wrong"));
		TestContext.assertTrue(map.removeEntry(k1, "v2"));
		TestContext.assertEquals("fb", map.remove(kAbsent, "fb"));
		TestContext.assertEquals("v3", map.remove(k3));
		TestContext.assertTrue(map.removeKey(k2));
		TestContext.assertFalse(map.removeKey(kAbsent));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.putKeyIfAbsent(k2, "v2"));
		final FastLongSet remSet = new FastLongSet(new long[] { k4 });
		TestContext.assertTrue(map.retainAllKeys(remSet));
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.removeAllKeys(remSet));
		TestContext.assertEquals(0, map.size());
	}
	private void checkLongObjectMapChurn() {
		final FastLongObjectMap map = new FastLongObjectMap(16);
		for(int round = 0; round < 256; ++round) {
			for(int i = 0; i < 8; ++i) {
				final long key = (round * 13L + i);
				TestContext.assertTrue(map.putKeyIfAbsent(key, "v" + i));
			}
			for(int i = 0; i < 8; ++i) {
				final long key = (round * 13L + i);
				TestContext.assertTrue(map.removeKey(key));
			}
			TestContext.assertFalse(map.containsKey((round * 13L + 31L)));
		}
		TestContext.assertEquals(0, map.size());
		final long keep = 9876543210123L;
		for(int i = 0; i < 50; ++i) { map.put(keep, "x"); }
		TestContext.assertEquals(1, map.size());
		TestContext.assertTrue(map.removeKey(keep));
		TestContext.assertTrue(map.putKeyIfAbsent(7L, "a"));
		TestContext.assertTrue(map.putKeyIfAbsent(9L, "b"));
		final FastLongIterator it = (FastLongIterator) map.keyIterator();
		TestContext.assertTrue(it.hasNext());
		final long returned = it.next();
		map.ensureCapacity(128);
		final int sizeBeforeRemove = map.size();
		it.remove();
		TestContext.assertFalse(map.containsKey(returned));
		TestContext.assertEquals(sizeBeforeRemove - 1, map.size());
		map.trimToSize();
		TestContext.assertEquals(1, map.size());
	}
	private void checkLongObjectMapEmptyFastPaths() {
		final long[] emptyKeys = new long[0];
		final Object[] emptyVals = new Object[0];
		final FastLongSet emptySet = new FastLongSet();
		final FastLongObjectMap emptySource = new FastLongObjectMap();
		final FastLongObjectMap map = new FastLongObjectMap(16);
		TestContext.assertNull(map.get(7L));
		TestContext.assertEquals("fb", map.get(7L, "fb"));
		TestContext.assertFalse(map.containsKey(7L));
		TestContext.assertFalse(map.containsValue("v"));
		TestContext.assertFalse(map.containsValue(null));
		TestContext.assertNull(map.remove(7L));
		TestContext.assertEquals("fb", map.remove(7L, "fb"));
		TestContext.assertFalse(map.removeKey(7L));
		TestContext.assertFalse(map.removeEntry(7L, null));
		TestContext.assertFalse(map.removeAllKeys((long[]) null));
		TestContext.assertFalse(map.removeAllKeys(emptyKeys));
		TestContext.assertFalse(map.removeAllKeys((FastLongSet) null));
		TestContext.assertFalse(map.removeAllKeys(emptySet));
		TestContext.assertFalse(map.retainAllKeys((FastLongSet) null));
		TestContext.assertFalse(map.retainAllKeys(emptySet));
		TestContext.assertFalse(map.putAll((FastLongObjectMap) null));
		TestContext.assertFalse(map.putAll(emptySource));
		TestContext.assertFalse(map.putAll(emptyKeys, emptyVals));
		map.clear();
		TestContext.assertEquals(0, map.keys().length);
		TestContext.assertEquals(0, map.values().length);
		TestContext.assertTrue(map.keySet().isEmpty());
		TestContext.assertEquals(0, map.hashCode());
		TestContext.assertEquals("{}", map.toString());
		TestContext.assertFalse(map.keyIterator().hasNext());
		TestContext.assertFalse(map.valueIterator().hasNext());
		TestContext.assertTrue(map.putKeyIfAbsent(1L, "a"));
		TestContext.assertTrue(map.putKeyIfAbsent(0x100000001L, null));
		TestContext.assertTrue(map.removeKey(1L));
		TestContext.assertTrue(map.removeKey(0x100000001L));
		TestContext.assertEquals(0, map.size());
		TestContext.assertFalse(map.containsValue(null));
		TestContext.assertEquals(0, map.hashCode());
		TestContext.assertEquals("{}", map.toString());
		final FastLongObjectMap shared = (FastLongObjectMap) map.shared();
		final FastLongObjectMap unmodifiable = (FastLongObjectMap) map.unmodifiable();
		TestContext.assertEquals("fb", shared.get(7L, "fb"));
		TestContext.assertEquals("fb", unmodifiable.get(7L, "fb"));
		TestContext.assertFalse(shared.valueIterator().hasNext());
		TestContext.assertFalse(unmodifiable.keyIterator().hasNext());
		shared.clear();
		TestContext.assertTrue(shared.putKeyIfAbsent(7L, "ok"));
		TestContext.assertEquals("ok", unmodifiable.get(7L));
	}
	private void checkLongObjectMapViews() {
		final FastLongObjectMap source = new FastLongObjectMap();
		source.put(Long.MIN_VALUE, "v1");
		source.put(1L, "v2");
		final FastLongObjectMap shared = (FastLongObjectMap) source.shared();
		final FastLongObjectMap unmodifiable = (FastLongObjectMap) source.unmodifiable();
		final FastLongObjectMap sharedUnmod = (FastLongObjectMap) shared.unmodifiable();
		TestContext.assertTrue(source.equals(shared));
		TestContext.assertTrue(shared.equals(source));
		TestContext.assertTrue(source.equals(unmodifiable));
		TestContext.assertTrue(unmodifiable.equals(source));
		TestContext.assertTrue(shared.equals(sharedUnmod));
		TestContext.assertTrue(sharedUnmod.equals(shared));
		TestContext.assertEquals(source.hashCode(), shared.hashCode());
		TestContext.assertEquals(source.hashCode(), unmodifiable.hashCode());
		TestContext.assertEquals(source.hashCode(), sharedUnmod.hashCode());
		final FastLongIterator kit = (FastLongIterator) shared.keyIterator();
		boolean badRemove = false;
		try { kit.remove(); } catch(final IllegalStateException ex) { badRemove = true; }
		TestContext.assertTrue(badRemove);
		int kCount = 0;
		while(kit.hasNext()) { kit.next(); ++kCount; }
		TestContext.assertEquals(2, kCount);
		boolean noMore = false;
		try { kit.next(); } catch(final NoSuchElementException ex) { noMore = true; }
		TestContext.assertTrue(noMore);
		final Iterator vit = shared.valueIterator();
		TestContext.assertTrue(vit.hasNext());
		TestContext.assertNotNull((String) vit.next());
		shared.ensureCapacity(64);
		vit.remove();
		TestContext.assertEquals(1, unmodifiable.size());
		TestContext.assertEquals(1, sharedUnmod.size());
		badRemove = false;
		try { vit.remove(); } catch(final IllegalStateException ex) { badRemove = true; }
		TestContext.assertTrue(badRemove);
		final FastLongIterator ukit = (FastLongIterator) unmodifiable.keyIterator();
		ukit.next();
		int rejected = 0;
		try { ukit.remove(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		final Iterator uvit = sharedUnmod.valueIterator();
		uvit.next();
		try { uvit.remove(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.put(7L, "x"); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putIfAbsent(7L, "x"); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putKeyIfAbsent(7L, "x"); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putAll(new long[] { 7L }, new Object[] { "x" }); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.putAll(source); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.remove(Long.MIN_VALUE); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.remove(Long.MIN_VALUE, "fb"); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeKey(Long.MIN_VALUE); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeEntry(Long.MIN_VALUE, "v1"); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeAllKeys(new long[] { Long.MIN_VALUE }); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.removeAllKeys(new FastLongSet()); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.retainAllKeys(new FastLongSet()); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.clear(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.reset(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.trimToSize(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.ensureCapacity(64); } catch(final UnsupportedOperationException ex) { ++rejected; }
		try { unmodifiable.shared(); } catch(final UnsupportedOperationException ex) { ++rejected; }
		TestContext.assertEquals(19, rejected);
	}
	private void checkMapEdgeSemantics() {
		final FastIntFloatMap intFloats = new FastIntFloatMap();
		intFloats.put(1, Float.NaN);
		intFloats.put(2, 0.0f);
		intFloats.put(3, -0.0f);
		TestContext.assertTrue(intFloats.containsValue(Float.intBitsToFloat(0x7f800001)));
		TestContext.assertTrue(intFloats.removeEntry(1, Float.intBitsToFloat(0x7f800001)));
		TestContext.assertFalse(intFloats.removeEntry(2, -0.0f));
		TestContext.assertTrue(intFloats.removeEntry(2, 0.0f));
		TestContext.assertEquals(1, intFloats.size());

		final FastLongDoubleMap longDoubles = new FastLongDoubleMap();
		longDoubles.put(0x100000001L, Double.NaN);
		longDoubles.put(0x200000001L, 0.0);
		longDoubles.put(1L, -0.0);
		TestContext.assertEquals(3, longDoubles.size());
		TestContext.assertTrue(longDoubles.containsValue(Double.longBitsToDouble(0x7ff0000000000001L)));
		TestContext.assertTrue(longDoubles.removeEntry(0x100000001L, Double.longBitsToDouble(0x7ff0000000000001L)));
		TestContext.assertFalse(longDoubles.removeEntry(0x200000001L, -0.0));
		TestContext.assertTrue(longDoubles.removeEntry(1L, -0.0));
		TestContext.assertEquals(1, longDoubles.size());

		final FastIntObjectMap selfIntMap = new FastIntObjectMap();
		selfIntMap.put(1, selfIntMap);
		TestContext.assertTrue(selfIntMap.toString().indexOf("(this Map)") >= 0);
		TestContext.assertEquals(1, selfIntMap.hashCode());
		selfIntMap.clear();
		TestContext.assertEquals(0, selfIntMap.size());

		final FastLongObjectMap selfLongMap = new FastLongObjectMap();
		selfLongMap.put(0x100000000L, selfLongMap);
		selfLongMap.put(0L, "zero");
		TestContext.assertEquals(2, selfLongMap.size());
		TestContext.assertTrue(selfLongMap.toString().indexOf("(this Map)") >= 0);
		TestContext.assertEquals(1 + "zero".hashCode(), selfLongMap.hashCode());
		selfLongMap.clear();
		TestContext.assertEquals(0, selfLongMap.size());
	}
	private void checkConcurrentMapWrites() throws InterruptedException {
		final FastIntIntMap sharedInt = (FastIntIntMap) new FastIntIntMap().shared();
		final FastLongLongMap sharedLong = (FastLongLongMap) new FastLongLongMap().shared();
		final FastIntObjectMap sharedIntObj = (FastIntObjectMap) new FastIntObjectMap().shared();
		final FastLongObjectMap sharedLongObj = (FastLongObjectMap) new FastLongObjectMap().shared();
		final Thread first = new Thread(new Runnable() {
			public void run() {
				for(int i = 0; i < 500; ++i) {
					sharedInt.put(i, i * 3);
					sharedLong.put(0x100000000L + i, i * 5L);
					sharedIntObj.put(i, "i" + i);
					sharedLongObj.put(0x100000000L + i, "l" + i);
				}
			}
		});
		final Thread second = new Thread(new Runnable() {
			public void run() {
				for(int i = 500; i < 1000; ++i) {
					sharedInt.put(i, i * 3);
					sharedLong.put(0x100000000L + i, i * 5L);
					sharedIntObj.put(i, "i" + i);
					sharedLongObj.put(0x100000000L + i, "l" + i);
				}
			}
		});
		final FastIntObjectMap unmodIntObj = (FastIntObjectMap) sharedIntObj.unmodifiable();
		final FastLongObjectMap unmodLongObj = (FastLongObjectMap) sharedLongObj.unmodifiable();
		final Thread reader = new Thread(new Runnable() {
			public void run() {
				for(int i = 0; i < 200; ++i) {
					sharedInt.get(i, -1);
					sharedLong.containsKey(0x100000000L + i);
					unmodIntObj.size();
					unmodLongObj.get(0x100000000L + i);
				}
			}
		});
		first.start();
		second.start();
		reader.start();
		first.join();
		second.join();
		reader.join();
		TestContext.assertEquals(1000, sharedInt.size());
		TestContext.assertEquals(1000, sharedLong.size());
		TestContext.assertEquals(1000, sharedIntObj.size());
		TestContext.assertEquals(1000, sharedLongObj.size());
		for(int i = 0; i < 1000; ++i) {
			TestContext.assertEquals(i * 3, sharedInt.get(i));
			TestContext.assertEquals(i * 5L, sharedLong.get(0x100000000L + i));
			TestContext.assertEquals("i" + i, (String) sharedIntObj.get(i));
			TestContext.assertEquals("l" + i, (String) sharedLongObj.get(0x100000000L + i));
		}
	}
}