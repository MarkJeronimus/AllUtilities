package nl.airsupplies.utilities.signal.window.generalized;

import java.util.ArrayList;
import java.util.List;

import nl.airsupplies.utilities.nodes.DoubleListParam;
import nl.airsupplies.utilities.nodes.Node;
import nl.airsupplies.utilities.signal.window.AbstractGeneralizedWindowFunction;
import nl.airsupplies.utilities.signal.window.BlackmanWindowFunction;
import nl.airsupplies.utilities.signal.window.ExactBlackmanWindowFunction;
import nl.airsupplies.utilities.signal.window.ExactHammingWindowFunction;
import nl.airsupplies.utilities.signal.window.HammingWindowFunction;
import nl.airsupplies.utilities.signal.window.HannWindowFunction;
import nl.airsupplies.utilities.signal.window.MinimalBlackmanHarris3WindowFunction;
import nl.airsupplies.utilities.signal.window.MinimalBlackmanHarris4WindowFunction;
import nl.airsupplies.utilities.signal.window.MinimalHammingWindowFunction;
import nl.airsupplies.utilities.signal.window.NarrowBlackmanHarris3WindowFunction;
import nl.airsupplies.utilities.signal.window.NarrowBlackmanHarris4WindowFunction;
import nl.airsupplies.utilities.signal.window.WindowFunction;
import static nl.airsupplies.utilities.constant.NumberConstants.TAU;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireAtLeast;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireRange;

/**
 * A cosine series {@link WindowFunction}.
 * <p>
 * Specializations (less parameter):
 * <ul>
 * <li>{@link TukeyWindowFunction}</li>
 * <li>{@link HannWindowFunction}</li>
 * <li>{@link HammingWindowFunction}</li>
 * <li>{@link ExactHammingWindowFunction}</li>
 * <li>{@link MinimalHammingWindowFunction}</li>
 * <li>{@link BlackmanWindowFunction}</li>
 * <li>{@link ExactBlackmanWindowFunction}</li>
 * <li>{@link MinimalBlackmanHarris3WindowFunction}</li>
 * <li>{@link NarrowBlackmanHarris3WindowFunction}</li>
 * <li>{@link MinimalBlackmanHarris4WindowFunction}</li>
 * <li>{@link NarrowBlackmanHarris4WindowFunction}</li>
 * </ul>
 *
 * @author Mark Jeronimus
 */
// Created 2014-04-18
// Changed 2016-04-31
// Changed 2019-08-30
@Node(name = "Cosine (generalized)", description = "Generalized Cosine Series")
public class GeneralizedCosineSeriesWindowGenerator extends
                                                    AbstractGeneralizedWindowFunction {
	@DoubleListParam(description = "A list of coefficient parameters, starting with the period-1 cosine",
	                 minLength = 1)
	private final List<Double> coefficients = new ArrayList<>(10);

	public GeneralizedCosineSeriesWindowGenerator() {
		coefficients.add(0.0);
	}

	public int getNumCoefficients() {
		return coefficients.size();
	}

	public void setNumCoefficients(int numCoefficients) {
		requireAtLeast(1, numCoefficients, "numCoefficients");

		while (coefficients.size() > numCoefficients) {
			coefficients.remove(coefficients.size() - 1);
		}

		while (coefficients.size() < numCoefficients) {
			coefficients.add(0.0);
		}
	}

	public double getCoefficient(int index) {
		return coefficients.get(requireRange(0, coefficients.size(), index, "index"));
	}

	public void setCoefficient(int index, double coefficient) {
		coefficients.set(requireRange(0, coefficients.size(), index, "index"), coefficient);
	}

	@Override
	public double getValueAt(double x) {
		int numCoefficients = coefficients.size();

		double sum      = 0;
		double polarity = 1;
		for (int i = 0; i < numCoefficients; i++) {
			sum += polarity * coefficients.get(i) * Math.cos(TAU * i * x);
			polarity *= -1;
		}

		return sum;
	}
}
