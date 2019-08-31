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
package org.digitalmodular.utilities.gui.image.function;

import org.digitalmodular.utilities.gui.image.ImageMatrixFloat;

/**
 * Functions of the form:<br> {@link ImageMatrixFloat} = ImageMatrix &lt;operator&gt; ImageMatrix &lt;operator&gt;
 * ImageMatrix<br>
 *
 * @author Mark Jeronimus
 */
// Created 2012-04-05
public class ImageMatrixImageImageImageFunctions {
	public static void addAdd(ImageMatrixFloat out, ImageMatrixFloat add1, ImageMatrixFloat add2) {
		if (!(out.isCompatibleByBorderAndSize(add1) && out.isCompatibleByBorderAndSize(add2))) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowAdd1; // 1
		float[] rowAdd2; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			for (int y = out.border; y < out.endY; y++) {
				rowOut = out.matrix[z][y];
				rowAdd1 = add1.matrix[z][y];
				rowAdd2 = add2.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					rowOut[x] += rowAdd1[x] + rowAdd2[x];
				}
			}
		}
	}

	public static void addScaled(ImageMatrixFloat out, ImageMatrixFloat mul, ImageMatrixFloat scale) {
		if (!(out.isCompatibleByBorderAndSize(mul) && out.isCompatibleByBorderAndSize(scale))) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowMul; // 1
		float[] rowScale; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			for (int y = out.border; y < out.endY; y++) {
				rowOut = out.matrix[z][y];
				rowMul = mul.matrix[z][y];
				rowScale = scale.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					rowOut[x] += rowMul[x] * rowScale[x];
				}
			}
		}
	}

	public static void mulOffset(ImageMatrixFloat out, ImageMatrixFloat mul, ImageMatrixFloat offset) {
		if (!(out.isCompatibleByBorderAndSize(mul) && out.isCompatibleByBorderAndSize(offset))) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowMul; // 1
		float[] rowOffset; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			for (int y = out.border; y < out.endY; y++) {
				rowOut = out.matrix[z][y];
				rowMul = mul.matrix[z][y];
				rowOffset = offset.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					rowOut[x] *= rowMul[x] + rowOffset[x];
				}
			}
		}
	}

	public static void mulMul(ImageMatrixFloat out, ImageMatrixFloat mul1, ImageMatrixFloat mul2) {
		if (!(out.isCompatibleByBorderAndSize(mul1) && out.isCompatibleByBorderAndSize(mul2))) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowMul1; // 1
		float[] rowMul2; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			for (int y = out.border; y < out.endY; y++) {
				rowOut = out.matrix[z][y];
				rowMul1 = mul1.matrix[z][y];
				rowMul2 = mul2.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					rowOut[x] *= rowMul1[x] * rowMul2[x];
				}
			}
		}
	}
}
