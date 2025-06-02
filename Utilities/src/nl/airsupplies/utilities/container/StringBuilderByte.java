package nl.airsupplies.utilities.container;

import java.nio.charset.Charset;
import java.util.Arrays;

import net.jcip.annotations.NotThreadSafe;

import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireAtLeast;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * A {@link StringBuilder} alternative that works with bytes instead of characters, which can be converted to a string
 * using a specified {@link Charset code page}.
 * <p>
 * This is useful in cases where characters are received one byte at a time, where each byte can be a part of a
 * character or of a surrogate pair.
 * <p>
 * Because this container doesn't work with 'characters' at all, it is neither a {@link CharSequence} nor an
 * {@link Appendable}.
 * <p>
 * The implementation is mostly a copy of both {@code AbstractStringBuilder} and {@code StringBuilder} (of OpenJDK
 * 11.0), with incompatible methods removed and code cleanup applied to the rest.
 * Comments and Javadoc are not cleaned up rigorously (other than bulk search/replace) so it can contain errors.
 *
 * @author Mark Jeronimus
 */
// Created 2020-06-29
@NotThreadSafe
public class StringBuilderByte {
	/**
	 * The maximum size of array to allocate (unless necessary).
	 * Some VMs reserve some header words in an array.
	 * Attempts to allocate larger arrays may result in
	 * OutOfMemoryError: Requested array size exceeds VM limit
	 */
	private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

	private final Charset charset;

	private byte[] value;
	private int    count;

	/**
	 * Constructs a string builder with no bytes in it and an
	 * initial capacity specified by the {@code initialCapacity} argument.
	 *
	 * @param initialCapacity the initial capacity.
	 * @throws IllegalArgumentException if the {@code initialCapacity}
	 *                                  argument is less than {@code 0}.
	 */
	public StringBuilderByte(int initialCapacity, Charset charset) {
		requireAtLeast(0, initialCapacity, "initialCapacity");
		this.charset = requireNonNull(charset, "charset");

		value = new byte[initialCapacity];
		count = 0;
	}

	/**
	 * Returns the current capacity. The capacity is the amount of storage
	 * available for newly inserted bytes, beyond which an allocation
	 * will occur.
	 *
	 * @return the current capacity
	 */
	public int capacity() {
		return value.length;
	}

	/**
	 * Returns the length (byte count).
	 *
	 * @return the length of the sequence of bytes currently
	 * represented by this object
	 */
	public int length() {
		return count;
	}

	/**
	 * Appends the string representation of the {@code byte}
	 * argument to this sequence.
	 * <p>
	 * The argument is appended to the contents of this sequence.
	 * The length of this sequence increases by {@code 1}.
	 *
	 * @param b a {@code byte}.
	 * @return a reference to this object.
	 */
	public StringBuilderByte append(byte b) {
		ensureCapacityInternal(count + 1);
		value[count++] = b;
		return this;
	}

	/**
	 * Appends the string representation of the {@code byte} array
	 * argument to this sequence.
	 * <p>
	 * The bytes of the array argument are appended, in order, to
	 * the contents of this sequence. The length of this sequence
	 * increases by the length of the argument.
	 *
	 * @param bytes the bytes to be appended.
	 * @return a reference to this object.
	 */
	public StringBuilderByte append(byte[] bytes) {
		requireNonNull(bytes, "bytes");

		int len = bytes.length;
		ensureCapacityInternal(count + len);
		System.arraycopy(bytes, 0, value, count, len);
		count += len;

		return this;
	}

	/**
	 * Appends the string representation of a subarray of the
	 * {@code byte} array argument to this sequence.
	 * <p>
	 * Bytes of the {@code byte} array {@code bytes}, starting at
	 * index {@code offset}, are appended, in order, to the contents
	 * of this sequence. The length of this sequence increases
	 * by the value of {@code len}.
	 *
	 * @param bytes  the bytes to be appended.
	 * @param offset the index of the first {@code byte} to append.
	 * @param len    the number of {@code byte}s to append.
	 * @return a reference to this object.
	 * @throws IndexOutOfBoundsException if {@code offset < 0} or {@code len < 0}
	 *                                   or {@code offset+len > bytes.length}
	 */
	public StringBuilderByte append(byte[] bytes, int offset, int len) {
		requireNonNull(bytes, "bytes");
		if (offset < 0 || offset > count) {
			throw new StringIndexOutOfBoundsException("offset " + offset);
		} else if (len < 0 || offset > bytes.length - len) {
			throw new StringIndexOutOfBoundsException(
					"offset " + offset + ", len " + len + ", bytes.length " + bytes.length);
		}

		ensureCapacityInternal(count + len);
		System.arraycopy(bytes, offset, value, count, len);
		count += len;

		return this;
	}

