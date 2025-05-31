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
