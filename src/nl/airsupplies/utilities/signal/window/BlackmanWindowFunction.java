package nl.airsupplies.utilities.signal.window;

import nl.airsupplies.utilities.nodes.Node;
import nl.airsupplies.utilities.signal.window.generalized.GeneralizedCosineSeriesWindowGenerator;
import static nl.airsupplies.utilities.constant.NumberConstants.TAU;

/**
 * A Blackman {@link WindowFunction}.
 * <p>
 * Variations:
 * <ul>
 * <li>{@link ExactBlackmanWindowFunction} (where the coefficients are not rounded)</li>
 * <li>{@link MinimalBlackmanHarris3WindowFunction}</li>
 * <li>{@link MinimalBlackmanHarris4WindowFunction}</li>
 * <li>{@link NarrowBlackmanHarris3WindowFunction}</li>
 * <li>{@link NarrowBlackmanHarris4WindowFunction}</li>
 * <li>{@link BlackmanNuttall4bWindowGenerator}</li>
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
// Changed 2016-03-28
// Changed 2019-08-30
@Node(name = "Blackman")
public class BlackmanWindowFunction extends AbstractWindowFunction {
	@Override
	public double getValueAt(double x) {
		return 0.42
		       - 0.50 * Math.cos(TAU * 1 * x)
		       + 0.08 * Math.cos(TAU * 2 * x);
	}
}
