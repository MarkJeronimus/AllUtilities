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

package org.digitalmodular.utilities.graphics.image.function;

import org.digitalmodular.utilities.container.Vector2f;
import org.digitalmodular.utilities.graphics.image.ImageMatrixFloat;

/**
 * Functions of the form:<br> scalar = &lt;operator&gt;({@link ImageMatrixFloat})<br> vector = &lt;operator&gt;({@link
 * ImageMatrixFloat})<br>
 *
 * @author Mark Jeronimus
 */
// Created 2012-04-05
public class ImageMatrixQueryFunctions {
	public static float findMin(ImageMatrixFloat img) {
		// Inner loop
		int     x; // 3
		float   min  = Float.MAX_VALUE; // 1.5 (1~2)
		float[] row; // 1
		int     endX = img.endX; // 1

		for (int z = 0; z < img.numComponents; z++) {
			for (int y = img.border; y < img.endY; y++) {
				row = img.matrix[z][y];
				for (x = img.border; x < endX; x++) {
					float c = row[x];
					if (c == c) {
						if (min > c) {
							min = c;
						}
					}
				}
			}
		}

		return min;
	}

	public static float findMax(ImageMatrixFloat img) {
		// Inner loop
		int     x; // 3
		float   max  = -Float.MAX_VALUE; // 1.5 (1~2)
		float[] row; // 1
		int     endX = img.endX; // 1

		for (int z = 0; z < img.numComponents; z++) {
			for (int y = img.border; y < img.endY; y++) {
				row = img.matrix[z][y];
				for (x = img.border; x < endX; x++) {
					float c = row[x];
					if (c == c) {
						if (max < c) {
							max = c;
						}
					}
				}
			}
		}

		return max;
	}

	public static Vector2f findMinMax(ImageMatrixFloat img) {
		// Inner loop
		float   c; // 4.5 (4~6)
		int     x; // 3
		float   min  = Float.MAX_VALUE; // 1.5 (1~2)
		float[] row; // 1
		int     endX = img.endX; // 1
		float   max  = -Float.MAX_VALUE; // 0.5 (0~1)

		for (int z = 0; z < img.numComponents; z++) {
			for (int y = img.border; y < img.endY; y++) {
				row = img.matrix[z][y];
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
		}

		return new Vector2f(min, max);
	}

	public static Vector2f findBiasAmplitude(ImageMatrixFloat img) {
		// Inner loop
		float   c; // 4.5 (4~6)
		int     x; // 3
		float   min  = Integer.MAX_VALUE; // 1.5 (1~2)
		float[] row; // 1
		int     endX = img.endX; // 1
		float   max  = -Integer.MAX_VALUE; // 0.5 (0~1)

		for (int z = 0; z < img.numComponents; z++) {
			for (int y = img.border; y < img.endY; y++) {
				row = img.matrix[z][y];
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
		}

		return new Vector2f((max + min) * 0.5f, (max - min) * 0.5f);
	}

	public static float findAverage(ImageMatrixFloat img) {
		// Inner loop
		float   c; // 4
		int     x; // 4
		float[] row; // 1
		int     endX = img.endX; // 1
		float   sum  = 0; // 1

		for (int z = 0; z < img.numComponents; z++) {
			for (int y = img.border; y < img.endY; y++) {
				row = img.matrix[z][y];
				for (x = img.border; x < endX; x++) {
					c = row[x];
					if (c == c) {
						sum += c;
					}
				}
			}
		}

		return sum / (img.numColumns * img.numRows * img.numComponents);
	}

	public static void makeHistogram(ImageMatrixFloat img, int[] hist) {
		// Inner loop
		float   c; // 4
		int     x; // 4
		int     bin; // 2.1 (2~4)
		int     len  = hist.length - 1; // 2.1 (2~3)
		float[] row; // 1
		int     endX = img.endX; // 1

		for (int z = 0; z < img.numComponents; z++) {
			for (int y = img.border; y < img.endY; y++) {
				row = img.matrix[z][y];
				for (x = img.border; x < endX; x++) {
					c = row[x];
					if (c != c) {
						continue;
					}

					bin = (int)(c * len + 0.5);
					if (bin >= len) {
						bin = len;
					} else if (bin < 0) {
						bin = 0;
					}
					hist[bin]++;
				}
			}
		}
	}
}
