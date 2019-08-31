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
package org.digitalmodular.utilities.signal.window.generalized;

import org.digitalmodular.utilities.nodes.Node;
import org.digitalmodular.utilities.signal.window.AbstractGeneralizedWindowFunction;
import org.digitalmodular.utilities.signal.window.HannWindowFunction;
import org.digitalmodular.utilities.signal.window.RectangularWindowFunction;
import org.digitalmodular.utilities.signal.window.WindowFunction;
import static org.digitalmodular.utilities.constant.NumberConstants.TAU;

/**
 * A Tukey {@link WindowFunction}.
 * <p>
 * It's designed to retain as much spectral power as possible (like the Rectangle window), but still has smooth fall-off
 * at the edges (like the Hann window). It's constructed by convolving a Rectangle window with a Hann window. An
 * alternative interpretation is the Hann window split in the middle with a rectangle window inserted in the gap.
 * <p>
 * Synonyms (more parameters):
 * <ul>
 * <li>Cosine-tapered window</li>
 * </ul>
 * <p>
 * Generalizations (more parameters):
 * <ul>
 * <li>{@link GeneralizedCosineSeriesWindowGenerator} when </li>
 * </ul>
 * <p>
 * Specializations (less parameters):
 * <ul>
 * <li>{@link RectangularWindowFunction} when {@code taper = 0}</li>
 * <li>{@link HannWindowFunction} when {@code taper = 1}</li>
 * </ul>
 *
 * @author Mark Jeronimus
 */
// Created 2014-04-18
// Changed 2016-03-27
// Changed 2019-08-30
// TODO make non-generalized
@Node(name = "Tukey (generalized)")
public class TukeyWindowFunction extends AbstractGeneralizedWindowFunction {
	@Override
	public double getValueAt(double x) {
		return 1 - Math.cos(x * TAU);
	}
}
