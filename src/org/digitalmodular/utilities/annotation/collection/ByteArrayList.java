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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Resizable-array implementation for {@code byte[]}, similar to {@link ArrayList} for Object[]. It contains all
 * the functions of the {@link List} interface except the iterator (TODO).
 * <p>
 * Most of this is copied from the source code of {@code ArrayList.java} of Java 1.6.
 *
 * @author Mark Jeronimus
 */
// Created 2013-12-03
public class ByteArrayList implements Serializable {

	/**
	 * The array buffer into which the elements of the {@link ByteArrayList} are stored. The capacity of the {@link
	 * ByteArrayList} is the length of this array buffer.
	 */
	public    byte[] values = new byte[256];
	/**
	 * The size of the {@link ByteArrayList} (the number of elements it contains).
	 */
	protected int    size   = 0;

	/**
	 * Constructs an empty list with the specified initial capacity.
	 *
	 * @param initialCapacity the initial capacity of the list
	 * @throws IllegalArgumentException if the specified initial capacity is negative
	 */
	public ByteArrayList(int initialCapacity) {
		ensureCapacity(initialCapacity);
	}

	/**
	 * Constructs an empty list with an initial capacity of 16.
	 */
	public ByteArrayList() {
		this(32);
	}

	/**
	 * Constructs a list containing the elements of the specified array, either as a copy or as a wrapper.
	 *
	 * @param array the array whose elements are to be placed into this list
	 * @throws NullPointerException if the specified array is null
	 */
	public ByteArrayList(byte[] array, boolean copy) {
		if (copy) {
			ensureCapacity(array.length);
			addAll(array);
		} else {
			size = array.length;
			values = array;
		}
	}

	/**
	 * Constructs a list containing the elements of the specified array.
	 *
	 * @param array the array whose elements are to be placed into this list
	 * @throws NullPointerException if the specified array is null
	 */
	public ByteArrayList(byte[] array) {
		this(array, false);
	}

	/**
	 * Constructs a list containing the elements of the specified list.
	 *
	 * @param list the array whose elements are to be placed into this list
	 */
	public ByteArrayList(ByteArrayList list) {
		ensureCapacity(list.size);
		addAll(list);
	}

	/**
	 * Trims the capacity of this  instance to be the list's current size. An application can use
	 * this operation to minimize the storage of an  instance.
	 */
	public void trimToSize() {
		int oldCapacity = values.length;
		if (size < oldCapacity) {
			values = Arrays.copyOf(values, size);
		}
	}

	/**
	 * Increases the capacity of this  instance, if necessary, to ensure that it can hold at least
	 * the number of elements specified by the minimum capacity argument.
	 *
	 * @param minCapacity the desired minimum capacity. The actual resulting capacity may be larger.
	 */
	public void ensureCapacity(int minCapacity) {
		int oldCapacity = values.length;
		if (minCapacity > oldCapacity) {
			int newCapacity = oldCapacity * 2;
			if (newCapacity < minCapacity) {
				newCapacity = minCapacity;
			}
			// minCapacity is usually close to size, so this is a win:
			values = Arrays.copyOf(values, newCapacity);
		}
	}

	/**
	 * Returns the number of elements in this list.
	 *
	 * @return the number of elements in this list
	 */
	public int size() {
		return size;
	}

	/**
	 * Returns {@code true} if this list contains no elements.
	 *
	 * @return {@code true} if this list contains no elements
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Removes all of the values from this list. The list will be empty after this call returns.
	 */
	public void clear() {
		size = 0;
	}

	/**
	 * Appends the specified value to the end of this list.
	 *
	 * @param value the value to be appended to this list
	 * @return {@code true} (as specified by {@link Collection#add})
	 */
	public boolean add(byte value) {
		ensureCapacity(size + 1);
		values[size] = value;
		size++;
		return true;
	}

	/**
	 * Inserts the specified value at the specified position in this list. Shifts the value currently at that position
	 * (if any) and any subsequent values to the right (adds one to their indices).
	 *
	 * @param index index at which the specified value is to be inserted
	 * @param value the value to be inserted
	 * @throws IndexOutOfBoundsException if index is out of range ( {@code index &lt; 0 || index &gt;= size()}).
	 */
	public void add(int index, byte value) {
		rangeCheck(index);

		ensureCapacity(size + 1);
		System.arraycopy(values, index, values, index + 1, size - index);
		values[index] = value;

		size++;
	}

