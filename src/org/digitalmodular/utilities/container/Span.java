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

import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import net.jcip.annotations.Immutable;

import static org.digitalmodular.utilities.ValidatorUtilities.requireAtLeast;
import static org.digitalmodular.utilities.ValidatorUtilities.requireNotDegenerate;

/**
 * A one-dimensional, immutable, variant of {@link Rectangle2D.Double}, with the added constraint that negative size
 * is not allowed.
 *
 * @author Mark Jeronimus
 */
@Immutable
public class Span implements Comparable<Span>, Serializable {
	public static final Span ZERO = new Span(0, 0);
	public static final Span UNIT = new Span(0, 1);

	private final double begin;
	private final double end;

	/**
	 * Creates a new span with specified endpoints. It's recommended to use the factory method
	 * {@link #getInstance(double, double)} instead.
	 *
	 * @throws IllegalArgumentException when begin &gt; end. If thrown, split it
	 *                                  up in empty constructor and <tt>set</tt> method and handle swapping
	 *                                  properly.
	 */
	public Span(double begin, double end) {
		requireNotDegenerate(begin, "begin");
		requireNotDegenerate(end, "end");
		requireAtLeast(begin, end, "end");
		requireNotDegenerate(end - begin, "end - begin");

		this.begin = begin;
		this.end = end;
	}

	/**
	 * Creates a new span with specified endpoints. For some special values, a
	 * pre-made instance will be returned.
	 *
	 * @throws IllegalArgumentException when begin &gt; end. If thrown, split it
	 *                                  up in empty constructor and <tt>set</tt> method and handle swapping
	 *                                  properly.
	 */
	public static Span getInstance(double begin, double end) {
		if (begin == 0 && end == 0) {
			return ZERO;
		} else if (begin == 0 && end == 1) {
			return UNIT;
		}

		return new Span(begin, end);
	}

	/**
	 * Returns the begin position of the span.
	 */
	public double getBegin() {
		return begin;
	}

	/**
	 * Returns the end position of the range.
	 */
	public double getEnd() {
		return end;
	}

	/**
	 * Returns the size of this span.
	 * <p>
	 * This returns the same as {@link #getEnd() getEnd}{@code () - }
	 * {@link #getBegin() getBegin}{@code ()}.
	 */
	public double getSize() {
		return end - begin;
	}

	/**
	 * Returns the midpoint of this span.
	 */
	public double getCenter() {
		// Don't do (begin + end) * 0.5 because of intermediate overflow.
		return begin + (end - begin) * 0.5;
	}

	/**
	 * Determines whether the {@code Span} is empty. When the
	 * {@code Span} is empty, it's endpoints overlap.
	 */
	public boolean isEmpty() {
		return end == begin;
	}

	public Span set(double newBegin, double newEnd) {
		if (newBegin == begin && newEnd == end) {
			return this;
		}

		return getInstance(newBegin, newEnd);
	}

	public Span setBeginTo(double newBegin) {
		requireNotDegenerate(newBegin, "newBegin");

		return getInstance(newBegin, end);
	}

	public Span setEndTo(double newEnd) {
		requireNotDegenerate(newEnd, "newEnd");

		return getInstance(begin, newEnd);
	}

	@Override
	public int compareTo(Span other) {
		int i = Double.compare(begin, other.begin);
		if (i != 0) {
			return i;
		}
		return Double.compare(end, other.end);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}

		Span other = (Span)obj;
		return !(Double.doubleToLongBits(begin) != Double.doubleToLongBits(other.begin) ||
		         Double.doubleToLongBits(end) != Double.doubleToLongBits(other.end));
	}

	@Override
	public int hashCode() {
		int hash = 0x811C9DC5;
		hash ^= Double.hashCode(begin);
		hash *= 0x01000193;
		hash ^= Double.hashCode(end);
		hash *= 0x01000193;
		return hash;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + '[' + "begin=" + begin + ", end=" + end + ", size=" + (end - begin) + ']';
	}
}
