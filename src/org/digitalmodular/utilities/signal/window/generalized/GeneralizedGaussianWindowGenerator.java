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

import org.digitalmodular.utilities.nodes.DoubleParam;
import org.digitalmodular.utilities.nodes.Node;
import org.digitalmodular.utilities.nodes.ParameterCurve;
import org.digitalmodular.utilities.signal.window.AbstractGeneralizedWindowFunction;
import org.digitalmodular.utilities.signal.window.WindowFunction;
import static org.digitalmodular.utilities.ValidatorUtilities.requireRange;

/**
 * A gaussian {@link WindowFunction}.
 *
 * @author Mark Jeronimus
 */
// Created 2014-04-22
// Changed 2016-03-31
// Changed 2019-08-30
@Node(name = "Gaussian (generalized)", description = "Generalized Gaussian")
public class GeneralizedGaussianWindowGenerator extends
                                                AbstractGeneralizedWindowFunction {
	@DoubleParam(description = "The sigma parameter for the gaussian curve",
	             min = 0.01, max = 100, curve = ParameterCurve.LOGARITHMIC)
	private double sigma = 6.0;

	public double getSigma() {
		return sigma;
	}

	public void setSigma(double sigma) {
		this.sigma = requireRange(0.01, 100, sigma, "sigma");
	}

	@Override
	public double getValueAt(double x) {
		double sigma = getSigma();

		// Convert range to -1..1
		x = x * 2 - 1;

		return Math.exp(-sigma * x * x);
	}
}
