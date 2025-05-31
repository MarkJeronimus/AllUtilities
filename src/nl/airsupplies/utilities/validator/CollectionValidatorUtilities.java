package nl.airsupplies.utilities.validator;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jetbrains.annotations.Nullable;

import nl.airsupplies.utilities.annotation.UtilityClass;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.assertThat;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * @author Mark Jeronimus
 */
// Created 2016-12-21
@SuppressWarnings("OverlyComplexClass")
@UtilityClass
public final class CollectionValidatorUtilities {
	public static <V, C extends Collection<V>> C requireNotEmpty(C actual, String varName) {
		requireNonNull(actual, varName);

		if (actual.isEmpty()) {
			throw new IllegalArgumentException('\'' + varName + "' must not me empty");
		}

		return actual;
	}

	public static <K, V, M extends Map<K, V>> M requireNotEmpty(M actual, String varName) {
		requireNonNull(actual, varName);

		if (actual.isEmpty()) {
			throw new IllegalArgumentException('\'' + varName + "' must not me empty");
		}

		return actual;
	}

	public static @Nullable <V, C extends Collection<V>> C requireNullOrNotEmpty(@Nullable C actual, String varName) {
		if (actual != null && actual.isEmpty()) {
			throw new IllegalArgumentException('\'' + varName + "' must not me empty");
		}

		return actual;
	}

	public static @Nullable <K, V, M extends Map<K, V>> M requireNullOrNotEmpty(@Nullable M actual, String varName) {
		if (actual != null && actual.isEmpty()) {
			throw new IllegalArgumentException('\'' + varName + "' must not me empty");
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

	public static @Nullable <V, C extends Collection<V>> C requireNullOrSizeExactly(
			int size, @Nullable C actual, String varName) {
		assertThat(size >= 0, () -> "'size' is invalid: " + size);

		if (actual != null && actual.size() != size) {
			throw new IllegalArgumentException('\'' + varName + "' must have a size of exactly " +
			                                   size + ": " + actual.size());
		}

		return actual;
	}

	public static @Nullable <K, V, M extends Map<K, V>> M requireNullOrSizeExactly(
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

	public static @Nullable <V, C extends Collection<V>> C requireNullOrSizeAtLeast(
			int min, @Nullable C actual, String varName) {
		assertThat(min >= 0, () -> "'min' is invalid: " + min);

		if (actual != null && actual.size() < min) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or have a size of at least " +
			                                   min + ": " + actual.size());
		}

		return actual;
	}

	public static @Nullable <K, V, M extends Map<K, V>> M requireNullOrSizeAtLeast(
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

	public static @Nullable <V, C extends Collection<V>> C requireNullOrSizeAtMost(
			int max, @Nullable C actual, String varName) {
		assertThat(max >= 0, () -> "'max' is invalid: " + max);

		if (actual != null && actual.size() > max) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or have a size of at most " +
			                                   max + ": " + actual.size());
		}

		return actual;
	}

