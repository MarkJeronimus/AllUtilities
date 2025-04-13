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

package org.digitalmodular.utilities.graphics.gradient;

import org.digitalmodular.utilities.graphics.color.Color3f;

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
