package nl.airsupplies.utilities.signal.window.generalized;

import nl.airsupplies.utilities.nodes.Node;
import nl.airsupplies.utilities.signal.window.AbstractGeneralizedWindowFunction;
import nl.airsupplies.utilities.signal.window.ParabolicWindowFunction;
import nl.airsupplies.utilities.signal.window.WindowFunction;

/**
 * A parabolic {@link WindowFunction}.
 * <p>
 * Specializations (less parameter):
 * <ul>
 * <li>{@link ParabolicWindowFunction} with </li>
 * </ul>
 *
 * @author Mark Jeronimus
 */
// Created 2014-04-22
// Changed 2016-03-31
// Changed 2019-08-30
@Node(name = "Parabola (generalized)", description = "Generalized Parabolic")
public class GeneralizedParabolicWindowFunction extends
                                                AbstractGeneralizedWindowFunction {
	@Override
	public double getValueAt(double x) {
		// Needs to be normalized because of the generalization steps that follow.
		return x * (1 - x) * 4;
	}
}
