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

package org.digitalmodular.utilities.signal.window.generalized;

import org.digitalmodular.utilities.nodes.Node;
import org.digitalmodular.utilities.signal.window.AbstractGeneralizedWindowFunction;
import org.digitalmodular.utilities.signal.window.ParabolicWindowFunction;
import org.digitalmodular.utilities.signal.window.WindowFunction;

/**
 * A parabolic {@link WindowFunction}.
 * <p>
 * Specializations (less parameter):
 * <ul>
 * <li>{@link ParabolicWindowFunction} with </li>
 * </ul>
 *
 * @author Mark Jeronimus
 */
// Created 2014-04-22
// Changed 2016-03-31
// Changed 2019-08-30
@Node(name = "Parabola (generalized)", description = "Generalized Parabolic")
public class GeneralizedParabolicWindowFunction extends
                                                AbstractGeneralizedWindowFunction {
	@Override
	public double getValueAt(double x) {
		// Needs to be normalized because of the generalization steps that follow.
		return x * (1 - x) * 4;
	}
}
