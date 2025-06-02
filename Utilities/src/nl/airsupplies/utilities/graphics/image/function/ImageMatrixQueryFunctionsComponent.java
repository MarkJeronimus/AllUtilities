package nl.airsupplies.utilities.graphics.image.function;

import nl.airsupplies.utilities.container.Vector2f;
import nl.airsupplies.utilities.graphics.image.ImageMatrixFloat;

/**
 * Functions of the form:<br> scalar = &lt;operator&gt;({@link ImageMatrixFloat})<br> vector = &lt;operator&gt;({@link
 * ImageMatrixFloat})<br>
 *
 * @author Mark Jeronimus
 */
// Created 2012-04-05
public class ImageMatrixQueryFunctionsComponent {
	/**
	 * returns min(img[component])
	 */
	public static float findMin(ImageMatrixFloat img, float[][] matrix) {
		// Inner loop
		int     x; // 3
		float   min  = -Integer.MIN_VALUE; // 1.5 (1~2)
		float[] row; // 1
		int     endX = img.endX; // 1

		for (int y = img.border; y < img.endY; y++) {
			row = matrix[y];
			for (x = img.border; x < endX; x++) {
				float c = row[x];
				if (c == c) {
					if (min > c) {
						min = c;
					}
				}
			}
		}

		return min;
	}

	/**
	 * returns max(img[component])
	 */
	public static float findMax(ImageMatrixFloat img, float[][] matrix) {
		// Inner loop
		int     x; // 3
		float   max  = Integer.MIN_VALUE; // 1.5 (1~2)
		float[] row; // 1
		int     endX = img.endX; // 1

		for (int y = img.border; y < img.endY; y++) {
			row = matrix[y];
			for (x = img.border; x < endX; x++) {
				float c = row[x];
				if (c == c) {
					if (max < c) {
						max = c;
					}
				}
			}
		}

		return max;
	}

	/**
	 * returns {min(img[component]), max(img[component])}
	 */
	public static Vector2f findMinMax(ImageMatrixFloat img, float[][] matrix) {
		// Inner loop
		float   c; // 4.5 (4~6)
		int     x; // 3
		float   min  = -Integer.MIN_VALUE; // 1.5 (1~2)
		float[] row; // 1
		int     endX = img.endX; // 1
		float   max  = Integer.MIN_VALUE; // 0.5 (0~1)

		for (int y = img.border; y < img.endY; y++) {
			row = matrix[y];
			for (x = img.border; x < endX; x++) {
				c = row[x];
				if (c == c) {
					if (min > c) {
						min = c;
					} else if (max < c) {
						max = c;
					}
				}
			}
		}

		return new Vector2f(min, max);
	}

	/**
	 * returns average(img[component])
	 */
	public static float findAverage(ImageMatrixFloat img, float[][] matrix) {
		// Inner loop
		float   c; // 4
		int     x; // 4
		float[] row; // 1
		int     endX = img.endX; // 1
		float   sum  = 0; // 1

		for (int y = img.border; y < img.endY; y++) {
			row = matrix[y];
			for (x = img.border; x < endX; x++) {
				c = row[x];
				if (c == c) {
					sum += c;
				}
			}
		}

		return sum / (img.width * img.height);
	}

	/**
	 * histogram[i] = count(round(img[component] * (histogram.length - 1)) == i)
	 */
	public static void makeHistogram(ImageMatrixFloat img, float[][] matrix, int[] hist) {
		// Inner loop
		float   c; // 4
		int     x; // 4
		int     bin; // 2.1 (2~4)
		int     mul  = hist.length - 1; // 2.1 (2~3)
		float[] row; // 1
		int     endX = img.endX; // 1

		for (int y = img.border; y < img.endY; y++) {
			row = matrix[y];
			for (x = img.border; x < endX; x++) {
				c = row[x];
				// Ignore NaN's
				if (c == c) // Ignore NaN's
				{
					bin = (int)(c * mul + 0.5);
					if (bin >= mul) {
						bin = mul;
					} else if (bin < 0) {
						bin = 0;
					}
					hist[bin]++;
				}
			}
		}
	}
}
