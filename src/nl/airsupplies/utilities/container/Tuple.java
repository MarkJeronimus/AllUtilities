package nl.airsupplies.utilities.container;

import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * @param <K> Type of first object
 * @param <V> Type of second object
 * @author Mark Jeronimus
 */
// Created 2016-04-25
public class Tuple<K extends Comparable<K>, V extends Comparable<V>> implements Comparable<Tuple<K, V>> {
	private final K first;
	private final V second;

	public Tuple(K first, V value) {
		this.first = requireNonNull(first, "first");
		second     = requireNonNull(value, "second");
	}

	public K first() {
		return first;
	}

	public V second() {
		return second;
	}

	@Override
	public int compareTo(Tuple<K, V> o) {
		int i = first.compareTo(o.first);
		if (i != 0) {
			return i;
		}

		return second.compareTo(o.second);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (o instanceof Tuple<?, ?>) {
			Tuple<?, ?> other = (Tuple<?, ?>)o;
			return first.equals(other.first) && second.equals(other.second);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		int hash = 0x811C9DC5;
		hash ^= first.hashCode();
		hash *= 0x01000193;
		hash ^= second.hashCode();
		hash *= 0x01000193;
		return hash;
	}
}
