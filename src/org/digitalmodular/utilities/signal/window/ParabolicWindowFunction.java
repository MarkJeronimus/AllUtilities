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

package org.digitalmodular.utilities.signal.window;

import org.digitalmodular.utilities.nodes.Node;
import org.digitalmodular.utilities.signal.window.generalized.GeneralizedParabolicWindowFunction;
import org.digitalmodular.utilities.signal.window.generalized.GeneralizedTriangularWindowGenerator;

/**
 * A Parabolic {@link WindowFunction}.
 * <p>
 * Synonyms:
 * <ul>
 * <li>Welch</li>
 * <li>(Inverse) Parabolic</li>
 * </ul>
 * <p>
 * Generalizations (more parameter):
 * <ul>
 * <li>{@link GeneralizedTriangularWindowGenerator}</li>
 * <li>{@link GeneralizedParabolicWindowFunction}</li>
 * </ul>
 *
 * @author Mark Jeronimus
 */
// Created 2014-04-18
// Changed 2016-03-29
// Changed 2019-08-30
@Node(name = "Parabola", description = "An inverse parabolic window")
public class ParabolicWindowFunction extends AbstractWindowFunction {
	@Override
	public double getValueAt(double x) {
		return x * (1 - x);
	}
}
