/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2024 Mark Jeronimus. All Rights Reversed.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.digitalmodular.utilities.container;

/**
 * Implements an unsigned short, including proper comparison methods. Implementation is simplified by using the
 * already-available char primitive.
 *
 * @author Mark Jeronimus
 */
// Created 2013-11-27
public class UnsignedShort extends Number implements Comparable<UnsignedShort> {
	public static final short MIN_VALUE = 0x0000;
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
