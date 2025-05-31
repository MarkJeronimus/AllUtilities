package nl.airsupplies.utilities.graphics.image.function;

import nl.airsupplies.utilities.graphics.image.ImageMatrixFloat;

/**
 * Functions of the form:<br> {@link ImageMatrixFloat} = ImageMatrix<br> {@link ImageMatrixFloat} = ImageMatrix &lt;
 * operator&gt;
 * ImageMatrix<br> {@link ImageMatrixFloat} = operator(ImageMatrix, ImageMatrix)<br> {@link ImageMatrixFloat} =
 * ImageMatrix
 * &lt;operator&gt; (ImageMatrix &lt;operator&gt; scalar)<br> {@link ImageMatrixFloat} = (ImageMatrix &lt;operator&gt;
 * ImageMatrix) &lt;operator&gt; scalar<br> {@link ImageMatrixFloat} = ImageMatrix &lt;operator&gt; (scalar &lt;
 * operator&gt;
 * ImageMatrix)<br> {@link ImageMatrixFloat} = (ImageMatrix &lt;operator&gt; scalar) &lt;operator&gt; ImageMatrix<br>
 *
 * @author Mark Jeronimus
 */
// Created 2012-04-05
public class ImageMatrixImageImageFunctions {
	public static void set(ImageMatrixFloat out, ImageMatrixFloat in) {
		if (!out.isCompatibleByBorderAndSize(in)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowIn; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			for (int y = out.border; y < out.endY; y++) {
				rowOut = out.matrix[z][y];
				rowIn  = in.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					rowOut[x] = rowIn[x];
				}
			}
		}
	}

	public static void add(ImageMatrixFloat out, ImageMatrixFloat in) {
		if (!out.isCompatibleByBorderAndSize(in)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowIn; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			for (int y = out.border; y < out.endY; y++) {
				rowOut = out.matrix[z][y];
				rowIn  = in.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					rowOut[x] += rowIn[x];
				}
			}
		}
	}

	public static void sub(ImageMatrixFloat out, ImageMatrixFloat in) {
		if (!out.isCompatibleByBorderAndSize(in)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowIn; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			for (int y = out.border; y < out.endY; y++) {
				rowOut = out.matrix[z][y];
				rowIn  = in.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					rowOut[x] -= rowIn[x];
				}
			}
		}
	}

	public static void subR(ImageMatrixFloat out, ImageMatrixFloat in) {
		if (!out.isCompatibleByBorderAndSize(in)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowIn; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			for (int y = out.border; y < out.endY; y++) {
				rowOut = out.matrix[z][y];
				rowIn  = in.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					rowOut[x] = rowIn[x] - rowOut[x];
				}
			}
		}
	}

	public static void mul(ImageMatrixFloat out, ImageMatrixFloat in) {
		if (!out.isCompatibleByBorderAndSize(in)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowIn; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			for (int y = out.border; y < out.endY; y++) {
				rowOut = out.matrix[z][y];
				rowIn  = in.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					rowOut[x] *= rowIn[x];
				}
			}
		}
	}

	public static void div(ImageMatrixFloat out, ImageMatrixFloat in) {
		if (!out.isCompatibleByBorderAndSize(in)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowIn; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			for (int y = out.border; y < out.endY; y++) {
				rowOut = out.matrix[z][y];
				rowIn  = in.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					rowOut[x] /= rowIn[x];
				}
			}
		}
	}

	public static void divR(ImageMatrixFloat out, ImageMatrixFloat in) {
		if (!out.isCompatibleByBorderAndSize(in)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowIn; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			for (int y = out.border; y < out.endY; y++) {
				rowOut = out.matrix[z][y];
				rowIn  = in.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					rowOut[x] = rowIn[x] / rowOut[x];
				}
			}
		}
	}

	public static void pow(ImageMatrixFloat out, ImageMatrixFloat powerImage) {
		if (!out.isCompatibleByBorderAndSize(powerImage)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowPower; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			for (int y = out.border; y < out.endY; y++) {
				rowOut   = out.matrix[z][y];
				rowPower = powerImage.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					rowOut[x] = (float)Math.pow(rowPower[x], rowOut[x]);
				}
			}
		}
	}

	public static void powR(ImageMatrixFloat out, ImageMatrixFloat baseImage) {
		if (!out.isCompatibleByBorderAndSize(baseImage)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowBase; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			for (int y = out.border; y < out.endY; y++) {
				rowOut  = out.matrix[z][y];
				rowBase = baseImage.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					rowOut[x] = (float)Math.pow(rowOut[x], rowBase[x]);
				}
			}
		}
	}

	public static void cabs(ImageMatrixFloat out, ImageMatrixFloat in) {
		if (!out.isCompatibleByBorderAndSize(in)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		// Inner loop
		int     x; // 7
		float[] rowOut; // 5
		float[] rowIn; // 2
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			for (int y = out.border; y < out.endY; y++) {
				rowOut = out.matrix[z][y];
				rowIn  = in.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					rowOut[x] = rowOut[x] * rowOut[x] + rowIn[x] * rowIn[x];
				}
			}
		}
	}

	public static void hypot(ImageMatrixFloat out, ImageMatrixFloat in) {
		if (!out.isCompatibleByBorderAndSize(in)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowIn; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			for (int y = out.border; y < out.endY; y++) {
				rowOut = out.matrix[z][y];
				rowIn  = in.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					rowOut[x] = (float)Math.hypot(rowOut[x], rowIn[x]);
				}
			}
		}
	}

	public static void atan2R(ImageMatrixFloat out, ImageMatrixFloat in) {
		if (!out.isCompatibleByBorderAndSize(in)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowIn; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			for (int y = out.border; y < out.endY; y++) {
				rowOut = out.matrix[z][y];
				rowIn  = in.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					rowOut[x] = (float)Math.atan2(rowIn[x], rowOut[x]);
				}
			}
		}
	}

	public static void min(ImageMatrixFloat out, ImageMatrixFloat in) {
		if (!out.isCompatibleByBorderAndSize(in)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowIn; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			for (int y = out.border; y < out.endY; y++) {
				rowOut = out.matrix[z][y];
				rowIn  = in.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					rowOut[x] = Math.min(rowIn[x], rowOut[x]);
				}
			}
		}
	}

	public static void max(ImageMatrixFloat out, ImageMatrixFloat in) {
		if (!out.isCompatibleByBorderAndSize(in)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowIn; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			for (int y = out.border; y < out.endY; y++) {
				rowOut = out.matrix[z][y];
				rowIn  = in.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					rowOut[x] = Math.max(rowIn[x], rowOut[x]);
				}
			}
		}
	}

	public static void addAdd(ImageMatrixFloat out, ImageMatrixFloat offset1, float offset2) {
		if (!out.isCompatibleByBorderAndSize(offset1)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowOffset1; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			for (int y = out.border; y < out.endY; y++) {
				rowOut     = out.matrix[z][y];
				rowOffset1 = offset1.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					rowOut[x] += rowOffset1[x] + offset2;
				}
			}
		}
	}

	public static void addMul(ImageMatrixFloat out, ImageMatrixFloat add, float mul) {
		if (!out.isCompatibleByBorderAndSize(add)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowAdd; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			for (int y = out.border; y < out.endY; y++) {
				rowOut = out.matrix[z][y];
				rowAdd = add.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					rowOut[x] = (rowOut[x] + rowAdd[x]) * mul;
				}
			}
		}
	}

	public static void addMul(ImageMatrixFloat out, float add, ImageMatrixFloat mul) {
		if (!out.isCompatibleByBorderAndSize(mul)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowMul; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			for (int y = out.border; y < out.endY; y++) {
				rowOut = out.matrix[z][y];
				rowMul = mul.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					rowOut[x] = (rowOut[x] + add) * rowMul[x];
				}
			}
		}
	}

	public static void mulAdd(ImageMatrixFloat out, ImageMatrixFloat scale, float offset) {
		if (!out.isCompatibleByBorderAndSize(scale)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowScale; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			for (int y = out.border; y < out.endY; y++) {
				rowOut   = out.matrix[z][y];
				rowScale = scale.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					rowOut[x] = rowOut[x] * rowScale[x] + offset;
				}
			}
		}
	}

	public static void mulOffset(ImageMatrixFloat out, ImageMatrixFloat mul, float offset) {
		if (!out.isCompatibleByBorderAndSize(mul)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowMul; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			for (int y = out.border; y < out.endY; y++) {
				rowOut = out.matrix[z][y];
				rowMul = mul.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					rowOut[x] *= (rowMul[x] + offset);
				}
			}
		}
	}

	public static void mulAdd(ImageMatrixFloat out, float mul, ImageMatrixFloat add) {
		if (!out.isCompatibleByBorderAndSize(add)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowAdd; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			for (int y = out.border; y < out.endY; y++) {
				rowOut = out.matrix[z][y];
				rowAdd = add.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					rowOut[x] = rowOut[x] * mul + rowAdd[x];
				}
			}
		}
	}

	public static void addScaled(ImageMatrixFloat out, ImageMatrixFloat offset, float scale) {
		if (!out.isCompatibleByBorderAndSize(offset)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowOffset; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			for (int y = out.border; y < out.endY; y++) {
				rowOut    = out.matrix[z][y];
				rowOffset = offset.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					rowOut[x] += rowOffset[x] * scale;
				}
			}
		}
	}
}
