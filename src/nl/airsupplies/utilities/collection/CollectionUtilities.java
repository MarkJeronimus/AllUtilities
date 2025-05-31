package nl.airsupplies.utilities.collection;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nl.airsupplies.utilities.annotation.UtilityClass;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * @author Mark Jeronimus
 */
// Created 2022-09-07
@UtilityClass
public final class CollectionUtilities {
	/**
	 * Wraps the given {@code collection} in an unmodifiable collection iff it is not already unmodifiable.
	 */
	public static <T> Collection<T> asUnmodifiableSet(Collection<T> collection) {
		requireNonNull(collection, "collection");

		//noinspection ObjectEquality // Comparing identity, not equality
		if (collection == Collections.EMPTY_LIST) {
			return collection;
		}

		String name = collection.getClass().getName();
		if (name.equals("java.util.Collections$UnmodifiableCollection") ||
		    name.startsWith("java.util.ImmutableCollections")) {
			return collection;
		}

		return Collections.unmodifiableCollection(collection);
	}

	/**
	 * Wraps the given {@code list} in an unmodifiable list iff it is not already unmodifiable.
	 */
	public static <T> List<T> asUnmodifiableSet(List<T> list) {
		requireNonNull(list, "list");

		//noinspection ObjectEquality // Comparing identity, not equality
		if (list == Collections.EMPTY_LIST) {
			return list;
		}

		String name = list.getClass().getName();
		if (name.equals("java.util.Collections$UnmodifiableList") ||
		    name.equals("java.util.Collections$UnmodifiableRandomAccessList") ||
		    name.equals("java.util.Collections$SingletonList") ||
		    name.startsWith("java.util.ImmutableCollections")) {
			return list;
		}

		return Collections.unmodifiableList(list);
	}

	/**
	 * Wraps the given {@code set} in an unmodifiable set iff it is not already unmodifiable.
	 */
	public static <T> Set<T> asUnmodifiableSet(Set<T> set) {
		requireNonNull(set, "set");

		//noinspection ObjectEquality // Comparing identity, not equality
		if (set == Collections.EMPTY_SET) {
			return set;
		}

		String name = set.getClass().getName();
		if (name.equals("java.util.Collections$UnmodifiableSet") ||
		    name.equals("java.util.Collections$UnmodifiableNavigableSet") ||
		    name.equals("java.util.Collections$UnmodifiableSortedSet") ||
		    name.equals("java.util.Collections$SingletonSet") ||
		    name.startsWith("java.util.ImmutableCollections")) {
			return set;
		}

		return Collections.unmodifiableSet(set);
	}

	/**
	 * Wraps the given {@code map} in an unmodifiable map iff it is not already unmodifiable.
	 */
	public static <K, V> Map<K, V> asUnmodifiableSet(Map<K, V> map) {
		requireNonNull(map, "map");

		//noinspection ObjectEquality // Comparing identity, not equality
		if (map == Collections.EMPTY_MAP) {
			return map;
		}

		String name = map.getClass().getName();
		if (name.equals("java.util.Collections$UnmodifiableMap") ||
		    name.equals("java.util.Collections$UnmodifiableNavigableMap") ||
		    name.equals("java.util.Collections$UnmodifiableSortedMap") ||
		    name.equals("java.util.Collections$SingletonMap") ||
		    name.startsWith("java.util.ImmutableCollections")) {
			return map;
		}

		return Collections.unmodifiableMap(map);
	}
}
