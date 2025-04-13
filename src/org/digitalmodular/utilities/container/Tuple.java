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

package org.digitalmodular.utilities.container;

import static org.digitalmodular.utilities.ValidatorUtilities.requireNonNull;

/**
 * @author Mark Jeronimus
 */
// Created 2016-04-25
public class Tuple<K extends Comparable<K>, V extends Comparable<V>> implements Comparable<Tuple<K, V>> {
	private final K first;
	private final V second;

	public Tuple(K first, V value) {
		this.first = requireNonNull(first, "first");
		second = requireNonNull(value, "second");
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
		}
		if (!(o instanceof Tuple)) {
			return false;
		}

		Tuple<?, ?> other = (Tuple<?, ?>)o;
		return first.equals(other.first) && second.equals(other.second);

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
