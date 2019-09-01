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
package org.digitalmodular.utilities.gui.image.filter;

import org.digitalmodular.utilities.gui.image.ImageMatrixFloat;

/**
 * Convolution of the shape:
 *
 * <pre>
 * -b a b
 * </pre>
 * <p>
 * for parameter coefficients = {a, b}
 *
 * @author Mark Jeronimus
 */
// Created 2011-11-22
public class ImageMedianXFilter extends ImageFilter {
	@Override
	public void filter(ImageMatrixFloat in, ImageMatrixFloat out) {
		if (in.border < 1) {
			throw new IllegalArgumentException("Border too small");
		}
		if (!in.isCompatibleByBorderAndSize(out)) {
			throw new IllegalArgumentException("Images not compatible");
		}

		// Inner loop
		int     x; // 7.3 (6~8)
		float[] rowIn; // 5.3 (4~6)
		int     endX = in.endX; // 1
		float[] rowOut; // 1

		for (int z = 0; z < in.numComponents; z++) {
			for (int y = in.border; y < in.endY; y++) {
				rowIn = in.matrix[z][y];
				rowOut = out.matrix[z][y];
				for (x = in.border; x < endX; x++) {
					float c = (rowIn[x + 1] + rowIn[x - 1]) * 0.5f;
					rowOut[x] = rowIn[x] > rowIn[x + 1] ? rowIn[x] > rowIn[x - 1] ? rowIn[x - 1] > rowIn[x + 1] ?
					                                                                rowIn[
							                                                                x - 1]
					                                                                                            :
					                                                                rowIn[
							                                                                x + 1] : c
					                                    : rowIn[x - 1] > rowIn[x] ? rowIn[x - 1] > rowIn[x + 1] ?
					                                                                rowIn[
							                                                                x + 1]
					                                                                                            :
					                                                                rowIn[
							                                                                x - 1] : c;
				}
			}
		}
	}
}
