/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2024 Mark Jeronimus. All Rights Reversed.
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package nl.airsupplies.utilities.signal.window;

import nl.airsupplies.utilities.NumberUtilities;
import nl.airsupplies.utilities.nodes.IntParam;
import nl.airsupplies.utilities.nodes.Node;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireAtLeast;
import static nl.airsupplies.utilities.constant.NumberConstants.TAU;

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
