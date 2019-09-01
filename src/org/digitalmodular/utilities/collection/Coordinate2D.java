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
package org.digitalmodular.utilities.collection;

import java.awt.geom.Point2D;

/**
 * @author Mark Jeronimus
 */
// Created 2016-03-25
public class Coordinate2D extends Point2D {
	public static final Coordinate2D ZERO = new Coordinate2D(0, 0);

	private final double x;
	private final double y;

	public Coordinate2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public static Coordinate2D valueOf(double x, double y) {
		if (x == 0 & y == 0)
			return ZERO;
		return new Coordinate2D(x, y);
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public void setLocation(double x, double y) {
		throw new UnsupportedOperationException("Immutable");
	}

	//
	// Calculation functions
	//

	@Override
	public double distance(double x, double y) {
		return Math.hypot(this.x - x, this.y - y);
	}

	public double distance(Coordinate2D other) {
		return Math.hypot(x - other.x, y - other.y);
	}

	public double distanceSquared(Coordinate2D other) {
		double x = this.x - other.x;
		double y = this.y - other.y;
		return x * x + y * y;
	}

	//
	// Binary functions
	//

	public Coordinate2D add(double x, double y) {
		return valueOf(this.x + x,
		               this.y + y);
	}

	public Coordinate2D add(Point2D other) {
		return valueOf(x + other.getX(),
		               y + other.getY());
	}

	public Coordinate2D add(Coordinate2D other) {
		return valueOf(x + other.x,
		               y + other.y);
	}

	//
	// Ternary functions
	//

	public Coordinate2D addScaled(Coordinate2D other, double factor) {
		return valueOf(x + other.x * factor,
		               y + other.y * factor);
	}

	public Coordinate2D lerp(Coordinate2D other, double factor) {
		return valueOf(x * (1 - factor) + other.x * factor,
		               y * (1 - factor) + other.y * factor);
	}

	//
	//
	//

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
