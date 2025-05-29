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
import java.util.concurrent.ThreadLocalRandom;

import org.jetbrains.annotations.Nullable;

import org.digitalmodular.utilities.NumberUtilities;
import static org.digitalmodular.utilities.ValidatorUtilities.requireAtLeast;
import static org.digitalmodular.utilities.ValidatorUtilities.requireNotDegenerate;

/**
 * A one-dimensional, immutable, variant of {@link Rectangle2D.Double}, with the added constraint that negative size
 * is not allowed.
 *
 * @author Mark Jeronimus
 */
// Created 2014-04-14
public class Range implements Comparable<Range> {
	public static final Range ZERO = new Range(0.0, 0.0);
	public static final Range UNIT = new Range(0.0, 1.0);

	private final double begin;
	private final double end;

	/**
	 * Creates a new range with specified endpoints. It's recommended to use the factory method
	 * {@link #of(double, double)} instead.
	 *
	 * @throws IllegalArgumentException when begin &gt; end. If thrown, split it
	 *                                  up in empty constructor and <tt>set</tt> method and handle swapping
	 *                                  properly.
	 */
	public Range(double begin, double end) {
		requireNotDegenerate(begin, "begin");
		requireNotDegenerate(end, "end");
		requireAtLeast(begin, end, "end");
		requireNotDegenerate(end - begin, "end - begin");

		this.begin = begin;
		this.end   = end;
	}

	/**
	 * Creates a new range with specified endpoints. For some special values, a
	 * pre-made instance will be returned.
	 *
	 * @throws IllegalArgumentException when begin &gt; end. If thrown, split it
	 *                                  up in empty constructor and <tt>set</tt> method and handle swapping
	 *                                  properly.
	 */
	public static Range of(double begin, double end) {
		if (begin == 0.0) {
			if (end == 0.0) {
				return ZERO;
			} else if (end == 1.0) {
				return UNIT;
			}
		}

		return new Range(begin, end);
	}

	/**
	 * Determines whether the {@code Range} is empty. When the
	 * {@code Range} is empty, it's endpoints overlap.
	 */
	public boolean isEmpty() {
		return end == begin;
	}

	public Range set(double newBegin, double newEnd) {
		if (newBegin == begin && newEnd == end) {
			return this;
		}

		return of(newBegin, newEnd);
	}

	/**
	 * Returns the begin position of the range.
	 */
	public double getBegin() {
		return begin;
	}

	public Range setBegin(double newBegin) {
		requireNotDegenerate(newBegin, "newBegin");

		return of(newBegin, end);
	}

	/**
	 * Returns the end position of the range.
	 */
	public double getEnd() {
		return end;
	}

	public Range setEnd(double newEnd) {
		requireNotDegenerate(newEnd, "newEnd");

		return of(begin, newEnd);
	}

	/**
	 * Returns the span of this range.
	 * <p><!-- Watch out with the no-break-spaces inside the next @code block -->
	 * This returns the same as {@link #getEnd()}{@code  ﻿- }{@link #getBegin()}.
	 */
	public double getSpan() {
		return end - begin;
	}

	/**
	 * Returns the midpoint of this range.
	 */
	public double getCenter() {
		return NumberUtilities.lerp(begin, end, 0.5f);
	}

	/**
	 * Tests whether the range contains the given value.
	 * <p>
	 * Both ends are inclusive.
	 */
	public boolean contains(double value) {
		return begin <= value && value <= end;
	}

	/**
	 * Tests whether the range contains the given range entirely.
	 * <p>
	 * Both ends are inclusive.
	 */
	public boolean contains(Range other) {
		return begin <= other.begin && other.end <= end;
	}

	/**
	 * Tests whether the range contains any part of the given range.
	 * <p>
	 * Both ends are inclusive.
	 */
	public boolean intersects(Range other) {
		double begin = Math.max(this.begin, other.begin);
		double end   = Math.min(this.end, other.end);
		return begin <= end;
	}

