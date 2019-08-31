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
package org.digitalmodular.utilities.gui.image.filter;

import org.digitalmodular.utilities.gui.image.ImageMatrixFloat;

/**
 * Convolution of the shape:
 *
 * <pre>
 * a b c
 * d e f
 * g h i
 * </pre>
 *
 * @author Mark Jeronimus
 */
// Created 2011-11-22
public class ImageConvolutionFilter extends ImageFilter {
	public final int       diameter;
	public final int       radius;
	public final float[][] kernel;

	private final ConvolutionProcess processes;

	ImageMatrixFloat in;
	ImageMatrixFloat out;

	public ImageConvolutionFilter(float[]... kernel) {
		diameter = kernel.length;
		if ((diameter & 1) == 0) {
			throw new IllegalArgumentException("Kernel size must be an odd number: " + diameter);
		}
		if (diameter < 2) {
			throw new IllegalArgumentException("Kernel size too small: " + diameter);
		}
		if (diameter != kernel[0].length) {
			throw new IllegalArgumentException("Kernel size must be square: " + kernel[0].length + "x" + diameter);
		}

		radius = diameter >> 1;
		this.kernel = kernel;

		switch (radius) {
			case 1:
				processes = new ConvolutionProcess3();
				break;
			case 2:
				processes = new ConvolutionProcess5();
				break;
			default:
				processes = new ConvolutionProcessN();
				// throw new
				// IllegalArgumentException("Kernel size not supported: " +
				// diameter);
		}
	}

	public static ImageConvolutionFilter designDiscBlur(float radius) {
		int r = (int)Math.ceil(radius);
		if (r < 1) {
			r = 1;
		}
		float offset = 1 + radius;

		float[][] kernel = new float[r + r + 1][r + r + 1];
		for (int v = -r; v <= radius; v++) {
			int y = v + r;
			for (int u = -r; u <= radius; u++) {
				int x = u + r;

				float c = offset - (float)Math.hypot(u, v);
				if (c > 1) {
					c = 1;
				} else if (c < 0) {
					c = 0;
				}
				kernel[y][x] = c;
			}
			System.out.println();
		}

		return new ImageConvolutionFilter(kernel).normalize(1);
	}

	/**
	 * Design a Gaussian blur for a given standard deviation. This is an inefficient implementation. using two
	 * perpendicular linear kernels (using {@link ImageConvolutionFlatSymmetricFilter}) is much more efficient)
	 *
	 * @param stDeviation the standard deviation of the Gaussian kernel.
	 * @param precision   the precision of the filter. It will try keeping the values on the boundary smaller than
	 *                    {@code precision} times the peak value.
	 * @param maxRadius   the maximum radius of the kernel allowed (set to the same as the border width of the {@link
	 *                    ImageMatrixFloat}. Specifying a too small maximum radius or a too big standard deviation may
	 *                    cause
	 *                    the precision to be worse than the requested {@code maxRadius}. It is good practice to
	 *                    choose the maximum radius well above the expected radius.
	 * @see ImageConvolutionFlatSymmetricFilter
	 */
	public static ImageConvolutionFilter designGaussianBlur(float stDeviation, float precision, int maxRadius) {
		float denominator = -0.5f / (stDeviation * stDeviation);

		int r = 0;
		do {
			r++;
		} while (Math.exp(r * r * denominator) > precision);
		r--;

		if (r < 1) {
			r = 1;
		} else if (r > maxRadius) {
			r = maxRadius;
		}

		float[][] kernel = new float[r + r + 1][r + r + 1];
		for (int v = -r; v <= r; v++) {
			int y = v + r;
			for (int u = -r; u <= r; u++) {
				int x = u + r;

				kernel[y][x] = (float)Math.exp((u * u + v * v) * denominator);
			}
		}

		return new ImageConvolutionFilter(kernel).normalize(1);
	}

	/**
	 * Normalize the signed area (ie. positive area - negative area). If the area equals 0, nothing happens.
	 */
	public ImageConvolutionFilter normalize(float newArea) {
		float sum = 0;
		for (int y = 0; y < diameter; y++) {
			for (int x = 0; x < diameter; x++) {
				sum += kernel[y][x];
			}
		}

		if (sum < 1) {
			sum = 1;
		}
		sum = sum == 0 ? 1 : newArea / sum;

		scale(sum);

		return this;
	}

	/**
	 * Normalize the absolute area (ie. positive area + negative area).
	 */
	public ImageConvolutionFilter normalizeAbs(float newArea) {
		float sum = 0;
		for (int y = 0; y < diameter; y++) {
			for (int x = 0; x < diameter; x++) {
				sum += Math.abs(kernel[y][x]);
			}
		}

		if (sum < 1) {
			sum = 1;
		}
		sum = sum == 0 ? 1 : newArea / sum;

		scale(sum);

		return this;
	}

	public void scale(float sum) {
		for (int y = 0; y < diameter; y++) {
			for (int x = 0; x < diameter; x++) {
				kernel[y][x] *= sum;
			}
		}
	}

