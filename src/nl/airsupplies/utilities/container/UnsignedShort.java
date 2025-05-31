package nl.airsupplies.utilities.container;

/**
 * Implements an unsigned short, including proper comparison methods. Implementation is simplified by using the
 * already-available char primitive.
 *
 * @author Mark Jeronimus
 */
// Created 2013-11-27
public class UnsignedShort extends Number implements Comparable<UnsignedShort> {
	public static final short MIN_VALUE = (short)0x0000;
	public static final short MAX_VALUE = (short)0xFFFF;

	private final short value;

	public UnsignedShort(short value) {
		this.value = value;
	}

	public static UnsignedShort valueOf(short value) {
		return new UnsignedShort(value);
	}

	public static UnsignedShort valueOf(int value) {
		return new UnsignedShort((short)value);
	}

	@Override
	public byte byteValue() {
		return (byte)value;
	}

	@Override
	public short shortValue() {
		return value;
	}

	@Override
	public int intValue() {
		return value & 0xFFFF;
	}

	@Override
	public long longValue() {
		return value & 0xFFFFL;
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
	public int compareTo(UnsignedShort o) {
		return (value & 0xFFFF) - (o.value & 0xFFFF);
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof UnsignedShort && value == ((UnsignedShort)obj).value;
	}

	@Override
	public int hashCode() {
		int hash = 0x811C9DC5;
		hash ^= Short.hashCode(value);
		hash *= 0x01000193;
		return hash;
	}
}
