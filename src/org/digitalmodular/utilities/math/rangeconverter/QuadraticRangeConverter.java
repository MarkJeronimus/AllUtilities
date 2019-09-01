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

package org.digitalmodular.utilities.math.rangeconverter;

import org.digitalmodular.utilities.NumberUtilities;

/**
 * Converts a value (in the range 0..1) to a quadratic domain.
 * <p>
 * Useful where the adjustment precision or derivative should linearly depend on the input value.
 * <p>
 * {@code Domain} is a linear function of {@code value}; regular increments in {@code value} cause regular increments in
 * {@code domain}:
 * <pre>
 * domain = a * value * value + b * value + c;
 * value = (-b + sqrt(b * b + 4 * a * (domain - c))) / (2 * a);
 * </pre>
 *
 * @author Mark Jeronimus
 */
// Created 2016-03-26
public class QuadraticRangeConverter implements RangeConverter {
	private final double a;
	private final double b;
	private final double c;

	private final double max;

	private QuadraticRangeConverter(double a, double b, double c) {
		this.a = a;
		this.b = b;
		this.c = c;

		max = a + b + c;
	}

	public static QuadraticRangeConverter fromABC(double a, double b, double c) {
		if (NumberUtilities.isDegenerate(a))
			throw new IllegalArgumentException("a is degenerate:" + a);
		if (NumberUtilities.isDegenerate(b))
			throw new IllegalArgumentException("b is degenerate:" + b);
		if (NumberUtilities.isDegenerate(c))
			throw new IllegalArgumentException("c is degenerate:" + c);
		if (a <= 0)
			throw new IllegalArgumentException("a must be positive: " + a);
		if (b < 0)
			throw new IllegalArgumentException("b must be non-negative: " + b);
		if (a == 0 && b == 0)
			throw new IllegalArgumentException("One of a or b must be non-negative: " + a + ", " + b);

		return new QuadraticRangeConverter(a, b, c);
	}

	public static QuadraticRangeConverter fromMidMax(double mid, double max) {
		return fromMinMidMax(0, mid, max);
	}

	public static QuadraticRangeConverter fromMinMidMax(double min, double mid, double max) {
		if (NumberUtilities.isDegenerate(min))
			throw new IllegalArgumentException("min is degenerate:" + min);
		if (NumberUtilities.isDegenerate(mid))
			throw new IllegalArgumentException("mid is degenerate:" + mid);
		if (NumberUtilities.isDegenerate(max))
			throw new IllegalArgumentException("max is degenerate:" + max);
		if (min >= mid)
			throw new IllegalArgumentException("min should be less than mid: " + min + ", " + mid);
		if (mid >= max)
			throw new IllegalArgumentException("mid should be less than max: " + mid + ", " + max);
		if (mid - min >= max - mid)
			throw new IllegalArgumentException("mid-min should be less than max-mid: " + min + ", " + mid + ", " +
			                                   max);

		double a = 2 * (min + max - 2 * mid);
		double b = 2 * (mid - min - a / 4);

		if (b < 0)
			throw new IllegalArgumentException("This combination causes negative b: " + b);

		return new QuadraticRangeConverter(a, b, min);
	}

	@Override
	public double toDomain(double value) {
		if (value < 0 || value > 1)
			new IllegalArgumentException("value must be in the range [0, 1]: " + value);
		return (a * value + b) * value + c;
	}

	@Override
	public double fromDomain(double domain) {
		if (domain < b || domain > max)
			new IllegalArgumentException("value must be in the range [" + c + ", " + max + "]: " + domain);
		return (Math.sqrt(b * b + 4 * a * (domain - c)) - b) / (2 * a);
	}
}
