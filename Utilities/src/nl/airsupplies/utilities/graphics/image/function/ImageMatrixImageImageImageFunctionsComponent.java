package nl.airsupplies.utilities.graphics.image.function;

import nl.airsupplies.utilities.graphics.image.ImageMatrixFloat;

/**
 * Functions of the form:<br> {@link ImageMatrixFloat} = ImageMatrix &lt;operator&gt; ImageMatrix &lt;operator&gt;
 * ImageMatrix<br>
 *
 * @author Mark Jeronimus
 */
// Created 2012-04-05
public class ImageMatrixImageImageImageFunctionsComponent {
	public static void addMul(ImageMatrixFloat out, float[][] outComponent, float[][] addComponent, float[][]
			mulComponent) {
		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowAdd; // 1
		float[] rowMul; // 1
		int     endX = out.endX; // 1

		for (int y = out.border; y < out.endY; y++) {
			rowOut = outComponent[y];
			rowAdd = addComponent[y];
			rowMul = mulComponent[y];
			for (x = out.border; x < endX; x++) {
				rowOut[x] = (rowOut[x] + rowAdd[x]) * rowMul[x];
			}
		}
	}

	public static void mulAdd(ImageMatrixFloat out, float[][] outComponent, float[][] mulComponent, float[][]
			addComponent) {
		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowMul; // 1
		float[] rowAdd; // 1
		int     endX = out.endX; // 1

		for (int y = out.border; y < out.endY; y++) {
			rowOut = outComponent[y];
			rowMul = mulComponent[y];
			rowAdd = addComponent[y];
			for (x = out.border; x < endX; x++) {
				rowOut[x] = rowOut[x] * rowMul[x] + rowAdd[x];
			}
		}
	}

	public static void addScaled(ImageMatrixFloat out, float[][] outComponent, float[][] offsetComponent,
	                             float[][] scaleComponentOut) {
		// Inner loop
		int     x; // 4
		float[] rowOut; // 2
		float[] rowOffset; // 1
		float[] rowScale; // 1
		int     endX = out.endX; // 1

		for (int y = out.border; y < out.endY; y++) {
			rowOut    = outComponent[y];
			rowOffset = offsetComponent[y];
			rowScale  = scaleComponentOut[y];
			for (x = out.border; x < endX; x++) {
				rowOut[x] += rowOffset[x] * rowScale[x];
			}
		}
	}
}