	/**
	 * Appends the specified {@code StringBuffer} to this sequence.
	 * <p>
	 * The bytes of the {@code StringBuffer} argument are appended,
	 * in order, to this sequence, increasing the
	 * length of this sequence by the length of the argument.
	 * If {@code other} is {@code null}, then the four bytes
	 * {@code "null"} are appended to this sequence.
	 * <p>
	 * Let <i>n</i> be the length of this byte sequence just prior to
	 * execution of the {@code append} method. Then the byte at index
	 * <i>k</i> in the new byte sequence is equal to the byte at
	 * index <i>k</i> in the old byte sequence, if <i>k</i> is less than
	 * <i>n</i>; otherwise, it is equal to the byte at index <i>k-n</i>
	 * in the argument {@code other}.
	 *
	 * @param other the {@code StringBuffer} to append.
	 * @return a reference to this object.
	 */
	public StringBuilderByte append(StringBuilderByte other) {
		requireNonNull(other, "other");

		int len = other.count;
		ensureCapacityInternal(count + len);
		other.getBytes(0, len, value, count);
		count += len;

		return this;
	}

	/**
	 * Inserts the string representation of the {@code byte} array
	 * argument into this sequence.
	 * <p>
	 * The bytes of the array argument are inserted into the
	 * contents of this sequence at the position indicated by
	 * {@code index}. The length of this sequence increases by
	 * the length of the argument.
	 * <p>
	 * The {@code index} argument must be greater than or equal to
	 * {@code 0}, and less than or equal to the {@linkplain #length() length}
	 * of this sequence.
	 *
	 * @param index the index.
	 * @param bytes a byte array.
	 * @return a reference to this object.
	 * @throws StringIndexOutOfBoundsException if the index is invalid.
	 */
	public StringBuilderByte insert(int index, byte[] bytes) {
		requireNonNull(bytes, "bytes");
		if (index < 0 || index > count) {
			throw new StringIndexOutOfBoundsException("index " + index);
		}

		int len = bytes.length;
		ensureCapacityInternal(count + len);
		System.arraycopy(value, index, value, index + len, count - index);
		System.arraycopy(bytes, 0, value, index, len);
		count += len;
		return this;
	}

	/**
	 * Inserts the string representation of a subarray of the {@code bytes}
	 * array argument into this sequence. The subarray begins at the
	 * specified {@code offset} and extends {@code len} {@code byte}s.
	 * The bytes of the subarray are inserted into this sequence at
	 * the position indicated by {@code index}. The length of this
	 * sequence increases by {@code len} {@code byte}s.
	 *
	 * @param index  position at which to insert subarray.
	 * @param bytes  A {@code byte} array.
	 * @param offset the index of the first {@code byte} in subarray to
	 *               be inserted.
	 * @param len    the number of {@code byte}s in the subarray to
	 *               be inserted.
	 * @return This object
	 * @throws StringIndexOutOfBoundsException if {@code index}
	 *                                         is negative or greater than {@code length()}, or
	 *                                         {@code offset} or {@code len} are negative, or
	 *                                         {@code (offset+len)} is greater than
	 *                                         {@code bytes.length}.
	 */
	public StringBuilderByte insert(int index, byte[] bytes, int offset, int len) {
		requireNonNull(bytes, "bytes");
		if (index < 0 || index > count) {
			throw new StringIndexOutOfBoundsException("index " + index);
		} else if (offset < 0 || len < 0 || offset > bytes.length - len) {
			throw new StringIndexOutOfBoundsException(
					"offset " + offset + ", len " + len + ", bytes.length " + bytes.length);
		}

		ensureCapacityInternal(count + len);
		System.arraycopy(value, index, value, index + len, count - index);
		System.arraycopy(bytes, offset, value, index, len);
		count += len;

		return this;
	}

	/**
	 * Inserts the string representation of the {@code byte}
	 * argument into this sequence.
	 * <p>
	 * The {@code index} argument must be greater than or equal to
	 * {@code 0}, and less than or equal to the {@linkplain #length() length}
	 * of this sequence.
	 *
	 * @param index the index.
	 * @param b     a {@code byte}.
	 * @return a reference to this object.
	 * @throws IndexOutOfBoundsException if the index is invalid.
	 */
	public StringBuilderByte insert(int index, byte b) {
		if (index < 0 || index > count) {
			throw new StringIndexOutOfBoundsException("index " + index);
		}

		ensureCapacityInternal(count + 1);
		System.arraycopy(value, index, value, index + 1, count - index);
		value[index] = b;
		               count += 1;

		return this;
	}

