/*
 * This file is part of Utilities.
 *
 * Copyleft 2024 Mark Jeronimus. All Rights Reversed.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms
 * of the GNU General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.digitalmodular.utilities;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.StringJoiner;

import org.jetbrains.annotations.Contract;

import org.digitalmodular.utilities.annotation.UtilityClass;
import static org.digitalmodular.utilities.ValidatorUtilities.requireAtLeast;

/**
 * @author Mark Jeronimus
 */
// Created 2016-08-02
@UtilityClass
public final class ArrayUtilities {
	public static final byte[]    EMPTY_BYTE_ARRAY    = new byte[0];
	public static final short[]   EMPTY_SHORT_ARRAY   = new short[0];
	public static final int[]     EMPTY_INT_ARRAY     = new int[0];
	public static final long[]    EMPTY_LONG_ARRAY    = new long[0];
	public static final char[]    EMPTY_CHAR_ARRAY    = new char[0];
	public static final float[]   EMPTY_FLOAT_ARRAY   = new float[0];
	public static final double[]  EMPTY_DOUBLE_ARRAY  = new double[0];
	public static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];
	public static final Object[]  EMPTY_OBJECT_ARRAY  = new Object[0];

	/** Makes a defensive copy, except when the array is empty in which case a defensive copy would be pointless. */
	public static int[] defensiveCopy(int[] array) {
		return array.length == 0 ? array : array.clone();
	}

	/** Makes a defensive copy, except when the array is empty in which case a defensive copy would be pointless. */
	public static double[] defensiveCopy(double[] array) {
		return array.length == 0 ? array : array.clone();
	}

	/** Makes a defensive copy, except when the array is empty in which case a defensive copy would be pointless. */
	public static <T> T[] defensiveCopy(T[] array) {
		return array.length == 0 ? array : array.clone();
	}

	/** Makes a defensive copy, except when the list is empty in which case a defensive copy would be pointless. */
	public static <T> List<T> defensiveCopy(List<T> list) {
		return list.isEmpty() ? Collections.emptyList() : Collections.unmodifiableList(new ArrayList<>(list));
	}

	public static <T> Set<T> defensiveCopy(Set<T> set) {
		return set.isEmpty() ? Collections.emptySet() : Collections.unmodifiableSet(new HashSet<>(set));
	}

	public static <K, V> Map<K, V> defensiveCopy(Map<K, V> set) {
		return set.isEmpty() ? Collections.emptyMap() : Collections.unmodifiableMap(new HashMap<>(set));
	}

	/** Converts an array of primitives to an array of boxed primitives. */
	public static Double[] boxed(double... array) {
		Double[] copy = new Double[array.length];
		for (int i = 0; i < array.length; i++) {
			copy[i] = array[i];
		}
		return copy;
	}

	/** Converts an array of boxed primitives to an array of primitives. */
	public static int[] unboxed(Integer[] array) {
		int[] copy = new int[array.length];
		for (int i = 0; i < array.length; i++) {
			copy[i] = array[i];
		}
		return copy;
	}

	public static void shuffle(byte[] array, Random random) {
		for (int i = array.length - 1; i > 0; i--) {
			int j = random.nextInt(i + 1);

			swap(array, i, j);
		}
	}

	public static void swap(byte[] array, int index1, int index2) {
		if (index1 == index2) {
			return;
		}

		byte temp = array[index1];
		array[index1] = array[index2];
		array[index2] = temp;
	}

	public static byte[] generateRandom(int length, Random random) {
		byte[] array = new byte[length];
		random.nextBytes(array);
		return array;
	}

	public static int compareUnsigned(byte[] array1, byte[] array2) {
		boolean null1 = array1 == null;
		boolean null2 = array2 == null;

		if (null1 && null2) {
			return 0;
		}
		if (null1 != null2) {
			return null1 ? -1 : 1;
		}

		int len1   = array1.length;
		int len2   = array2.length;
		int length = Math.min(len1, len2);

		for (int i = 0; i < length; i++) {
			int byte1 = array1[i] & 0xFF;
			int byte2 = array2[i] & 0xFF;

			if (byte1 == byte2) {
				continue;
			}
			return byte1 < byte2 ? -1 : 1;
		}

		if (len1 == len2) {
			return 0;
		}
		return (len1 < len2) ? -1 : 1;
	}

	public static int compareNullTerminatedString(byte[] array1, byte[] array2) {
		boolean null1 = array1 == null;
		boolean null2 = array2 == null;

		if (null1 && null2) {
			return 0;
		}
		if (null1 != null2) {
			return null1 ? -1 : 1;
		}

		int len1   = array1.length;
		int len2   = array2.length;
		int length = Math.min(len1, len2);

		for (int i = 0; i < length; i++) {
			int byte1 = array1[i] & 0xFF;
			int byte2 = array2[i] & 0xFF;

			if (byte1 == 0 || byte2 == 0) {
				if (byte1 == 0 && byte2 == 0) {
					continue;
				}
				return byte1 == 0 ? -1 : 1;
			}

			if (byte1 == byte2) {
				continue;
			}
			return byte1 < byte2 ? -1 : 1;
		}

		if (len1 == len2) {
			return 0;
		}
		return (len1 < len2) ? -1 : 1;
	}

	public static byte[] stringToNullTerminatedString(String string, int arrayLength) {
		byte[] array;

		array = string.getBytes(StandardCharsets.UTF_8);

		if (array.length > arrayLength) {
			throw new IllegalArgumentException("String has more than " + arrayLength + " bytes: " + array.length +
			                                   ". How many characters that is depends on UTF-8.");
		}

		array = Arrays.copyOf(array, arrayLength);
		return array;
	}

	public static void getCircularBufferSection(byte[] circularBuffer, int offset, byte[] dest) {
		int lengthBeforeWrap = getLengthBeforeWrap(circularBuffer.length, offset, dest.length);
		int lengthAfterWrap  = getLengthAfterWrap(circularBuffer.length, offset, dest.length);

		assert lengthAfterWrap + lengthAfterWrap == dest.length;

		System.arraycopy(circularBuffer, offset, dest, 0, lengthBeforeWrap);
		System.arraycopy(circularBuffer, 0, dest, lengthBeforeWrap, lengthAfterWrap);
	}

	public static void putCircularBufferSection(byte[] src, byte[] circularBuffer, int offset) {
		int lengthBeforeWrap = getLengthBeforeWrap(circularBuffer.length, offset, src.length);
		int lengthAfterWrap  = getLengthAfterWrap(circularBuffer.length, offset, src.length);

		assert lengthAfterWrap + lengthAfterWrap == src.length;

		System.arraycopy(src, 0, circularBuffer, offset, lengthBeforeWrap);
		System.arraycopy(src, lengthBeforeWrap, circularBuffer, 0, lengthAfterWrap);
	}

	@Contract(pure = true)
	private static int getLengthBeforeWrap(int arrayLength, int offset, int length) {
		return Math.min(length, arrayLength - offset);
	}

	@Contract(pure = true)
	private static int getLengthAfterWrap(int arrayLength, int offset, int length) {
		return Math.max(0, offset + length - arrayLength);
	}

	public static String toHexStringTruncated(byte[] array, int maxElements) {
		requireAtLeast(1, maxElements, "maxElements");

		// 20 assumes at most 2**31-1 = 2147483647 more elements: "[xx, yy, (2147483647 total)]"
		//                                                         ^        ^^^^^^^^^^^^^^^^^^^
		StringJoiner sj = new StringJoiner(", ", "[", "]");

		for (int i = 0; i < array.length && i < maxElements; i++) {
			sj.add(HexUtilities.toUnsignedWordString(array[i]));
		}

		if (array.length > maxElements) {
			sj.add("(" + array.length + " total)");
		}

		return sj.toString();
	}

	public static int indexOf(Object[] array, Object searchFor) {
		for (int i = 0; i < array.length; i++) {
			if (Objects.equals(array[i], searchFor)) {
				return i;
			}
		}

		return -1;
	}

	public static Set<String> asSet(String... strings) {
		return new HashSet<>(Arrays.asList(strings));
	}

	public static int[] toIntArray(Collection<Integer> integers) {
		int[] result = new int[integers.size()];
		int   i      = 0;
		for (Iterator<Integer> iter = integers.iterator(); iter.hasNext(); i++) {
			result[i] = iter.next();
			i++;
		}

		return result;
	}
}
