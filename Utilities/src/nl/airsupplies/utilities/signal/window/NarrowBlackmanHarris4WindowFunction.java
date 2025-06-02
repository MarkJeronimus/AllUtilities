package nl.airsupplies.utilities.signal.window;

import nl.airsupplies.utilities.nodes.Node;
import nl.airsupplies.utilities.signal.window.generalized.GeneralizedCosineSeriesWindowGenerator;
import static nl.airsupplies.utilities.constant.NumberConstants.TAU;

/**
 * Generator for Harris' 4-term narrow Blackman-Harris {@link WindowFunction}.
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
 * <li>{@link MinimalBlackmanHarris4WindowFunction}</li>
 * <li>{@link NarrowBlackmanHarris3WindowFunction}</li>
 * <li>{@link BlackmanNuttall4bWindowGenerator}</li>
 * </ul>
 *
 * @author Mark Jeronimus
 */
// Created 2014-04-18
// Changed 2016-03-29
// Changed 2019-08-30
@Node(name = "4-term narrow Blackman-Harris", description = "4-term narrow Blackman-Harris")
public class NarrowBlackmanHarris4WindowFunction extends AbstractWindowFunction {
	@Override
	public double getValueAt(double x) {
		return 0.40217 +
		       -0.49703 * Math.cos(TAU * 1 * x) +
		       0.09392 * Math.cos(TAU * 2 * x) +
		       -0.00183 * Math.cos(TAU * 3 * x);
	}
}
