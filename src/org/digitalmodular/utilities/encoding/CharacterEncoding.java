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

package org.digitalmodular.utilities.encoding;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * @author Mark Jeronimus
 */
// Created 2012-07-30
public abstract class CharacterEncoding {
	protected static char[]     surrogatePair = new char[2];
	protected static CharBuffer charBuffer    = CharBuffer.allocate(1);
	protected static ByteBuffer byteBuffer    = ByteBuffer.allocate(2);

	public static final CharacterEncoding jisEncoding = new JISEncoding();

	public abstract int encode(int codePoint);

	public abstract int decode(int codePoint);
}