	/**
	 * Add a point to the range.
	 * <p>
	 * This extends it to the smallest range that encompasses both the original range and the provided value.
	 * <p>
	 * When the range is "empty", adding a point does <strong>not</strong> make a range that only includes the
	 * specified point. Instead, it encompasses the original 'location' (equal to both begin and end) and the
	 * specified point.
	 */
	public Range add(double value) {
		requireNotDegenerate(value, "value");

		if (value > end) {
			return new Range(begin, value);
		} else if (value < begin) {
			return new Range(value, end);
		} else {
			return this;
		}
	}

	/**
	 * Translates the range by adding a specified amount to both the begin and end.
	 * <p>
	 * This almost does the same as: <tt>range.set(range.begin() + amount, range.end() + amount)</tt>, except for some
	 * additional integer-limit checking. If one of the endpoints ends up wrapping around the integer limit, an
	 * exception is thrown.
	 *
	 * @throws IllegalArgumentException if one of the sides would exceed &plusmn;Infinity
	 */
	public Range drag(double amount) {
		requireNotDegenerate(amount, "amount");

		double begin = this.begin + amount;
		double end   = this.end + amount;

		if (end == Double.POSITIVE_INFINITY) {
			throw new IllegalArgumentException(
					"This drag would cause end to be infinity");
		} else if (begin < Double.NEGATIVE_INFINITY) {
			throw new IllegalArgumentException(
					"This drag would cause begin to be negative infinity");
		}

		if (begin == this.begin && end == this.end) {
			return this;
		}

		return of(begin, end);
	}

	/**
	 * Returns the smallest range that contains both given ranges, even if the given ranges don't intersect.
	 * <p>
	 * Null-safe. If the parameter is {@code null}, it returns {@code this}.
	 */
	public Range union(Range other) {
		if (equals(other)) {
			return this;
		}

		double begin = Math.min(this.begin, other.begin);
		double end   = Math.max(this.end, other.end);

		if (begin == this.begin && end == this.end) {
			return this;
		} else if (begin == other.begin && end == other.end) {
			return other;
		}

		return of(begin, end);
	}

	/**
	 * Returns the largest range that's within both given ranges.
	 * <p>
	 * If the ranges don't intersect, an empty range of {@code (other.begin, other.begin)} is returned.
	 * <p>
	 * Null-safe. If the parameter is {@code null}, it returns {@code this}.
	 */
	public Range intersect(Range other) {
		if (equals(other)) {
			return this;
		}

		double begin = Math.max(this.begin, other.begin);
		double end   = Math.min(this.end, other.end);
		if (begin > end) {
			return of(other.begin, other.begin);
		} else if (begin == this.begin && end == this.end) {
			return this;
		} else if (begin == other.begin && end == other.end) {
			return other;
		}

		return of(begin, end);
	}

	public double lerp(double position) {
		return NumberUtilities.lerp(begin, end, position);
	}

	public double unLerp(double value) {
		return NumberUtilities.unLerp(begin, end, value);
	}

	public double random() {
		return NumberUtilities.lerp(begin, end, ThreadLocalRandom.current().nextDouble());
	}

	@Override
	public int compareTo(Range other) {
		int i = Double.compare(begin, other.begin);
		if (i != 0) {
			return i;
		}

		return Double.compare(end, other.end);
	}

	@Override
	public boolean equals(@Nullable Object o) {
		if (this == o) {
			return true;
		} else if (!(o instanceof Range)) {
			return false;
		}

		Range other = (Range)o;
		return Double.doubleToLongBits(begin) == Double.doubleToLongBits(other.begin) &&
		       Double.doubleToLongBits(end) == Double.doubleToLongBits(other.end);
	}

	@Override
	public int hashCode() {
		int hashCode = 0x811C9DC5;
		hashCode = 0x01000193 * (hashCode ^ Double.hashCode(begin));
		hashCode = 0x01000193 * (hashCode ^ Double.hashCode(end));
		return hashCode;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + '[' + "begin=" + begin + ", end=" + end + ", size=" + (end - begin) + ']';
	}
}