	/**
	 * Removes the bytes in a substring of this sequence.
	 * The substring begins at the specified {@code start} and extends to
	 * the byte at index {@code end - 1} or to the end of the
	 * sequence if no such byte exists. If
	 * {@code start} is equal to {@code end}, no changes are made.
	 *
	 * @param start The beginning index, inclusive.
	 * @param end   The ending index, exclusive.
	 * @return This object.
	 * @throws StringIndexOutOfBoundsException if {@code start}
	 *                                         is negative, greater than {@code length()}, or
	 *                                         greater than {@code end}.
	 */
	public StringBuilderByte delete(int start, int end) {
		if (start < 0) {
			throw new StringIndexOutOfBoundsException("start " + start);
		}

		if (end > count) {
			end = count;
		}

		int len = end - start;
		if (len < 0) {
			throw new StringIndexOutOfBoundsException("start " + start + ", end " + end);
		}

		System.arraycopy(value, start + len, value, start, count - end);
		count -= len;

		return this;
	}

	/**
	 * Removes the {@code byte} at the specified position in this
	 * sequence. This sequence is shortened by one {@code byte}.
	 *
	 * <p>Note: If the byte at the given index is a supplementary
	 * byte, this method does not remove the entire byte. If
	 * correct handling of supplementary bytes is required,
	 * determine the number of {@code byte}s to remove by calling
	 * {@code Byte.charCount(thisSequence.codePointAt(index))},
	 * where {@code thisSequence} is this sequence.
	 *
	 * @param index Index of {@code byte} to remove
	 * @return This object.
	 * @throws StringIndexOutOfBoundsException if the {@code index}
	 *                                         is negative or greater than or equal to
	 *                                         {@code length()}.
	 */
	public StringBuilderByte deleteByteAt(int index) {
		if (index < 0 || index >= count) {
			throw new StringIndexOutOfBoundsException("index " + index);
		}

		System.arraycopy(value, index + 1, value, index, count - index - 1);
		count--;

		return this;
	}

	/**
	 * Returns the {@code byte} value in this sequence at the specified index.
	 * The first {@code byte} value is at index {@code 0}, the next at index
	 * {@code 1}, and so on, as in array indexing.
	 * <p>
	 * The index argument must be greater than or equal to
	 * {@code 0}, and less than the length of this sequence.
	 *
	 * <p>If the {@code byte} value specified by the index is a
	 * <a href="Byte.html#unicode">surrogate</a>, the surrogate
	 * value is returned.
	 *
	 * @param index the index of the desired {@code byte} value.
	 * @return the {@code byte} value at the specified index.
	 * @throws IndexOutOfBoundsException if {@code index} is
	 *                                   negative or greater than or equal to {@code length()}.
	 */
	public byte byteAt(int index) {
		if (index < 0 || index >= count) {
			throw new StringIndexOutOfBoundsException("index " + index);
		}

		return value[index];
	}

	/**
	 * Bytes are copied from this sequence into the
	 * destination byte array {@code dst}. The first byte to
	 * be copied is at index {@code srcBegin}; the last byte to
	 * be copied is at index {@code srcEnd-1}. The total number of
	 * bytes to be copied is {@code srcEnd-srcBegin}. The
	 * bytes are copied into the subarray of {@code dst} starting
	 * at index {@code dstBegin} and ending at index:
	 * <pre>{@code
	 * dstbegin + (srcEnd-srcBegin) - 1
	 * }</pre>
	 *
	 * @param srcBegin start copying at this offset.
	 * @param srcEnd   stop copying at this offset.
	 * @param dst      the array to copy the data into.
	 * @param dstBegin offset into {@code dst}.
	 * @throws IndexOutOfBoundsException if any of the following is true:
	 *                                   <ul>
	 *                                   <li>{@code srcBegin} is negative
	 *                                   <li>{@code dstBegin} is negative
	 *                                   <li>the {@code srcBegin} argument is greater than
	 *                                   the {@code srcEnd} argument.
	 *                                   <li>{@code srcEnd} is greater than
	 *                                   {@code this.length()}.
	 *                                   <li>{@code dstBegin+srcEnd-srcBegin} is greater than
	 *                                   {@code dst.length}
	 *                                   </ul>
	 */
	public void getBytes(int srcBegin, int srcEnd, byte[] dst, int dstBegin) {
		if (srcBegin < 0) {
			throw new StringIndexOutOfBoundsException("srcBegin " + srcBegin);
		} else if (srcEnd < 0 || srcEnd > count) {
			throw new StringIndexOutOfBoundsException("srcEnd " + srcEnd);
		} else if (srcBegin > srcEnd) {
			throw new StringIndexOutOfBoundsException("srcBegin " + srcBegin + ", srcEnd " + srcEnd);
		}
		requireNonNull(dst, "dst");
		if (dstBegin < 0 || dstBegin + srcEnd - srcBegin > dst.length) {
			throw new StringIndexOutOfBoundsException(
					"srcBegin " + srcBegin + ", srcEnd " + srcEnd + ", dst.length " + dst.length +
					", dstBegin " + dstBegin);
		}

		System.arraycopy(value, srcBegin, dst, dstBegin, srcEnd - srcBegin);
	}

