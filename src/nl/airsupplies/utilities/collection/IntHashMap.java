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

package nl.airsupplies.utilities.collection;

import java.io.Serializable;

/**
 * Hash map with int keys. Access time with a numerical int (not {@link Integer}) takes O(1) time, but it's not nearly
 * as fast as array indexing. For faster constant-time access when integers are NOT spread equally over a large range
 * (contain large gaps), use a hierarchical approach, like {@link IntTreeMap}.
 *
 * @author Mark Jeronimus
 */
// Created 2012-08-06
public class IntHashMap<V> implements Serializable {
	private static class Entry implements Serializable {
		int    key;
		Object value;

		public Entry(int key, Object value) {
			this.key   = key;
			this.value = value;
		}
	}

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
	private Entry[][] table;

	/**
	 * The number of key-value mappings contained in this map.
	 */
	private int size;

	private int numEnlarged = 0;

	public IntHashMap() {
		table = new Entry[DEFAULT_INITIAL_CAPACITY][DEFAULT_LIST_SIZE];
	}

	public IntHashMap(int initialCapacity) {
		// Round up to the next power of two.
		initialCapacity = Integer.reverse(initialCapacity - 1);
		initialCapacity = Integer.reverse(initialCapacity & -initialCapacity) << 1;

		if (initialCapacity < MINIMUM_CAPACITY) {
			initialCapacity = MINIMUM_CAPACITY;
		} else if (initialCapacity > MAXIMUM_CAPACITY) {
			initialCapacity = MAXIMUM_CAPACITY;
		}

		table = new Entry[initialCapacity][DEFAULT_LIST_SIZE];
	}

	/**
	 * @return true if the value was added, false if it already existed.
	 */
	public boolean put(int key, V value) {
		while (true) {
			int     hash = hash(key);
			Entry[] list = table[hash];

			// Check if exist.
			int position = 0;
			for (; position < list.length && list[position] != null; position++) {
				if (list[position].key == key) {
					return false;
				}
			}

			// Grow if necessary.
			if (position == list.length) {
				// Grow only when the enlarged lists together make the table 1/16th
				// larger.
				if (numEnlarged >= table.length >>> 4 && table.length < MAXIMUM_CAPACITY) {
					growTable();
					continue;
				}

				list = new Entry[list.length << 1];
				System.arraycopy(table[hash], 0, list, 0, position);
				table[hash] = list;

				numEnlarged += list.length / DEFAULT_LIST_SIZE - 1;
			}

			list[position] = new Entry(key, value);
			size++;

			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public V get(int key) {
		int     hash = hash(key);
		Entry[] list = table[hash];

		// Check if exist.
		for (int position = 0; position < list.length && list[position] != null; position++) {
			if (list[position].key == key) {
				return (V)list[position].value;
			}
		}

		return null;
	}

	public boolean containsKey(int key) {
		int     hash = hash(key);
		Entry[] list = table[hash];

		// Check if exist.
		for (int position = 0; position < list.length && list[position] != null; position++) {
			if (list[position].key == key) {
				return true;
			}
		}

		return false;
	}

	public int size() {
		return size;
	}

	private void growTable() {
		Entry[][] oldTable  = table;
		int       oldLength = table.length;

		table = new Entry[oldLength << 2][DEFAULT_LIST_SIZE];

		for (int y = oldLength - 1; y >= 0; y--) {
			Entry[] list = oldTable[y];
			for (int position = 0; position < list.length && list[position] != null; position++) {
				addUnchecked(list[position]);
			}
		}

		numEnlarged = 0;
	}

	// Adds without growing the table (called from within growTable()).
	private void addUnchecked(Entry entry) {
		int     hash = hash(entry.key);
		Entry[] list = table[hash];

		int position = 0;
		for (; position < list.length && list[position] != null; position++) {
		}

		// Grow if necessary.
		if (position == list.length) {
			list = new Entry[list.length << 1];
			System.arraycopy(table[hash], 0, list, 0, position);
			table[hash] = list;

			numEnlarged += list.length / DEFAULT_LIST_SIZE - 1;
		}

		list[position] = entry;
	}

	private int hash(int value) {
		int hash = -2128831035;
		hash ^= value & 0xFF;
		hash *= 16777619;
		hash ^= value >>> 8 & 0xFF;
		hash *= 16777619;
		hash ^= value >>> 16 & 0xFF;
		hash *= 16777619;
		hash ^= value >>> 24 & 0xFF;
		hash *= 16777619;
		return hash & table.length - 1;
	}

	@SuppressWarnings("unchecked")
	public V[] toArray(V[] array) {
		// if (array.length < size)
		// array = new V[size];

		int i = 0;

		for (Entry[] list : table) {
			for (int position = 0; position < list.length && list[position] != null; position++) {
				array[i] = (V)list[position].value;
				i++;
			}
		}

		return array;
	}
}
