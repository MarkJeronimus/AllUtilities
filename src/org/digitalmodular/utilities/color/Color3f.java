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
package org.digitalmodular.utilities.color;

import java.awt.Color;

/**
 * @author Mark Jeronimus
 */
// Created 2005-10-03
public class Color3f implements Comparable<Color3f> {
	public float r;
	public float g;
	public float b;

	/**
	 * Create a black {@link Color3f}
	 */
	public Color3f() {
		r = 0;
		g = 0;
		b = 0;
	}

	/**
	 * Create a specified {@link Color3f}
	 */
	public Color3f(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	/**
	 * Create a specified {@link Color3f}
	 */
	public Color3f(int r, int g, int b) {
		this.r = r / 255f;
		this.g = g / 255f;
		this.b = b / 255f;
	}

	/**
	 * Create a {@link Color3f} from packed RGB triplet
	 */
	public Color3f(int rgb) {
		r = (rgb >> 16 & 0xFF) / 255f;
		g = (rgb >> 8 & 0xFF) / 255f;
		b = (rgb & 0xFF) / 255f;
	}

	/**
	 * Create a {@link Color3f} from an awt {@link Color}
	 */
	public Color3f(Color color) {
		int rgb = color.getRGB();
		r = (rgb >> 16 & 0xFF) / 255f;
		g = (rgb >> 8 & 0xFF) / 255f;
		b = (rgb & 0xFF) / 255f;
	}

	/**
	 * Create a copy of another {@link Color3f}
	 */
	public Color3f(Color3f color) {
		r = color.r;
		g = color.g;
		b = color.b;
	}

	public void set(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public void set(int r, int g, int b) {
		this.r = r / 255f;
		this.g = g / 255f;
		this.b = b / 255f;
	}

	public void set(int rgb) {
		r = (rgb >> 16 & 0xFF) / 255f;
		g = (rgb >> 8 & 0xFF) / 255f;
		b = (rgb & 0xFF) / 255f;
	}

	public void set(Color3f color) {
		r = color.r;
		g = color.g;
		b = color.b;
	}

	public void set(Color color) {
		int rgb = color.getRGB();
		r = (rgb >> 16 & 0xFF) / 255f;
		g = (rgb >> 8 & 0xFF) / 255f;
		b = (rgb & 0xFF) / 255f;
	}

	public float getGrayValue() {
		return 0.299f * r + 0.587f * g + 0.114f * b;
	}

	public void negative() {
		r = 1 - r;
		g = 1 - g;
		b = 1 - b;
	}

	public void add(int r, float g, float b) {
		this.r += r;
		this.g += g;
		this.b += b;
	}

	public void add(int rgb) {
		r += (rgb >> 16 & 0xFF) / 255f;
		g += (rgb >> 8 & 0xFF) / 255f;
		b += (rgb & 0xFF) / 255f;
	}

	public void add(Color3f c) {
		r += c.r;
		g += c.g;
		b += c.b;
	}

	public void sub(Color3f c) {
		r -= c.r;
		g -= c.g;
		b -= c.b;
	}

	public void mul(float f) {
		r *= f;
		g *= f;
		b *= f;
	}

	public void mul(float rf, float gf, float bf) {
		r *= rf;
		g *= gf;
		b *= bf;
	}

	public void div(Color3f f) {
		r /= f.r;
		g /= f.g;
		b /= f.b;
	}

	public void lerp(Color3f other, float f) {
		r = r + (other.r - r) * f;
		g = g + (other.g - g) * f;
		b = b + (other.b - b) * f;
	}

	public void lerp(Color3f other, Color3f f) {
		r = r + (other.r - r) * f.r;
		g = g + (other.g - g) * f.g;
		b = b + (other.b - b) * f.b;
	}

	public void pow(float power) {
		r = (float)Math.pow(r, power);
		g = (float)Math.pow(g, power);
		b = (float)Math.pow(b, power);
	}

	public float getMax() {
		if (r > g)
			return r > b ? r : b;
		return g > b ? g : b;
	}

	public float getMin() {
		if (r < g)
			return r < b ? r : b;
		return g < b ? g : b;
	}

	/**
	 * Convert RGB to YPbPr (the digital version of YCbCr, the colorspace used by JPEG/DVD). Outputs: R=Y in [0, 1];
	 * G=Pb in [-0.5, 0.5]; B=Pr in [-0.5, 0.5]
	 */
	public void rgb2YCbCr() {
		float r2 = r;
		float g2 = g;
		r = +0.29900000000000000000f * r2 + 0.58700000000000000000f * g2 + 0.114000000000000000000f * b;
		g = -0.16873589164785553047f * r2 - 0.33126410835214446952f * g2 + 0.500000000000000000000f * b;
		b = +0.50000000000000000000f * r2 - 0.41868758915834522111f * g2 - 0.081312410841654778888f * b;
	}

	/**
	 * Convert YPbPr (the digital version of YCbCr, the colorspace used by JPEG/DVD) to RGB. Inputs: R=Y in [0, 1];
	 * G=Pb
	 * in [-0.5, 0.5]; B=Pr in [-0.5, 0.5]
	 */
	public void yCbCr2RGB() {
		float r2 = r;
		float g2 = g;
		r = r2 + 0.00000000000000000000f * g2 + +1.40200000000000000000f * b;
		g = r2 - 0.34413628620102214650f * g2 + -0.71413628620102214645f * b;
		b = r2 + 1.77200000000000000000f * g2 + +0.00000000000000000000f * b;

	}

	/**
	 * Convert RGB to YUV (the colorspace used by PAL TV). Outputs: Y in [0, 1]; G=U in [-0.436, 0.436]; B=V in
	 * [-0.615,
	 * 0.615]
	 */
	public void rgb2YUV() {
		float r2 = r;
		float g2 = g;
		r = +0.29900000000000000000f * r2 + 0.58700000000000000000f * g2 + 0.11400000000000000000f * b;
		g = -0.14713769751693002257f * r2 - 0.28886230248306997742f * g2 + 0.43600000000000000000f * b;
		b = +0.61500000000000000000f * r2 - 0.51498573466476462197f * g2 - 0.10001426533523537803f * b;
	}

	/**
	 * Convert YUV (the colorspace used by PAL TV) to RGB. Inputs: Y in [0, 1]; G=U in [-0.436, 0.436]; B=V in [-0.615,
	 * 0.615]
	 */
	public void yuv2RGB() {
		float r2 = r;
		float g2 = g;
		r = r2 + 0.00000000000000000000f * g2 + 1.13983739837398373984f * b;
		g = r2 - 0.39465170435897035150f * g2 - 0.58059860666749768009f * b;
		b = r2 + 2.03211009174311926606f * g2 + 0.00000000000000000000f * b;

	}

	/**
	 * Convert RGB to YIQ (the colorspace used in NTSC TV). Outputs: R=Y in [0, 1]; G=I in [-0.5, 0.5]; B=Q in [-0.5,
	 * 0.5]
	 */
	public void rgb2YIQ() {
		float r2 = r;
		float g2 = g;
		r = 0.29900000000000000000f * r2 + 0.58700000000000000000f * g2 + 0.11400000000000000000f * b;
		g = 0.59571613491277455268f * r2 - 0.27445283783925646357f * g2 - 0.32126329707351808911f * b;
		b = 0.21145640212011786639f * r2 - 0.52259104529161116836f * g2 + 0.31113464317149330198f * b;
	}

	/**
	 * Convert YIQ (the colorspace used in NTSC TV) to RGB. Inputs: R=Y in [0 1]; G=I in [-0.5 0.5]; B=Q in [-0.5 0.5]
	 */
	public void yiq2RGB() {
		float r2 = r;
		float g2 = g;
		r = r2 + 0.95629483232089399045f * g2 + 0.62102512544472871408f * b;
		g = r2 - 0.27212147408397731948f * g2 - 0.64738095351761572226f * b;
		b = r2 - 1.10698990856712821590f * g2 + 1.70461497549882932850f * b;
	}

	/**
	 * Convert RGB to XYZ (the CIE 1931 color space). Outputs: R=Y in [0, 1]; G=U in [0, 1]; B=V in [0, 1]
	 */
	public void rgb2XYZ() {
		float r2 = r;
		float g2 = g;
		r = 0.430302942609232490740f * r2 + 0.34163640134469669562f * g2 + 0.178227778501251609730f * b;
		g = 0.221874954782885503040f * r2 + 0.70683393381661385301f * g2 + 0.071291111400500643890f * b;
		b = 0.020170450434807773004f * r2 + 0.12958622119971253972f * g2 + 0.938666300106591811240f * b;
	}

	/**
	 * Convert XYZ (the CIE 1931 color space) to RGB. Inputs: R=X in [0, 1]; G=Y in [0, 1]; B=z in [0, 1]
	 */
	public void xyz2RGB() {
		float r2 = r;
		float g2 = g;
		r = +2.060846560846560846500f * r2 - 0.93738977072310405639f * g2 - 0.320105820105820105800f * b;
		g = -1.141534391534391534600f * r2 + 2.20943562610229276930f * g2 + 0.048941798941798941806f * b;
		b = +0.080687830687830687828f * r2 - 0.27204585537918871252f * g2 + 1.271164021164021164100f * b;
	}

	public void clip() {
		if (r > 1) {
			r = 1;
		} else if (r < 0) {
			r = 0;
		}
		if (g > 1) {
			g = 1;
		} else if (g < 0) {
			g = 0;
		}
		if (b > 1) {
			b = 1;
		} else if (b < 0) {
			b = 0;
		}
	}

	public void clip(float min, float max) {
		if (r > max) {
			r = max;
		} else if (r < min) {
			r = min;
		}
		if (g > max) {
			g = max;
		} else if (g < min) {
			g = min;
		}
		if (b > max) {
			b = max;
		} else if (b < min) {
			b = min;
		}
	}

	public void clipMin(float min) {
		if (r < min) {
			r = min;
		}
		if (g < min) {
			g = min;
		}
		if (b < min) {
			b = min;
		}
	}

	public void clipMax(float max) {
		if (r > max) {
			r = max;
		}
		if (g > max) {
			g = max;
		}
		if (b > max) {
			b = max;
		}
	}

	public boolean isClipping() {
		return r < 0 || r > 1 || g < 0 || g > 1 || b < 0 || b > 1;
	}

	public int toInteger() {
		int ra = (int)(r * 255 + 0.5);
		int ga = (int)(g * 255 + 0.5);
		int ba = (int)(b * 255 + 0.5);

		if (ra < 0) {
			ra = 0;
		} else if (ra > 255) {
			ra = 255;
		}
		if (ga < 0) {
			ga = 0;
		} else if (ga > 255) {
			ga = 255;
		}
		if (ba < 0) {
			ba = 0;
		} else if (ba > 255) {
			ba = 255;
		}

		return ba + (ga + (ra << 8) << 8);
	}

	@Override
	public int compareTo(Color3f o) {
		int diff = Float.compare(r, o.r);
		if (diff != 0)
			return diff;
		diff = Float.compare(g, o.g);
		if (diff != 0)
			return diff;
		return Float.compare(b, o.b);
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Color3f))
			return false;

		Color3f col = (Color3f)other;
		return col.r == r && col.g == g && col.b == b;
	}

	@Override
	public int hashCode() {
		int hash = 0x811C9DC5;
		hash ^= Float.hashCode(r);
		hash *= 0x01000193;
		hash ^= Float.hashCode(g);
		hash *= 0x01000193;
		hash ^= Float.hashCode(b);
		hash *= 0x01000193;
		return hash;
	}

	@Override
	public String toString() {
		return super.getClass().getSimpleName() + "(" + r + ", " + g + ", " + b + ")";
	}

}
