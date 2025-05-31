package nl.airsupplies.utilities.collection;

import nl.airsupplies.utilities.NumberUtilities;

/**
 * An unbalanced (fixed) tree structure with specified node width and depth. The size equals the number of non-null
 * entries, so removing an entry is the same as adding/setting an entry to null.
 *
 * @author Mark Jeronimus
 */
// Created 2012-08-08
public class IntTreeMap<V> {
	private final int   lowBound;
	private final int   highBound;
	private final int   numBitsUsed;
	private final int[] treeWidthsInBits;
	private final int[] mask;

	/**
	 * Hierarchical tree; every 'Object' is recursively another Object[] or V when leaf node.
	 */
	private final Object[] tree;

	private int size = 0;

	public IntTreeMap(int lowBound, int highBound, int... treeWidthsInBits) {
		if (highBound <= lowBound) {
			throw new IllegalArgumentException("Empty or negative range specified: " + lowBound + ".." + highBound);
		}

		this.lowBound         = lowBound;
		this.highBound        = highBound;
		numBitsUsed           = NumberUtilities.bitCount(highBound - lowBound + 1);
		this.treeWidthsInBits = treeWidthsInBits.clone();

		int numBits = 0;
		mask = new int[this.treeWidthsInBits.length];
		for (int i = 0; i < this.treeWidthsInBits.length; i++) {
			if (this.treeWidthsInBits[i] < 1) {
				throw new IllegalArgumentException("One of the bit widths is less than 1: " + this.treeWidthsInBits[i]);
			}
			mask[i] = (1 << this.treeWidthsInBits[i]) - 1;
			numBits += this.treeWidthsInBits[i];
		}

		if (numBits != numBitsUsed) {
			throw new IllegalArgumentException("Sum of bit widths (" + numBits
			                                   + ") differs from the actual bits used by the specified range (" +
			                                   numBitsUsed + ')');
		}

		tree = new Object[1 << this.treeWidthsInBits[0]];
	}

	public boolean containsKey(int key) {
		if (key < lowBound || key > highBound) {
			throw new IllegalArgumentException("Key (" + key + ") not within range: " + lowBound + ".." + highBound);
		}

		key -= lowBound;

		Object node = tree;

		// Talk the tree.
		int shift = numBitsUsed;
		for (int i = 0; i < treeWidthsInBits.length; i++) {
			int bits = treeWidthsInBits[i];
			shift -= bits;
			int index = key >> shift & mask[i];

			node = ((Object[])node)[index];

			if (node == null) {
				return false;
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public V get(int key) {
		if (key < lowBound || key > highBound) {
			throw new IllegalArgumentException("Key (" + key + ") not within range: " + lowBound + ".." + highBound);
		}

		key -= lowBound;

		Object node = tree;

		// Talk the tree.
		int shift = numBitsUsed;
		for (int i = 0; i < treeWidthsInBits.length; i++) {
			int bits = treeWidthsInBits[i];
			shift -= bits;
			int index = key >> shift & mask[i];

			node = ((Object[])node)[index];

			if (node == null) {
				return null;
			}
		}

		return (V)node;
	}

	/**
	 * Add or set an entry
	 *
	 * @return the previous value of the entry, or null if the entry was empty.
	 */
	@SuppressWarnings({"unchecked", "null"})
	public V add(int key, V value) {
		if (key < lowBound || key > highBound) {
			throw new IllegalArgumentException("Key (" + key + ") not within range: " + lowBound + ".." + highBound);
		}

		key -= lowBound;

		Object parent = null;
		Object node   = tree;
		int    index  = 0;

		// Talk the tree.
		int shift = numBitsUsed;
		for (int i = 0; i < treeWidthsInBits.length; i++) {
			int bits = treeWidthsInBits[i];
			shift -= bits;
			index = key >> shift & mask[i];

			parent = node;
			node   = ((Object[])node)[index];

			// Allocate tree branches where necessary.
			if (node == null && i < treeWidthsInBits.length - 1) {
				// Trying to remove a non-existing value?
				if (value == null) {
					return null;
				}

				node                      = new Object[mask[i + 1] + 1];
				((Object[])parent)[index] = node;
			}
		}

		if (node == null && value != null) {
			size++;
		} else if (node != null && value == null) {
			size--;
		}

		((Object[])parent)[index] = value;

		return (V)node;
	}

	public int getSize() {
		return size;
	}

	public void remove(int key) {
		add(key, null);
	}
}
