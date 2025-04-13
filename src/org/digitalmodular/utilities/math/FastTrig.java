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

package org.digitalmodular.utilities.math;

import org.digitalmodular.utilities.constant.NumberConstants;

/**
 * @author Mark Jeronimus
 */
// Created 2012-04-26
public class FastTrig {
	private final int   size;
	private final int   sqrtSize;
	private final float scale;

	private final int atan2_mul_p;
	private final int atan2_mul_n;

	private final float[] sqrtTable;
	private final float[] sinTable;
	private final float[] tanTable;
	private final float[] atan2_ene;
	private final float[] atan2_nne;
	private final float[] atan2_ese;
	private final float[] atan2_sse;
	private final float[] atan2_wnw;
	private final float[] atan2_nnw;
	private final float[] atan2_wsw;
	private final float[] atan2_ssw;

	/**
	 * @param size  The maximum size of the look-up tables.
	 *              This defines the quantization (no interpolation is done for maximum performance).
	 *              This is always the number of parts a full circle/period is divided into.
	 *              For the square root, a 'period' goes from 1 to 4.
	 *              The size of the square root lookup table is rounded down to a multiple of 6.
	 *              The size of the trigonometric lookup tables are rounded down to a multiple of 8.
	 * @param scale Specify the amount unit that defines half a circle (<em>e.g.</em> tau or 360)
	 */
	public FastTrig(int size, float scale) {
		sqrtSize = size / 6 * 6;
		size &= ~7;

		atan2_mul_p = size / 8;
		this.size = atan2_mul_p * 8;
		atan2_mul_n = -atan2_mul_p;

		this.scale = scale;

		sqrtTable = new float[sqrtSize];
		sinTable = new float[size + 1];
		tanTable = new float[size + 1];
		atan2_ene = new float[atan2_mul_p + 1];
		atan2_nne = new float[atan2_mul_p + 1];
		atan2_ese = new float[atan2_mul_p + 1];
		atan2_sse = new float[atan2_mul_p + 1];
		atan2_wnw = new float[atan2_mul_p + 1];
		atan2_nnw = new float[atan2_mul_p + 1];
		atan2_wsw = new float[atan2_mul_p + 1];
		atan2_ssw = new float[atan2_mul_p + 1];

		for (int i = 0; i < sqrtSize; i++) {
			// Square root of values 2..8
			sqrtTable[i] = (float)Math.sqrt(2.0 + i * 6.0 / sqrtSize);
		}

		for (int i = 0; i <= size; i++) {
			double f = i * NumberConstants.TAU / size;
			sinTable[i] = (float)Math.sin(f);
			tanTable[i] = (float)Math.tan(f);
		}

		for (int i = 0; i <= atan2_mul_p; i++) {
			double f = (double)i / atan2_mul_p;
			atan2_ene[i] = (float)(Math.atan(f) * scale / NumberConstants.TAU);
			atan2_nne[i] = scale * 0.25f - atan2_ene[i];
			atan2_nnw[i] = atan2_ene[i] + scale * 0.25f;
			atan2_wnw[i] = scale * 0.5f - atan2_ene[i];
			atan2_wsw[i] = atan2_ene[i] + scale * 0.5f;
			atan2_ssw[i] = scale * 0.75f - atan2_ene[i];
			atan2_sse[i] = atan2_ene[i] + scale * 0.75f;
			atan2_ese[i] = scale - atan2_ene[i];
		}
	}

	public float sqrt(float v) {
		int bits = Float.floatToRawIntBits(v);
		if ((bits & 0x80000000) != 0) {
			return Float.NaN;
		}
		if (bits == 0x7F800000) {
			return v;
		}
		if ((bits & 0x7FFFFFFF) == 0x00000000) {
			return 0;
		}

		int   sqrtMagn = ((bits >> 23) - 128) >> 1;
		float mantissa = (Float.intBitsToFloat(bits & 0x00FFFFFF | 0x40000000) - 2) / 6;
		assert mantissa >= 0 && mantissa < 1 : mantissa;

		float sqrtMantissa = sqrtTable[(int)(mantissa * sqrtSize)];

		return (1 << sqrtMagn) * sqrtMantissa;
	}

	public float sin(float theta) {
		theta /= scale;
		theta -= Math.floor(theta);
		return sinTable[(int)(theta * size + 0.5f)];
	}

	public float cos(float theta) {
		theta = theta / scale + 0.25f;
		theta -= Math.floor(theta);
		return sinTable[(int)(theta * size + 0.5f)];
	}

	public float tan(float theta) {
		theta /= scale;
		theta -= Math.floor(theta);
		return tanTable[(int)(theta * size + 0.5f)];
	}

	/**
	 * (1,0) returns 0.<br/>
	 * Counter-clockwise increases the angle until it reaches {@code scale}.
	 */
	public float atan2(float x, float y) {
		if (x >= 0) {
			if (y >= 0) {
				if (x >= y) {
					return atan2_ene[(int)(atan2_mul_p * y / x + 0.5)];
				} else {
					return atan2_nne[(int)(atan2_mul_p * x / y + 0.5)];
				}
			} else {
				if (x >= -y) {
					return atan2_ese[(int)(atan2_mul_n * y / x + 0.5)];
				} else {
					return atan2_sse[(int)(atan2_mul_n * x / y + 0.5)];
				}
			}
		} else {
			if (y >= 0) {
				if (-x >= y) {
					return atan2_wnw[(int)(atan2_mul_n * y / x + 0.5)];
				} else {
					return atan2_nnw[(int)(atan2_mul_n * x / y + 0.5)];
				}
			} else {
				if (x <= y) {
					return atan2_wsw[(int)(atan2_mul_p * y / x + 0.5)];
				} else {
					return atan2_ssw[(int)(atan2_mul_p * x / y + 0.5)];
				}
			}
		}
	}
}
