package nl.airsupplies.utilities.signal.interpolation;

import nl.airsupplies.utilities.signal.Wave;

/**
 * General superclass for interpolation methods that work with {@link Wave}s.
 *
 * @author Mark Jeronimus
 */
// Created 2013-12-19
public abstract class SignalInterpolation {
	// Wraps:
	protected final int      minDegree;
	protected final int      maxDegree;
	// State:
	protected final double[] parameters;
	protected       int      degree;

	protected SignalInterpolation(int numParameters, int minDegree, int maxDegree, int degree) {
		parameters     = new double[numParameters];
		this.minDegree = minDegree;
		this.maxDegree = maxDegree;
		this.degree    = degree;
	}

	/**
	 * Returns the minimum degree of the interpolation function. The degree is defined as the number of samples to look
	 * at in either direction when interpolating.
	 */
	public int getMinDegree() {
		return minDegree;
	}

	/**
	 * Returns the maximum degree of the interpolation function. The degree is defined as the number of samples to look
	 * at in either direction when interpolating.
	 */
	public int getMaxDegree() {
		return maxDegree;
	}

	/**
	 * Returns the current degree of the interpolation function. The degree is defined as the number of samples to look
	 * at in either direction when interpolating.
	 */
	public int getDegree() {
		return degree;
	}

	/**
	 * Set the degree of the interpolation function. The degree is defined as the number of samples to look at in
	 * either
	 * direction when interpolating. For example,
	 */
	public void setDegree(int degree) {
		if (degree < minDegree || degree > maxDegree) {
			throw new IllegalArgumentException("Degree out of range: " + degree);
		}
		this.degree = degree;
	}

	/**
	 * Returns the algorithm-dependent parameter count.
	 */
	public int getNumParameters() {
		return parameters.length;
	}

	/**
	 * Returns one of the algorithm-specific parameter values.
	 *
	 * @param index the index into the parameter list. The mapping between parameter and indices is
	 *              algorithm-dependent.
	 * @return the parameter value.
	 */
	public double getParameter(int index) {
		if (index < 0 || index >= parameters.length) {
			throw new IllegalArgumentException("AbstractValidatableParameter index out of range: " + index);
		}
		return parameters[index];
	}

	/**
	 * Sets one of the algorithm-specific parameter values.
	 *
	 * @param index the index into the parameter list. The mapping between parameter and indices is
	 *              algorithm-dependent.
	 * @param value the new value.
	 */
	public void setParameter(int index, double value) {
		if (index < 0 || index >= parameters.length) {
			throw new IllegalArgumentException("AbstractValidatableParameter index out of range: " + index);
		}
		parameters[index] = value;
	}

	/**
	 * Interpolates a {@link Wave}.
	 *
	 * @param waveform the waveform to interpolate.
	 * @param time     the exact time for which the signal value needs to be interpolated.
	 */
	public abstract double interpolate(Wave waveform, double time);

	protected static double getValueSafe(Wave waveform, int index) {
		if (index < 0) {
			index = 0;
		} else if (index >= waveform.getNumberOfValues()) {
			index = waveform.getNumberOfValues() - 1;
		}

		return waveform.getValue(index);
	}
}
