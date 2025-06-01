package nl.airsupplies.utilities;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import static java.nio.ByteOrder.BIG_ENDIAN;
import static java.nio.ByteOrder.LITTLE_ENDIAN;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import nl.airsupplies.utilities.annotation.UtilityClass;
import static nl.airsupplies.utilities.validator.ArrayValidatorUtilities.requireArrayLengthAtLeast;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireAtLeast;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireBetween;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireThat;

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
	public static final String[]  EMPTY_STRING_ARRAY  = new String[0];
	public static final Object[]  EMPTY_OBJECT_ARRAY  = new Object[0];

	/**
	 * As opposed to {@link Arrays#copyOf(int[], int)}, this doesn't create a new array if it is not necessary.
	 */
	public static byte[] copyIfNecessary(byte[] array, int newLength) {
		if (array.length == 0 || array.length == newLength) {
			return array;
		} else {
			return Arrays.copyOf(array, newLength);
		}
	}

	/**
	 * As opposed to {@link Arrays#copyOf(int[], int)}, this doesn't create a new array if it is not necessary.
	 */
	public static int[] copyIfNecessary(int[] array, int newLength) {
		if (array.length == 0 || array.length == newLength) {
			return array;
		} else {
			return Arrays.copyOf(array, newLength);
		}
	}

	/**
	 * As opposed to {@link Arrays#copyOf(Object[], int)}, this doesn't create a new array if it is not necessary.
	 */
	public static <T> T[] copyIfNecessary(T[] array, int newLength) {
		if (array.length == 0 || array.length == newLength) {
			return array;
		} else {
			return Arrays.copyOf(array, newLength);
		}
	}

	/** Makes a defensive copy, except when the array is empty in which case a defensive copy would be pointless. */
	public static byte[] defensiveCopy(byte[] array) {
		return array.length == 0 ? array : array.clone();
	}

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

	/** Concatenates two or more non-primitive arrays. */
	public static byte[] concatenate(byte[] a, byte... b) {
		byte[] result = Arrays.copyOf(a, a.length + b.length);
		System.arraycopy(b, 0, result, a.length, b.length);
		return result;
	}

	/** Concatenates two or more non-primitive arrays. */
	public static <T> T[] concatenate(T[] a, T... b) {
		T[] result = Arrays.copyOf(a, a.length + b.length);
		System.arraycopy(b, 0, result, a.length, b.length);
		return result;
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

	public static void swap(byte[] array, int index1, int index2) {
		if (index1 == index2) {
			return;
		}

		byte temp = array[index1];
		array[index1] = array[index2];
		array[index2] = temp;
	}

	public static void shuffle(byte[] array, Random random) {
		for (int i = array.length - 1; i > 0; i--) {
			int j = random.nextInt(i + 1);

			swap(array, i, j);
		}
	}

	public static <E> E randomElement(E[] values) {
		requireArrayLengthAtLeast(1, values, "values");

		return values[ThreadLocalRandom.current().nextInt(values.length)];
	}

	public static byte[] generateRandom(int length, Random random) {
		byte[] array = new byte[length];
		random.nextBytes(array);
		return array;
	}

	/** Finds the first element in the array equal to the specified element. */
	public static int indexOf(byte[] array, byte valueToFind) {
		return indexOf(array, valueToFind, 0);
	}

	/** Finds the first element in the array equal to the specified element. */
	public static int indexOf(byte[] array, byte valueToFind, int fromIndex) {
		requireNonNull(array, "array");
		requireAtLeast(0, fromIndex, "fromIndex");

		for (int i = fromIndex; i < array.length; i++) {
			if (array[i] == valueToFind) {
				return i;
			}
		}

		return -1;
	}

	/** Finds the first element in the array {@link Objects#equals(Object, Object) equal} to the specified element. */
	public static <T> int indexOf(T[] array, @Nullable T elementToFind) {
		requireNonNull(array, "array");

		for (int i = 0; i < array.length; i++) {
			if (Objects.equals(array[i], elementToFind)) {
				return i;
			}
		}

		return -1;
	}

	// Copied from String.indexOf()
	public static int indexOf(byte[] array, byte[] sequenceToFind, int fromIndex) {
		requireNonNull(array, "array");
		requireNonNull(sequenceToFind, "sequenceToFind");
		requireAtLeast(0, fromIndex, "fromIndex");

		if (fromIndex >= array.length) {
			return (sequenceToFind.length == 0 ? array.length : -1);
		} else if (fromIndex < 0) {
			fromIndex = 0;
		}

		if (sequenceToFind.length == 0) {
			return fromIndex;
		}

		byte first = sequenceToFind[0];
		int  max   = (array.length - sequenceToFind.length);

		for (int i = fromIndex; i <= max; i++) {
			/* Look for first byte. */
			if (array[i] != first) {
				while (++i <= max && array[i] != first) {
				}
			}

			if (i <= max) {
				int j   = i + 1;
				int end = j + sequenceToFind.length - 1;
				for (int k = 1; j < end && array[j] == sequenceToFind[k]; j++, k++) {
				}

				if (j == end) {
					return i;
				}
			}
		}

		return -1;
	}

	// Copied from String.startsWith()
	public static boolean startsWith(byte[] array, byte[] prefix) {
		requireNonNull(array, "array");
		requireNonNull(prefix, "prefix");

		int pc = prefix.length;
		if (pc > array.length) {
			return false;
		}

		int to = 0;
		int po = 0;
		while (--pc >= 0) {
			if (array[to++] != prefix[po++]) {
				return false;
			}
		}

		return true;
	}

	// Copied from String.endsWith()
	public static boolean endsWith(byte[] array, byte[] postfix) {
		requireNonNull(array, "array");
		requireNonNull(postfix, "postfix");

		int to = array.length - postfix.length;
		if (to < 0) {
			return false;
		}

		int po = 0;
		int pc = postfix.length;
		while (--pc >= 0) {
			if (array[to++] != postfix[po++]) {
				return false;
			}
		}

		return true;
	}

	// https://www.baeldung.com/java-concatenate-arrays
	public static <T> T concat(T array1, T array2) {
		if (!array1.getClass().isArray() || !array2.getClass().isArray()) {
			throw new IllegalArgumentException("Only arrays are accepted.");
		}

		Class<?> compType1 = array1.getClass().getComponentType();
		Class<?> compType2 = array2.getClass().getComponentType();

		if (!compType1.equals(compType2)) {
			throw new IllegalArgumentException("Two arrays have different types.");
		}

		int len1 = Array.getLength(array1);
		int len2 = Array.getLength(array2);

		@SuppressWarnings("unchecked")
		//the cast is safe due to the previous checks
		T result = (T)Array.newInstance(compType1, len1 + len2);

		System.arraycopy(array1, 0, result, 0, len1);
		System.arraycopy(array2, 0, result, len1, len2);

		return result;
	}

	/**
	 * Relative <i>put</i> method for writing a 3-byte int (which MySQL calls a MEDIUMINT). Byte order is
	 * respected.
	 *
	 * @return the buffer, to allow method-chaining.
	 * @see ByteBuffer#putInt(int)
	 */
	public static ByteBuffer putMediumInt(ByteBuffer buffer, int value) {
		requireThat(buffer.remaining() >= 3, () -> "buffer has less than 3 bytes remaining");

		if (buffer.order() == BIG_ENDIAN) {
			buffer.put((byte)((value >> 16) & 0xFF));
			buffer.put((byte)((value >> 8) & 0xFF));
			buffer.put((byte)(value & 0xFF));
		} else if (buffer.order() == LITTLE_ENDIAN) {
			buffer.put((byte)(value & 0xFF));
			buffer.put((byte)((value >> 8) & 0xFF));
			buffer.put((byte)((value >> 16) & 0xFF));
		} else {
			throw new UnsupportedOperationException(Objects.toString(buffer.order()));
		}

		return buffer;
	}

	/**
	 * Relative <i>get</i> method for reading a 3-byte unsigned int (which MySQL calls a MEDIUMINT). Byte order is
	 * respected.
	 *
	 * @see ByteBuffer#getInt()
	 */
	public static int getMediumInt(ByteBuffer buffer) {
		requireThat(buffer.remaining() >= 3, () -> "buffer has less than 3 bytes remaining");

		if (buffer.order() == BIG_ENDIAN) {
			return (buffer.get() & 0xFF) << 16 |
			       (buffer.get() & 0xFF) << 8 |
			       (buffer.get() & 0xFF);
		} else if (buffer.order() == LITTLE_ENDIAN) {
			return (buffer.get() & 0xFF) |
			       (buffer.get() & 0xFF) << 8 |
			       (buffer.get() & 0xFF) << 16;
		} else {
			throw new UnsupportedOperationException(Objects.toString(buffer.order()));
		}
	}

	/**
	 * Absolute <i>get</i> method for reading a 3-byte unsigned int (which MySQL calls a MEDIUMINT). Byte order is
	 * respected.
	 *
	 * @see ByteBuffer#getInt()
	 */
	public static int getMediumInt(ByteBuffer buffer, int index) {
		requireBetween(0, buffer.limit() - 3, index, "index");

		if (buffer.order() == BIG_ENDIAN) {
			return (buffer.get(index) & 0xFF) << 16 |
			       (buffer.get(index + 1) & 0xFF) << 8 |
			       (buffer.get(index + 2) & 0xFF);
		} else if (buffer.order() == LITTLE_ENDIAN) {
			return (buffer.get(index) & 0xFF) |
			       (buffer.get(index + 1) & 0xFF) << 8 |
			       (buffer.get(index + 2) & 0xFF) << 16;
		} else {
			throw new UnsupportedOperationException(Objects.toString(buffer.order()));
		}
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
}
