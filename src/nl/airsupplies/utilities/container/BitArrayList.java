package nl.airsupplies.utilities.container;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import nl.airsupplies.utilities.NumberUtilities;

/**
 * @author Mark Jeronimus
 */
// Created 2016-03-20
public class BitArrayList implements BitList {
	private static final byte[] BYTE_BITS = {(byte)0x80, (byte)0x40, (byte)0x20, (byte)0x10,
	                                         (byte)0x08, (byte)0x04, (byte)0x02, (byte)0x01};
	private static final byte[] BYTE_MASK = {(byte)0x00, (byte)0x80, (byte)0xC0, (byte)0xE0,
	                                         (byte)0xF0, (byte)0xF8, (byte)0xFC, (byte)0xFE};

	private byte[] bytes;
	private int    bitLength;

	public BitArrayList() {
		bytes     = new byte[8];
		bitLength = 0;
	}

	public BitArrayList(int initialCapacity) {
		bytes     = new byte[(initialCapacity + 7) / 8];
		bitLength = 0;
	}

	public BitArrayList(byte[] array) {
		bytes     = Arrays.copyOf(array, array.length);
		bitLength = bytes.length * 8;
	}

	public BitArrayList(BitArrayList other) {
		bytes     = Arrays.copyOf(other.bytes, other.bytes.length);
		bitLength = other.bitLength;
	}

	public BitArrayList(String hex) {
		appendHex(hex);
	}

	@Override
	public boolean isEmpty() {
		return bitLength == 0;
	}

	@Override
	public int getBitLength() {
		return bitLength;
	}

	public BitArrayList setBitLength(int bitLength) {
		ensureCapacity(bitLength);
		this.bitLength = bitLength;
		return this;
	}

	@Override
	public int getByteLength() {
		return bitLength / 8;
	}

	@Override
	public boolean getBit(int index) {
		byte byteBit = BYTE_BITS[index & 0x7];
		int  byteIdx = index / 8;

		return (bytes[byteIdx] & byteBit) != 0;
	}

	public BitArrayList setBit(int index, boolean bit) {
		if (index == bitLength) {
			return appendBit(bit);
		}

		byte byteBit = BYTE_BITS[index & 0x7];
		int  byteIdx = index / 8;

		if (bit) {
			bytes[byteIdx] |= byteBit;
		} else {
			bytes[byteIdx] &= ~byteBit;
		}
		return this;
	}

	@Override
	public byte getByte(int index) {
		if (index > bitLength + 7) {
			throw new ArrayIndexOutOfBoundsException("bitLength = " + bitLength + ", index=" + index);
		}

		if ((index & 7) == 0) {
			if (index < bitLength / 8) {
				return bytes[index];
			} else {
				return (byte)(bytes[index] & BYTE_MASK[bitLength & 0x7]);
			}
		} else {
			byte result = 0;
			int  mask   = 1 << 7;
			int  max    = Math.min(bitLength, index + 8);
			for (; index < max; index++) {
				if (getBit(index)) {
					result |= mask;
				}
				mask >>>= 1;
			}
			return result;
		}
	}

	@Override
	public short getShort(int index) {
		return 0;
	}

	@Override
	public int getInt(int index) {
		return 0;
	}

	@Override
	public long getLong(int index) {
		if (index > bitLength + 63) {
			throw new ArrayIndexOutOfBoundsException("bitLength = " + bitLength + ", index=" + index);
		}

		long value = 0;

		int bitInByte = index & 7;
		int shift     = 56 - bitInByte;

		for (; shift >= 0; shift -= 8) {
			value |= (long)bytes[index] << shift;
			index++;
		}
		if (shift > -8) {
			value |= bytes[index] >>> (8 - shift);
			index++;
		}

		return value;
	}

	@Override
	public BitArrayList copy() {
		return new BitArrayList(this);
	}

	@Override
	public BitArrayList getSubList(int begin, int end) {
		BitArrayList copy = new BitArrayList(end - begin);

		// Copy aligned bytes.
		if ((begin & 7) == 0) {
			int n = (end - begin) / 8;
			System.arraycopy(bytes, begin / 8, copy.bytes, 0, n);
			begin += n * 8;
		}

		// Copy unaligned or remainder bits.
		for (; begin < end; begin++) {
			copy.appendBit(getBit(begin));
		}

		return copy;
	}

