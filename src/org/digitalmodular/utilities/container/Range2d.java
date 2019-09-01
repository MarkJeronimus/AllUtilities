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

/**
 * A mutable <u>Range</u> with <u>2</u> degrees of freedom (1 dimension) and <u>d</u>ouble precision. It defines a begin
 * and end value where <tt>begin &lt; end</tt> is always true. If a situation arises where this condition would be
 * violated, the values will be swapped, and the user is always informed of this. The values cannot be NaN.
 *
 * @author Mark Jeronimus
 */
public class Range2d {
	private double begin;
	private double end;

	/**
	 * Creates a new range of [0,0].
	 */
	public Range2d() {
		begin = 0;
		end = 0;
	}

	/**
	 * Creates a new range with specified endpoints.
	 *
	 * @throws IllegalArgumentException when begin &gt; end. If thrown, split it up in empty constructor and
	 *                                  <tt>set</tt> method and handle swapping properly.
	 */
	public Range2d(double begin, double end) throws IllegalArgumentException {
		this.begin = begin;
		this.end = end;
		if (normalize()) {
			throw new IllegalArgumentException("Begin > end not possible for constructor.");
		}
	}

	/**
	 * Creates a clone.
	 */
	public Range2d(Range2d other) {
		begin = other.begin;
		end = other.end;
	}

	/**
	 * Returns the begin position of the range.
	 */
	public double begin() {
		return begin;
	}

	/*
	 * Returns the end position of the range.
	 */
	public double end() {
		return end;
	}

	/**
	 * Returns the span of the range.
	 * <p>
	 * This does the same as: <tt>(range.end() - range.begin())</tt>.
	 */
	public double getSpan() {
		return end - begin;
	}

	/**
	 * Returns true if the range is zero and the range is effectively nonexistent.
	 */
	public boolean isEmpty() {
		return end == begin;
	}

	/**
	 * Sets the begin and end of the range to new positions.
	 * <p>
	 * If the values violate the <tt>begin &lt; end</tt> requirement, they will be swapped.
	 *
	 * @return true if the begin and end were swapped in order to meet the criteria.
	 * @throws IllegalArgumentException if on of the specified values is NaN.
	 */
	public boolean set(double begin, double end) throws IllegalArgumentException {
		if (Double.isNaN(begin) || Double.isNaN(end)) {
			throw new IllegalArgumentException(
					"Setting range endpoints to NaN inevitably violates the 'begin <= end' requirement.");
		}

		this.begin = begin;
		this.end = end;

		return normalize();
	}

	/**
	 * Sets the current begin of the range to a new position.
	 * <p>
	 * This does the same as: <tt>range.set(begin, range.end())</tt>. If the value violates the <tt>begin &lt; end</tt>
	 * requirement, they will be swapped and the new end of the range will be at the specified position.
	 *
	 * @return true if the begin and end were swapped in order to meet the criteria.
	 * @throws IllegalArgumentException if the specified value is NaN.
	 */
	public boolean setBegin(double begin) throws IllegalArgumentException {
		if (Double.isNaN(begin)) {
			throw new IllegalArgumentException(
					"Setting range endpoints to NaN inevitably violates the 'begin <= end' requirement.");
		}

		this.begin = begin;

		return normalize();
	}

	/**
	 * Sets the current end of the range to a new position.
	 * <p>
	 * This does the same as: <tt>range.set(range.begin(), end)</tt>. If the value violates the <tt>begin &lt; end</tt>
	 * requirement, they will be swapped and the new begin of the range will be at the specified position.
	 *
	 * @return true if the begin and end were swapped in order to meet the criteria.
	 * @throws IllegalArgumentException if the specified value is NaN.
	 */
	public boolean setEnd(double end) throws IllegalArgumentException {
		if (Double.isNaN(end)) {
			throw new IllegalArgumentException(
					"Setting range endpoints to NaN inevitably violates the 'begin <= end' requirement.");
		}

		this.end = end;

		return normalize();
	}

	/**
	 * Translates the range by adding a specified amount to both the begin and end.
	 * <p>
	 * This almost does the same as: <tt>range.set(range.begin() + amount, range.end() + amount)</tt>, except for some
	 * additional overflow checking. If the range ends up with a smaller span than before (because one of the sides is
	 * at &plusmn;Infinity), an exception is thrown.
	 *
	 * @throws IllegalArgumentException if one of the sides would exceed &plusmn;Infinity
	 */
	public void drag(double amount) throws IllegalArgumentException {
		if (end != Double.POSITIVE_INFINITY && end + amount == Double.POSITIVE_INFINITY) {
			throw new IllegalArgumentException(
					"This drag would change the span as the right side would exceed +Infinity");
		}
		if (begin != -Double.POSITIVE_INFINITY && begin + amount == -Double.POSITIVE_INFINITY) {
			throw new IllegalArgumentException(
					"This drag would change the span as the left side would exceed -Infinity");
		}

		begin += amount;
		end += amount;
	}

	private boolean normalize() {
		if (begin > end) {
			double temp = begin;
			begin = end;
			end = temp;
			return true;
		}

		return false;
	}

	/**
	 * Clamp a given value to fall within this range.
	 *
	 * @param value the value to clamp.
	 */
	public double clamp(double value) {
		return Math.min(end, Math.max(begin, value));
	}

	/**
	 * Limits the begin and end values of this range to the specified min and max.
	 *
	 * @param min the lower bound. if begin is lower than min, it will be set to min
	 * @param max the upper bound. if end is lower than max, it will be set to max
	 */
	public void limit(double min, double max) {
		if (min > max) {
			throw new IllegalArgumentException("The min should be smaller or equal to the max");
		}

		if (begin < min) {
			begin = min;
		}
		if (end > max) {
			end = max;
		}
	}
}
