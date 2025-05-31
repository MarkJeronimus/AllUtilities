package nl.airsupplies.utilities.math;

import java.util.Random;

/**
 * @author Mark Jeronimus
 */
// Created 2005-10-08
public class SpiroSin {
	private static final int NUM_TERMS = 4;

	private final Random   randomNumberGenerator = new Random();
	private final double[] frequency             = new double[NUM_TERMS];
	private final double[] phase                 = new double[NUM_TERMS];
	private final double[] amplitude             = new double[NUM_TERMS];

	public SpiroSin() {
		double sum = 0;

		// Generate random parameter
		for (int i = 0; i < NUM_TERMS; i++) {
			frequency[i] = randomNumberGenerator.nextFloat();
			phase[i]     = 2 * (float)Math.PI * randomNumberGenerator.nextFloat();
			amplitude[i] = randomNumberGenerator.nextFloat();
			sum += amplitude[i];
		}

		// Normalize sum(amplitude) to 1
		sum = 1 / sum;
		for (int i = 0; i < NUM_TERMS; i++) {
			amplitude[i] *= sum;
		}
	}

	public double get(double phase) {
		float sum = 0;
		for (int i = 0; i < NUM_TERMS; i++) {
			sum += Math.sin(phase * frequency[i] + this.phase[i]) * amplitude[i];
		}
		return sum;
	}
}
