package nl.airsupplies.utilities.math;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import nl.airsupplies.utilities.annotation.UtilityClass;
import nl.airsupplies.utilities.constant.NumberConstants;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireAtLeast;

/**
 * @author Mark Jeronimus
 */
// Created 2021-11-13
@UtilityClass
public final class CirclePackingGenerator {
	public enum PackingMode {
		EVEN,
		EVEN_ODD,
		TIGHT
	}

	public static List<Point2D.Double> calculate(int numShells, PackingMode mode) {
		requireAtLeast(1, numShells, "numShells");

		List<Point2D.Double> points = new ArrayList<>(numShells * (numShells - 1) * 3 + 1);

		points.add(new Point2D.Double(0, 0));
		for (int i = 1; i < numShells; i++) {
			double radius = (double)i / (numShells - 1);

			int shellCount;
			if (mode == PackingMode.TIGHT) {
				shellCount = (int)Math.floor(i * NumberConstants.TAU);
			} else {
				shellCount = i * 6;
			}

			for (int j = 0; j < shellCount; j++) {
				double angle = j;

				if (mode == PackingMode.EVEN_ODD && i % 2 == 0) {
					angle += 0.5;
				}

				angle *= NumberConstants.TAU / shellCount;

				double x = Math.cos(angle) * radius;
				double y = Math.sin(angle) * radius;
				points.add(new Point2D.Double(x, y));
			}
		}

		return points;
	}
}
