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
public class ImageMatrixImageImageImageFunctionsComponent {
	public static void addMul(ImageMatrixFloat out, float[][] outComponent, float[][] addComponent, float[][]
			mulComponent) {
		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowAdd; // 1
		float[] rowMul; // 1
		int     endX = out.endX; // 1

		for (int y = out.border; y < out.endY; y++) {
			rowOut = outComponent[y];
			rowAdd = addComponent[y];
			rowMul = mulComponent[y];
			for (x = out.border; x < endX; x++) {
				rowOut[x] = (rowOut[x] + rowAdd[x]) * rowMul[x];
			}
		}
	}

	public static void mulAdd(ImageMatrixFloat out, float[][] outComponent, float[][] mulComponent, float[][]
			addComponent) {
		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowMul; // 1
		float[] rowAdd; // 1
		int     endX = out.endX; // 1

		for (int y = out.border; y < out.endY; y++) {
			rowOut = outComponent[y];
			rowMul = mulComponent[y];
			rowAdd = addComponent[y];
			for (x = out.border; x < endX; x++) {
				rowOut[x] = rowOut[x] * rowMul[x] + rowAdd[x];
			}
		}
	}

	public static void addScaled(ImageMatrixFloat out, float[][] outComponent, float[][] offsetComponent,
	                             float[][] scaleComponentOut) {
		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowOffset; // 1
		float[] rowScale; // 1
		int     endX = out.endX; // 1

		for (int y = out.border; y < out.endY; y++) {
			rowOut = outComponent[y];
			rowOffset = offsetComponent[y];
			rowScale = scaleComponentOut[y];
			for (x = out.border; x < endX; x++) {
				rowOut[x] += rowOffset[x] * rowScale[x];
			}
		}
	}
}
