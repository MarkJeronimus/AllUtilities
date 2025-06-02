package nl.airsupplies.utilities.signal.window;

import nl.airsupplies.utilities.nodes.Node;
import nl.airsupplies.utilities.signal.window.generalized.GeneralizedParabolicWindowFunction;
import nl.airsupplies.utilities.signal.window.generalized.GeneralizedTriangularWindowGenerator;

/**
 * A Parabolic {@link WindowFunction}.
 * <p>
 * Synonyms:
 * <ul>
 * <li>Welch</li>
 * <li>(Inverse) Parabolic</li>
 * </ul>
 * <p>
 * Generalizations (more parameter):
 * <ul>
 * <li>{@link GeneralizedTriangularWindowGenerator}</li>
 * <li>{@link GeneralizedParabolicWindowFunction}</li>
 * </ul>
 *
 * @author Mark Jeronimus
 */
// Created 2014-04-18
// Changed 2016-03-29
// Changed 2019-08-30
@Node(name = "Parabola", description = "An inverse parabolic window")
public class ParabolicWindowFunction extends AbstractWindowFunction {
	@Override
	public double getValueAt(double x) {
		return x * (1 - x);
	}
}
