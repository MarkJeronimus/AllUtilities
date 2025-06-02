package nl.airsupplies.utilities.signal.window;

import nl.airsupplies.utilities.nodes.Node;
import nl.airsupplies.utilities.signal.window.generalized.GeneralizedTriangularWindowGenerator;

/**
 * A Triangular {@link WindowFunction}.
 * <p>
 * Synonyms:
 * <ul>
 * <li>Barlett</li>
 * <li>Tent</li>
 * </ul>
 * <p>
 * Generalizations (more parameters):
 * <ul>
 * <li>{@link GeneralizedTriangularWindowGenerator} when </li>
 * </ul>
 *
 * @author Mark Jeronimus
 */
// Created 2014-04-18
// Changed 2016-03-28
// Changed 2019-08-30
@Node(name = "Triangle", description = "Triangular window")
public class TriangularWindowFunction extends AbstractWindowFunction {
	@Override
	public double getValueAt(double x) {
		return 0.5 - Math.abs(x - 0.5);
	}
}
