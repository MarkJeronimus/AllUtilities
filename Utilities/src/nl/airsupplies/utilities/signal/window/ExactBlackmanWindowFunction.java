package nl.airsupplies.utilities.signal.window;

import nl.airsupplies.utilities.nodes.Node;
import nl.airsupplies.utilities.signal.window.generalized.GeneralizedCosineSeriesWindowGenerator;
import static nl.airsupplies.utilities.constant.NumberConstants.TAU;

/**
 * An Exact Blackman {@link WindowFunction}.
 * <p>
 * Variations:
 * <ul>
 * <li>{@link BlackmanWindowFunction} (where the  coefficients are rounded to two decimal places)</li>
 * <li>{@link MinimalBlackmanHarris3WindowFunction}</li>
 * <li>{@link MinimalBlackmanHarris4WindowFunction}</li>
 * <li>{@link NarrowBlackmanHarris3WindowFunction}</li>
 * <li>{@link NarrowBlackmanHarris4WindowFunction}</li>
 * <li>{@link BlackmanNuttall4bWindowGenerator}</li>
 * </ul>
 * <p>
 * Generalizations (more parameters):
 * <ul>
 * <li>{@link GeneralizedCosineSeriesWindowGenerator}</li>
 * </ul>
 *
 * @author Mark Jeronimus
 */
// Created 2014-04-18
// Changed 2016-03-29
// Changed 2019-08-30
@Node(name = "Blackman (exact)", description = "Exact Blackman")
public class ExactBlackmanWindowFunction extends AbstractWindowFunction {
	@Override
	public double getValueAt(double x) {
		// Needs to be normalized because of the generalization steps that follow.
		return (3969
		        - 4620 * Math.cos(TAU * 1 * x)
		        + 715 * Math.cos(TAU * 2 * x)) / 9304;
	}
}
