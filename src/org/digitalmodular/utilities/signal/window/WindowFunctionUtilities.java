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
package org.digitalmodular.utilities.signal.window;

import org.digitalmodular.utilities.NumberUtilities;

/**
 * @author Mark Jeronimus
 */
// Created 2016-03-27
public enum WindowFunctionUtilities {
	;

	public static void sampleWindow(double[] window, int length, WindowSymmetryMode symmetryMode) {
		for (int i = 0; i < length; i++)
			window[i] = symmetryMode.getValueAt(i, length);
	}

	public static void taperWindow(double[] window, int length, WindowTaperMode taperMode, double taper) {
		for (int i = 0; i < length; i++)
			window[i] = taperMode.getValueAt(window[i], taper);
	}

	public static void makeWindow(double[] window, int length, WindowFunction windowFunction) {
		for (int i = 0; i < length; i++)
			window[i] = windowFunction.getValueAt(window[i]);
	}

	public static void lerpedBiPower(double[] window, int length, double power, double invPower, double powerLerp) {
		for (int i = 0; i < length; i++) {
			double powerInvPower = 1 - Math.pow(1 - Math.pow(window[i], power), invPower);
			double invPowerPower = Math.pow(1 - Math.pow(1 - window[i], power), invPower);
			window[i] = NumberUtilities.lerp(powerInvPower, invPowerPower, powerLerp);
		}
	}

	public static void topScale(double[] window, int length, double topScale) {
		for (int i = 0; i < length; i++)
			window[i] = 1 - (1 - window[i]) * topScale;
	}

	public static void normalize(double[] window, int length, WindowNormalizationMode normalization) {
		double scale;
		switch (normalization) {
			case NONE:
				return;
			case PEAK:
				double max = Double.NEGATIVE_INFINITY;
				for (int i = 0; i < length; i++)
					if (max < window[i])
						max = window[i];

				scale = max;
				break;
			case CENTER:
				scale = window[length / 2];
				break;
			case AREA:
				double sum = 0;
				for (int i = 0; i < length; i++)
					sum += window[i];

				scale = sum / length;
				break;
			default:
				throw new UnsupportedOperationException("normalizationMode: " + normalization);
		}

		for (int i = 0; i < length; i++)
			window[i] /= scale;
	}
}
