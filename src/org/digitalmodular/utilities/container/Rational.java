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

import net.jcip.annotations.NotThreadSafe;

import org.digitalmodular.utilities.NumberUtilities;
import static org.digitalmodular.utilities.ValidatorUtilities.requireAtLeast;
import static org.digitalmodular.utilities.ValidatorUtilities.requireNotDegenerate;

/**
 * @author Mark Jeronimus
 */
// Created 2013-08-17
@NotThreadSafe
public class Rational {
	public static final Rational ZERO              = new Rational(0, 1);
	public static final Rational ONE               = new Rational(1, 1);
	public static final Rational POSITIVE_INFINITY = new Rational(1, 0);
	public static final Rational NEGATIVE_INFINITY = new Rational(-1, 0);
	public static final Rational NAN               = new Rational(0, 0);

	private final long numerator;
	private final long denominator;

	public static Rational of(long value) {
		if (value == 0) {
			return ZERO;
		} else if (value == 1) {
			return ONE;
		} else {
			return new Rational(value);
		}
	}

	public static Rational of(long numerator, long denominator) {
		requireAtLeast(0, denominator, "denominator");

		if (numerator == 0) {
			if (denominator == 0) {
				return NAN;
			} else {
				return ZERO;
			}
		} else if (denominator == 0) {
			if (numerator > 0) {
				return POSITIVE_INFINITY;
			} else {
				return NEGATIVE_INFINITY;
			}
		} else if (numerator == denominator) {
			return ONE;
		} else {
			return new Rational(numerator, denominator);
		}
	}

	public Rational(long value) {
		numerator = value;
		denominator = 1;
	}

	public Rational(long numerator, long denominator) {
		requireAtLeast(0, denominator, "denominator");

		if (denominator == 0) {
			this.numerator = Long.signum(numerator);
			this.denominator = 0;
		} else {
			long gcd = NumberUtilities.gcd(Math.abs(numerator), denominator);
			this.numerator = numerator / gcd;
			this.denominator = denominator / gcd;
		}
	}

	public static Rational findFraction(double value) {
		requireNotDegenerate(value, "value");

		long numerator   = 1;
		long denominator = 0;

		long numerator1   = 0;
		long denominator1 = 1;

		double d = value;
		do {
			if (Math.abs(d) > Long.MAX_VALUE) {
				return of(numerator, denominator);
			}

			long integerPart = (long)Math.floor(d);

			long numerator2   = numerator1;
			long denominator2 = denominator1;
			numerator1 = numerator;
			denominator1 = denominator;

			numerator *= integerPart;
			denominator *= integerPart;

			// Detect long overflow
			if (integerPart > 0) {
				if (numerator / integerPart != numerator1 ||
				    denominator / integerPart != denominator1) {
					return of(numerator1, denominator1);
				}
			}

			numerator += numerator2;
			denominator += denominator2;

			// Detect long overflow
			if ((numerator ^ numerator2) < 0 ||
			    (denominator ^ denominator2) < 0) {
				return of(numerator1, denominator1);
			}

			if ((double)numerator / denominator == value) {
				return of(numerator, denominator);
			}

			d = 1.0 / (d - integerPart);
		} while (true);
	}

	public Rational(Rational o) {
		numerator = o.numerator;
		denominator = o.denominator;
	}

	public long getNumerator() {
		return numerator;
	}

	public long getDenominator() {
		return denominator;
	}

	public double doubleValue() {
		return (double)numerator / denominator;
	}

	public Rational add(long v) {
		return of(numerator + v * denominator,
		          denominator);
	}

	public Rational add(Rational o) {
		return of(numerator * o.denominator + o.numerator * denominator,
		          denominator * o.denominator);
	}

	public Rational sub(long v) {
		return of(numerator - (v * denominator),
		          denominator);
	}

	public Rational subR(long v) {
		return of(v * denominator - numerator,
		          denominator);
	}

	public Rational sub(Rational o) {
		return of(numerator * o.denominator - o.numerator * denominator,
		          denominator * o.denominator);
	}

	public Rational mul(long v) {
		return of(numerator * v,
		          denominator);
	}

	public Rational mul(Rational o) {
		return of(numerator * o.numerator,
		          denominator * o.denominator);
	}

	public Rational div(long v) {
		return of(numerator * Long.signum(v),
		          denominator * (v * Long.signum(v)));
	}

	public Rational div(Rational o) {
		return of(numerator * (o.denominator * Long.signum(o.numerator)),
		          denominator * (o.numerator * Long.signum(o.numerator)));
	}

	private Rational recip() {
		// Keep denominator positive
		if (numerator >= 0) {
			return new Rational(denominator, numerator);
		} else {
			return new Rational(-denominator, -numerator);
		}
	}

	public Rational beatPeriod(Rational other) {
		long numProduct = numerator * other.numerator;
		long denProduct = denominator * other.denominator;
		long dx         = numerator * other.denominator - other.numerator * denominator;
		return new Rational(numProduct * denProduct, denProduct * dx);
	}

	/**
	 * Takes the reciprocal if the absolute value is below 1.
	 */
	public Rational rabs() {
		if (Math.abs(numerator) >= denominator) {
			return this;
		} else {
			return recip();
		}
	}

	/**
	 * Returns {@code lhs * this}
	 */
	public double multiplyToDouble(double lhs) {
		return lhs * numerator / denominator;
	}

	/**
	 * Returns {@code lhs / this}.
	 * <p>
	 * This is implemented in a way that is more numerically stable than {@code lhs / this.doubleValue()},
	 * in particular when this rational has an infinite binary expansion (e.g. in the case of {@code 1/10}).
	 * For example, dividing by {@code 1/3}rd is achieved by first multiplying with the denominator {@code 3}
	 * and then dividing with the numerator {@code 1}, instead of dividing by the inaccurate {@code double}
	 * representation which is {@code 0.33333...} truncated to a certain length.
	 */
	public double divideToDouble(double lhs) {
		return lhs * denominator / numerator;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Rational other = (Rational)o;
		return numerator == other.numerator &&
		       denominator == other.denominator;
	}

	@Override
	public int hashCode() {
		int hashCode = 0x811C9DC5;
		hashCode = 0x01000193 * (hashCode ^ Long.hashCode(numerator));
		hashCode = 0x01000193 * (hashCode ^ Long.hashCode(denominator));
		return hashCode;
	}

	@Override
	public String toString() {
		return numerator + "/" + denominator + " (" + doubleValue() + ')';
	}
}