	/**
	 * Appends all of the values in the specified array to the end of this list. The behavior of this operation is
	 * undefined if the specified array is modified while the operation is in progress.
	 *
	 * @param array the array containing the values to be added to this list
	 * @return {@code true} if this list changed as a result of the call
	 * @throws NullPointerException if the specified array is null
	 */
	public boolean addAll(byte[] array) {
		int numNew = array.length;
		ensureCapacity(size + numNew);
		System.arraycopy(array, 0, values, size, numNew);
		size += numNew;
		return numNew != 0;
	}

	/**
	 * Appends all of the values in the specified list to the end of this list. The behavior of this operation is
	 * undefined if the specified list is modified while the operation is in progress. (Except if the specified list is
	 * this list)
	 *
	 * @param list the list containing the values to be added to this list
	 * @return {@code true} if this list changed as a result of the call
	 * @throws NullPointerException if the specified list is null
	 */
	public boolean addAll(ByteArrayList list) {
		int numNew = list.values.length;
		ensureCapacity(size + numNew);
		System.arraycopy(list.values, 0, values, size, numNew);
		size += numNew;
		return numNew != 0;
	}

	/**
	 * Inserts all of the values in the specified array into this list, starting at the specified position. Shifts the
	 * value currently at that position (if any) and any subsequent values to the right (increases their indices).
	 *
	 * @param index index at which to insert the first value from the specified array
	 * @param array the array containing the values to be added to this list
	 * @return {@code true} if this list changed as a result of the call
	 * @throws IndexOutOfBoundsException if index is out of range ( {@code index &lt; 0 || index &gt;= size()}).
	 * @throws NullPointerException      if the specified array is null
	 */
	public boolean addAll(int index, byte[] array) {
		rangeCheck(index, index + array.length);

		int numNew = array.length;
		ensureCapacity(size + numNew);

		if (index < size) {
			System.arraycopy(values, index, values, index + numNew, size - index);
		}

		System.arraycopy(array, 0, values, index, numNew);
		size += numNew;
		return numNew != 0;
	}

	/**
	 * Inserts all of the values in the specified array into this list, starting at the specified position. Shifts the
	 * value currently at that position (if any) and any subsequent values to the right (increases their indices).
	 *
	 * @param index index at which to insert the first value from the specified array
	 * @param array the array containing the values to be added to this list
	 * @return {@code true} if this list changed as a result of the call
	 * @throws IndexOutOfBoundsException if index is out of range ( {@code index < 0 || length < 0 || index >=
	 *                                   size()} ).
	 * @throws NullPointerException      if the specified array is null
	 */
	public boolean addAll(int index, byte[] array, int offset, int length) {
		rangeCheck(index + length);

		ensureCapacity(size + length);

		if (index < size) {
			System.arraycopy(values, index, values, index + length, size - index);
		}

		System.arraycopy(array, offset, values, index, length);
		size += length;
		return length != 0;
	}

	/**
	 * Removes the value at the specified position in this list. Shifts any subsequent values to the left (subtracts
	 * one
	 * from their indices).
	 *
	 * @param index the index of the value to be removed
	 * @return the value that was removed from the list
	 * @throws IndexOutOfBoundsException if index is out of range ( {@code index &lt; 0 || index &gt;= size()}).
	 */
	public byte remove(int index) {
		rangeCheck(index);

		byte oldValue = values[index];

		int numMoved = size - index - 1;
		if (numMoved > 0) {
			System.arraycopy(values, index + 1, values, index, numMoved);
		}

		size--;

		return oldValue;
	}

	/**
	 * Removes from this list all of the values whose index is between {@code fromIndex}, inclusive, and
	 * {@code toIndex}, exclusive. Shifts any succeeding values to the left (reduces their index). This call
	 * shortens the list by {@code (toIndex - fromIndex)} values. (If {@code toIndex==fromIndex}, this
	 * operation has no effect.)
	 *
	 * @param fromIndex index of first value to be removed
	 * @param toIndex   index after last value to be removed
	 * @throws IndexOutOfBoundsException if fromIndex or toIndex out of range ( {@code fromIndex < 0 || fromIndex >
	 *                                   toIndex || toIndex >= size()} ).
	 */
	public void removeRange(int fromIndex, int toIndex) {
		rangeCheck(fromIndex);
		rangeCheck(toIndex - 1);

		int numMoved = size - toIndex;
		System.arraycopy(values, toIndex, values, fromIndex, numMoved);

		size -= toIndex - fromIndex;
	}

