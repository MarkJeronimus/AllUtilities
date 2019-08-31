package org.digitalmodular.utilities.math;

/**
 * @author Mark Jeronimus
 * @since 1.0
 */
public class GaussianDistribution {
	private double runningSum;
	private double runningSumSquared;

	private long numSamples;

	public GaussianDistribution() {
		reset();
	}

	public void reset() {
		runningSum = 0;
		runningSumSquared = 0;
		numSamples = 0;
	}

	public void addSample(double value) {
		runningSum += value;
		runningSumSquared += value * value;

		numSamples++;
	}

	public double getMean() {
		return runningSum / numSamples;
	}

	public double getVariance() {
		double runningMean = runningSum / numSamples;
		return runningSumSquared / numSamples - runningMean * runningMean;
	}

	public double getStandardDeviation() {
		double runningMean = runningSum / numSamples;
		return Math.sqrt(runningSumSquared / numSamples - runningMean * runningMean);
	}
}
