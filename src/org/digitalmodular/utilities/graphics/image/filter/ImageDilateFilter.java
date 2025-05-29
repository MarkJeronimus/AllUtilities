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

package org.digitalmodular.utilities.graphics.image.filter;

import java.util.Arrays;

import org.digitalmodular.utilities.graphics.image.ImageMatrixFloat;

/**
 * @author Mark Jeronimus
 */
// Created 2012-04-19
public class ImageDilateFilter extends ImageFilter {
	@Override
	public void filter(ImageMatrixFloat in, ImageMatrixFloat out) {
		// Inner loop
		int     x; // 12
		float[] rowIn; // 5
		float[] lineBuffer0 = new float[in.numColumns]; // 1.75
		float[] lineBuffer1 = new float[in.numColumns]; // 1.75
		float[] lineBuffer2 = new float[in.numColumns]; // 1.5
		int     endX        = in.endX; // 1
		float[] rowOut; // 1

		for (int z = 0; z < in.numComponents; z++) {
			Arrays.fill(lineBuffer0, -Float.MAX_VALUE);

			rowIn = in.matrix[z][in.border];
			for (x = in.border; x < endX; x++) {
				lineBuffer1[x] = rowIn[x - 1] > rowIn[x] ? Math.max(rowIn[x - 1], rowIn[x + 1])
				                                         : Math.max(rowIn[x], rowIn[x + 1]);
			}

			for (int y = in.border; y < in.endY; y++) {
				rowIn  = in.matrix[z][y + 1];
				rowOut = out.matrix[z][y];

				for (x = in.border; x < endX; x++) {
					lineBuffer2[x] = rowIn[x - 1] > rowIn[x] ? Math.max(rowIn[x - 1], rowIn[x + 1])
					                                         : Math.max(rowIn[x], rowIn[x + 1]);
					rowOut[x]      = lineBuffer0[x] > lineBuffer1[x] ? Math.max(lineBuffer0[x], lineBuffer2[x])
					                                                 : Math.max(lineBuffer1[x], lineBuffer2[x]);
				}

				float[] temp = lineBuffer0;
				lineBuffer0 = lineBuffer1;
				lineBuffer1 = lineBuffer2;
				lineBuffer2 = temp;
			}
		}
	}
}
