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
import org.digitalmodular.utilities.signal.window.generalized.GeneralizedCosineSeriesWindowGenerator;
import static org.digitalmodular.utilities.constant.NumberConstants.TAU;

/**
 * Generator for Harris' 4-term minimal Blackman {@link WindowFunction}.
 * <p>
 * Generalizations (more parameter):
 * <ul>
 * <li>{@link GeneralizedCosineSeriesWindowGenerator}</li>
 * </ul>
 * <p>
 * Variations:
 * <ul>
 * <li>{@link BlackmanWindowFunction} (where the original coefficients are rounded to two decimal places)</li>
 * <li>{@link ExactBlackmanWindowFunction} (where the original coefficients are not rounded)</li>
 * <li>{@link MinimalBlackmanHarris3WindowFunction}</li>
 * <li>{@link NarrowBlackmanHarris3WindowFunction}</li>
 * <li>{@link NarrowBlackmanHarris4WindowFunction}</li>
 * <li>{@link BlackmanNuttall4bWindowGenerator}</li>
 * </ul>
 *
 * @author Mark Jeronimus
 */
// Created 2014-04-18
// Changed 2016-03-29
// Changed 2019-08-30
@Node(name = "4-term minimal Blackman-Harris", description = "4-term minimal Blackman-Harris")
public class MinimalBlackmanHarris4WindowFunction extends AbstractWindowFunction {
	@Override
	@SuppressWarnings("OverlyComplexArithmeticExpression")
	public double getValueAt(double x) {
		return 0.35875
		       - 0.48829 * Math.cos(TAU * 1 * x)
		       + 0.14128 * Math.cos(TAU * 2 * x)
		       - 0.01168 * Math.cos(TAU * 3 * x);
	}
}
