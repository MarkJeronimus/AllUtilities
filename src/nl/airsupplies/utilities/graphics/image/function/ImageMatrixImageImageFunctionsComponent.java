package nl.airsupplies.utilities.graphics.image.function;

import nl.airsupplies.utilities.graphics.image.ImageMatrixFloat;

/**
 * Functions of the form:<br> {@link ImageMatrixFloat} = ImageMatrix<br> {@link ImageMatrixFloat} = ImageMatrix &lt;
 * operator&gt;
 * ImageMatrix<br>
 *
 * @author Mark Jeronimus
 */
// Created 2012-04-05
public class ImageMatrixImageImageFunctionsComponent {
	public static void set(ImageMatrixFloat out, float[][] outComponent, float[][] inComponent) {
		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowIn; // 1
		int     endX = out.endX; // 1

		for (int y = out.border; y < out.endY; y++) {
			rowOut = outComponent[y];
			rowIn  = inComponent[y];
			for (x = out.border; x < endX; x++) {
				rowOut[x] = rowIn[x];
			}
		}
	}

	public static void add(ImageMatrixFloat out, float[][] outComponent, float[][] inComponent) {
		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowIn; // 1
		int     endX = out.endX; // 1

		for (int y = out.border; y < out.endY; y++) {
			rowOut = outComponent[y];
			rowIn  = inComponent[y];
			for (x = out.border; x < endX; x++) {
				rowOut[x] += rowIn[x];
			}
		}
	}

	public static void sub(ImageMatrixFloat out, float[][] outComponent, float[][] inComponent) {
		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowIn; // 1
		int     endX = out.endX; // 1

		for (int y = out.border; y < out.endY; y++) {
			rowOut = outComponent[y];
			rowIn  = inComponent[y];
			for (x = out.border; x < endX; x++) {
				rowOut[x] -= rowIn[x];
			}
		}
	}

	public static void subR(ImageMatrixFloat out, float[][] outComponent, float[][] inComponent) {
		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowIn; // 1
		int     endX = out.endX; // 1

		for (int y = out.border; y < out.endY; y++) {
			rowOut = outComponent[y];
			rowIn  = inComponent[y];
			for (x = out.border; x < endX; x++) {
				rowOut[x] = rowIn[x] - rowOut[x];
			}
		}
	}

	public static void mul(ImageMatrixFloat out, float[][] outComponent, float[][] inComponent) {
		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowIn; // 1
		int     endX = out.endX; // 1

		for (int y = out.border; y < out.endY; y++) {
			rowOut = outComponent[y];
			rowIn  = inComponent[y];
			for (x = out.border; x < endX; x++) {
				rowOut[x] *= rowIn[x];
			}
		}
	}

	public static void div(ImageMatrixFloat out, float[][] outComponent, float[][] inComponent) {
		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowIn; // 1
		int     endX = out.endX; // 1

		for (int y = out.border; y < out.endY; y++) {
			rowOut = outComponent[y];
			rowIn  = inComponent[y];
			for (x = out.border; x < endX; x++) {
				rowOut[x] /= rowIn[x];
			}
		}
	}

	public static void divR(ImageMatrixFloat out, float[][] outComponent, float[][] inComponent) {
		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowIn; // 1
		int     endX = out.endX; // 1

		for (int y = out.border; y < out.endY; y++) {
			rowOut = outComponent[y];
			rowIn  = inComponent[y];
			for (x = out.border; x < endX; x++) {
				rowOut[x] = rowIn[x] / rowOut[x];
			}
		}
	}

	public static void pow(ImageMatrixFloat out, float[][] outComponent, float[][] powerComponent) {
		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowPower; // 1
		int     endX = out.endX; // 1

		for (int y = out.border; y < out.endY; y++) {
			rowOut   = outComponent[y];
			rowPower = powerComponent[y];
			for (x = out.border; x < endX; x++) {
				rowOut[x] = (float)Math.pow(rowOut[x], rowPower[x]);
			}
		}
	}

	public static void powR(ImageMatrixFloat out, float[][] outComponent, float[][] baseComponent) {
		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowBase; // 1
		int     endX = out.endX; // 1

		for (int y = out.border; y < out.endY; y++) {
			rowOut  = outComponent[y];
			rowBase = baseComponent[y];
			for (x = out.border; x < endX; x++) {
				rowOut[x] = (float)Math.pow(rowBase[x], rowOut[x]);
			}
		}
	}

