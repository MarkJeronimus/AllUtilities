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
import org.digitalmodular.utilities.signal.window.generalized.FlatTopParabolicWindowFunction;
import org.digitalmodular.utilities.signal.window.generalized.GeneralizedCosineSeriesWindowGenerator;
import static org.digitalmodular.utilities.constant.NumberConstants.TAU;

/**
 * Salvatore's 3-term Flat-top-3M variant b (SFT3M) {@link WindowFunction}.
 * <p>
 * Generalizations (more parameter):
 * <ul>
 * <li>{@link GeneralizedCosineSeriesWindowGenerator} with </li>
 * </ul>
 * <p>
 * Variations:
 * <ul>
 * <li>{@link FlatTop5mWindowFunction}</li>
 * <li>{@link FlatTopParabolicWindowFunction}</li>
 * </ul>
 *
 * @author Mark Jeronimus
 */
// Created 2014-04-29
// Changed 2016-03-29
// Changed 2019-08-30
@Node(name = "Flat-Top (SFT3M)", description = "A flat-top variation of a 3-term cosine series window")
public class FlatTop3mWindowFunction extends AbstractWindowFunction {
	@Override
	public double getValueAt(double x) {
		return 0.28235
		       - 0.52105 * Math.cos(TAU * 1 * x)
		       + 0.19659 * Math.cos(TAU * 2 * x);
	}
}
