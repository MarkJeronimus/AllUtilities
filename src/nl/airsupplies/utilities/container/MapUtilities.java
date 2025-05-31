package nl.airsupplies.utilities.container;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Mark Jeronimus
 */
// Created 2025-02-28
public class MapUtilities {
	public static <K, V> Map<V, List<K>> transpose(Map<K, V> templateOccurrences) {
		Map<V, List<K>> transposed = new HashMap<>(templateOccurrences.size());

		for (Map.Entry<K, V> entry : templateOccurrences.entrySet()) {
			transposed.computeIfAbsent(entry.getValue(), ignored -> new ArrayList<>(16)).add(entry.getKey());
		}

		return transposed;
	}

	public static <K, V extends Comparable<V>> TreeMap<V, List<K>> transposeSorted(Map<K, V> templateOccurrences) {
		TreeMap<V, List<K>> transposed = new TreeMap<>();

		for (Map.Entry<K, V> entry : templateOccurrences.entrySet()) {
			transposed.computeIfAbsent(entry.getValue(), ignored -> new ArrayList<>(16)).add(entry.getKey());
		}

		return transposed;
	}

	public static <K, V> TreeMap<V, List<K>> transposeSorted(Map<K, V> templateOccurrences, Comparator<V> comparator) {
		TreeMap<V, List<K>> transposed = new TreeMap<>(comparator);

		for (Map.Entry<K, V> entry : templateOccurrences.entrySet()) {
			transposed.computeIfAbsent(entry.getValue(), ignored -> new ArrayList<>(16)).add(entry.getKey());
		}

		return transposed;
	}
}
