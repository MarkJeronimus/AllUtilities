package nl.airsupplies.utilities.validator;

import org.jetbrains.annotations.Nullable;

import nl.airsupplies.utilities.annotation.UtilityClass;
import nl.airsupplies.utilities.complex.Complex2d;
import static nl.airsupplies.utilities.NumberUtilities.isDegenerate;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.assertNonNull;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

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
