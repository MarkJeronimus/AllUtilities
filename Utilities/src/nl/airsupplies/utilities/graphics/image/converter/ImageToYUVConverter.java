package nl.airsupplies.utilities.graphics.image.converter;

import nl.airsupplies.utilities.graphics.image.ImageMatrixFloat;

/**
 * Convert to opaque. Premultiplies opacity, is available.
 *
 * @author Mark Jeronimus
 */
// Created 2011-11-22
public class ImageToYUVConverter extends ImageConverter {
	@Override
	public void convertSelf(ImageMatrixFloat image) {
		// Inner loop
		float   r;
		float   g;
		float   b;
		int     x;
		int     endX = image.endX;
		float[] row0;
		float[] row1;
		float[] row2;

		switch (image.numComponents) {
			case 3:
			case 4:
				for (int y = image.border; y < image.endY; y++) {
					row0 = image.matrix[0][y];
					row1 = image.matrix[1][y];
					row2 = image.matrix[2][y];
					for (x = image.border; x < endX; x++) {
						r       = row0[x];
						g       = row1[x];
						b       = row2[x];
						row0[x] = 0.29900000000000000000f * r +
						          0.58700000000000000000f * g +
						          0.11400000000000000000f * b;
						row1[x] = -0.14713769751693002257f * r -
						          0.28886230248306997742f * g +
						          0.43600000000000000000f * b;
						row2[x] = 0.61500000000000000000f * r -
						          0.51498573466476462197f * g -
						          0.10001426533523537803f * b;
					}
				}
				break;
			default:
				throw new IllegalArgumentException("Unsupported number of components: " + image.numComponents);
		}
	}
}
