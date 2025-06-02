package nl.airsupplies.utilities.validator;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import nl.airsupplies.utilities.NumberUtilities;
import nl.airsupplies.utilities.annotation.UtilityClass;
import static nl.airsupplies.utilities.validator.ArrayValidatorUtilities.requireArrayLengthAtLeast;

/**
 * @author Mark Jeronimus
 */
// Created 2016-12-21
@SuppressWarnings({"OverlyComplexClass", "UnusedReturnValue"})
@UtilityClass
public final class ValidatorUtilities {
	public static void assertThat(boolean condition, Supplier<String> message) {
		if (!condition) {
			throw new AssertionError(message.get());
		}
	}

	public static void requireThat(boolean condition, Supplier<String> message) {
		if (!condition) {
			throw new IllegalArgumentException(message.get());
		}
	}

	public static void requireState(boolean condition, Supplier<String> message) {
		if (!condition) {
			throw new IllegalStateException(message.get());
		}
	}

	public static <T> T requireType(Class<? extends T> type, Object actual, String varName) {
		requireNonNull(actual, varName);

		if (!type.isInstance(actual)) {
			throw new IllegalArgumentException('\'' + varName + "' must be of type " + type.getName() +
			                                   " (type is " + actual.getClass() + ')');
		}

		//noinspection unchecked
		return (T)actual;
	}

	public static <T> T assertNonNull(T actual, String varName) {
		if (actual == null) {
			throw new AssertionError('\'' + varName + "' can't be null");
		}

		return actual;
	}

	public static <T> T requireNonNull(@Nullable T actual, String varName) {
		if (actual == null) {
			throw new NullPointerException('\'' + varName + "' can't be null");
		}

		return actual;
	}

	public static float assertNotDegenerate(float actual, String varName) {
		if (NumberUtilities.isDegenerate(actual)) {
			throw new AssertionError('\'' + varName + "' is degenerate: " + actual);
		}

		return actual;
	}

	public static double assertNotDegenerate(double actual, String varName) {
		if (NumberUtilities.isDegenerate(actual)) {
			throw new AssertionError('\'' + varName + "' is degenerate: " + actual);
		}

		return actual;
	}

	public static float requireNotDegenerate(float actual, String varName) {
		if (NumberUtilities.isDegenerate(actual)) {
			throw new IllegalArgumentException('\'' + varName + "' is degenerate: " + actual);
		}

		return actual;
	}

	public static double requireNotDegenerate(double actual, String varName) {
		if (NumberUtilities.isDegenerate(actual)) {
			throw new IllegalArgumentException('\'' + varName + "' is degenerate: " + actual);
		}

		return actual;
	}

	public static @Nullable Float requireNullOrNotDegenerate(@Nullable Float actual, String varName) {
		if (actual == null) {
			return null;
		}

		if (NumberUtilities.isDegenerate(actual)) {
			throw new IllegalArgumentException('\'' + varName + "' is degenerate: " + actual);
		}

		return actual;
	}

	public static @Nullable Double requireNullOrNotDegenerate(@Nullable Double actual, String varName) {
		if (actual == null) {
			return null;
		}

		if (NumberUtilities.isDegenerate(actual)) {
			throw new IllegalArgumentException('\'' + varName + "' is degenerate: " + actual);
		}

		return actual;
	}

	public static byte requireNonZero(byte actual, String varName) {
		if (actual == 0) {
			throw new IllegalArgumentException('\'' + varName + "' must be non-zero");
		}

		return actual;
	}

	public static int requireNonZero(int actual, String varName) {
		if (actual == 0) {
			throw new IllegalArgumentException('\'' + varName + "' must be non-zero");
		}

		return actual;
	}

	public static long requireNonZero(long actual, String varName) {
		if (actual == 0) {
			throw new IllegalArgumentException('\'' + varName + "' must be non-zero");
		}

		return actual;
	}

	public static float requireNonZero(float actual, String varName) {
		requireNotDegenerate(actual, varName);

		if (actual == 0) {
			throw new IllegalArgumentException('\'' + varName + "' must be non-zero");
		}

		return actual;
	}

	public static double requireNonZero(double actual, String varName) {
		requireNotDegenerate(actual, varName);

		if (actual == 0) {
			throw new IllegalArgumentException('\'' + varName + "' must be non-zero");
		}

		return actual;
	}

