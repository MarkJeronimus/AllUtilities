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
public final class FloatUtilities {
	public static float min(float[] array) {
		if (array.length == 1) {
			return array[0];
		} else if (array.length == 2) {
			return Math.min(array[0], array[1]);
		} else if (array.length == 0) {
			throw new IllegalArgumentException("Can't calculate the min of an empty array");
		}

		float min = Integer.MAX_VALUE;

		for (float i : array) {
			min = Math.min(min, i);
		}

		return min;
	}

	public static float max(float[] array) {
		if (array.length == 1) {
			return array[0];
		} else if (array.length == 2) {
			return Math.max(array[0], array[1]);
		} else if (array.length == 0) {
			throw new IllegalArgumentException("Can't calculate the max of an empty array");
		}

		float max = Integer.MIN_VALUE;

		for (float i : array) {
			max = Math.max(max, i);
		}

		return max;
	}

	public static float sum(float[] array) {
		if (array.length == 1) {
			return array[0];
		} else if (array.length == 2) {
			return array[0] + array[1];
		}

		float sum = 0.0f;

		for (float d : array) {
			sum += d;
		}

		return sum;
	}

	public static double doubleSum(float[] array) {
		if (array.length == 1) {
			return array[0];
		} else if (array.length == 2) {
			return array[0] + array[1];
		}

		double sum = 0.0;

		for (float d : array) {
			sum += d;
		}

		return sum;
	}

	public static float average(float[] array) {
		if (array.length == 1) {
			return array[0];
		} else if (array.length == 2) {
			return (array[0] + array[1]) * 0.5f;
		} else if (array.length == 0) {
			return Float.NaN;
		}

		float sum = 0.0f;

		for (float d : array) {
			sum += d;
		}

		return sum / (float)array.length;
	}

	public static double doubleAverage(float[] array) {
		if (array.length == 1) {
			return array[0];
		} else if (array.length == 2) {
			return (array[0] + array[1]) * 0.5f;
		} else if (array.length == 0) {
			return Float.NaN;
		}

		double sum = 0.0;

		for (float d : array) {
			sum += d;
		}

		return sum / array.length;
	}

	public static float product(float[] array) {
		if (array.length == 1) {
			return array[0];
		} else if (array.length == 2) {
			return array[0] * array[1];
		}

		float product = 1.0f;

		for (float d : array) {
			product *= d;
		}

		return product;
	}

	public static double doubleProduct(float[] array) {
		if (array.length == 1) {
			return array[0];
		} else if (array.length == 2) {
			return array[0] * array[1];
		}

		double product = 1.0;

		for (float d : array) {
			product *= d;
		}

		return product;
	}

	public static float[] sumByElements(float[] lhs, float[] rhs) {
		requireNonNull(lhs, "lhs");
		requireNonNull(rhs, "rhs");
		requireArrayLengthsMatch(lhs, rhs, "lhs", "rhs");

		float[] result = new float[lhs.length];

		for (int i = 0; i < lhs.length; i++) {
			result[i] = lhs[i] + rhs[i];
		}

		return result;
	}

	public static double[] doubleSumByElements(float[] lhs, float[] rhs) {
		requireNonNull(lhs, "lhs");
		requireNonNull(rhs, "rhs");
		requireArrayLengthsMatch(lhs, rhs, "lhs", "rhs");

		double[] result = new double[lhs.length];

		for (int i = 0; i < lhs.length; i++) {
			result[i] = (double)lhs[i] + rhs[i];
		}

		return result;
	}

	public static float[] multiplyByElements(float[] lhs, float[] rhs) {
		requireNonNull(lhs, "lhs");
		requireNonNull(rhs, "rhs");
		requireArrayLengthsMatch(lhs, rhs, "lhs", "rhs");

		float[] result = new float[lhs.length];

		for (int i = 0; i < lhs.length; i++) {
			result[i] = lhs[i] * rhs[i];
		}

		return result;
	}

	public static double[] doubleMultiplyByElements(float[] lhs, float[] rhs) {
		requireNonNull(lhs, "lhs");
		requireNonNull(rhs, "rhs");
		requireArrayLengthsMatch(lhs, rhs, "lhs", "rhs");

		double[] result = new double[lhs.length];

		for (int i = 0; i < lhs.length; i++) {
			result[i] = (double)lhs[i] * rhs[i];
		}

		return result;
	}

	public static float[] toFloatArray(Collection<Float> integers) {
		float[] result = new float[integers.size()];

		int i = 0;
		for (Iterator<Float> iter = integers.iterator(); iter.hasNext(); i++) {
			result[i] = iter.next();
			i++;
		}

		return result;
	}
}
