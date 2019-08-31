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

import org.digitalmodular.utilities.container.Vector2f;
import org.digitalmodular.utilities.gui.image.ImageMatrixFloat;

/**
 * Functions of the form:<br> scalar = &lt;operator&gt;({@link ImageMatrixFloat})<br> vector = &lt;operator&gt;({@link
 * ImageMatrixFloat})<br>
 *
 * @author Mark Jeronimus
 */
// Created 2012-04-05
public class ImageMatrixQueryFunctionsComponent {
	/**
	 * returns min(img[component])
	 */
	public static float findMin(ImageMatrixFloat img, float[][] matrix) {
		// Inner loop
		int     x; // 3
		float   min  = -Integer.MIN_VALUE; // 1.5 (1~2)
		float[] row; // 1
		int     endX = img.endX; // 1

		for (int y = img.border; y < img.endY; y++) {
			row = matrix[y];
			for (x = img.border; x < endX; x++) {
				float c = row[x];
				if (c == c) {
					if (min > c) {
						min = c;
					}
				}
			}
		}

		return min;
	}

	/**
	 * returns max(img[component])
	 */
	public static float findMax(ImageMatrixFloat img, float[][] matrix) {
		// Inner loop
		int     x; // 3
		float   max  = Integer.MIN_VALUE; // 1.5 (1~2)
		float[] row; // 1
		int     endX = img.endX; // 1

		for (int y = img.border; y < img.endY; y++) {
			row = matrix[y];
			for (x = img.border; x < endX; x++) {
				float c = row[x];
				if (c == c) {
					if (max < c) {
						max = c;
					}
				}
			}
		}

		return max;
	}

	/**
	 * returns {min(img[component]), max(img[component])}
	 */
	public static Vector2f findMinMax(ImageMatrixFloat img, float[][] matrix) {
		// Inner loop
		float   c; // 4.5 (4~6)
		int     x; // 3
		float   min  = -Integer.MIN_VALUE; // 1.5 (1~2)
		float[] row; // 1
		int     endX = img.endX; // 1
		float   max  = Integer.MIN_VALUE; // 0.5 (0~1)

		for (int y = img.border; y < img.endY; y++) {
			row = matrix[y];
			for (x = img.border; x < endX; x++) {
				c = row[x];
				if (c == c) {
					if (min > c) {
						min = c;
					} else if (max < c) {
						max = c;
					}
				}
			}
		}

		return new Vector2f(min, max);
	}

	/**
	 * returns average(img[component])
	 */
	public static float findAverage(ImageMatrixFloat img, float[][] matrix) {
		// Inner loop
		float   c; // 4
		int     x; // 4
		float[] row; // 1
		int     endX = img.endX; // 1
		float   sum  = 0; // 1

		for (int y = img.border; y < img.endY; y++) {
			row = matrix[y];
			for (x = img.border; x < endX; x++) {
				c = row[x];
				if (c == c) {
					sum += c;
				}
			}
		}

		return sum / (img.width * img.height);
	}

	/**
	 * histogram[i] = count(round(img[component] * (histogram.length - 1)) == i)
	 */
	public static void makeHistogram(ImageMatrixFloat img, float[][] matrix, int[] hist) {
		// Inner loop
		float   c; // 4
		int     x; // 4
		int     bin; // 2.1 (2~4)
		int     mul  = hist.length - 1; // 2.1 (2~3)
		float[] row; // 1
		int     endX = img.endX; // 1

		for (int y = img.border; y < img.endY; y++) {
			row = matrix[y];
			for (x = img.border; x < endX; x++) {
				c = row[x];
				// Ignore NaN's
				if (c == c) // Ignore NaN's
				{
					bin = (int)(c * mul + 0.5);
					if (bin >= mul) {
						bin = mul;
					} else if (bin < 0) {
						bin = 0;
					}
					hist[bin]++;
				}
			}
		}
	}
}