	public static @Nullable Byte requireNullOrNonZero(@Nullable Byte actual, String varName) {
		if (actual != null && actual == 0) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or non-zero");
		}

		return actual;
	}

	public static @Nullable Integer requireNullOrNonZero(@Nullable Integer actual, String varName) {
		if (actual != null && actual == 0) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or non-zero");
		}

		return actual;
	}

	public static @Nullable Long requireNullOrNonZero(@Nullable Long actual, String varName) {
		if (actual != null && actual == 0) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or non-zero");
		}

		return actual;
	}

	public static @Nullable Float requireNullOrNonZero(@Nullable Float actual, String varName) {
		requireNullOrNotDegenerate(actual, varName);

		if (actual != null && actual == 0) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or non-zero");
		}

		return actual;
	}

	public static @Nullable Double requireNullOrNonZero(@Nullable Double actual, String varName) {
		requireNullOrNotDegenerate(actual, varName);

		if (actual != null && actual == 0) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or non-zero");
		}

		return actual;
	}

	public static byte requireAtLeast(byte min, byte actual, String varName) {
		if (actual < min) {
			throw new IllegalArgumentException('\'' + varName + "' must be at least " + min + ": " + actual);
		}

		return actual;
	}

	public static byte requireAtLeastUnsigned(int min, byte actual, String varName) {
		assertThat(min >= 0 && min <= 255, () -> "'min' is invalid: " + min);

		if ((actual & 0xFF) < min) {
			throw new IllegalArgumentException('\'' + varName + "' must be at least " + min + ": " + (actual & 0xFF));
		}

		return actual;
	}

	public static int requireAtLeast(int min, int actual, String varName) {
		if (actual < min) {
			throw new IllegalArgumentException('\'' + varName + "' must be at least " + min + ": " + actual);
		}

		return actual;
	}

	public static long requireAtLeast(long min, long actual, String varName) {
		if (actual < min) {
			throw new IllegalArgumentException('\'' + varName + "' must be at least " + min + ": " + actual);
		}

		return actual;
	}

	public static float requireAtLeast(float min, float actual, String varName) {
		assertNotDegenerate(min, "min");
		requireNotDegenerate(actual, varName);

		if (actual < min) {
			throw new IllegalArgumentException('\'' + varName + "' must be at least " + min + ": " + actual);
		}

		return actual;
	}

	public static double requireAtLeast(double min, double actual, String varName) {
		assertNotDegenerate(min, "min");
		requireNotDegenerate(actual, varName);

		if (actual < min) {
			throw new IllegalArgumentException('\'' + varName + "' must be at least " + min + ": " + actual);
		}

		return actual;
	}

	public static @Nullable Byte requireNullOrAtLeast(byte min, @Nullable Byte actual, String varName) {
		if (actual != null && actual < min) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or at least " +
			                                   min + ": " + actual);
		}

		return actual;
	}

	public static @Nullable Byte requireNullOrAtLeastUnsigned(int min, @Nullable Byte actual, String varName) {
		assertThat(min >= 0 && min <= 255, () -> "'min' is invalid: " + min);

		if (actual != null && actual < min) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or at least " +
			                                   min + ": " + actual);
		}

		return actual;
	}

	public static @Nullable Integer requireNullOrAtLeast(int min, @Nullable Integer actual, String varName) {
		if (actual != null && actual < min) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or at least " +
			                                   min + ": " + actual);
		}

		return actual;
	}

	public static @Nullable Long requireNullOrAtLeast(long min, @Nullable Long actual, String varName) {
		if (actual != null && actual < min) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or at least " +
			                                   min + ": " + actual);
		}

		return actual;
	}

	public static @Nullable Float requireNullOrAtLeast(float min, @Nullable Float actual, String varName) {
		assertNotDegenerate(min, "min");
		requireNullOrNotDegenerate(actual, varName);

		if (actual != null && actual < min) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or at least " +
			                                   min + ": " + actual);
		}

		return actual;
	}

	public static @Nullable Double requireNullOrAtLeast(double min, @Nullable Double actual, String varName) {
		assertNotDegenerate(min, "min");
		requireNullOrNotDegenerate(actual, varName);

		if (actual != null && actual < min) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or at least " +
			                                   min + ": " + actual);
		}

		return actual;
	}

	public static int requireAtMost(int max, int actual, String varName) {
		if (actual > max) {
			throw new IllegalArgumentException('\'' + varName + "' must be at most " + max + ": " + actual);
		}

		return actual;
	}

	public static byte requireAtMost(byte max, byte actual, String varName) {
		if (actual > max) {
			throw new IllegalArgumentException('\'' + varName + "' must be at most " + max + ": " + actual);
		}

		return actual;
	}

	public static byte requireAtMostUnsigned(int max, byte actual, String varName) {
		assertThat(max >= 0 && max <= 255, () -> "'max' is invalid: " + max);

		if (actual > max) {
			throw new IllegalArgumentException('\'' + varName + "' must be at most " + max + ": " + actual);
		}

		return actual;
	}

	public static long requireAtMost(long max, long actual, String varName) {
		if (actual > max) {
			throw new IllegalArgumentException('\'' + varName + "' must be at most " + max + ": " + actual);
		}

		return actual;
	}

	public static float requireAtMost(float max, float actual, String varName) {
		assertNotDegenerate(max, "max");
		requireNotDegenerate(actual, varName);

		if (actual > max) {
			throw new IllegalArgumentException('\'' + varName + "' must be at most " + max + ": " + actual);
		}

		return actual;
	}

	public static double requireAtMost(double max, double actual, String varName) {
		assertNotDegenerate(max, "max");
		requireNotDegenerate(actual, varName);

		if (actual > max) {
			throw new IllegalArgumentException('\'' + varName + "' must be at most " + max + ": " + actual);
		}

		return actual;
	}

	public static @Nullable Byte requireNullOrAtMost(byte max, @Nullable Byte actual, String varName) {
		if (actual != null && actual > max) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or at most " +
			                                   max + ": " + actual);
		}

		return actual;
	}

	public static @Nullable Byte requireNullOrAtMostUnsigned(int max, @Nullable Byte actual, String varName) {
		assertThat(max >= 0 && max <= 255, () -> "'max' is invalid: " + max);

		if (actual != null && (actual & 0xFF) > max) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or at most " +
			                                   max + ": " + (actual & 0xFF));
		}

		return actual;
	}

	public static @Nullable Integer requireNullOrAtMost(int max, @Nullable Integer actual, String varName) {
		if (actual != null && actual > max) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or at most " +
			                                   max + ": " + actual);
		}

		return actual;
	}

	public static @Nullable Long requireNullOrAtMost(long max, @Nullable Long actual, String varName) {
		if (actual != null && actual > max) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or at most " +
			                                   max + ": " + actual);
		}

		return actual;
	}

	public static @Nullable Float requireNullOrAtMost(float max, @Nullable Float actual, String varName) {
		assertNotDegenerate(max, "max");
		requireNullOrNotDegenerate(actual, varName);

		if (actual != null && actual > max) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or at most " +
			                                   max + ": " + actual);
		}

		return actual;
	}

	public static @Nullable Double requireNullOrAtMost(double max, @Nullable Double actual, String varName) {
		assertNotDegenerate(max, "max");
		requireNullOrNotDegenerate(actual, varName);

		if (actual != null && actual > max) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or at most " +
			                                   max + ": " + actual);
		}

		return actual;
	}

	public static byte requireAbove(byte min, byte actual, String varName) {
		assertThat(min < Byte.MAX_VALUE, () -> "'min' must not be Byte.MAX_VALUE");

		if (actual <= min) {
			throw new IllegalArgumentException('\'' + varName + "' must be above " + min + ": " + actual);
		}

		return actual;
	}

	public static byte requireAboveUnsigned(int min, byte actual, String varName) {
		assertThat(min >= 0 && min <= 254, () -> "'min' is invalid: " + min);

		if ((actual & 0xFF) <= min) {
			throw new IllegalArgumentException('\'' + varName + "' must be above " + min + ": " + (actual & 0xFF));
		}

		return actual;
	}

	public static int requireAbove(int min, int actual, String varName) {
		assertThat(min < Integer.MAX_VALUE, () -> "'min' must not be Integer.MAX_VALUE");

		if (actual <= min) {
			throw new IllegalArgumentException('\'' + varName + "' must be above " + min + ": " + actual);
		}

		return actual;
	}

	public static long requireAbove(long min, long actual, String varName) {
		assertThat(min < Long.MAX_VALUE, () -> "'min' must not be Long.MAX_VALUE");

		if (actual <= min) {
			throw new IllegalArgumentException('\'' + varName + "' must be above " + min + ": " + actual);
		}

		return actual;
	}

	public static float requireAbove(float min, float actual, String varName) {
		assertNotDegenerate(min, "min");
		requireNotDegenerate(actual, varName);

		if (actual <= min) {
			throw new IllegalArgumentException('\'' + varName + "' must be above " + min + ": " + actual);
		}

		return actual;
	}

	public static double requireAbove(double min, double actual, String varName) {
		assertNotDegenerate(min, "min");
		requireNotDegenerate(actual, varName);

		if (actual <= min) {
			throw new IllegalArgumentException('\'' + varName + "' must be above " + min + ": " + actual);
		}

		return actual;
	}

	public static @Nullable Byte requireNullOrAbove(byte min, @Nullable Byte actual, String varName) {
		assertThat(min < Byte.MAX_VALUE, () -> "'min' must not be Byte.MAX_VALUE");

		if (actual != null && actual <= min) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or above " +
			                                   min + ": " + actual);
		}

		return actual;
	}

	public static @Nullable Byte requireNullOrAboveUnsigned(int min, @Nullable Byte actual, String varName) {
		assertThat(min >= 0 && min <= 254, () -> "'min' is invalid: " + min);

		if (actual != null && (actual & 0xFF) <= min) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or above " +
			                                   min + ": " + (actual & 0xFF));
		}

		return actual;
	}

	public static @Nullable Integer requireNullOrAbove(int min, @Nullable Integer actual, String varName) {
		assertThat(min < Integer.MAX_VALUE, () -> "'min' must not be Integer.MAX_VALUE");

		if (actual != null && actual <= min) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or above " +
			                                   min + ": " + actual);
		}

		return actual;
	}

	public static @Nullable Long requireNullOrAbove(long min, @Nullable Long actual, String varName) {
		assertThat(min < Long.MAX_VALUE, () -> "'min' must not be Long.MAX_VALUE");

		if (actual != null && actual <= min) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or above " +
			                                   min + ": " + actual);
		}

		return actual;
	}

	public static @Nullable Float requireNullOrAbove(float min, @Nullable Float actual, String varName) {
		assertNotDegenerate(min, "min");
		requireNullOrNotDegenerate(actual, varName);

		if (actual != null && actual <= min) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or above " +
			                                   min + ": " + actual);
		}

		return actual;
	}

	public static @Nullable Double requireNullOrAbove(double min, @Nullable Double actual, String varName) {
		assertNotDegenerate(min, "min");
		requireNullOrNotDegenerate(actual, varName);

		if (actual != null && actual <= min) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or above " +
			                                   min + ": " + actual);
		}

		return actual;
	}

	public static byte requireBelow(byte max, byte actual, String varName) {
		assertThat(max > Byte.MIN_VALUE, () -> "'max' must not be Byte.MIN_VALUE");

		if (actual >= max) {
			throw new IllegalArgumentException('\'' + varName + "' must be below " + max + ": " + actual);
		}

		return actual;
	}

	public static byte requireBelowUnsigned(int max, byte actual, String varName) {
		assertThat(max >= 0 && max <= 255, () -> "'max' is invalid: " + max);

		if ((actual & 0xFF) >= max) {
			throw new IllegalArgumentException('\'' + varName + "' must be below " + max + ": " + (actual & 0xFF));
		}

		return actual;
	}

	public static int requireBelow(int max, int actual, String varName) {
		assertThat(max > Integer.MIN_VALUE, () -> "'max' must not be Integer.MIN_VALUE");

		if (actual >= max) {
			throw new IllegalArgumentException('\'' + varName + "' must be below " + max + ": " + actual);
		}

		return actual;
	}

	public static long requireBelow(long max, long actual, String varName) {
		assertThat(max > Long.MIN_VALUE, () -> "'max' must not be Long.MIN_VALUE");

		if (actual >= max) {
			throw new IllegalArgumentException('\'' + varName + "' must be below " + max + ": " + actual);
		}

		return actual;
	}

	public static float requireBelow(float max, float actual, String varName) {
		assertNotDegenerate(max, "max");
		requireNotDegenerate(actual, varName);

		if (actual >= max) {
			throw new IllegalArgumentException('\'' + varName + "' must be below " + max + ": " + actual);
		}

		return actual;
	}

	public static double requireBelow(double max, double actual, String varName) {
		assertNotDegenerate(max, "max");
		requireNotDegenerate(actual, varName);

		if (actual >= max) {
			throw new IllegalArgumentException('\'' + varName + "' must be below " + max + ": " + actual);
		}

		return actual;
	}

	public static @Nullable Byte requireNullOrBelow(byte max, @Nullable Byte actual, String varName) {
		assertThat(max > Byte.MIN_VALUE, () -> "'max' must not be Byte.MIN_VALUE");

		if (actual != null && actual >= max) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or below " +
			                                   max + ": " + actual);
		}

		return actual;
	}

	public static @Nullable Byte requireNullOrBelowUnsigned(int max, @Nullable Byte actual, String varName) {
		assertThat(max >= 1 && max <= 255, () -> "'max' is invalid: " + max);

		if (actual != null && (actual & 0xFF) >= max) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or below " +
			                                   max + ": " + (actual & 0xFF));
		}

		return actual;
	}

	public static @Nullable Integer requireNullOrBelow(int max, @Nullable Integer actual, String varName) {
		assertThat(max > Integer.MIN_VALUE, () -> "'max' must not be Integer.MIN_VALUE");

		if (actual != null && actual >= max) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or below " +
			                                   max + ": " + actual);
		}

		return actual;
	}

	public static @Nullable Long requireNullOrBelow(long max, @Nullable Long actual, String varName) {
		assertThat(max > Long.MIN_VALUE, () -> "'max' must not be Long.MIN_VALUE");

		if (actual != null && actual >= max) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or below " +
			                                   max + ": " + actual);
		}

		return actual;
	}

	public static @Nullable Float requireNullOrBelow(float max, @Nullable Float actual, String varName) {
		assertNotDegenerate(max, "max");
		requireNullOrNotDegenerate(actual, varName);

		if (actual != null && actual >= max) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or below " +
			                                   max + ": " + actual);
		}

		return actual;
	}

	public static @Nullable Double requireNullOrBelow(double max, @Nullable Double actual, String varName) {
		assertNotDegenerate(max, "max");
		requireNullOrNotDegenerate(actual, varName);

		if (actual != null && actual >= max) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or below " +
			                                   max + ": " + actual);
		}

		return actual;
	}

	public static byte requireBetween(byte min, byte max, byte actual, String varName) {
		assertThat(min <= max, () -> "Range is invalid: " + min + "..." + max);

		if (actual < min || actual > max) {
			if (min == max) {
				throw new IllegalArgumentException('\'' + varName + "' must be exactly " +
				                                   min + ": " + actual);
			} else {
				throw new IllegalArgumentException('\'' + varName + "' must be in the range [" +
				                                   min + ", " + max + "]: " + actual);
			}
		}

		return actual;
	}

	public static byte requireBetweenUnsigned(int min, int max, byte actual, String varName) {
		assertThat(min >= 0 && min <= 255, () -> "'min' is invalid: " + min);
		assertThat(max >= 0 && max <= 255, () -> "'max' is invalid: " + max);
		assertThat(min <= max, () -> "Range is invalid: " + min + "..." + max);

		if (actual < min || actual > max) {
			if (min == max) {
				throw new IllegalArgumentException('\'' + varName + "' must be exactly " +
				                                   min + ": " + actual);
			} else {
				throw new IllegalArgumentException('\'' + varName + "' must be in the range [" +
				                                   min + ", " + max + "]: " + actual);
			}
		}

		return actual;
	}

	public static int requireBetween(int min, int max, int actual, String varName) {
		assertThat(min <= max, () -> "Range is invalid: " + min + "..." + max);

		if (actual < min || actual > max) {
			if (min == max) {
				throw new IllegalArgumentException('\'' + varName + "' must be exactly " +
				                                   min + ": " + actual);
			} else {
				throw new IllegalArgumentException('\'' + varName + "' must be in the range [" +
				                                   min + ", " + max + "]: " + actual);
			}
		}

		return actual;
	}

	public static long requireBetween(long min, long max, long actual, String varName) {
		assertThat(min <= max, () -> "Range is invalid: " + min + "..." + max);

		if (actual < min || actual > max) {
			if (min == max) {
				throw new IllegalArgumentException('\'' + varName + "' must be exactly " +
				                                   min + ": " + actual);
			} else {
				throw new IllegalArgumentException('\'' + varName + "' must be in the range [" +
				                                   min + ", " + max + "]: " + actual);
			}
		}

		return actual;
	}

	public static float requireBetween(float min, float max, float actual, String varName) {
		assertNotDegenerate(min, "min");
		assertNotDegenerate(max, "max");
		assertThat(min <= max, () -> "Range is invalid: " + min + "..." + max);
		requireNotDegenerate(actual, varName);

		if (actual < min || actual > max) {
			if (min == max) {
				throw new IllegalArgumentException('\'' + varName + "' must be exactly " +
				                                   min + ": " + actual);
			} else {
				throw new IllegalArgumentException('\'' + varName + "' must be in the range [" +
				                                   min + ", " + max + "]: " + actual);
			}
		}

		return actual;
	}

	public static double requireBetween(double min, double max, double actual, String varName) {
		assertNotDegenerate(min, "min");
		assertNotDegenerate(max, "max");
		assertThat(min <= max, () -> "Range is invalid: " + min + "..." + max);
		requireNotDegenerate(actual, varName);

		if (actual < min || actual > max) {
			if (min == max) {
				throw new IllegalArgumentException('\'' + varName + "' must be exactly " +
				                                   min + ": " + actual);
			} else {
				throw new IllegalArgumentException('\'' + varName + "' must be in the range [" +
				                                   min + ", " + max + "]: " + actual);
			}
		}

		return actual;
	}

	public static @Nullable Integer requireNullOrBetween(int min, int max, @Nullable Integer actual, String varName) {
		assertThat(min <= max, () -> "Range is invalid: " + min + "..." + max);

		if (actual != null && (actual < min || actual > max)) {
			if (min == max) {
				throw new IllegalArgumentException('\'' + varName + "' must either be null or exactly " +
				                                   min + ": " + actual);
			} else {
				throw new IllegalArgumentException('\'' + varName + "' must either be null or in the range [" +
				                                   min + ", " + max + "]: " + actual);
			}
		}

		return actual;
	}

	public static @Nullable Byte requireNullOrBetween(byte min, byte max, @Nullable Byte actual, String varName) {
		assertThat(min <= max, () -> "Range is invalid: " + min + "..." + max);

		if (actual != null && (actual < min || actual > max)) {
			if (min == max) {
				throw new IllegalArgumentException('\'' + varName + "' must either be null or exactly " +
				                                   min + ": " + actual);
			} else {
				throw new IllegalArgumentException('\'' + varName + "' must either be null or in the range [" +
				                                   min + ", " + max + "]: " + actual);
			}
		}

		return actual;
	}

	public static @Nullable Byte requireNullOrBetweenUnsigned(int min, int max, @Nullable Byte actual, String varName) {
		assertThat(min >= 0 && min <= 255, () -> "'min' is invalid: " + min);
		assertThat(max >= 0 && max <= 255, () -> "'max' is invalid: " + max);
		assertThat(min <= max, () -> "Range is invalid: " + min + "..." + max);

		if (actual != null && (actual < min || actual > max)) {
			if (min == max) {
				throw new IllegalArgumentException('\'' + varName + "' must either be null or exactly " +
				                                   min + ": " + actual);
			} else {
				throw new IllegalArgumentException('\'' + varName + "' must either be null or in the range [" +
				                                   min + ", " + max + "]: " + actual);
			}
		}

		return actual;
	}

	public static @Nullable Long requireNullOrBetween(long min, long max, @Nullable Long actual, String varName) {
		assertThat(min <= max, () -> "Range is invalid: " + min + "..." + max);

		if (actual != null && (actual < min || actual > max)) {
			if (min == max) {
				throw new IllegalArgumentException('\'' + varName + "' must either be null or exactly " +
				                                   min + ": " + actual);
			} else {
				throw new IllegalArgumentException('\'' + varName + "' must either be null or in the range [" +
				                                   min + ", " + max + "]: " + actual);
			}
		}

		return actual;
	}

	public static @Nullable Float requireNullOrBetween(float min, float max, @Nullable Float actual, String varName) {
		assertNotDegenerate(min, "min");
		assertNotDegenerate(max, "max");
		assertThat(min <= max, () -> "Range is invalid: " + min + "..." + max);
		requireNullOrNotDegenerate(actual, varName);

		if (actual != null && (actual < min || actual > max)) {
			if (min == max) {
				throw new IllegalArgumentException('\'' + varName + "' must either be null or exactly " +
				                                   min + ": " + actual);
			} else {
				throw new IllegalArgumentException('\'' + varName + "' must either be null or in the range [" +
				                                   min + ", " + max + "]: " + actual);
			}
		}

		return actual;
	}

	public static @Nullable Double requireNullOrBetween(
			double min, double max, @Nullable Double actual, String varName) {
		assertNotDegenerate(min, "min");
		assertNotDegenerate(max, "max");
		assertThat(min <= max, () -> "Range is invalid: " + min + "..." + max);
		requireNullOrNotDegenerate(actual, varName);

		if (actual != null && (actual < min || actual > max)) {
			if (min == max) {
				throw new IllegalArgumentException('\'' + varName + "' must either be null or exactly " +
				                                   min + ": " + actual);
			} else {
				throw new IllegalArgumentException('\'' + varName + "' must either be null or in the range [" +
				                                   min + ", " + max + "]: " + actual);
			}
		}

		return actual;
	}

	public static int requireZeroOrRange(int min, int max, int actual, String varName) {
		assertThat(min <= max, () -> "Range is invalid: " + min + "..." + max);

		if (actual != 0 && (actual < min || actual > max)) {
			if (min == max) {
				throw new IllegalArgumentException('\'' + varName + "' must either be zero or exactly " +
				                                   min + ": " + actual);
			} else {
				throw new IllegalArgumentException('\'' + varName + "' must either be zero or in the range [" +
				                                   min + ", " + max + "]: " + actual);
			}
		}

		return actual;
	}

	public static long requireZeroOrRange(long min, long max, long actual, String varName) {
		assertThat(min <= max, () -> "Range is invalid: " + min + "..." + max);

		if (actual != 0 && (actual < min || actual > max)) {
			if (min == max) {
				throw new IllegalArgumentException('\'' + varName + "' must either be zero or exactly " +
				                                   min + ": " + actual);
			} else {
				throw new IllegalArgumentException('\'' + varName + "' must either be zero or in the range [" +
				                                   min + ", " + max + "]: " + actual);
			}
		}

		return actual;
	}

	public static float requireZeroOrRange(float min, float max, float actual, String varName) {
		assertNotDegenerate(min, "min");
		assertNotDegenerate(max, "max");
		assertThat(min <= max, () -> "Range is invalid: " + min + "..." + max);
		requireNotDegenerate(actual, "actual");

		if (actual != 0.0f && (actual < min || actual > max)) {
			if (min == max) {
				throw new IllegalArgumentException('\'' + varName + "' must either be zero or exactly " +
				                                   min + ": " + actual);
			} else {
				throw new IllegalArgumentException('\'' + varName + "' must either be zero or in the range [" +
				                                   min + ", " + max + "]: " + actual);
			}
		}

		return actual;
	}

	public static double requireZeroOrRange(double min, double max, double actual, String varName) {
		assertNotDegenerate(min, "min");
		assertNotDegenerate(max, "max");
		assertThat(min <= max, () -> "Range is invalid: " + min + "..." + max);
		requireNotDegenerate(actual, "actual");

		if (actual != 0.0 && (actual < min || actual > max)) {
			if (min == max) {
				throw new IllegalArgumentException('\'' + varName + "' must either be zero or exactly " +
				                                   min + ": " + actual);
			} else {
				throw new IllegalArgumentException('\'' + varName + "' must either be zero or in the range [" +
				                                   min + ", " + max + "]: " + actual);
			}
		}

		return actual;
	}

	public static void requireDistanceBetween(
			long minDist, long maxDist, int actualMin, int actualMax, String varNameMin, String varNameMax) {
		assertThat(maxDist >= minDist, () -> "Range is invalid: " + minDist + "..." + maxDist);
		long dist = (long)actualMax - actualMin;

		if (dist < minDist) {
			throw new IllegalArgumentException('\'' + varNameMin + "' and '" + varNameMax +
			                                   "' must be at least " + minDist + " apart: " + dist);
		} else if (dist > maxDist) {
			throw new IllegalArgumentException('\'' + varNameMin + "' and '" + varNameMax +
			                                   "' must be at most " + maxDist + " apart: " + dist);
		}
	}

	public static void requireDistanceBetween(
			long minDist, long maxDist, long actualMin, long actualMax, String varNameMin, String varNameMax) {
		assertThat(maxDist >= minDist, () -> "Range is invalid: " + minDist + "..." + maxDist);
		long dist = actualMax - actualMin;

		if (dist < minDist) {
			throw new IllegalArgumentException('\'' + varNameMin + "' and '" + varNameMax +
			                                   "' must be at least " + minDist + " apart: " + dist);
		} else if (dist > maxDist) {
			throw new IllegalArgumentException('\'' + varNameMin + "' and '" + varNameMax +
			                                   "' must be at most " + maxDist + " apart: " + dist);
		}
	}

	public static void requireDistanceBetween(
			float minDist, float maxDist, float actualMin, float actualMax, String varNameMin, String varNameMax) {
		assertNotDegenerate(minDist, "minDist");
		assertNotDegenerate(maxDist, "maxDist");
		assertThat(maxDist >= minDist, () -> "Range is invalid: " + minDist + "..." + maxDist);
		requireNotDegenerate(actualMin, varNameMin);
		requireNotDegenerate(actualMax, varNameMax);

		double dist = actualMax - actualMin;

		if (dist < minDist) {
			throw new IllegalArgumentException('\'' + varNameMin + "' and '" + varNameMax +
			                                   "' must be at least " + minDist + " apart: " + dist);
		} else if (dist > maxDist) {
			throw new IllegalArgumentException('\'' + varNameMin + "' and '" + varNameMax +
			                                   "' must be at most " + maxDist + " apart: " + dist);
		}
	}

	public static void requireDistanceBetween(
			double minDist, double maxDist, double actualMin, double actualMax, String varNameMin, String varNameMax) {
		assertNotDegenerate(minDist, "minDist");
		assertNotDegenerate(maxDist, "maxDist");
		assertThat(maxDist >= minDist, () -> "Range is invalid: " + minDist + "..." + maxDist);
		requireNotDegenerate(actualMin, varNameMin);
		requireNotDegenerate(actualMax, varNameMax);

		double dist = actualMax - actualMin;

		if (dist < minDist) {
			throw new IllegalArgumentException('\'' + varNameMin + "' and '" + varNameMax +
			                                   "' must be at least " + minDist + " apart: " + dist);
		} else if (dist > maxDist) {
			throw new IllegalArgumentException('\'' + varNameMin + "' and '" + varNameMax +
			                                   "' must be at most " + maxDist + " apart: " + dist);
		}
	}

	public static int checkLength(int length, int actual, String varName) {
		assertThat(length >= 0, () -> "'length' is invalid: " + length);

		if (actual < 0 || actual >= length) {
			throw new IllegalArgumentException(
					'\'' + varName + "' must be at least 0 and below " + length + ": " + actual);
		}

		return actual;
	}

	public static <T> T requireOneOf(T[] allowed, T actual, String varName) {
		assertNonNull(allowed, "allowed");
		requireArrayLengthAtLeast(1, allowed, "allowed");
		requireNonNull(actual, varName);

		for (T element : allowed) {
			if (Objects.equals(element, actual)) {
				return actual;
			}
		}

		throw new IllegalArgumentException('\'' + varName + "' must be one of " + Arrays.toString(allowed) +
		                                   ": " + actual);
	}

	public static <T> T requireOneOf(Collection<T> allowed, T actual, String varName) {
		assertNonNull(allowed, "allowed");
		requireArrayLengthAtLeast(1, allowed, "allowed");
		requireNonNull(actual, varName);

		for (T element : allowed) {
			if (Objects.equals(element, actual)) {
				return actual;
			}
		}

		throw new IllegalArgumentException('\'' + varName + "' must be one of " + allowed + ": " + actual);
	}

	public static <P extends Point2D> P requirePointNotDegenerate(P actual, String varName) {
		requireNonNull(actual, varName);

		if (NumberUtilities.isDegenerate(actual)) {
			throw new IllegalArgumentException('\'' + varName + "' is degenerate: " + actual);
		}

		return actual;
	}

	public static void requireThread(String threadName) {
		String actual = Thread.currentThread().getName();
		if (!actual.equals(threadName)) {
			throw new IllegalMonitorStateException(
					"Method thread-bounded to " + threadName + " was called from thread " + actual);
		}
	}
}
