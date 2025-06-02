package nl.airsupplies.utilities.graphics.gradient;

import java.util.HashMap;
import java.util.Map;

import nl.airsupplies.utilities.graphics.color.Color3f;
import nl.airsupplies.utilities.graphics.color.Color4f;

/**
 * @author Mark Jeronimus
 */
// Created 2009-04-06
public class SmoothDotGradientPaint extends RadialGradientPaint {
	private static final int     PRECISION   = 128;
	private static final float[] ALPHA_CURVE = new float[PRECISION];

	private static final Map<Color3f, ControlPointGradient> CACHE = new HashMap<>(16);

	static {
		for (int i = 0; i < PRECISION; i++) {
			double x = i / (double)(PRECISION - 1);
			ALPHA_CURVE[i] = (float)((2 * x - 3) * x * x + 1);
		}
	}

	public SmoothDotGradientPaint(Color3f color) {
		this(color, 1);
	}

	public SmoothDotGradientPaint(Color3f color, float opacity) {
		ControlPointGradient gradient = CACHE.get(color);

		if (gradient == null) {
			Color4f[] c = new Color4f[PRECISION];

			for (int i = 0; i < PRECISION; i++) {
				c[i] = new Color4f(color.r, color.g, color.b, ALPHA_CURVE[i] * opacity);
			}

			gradient = new ControlPointGradient(c, Gradient.ClampingMode.CLAMP);
			CACHE.put(color, gradient);
		}
		setGradient(gradient);
	}
}
