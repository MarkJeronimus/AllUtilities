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
package org.digitalmodular.utilities.math.interpolation;

import org.digitalmodular.utilities.signal.Wave;

/**
 * An interpolation technique that uses segments of Hermite Spline to approximate an ideal low-pass filter. This is
 * closely related to the Bicubic and Lanczos interpolation techniques.
 *
 * @author Mark Jeronimus
 */
// Created 2013-12-19
public class HermiteSegmentInterpolation extends SignalInterpolation {
	public HermiteSegmentInterpolation() {
		super(2, 3, 3, 3);
		setParameter(0, -1);
		setParameter(1, 0.25);
		// TODO find better parameter and implement/test more degrees.
	}

	@Override
	public double interpolate(Wave waveform, double time) {
		int    i = (int)Math.floor(time); // integer part (of sample index)
		double f = time - i;           // fractional part (of sample index)

		// Retrieve the derivatives.
		double n = parameters[0];
		double m = parameters[1];

		// Calculate the Cubic Hermite Spline basis functions.
		double h00 = hermite00(f);
		double h01 = hermite01(f);
		double h10 = hermite10(f);
		double h11 = hermite11(f);

		// Interpolate with 2*degree pieces of Hermite Spline.
		switch (degree) {
			case 3:
				double f0 = getValueSafe(waveform, i - 2) * (m * h10);
				double f1 = getValueSafe(waveform, i - 1) * (n * h10 + m * h11);
				double f2 = getValueSafe(waveform, i - 0) * (n * h11 + h00);
				double f3 = getValueSafe(waveform, i + 1) * (-n * h10 + h01);
				double f4 = getValueSafe(waveform, i + 2) * (-n * h11 - m * h10);
				double f5 = getValueSafe(waveform, i + 3) * (-m * h11);

				return f0 + f1 + f2 + f3 + f4 + f5;
		}

		throw new IllegalArgumentException("Degree out of range: " + degree);
	}

	/**
	 * Calculate the basis function with points (1; 0) and derivatives (0; 0)
	 */
	private static double hermite00(double x) {
		return 2 * x * x * x - 3 * x * x + 1;
	}

	/**
	 * Calculate the basis function with points (0; 1) and derivatives (0; 0)
	 */
	private static double hermite01(double x) {
		return 3 * x * x - 2 * x * x * x;
	}

	/**
	 * Calculate the basis function with points (0; 0) and derivatives (1; 0)
	 */
	private static double hermite10(double x) {
		return x * x * x - 2 * x * x + x;
	}

	/**
	 * Calculate the basis function with points (0; 0) and derivatives (0; 1)
	 */
	private static double hermite11(double x) {
		return x * x * x - x * x;
	}
}