	@Override
	public void filter(ImageMatrixFloat in, ImageMatrixFloat out) {
		if (in.border < radius) {
			throw new IllegalArgumentException("Border too small for kernel: " + in.border + " < " + radius);
		}
		if (!in.isCompatibleByBorderAndSize(out)) {
			throw new IllegalArgumentException("Images not compatible");
		}

		this.in = in;
		this.out = out;
		processes.convolve();
	}

	private interface ConvolutionProcess {
		void convolve();
	}

	private class ConvolutionProcess3 implements ConvolutionProcess {
		public ConvolutionProcess3() {}

		@Override
		public void convolve() {
			// Inner loop
			int     x;
			int     endX = in.endX;
			float[] row0;
			float[] row1;
			float[] row2;
			float[] rowOut;
			float[] kernel0;
			float[] kernel1;
			float[] kernel2;

			for (int z = 0; z < in.numComponents; z++) {
				for (int y = in.border; y < in.endY; y++) {
					row0 = in.matrix[z][y - 1];
					row1 = in.matrix[z][y];
					row2 = in.matrix[z][y + 1];
					rowOut = out.matrix[z][y];
					kernel0 = kernel[0];
					kernel1 = kernel[1];
					kernel2 = kernel[2];
					for (x = in.border; x < endX; x++) {
						rowOut[x] = row0[x - 1] * kernel0[0] + //
						            row0[x] * kernel0[1] + //
						            row0[x + 1] * kernel0[2] + //
						            row1[x - 1] * kernel1[0] + //
						            row1[x] * kernel1[1] + //
						            row1[x + 1] * kernel1[2] + //
						            row2[x - 1] * kernel2[0] + //
						            row2[x] * kernel2[1] + //
						            row2[x + 1] * kernel2[2];
					}
				}
			}
		}
	}

	private class ConvolutionProcess5 implements ConvolutionProcess {
		public ConvolutionProcess5() {}

		@Override
		public void convolve() {
			// Inner loop
			int     x;
			int     endX = in.endX;
			float[] row;
			float[] row0;
			float[] row1;
			float[] row2;
			float[] row3;
			float[] row4;
			float[] kernel0;
			float[] kernel1;
			float[] kernel2;
			float[] kernel3;
			float[] kernel4;

			for (int z = 0; z < in.numComponents; z++) {
				for (int y = in.border; y < in.endY; y++) {
					row = out.matrix[z][y];
					row0 = in.matrix[z][y - 2];
					row1 = in.matrix[z][y - 1];
					row2 = in.matrix[z][y];
					row3 = in.matrix[z][y + 1];
					row4 = in.matrix[z][y + 2];
					kernel0 = kernel[0];
					kernel1 = kernel[1];
					kernel2 = kernel[2];
					kernel3 = kernel[3];
					kernel4 = kernel[4];
					for (x = in.border; x < endX; x++) {
						row[x] = row0[x - 2] * kernel0[0] + //
						         row0[x - 1] * kernel0[1] + //
						         row0[x] * kernel0[2] + //
						         row0[x + 1] * kernel0[3] + //
						         row0[x + 2] * kernel0[4] + //
						         row1[x - 2] * kernel1[0] + //
						         row1[x - 1] * kernel1[1] + //
						         row1[x] * kernel1[2] + //
						         row1[x + 1] * kernel1[3] + //
						         row1[x + 2] * kernel1[4] + //
						         row2[x - 2] * kernel2[0] + //
						         row2[x - 1] * kernel2[1] + //
						         row2[x] * kernel2[2] + //
						         row2[x + 1] * kernel2[3] + //
						         row2[x + 2] * kernel2[4] + //
						         row3[x - 2] * kernel3[0] + //
						         row3[x - 1] * kernel3[1] + //
						         row3[x] * kernel3[2] + //
						         row3[x + 1] * kernel3[3] + //
						         row3[x + 2] * kernel3[4] + //
						         row4[x - 2] * kernel4[0] + //
						         row4[x - 1] * kernel4[1] + //
						         row4[x] * kernel4[2] + //
						         row4[x + 1] * kernel4[3] + //
						         row4[x + 2] * kernel4[4];
					}
				}
			}
		}
	}

	private class ConvolutionProcessN implements ConvolutionProcess {
		public ConvolutionProcessN() {}

		@Override
		public void convolve() {
			// Inner loop
			int     x;
			int     endX = in.endX;
			float[] row;

			for (int z = 0; z < in.numComponents; z++) {
				for (int y = in.border; y < in.endY; y++) {
					row = out.matrix[z][y];
					for (x = in.border; x < endX; x++) {
						float sum = 0;

						for (int v = 0; v < diameter; v++) {
							for (int u = 0; u < diameter; u++) {
								sum += in.matrix[z][y + v - radius][x + u - radius] * kernel[v][u];
							}
						}

						row[x] = sum;
					}
				}
			}
		}
	}
}
