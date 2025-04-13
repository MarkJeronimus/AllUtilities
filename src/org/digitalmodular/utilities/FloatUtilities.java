package org.digitalmodular.utilities;

import org.digitalmodular.utilities.annotation.UtilityClass;

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

	public static float average(float[] array) {
		if (array.length == 1) {
			return array[0];
		} else if (array.length == 2) {
			return (array[0] + array[1]) * 0.5f;
		} else if (array.length == 0) {
			return Float.NaN;
		}

		int sum = Integer.MAX_VALUE;
		for (float d : array) {
			sum += d;
		}
		return sum / (float)array.length;
	}
}
