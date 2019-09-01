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
package org.digitalmodular.utilities.signal.window;

import org.digitalmodular.utilities.nodes.Node;
import org.digitalmodular.utilities.signal.window.generalized.TukeyWindowFunction;

/**
 * A rectangular {@link WindowFunction}.
 * <p>
 * Synonyms:
 * <ul>
 * <li>boxcar</li>
 * <li>Dirichlet</li>
 * <li>1st order B-spline</li>
 * <li>unity function</li>
 * <li>'no window'</li>
 * </ul>
 * <p>
 * Generalizations (more parameters):
 * <ul>
 * <li>{@link TukeyWindowFunction} when {@code taper = {0}}</li>
 * </ul>
 *
 * @author Mark Jeronimus
 */
// Created 2014-04-17
// Changed 2016-03-28
// Changed 2019-08-30
@Node(name = "Rectangle", description = "The rectangle window (unity function)")
public class RectangularWindowFunction extends AbstractWindowFunction {
	@Override
	public double getValueAt(double x) {
		return 1;
	}
}
