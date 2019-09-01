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

import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;

/**
 * @author Mark Jeronimus
 */
// Created 2009-04-04
public class RadialGradientPaint extends GradientPaintContext implements Paint {
	private float centerX;
	private float centerY;
	private float radius;

	/**
	 * Length of a square distance interval in the lookup table
	 */
	private float invSqStepFloat;

	/**
	 * Used to limit the size of the square root lookup table
	 */
	private int MAX_PRECISION = 256;

	/**
	 * Square root lookup table
	 */
	private int sqrtLutFixed[] = new int[MAX_PRECISION];

	public RadialGradientPaint(Gradient gradient) {
		super();

		setGradient(gradient);
	}

	/*
	 * After using this constructor, a call to setGradient is required before using this.
	 */
	protected RadialGradientPaint() {}

	@Override
	public void setGradient(Gradient gradient) {
		super.setGradient(gradient);

		calculateFixedPointSqrtLookupTable();
	}

	public void set(float cx, float cy, float r) {
		centerX = cx;
		centerY = cy;
		radius = r;
	}

	/**
	 * Build square root lookup table
	 */
	private void calculateFixedPointSqrtLookupTable() {
		float sqStepFloat;
		sqStepFloat = gradient.getLength() * gradient.getLength() / (float)(MAX_PRECISION - 2);

		// The last two values are the same so that linear square root
		// interpolation can happen on the maximum reachable element in the
		// lookup table (precision-2)
		int i;
		for (i = 0; i < MAX_PRECISION - 1; i++) {
			sqrtLutFixed[i] = (int)Math.sqrt(i * sqStepFloat);
		}
		sqrtLutFixed[i] = sqrtLutFixed[i - 1];
		invSqStepFloat = 1 / sqStepFloat;
	}

	@Override
	protected void fillRaster(int x, int y, int w, int h, int pixels[], int offset, int skip) {
		final float radiusToGradient = gradient.getLength() / radius;
		final int   gradientLengthSq = gradient.getLength() * gradient.getLength();

		// incremental change in dX
		final float dgx = radiusToGradient * 1;

		// constant part of X and Y coordinates for the entire raster
		final float constX = x - centerX;
		final float constY = y - centerY;

		int end, j; // indexing variables
		int p = offset;

		// these values below here allow for an incremental calculation of dX^2
		// + dY^2
		float dgxSq = dgx * dgx;
		float ddg   = dgxSq * 2;

		if (dgxSq > gradientLengthSq) {
			// This combination of scale and circle radius means
			// essentially no pixels will be anything but the end
			// stop color. This also avoids math problems.
			final int val = gradient.getOverflow();
			for (j = 0; j < h; j++) { // for every row
				// for every column (inner loop begins here)
				end = p + w;
				while (p < end) {
					pixels[p++] = val;
				}
				p += skip;
			}
			return;
		}

		// For every point in the raster, calculate the color at that point
		float gx = radiusToGradient * constX;

		// for every row
		for (j = 0; j < h; j++) {
			// x and y (in user space) of the first pixel of this row
			// the current distance from center
			float gy = radiusToGradient * (j + constY);

			// these values below here allow for an incremental calculation
			// of dX^2 + dY^2

			// gradient square value
			float gradientSq = gy * gy + gx * gx;

			float dg = dgxSq + dgx * gx * 2;

			// for every column (inner loop begins here)
			end = p + w;
			while (p < end) {
				// determine the distance to the center

				// since this is a non cyclic fill raster, crop at "1" and 0
				if (gradientSq >= gradientLengthSq) {
					pixels[p++] = gradient.getOverflow();
				} else {
					// Square distance index
					float iSq = gradientSq * invSqStepFloat;

					// Square distance index
					int iSqInt = (int)iSq; // chop off fractional part
					iSq -= iSqInt;

					// integer number used to index gradient array
					int gIndex = sqrtLutFixed[iSqInt];
					gIndex += (int)((sqrtLutFixed[iSqInt + 1] - gIndex) * iSq);
					pixels[p++] = gradient.getInt(gIndex);
				}

				// incremental calculation
				gradientSq += dg;
				dg += ddg;
			}
			p += skip;
		}
	}

	@Override
	public PaintContext createContext(ColorModel cm, Rectangle deviceBounds, Rectangle2D userBounds,
	                                  AffineTransform transform,
	                                  RenderingHints hints) {
		return this;
	}

	@Override
	public int getTransparency() {
		return gradient instanceof TransparentGradient ? Transparency.TRANSLUCENT : Transparency.OPAQUE;
	}
}
