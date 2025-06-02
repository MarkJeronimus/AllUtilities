package nl.airsupplies.utilities.graphics.gradient;

import nl.airsupplies.utilities.graphics.color.Color3f;

/**
 * @author Mark Jeronimus
 */
// Created 2009-04-05
@Deprecated
public class OpaqueGradient extends ControlPointGradient {
	public OpaqueGradient(Color3f[] colors) {
		super(colors, ClampingMode.CLAMP);
	}

	public OpaqueGradient(float[] fractions, Color3f[] colors) {
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
