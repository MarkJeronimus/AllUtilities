package nl.airsupplies.utilities.math;

import nl.airsupplies.utilities.annotation.UtilityClass;

/**
 * @author Mark Jeronimus
 */
// Created 2024-06-04
@UtilityClass
public final class DoubleUtilities {
	public static double min(double[] array) {
		if (array.length == 1) {
			return array[0];
		} else if (array.length == 2) {
			return Math.min(array[0], array[1]);
		} else if (array.length == 0) {
			throw new IllegalArgumentException("Can't calculate the min of an empty array");
		}

		double min = Integer.MAX_VALUE;
		for (double i : array) {
			min = Math.min(min, i);
		}

		return min;
	}

	public static double max(double[] array) {
		if (array.length == 1) {
			return array[0];
		} else if (array.length == 2) {
			return Math.max(array[0], array[1]);
		} else if (array.length == 0) {
			throw new IllegalArgumentException("Can't calculate the max of an empty array");
		}

		double max = Integer.MIN_VALUE;
		for (double i : array) {
			max = Math.max(max, i);
		}

		return max;
	}

	public static double sum(double[] array) {
		if (array.length == 1) {
			return array[0];
		} else if (array.length == 2) {
			return array[0] + array[1];
		}

		double sum = 0.0;
		for (double i : array) {
			sum += i;
		}

		return sum;
	}

	public static double average(double[] array) {
		if (array.length == 1) {
			return array[0];
		} else if (array.length == 2) {
			return (array[0] + array[1]) / 2.0f;
		} else if (array.length == 0) {
			throw new IllegalArgumentException("Can't calculate the average of an empty array");
		}

		double sum = 0.0;
		for (double i : array) {
			sum += i;
		}

		return sum / array.length;
	}

	public static double product(double[] array) {
		if (array.length == 1) {
			return array[0];
		} else if (array.length == 2) {
			return array[0] * array[1];
		}

		double product = 1.0;
		for (double i : array) {
			product *= i;
		}

		return product;
	}
}
