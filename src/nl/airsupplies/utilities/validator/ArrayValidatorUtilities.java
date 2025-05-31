package nl.airsupplies.utilities.validator;

import java.lang.reflect.Array;

import org.jetbrains.annotations.Nullable;

import nl.airsupplies.utilities.NumberUtilities;
import nl.airsupplies.utilities.annotation.UtilityClass;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.assertNotDegenerate;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.assertThat;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * @author Mark Jeronimus
 */
// Created 2016-12-21
@SuppressWarnings({"OverloadedMethodsWithSameNumberOfParameters", "OverlyComplexClass"})
@UtilityClass
public final class ArrayValidatorUtilities {
	public static <A> A[] requireArrayNotEmpty(A[] actual, String varName) {
		requireNonNull(actual, varName);

		if (actual.length == 0) {
			throw new IllegalArgumentException('\'' + varName + "' must not me empty");
		}

		return actual;
	}

	public static void requireArrayNotEmpty(Object actual, String varName) {
		requireNonNull(actual, varName);

		if (Array.getLength(actual) == 0) {
			throw new IllegalArgumentException('\'' + varName + "' must not me empty");
		}
	}

	public static <A> A @Nullable [] requireNullOrArrayNotEmpty(A @Nullable [] actual, String varName) {
		if (actual != null && actual.length == 0) {
			throw new IllegalArgumentException('\'' + varName + "' must not me empty");
		}

		return actual;
	}

	public static void requireNullOrArrayNotEmpty(@Nullable Object actual, String varName) {
		if (actual != null && Array.getLength(actual) == 0) {
			throw new IllegalArgumentException('\'' + varName + "' must not me empty");
		}
	}

	public static <A> A[] requireArrayLengthExactly(int length, A[] actual, String varName) {
		assertThat(length >= 0, () -> "'length' is invalid: " + length);
		requireNonNull(actual, varName);

		if (actual.length != length) {
			throw new IllegalArgumentException('\'' + varName + "' must have a length of exactly " +
			                                   length + ": " + actual.length);
		}

		return actual;
	}

	public static void requireArrayLengthExactly(int length, Object actual, String varName) {
		assertThat(length >= 0, () -> "'length' is invalid: " + length);
		requireNonNull(actual, varName);

		if (Array.getLength(actual) != length) {
			throw new IllegalArgumentException('\'' + varName + "' must have a length of exactly " +
			                                   length + ": " + Array.getLength(actual));
		}
	}

	public static <A> A @Nullable [] requireNullOrArrayLengthExactly(
			int length, A @Nullable [] actual, String varName) {
		assertThat(length >= 0, () -> "'length' is invalid: " + length);

		if (actual != null && actual.length != length) {
			throw new IllegalArgumentException('\'' + varName + "' must have a length of exactly " +
			                                   length + ": " + actual.length);
		}

		return actual;
	}

	public static void requireNullOrArrayLengthExactly(int length, @Nullable Object actual, String varName) {
		assertThat(length >= 0, () -> "'length' is invalid: " + length);

		if (actual != null && Array.getLength(actual) != length) {
			throw new IllegalArgumentException('\'' + varName + "' must have a length of exactly " +
			                                   length + ": " + Array.getLength(actual));
		}
	}

	public static <A> A[] requireArrayLengthAtLeast(int min, A[] actual, String varName) {
		assertThat(min >= 0, () -> "'min' is invalid: " + min);
		requireNonNull(actual, varName);

		if (actual.length < min) {
			throw new IllegalArgumentException('\'' + varName + "' must have a length of at least " +
			                                   min + ": " + actual.length);
		}

		return actual;
	}

	public static void requireArrayLengthAtLeast(int min, Object actual, String varName) {
		assertThat(min >= 0, () -> "'min' is invalid: " + min);
		requireNonNull(actual, varName);

		if (!actual.getClass().isArray()) {
			throw new AssertionError('\'' + varName + "' is not an array");
		} else if (Array.getLength(actual) < min) {
			throw new IllegalArgumentException('\'' + varName + "' must have a length of at least " +
			                                   min + ": " + Array.getLength(actual));
		}
	}

