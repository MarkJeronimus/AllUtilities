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
package org.digitalmodular.utilities.math.interpolation;

import org.digitalmodular.utilities.signal.Wave;

/**
 * An interpolation technique that interpolates linearly between neighboring values.
 *
 * @author Mark Jeronimus
 */
// Created 2014-02-12
public class LinearInterpolation extends SignalInterpolation {
	public LinearInterpolation() {
		super(0, 0, 0, 0);
	}

	@Override
	public double interpolate(Wave waveform, double time) {
		int    i = (int)Math.floor(time); // integer part (of sample index)
		double f = time - i;           // fractional part (of sample index)

		// Interpolate with 2*degree pieces of Hermite Spline.
		double f0 = getValueSafe(waveform, i);
		double f1 = getValueSafe(waveform, i + 1);

		return f0 + (f1 - f0) * f;
	}
}
