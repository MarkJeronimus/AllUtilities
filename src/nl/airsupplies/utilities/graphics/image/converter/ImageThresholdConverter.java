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

package nl.airsupplies.utilities.graphics.image.converter;

import nl.airsupplies.utilities.graphics.image.ImageMatrixFloat;

/**
 * Convert to opaque. Premultiplies opacity, is available.
 *
 * @author Mark Jeronimus
 */
// Created 2011-11-22
public class ImageThresholdConverter extends ImageConverter {
	public float thresholdValue;

	public ImageThresholdConverter() {
		this(0.5f);
	}

	public ImageThresholdConverter(float threshold) {
		thresholdValue = threshold;
	}

	@Override
	public void convertSelf(ImageMatrixFloat image) {
		// Inner loop
		int     x; // 4
		float[] row; // 2
		int     endX           = image.endX; // 1
		float   thresholdValue = this.thresholdValue; // 1

		for (int z = 0; z < image.numComponents; z++) {
			for (int y = image.border; y < image.endY; y++) {
				row = image.matrix[z][y];
				for (x = image.border; x < endX; x++) {
					row[x] = row[x] < thresholdValue ? 0 : 0.99609375f;
				}
			}
		}
	}

	@Override
	public void convert(ImageMatrixFloat inImage, ImageMatrixFloat outImage) {
		// Inner loop
		int     x; // 4
		float[] rowOut; // 1
		float[] rowIn; // 1
		int     endX           = outImage.endX; // 1
		float   thresholdValue = this.thresholdValue; // 1

		for (int z = 0; z < outImage.numComponents; z++) {
			for (int y = outImage.border; y < outImage.endY; y++) {
				rowOut = outImage.matrix[z][y];
				rowIn  = inImage.matrix[z][y];
				for (x = outImage.border; x < endX; x++) {
					rowOut[x] = rowIn[x] < thresholdValue ? 0 : 0.99609375f;
				}
			}
		}
	}
}
