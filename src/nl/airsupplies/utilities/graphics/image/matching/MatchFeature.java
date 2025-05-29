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

package nl.airsupplies.utilities.graphics.image.matching;

import java.awt.image.BufferedImage;
import java.util.HashSet;

/**
 * @author Mark Jeronimus
 */
// Created 2013-03-02
public abstract class MatchFeature implements Comparable<MatchFeature> {
	protected HashSet<MatchFeature> marks     = new HashSet<>();
	private   double                sortValue = 0;

	public void setSortValue(double sortValue) {
		this.sortValue = sortValue;
	}

	public double getSortValue() {
		return sortValue;
	}

	@Override
	public int compareTo(MatchFeature other) {
		if (sortValue > other.sortValue) {
			return 1;
		}
		if (sortValue < other.sortValue) {
			return -1;
		}
		return 0;
	}

	public abstract String getName();

	public abstract BufferedImage getImage();

	public abstract double match(MatchFeature other);

	public abstract int getNumParameters();

	public abstract String getParameterName(int index);

	public abstract Class<?> getParameterType(int index);

	public abstract Object getParameter(int index);

	public abstract void setParameter(int index, Object value);
}
