package nl.airsupplies.utilities.math;

import nl.airsupplies.utilities.annotation.UtilityClass;

/**
 * @author Mark Jeronimus
 */
// Created 2024-06-04
@UtilityClass
public final class IntUtilities {
	public static int min(int[] array) {
		if (array.length == 1) {
			return array[0];
		} else if (array.length == 2) {
			return Math.min(array[0], array[1]);
		} else if (array.length == 0) {
			throw new IllegalArgumentException("Can't calculate the min of an empty array");
		}

		int min = Integer.MAX_VALUE;

		for (int i : array) {
			min = Math.min(min, i);
		}

		return min;
	}

	public static int max(int[] array) {
		if (array.length == 1) {
			return array[0];
		} else if (array.length == 2) {
			return Math.max(array[0], array[1]);
		} else if (array.length == 0) {
			throw new IllegalArgumentException("Can't calculate the max of an empty array");
		}

		int max = Integer.MIN_VALUE;

		for (int i : array) {
			max = Math.max(max, i);
		}

		return max;
	}

	public static int sum(int[] array) {
		if (array.length == 1) {
			return array[0];
		} else if (array.length == 2) {
			return array[0] + array[1];
		}

		int sum = 0;

		for (int i : array) {
			sum += i;
		}

		return sum;
	}

	public static long longSum(int[] array) {
		if (array.length == 1) {
			return array[0];
		} else if (array.length == 2) {
			return array[0] + array[1];
		}

		long sum = 0;

		for (int i : array) {
			sum += i;
		}

		return sum;
	}

	public static int average(int[] array) {
		if (array.length == 1) {
			return array[0];
		} else if (array.length == 2) {
			return (array[0] + array[1]) >> 1;
		} else if (array.length == 0) {
			throw new IllegalArgumentException("Can't calculate the average of an empty array");
		}

		int sum = 0;

		for (int i : array) {
			sum += i;
		}

		return (sum + (array.length >> 1)) / array.length;
	}

	public static int longAverage(int[] array) {
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

		return (int)((sum + (array.length >> 1)) / array.length);
	}

	public static int product(int[] array) {
		if (array.length == 1) {
			return array[0];
		} else if (array.length == 2) {
			return array[0] * array[1];
		}

		int product = 1;

		for (int i : array) {
			product *= i;
		}

		return product;
	}

	public static long longProduct(int[] array) {
		if (array.length == 1) {
			return array[0];
		} else if (array.length == 2) {
			return (long)array[0] * array[1];
		}

		long product = 1;

		for (int i : array) {
			product *= i;
		}

		return product;
	}
}
