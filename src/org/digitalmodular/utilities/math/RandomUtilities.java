/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2018 Mark Jeronimus. All Rights Reversed.
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
package org.digitalmodular.utilities.math;

import java.security.SecureRandom;
import java.util.Random;

import org.digitalmodular.utilities.StaticRandom;

/**
 * A set of tools for working and generating random numbers and hashes.
 * <p>
 * For generating random numbers, it is discouraged, even by ORable itself, to use {@link Math#random()} or using
 * multiple instances of {@link Random}. This class address these problems, as well as offering an alternative RNG that
 * is an order of magnitude faster than {@link Random} and a cryptographically secure RNG.
 *
 * @author Mark Jeronimus
 * @deprecated Replaced by {@link StaticRandom}. FastRandom is discouraged.
 */
// Created 2013-11-29
@Deprecated
public class RandomUtilities {
	// Constants:
	public static final long         USED_SEED = System.nanoTime() ^ System.currentTimeMillis();
	/**
	 * Use this singleton instance to generate independent pseudo-random numbers. If any other instance like {@link
	 * Random} or {@link Math#random()} are used, the numbers will no longer be independent pseudo-random numbers.
	 */
	public static       Random       RND       = new Random(USED_SEED);
	/**
	 * A random number generator that is an order of magnitude faster than {@link Random}, yet just as useful.
	 */
	public static final FastRandom   FAST_RND  = new FastRandom(USED_SEED);
	// State:
	private static      SecureRandom secureRnd = null;

	/**
	 * Not instantiable.
	 */
	private RandomUtilities() {
		throw new IllegalArgumentException();
	}

	/**
	 * Get a singleton instance to generate cryptographically secure random numbers. If any other {@link SecureRandom}
	 * instance is used, the numbers will no longer be independent pseudo-random numbers.
	 *
	 * @return the {@link SecureRandom} instance.
	 * @see SecureRandom#SecureRandom()
	 */
	public static Random getSecureRandom() {
		if (secureRnd == null) {
			secureRnd = new SecureRandom();
		}
		return secureRnd;
	}

