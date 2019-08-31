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
package org.digitalmodular.utilities.gui.color;

import java.awt.Color;

import org.digitalmodular.utilities.color.Color3f;

/**
 * @author Mark Jeronimus
 */
// Created 2006-09-11
public class ColorModelHSB extends AbstractColorModel {
	private static final int HUE = 0;
	private static final int SAT = 1;
	private static final int BRI = 2;

	public ColorModelHSB() {
		components = new float[]{0, 0, 0};
	}

	public ColorModelHSB(float r, float g, float b) {
		components = new float[]{r, g, b};
	}

	@Override
	public Color3f getColor3f() {
		if (components[ColorModelHSB.SAT] == 0) {
			return new Color3f(components[ColorModelHSB.BRI], components[ColorModelHSB.BRI],
			                   components[ColorModelHSB.BRI]);
		}
		Color3f out  = new Color3f(0, 0, 0);
		float   hue6 = components[ColorModelHSB.HUE] % 1 * 6;
		if (components[ColorModelHSB.HUE] < 0) {
			hue6 += 6;
		}
		float hue_f = hue6 % 1;
		float p     = components[ColorModelHSB.BRI] * (1 - components[ColorModelHSB.SAT]);
		switch ((int)hue6) {
			case 0: {
				out.r = components[ColorModelHSB.BRI];
				out.g = components[ColorModelHSB.BRI] * (1 - components[ColorModelHSB.SAT] * (1 - hue_f));
				out.b = p;
				break;
			}
			case 1: {
				out.r = components[ColorModelHSB.BRI] * (1 - components[ColorModelHSB.SAT] * hue_f);
				out.g = components[ColorModelHSB.BRI];
				out.b = p;
				break;
			}
			case 2: {
				out.r = p;
				out.g = components[ColorModelHSB.BRI];
				out.b = components[ColorModelHSB.BRI] * (1 - components[ColorModelHSB.SAT] * (1 - hue_f));
				break;
			}
			case 3: {
				out.r = p;
				out.g = components[ColorModelHSB.BRI] * (1 - components[ColorModelHSB.SAT] * hue_f);
				out.b = components[ColorModelHSB.BRI];
			}
			break;
			case 4: {
				out.r = components[ColorModelHSB.BRI] * (1 - components[ColorModelHSB.SAT] * (1 - hue_f));
				out.g = p;
				out.b = components[ColorModelHSB.BRI];
				break;
			}
			case 5: {
				out.r = components[ColorModelHSB.BRI];
				out.g = p;
				out.b = components[ColorModelHSB.BRI] * (1 - components[ColorModelHSB.SAT] * hue_f);
			}
		}
		return out;
	}

	@Override
	public void setRGBColor(Color3f color) {
		float cmax = color.r > color.g ? color.r : color.g;
		if (color.b > cmax) {
			cmax = color.b;
		}
		float cmin = color.r < color.g ? color.r : color.g;
		if (color.b < cmin) {
			cmin = color.b;
		}
		float dist = cmax - cmin;

		components[ColorModelHSB.BRI] = cmax;
		components[ColorModelHSB.SAT] = cmax != 0 ? dist / cmax : 0;

		if (components[ColorModelHSB.SAT] == 0) {
			components[ColorModelHSB.HUE] = 0;
		} else {
			if (color.r == cmax) {
				if (color.g >= color.b) {
					components[ColorModelHSB.HUE] = (color.g - color.b) / dist;
				} else {
					components[ColorModelHSB.HUE] = 6.0f + (color.g - color.b) / dist;
				}
			} else if (color.g == cmax) {
				components[ColorModelHSB.HUE] = 2.0f + (color.b - color.r) / dist;
			} else {
				components[ColorModelHSB.HUE] = 4.0f + (color.r - color.g) / dist;
			}
			if (components[ColorModelHSB.HUE] < 0) {
				System.err.println("Color3f.RGBtoHSB(): below 0");
				System.exit(0);
			}
			components[ColorModelHSB.HUE] = components[ColorModelHSB.HUE] / 6.0f;
		}
	}

	@Override
	public Color toColor() {
		return new Color(getColor3f().toInteger());
	}

	@Override
	public AbstractColorModel clone() {
		return new ColorModelHSB(components[0], components[1], components[2]);
	}

	@Override
	public String toString() {
		return "ColorModelRGB[Red=" + components[0] + ",Green=" + components[1] + ",Blue=" + components[2] + "]";
	}
}
