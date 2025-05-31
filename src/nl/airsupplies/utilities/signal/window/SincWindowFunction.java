package nl.airsupplies.utilities.signal.window;

import nl.airsupplies.utilities.NumberUtilities;
import nl.airsupplies.utilities.nodes.IntParam;
import nl.airsupplies.utilities.nodes.Node;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireAtLeast;
import static nl.airsupplies.utilities.constant.NumberConstants.TAU;

/**
 * A sinc {@link WindowFunction}.
 *
 * @author Mark Jeronimus
 */
// Created 2016-22-28
// Changed 2016-03-28
// Changed 2019-08-30
@Node(name = "Sinc", description = "A window with a Sinc shape")
public class SincWindowFunction extends AbstractWindowFunction {
	@IntParam(description = "", min = 1)
	private int degree = 3;

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = requireAtLeast(1, degree, "degree");
	}

	@Override
	public double getValueAt(double x) {
		int degree = getDegree();
		return NumberUtilities.sinc((x - 0.5) * TAU * degree);
	}
}
