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

import org.digitalmodular.utilities.nodes.DoubleParam;
import org.digitalmodular.utilities.nodes.EnumParam;
import org.digitalmodular.utilities.nodes.Generator;
import static org.digitalmodular.utilities.ValidatorUtilities.requireAbove;
import static org.digitalmodular.utilities.ValidatorUtilities.requireNonNull;
import static org.digitalmodular.utilities.ValidatorUtilities.requireRange;

/**
 * a {@code AbstractWindowFunction} is a {@link Generator} that generates 'windowing functions'.
 *
 * @author Mark Jeronimus
 */
// Created 2016-54-28
// Changed 2019-08-30
// Changed 2019-08-30 replace hardcoded params with annotations
public abstract class AbstractGeneralizedWindowFunction extends AbstractWindowFunction {
	@EnumParam(description = "The way the window is tapered", type = WindowTaperMode.class)
	private WindowTaperMode taperMode = WindowTaperMode.TRAPEZIUM;

	@DoubleParam(description = "The ratio of the time scale that's being tapered", min = 0, max = 1)
	private double taper = 0.5;

	@DoubleParam(description = "The power used to exponentiate the window function to",
	             min = 0, minIsInclusive = false, maxIsInclusive = false)
	private double power = 1.0;

	@DoubleParam(description = "The power used to exponentiate the inverse of the window function to",
	             min = 0, minIsInclusive = false, maxIsInclusive = false)
	private double invPower = 1.0;

	@DoubleParam(description = "Linearly interpolates between the power applied before the invPower" +
	                           " and the invPower applied before the power", min = 0, max = 1)
	private double powerLerp = 1.0;

	@DoubleParam(description = "Scales the window samples relative to 1.0." +
	                           " For some values, this creates flat-top windows",
	             min = 0, minIsInclusive = false, maxIsInclusive = false)
	private double topScale = 0.5;

	public WindowTaperMode getTaperMode() {
		return taperMode;
	}

	public void setTaperMode(WindowTaperMode taperMode) {
		this.taperMode = requireNonNull(taperMode, "taperMode");
	}

	public double getTaper() {
		return taper;
	}

	public void setTaper(double taper) {
		this.taper = requireRange(0, 1, taper, "taper");
	}

	public double getPower() {
		return power;
	}

	public void setPower(double power) {
		this.power = requireRange(0, 1, power, "power");
	}

	public double getInvPower() {
		return invPower;
	}

	public void setInvPower(double invPower) {
		this.invPower = requireRange(0, 1, invPower, "invPower");
	}

	public double getPowerLerp() {
		return powerLerp;
	}

	public void setPowerLerp(double powerLerp) {
		this.powerLerp = requireRange(0, 1, powerLerp, "powerLerp");
	}

	public double getTopScale() {
		return topScale;
	}

	public void setTopScale(double topScale) {
		this.topScale = requireAbove(0, topScale, "topScale");
	}

	@Override
	public double[] generate() {
		int      length = getLength();
		double[] window = getWindowArray(length);

		// Preprocessing of the sample positions
		WindowFunctionUtilities.sampleWindow(window, length, getSymmetryMode());
		WindowFunctionUtilities.taperWindow(window, length, getTaperMode(), getTaper());

		WindowFunctionUtilities.makeWindow(window, length, this);

		// Postprocessing of the window values
		WindowFunctionUtilities.lerpedBiPower(window, length, getPower(), getInvPower(), getPowerLerp());
		WindowFunctionUtilities.topScale(window, length, getTopScale());
		WindowFunctionUtilities.normalize(window, length, getNormalizationMode());

		return window;
	}
}
