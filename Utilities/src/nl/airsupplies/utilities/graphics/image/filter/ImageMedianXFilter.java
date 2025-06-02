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
public class ImageMedianXFilter extends ImageFilter {
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
					float c = (rowIn[x + 1] + rowIn[x - 1]) * 0.5f;
					if (rowIn[x] > rowIn[x + 1]) {
						rowOut[x] = rowIn[x] > rowIn[x - 1] ? Math.max(rowIn[x - 1], rowIn[x + 1]) : c;
					} else {
						rowOut[x] = rowIn[x - 1] > rowIn[x] ? Math.min(rowIn[x - 1], rowIn[x + 1]) : c;
					}
				}
			}
		}
	}
}
