package nl.airsupplies.utilities.validator;

import org.jetbrains.annotations.Nullable;

import nl.airsupplies.utilities.annotation.UtilityClass;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.assertThat;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * @author Mark Jeronimus
 */
// Created 2016-12-21
@UtilityClass
public final class StringValidatorUtilities {
	public static String requireStringNotEmpty(String actual, String varName) {
		requireNonNull(actual, varName);

		if (actual.isEmpty()) {
			throw new IllegalArgumentException('\'' + varName + "' must not me empty");
		}

		return actual;
	}

	public static @Nullable String requireNullOrStringNotEmpty(@Nullable String actual, String varName) {
		if (actual != null && actual.isEmpty()) {
			throw new IllegalArgumentException('\'' + varName + "' must not me empty");
		}

		return actual;
	}

	public static String requireStringLengthExactly(int length, String actual, String varName) {
		assertThat(length >= 0, () -> "'length' is invalid: " + length);
		requireNonNull(actual, varName);

		if (actual.length() != length) {
			throw new IllegalArgumentException('\'' + varName + "' must have a length of exactly " +
			                                   length + ": " + actual.length());
		}

		return actual;
	}

	public static @Nullable String requireNullOrStringLengthExactly(
			int length, @Nullable String actual, String varName) {
		assertThat(length >= 0, () -> "'length' is invalid: " + length);

		if (actual != null && actual.length() != length) {
			throw new IllegalArgumentException('\'' + varName + "' must have a length of exactly " +
			                                   length + ": " + actual.length());
		}

		return actual;
	}

	public static String requireStringLengthAtLeast(int min, String actual, String varName) {
		assertThat(min >= 0, () -> "'min' is invalid: " + min);
		requireNonNull(actual, varName);

		if (actual.length() < min) {
			throw new IllegalArgumentException('\'' + varName + "' must have a length of at least " +
			                                   min + ": " + actual.length());
		}

		return actual;
	}

	public static @Nullable String requireNullOrStringLengthAtLeast(int min, @Nullable String actual, String varName) {
		assertThat(min >= 0, () -> "'min' is invalid: " + min);

		if (actual != null && actual.length() < min) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or have a length of at least " +
			                                   min + ": " + actual.length());
		}

		return actual;
	}

	public static String requireStringLengthAtMost(int max, String actual, String varName) {
		assertThat(max >= 0, () -> "'max' is invalid: " + max);
		requireNonNull(actual, varName);

		if (actual.length() > max) {
			throw new IllegalArgumentException('\'' + varName + "' must have a length of at least " +
			                                   max + ": " + actual.length());
		}

		return actual;
	}

	public static @Nullable String requireNullOrStringLengthAtMost(int max, @Nullable String actual, String varName) {
		assertThat(max >= 0, () -> "'max' is invalid: " + max);

		if (actual != null && actual.length() > max) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or have a length of at least " +
			                                   max + ": " + actual.length());
		}

		return actual;
	}

	public static String requireStringLengthAbove(int min, String actual, String varName) {
		assertThat(min >= 0 && min < Integer.MAX_VALUE, () -> "'min' is invalid: " + min);
		requireNonNull(actual, varName);

		if (actual.length() <= min) {
			throw new IllegalArgumentException('\'' + varName + "' must have a length of at least " +
			                                   min + ": " + actual.length());
		}

		return actual;
	}

	public static @Nullable String requireNullOrStringLengthAbove(int min, @Nullable String actual, String varName) {
		assertThat(min >= 0 && min < Integer.MAX_VALUE, () -> "'min' is invalid: " + min);

		if (actual != null && actual.length() <= min) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or have a length of at least " +
			                                   min + ": " + actual.length());
		}

		return actual;
	}

	public static String requireStringLengthBelow(int max, String actual, String varName) {
		assertThat(max > 0, () -> "'max' is invalid: " + max);
		requireNonNull(actual, varName);

		if (actual.length() >= max) {
			throw new IllegalArgumentException('\'' + varName + "' must have a length of at least " +
			                                   max + ": " + actual.length());
		}

		return actual;
	}

	public static @Nullable String requireNullOrStringLengthBelow(int max, @Nullable String actual, String varName) {
		assertThat(max > 0, () -> "'max' is invalid: " + max);

		if (actual != null && actual.length() >= max) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or have a length of at least " +
			                                   max + ": " + actual.length());
		}

		return actual;
	}

	public static String requireStringLengthBetween(int min, int max, String actual, String varName) {
		assertThat(min < Integer.MAX_VALUE, () -> "'min' is invalid: " + min);
		assertThat(max > Integer.MIN_VALUE, () -> "'max' is invalid: " + max);
		requireNonNull(actual, varName);

		if (actual.length() < min || actual.length() > max) {
			throw new IllegalArgumentException('\'' + varName + "' must have a length of between " +
			                                   min + " and " + max + ": " + actual.length());
		}

		return actual;
	}

	public static @Nullable String requireNullOrStringLengthBetween(
			int min, int max, @Nullable String actual, String varName) {
		assertThat(min < Integer.MAX_VALUE, () -> "'min' is invalid: " + min);
		assertThat(max > Integer.MIN_VALUE, () -> "'max' is invalid: " + max);

		if (actual != null && (actual.length() < min || actual.length() > max)) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or have a length of between " +
			                                   min + " and " + max + ": " + actual.length());
		}

		return actual;
	}

	public static void requireStringLengthsMatch(String string1, String string2, String varName1, String varName2) {
		requireNonNull(string1, varName1);
		requireNonNull(string2, varName2);

		if (string1.length() != string2.length()) {
			throw new IllegalArgumentException('\'' + varName1 + "' and '" + varName2 + "' must have equal lengths: " +
			                                   string1.length() + " != " + string2.length());
		}
	}

	public static String requireStringNotContaining(char disallowed, String actual, String varName) {
		requireNonNull(actual, varName);

		if (actual.indexOf(disallowed) >= 0) {
			throw new IllegalArgumentException('\'' + varName + "' may not contain '" + disallowed + "': " + actual);
		}

		return actual;
	}
}
