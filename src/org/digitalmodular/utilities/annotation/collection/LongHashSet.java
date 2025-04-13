/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2024 Mark Jeronimus. All Rights Reversed.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.digitalmodular.utilities.annotation.collection;

/**
 * Hash set for just longs. Can contain longs and can check if it contains an long in O(1) time.
 *
 * @author Mark Jeronimus
 */
// Created 2012-02-11
public class LongHashSet {
	/**
	 * The default initial capacity - MUST be a power of two.
	 */
	private static final int DEFAULT_INITIAL_CAPACITY = 1 << 8;

	/**
	 * The minimum capacity - MUST be a power of two.
	 */
	private static final int MINIMUM_CAPACITY = 1 << 4;
	/**
	 * The maximum capacity, used if a higher value is implicitly specified by either of the constructors with
	 * arguments
	 * - MUST be a power of two.
	 */
	private static final int MAXIMUM_CAPACITY = 1 << 30;

	/**
	 * The default capacity of a table entry - Preferably a power of two.
	 */
	private static final int DEFAULT_LIST_SIZE = 1 << 4;

	/**
	 * The table, resized as necessary. Length MUST Always be a power of two.
	 */
	private long[][] table;

	/**
	 * The number of key-value mappings contained in this map.
	 */
	private int size;

	private int numEnlarged = 0;

	public LongHashSet() {
		table = new long[DEFAULT_INITIAL_CAPACITY][DEFAULT_LIST_SIZE];
	}

	public LongHashSet(int initialCapacity) {
		// Round up to the next power of two.
		initialCapacity = Integer.reverse(initialCapacity - 1);
		initialCapacity = Integer.reverse(initialCapacity & -initialCapacity) << 1;

		if (initialCapacity < MINIMUM_CAPACITY) {
			initialCapacity = MINIMUM_CAPACITY;
		} else if (initialCapacity > MAXIMUM_CAPACITY) {
			initialCapacity = MAXIMUM_CAPACITY;
		}

		table = new long[initialCapacity][DEFAULT_LIST_SIZE];
	}

	/**
	 * @return true if the value was added, false if it already existed.
	 */
	public boolean add(long value) {
		int    hash = hash(value);
		long[] list = table[hash];

		// Check if exist.
		for (int i = (int)list[0]; i > 0; i--) {
			if (list[i] == value) {
				return false;
			}
		}

		// Grow if necessary.
		++list[0];
		if (list[0] == list.length) {
			// Grow only when the enlarged lists together make the table 1/16th
			// larger.
			if (numEnlarged >= table.length >>> 4 && table.length < MAXIMUM_CAPACITY) {
				// Undo change;
				--list[0];

				growTable();
			} else {
				list = new long[list.length << 1];
				System.arraycopy(table[hash], 0, list, 0, (int)table[hash][0]);
				table[hash] = list;

				numEnlarged += list.length / DEFAULT_LIST_SIZE - 1;
			}
		}

		list[(int)list[0]] = value;
		size++;

		return false;
	}

	public boolean contains(long value) {
		int    hash = hash(value);
		long[] list = table[hash];

		// Check if exist.
		for (int i = (int)list[0]; i > 0; i--) {
			if (list[i] == value) {
				return true;
			}
		}

		return false;
	}

	public int size() {
		return size;
	}

	private void growTable() {
		long[][] oldTable  = table;
		int      oldLength = table.length;

		table = new long[oldLength << 2][DEFAULT_LIST_SIZE];

		for (int y = oldLength - 1; y >= 0; y--) {
			long[] list = oldTable[y];
			for (int i = (int)list[0]; i > 0; i--) {
				addUnchecked(list[i]);
			}
		}

		numEnlarged = 0;
	}

	// Adds without growing the table (called from within growTable()).
	private void addUnchecked(long value) {
		int    hash = hash(value);
		long[] list = table[hash];

		// Grow if necessary.
		++list[0];
		if (list[0] == list.length) {
			int newLen = list.length << 1;
			table[hash] = new long[newLen];
			System.arraycopy(list, 0, table[hash], 0, (int)list[0]);
			list = table[hash];
			System.gc();
		}

		list[(int)list[0]] = value;
	}

	private int hash(long value) {
		int hash = -2128831035;
		hash ^= value & 0xFF;
		hash *= 16777619;
		hash ^= value >>> 8 & 0xFF;
		hash *= 16777619;
		hash ^= value >>> 16 & 0xFF;
		hash *= 16777619;
		hash ^= value >>> 24 & 0xFF;
		hash *= 16777619;
		hash ^= value >>> 32 & 0xFF;
		hash *= 16777619;
		hash ^= value >>> 40 & 0xFF;
		hash *= 16777619;
		hash ^= value >>> 48 & 0xFF;
		hash *= 16777619;
		hash ^= value >>> 56 & 0xFF;
		hash *= 16777619;
		return hash & table.length - 1;
	}
}
