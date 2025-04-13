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
// Created 2014-06-17 copy from Matrix3d
public class Matrix2d {

	public double x0;
	public double x1;
	public double y0;
	public double y1;

	/**
	 * Create an identity Matrix2d
	 */
	public Matrix2d() {
		this(1, 0, 0, 1);
	}

	/**
	 * Create a defined Matrix2d
	 */
	public Matrix2d(double x0, double x1, double y0, double y1) {
		this.x0 = x0;
		this.x1 = x1;
		this.y0 = y0;
		this.y1 = y1;
	}

	public void set(Matrix2d o) {
		x0 = o.x0;
		x1 = o.x1;
		y0 = o.y0;
		y1 = o.y1;
	}

	public void set(double x0, double x1, double y0, double y1) {
		this.x0 = x0;
		this.x1 = x1;
		this.y0 = y0;
		this.y1 = y1;
	}

	/**
	 * Returns this Matrix2d multiplied with a vector
	 */
	public Vector2d multiply(Vector2d vector) {
		return new Vector2d(vector.x * x0 + vector.y * x1, //
		                    vector.x * y0 + vector.y * y1);
	}

	/**
	 * Returns this Matrix2d multiplied with a vector
	 */
	public Vector2d multiply(Vector2i vector) {
		return new Vector2d(vector.x * x0 + vector.y * x1, //
		                    vector.x * y0 + vector.y * y1);
	}

	/**
	 * Returns a Matrix2d such that when multiplied with the original, the identity Matrix2d results.
	 *
	 * @return The inverse of this Matrix2d
	 */
	public Matrix2d recip() {
		double d = x0 * y1 - y0 * x1;

		return new Matrix2d(y1 / d, -x1 / d, //
		                    -y0 / d, x0 / d);
	}

	public Matrix2d rotate(double rotz) {
		double c = Math.cos(rotz);
		double s = Math.sin(rotz);

		Matrix2d mul = new Matrix2d(c, -s, //
		                            s, c);

		return mul.multiply(this);
	}

	public Matrix2d flipX() {
		return new Matrix2d(-x0, -x1, //
		                    y0, y1);
	}

	public Matrix2d flipY() {
		return new Matrix2d(x0, x1, //
		                    -y0, -y1);
	}

	private Matrix2d multiply(Matrix2d m) {
		return new Matrix2d(m.x0 * x0 + m.x1 * y0, m.x0 * x1 + m.x1 * y1, //
		                    m.y0 * x0 + m.y1 * y0, m.y0 * x1 + m.y1 * y1);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[\n[" //
		       + x0 + '\t' + x1 + "]\n[" //
		       + y0 + '\t' + y1 + "]]\n";
	}
}
