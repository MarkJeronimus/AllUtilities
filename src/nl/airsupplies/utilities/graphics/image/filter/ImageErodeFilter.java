package nl.airsupplies.utilities.graphics.image.filter;

import java.util.Arrays;

import nl.airsupplies.utilities.graphics.image.ImageMatrixFloat;

/**
 * @author Mark Jeronimus
 */
// Created 2012-04-19
public class ImageErodeFilter extends ImageFilter {
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
			Arrays.fill(lineBuffer0, Float.MAX_VALUE);

			rowIn = in.matrix[z][in.border];
			for (x = in.border; x < endX; x++) {
				lineBuffer1[x] = rowIn[x - 1] < rowIn[x] ?
				                 Math.min(rowIn[x - 1], rowIn[x + 1]) :
				                 Math.min(rowIn[x], rowIn[x + 1]);
			}

			for (int y = in.border; y < in.endY; y++) {
				rowIn  = in.matrix[z][y + 1];
				rowOut = out.matrix[z][y];

				for (x = in.border; x < endX; x++) {
					lineBuffer2[x] = rowIn[x - 1] < rowIn[x] ?
					                 Math.min(rowIn[x - 1], rowIn[x + 1]) :
					                 Math.min(rowIn[x], rowIn[x + 1]);
					rowOut[x]      = lineBuffer0[x] < lineBuffer1[x] ?
					                 Math.min(lineBuffer0[x], lineBuffer2[x]) :
					                 Math.min(lineBuffer1[x], lineBuffer2[x]);
				}

				float[] temp = lineBuffer0;
				lineBuffer0 = lineBuffer1;
				lineBuffer1 = lineBuffer2;
				lineBuffer2 = temp;
			}
		}
	}
}
