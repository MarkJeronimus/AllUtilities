package nl.airsupplies.utilities.graphics.image.function;

import nl.airsupplies.utilities.graphics.image.ImageMatrixFloat;

/**
 * Functions of the form:<br> {@link ImageMatrixFloat} = scalar<br> {@link ImageMatrixFloat} = ImageMatrix &lt;
 * operator&gt;
 * scalar<br> {@link ImageMatrixFloat} = ImageMatrix &lt;operator&gt; scalar &lt;operator&gt; scalar<br>
 *
 * @author Mark Jeronimus
 */
// Created 2012-04-05
public class ImageMatrixImageScalarFunctions {
	/**
	 * out = in
	 */
	public static void set(ImageMatrixFloat out, float in) {
		// Inner loop
		int     x; // 4
		float[] row; // 2
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			for (int y = out.border; y < out.endY; y++) {
				row = out.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					row[x] = in;
				}
			}
		}
	}

	/**
	 * out += in
	 */
	public static void add(ImageMatrixFloat out, float in) {
		// Inner loop
		int     x; // 4
		float[] row; // 2
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			for (int y = out.border; y < out.endY; y++) {
				row = out.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					row[x] += in;
				}
			}
		}
	}

	/**
	 * out -= in
	 */
	public static void sub(ImageMatrixFloat out, float in) {
		// Inner loop
		int     x; // 4
		float[] row; // 2
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			for (int y = out.border; y < out.endY; y++) {
				row = out.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					row[x] -= in;
				}
			}
		}
	}

	/**
	 * out = in - out
	 */
	public static void subR(ImageMatrixFloat out, float in) {
		// Inner loop
		int     x; // 4
		float[] row; // 2
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			for (int y = out.border; y < out.endY; y++) {
				row = out.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					row[x] = in - row[x];
				}
			}
		}
	}

	/**
	 * out *= in
	 */
	public static void mul(ImageMatrixFloat out, float in) {
		// Inner loop
		int     x; // 4
		float[] row; // 2
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			for (int y = out.border; y < out.endY; y++) {
				row = out.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					row[x] *= in;
				}
			}
		}
	}

	/**
	 * out /= in
	 */
	public static void div(ImageMatrixFloat out, float in) {
		// Inner loop
		int     x; // 4
		float[] row; // 2
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			for (int y = out.border; y < out.endY; y++) {
				row = out.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					row[x] /= in;
				}
			}
		}
	}

	public static void divR(ImageMatrixFloat out, float in) {
		// Inner loop
		int     x; // 4
		float[] row; // 2
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			for (int y = out.border; y < out.endY; y++) {
				row = out.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					row[x] = in / row[x];
				}
			}
		}
	}

	/**
	 * out /= in
	 */
	public static void pow(ImageMatrixFloat out, float power) {
		// Inner loop
		int     x; // 4
		float[] row; // 2
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			for (int y = out.border; y < out.endY; y++) {
				row = out.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					row[x] = (float)Math.pow(row[x], power);
				}
			}
		}
	}

	public static void powR(ImageMatrixFloat out, float base) {
		// Inner loop
		int     x; // 4
		float[] row; // 2
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			for (int y = out.border; y < out.endY; y++) {
				row = out.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					row[x] = (float)Math.pow(base, row[x]);
				}
			}
		}
	}

	public static void addMul(ImageMatrixFloat out, float add, float mul) {
		// Inner loop
		int     x; // 4
		float[] row; // 2
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			for (int y = out.border; y < out.endY; y++) {
				row = out.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					row[x] = (row[x] + add) * mul;
				}
			}
		}
	}

	public static void mulAdd(ImageMatrixFloat out, float mul, float add) {
		// Inner loop
		int     x; // 4
		float[] row; // 2
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			for (int y = out.border; y < out.endY; y++) {
				row = out.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					row[x] = row[x] * mul + add;
				}
			}
		}
	}
}
