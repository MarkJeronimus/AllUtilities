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

package org.digitalmodular.utilities.collection;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Mark Jeronimus
 */
// Created 2008-01-23
public class ArrayMap<K, V> extends AbstractMap<K, V> implements Cloneable, Serializable {
	ArrayList<K> keys   = new ArrayList<>();
	ArrayList<V> values = new ArrayList<>();

	@Override
	public void clear() {
		keys.clear();
		values.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return keys.contains(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return values.contains(value);
	}

	public V getValue(int i) {
		return values.get(i);
	}

	public K getKey(int i) {
		return keys.get(i);
	}

	@Override
	public V get(Object key) {
		return values.get(keys.indexOf(key));
	}

	public V remove(int i) {
		keys.remove(i);
		return values.remove(i);
	}

	@Override
	public boolean isEmpty() {
		return keys.isEmpty();
	}

	@Override
	public V put(K key, V value) {
		int i = keys.indexOf(key);
		if (i != -1) {
			return values.set(i, value);
		}

		keys.add(key);
		values.add(value);
		return null;
	}

	@Override
	public V remove(Object key) {
		int i = keys.indexOf(key);
		if (i != -1) {
			keys.remove(i);
			return values.remove(i);
		}

		return null;
	}

	@Override
	public int size() {
		return keys.size();
	}

	@Override
	public Collection<V> values() {
		return values;
	}

	public Collection<K> keys() {
		return keys;
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return new AbstractSet<Entry<K, V>>() {
			@Override
			public Iterator<Entry<K, V>> iterator() {
				return new Iterator<Entry<K, V>>() {
					int i = 0;

					@Override
					public boolean hasNext() {
						return i < keys.size();
					}

					@Override
					public Entry<K, V> next() {
						SimpleImmutableEntry<K, V> kvSimpleImmutableEntry = new SimpleImmutableEntry<>(keys.get(i),
						                                                                               values.get(i));
						i++;
						return kvSimpleImmutableEntry;
					}

					@Override
					public void remove() {
						--i;
						keys.remove(i);
						values.remove(i);
					}
				};
			}

			@Override
			public int size() {
				return keys.size();
			}
		};
	}
}
