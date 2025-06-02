package nl.airsupplies.utilities.container;

/**
 * A tuple of an integer and double.
 * <p>
 * This class supports sorting, and elements are sorted by the integer first, then by the double.
 *
 * @author Mark Jeronimus
 */
// Created 2013-12-04
public class TupleIntDouble implements Comparable<TupleIntDouble> {
	/**
	 * The freely-modifiable integer value.
	 */
	public int    i;
	/**
	 * The freely-modifiable double value.
	 */
	public double d;

	public TupleIntDouble() {
		i = 0;
		d = 0;
	}

	public TupleIntDouble(int i, double d) {
		this.i = i;
		this.d = d;
	}

	/**
	 * This is the preferred way to clone a .
	 */
	public TupleIntDouble(TupleIntDouble other) {
		i = other.i;
		d = other.d;
	}

	public void set(int i, double d) {
		this.i = i;
		this.d = d;
	}

	public void set(TupleIntDouble other) {
		i = other.i;
		d = other.d;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + '[' + i + ", " + d + ']';
	}

	@Override
	public int compareTo(TupleIntDouble other) {
		if (i != other.i) {
			return i > other.i ? 1 : -1;
		}
		if (d != other.d) {
			return d > other.d ? 1 : -1;
		}
		return 0;
	}
}
