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

package nl.airsupplies.utilities.container;

/**
 * @author Mark Jeronimus
 */
// Created 2005-10-03
public class Vector4i implements Comparable<Vector4i> {
	public int x;
	public int y;
	public int z;
	public int w;

	/**
	 * Create a null-vector
	 */
	public Vector4i() {
		x = 0;
		y = 0;
		z = 0;
		w = 0;
	}

	/**
	 * Create a defined Vector4d
	 */
	public Vector4i(int x, int y, int z, int t) {
		this.x = x;
		this.y = y;
		this.z = z;
		w      = t;
	}

	public void set(Vector4i o) {
		x = o.x;
		y = o.y;
		z = o.z;
		w = o.w;
	}

	public void set(int x, int y, int z, int t) {
		this.x = x;
		this.y = y;
		this.z = z;
		w      = t;
	}

	/**
	 * Returns a Vector added to this Vector4d
	 */
	public Vector4i add(Vector4i o) {
		return new Vector4i(x + o.x, y + o.y, z + o.z, w + o.w);
	}

	public void addSelf(Vector4i o) {
		x += o.x;
		y += o.y;
		z += o.z;
		w += o.w;
	}

	/**
	 * Returns the subtraction of a supplied Vector4d from this Vector4d
	 */
	public Vector4i sub(Vector4i o) {
		return new Vector4i(x - o.x, y - o.y, z - o.z, w - o.w);
	}

	/**
	 * Returns this Vector4d multiplied by a factor
	 */
	public Vector4i mul(int factor) {
		return new Vector4i(x * factor, y * factor, z * factor, w * factor);
	}

	/**
	 * Multiply this Vector4d with a factor
	 */
	public void mulSelf(int factor) {
		x *= factor;
		y *= factor;
		z *= factor;
	}

	/**
	 * Returns the addition of this Vector4d and a scaled Vector4d
	 */
	public Vector4i addScaled(Vector4i o, int scale) {
		return new Vector4i(x + o.x * scale, y + o.y * scale, z + o.z * scale, w + o.w * scale);
	}

	/**
	 * Returns the inner product (sometimes called the dot product) of this Vector4d and the supplied Vector4d
	 */
	public int innerProduct(Vector4i o) {
		return x * o.x + y * o.y + z * o.z + w * o.w;
	}

	/**
	 * Returns the vectorized multiplication of this Vector4d and the supplied Vector4d
	 */
	public Vector4i vectorProduct(Vector4i o) {
		return new Vector4i(x * o.x, y * o.y, z * o.z, w * o.w);
	}

	public double length() {
		return Math.sqrt(x * x + y * y + z * z + w * w);
	}

	public double distanceTo(int x, int y, int z, int t) {
		x -= this.x;
		y -= this.y;
		z -= this.z;
		t -= w;
		return Math.sqrt(x * x + y * y + z * z + t * t);
	}

	public int distanceTo(Vector4i o) {
		int x = this.x - o.x;
		int y = this.y - o.y;
		int z = this.z - o.z;
		int t = w - o.w;
		return (int)Math.sqrt(x * x + y * y + z * z + t * t);
	}

	/**
	 * Returns the distance squared of the Vector4d
	 */
	public int mod() {
		return x * x + y * y + z * z + w * w;
	}

	public Vector4i invert() {
		return new Vector4i(-x, -y, -z, -w);
	}

	public void minimumSelf(Vector4i o) {
		if (x > o.x) {
			x = o.x;
		}
		if (y > o.y) {
			y = o.y;
		}
		if (z > o.z) {
			z = o.z;
		}
		if (w > o.w) {
			w = o.w;
		}
	}

	public void maximumSelf(Vector4i o) {
		if (x < o.x) {
			x = o.x;
		}
		if (y < o.y) {
			y = o.y;
		}
		if (z < o.z) {
			z = o.z;
		}
		if (w < o.w) {
			w = o.w;
		}
	}

	@Override
	public int compareTo(Vector4i o) {
		int diff = Integer.compare(w, o.w);
		if (diff != 0) {
			return diff;
		}
		diff = Integer.compare(z, o.z);
		if (diff != 0) {
			return diff;
		}
		diff = Integer.compare(y, o.y);
		if (diff != 0) {
			return diff;
		}
		return Integer.compare(x, o.x);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Vector4i v) {
			return x == v.x && y == v.y && z == v.z && w == v.w;
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		int hash = 0x811C9DC5;
		hash ^= Integer.hashCode(x);
		hash *= 0x01000193;
		hash ^= Integer.hashCode(y);
		hash *= 0x01000193;
		hash ^= Integer.hashCode(z);
		hash *= 0x01000193;
		hash ^= Integer.hashCode(w);
		hash *= 0x01000193;
		return hash;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ", " + w + ')';
	}
}
