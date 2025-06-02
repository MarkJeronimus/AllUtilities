package nl.airsupplies.utilities.graphics.image.filter;

import nl.airsupplies.utilities.graphics.image.ImageMatrixFloat;

/**
 * Convolution of the shape:
 *
 * <pre>
 *         b
 * b a b · a
 *         b
 * </pre>
 * <p>
 * for parameter coefficients = {a, b}
 *
 * @author Mark Jeronimus
 */
// Created 2011-11-22
public class ImageConvolutionFlatSymmetricFilter extends ImageFilter {
	public final int     radius;
	public final float[] kernel;

	private final ConvolutionProcessFlat processes;

	ImageMatrixFloat in;
	ImageMatrixFloat temp;
	ImageMatrixFloat out;

	public ImageConvolutionFlatSymmetricFilter(float... kernel) {
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
	 * Normalize the signed area (ie. positive area - negative area). If the area equals 0, nothing happens.
	 */
	public ImageConvolutionFlatSymmetricFilter normalize(float newArea) {
		float sum = -kernel[0]; // Count this only once, not twice.
		for (int i = 0; i < radius; i++) {
			sum += kernel[i] * 2;
		}

		sum = sum == 0 ? 1 : newArea / sum;

		scale(sum);

		return this;
	}

	/**
	 * Normalize the absolute area (ie. positive area + negative area).
	 */
	public ImageConvolutionFlatSymmetricFilter normalizeAbs(float newArea) {
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

	/**
	 * Design a gaussian blur for a given standard deviation.
	 *
	 * @param stdDeviation the standard deviation of the gaussian kernel.
	 * @param precision    the precision of the filter. It will try keeping the values on the boundary smaller than
	 *                     {@code precision} times the peak value.
	 * @param maxRadius    the maximum radius of the kernel allowed (set to the same as the border width of the {@link
	 *                     ImageMatrixFloat}. Specifying a too small maximum radius or a too big standard deviation may
	 *                     cause
	 *                     the precision to be worse than the requested {@code maxRadius}. It is good practice to
	 *                     choose the maximum radius well above the expected radius.
	 */
	public static ImageConvolutionFlatSymmetricFilter designGaussianBlur(
			float stdDeviation, float precision, int maxRadius) {
		int idealR = getIdealRadius(stdDeviation, precision);

		int r = idealR;
		if (r < 2) {
			r = 2;
		} else if (r > maxRadius) {
			r = maxRadius;
		}

		double denominator = -0.5 / (stdDeviation * stdDeviation);

		float[] kernel = new float[r];
		for (int x = 0; x < r; x++) {
			kernel[x] = (float)Math.exp(x * x * denominator);
		}

		r++;
		if (Math.exp(r * r * denominator) > 2 * precision) {
			System.err.println(
					"Warning: current filter much less precise than requested: " + (float)Math.exp(r * r * denominator)
					+ ". Ideal r = " + idealR + ". Ideal sigma = "
					+ Math.sqrt(-0.5 * (maxRadius * maxRadius) / Math.log(precision)));
		}

		return new ImageConvolutionFlatSymmetricFilter(kernel).normalize(1);
	}

	public static int getIdealRadius(float stdDeviation, float precision) {
		double denominator = -0.5 / (stdDeviation * stdDeviation);

		int r = 0;
		do {
			r++;
		} while (Math.exp(r * r * denominator) > precision);

		return Math.max(2, r - 1);
	}

	public void scale(float factor) {
		for (int i = 0; i < radius; i++) {
			kernel[i] *= factor;
		}
	}

	@Override
	public void filter(ImageMatrixFloat in, ImageMatrixFloat temp, ImageMatrixFloat out) {
		if (in.border < radius) {
			throw new IllegalArgumentException("Border too small for kernel: " + in.border + " < " + radius);
		}
		if (!(in.isCompatibleByBorderAndSize(temp) && in.isCompatibleByBorderAndSize(out))) {
			throw new IllegalArgumentException("Images not compatible");
		}

		this.in   = in;
		this.temp = temp;
		this.out  = out;
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
			int     x; // 12
			float[] row; // 4
			int     endX = in.endX; // 2
			float   kernel0; // 2
			float   kernel1; // 2
			float[] rowTemp0; // 2
			float[] rowTemp1a; // 1
			float[] rowTemp1b; // 1

			for (int z = 0; z < in.numComponents; z++) {
				kernel0 = kernel[0];
				kernel1 = kernel[1];
				for (int y = in.border; y < in.endY; y++) {
					row      = in.matrix[z][y];
					rowTemp0 = temp.matrix[z][y];
					for (x = in.border; x < endX; x++) {
						rowTemp0[x] = row[x] * kernel0 +
						              (row[x - 1] + row[x + 1]) * kernel1;
					}
				}
			}
			temp.extendBorderY();
			for (int z = 0; z < in.numComponents; z++) {
				kernel0 = kernel[0];
				kernel1 = kernel[1];
				for (int y = in.border; y < in.endY; y++) {
					rowTemp1a = temp.matrix[z][y - 1];
					rowTemp0  = temp.matrix[z][y];
					rowTemp1b = temp.matrix[z][y + 1];
					row       = out.matrix[z][y];
					for (x = in.border; x < endX; x++) {
						row[x] = rowTemp0[x] * kernel0 +
						         (rowTemp1a[x] + rowTemp1b[x]) * kernel1;
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
			int     x; // 16
			float[] row; // 6
			int     endX = in.endX; // 2
			float   kernel0; // 2
			float   kernel1; // 2
			float   kernel2; // 2
			float[] rowTemp0; // 2
			float[] rowTemp1a; // 1
			float[] rowTemp1b; // 1
			float[] rowTemp2a; // 1
			float[] rowTemp2b; // 1

			for (int z = 0; z < in.numComponents; z++) {
				kernel0 = kernel[0];
				kernel1 = kernel[1];
				kernel2 = kernel[2];
				for (int y = in.border; y < in.endY; y++) {
					row      = in.matrix[z][y];
					rowTemp0 = temp.matrix[z][y];
					for (x = in.border; x < endX; x++) {
						rowTemp0[x] = row[x] * kernel0 +
						              (row[x - 1] + row[x + 1]) * kernel1 +
						              (row[x - 2] + row[x + 2]) * kernel2;
					}
				}
			}
			temp.extendBorderY();
			for (int z = 0; z < in.numComponents; z++) {
				kernel0 = kernel[0];
				kernel1 = kernel[1];
				kernel2 = kernel[2];
				for (int y = in.border; y < in.endY; y++) {
					rowTemp2a = temp.matrix[z][y - 2];
					rowTemp1a = temp.matrix[z][y - 1];
					rowTemp0  = temp.matrix[z][y];
					rowTemp1b = temp.matrix[z][y + 1];
					rowTemp2b = temp.matrix[z][y + 2];
					row       = out.matrix[z][y];
					for (x = in.border; x < endX; x++) {
						row[x] = rowTemp0[x] * kernel0 +
						         (rowTemp1a[x] + rowTemp1b[x]) * kernel1 +
						         (rowTemp2a[x] + rowTemp2b[x]) * kernel2;
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
			int     i; // 2+10r (r>=3)
			int     x; // 8+4r (r>=3)
			float   sum; // 4+2r (r>=3)
			float[] kernel = ImageConvolutionFlatSymmetricFilter.this.kernel; // 2+2r
			// (r>=3)
			float[] rowIn; // 1+2r (r>=3)
			int     radius = ImageConvolutionFlatSymmetricFilter.this.radius; // 2r
			// (r>=3)
			float[][] planeTemp; // 2r (r>=3)
			int       endX = in.endX; // 2
			float[]   rowTemp; // 2
			float[]   rowOut; // 1

			for (int z = 0; z < in.numComponents; z++) {
				planeTemp = temp.matrix[z];
				for (int y = in.border; y < in.endY; y++) {
					rowIn   = in.matrix[z][y];
					rowTemp = planeTemp[y];
					for (x = in.border; x < endX; x++) {
						sum = rowIn[x] * kernel[0];

						for (i = 1; i < radius; i++) {
							sum += (rowIn[x - i] + rowIn[x + i]) * kernel[i];
						}

						rowTemp[x] = sum;
					}
				}
			}
			temp.extendBorderY();
			for (int z = 0; z < in.numComponents; z++) {
				planeTemp = temp.matrix[z];
				for (int y = in.border; y < in.endY; y++) {
					rowTemp = planeTemp[y];
					rowOut  = out.matrix[z][y];
					for (x = in.border; x < endX; x++) {
						sum = rowTemp[x] * kernel[0];

						for (i = 1; i < radius; i++) {
							sum += (planeTemp[y - i][x] + planeTemp[y + i][x]) * kernel[i];
						}

						rowOut[x] = sum;
					}
				}
			}
		}
	}
}
