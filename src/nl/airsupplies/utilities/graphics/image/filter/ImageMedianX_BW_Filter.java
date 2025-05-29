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

package nl.airsupplies.utilities.graphics.image.filter;

import nl.airsupplies.utilities.graphics.image.ImageMatrixFloat;

/**
 * Convolution of the shape:
 *
 * <pre>
 * -b a b
 * </pre>
 * <p>
 * for parameter coefficients = {a, b}
 *
 * @author Mark Jeronimus
 */
// Created 2011-11-22
public class ImageMedianX_BW_Filter extends ImageFilter {
	@Override
	public void filter(ImageMatrixFloat in, ImageMatrixFloat out) {
		if (in.border < 1) {
			throw new IllegalArgumentException("Border too small");
		}
		if (!in.isCompatibleByBorderAndSize(out)) {
			throw new IllegalArgumentException("Images not compatible");
		}

		// Inner loop
		int     x; // 7.3 (6~8)
		float[] rowIn; // 5.3 (4~6)
		int     endX = in.endX; // 1
		float[] rowOut; // 1

		for (int z = 0; z < in.numComponents; z++) {
			for (int y = in.border; y < in.endY; y++) {
				rowIn  = in.matrix[z][y];
				rowOut = out.matrix[z][y];
				for (x = in.border; x < endX; x++) {
					rowOut[x] = rowIn[x - 1] != rowIn[x + 1] ? rowIn[x] : rowIn[x - 1];
				}
			}
		}
	}
}
