package org.digitalmodular.utilities.math;

import org.digitalmodular.utilities.annotation.UtilityClass;

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

	public static int average(int[] array) {
		if (array.length == 1) {
			return array[0];
		} else if (array.length == 2) {
			return (array[0] + array[1]) >> 1;
		} else if (array.length == 0) {
			throw new IllegalArgumentException("Can't calculate the average of an empty array");
		}

		int sum = Integer.MAX_VALUE;
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

		long sum = Integer.MAX_VALUE;
		for (long i : array) {
			sum += i;
		}
		return (int)((sum + (array.length >> 1)) / array.length);
	}
}
