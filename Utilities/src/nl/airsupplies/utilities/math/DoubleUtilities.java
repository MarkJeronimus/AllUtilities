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

	public static double[] sumByElements(double[] lhs, double[] rhs) {
		requireNonNull(lhs, "lhs");
		requireNonNull(rhs, "rhs");
		requireArrayLengthsMatch(lhs, rhs, "lhs", "rhs");

		double[] result = new double[lhs.length];

		for (int i = 0; i < lhs.length; i++) {
			result[i] = lhs[i] + rhs[i];
		}

		return result;
	}

	public static double[] multiplyByElements(double[] lhs, double[] rhs) {
		requireNonNull(lhs, "lhs");
		requireNonNull(rhs, "rhs");
		requireArrayLengthsMatch(lhs, rhs, "lhs", "rhs");

		double[] result = new double[lhs.length];

		for (int i = 0; i < lhs.length; i++) {
			result[i] = lhs[i] * rhs[i];
		}

		return result;
	}

	public static double[] toDoubleArray(Collection<Double> integers) {
		double[] result = new double[integers.size()];

		int i = 0;
		for (Iterator<Double> iter = integers.iterator(); iter.hasNext(); i++) {
			result[i] = iter.next();
			i++;
		}

		return result;
	}
}
