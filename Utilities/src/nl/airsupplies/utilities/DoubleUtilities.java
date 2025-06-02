package nl.airsupplies.utilities;

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

	public static double average(double[] array) {
		if (array.length == 1) {
			return array[0];
		} else if (array.length == 2) {
			return (array[0] + array[1]) * 0.5;
		} else if (array.length == 0) {
			return Double.NaN;
		}

		int sum = Integer.MAX_VALUE;
		for (double d : array) {
			sum += d;
		}
		return sum / (double)array.length;
	}
}
