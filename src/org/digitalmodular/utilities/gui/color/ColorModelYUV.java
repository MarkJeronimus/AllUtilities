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
public class ColorModelYUV extends AbstractColorModel {
	private static final int Y = 0;
	private static final int U = 1;
	private static final int V = 2;

	public ColorModelYUV() {
		components = new float[]{0, 0, 0};
	}

	public ColorModelYUV(float r, float g, float b) {
		components = new float[]{r, g, b};
	}

	@Override
	public Color3f getColor3f() {
		return new Color3f( //
		                    components[ColorModelYUV.Y] /* ***************************************** */ +
		                    1.13983739837398373980f
		                    * components[ColorModelYUV.V],
		                    //
		                    components[ColorModelYUV.Y] - 0.39465170435897035149f * components[ColorModelYUV.U] -
		                    0.58059860666749768007f
		                    * components[ColorModelYUV.V],
		                    //
		                    components[ColorModelYUV.Y] + 2.03211009174311926600f * components[ColorModelYUV.U] /*
		 *************************************** */);
	}

	@Override
	public void setRGBColor(Color3f color) {
		components[ColorModelYUV.Y] = +0.29900000000000000000f * color.r + 0.58700000000000000000f * color.g
		                              + 0.11400000000000000000f * color.b; //
		components[ColorModelYUV.U] = -0.14713769751693002257f * color.r - 0.28886230248306997742f * color.g
		                              + 0.43600000000000000000f * color.b; //
		components[ColorModelYUV.V] = +0.61500000000000000000f * color.r - 0.51498573466476462197f * color.g
		                              - 0.10001426533523537803f * color.b;
	}

	@Override
	public Color toColor() {
		float r = components[ColorModelYUV.Y] /* ***************************************** */ + 1.13983739837398373980f
		                                                                                        *
		                                                                                        components[ColorModelYUV.V];
		float g = components[ColorModelYUV.Y] - 0.39465170435897035149f * components[ColorModelYUV.U] -
		          0.58059860666749768007f
		          * components[ColorModelYUV.V];
		float b = components[ColorModelYUV.Y] + 2.03211009174311926600f * components[ColorModelYUV.U]/*
		 ***************************************** */;

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
		return new ColorModelYUV(components[ColorModelYUV.Y], components[ColorModelYUV.U], components[ColorModelYUV
				.V]);
	}

	@Override
	public String toString() {
		return "ColorModelRGB[Red=" + components[ColorModelYUV.Y] + ",Green=" + components[ColorModelYUV.U] + ",Blue="
		       + components[ColorModelYUV.V] + "]";
	}
}
