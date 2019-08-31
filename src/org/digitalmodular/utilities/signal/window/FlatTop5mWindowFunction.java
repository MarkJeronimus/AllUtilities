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
package org.digitalmodular.utilities.signal.window;

import org.digitalmodular.utilities.nodes.Node;
import org.digitalmodular.utilities.signal.window.generalized.FlatTopParabolicWindowFunction;
import org.digitalmodular.utilities.signal.window.generalized.GeneralizedCosineSeriesWindowGenerator;
import static org.digitalmodular.utilities.constant.NumberConstants.TAU;

/**
 * Salvatore's 5-term Flat-top-5M variant b (SFT5M) {@link WindowFunction}.
 * <p>
 * Generalizations (more parameter):
 * <ul>
 * <li>{@link GeneralizedCosineSeriesWindowGenerator} with </li>
 * </ul>
 * <p>
 * Variations:
 * <ul>
 * <li>{@link FlatTop3mWindowFunction}</li>
 * <li>{@link FlatTopParabolicWindowFunction}</li>
 * </ul>
 *
 * @author Mark Jeronimus
 */
// Created 2014-04-29
// Changed 2016-03-29
// Changed 2019-08-30
@Node(name = "Flat-Top (SFT5M)", description = "A flat-top variation of a 5-term cosine series window")
public class FlatTop5mWindowFunction extends AbstractWindowFunction {
	@Override
	@SuppressWarnings("OverlyComplexArithmeticExpression")
	public double getValueAt(double x) {
		return 0.209671
		       - 0.407331 * Math.cos(TAU * 1 * x)
		       + 0.281225 * Math.cos(TAU * 2 * x)
		       - 0.092669 * Math.cos(TAU * 3 * x)
		       + 0.0091036 * Math.cos(TAU * 4 * x);
	}
}
