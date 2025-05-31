package nl.airsupplies.utilities.graphics.image.function;

import nl.airsupplies.utilities.graphics.image.ImageMatrixFloat;

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
				rowOut  = out.matrix[z][y];
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
				rowOut   = out.matrix[z][y];
				rowMul   = mul.matrix[z][y];
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
				rowOut    = out.matrix[z][y];
				rowMul    = mul.matrix[z][y];
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
				rowOut  = out.matrix[z][y];
				rowMul1 = mul1.matrix[z][y];
				rowMul2 = mul2.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					rowOut[x] *= rowMul1[x] * rowMul2[x];
				}
			}
		}
	}
}
