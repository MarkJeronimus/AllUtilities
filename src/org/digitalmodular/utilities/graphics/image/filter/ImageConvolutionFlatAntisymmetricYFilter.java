/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2024 Mark Jeronimus. All Rights Reversed.
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.digitalmodular.utilities.graphics.image.filter;

import org.digitalmodular.utilities.graphics.image.ImageMatrixFloat;

/**
 * Convolution of the shape:
 *
 * <pre>
 * -b
 *  a
 *  b
 * </pre>
 * <p>
 * for parameter coefficients = {a, b}
 *
 * @author Mark Jeronimus
 */
// Created 2011-11-22
public class ImageConvolutionFlatAntisymmetricYFilter extends ImageFilter {
	public final int     radius;
	public final float[] kernel;

	private final ConvolutionProcessFlat processes;

	ImageMatrixFloat in;
	ImageMatrixFloat out;

	public ImageConvolutionFlatAntisymmetricYFilter(float... kernel) {
		radius = kernel.length;
		if (radius < 2) {
			throw new IllegalArgumentException("Kernel size too small: " + radius);
		}

		this.kernel = new float[radius];
		System.arraycopy(kernel, 0, this.kernel, 0, radius);

		switch (radius) {
			case 2:
				processes = new ConvolutionProcessFlat3();
				break;
			case 3:
				processes = new ConvolutionProcessFlat5();
				break;
			default:
				processes = new ConvolutionProcessFlatN();
				// throw new
				// IllegalArgumentException("Kernel size not supported: " +
				// diameter);
		}
	}

	/**
	 * Design a gaussian blur for a given standard deviation.
	 *
	 * @param stDeviation the standard deviation of the gaussian kernel.
	 * @param precision   the precision of the filter. It will try keeping the values on the boundary smaller than
	 *                    {@code precision} times the peak value.
	 * @param maxRadius   the maximum radius of the kernel allowed (set to the same as the border width of the {@link
	 *                    ImageMatrixFloat}. Specifying a too small maximum radius or a too big standard deviation may
	 *                    cause
	 *                    the precision to be worse than the requested {@code maxRadius}. It is good practice to
	 *                    choose the maximum radius well above the expected radius.
	 */
	public static ImageConvolutionFlatAntisymmetricXFilter designGaussianBlur(float stDeviation, float precision,
	                                                                          int maxRadius) {
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

		float[] kernel = new float[r + 1];
		for (int x = 0; x <= r; x++) {
			kernel[x] = (float)Math.exp(x * x * denominator);
		}

		return new ImageConvolutionFlatAntisymmetricXFilter(kernel).normalize(1);
	}

	/**
	 * Normalize the signed area (ie. positive area - negative area). If the area equals 0, nothing happens.
	 */
	public ImageConvolutionFlatAntisymmetricYFilter normalize(float newArea) {
		float sum = kernel[0];

		sum = sum == 0 ? 1 : newArea / sum;

		scale(sum);

		return this;
	}

	/**
	 * Normalize the absolute area (ie. positive area + negative area).
	 */
	public ImageConvolutionFlatAntisymmetricYFilter normalizeAbs(float newArea) {
		float sum = -kernel[0]; // Count this only once, not twice.
		for (int i = 0; i < radius; i++) {
			sum += Math.abs(kernel[i] * 2);
		}

		if (sum < 1) {
			sum = 1;
		}
		sum = sum == 0 ? 1 : newArea / sum;

		scale(sum);

		return this;
	}

	public void scale(float factor) {
		for (int i = 0; i < radius; i++) {
			kernel[i] *= factor;
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

		this.in  = in;
		this.out = out;
		processes.convolve();
	}

	private interface ConvolutionProcessFlat {
		void convolve();
	}

	private class ConvolutionProcessFlat3 implements ConvolutionProcessFlat {
		public ConvolutionProcessFlat3() {
		}

		@Override
		public void convolve() {
			// Inner loop
			int     x; // 6+6
			int     endX = in.endX; // 2+2
			float[] rowOut; // 1+3
			float   kernel0; // 1+1
			float   kernel1; // 1+1
			float[] rowIn0; // 1+1
			float[] rowIn1a; // 0+1
			float[] rowIn1b; // 0+1

			for (int z = 0; z < in.numComponents; z++) {
				kernel0 = kernel[0];
				kernel1 = kernel[1];
				for (int y = in.border; y < in.endY; y++) {
					rowIn1a = in.matrix[z][y - 1];
					rowIn0  = in.matrix[z][y];
					rowIn1b = in.matrix[z][y + 1];
					rowOut  = out.matrix[z][y];
					for (x = in.border; x < endX; x++) {
						rowOut[x] = rowIn0[x] * kernel0 +
						            (rowIn1b[x] - rowIn1a[x]) * kernel1;
					}
				}
			}
		}
	}

	private class ConvolutionProcessFlat5 implements ConvolutionProcessFlat {
		public ConvolutionProcessFlat5() {
		}

		@Override
		public void convolve() {
			// Inner loop
			int     x; // 6+6
			int     endX = in.endX; // 2+2
			float[] rowOut; // 1+3
			float   kernel0; // 1+1
			float   kernel1; // 1+1
			float   kernel2; // 1+1
			float[] rowIn0; // 1+1
			float[] rowIn1a; // 0+1
			float[] rowIn1b; // 0+1
			float[] rowIn2a; // 0+1
			float[] rowIn2b; // 0+1

			for (int z = 0; z < in.numComponents; z++) {
				kernel0 = kernel[0];
				kernel1 = kernel[1];
				kernel2 = kernel[2];
				for (int y = in.border; y < in.endY; y++) {
					rowIn2a = in.matrix[z][y - 2];
					rowIn1a = in.matrix[z][y - 1];
					rowIn0  = in.matrix[z][y];
					rowIn1b = in.matrix[z][y + 1];
					rowIn2b = in.matrix[z][y + 2];
					rowOut  = out.matrix[z][y];
					for (x = in.border; x < endX; x++) {
						rowOut[x] = rowIn0[x] * kernel0 +
						            (rowIn1b[x] - rowIn1a[x]) * kernel1 +
						            (rowIn2b[x] - rowIn2a[x]) * kernel2;
					}
				}
			}
		}
	}

	private class ConvolutionProcessFlatN implements ConvolutionProcessFlat {
		public ConvolutionProcessFlatN() {
		}

		@Override
		public void convolve() {
			// Inner loop
			int       i; // 1+5r (r>=3)
			int       x; // 4+2r (r>=3)
			float[][] planeIn; // 1+2r (r>=3)
			float     sum; // 2+r (r>=3)
			float[]   kernel = ImageConvolutionFlatAntisymmetricYFilter.this.kernel; // 1+r
			// (r>=3)
			int     radius = ImageConvolutionFlatAntisymmetricYFilter.this.radius; // 1
			int     endX   = in.endX; // 1
			float[] rowOut; // 1

			for (int z = 0; z < in.numComponents; z++) {
				planeIn = in.matrix[z];
				for (int y = in.border; y < in.endY; y++) {
					rowOut = out.matrix[z][y];
					for (x = in.border; x < endX; x++) {
						sum = planeIn[y][x] * kernel[0];

						for (i = 1; i < radius; i++) {
							sum += (planeIn[y + i][x] - planeIn[y - i][x]) * kernel[i];
						}

						rowOut[x] = sum;
					}
				}
			}
		}
	}
}
