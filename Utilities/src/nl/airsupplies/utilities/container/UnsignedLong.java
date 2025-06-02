package nl.airsupplies.utilities.container;

import nl.airsupplies.utilities.NumberUtilities;

/**
 * Implements an unsigned long, including proper comparison methods.
 *
 * @author Mark Jeronimus
 */
// Created 2013-11-27
public class UnsignedLong extends Number implements Comparable<UnsignedLong> {
	public static final long MIN_VALUE = 0x0000000000000000L;
	public static final long MAX_VALUE = 0xFFFFFFFFFFFFFFFFL;

	private final long value;

	public UnsignedLong(long value) {
		this.value = value;
	}

	public static UnsignedLong valueOf(long value) {
		return new UnsignedLong(value);
	}

	@Override
	public byte byteValue() {
		return (byte)value;
	}

	@Override
	public short shortValue() {
		return (short)value;
	}

	@Override
	public int intValue() {
		return (int)value;
	}

	@Override
	public long longValue() {
		return value;
	}

	@Override
	public float floatValue() {
		return value & 0xFFFFL;
	}

	@Override
	public double doubleValue() {
		return value & 0xFFFFL;
	}

	@Override
	public int compareTo(UnsignedLong o) {
		// (n1 < n2) ^ ((n1 < 0) != (n2 < 0))
		// Handle negative values as if positive.
		if (value >= 0) {
			if (o.value < 0) {
				return -1;
			}
		} else {
			if (o.value > 0) {
				return 1;
			}
		}
		return Long.compare(value, o.value);
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof UnsignedLong && value == ((UnsignedLong)obj).value;
	}

	@Override
	public int hashCode() {
		int hash = 0x811C9DC5;
		hash ^= Long.hashCode(value);
		hash *= 0x01000193;
		return hash;
	}

	public static String toBinaryString(long value, int length) {
		if (length < 1 || length > 64) {
			throw new IllegalArgumentException("length: " + length);
		}

		StringBuilder out = new StringBuilder(64);

		long mask = 1L << length - 1;
		for (int i = 0; i < length; i++) {
			out.append((value & mask) != 0 ? '1' : '0');
			mask >>>= 1;
		}

		return out.toString();
	}

	public static String toHexString(long value, int length) {
		if (length < 1 || length > 16) {
			throw new IllegalArgumentException("length: " + length);
		}

		StringBuilder out = new StringBuilder(64);

		for (int i = (length - 1) * 4; i >= 0; i -= 4) {
			out.append(NumberUtilities.RADIX_DIGITS.charAt((int)(value >>> i) & 0xF));
		}

		return out.toString();
	}
}
