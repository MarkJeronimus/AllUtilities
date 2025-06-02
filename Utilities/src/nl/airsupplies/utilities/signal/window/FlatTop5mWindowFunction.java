package nl.airsupplies.utilities.signal.window;

import nl.airsupplies.utilities.nodes.Node;
import nl.airsupplies.utilities.signal.window.generalized.FlatTopParabolicWindowFunction;
import nl.airsupplies.utilities.signal.window.generalized.GeneralizedCosineSeriesWindowGenerator;
import static nl.airsupplies.utilities.constant.NumberConstants.TAU;

/**
 * Salvatore's 5-term Flat-top-5M variant b (SFT5M) {@link WindowFunction}.
 * <p>
 * Generalizations (more parameter):
 * <ul>
 * <li>{@link GeneralizedCosineSeriesWindowGenerator} with </li>
 * </ul>
 * <p>
 * Variations:
 * <ul>
 * <li>{@link FlatTop3mWindowFunction}</li>
 * <li>{@link FlatTopParabolicWindowFunction}</li>
 * </ul>
 *
 * @author Mark Jeronimus
 */
// Created 2014-04-29
// Changed 2016-03-29
// Changed 2019-08-30
@Node(name = "Flat-Top (SFT5M)", description = "A flat-top variation of a 5-term cosine series window")
public class FlatTop5mWindowFunction extends AbstractWindowFunction {
	@Override
	@SuppressWarnings("OverlyComplexArithmeticExpression")
	public double getValueAt(double x) {
		return 0.209671
		       - 0.407331 * Math.cos(TAU * 1 * x)
		       + 0.281225 * Math.cos(TAU * 2 * x)
		       - 0.092669 * Math.cos(TAU * 3 * x)
		       + 0.0091036 * Math.cos(TAU * 4 * x);
	}
}
