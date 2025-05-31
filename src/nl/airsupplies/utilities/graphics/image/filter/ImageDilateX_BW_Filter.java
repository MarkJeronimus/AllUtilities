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
public class ImageDilateX_BW_Filter extends ImageFilter {
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
					rowOut[x] = rowIn[x] != 0 || rowIn[x - 1] != 0 || rowIn[x + 1] != 0 ? 1 : 0;
				}
			}
		}
	}
}
