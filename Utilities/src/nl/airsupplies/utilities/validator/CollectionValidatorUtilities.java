package nl.airsupplies.utilities.validator;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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
public final class CollectionValidatorUtilities {
	public static <V, C extends Collection<V>> C requireNotEmpty(C actual, String varName) {
		requireNonNull(actual, varName);

		if (actual.isEmpty()) {
			throw new IllegalArgumentException('\'' + varName + "' must not be empty");
		}

		return actual;
	}

	public static <K, V, M extends Map<K, V>> M requireNotEmpty(M actual, String varName) {
		requireNonNull(actual, varName);

		if (actual.isEmpty()) {
			throw new IllegalArgumentException('\'' + varName + "' must not be empty");
		}

		return actual;
	}

	public static <V, C extends Collection<V>> @Nullable C requireNullOrNotEmpty(@Nullable C actual, String varName) {
		if (actual != null && actual.isEmpty()) {
			throw new IllegalArgumentException('\'' + varName + "' must be null or not be empty");
		}

		return actual;
	}

	public static <K, V, M extends Map<K, V>> @Nullable M requireNullOrNotEmpty(@Nullable M actual, String varName) {
		if (actual != null && actual.isEmpty()) {
			throw new IllegalArgumentException('\'' + varName + "' must not be empty");
		}

		return actual;
	}

	public static <V, C extends Collection<V>> C requireSizeExactly(int size, C actual, String varName) {
		assertThat(size >= 0, () -> "'size' is invalid: " + size);
		requireNonNull(actual, varName);

		if (actual.size() != size) {
			throw new IllegalArgumentException('\'' + varName + "' must have a size of exactly " +
			                                   size + ": " + actual.size());
		}

		return actual;
	}

	public static <K, V, M extends Map<K, V>> M requireSizeExactly(int size, M actual, String varName) {
		assertThat(size >= 0, () -> "'size' is invalid: " + size);
		requireNonNull(actual, varName);

		if (actual.size() != size) {
			throw new IllegalArgumentException('\'' + varName + "' must have a size of exactly " +
			                                   size + ": " + actual.size());
		}

		return actual;
	}

	public static <V, C extends Collection<V>> @Nullable C requireNullOrSizeExactly(
			int size, @Nullable C actual, String varName) {
		assertThat(size >= 0, () -> "'size' is invalid: " + size);

		if (actual != null && actual.size() != size) {
			throw new IllegalArgumentException('\'' + varName + "' must have a size of exactly " +
			                                   size + ": " + actual.size());
		}

		return actual;
	}

	public static <K, V, M extends Map<K, V>> @Nullable M requireNullOrSizeExactly(
			int size, @Nullable M actual, String varName) {
		assertThat(size >= 0, () -> "'size' is invalid: " + size);

		if (actual != null && actual.size() != size) {
			throw new IllegalArgumentException('\'' + varName + "' must have a size of exactly " +
			                                   size + ": " + actual.size());
		}

		return actual;
	}

	public static <V, C extends Collection<V>> C requireSizeAtLeast(int min, C actual, String varName) {
		assertThat(min >= 0, () -> "'min' is invalid: " + min);
		requireNonNull(actual, varName);

		if (actual.size() < min) {
			throw new IllegalArgumentException('\'' + varName + "' must have a size of at least " +
			                                   min + ": " + actual.size());
		}

		return actual;
	}

	public static <K, V, M extends Map<K, V>> M requireSizeAtLeast(int min, M actual, String varName) {
		assertThat(min >= 0, () -> "'min' is invalid: " + min);
		requireNonNull(actual, varName);

		if (actual.size() < min) {
			throw new IllegalArgumentException('\'' + varName + "' must have a size of at least " +
			                                   min + ": " + actual.size());
		}

		return actual;
	}

