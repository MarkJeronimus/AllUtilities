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
package org.digitalmodular.utilities.gui.gradient;

import org.digitalmodular.utilities.color.Color4f;

/**
 * @author Mark Jeronimus
 */
// Created 2009-04-05
public class TransparentGradient implements Gradient {
	private static final int MAX_GRADIENT_ARRAY_SIZE = 5000;

	private static final int GRADIENT_SIZE = 256;

	private float[] fractions;

	private int gradientOverflow;

	private float[] intervals;

	private int gradientLength;

	private int[] gradient;

	public TransparentGradient(float[] fractions, Color4f[] colors) {
		this.fractions = fractions;

		gradientOverflow = colors[colors.length - 1].toInteger();

		intervals = new float[fractions.length - 1];
		for (int i = 0; i < intervals.length; i++) {
			intervals[i] = fractions[i + 1] - fractions[i];
		}

		calculateGradientFractions(colors);
	}

	private final void calculateGradientFractions(Color4f[] colors) {
		int n = intervals.length;

		// Find smallest interval
		float Imin = 1;
		for (int i = 0; i < n; i++) {
			if (Imin > intervals[i]) {
				Imin = intervals[i];
			}
		}

		calculateSingleArrayGradient(colors, Imin);

	}

	private void calculateSingleArrayGradient(Color4f[] colors, float Imin) {
		gradientLength = (int)(TransparentGradient.GRADIENT_SIZE / Imin + 0.5);
		if (gradientLength > TransparentGradient.MAX_GRADIENT_ARRAY_SIZE) {
			gradientLength = TransparentGradient.MAX_GRADIENT_ARRAY_SIZE;
		}

		gradient = new int[gradientLength + 1];
		int part = 0;
		for (int i = 0; i <= gradientLength; i++) {
			// Fraction of the gradient.
			float f = (float)i / gradientLength;

			// Select interval.
			while (f > fractions[part + 1]) {
				part++;
			}

			// Fraction of the current interval.
			float p = (f - fractions[part]) / intervals[part];

			// 2 colors to interpolate
			Color4f c1 = colors[part];
			Color4f c2 = colors[part + 1];

			gradient[i] = interpolate(c1, c2, p);
		}
	}

	private static int interpolate(Color4f c1, Color4f c2, float p) {
		return (int)((c1.a + (c2.a - c1.a) * p) * 255 + 0.5) << 24 //
		       | (int)((c1.r + (c2.r - c1.r) * p) * 255 + 0.5) << 16 //
		       | (int)((c1.g + (c2.g - c1.g) * p) * 255 + 0.5) << 8//
		       | (int)((c1.b + (c2.b - c1.b) * p) * 255 + 0.5);
	}

	@Override
	public boolean hasTransparency() {
		return true;
	}

	@Override
	public int getLength() {
		return gradientLength;
	}

	@Override
	public int getInt(int index) {
		return gradient[index];
	}

	@Override
	public int get(double position) {
		return gradient[(int)(position * gradientLength + 0.5)];
	}

	@Override
	public int getOverflow() {
		return gradientOverflow;
	}
}
