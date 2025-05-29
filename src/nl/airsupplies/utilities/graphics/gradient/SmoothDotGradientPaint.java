/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2024 Mark Jeronimus. All Rights Reversed.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
