package nl.airsupplies.utilities.container;

import java.nio.Buffer;
import java.nio.ByteBuffer;

import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireAtLeast;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireRange;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireThat;

/**
 * @author Mark Jeronimus
 */
// Created 2018-08-16
public final class BitBufferOld {
	private final ByteBuffer buffer;
	private final int        bitCapacity;

	private int remainingBits = 8; // Always in the range [1, 8]

	public BitBufferOld(int bitCapacity) {
		this.bitCapacity = requireAtLeast(1, bitCapacity, "bitCapacity");
		buffer           = ByteBuffer.allocate((bitCapacity + 7) >> 3);
	}

	public BitBufferOld(int bitSize, int bits) {
		bitCapacity = requireRange(1, 32, bitSize, "bitSize");
		buffer      = ByteBuffer.allocate((bitSize + 7) >> 3);

		putBits(bitSize, bits);
	}

	public BitBufferOld(ByteBuffer buffer, int bitCapacity) {
		this.buffer      = requireNonNull(buffer, "buffer");
		this.bitCapacity = bitCapacity;
	}

	public static BitBufferOld allocate(int bitCapacity) {
		requireAtLeast(1, bitCapacity, "bitCapacity");
		int        capacity = (bitCapacity + 7) / 8;
		ByteBuffer buffer   = ByteBuffer.allocate(capacity);
		return new BitBufferOld(buffer, bitCapacity);
	}

	public int bitCapacity() {
		return bitCapacity;
	}

	public int capacity() {
		return (bitCapacity + 7) / 8;
	}

	public int remainingBits() {
		return bitCapacity - buffer.position() * 8 + remainingBits - 8;
	}

	public byte[] array() {
		return buffer.array();
	}

	public int bitPosition() {
		return buffer.position() * 8 + 8 - remainingBits;
	}

	public void bitPosition(int bitPosition) {
		((Buffer)buffer).position(bitPosition / 8);
		remainingBits = 8 - (bitPosition & 7);
	}

	public byte[] getArray(int numBits) {
		requireThat((numBits & 7) == 0, () -> "numBits not an integral number of bytes: " + numBits);
		if (numBits > remainingBits()) {
			throw new IndexOutOfBoundsException(numBits + " > " + remainingBits());
		}

		byte[] array = new byte[numBits / 8];

		// Use more efficient method for byte-aligned bytes
		if (remainingBits == 8) {
			for (int i = 0; i < array.length; i++) {
				array[i] = buffer.get();
			}
		} else {
			for (int i = 0; i < array.length; i++) {
				array[i] = (byte)getBits(8);
			}
		}

		return array;
	}

	public void putArray(byte[] array) {
		requireNonNull(array, "array");
		if (array.length * 8 > remainingBits()) {
			throw new IndexOutOfBoundsException(array.length * 8 + " > " + remainingBits());
		}

		// Use more efficient method for byte-aligned bytes
		if (remainingBits == 8) {
			for (byte element : array) {
				buffer.put(element);
			}
		} else {
			for (byte element : array) {
				putBits(8, element);
			}
		}
	}

	public int getBits(int numBits) {
		requireRange(1, 32, numBits, "numBits");
		if (numBits > remainingBits()) {
			throw new IndexOutOfBoundsException(numBits + " > " + remainingBits());
		}

		if (numBits < remainingBits) {
			// Get middle bits of this byte and don't advance byte pointer
			byte value  = buffer.get(buffer.position());
			int  result = extractBits(value, remainingBits - numBits, numBits);
			remainingBits -= numBits;
			return result;
		}

		int result;
		if (remainingBits != 8) {
			// Get all remaining bits of this byte and advance byte pointer
			byte value = buffer.get();
			result = extractBits(value, 0, remainingBits);
			numBits -= remainingBits;
		} else {
			result = 0;
		}

		// Get middle bytes and advance byte pointer
		while (numBits >= 8) {
			result <<= 8;
			result |= buffer.get() & 0xFF;
			numBits -= 8;
		}

		if (numBits > 0) {
			// Get remaining bits of this byte and don't advance byte pointer
			byte value = buffer.get(buffer.position());
			result <<= numBits;
			result |= extractBits(value, 8 - numBits, numBits);
		}

		remainingBits = 8 - numBits;

		return result;
	}

	public void putBits(int numBits, int bits) {
		requireRange(1, 32, numBits, "numBits");
		if (numBits > remainingBits()) {
			throw new IndexOutOfBoundsException(numBits + " > " + remainingBits());
		}

		if (numBits < remainingBits) {
			// Put all bits in the middle of this byte and don't advance byte pointer
			byte value  = buffer.get(buffer.position());
			byte result = injectBits(bits, value, remainingBits - numBits, numBits);
			buffer.put(buffer.position(), result);
			remainingBits -= numBits;
			return;
		}

		if (remainingBits != 8) {
			// Fill remaining bits of this byte and advance byte pointer
			byte value    = buffer.get(buffer.position());
			int  highBits = extractBits(bits, numBits - remainingBits, remainingBits);
			byte result   = injectBits(highBits, value, 0, remainingBits);
			numBits -= remainingBits;
			buffer.put(result);
		}

		// Fill middle bytes and advance byte pointer
		while (numBits >= 8) {
			int value = extractBits(bits, numBits - 8, 8);
			buffer.put((byte)value);
			numBits -= 8;
		}

		if (numBits > 0) {
			// Put remaining bits in this byte and don't advance byte pointer
			byte value   = buffer.get(buffer.position());
			int  lowBits = extractBits(bits, 0, numBits);
			byte result  = injectBits(lowBits, value, 8 - numBits, numBits);
			buffer.put(buffer.position(), result);
		}

		remainingBits = 8 - numBits;
	}

	private static int extractBits(byte value, int firstBit, int numBits) {
		int mask = (1 << numBits) - 1;
		return value >> firstBit & mask;
	}

	private static int extractBits(int value, int firstBit, int numBits) {
		int mask = (1 << numBits) - 1;
		return value >> firstBit & mask;
	}

	private static byte injectBits(int bits, byte value, int firstBit, int numBits) {
		int mask = ((1 << numBits) - 1) << firstBit;
		return (byte)((value & ~mask) | (bits << firstBit & mask));
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder((bitCapacity + 7) / 8 * 3 + bitCapacity + 6);

//		sb.append("0x");
//		for (int i = 0; i < buffer.capacity(); i++) {
//			sb.append(HexUtilities.toUnsignedWordString(buffer.get(i))).append(' ');
//		}

//		sb.append("= 0b");
		for (int i = 0; i < bitCapacity; i++) {
			int pos  = i / 8;
			int mask = 1 << (7 - (i % 8));
			sb.append((buffer.get(pos) & mask) != 0 ? '1' : '0');
		}

		return sb.toString();
	}
}
