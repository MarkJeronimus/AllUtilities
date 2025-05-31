package nl.airsupplies.utilities.signal.window.generalized;

import nl.airsupplies.utilities.nodes.DoubleParam;
import nl.airsupplies.utilities.nodes.Node;
import nl.airsupplies.utilities.nodes.ParameterCurve;
import nl.airsupplies.utilities.signal.window.AbstractGeneralizedWindowFunction;
import nl.airsupplies.utilities.signal.window.WindowFunction;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireRange;

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
