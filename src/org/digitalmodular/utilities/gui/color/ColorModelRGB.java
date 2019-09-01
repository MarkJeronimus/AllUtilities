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
package org.digitalmodular.utilities.gui.color;

import java.awt.Color;

import org.digitalmodular.utilities.color.Color3f;

/**
 * @author Mark Jeronimus
 */
// Created 2006-09-11
public class ColorModelRGB extends AbstractColorModel {
	private static final int RED = 0;
	private static final int GRE = 1;
	private static final int BLU = 2;

	public ColorModelRGB() {
		components = new float[]{0, 0, 0};
	}

	public ColorModelRGB(float r, float g, float b) {
		components = new float[]{r, g, b};
	}

	@Override
	public Color3f getColor3f() {
		return new Color3f(components[ColorModelRGB.RED], components[ColorModelRGB.GRE], components[ColorModelRGB
				.BLU]);
	}

	@Override
	public void setRGBColor(Color3f color) {
		components[ColorModelRGB.RED] = color.r;
		components[ColorModelRGB.GRE] = color.g;
		components[ColorModelRGB.BLU] = color.b;
	}

	@Override
	public Color toColor() {
		float r = components[ColorModelRGB.RED];
		float g = components[ColorModelRGB.GRE];
		float b = components[ColorModelRGB.BLU];

		if (r < 0) {
			r = 0;
		} else if (r > 1) {
			r = 1;
		}
		if (g < 0) {
			g = 0;
		} else if (g > 1) {
			g = 1;
		}
		if (b < 0) {
			b = 0;
		} else if (b > 1) {
			b = 1;
		}

		return new java.awt.Color(r, g, b);
	}

	@Override
	public AbstractColorModel clone() {
		return new ColorModelRGB(components[ColorModelRGB.RED], components[ColorModelRGB.GRE],
		                         components[ColorModelRGB.BLU]);
	}

	@Override
	public String toString() {
		return "ColorModelRGB[Red=" + components[ColorModelRGB.RED] + ",Green=" + components[ColorModelRGB.GRE] +
		       ",Blue="
		       + components[ColorModelRGB.BLU] + "]";
	}
}
