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
package org.digitalmodular.utilities.gui.image.converter;

import org.digitalmodular.utilities.gui.image.ImageMatrixFloat;

/**
 * Convert to opaque. Premultiplies opacity, is available.
 *
 * @author Mark Jeronimus
 */
// Created 2011-11-22
public class ImageToYCbCrConverter extends ImageConverter {
	@Override
	public void convertSelf(ImageMatrixFloat image) {
		// Inner loop
		float   r;
		float   g;
		float   b;
		int     x;
		int     endX = image.endX;
		float[] row0;
		float[] row1;
		float[] row2;

		switch (image.numComponents) {
			case 3:
			case 4:
				for (int y = image.border; y < image.endY; y++) {
					row0 = image.matrix[0][y];
					row1 = image.matrix[1][y];
					row2 = image.matrix[2][y];
					for (x = image.border; x < endX; x++) {
						r = row0[x];
						g = row1[x];
						b = row2[x];
						row0[x] = +0.50000000000000000000f * r - 0.41868758915834522111f * g -
						          0.081312410841654778888f * b;// pr
						row1[x] = +0.29900000000000000000f * r + 0.58700000000000000000f * g +
						          0.114000000000000000000f * b;// y
						row2[x] = -0.16873589164785553047f * r - 0.33126410835214446952f * g +
						          0.500000000000000000000f * b;// pb
					}
				}
				break;
			default:
				throw new IllegalArgumentException("Unsupported number of components: " + image.numComponents);
		}
	}

	@Override
	public void reverseSelf(ImageMatrixFloat image) {
		// Inner loop
		float   pr;
		float   py;
		float   pb;
		int     x;
		int     endX = image.endX;
		float[] row0;
		float[] row1;
		float[] row2;

		switch (image.numComponents) {
			case 3:
			case 4:
				for (int y = image.border; y < image.endY; y++) {
					row0 = image.matrix[0][y];
					row1 = image.matrix[1][y];
					row2 = image.matrix[2][y];
					for (x = image.border; x < endX; x++) {
						pr = row0[x];
						py = row1[x];
						pb = row2[x];
						row0[x] = py + 1.40200000000000000000f * pr;
						row1[x] = py - 0.34413628620102214650f * pb - 0.71413628620102214645f * pr;
						row2[x] = py + 1.77200000000000000000f * pb;
					}
				}
				break;
			default:
				throw new IllegalArgumentException("Unsupported number of components: " + image.numComponents);
		}
	}
}