	public static void hypot(ImageMatrixFloat out, float[][] outComponent, float[][] inComponent) {
		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowIn; // 1
		int     endX = out.endX; // 1

		for (int y = out.border; y < out.endY; y++) {
			rowOut = outComponent[y];
			rowIn  = inComponent[y];
			for (x = out.border; x < endX; x++) {
				rowOut[x] = (float)Math.hypot(rowOut[x], rowIn[x]);
			}
		}
	}

	public static void atan2(ImageMatrixFloat out, float[][] outComponent, float[][] inComponent) {
		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowIn; // 1
		int     endX = out.endX; // 1

		for (int y = out.border; y < out.endY; y++) {
			rowOut = outComponent[y];
			rowIn  = inComponent[y];
			for (x = out.border; x < endX; x++) {
				rowOut[x] = (float)Math.atan2(rowOut[x], rowIn[x]);
			}
		}
	}

	public static void atan2R(ImageMatrixFloat out, float[][] outComponent, float[][] inComponent) {
		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowIn; // 1
		int     endX = out.endX; // 1

		for (int y = out.border; y < out.endY; y++) {
			rowOut = outComponent[y];
			rowIn  = inComponent[y];
			for (x = out.border; x < endX; x++) {
				rowOut[x] = (float)Math.atan2(rowIn[x], rowOut[x]);
			}
		}
	}

	public static void addAdd(ImageMatrixFloat out, float[][] outComponent, float[][] offset1Component,
	                          float offset2) {
		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowIn; // 1
		int     endX = out.endX; // 1

		for (int y = out.border; y < out.endY; y++) {
			rowOut = outComponent[y];
			rowIn  = offset1Component[y];
			for (x = out.border; x < endX; x++) {
				rowOut[x] += rowIn[x] + offset2;
			}
		}
	}

	public static void mulAdd(ImageMatrixFloat out, float[][] outComponent, float[][] mulComponent, float add) {
		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowMul; // 1
		int     endX = out.endX; // 1

		for (int y = out.border; y < out.endY; y++) {
			rowOut = outComponent[y];
			rowMul = mulComponent[y];
			for (x = out.border; x < endX; x++) {
				rowOut[x] = rowOut[x] * rowMul[x] + add;
			}
		}
	}

	public static void addMul(ImageMatrixFloat out, float[][] outComponent, float[][] addComponent, float mul) {
		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowAdd; // 1
		int     endX = out.endX; // 1

		for (int y = out.border; y < out.endY; y++) {
			rowOut = outComponent[y];
			rowAdd = addComponent[y];
			for (x = out.border; x < endX; x++) {
				rowOut[x] = (rowOut[x] + rowAdd[x]) * mul;
			}
		}
	}

	public static void addMul(ImageMatrixFloat out, float[][] outComponent, float add, float[][] mulComponent) {
		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowMul; // 1
		int     endX = out.endX; // 1

		for (int y = out.border; y < out.endY; y++) {
			rowOut = outComponent[y];
			rowMul = mulComponent[y];
			for (x = out.border; x < endX; x++) {
				rowOut[x] = (rowOut[x] + add) * rowMul[x];
			}
		}
	}

	public static void mulOffset(ImageMatrixFloat out, float[][] outComponent, float[][] mulComponent, float offset) {
		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowScale; // 1
		int     endX = out.endX; // 1

		for (int y = out.border; y < out.endY; y++) {
			rowOut   = outComponent[y];
			rowScale = mulComponent[y];
			for (x = out.border; x < endX; x++) {
				rowOut[x] *= (rowScale[x] + offset);
			}
		}
	}

	public static void mulAdd(ImageMatrixFloat out, float[][] outComponent, float mul, float[][] addComponent) {
		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowOffset; // 1
		int     endX = out.endX; // 1

		for (int y = out.border; y < out.endY; y++) {
			rowOut    = outComponent[y];
			rowOffset = addComponent[y];
			for (x = out.border; x < endX; x++) {
				rowOut[x] = rowOut[x] * mul + rowOffset[x];
			}
		}
	}

	public static void addScaled(ImageMatrixFloat out, float[][] outComponent, float[][] inComponent, float scale) {
		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowIn; // 1
		int     endX = out.endX; // 1

		for (int y = out.border; y < out.endY; y++) {
			rowOut = outComponent[y];
			rowIn  = inComponent[y];
			for (x = out.border; x < endX; x++) {
				rowOut[x] += rowIn[x] * scale;
			}
		}
	}
}