	public static <V, C extends Collection<V>> @Nullable C requireNullOrSizeAtLeast(
			int min, @Nullable C actual, String varName) {
		assertThat(min >= 0, () -> "'min' is invalid: " + min);

		if (actual != null && actual.size() < min) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or have a size of at least " +
			                                   min + ": " + actual.size());
		}

		return actual;
	}

	public static <K, V, M extends Map<K, V>> @Nullable M requireNullOrSizeAtLeast(
			int min, @Nullable M actual, String varName) {
		assertThat(min >= 0, () -> "'min' is invalid: " + min);

		if (actual != null && actual.size() < min) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or have a size of at least " +
			                                   min + ": " + actual.size());
		}

		return actual;
	}

	public static <V, C extends Collection<V>> C requireSizeAtMost(int max, C actual, String varName) {
		assertThat(max >= 0, () -> "'max' is invalid: " + max);
		requireNonNull(actual, varName);

		if (actual.size() > max) {
			throw new IllegalArgumentException('\'' + varName + "' must have a size of at most " +
			                                   max + ": " + actual.size());
		}

		return actual;
	}

	public static <K, V, M extends Map<K, V>> M requireSizeAtMost(int max, M actual, String varName) {
		assertThat(max >= 0, () -> "'max' is invalid: " + max);
		requireNonNull(actual, varName);

		if (actual.size() > max) {
			throw new IllegalArgumentException('\'' + varName + "' must have a size of at most " +
			                                   max + ": " + actual.size());
		}

		return actual;
	}

	public static <V, C extends Collection<V>> @Nullable C requireNullOrSizeAtMost(
			int max, @Nullable C actual, String varName) {
		assertThat(max >= 0, () -> "'max' is invalid: " + max);

		if (actual != null && actual.size() > max) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or have a size of at most " +
			                                   max + ": " + actual.size());
		}

		return actual;
	}

	public static <K, V, M extends Map<K, V>> @Nullable M requireNullOrSizeAtMost(
			int max, @Nullable M actual, String varName) {
		assertThat(max >= 0, () -> "'max' is invalid: " + max);

		if (actual != null && actual.size() > max) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or have a size of at most " +
			                                   max + ": " + actual.size());
		}

		return actual;
	}

	public static <V, C extends Collection<V>> C requireSizeAbove(int min, C actual, String varName) {
		assertThat(min >= 0 && min < Integer.MAX_VALUE, () -> "'min' is invalid: " + min);
		requireNonNull(actual, varName);

		if (actual.size() <= min) {
			if (min == 0) {
				throw new IllegalArgumentException('\'' + varName + "' must must not be empty");
			} else {
				throw new IllegalArgumentException('\'' + varName + "' must have a size above " +
				                                   min + ": " + actual.size());
			}
		}

		return actual;
	}

	public static <K, V, M extends Map<K, V>> M requireSizeAbove(int min, M actual, String varName) {
		assertThat(min >= 0 && min < Integer.MAX_VALUE, () -> "'min' is invalid: " + min);
		requireNonNull(actual, varName);

		if (actual.size() <= min) {
			if (min == 0) {
				throw new IllegalArgumentException('\'' + varName + "' must must not be empty");
			} else {
				throw new IllegalArgumentException('\'' + varName + "' must have a size above " +
				                                   min + ": " + actual.size());
			}
		}

		return actual;
	}

	public static <V, C extends Collection<V>> @Nullable C requireNullOrSizeAbove(
			int min, @Nullable C actual, String varName) {
		assertThat(min >= 0 && min < Integer.MAX_VALUE, () -> "'min' is invalid: " + min);

		if (actual != null && actual.size() <= min) {
			if (min == 0) {
				throw new IllegalArgumentException('\'' + varName + "' must must not be empty");
			} else {
				throw new IllegalArgumentException('\'' + varName + "' must either be null or have a size above " +
				                                   min + ": " + actual.size());
			}
		}

		return actual;
	}

	public static <K, V, M extends Map<K, V>> @Nullable M requireNullOrSizeAbove(
			int min, @Nullable M actual, String varName) {
		assertThat(min >= 0 && min < Integer.MAX_VALUE, () -> "'min' is invalid: " + min);

		if (actual != null && actual.size() <= min) {
			if (min == 0) {
				throw new IllegalArgumentException('\'' + varName + "' must must not be empty");
			} else {
				throw new IllegalArgumentException('\'' + varName + "' must either be null or have a size above " +
				                                   min + ": " + actual.size());
			}
		}

		return actual;
	}

	public static <V, C extends Collection<V>> C requireSizeBelow(int max, C actual, String varName) {
		assertThat(max > 0, () -> "'max' is invalid: " + max);
		requireNonNull(actual, varName);

		if (actual.size() >= max) {
			throw new IllegalArgumentException('\'' + varName + "' must have a size below " +
			                                   max + ": " + actual.size());
		}

		return actual;
	}

	public static <K, V, M extends Map<K, V>> M requireSizeBelow(int max, M actual, String varName) {
		assertThat(max > 0, () -> "'max' is invalid: " + max);
		requireNonNull(actual, varName);

		if (actual.size() >= max) {
			throw new IllegalArgumentException('\'' + varName + "' must have a size below " +
			                                   max + ": " + actual.size());
		}

		return actual;
	}

	public static <V, C extends Collection<V>> @Nullable C requireNullOrSizeBelow(
			int max, @Nullable C actual, String varName) {
		assertThat(max > 0, () -> "'max' is invalid: " + max);
		if (actual != null && actual.size() >= max) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or have a size below " +
			                                   max + ": " + actual.size());
		}

		return actual;
	}

	public static <K, V, M extends Map<K, V>> @Nullable M requireNullOrSizeBelow(
			int max, @Nullable M actual, String varName) {
		assertThat(max > 0, () -> "'max' is invalid: " + max);
		if (actual != null && actual.size() >= max) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or have a size below " +
			                                   max + ": " + actual.size());
		}

		return actual;
	}

	public static <V, C extends Collection<V>> C requireSizeBetween(int min, int max, C actual, String varName) {
		assertThat(min >= 0, () -> "'min' is invalid: " + min);
		assertThat(max > min, () -> "Range is invalid: " + min + "..." + max);
		requireNonNull(actual, varName);

		if (actual.size() < min || actual.size() > max) {
			throw new IllegalArgumentException('\'' + varName + "' must have a size between [" +
			                                   min + ", " + max + "]: " + actual.size());
		}

		return actual;
	}

	public static <K, V, M extends Map<K, V>> M requireSizeBetween(int min, int max, M actual, String varName) {
		assertThat(min >= 0, () -> "'min' is invalid: " + min);
		assertThat(max > min, () -> "Range is invalid: " + min + "..." + max);
		requireNonNull(actual, varName);

		if (actual.size() < min || actual.size() > max) {
			throw new IllegalArgumentException('\'' + varName + "' must have a size between [" +
			                                   min + ", " + max + "]: " + actual.size());
		}

		return actual;
	}

	public static <V, C extends Collection<V>> @Nullable C requireNullOrSizeBetween(
			int min, int max, @Nullable C actual, String varName) {
		assertThat(min >= 0, () -> "'min' is invalid: " + min);
		assertThat(max > min, () -> "Range is invalid: " + min + "..." + max);

		if (actual != null && (actual.size() < min || actual.size() > max)) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or have a size between [" +
			                                   min + ", " + max + "]: " + actual.size());
		}

		return actual;
	}

	public static <K, V, M extends Map<K, V>> @Nullable M requireNullOrSizeBetween(
			int min, int max, @Nullable M actual, String varName) {
		assertThat(min >= 0, () -> "'min' is invalid: " + min);
		assertThat(max > min, () -> "Range is invalid: " + min + "..." + max);

		if (actual != null && (actual.size() < min || actual.size() > max)) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or have a size between [" +
			                                   min + ", " + max + "]: " + actual.size());
		}

		return actual;
	}

	public static void requireSizesMatch(
			Collection<?> string1, Collection<?> string2, String varName1, String varName2) {
		requireNonNull(string1, varName1);
		requireNonNull(string2, varName2);

		if (string1.size() != string2.size()) {
			throw new IllegalArgumentException('\'' + varName1 + "' and '" + varName2 + "' must have equal sizes: " +
			                                   string1.size() + " != " + string2.size());
		}
	}

	public static void requireSizesMatch(Map<?, ?> string1, Map<?, ?> string2, String varName1, String varName2) {
		requireNonNull(string1, varName1);
		requireNonNull(string2, varName2);

		if (string1.size() != string2.size()) {
			throw new IllegalArgumentException('\'' + varName1 + "' and '" + varName2 + "' must have equal sizes: " +
			                                   string1.size() + " != " + string2.size());
		}
	}

	public static <V, I extends Iterable<V>> I requireValuesNonNull(I actual, String varName) {
		requireNonNull(actual, varName);

		for (V value : actual) {
			if (value == null) {
				throw new NullPointerException('\'' + varName + "' contains a null value");
			}
		}

		return actual;
	}

	public static <V, L extends List<V>> L requireValuesNonNull(L actual, String varName) {
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.size(); i++) {
			if (actual.get(i) == null) {
				throw new NullPointerException('\'' + varName + '[' + i + "]' can't be null");
			}
		}

		return actual;
	}

	public static <K, V, M extends Map<K, V>> M requireValuesNonNull(M actual, String varName) {
		requireNonNull(actual, varName);

		for (V value : actual.values()) {
			if (value == null) {
				throw new NullPointerException('\'' + varName + "' contains a null value");
			}
		}

		return actual;
	}

	public static <V, I extends Iterable<V>> @Nullable I requireNullOrValuesNonNull(
			@Nullable I actual, String varName) {
		if (actual == null) {
			return null;
		}

		for (V value : actual) {
			if (value == null) {
				throw new NullPointerException('\'' + varName + "' contains a null value");
			}
		}

		return actual;
	}

	public static <V, L extends List<V>> @Nullable L requireNullOrValuesNonNull(@Nullable L actual, String varName) {
		if (actual == null) {
			return null;
		}

		for (int i = 0; i < actual.size(); i++) {
			if (actual.get(i) == null) {
				throw new NullPointerException('\'' + varName + '[' + i + "]' can't be null");
			}
		}

		return actual;
	}

	public static <K, V, M extends Map<K, V>> @Nullable M requireNullOrValuesNonNull(
			@Nullable M actual, String varName) {
		if (actual == null) {
			return null;
		}

		for (V value : actual.values()) {
			if (value == null) {
				throw new NullPointerException('\'' + varName + "' contains a null value");
			}
		}

		return actual;
	}

	public static <V, I extends Iterable<?>> I requireValuesOfType(Class<V> type, I actual, String varName) {
		requireNonNull(type, "type");
		requireNonNull(actual, varName);

		for (Object value : actual) {
			if (value.getClass() != type) {
				throw new IllegalArgumentException(
						'\'' + varName + "' contains a value that's not of type " + type.getName());
			}
		}

		return actual;
	}

	public static <V, L extends List<?>> L requireValuesOfType(Class<V> type, L actual, String varName) {
		requireNonNull(type, "type");
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.size(); i++) {
			Object value = actual.get(i);
			if (value.getClass() != type) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be of type " + type.getName());
			}
		}

		return actual;
	}

	public static <K, V, M extends Map<?, ?>> M requireKeysOfType(Class<K> type, M actual, String varName) {
		requireNonNull(type, "type");
		requireNonNull(actual, varName);

		for (Object value : actual.keySet()) {
			if (value.getClass() != type) {
				throw new IllegalArgumentException(
						'\'' + varName + "' contains a key that's not of type " + type.getName());
			}
		}

		return actual;
	}

	public static <K, V, M extends Map<?, ?>> M requireValuesOfType(Class<V> type, M actual, String varName) {
		requireNonNull(type, "type");
		requireNonNull(actual, varName);

		for (Object value : actual.values()) {
			if (value.getClass() != type) {
				throw new IllegalArgumentException(
						'\'' + varName + "' contains a value that's not of type " + type.getName());
			}
		}

		return actual;
	}

	public static <V, I extends Iterable<?>> I requireValuesInstanceOf(Class<V> type, I actual, String varName) {
		requireNonNull(type, "type");
		requireNonNull(actual, varName);

		for (Object value : actual) {
			if (!type.isInstance(value)) {
				throw new IllegalArgumentException(
						'\'' + varName + "' contains a value that's not an instance of " + type.getName());
			}
		}

		return actual;
	}

	public static <V, L extends List<?>> L requireValuesInstanceOf(Class<V> type, L actual, String varName) {
		requireNonNull(type, "type");
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.size(); i++) {
			Object value = actual.get(i);
			if (!type.isInstance(value)) {
				throw new IllegalArgumentException(
						'\'' + varName + '[' + i + "]' must be an instance of " + type.getName());
			}
		}

		return actual;
	}

	public static <K, V, M extends Map<?, ?>> M requireKeysInstanceOf(Class<K> type, M actual, String varName) {
		requireNonNull(type, "type");
		requireNonNull(actual, varName);

		for (Object value : actual.keySet()) {
			if (!type.isInstance(value)) {
				throw new IllegalArgumentException(
						'\'' + varName + "' contains a key that's not an instance of " + type.getName());
			}
		}

		return actual;
	}

	public static <K, V, M extends Map<?, ?>> M requireValuesInstanceOf(Class<V> type, M actual, String varName) {
		requireNonNull(type, "type");
		requireNonNull(actual, varName);

		for (Object value : actual.values()) {
			if (!type.isInstance(value)) {
				throw new IllegalArgumentException(
						'\'' + varName + "' contains a value that's not an instance of " + type.getName());
			}
		}

		return actual;
	}

	public static <I extends Iterable<String>> I requireStringsNotEmpty(I actual, String varName) {
		requireValuesNonNull(actual, varName);

		for (String value : actual) {
			if (value.isEmpty()) {
				throw new IllegalArgumentException('\'' + varName + "' contains an empty string");
			}
		}

		return actual;
	}

	public static <L extends List<String>> L requireStringsNotEmpty(L actual, String varName) {
		requireValuesNonNull(actual, varName);

		for (int i = 0; i < actual.size(); i++) {
			if (actual.get(i).isEmpty()) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' can't be an empty string");
			}
		}

		return actual;
	}

	public static <K, M extends Map<K, String>> M requireStringsNotEmpty(M actual, String varName) {
		requireValuesNonNull(actual, varName);

		for (String value : actual.values()) {
			if (value.isEmpty()) {
				throw new IllegalArgumentException('\'' + varName + "' contains an empty string");
			}
		}

		return actual;
	}

	public static <I extends Iterable<String>> @Nullable I requireNullOrStringsNotEmpty(
			@Nullable I actual, String varName) {
		if (actual == null) {
			return null;
		}

		requireValuesNonNull(actual, varName);

		for (String value : actual) {
			if (value.isEmpty()) {
				throw new IllegalArgumentException('\'' + varName + "' contains an empty string");
			}
		}

		return actual;
	}

	public static <L extends List<String>> @Nullable L requireNullOrStringsNotEmpty(
			@Nullable L actual, String varName) {
		if (actual == null) {
			return null;
		}

		requireValuesNonNull(actual, varName);

		for (int i = 0; i < actual.size(); i++) {
			if (actual.get(i).isEmpty()) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' can't be an empty string");
			}
		}

		return actual;
	}

	public static <K, M extends Map<K, String>> @Nullable M requireNullOrStringsNotEmpty(
			@Nullable M actual, String varName) {
		if (actual == null) {
			return null;
		}

		requireValuesNonNull(actual, varName);

		for (String value : actual.values()) {
			if (value.isEmpty()) {
				throw new IllegalArgumentException('\'' + varName + "' contains an empty string");
			}
		}

		return actual;
	}
}
