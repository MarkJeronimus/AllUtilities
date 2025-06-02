package nl.airsupplies.utilities.signal.window;

import nl.airsupplies.utilities.nodes.Node;
import nl.airsupplies.utilities.signal.window.generalized.TukeyWindowFunction;

/**
 * A rectangular {@link WindowFunction}.
 * <p>
 * Synonyms:
 * <ul>
 * <li>boxcar</li>
 * <li>Dirichlet</li>
 * <li>1st order B-spline</li>
 * <li>unity function</li>
 * <li>'no window'</li>
 * </ul>
 * <p>
 * Generalizations (more parameters):
 * <ul>
 * <li>{@link TukeyWindowFunction} when {@code taper = {0}}</li>
 * </ul>
 *
 * @author Mark Jeronimus
 */
// Created 2014-04-17
// Changed 2016-03-28
// Changed 2019-08-30
@Node(name = "Rectangle", description = "The rectangle window (unity function)")
public class RectangularWindowFunction extends AbstractWindowFunction {
	@Override
	public double getValueAt(double x) {
		return 1;
	}
}
