package nl.airsupplies.utilities.collection;

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
