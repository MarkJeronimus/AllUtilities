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
 * @author Mark Jeronimus
 */
// Created 2005-10-03
public class Vector4f implements Comparable<Vector4f> {
	public float x;
	public float y;
	public float z;
	public float w;

	/** Create a zero-length vector. */
	public Vector4f() {
		x = 0;
		y = 0;
		z = 0;
		w = 0;
	}

	/**
	 * Create a defined Vector4f
	 */
	public Vector4f(float x, float y, float z, float t) {
		this.x = x;
		this.y = y;
		this.z = z;
		w      = t;
	}

	public void set(Vector4f o) {
		x = o.x;
		y = o.y;
		z = o.z;
		w = o.w;
	}

	public void set(float x, float y, float z, float t) {
		this.x = x;
		this.y = y;
		this.z = z;
		w      = t;
	}

	/**
	 * Returns a Vector added to this Vector4f
	 */
	public void add(Vector4f o) {
		x += o.x;
		y += o.y;
		z += o.z;
		w += o.w;
	}

	/**
	 * Multiply this Vector4f with a factor
	 */
	public void mul(float factor) {
		x *= factor;
		y *= factor;
		z *= factor;
	}

	/**
	 * Returns the inner product (sometimes called the dot product) of this Vector4f and the supplied Vector4f
	 */
	public float innerProduct(Vector4f o) {
		return x * o.x + y * o.y + z * o.z + w * o.w;
	}

	public float length() {
		return (float)Math.sqrt(x * x + y * y + z * z + w * w);
	}

	public float distanceTo(float x, float y, float z, float t) {
		x -= this.x;
		y -= this.y;
		z -= this.z;
		t -= w;
		return (float)Math.sqrt(x * x + y * y + z * z + t * t);
	}

	public float distanceTo(Vector4f o) {
		float x = this.x - o.x;
		float y = this.y - o.y;
		float z = this.z - o.z;
		float t = w - o.w;
		return (float)Math.sqrt(x * x + y * y + z * z + t * t);
	}

	/**
	 * Returns the distance squared of the Vector4f
	 */
	public float mod() {
		return x * x + y * y + z * z + w * w;
	}

	public void normalize() {
		float det = (float)Math.sqrt(x * x + y * y + z * z + w * w);
		if (det == 0) {
			x = 0;
			y = 0;
			z = 0;
			w = 0;
		} else {
			x /= det;
			y /= det;
			z /= det;
			w /= det;
		}
	}

	public void minimum(Vector4f o) {
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

	public void maximum(Vector4f o) {
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
	public int compareTo(Vector4f o) {
		int diff = Float.compare(w, o.w);
		if (diff != 0) {
			return diff;
		}
		diff = Float.compare(z, o.z);
		if (diff != 0) {
			return diff;
		}
		diff = Float.compare(y, o.y);
		if (diff != 0) {
			return diff;
		}
		return Float.compare(x, o.x);
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ", " + w + ')';
	}
}
