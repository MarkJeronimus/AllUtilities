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
package org.digitalmodular.utilities.container;

/**
 * A homogeneous matrix in 3D.
 *
 * @author Mark Jeronimus
 */
// Created 2005-10-21
public class Matrix4d {

	public double x0;
	public double x1;
	public double x2;
	public double x3;
	public double y0;
	public double y1;
	public double y2;
	public double y3;
	public double z0;
	public double z1;
	public double z2;
	public double z3;

	public Matrix4d() {
		x0 = y1 = z2 = 1;
		x1 = x2 = x3 = y0 = y2 = y3 = z0 = z1 = z3 = 0;
	}

	public Matrix4d(double x0, double x1, double x2, double x3, double y0, double y1, double y2, double y3, double z0,
	                double z1,
	                double z2, double z3) {
		this.x0 = x0;
		this.x1 = x1;
		this.x2 = x2;
		this.x3 = x3;
		this.y0 = y0;
		this.y1 = y1;
		this.y2 = y2;
		this.y3 = y3;
		this.z0 = z0;
		this.z1 = z1;
		this.z2 = z2;
		this.z3 = z3;
	}

	public static Matrix4d makeRotationMatrix(Vector3d v, double a) {
		// Using Rodriques' rotation formula (rewritten).
		double c = Math.cos(a);
		double s = Math.sin(a);
		a = 1 - c;

		double xx = v.x * v.x;
		double xy = v.x * v.y;
		double xz = v.x * v.z;
		double yy = v.y * v.y;
		double yz = v.y * v.z;
		double zz = v.z * v.z;

		return new Matrix4d(//
		                    xx + (1 - xx) * c, //
		                    xy * a - v.z * s, //
		                    xz * a + v.y * s, //
		                    0, //
		                    xy * a + v.z * s, //
		                    yy + (1 - yy) * c, //
		                    yz * a - v.x * s, //
		                    0, //
		                    xz * a - v.y * s, //
		                    yz * a + v.x * s, //
		                    zz + (1 - zz) * c, //
		                    0);
	}

	public void setIdentity() {
		x0 = y1 = z2 = 1;
		x1 = x2 = x3 = y0 = y2 = y3 = z0 = z1 = z3 = 0;
	}

	public void set(double x0, double x1, double x2, double x3, double y0, double y1, double y2, double y3, double z0,
	                double z1,
	                double z2, double z3) {
		this.x0 = x0;
		this.x1 = x1;
		this.x2 = x2;
		this.x3 = x3;
		this.y0 = y0;
		this.y1 = y1;
		this.y2 = y2;
		this.y3 = y3;
		this.z0 = z0;
		this.z1 = z1;
		this.z2 = z2;
		this.z3 = z3;
	}

	public void set(Matrix4d m) {
		x0 = m.x0;
		x1 = m.x1;
		x2 = m.x2;
		x3 = m.x3;
		y0 = m.y0;
		y1 = m.y1;
		y2 = m.y2;
		y3 = m.y3;
		z0 = m.z0;
		z1 = m.z1;
		z2 = m.z2;
		z3 = m.z3;
	}

	/**
	 * Translate (move) the non-inverse matrix.
	 */
	public void translate(double x, double y, double z) {
		x3 += x;
		y3 += y;
		z3 += z;
	}

	/**
	 * Scale the non-inverse matrix.
	 */
	public void scale(double f) {
		x0 *= f;
		x1 *= f;
		x2 *= f;
		x3 *= f;
		y0 *= f;
		y1 *= f;
		y2 *= f;
		y3 *= f;
		z0 *= f;
		z1 *= f;
		z2 *= f;
		z3 *= f;
	}

	/**
	 * Scale the non-inverse matrix.
	 */
	public void scale(double x, double y, double z) {
		x0 *= x;
		x1 *= x;
		x2 *= x;
		x3 *= x;
		y0 *= y;
		y1 *= y;
		y2 *= y;
		y3 *= y;
		z0 *= z;
		z1 *= z;
		z2 *= z;
		z3 *= z;
	}

	public Matrix4d recip() {
		double determinant = 1 /
		                     (x0 * y1 * z2 - x0 * z1 * y2 - y0 * x1 * z2 + y0 * z1 * x2 + z0 * x1 * y2 - z0 * y1 * x2);

		double x0b = x0;
		double x1b = x1;
		double x2b = x2;
		double y0b = y0;
		double y1b = y1;
		double y2b = y2;
		double z0b = z0;
		double z1b = z1;
		double z2b = z2;

		double x0 = (y1b * z2b - z1b * y2b) * determinant;
		double x1 = (z1b * x2b - x1b * z2b) * determinant;
		double x2 = (x1b * y2b - y1b * x2b) * determinant;

		double y0 = (y2b * z0b - z2b * y0b) * determinant;
		double y1 = (z2b * x0b - x2b * z0b) * determinant;
		y2b = (x2b * y0b - y2b * x0b) * determinant;

		double z0 = (y0b * z1b - z0b * y1b) * determinant;
		z1b = (z0b * x1b - x0b * z1b) * determinant;
		z2b = (x0b * y1b - y0b * x1b) * determinant;

		return new Matrix4d( //
		                     x0, x1, x2, -(x0 * x3 + x1 * y3 + x2 * z3), //
		                     y0, y1, y2b, -(y0 * x3 + y1 * y3 + y2b * z3), //
		                     z0, z1b, z2b, -(z0 * x3 + z1b * y3 + z2b * z3));
	}