	/**
	 * Hashes an object using the 32-bit FNV-1a algorithm which is the fastest known hash function optimized for use in
	 * hash tables. The object can either be a primitive (boxed) or an array of primitives (primitives include String).
	 * Giving any other Object will throw an error, as the object should implement it's own {@link Object#hashCode()}
	 * method.
	 * <p>
	 * Note: Results of this call should not be truncated. For exact power-of-2 values, XOR-fold once ( <i>eg.</i>
	 * ((hash &gt;&gt; 8) ^ hash) & 0xFF for 6-bit results) or use modulo for arbitrary ranges ( <i>eg.</i> (hash &
	 * 0x7FFFFFFF) % 150 for 150 different values).
	 * <p>
	 * Note 2: For values &gt; 1 million that are not a power-of-2, this hash function is not recommended, unless bias
	 * is not a concern.
	 *
	 * @return the hash value.
	 */
	public static int hash(Object o) {
		int hash = 0x811C9DC5;
		if (o instanceof Integer) {
			int value = ((Integer)o).intValue();
			hash = (hash ^ value >>> 24) * 0x1000193;
			hash = (hash ^ value >>> 16 & 0xFF) * 0x1000193;
			hash = (hash ^ value >>> 8 & 0xFF) * 0x1000193;
			return (hash ^ value & 0xFF) * 0x1000193;
		}
		if (o instanceof int[]) {
			int[] values = (int[])o;
			for (int value : values) {
				hash = (hash ^ value >>> 24) * 0x1000193;
				hash = (hash ^ value >>> 16 & 0xFF) * 0x1000193;
				hash = (hash ^ value >>> 8 & 0xFF) * 0x1000193;
				hash = (hash ^ value & 0xFF) * 0x1000193;
			}
			return hash;
		}
		if (o instanceof Long) {
			long value = ((Long)o).longValue();
			hash = (hash ^ (int)(value >>> 56)) * 0x1000193;
			hash = (hash ^ (int)(value >>> 48) & 0xFF) * 0x1000193;
			hash = (hash ^ (int)(value >>> 40) & 0xFF) * 0x1000193;
			hash = (hash ^ (int)(value >>> 32) & 0xFF) * 0x1000193;
			hash = (hash ^ (int)value >>> 24) * 0x1000193;
			hash = (hash ^ (int)value >>> 16 & 0xFF) * 0x1000193;
			hash = (hash ^ (int)value >>> 8 & 0xFF) * 0x1000193;
			return (hash ^ (int)value & 0xFF) * 0x1000193;
		}
		if (o instanceof long[]) {
			long[] values = (long[])o;
			for (long value : values) {
				hash = (hash ^ (int)(value >>> 56)) * 0x1000193;
				hash = (hash ^ (int)(value >>> 48) & 0xFF) * 0x1000193;
				hash = (hash ^ (int)(value >>> 40) & 0xFF) * 0x1000193;
				hash = (hash ^ (int)(value >>> 32) & 0xFF) * 0x1000193;
				hash = (hash ^ (int)value >>> 24) * 0x1000193;
				hash = (hash ^ (int)value >>> 16 & 0xFF) * 0x1000193;
				hash = (hash ^ (int)value >>> 8 & 0xFF) * 0x1000193;
				hash = (hash ^ (int)value & 0xFF) * 0x1000193;
			}
			return hash;
		}
		if (o instanceof Float) {
			int value = Float.floatToRawIntBits(((Float)o).floatValue());
			hash = (hash ^ value >>> 24) * 0x1000193;
			hash = (hash ^ value >>> 16 & 0xFF) * 0x1000193;
			hash = (hash ^ value >>> 8 & 0xFF) * 0x1000193;
			return (hash ^ value & 0xFF) * 0x1000193;
		}
		if (o instanceof float[]) {
			float[] values = (float[])o;
			for (float f : values) {
				int value = Float.floatToRawIntBits(f);
				hash = (hash ^ value >>> 24) * 0x1000193;
				hash = (hash ^ value >>> 16 & 0xFF) * 0x1000193;
				hash = (hash ^ value >>> 8 & 0xFF) * 0x1000193;
				hash = (hash ^ value & 0xFF) * 0x1000193;
			}
			return hash;
		}
		if (o instanceof Long) {
			long value = Double.doubleToRawLongBits(((Double)o).doubleValue());
			hash = (hash ^ (int)(value >>> 56)) * 0x1000193;
			hash = (hash ^ (int)(value >>> 48) & 0xFF) * 0x1000193;
			hash = (hash ^ (int)(value >>> 40) & 0xFF) * 0x1000193;
			hash = (hash ^ (int)(value >>> 32) & 0xFF) * 0x1000193;
			hash = (hash ^ (int)value >>> 24) * 0x1000193;
			hash = (hash ^ (int)value >>> 16 & 0xFF) * 0x1000193;
			hash = (hash ^ (int)value >>> 8 & 0xFF) * 0x1000193;
			return (hash ^ (int)value & 0xFF) * 0x1000193;
		}
		if (o instanceof double[]) {
			double[] values = (double[])o;
			for (double d : values) {
				long value = Double.doubleToRawLongBits(d);
				hash = (hash ^ (int)(value >>> 56)) * 0x1000193;
				hash = (hash ^ (int)(value >>> 48) & 0xFF) * 0x1000193;
				hash = (hash ^ (int)(value >>> 40) & 0xFF) * 0x1000193;
				hash = (hash ^ (int)(value >>> 32) & 0xFF) * 0x1000193;
				hash = (hash ^ (int)value >>> 24) * 0x1000193;
				hash = (hash ^ (int)value >>> 16 & 0xFF) * 0x1000193;
				hash = (hash ^ (int)value >>> 8 & 0xFF) * 0x1000193;
				hash = (hash ^ (int)value & 0xFF) * 0x1000193;
			}
			return hash;
		}
		if (o instanceof Byte)
			return (hash ^ ((Byte)o).byteValue() & 0xFF) * 0x1000193;
		if (o instanceof byte[]) {
			byte[] values = (byte[])o;
			for (byte value : values) {
				hash = (hash ^ value & 0xFF) * 0x1000193;
			}
			return hash;
		}
		if (o instanceof Short) {
			short value = ((Short)o).shortValue();
			// short doesn't handle >>> well as it's first converted to int with sign-extend
			hash = (hash ^ value >> 8 & 0xFF) * 0x1000193;
			return (hash ^ value & 0xFF) * 0x1000193;
		}
		if (o instanceof short[]) {
			short[] values = (short[])o;
			for (short value : values) {
				// short doesn't handle >>> well as it's first converted to int with sign-extend
				hash = (hash ^ value >> 8 & 0xFF) * 0x1000193;
				hash = (hash ^ value & 0xFF) * 0x1000193;
			}
			return hash;
		}
		if (o instanceof Character) {
			char value = ((Character)o).charValue();
			hash = (hash ^ value >>> 8) * 0x1000193;
			return (hash ^ value & 0xFF) * 0x1000193;
		}
		if (o instanceof char[]) {
			char[] values = (char[])o;
			for (char value : values) {
				hash = (hash ^ value >>> 8) * 0x1000193;
				hash = (hash ^ value & 0xFF) * 0x1000193;
			}
			return hash;
		}
		if (o instanceof String) {
			byte[] values = ((String)o).getBytes();
			for (byte value : values) {
				hash = (hash ^ value & 0xFF) * 0x1000193;
			}
			return hash;
		}
		if (o instanceof String[]) {
			for (String s : (String[])o) {
				byte[] values = s.getBytes();
				for (byte value : values) {
					hash = (hash ^ value & 0xFF) * 0x1000193;
				}
			}
			return hash;
		}

		throw new IllegalArgumentException(
				o.getClass().getName() + " is not a primitive or array of primitives.");
	}

}
