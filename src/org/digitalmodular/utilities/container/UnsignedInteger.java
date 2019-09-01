/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2019 Mark Jeronimus. All Rights Reversed.
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
 * along with AllUtilities. If not, see <http://www.gnu.org/licenses/>.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.digitalmodular.utilities.container;

import org.digitalmodular.utilities.NumberUtilities;

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
		if (!(obj instanceof UnsignedInteger))
			return false;
		return value == ((UnsignedInteger)obj).value;
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

		while (b.length() < diff)
			b.append('0');

		b.append(num.substring(Math.max(0, -diff)));

		return b.toString();
	}

	public static String toBinaryString(int value, int length) {
		if (length < 1 || length > 32)
			throw new IllegalArgumentException("length: " + length);

		StringBuilder out = new StringBuilder(32);

		int mask = 1 << length - 1;
		for (int i = 0; i < length; i++) {
			out.append((value & mask) != 0 ? '1' : '0');
			mask >>>= 1;
		}

		return out.toString();
	}

	public static String toHexString(int value, int length) {
		if (length < 1 || length > 8)
			throw new IllegalArgumentException("length: " + length);

		StringBuilder out = new StringBuilder(32);

		for (int i = (length - 1) * 4; i >= 0; i -= 4) {
			out.append(NumberUtilities.DIGITS[value >>> i & 0xF]);
		}

		return out.toString();
	}
}
