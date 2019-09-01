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
package org.digitalmodular.utilities.collection;

import java.lang.reflect.Method;
import java.util.Iterator;

import org.digitalmodular.utilities.NumberUtilities;

/**
 * A mapping between an integer bitmask and an enum (of up to 64 elements).
 *
 * @author Mark Jeronimus
 */
// Created 2014-09-04
public class EnumBitMask<E extends Enum<E>> implements Iterable<E> {
	final E[] values;
	long bitMask;

	private EnumBitMask(Class<E> elementType) {
		try {
			Method valuesMethod = elementType.getMethod("values", new Class<?>[]{});
			values = (E[])valuesMethod.invoke(null, new Object[]{});
		} catch (ReflectiveOperationException | IllegalArgumentException e) {
			throw new RuntimeException("elementType is not an Enum");
		}

		if (values.length > 64) {
			throw new IllegalArgumentException("Attempted to create an " + EnumBitMask.class.getSimpleName()
			                                   + " from an enum with " + values.length + " elements. (max 64)");
		}
	}

	/**
	 * Creates an {@link EnumBitMask} using a specified bitmask. The elements corresponding to '1' bits in the binary
	 * representation of the mask will be in this set.
	 */
	public EnumBitMask(Class<E> elementType, long bitMask) {
		this(elementType);

		this.bitMask = bitMask;
	}

	/**
	 * Creates an {@link EnumBitMask} containing the specified elements. It is equal to creating an empty {@link
	 * EnumBitMask} and adding each element separately using the {@link #add(java.lang.Enum)} method.
	 */
	@SuppressWarnings("unchecked")
	public EnumBitMask(E... elements) {
		this((Class<E>)elements[0].getClass());

		bitMask = 0;
		for (E element : elements) {
			add(element);
		}
	}

	/**
	 * Sets the bitmask where bits in the binary representation are '1' if the corresponding Enum is in the set.
	 */
	public void setBitMask(long bitMask) {
		if (NumberUtilities.bitSize(bitMask) > values.length) {
			throw new IllegalArgumentException("Bits set for nonexisting enum values");
		}

		this.bitMask = bitMask;
	}

	/**
	 * Returns the bitmask where bits in the binary representation are '1' if the corresponding Enum is in the set.
	 */
	public long getBitMask() {
		return bitMask;
	}

	/**
	 * Adds an Enum element to the set whether it was there already or not. An element can't be in the set twice.
	 */
	public void add(E element) {
		bitMask |= 1L << element.ordinal();
	}

	/**
	 * Removes an Enum element from the set whether it was there already or not. There can't be a negative amount of an
	 * element.
	 */
	public void remove(E element) {
		bitMask &= ~(1L << element.ordinal());
	}

	/**
	 * Checks whether the set contains an Enum element.
	 */
	public boolean contains(E element) {
		return (bitMask &= 1L << element.ordinal()) != 0;
	}

	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			private int i;

			public Iterator<E> init() {
				// Find first.
				for (i = 0; i < 32; i++) {
					if ((bitMask & 1L << i) != 0) {
						break;
					}
				}

				return this;
			}

			@Override
			public boolean hasNext() {
				return i < 32;
			}

			@Override
			public E next() {
				E value = values[i];

				// Find next.
				for (i++; i < 32; i++) {
					if ((bitMask & 1L << i) != 0) {
						break;
					}
				}

				return value;
			}

			@Override
			public void remove() {
				bitMask &= ~(1L << i);
			}
		}.init();
	}

	@Override
	public String toString() {
		StringBuilder out = new StringBuilder(getClass().getSimpleName()).append('[');
		int           i   = 0;
		for (E element : this) {
			if (i++ > 0) {
				out.append(", ");
			}
			out.append(element.toString());
		}
		return out.append(']').toString();
	}
}
