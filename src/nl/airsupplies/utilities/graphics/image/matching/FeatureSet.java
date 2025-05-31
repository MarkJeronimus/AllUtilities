package nl.airsupplies.utilities.graphics.image.matching;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import nl.airsupplies.utilities.container.Vector2i;

/**
 * @author Mark Jeronimus
 */
// Created 2013-03-02
public class FeatureSet extends ArrayList<MatchFeature> {
	public static void mark(MatchFeature first, MatchFeature second) {
		first.marks.add(second);
		second.marks.add(first);
	}

	public Vector2i getBestMatch() {
		int length = size();

		double   bestMatch = Double.MAX_VALUE;
		Vector2i best      = new Vector2i(-1, -1);
		for (int i = length - 1; i > 0; i--) {
			MatchFeature first = get(i);
			for (int j = i - 1; j >= 0; j--) {
				double match = first.match(get(j));
				if (bestMatch > match) {
					bestMatch = match;
					best.set(i, j);
				}
			}
		}

		return best;
	}

	public Vector2i getBestMatchUnMarked() {
		int length = size();

		double   bestMatch = Double.MAX_VALUE;
		Vector2i best      = new Vector2i(-1, -1);
		for (int i = length - 1; i > 0; i--) {
			MatchFeature          first = get(i);
			HashSet<MatchFeature> marks = first.marks;

			for (int j = i - 1; j >= 0; j--) {
				MatchFeature second = get(j);

				if (marks.contains(second)) {
					continue;
				}

				double match = first.match(second);
				if (bestMatch > match) {
					bestMatch = match;
					best.set(i, j);
				}
			}
		}

		return best;
	}

	public void matchAndSort(MatchFeature matchItem) {
		int length = size();

		double[] matches = new double[length];

		for (int i = length - 1; i >= 0; i--) {
			MatchFeature other = get(i);

			matches[i] = other.match(matchItem);
		}

		// Store scores.
		for (int i = length - 1; i >= 0; i--) {
			get(i).setSortValue(matches[i]);
		}

		matchItem.setSortValue(-Double.MIN_VALUE);

		Collections.sort(this);
	}

	public void matchAndSortUnmarked(MatchFeature matchItem) {
		int length = size();

		HashSet<MatchFeature> marks = matchItem.marks;

		double[] matches = new double[length];

		for (int i = length - 1; i >= 0; i--) {
			MatchFeature other = get(i);

			matches[i] = other.match(matchItem);
			if (marks.contains(other)) {
				matches[i] = Double.MAX_VALUE;
			}
		}

		// Store scores.
		for (int i = length - 1; i >= 0; i--) {
			get(i).setSortValue(matches[i]);
		}

		matchItem.setSortValue(-Double.MIN_VALUE);

		Collections.sort(this);
	}
}