	public static <A> A @Nullable [] requireNullOrArrayLengthAtLeast(int min, A @Nullable [] actual, String varName) {
		assertThat(min >= 0, () -> "'min' is invalid: " + min);

		if (actual != null && actual.length < min) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or have a length of at least " +
			                                   min + ": " + actual.length);
		}

		return actual;
	}

	public static void requireNullOrArrayLengthAtLeast(int min, @Nullable Object actual, String varName) {
		assertThat(min >= 0, () -> "'min' is invalid: " + min);

		if (actual == null) {
		} else if (!actual.getClass().isArray()) {
			throw new AssertionError(varName + " is not an array");
		} else if (Array.getLength(actual) < min) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or have a length of at least " +
			                                   min + ": " + Array.getLength(actual));
		}
	}

	public static <A> A[] requireArrayLengthAtMost(int max, A[] actual, String varName) {
		assertThat(max >= 0, () -> "'max' is invalid: " + max);
		requireNonNull(actual, varName);

		if (actual.length > max) {
			throw new IllegalArgumentException('\'' + varName + "' must have a length of at most " +
			                                   max + ": " + actual.length);
		}

		return actual;
	}

	public static void requireArrayLengthAtMost(int max, Object actual, String varName) {
		assertThat(max >= 0, () -> "'max' is invalid: " + max);
		requireNonNull(actual, varName);

		if (!actual.getClass().isArray()) {
			throw new AssertionError('\'' + varName + "' is not an array");
		} else if (Array.getLength(actual) > max) {
			throw new IllegalArgumentException('\'' + varName + "' must have a length of at most " +
			                                   max + ": " + Array.getLength(actual));
		}
	}

	public static <A> A @Nullable [] requireNullOrArrayLengthAtMost(int max, A @Nullable [] actual, String varName) {
		assertThat(max >= 0, () -> "'max' is invalid: " + max);

		if (actual != null && actual.length > max) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or have a length of at most " +
			                                   max + ": " + actual.length);
		}

		return actual;
	}

	public static void requireNullOrArrayLengthAtMost(int max, @Nullable Object actual, String varName) {
		assertThat(max >= 0, () -> "'max' is invalid: " + max);

		if (actual == null) {
		} else if (!actual.getClass().isArray()) {
			throw new AssertionError(varName + " is not an array");
		} else if (Array.getLength(actual) > max) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or have a length of at most " +
			                                   max + ": " + Array.getLength(actual));
		}
	}

	public static <A> A[] requireArrayLengthAbove(int min, A[] actual, String varName) {
		assertThat(min >= 0 && min < Integer.MAX_VALUE, () -> "'min' is invalid: " + min);
		requireNonNull(actual, varName);

		if (actual.length <= min) {
			if (min == 0) {
				throw new IllegalArgumentException('\'' + varName + "' must must not me empty");
			} else {
				throw new IllegalArgumentException('\'' + varName + "' must have a length above " +
				                                   min + ": " + actual.length);
			}
		}

		return actual;
	}

	public static void requireArrayLengthAbove(int min, Object actual, String varName) {
		assertThat(min >= 0 && min < Integer.MAX_VALUE, () -> "'min' is invalid: " + min);
		requireNonNull(actual, varName);

		if (!actual.getClass().isArray()) {
			throw new AssertionError('\'' + varName + "' is not an array");
		} else if (Array.getLength(actual) <= min) {
			if (min == 0) {
				throw new IllegalArgumentException('\'' + varName + "' must must not me empty");
			} else {
				throw new IllegalArgumentException('\'' + varName + "' must have a length above " +
				                                   min + ": " + Array.getLength(actual));
			}
		}
	}

	public static <A> A @Nullable [] requireNullOrArrayLengthAbove(int min, A @Nullable [] actual, String varName) {
		assertThat(min >= 0 && min < Integer.MAX_VALUE, () -> "'min' is invalid: " + min);

		if (actual != null && actual.length <= min) {
			if (min == 0) {
				throw new IllegalArgumentException('\'' + varName + "' must must not me empty");
			} else {
				throw new IllegalArgumentException('\'' + varName + "' must either be null or have a length above " +
				                                   min + ": " + actual.length);
			}
		}

		return actual;
	}

	public static void requireNullOrArrayLengthAbove(int min, @Nullable Object actual, String varName) {
		assertThat(min >= 0 && min < Integer.MAX_VALUE, () -> "'min' is invalid: " + min);

		if (actual == null) {
		} else if (!actual.getClass().isArray()) {
			throw new AssertionError(varName + " is not an array");
		} else if (Array.getLength(actual) <= min) {
			if (min == 0) {
				throw new IllegalArgumentException('\'' + varName + "' must must not me empty");
			} else {
				throw new IllegalArgumentException('\'' + varName + "' must either be null or have a length above " +
				                                   min + ": " + Array.getLength(actual));
			}
		}
	}

	public static <A> A[] requireArrayLengthBelow(int max, A[] actual, String varName) {
		assertThat(max > 0, () -> "'max' is invalid: " + max);
		requireNonNull(actual, varName);

		if (actual.length >= max) {
			throw new IllegalArgumentException('\'' + varName + "' must have a length below " +
			                                   max + ": " + actual.length);
		}

		return actual;
	}

	public static void requireArrayLengthBelow(int max, Object actual, String varName) {
		assertThat(max > 0, () -> "'max' is invalid: " + max);
		requireNonNull(actual, varName);

		if (!actual.getClass().isArray()) {
			throw new AssertionError('\'' + varName + "' is not an array");
		} else if (Array.getLength(actual) >= max) {
			throw new IllegalArgumentException('\'' + varName + "' must have a length below " +
			                                   max + ": " + Array.getLength(actual));
		}
	}

	public static <A> A @Nullable [] requireNullOrArrayLengthBelow(int max, A @Nullable [] actual, String varName) {
		assertThat(max > 0, () -> "'max' is invalid: " + max);
		if (actual != null && actual.length >= max) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or have a length below " +
			                                   max + ": " + actual.length);
		}

		return actual;
	}

	public static void requireNullOrArrayLengthBelow(int max, @Nullable Object actual, String varName) {
		assertThat(max > 0, () -> "'max' is invalid: " + max);
		if (actual == null) {
		} else if (!actual.getClass().isArray()) {
			throw new AssertionError(varName + " is not an array");
		} else if (Array.getLength(actual) >= max) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or have a length below " +
			                                   max + ": " + Array.getLength(actual));
		}
	}

	public static <A> A[] requireArrayLengthBetween(int min, int max, A[] actual, String varName) {
		requireNonNull(actual, varName);

		if (actual.length < min || actual.length > max) {
			throw new IllegalArgumentException('\'' + varName + "' must have a length between [" +
			                                   min + ", " + max + "]: " + actual.length);
		}

		return actual;
	}

	public static void requireArrayLengthBetween(int min, int max, Object actual, String varName) {
		requireNonNull(actual, varName);

		if (!actual.getClass().isArray()) {
			throw new AssertionError('\'' + varName + "' is not an array");
		} else if (Array.getLength(actual) < min || Array.getLength(actual) > max) {
			throw new IllegalArgumentException('\'' + varName + "' must have a length between [" +
			                                   min + ", " + max + "]: " + Array.getLength(actual));
		}
	}

	public static <A> A @Nullable [] requireNullOrArrayLengthBetween(
			int min, int max, A @Nullable [] actual, String varName) {
		if (actual != null && (actual.length < min || actual.length > max)) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or have a length between [" +
			                                   min + ", " + max + "]: " + actual.length);
		}

		return actual;
	}

	public static void requireNullOrArrayLengthBetween(int min, int max, @Nullable Object actual, String varName) {
		if (actual == null) {
		} else if (!actual.getClass().isArray()) {
			throw new AssertionError(varName + " is not an array");
		} else if (Array.getLength(actual) < min || Array.getLength(actual) > max) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or have a length between [" +
			                                   min + ", " + max + "]: " + Array.getLength(actual));
		}
	}

	public static <A> void requireArrayLengthsMatch(A[] array1, A[] array2, String varName1, String varName2) {
		requireNonNull(array1, varName1);
		requireNonNull(array2, varName2);

		if (array1.length != array2.length) {
			throw new IllegalArgumentException('\'' + varName1 + "' and '" + varName2 + "' must have equal lengths: " +
			                                   array1.length + " != " + array2.length);
		}
	}

	public static void requireArrayLengthsMatch(Object array1, Object array2, String varName1, String varName2) {
		requireNonNull(array1, varName1);
		requireNonNull(array2, varName2);

		if (!array1.getClass().isArray()) {
			throw new AssertionError('\'' + varName1 + "' is not an array");
		} else if (!array2.getClass().isArray()) {
			throw new AssertionError('\'' + varName2 + "' is not an array");
		} else if (Array.getLength(array1) != Array.getLength(array2)) {
			throw new IllegalArgumentException('\'' + varName1 + "' and '" + varName2 + "' must have equal lengths: " +
			                                   Array.getLength(array1) + " != " + Array.getLength(array2));
		}
	}

	public static <T> T[] requireArrayValuesNonNull(T[] actual, String varName) {
		requireNonNull(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] == null) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' can't be null");
			}
		}

		return actual;
	}

	public static <T> T @Nullable [] requireNullOrArrayValuesNonNull(T @Nullable [] actual, String varName) {
		if (actual == null) {
			return null;
		}

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] == null) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' can't be null");
			}
		}

		return actual;
	}

	public static float[] requireArrayValuesNotDegenerate(float[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if (NumberUtilities.isDegenerate(actual[i])) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' is degenerate: " + actual[i]);
			}
		}

		return actual;
	}

	public static double[] requireArrayValuesNotDegenerate(double[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if (NumberUtilities.isDegenerate(actual[i])) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' is degenerate: " + actual[i]);
			}
		}

		return actual;
	}

	public static float @Nullable [] requireNullOrArrayValuesNotDegenerate(float @Nullable [] actual, String varName) {
		if (actual == null) {
			return null;
		}

		for (int i = 0; i < actual.length; i++) {
			if (NumberUtilities.isDegenerate(actual[i])) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' is degenerate: " + actual[i]);
			}
		}

		return actual;
	}

	public static double @Nullable [] requireNullOrArrayValuesNotDegenerate(
			double @Nullable [] actual, String varName) {
		if (actual == null) {
			return null;
		}

		for (int i = 0; i < actual.length; i++) {
			if (NumberUtilities.isDegenerate(actual[i])) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' is degenerate: " + actual[i]);
			}
		}

		return actual;
	}

	public static int[] requireArrayValuesNonZero(int[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] == 0) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be non-zero");
			}
		}

		return actual;
	}

	public static long[] requireArrayValuesNonZero(long[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] == 0) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be non-zero");
			}
		}

		return actual;
	}

	public static float[] requireArrayValuesNonZero(float[] actual, String varName) {
		requireNonNull(actual, varName);
		requireArrayValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] == 0) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be non-zero");
			}
		}

		return actual;
	}

	public static double[] requireArrayValuesNonZero(double[] actual, String varName) {
		requireNonNull(actual, varName);
		requireArrayValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] == 0) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be non-zero");
			}
		}

		return actual;
	}

	public static int @Nullable [] requireNullOrArrayValuesNonZero(int @Nullable [] actual, String varName) {
		if (actual == null) {
			return null;
		}

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] == 0) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be non-zero");
			}
		}

		return actual;
	}

	public static long @Nullable [] requireNullOrArrayValuesNonZero(long @Nullable [] actual, String varName) {
		if (actual == null) {
			return null;
		}

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] == 0) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be non-zero");
			}
		}

		return actual;
	}

	public static float @Nullable [] requireNullOrArrayValuesNonZero(float @Nullable [] actual, String varName) {
		if (actual == null) {
			return null;
		}

		requireArrayValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] == 0) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be non-zero");
			}
		}

		return actual;
	}

	public static double @Nullable [] requireNullOrArrayValuesNonZero(double @Nullable [] actual, String varName) {
		if (actual == null) {
			return null;
		}

		requireArrayValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] == 0) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be non-zero");
			}
		}

		return actual;
	}

	public static int[] requireArrayValuesAtLeast(int min, int[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] < min) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be at least " +
				                                   min + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static long[] requireArrayValuesAtLeast(long min, long[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] < min) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be at least " +
				                                   min + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static float[] requireArrayValuesAtLeast(float min, float[] actual, String varName) {
		assertNotDegenerate(min, "min");
		requireNonNull(actual, varName);
		requireArrayValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] < min) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be at least " +
				                                   min + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static double[] requireArrayValuesAtLeast(double min, double[] actual, String varName) {
		assertNotDegenerate(min, "min");
		requireNonNull(actual, varName);
		requireArrayValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] < min) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be at least " +
				                                   min + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static int @Nullable [] requireNullOrArrayValuesAtLeast(int min, int @Nullable [] actual, String varName) {
		if (actual == null) {
			return null;
		}

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] < min) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be at least " +
				                                   min + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static long @Nullable [] requireNullOrArrayValuesAtLeast(
			long min, long @Nullable [] actual, String varName) {
		if (actual == null) {
			return null;
		}

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] < min) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be at least " +
				                                   min + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static float @Nullable [] requireNullOrArrayValuesAtLeast(
			float min, float @Nullable [] actual, String varName) {
		assertNotDegenerate(min, "min");

		if (actual == null) {
			return null;
		}

		requireArrayValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] < min) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be at least " +
				                                   min + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static double @Nullable [] requireNullOrArrayValuesAtLeast(
			double min, double @Nullable [] actual, String varName) {
		assertNotDegenerate(min, "min");

		if (actual == null) {
			return null;
		}

		requireArrayValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] < min) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be at least " +
				                                   min + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static int[] requireArrayValuesAtMost(int max, int[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] > max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be at most " +
				                                   max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static long[] requireArrayValuesAtMost(long max, long[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] > max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be at most " +
				                                   max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static float[] requireArrayValuesAtMost(float max, float[] actual, String varName) {
		assertNotDegenerate(max, "max");
		requireNonNull(actual, varName);
		requireArrayValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] > max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be at most " +
				                                   max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static double[] requireArrayValuesAtMost(double max, double[] actual, String varName) {
		assertNotDegenerate(max, "max");
		requireNonNull(actual, varName);
		requireArrayValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] > max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be at most " +
				                                   max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static int @Nullable [] requireNullOrArrayValuesAtMost(int max, int @Nullable [] actual, String varName) {
		if (actual == null) {
			return null;
		}

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] > max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be at most " +
				                                   max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static long @Nullable [] requireNullOrArrayValuesAtMost(
			long max, long @Nullable [] actual, String varName) {
		if (actual == null) {
			return null;
		}

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] > max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be at most " +
				                                   max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static float @Nullable [] requireNullOrArrayValuesAtMost(
			float max, float @Nullable [] actual, String varName) {
		assertNotDegenerate(max, "max");

		if (actual == null) {
			return null;
		}

		requireArrayValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] > max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be at most " +
				                                   max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static double @Nullable [] requireNullOrArrayValuesAtMost(
			double max, double @Nullable [] actual, String varName) {
		assertNotDegenerate(max, "max");

		if (actual == null) {
			return null;
		}

		requireArrayValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] > max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be at most " +
				                                   max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static int[] requireArrayValuesAbove(int min, int[] actual, String varName) {
		assertThat(min < Integer.MAX_VALUE, () -> "'min' is degenerate: " + min);
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] <= min) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be above " +
				                                   min + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static long[] requireArrayValuesAbove(long min, long[] actual, String varName) {
		assertThat(min < Long.MAX_VALUE, () -> "'min' is degenerate: " + min);
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] <= min) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be above " +
				                                   min + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static float[] requireArrayValuesAbove(float min, float[] actual, String varName) {
		assertNotDegenerate(min, "min");
		requireNonNull(actual, varName);
		requireArrayValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] <= min) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be above " +
				                                   min + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static double[] requireArrayValuesAbove(double min, double[] actual, String varName) {
		assertNotDegenerate(min, "min");
		requireNonNull(actual, varName);
		requireArrayValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] <= min) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be above " +
				                                   min + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static int @Nullable [] requireNullOrArrayValuesAbove(int min, int @Nullable [] actual, String varName) {
		assertThat(min < Integer.MAX_VALUE, () -> "'min' is degenerate: " + min);

		if (actual == null) {
			return null;
		}

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] <= min) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be above " +
				                                   min + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static long @Nullable [] requireNullOrArrayValuesAbove(long min, long @Nullable [] actual, String varName) {
		assertThat(min < Long.MAX_VALUE, () -> "'min' is degenerate: " + min);

		if (actual == null) {
			return null;
		}

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] <= min) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be above " +
				                                   min + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static float @Nullable [] requireNullOrArrayValuesAbove(
			float min, float @Nullable [] actual, String varName) {
		assertNotDegenerate(min, "min");

		if (actual == null) {
			return null;
		}

		requireArrayValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] <= min) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be above " +
				                                   min + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static double @Nullable [] requireNullOrArrayValuesAbove(
			double min, double @Nullable [] actual, String varName) {
		assertNotDegenerate(min, "min");

		if (actual == null) {
			return null;
		}

		requireArrayValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] <= min) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be above " +
				                                   min + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static int[] requireArrayValuesBelow(int max, int[] actual, String varName) {
		assertThat(max > Integer.MIN_VALUE, () -> "'max' is degenerate: " + max);
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] >= max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be below " +
				                                   max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static long[] requireArrayValuesBelow(long max, long[] actual, String varName) {
		assertThat(max > Long.MIN_VALUE, () -> "'max' is degenerate: " + max);
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] >= max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be below " +
				                                   max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static float[] requireArrayValuesBelow(float max, float[] actual, String varName) {
		assertNotDegenerate(max, "max");
		requireNonNull(actual, varName);
		requireArrayValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] >= max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be below " +
				                                   max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static double[] requireArrayValuesBelow(double max, double[] actual, String varName) {
		assertNotDegenerate(max, "max");
		requireNonNull(actual, varName);
		requireArrayValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] >= max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be below " +
				                                   max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static int @Nullable [] requireNullOrArrayValuesBelow(int max, int @Nullable [] actual, String varName) {
		assertThat(max > Integer.MIN_VALUE, () -> "'max' is degenerate: " + max);

		if (actual == null) {
			return null;
		}

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] >= max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must either be null or below " +
				                                   max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static long @Nullable [] requireNullOrArrayValuesBelow(long max, long @Nullable [] actual, String varName) {
		assertThat(max > Long.MIN_VALUE, () -> "'max' is degenerate: " + max);

		if (actual == null) {
			return null;
		}

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] >= max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must either be null or below " +
				                                   max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static float @Nullable [] requireNullOrArrayValuesBelow(
			float max, float @Nullable [] actual, String varName) {
		assertNotDegenerate(max, "max");
		if (actual == null) {
			return null;
		}

		requireArrayValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] >= max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must either be null or below " +
				                                   max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static double @Nullable [] requireNullOrArrayValuesBelow(
			double max, double @Nullable [] actual, String varName) {
		assertNotDegenerate(max, "max");

		if (actual == null) {
			return null;
		}

		requireArrayValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] >= max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must either be null or below " +
				                                   max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static int[] requireArrayValueRange(int min, int max, int[] actual, String varName) {
		assertThat(min <= max, () -> "Range is invalid: [" + min + ", " + max + ']');
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] < min || actual[i] > max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be in the range [" +
				                                   min + ", " + max + "]: " + actual[i]);
			}
		}

		return actual;
	}

	public static long[] requireArrayValueRange(long min, long max, long[] actual, String varName) {
		assertThat(min <= max, () -> "Range is invalid: [" + min + ", " + max + ']');
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] < min || actual[i] > max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be in the range [" +
				                                   min + ", " + max + "]: " + actual[i]);
			}
		}

		return actual;
	}

	public static float[] requireArrayValueRange(float min, float max, float[] actual, String varName) {
		assertNotDegenerate(min, "min");
		assertNotDegenerate(max, "max");
		assertThat(min <= max, () -> "Range is invalid: [" + min + ", " + max + ']');
		requireNonNull(actual, varName);
		requireArrayValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] < min || actual[i] > max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be in the range [" +
				                                   min + ", " + max + "]: " + actual[i]);
			}
		}

		return actual;
	}

	public static double[] requireArrayValueRange(double min, double max, double[] actual, String varName) {
		assertNotDegenerate(min, "min");
		assertNotDegenerate(max, "max");
		assertThat(min <= max, () -> "Range is invalid: [" + min + ", " + max + ']');
		requireNonNull(actual, varName);
		requireArrayValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] < min || actual[i] > max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be in the range [" +
				                                   min + ", " + max + "]: " + actual[i]);
			}
		}

		return actual;
	}

	public static int @Nullable [] requireNullOrArrayValueRange(
			int min, int max, int @Nullable [] actual, String varName) {
		assertThat(min <= max, () -> "Range is invalid: [" + min + ", " + max + ']');

		if (actual == null) {
			return null;
		}

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] < min || actual[i] > max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i +
				                                   "]' must either be null or in the range [" +
				                                   min + ", " + max + "]: " + actual[i]);
			}
		}

		return actual;
	}

	public static long @Nullable [] requireNullOrArrayValueRange(
			long min, long max, long @Nullable [] actual, String varName) {
		assertThat(min <= max, () -> "Range is invalid: [" + min + ", " + max + ']');

		if (actual == null) {
			return null;
		}

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] < min || actual[i] > max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i +
				                                   "]' must either be null or in the range [" +
				                                   min + ", " + max + "]: " + actual[i]);
			}
		}

		return actual;
	}

	public static float @Nullable [] requireNullOrArrayValueRange(
			float min, float max, float @Nullable [] actual, String varName) {
		assertNotDegenerate(min, "min");
		assertNotDegenerate(max, "max");
		assertThat(min <= max, () -> "Range is invalid: [" + min + ", " + max + ']');

		if (actual == null) {
			return null;
		}

		requireArrayValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] < min || actual[i] > max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i +
				                                   "]' must either be null or in the range [" +
				                                   min + ", " + max + "]: " + actual[i]);
			}
		}

		return actual;
	}

	public static double @Nullable [] requireNullOrArrayValueRange(
			double min, double max, double @Nullable [] actual, String varName) {
		assertNotDegenerate(min, "min");
		assertNotDegenerate(max, "max");
		assertThat(min <= max, () -> "Range is invalid: [" + min + ", " + max + ']');

		if (actual == null) {
			return null;
		}

		requireArrayValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] < min || actual[i] > max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i +
				                                   "]' must either be null or in the range [" +
				                                   min + ", " + max + "]: " + actual[i]);
			}
		}

		return actual;
	}

	public static <T> T[] requireValueType(Class<T> type, T[] actual, String varName) {
		requireNonNull(type, "type");
		requireNonNull(actual, varName);

		int i = 0;
		for (Object element : actual) {
			if (!type.isInstance(element)) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be of type " + type.getName() +
				                                   ": " + (element == null ? "null" : element.getClass().getName()));
			}
			i++;
		}

		return actual;
	}

	public static <T> T @Nullable [] requireNullOrValueType(Class<T> type, T @Nullable [] actual, String varName) {
		requireNonNull(type, "type");

		if (actual == null) {
			return null;
		}

		int i = 0;
		for (Object element : actual) {
			if (!type.isInstance(element)) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be of type " + type.getName() +
				                                   ": " + (element == null ? "null" : element.getClass().getName()));
			}
			i++;
		}

		return actual;
	}

	public static int[] requireNotDecreasing(int[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 1; i < actual.length; i++) {
			if (actual[i] < actual[i - 1]) {
				throw new IllegalArgumentException('\'' + varName + "[]' must not be decreasing: [" +
				                                   i + "] ≱ [" + (i - 1) + ']');
			}
		}

		return actual;
	}

	public static long[] requireNotDecreasing(long[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 1; i < actual.length; i++) {
			if (actual[i] < actual[i - 1]) {
				throw new IllegalArgumentException('\'' + varName + "[]' must not be decreasing: [" +
				                                   i + "] ≱ [" + (i - 1) + ']');
			}
		}

		return actual;
	}

	public static float[] requireNotDecreasing(float[] actual, String varName) {
		requireNonNull(actual, varName);
		requireArrayValuesNotDegenerate(actual, "actual");

		for (int i = 1; i < actual.length; i++) {
			if (actual[i] < actual[i - 1]) {
				throw new IllegalArgumentException('\'' + varName + "[]' must not be decreasing: [" +
				                                   i + "] ≱ [" + (i - 1) + ']');
			}
		}

		return actual;
	}

	public static double[] requireNotDecreasing(double[] actual, String varName) {
		requireNonNull(actual, varName);
		requireArrayValuesNotDegenerate(actual, "actual");

		for (int i = 1; i < actual.length; i++) {
			if (actual[i] < actual[i - 1]) {
				throw new IllegalArgumentException('\'' + varName + "[]' must not be decreasing: [" +
				                                   i + "] ≱ [" + (i - 1) + ']');
			}
		}

		return actual;
	}

	public static int[] requireStrictlyIncreasing(int[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 1; i < actual.length; i++) {
			if (actual[i] <= actual[i - 1]) {
				throw new IllegalArgumentException('\'' + varName + "[]' must be strictly increasing: [" +
				                                   i + "] ≯ [" + (i - 1) + ']');
			}
		}

		return actual;
	}

	public static long[] requireStrictlyIncreasing(long[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 1; i < actual.length; i++) {
			if (actual[i] <= actual[i - 1]) {
				throw new IllegalArgumentException('\'' + varName + "[]' must be strictly increasing: [" +
				                                   i + "] ≯ [" + (i - 1) + ']');
			}
		}

		return actual;
	}

	public static float[] requireStrictlyIncreasing(float[] actual, String varName) {
		requireNonNull(actual, varName);
		requireArrayValuesNotDegenerate(actual, "actual");

		for (int i = 1; i < actual.length; i++) {
			if (actual[i] <= actual[i - 1]) {
				throw new IllegalArgumentException('\'' + varName + "[]' must be strictly increasing: [" +
				                                   i + "] ≯ [" + (i - 1) + ']');
			}
		}

		return actual;
	}

	public static double[] requireStrictlyIncreasing(double[] actual, String varName) {
		requireNonNull(actual, varName);
		requireArrayValuesNotDegenerate(actual, "actual");

		for (int i = 1; i < actual.length; i++) {
			if (actual[i] <= actual[i - 1]) {
				throw new IllegalArgumentException('\'' + varName + "[]' must be strictly increasing: [" +
				                                   i + "] ≯ [" + (i - 1) + ']');
			}
		}

		return actual;
	}
}
