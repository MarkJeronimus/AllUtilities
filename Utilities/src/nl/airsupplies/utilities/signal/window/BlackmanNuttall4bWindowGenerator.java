package nl.airsupplies.utilities.signal.window;

import nl.airsupplies.utilities.nodes.Node;
import nl.airsupplies.utilities.signal.window.generalized.GeneralizedCosineSeriesWindowGenerator;
import static nl.airsupplies.utilities.constant.NumberConstants.TAU;

/**
 * Nuttall's 4-term Blackman-4b {@link WindowFunction}.
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
 * <li>{@link NarrowBlackmanHarris4WindowFunction}</li>
 * </ul>
 *
 * @author Mark Jeronimus
 */
// Created 2014-04-29
// Changed 2016-03-30
@Node(name = "Nuttall4c", description = "4-term Blackman-Nuttall variant b")
public class BlackmanNuttall4bWindowGenerator extends AbstractWindowFunction {
	@Override
	@SuppressWarnings("OverlyComplexArithmeticExpression")
	public double getValueAt(double x) {
		return 0.355768
		       - 0.487396 * Math.cos(TAU * 1 * x)
		       + 0.144232 * Math.cos(TAU * 2 * x)
		       - 0.012604 * Math.cos(TAU * 3 * x);
	}
}
