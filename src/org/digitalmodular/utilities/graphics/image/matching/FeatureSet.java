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

package org.digitalmodular.utilities.graphics.image.matching;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import org.digitalmodular.utilities.container.Vector2i;

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
