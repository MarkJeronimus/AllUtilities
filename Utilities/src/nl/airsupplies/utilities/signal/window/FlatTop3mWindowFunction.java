package nl.airsupplies.utilities.signal.window;

import nl.airsupplies.utilities.nodes.Node;
import nl.airsupplies.utilities.signal.window.generalized.FlatTopParabolicWindowFunction;
import nl.airsupplies.utilities.signal.window.generalized.GeneralizedCosineSeriesWindowGenerator;
import static nl.airsupplies.utilities.constant.NumberConstants.TAU;

/**
 * Salvatore's 3-term Flat-top-3M variant b (SFT3M) {@link WindowFunction}.
 * <p>
 * Generalizations (more parameter):
 * <ul>
 * <li>{@link GeneralizedCosineSeriesWindowGenerator} with </li>
 * </ul>
 * <p>
 * Variations:
 * <ul>
 * <li>{@link FlatTop5mWindowFunction}</li>
 * <li>{@link FlatTopParabolicWindowFunction}</li>
 * </ul>
 *
 * @author Mark Jeronimus
 */
// Created 2014-04-29
// Changed 2016-03-29
// Changed 2019-08-30
@Node(name = "Flat-Top (SFT3M)", description = "A flat-top variation of a 3-term cosine series window")
public class FlatTop3mWindowFunction extends AbstractWindowFunction {
	@Override
	public double getValueAt(double x) {
		return 0.28235
		       - 0.52105 * Math.cos(TAU * 1 * x)
		       + 0.19659 * Math.cos(TAU * 2 * x);
	}
}
