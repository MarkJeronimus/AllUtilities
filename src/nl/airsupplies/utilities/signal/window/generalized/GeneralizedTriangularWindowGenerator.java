package nl.airsupplies.utilities.signal.window.generalized;

import nl.airsupplies.utilities.nodes.Node;
import nl.airsupplies.utilities.signal.window.AbstractGeneralizedWindowFunction;
import nl.airsupplies.utilities.signal.window.TriangularWindowFunction;
import nl.airsupplies.utilities.signal.window.WindowFunction;

/**
 * A triangular {@link WindowFunction}.
 * <p>
 * Specializations (less parameters):
 * <ul>
 * <li>{@link TriangularWindowFunction} when {@code bottom = 0}</li>
 * </ul>
 *
 * @author Mark Jeronimus
 */
// Created 2014-04-22
// Changed 2016-03-31
// Changed 2019-08-30
@Node(name = "Triangular (generalized)", description = "Generalized Triangular")
public class GeneralizedTriangularWindowGenerator extends
                                                  AbstractGeneralizedWindowFunction {
	@Override
	public double getValueAt(double x) {
		// Needs to be normalized because of the generalization steps that follow.
		return 1 - Math.abs(x * 2 - 1);
	}
}
