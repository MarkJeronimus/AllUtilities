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

package nl.airsupplies.utilities.signal.window.generalized;

import nl.airsupplies.utilities.nodes.Node;
import nl.airsupplies.utilities.signal.window.AbstractGeneralizedWindowFunction;
import nl.airsupplies.utilities.signal.window.HannWindowFunction;
import nl.airsupplies.utilities.signal.window.WindowFunction;
import static nl.airsupplies.utilities.constant.NumberConstants.TAU05;

/**
 * A sine lobe {@link WindowFunction}.
 * <p>
 * Specializations (less parameter):
 * <ul>
 * <li>{@link HannWindowFunction} when {@code power = 2}</li>
 * </ul>
 *
 * @author Mark Jeronimus
 */
// Created 2014-04-22
// Changed 2016-03-31
// Changed 2019-08-30
@Node(name = "SineLobe (generalized)", description = "Half a period of the sine function, from 0 to Tau/2")
public class GeneralizedSineLobeWindowGenerator extends
                                                AbstractGeneralizedWindowFunction {
	@Override
	public double getValueAt(double x) {
		return Math.sin(TAU05 * x);
	}
}
