package nl.airsupplies.utilities.container;

import nl.airsupplies.utilities.NumberUtilities;

/**
 * Implements an unsigned int, including proper comparison methods.
 *
 * @author Mark Jeronimus
 */
// Created 2013-11-27
public class UnsignedInteger extends Number implements Comparable<UnsignedInteger> {
	public static final int MIN_VALUE = 0x00000000;
	public static final int MAX_VALUE = 0xFFFFFFFF;

	private final int value;

	public UnsignedInteger(int value) {
		this.value = value;
	}

	public static UnsignedInteger valueOf(int value) {
		return new UnsignedInteger(value);
	}

	public static UnsignedInteger valueOf(long value) {
		return new UnsignedInteger((int)value);
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
		return value;
	}

	@Override
	public long longValue() {
		return value & 0xFFFFFFFFL;
	}

	@Override
	public float floatValue() {
		return value & 0xFFFFFFFFL;
	}

	@Override
	public double doubleValue() {
		return value & 0xFFFFFFFFL;
	}

	@Override
	public int compareTo(UnsignedInteger o) {
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
		return Integer.compare(value, o.value);
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof UnsignedInteger && value == ((UnsignedInteger)obj).value;
	}

	@Override
	public int hashCode() {
		int hash = 0x811C9DC5;
		hash ^= Integer.hashCode(value);
		hash *= 0x01000193;
		return hash;
	}

	@Override
	public String toString() {
		return Long.toString(value & 0xFFFFFFFFL);
	}

	public String toString(int length) {
		String num  = toString();
		int    diff = length - num.length();

		StringBuilder b = new StringBuilder(length);

		while (b.length() < diff) {
			b.append('0');
		}

		b.append(num.substring(Math.max(0, -diff)));

		return b.toString();
	}

	public static String toBinaryString(int value, int length) {
		if (length < 1 || length > 32) {
			throw new IllegalArgumentException("length: " + length);
		}

		StringBuilder out = new StringBuilder(32);

		int mask = 1 << length - 1;
		for (int i = 0; i < length; i++) {
			out.append((value & mask) != 0 ? '1' : '0');
			mask >>>= 1;
		}

		return out.toString();
	}

	public static String toHexString(int value, int length) {
		if (length < 1 || length > 8) {
			throw new IllegalArgumentException("length: " + length);
		}

		StringBuilder out = new StringBuilder(32);

		for (int i = (length - 1) * 4; i >= 0; i -= 4) {
			out.append(NumberUtilities.DIGITS[value >>> i & 0xF]);
		}

		return out.toString();
	}
}
