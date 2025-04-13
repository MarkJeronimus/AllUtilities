package org.digitalmodular.utilities.container;

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
 * along with temp. If not, see <http://www.gnu.org/licenses/>.
 */

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