	// Speed classification: Frame
	public void recipSelf() {
		double determinant = 1 /
		                     (x0 * y1 * z2 - x0 * z1 * y2 - y0 * x1 * z2 + y0 * z1 * x2 + z0 * x1 * y2 - z0 * y1 * x2);

		double x0 = this.x0;
		double x1 = this.x1;
		double x2 = this.x2;
		double y0 = this.y0;
		double y1 = this.y1;
		double y2 = this.y2;
		double z0 = this.z0;
		double z1 = this.z1;
		double z2 = this.z2;

		this.x0 = (y1 * z2 - z1 * y2) * determinant;
		this.x1 = (z1 * x2 - x1 * z2) * determinant;
		this.x2 = (x1 * y2 - y1 * x2) * determinant;

		this.y0 = (y2 * z0 - z2 * y0) * determinant;
		this.y1 = (z2 * x0 - x2 * z0) * determinant;
		this.y2 = (x2 * y0 - y2 * x0) * determinant;

		this.z0 = (y0 * z1 - z0 * y1) * determinant;
		this.z1 = (z0 * x1 - x0 * z1) * determinant;
		this.z2 = (x0 * y1 - y0 * x1) * determinant;

		x3 = -(this.x0 * x3 + this.x1 * y3 + this.x2 * z3);
		y3 = -(this.y0 * x3 + this.y1 * y3 + this.y2 * z3);
		z3 = -(this.z0 * x3 + this.z1 * y3 + this.z2 * z3);
	}

	public double getX(double u, double v, double w) {
		return x0 * u + x1 * v + x2 * w + x3;
	}

	public double getY(double u, double v, double w) {
		return y0 * u + y1 * v + y2 * w + y3;
	}

	public double getZ(double u, double v, double w) {
		return z0 * u + z1 * v + z2 * w + z3;
	}

	public Vector3d mul(Vector3d v) {
		return new Vector3d(x0 * v.x + x1 * v.y + x2 * v.z + x3, //
		                    y0 * v.x + y1 * v.y + y2 * v.z + y3, //
		                    z0 * v.x + z1 * v.y + z2 * v.z + z3);
	}

	public Matrix4d mul(Matrix4d m) {
		return new Matrix4d( //
		                     x0 * m.x0 + x1 * m.y0 + x2 * m.z0, //
		                     x0 * m.x1 + x1 * m.y1 + x2 * m.z1, //
		                     x0 * m.x2 + x1 * m.y2 + x2 * m.z2, //
		                     x0 * m.x3 + x1 * m.y3 + x2 * m.z3 + x3, //

		                     y0 * m.x0 + y1 * m.y0 + y2 * m.z0, //
		                     y0 * m.x1 + y1 * m.y1 + y2 * m.z1, //
		                     y0 * m.x2 + y1 * m.y2 + y2 * m.z2, //
		                     y0 * m.x3 + y1 * m.y3 + y2 * m.z3 + y3, //

		                     z0 * m.x0 + z1 * m.y0 + z2 * m.z0, //
		                     z0 * m.x1 + z1 * m.y1 + z2 * m.z1, //
		                     z0 * m.x2 + z1 * m.y2 + z2 * m.z2, //
		                     z0 * m.x3 + z1 * m.y3 + z2 * m.z3 + z3);
	}

	public void mulSelf(Matrix4d m) {
		double t0;
		double t1;

		x3 = x0 * m.x3 + x1 * m.y3 + x2 * m.z3 + x3;
		t0 = x0 * m.x0 + x1 * m.y0 + x2 * m.z0;
		t1 = x0 * m.x1 + x1 * m.y1 + x2 * m.z1;
		x2 = x0 * m.x2 + x1 * m.y2 + x2 * m.z2;
		x0 = t0;
		x1 = t1;

		y3 = y0 * m.x3 + y1 * m.y3 + y2 * m.z3 + y3;
		t0 = y0 * m.x0 + y1 * m.y0 + y2 * m.z0;
		t1 = y0 * m.x1 + y1 * m.y1 + y2 * m.z1;
		y2 = y0 * m.x2 + y1 * m.y2 + y2 * m.z2;
		y0 = t0;
		y1 = t1;

		z3 = z0 * m.x3 + z1 * m.y3 + z2 * m.z3 + z3;
		t0 = z0 * m.x0 + z1 * m.y0 + z2 * m.z0;
		t1 = z0 * m.x1 + z1 * m.y1 + z2 * m.z1;
		z2 = z0 * m.x2 + z1 * m.y2 + z2 * m.z2;
		z0 = t0;
		z1 = t1;
	}

	@Override
	public String toString() {
		return super.getClass().getSimpleName() + "[\n[" + //
		       x0 + '\t' + //
		       x1 + '\t' + //
		       x2 + '\t' + //
		       x3 + "]\n[" + //
		       y0 + '\t' + //
		       y1 + '\t' + //
		       y2 + '\t' + //
		       y3 + "]\n[" + //
		       z0 + '\t' + //
		       z1 + '\t' + //
		       z2 + '\t' + //
		       z3 + "]\n[0.0\t0.0\t0.0\t1.0]\n]";
	}
}
