package nl.airsupplies.utilities.container;

import java.nio.ByteBuffer;

import net.jcip.annotations.NotThreadSafe;

import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireAtLeast;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireAtMost;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireRange;

/**
 * @author Mark Jeronimus
 */
// Created 2023-08-19
@NotThreadSafe
@SuppressWarnings("ValueOfIncrementOrDecrementUsed")
public final class BitBuffer {
	private final ByteBuffer buffer;
	private final int        bitCapacity;

	private int bitSize     = 0;
	private int bitPosition = 0;

	public BitBuffer(int bitCapacity) {
		this.bitCapacity = requireAtLeast(1, bitCapacity, "bitCapacity");
		buffer           = ByteBuffer.allocate((bitCapacity + 7) >> 3);
	}

	public BitBuffer(int bitSize, int bits) {
		bitCapacity = requireRange(1, 32, bitSize, "bitSize");
		buffer      = ByteBuffer.allocate((bitSize + 7) >> 3);

		putBits(0, bitSize, bits);
	}

	public BitBuffer(ByteBuffer buffer, int bitCapacity) {
		requireAtMost(Integer.MAX_VALUE >> 3, buffer.capacity(), "buffer.capacity");
		this.buffer = requireNonNull(buffer, "buffer");

		int maxBitCapacity = buffer.capacity() * 8;
		int minBitCapacity = maxBitCapacity - 7;
		this.bitCapacity = requireRange(minBitCapacity, maxBitCapacity, bitCapacity, "bitCapacity");
	}

	public byte[] backingArray() {
		return buffer.array();
	}

	public int getBitCapacity() {
		return bitCapacity;
	}

	public int getBitSize() {
		return bitSize;
	}

	public void setBitSize(int bitSize) {
		this.bitSize = requireRange(0, bitCapacity, bitSize, "bitSize");
	}

	public int getBitPosition() {
		return bitPosition;
	}

	public void setBitPosition(int bitPosition) {
		this.bitPosition = requireRange(0, bitSize, bitPosition, "bitPosition");
	}

	public int bitsRemaining() {
		return bitSize - bitPosition;
	}

	public boolean getBit(int bitPosition) {
		requireRange(0, bitSize, bitPosition, "bitPosition");

		int position = bitPosition >> 3;
		int mask     = 0b10000000 >> (bitPosition - (position << 3));

		return (buffer.get(position) & mask) != 0;
	}

	public boolean getBit() {
		return getBit(bitPosition++);
	}

	@SuppressWarnings("lossy-conversions")
	public void putBit(int bitPosition, boolean bit) {
		requireRange(0, bitSize, bitPosition, "bitPosition");

		int position = bitPosition >> 3;
		int mask     = 0b10000000 >> (bitPosition - (position << 3));

		byte value = buffer.get(position);
		value &= ~mask;
		value |= bit ? mask : 0;
		buffer.put(position, value);
	}

	public void putBit(int bitPosition, int bit) {
		putBit(bitPosition, bit != 0);
	}

	public void putBit(boolean bit) {
		bitSize = Math.max(bitSize, bitPosition + 1);
		putBit(bitPosition++, bit);
	}

	public void putBit(int bit) {
		putBit(bit != 0);
	}

	public int getBits(int bitPosition, int numBits) {
		requireRange(1, 32, numBits, "numBits");
		requireRange(0, bitSize - numBits, bitPosition, "bitPosition");

		int toBitPosition = bitPosition + numBits - 1;

		int position   = bitPosition >> 3;
		int toPosition = toBitPosition >> 3;

		int skipBits = bitPosition - (position << 3);

		// Does it fall completely within a single byte?
		if (position == toPosition) {
			return extractBits(position, skipBits, numBits);
		}

		int result;
		int shift = numBits; // subtract extracted bits before using

		// Does it start in the middle of a byte?
		if (skipBits != 0) {
			int bitsToExtract = 8 - skipBits;
			shift -= bitsToExtract;
			result = extractBits(position++, skipBits, bitsToExtract) << shift;
			numBits -= bitsToExtract;
		} else {
			result = 0;
		}

		// Get middle bytes
		while (numBits >= 8) {
			shift -= 8;
			result |= (buffer.get(position++) & 0xFF) << shift;
			numBits -= 8;
		}

		// Does it end in the middle of a byte?
		if (numBits > 0) {
			result |= extractBits(position, 0, numBits);
		}

		return result;
	}

	public int getBits(int numBits) {
		int bits = getBits(bitPosition, numBits);
		bitPosition += numBits;
		return bits;
	}

