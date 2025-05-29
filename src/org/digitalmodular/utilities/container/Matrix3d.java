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
public class Matrix3d {
	public double x0;
	public double x1;
	public double x2;
	public double y0;
	public double y1;
	public double y2;
	public double z0;
	public double z1;
	public double z2;

	/**
	 * Create an identity Matrix3d
	 */
	public Matrix3d() {
		this(1, 0, 0, 0, 1, 0, 0, 0, 1);
	}

	/**
	 * Create a defined Matrix3d
	 */
	public Matrix3d(double x0, double x1, double x2,
	                double y0, double y1, double y2,
	                double z0, double z1, double z2) {
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
	 * Returns this Matrix3d multiplied with a vector
	 */
	public Vector3d multiply(Vector3d vector) {
		return new Vector3d(
				vector.x * x0 + vector.y * x1 + vector.z * x2,
				vector.x * y0 + vector.y * y1 + vector.z * y2,
				vector.x * z0 + vector.y * z1 + vector.z * z2);
	}

	/**
	 * Returns a Matrix3d such that when multiplied with the original, the identity Matrix3d results.
	 *
	 * @return The inverse of this Matrix3d
	 */
	public Matrix3d recip() {
		double x0d = y1 * z2 - y2 * z1;
		double x1d = z1 * x2 - z2 * x1;
		double x2d = x1 * y2 - x2 * y1;

		double d = x0 * x0d + y0 * x1d + z0 * x2d;

		Matrix3d n = new Matrix3d(x0d / d, x1d / d, x2d / d,
		                          (y2 * z0 - y0 * z2) / d, (z2 * x0 - z0 * x2) / d, (x2 * y0 - x0 * y2) / d,
		                          (y0 * z1 - y1 * z0) / d, (z0 * x1 - z1 * x0) / d, (x0 * y1 - x1 * y0) / d);

		return n;
	}

	public Matrix3d rotateX(double rotx) {
		double c = Math.cos(rotx);
		double s = Math.sin(rotx);

		// TODO Make inline
		Matrix3d mul = new Matrix3d(1, 0, 0,
		                            0, c, -s,
		                            0, s, c);

		return mul.multiply(this);
	}

	public Matrix3d rotateY(double roty) {
		double c = Math.cos(roty);
		double s = Math.sin(roty);

		// TODO Make inline
		Matrix3d mul = new Matrix3d(c, 0, s,
		                            0, 1, 0,
		                            -s, 0, c);

		return mul.multiply(this);
	}

	public Matrix3d rotateZ(double rotz) {
		double c = Math.cos(rotz);
		double s = Math.sin(rotz);

		Matrix3d mul = new Matrix3d(c, -s, 0,
		                            s, c, 0,
		                            0, 0, 1);

		return mul.multiply(this);
	}

	public Matrix3d flipX() {
		return new Matrix3d(-x0, -x1, -x2,
		                    y0, y1, y2,
		                    z0, z1, z2);
	}

	public Matrix3d flipY() {
		return new Matrix3d(x0, x1, x2,
		                    -y0, -y1, -y2,
		                    z0, z1, z2);
	}

	public Matrix3d flipZ() {
		return new Matrix3d(x0, x1, x2,
		                    y0, y1, y2,
		                    -z0, -z1, -z2);
	}

	private Matrix3d multiply(Matrix3d m) {
		return new Matrix3d(m.x0 * x0 + m.x1 * y0 + m.x2 * z0,
		                    m.x0 * x1 + m.x1 * y1 + m.x2 * z1,
		                    m.x0 * x2 + m.x1 * y2 + m.x2 * z2,

		                    m.y0 * x0 + m.y1 * y0 + m.y2 * z0,
		                    m.y0 * x1 + m.y1 * y1 + m.y2 * z1,
		                    m.y0 * x2 + m.y1 * y2 + m.y2 * z2,

		                    m.z0 * x0 + m.z1 * y0 + m.z2 * z0,
		                    m.z0 * x1 + m.z1 * y1 + m.z2 * z1,
		                    m.z0 * x2 + m.z1 * y2 + m.z2 * z2);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[\n[" +
		       x0 + '\t' + x1 + '\t' + x2 + "]\n[" +
		       y0 + '\t' + y1 + '\t' + y2 + "]\n[" +
		       z0 + '\t' + z1 + '\t' + z2 + "]]\n";
	}
}
