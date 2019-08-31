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
package org.digitalmodular.utilities.gui.pixelsorter;

import static org.digitalmodular.utilities.constant.NumberConstants.TAU;

/**
 * @author Mark Jeronimus
 */
// Created 2009-05-02
public class SpiralPixelSorter extends PixelSorter {
	public enum SpiralType {
		ARCHIMEDIAN,
		FERMAT;
	}

	private final double     thickness;
	private final SpiralType type;

	public SpiralPixelSorter(double thickness) {
		this(thickness, SpiralType.ARCHIMEDIAN);
	}

	public SpiralPixelSorter(double thickness, SpiralType type) {
		this.thickness = thickness;
		this.type = type;
	}

	@Override
	public double getSortOrder(int x, int y, int width, int height) {
		double dx = x - (width - 0.75) * 0.5;
		double dy = y - (height - 0.875) * 0.5;

		double a = StrictMath.atan2(dx, dy) / TAU;
		double r = StrictMath.hypot(dx, dy);

		double t = Math.hypot(width, height) * 0.5 * thickness;

		if (type == SpiralType.FERMAT) {
			r *= r / 100;
			t *= t / 10;
		}

		return Math.rint(a + r / t) * t - a;
	}
}
