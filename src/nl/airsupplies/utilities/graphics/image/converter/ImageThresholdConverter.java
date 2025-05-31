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