	/**
	 * The byte at the specified index is set to {@code b}. This
	 * sequence is altered to represent a new byte sequence that is
	 * identical to the old byte sequence, except that it contains the
	 * byte {@code b} at position {@code index}.
	 * <p>
	 * The index argument must be greater than or equal to
	 * {@code 0}, and less than the length of this sequence.
	 *
	 * @param index the index of the byte to modify.
	 * @param b     the new byte.
	 * @throws IndexOutOfBoundsException if {@code index} is
	 *                                   negative or greater than or equal to {@code length()}.
	 */
	public void setByteAt(int index, byte b) {
		if (index < 0 || index >= count) {
			throw new StringIndexOutOfBoundsException("index " + index);
		}

		value[index] = b;
	}

	/**
	 * Sets the length of the byte sequence.
	 * The sequence is changed to a new byte sequence
	 * whose length is specified by the argument. For every nonnegative
	 * index <i>k</i> less than {@code newLength}, the byte at
	 * index <i>k</i> in the new byte sequence is the same as the
	 * byte at index <i>k</i> in the old sequence if <i>k</i> is less
	 * than the length of the old byte sequence; otherwise, it is the
	 * null byte {@code '\u005Cu0000'}.
	 * <p>
	 * In other words, if the {@code newLength} argument is less than
	 * the current length, the length is changed to the specified length.
	 * <p>
	 * If the {@code newLength} argument is greater than or equal
	 * to the current length, sufficient null bytes
	 * ({@code '\u005Cu0000'}) are appended so that
	 * length becomes the {@code newLength} argument.
	 * <p>
	 * The {@code newLength} argument must be greater than or equal
	 * to {@code 0}.
	 *
	 * @param newLength the new length
	 * @throws IndexOutOfBoundsException if the
	 *                                   {@code newLength} argument is negative.
	 */
	public void setLength(int newLength) {
		if (newLength < 0) {
			throw new StringIndexOutOfBoundsException("newLength " + newLength);
		}

		ensureCapacityInternal(newLength);

		if (count < newLength) {
			Arrays.fill(value, count, newLength, (byte)0);
		}

		count = newLength;
	}

	/**
	 * Attempts to reduce storage used for the byte sequence.
	 * If the buffer is larger than necessary to hold its current sequence of
	 * bytes, then it may be resized to become more space efficient.
	 * Calling this method may, but is not required to, affect the value
	 * returned by a subsequent call to the {@link #capacity()} method.
	 */
	public void trimToSize() {
		if (count < value.length) {
			value = Arrays.copyOf(value, count);
		}
	}

	/**
	 * Causes this byte sequence to be replaced by the reverse of
	 * the sequence. If there are any surrogate pairs included in the
	 * sequence, these are treated as single bytes for the
	 * reverse operation. Thus, the order of the high-low surrogates
	 * is never reversed.
	 * <p>
	 * Let <i>n</i> be the byte length of this byte sequence
	 * (not the length in {@code byte} values) just prior to
	 * execution of the {@code reverse} method. Then the
	 * byte at index <i>k</i> in the new byte sequence is
	 * equal to the byte at index <i>n-k-1</i> in the old
	 * byte sequence.
	 *
	 * <p>Note that the reverse operation may result in producing
	 * surrogate pairs that were unpaired low-surrogates and
	 * high-surrogates before the operation. For example, reversing
	 * "\u005CuDC00\u005CuD800" produces "\u005CuD800\u005CuDC00" which is
	 * a valid surrogate pair.
	 *
	 * @return a reference to this object.
	 */
	public StringBuilderByte reverse() {
		int n = count - 1;
		for (int i = (n - 1) >> 1; i >= 0; i--) {
			int  j    = n - i;
			byte temp = value[i];
			value[i] = value[j];
			value[j] = temp;
		}

		return this;
	}