	public void setSubList(int begin, BitList other) {
		int end = begin + other.getBitLength();
		ensureCapacity(end);

		// Copy aligned bytes.
		if ((begin & 7) == 0) {
			int n = other.getBitLength() / 8;
			if (other instanceof BitArrayList) {
				System.arraycopy(((BitArrayList)other).bytes, 0, bytes, begin / 8, n);
				begin += n * 8;
			} else {
				for (int i = 0; i < n; i++) {
					bytes[begin / 8] = other.getByte(i);
					begin += 8;
				}
			}
		}

		// Copy unaligned or remainder bits.
		for (; begin < end; begin++) {
			setBit(begin, other.getBit(begin));
		}
	}

	@Override
	public byte[] toByteArray(byte[] array) {
		if (array.length < getByteLength()) {
			throw new ArrayIndexOutOfBoundsException(
					"Array size = " + array.length + ", byteLength = " + getByteLength());
		}

		System.arraycopy(bytes, 0, array, 0, array.length);

		return array;
	}

	public BitArrayList clear() {
		bitLength = 0;
		return this;
	}

	public void ensureCapacity(int reqestedLength) {
		reqestedLength /= 8;
		if (bytes.length < reqestedLength) {
			int newLength = bytes.length;
			while (newLength < reqestedLength) {
				newLength *= 2;
			}
			bytes = Arrays.copyOf(bytes, newLength);
		}
	}

	public BitArrayList appendBit(boolean bit) {
		byte byteBit = BYTE_BITS[bitLength & 0x7];
		int  byteIdx = bitLength / 8;

		ensureCapacity(bitLength + 1);

		if (bit) {
			bytes[byteIdx] |= byteBit;
		} else {
			bytes[byteIdx] &= ~byteBit;
		}

		bitLength++;

		return this;
	}

	public BitArrayList append(byte[] array) {
		ensureCapacity(bitLength + array.length * 8);
		for (byte b : array) {
			appendByte(b);
		}
		return this;
	}

	public BitArrayList appendNibble(int b) {
		appendBit((b & 0x8) != 0);
		appendBit((b & 0x4) != 0);
		appendBit((b & 0x2) != 0);
		appendBit((b & 0x1) != 0);
		return this;
	}

	public BitArrayList appendByte(int b) {
		ensureCapacity(bitLength + 8);
		if ((bitLength & 7) == 0) {
			appendByteAligned(b);
		} else {
			appendByteUnaligned(b);
		}
		return this;
	}

	public BitArrayList appendShort(int b) {
		ensureCapacity(bitLength + 16);
		if ((bitLength & 7) == 0) {
			appendByteAligned(b >>> 8);
			appendByteAligned(b);
		} else {
			appendByteUnaligned(b >>> 8);
			appendByteUnaligned(b);
		}
		return this;
	}

	public BitArrayList appendInt(int b) {
		ensureCapacity(bitLength + 32);
		if ((bitLength & 7) == 0) {
			appendByteAligned(b >>> 24);
			appendByteAligned(b >>> 16);
			appendByteAligned(b >>> 8);
			appendByteAligned(b);
		} else {
			appendByteUnaligned(b >>> 24);
			appendByteUnaligned(b >>> 16);
			appendByteUnaligned(b >>> 8);
			appendByteUnaligned(b);
		}
		return this;
	}

	public BitArrayList appendLong(long b) {
		ensureCapacity(bitLength + 64);
		if ((bitLength & 7) == 0) {
			appendByteAligned((int)(b >>> 56));
			appendByteAligned((int)(b >>> 48));
			appendByteAligned((int)(b >>> 40));
			appendByteAligned((int)(b >>> 32));
			appendByteAligned((int)(b >>> 24));
			appendByteAligned((int)(b >>> 16));
			appendByteAligned((int)(b >>> 8));
			appendByteAligned((int)b);
		} else {
			appendByteUnaligned((int)(b >>> 56));
			appendByteUnaligned((int)(b >>> 48));
			appendByteUnaligned((int)(b >>> 40));
			appendByteUnaligned((int)(b >>> 32));
			appendByteUnaligned((int)(b >>> 24));
			appendByteUnaligned((int)(b >>> 16));
			appendByteUnaligned((int)(b >>> 8));
			appendByteUnaligned((int)b);
		}
		return this;
	}

