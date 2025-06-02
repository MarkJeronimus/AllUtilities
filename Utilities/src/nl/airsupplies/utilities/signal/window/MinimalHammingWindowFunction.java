package nl.airsupplies.utilities.signal.window;

import nl.airsupplies.utilities.nodes.Node;
import nl.airsupplies.utilities.signal.window.generalized.GeneralizedCosineSeriesWindowGenerator;
import static nl.airsupplies.utilities.constant.NumberConstants.TAU;

/**
 * A Minimal Hamming {@link WindowFunction}.
 * <p>
 * Variations:
 * <ul>
 * <li>{@link HammingWindowFunction} (where the coefficients are rounded to two decimal places)</li>
 * <li>{@link ExactHammingWindowFunction} (where the coefficients are not rounded)</li>
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
@Node(name = "Hamming (minimal)", description = "Minimal Hamming")
public class MinimalHammingWindowFunction extends AbstractWindowFunction {
	@Override
	public double getValueAt(double x) {
		return 0.538379 - 0.461621 * Math.cos(TAU * 1 * x);
	}
}
