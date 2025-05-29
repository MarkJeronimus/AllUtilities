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
public class Vector3f implements Comparable<Vector3f> {
	public float x;
	public float y;
	public float z;

	public Vector3f() {
		x = y = z = 0;
	}

	public Vector3f(float f) {
		x = y = z = f;
	}

	public Vector3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3f(Vector3f v) {
		x = v.x;
		y = v.y;
		z = v.z;
	}

	public void set(float f) {
		x = y = z = f;
	}

	public void set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void set(Vector3f v) {
		x = v.x;
		y = v.y;
		z = v.z;
	}

	public void add(float f) {
		x += f;
		y += f;
		z += f;
	}

	public void add(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
	}

	public void add(Vector3f v) {
		x += v.x;
		y += v.y;
		z += v.z;
	}

	public void addScaled(Vector3f v, float scale) {
		x += v.x * scale;
		y += v.y * scale;
		z += v.z * scale;
	}

	public void sub(float f) {
		x -= f;
		y -= f;
		z -= f;
	}

	public void subR(float f) {
		x = f - x;
		y = f - y;
		z = f - z;
	}

	public void sub(float x, float y, float z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
	}

	public void subR(float x, float y, float z) {
		this.x = x - this.x;
		this.y = y - this.y;
		this.z = z - this.z;
	}

	public void sub(Vector3f v) {
		x -= v.x;
		y -= v.y;
		z -= v.z;
	}

	public void subR(Vector3f v) {
		x = v.x - x;
		y = v.y - y;
		z = v.z - z;
	}

	public void mul(float f) {
		x *= f;
		y *= f;
		z *= f;
	}

	public void mul(float x, float y, float z) {
		this.x *= x;
		this.y *= y;
		this.z *= z;
	}

	public void mul(Vector3f v) {
		x *= v.x;
		y *= v.y;
		z *= v.z;
	}

	/**
	 * Set this vector to the result of a matrix multiplied by this vector
	 */
	public void mul(Matrix4f matrix) {
		float x = this.x * matrix.x0 + y * matrix.x1 + z * matrix.x2 + matrix.x3;
		float y = this.x * matrix.y0 + this.y * matrix.y1 + z * matrix.y2 + matrix.y3;
		z      = this.x * matrix.z0 + this.y * matrix.z1 + z * matrix.z2 + matrix.z3;
		this.x = x;
		this.y = y;
	}

	public void sqr() {
		x *= x;
		y *= y;
		z *= z;
	}

	public float dotProduct(Vector3f vector) {
		return x * vector.x + y * vector.y + z * vector.z;
	}

	public void crossProduct(Vector3f v) {
		float x = y * v.z - z * v.y;
		float y = z * v.x - this.x * v.z;
		z      = this.x * v.y - this.y * v.x;
		this.x = x;
		this.y = y;
	}

	public void crossProductR(Vector3f v) {
		float x = v.y * z - v.z * y;
		float y = v.z * this.x - v.x * z;
		z      = v.x * this.y - v.y * this.x;
		this.x = x;
		this.y = y;
	}

	public void rotateX(float theta) {
		float s = (float)Math.sin(theta);
		float c = (float)Math.cos(theta);

		float y = this.y * c - z * s;
		z      = this.y * s + z * c;
		this.y = y;
	}

	public void rotateY(float theta) {
		float s = (float)Math.sin(theta);
		float c = (float)Math.cos(theta);

		float z = this.z * c - x * s;
		x      = this.z * s + x * c;
		this.z = z;
	}

	public void rotateZ(float theta) {
		float s = (float)Math.sin(theta);
		float c = (float)Math.cos(theta);

		float x = this.x * c - y * s;
		y      = this.x * s + y * c;
		this.x = x;
	}

	// Speed classification: Object
	public void multiplyMatrixByVector(Matrix4f m) {
		float x = this.x;
		float y = this.y;
		float z = this.z;
		this.x = m.x0 * x + m.x1 * y + m.x2 * z;
		this.y = m.y0 * x + m.y1 * y + m.y2 * z;
		this.z = m.z0 * x + m.z1 * y + m.z2 * z;
	}

	// Speed classification: Object
	public void multiplyMatrixByPoint(Matrix4f m) {
		float x = this.x;
		float y = this.y;
		float z = this.z;
		this.x = m.x0 * x + m.x1 * y + m.x2 * z + m.x3;
		this.y = m.y0 * x + m.y1 * y + m.y2 * z + m.y3;
		this.z = m.z0 * x + m.z1 * y + m.z2 * z + m.z3;
	}

	public void normalize() {
		float det = 1 / (float)Math.sqrt(x * x + y * y + z * z);
		x *= det;
		y *= det;
		z *= det;
	}

	public Vector3f invert() {
		return new Vector3f(-x, -y, -z);
	}

	public float length() {
		return (float)Math.sqrt(x * x + y * y + z * z);
	}

	public float mod() {
		return x * x + y * y + z * z;
	}

	@Override
	public int compareTo(Vector3f o) {
		int diff = Float.compare(z, o.z);
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
	public boolean equals(Object obj) {
		if (obj instanceof Vector3f v) {
			return x == v.x && y == v.y && z == v.z;
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		int hash = 0x811C9DC5;
		hash ^= Float.hashCode(x);
		hash *= 0x01000193;
		hash ^= Float.hashCode(y);
		hash *= 0x01000193;
		hash ^= Float.hashCode(z);
		hash *= 0x01000193;
		return hash;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + '(' + x + ", " + y + ", " + z + ')';
	}

	public void mulR(Matrix4f matrix) {
		float x = matrix.x0 * this.x + matrix.x1 * y + matrix.x2 * z + matrix.x3;
		float y = matrix.y0 * this.x + matrix.y1 * this.y + matrix.y2 * z + matrix.y3;
		z      = matrix.z0 * this.x + matrix.z1 * this.y + matrix.z2 * z + matrix.z3;
		this.x = x;
		this.y = y;
	}
}
