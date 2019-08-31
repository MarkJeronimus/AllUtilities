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
 * Functions of the form:<br> {@link ImageMatrixFloat}[component] = scalar<br> {@link ImageMatrixFloat}[component] = ImageMatrix
 * &lt;operator&gt; scalar<br> {@link ImageMatrixFloat}[component] = ImageMatrix &lt;operator&gt; scalar &lt;operator&gt;
 * scalar<br>
 *
 * @author Mark Jeronimus
 */
// Created 2012-04-05
public class ImageMatrixImageScalarFunctionsComponent {
	public static void set(ImageMatrixFloat out, float[][] component, float in) {
		// Inner loop
		int     x; // 4
		float[] row; // 2
		int     endX = out.endX; // 1

		for (int y = out.border; y < out.endY; y++) {
			row = component[y];
			for (x = out.border; x < endX; x++) {
				row[x] = in;
			}
		}
	}

	public static void add(ImageMatrixFloat out, float[][] component, float in) {
		// Inner loop
		int     x; // 4
		float[] row; // 2
		int     endX = out.endX; // 1

		for (int y = out.border; y < out.endY; y++) {
			row = component[y];
			for (x = out.border; x < endX; x++) {
				row[x] += in;
			}
		}
	}

	public static void sub(ImageMatrixFloat out, float[][] component, float in) {
		// Inner loop
		int     x; // 4
		float[] row; // 2
		int     endX = out.endX; // 1

		for (int y = out.border; y < out.endY; y++) {
			row = component[y];
			for (x = out.border; x < endX; x++) {
				row[x] -= in;
			}
		}
	}

	public static void subR(ImageMatrixFloat out, float[][] component, float in) {
		// Inner loop
		int     x; // 4
		float[] row; // 2
		int     endX = out.endX; // 1

		for (int y = out.border; y < out.endY; y++) {
			row = component[y];
			for (x = out.border; x < endX; x++) {
				row[x] = in - row[x];
			}
		}
	}

	public static void mul(ImageMatrixFloat out, float[][] component, float in) {
		// Inner loop
		int     x; // 4
		float[] row; // 2
		int     endX = out.endX; // 1

		for (int y = out.border; y < out.endY; y++) {
			row = component[y];
			for (x = out.border; x < endX; x++) {
				row[x] *= in;
			}
		}
	}

	public static void div(ImageMatrixFloat out, float[][] component, float in) {
		// Inner loop
		int     x; // 4
		float[] row; // 2
		int     endX = out.endX; // 1

		for (int y = out.border; y < out.endY; y++) {
			row = component[y];
			for (x = out.border; x < endX; x++) {
				row[x] /= in;
			}
		}
	}

	public static void divR(ImageMatrixFloat out, float[][] component, float in) {
		// Inner loop
		int     x; // 4
		float[] row; // 2
		int     endX = out.endX; // 1

		for (int y = out.border; y < out.endY; y++) {
			row = component[y];
			for (x = out.border; x < endX; x++) {
				row[x] = in / row[x];
			}
		}
	}

	public static void pow(ImageMatrixFloat out, float[][] component, float power) {
		// Inner loop
		int     x; // 4
		float[] row; // 2
		int     endX = out.endX; // 1

		for (int y = out.border; y < out.endY; y++) {
			row = component[y];
			for (x = out.border; x < endX; x++) {
				row[x] = (float)Math.pow(row[x], power);
			}
		}
	}

	public static void powR(ImageMatrixFloat out, float[][] component, float base) {
		// Inner loop
		int     x; // 4
		float[] row; // 2
		int     endX = out.endX; // 1

		for (int y = out.border; y < out.endY; y++) {
			row = component[y];
			for (x = out.border; x < endX; x++) {
				row[x] = (float)Math.pow(base, row[x]);
			}
		}
	}

	public static void addMul(ImageMatrixFloat out, float[][] component, float add, float mul) {
		// Inner loop
		int     x; // 4
		float[] row; // 2
		int     endX = out.endX; // 1

		for (int y = out.border; y < out.endY; y++) {
			row = component[y];
			for (x = out.border; x < endX; x++) {
				row[x] = (row[x] + add) * mul;
			}
		}
	}

	public static void mulAdd(ImageMatrixFloat out, float[][] component, float mul, float add) {
		// Inner loop
		int     x; // 4
		float[] row; // 2
		int     endX = out.endX; // 1

		for (int y = out.border; y < out.endY; y++) {
			row = component[y];
			for (x = out.border; x < endX; x++) {
				row[x] = row[x] * mul + add;
			}
		}
	}
}
