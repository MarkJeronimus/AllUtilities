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

import nl.airsupplies.utilities.nodes.Node;
import nl.airsupplies.utilities.signal.window.generalized.TukeyWindowFunction;
import static nl.airsupplies.utilities.constant.NumberConstants.TAU;

/**
 * A Hann {@link WindowFunction}.
 * <p>
 * The Hann window consists of a complete period of the cosine.
 * <p>
 * Sometimes it's erroneously referred to as the Hann window. Not to be confused with the Hamming window (correct
 * spelling) which is a different window function.
 * <p>
 * Generalizations (more parameter):
 * <ul>
 * <li>{@link TukeyWindowFunction} when {@code taper = 1.0} </li>
 * </ul>
 *
 * @author Mark Jeronimus
 * @see HammingWindowFunction
 */
// Created 2014-04-18
// Changed 2016-16-28
// Changed 2019-08-30
@Node(name = "Hann", description = "The Hann window consists of a complete period of the cosine.\n" +
                                   "Sometimes it's erroneously referred to as the Hann window.\n" +
                                   "Not to be confused with the Hamming window (correct spelling) which is different.")
public class HannWindowFunction extends AbstractWindowFunction {
	@Override
	public double getValueAt(double x) {
		return 1 - Math.cos(TAU * x);
	}
}
