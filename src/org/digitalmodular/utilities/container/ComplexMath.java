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

import java.io.Serializable;

import org.digitalmodular.utilities.annotation.UtilityClass;
import static org.digitalmodular.utilities.constant.NumberConstants.LOG10;
import static org.digitalmodular.utilities.constant.NumberConstants.LOG2;
import static org.digitalmodular.utilities.constant.NumberConstants.Q05LOG2;
import static org.digitalmodular.utilities.constant.NumberConstants.Q1LOG10;
import static org.digitalmodular.utilities.constant.NumberConstants.Q1LOG2;
import static org.digitalmodular.utilities.constant.NumberConstants.Q1_6;
import static org.digitalmodular.utilities.constant.NumberConstants.TAU;
import static org.digitalmodular.utilities.constant.NumberConstants.TAU05;

// https://mpmath.org/gallery/
// http://mathworld.wolfram.com/topics/MiscellaneousSpecialFunctions.html

/**
 * @author Mark Jeronimus
 */
// Created 2024-08-02 Split from Complex2d
// Predicate<Complex2d>
// BiPredicate<Complex2d, Double>
// BiPredicate<Complex2d, Complex2d>
// ToDoubleFunction<Complex2D>
// ToDoubleBiFunction<Complex2d, Complex2d>, ToIntBiFunction<Complex2d, Complex2d>
// Function<Complex2d, Complex2d>
// Function<Complex2d, Complex2d> (exponential)
// BiFunction<Complex2d, Complex2d, Complex2d>
// TriFunction<Complex2d, Complex2d, Double, Complex2d>
@SuppressWarnings("OverlyComplexClass")
@UtilityClass
public final class ComplexMath implements Serializable {
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Predicate<Complex2d>
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static boolean isZero(Complex2d value) {
		return value.real == 0 && value.imag == 0;
	}

	public static boolean isPositiveOne(Complex2d value) {
		return value.real == 1 && value.imag == 0;
	}

	public static boolean isPositiveI(Complex2d value) {
		return value.real == 0 && value.imag == 1;
	}

	public static boolean isNegativeOne(Complex2d value) {
		return value.real == -1 && value.imag == 0;
	}

	public static boolean isNegativeI(Complex2d value) {
		return value.real == 0 && value.imag == -1;
	}

	public static boolean isOnlyReal(Complex2d value) {
		return value.imag == 0;
	}

	public static boolean isOnlyImaginary(Complex2d value) {
		return value.real == 0;
	}

	public static boolean isInfinite(Complex2d value) {
		return Double.isInfinite(value.real) || Double.isInfinite(value.imag);
	}

	public static boolean isNaN(Complex2d value) {
		return Double.isNaN(value.real) || Double.isNaN(value.imag);
	}

