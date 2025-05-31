package nl.airsupplies.utilities.container;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.BiConsumer;

import net.jcip.annotations.NotThreadSafe;
import org.jetbrains.annotations.Nullable;

import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireAtLeast;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @author Mark Jeronimus
 */
// Created 2022-12-23
@NotThreadSafe
public class CacheMap<K, V> implements Map<K, V> {
	private final int       capacity;
	private final Map<K, V> data;

	// Fast-path access for most recent entry
	private @Nullable K headKey   = null;
	private @Nullable V headValue = null;

	private final Set<BiConsumer<K, V>> elementUpdatedListeners = new CopyOnWriteArraySet<>();
	private final Set<BiConsumer<K, V>> elementRemovedListeners = new CopyOnWriteArraySet<>();

	public CacheMap(int capacity) {
		this.capacity = requireAtLeast(1, capacity, "capacity");
		data          = new LinkedHashMap<>(capacity);
	}

	@Override
	public int size() {
		return data.size();
	}

	@Override
	public boolean isEmpty() {
		return data.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		requireNonNull(key, "key");

		return data.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		requireNonNull(value, "value");

		return data.containsValue(value);
	}

	@Override
	public @Nullable V put(K key, V value) {
		requireNonNull(key, "key");
		requireNonNull(value, "value");

		ensureEmptySpace(key);

		V previousValue = data.put(key, value);
		headKey   = key;
		headValue = value;

		fireElementUpdated(key, value);

		return previousValue;
	}

	@Override
	public @Nullable V remove(Object key) {
		@Nullable V removedEntry = data.remove(key);

		if (removedEntry != null) {
			Entry<K, V> headEntry = null;

			// Because LinkedHashMap doesn't expose it's head, we're forced to use this VERY(!) inefficient method.
			// IMPROVE: Make custom Entry subclass with a 'prev' field, mimicking LinkedHashMap internals.
			for (Entry<K, V> entry : data.entrySet()) {
				headEntry = entry;
			}

			if (headEntry == null) {
				headKey   = null;
				headValue = null;
			} else {
				headKey   = headEntry.getKey();
				headValue = headEntry.getValue();
			}
		}

		return removedEntry;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		requireNonNull(m, "m");

		for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public void clear() {
		while (!data.isEmpty()) {
			removeTailEntry();
		}
	}

	@Override
	public Set<K> keySet() {
		return data.keySet();
	}

	@Override
	public Collection<V> values() {
		return data.values();
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return data.entrySet();
	}

	@Override
	public @Nullable V get(Object key) {
		requireNonNull(key, "key");

		if (key.equals(headKey)) {
			return headValue;
		}

		return data.get(key);
	}

	public K getHeadKey() {
		return headKey;
	}

	public V getHeadValue() {
		return headValue;
	}

	public Entry<K, V> getHeadElement() {
		return new AbstractMap.SimpleEntry<>(headKey, headValue);
	}

	public K getTailKey() {
		if (data.isEmpty()) {
			throw new IllegalStateException("Empty map");
		}

		Iterator<K> iterator = data.keySet().iterator();
		return iterator.next();
	}

	public V getTailValue() {
		if (data.isEmpty()) {
			throw new IllegalStateException("Empty map");
		}

		Iterator<V> iterator = data.values().iterator();
		return iterator.next();
	}

	public Entry<K, V> getTailEntry() {
		if (data.isEmpty()) {
			throw new IllegalStateException("Empty map");
		}

		Iterator<Entry<K, V>> iterator = data.entrySet().iterator();
		return iterator.next();
	}

	public K removeTailKey() {
		return removeTailEntry().getKey();
	}

	public V removeTailValue() {
		return removeTailEntry().getValue();
	}

	public Entry<K, V> removeTailEntry() {
		if (data.isEmpty()) {
			throw new IllegalStateException("Empty map");
		}

		// Because LinkedHashMap doesn't expose it's tail, we're forced to use this inefficient method.
		Iterator<Entry<K, V>> iterator = data.entrySet().iterator();
		Entry<K, V>           entry    = iterator.next();
		iterator.remove();

		if (data.isEmpty()) {
			headKey   = null;
			headValue = null;
		}

		fireElementRemoved(entry.getKey(), entry.getValue());

		return entry;
	}

	private void ensureEmptySpace(K keyToDelete) {
		requireNonNull(keyToDelete, "keyToDelete");

		@Nullable V oldTile = data.remove(keyToDelete);
		if (oldTile != null) {
			return; // It was removed. There's space now.
		}

		if (data.size() != capacity) {
			return;
		}

		// Remove before firing listeners.
		removeTailValue();
	}

	public void removeElementUpdatedListener(BiConsumer<K, V> listener) {
		elementUpdatedListeners.remove(requireNonNull(listener, "listener"));
	}

	public void addElementUpdatedListener(BiConsumer<K, V> listener) {
		elementUpdatedListeners.add(requireNonNull(listener, "listener"));
	}

	@SuppressWarnings("ProhibitedExceptionThrown")
	private void fireElementUpdated(K key, V value) {
		@Nullable RuntimeException thrown = null;

		for (BiConsumer<K, V> listener : elementUpdatedListeners) {
			try {
				listener.accept(key, value);
			} catch (RuntimeException ex) {
				if (thrown == null) {
					thrown = ex;
				} else {
					thrown.addSuppressed(ex);
				}
			}
		}

		if (thrown != null) {
			throw thrown;
		}
	}

	public void removeElementRemovedListener(BiConsumer<K, V> listener) {
		elementRemovedListeners.remove(requireNonNull(listener, "listener"));
	}

	public void addElementRemovedListener(BiConsumer<K, V> listener) {
		elementRemovedListeners.add(requireNonNull(listener, "listener"));
	}

	@SuppressWarnings("ProhibitedExceptionThrown")
	private void fireElementRemoved(K key, V value) {
		@Nullable RuntimeException thrown = null;

		for (BiConsumer<K, V> listener : elementRemovedListeners) {
			try {
				listener.accept(key, value);
			} catch (RuntimeException ex) {
				if (thrown == null) {
					thrown = ex;
				} else {
					thrown.addSuppressed(ex);
				}
			}
		}

		if (thrown != null) {
			throw thrown;
		}
	}
}
