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

import java.io.Serializable;

/**
 * @author Mark Jeronimus
 */
// Created 2005-10-17
public class Vector2f implements Comparable<Vector2f>, Serializable {
	public float x;
	public float y;

	/**
	 * Create a new 2-dimensional vector of length 0.
	 */
	public Vector2f() {
		x = 0;
		y = 0;
	}

	/**
	 * Create a new 2-dimensional vector
	 */
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}

	// Speed classification: Object
	public void set(Vector2f v) {
		x = v.x;
		y = v.y;
	}

	public void negSelf() {
		x = -x;
		y = -y;
	}

	public Vector2f add(float f) {
		return new Vector2f(x + f, y + f);
	}

	public void addSelf(float f) {
		x += f;
		y += f;
	}

	public Vector2f add(float x, float y) {
		return new Vector2f(this.x + x, this.y + y);
	}

	public void addSelf(float x, float y) {
		this.x += x;
		this.y += y;
	}

	public Vector2f add(Vector2f vector) {
		return new Vector2f(x + vector.x, y + vector.y);
	}

	public void addSelf(Vector2i o) {
		x += o.x;
		y += o.y;
	}

	public void addSelf(Vector2f vector) {
		x += vector.x;
		y += vector.y;
	}

	public Vector2f addScaled(Vector2f v, float scale) {
		return new Vector2f(x + v.x * scale, y + v.y * scale);
	}

	// Speed classification: Object
	public void addScaledSelf(Vector2f v, float scale) {
		x += v.x * scale;
		y += v.y * scale;
	}

	public void addScaledSelf(Vector2i v, float scale) {
		x += v.x * scale;
		y += v.y * scale;
	}

	public Vector2f sub(float f) {
		return new Vector2f(x - f, y - f);
	}

	public void subSelf(float f) {
		x -= f;
		y -= f;
	}

	public void subSelfR(float f) {
		x = f - x;
		y = f - y;
	}

	public Vector2f sub(float x, float y) {
		return new Vector2f(this.x - x, this.y - y);
	}

	public Vector2f sub(Vector2i o) {
		return new Vector2f(x - o.x, y - o.y);
	}

	public void subSelf(float x, float y) {
		this.x -= x;
		this.y -= y;
	}

	public void subSelfR(float x, float y) {
		this.x = x - this.x;
		this.y = y - this.y;
	}

	public Vector2f sub(Vector2f vector) {
		return new Vector2f(x - vector.x, y - vector.y);
	}

	public void subSelf(Vector2f vector) {
		x -= vector.x;
		y -= vector.y;
	}

	public void subSelfR(Vector2f vector) {
		x = vector.x - x;
		y = vector.y - y;
	}

	public Vector2f scale(float f) {
		return new Vector2f(x * f, y * f);
	}

	public void scaleSelf(float f) {
		x *= f;
		y *= f;
	}

	public Vector2f scale(float x, float y) {
		return new Vector2f(this.x * x, this.y * y);
	}

	public void scaleSelf(float x, float y) {
		this.x *= x;
		this.y *= y;
	}

	public Vector2f scale(Vector2f vector) {
		return new Vector2f(x * vector.x, y * vector.y);
	}

	public void scaleSelf(Vector2f vector) {
		x *= vector.x;
		y *= vector.y;
	}

	/**
	 * Returns a matrix multiplied by this vector
	 */
	public Vector2f mul(Matrix3f matrix) {
		return new Vector2f( //
		                     x * matrix.x0 + y * matrix.x1 + matrix.x2, //
		                     x * matrix.y0 + y * matrix.y1 + matrix.y2);
	}

	/**
	 * Set this vector to the result of a matrix multiplied by this vector
	 */
	public void mulSelf(Matrix3f matrix) {
		float x = this.x * matrix.x0 + y * matrix.x1 + matrix.x2;
		y = this.x * matrix.y0 + y * matrix.y1 + matrix.y2;
		this.x = x;
	}

	public float distanceTo(Vector2f o) {
		float dx = x - o.x;
		float dy = y - o.y;
		return (float)Math.sqrt(dx * dx + dy * dy);
	}

	public float distanceTo(Vector2i o) {
		float dx = x - o.x;
		float dy = y - o.y;
		return (float)Math.sqrt(dx * dx + dy * dy);
	}

	public float dotProduct(Vector2f vector) {
		return x * vector.x + y * vector.y;
	}

	public Vector2f rotate(float theta) {
		float s = (float)Math.sin(theta);
		float c = (float)Math.cos(theta);

		return new Vector2f(x * c - y * s, x * s + y * c);
	}

	public void rotateSelf(float theta) {
		float s = (float)Math.sin(theta);
		float c = (float)Math.cos(theta);

		float x = this.x * c - y * s;
		y = this.x * s + y * c;
		this.x = x;
	}

	// Speed classification: Object
	public void multiplyMatrixByVectorSelf(Matrix3f m) {
		float x = this.x;
		float y = this.y;
		this.x = m.x0 * x + m.x1 * y;
		this.y = m.y0 * x + m.y1 * y;
	}

	// Speed classification: Object
	public void multiplyMatrixByPointSelf(Matrix3f m) {
		float x = this.x;
		float y = this.y;
		this.x = m.x0 * x + m.x1 * y + m.x2;
		this.y = m.y0 * x + m.y1 * y + m.y2;
	}

	public Vector2f normalize() {
		float determinant = 1 / (float)Math.sqrt(x * x + y * y);
		return new Vector2f(x * determinant, y * determinant);
	}

	public void normalizeSelf() {
		float determinant = 1 / (float)Math.sqrt(x * x + y * y);
		x *= determinant;
		y *= determinant;
	}

	public Vector2f invert() {
		return new Vector2f(-x, -y);
	}

	public float length() {
		return (float)Math.hypot(x, y);
	}

	public float lengthSquared() {
		return x * x + y * y;
	}

	public float arg() {
		return (float)Math.atan2(y, x);
	}

	/**
	 * x = length<br> y = angle
	 *
	 * @return the coordinate of the vector in the Cartesian plane which is represented by the vector with length and
	 * angle starting at the origin.
	 */
	public Vector2f polarToCartesian() {
		return new Vector2f(x * (float)Math.cos(y), x * (float)Math.sin(y));
	}

	/**
	 * x = length<br> y = angle
	 */
	public void polarToCartesianSelf() {
		float x = this.x * (float)Math.cos(y);
		y = this.x * (float)Math.sin(y);
		this.x = x;
	}

	@Override
	public int compareTo(Vector2f o) {
		int diff = Float.compare(y, o.y);
		if (diff != 0) {
			return diff;
		}
		return Float.compare(x, o.x);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Vector2f) {
			Vector2f v = (Vector2f)obj;
			return x == v.x && y == v.y;
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
		return hash;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + '(' + x + ", " + y + ')';
	}

	public void flip() {
		float t = x;
		x = y;
		y = t;
	}
}
