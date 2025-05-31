package nl.airsupplies.utilities.container;

import nl.airsupplies.utilities.NumberUtilities;

/**
 * A mutable <u>Range</u> with <u>2</u> degrees of freedom (1 dimension) and <u>i</u>nteger precision. It defines a
 * begin and end value where <tt>begin &lt; end</tt> is always true. If a situation arises where this condition would be
 * violated, the values will be swapped, and the user is always informed of this.
 *
 * @author Mark Jeronimus
 */
// Created 2014-04-14
public class RangeInt {
	private int begin;
	private int end;

	/**
	 * Creates a new range of [0,0].
	 */
	public RangeInt() {
		begin = 0;
		end   = 0;
	}

	/**
	 * Creates a new range with specified endpoints.
	 *
	 * @throws IllegalArgumentException when begin &gt; end. If thrown, split it up in empty constructor and
	 *                                  <tt>set</tt> method and handle swapping properly.
	 */
	public RangeInt(int begin, int end) throws IllegalArgumentException {
		this.begin = begin;
		this.end   = end;
		if (normalize()) {
			throw new IllegalArgumentException("Begin > End not possible in constructor.");
		}
	}

	/**
	 * Sets the begin and end of the range to new positions.
	 * <p>
	 * If the values violate the <tt>begin &lt; end</tt> requirement, they will be swapped.
	 *
	 * @return true if the begin and end were swapped in order to meet the criteria.
	 * @throws IllegalArgumentException if on of the specified values is NaN.
	 */
	public boolean set(int begin, int end) {
		this.begin = begin;
		this.end   = end;

		return normalize();
	}

	/**
	 * Sets the range of this range to that of another range.
	 */
	public void set(RangeInt other) {
		begin = other.begin;
		end   = other.end;
	}

	/**
	 * Returns true if the range is zero and the range is effectively nonexistent.
	 */
	public boolean isEmpty() {
		return end == begin;
	}

	/**
	 * Copy constructor.
	 */
	public RangeInt(RangeInt other) {
		begin = other.begin;
		end   = other.end;
	}

	/**
	 * Returns the begin position of the range.
	 */
	public int begin() {
		return begin;
	}

	/**
	 * Sets the current begin of the range to a new position.
	 * <p>
	 * This does the same as: <tt>range.set(begin, range.end())</tt>. If the value violates the <tt>begin &lt; end</tt>
	 * requirement, they will be swapped and the new end of the range will be at the specified position.
	 *
	 * @return true if the begin and end were swapped in order to meet the criteria.
	 * @throws IllegalArgumentException if the specified value is NaN.
	 */
	public boolean setBegin(int begin) {
		this.begin = begin;

		return normalize();
	}

	/**
	 * Returns the end position of the range.
	 */
	public int end() {
		return end;
	}

	/**
	 * Sets the current end of the range to a new position.
	 * <p>
	 * This does the same as: <tt>range.set(range.begin(), end)</tt>. If the value violates the <tt>begin &lt; end</tt>
	 * requirement, they will be swapped and the new begin of the range will be at the specified position.
	 *
	 * @return true if the begin and end were swapped in order to meet the criteria.
	 * @throws IllegalArgumentException if the specified value is NaN.
	 */
	public boolean setEnd(int end) {
		this.end = end;

		return normalize();
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
	public void add(int value) {
		if (value > end) {
			end = value;
		} else if (value < begin) {
			begin = value;
		}
	}

	public boolean contains(int value) {
		return value >= begin && value <= end;
	}

	/**
	 * Returns the span of the range.
	 * <p>
	 * This does the same as: <tt>(range.end() - range.begin())</tt>.
	 */
	public int getSpan() {
		return end - begin;
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
	public void drag(int amount) {
		if (amount > 0 && end + amount < end) {
			throw new IllegalArgumentException(
					"This drag would cause end to integer-overflow");
		} else if (amount < 0 && begin + amount > begin) {
			throw new IllegalArgumentException(
					"This drag would cause begin to integer-underflow");
		}

		begin += amount;
		end += amount;
	}

	/**
	 * Returns the smallest range that contains both given ranges, even if the given ranges don't intersect.
	 * <p>
	 * Null-safe. If the parameter is {@code null}, it returns {@code this}.
	 */
	public void union(RangeInt other) {
		if (equals(other)) {
			return;
		}

		begin = Math.min(begin, other.begin);
		end   = Math.max(end, other.end);
	}

	/**
	 * Returns the largest range that's within both given ranges.
	 * <p>
	 * If the ranges don't intersect, an empty range of {@code max(begin, other.begin)} is returned.
	 * <p>
	 * Null-safe. If the parameter is {@code null}, it returns {@code this}.
	 */
	public void intersect(RangeInt other) {
		if (equals(other)) {
			return;
		}

		begin = Math.max(begin, other.begin);
		end   = Math.min(end, other.end);

		end = Math.max(end, begin);
	}

	/**
	 * Clamp a given value to fall within this range.
	 *
	 * @param value the value to clamp.
	 */
	public int clamp(int value) {
		return Math.min(end, Math.max(begin, value));
	}

	public double lerp(double position) {
		return NumberUtilities.lerp(begin, end, position);
	}

	public double unLerp(double value) {
		return NumberUtilities.unLerp(begin, end, value);
	}

	/**
	 * Limits the begin and end values of this range to the specified min and max.
	 *
	 * @param min the lower bound. if begin is lower than min, it will be set to min
	 * @param max the upper bound. if end is lower than max, it will be set to max
	 */
	public void limit(int min, int max) {
		if (min > max) {
			throw new IllegalArgumentException("The min should be smaller or equal to the max");
		}

		if (begin < min) {
			begin = min;
		}
		if (end > max) {
			end = max;
		}
	}

	/**
	 *
	 */
	private boolean normalize() {
		if (begin > end) {
			int temp = begin;
			begin = end;
			end   = temp;
			return true;
		}

		return false;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		RangeInt other = (RangeInt)o;
		return begin() == other.begin() &&
		       end() == other.end();
	}

	@Override
	public int hashCode() {
		int hashCode = 0x811C9DC5;
		hashCode = 0x01000193 * (hashCode ^ begin());
		hashCode = 0x01000193 * (hashCode ^ end());
		return hashCode;
	}

	@Override
	public String toString() {
		return "[" + begin + ", " + end + ']';
	}
}
