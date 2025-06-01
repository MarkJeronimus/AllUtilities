package nl.airsupplies.utilities.brokenorold;

import nl.airsupplies.utilities.NumberUtilities;
import nl.airsupplies.utilities.graphics.image.ImageMatrixFloat;
import nl.airsupplies.utilities.graphics.image.generator.ImageGenerator;
import static nl.airsupplies.utilities.constant.NumberConstants.QTAU360;

/**
 * @author Mark Jeronimus
 */
// Created 2012-04-03
public class LinearGradientGenerator extends ImageGenerator {
	public float angle;
	public float fromColor = 0;
	public float toColor   = 0;

	float color1 = 0;
	float color2 = 0;

	public float value = 0;

	public LinearGradientGenerator(float angle, float fromColor, float toColor) {
		this.angle     = angle;
		this.fromColor = fromColor;
		this.toColor   = toColor;
	}

	@Override
	public ImageMatrixFloat generate() {
		angle = NumberUtilities.modulo(angle, 360);

		if (angle == 0) {
			color1 = fromColor;
			color2 = (toColor - fromColor) / (image.width - 1);
			processHorizontal();
		} else if (angle == 180) {
			color1 = toColor;
			color2 = (fromColor - toColor) / (image.width - 1);
			processHorizontal();
		} else if (angle == 90) {
			color1 = fromColor;
			color2 = (toColor - fromColor) / (image.height - 1);
			processVertical();
		} else if (angle == 270) {
			color1 = toColor;
			color2 = (fromColor - toColor) / (image.height - 1);
			processVertical();
		} else {
			color1 = (toColor + fromColor) * 0.5f;

			float dx = image.width * Math.abs((float)Math.cos(angle * QTAU360));
			float dy = image.height * Math.abs((float)Math.sin(angle * QTAU360));

			color2 = (toColor - fromColor) / (dx + dy);
			processAngled();
		}

		return image;
	}

	public void processHorizontal() {
		// Inner loop
		int     x; // 3
		float[] row; // 1
		int     numColumns = image.numColumns; // 1
		float   left       = color1; // 1
		float   slope      = color2; // 1

		for (int y = 0; y < image.numRows; y++) {
			row = image.matrix[0][y];
			for (x = 0; x < numColumns; x++) {
				row[x] = left + slope * (x - image.border);
			}
		}
	}

	public void processVertical() {
		// Inner loop
		int     x; // 3
		float[] row; // 1
		int     numColumns = image.numColumns; // 1
		float   c; // 1

		for (int y = 0; y < image.numRows; y++) {
			row = image.matrix[0][y];
			c   = color1 + color2 * (y - image.border);
			for (x = 0; x < numColumns; x++) {
				row[x] = c;
			}
		}
	}

	public void processAngled() {
		// Inner loop
		int     x; // 3
		float[] row; // 1
		int     numColumns = image.numColumns; // 1
		float   center     = color1; // 1

		float dx = color2 * (float)Math.cos(angle * QTAU360);
		float dy = color2 * (float)Math.sin(angle * QTAU360);
		float cx = (1 - image.width) * 0.5f - image.border;
		float cy = (1 - image.height) * 0.5f - image.border;

		for (int y = 0; y < image.numRows; y++) {
			row = image.matrix[0][y];
			for (x = 0; x < numColumns; x++) {
				row[x] = center + dx * (x + cx) + dy * (y + cy);
			}
		}
	}
}
