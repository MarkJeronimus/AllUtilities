/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2019 Mark Jeronimus. All Rights Reversed.
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
 * along with AllUtilities. If not, see <http://www.gnu.org/licenses/>.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.digitalmodular.utilities.math;

import static org.digitalmodular.utilities.constant.NumberConstants.TAU;

/**
 * @author Mark Jeronimus
 */
// Created 2012-04-26
public class FastTrig {
	private final int   size;
	private final float scale;

	private final int   atan2_mul_p;
	private final int   atan2_mul_n;
	private final float stretch;

	private final float[] sin;
	private final float[] tan;
	private final float[] atan2_ppy;
	private final float[] atan2_ppx;
	private final float[] atan2_pny;
	private final float[] atan2_pnx;
	private final float[] atan2_npy;
	private final float[] atan2_npx;
	private final float[] atan2_nny;
	private final float[] atan2_nnx;

	/**
	 * @param size  the size for the look-up tables used for the trigonometric functions, and defines the quantization
	 *              (no interpolation is done for maximum performance). This is always the number of parts a full
	 *              circle/period is divided into. It should be a number divisible by 8, otherwise it's rounded down to
	 *              the closest multiple of 8.
	 * @param scale the amount of units in half a circle (Default: Pi or 180)
	 */
	public FastTrig(int size, float scale) {
		size &= ~7;

		atan2_mul_p = size / 8;
		this.size = atan2_mul_p * 8;
		atan2_mul_n = -atan2_mul_p;

		this.scale = scale;
		stretch = scale / 2;

		sin = new float[size + 1];
		tan = new float[size + 1];
		atan2_ppy = new float[atan2_mul_p + 1];
		atan2_ppx = new float[atan2_mul_p + 1];
		atan2_pny = new float[atan2_mul_p + 1];
		atan2_pnx = new float[atan2_mul_p + 1];
		atan2_npy = new float[atan2_mul_p + 1];
		atan2_npx = new float[atan2_mul_p + 1];
		atan2_nny = new float[atan2_mul_p + 1];
		atan2_nnx = new float[atan2_mul_p + 1];

		for (int i = 0; i <= size; i++) {
			double f = i * TAU / size;
			sin[i] = (float)Math.sin(f);
			tan[i] = (float)Math.tan(f);
		}
		for (int i = 0; i <= atan2_mul_p; i++) {
			double f = (double)i / atan2_mul_p;
			atan2_ppy[i] = (float)(Math.atan(f) * stretch / Math.PI);
			atan2_ppx[i] = stretch * 0.5f - atan2_ppy[i];
			atan2_pny[i] = -atan2_ppy[i];
			atan2_pnx[i] = atan2_ppy[i] - stretch * 0.5f;
			atan2_npy[i] = stretch - atan2_ppy[i];
			atan2_npx[i] = atan2_ppy[i] + stretch * 0.5f;
			atan2_nny[i] = atan2_ppy[i] - stretch;
			atan2_nnx[i] = -stretch * 0.5f - atan2_ppy[i];
		}
	}

	public float sin(float theta) {
		theta = theta / scale;
		theta -= Math.floor(theta);
		return sin[(int)(theta * size + 0.5f)];
	}

	public float cos(float theta) {
		theta = theta / scale + 0.25f;
		theta -= Math.floor(theta);
		return sin[(int)(theta * size + 0.5f)];
	}

	public float tan(float theta) {
		theta = theta / scale;
		theta -= Math.floor(theta);
		return tan[(int)(theta * size + 0.5f)];
	}

	public float atan2(float y, float x) {
		if (x >= 0) {
			if (y >= 0) {
				if (x >= y) {
					return atan2_ppy[(int)(atan2_mul_p * y / x + 0.5)];
				}
				return atan2_ppx[(int)(atan2_mul_p * x / y + 0.5)];
			}
			if (x >= -y) {
				return atan2_pny[(int)(atan2_mul_n * y / x + 0.5)];
			}
			return atan2_pnx[(int)(atan2_mul_n * x / y + 0.5)];
		}
		if (y >= 0) {
			if (-x >= y) {
				return atan2_npy[(int)(atan2_mul_n * y / x + 0.5)];
			}
			return atan2_npx[(int)(atan2_mul_n * x / y + 0.5)];
		}
		if (x <= y) {
			return atan2_nny[(int)(atan2_mul_p * y / x + 0.5)];
		}
		return atan2_nnx[(int)(atan2_mul_p * x / y + 0.5)];
	}
}
