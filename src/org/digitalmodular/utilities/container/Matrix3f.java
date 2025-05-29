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
public class Matrix3f {
	public float x0;
	public float x1;
	public float x2;
	public float y0;
	public float y1;
	public float y2;
	public float z0;
	public float z1;
	public float z2;

	/**
	 * Create an identity Matrix3f
	 */
	public Matrix3f() {
		this(1, 0, 0, 0, 1, 0, 0, 0, 1);
	}

	/**
	 * Create a defined Matrix3f
	 */
	public Matrix3f(float x0, float x1, float x2, float y0, float y1, float y2, float z0, float z1, float z2) {
		this.x0 = x0;
		this.x1 = x1;
		this.x2 = x2;
		this.y0 = y0;
		this.y1 = y1;
		this.y2 = y2;
		this.z0 = z0;
		this.z1 = z1;
		this.z2 = z2;
	}

	/**
	 * Returns this Matrix3f multiplied with a vector
	 */
	public Vector3f multiply(Vector3f vector) {
		return new Vector3f(vector.x * x0 + vector.y * x1 + vector.z * x2,
		                    vector.x * y0 + vector.y * y1 + vector.z * y2,
		                    vector.x * z0 + vector.y * z1 + vector.z * z2);
	}

	/**
	 * Returns a Matrix3f such that when multiplied with the original, the identity Matrix3f results.
	 *
	 * @return The inverse of this Matrix3f
	 */
	public Matrix3f recip() {
		float x0d = y1 * z2 - y2 * z1;
		float x1d = z1 * x2 - z2 * x1;
		float x2d = x1 * y2 - x2 * y1;

		float d = x0 * x0d + y0 * x1d + z0 * x2d;

		Matrix3f n = new Matrix3f(x0d / d, x1d / d, x2d / d,
		                          (y2 * z0 - y0 * z2) / d, (z2 * x0 - z0 * x2) / d, (x2 * y0 - x0 * y2) / d,
		                          (y0 * z1 - y1 * z0) / d, (z0 * x1 - z1 * x0) / d, (x0 * y1 - x1 * y0) / d);

		return n;
	}

	/**
	 *
	 */
	public Matrix3f rotatex(float rotx) {
		float c = (float)Math.cos(rotx);
		float s = (float)Math.sin(rotx);

		// TODO Make inline
		Matrix3f mul = new Matrix3f(1, 0, 0,
		                            0, c, -s,
		                            0, s, c);

		return mul.multiply(this);
	}

	/**
	 *
	 */
	public Matrix3f rotatey(float roty) {
		float c = (float)Math.cos(roty);
		float s = (float)Math.sin(roty);

		// TODO Make inline
		Matrix3f mul = new Matrix3f(c, 0, s,
		                            0, 1, 0,
		                            -s, 0, c);

		return mul.multiply(this);
	}

	/**
	 *
	 */
	private Matrix3f multiply(Matrix3f m) {
		return new Matrix3f(m.x0 * x0 + m.x1 * y0 + m.x2 * z0,
		                    m.x0 * x1 + m.x1 * y1 + m.x2 * z1,
		                    m.x0 * x2 + m.x1 * y2 + m.x2 * z2,

		                    m.y0 * x0 + m.y1 * y0 + m.y2 * z0,
		                    m.y0 * x1 + m.y1 * y1 + m.y2 * z1,
		                    m.y0 * x2 + m.y1 * y2 + m.y2 * z2,

		                    m.z0 * x0 + m.z1 * y0 + m.z2 * z0,
		                    m.z0 * x1 + m.z1 * y1 + m.z2 * z1,
		                    m.z0 * x2 + m.z1 * y2 + m.z2 * z2);
	}

	/**
	 *
	 */
	public Matrix3f rotatez(float rotz) {
		float c = (float)Math.cos(rotz);
		float s = (float)Math.sin(rotz);

		Matrix3f mul = new Matrix3f(c, -s, 0,
		                            s, c, 0,
		                            0, 0, 1);

		return mul.multiply(this);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[\n[" +
		       x0 + '\t' + x1 + '\t' + x2 + "]\n[" +
		       y0 + '\t' + y1 + '\t' + y2 + "]\n[" +
		       z0 + '\t' + z1 + '\t' + z2 + "]]\n";
	}
}
