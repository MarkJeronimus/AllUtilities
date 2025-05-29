package nl.airsupplies.utilities.math;

import nl.airsupplies.utilities.annotation.UtilityClass;

/**
 * @author Mark Jeronimus
 */
// Created 2024-06-04
@UtilityClass
public final class LongUtilities {
	public static long min(long[] array) {
		if (array.length == 1) {
			return array[0];
		} else if (array.length == 2) {
			return Math.min(array[0], array[1]);
		} else if (array.length == 0) {
			throw new IllegalArgumentException("Can't calculate the min of an empty array");
		}

		long min = Integer.MAX_VALUE;

		for (long i : array) {
			min = Math.min(min, i);
		}

		return min;
	}

	public static long max(long[] array) {
		if (array.length == 1) {
			return array[0];
		} else if (array.length == 2) {
			return Math.max(array[0], array[1]);
		} else if (array.length == 0) {
			throw new IllegalArgumentException("Can't calculate the max of an empty array");
		}

		long max = Integer.MIN_VALUE;

		for (long i : array) {
			max = Math.max(max, i);
		}

		return max;
	}

	public static long sum(long[] array) {
		if (array.length == 1) {
			return array[0];
		} else if (array.length == 2) {
			return array[0] + array[1];
		}

		long sum = 0;

		for (long i : array) {
			sum += i;
		}

		return sum;
	}

	public static long average(long[] array) {
		if (array.length == 1) {
			return array[0];
		} else if (array.length == 2) {
			return (array[0] + array[1]) >> 1;
		} else if (array.length == 0) {
			throw new IllegalArgumentException("Can't calculate the average of an empty array");
		}

		long sum = 0;

		for (long i : array) {
			sum += i;
		}

		return (sum + (array.length >> 1)) / array.length;
	}

	public static long product(long[] array) {
		if (array.length == 1) {
			return array[0];
		} else if (array.length == 2) {
			return array[0] * array[1];
		}

		long product = 1;

		for (long i : array) {
			product *= i;
		}

		return product;
	}
}
