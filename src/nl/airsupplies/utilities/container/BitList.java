package nl.airsupplies.utilities.container;

/**
 * @author Mark Jeronimus
 */
// Created 2016-03-20
public interface BitList {
	boolean isEmpty();

	int getBitLength();

	int getByteLength();

	boolean getBit(int index);

	byte getByte(int index);

	short getShort(int index);

	int getInt(int index);

	long getLong(int index);

	BitArrayList copy();

	BitList getSubList(int begin, int end);

	default byte[] toByteArray() {
		return toByteArray(new byte[getByteLength()]);
	}

	byte[] toByteArray(byte[] array);
}
