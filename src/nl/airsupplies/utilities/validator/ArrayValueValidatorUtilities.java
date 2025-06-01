package nl.airsupplies.utilities.validator;

import org.jetbrains.annotations.Nullable;

import nl.airsupplies.utilities.NumberUtilities;
import nl.airsupplies.utilities.annotation.UtilityClass;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.assertNonNull;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.assertNotDegenerate;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.assertThat;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNotDegenerate;

/**
 * @author Mark Jeronimus
 */
// Created 2016-12-21 Split from ArrayValidatorUtilities
@SuppressWarnings({"OverloadedMethodsWithSameNumberOfParameters", "OverlyComplexClass", "UnusedReturnValue"})
@UtilityClass
public final class ArrayValueValidatorUtilities {
	public static <T> T[] requireValuesNonNull(T[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] == null) {
				throw new NullPointerException('\'' + varName + '[' + i + "]' can't be null");
			}
		}

		return actual;
	}

	public static <T> T @Nullable [] requireNullOrValuesNonNull(T @Nullable [] actual, String varName) {
		if (actual == null) {
			return null;
		}

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] == null) {
				throw new NullPointerException('\'' + varName + '[' + i + "]' can't be null");
			}
		}

		return actual;
	}

	public static <T> T[] requireValuesOfType(Class<T> type, T[] actual, String varName) {
		assertNonNull(type, "type");
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if (actual[i].getClass() != type) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be of type " + type.getName());
			}
		}

		return actual;
	}

	public static <T> T @Nullable [] requireNullOrValuesOfType(Class<T> type, T @Nullable [] actual, String varName) {
		assertNonNull(type, "type");

		if (actual == null) {
			return null;
		}

		for (int i = 0; i < actual.length; i++) {
			if (actual[i].getClass() != type) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be of type " + type.getName());
			}
		}

		return actual;
	}

	public static <T> T[] requireValuesInstanceOf(Class<T> type, T[] actual, String varName) {
		assertNonNull(type, "type");
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if (!type.isInstance(actual[i])) {
				throw new IllegalArgumentException(
						'\'' + varName + '[' + i + "]' must be an instance of " + type.getName());
			}
		}

		return actual;
	}

	public static <T> T @Nullable [] requireNullOrValuesInstanceOf(
			Class<T> type, T @Nullable [] actual, String varName) {
		assertNonNull(type, "type");

		if (actual == null) {
			return null;
		}

		for (int i = 0; i < actual.length; i++) {
			if (!type.isInstance(actual[i])) {
				throw new IllegalArgumentException(
						'\'' + varName + '[' + i + "]' must be an instance of " + type.getName());
			}
		}

		return actual;
	}

	public static float[] requireValuesNotDegenerate(float[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if (NumberUtilities.isDegenerate(actual[i])) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' is degenerate: " + actual[i]);
			}
		}

		return actual;
	}

	public static double[] requireValuesNotDegenerate(double[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if (NumberUtilities.isDegenerate(actual[i])) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' is degenerate: " + actual[i]);
			}
		}

		return actual;
	}

	public static float @Nullable [] requireNullOrValuesNotDegenerate(float @Nullable [] actual, String varName) {
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

	public static double @Nullable [] requireNullOrValuesNotDegenerate(
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

	public static byte[] requireValuesNonZero(byte[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] == 0) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be non-zero");
			}
		}

		return actual;
	}

	public static int[] requireValuesNonZero(int[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] == 0) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be non-zero");
			}
		}

		return actual;
	}

	public static long[] requireValuesNonZero(long[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] == 0) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be non-zero");
			}
		}

		return actual;
	}

	public static float[] requireValuesNonZero(float[] actual, String varName) {
		requireNonNull(actual, varName);
		requireValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] == 0) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be non-zero");
			}
		}

		return actual;
	}

	public static double[] requireValuesNonZero(double[] actual, String varName) {
		requireNonNull(actual, varName);
		requireValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] == 0) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be non-zero");
			}
		}

		return actual;
	}

	public static byte @Nullable [] requireNullOrValuesNonZero(byte @Nullable [] actual, String varName) {
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

	public static int @Nullable [] requireNullOrValuesNonZero(int @Nullable [] actual, String varName) {
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

	public static long @Nullable [] requireNullOrValuesNonZero(long @Nullable [] actual, String varName) {
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

	public static float @Nullable [] requireNullOrValuesNonZero(float @Nullable [] actual, String varName) {
		if (actual == null) {
			return null;
		}

		requireValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] == 0.0f) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be non-zero");
			}
		}

		return actual;
	}

	public static double @Nullable [] requireNullOrValuesNonZero(double @Nullable [] actual, String varName) {
		if (actual == null) {
			return null;
		}

		requireValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] == 0.0) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be non-zero");
			}
		}

		return actual;
	}

	public static byte[] requireValuesAtLeast(byte min, byte[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] < min) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be at least " +
				                                   min + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static byte[] requireValuesAtLeastUnsigned(int min, byte[] actual, String varName) {
		assertThat(min >= 0 && min <= 255, () -> "'min' is invalid: " + min);
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if ((actual[i] & 0xFF) < min) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be at least " +
				                                   min + ": " + (actual[i] & 0xFF));
			}
		}

		return actual;
	}

	public static int[] requireValuesAtLeast(int min, int[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] < min) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be at least " +
				                                   min + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static long[] requireValuesAtLeast(long min, long[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] < min) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be at least " +
				                                   min + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static float[] requireValuesAtLeast(float min, float[] actual, String varName) {
		assertNotDegenerate(min, "min");
		requireNonNull(actual, varName);
		requireValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] < min) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be at least " +
				                                   min + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static double[] requireValuesAtLeast(double min, double[] actual, String varName) {
		assertNotDegenerate(min, "min");
		requireNonNull(actual, varName);
		requireValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] < min) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be at least " +
				                                   min + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static byte @Nullable [] requireNullOrValuesAtLeast(
			byte min, byte @Nullable [] actual, String varName) {
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

	public static byte @Nullable [] requireNullOrValuesAtLeastUnsigned(
			int min, byte @Nullable [] actual, String varName) {
		assertThat(min >= 0 && min <= 255, () -> "'min' is invalid: " + min);

		if (actual == null) {
			return null;
		}

		for (int i = 0; i < actual.length; i++) {
			if ((actual[i] & 0xFF) < min) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be at least " +
				                                   min + ": " + (actual[i] & 0xFF));
			}
		}

		return actual;
	}

	public static int @Nullable [] requireNullOrValuesAtLeast(int min, int @Nullable [] actual, String varName) {
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

	public static long @Nullable [] requireNullOrValuesAtLeast(
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

	public static float @Nullable [] requireNullOrValuesAtLeast(
			float min, float @Nullable [] actual, String varName) {
		assertNotDegenerate(min, "min");

		if (actual == null) {
			return null;
		}

		requireValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] < min) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be at least " +
				                                   min + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static double @Nullable [] requireNullOrValuesAtLeast(
			double min, double @Nullable [] actual, String varName) {
		assertNotDegenerate(min, "min");

		if (actual == null) {
			return null;
		}

		requireValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] < min) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be at least " +
				                                   min + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static byte[] requireValuesAtMost(byte max, byte[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] > max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be at most " +
				                                   max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static byte[] requireValuesAtMostUnsigned(int max, byte[] actual, String varName) {
		assertThat(max >= 0 && max <= 255, () -> "'max' is invalid: " + max);
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if ((actual[i] & 0xFF) > max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be at most " +
				                                   max + ": " + (actual[i] & 0xFF));
			}
		}

		return actual;
	}

	public static int[] requireValuesAtMost(int max, int[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] > max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be at most " +
				                                   max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static long[] requireValuesAtMost(long max, long[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] > max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be at most " +
				                                   max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static float[] requireValuesAtMost(float max, float[] actual, String varName) {
		assertNotDegenerate(max, "max");
		requireNonNull(actual, varName);
		requireValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] > max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be at most " +
				                                   max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static double[] requireValuesAtMost(double max, double[] actual, String varName) {
		assertNotDegenerate(max, "max");
		requireNonNull(actual, varName);
		requireValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] > max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be at most " +
				                                   max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static byte @Nullable [] requireNullOrValuesAtMost(byte max, byte @Nullable [] actual, String varName) {
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

	public static byte @Nullable [] requireNullOrValuesAtMostUnsigned(
			byte max, byte @Nullable [] actual, String varName) {
		assertThat(max >= 0 && max <= 255, () -> "'max' is invalid: " + max);

		if (actual == null) {
			return null;
		}

		for (int i = 0; i < actual.length; i++) {
			if ((actual[i] & 0xFF) > max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be at most " +
				                                   max + ": " + (actual[i] & 0xFF));
			}
		}

		return actual;
	}

	public static int @Nullable [] requireNullOrValuesAtMost(int max, int @Nullable [] actual, String varName) {
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

	public static long @Nullable [] requireNullOrValuesAtMost(
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

	public static float @Nullable [] requireNullOrValuesAtMost(
			float max, float @Nullable [] actual, String varName) {
		assertNotDegenerate(max, "max");

		if (actual == null) {
			return null;
		}

		requireValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] > max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be at most " +
				                                   max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static double @Nullable [] requireNullOrValuesAtMost(
			double max, double @Nullable [] actual, String varName) {
		assertNotDegenerate(max, "max");

		if (actual == null) {
			return null;
		}

		requireValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] > max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be at most " +
				                                   max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static byte[] requireValuesAbove(byte min, byte[] actual, String varName) {
		assertThat(min < Byte.MAX_VALUE, () -> "'min' must not be Byte.MAX_VALUE");
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] <= min) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be above " +
				                                   min + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static byte[] requireValuesAboveUnsigned(int min, byte[] actual, String varName) {
		assertThat(min >= 0 && min <= 254, () -> "'min' is invalid: " + min);
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if ((actual[i] & 0xFF) <= min) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be above " +
				                                   min + ": " + (actual[i] & 0xFF));
			}
		}

		return actual;
	}

	public static int[] requireValuesAbove(int min, int[] actual, String varName) {
		assertThat(min < Integer.MAX_VALUE, () -> "'min' must not be Integer.MAX_VALUE");
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] <= min) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be above " +
				                                   min + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static long[] requireValuesAbove(long min, long[] actual, String varName) {
		assertThat(min < Long.MAX_VALUE, () -> "'min' must not be Long.MAX_VALUE");
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] <= min) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be above " +
				                                   min + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static float[] requireValuesAbove(float min, float[] actual, String varName) {
		assertNotDegenerate(min, "min");
		requireNonNull(actual, varName);
		requireValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] <= min) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be above " +
				                                   min + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static double[] requireValuesAbove(double min, double[] actual, String varName) {
		assertNotDegenerate(min, "min");
		requireNonNull(actual, varName);
		requireValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] <= min) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be above " +
				                                   min + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static byte @Nullable [] requireNullOrValuesAbove(byte min, byte @Nullable [] actual, String varName) {
		assertThat(min < Byte.MAX_VALUE, () -> "'min' must not be Byte.MAX_VALUE");

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

	public static byte @Nullable [] requireNullOrValuesAboveUnsigned(
			int min, byte @Nullable [] actual, String varName) {
		assertThat(min >= 0 && min <= 254, () -> "'min' is invalid: " + min);

		if (actual == null) {
			return null;
		}

		for (int i = 0; i < actual.length; i++) {
			if ((actual[i] & 0xFF) <= min) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be above " +
				                                   min + ": " + (actual[i] & 0xFF));
			}
		}

		return actual;
	}

	public static int @Nullable [] requireNullOrValuesAbove(int min, int @Nullable [] actual, String varName) {
		assertThat(min < Integer.MAX_VALUE, () -> "'min' must not be Integer.MAX_VALUE");

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

	public static long @Nullable [] requireNullOrValuesAbove(long min, long @Nullable [] actual, String varName) {
		assertThat(min < Long.MAX_VALUE, () -> "'min' must not be Long.MAX_VALUE");

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

	public static float @Nullable [] requireNullOrValuesAbove(
			float min, float @Nullable [] actual, String varName) {
		assertNotDegenerate(min, "min");

		if (actual == null) {
			return null;
		}

		requireValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] <= min) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be above " +
				                                   min + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static double @Nullable [] requireNullOrValuesAbove(
			double min, double @Nullable [] actual, String varName) {
		assertNotDegenerate(min, "min");

		if (actual == null) {
			return null;
		}

		requireValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] <= min) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be above " +
				                                   min + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static byte[] requireValuesBelow(byte max, byte[] actual, String varName) {
		assertThat(max > Byte.MIN_VALUE, () -> "'max' must not be Byte.MIN_VALUE");
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] >= max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be below " +
				                                   max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static byte[] requireValuesBelowUnsigned(int max, byte[] actual, String varName) {
		assertThat(max >= 1 && max <= 255, () -> "'max' is invalid: " + max);
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if ((actual[i] & 0xFF) >= max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be below " +
				                                   max + ": " + (actual[i] & 0xFF));
			}
		}

		return actual;
	}

	public static int[] requireValuesBelow(int max, int[] actual, String varName) {
		assertThat(max > Integer.MIN_VALUE, () -> "'max' must not be Integer.MIN_VALUE");
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] >= max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be below " +
				                                   max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static long[] requireValuesBelow(long max, long[] actual, String varName) {
		assertThat(max > Long.MIN_VALUE, () -> "'max' must not be Long.MIN_VALUE");
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] >= max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be below " +
				                                   max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static float[] requireValuesBelow(float max, float[] actual, String varName) {
		assertNotDegenerate(max, "max");
		requireNonNull(actual, varName);
		requireValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] >= max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be below " +
				                                   max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static double[] requireValuesBelow(double max, double[] actual, String varName) {
		assertNotDegenerate(max, "max");
		requireNonNull(actual, varName);
		requireValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] >= max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be below " +
				                                   max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static byte @Nullable [] requireNullOrValuesBelow(byte max, byte @Nullable [] actual, String varName) {
		assertThat(max > Byte.MIN_VALUE, () -> "'max' must not be Byte.MIN_VALUE");

		if (actual == null) {
			return null;
		}

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] >= max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be null or below " +
				                                   max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static byte @Nullable [] requireNullOrValuesBelowUnsigned(
			byte max, byte @Nullable [] actual, String varName) {
		assertThat(max >= 1 && max <= 255, () -> "'max' is invalid: " + max);

		if (actual == null) {
			return null;
		}

		for (int i = 0; i < actual.length; i++) {
			if ((actual[i] & 0xFF) >= max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be null or below " +
				                                   max + ": " + (actual[i] & 0xFF));
			}
		}

		return actual;
	}

	public static int @Nullable [] requireNullOrValuesBelow(int max, int @Nullable [] actual, String varName) {
		assertThat(max > Integer.MIN_VALUE, () -> "'max' must not be Integer.MIN_VALUE");

		if (actual == null) {
			return null;
		}

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] >= max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be null or below " +
				                                   max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static long @Nullable [] requireNullOrValuesBelow(long max, long @Nullable [] actual, String varName) {
		assertThat(max > Long.MIN_VALUE, () -> "'max' must not be Long.MIN_VALUE");

		if (actual == null) {
			return null;
		}

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] >= max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be null or below " +
				                                   max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static float @Nullable [] requireNullOrValuesBelow(
			float max, float @Nullable [] actual, String varName) {
		assertNotDegenerate(max, "max");
		if (actual == null) {
			return null;
		}

		requireValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] >= max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be null or below " +
				                                   max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static double @Nullable [] requireNullOrValuesBelow(
			double max, double @Nullable [] actual, String varName) {
		assertNotDegenerate(max, "max");

		if (actual == null) {
			return null;
		}

		requireValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] >= max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be null or below " +
				                                   max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static byte[] requireValuesBetween(byte min, byte max, byte[] actual, String varName) {
		assertThat(min <= max, () -> "Range is invalid: " + min + "..." + max);
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] < min || actual[i] > max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be between " +
				                                   min + " and " + max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static byte[] requireValueBetweenUnsigned(int min, int max, byte[] actual, String varName) {
		assertThat(min >= 0 && min <= 255, () -> "'min' is invalid: " + min);
		assertThat(max >= 0 && max <= 255, () -> "'max' is invalid: " + max);
		assertThat(min <= max, () -> "Range is invalid: " + min + "..." + max);
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if ((actual[i] & 0xFF) < min || (actual[i] & 0xFF) > max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be between " +
				                                   min + " and " + max + ": " + (actual[i] & 0xFF));
			}
		}

		return actual;
	}

	public static int[] requireValuesBetween(int min, int max, int[] actual, String varName) {
		assertThat(min <= max, () -> "Range is invalid: " + min + "..." + max);
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] < min || actual[i] > max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be between " +
				                                   min + " and " + max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static long[] requireValuesBetween(long min, long max, long[] actual, String varName) {
		assertThat(min <= max, () -> "Range is invalid: " + min + "..." + max);
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] < min || actual[i] > max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be between " +
				                                   min + " and " + max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static float[] requireValuesBetween(float min, float max, float[] actual, String varName) {
		assertNotDegenerate(min, "min");
		assertNotDegenerate(max, "max");
		assertThat(min <= max, () -> "Range is invalid: " + min + "..." + max);
		requireNonNull(actual, varName);
		requireValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] < min || actual[i] > max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be between " +
				                                   min + " and " + max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static double[] requireValuesBetween(double min, double max, double[] actual, String varName) {
		assertNotDegenerate(min, "min");
		assertNotDegenerate(max, "max");
		assertThat(min <= max, () -> "Range is invalid: " + min + "..." + max);
		requireNonNull(actual, varName);
		requireValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] < min || actual[i] > max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be between " +
				                                   min + " and " + max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static byte @Nullable [] requireNullOrValuesBetween(
			byte min, byte max, byte @Nullable [] actual, String varName) {
		assertThat(min <= max, () -> "Range is invalid: " + min + "..." + max);

		if (actual == null) {
			return null;
		}

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] < min || actual[i] > max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be null or between " +
				                                   min + " and " + max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static byte @Nullable [] requireNullOrValueBetweenUnsigned(
			int min, int max, byte @Nullable [] actual, String varName) {
		assertThat(min >= 0 && min <= 255, () -> "'min' is invalid: " + min);
		assertThat(max >= 0 && max <= 255, () -> "'max' is invalid: " + max);
		assertThat(min <= max, () -> "Range is invalid: " + min + "..." + max);

		if (actual == null) {
			return null;
		}

		for (int i = 0; i < actual.length; i++) {
			if ((actual[i] & 0xFF) < min || (actual[i] & 0xFF) > max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be null or between " +
				                                   min + " and " + max + ": " + (actual[i] & 0xFF));
			}
		}

		return actual;
	}

	public static int @Nullable [] requireNullOrValuesBetween(
			int min, int max, int @Nullable [] actual, String varName) {
		assertThat(min <= max, () -> "Range is invalid: " + min + "..." + max);

		if (actual == null) {
			return null;
		}

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] < min || actual[i] > max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be null or between " +
				                                   min + " and " + max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static long @Nullable [] requireNullOrValuesBetween(
			long min, long max, long @Nullable [] actual, String varName) {
		assertThat(min <= max, () -> "Range is invalid: " + min + "..." + max);

		if (actual == null) {
			return null;
		}

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] < min || actual[i] > max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be null or between " +
				                                   min + " and " + max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static float @Nullable [] requireNullOrValuesBetween(
			float min, float max, float @Nullable [] actual, String varName) {
		assertNotDegenerate(min, "min");
		assertNotDegenerate(max, "max");
		assertThat(min <= max, () -> "Range is invalid: " + min + "..." + max);

		if (actual == null) {
			return null;
		}

		requireValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] < min || actual[i] > max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be null or between " +
				                                   min + " and " + max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static double @Nullable [] requireNullOrValuesBetween(
			double min, double max, double @Nullable [] actual, String varName) {
		assertNotDegenerate(min, "min");
		assertNotDegenerate(max, "max");
		assertThat(min <= max, () -> "Range is invalid: " + min + "..." + max);

		if (actual == null) {
			return null;
		}

		requireValuesNotDegenerate(actual, "actual");

		for (int i = 0; i < actual.length; i++) {
			if (actual[i] < min || actual[i] > max) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be null or between " +
				                                   min + " and " + max + ": " + actual[i]);
			}
		}

		return actual;
	}

	public static byte[] requireIncreasing(byte[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 1; i < actual.length; i++) {
			if (actual[i - 1] > actual[i]) {
				throw new IllegalArgumentException("Values in '" + varName + "' must not be decreasing: " +
				                                   actual[i - 1] + " ≱ " + actual[i] + " (at index " + (i - 1) + ')');
			}
		}

		return actual;
	}

	public static byte[] requireIncreasingUnsigned(byte[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 1; i < actual.length; i++) {
			if ((actual[i - 1] & 0xFF) > (actual[i] & 0xFF)) {
				throw new IllegalArgumentException(
						"Unsigned values in '" + varName + "' must not be decreasing: " +
						(actual[i - 1] & 0xFF) + " ≱ " + (actual[i] & 0xFF) + " (at index " + (i - 1) + ')');
			}
		}

		return actual;
	}

	public static int[] requireIncreasing(int[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 1; i < actual.length; i++) {
			if (actual[i - 1] > actual[i]) {
				throw new IllegalArgumentException("Values in '" + varName + "' must not be decreasing: " +
				                                   actual[i - 1] + " ≱ " + actual[i] + " (at index " + (i - 1) + ')');
			}
		}

		return actual;
	}

	public static long[] requireIncreasing(long[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 1; i < actual.length; i++) {
			if (actual[i - 1] > actual[i]) {
				throw new IllegalArgumentException("Values in '" + varName + "' must not be decreasing: " +
				                                   actual[i - 1] + " ≱ " + actual[i] + " (at index " + (i - 1) + ')');
			}
		}

		return actual;
	}

	public static float[] requireIncreasing(float[] actual, String varName) {
		requireValuesNotDegenerate(actual, "actual");
		requireNonNull(actual, varName);

		for (int i = 1; i < actual.length; i++) {
			if (actual[i - 1] > actual[i]) {
				throw new IllegalArgumentException("Values in '" + varName + "' must not be decreasing: " +
				                                   actual[i - 1] + " ≱ " + actual[i] + " (at index " + (i - 1) + ')');
			}
		}

		return actual;
	}

	public static double[] requireIncreasing(double[] actual, String varName) {
		requireValuesNotDegenerate(actual, "actual");
		requireNonNull(actual, varName);

		for (int i = 1; i < actual.length; i++) {
			if (actual[i - 1] > actual[i]) {
				throw new IllegalArgumentException("Values in '" + varName + "' must not be decreasing: " +
				                                   actual[i - 1] + " ≱ " + actual[i] + " (at index " + (i - 1) + ')');
			}
		}

		return actual;
	}

	public static byte[] requireStrictlyIncreasing(byte[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 1; i < actual.length; i++) {
			if (actual[i - 1] >= actual[i]) {
				throw new IllegalArgumentException("Values in '" + varName + "' must be strictly increasing: " +
				                                   actual[i - 1] + " ≯ " + actual[i] + " (at index " + (i - 1) + ')');
			}
		}

		return actual;
	}

	public static byte[] requireStrictlyIncreasingUnsigned(byte[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 1; i < actual.length; i++) {
			if ((actual[i - 1] & 0xFF) >= (actual[i] & 0xFF)) {
				throw new IllegalArgumentException(
						"Unsigned values in '" + varName + "' must be strictly increasing: " +
						(actual[i - 1] & 0xFF) + " ≯ " + (actual[i] & 0xFF) + " (at index " + (i - 1) + ')');
			}
		}

		return actual;
	}

	public static int[] requireStrictlyIncreasing(int[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 1; i < actual.length; i++) {
			if (actual[i - 1] >= actual[i]) {
				throw new IllegalArgumentException("Values in '" + varName + "' must be strictly increasing: " +
				                                   actual[i - 1] + " ≯ " + actual[i] + " (at index " + (i - 1) + ')');
			}
		}

		return actual;
	}

	public static long[] requireStrictlyIncreasing(long[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 1; i < actual.length; i++) {
			if (actual[i - 1] >= actual[i]) {
				throw new IllegalArgumentException("Values in '" + varName + "' must be strictly increasing: " +
				                                   actual[i - 1] + " ≯ " + actual[i] + " (at index " + (i - 1) + ')');
			}
		}

		return actual;
	}

	public static float[] requireStrictlyIncreasing(float[] actual, String varName) {
		requireValuesNotDegenerate(actual, "actual");
		requireNonNull(actual, varName);

		for (int i = 1; i < actual.length; i++) {
			if (actual[i - 1] >= actual[i]) {
				throw new IllegalArgumentException("Values in '" + varName + "' must be strictly increasing: " +
				                                   actual[i - 1] + " ≯ " + actual[i] + " (at index " + (i - 1) + ')');
			}
		}

		return actual;
	}

	public static double[] requireStrictlyIncreasing(double[] actual, String varName) {
		requireValuesNotDegenerate(actual, "actual");
		requireNonNull(actual, varName);

		for (int i = 1; i < actual.length; i++) {
			if (actual[i - 1] >= actual[i]) {
				throw new IllegalArgumentException("Values in '" + varName + "' must be strictly increasing: " +
				                                   actual[i - 1] + " ≯ " + actual[i] + " (at index " + (i - 1) + ')');
			}
		}

		return actual;
	}

	public static byte[] requireDecreasing(byte[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 1; i < actual.length; i++) {
			if (actual[i - 1] < actual[i]) {
				throw new IllegalArgumentException("Values in '" + varName + "' must not be increasing: " +
				                                   actual[i - 1] + " ≰ " + actual[i] + " (at index " + (i - 1) + ')');
			}
		}

		return actual;
	}

	public static byte[] requireDecreasingUnsigned(byte[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 1; i < actual.length; i++) {
			if ((actual[i - 1] & 0xFF) < (actual[i] & 0xFF)) {
				throw new IllegalArgumentException(
						"Unsigned values in '" + varName + "' must not be increasing: " +
						(actual[i - 1] & 0xFF) + " ≰ " + (actual[i] & 0xFF) + " (at index " + (i - 1) + ')');
			}
		}

		return actual;
	}

	public static int[] requireDecreasing(int[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 1; i < actual.length; i++) {
			if (actual[i - 1] < actual[i]) {
				throw new IllegalArgumentException("Values in '" + varName + "' must not be increasing: " +
				                                   actual[i - 1] + " ≰ " + actual[i] + " (at index " + (i - 1) + ')');
			}
		}

		return actual;
	}

	public static long[] requireDecreasing(long[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 1; i < actual.length; i++) {
			if (actual[i - 1] < actual[i]) {
				throw new IllegalArgumentException("Values in '" + varName + "' must not be increasing: " +
				                                   actual[i - 1] + " ≰ " + actual[i] + " (at index " + (i - 1) + ')');
			}
		}

		return actual;
	}

	public static float[] requireDecreasing(float[] actual, String varName) {
		requireValuesNotDegenerate(actual, "actual");
		requireNonNull(actual, varName);

		for (int i = 1; i < actual.length; i++) {
			if (actual[i - 1] < actual[i]) {
				throw new IllegalArgumentException("Values in '" + varName + "' must not be increasing: " +
				                                   actual[i - 1] + " ≰ " + actual[i] + " (at index " + (i - 1) + ')');
			}
		}

		return actual;
	}

	public static double[] requireDecreasing(double[] actual, String varName) {
		requireValuesNotDegenerate(actual, "actual");
		requireNonNull(actual, varName);

		for (int i = 1; i < actual.length; i++) {
			if (actual[i - 1] < actual[i]) {
				throw new IllegalArgumentException("Values in '" + varName + "' must not be increasing: " +
				                                   actual[i - 1] + " ≰ " + actual[i] + " (at index " + (i - 1) + ')');
			}
		}

		return actual;
	}

	public static byte[] requireStrictlyDecreasing(byte[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 1; i < actual.length; i++) {
			if (actual[i - 1] <= actual[i]) {
				throw new IllegalArgumentException("Values in '" + varName + "' must be strictly decreasing: " +
				                                   actual[i - 1] + " ≮ " + actual[i] + " (at index " + (i - 1) + ')');
			}
		}

		return actual;
	}

	public static byte[] requireStrictlyDecreasingUnsigned(byte[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 1; i < actual.length; i++) {
			if ((actual[i - 1] & 0xFF) <= (actual[i] & 0xFF)) {
				throw new IllegalArgumentException(
						"Unsigned values in '" + varName + "' must be strictly decreasing: " +
						(actual[i - 1] & 0xFF) + " ≮ " + (actual[i] & 0xFF) + " (at index " + (i - 1) + ')');
			}
		}

		return actual;
	}

	public static int[] requireStrictlyDecreasing(int[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 1; i < actual.length; i++) {
			if (actual[i - 1] <= actual[i]) {
				throw new IllegalArgumentException("Values in '" + varName + "' must be strictly decreasing: " +
				                                   actual[i - 1] + " ≮ " + actual[i] + " (at index " + (i - 1) + ')');
			}
		}

		return actual;
	}

	public static long[] requireStrictlyDecreasing(long[] actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 1; i < actual.length; i++) {
			if (actual[i - 1] <= actual[i]) {
				throw new IllegalArgumentException("Values in '" + varName + "' must be strictly decreasing: " +
				                                   actual[i - 1] + " ≮ " + actual[i] + " (at index " + (i - 1) + ')');
			}
		}

		return actual;
	}

	public static float[] requireStrictlyDecreasing(float[] actual, String varName) {
		requireValuesNotDegenerate(actual, "actual");
		requireNonNull(actual, varName);

		for (int i = 1; i < actual.length; i++) {
			if (actual[i - 1] <= actual[i]) {
				throw new IllegalArgumentException("Values in '" + varName + "' must be strictly decreasing: " +
				                                   actual[i - 1] + " ≮ " + actual[i] + " (at index " + (i - 1) + ')');
			}
		}

		return actual;
	}

	public static double[] requireStrictlyDecreasing(double[] actual, String varName) {
		requireValuesNotDegenerate(actual, "actual");
		requireNonNull(actual, varName);

		for (int i = 1; i < actual.length; i++) {
			if (actual[i - 1] <= actual[i]) {
				throw new IllegalArgumentException("Values in '" + varName + "' must be strictly decreasing: " +
				                                   actual[i - 1] + " ≮ " + actual[i] + " (at index " + (i - 1) + ')');
			}
		}

		return actual;
	}

	public static byte[] requireSummingTo(long expectedSum, byte[] actual, String varName) {
		requireNotDegenerate(expectedSum, "expectedSum");
		requireNonNull(actual, varName);

		long sum = 0;

		for (int i = 1; i < actual.length; i++) {
			sum += actual[i];
		}

		if (sum != expectedSum) {
			throw new IllegalArgumentException("Values in '" + varName + "' must sum to " + expectedSum +
			                                   ": actual sum = " + sum);
		}

		return actual;
	}

	public static byte[] requireSummingUnsignedTo(long expectedSum, byte[] actual, String varName) {
		requireNotDegenerate(expectedSum, "expectedSum");
		requireNonNull(actual, varName);

		long sum = 0;

		for (int i = 1; i < actual.length; i++) {
			sum += actual[i] & 0xFF;
		}

		if (sum != expectedSum) {
			throw new IllegalArgumentException("Unsigned values in '" + varName + "' must sum to " + expectedSum +
			                                   ": actual sum = " + sum);
		}

		return actual;
	}

	public static int[] requireSummingTo(long expectedSum, int[] actual, String varName) {
		requireNotDegenerate(expectedSum, "expectedSum");
		requireNonNull(actual, varName);

		long sum = 0;

		for (int i = 1; i < actual.length; i++) {
			sum += actual[i];
		}

		if (sum != expectedSum) {
			throw new IllegalArgumentException("Values in '" + varName + "' must sum to " + expectedSum +
			                                   ": actual sum = " + sum);
		}

		return actual;
	}

	public static long[] requireSummingTo(long expectedSum, long[] actual, String varName) {
		requireNotDegenerate(expectedSum, "expectedSum");
		requireNonNull(actual, varName);

		long sum = 0;

		for (int i = 1; i < actual.length; i++) {
			sum += actual[i];
		}

		if (sum != expectedSum) {
			throw new IllegalArgumentException("Values in '" + varName + "' must sum to " + expectedSum +
			                                   ": actual sum = " + sum);
		}

		return actual;
	}

	public static float[] requireSummingTo(float expectedSum, float tolerance, float[] actual, String varName) {
		requireNotDegenerate(expectedSum, "expectedSum");
		requireValuesNotDegenerate(actual, "actual");
		requireNonNull(actual, varName);

		float sum = 0.0f;

		for (int i = 1; i < actual.length; i++) {
			sum += actual[i];
		}

		if (Math.abs(sum - expectedSum) > tolerance) {
			throw new IllegalArgumentException("Values in '" + varName + "' must sum to " + expectedSum + " ± " +
			                                   tolerance + ": actual sum = " + sum);
		}

		return actual;
	}

	public static double[] requireSummingTo(double expectedSum, double tolerance, double[] actual, String varName) {
		requireNotDegenerate(expectedSum, "expectedSum");
		requireNotDegenerate(tolerance, "tolerance");
		requireValuesNotDegenerate(actual, "actual");
		requireNonNull(actual, varName);

		double sum = 0.0;

		for (int i = 1; i < actual.length; i++) {
			sum += actual[i];
		}

		if (Math.abs(sum - expectedSum) > tolerance) {
			throw new IllegalArgumentException("Values in '" + varName + "' must sum to " + expectedSum + " ± " +
			                                   tolerance + ": actual sum = " + sum);
		}

		return actual;
	}
}