	/**
	 * Returns the value at the specified position in this list.
	 *
	 * @param index index of the element to return
	 * @return the element at the specified position in this list
	 * @throws IndexOutOfBoundsException if index is out of range ( {@code index &lt; 0 || index &gt;= size()}).
	 */
	public byte get(int index) {
		rangeCheck(index);

		return values[index];
	}

	/**
	 * Replaces the value at the specified position in this list with the specified value.
	 *
	 * @param index index of the element to replace
	 * @param value the value to be stored at the specified position
	 * @return the element previously at the specified position
	 * @throws IndexOutOfBoundsException if index is out of range ( {@code index &lt; 0 || index &gt;= size()}).
	 */
	public byte set(int index, byte value) {
		rangeCheck(index);

		byte oldValue = values[index];
		values[index] = value;
		return oldValue;
	}

	/**
	 * Replaces all of the values in the specified array into this list, starting at the specified position.
	 *
	 * @param fromIndex index at which to insert the first value from the specified array
	 * @param array     the array containing the values to be stored, starting at the specified position
	 * @throws IndexOutOfBoundsException if index is out of range {@code (fromIndex < 0 || index + array.length >=
	 *                                   size())} .
	 * @throws NullPointerException      if the specified array is null
	 */
	public void setAll(int fromIndex, byte[] array) {
		rangeCheck(fromIndex, fromIndex + array.length);

		System.arraycopy(array, 0, values, fromIndex, array.length);
	}

	/**
	 * Replaces a range of the specified array into this list, starting at the specified position.
	 *
	 * @param fromIndex index at which to insert the first value from the specified array
	 * @param array     the array containing the values to be stored, starting at the specified position
	 * @param offset    the starting position of the range in the specified array.
	 * @param length    the length of the range in the specified array.
	 * @throws IndexOutOfBoundsException if index is out of range {@code (fromIndex < 0 || offset < 0 || index +
	 *                                   length >= size())} .
	 * @throws NullPointerException      if the specified array is null
	 */
	public void setAll(int fromIndex, byte[] array, int offset, int length) {
		rangeCheck(fromIndex, fromIndex + length);

		System.arraycopy(array, offset, values, fromIndex, length);
	}

	/**
	 * Replaces in this list all of the values whose index is between {@code fromIndex}, inclusive, and
	 * {@code toIndex}, exclusive.
	 *
	 * @param fromIndex index of first value to be removed
	 * @param toIndex   index after last value to be removed
	 * @param value     the value to be stored at the specified position
	 * @throws IndexOutOfBoundsException if fromIndex or toIndex out of range ( {@code fromIndex < 0 || fromIndex >
	 *                                   toIndex || toIndex >= size()} ).
	 */
	public void setRange(int fromIndex, int toIndex, byte value) {
		rangeCheck(fromIndex, toIndex);

		for (int index = fromIndex; index < toIndex; index++) {
			values[index] = value;
		}
	}