	private void appendByteAligned(int value) {
		bytes[bitLength / 8] = (byte)value;
		bitLength += 8;
	}

	private void appendByteUnaligned(int b) {
		appendBit((b & 0x80) != 0);
		appendBit((b & 0x40) != 0);
		appendBit((b & 0x20) != 0);
		appendBit((b & 0x10) != 0);
		appendBit((b & 0x08) != 0);
		appendBit((b & 0x04) != 0);
		appendBit((b & 0x02) != 0);
		appendBit((b & 0x01) != 0);
		bitLength += 8;
	}

	public BitArrayList appendTimes(boolean b, int count) {
		ensureCapacity(bitLength + count);

		for (int i = 0; i < count; i++) {
			appendBit(b);
		}

		return this;
	}

	public BitArrayList appendByteTimes(int b, int count) {
		ensureCapacity(bitLength + count);

		for (int i = 0; i < count; i++) {
			appendByte(b);
		}

		return this;
	}

	public BitArrayList appendShortTimes(int b, int count) {
		ensureCapacity(bitLength + count);

		for (int i = 0; i < count; i++) {
			appendShort(b);
		}

		return this;
	}

	public BitArrayList appendIntTimes(int b, int count) {
		ensureCapacity(bitLength + count);

		for (int i = 0; i < count; i++) {
			appendInt(b);
		}

		return this;
	}

	public BitArrayList appendLongTimes(long b, int count) {
		ensureCapacity(bitLength + count);

		for (int i = 0; i < count; i++) {
			appendLong(b);
		}

		return this;
	}

	public BitArrayList appendHex(String hex) {
		hex.chars().map(i -> {
			if (i >= '0' && i <= '9') {
				return i - '0';
			} else if (i >= 'a' && i <= 'f') {
				return i - ('a' - 10);
			} else if (i >= 'A' && i <= 'F') {
				return i - ('A' - 10);
			} else {
				return -1;
			}
		}).filter(i -> i >= 0).forEach(this::appendNibble);
		return this;
	}

	public BitArrayList append(BitArrayList ba) {
		ensureCapacity(bitLength + ba.bitLength);
		if ((bitLength & 7) == 0) {
			// Bytes are aligned.
			System.arraycopy(ba.bytes, 0, bytes, bitLength / 8, ba.bitLength / 8);
			bitLength += ba.bitLength / 8 * 8;

			for (int i = ba.bitLength / 8 * 8; i < ba.bitLength; i++) {
				appendBit(ba.getBit(i));
			}
		} else {
			for (int i = 0; i < ba.bitLength; i++) {
				appendBit(ba.getBit(i));
			}
		}
		return this;
	}

	public BitArrayList appendRandomBits(int count) {
		ensureCapacity(bitLength + count);

		// Append bits until aligned.
		for (int i = bitLength; i < (bitLength + 7) / 8 * 8; i++) {
			appendBit(ThreadLocalRandom.current().nextBoolean());
			count--;
		}

		// Append aligned bytes.
		while (count > 8) {
			int rnd = ThreadLocalRandom.current().nextInt();
			for (int n = Math.min(count / 8, 4); n > 0; n--) {
				appendByteAligned(rnd);
				rnd >>= 8;
				count -= 8;
			}
		}

		// Append tailing bits.
		for (; count > 0; count++) {
			appendBit(ThreadLocalRandom.current().nextBoolean());
		}

		return this;
	}

	public BitArrayList appendASCII(String string) {
		string.chars().filter(i -> i >= 0 && i <= 255).forEach(this::appendByte);
		return this;
	}

	public String toStringGrouped(int groupSize) {
		int           l  = (getBitLength() + 7) / 8;
		StringBuilder sb = new StringBuilder(l * 2 + Math.max(0, l - 1) / groupSize);
		for (int i = 0; i < l; i++) {
			if (i > 0 && i % groupSize == 0) {
				sb.append(' ');
			}
			byte b = getByte(i);
			sb.append(NumberUtilities.DIGITS[b >> 4 & 0xF]);
			sb.append(NumberUtilities.DIGITS[b & 0xF]);
		}
		return sb.toString();
	}

	@Override
	public String toString() {
		return toStringGrouped(Integer.MAX_VALUE);
	}
}
