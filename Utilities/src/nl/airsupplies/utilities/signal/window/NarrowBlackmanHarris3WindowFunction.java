package nl.airsupplies.utilities.signal.window;

import nl.airsupplies.utilities.nodes.Node;
import nl.airsupplies.utilities.signal.window.generalized.GeneralizedCosineSeriesWindowGenerator;
import static nl.airsupplies.utilities.constant.NumberConstants.TAU;

/**
 * Generator for Harris' 3-term narrow Blackman-Harris {@link WindowFunction}.
 * <p>
 * Generalizations (more parameter):
 * <ul>
 * <li>{@link GeneralizedCosineSeriesWindowGenerator}
 * </ul>
 * <p>
 * Variations:
 * <ul>
 * <li>{@link BlackmanWindowFunction} (where the original coefficients are rounded to two decimal places)</li>
 * <li>{@link ExactBlackmanWindowFunction} (where the original coefficients are not rounded)</li>
 * <li>{@link MinimalBlackmanHarris3WindowFunction}</li>
 * <li>{@link MinimalBlackmanHarris4WindowFunction}</li>
 * <li>{@link NarrowBlackmanHarris4WindowFunction}</li>
 * <li>{@link BlackmanNuttall4bWindowGenerator}</li>
 * </ul>
 *
 * @author Mark Jeronimus
 */
// Created 2014-04-18
// Changed 2016-03-29
// Changed 2019-08-30
@Node(name = "3-term narrow Blackman-Harris", description = "3-term narrow Blackman-Harris")
public class NarrowBlackmanHarris3WindowFunction extends AbstractWindowFunction {
	@Override
	public double getValueAt(double x) {
		return 0.44959 +
		       -0.49364 * Math.cos(TAU * 1 * x) +
		       0.05677 * Math.cos(TAU * 2 * x);
	}
}
