package nl.airsupplies.utilities.signal.window;

import nl.airsupplies.utilities.nodes.Node;
import nl.airsupplies.utilities.signal.window.generalized.TukeyWindowFunction;
import static nl.airsupplies.utilities.constant.NumberConstants.TAU;

/**
 * A Hann {@link WindowFunction}.
 * <p>
 * The Hann window consists of a complete period of the cosine.
 * <p>
 * Sometimes it's erroneously referred to as the Hann window. Not to be confused with the Hamming window (correct
 * spelling) which is a different window function.
 * <p>
 * Generalizations (more parameter):
 * <ul>
 * <li>{@link TukeyWindowFunction} when {@code taper = 1.0} </li>
 * </ul>
 *
 * @author Mark Jeronimus
 * @see HammingWindowFunction
 */
// Created 2014-04-18
// Changed 2016-16-28
// Changed 2019-08-30
@Node(name = "Hann", description = "The Hann window consists of a complete period of the cosine.\n" +
                                   "Sometimes it's erroneously referred to as the Hann window.\n" +
                                   "Not to be confused with the Hamming window (correct spelling) which is different.")
public class HannWindowFunction extends AbstractWindowFunction {
	@Override
	public double getValueAt(double x) {
		return 1 - Math.cos(TAU * x);
	}
}
