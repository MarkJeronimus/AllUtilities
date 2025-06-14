package nl.airsupplies.utilities.signal.window;

import nl.airsupplies.utilities.NumberUtilities;
import nl.airsupplies.utilities.annotation.UtilityClass;

/**
 * @author Mark Jeronimus
 */
// Created 2016-03-27
@UtilityClass
public final class WindowFunctionUtilities {
	public static void sampleWindow(double[] window, int length, WindowSymmetryMode symmetryMode) {
		for (int i = 0; i < length; i++) {
			window[i] = symmetryMode.getValueAt(i, length);
		}
	}

	public static void taperWindow(double[] window, int length, WindowTaperMode taperMode, double taper) {
		for (int i = 0; i < length; i++) {
			window[i] = taperMode.getValueAt(window[i], taper);
		}
	}

	public static void makeWindow(double[] window, int length, WindowFunction windowFunction) {
		for (int i = 0; i < length; i++) {
			window[i] = windowFunction.getValueAt(window[i]);
		}
	}

	public static void lerpedBiPower(double[] window, int length, double power, double invPower, double powerLerp) {
		for (int i = 0; i < length; i++) {
			double powerInvPower = 1 - Math.pow(1 - Math.pow(window[i], power), invPower);
			double invPowerPower = Math.pow(1 - Math.pow(1 - window[i], invPower), power);
			window[i] = NumberUtilities.lerp(powerInvPower, invPowerPower, powerLerp);
		}
	}

	public static void topScale(double[] window, int length, double topScale) {
		for (int i = 0; i < length; i++) {
			window[i] = 1 - (1 - window[i]) * topScale;
		}
	}

	public static void normalize(double[] window, int length, WindowNormalizationMode normalization) {
		double scale;
		switch (normalization) {
			case NONE:
				return;
			case PEAK:
				double max = Double.NEGATIVE_INFINITY;
				for (int i = 0; i < length; i++) {
					if (max < window[i]) {
						max = window[i];
					}
				}

				scale = max;
				break;
			case CENTER:
				scale = window[length / 2];
				break;
			case AREA:
				double sum = 0;
				for (int i = 0; i < length; i++) {
					sum += window[i];
				}

				scale = sum / length;
				break;
			default:
				throw new UnsupportedOperationException("normalizationMode: " + normalization);
		}

		for (int i = 0; i < length; i++) {
			window[i] /= scale;
		}
	}
}
