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

package org.digitalmodular.utilities;

import org.jetbrains.annotations.Nullable;

import org.digitalmodular.utilities.annotation.UtilityClass;
import org.digitalmodular.utilities.container.Complex2d;
import static org.digitalmodular.utilities.NumberUtilities.isDegenerate;
import static org.digitalmodular.utilities.ValidatorUtilities.assertNonNull;
import static org.digitalmodular.utilities.ValidatorUtilities.requireNonNull;

/**
 * @author Mark Jeronimus
 */
// Created 2016-12-21
@SuppressWarnings("OverloadedMethodsWithSameNumberOfParameters")
@UtilityClass
public final class ComplexValidatorUtilities {
	public static Complex2d assertComplexNotDegenerate(Complex2d actual, String varName) {
		assertNonNull(actual, varName);

		if (isDegenerate(actual.real) || isDegenerate(actual.imag)) {
			if (isDegenerate(actual.real)) {
				if (isDegenerate(actual.imag)) {
					throw new IllegalArgumentException(varName + ".re and .im are both degenerate: " + actual);
				} else {
					throw new IllegalArgumentException(varName + ".re is degenerate: " + actual);
				}
			} else {
				throw new IllegalArgumentException(varName + ".im is degenerate: " + actual);
			}
		}

		return actual;
	}

	public static Complex2d requireComplexNotDegenerate(Complex2d actual, String varName) {
		requireNonNull(actual, varName);

		if (isDegenerate(actual.real) || isDegenerate(actual.imag)) {
			if (isDegenerate(actual.real)) {
				if (isDegenerate(actual.imag)) {
					throw new IllegalArgumentException(varName + ".re and .im are both degenerate: " + actual);
				} else {
					throw new IllegalArgumentException(varName + ".re is degenerate: " + actual);
				}
			} else {
				throw new IllegalArgumentException(varName + ".im is degenerate: " + actual);
			}
		}

		return actual;
	}

	public static @Nullable Complex2d requireComplexNullOrNotDegenerate(@Nullable Complex2d actual, String varName) {
		if (actual == null) {
			return null;
		}

		if (isDegenerate(actual.real) || isDegenerate(actual.imag)) {
			if (isDegenerate(actual.real)) {
				if (isDegenerate(actual.imag)) {
					throw new IllegalArgumentException(varName + ".re and .im are both degenerate: " + actual);
				} else {
					throw new IllegalArgumentException(varName + ".re is degenerate: " + actual);
				}
			} else {
				throw new IllegalArgumentException(varName + ".im is degenerate: " + actual);
			}
		}

		return actual;
	}

	public static Complex2d requireMagnAtLeast(double min, Complex2d actual, String varName) {
		requireNonNull(actual, varName);

		double magnSquared = magnSquaredExactly(actual, varName);
		if (magnSquared < min * min) {
			throw new IllegalArgumentException(varName + ".magn() must be at least " + min + ": " + actual.magn());
		}

		return actual;
	}

	public static Complex2d requireMagnAbove(double min, Complex2d actual, String varName) {
		requireNonNull(actual, varName);

		double magnSquared = magnSquaredExactly(actual, varName);
		if (magnSquared <= min * min) {
			throw new IllegalArgumentException(varName + ".magn() must be above " + min + ": " + actual.magn());
		}

		return actual;
	}

	public static Complex2d requireMagnBelow(double max, Complex2d actual, String varName) {
		requireNonNull(actual, varName);

		double magnSquared = magnSquaredExactly(actual, varName);
		if (magnSquared > max * max) {
			throw new IllegalArgumentException(varName + ".magn() must be below " + max + ": " + actual.magn());
		}

		return actual;
	}

	public static Complex2d requireMagnAtMost(double max, Complex2d actual, String varName) {
		requireNonNull(actual, varName);

		double magnSquared = magnSquaredExactly(actual, varName);
		if (magnSquared >= max * max) {
			throw new IllegalArgumentException(varName + ".magn() must be at most " + max + ": " + actual.magn());
		}

		return actual;
	}

	public static Complex2d requireMagnRange(double min, double max, Complex2d actual, String varName) {
		requireNonNull(actual, varName);

		double magnSquared = magnSquaredExactly(actual, varName);
		if (magnSquared < min * min || magnSquared > max * max) {
			throw new IllegalArgumentException(varName + ".magn() must be in the range [" + min + ", " + max + "]: " +
			                                   actual.magn());
		}

		return actual;
	}

	private static double magnSquaredExactly(Complex2d actual, String varName) {
		double magnSquared = actual.magnSquared();
		if (magnSquared == Double.POSITIVE_INFINITY) {
			throw new IllegalArgumentException(varName + " is too large to have a calculable magn(): " + actual);
		}

		return magnSquared;
	}
}
