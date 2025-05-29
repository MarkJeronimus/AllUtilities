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
import nl.airsupplies.utilities.signal.window.FlatTop3mWindowFunction;
import nl.airsupplies.utilities.signal.window.FlatTop5mWindowFunction;
import nl.airsupplies.utilities.signal.window.ParabolicWindowFunction;
import nl.airsupplies.utilities.signal.window.WindowFunction;

/**
 * A Flat-top Parabolic {@link WindowFunction}.
 * <p>
 * Synonyms:
 * <ul>
 * <li>Flat-top Welch</li>
 * </ul>
 * <p>
 * Generalizations (more parameter):
 * <ul>
 * <li>{@link GeneralizedParabolicWindowFunction} with {@code topScale = 1.7081}</li>
 * </ul>
 * <p>
 * Variations:
 * <ul>
 * <li>{@link FlatTop3mWindowFunction} (using cosine series)</li>
 * <li>{@link FlatTop5mWindowFunction} (using cosine series)</li>
 * <li>{@link ParabolicWindowFunction} (not flat-top)</li>
 * </ul>
 *
 * @author Mark Jeronimus
 */
// Created 2014-04-29
// Changed 2016-03-29
// Changed 2019-08-30
// TODO make non-generalized
@Node(name = "Parabola (flat-top)", description = "A flat-top variation of the 'Parabolic' window function")
public class FlatTopParabolicWindowFunction extends
                                            AbstractGeneralizedWindowFunction {
	public FlatTopParabolicWindowFunction() {
		setTopScale(1.7081);
	}

	@Override
	public double getValueAt(double x) {
		// Needs to be normalized because of the generalization steps that follow.
		return x * (1 - x) * 4;
	}
}