	public void putBits(int bitPosition, int numBits, int bits) {
		requireRange(1, 32, numBits, "numBits");
		requireRange(0, Math.min(bitSize + 1, bitCapacity - numBits), bitPosition, "bitPosition");

		int toBitPosition = bitPosition + numBits - 1;

		int position   = bitPosition >> 3;
		int toPosition = toBitPosition >> 3;

		int skipBits = bitPosition - (position << 3);

		// Does it fall completely within a single byte?
		if (position == toPosition) {
			injectBits(position, skipBits, numBits, bits);
			return;
		}

		int shift = numBits; // subtract extracted bits before using

		// Does it start in the middle of a byte?
		if (skipBits != 0) {
			int bitsToExtract = 8 - skipBits;
			shift -= bitsToExtract;
			injectBits(position++, skipBits, bitsToExtract, bits >> shift);
			numBits -= bitsToExtract;
		}

		// Get middle bytes
		while (numBits >= 8) {
			shift -= 8;
			buffer.put(position++, (byte)(bits >> shift));
			numBits -= 8;
		}

		// Does it end in the middle of a byte?
		if (numBits > 0) {
			injectBits(position, 0, numBits, bits);
		}
	}

	public void putBits(int numBits, int bits) {
		putBits(bitPosition, numBits, bits);
		bitPosition += numBits;
	}

	// Unfinished
//	public byte[] getArray(int numBits) {
//		byte[] array = getArray(bitPosition, numBits);
//		bitPosition += numBits;
//		return array;
//	}
//
//	public byte[] getArray(int numBits, byte[] array) {
//		getArray(bitPosition, numBits, array);
//		bitPosition += numBits;
//		return array;
//	}
//
//	public byte[] getArray(int bitPosition, int numBits) {
//		requireThat((numBits & 7) == 0, () -> "numBits is not an integral number of bytes: " + numBits);
//		int maxNumBits = (bitCapacity - bitSize) & 7;
//		requireRange(8, maxNumBits, numBits, "numBits");
//
//		return getArray(bitPosition, numBits, null);
//	}
//
//	public byte[] getArray(int bitPosition, byte[] array) {
//		int numBytes = array.length;
//
//		int numBytes = array.length;
//		requireThat(numBits == numBytes << 3, () -> "numBits is not an integral number of bytes: " + numBits);
//		int maxNumBits = (bitCapacity - bitSize) & 7;
//		requireRange(8, maxNumBits, numBits, "numBits");
//		requireRange(0, Math.min(bitSize + 1, bitCapacity - numBits), bitPosition, "bitPosition");
//
//		if (array == null) {
//			array = new byte[numBytes];
//		}
//
//		int position = bitPosition >> 3;
//
//		// Is the position byte-aligned?
//		if ((bitPosition & 7) == 0) {
//			for (int i = 0; i < numBytes; i++) {
//				array[i] = buffer.get(position);
//			}
//
//			return array;
//		}
//
//		for (int i = 0; i < array.length; i++) {
//			array[i] = (byte)getBits(8);
//		}
//
//		return array;
//	}
//
//	public void putArray(byte[] array) {
//		putArray(bitPosition, array);
//		bitPosition += array.length * 8;
//	}
//
//	public void putArray(int bitPosition, byte[] array) {
//		int numBytes = array.length;
//		requireNonNull(array, "array");
//		int numBits = numBytes * 8;
//		requireRange(0, Math.min(bitSize + 1, bitCapacity - numBits), bitPosition, "bitPosition");
//
//		// Is the position byte-aligned?
//		if ((bitPosition & 7) == 0) {
//			for (byte element : array) {
//				buffer.put(element);
//			}
//
//			return;
//		}
//
//		for (byte element : array) {
//			putBits(8, element);
//		}
//	}

	private int extractBits(int position, int firstBit, int numBits) {
		int mask = (1 << numBits) - 1;
		return buffer.get(position) >> (8 - numBits - firstBit) & mask;
	}

	@SuppressWarnings("lossy-conversions")
	private void injectBits(int position, int fromBit, int numBits, int bits) {
		int fromShift = 8 - fromBit;
		int shift     = fromShift - numBits;
		int mask      = (1 << fromShift) - (1 << shift);

		byte value = buffer.get(position);
		value &= ~mask;
		value |= (bits << shift) & mask;
		buffer.put(position, value);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder((bitSize + 7) / 8 * 3 + bitSize + 6);

//		sb.append("0x");
//		for (int i = 0; i < buffer.capacity(); i++) {
//			sb.append(HexUtilities.toUnsignedWordString(buffer.get(i))).append(' ');
//		}

//		sb.append("= 0b");
		for (int i = 0; i < bitSize; i++) {
			int pos  = i / 8;
			int mask = 1 << (7 - (i % 8));
			sb.append((buffer.get(pos) & mask) != 0 ? '1' : '0');
		}

		return sb.toString();
	}
}