	/**
	 * Returns {@code true} if this list contains the specified value. More formally, returns {@code true} if
	 * and only if this list contains at least one value {@code e} such that {@code (o==null&nbsp;?&nbsp;e==null&nbsp;
	 * :&nbsp;o.equals(e))}.
	 *
	 * @param value the value whose presence in this list is to be tested
	 * @return {@code true} if this list contains the specified value
	 */
	public boolean contains(byte value) {
		for (int i = 0; i < size; i++) {
			if (value == values[i]) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the index of the first occurrence of the specified value in this list, or -1 if this list does not
	 * contain the value. More formally, returns the lowest index {@code i} such that
	 * {@code (o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))} , or -1 if there is no such index.
	 *
	 * @param value the search pattern.
	 * @return the index, or -1.
	 */
	public int indexOf(byte value) {
		for (int i = 0; i < size; i++) {
			if (value == values[i]) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Returns the index of the first occurrence of the specified array in this list, or -1 if this list does not
	 * contain the value. More formally, returns the lowest index {@code i} such that
	 * {@code Arrays.equals(toArray(i, i+array.length, new byte[0]), array)} returns true , or -1 if there is no
	 * such index.
	 *
	 * @param array the search pattern.
	 * @return the index, or -1.
	 */
	public int indexOf(byte[] array) {
		if (size == 0) {
			return array.length == 0 ? 0 : -1;
		} else if (array.length == 0) {
			return 0;
		}

		byte first = array[0];
		int  max   = size - array.length;
		for (int i = 0; i <= max; i++) {
			// Look for first byte.
			if (values[i] != first) {
				++i;
				while (i <= max && values[i] != first) {
					++i;
				}
			}

			if (i <= max) {
				// Found first byte, now look at the rest of v2.
				int j   = i + 1;
				int end = j + array.length - 1;
				for (int k = 1; j < end && values[j] == array[k]; j++, k++) {
				}

				if (j == end) {
					// Found whole array.
					return i;
				}
			}
		}

		return -1;
	}

	/**
	 * Returns the index of the last occurrence of the specified value in this list, or -1 if this list does not
	 * contain
	 * the value. More formally, returns the highest index {@code i} such that
	 * {@code (o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))} , or -1 if there is no such index.
	 */
	public int lastIndexOf(byte value) {
		for (int i = size - 1; i >= 0; i--) {
			if (value == values[i]) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Returns an array containing all of the elements in this list in proper sequence (from first to last element).
	 * <p>
	 * <p>
	 * The returned array will be "safe" in that no references to it are maintained by this list. (In other words, this
	 * method must allocate a new array). The caller is thus free to modify the returned array.
	 *
	 * @return an array containing all of the elements in this list in proper sequence
	 */
	public byte[] toArray() {
		return Arrays.copyOf(values, size);
	}

	/**
	 * Returns an array containing all of the elements in this list in proper sequence (from first to last element). If
	 * the list fits in the specified array, it is returned therein. Otherwise, a new array is allocated with the size
	 * of this list.
	 * <p>
	 * <p>
	 * If the list fits in the specified array with room to spare (i.e., the array has more elements than the list),
	 * then the only way to know the length of the data is by calling the {@link #size()} method.
	 *
	 * @param array the array into which the elements of the list are to be stored, if it is big enough; otherwise, a
	 *              new array of the same runtime type is allocated for this purpose.
	 * @return an array containing the elements of the list
	 * @throws NullPointerException if the specified array is null
	 */
	public byte[] toArray(byte[] array) {
		if (array.length < size) {
			// Make a new array:
			return Arrays.copyOf(values, size);
		}
		System.arraycopy(values, 0, array, 0, size);
		return array;
	}

	/**
	 * Returns an array containing a subset of the elements in this list in proper sequence (from first to last
	 * element). If the list fits in the specified array, it is returned therein. Otherwise, a new array is allocated
	 * with the size of this list.
	 * <p>
	 * <p>
	 * If the list fits in the specified array with room to spare (i.e., the array has more elements than the list),
	 * then the amount of elements returned equals {@code toIndex - fromIndex - 1}.
	 *
	 * @param fromIndex the beginning index, inclusive.
	 * @param toIndex   the ending index, exclusive.
	 * @param array     the array into which the elements of the list are to be stored, if it is big enough; otherwise,
	 *                  a new array of the same runtime type is allocated for this purpose.
	 * @return an array containing the elements of the list
	 * @throws NullPointerException if the specified array is null
	 */
	public byte[] toArray(int fromIndex, int toIndex, byte[] array) {
		rangeCheck(fromIndex);
		rangeCheck(toIndex - 1);

		int length = toIndex - fromIndex;
		if (array.length < length) {
			// Make a new array:
			array = new byte[length];
		}
		System.arraycopy(values, fromIndex, array, 0, length);
		return array;
	}

	/**
	 * Checks if the given index is in range. If not, throws an appropriate runtime exception.
	 */
	private void rangeCheck(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
		}
	}

	/**
	 * Checks if the given index range is in range. If not, throws an appropriate runtime exception.
	 */
	private void rangeCheck(int fromIndex, int toIndex) {
		if (fromIndex < 0 || fromIndex > toIndex || toIndex >= size) {
			throw new IndexOutOfBoundsException(
					"fromIndex: " + fromIndex + ", toIndex: " + toIndex + ", Size: " + size);
		}
	}
}
