/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2018 Mark Jeronimus. All Rights Reversed.
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
package org.digitalmodular.utilities.gui.gradient;

import java.util.HashMap;
import java.util.Map;

import org.digitalmodular.utilities.color.Color3f;
import org.digitalmodular.utilities.color.Color4f;

/**
 * @author Mark Jeronimus
 */
// Created 2009-04-06
public class SmoothDotGradient extends RadialGradientPaint {
	private static final int     PRECISION   = 128;
	private static final float[] FRACTIONS   = new float[SmoothDotGradient.PRECISION];
	private static final float[] ALPHA_CURVE = new float[SmoothDotGradient.PRECISION];

	private static final Map<Color3f, TransparentGradient> cache = new HashMap<>();

	static {
		for (int i = 0; i < SmoothDotGradient.PRECISION; i++) {
			float x = i / (float)(SmoothDotGradient.PRECISION - 1);
			SmoothDotGradient.FRACTIONS[i] = x;
			SmoothDotGradient.ALPHA_CURVE[i] = (2 * x - 3) * x * x + 1;
		}
	}

	public SmoothDotGradient(Color3f color) {
		this(color, 1);
	}

	public SmoothDotGradient(Color3f color, float opacity) {
		super();

		TransparentGradient gradient = SmoothDotGradient.cache.get(color);

		if (gradient == null) {
			Color4f[] c = new Color4f[SmoothDotGradient.PRECISION];

			for (int i = 0; i < SmoothDotGradient.PRECISION; i++) {
				c[i] = new Color4f(color.r, color.g, color.b, SmoothDotGradient.ALPHA_CURVE[i] * opacity);
			}

			gradient = new TransparentGradient(SmoothDotGradient.FRACTIONS, c);
		}
		super.setGradient(gradient);
	}
}
