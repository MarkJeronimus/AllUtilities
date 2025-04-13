package org.digitalmodular.utilities.math;

/**
 * @author Mark Jeronimus
 */
public class GaussianProbabilityDistribution {
	private double runningSum;
	private double runningSumSquared;

	private double numSamples;

	public GaussianProbabilityDistribution() {
		reset();
	}

	public void reset() {
		runningSum = 0;
		runningSumSquared = 0;
		numSamples = 0;
	}

	public void addSample(double value, double amount) {
		double product = value * amount;
		runningSum += product;
		runningSumSquared += product * product;
		numSamples += amount;
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
