package nl.airsupplies.utilities.validator;

import java.lang.reflect.Array;

import org.jetbrains.annotations.Nullable;

import nl.airsupplies.utilities.annotation.UtilityClass;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.assertThat;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * @author Mark Jeronimus
 */
// Created 2024-??-?? Split from ValidatorUtilities
@SuppressWarnings({"OverlyComplexClass", "UnusedReturnValue"})
@UtilityClass
public final class ArrayValidatorUtilities {
	public static void requireArray(Object actual, String varName) {
		requireNonNull(actual, varName);

		if (!actual.getClass().isArray()) {
			throw new IllegalArgumentException('\'' + varName + "' must be an array");
		}
	}

	public static void requireNullOrArray(@Nullable Object actual, String varName) {
		if (actual != null && !actual.getClass().isArray()) {
			throw new IllegalArgumentException('\'' + varName + "' must be null or be an array");
		}
	}

	public static <A> A[] requireArrayNotEmpty(A[] actual, String varName) {
		requireNonNull(actual, varName);

		if (actual.length == 0) {
			throw new IllegalArgumentException('\'' + varName + "' must not be empty");
		}

		return actual;
	}

	public static void requireArrayNotEmpty(Object actual, String varName) {
		requireArray(actual, varName);

		if (!actual.getClass().isArray()) {

		}
		if (Array.getLength(actual) == 0) {
			throw new IllegalArgumentException('\'' + varName + "' must not be empty");
		}
	}

	public static <A> A @Nullable [] requireNullOrArrayNotEmpty(A @Nullable [] actual, String varName) {
		if (actual != null && actual.length == 0) {
			throw new IllegalArgumentException('\'' + varName + "' must be null or not be empty");
		}

		return actual;
	}

