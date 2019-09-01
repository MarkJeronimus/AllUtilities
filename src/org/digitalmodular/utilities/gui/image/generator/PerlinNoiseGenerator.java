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
//package org.digitalmodular.utilities.gui.image.generator;
//
//import org.digitalmodular.utilities.math.RandomUtilities;
//import org.digitalmodular.utilities.math.RandomUtilities.FastRandom;
//import org.digitalmodular.utilities.gui.image.ImageMatrix;
//
///**
// * @author Mark Jeronimus
// */
//// Created 2012-04-04
//public class PerlinNoiseGenerator extends ImageGenerator {
//	private static final FastRandom RND = RandomUtilities.FAST_RND;
//
//	public float amount;
//	public float scale;
//
//	public PerlinNoiseGenerator(int width, int height, int border, float amount, float scale) {
//		super(width, height, border);
//
//		this.amount = amount;
//		this.scale = scale;
//	}
//
//	@Override
//	public ImageMatrix generate() {
//		int cols = (int) Math.ceil(image.numColumns * scale + 1.5f);
//		int rows = (int) Math.ceil(image.numRows * scale + 1.5f);
//
//		float[][][] gradients = new float[rows][cols][2];
//		int         dx        = PerlinNoiseGenerator.RND.nextInt();
//		int         dy        = PerlinNoiseGenerator.RND.nextInt();
//		int         re        = PerlinNoiseGenerator.RND.nextInt();
//		int         im        = re + (PerlinNoiseGenerator.RND.nextInt() | 1);
//		for (int y = 0; y < rows; y++) {
//			for (int x = 0; x < cols; x++) {
//				float u = RandomUtilities.getDirect(dy + y, dx + x, re);
//				float v = RandomUtilities.getDirect(dy + y, dx + x, im);
//				float d = 1f / (float) Math.sqrt(u * u + v * v);
//				gradients[y][x][0] = u * d;
//				gradients[y][x][1] = v * d;
//			}
//		}
//
//		// Inner loop
//		float   fu0; // 7
//		int     u0; // 5
//		float   a; // 5
//		float   c; // 4
//		float   u; // 3
//		int     u1; // 3
//		float   fu1; // 3
//		float   sx; // 3
//		float[] g_b00; // 3
//		float[] g_b01; // 3
//		float[] g_b10; // 3
//		float[] g_b11; // 3
//		float   b; // 2
//		float   d; // 2
//		float   scale  = this.scale; // 1
//		float   amount = this.amount; // 1
//		int     endX   = image.endX; // 1
//
//		for (int y = image.border; y < image.endY; y++) {
//			float     v    = (y + 0.5f) * scale;
//			int       v0   = (int) v;
//			int       v1   = v0 + 1;
//			float     fv0  = v - v0;
//			float     fv1  = fv0 - 1;
//			float     sy   = ((6 * fv0 - 15) * fv0 + 10) * fv0 * fv0 * fv0;
//			float[][] g_b0 = gradients[v0];
//			float[][] g_b1 = gradients[v1];
//
//			float[] row = image.matrix[0][y];
//			for (int x = image.border; x < endX; x++) {
//				u = (x + 0.5f) * scale;
//				u0 = (int) u;
//				u1 = u0 + 1;
//				fu0 = u - u0;
//				fu1 = fu0 - 1;
//				sx = ((6 * fu0 - 15) * fu0 + 10) * fu0 * fu0 * fu0;
//
//				g_b00 = g_b0[u0];
//				g_b01 = g_b0[u1];
//				g_b10 = g_b1[u0];
//				g_b11 = g_b1[u1];
//
//				a = fu0 * g_b00[0] + fv0 * g_b00[1];
//				b = fu1 * g_b01[0] + fv0 * g_b01[1];
//				c = fu0 * g_b10[0] + fv1 * g_b10[1];
//				d = fu1 * g_b11[0] + fv1 * g_b11[1];
//
//				a += sx * (b - a);
//				c += sx * (d - c);
//
//				row[x] = (a + sy * (c - a)) * amount;
//			}
//		}
//
//		return image;
//	}
//}
