package nl.airsupplies.utilities.math;

import java.util.Collection;
import java.util.Iterator;

import nl.airsupplies.utilities.annotation.UtilityClass;
import static nl.airsupplies.utilities.validator.ArrayValidatorUtilities.requireArrayLengthsMatch;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

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

	public static int[] sumByElements(int[] lhs, int[] rhs) {
		requireNonNull(lhs, "lhs");
		requireNonNull(rhs, "rhs");
		requireArrayLengthsMatch(lhs, rhs, "lhs", "rhs");

		int[] result = new int[lhs.length];

		for (int i = 0; i < lhs.length; i++) {
			result[i] = lhs[i] + rhs[i];
		}

		return result;
	}

	public static long[] longSumByElements(int[] lhs, int[] rhs) {
		requireNonNull(lhs, "lhs");
		requireNonNull(rhs, "rhs");
		requireArrayLengthsMatch(lhs, rhs, "lhs", "rhs");

		long[] result = new long[lhs.length];

		for (int i = 0; i < lhs.length; i++) {
			result[i] = (long)lhs[i] + rhs[i];
		}

		return result;
	}

	public static int[] multiplyByElements(int[] lhs, int[] rhs) {
		requireNonNull(lhs, "lhs");
		requireNonNull(rhs, "rhs");
		requireArrayLengthsMatch(lhs, rhs, "lhs", "rhs");

		int[] result = new int[lhs.length];

		for (int i = 0; i < lhs.length; i++) {
			result[i] = lhs[i] * rhs[i];
		}

		return result;
	}

	public static long[] longMultiplyByElements(int[] lhs, int[] rhs) {
		requireNonNull(lhs, "lhs");
		requireNonNull(rhs, "rhs");
		requireArrayLengthsMatch(lhs, rhs, "lhs", "rhs");

		long[] result = new long[lhs.length];

		for (int i = 0; i < lhs.length; i++) {
			result[i] = (long)lhs[i] * rhs[i];
		}

		return result;
	}

	public static int[] toIntArray(Collection<Integer> integers) {
		int[] result = new int[integers.size()];

		int i = 0;
		for (Iterator<Integer> iter = integers.iterator(); iter.hasNext(); i++) {
			result[i] = iter.next();
			i++;
		}

		return result;
	}
}
