/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2019 Mark Jeronimus. All Rights Reversed.
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
 * along with AllUtilities. If not, see <http://www.gnu.org/licenses/>.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.digitalmodular.utilities.collection;

import org.digitalmodular.utilities.NumberUtilities;

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

		this.lowBound = lowBound;
		this.highBound = highBound;
		numBitsUsed = NumberUtilities.bitCount(highBound - lowBound);
		this.treeWidthsInBits = treeWidthsInBits;

		int numBits = 0;
		mask = new int[treeWidthsInBits.length];
		for (int i = 0; i < treeWidthsInBits.length; i++) {
			if (treeWidthsInBits[i] < 1) {
				throw new IllegalArgumentException("One of the bit widths less than 1: " + treeWidthsInBits[i]);
			}
			mask[i] = (1 << treeWidthsInBits[i]) - 1;
			numBits += treeWidthsInBits[i];
		}

		if (numBits != numBitsUsed) {
			throw new IllegalArgumentException("Sum of bit widths (" + numBits
			                                   + ") differs from the actual bits used by the specified range (" +
			                                   numBitsUsed + ")");
		}

		tree = new Object[1 << treeWidthsInBits[0]];
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
			node = ((Object[])node)[index];

			// Allocate tree branches where necessary.
			if (node == null && i < treeWidthsInBits.length - 1) {
				// Trying to remove a non-existing value?
				if (value == null) {
					return null;
				}

				node = new Object[mask[i + 1] + 1];
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
