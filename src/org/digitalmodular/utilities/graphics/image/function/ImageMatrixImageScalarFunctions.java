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

import org.digitalmodular.utilities.graphics.image.ImageMatrixFloat;

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
