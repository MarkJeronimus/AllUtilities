package nl.airsupplies.utilities.graphics.image.function;

import nl.airsupplies.utilities.graphics.image.ImageMatrixFloat;

/**
 * Functions of the form:<br> {@link ImageMatrixFloat} = vector<br> {@link ImageMatrixFloat} = ImageMatrix &lt;
 * operator&gt;
 * vector<br> {@link ImageMatrixFloat} = ImageMatrix &lt;operator&gt; vector &lt;operator&gt; vector<br>
 *
 * @author Mark Jeronimus
 */
// Created 2012-04-05
public class ImageMatrixImageVectorFunctions {
	public static void set(ImageMatrixFloat out, float... in) {
		if (in.length != out.numComponents) {
			throw new IllegalArgumentException("Number of components differs");
		}

		// Inner loop
		int     x; // 4
		float[] row; // 2
		float   inz; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			inz = in[z];
			for (int y = out.border; y < out.endY; y++) {
				row = out.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					row[x] = inz;
				}
			}
		}
	}

	public static void add(ImageMatrixFloat out, float... in) {
		if (in.length != out.numComponents) {
			throw new IllegalArgumentException("Number of components differs");
		}

		// Inner loop
		int     x; // 4
		float[] row; // 2
		float   inz; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			inz = in[z];
			for (int y = out.border; y < out.endY; y++) {
				row = out.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					row[x] += inz;
				}
			}
		}
	}

	public static void sub(ImageMatrixFloat out, float... in) {
		if (in.length != out.numComponents) {
			throw new IllegalArgumentException("Number of components differs");
		}

		// Inner loop
		int     x; // 4
		float[] row; // 2
		float   inz; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			inz = in[z];
			for (int y = out.border; y < out.endY; y++) {
				row = out.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					row[x] -= inz;
				}
			}
		}
	}

	public static void subR(ImageMatrixFloat out, float... in) {
		if (in.length != out.numComponents) {
			throw new IllegalArgumentException("Number of components differs");
		}

		// Inner loop
		int     x; // 4
		float[] row; // 2
		float   inz; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			inz = in[z];
			for (int y = out.border; y < out.endY; y++) {
				row = out.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					row[x] = inz - row[x];
				}
			}
		}
	}

	public static void mul(ImageMatrixFloat out, float... in) {
		if (in.length != out.numComponents) {
			throw new IllegalArgumentException("Number of components differs");
		}

		// Inner loop
		int     x; // 4
		float[] row; // 2
		float   inz; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			inz = in[z];
			for (int y = out.border; y < out.endY; y++) {
				row = out.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					row[x] *= inz;
				}
			}
		}
	}

	public static void div(ImageMatrixFloat out, float... in) {
		if (in.length != out.numComponents) {
			throw new IllegalArgumentException("Number of components differs");
		}

		// Inner loop
		int     x; // 4
		float[] row; // 2
		float   inz; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			inz = in[z];
			for (int y = out.border; y < out.endY; y++) {
				row = out.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					row[x] /= inz;
				}
			}
		}
	}

	public static void divR(ImageMatrixFloat out, float... in) {
		if (in.length != out.numComponents) {
			throw new IllegalArgumentException("Number of components differs");
		}

		// Inner loop
		int     x; // 4
		float[] row; // 2
		float   inz; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			inz = in[z];
			for (int y = out.border; y < out.endY; y++) {
				row = out.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					row[x] = inz / row[x];
				}
			}
		}
	}

	public static void mod(ImageMatrixFloat out, float... in) {
		if (in.length != out.numComponents) {
			throw new IllegalArgumentException("Number of components differs");
		}

		// Inner loop
		int     x; // 4
		float[] row; // 2
		float   inz; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			inz = in[z];
			for (int y = out.border; y < out.endY; y++) {
				row = out.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					row[x] -= (float)Math.floor(row[x] / inz) * inz;
				}
			}
		}
	}

	public static void modR(ImageMatrixFloat out, float... in) {
		if (in.length != out.numComponents) {
			throw new IllegalArgumentException("Number of components differs");
		}

		// Inner loop
		int     x; // 4
		float[] row; // 2
		float   inz; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			inz = in[z];
			for (int y = out.border; y < out.endY; y++) {
				row = out.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					row[x] = inz - (float)Math.floor(inz / row[x]) * row[x];
				}
			}
		}
	}

	public static void pow(ImageMatrixFloat out, float... power) {
		if (power.length != out.numComponents) {
			throw new IllegalArgumentException("Number of components differs");
		}

		// Inner loop
		int     x; // 4
		float[] row; // 2
		float   powerz; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			powerz = power[z];
			for (int y = out.border; y < out.endY; y++) {
				row = out.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					row[x] = (float)Math.pow(row[x], powerz);
				}
			}
		}
	}

	public static void powR(ImageMatrixFloat out, float... base) {
		if (base.length != out.numComponents) {
			throw new IllegalArgumentException("Number of components differs");
		}

		// Inner loop
		int     x; // 4
		float[] row; // 2
		float   basez; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			basez = base[z];
			for (int y = out.border; y < out.endY; y++) {
				row = out.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					row[x] = (float)Math.pow(basez, row[x]);
				}
			}
		}
	}

	public static void showUnderflow(ImageMatrixFloat out, float[] in) {
		if (in.length != out.numComponents) {
			throw new IllegalArgumentException("Number of components differs");
		}

		// Inner loop
		int     x; // 4
		float[] row; // 2
		float   inz; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			inz = in[z];
			for (int y = out.border; y < out.endY; y++) {
				row = out.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					if (row[x] < 0) {
						row[x] = inz;
					}
				}
			}
		}
	}

	public static void showOverflow(ImageMatrixFloat out, float[] in) {
		if (in.length != out.numComponents) {
			throw new IllegalArgumentException("Number of components differs");
		}

		// Inner loop
		int     x; // 4
		float[] row; // 2
		float   inz; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			inz = in[z];
			for (int y = out.border; y < out.endY; y++) {
				row = out.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					if (row[x] > 0) {
						row[x] = inz;
					}
				}
			}
		}
	}

	public static void showBlacks(ImageMatrixFloat out, float[] in) {
		if (in.length != out.numComponents) {
			throw new IllegalArgumentException("Number of components differs");
		}

		// Inner loop
		int     x; // 4
		float[] row; // 2
		float   inz; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			inz = in[z];
			for (int y = out.border; y < out.endY; y++) {
				row = out.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					if (row[x] <= 0) {
						row[x] = inz;
					}
				}
			}
		}
	}

	public static void showWhites(ImageMatrixFloat out, float[] in) {
		if (in.length != out.numComponents) {
			throw new IllegalArgumentException("Number of components differs");
		}

		// Inner loop
		int     x; // 4
		float[] row; // 2
		float   inz; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			inz = in[z];
			for (int y = out.border; y < out.endY; y++) {
				row = out.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					if (row[x] >= 1) {
						row[x] = inz;
					}
				}
			}
		}
	}

	public static void threshold(ImageMatrixFloat out, float[] in) {
		if (in.length != out.numComponents) {
			throw new IllegalArgumentException("Number of components differs");
		}

		// Inner loop
		int     x; // 4
		float[] row; // 2
		float   inz; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			inz = in[z];
			for (int y = out.border; y < out.endY; y++) {
				row = out.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					row[x] = row[x] > inz ? 1 : 0;
				}
			}
		}
	}
}