	public static void requireNullOrArrayNotEmpty(@Nullable Object actual, String varName) {
		requireNullOrArray(actual, varName);

		if (actual != null && Array.getLength(actual) == 0) {
			throw new IllegalArgumentException('\'' + varName + "' must be null or not be empty");
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
		requireArray(actual, varName);

		if (Array.getLength(actual) != length) {
			throw new IllegalArgumentException('\'' + varName + "' must have a length of exactly " +
			                                   length + ": " + Array.getLength(actual));
		}
	}

	public static <A> A @Nullable [] requireNullOrArrayLengthExactly(
			int length, A @Nullable [] actual, String varName) {
		assertThat(length >= 0, () -> "'length' is invalid: " + length);

		if (actual != null && actual.length != length) {
			throw new IllegalArgumentException('\'' + varName + "' must be null or have a length of exactly " +
			                                   length + ": " + actual.length);
		}

		return actual;
	}

	public static void requireNullOrArrayLengthExactly(int length, @Nullable Object actual, String varName) {
		requireNullOrArray(actual, varName);

		assertThat(length >= 0, () -> "'length' is invalid: " + length);

		if (actual != null && Array.getLength(actual) != length) {
			throw new IllegalArgumentException('\'' + varName + "' must be null or have a length of exactly " +
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
		requireArray(actual, varName);

		if (Array.getLength(actual) < min) {
			throw new IllegalArgumentException('\'' + varName + "' must have a length of at least " +
			                                   min + ": " + Array.getLength(actual));
		}
	}

	public static <A> A @Nullable [] requireNullOrArrayLengthAtLeast(int min, A @Nullable [] actual, String varName) {
		assertThat(min >= 0, () -> "'min' is invalid: " + min);

		if (actual != null && actual.length < min) {
			throw new IllegalArgumentException('\'' + varName + "' must be null or have a length of at least " +
			                                   min + ": " + actual.length);
		}

		return actual;
	}

	public static void requireNullOrLengthAtLeast(int min, @Nullable Object actual, String varName) {
		assertThat(min >= 0, () -> "'min' is invalid: " + min);
		requireNullOrArray(actual, varName);

		if (actual != null && Array.getLength(actual) < min) {
			throw new IllegalArgumentException('\'' + varName + "' must be null or have a length of at least " +
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
		requireArray(actual, varName);

		if (Array.getLength(actual) > max) {
			throw new IllegalArgumentException('\'' + varName + "' must have a length of at most " +
			                                   max + ": " + Array.getLength(actual));
		}
	}

	public static <A> A @Nullable [] requireNullOrArrayLengthAtMost(int max, A @Nullable [] actual, String varName) {
		assertThat(max >= 0, () -> "'max' is invalid: " + max);

		if (actual != null && actual.length > max) {
			throw new IllegalArgumentException('\'' + varName + "' must be null or have a length of at most " +
			                                   max + ": " + actual.length);
		}

		return actual;
	}

	public static void requireNullOrArrayLengthAtMost(int max, @Nullable Object actual, String varName) {
		assertThat(max >= 0, () -> "'max' is invalid: " + max);
		requireNullOrArray(actual, varName);

		if (actual != null && Array.getLength(actual) > max) {
			throw new IllegalArgumentException('\'' + varName + "' must be null or have a length of at most " +
			                                   max + ": " + Array.getLength(actual));
		}
	}

	public static <A> A[] requireArrayLengthAbove(int min, A[] actual, String varName) {
		assertThat(min >= 0 && min < Integer.MAX_VALUE, () -> "'min' is invalid: " + min);
		requireNonNull(actual, varName);

		if (actual.length <= min) {
			throw new IllegalArgumentException('\'' + varName + "' must have a length above " +
			                                   min + ": " + actual.length);
		}

		return actual;
	}

	public static void requireArrayLengthAbove(int min, Object actual, String varName) {
		assertThat(min >= 0 && min < Integer.MAX_VALUE, () -> "'min' is invalid: " + min);
		requireArray(actual, varName);

		if (Array.getLength(actual) <= min) {
			throw new IllegalArgumentException('\'' + varName + "' must have a length above " +
			                                   min + ": " + Array.getLength(actual));
		}
	}

	public static <A> A @Nullable [] requireNullOrArrayLengthAbove(int min, A @Nullable [] actual, String varName) {
		assertThat(min >= 0 && min < Integer.MAX_VALUE, () -> "'min' is invalid: " + min);

		if (actual != null && actual.length <= min) {
			throw new IllegalArgumentException('\'' + varName + "' must be null or have a length above " +
			                                   min + ": " + actual.length);
		}

		return actual;
	}

	public static void requireNullOrArrayLengthAbove(int min, @Nullable Object actual, String varName) {
		assertThat(min >= 0 && min < Integer.MAX_VALUE, () -> "'min' is invalid: " + min);
		requireNullOrArray(actual, varName);

		if (actual != null && Array.getLength(actual) <= min) {
			throw new IllegalArgumentException('\'' + varName + "' must be null or have a length above " +
			                                   min + ": " + Array.getLength(actual));
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
		requireArray(actual, varName);

		if (Array.getLength(actual) >= max) {
			throw new IllegalArgumentException('\'' + varName + "' must have a length below " +
			                                   max + ": " + Array.getLength(actual));
		}
	}

	public static <A> A @Nullable [] requireNullOrArrayLengthBelow(int max, A @Nullable [] actual, String varName) {
		assertThat(max > 0, () -> "'max' is invalid: " + max);

		if (actual != null && actual.length >= max) {
			throw new IllegalArgumentException('\'' + varName + "' must be null or have a length below " +
			                                   max + ": " + actual.length);
		}

		return actual;
	}

	public static void requireNullOrArrayLengthBelow(int max, @Nullable Object actual, String varName) {
		assertThat(max > 0, () -> "'max' is invalid: " + max);
		requireNullOrArray(actual, varName);

		if (actual != null && Array.getLength(actual) >= max) {
			throw new IllegalArgumentException('\'' + varName + "' must be null or have a length below " +
			                                   max + ": " + Array.getLength(actual));
		}
	}

	public static <A> A[] requireArrayLengthBetween(int min, int max, A[] actual, String varName) {
		assertThat(min >= 0, () -> "'min' is invalid: " + min);
		assertThat(min <= max, () -> "Range is invalid: " + min + "..." + max);
		requireNonNull(actual, varName);

		if (actual.length < min || actual.length > max) {
			if (min == max) {
				throw new IllegalArgumentException('\'' + varName + "' must have a length of exactly " +
				                                   min + ": " + actual.length);
			} else {
				throw new IllegalArgumentException('\'' + varName + "' must have a length between" +
				                                   min + " and " + max + ": " + actual.length);
			}
		}

		return actual;
	}

	public static void requireArrayLengthBetween(int min, int max, Object actual, String varName) {
		assertThat(min >= 0, () -> "'min' is invalid: " + min);
		assertThat(min <= max, () -> "Range is invalid: " + min + "..." + max);
		requireNonNull(actual, varName);
		requireArray(actual, varName);

		if (Array.getLength(actual) < min || Array.getLength(actual) > max) {
			if (min == max) {
				throw new IllegalArgumentException('\'' + varName + "' must have a length of exactly " +
				                                   min + ": " + Array.getLength(actual));
			} else {
				throw new IllegalArgumentException('\'' + varName + "' must have a length between" +
				                                   min + " and " + max + ": " + Array.getLength(actual));
			}
		}
	}

	public static <A> A @Nullable [] requireNullOrArrayLengthBetween(
			int min, int max, A @Nullable [] actual, String varName) {
		assertThat(min >= 0, () -> "'min' is invalid: " + min);
		assertThat(min <= max, () -> "Range is invalid: " + min + "..." + max);

		if (actual != null && (actual.length < min || actual.length > max)) {
			if (min == max) {
				throw new IllegalArgumentException('\'' + varName + "' must be null or have a length of exactly " +
				                                   min + ": " + actual.length);
			} else {
				throw new IllegalArgumentException('\'' + varName + "' must be null or have a length between" +
				                                   min + " and " + max + ": " + actual.length);
			}
		}

		return actual;
	}

	public static void requireNullOrArrayLengthBetween(int min, int max, @Nullable Object actual, String varName) {
		assertThat(min >= 0, () -> "'min' is invalid: " + min);
		assertThat(min <= max, () -> "Range is invalid: " + min + "..." + max);

		if (actual == null) {
			return;
		}

		requireArray(actual, varName);

		if (Array.getLength(actual) < min || Array.getLength(actual) > max) {
			if (min == max) {
				throw new IllegalArgumentException('\'' + varName + "' must be null or have a length of exactly " +
				                                   min + ": " + Array.getLength(actual));
			} else {
				throw new IllegalArgumentException('\'' + varName + "' must be null or have a length between" +
				                                   min + " and " + max + ": " + Array.getLength(actual));
			}
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
		requireArray(array1, varName1);
		requireArray(array2, varName2);

		if (Array.getLength(array1) != Array.getLength(array2)) {
			throw new IllegalArgumentException('\'' + varName1 + "' and '" + varName2 + "' must have equal lengths: " +
			                                   Array.getLength(array1) + " != " + Array.getLength(array2));
		}
	}
}
