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

package org.digitalmodular.utilities.math.rangeconverter;

import org.digitalmodular.utilities.NumberUtilities;

/**
 * Converts a value (in the range 0..1) to a linear domain.
 * <p>
 * Useful where the domain value should linearly depend on the input value or where the derivative should be constant.
 * <p>
 * {@code Domain} is a linear function of {@code value}; regular increments in {@code value} cause regular increments in
 * {@code domain}:
 * <pre>
 * domain = a * value + b;
 * value = (domain - b) / a;
 * </pre>
 *
 * @author Mark Jeronimus
 */
// Created 2016-03-25
public class LinearRangeConverter implements RangeConverter {
	private final double a;
	private final double b;

	private final double max;

	protected LinearRangeConverter(double a, double b) {
		this.a = a;
		this.b = b;

		max = a + b;
	}

	public static LinearRangeConverter fromAB(double a, double b) {
		if (NumberUtilities.isDegenerate(a))
			throw new IllegalArgumentException("a is degenerate:" + a);
		if (NumberUtilities.isDegenerate(b))
			throw new IllegalArgumentException("b is degenerate:" + b);
		if (a <= 0)
			throw new IllegalArgumentException("a must be positive: " + a);

		return new LinearRangeConverter(a, b);
	}

	public static LinearRangeConverter fromMinMax(double max) {
		return fromMinMax(0, max);
	}

	public static LinearRangeConverter fromMinMax(double min, double max) {
		if (NumberUtilities.isDegenerate(min))
			throw new IllegalArgumentException("min is degenerate:" + min);
		if (NumberUtilities.isDegenerate(max))
			throw new IllegalArgumentException("max is degenerate:" + min);
		if (min >= max)
			throw new IllegalArgumentException("min should be less than max: " + min + ", " + max);

		double a = max - min;

		if (a == Double.POSITIVE_INFINITY)
			throw new IllegalArgumentException("difference between min and max too large: " + min + ", " + max);

		return new LinearRangeConverter(a, min);
	}

	@Override
	public double toDomain(double value) {
		if (value < 0 || value > 1)
			new IllegalArgumentException("value must be in the range [0, 1]: " + value);
		return a * value + b;
	}

	@Override
	public double fromDomain(double domain) {
		if (domain < b || domain > max)
			new IllegalArgumentException("value must be in the range [" + b + ", " + max + "]: " + domain);
		return (domain - b) / a;
	}
}
