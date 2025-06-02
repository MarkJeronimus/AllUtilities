package nl.airsupplies.utilities.signal.window;

import nl.airsupplies.utilities.nodes.Node;
import nl.airsupplies.utilities.signal.window.generalized.GeneralizedCosineSeriesWindowGenerator;
import static nl.airsupplies.utilities.constant.NumberConstants.TAU;

/**
 * An Exact Hamming {@link WindowFunction}.
 * <p>
 * Variations:
 * <ul>
 * <li>{@link HammingWindowFunction} (where the coefficients are rounded to two decimal places)</li>
 * <li>{@link MinimalHammingWindowFunction}</li>
 * </ul>
 * <p>
 * Generalizations (more parameter):
 * <ul>
 * <li>{@link GeneralizedCosineSeriesWindowGenerator}</li>
 * </ul>
 *
 * @author Mark Jeronimus
 */
// Created 2014-04-18
// Changed 2016-03-29
// Changed 2019-08-30
@Node(name = "Hamming (exact)", description = "Exact Hamming")
public class ExactHammingWindowFunction extends AbstractWindowFunction {
	@Override
	public double getValueAt(double x) {
		// Needs to be normalized because of the generalization steps that follow.
		return (25 - 21 * Math.cos(TAU * 1 * x)) / 46;
	}
}