	public static boolean isDegenerate(Complex2d value) {
		return Double.isInfinite(value.real) || Double.isNaN(value.real) ||
		       Double.isInfinite(value.imag) || Double.isNaN(value.imag);
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// BiPredicate<Complex2d, Double>
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static boolean realLessThan(Complex2d lhs, double rhs) {
		return lhs.real < rhs;
	}

	public static boolean realLessThanOrEqual(Complex2d lhs, double rhs) {
		return lhs.real <= rhs;
	}

	public static boolean realGreaterThan(Complex2d lhs, double rhs) {
		return lhs.real > rhs;
	}

	public static boolean realGreaterThanOrEqual(Complex2d lhs, double rhs) {
		return lhs.real >= rhs;
	}

	public static boolean imagLessThan(Complex2d lhs, double rhs) {
		return lhs.imag < rhs;
	}

	public static boolean imagLessThanOrEqual(Complex2d lhs, double rhs) {
		return lhs.imag <= rhs;
	}

	public static boolean imagGreaterThan(Complex2d lhs, double rhs) {
		return lhs.imag > rhs;
	}

	public static boolean imagGreaterThanOrEqual(Complex2d lhs, double rhs) {
		return lhs.imag >= rhs;
	}

	public static boolean magnLessThan(Complex2d lhs, double rhs) {
		return lhs.magnSquared() < rhs * rhs;
	}

	public static boolean magnLessThanOrEqual(Complex2d lhs, double rhs) {
		return lhs.magnSquared() <= rhs * rhs;
	}

	public static boolean magnGreaterThan(Complex2d lhs, double rhs) {
		return lhs.magnSquared() > rhs * rhs;
	}

	public static boolean magnGreaterThanOrEqual(Complex2d lhs, double rhs) {
		return lhs.magnSquared() >= rhs * rhs;
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// BiPredicate<Complex2d, Complex2d>
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static boolean equals(Complex2d lhs, Complex2d rhs) {
		return lhs.real == rhs.real && lhs.imag == rhs.imag;
	}

	public static boolean differs(Complex2d lhs, Complex2d rhs) {
		return lhs.real != rhs.real || lhs.imag != rhs.imag;
	}

	public static boolean realLessThan(Complex2d lhs, Complex2d rhs) {
		return lhs.real < rhs.real;
	}

	public static boolean realLessThanOrEqual(Complex2d lhs, Complex2d rhs) {
		return lhs.real <= rhs.real;
	}

	public static boolean realGreaterThan(Complex2d lhs, Complex2d rhs) {
		return lhs.real > rhs.real;
	}

	public static boolean realGreaterThanOrEqual(Complex2d lhs, Complex2d rhs) {
		return lhs.real >= rhs.real;
	}

	public static boolean imagLessThan(Complex2d lhs, Complex2d rhs) {
		return lhs.imag < rhs.imag;
	}

	public static boolean imagLessThanOrEqual(Complex2d lhs, Complex2d rhs) {
		return lhs.imag <= rhs.imag;
	}

	public static boolean imagGreaterThan(Complex2d lhs, Complex2d rhs) {
		return lhs.imag > rhs.imag;
	}

	public static boolean imagGreaterThanOrEqual(Complex2d lhs, Complex2d rhs) {
		return lhs.imag >= rhs.imag;
	}

	public static boolean magnLessThan(Complex2d lhs, Complex2d rhs) {
		return lhs.magnSquared() < rhs.magnSquared();
	}

	public static boolean magnLessThanOrEqual(Complex2d lhs, Complex2d rhs) {
		return lhs.magnSquared() <= rhs.magnSquared();
	}

	public static boolean magnGreaterThan(Complex2d lhs, Complex2d rhs) {
		return lhs.magnSquared() > rhs.magnSquared();
	}

	public static boolean magnGreaterThanOrEqual(Complex2d lhs, Complex2d rhs) {
		return lhs.magnSquared() >= rhs.magnSquared();
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ToDoubleFunction<Complex2D>
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/** sumComponent = real+imag */
	public static double sumComponent(Complex2d value) {
		return value.real + value.imag;
	}

	/** absSumComponent = |real+imag| */
	public static double absSumComponent(Complex2d value) {
		return Math.abs(value.real + value.imag);
	}

	/** diffComponent = real-imag */
	public static double diffComponent(Complex2d value) {
		return value.real - value.imag;
	}

	/** diffRComponent = imag-real */
	public static double diffRComponent(Complex2d value) {
		return value.imag - value.real;
	}

	/** absDiffComponent = |real-imag| */
	public static double absDiffComponent(Complex2d value) {
		return Math.abs(value.real - value.imag);
	}

	/** prodComponent = real*imag */
	public static double prodComponent(Complex2d value) {
		return value.real * value.imag;
	}

	/** absProdComponent = |real*imag| */
	public static double absProdComponent(Complex2d value) {
		return Math.abs(value.real * value.imag);
	}

	/** quotientComponent = real/imag */
	public static double quotientComponent(Complex2d value) {
		return value.real / value.imag;
	}

	/** quotientRComponent = imag/real */
	public static double quotientRComponent(Complex2d value) {
		return value.imag / value.real;
	}

	/** bottomQuotientComponent = real>imag ? imag/real : real/imag */
	public static double bottomQuotientComponent(Complex2d value) {
		if (value.real >= value.imag) {
			return value.imag / value.real;
		} else {
			return value.real / value.imag;
		}
	}

	/** topQuotientComponent = real>imag ? real/imag : imag/real */
	public static double topQuotientComponent(Complex2d value) {
		if (value.real >= value.imag) {
			return value.real / value.imag;
		} else {
			return value.imag / value.real;
		}
	}

	/** minNorm = L[-∞](real, imag) = min(|real|, |imag|) */
	public static double minNorm(Complex2d value) {
		return Math.min(Math.abs(value.real), Math.abs(value.imag));
	}

	/** harmonicNorm = L[-1](real, imag) = 1/(1/|real| + 1/|imag|) = |real|*|imag|/(|real|+|imag|) */
	public static double harmonicNorm(Complex2d value) {
		double dx = Math.abs(value.real);
		double dy = Math.abs(value.imag);
		return dx * dy / (dx + dy);
	}

	/** geometricNorm = L[0](real, imag) = sqrt(|real*imag|) */
	public static double geometricNorm(Complex2d value) {
		return Math.sqrt(Math.abs(value.real * value.imag));
	}

	/** asteroidNorm = L[1/2](real, imag) = (sqrt|real| + sqrt|imag|)^2 */
	public static double asteroidNorm(Complex2d value) {
		double temp = Math.sqrt(Math.abs(value.real)) * Math.sqrt(Math.abs(value.imag));
		return temp * temp;
	}

	/** manhattanNorm = Taxicab Norm = L[1](real, imag) = |real|+|imag| */
	public static double manhattanNorm(Complex2d value) {
		return Math.abs(value.real) + Math.abs(value.imag);
	}

	/** magn = modulus = Euclidean Norm = L[2](real, imag) = |value| = sqrt(dx^2 + dy^2) */
	public static double magn(Complex2d value) {
		return Math.sqrt(value.real * value.real + value.imag * value.imag);
	}

	/** cubicNorm = Squircle norm = L[3](real, imag) = cbrt(real^3 + imag^3) */
	public static double cubicNorm(Complex2d value) {
		return Math.cbrt(value.real * value.real * value.real + value.imag * value.imag * value.imag);
	}

	/** maxNorm = Chebyshev Norm = L[∞](real, imag) = max(|real|, |imag|) */
	public static double maxNorm(Complex2d value) {
		return Math.max(Math.abs(value.real), Math.abs(value.imag));
	}

	/**
	 * Returns the turn number from the positive real axis in counterclockwise direction, in the range [0, 1).
	 */
	public static double turnNumber(Complex2d value) {
		return (value.arg() + TAU) / TAU % 1;
	}

	/** deciBel = 20*log10(|a|) */
	public static double decibel(Complex2d value) {
		return Math.log10(value.magn()) * 20;
	}

	/**
	 * The Rosenbrock function is a non-convex function, introduced by Howard H. Rosenbrock,
	 * which is used as a performance test problem for optimization algorithms.
	 * <p>
	 * rosenbrock(a, b) = (a-x)^2 + b*(y-x^2)^2 with a=1, b=100
	 */
	public static double rosenbrock(Complex2d value) {
		double ax  = 1 - value.real;
		double yxx = value.imag - value.real * value.real;
		return ax * ax + 100 * yxx * yxx;
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ToDoubleBiFunction<Complex2d, Complex2d>, ToIntBiFunction<Complex2d, Complex2d>
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/** distanceSquared = dx^2 + dy^2 */
	public static double distanceSquared(Complex2d lhs, Complex2d rhs) {
		double dx = lhs.real - rhs.real;
		double dy = lhs.imag - rhs.imag;
		return dx * dx + dy * dy;
	}

	/** minDistance = L[-∞](lhs, rhs) = min(|dx|, |dy|) */
	public static double minDistance(Complex2d lhs, Complex2d rhs) {
		return Math.min(Math.abs(lhs.real - rhs.real), Math.abs(lhs.imag - rhs.imag));
	}

	/** harmonicDistance = L[-1](lhs, rhs) = 1/(1/|dx| + 1/|dy|) = |dx|*|dy|/(|dx|+|dy|) */
	public static double harmonicDistance(Complex2d lhs, Complex2d rhs) {
		double dx = Math.abs(lhs.real - rhs.real);
		double dy = Math.abs(lhs.imag - rhs.imag);
		return dx * dy / (dx + dy);
	}

	/** geometricDistance = L[0](lhs, rhs) = sqrt(|dx*dy|) */
	public static double geometricDistance(Complex2d lhs, Complex2d rhs) {
		return Math.sqrt(Math.abs((lhs.real - rhs.real) * (lhs.imag - rhs.imag)));
	}

	/** asteroidDistance = L[1/2](lhs, rhs) = (sqrt|dx| + sqrt|dy|)^2 */
	public static double asteroidDistance(Complex2d lhs, Complex2d rhs) {
		double temp = Math.sqrt(Math.abs(lhs.real - rhs.real)) * Math.sqrt(Math.abs(lhs.imag - rhs.imag));
		return temp * temp;
	}

	/** manhattanDistance = Taxicab Distance = L[1](lhs, rhs) = |dx|+|dy| */
	public static double manhattanDistance(Complex2d lhs, Complex2d rhs) {
		return Math.abs(lhs.real - rhs.real) + Math.abs(lhs.imag - rhs.imag);
	}

	/** distance = Euclidean Distance = L[2](lhs, rhs) = sqrt(dx^2 + dy^2) */
	public static double distance(Complex2d lhs, Complex2d rhs) {
		double dx = lhs.real - rhs.real;
		double dy = lhs.imag - rhs.imag;
		return Math.sqrt(dx * dx + dy * dy);
	}

	/** cubicDistance = Squircle Distance = L[3](lhs, rhs) = cbrt(dx^3 + dy^3) */
	public static double cubicDistance(Complex2d lhs, Complex2d rhs) {
		double dx = lhs.real - rhs.real;
		double dy = lhs.imag - rhs.imag;
		return Math.cbrt(dx * dx * dx + dy * dy * dy);
	}

	/** maxDistance = Chebyshev Distance = L[∞](lhs, rhs) = max(|dx|, |dy|) */
	public static double maxDistance(Complex2d lhs, Complex2d rhs) {
		return Math.max(Math.abs(lhs.real - rhs.real), Math.abs(lhs.imag - rhs.imag));
	}

	public static double dotProduct(Complex2d lhs, Complex2d rhs) {
		return lhs.real * rhs.real + lhs.imag * rhs.imag;
	}

	public static int compare(Complex2d lhs, Complex2d rhs) {
		return Double.compare(lhs.magnSquared(), rhs.magnSquared());
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Function<Complex2d, Complex2d>
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// Provided for lambdas
	public static Complex2d identity(Complex2d value) {
		return new Complex2d(value.real, value.imag);
	}

	// Provided for lambdas
	public static Complex2d zero(Complex2d ignored) {
		return new Complex2d(0, 0);
	}

	// Provided for lambdas
	public static Complex2d one(Complex2d ignored) {
		return new Complex2d(1, 0);
	}

	// Provided for lambdas
	public static Complex2d i(Complex2d ignored) {
		return new Complex2d(0, 1);
	}

	// Provided for lambdas
	public static Complex2d negativeOne(Complex2d ignored) {
		return new Complex2d(-1, 0);
	}

	// Provided for lambdas
	public static Complex2d negativeI(Complex2d ignored) {
		return new Complex2d(0, -1);
	}

	// Provided for lambdas
	public static Complex2d realComplex(Complex2d value) {
		return new Complex2d(value.real, 0);
	}

	// Provided for lambdas
	public static Complex2d imagComplex(Complex2d value) {
		return new Complex2d(value.imag, 0);
	}

	public static Complex2d increment(Complex2d value) {
		return new Complex2d(value.real + 1, value.imag);
	}

	public static Complex2d decrement(Complex2d value) {
		return new Complex2d(value.real - 1, value.imag);
	}

	public static Complex2d incrementImag(Complex2d value) {
		return new Complex2d(value.real, value.imag + 1);
	}

	public static Complex2d decrementImag(Complex2d value) {
		return new Complex2d(value.real, value.imag - 1);
	}

	/** oneSub = 1 - value */
	public static Complex2d oneSub(Complex2d value) {
		return new Complex2d(1 - value.real, -value.imag);
	}

	/** negativeOneSub = -1 - value */
	public static Complex2d negativeOneSub(Complex2d value) {
		return new Complex2d(-1 - value.real, -value.imag);
	}

	public static Complex2d conjugate(Complex2d value) {
		return new Complex2d(value.real, -value.imag);
	}

	public static Complex2d flip(Complex2d value) {
		return new Complex2d(value.imag, value.real);
	}

	public static Complex2d flipNegate(Complex2d value) {
		return new Complex2d(-value.imag, -value.real);
	}

	public static Complex2d negateReal(Complex2d value) {
		return new Complex2d(-value.real, value.imag);
	}

	public static Complex2d rotCW(Complex2d value) {
		return new Complex2d(value.imag, -value.real);
	}

	public static Complex2d rotCCW(Complex2d value) {
		return new Complex2d(-value.imag, value.real);
	}

	public static Complex2d trunc(Complex2d value) {
		return new Complex2d((int)value.real, (int)value.imag);
	}

	public static Complex2d round(Complex2d value) {
		return new Complex2d(Math.rint(value.real), Math.rint(value.imag));
	}

	public static Complex2d roundEast(Complex2d value) {
		return new Complex2d(Math.ceil(value.real), Math.rint(value.imag));
	}

	public static Complex2d ceil(Complex2d value) {
		return new Complex2d(Math.ceil(value.real), Math.ceil(value.imag));
	}

	public static Complex2d roundNorth(Complex2d value) {
		return new Complex2d(Math.rint(value.real), Math.ceil(value.imag));
	}

	public static Complex2d roundNorthWest(Complex2d value) {
		return new Complex2d(Math.floor(value.real), Math.ceil(value.imag));
	}

	public static Complex2d roundWest(Complex2d value) {
		return new Complex2d(Math.floor(value.real), Math.rint(value.imag));
	}

	public static Complex2d floor(Complex2d value) {
		return new Complex2d(Math.floor(value.real), Math.floor(value.imag));
	}

	public static Complex2d roundSouth(Complex2d value) {
		return new Complex2d(Math.rint(value.real), Math.floor(value.imag));
	}

	public static Complex2d roundSouthEast(Complex2d value) {
		return new Complex2d(Math.ceil(value.real), Math.floor(value.imag));
	}

	public static Complex2d truncReal(Complex2d value) {
		return new Complex2d((int)value.real, value.imag);
	}

	public static Complex2d truncImag(Complex2d value) {
		return new Complex2d(value.real, (int)value.imag);
	}

	public static Complex2d roundReal(Complex2d value) {
		return new Complex2d(Math.rint(value.real), value.imag);
	}

	public static Complex2d roundImag(Complex2d value) {
		return new Complex2d(value.real, Math.rint(value.imag));
	}

	public static Complex2d floorReal(Complex2d value) {
		return new Complex2d(Math.floor(value.real), value.imag);
	}

	public static Complex2d floorImag(Complex2d value) {
		return new Complex2d(value.real, Math.floor(value.imag));
	}

	public static Complex2d ceilReal(Complex2d value) {
		return new Complex2d(Math.ceil(value.real), value.imag);
	}

	public static Complex2d ceilImag(Complex2d value) {
		return new Complex2d(value.real, Math.ceil(value.imag));
	}

	public static Complex2d abs(Complex2d l) {
		return new Complex2d(Math.abs(l.real), Math.abs(l.imag));
	}

	public static Complex2d signum(Complex2d l) {
		return new Complex2d(Math.signum(l.real), Math.signum(l.imag));
	}

	public static Complex2d recip(Complex2d value) {
		// recip = 1/value = value.re/magnSquared(value) - value.im/magnSquared(value)*i
		double magnSquared = value.real * value.real + value.imag * value.imag;
		return new Complex2d(value.real / magnSquared,
		                     value.imag / -magnSquared);
	}

	public static Complex2d recipComponent(Complex2d value) {
		return new Complex2d(1 / value.real, 1 / value.imag);
	}

	public static Complex2d sqr(Complex2d value) {
		return new Complex2d(value.real * value.real - value.imag * value.imag,
		                     2 * value.real * value.imag);
	}

	public static Complex2d sqrComponent(Complex2d value) {
		return new Complex2d(value.real * value.real, value.imag * value.imag);
	}

	public static Complex2d sqrt(Complex2d value) {
		// sqrt(a) = sqrt((|a|+a.re)/2) + sqrt((|a|-a.re)/2)*i
		double magn = value.magn();
		return new Complex2d(Math.sqrt((magn + value.real) * 0.5),
		                     Math.sqrt((magn - value.real) * 0.5) * Math.signum(value.imag));
	}

	public static Complex2d sqrtComponent(Complex2d value) {
		return new Complex2d(Math.sqrt(value.real), Math.sqrt(value.imag));
	}

	public static Complex2d cube(Complex2d value) {
		double xx = value.real * value.real;
		double yy = value.imag * value.imag;
		return new Complex2d(value.real * (xx - 3 * yy),
		                     value.imag * (3 * xx - yy));
	}

	public static Complex2d cubeComponent(Complex2d value) {
		return new Complex2d(value.real * value.real * value.real,
		                     value.imag * value.imag * value.imag);
	}

	/**
	 * cbrt(a) = exp(log(a)/3)
	 */
	public static Complex2d cbrt(Complex2d value) {
		//
		// Exception for exp(log(0))=0 which uses -infinite as intermediate
		if (isZero(value)) {
			return new Complex2d();
		}

		double a = Math.pow(value.real * value.real + value.imag * value.imag, Q1_6);
		double b = value.arg() / 3;
		return new Complex2d(a * Math.cos(b),
		                     a * Math.sin(b));
	}

	public static Complex2d cbrtComponent(Complex2d value) {
		return new Complex2d(Math.cbrt(value.real),
		                     Math.cbrt(value.imag));
	}

	public static Complex2d normalize(Complex2d value) {
		double magn = value.magn();
		return new Complex2d(value.real / magn, value.imag / magn);
	}

	/**
	 * Takes the reciprocal if the magnitude is below 1.
	 */
	public Complex2d rabs(Complex2d value) {
		if (value.magnSquared() >= 1.0) {
			return value;
		} else {
			return recip(value);
		}
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Function<Complex2d, Complex2d> (exponential)
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/** log = ln(|value|) + arg(value)*i = ln(magnSquared(value))/2 + arg(value)*i */
	public static Complex2d log(Complex2d value) {
		return new Complex2d(Math.log(value.real * value.real + value.imag * value.imag) * 0.5,
		                     value.arg());
	}

	/** log2 = ln(value)/ln(2) = (ln(|value|) + arg(value)*i)/ln(2) = (log(magnSquared(value))/2 + arg(value)*i)/ln(2) */
	public static Complex2d log2(Complex2d value) {
		return new Complex2d(Math.log(value.real * value.real + value.imag * value.imag) * Q05LOG2,
		                     value.arg() * Q1LOG2);
	}

	/**
	 * log10 = ln(value)/ln(10) = log10(|value|) + arg(value)/ln(10)*i
	 * = log10(magnSquared(value))/2 + arg(value)/ln(10)*i
	 */
	public static Complex2d log10(Complex2d value) {
		return new Complex2d(Math.log10(value.real * value.real + value.imag * value.imag) * 0.5,
		                     value.arg() * Q1LOG10);
	}

	/** log1p = ln(value + 1) */
	public static Complex2d log1p(Complex2d value) {
		double tempReal = value.real + 1;
		return new Complex2d(Math.log(tempReal * tempReal + value.imag * value.imag) * 0.5,
		                     Math.atan2(value.imag, tempReal));
	}

	/**
	 * exp = e^value
	 */
	public static Complex2d exp(Complex2d value) {
		double re2 = Math.exp(value.real);
		return new Complex2d(re2 * Math.cos(value.imag),
		                     re2 * Math.sin(value.imag));
	}

	/** exp2 = e^(value * ln(2)) */
	public static Complex2d exp2(Complex2d value) {
		double re2 = Math.exp(value.real * LOG2);
		double im2 = value.imag * LOG2;
		return new Complex2d(re2 * Math.cos(im2),
		                     re2 * Math.sin(im2));
	}

	/** exp10 = e^(value * ln(10)) */
	public static Complex2d exp10(Complex2d value) {
		double re2 = Math.exp(value.real * LOG10);
		double im2 = value.imag * LOG10;
		return new Complex2d(re2 * Math.cos(im2),
		                     re2 * Math.sin(im2));
	}

	/** exp1n = e^value - 1 */
	public static Complex2d exp1n(Complex2d value) {
		double re2 = Math.exp(value.real);
		return new Complex2d(re2 * Math.cos(value.imag) - 1,
		                     re2 * Math.sin(value.imag));
	}

	/** expRamp = 1 - e^value */
	public static Complex2d expRamp(Complex2d value) {
		double re2 = Math.exp(value.real);
		return new Complex2d(1 - re2 * Math.cos(value.imag),
		                     0 - re2 * Math.sin(value.imag));
	}

	/** fromPolar = r*cis(theta) = r*e^(theta*i) */
	public static Complex2d fromPolar(double r, double theta) {
		return new Complex2d(r * Math.cos(theta),
		                     r * Math.sin(theta));
	}

	/** fromPolar = real(value)*cis(imag(value)) = real(value)*e^(imag(value)*i) */
	public static Complex2d fromPolar(Complex2d value) {
		return new Complex2d(value.real * Math.cos(value.imag),
		                     value.real * Math.sin(value.imag));
	}

	/** toPolar = |value| + arg(value)*i */
	public static Complex2d toPolar(Complex2d value) {
		return new Complex2d(value.magnSquared(),
		                     value.arg());
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// BiFunction<Complex2d, Complex2d, Complex2d>
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static Complex2d copySign(Complex2d lhs, Complex2d rhs) {
		return new Complex2d(Math.copySign(lhs.real, rhs.real),
		                     Math.copySign(lhs.imag, rhs.imag));
	}

	/** copyMagn = magn(rhs)*e^(i*arg(lhs)) = normalize(lhs) * magn(rhs) */
	public static Complex2d copyMagn(Complex2d lhs, Complex2d rhs) {
		return normalize(lhs).mul(rhs.magn());
	}

	public static Complex2d minComponent(Complex2d lhs, Complex2d rhs) {
		return new Complex2d(Math.min(lhs.real, rhs.real),
		                     Math.min(lhs.imag, rhs.imag));
	}

	public static Complex2d maxComponent(Complex2d lhs, Complex2d rhs) {
		return new Complex2d(Math.max(lhs.real, rhs.real),
		                     Math.max(lhs.imag, rhs.imag));
	}

	public static Complex2d distanceComponent(Complex2d lhs, Complex2d rhs) {
		return new Complex2d(Math.abs(lhs.real - rhs.real),
		                     Math.abs(lhs.imag - rhs.imag));
	}

	/** lhs%rhs = lhs - trunc(lhs/rhs)*rhs */
	public static Complex2d remainder(Complex2d lhs, double rhs) {
		//return lhs.sub(trunc(lhs.div(rhs)).mul(rhs));
		return new Complex2d(lhs.real - (int)(lhs.real / rhs) * rhs,
		                     lhs.imag - (int)(lhs.imag / rhs) * rhs);
	}

	/** remainder = lhs%rhs = lhs - trunc(lhs/rhs)*rhs = lhs */
	public static Complex2d remainder(Complex2d lhs, Complex2d rhs) {
		return lhs.sub(trunc(lhs.div(rhs)).mul(rhs));
	}

	/** remainderComponent = lhs%rhs = lhs - trunc(lhs/rhs)*rhs */
	public static Complex2d remainderComponent(Complex2d lhs, Complex2d rhs) {
		return new Complex2d(lhs.real - (int)(lhs.real / rhs.real) * rhs.real,
		                     lhs.imag - (int)(lhs.imag / rhs.imag) * rhs.imag);
	}

	/** floorMod = lhs - floor(lhs/rhs)*rhs */
	public static Complex2d floorMod(Complex2d lhs, double rhs) {
		return new Complex2d(lhs.real - (Math.floor(lhs.real / rhs) * rhs),
		                     lhs.imag - (Math.floor(lhs.imag / rhs) * rhs));
	}

	/** floorMod = lhs - floor(lhs/rhs)*rhs */
	public static Complex2d floorMod(Complex2d lhs, Complex2d rhs) {
		return lhs.sub(floor(lhs.div(rhs)).mul(rhs));
	}

	/** floorModComponent = lhs - floor(lhs/rhs)*rhs */
	public static Complex2d floorModComponent(Complex2d lhs, Complex2d rhs) {
		return new Complex2d(lhs.real - Math.floor(lhs.real / rhs.real) * rhs.real,
		                     lhs.imag - Math.floor(lhs.imag / rhs.imag) * rhs.imag);
	}

	/** roundMod = lhs - round(lhs/rhs)*rhs */
	public static Complex2d roundMod(Complex2d lhs, double rhs) {
		return new Complex2d(lhs.real - (Math.rint(lhs.real / rhs) * rhs),
		                     lhs.imag - (Math.rint(lhs.imag / rhs) * rhs));
	}

	/** roundMod = lhs - round(lhs/rhs)*rhs */
	public static Complex2d roundMod(Complex2d lhs, Complex2d rhs) {
		return lhs.sub(round(lhs.div(rhs)).mul(rhs));
	}

	/** roundModComponent = lhs - round(lhs/rhs)*rhs */
	public static Complex2d roundModComponent(Complex2d lhs, Complex2d rhs) {
		return new Complex2d(lhs.real - Math.rint(lhs.real / rhs.real) * rhs.real,
		                     lhs.imag - Math.rint(lhs.imag / rhs.imag) * rhs.imag);
	}

	/** ceilMod = lhs - ceil(lhs/rhs)*rhs */
	public static Complex2d ceilMod(Complex2d lhs, double rhs) {
		return new Complex2d(lhs.real - (Math.ceil(lhs.real / rhs) * rhs),
		                     lhs.imag - (Math.ceil(lhs.imag / rhs) * rhs));
	}

	/** ceilMod = lhs - ceil(lhs/rhs)*rhs */
	public static Complex2d ceilMod(Complex2d lhs, Complex2d rhs) {
		return lhs.sub(ceil(lhs.div(rhs)).mul(rhs));
	}

	/** moduloCeilComponent = lhs - ceil(lhs/rhs)*rhs */
	public static Complex2d moduloCeilComponent(Complex2d lhs, Complex2d rhs) {
		return new Complex2d(lhs.real - Math.ceil(lhs.real / rhs.real) * rhs.real,
		                     lhs.imag - Math.ceil(lhs.imag / rhs.imag) * rhs.imag);
	}

	/** min = Min Mean = P[-∞](lhs, rhs) = min(lhs, rhs) = |lhs|<=|rhs| ? lhs : rhs */
	public static Complex2d min(Complex2d lhs, Complex2d rhs) {
		if (lhs.magnSquared() <= rhs.magnSquared()) {
			return new Complex2d(lhs);
		} else {
			return new Complex2d(rhs);
		}
	}

	/** harmonicMean = P[-1](lhs, rhs) = 1/(1/lhs + 1/rhs) = lhs*rhs/(lhs+rhs) */
	public static Complex2d harmonicMean(Complex2d lhs, Complex2d rhs) {
//		return recip(recip(lhs).add(recip(rhs)));
		return lhs.mul(rhs).mul(recip(lhs.add(rhs)));
	}

	/** geometricMean = P[0](lhs, rhs) = sqrt(lhs * rhs) */
	public static Complex2d geometricMean(Complex2d lhs, Complex2d rhs) {
		return sqrt(lhs.mul(rhs));
	}

	/** asteroidMean = L[1/2](lhs, rhs) = ((sqrt(lhs) + sqrt(rhs))/2)^2 */
	public static Complex2d asteroidMean(Complex2d lhs, Complex2d rhs) {
		return sqr(sqrt(lhs).add(sqrt(rhs)).mul(0.5));
	}

	/** asteroidMeanWrong = L[1/2](lhs, rhs)*4 = (sqrt(lhs) + sqrt(rhs))^2 */
	public static Complex2d asteroidMeanWrong(Complex2d lhs, Complex2d rhs) {
		return sqr(sqrt(lhs).add(sqrt(rhs)));
	}

	/** mean = Arithmetic Mean = P[1](lhs, rhs) = (lhs+rhs)/2 */
	public static Complex2d mean(Complex2d lhs, Complex2d rhs) {
		return new Complex2d((lhs.real + rhs.real) * 0.5, (lhs.imag + rhs.imag) * 0.5);
	}

	/** quadraticMean = P[2](x, y) = sqrt((lhs^2 + rhs^2)/2) */
	public static Complex2d quadraticMean(Complex2d lhs, Complex2d rhs) {
		return sqrt(sqr(lhs).add(sqr(rhs)).mul(0.5));
	}

	/** quadraticMeanWrong = P[2](x, y)*sqrt(2) = sqrt(lhs^2 + rhs^2) */
	public static Complex2d quadraticMeanWrong(Complex2d lhs, Complex2d rhs) {
		return sqrt(sqr(lhs).add(sqr(rhs)));
	}

	/** cubicMean = Squircle Mean = P[3](x, y) = cbrt((lhs^3 + rhs^3)/2) */
	public static Complex2d cubicMean(Complex2d lhs, Complex2d rhs) {
		return cbrt(cube(lhs).add(cube(rhs)).mul(0.5));
	}

	/** cubicMeanWrong = P[3](x, y)*sqrt(2) = cbrt(lhs^3 + rhs^3) */
	public static Complex2d cubicMeanWrong(Complex2d lhs, Complex2d rhs) {
		return cbrt(cube(lhs).add(cube(rhs)));
	}

	/** max = Max Mean = P[∞](lhs, rhs) = max(lhs, rhs) = |lhs|>=|rhs| ? lhs : rhs */
	public static Complex2d max(Complex2d lhs, Complex2d rhs) {
		if (lhs.magnSquared() >= rhs.magnSquared()) {
			return new Complex2d(lhs);
		} else {
			return new Complex2d(rhs);
		}
	}

	public static Complex2d pow(double lhs, Complex2d rhs) {
		// Avoid exp(log(0))=0 which uses -infinite as intermediate
		if (lhs == 0) {
			return new Complex2d();
		}

		return exp(rhs.mul(Math.log(lhs)));
	}

	public static Complex2d pow(Complex2d lhs, double rhs) {
		// Avoid exp(log(0))=0 which uses -infinite as intermediate
		if (lhs.real == 0 && lhs.imag == 0) {
			return new Complex2d();
		}

		return exp(log(lhs).mul(rhs));
	}

	public static Complex2d pow(Complex2d lhs, Complex2d rhs) {
		// Avoid exp(log(0))=0 which uses -infinite as intermediate
		if (lhs.real == 0 && lhs.imag == 0) {
			return new Complex2d();
		}

		return exp(log(lhs).mul(rhs));
	}

	/** nthRoot(lhs) = exp(log(lhs)/rhs) */
	public static Complex2d nthRoot(Complex2d lhs, double rhs) {
		// Avoid exp(log(0))=0 which uses -infinite as intermediate
		if (lhs.real == 0 && lhs.imag == 0) {
			return new Complex2d();
		}

		return exp(log(lhs).div(rhs));
	}

	/** nthRoot(lhs) = exp(log(lhs)/rhs) */
	public static Complex2d nthRoot(Complex2d lhs, Complex2d rhs) {
		// Avoid exp(log(0))=0 which uses -infinite as intermediate
		if (lhs.real == 0 && lhs.imag == 0) {
			return new Complex2d();
		}

		return exp(log(lhs).div(rhs));
	}

	/** logBase = log(lhs)/log(rhs) = log(lhs)*log(-rhs) */
	public static Complex2d logBase(Complex2d lhs, double rhs) {
		return log(lhs).mul(Math.log(-rhs));
	}

	/** logBase = log(lhs)/log(rhs) = log(lhs)*log(-rhs) */
	public static Complex2d logBase(Complex2d lhs, Complex2d rhs) {
		return log(lhs).mul(log(rhs.negate()));
	}

	/** expRamp = 1 - exp(lhs*rhs) */
	public static Complex2d expRamp(Complex2d lhs, Complex2d rhs) {
		return negativeOneSub(exp(lhs.mul(rhs)));
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// TriFunction<Complex2d, Complex2d, Double, Complex2d>
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/** lerp = linear interpolation */
	public static Complex2d lerp(Complex2d lhs, Complex2d rhs, double position) {
		return new Complex2d(lhs.real + (rhs.real - lhs.real) * position, lhs.imag + (rhs.imag - lhs.imag) * position);
	}

	/** clerp = circular linear interpolation */
	public static Complex2d clerp(Complex2d lhs, Complex2d rhs, double position) {
		lhs = toPolar(lhs);
		rhs = toPolar(rhs);

		lhs.real += (rhs.real - lhs.real) * position;

		double angleDiff = lhs.imag - rhs.imag;
		if (angleDiff > TAU05) {
			lhs.imag += (rhs.imag - lhs.imag + TAU) * position;
		} else if (angleDiff <= -TAU05) {
			lhs.imag += (rhs.imag - lhs.imag - TAU) * position;
		} else {
			lhs.imag += (rhs.imag - lhs.imag) * position;
		}

		return fromPolar(lhs);
	}
}
