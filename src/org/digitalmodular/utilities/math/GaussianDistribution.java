package org.digitalmodular.utilities.math;

import java.util.DoubleSummaryStatistics;
import java.util.function.DoubleConsumer;

import net.jcip.annotations.NotThreadSafe;

/**
 * @author Mark Jeronimus
 * @see DoubleSummaryStatistics
 */
// Created 2018-05-07
@NotThreadSafe
public class GaussianDistribution implements DoubleConsumer {
	private long   count             = 0;
	private double runningSum        = 0;
	private double runningSumSquared = 0;

	public void reset() {
		count             = 0;
		runningSum        = 0;
		runningSumSquared = 0;
	}

	@Override
	public void accept(double value) {
		runningSum += value;
		runningSumSquared += value * value;
		count++;
	}

	public long getCount() {
		return count;
	}

	public double getMean() {
		return runningSum / count;
	}

	public double getVariance() {
		double runningMean = runningSum / count;
		return runningSumSquared / count - runningMean * runningMean;
	}

	public double getStandardDeviation() {
		return Math.sqrt(getVariance());
	}

	/**
	 * (Copied from {@link DoubleSummaryStatistics})
	 * <p>
	 * Combines the state of another {@code DoubleSummaryStatistics} into this
	 * one.
	 *
	 * @param other another {@code DoubleSummaryStatistics}
	 * @throws NullPointerException if {@code other} is null
	 */
	public void combine(GaussianDistribution other) {
		count += other.count;
		runningSum += other.runningSum;
		runningSumSquared += other.runningSumSquared;
	}

	@Override
	public String toString() {
		return "GaussianDistribution{" +
		       "mean=" + getMean() +
		       ", variance=" + getVariance() +
		       ", standardDeviation=" + getStandardDeviation() +
		       '}';
	}
}
