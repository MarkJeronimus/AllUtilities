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
 * @author Mark Jeronimus
 */
// Created 2005-10-03
public class Vector4d implements Comparable<Vector4d> {
	public double x;
	public double y;
	public double z;
	public double w;

	/** Create a zero-length vector. */
	public Vector4d() {
		x = 0;
		y = 0;
		z = 0;
		w = 0;
	}

	/**
	 * Create a defined Vector4d
	 */
	public Vector4d(double x, double y, double z, double t) {
		this.x = x;
		this.y = y;
		this.z = z;
		w = t;
	}

	public void set(Vector4d o) {
		x = o.x;
		y = o.y;
		z = o.z;
		w = o.w;
	}

	public void set(double x, double y, double z, double t) {
		this.x = x;
		this.y = y;
		this.z = z;
		w = t;
	}

	/**
	 * Returns a Vector added to this Vector4d
	 */
	public Vector4d add(Vector4d o) {
		return new Vector4d(x + o.x, y + o.y, z + o.z, w + o.w);
	}

	public void addSelf(Vector4d o) {
		x += o.x;
		y += o.y;
		z += o.z;
		w += o.w;
	}

	/**
	 * Returns the subtraction of a supplied Vector4d from this Vector4d
	 */
	public Vector4d sub(Vector4d o) {
		return new Vector4d(x - o.x, y - o.y, z - o.z, w - o.w);
	}

	/**
	 * Returns this Vector4d multiplied by a factor
	 */
	public Vector4d mul(double factor) {
		return new Vector4d(x * factor, y * factor, z * factor, w * factor);
	}

	/**
	 * Multiply this Vector4d with a factor
	 */
	public void mulSelf(double factor) {
		x *= factor;
		y *= factor;
		z *= factor;
	}

	/**
	 * Returns the addition of this Vector4d and a scaled Vector4d
	 */
	public Vector4d addScaled(Vector4d o, double scale) {
		return new Vector4d(x + o.x * scale, y + o.y * scale, z + o.z * scale, w + o.w * scale);
	}

	/**
	 * Returns the inner product (sometimes called the dot product) of this Vector4d and the supplied Vector4d
	 */
	public double innerProduct(Vector4d o) {
		return x * o.x + y * o.y + z * o.z + w * o.w;
	}

	/**
	 * Returns the vectorized multiplication of this Vector4d and the supplied Vector4d
	 */
	public Vector4d vectorProduct(Vector4d o) {
		return new Vector4d(x * o.x, y * o.y, z * o.z, w * o.w);
	}

	public double length() {
		return Math.sqrt(x * x + y * y + z * z + w * w);
	}

	public double distanceTo(double x, double y, double z, double t) {
		x -= this.x;
		y -= this.y;
		z -= this.z;
		t -= w;
		return Math.sqrt(x * x + y * y + z * z + t * t);
	}

	public double distanceTo(Vector4d o) {
		double x = this.x - o.x;
		double y = this.y - o.y;
		double z = this.z - o.z;
		double t = w - o.w;
		return Math.sqrt(x * x + y * y + z * z + t * t);
	}

	/**
	 * Returns the distance squared of the Vector4d
	 */
	public double mod() {
		return x * x + y * y + z * z + w * w;
	}

	public Vector4d normalize() {
		double determinant = Math.sqrt(x * x + y * y + z * z + w * w);
		if (determinant == 0) {
			return new Vector4d();
		}
		return new Vector4d(x / determinant, y / determinant, z / determinant, w / determinant);
	}

	public void normalizeSelf() {
		double det = Math.sqrt(x * x + y * y + z * z + w * w);
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

	public Vector4d invert() {
		return new Vector4d(-x, -y, -z, -w);
	}

	public void minimumSelf(Vector4d o) {
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

	public void maximumSelf(Vector4d o) {
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
	public int compareTo(Vector4d o) {
		int diff = Double.compare(w, o.w);
		if (diff != 0) {
			return diff;
		}
		diff = Double.compare(z, o.z);
		if (diff != 0) {
			return diff;
		}
		diff = Double.compare(y, o.y);
		if (diff != 0) {
			return diff;
		}
		return Double.compare(x, o.x);
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ", " + w + ")";
	}
}