	/**
	 * Returns a new {@code String} that contains a subsequence of
	 * bytes currently contained in this byte sequence. The
	 * substring begins at the specified index and extends to the end of
	 * this sequence.
	 *
	 * @param start The beginning index, inclusive.
	 * @return The new string.
	 * @throws StringIndexOutOfBoundsException if {@code start} is
	 *                                         less than zero, or greater than the length of this object.
	 */
	public String substring(int start) {
		return substring(start, count);
	}

	/**
	 * Returns a new {@code String} that contains a subsequence of
	 * bytes currently contained in this sequence. The
	 * substring begins at the specified {@code start} and
	 * extends to the byte at index {@code end - 1}.
	 *
	 * @param start The beginning index, inclusive.
	 * @param end   The ending index, exclusive.
	 * @return The new string.
	 * @throws StringIndexOutOfBoundsException if {@code start}
	 *                                         or {@code end} are negative or greater than
	 *                                         {@code length()}, or {@code start} is
	 *                                         greater than {@code end}.
	 */
	public String substring(int start, int end) {
		if (start < 0) {
			throw new StringIndexOutOfBoundsException("start " + start);
		} else if (end > count) {
			throw new StringIndexOutOfBoundsException("end " + end);
		} else if (start > end) {
			throw new StringIndexOutOfBoundsException("start " + start + ", end " + end);
		}

		return new String(value, start, end - start, charset);
	}

	////

	/**
	 * Ensures that the capacity is at least equal to the specified minimum.
	 * If the current capacity is less than the argument, then a new internal
	 * array is allocated with greater capacity. The new capacity is the
	 * larger of:
	 * <ul>
	 * <li>The {@code minimumCapacity} argument.
	 * <li>Twice the old capacity, plus {@code 2}.
	 * </ul>
	 * If the {@code minimumCapacity} argument is nonpositive, this
	 * method takes no action and simply returns.
	 * Note that subsequent operations on this object can reduce the
	 * actual capacity below that requested here.
	 *
	 * @param minimumCapacity the minimum desired capacity.
	 */
	public void ensureCapacity(int minimumCapacity) {
		if (minimumCapacity > 0) {
			ensureCapacityInternal(minimumCapacity);
		}
	}

	/**
	 * For positive values of {@code minimumCapacity}, this method
	 * behaves like {@code ensureCapacity}, however it is never
	 * synchronized.
	 * If {@code minimumCapacity} is non positive due to numeric
	 * overflow, this method throws {@code OutOfMemoryError}.
	 */
	private void ensureCapacityInternal(int minimumCapacity) {
		// overflow-conscious code
		if (minimumCapacity - value.length > 0) {
			value = Arrays.copyOf(value, newCapacity(minimumCapacity));
		}
	}

	/**
	 * Returns a capacity at least as large as the given minimum capacity.
	 * Returns the current capacity increased by the same amount + 2 if
	 * that suffices.
	 * Will not return a capacity greater than {@code MAX_ARRAY_SIZE}
	 * unless the given minimum capacity is greater than that.
	 *
	 * @param minCapacity the desired minimum capacity
	 * @throws OutOfMemoryError if minCapacity is less than zero or
	 *                          greater than Integer.MAX_VALUE
	 */
	private int newCapacity(int minCapacity) {
		// overflow-conscious code
		int newCapacity = (value.length << 1) + 2;
		if (newCapacity - minCapacity < 0) {
			newCapacity = minCapacity;
		}
		return newCapacity <= 0 || MAX_ARRAY_SIZE - newCapacity < 0 ?
		       hugeCapacity(minCapacity) :
		       newCapacity;
	}

	private static int hugeCapacity(int minCapacity) {
		// overflow
		if (Integer.MAX_VALUE - minCapacity < 0) {
			throw new OutOfMemoryError();
		}

		return Math.max(minCapacity, MAX_ARRAY_SIZE);
	}

	/**
	 * Returns a string representing the data in this sequence.
	 * A new {@code String} object is allocated and initialized to
	 * contain the byte sequence currently represented by this
	 * object. This {@code String} is then returned. Subsequent
	 * changes to this sequence do not affect the contents of the
	 * {@code String}.
	 *
	 * @return a string representation of this sequence of bytes.
	 */
	@Override
	public String toString() {
		return new String(value, 0, count, charset);
	}
}
