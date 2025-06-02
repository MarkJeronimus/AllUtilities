package nl.airsupplies.utilities.signal.window.generalized;

import nl.airsupplies.utilities.nodes.Node;
import nl.airsupplies.utilities.signal.window.AbstractGeneralizedWindowFunction;
import nl.airsupplies.utilities.signal.window.HannWindowFunction;
import nl.airsupplies.utilities.signal.window.WindowFunction;
import static nl.airsupplies.utilities.constant.NumberConstants.TAU05;

/**
 * A sine lobe {@link WindowFunction}.
 * <p>
 * Specializations (less parameter):
 * <ul>
 * <li>{@link HannWindowFunction} when {@code power = 2}</li>
 * </ul>
 *
 * @author Mark Jeronimus
 */
// Created 2014-04-22
// Changed 2016-03-31
// Changed 2019-08-30
@Node(name = "SineLobe (generalized)", description = "Half a period of the sine function, from 0 to Tau/2")
public class GeneralizedSineLobeWindowGenerator extends
                                                AbstractGeneralizedWindowFunction {
	@Override
	public double getValueAt(double x) {
		return Math.sin(TAU05 * x);
	}
}
