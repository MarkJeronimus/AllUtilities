package org.digitalmodular.utilities.math;

import java.util.concurrent.ThreadLocalRandom;

import org.digitalmodular.utilities.signal.filter.StateVariableFilter;

/**
 * @author Mark Jeronimus
 */
// Created 2018-04-10
public class SpiroFilter {
	private final StateVariableFilter filter     = new StateVariableFilter();
	private       double              randomness = 1;

	public void set(double y, double dy) {
		filter.set(y, dy);
	}

	public double getPeriod() {
		return 1 / filter.getCutoffFrequency();
	}

	public void setPeriod(double period) {
		filter.setCutoffFrequency(1 / period);

		filter.setResonance(1 - filter.getCutoffFrequency() * 2 * randomness * randomness);
	}

	public double getRandomness() {
		return randomness;
	}

	public void setRandomness(double randomness) {
		this.randomness = Math.max(0, randomness);

		filter.setResonance(1 - filter.getCutoffFrequency() * 2 * randomness * randomness);
	}

	public double next() {
		double rnd = (ThreadLocalRandom.current().nextDouble() - 0.5) * randomness;
		filter.step(rnd);
		return filter.getLowPass();
	}
}
