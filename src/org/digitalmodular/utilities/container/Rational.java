/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2019 Mark Jeronimus. All Rights Reversed.
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

import org.digitalmodular.utilities.NumberUtilities;

/**
 * @author Mark Jeronimus
 */
// Created 2013-08-17
public class Rational extends Vector2l {
	public Rational(long value) {
		x = value;
		y = 1;
	}

	public Rational(long numerator, long denominator) {
		x = numerator;
		y = denominator;
		normalize();
	}

	public Rational(Rational o) {
		x = o.x;
		y = o.y;
	}

	public double doubleValue() {
		return x / (double)y;
	}

	public void normalize() {
		// Infinite?
		if (y == 0) {
			x = Long.signum(x);
			return;
		}

		// Alternate negative?
		if (y < 0) {
			x = -x;
			y = -y;
		}

		long d = NumberUtilities.gcd(Math.abs(x), y);
		x /= d;
		y /= d;
	}

	public void add(long v) {
		x += v * y;
		normalize();
	}

	public void add(Rational o) {
		x = x * o.y + o.x * y;
		y *= o.y;
		normalize();
	}

	public void sub(long v) {
		x -= v * y;
		normalize();
	}

	public void sub(Rational o) {
		x = x * o.y - o.x * y;
		y *= o.y;
		normalize();
	}

	public void mul(long v) {
		x *= v;
		normalize();
	}

	public void mul(Rational o) {
		x *= o.x;
		y *= o.y;
		normalize();
	}

	public void div(long v) {
		y *= v;
		normalize();
	}

	public void div(Rational o) {
		x *= o.y;
		y *= o.x;
		normalize();
	}

	public void beatPeriod(Rational other) {
		long px = x * other.x;
		long py = y * other.y;
		long dx = x * other.y - other.x * y;
		x = px * py;
		y = py * dx;
		normalize();
	}

	public void rabs() {
		if (Math.abs(x) < y) {
			long temp = x;
			x = y;
			y = temp;

			// Quick normalize
			if (y < 0) {
				x = -x;
				y = -y;
			}
		}
	}

	@Override
	public String toString() {
		return x + "/" + y + " (" + doubleValue() + ")";
	}
}
