/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2024 Mark Jeronimus. All Rights Reversed.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.digitalmodular.utilities;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.digitalmodular.utilities.annotation.UtilityClass;
import static org.digitalmodular.utilities.ArrayValidatorUtilities.requireArrayLengthAtLeast;
import static org.digitalmodular.utilities.NumberUtilities.isDegenerate;

/**
 * @author Mark Jeronimus
 */
// Created 2016-12-21
@SuppressWarnings("OverloadedMethodsWithSameNumberOfParameters")
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

	public static <T> @NotNull T requireNonNull(@Nullable T actual, String varName) {
		if (actual == null) {
			throw new NullPointerException('\'' + varName + "' can't be null");
		}

		return actual;
	}

	public static float assertNotDegenerate(float actual, String varName) {
		if (isDegenerate(actual)) {
			throw new AssertionError('\'' + varName + "' is degenerate: " + actual);
		}

		return actual;
	}

	public static double assertNotDegenerate(double actual, String varName) {
		if (isDegenerate(actual)) {
			throw new AssertionError('\'' + varName + "' is degenerate: " + actual);
		}

		return actual;
	}

	public static float requireNotDegenerate(float actual, String varName) {
		if (isDegenerate(actual)) {
			throw new IllegalArgumentException('\'' + varName + "' is degenerate: " + actual);
		}

		return actual;
	}

	public static double requireNotDegenerate(double actual, String varName) {
		if (isDegenerate(actual)) {
			throw new IllegalArgumentException('\'' + varName + "' is degenerate: " + actual);
		}

		return actual;
	}

	public static @Nullable Float requireNullOrNotDegenerate(@Nullable Float actual, String varName) {
		if (actual == null) {
			return null;
		}

		return requireNotDegenerate(actual, varName);
	}

	public static @Nullable Double requireNullOrNotDegenerate(@Nullable Double actual, String varName) {
		if (actual == null) {
			return null;
		}

		return requireNotDegenerate(actual, varName);
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

	public static int requireAbove(int min, int actual, String varName) {
		assertThat(min < Integer.MAX_VALUE, () -> "'min' is degenerate: " + min);
		requireNotDegenerate(actual, varName);

		if (actual <= min) {
			throw new IllegalArgumentException('\'' + varName + "' must be above " + min + ": " + actual);
		}

		return actual;
	}

	public static long requireAbove(long min, long actual, String varName) {
		assertThat(min < Long.MAX_VALUE, () -> "'min' is degenerate: " + min);
		requireNotDegenerate(actual, varName);

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

	public static @Nullable Integer requireNullOrAbove(int min, @Nullable Integer actual, String varName) {
		assertThat(min < Integer.MAX_VALUE, () -> "'min' is degenerate: " + min);

		if (actual != null && actual <= min) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or above " +
			                                   min + ": " + actual);
		}

		return actual;
	}

	public static @Nullable Long requireNullOrAbove(long min, @Nullable Long actual, String varName) {
		assertThat(min < Long.MAX_VALUE, () -> "'min' is degenerate: " + min);

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

	public static int requireBelow(int max, int actual, String varName) {
		assertThat(max > Integer.MIN_VALUE, () -> "'max' is degenerate: " + max);

		if (actual >= max) {
			throw new IllegalArgumentException('\'' + varName + "' must be below " + max + ": " + actual);
		}

		return actual;
	}

	public static long requireBelow(long max, long actual, String varName) {
		assertThat(max > Long.MIN_VALUE, () -> "'max' is degenerate: " + max);

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

	public static @Nullable Integer requireNullOrBelow(int max, @Nullable Integer actual, String varName) {
		assertThat(max > Integer.MIN_VALUE, () -> "'max' is degenerate: " + max);

		if (actual != null && actual >= max) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or below " +
			                                   max + ": " + actual);
		}

		return actual;
	}

	public static @Nullable Long requireNullOrBelow(long max, @Nullable Long actual, String varName) {
		assertThat(max > Long.MIN_VALUE, () -> "'max' is degenerate: " + max);

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

	public static int requireRange(int min, int max, int actual, String varName) {
		assertThat(min <= max, () -> "Range is invalid: [" + min + ", " + max + ']');

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

	public static long requireRange(long min, long max, long actual, String varName) {
		assertThat(min <= max, () -> "Range is invalid: [" + min + ", " + max + ']');

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

	public static float requireRange(float min, float max, float actual, String varName) {
		assertNotDegenerate(min, "min");
		assertNotDegenerate(max, "max");
		assertThat(min <= max, () -> "Range is invalid: [" + min + ", " + max + ']');
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

	public static double requireRange(double min, double max, double actual, String varName) {
		assertNotDegenerate(min, "min");
		assertNotDegenerate(max, "max");
		assertThat(min <= max, () -> "Range is invalid: [" + min + ", " + max + ']');
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

	public static @Nullable Integer requireNullOrRange(int min, int max, @Nullable Integer actual, String varName) {
		assertThat(min <= max, () -> "Range is invalid: [" + min + ", " + max + ']');

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

	public static @Nullable Long requireNullOrRange(long min, long max, @Nullable Long actual, String varName) {
		assertThat(min <= max, () -> "Range is invalid: [" + min + ", " + max + ']');

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

	public static @Nullable Float requireNullOrRange(float min, float max, @Nullable Float actual, String varName) {
		assertNotDegenerate(min, "min");
		assertNotDegenerate(max, "max");
		assertThat(min <= max, () -> "Range is invalid: [" + min + ", " + max + ']');
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

	public static @Nullable Double requireNullOrRange(double min, double max, @Nullable Double actual, String varName) {
		assertNotDegenerate(min, "min");
		assertNotDegenerate(max, "max");
		assertThat(min <= max, () -> "Range is invalid: [" + min + ", " + max + ']');
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

	public static void requireDistance(long minDistance,
	                                   long maxDistance,
	                                   int actualMin,
	                                   int actualMax,
	                                   String varNameMin,
	                                   String varNameMax) {
		long dist = (long)actualMax - actualMin;

		if (dist < minDistance) {
			throw new IllegalArgumentException('\'' + varNameMin + "' and '" + varNameMax +
			                                   "' must be at least " + minDistance + " apart: " + dist);
		} else if (dist > maxDistance) {
			throw new IllegalArgumentException('\'' + varNameMin + "' and '" + varNameMax +
			                                   "' must be at most " + maxDistance + " apart: " + dist);
		}
	}

	public static void requireDistance(double minDistance,
	                                   double maxDistance,
	                                   double actualMin,
	                                   double actualMax,
	                                   String varNameMin,
	                                   String varNameMax) {
		requireNotDegenerate(minDistance, "minDistance");
		requireNotDegenerate(maxDistance, "maxDistance");
		requireNotDegenerate(actualMin, varNameMin);
		requireNotDegenerate(actualMax, varNameMax);

		double dist = actualMax - actualMin;

		if (dist < minDistance) {
			throw new IllegalArgumentException('\'' + varNameMin + "' and '" + varNameMax +
			                                   "' must be at least " + minDistance + " apart: " + dist);
		} else if (dist > maxDistance) {
			throw new IllegalArgumentException('\'' + varNameMin + "' and '" + varNameMax +
			                                   "' must be at most " + maxDistance + " apart: " + dist);
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

		if (isDegenerate(actual)) {
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
