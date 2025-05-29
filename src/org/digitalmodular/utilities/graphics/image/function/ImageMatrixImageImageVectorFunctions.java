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
 * Functions of the form:<br> {@link ImageMatrixFloat} = ImageMatrix &lt;operator&gt; ImageMatrix &lt;operator&gt;
 * vector<br>
 * {@link ImageMatrixFloat} = ImageMatrix &lt;operator&gt; (ImageMatrix &lt;operator&gt; vector)<br>
 *
 * @author Mark Jeronimus
 */
// Created 2012-04-05
public class ImageMatrixImageImageVectorFunctions {
	public static void addAdd(ImageMatrixFloat out, ImageMatrixFloat add1, float... add2) {
		if (!out.isCompatibleByBorderAndSize(add1)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowAdd1; // 1
		float   addz; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			addz = add2[z];
			for (int y = out.border; y < out.endY; y++) {
				rowOut  = out.matrix[z][y];
				rowAdd1 = add1.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					rowOut[x] += rowAdd1[x] + addz;
				}
			}
		}
	}

	public static void addMul(ImageMatrixFloat out, ImageMatrixFloat add, float... mul) {
		if (!out.isCompatibleByBorderAndSize(add)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowAdd; // 1
		float   mulz; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			mulz = mul[z];
			for (int y = out.border; y < out.endY; y++) {
				rowOut = out.matrix[z][y];
				rowAdd = add.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					rowOut[x] = (rowOut[x] + rowAdd[x]) * mulz;
				}
			}
		}
	}

	public static void addMulR(ImageMatrixFloat out, ImageMatrixFloat mul, float... add) {
		if (!out.isCompatibleByBorderAndSize(mul)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowMul; // 1
		float   addz; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			addz = add[z];
			for (int y = out.border; y < out.endY; y++) {
				rowOut = out.matrix[z][y];
				rowMul = mul.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					rowOut[x] = (rowOut[x] + addz) * rowMul[x];
				}
			}
		}
	}

	public static void addScaled(ImageMatrixFloat out, ImageMatrixFloat add, float... scale) {
		if (!out.isCompatibleByBorderAndSize(add)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowAdd; // 1
		float   scalez; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			scalez = scale[z];
			for (int y = out.border; y < out.endY; y++) {
				rowOut = out.matrix[z][y];
				rowAdd = add.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					rowOut[x] += rowAdd[x] * scalez;
				}
			}
		}
	}

	public static void mulOffset(ImageMatrixFloat out, ImageMatrixFloat mul, float... offset) {
		if (!out.isCompatibleByBorderAndSize(mul)) {
			throw new IllegalArgumentException("Other ImageMatrix not compatible");
		}

		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowMul; // 1
		float   offsetz; // 1
		int     endX = out.endX; // 1

		for (int z = 0; z < out.numComponents; z++) {
			offsetz = offset[z];
			for (int y = out.border; y < out.endY; y++) {
				rowOut = out.matrix[z][y];
				rowMul = mul.matrix[z][y];
				for (x = out.border; x < endX; x++) {
					rowOut[x] += rowMul[x] * offsetz;
				}
			}
		}
	}
}
