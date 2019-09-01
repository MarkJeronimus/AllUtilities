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

import org.digitalmodular.utilities.NumberUtilities;
import org.digitalmodular.utilities.nodes.IntParam;
import org.digitalmodular.utilities.nodes.Node;
import static org.digitalmodular.utilities.ValidatorUtilities.requireAtLeast;
import static org.digitalmodular.utilities.constant.NumberConstants.TAU;

/**
 * A sinc {@link WindowFunction}.
 *
 * @author Mark Jeronimus
 */
// Created 2016-22-28
// Changed 2016-03-28
// Changed 2019-08-30
@Node(name = "Sinc", description = "A window with a Sinc shape")
public class SincWindowFunction extends AbstractWindowFunction {
	@IntParam(description = "", min = 1)
	private int degree = 3;

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = requireAtLeast(1, degree, "degree");
	}

	@Override
	public double getValueAt(double x) {
		int degree = getDegree();
		return NumberUtilities.sinc((x - 0.5) * TAU * degree);
	}
}
