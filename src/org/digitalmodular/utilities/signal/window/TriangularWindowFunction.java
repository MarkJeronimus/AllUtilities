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
import org.digitalmodular.utilities.signal.window.generalized.GeneralizedTriangularWindowGenerator;

/**
 * A Triangular {@link WindowFunction}.
 * <p>
 * Synonyms:
 * <ul>
 * <li>Barlett</li>
 * <li>Tent</li>
 * </ul>
 * <p>
 * Generalizations (more parameters):
 * <ul>
 * <li>{@link GeneralizedTriangularWindowGenerator} when </li>
 * </ul>
 *
 * @author Mark Jeronimus
 */
// Created 2014-04-18
// Changed 2016-03-28
// Changed 2019-08-30
@Node(name = "Triangle", description = "Triangular window")
public class TriangularWindowFunction extends AbstractWindowFunction {
	@Override
	public double getValueAt(double x) {
		return 0.5 - Math.abs(x - 0.5);
	}
}
