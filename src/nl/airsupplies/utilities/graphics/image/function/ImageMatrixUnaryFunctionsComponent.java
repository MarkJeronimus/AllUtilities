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

package nl.airsupplies.utilities.graphics.image.function;

import nl.airsupplies.utilities.container.Vector2f;
import nl.airsupplies.utilities.graphics.image.ImageMatrixFloat;

/**
 * Functions of the form:<br> {@link ImageMatrixFloat} = &lt;operator&gt;(ImageMatrix)<br> {@link ImageMatrixFloat} =
 * &lt;operator&gt;(ImageMatrix, scalar)<br> {@link ImageMatrixFloat} = &lt;operator&gt;(ImageMatrix, scalar, scalar)
 * <br>
 *
 * @author Mark Jeronimus
 */
// Created 2012-04-05
public class ImageMatrixUnaryFunctionsComponent {
	public static void invert(ImageMatrixFloat img, int component) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 2
		int     endX = img.endX; // 1

		for (int y = img.border; y < img.endY; y++) {
			row = img.matrix[component][y];
			for (x = img.border; x < endX; x++) {
				row[x] = -row[x];
			}
		}
	}

	public static void negative(ImageMatrixFloat img, int component) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 2
		int     endX = img.endX; // 1

		for (int y = img.border; y < img.endY; y++) {
			row = img.matrix[component][y];
			for (x = img.border; x < endX; x++) {
				row[x] = 1 - row[x];
			}
		}
	}

	public static void clamp(ImageMatrixFloat img, int component) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 2
		int     endX = img.endX; // 1

		for (int y = img.border; y < img.endY; y++) {
			row = img.matrix[component][y];
			for (x = img.border; x < endX; x++) {
				if (row[x] < 0) {
					row[x] = 0;
				} else if (row[x] >= 1) {
					row[x] = 1;
				}
			}
		}
	}

	public static void clamp(ImageMatrixFloat img, int component, float black, float white) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 2
		int     endX = img.endX; // 1

		for (int y = img.border; y < img.endY; y++) {
			row = img.matrix[component][y];
			for (x = img.border; x < endX; x++) {
				if (row[x] < black) {
					row[x] = black;
				} else if (row[x] >= white) {
					row[x] = white;
				}
			}
		}
	}

	public static void clampLower(ImageMatrixFloat img, int component, float black) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 2
		int     endX = img.endX; // 1

		for (int y = img.border; y < img.endY; y++) {
			row = img.matrix[component][y];
			for (x = img.border; x < endX; x++) {
				if (row[x] < black) {
					row[x] = black;
				}
			}
		}
	}

	public static void clampUpper(ImageMatrixFloat img, int component, float white) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 2
		int     endX = img.endX; // 1

		for (int y = img.border; y < img.endY; y++) {
			row = img.matrix[component][y];
			for (x = img.border; x < endX; x++) {
				if (row[x] >= white) {
					row[x] = white;
				}
			}
		}
	}

	public static void normalize(ImageMatrixFloat img, int component) {
		float mul = img.findMax();

		mul = mul == 0 ? Float.NaN : 1 / mul;
		img.mulComponent(component, mul);
	}

	public static void stretchHistogram(ImageMatrixFloat img, int component) {
		Vector2f minMax = img.findBiasAmplitude();

		minMax.y -= minMax.x;

		float mul = minMax.y == 0 ? Float.NaN : 1 / minMax.y;
		float add = -minMax.x;
		img.mulAddComponent(component, mul, add);
	}

	public static void abs(ImageMatrixFloat img, int component) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 3
		int     endX = img.endX; // 1

		for (int y = img.border; y < img.endY; y++) {
			row = img.matrix[component][y];
			for (x = img.border; x < endX; x++) {
				if (row[x] < 0) {
					row[x] = -row[x];
				}
			}
		}
	}

	public static void recip(ImageMatrixFloat img, int component) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 2
		int     endX = img.endX; // 1

		for (int y = img.border; y < img.endY; y++) {
			row = img.matrix[component][y];
			for (x = img.border; x < endX; x++) {
				row[x] = 1 / row[x];
			}
		}
	}

	public static void sqr(ImageMatrixFloat img, int component) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 2
		int     endX = img.endX; // 1

		for (int y = img.border; y < img.endY; y++) {
			row = img.matrix[component][y];
			for (x = img.border; x < endX; x++) {
				row[x] *= row[x];
			}
		}
	}

	public static void sqrt(ImageMatrixFloat img, int component) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 2
		int     endX = img.endX; // 1

		for (int y = img.border; y < img.endY; y++) {
			row = img.matrix[component][y];
			for (x = img.border; x < endX; x++) {
				row[x] = (float)Math.sqrt(row[x]);
			}
		}
	}

	public static void cube(ImageMatrixFloat img, int component) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 3
		int     endX = img.endX; // 1

		for (int y = img.border; y < img.endY; y++) {
			row = img.matrix[component][y];
			for (x = img.border; x < endX; x++) {
				row[x] *= row[x] * row[x];
			}
		}
	}

	public static void cbrt(ImageMatrixFloat img, int component) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 2
		int     endX = img.endX; // 1

		for (int y = img.border; y < img.endY; y++) {
			row = img.matrix[component][y];
			for (x = img.border; x < endX; x++) {
				if (row[x] < 0) {
					row[x] = (float)Math.cbrt(row[x]);
				}
			}
		}
	}

	public static void exp(ImageMatrixFloat img, int component) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 2
		int     endX = img.endX; // 1

		for (int y = img.border; y < img.endY; y++) {
			row = img.matrix[component][y];
			for (x = img.border; x < endX; x++) {
				row[x] = (float)Math.cbrt(row[x]);
			}
		}
	}

	public static void sin(ImageMatrixFloat img, int component) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 2
		int     endX = img.endX; // 1

		for (int y = img.border; y < img.endY; y++) {
			row = img.matrix[component][y];
			for (x = img.border; x < endX; x++) {
				row[x] = (float)Math.sin(row[x]);
			}
		}
	}

	public static void cos(ImageMatrixFloat img, int component) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 2
		int     endX = img.endX; // 1

		for (int y = img.border; y < img.endY; y++) {
			row = img.matrix[component][y];
			for (x = img.border; x < endX; x++) {
				row[x] = (float)Math.cos(row[x]);
			}
		}
	}

	public static void tan(ImageMatrixFloat img, int component) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 2
		int     endX = img.endX; // 1

		for (int y = img.border; y < img.endY; y++) {
			row = img.matrix[component][y];
			for (x = img.border; x < endX; x++) {
				row[x] = (float)Math.tan(row[x]);
			}
		}
	}

	public static void atan(ImageMatrixFloat img, int component) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 2
		int     endX = img.endX; // 1

		for (int y = img.border; y < img.endY; y++) {
			row = img.matrix[component][y];
			for (x = img.border; x < endX; x++) {
				row[x] = (float)Math.atan(row[x]);
			}
		}
	}
}
