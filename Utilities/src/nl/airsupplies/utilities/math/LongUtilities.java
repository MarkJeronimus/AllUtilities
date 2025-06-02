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

	public static long[] sumByElements(long[] lhs, long[] rhs) {
		requireNonNull(lhs, "lhs");
		requireNonNull(rhs, "rhs");
		requireArrayLengthsMatch(lhs, rhs, "lhs", "rhs");

		long[] result = new long[lhs.length];

		for (int i = 0; i < lhs.length; i++) {
			result[i] = lhs[i] + rhs[i];
		}

		return result;
	}

	public static long[] multiplyByElements(long[] lhs, long[] rhs) {
		requireNonNull(lhs, "lhs");
		requireNonNull(rhs, "rhs");
		requireArrayLengthsMatch(lhs, rhs, "lhs", "rhs");

		long[] result = new long[lhs.length];

		for (int i = 0; i < lhs.length; i++) {
			result[i] = lhs[i] * rhs[i];
		}

		return result;
	}

	public static long[] toLongArray(Collection<Long> integers) {
		long[] result = new long[integers.size()];

		int i = 0;
		for (Iterator<Long> iter = integers.iterator(); iter.hasNext(); i++) {
			result[i] = iter.next();
			i++;
		}

		return result;
	}
}
