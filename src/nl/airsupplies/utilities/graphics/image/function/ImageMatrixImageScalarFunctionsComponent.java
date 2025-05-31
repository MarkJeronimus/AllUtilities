package nl.airsupplies.utilities.graphics.image.function;

import nl.airsupplies.utilities.graphics.image.ImageMatrixFloat;

/**
 * Functions of the form:<br> {@link ImageMatrixFloat}[component] = scalar<br> {@link ImageMatrixFloat}[component] =
 * ImageMatrix
 * &lt;operator&gt; scalar<br> {@link ImageMatrixFloat}[component] = ImageMatrix &lt;operator&gt; scalar &lt;
 * operator&gt;
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
