package nl.airsupplies.utilities.signal.interpolation;

import nl.airsupplies.utilities.signal.Wave;

/**
 * An interpolation technique that interpolates linearly between neighboring values.
 *
 * @author Mark Jeronimus
 */
// Created 2014-02-12
public class LinearInterpolation extends SignalInterpolation {
	public LinearInterpolation() {
		super(0, 0, 0, 0);
	}

	@Override
	public double interpolate(Wave waveform, double time) {
		int    i = (int)Math.floor(time); // integer part (of sample index)
		double f = time - i;           // fractional part (of sample index)

		// Interpolate with 2*degree pieces of Hermite Spline.
		double f0 = getValueSafe(waveform, i);
		double f1 = getValueSafe(waveform, i + 1);

		return f0 + (f1 - f0) * f;
	}
}
