package nl.airsupplies.utilities.signal.window.generalized;

import nl.airsupplies.utilities.nodes.Node;
import nl.airsupplies.utilities.signal.window.AbstractGeneralizedWindowFunction;
import nl.airsupplies.utilities.signal.window.HannWindowFunction;
import nl.airsupplies.utilities.signal.window.RectangularWindowFunction;
import nl.airsupplies.utilities.signal.window.WindowFunction;
import static nl.airsupplies.utilities.constant.NumberConstants.TAU;

/**
 * A Tukey {@link WindowFunction}.
 * <p>
 * It's designed to retain as much spectral power as possible (like the Rectangle window), but still has smooth fall-off
 * at the edges (like the Hann window). It's constructed by convolving a Rectangle window with a Hann window. An
 * alternative interpretation is the Hann window split in the middle with a rectangle window inserted in the gap.
 * <p>
 * Synonyms (more parameters):
 * <ul>
 * <li>Cosine-tapered window</li>
 * </ul>
 * <p>
 * Generalizations (more parameters):
 * <ul>
 * <li>{@link GeneralizedCosineSeriesWindowGenerator} when </li>
 * </ul>
 * <p>
 * Specializations (less parameters):
 * <ul>
 * <li>{@link RectangularWindowFunction} when {@code taper = 0}</li>
 * <li>{@link HannWindowFunction} when {@code taper = 1}</li>
 * </ul>
 *
 * @author Mark Jeronimus
 */
// Created 2014-04-18
// Changed 2016-03-27
// Changed 2019-08-30
// TODO make non-generalized
@Node(name = "Tukey (generalized)")
public class TukeyWindowFunction extends AbstractGeneralizedWindowFunction {
	@Override
	public double getValueAt(double x) {
		return 1 - Math.cos(x * TAU);
	}
}
