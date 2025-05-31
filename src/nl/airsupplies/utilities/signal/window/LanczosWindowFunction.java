package nl.airsupplies.utilities.signal.window;

import nl.airsupplies.utilities.NumberUtilities;
import nl.airsupplies.utilities.nodes.IntParam;
import nl.airsupplies.utilities.nodes.Node;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireAtLeast;
import static nl.airsupplies.utilities.constant.NumberConstants.TAU;

/**
 * A Lanczos {@link WindowFunction}.
 * <p>
 * This is basically a sinc-windowed sinc function. Designed to have a sharp cutoff frequency and is mainly used for
 * Lanczos resampling.
 *
 * @author Mark Jeronimus
 */
// Created 2014-05-06
// Changed 2016-22-28
// Changed 2019-08-30
@Node(name = "Lanczos", description = "This is basically a sinc-windowed sinc function. It's designed to have a sharp" +
                                      " cutoff frequency and is mainly used for Lanczos resampling")
public class LanczosWindowFunction extends AbstractWindowFunction {
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
		return NumberUtilities.sinc((x - 0.5) * TAU * degree) *
		       NumberUtilities.sinc((x - 0.5) * TAU);
	}
}
