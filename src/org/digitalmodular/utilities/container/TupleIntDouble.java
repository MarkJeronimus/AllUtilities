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
 * A tuple of an integer and double.
 * <p>
 * This class supports sorting, and elements are sorted by the integer first, then by the double.
 *
 * @author Mark Jeronimus
 */
// Created 2013-12-04
public class TupleIntDouble implements Comparable<TupleIntDouble> {
	/**
	 * The freely-modifiable integer value.
	 */
	public int    i;
	/**
	 * The freely-modifiable double value.
	 */
	public double d;

	public TupleIntDouble() {
		i = 0;
		d = 0;
	}

	public TupleIntDouble(int i, double d) {
		this.i = i;
		this.d = d;
	}

	/**
	 * This is the preferred way to clone a {@link TupleIntDouble}.
	 */
	public TupleIntDouble(TupleIntDouble other) {
		i = other.i;
		d = other.d;
	}

	public void set(int i, double d) {
		this.i = i;
		this.d = d;
	}

	public void set(TupleIntDouble other) {
		i = other.i;
		d = other.d;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[" + i + ", " + d + "]";
	}

	@Override
	public int compareTo(TupleIntDouble other) {
		if (i != other.i) {
			return i > other.i ? 1 : -1;
		}
		if (d != other.d) {
			return d > other.d ? 1 : -1;
		}
		return 0;
	}
}
