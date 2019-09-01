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

import net.jcip.annotations.NotThreadSafe;

import org.digitalmodular.utilities.NumberUtilities;

/**
 * Collects {@code double} values and constructs a span that goes from the minimum value to the maximum value.
 *
 * @author Mark Jeronimus
 */
@NotThreadSafe
public final class SpanBuilder {
	private final Span defaultSpan;

	private double min = Double.POSITIVE_INFINITY;
	private double max = Double.NEGATIVE_INFINITY;

	/**
	 * @param defaultSpan the span to return when nothing is collected yet.
	 */
	public SpanBuilder(Span defaultSpan) {
		this.defaultSpan = defaultSpan;
	}

	public void collect(double value) {
		if (NumberUtilities.isDegenerate(value))
			return;

		if (min > value)
			min = value;

		if (max < value)
			max = value;
	}

	public Span get() {
		try {
			return Span.getInstance(min, max);
		} catch (IllegalArgumentException ignored) {
			return defaultSpan;
		}
	}
}
