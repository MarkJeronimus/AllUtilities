package nl.airsupplies.utilities.container;

/**
 * Implements an unsigned byte, including proper comparison methods.
 *
 * @author Mark Jeronimus
 */
// Created 2013-11-27
public class UnsignedByte extends Number implements Comparable<UnsignedByte> {
	public static final byte MIN_VALUE = (byte)0x00;
	public static final byte MAX_VALUE = (byte)0xFF;

	private final byte value;

	public UnsignedByte(byte value) {
		this.value = value;
	}

	public static UnsignedByte valueOf(byte value) {
		return new UnsignedByte(value);
	}

	public static UnsignedByte valueOf(int value) {
		return new UnsignedByte((byte)value);
	}

	@Override
	public byte byteValue() {
		return value;
	}

	@Override
	public short shortValue() {
		return (short)(value & 0xFF);
	}

	@Override
	public int intValue() {
		return value & 0xFF;
	}

	@Override
	public long longValue() {
		return value & 0xFFL;
	}

	@Override
	public float floatValue() {
		return value & 0xFFL;
	}

	@Override
	public double doubleValue() {
		return value & 0xFFL;
	}

	@Override
	public int compareTo(UnsignedByte o) {
		return (value & 0xFF) - (o.value & 0xFF);
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof UnsignedByte && value == ((UnsignedByte)obj).value;
	}

	@Override
	public int hashCode() {
		int hash = 0x811C9DC5;
		hash ^= Byte.hashCode(value);
		hash *= 0x01000193;
		return hash;
	}
}
