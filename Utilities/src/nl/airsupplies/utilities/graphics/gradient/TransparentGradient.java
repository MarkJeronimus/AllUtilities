package nl.airsupplies.utilities.graphics.gradient;

import nl.airsupplies.utilities.graphics.color.Color4f;

/**
 * @author Mark Jeronimus
 */
// Created 2009-04-05
@Deprecated
public final class TransparentGradient extends ControlPointGradient {
	public TransparentGradient(Color4f[] colors) {
		super(colors, ClampingMode.CLAMP);
	}

	public TransparentGradient(float[] fractions, Color4f[] colors) {
		super(colors, ClampingMode.CLAMP, toDouble(fractions));
	}

	private static double[] toDouble(float[] fractions) {
		double[] doubleFractions = new double[fractions.length];

		for (int i = 0; i < fractions.length; i++) {
			doubleFractions[i] = fractions[i];
		}

		return doubleFractions;
	}
}