	public static @Nullable <K, V, M extends Map<K, V>> M requireNullOrSizeAtMost(
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
				throw new IllegalArgumentException('\'' + varName + "' must must not me empty");
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
				throw new IllegalArgumentException('\'' + varName + "' must must not me empty");
			} else {
				throw new IllegalArgumentException('\'' + varName + "' must have a size above " +
				                                   min + ": " + actual.size());
			}
		}

		return actual;
	}

	public static @Nullable <V, C extends Collection<V>> C requireNullOrSizeAbove(
			int min, @Nullable C actual, String varName) {
		assertThat(min >= 0 && min < Integer.MAX_VALUE, () -> "'min' is invalid: " + min);

		if (actual != null && actual.size() <= min) {
			if (min == 0) {
				throw new IllegalArgumentException('\'' + varName + "' must must not me empty");
			} else {
				throw new IllegalArgumentException('\'' + varName + "' must either be null or have a size above " +
				                                   min + ": " + actual.size());
			}
		}

		return actual;
	}

	public static @Nullable <K, V, M extends Map<K, V>> M requireNullOrSizeAbove(
			int min, @Nullable M actual, String varName) {
		assertThat(min >= 0 && min < Integer.MAX_VALUE, () -> "'min' is invalid: " + min);

		if (actual != null && actual.size() <= min) {
			if (min == 0) {
				throw new IllegalArgumentException('\'' + varName + "' must must not me empty");
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

	public static @Nullable <V, C extends Collection<V>> C requireNullOrSizeBelow(
			int max, @Nullable C actual, String varName) {
		assertThat(max > 0, () -> "'max' is invalid: " + max);
		if (actual != null && actual.size() >= max) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or have a size below " +
			                                   max + ": " + actual.size());
		}

		return actual;
	}

	public static @Nullable <K, V, M extends Map<K, V>> M requireNullOrSizeBelow(
			int max, @Nullable M actual, String varName) {
		assertThat(max > 0, () -> "'max' is invalid: " + max);
		if (actual != null && actual.size() >= max) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or have a size below " +
			                                   max + ": " + actual.size());
		}

		return actual;
	}

	public static <V, C extends Collection<V>> C requireSizeBetween(int min, int max, C actual, String varName) {
		requireNonNull(actual, varName);

		if (actual.size() < min || actual.size() > max) {
			throw new IllegalArgumentException('\'' + varName + "' must have a size between [" +
			                                   min + ", " + max + "]: " + actual.size());
		}

		return actual;
	}

	public static <K, V, M extends Map<K, V>> M requireSizeBetween(int min, int max, M actual, String varName) {
		requireNonNull(actual, varName);

		if (actual.size() < min || actual.size() > max) {
			throw new IllegalArgumentException('\'' + varName + "' must have a size between [" +
			                                   min + ", " + max + "]: " + actual.size());
		}

		return actual;
	}

	public static @Nullable <V, C extends Collection<V>> C requireNullOrSizeBetween(
			int min, int max, @Nullable C actual, String varName) {
		if (actual != null && (actual.size() < min || actual.size() > max)) {
			throw new IllegalArgumentException('\'' + varName + "' must either be null or have a size between [" +
			                                   min + ", " + max + "]: " + actual.size());
		}

		return actual;
	}

	public static @Nullable <K, V, M extends Map<K, V>> M requireNullOrSizeBetween(
			int min, int max, @Nullable M actual, String varName) {
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
				throw new IllegalArgumentException('\'' + varName + "' contains a null value");
			}
		}

		return actual;
	}

	public static <V, L extends List<V>> L requireValuesNonNull(L actual, String varName) {
		requireNonNull(actual, "actual");

		for (int i = 0; i < actual.size(); i++) {
			if (actual.get(i) == null) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' can't be null");
			}
		}

		return actual;
	}

	public static <K, V, M extends Map<K, V>> M requireValuesNonNull(M actual, String varName) {
		requireNonNull(actual, varName);

		for (V value : actual.values()) {
			if (value == null) {
				throw new IllegalArgumentException('\'' + varName + "' contains a null value");
			}
		}

		return actual;
	}

	public static @Nullable <V, I extends Iterable<V>> I requireNullOrValuesNonNull(
			@Nullable I actual, String varName) {
		if (actual == null) {
			return null;
		}

		for (V value : actual) {
			if (value == null) {
				throw new IllegalArgumentException('\'' + varName + "' contains a null value");
			}
		}

		return actual;
	}

	public static @Nullable <V, L extends List<V>> L requireNullOrValuesNonNull(@Nullable L actual, String varName) {
		if (actual == null) {
			return null;
		}

		for (int i = 0; i < actual.size(); i++) {
			if (actual.get(i) == null) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' can't be null");
			}
		}

		return actual;
	}

	public static @Nullable <K, V, M extends Map<K, V>> M requireNullOrValuesNonNull(
			@Nullable M actual, String varName) {
		if (actual == null) {
			return null;
		}

		for (V value : actual.values()) {
			if (value == null) {
				throw new IllegalArgumentException('\'' + varName + "' contains a null value");
			}
		}

		return actual;
	}

	public static <I extends Iterable<String>> I requireNonEmptyStrings(I actual, String varName) {
		requireNonNull(actual, varName);
		requireValuesNonNull(actual, varName);

		for (String value : actual) {
			if (value.isEmpty()) {
				throw new IllegalArgumentException('\'' + varName + "' contains an empty string");
			}
		}

		return actual;
	}

	public static <L extends List<String>> L requireNonEmptyStrings(L actual, String varName) {
		requireNonNull(actual, varName);
		requireValuesNonNull(actual, varName);

		for (int i = 0; i < actual.size(); i++) {
			if (actual.get(i).isEmpty()) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' can't be an empty string");
			}
		}

		return actual;
	}

	public static <K, M extends Map<K, String>> M requireNonEmptyStrings(M actual, String varName) {
		requireNonNull(actual, varName);
		requireValuesNonNull(actual, varName);

		for (String value : actual.values()) {
			if (value.isEmpty()) {
				throw new IllegalArgumentException('\'' + varName + "' contains an empty string");
			}
		}

		return actual;
	}

	public static <I extends Iterable<String>> @Nullable I requireNullOrNonEmptyStrings(
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

	public static <L extends List<String>> @Nullable L requireNullOrNonEmptyStrings(
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

	public static <K, M extends Map<K, String>> @Nullable M requireNullOrNonEmptyStrings(
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

	public static <I extends Iterable<V>, V> I requireValueType(Class<V> type, I actual, String varName) {
		requireNonNull(type, "type");
		requireNonNull(actual, varName);

		for (Object value : actual) {
			if (!type.isInstance(value)) {
				throw new IllegalArgumentException('\'' + varName + "' contains a value not of type " + type.getName());
			}
		}

		return actual;
	}

	public static <L extends List<V>, V> L requireValueType(Class<V> type, L actual, String varName) {
		requireNonNull(type, "type");
		requireNonNull(actual, varName);

		for (int i = 0; i < actual.size(); i++) {
			Object value = actual.get(i);
			if (!type.isInstance(value)) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be of type " + type.getName());
			}
		}

		return actual;
	}

	public static <K, V, M extends Map<K, V>> M requireKeyType(Class<K> type, M actual, String varName) {
		requireNonNull(type, "type");
		requireNonNull(actual, varName);

		for (Object value : actual.keySet()) {
			if (!type.isInstance(value)) {
				throw new IllegalArgumentException('\'' + varName + "' contains a key not of type " + type.getName());
			}
		}

		return actual;
	}

	public static <K, V, M extends Map<K, V>> M requireValueType(Class<V> type, M actual, String varName) {
		requireNonNull(type, "type");
		requireNonNull(actual, varName);

		for (Object value : actual.values()) {
			if (!type.isInstance(value)) {
				throw new IllegalArgumentException('\'' + varName + "' contains a value not of type " + type.getName());
			}
		}

		return actual;
	}

	public static <K, V, M extends Map<K, V>> M requireEntryType(
			Class<V> keyType, Class<V> valueType, M actual, String varName) {
		requireNonNull(keyType, "keyType");
		requireNonNull(valueType, "valueType");
		requireNonNull(actual, varName);

		for (Entry<?, ?> entry : ((Map<?, ?>)actual).entrySet()) {
			if (!keyType.isInstance(entry.getKey())) {
				throw new IllegalArgumentException('\'' + varName + "' contains a key not of type " +
				                                   keyType.getName());
			} else if (!valueType.isInstance(entry.getValue())) {
				throw new IllegalArgumentException('\'' + varName + "' contains a value not of type " +
				                                   valueType.getName());
			}
		}

		return actual;
	}

	public static <I extends Iterable<V>, V> @Nullable I requireNullOrValueType(
			Class<V> type, @Nullable I actual, String varName) {
		requireNonNull(type, "type");

		if (actual == null) {
			return null;
		}

		for (Object value : actual) {
			if (!type.isInstance(value)) {
				throw new IllegalArgumentException('\'' + varName + "' contains a value not of type " + type.getName());
			}
		}

		return actual;
	}

	public static <L extends List<V>, V> @Nullable L requireNullOrValueType(
			Class<V> type, @Nullable L actual, String varName) {
		requireNonNull(type, "type");

		if (actual == null) {
			return null;
		}

		for (int i = 0; i < actual.size(); i++) {
			Object value = actual.get(i);
			if (!type.isInstance(value)) {
				throw new IllegalArgumentException('\'' + varName + '[' + i + "]' must be of type " + type.getName());
			}
		}

		return actual;
	}

	public static <K, V, M extends Map<K, V>> @Nullable M requireNullOrKeyType(
			Class<V> type, @Nullable M actual, String varName) {
		requireNonNull(type, "type");

		if (actual == null) {
			return null;
		}

		for (Object value : actual.keySet()) {
			if (!type.isInstance(value)) {
				throw new IllegalArgumentException('\'' + varName + "' contains a key not of type " + type.getName());
			}
		}

		return actual;
	}

	public static <K, V, M extends Map<K, V>> @Nullable M requireNullOrValueType(
			Class<V> type, @Nullable M actual, String varName) {
		requireNonNull(type, "type");

		if (actual == null) {
			return null;
		}

		for (Object value : actual.values()) {
			if (!type.isInstance(value)) {
				throw new IllegalArgumentException('\'' + varName + "' contains a value not of type " + type.getName());
			}
		}

		return actual;
	}

	public static <K, V, M extends Map<K, V>> @Nullable M requireNullOrEntryType(
			Class<V> keyType, Class<V> valueType, @Nullable M actual, String varName) {
		requireNonNull(keyType, "keyType");
		requireNonNull(valueType, "valueType");

		if (actual == null) {
			return null;
		}

		for (Entry<?, ?> entry : ((Map<?, ?>)actual).entrySet()) {
			if (!keyType.isInstance(entry.getKey())) {
				throw new IllegalArgumentException('\'' + varName + "' contains a key not of type " +
				                                   keyType.getName());
			} else if (!valueType.isInstance(entry.getValue())) {
				throw new IllegalArgumentException('\'' + varName + "' contains a value not of type " +
				                                   valueType.getName());
			}
		}

		return actual;
	}
}
