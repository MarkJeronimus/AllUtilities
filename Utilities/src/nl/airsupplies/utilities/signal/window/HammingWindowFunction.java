package nl.airsupplies.utilities.signal.window;

import nl.airsupplies.utilities.nodes.Node;
import nl.airsupplies.utilities.signal.window.generalized.GeneralizedCosineSeriesWindowGenerator;
import static nl.airsupplies.utilities.constant.NumberConstants.TAU;

/**
 * A Hamming {@link WindowFunction}.
 * <p>
 * Variations:
 * <ul>
 * <li>{@link ExactHammingWindowFunction} (where the coefficients are not rounded)</li>
 * <li>{@link MinimalHammingWindowFunction}</li>
 * </ul>
 * <p>
 * Generalizations (more parameter):
 * <ul>
 * <li>{@link GeneralizedCosineSeriesWindowGenerator} with {@code coefficients = [0.54, 0.46]}</li>
 * </ul>
 *
 * @author Mark Jeronimus
 */
// Created 2014-04-18
// Changed 2016-16-28
// Changed 2019-08-30
@Node(name = "Hamming")
public class HammingWindowFunction extends AbstractWindowFunction {
	@Override
	public double getValueAt(double x) {
		return 0.54 - 0.46 * Math.cos(TAU * x);
	}
}
