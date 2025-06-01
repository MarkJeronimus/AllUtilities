package nl.airsupplies.utilities;

import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import nl.airsupplies.utilities.annotation.UtilityClass;
import nl.airsupplies.utilities.container.Rational;
import nl.airsupplies.utilities.container.UnsignedByte;
import nl.airsupplies.utilities.container.UnsignedInteger;
import nl.airsupplies.utilities.container.UnsignedShort;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireBetween;

/**
 * @author Mark Jeronimus
 */
// 2007-11-16
// 2013-11-28 Major rewrite
// 2015-01-13 Bugfix: log3 & exp3 worked in float instead of double
@UtilityClass
public final class NumberUtilities {
	public static final DoubleUnaryOperator  ZERO_OPERATOR       = ignored -> 0;
	public static final DoubleUnaryOperator  UNITY_OPERATOR      = ignored -> 1;
	public static final DoubleUnaryOperator  NAN_OPERATOR        = ignored -> Double.NaN;
	public static final DoubleBinaryOperator ADD                 = Double::sum;
	public static final DoubleBinaryOperator SUBTRACT            = (a, b) -> a - b;
	public static final DoubleBinaryOperator SUBTRACT_REVERSE    = (a, b) -> b - a;
	public static final DoubleBinaryOperator MULTIPLY            = (a, b) -> a * b;
	public static final DoubleBinaryOperator DIVIDE              = (a, b) -> a / b;
	public static final DoubleBinaryOperator DIVIDE_REVERSE      = (a, b) -> b / a;
	public static final DoubleBinaryOperator SAFE_DIVIDE         = (a, b) -> Math.abs(b) < 1.0e-8 ? 0 : a / b;
	public static final DoubleBinaryOperator SAFE_DIVIDE_REVERSE = (a, b) -> Math.abs(a) < 1.0e-8 ? 0 : b / a;
	public static final DoubleBinaryOperator MIN                 = Double::min;
	public static final DoubleBinaryOperator MAX                 = Double::max;

	/**
	 * All possible chars for representing a number as a String
	 */
	@SuppressWarnings("SpellCheckingInspection")
	public static final String RADIX_DIGITS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static final  DecimalFormatSymbols US_FORMATTER_SYMBOLS = DecimalFormatSymbols.getInstance(Locale.US);
	private static final DecimalFormat[]      PRECISION_FORMATTERS = new DecimalFormat[17];

	private static SecureRandom secureRnd = null;

	public static Random getSecureRandom() {
		if (secureRnd == null) {
			secureRnd = new SecureRandom();
		}

		return secureRnd;
	}

	public static String formatNumber(double value, int minPrecision) {
		requireBetween(0, PRECISION_FORMATTERS.length - 1, minPrecision, "minPrecision");

		// No synchronization necessary because the initialization function is idempotent and has low overhead.
		if (PRECISION_FORMATTERS[minPrecision] == null) {
			PRECISION_FORMATTERS[minPrecision] = new DecimalFormat();
			PRECISION_FORMATTERS[minPrecision].setMinimumFractionDigits(minPrecision);
			PRECISION_FORMATTERS[minPrecision].setMaximumFractionDigits(minPrecision);
			PRECISION_FORMATTERS[minPrecision].setGroupingUsed(false);
			PRECISION_FORMATTERS[minPrecision].setDecimalFormatSymbols(US_FORMATTER_SYMBOLS);
		}

		return PRECISION_FORMATTERS[minPrecision].format(value);
	}

	public static boolean isNegative(float value) {
		return (Float.floatToRawIntBits(value) & 0x80000000) < 0;
	}

	public static boolean isNegative(double value) {
		return (Double.doubleToRawLongBits(value) & 0x8000000000000000L) < 0;
	}

	public static boolean isDegenerate(float value) {
		return Float.isInfinite(value) || Float.isNaN(value);
	}

	public static boolean isDegenerate(double value) {
		return Double.isInfinite(value) || Double.isNaN(value);
	}

	public static boolean isDegenerate(Point2D point) {
		return isDegenerate(point.getX()) || isDegenerate(point.getY());
	}

	public static int clamp(int value, int min, int max) {
		return Math.max(min, Math.min(max, value));
	}

	public static long clamp(long value, long min, long max) {
		return Math.max(min, Math.min(max, value));
	}

	public static float clamp(float value, float min, float max) {
		return Math.max(min, Math.min(max, value));
	}

	public static double clamp(double value, double min, double max) {
		return Math.max(min, Math.min(max, value));
	}

	/**
	 * Linearly interpolate two values.
	 */
	public static float lerp(float first, float second, float position) {
		// Numerically stable version. 'f+(s-f)*p' can be faster but is numerically unstable.
		return first * (1 - position) + second * position;
	}

	/**
	 * Linearly interpolate two values.
	 */
	public static double lerp(double first, double second, double position) {
		// Numerically stable version. 'f+(s-f)*p' can be faster but is numerically unstable.
		return first * (1 - position) + second * position;
	}

	public static double lerp(double first, double second, Rational position) {
		// Numerically stable version. 'f+(s-f)*n/d' can be faster but is numerically unstable.
		return (first * (position.getDenominator() - position.getNumerator()) + second * position.getNumerator())
		       / position.getDenominator();
	}

	public static float unLerp(float first, float second, float value) {
		float diff = second - first;
		return diff == 0 ? 0 : (value - first) / diff;
	}

	public static double unLerp(double first, double second, double value) {
		double diff = second - first;
		return diff == 0 ? 0 : (value - first) / diff;
	}

	/**
	 * Returns the minimum number of bits needed to represent the unsigned int.
	 * <p>
	 * The result is rounded down, as if calculated by the idealized formula {@code floor(log2(value)) + 1}.
	 * <p>
	 * To get a rounded up version, use {@link #log2(int)}.
	 */
	public static int bitSize(int value) {
		return value == 0 ? 0 : bitCount(Integer.highestOneBit(value) - 1) + 1;
	}

	/**
	 * Returns the minimum number of bits needed to represent the unsigned long.
	 * <p>
	 * The result is rounded down, as if calculated by the idealized formula {@code floor(log2(value)) + 1}.
	 * <p>
	 * To get a rounded up version, use {@link #log2(long)}.
	 */
	public static int bitSize(long value) {
		return value == 0 ? 0 : Long.bitCount(Long.highestOneBit(value) - 1) + 1;
	}

	/**
	 * Returns the base-2 log of the unsigned int, rounded up.
	 * <p>
	 * The result is rounded up, as if by the idealized formula {@code ceil(log2(value))}.
	 * <p>
	 * The rounded down version of log<sub>2</sub> can be obtained by {@link #bitSize(int)}{@code  - 1}.
	 */
	public static int log2(int value) {
		return value == 0 ? 0 : bitCount(Integer.highestOneBit(value - 1) - 1) + 1;
	}

	/**
	 * Returns the base-2 log of the unsigned long, rounded up.
	 * <p>
	 * The result is rounded up, as if by the idealized formula {@code ceil(log2(value))}.
	 * <p>
	 * To get a rounded down version, use {@link #bitSize(long)}{@code  - 1}.
	 */
	public static int log2(long value) {
		return value == 0 ? 0 : Long.bitCount(Long.highestOneBit(value - 1) - 1) + 1;
	}

	/**
	 * Checks if value is a power of two. This is to say, if there is an integer {@code x} such that
	 * <p>
	 * {@code value = 2<sup>x</sup>}
	 * <p>
	 * This is the sequence 0, 1, 2, 4, 8, 16, ...
	 */
	public static boolean isPowerOfTwo(int value) {
		return (value & value - 1) == 0;
	}

	/**
	 * Checks if value is a power of two. This is to say, if there is an integer {@code x} such that
	 * <p>
	 * {@code value = 2<sup>x</sup>}
	 * <p>
	 * This is the sequence 0, 1, 2, 4, 8, 16, ...
	 */
	public static boolean isPowerOfTwo(long value) {
		return (value & value - 1) == 0;
	}

	/**
	 * Calculate the closest power of two equal to or higher than the int.
	 *
	 * @param value an int.
	 * @return the highest power of two that's equal to or higher than the input value, or 0 if the number was
	 * negative.
	 */
	public static int nextPowerOf2(int value) {
		return value < 0 ? 0 : Integer.highestOneBit(value - 1) << 1;
	}

	/**
	 * Calculate the closest power of two equal to or higher than the long.
	 *
	 * @param value a long.
	 * @return the highest power of two that's equal to or higher than the input value, or 0 if the number was
	 * negative.
	 */
	public static long nextPowerOf2(long value) {
		return value < 0 ? 0 : Long.highestOneBit(value - 1) << 1;
	}

	public static int bitCount_java(int i) {
		// Java implementation
		i -= (i >>> 1 & 0x55555555);
		i = (i & 0x33333333) + (i >>> 2 & 0x33333333);
		i = i + (i >>> 4) & 0x0f0f0f0f;
		i += (i >>> 8);
		i += (i >>> 16);
		return i & 0x3f;
	}

	public static int bitCount(int i) {
		// Faster than Java implementation
		i -= (i >>> 1 & 0x55555555);
		i = (i & 0x33333333) + (i >>> 2 & 0x33333333);
		i = i + (i >>> 4) & 0x0f0f0f0f;
		return i * 0x01010101 >>> 24;
	}

	/**
	 * Reverses the first {@code numBits} bits in an {@code int}. It discards the others.
	 */
	public static int reverseBits(int value, int numBits) {
		return Integer.reverse(value) >>> 32 - numBits;
	}

	/**
	 * Reverses the first {@code numBits} bits in a {@code long}. It discards the others.
	 */
	public static long reverseBits(long value, int numBits) {
		return Long.reverse(value) >>> 64 - numBits;
	}

	/**
	 * Sign extends. The bits to the left of the value <em>must</em> be zero or the result will be undefined.
	 * <p>
	 * <em>E.g.</em> {@code signExtend(0x0000FED, 12)} returns {@code 0bFFFFFFED}
	 */
	public static int signExtend(int value, int numBits) {
		requireBetween(1, 32, numBits, "numBits");

		int mask = 1 << (numBits - 1);
		return (value ^ mask) - mask;
	}

	/**
	 * Sign extends. The bits to the left of the value <em>must</em> be zero or the result will be undefined.
	 * <p>
	 * <em>E.g.</em> {@code signExtend(0x000000000000FED, 12)} returns {@code 0bFFFFFFFFFFFFFFED}
	 */
	public static long signExtend(long value, int numBits) {
		requireBetween(1, 64, numBits, "numBits");

		long mask = 1L << (numBits - 1);
		return (value ^ mask) - mask;
	}

	/**
	 * Calculates {@code numerator / denominator}, and in the case {@code denominator == 0.0f} it returns 0.0f.
	 */
	public static float divSafe(float numerator, float denominator) {
		return denominator == 0 ? 0 : numerator / denominator;
	}

	/**
	 * Calculates {@code numerator / denominator}, and in the case {@code denominator == 0.0f} it returns 0.0.
	 */
	public static double divSafe(double numerator, double denominator) {
		return denominator == 0 ? 0 : numerator / denominator;
	}

	/**
	 * Calculates the equivalent of {@code (int)Math.rint(numerator / (double)denominator)} but without floating point
	 * operations, and in the case {@code denominator == 0} it returns 0.
	 */
	public static int divSafeRound(int numerator, int denominator) {
		return denominator == 0 ? 0 : (numerator + denominator / 2) / denominator;
	}

	/**
	 * Returns the result of {@code numerator <b>mod</b> denominator}. This is different from Java's
	 * <i>remainder</i> operator ({@code numerator % denominator}). For remainder, the sign of the result is the
	 * same as the sign of the numerator. For modulo, however, the sign of the result is the same as the sign of the
	 * denominator, making it useful for array operations with negative indices that should wrap around.
	 */
	public static int modulo(int numerator, int denominator) {
		return numerator - (int)Math.floor(numerator / (double)denominator) * denominator;
	}

	/**
	 * Returns the result of {@code numerator mod denominator}. This is different from Java's {@code numerator %
	 * denominator} operator (a.k.a. remainder). For remainder, the sign of the result is the same as the sign of
	 * the numerator. For modulo, however, the sign of the result is the same as the sign of the denominator, making it
	 * useful for operations that need strict wrapping behavior.
	 */
	public static long modulo(long numerator, int denominator) {
		return numerator - (long)Math.floor(numerator / (double)denominator) * denominator;
	}

	/**
	 * Returns the result of {@code numerator mod denominator}. This is different from Java's {@code numerator %
	 * denominator} operator (a.k.a. remainder). For remainder, the sign of the result is the same as the sign of
	 * the numerator. For modulo, however, the sign of the result is the same as the sign of the denominator, making it
	 * useful for operations that need strict wrapping behavior.
	 */
	public static float modulo(float numerator, float denominator) {
		return numerator - (float)Math.floor(numerator / denominator) * denominator;
	}

	/**
	 * Returns the result of {@code numerator mod denominator}. This is different from Java's {@code numerator %
	 * denominator} operator (a.k.a. remainder). For remainder, the sign of the result is the same as the sign of
	 * the numerator. For modulo, however, the sign of the result is the same as the sign of the denominator, making it
	 * useful for operations that need strict wrapping behavior.
	 */
	public static double modulo(double numerator, double denominator) {
		return numerator - Math.floor(numerator / denominator) * denominator;
	}

	/**
	 * Returns the floor modulus of the {@code double} arguments.
	 * <p>
	 * The floor modulus is {@code numerator - (floor(numerator / denominator) * denominator)},
	 * has the same sign as the divisor {@code denominator}, and
	 * is in the range of {@code -abs(denominator) < r < +abs(denominator)}.
	 * <p>
	 * The relationship between {@code floorDiv} and {@code floorMod} is such that:
	 * <ul>
	 * <li>{@code floor(numerator / denominator) * denominator + floorMod(numerator, denominator) == numerator}
	 * </ul>
	 * <p>
	 * Examples:
	 * <ul>
	 * <li>If the signs of the arguments are the same, the results
	 * of {@code floorMod} and the {@code %} operator are the same.  <br>
	 * <ul>
	 * <li>{@code floorMod(4, 3) == 1}; &nbsp; and {@code (4 % 3) == 1}</li>
	 * </ul>
	 * <li>If the signs of the arguments are different, the results differ from the {@code %} operator.<br>
	 * <ul>
	 * <li>{@code floorMod(+4, -3) == -2}; &nbsp; and {@code (+4 % -3) == +1} </li>
	 * <li>{@code floorMod(-4, +3) == +2}; &nbsp; and {@code (-4 % +3) == -1} </li>
	 * <li>{@code floorMod(-4, -3) == -1}; &nbsp; and {@code (-4 % -3) == -1} </li>
	 * </ul>
	 * </li>
	 * </ul>
	 * <p>
	 * If the signs of arguments are unknown and a positive modulus
	 * is needed it can be computed as {@code (floorMod(numerator, denominator) + abs(denominator)) % abs
	 * (denominator)}.
	 *
	 * @param numerator   the dividend
	 * @param denominator the divisor
	 * @return the floor modulus {@code numerator - (floor(numerator / denominator) * denominator)}
	 * @throws ArithmeticException if the divisor {@code denominator} is zero
	 */
	public static float floorMod(float numerator, float denominator) {
		return numerator - (float)Math.floor(numerator / denominator) * denominator;
	}

	/**
	 * Returns the floor modulus of the {@code double} arguments.
	 * <p>
	 * The floor modulus is {@code numerator - (floor(numerator / denominator) * denominator)},
	 * has the same sign as the divisor {@code denominator}, and
	 * is in the range of {@code -abs(denominator) < r < +abs(denominator)}.
	 * <p>
	 * The relationship between {@code floorDiv} and {@code floorMod} is such that:
	 * <ul>
	 * <li>{@code floor(numerator / denominator) * denominator + floorMod(numerator, denominator) == numerator}
	 * </ul>
	 * <p>
	 * Examples:
	 * <ul>
	 * <li>If the signs of the arguments are the same, the results
	 * of {@code floorMod} and the {@code %} operator are the same.  <br>
	 * <ul>
	 * <li>{@code floorMod(4, 3) == 1}; &nbsp; and {@code (4 % 3) == 1}</li>
	 * </ul>
	 * <li>If the signs of the arguments are different, the results differ from the {@code %} operator.<br>
	 * <ul>
	 * <li>{@code floorMod(+4, -3) == -2}; &nbsp; and {@code (+4 % -3) == +1} </li>
	 * <li>{@code floorMod(-4, +3) == +2}; &nbsp; and {@code (-4 % +3) == -1} </li>
	 * <li>{@code floorMod(-4, -3) == -1}; &nbsp; and {@code (-4 % -3) == -1} </li>
	 * </ul>
	 * </li>
	 * </ul>
	 * <p>
	 * If the signs of arguments are unknown and a positive modulus
	 * is needed it can be computed as {@code (floorMod(numerator, denominator) + abs(denominator)) % abs
	 * (denominator)}.
	 *
	 * @param numerator   the dividend
	 * @param denominator the divisor
	 * @return the floor modulus {@code numerator - (floor(numerator / denominator) * denominator)}
	 * @throws ArithmeticException if the divisor {@code denominator} is zero
	 */
	public static double floorMod(double numerator, double denominator) {
		return numerator - Math.floor(numerator / denominator) * denominator;
	}

	/**
	 * Returns the same as {@code (int)round(value / (double)step)} without using intermediate floating point
	 * operations.
	 */
	public static int roundDiv(int dividend, int divisor) {
		if (divisor > 1) {
			return Math.floorDiv(dividend + (divisor + 1) / 2, divisor);
		} else if (divisor == 1) {
			return dividend;
		} else {
			throw new IllegalArgumentException("'divisor' should be at least 1: " + divisor);
		}
	}

	/**
	 * Returns the same as {@code (int)round(value / (double)step)} without using intermediate floating point
	 * operations.
	 */
	public static long roundDiv(long dividend, long divisor) {
		if (divisor > 1) {
			return Math.floorDiv(dividend + (divisor + 1) / 2, divisor);
		} else if (divisor == 1) {
			return dividend;
		} else {
			throw new IllegalArgumentException("'divisor' should be at least 1: " + divisor);
		}
	}

	/**
	 * Returns the same as {@code (int)round(value / (double)step) * step} without using intermediate floating point
	 * operations.
	 */
	public static int quantize(int value, int step) {
		if (step > 1) {
			return Math.floorDiv(value + (step + 1) / 2, step) * step;
		} else if (step == 1) {
			return value;
		} else {
			throw new IllegalArgumentException("'step' should be at least 1: " + step);
		}
	}

	/** Calculates closest value to the specified value that is an exact multiple of 'step'. */
	public static long quantize(long value, long stepSize) {
		if (stepSize > 1) {
			return Math.floorDiv(value + (stepSize + 1) / 2, stepSize) * stepSize;
		} else if (stepSize == 1) {
			return value;
		} else {
			throw new IllegalArgumentException("'stepSize' should be at least 1: " + stepSize);
		}
	}

	/** Calculates closest value to the specified value that is an exact multiple of 'step'. */
	public static int quantize(float value, int stepSize) {
		if (stepSize > 1) {
			return Math.round(value / stepSize) * stepSize;
		} else if (stepSize == 1) {
			return Math.round(value);
		} else {
			throw new IllegalArgumentException("'stepSize' should be at least 1: " + stepSize);
		}
	}

	/** Calculates closest value to the specified value that is an exact multiple of 'step'. */
	public static long quantize(double value, int stepSize) {
		if (stepSize > 1) {
			return Math.round(value / stepSize) * stepSize;
		} else if (stepSize == 1) {
			return Math.round(value);
		} else {
			throw new IllegalArgumentException("'stepSize' should be at least 1: " + stepSize);
		}
	}

	/** Calculates closest value to the specified value that is an exact multiple of 'step'. */
	public static long quantize(double value, long stepSize) {
		if (stepSize > 1) {
			return Math.round(value / stepSize) * stepSize;
		} else if (stepSize == 1) {
			return Math.round(value);
		} else {
			throw new IllegalArgumentException("'stepSize' should be above 0: " + stepSize);
		}
	}

	/** Calculates closest value to the specified value that is an exact multiple of 'step'. */
	public static float quantize(float value, float stepSize) {
		if (stepSize == 1) {
			return (float)Math.rint(value);
		} else if (stepSize > 0) {
			return (float)(Math.rint(value / stepSize) * stepSize);
		} else {
			throw new IllegalArgumentException("'stepSize' should be above 0: " + stepSize);
		}
	}

	/** Calculates closest value to the specified value that is an exact multiple of 'step'. */
	public static double quantize(double value, double stepSize) {
		if (stepSize == 1) {
			return Math.rint(value);
		} else if (stepSize > 0) {
			return Math.rint(value / stepSize) * stepSize;
		} else {
			throw new IllegalArgumentException("'stepSize' should be above 0: " + stepSize);
		}
	}

	/**
	 * Calculates the final position when a virtual player in a playing field of length {@code size} starting at {@code
	 * 0} walks straight until it hits a wall and reverses direction, for {@code pos} steps.
	 * <p>
	 * For example, {@code bounce(10, 5) == 2}, as seen here:
	 *
	 * <pre>
	 * [X....] 0
	 * [.X...] 1
	 * [..X..] 2
	 * [...X.] 3
	 * [....X] 4
	 * [...X.] 5
	 * [..X..] 6
	 * [.X...] 7
	 * [X....] 8
	 * [.X...] 9
	 * [..X..] 10
	 *    &darr;
	 *  01234
	 * </pre>
	 * <p>
	 * It's like a zig-zagging version of {@link #modulo(int, int) modulo()} and Java's remainder ({@code %}) operator.
	 *
	 * @param pos  The number of steps to take.
	 * @param size The size of the playing field. Should be positive. Non-positive values result in undefined
	 *             behavior.
	 */
	public static int bounce(int pos, int size) {
		size--;
		int modulo = size * 2;
		pos = Math.floorMod(pos + size, modulo);
		return Math.abs(pos - size);
	}

	/**
	 * Calculates the final position when a virtual player in a playing field of length {@code size} starting at {@code
	 * 0} walks straight until it hits a wall and reverses direction, for {@code pos} steps.
	 * <p>
	 * For example, {@code bounce(10, 5) == 2}, as seen here:
	 *
	 * <pre>
	 * [X....] 0
	 * [.X...] 1
	 * [..X..] 2
	 * [...X.] 3
	 * [....X] 4
	 * [....X] 5
	 * [...X.] 6
	 * [..X..] 7
	 * [.X...] 8
	 * [X....] 9
	 * [X....] 10
	 *    &darr;
	 *  01234
	 * </pre>
	 * <p>
	 * It's like a zig-zagging version of {@link #modulo(int, int) modulo()} and Java's remainder ({@code %}) operator.
	 *
	 * @param pos  The number of steps to take.
	 * @param size The size of the playing field. Should be positive. Non-positive values result in undefined
	 *             behavior.
	 */
	public static int reflect(int pos, int size) {
		int modulo = size * 2;
		pos = Math.floorMod(pos, modulo);
		return pos < size ? pos : modulo - pos - 1;
	}

	/**
	 * Calculates a float number to the power of an integer. The implementation uses square-and-multiply to prevent
	 * calculation of expensive logarithms and exponents.
	 *
	 * @param base     the (floating point) base of the exponentiation.
	 * @param exponent the unsigned exponent.
	 * @return base<sup>exponent</sup>.
	 */
	public static float pow(float base, int exponent) {
		float result = 1;

		float squares = base;
		while (exponent > 0) {
			if ((exponent & 1) != 0) {
				result *= squares;
			}
			squares *= squares;
			exponent >>>= 1;
		}

		return result;
	}

	/**
	 * Calculates a double number to the power of a long. The implementation uses square-and-multiply to prevent
	 * calculation of expensive logarithms and exponents.
	 *
	 * @param base     the (floating point) base of the exponentiation.
	 * @param exponent the unsigned exponent.
	 * @return base<sup>exponent</sup>.
	 */
	public static double pow(double base, long exponent) {
		double result = 1;

		double squares = base;
		while (exponent > 0) {
			if ((exponent & 1) != 0) {
				result *= squares;
			}
			squares *= squares;
			exponent >>>= 1;
		}

		return result;
	}

	/**
	 * Sign-preserving version of {@link Math#pow(double, double)}.
	 */
	public static float powSign(float base, float power) {
		return Float.intBitsToFloat(Float.floatToRawIntBits((float)Math.pow(Math.abs(base), power))
		                            | Float.floatToRawIntBits(base) & 0x80000000);
	}

	/**
	 * Sign-preserving version of {@link Math#pow(double, double)}.
	 */
	public static double powSign(double base, double power) {
		return Double.longBitsToDouble(Double.doubleToRawLongBits(Math.pow(Math.abs(base), power))
		                               | Double.doubleToRawLongBits(base) & 0x8000000000000000L);
	}

	/**
	 * Sign-preserving version of {@link Math#sqrt(double)}.
	 */
	public static float sqrtSign(float value) {
		return Float.intBitsToFloat(Float.floatToRawIntBits((float)Math.sqrt(Math.abs(value)))
		                            | Float.floatToRawIntBits(value) & 0x80000000);
	}

	/**
	 * Sign-preserving version of {@link Math#sqrt(double)}.
	 */
	public static double sqrtSign(double value) {
		return Double.longBitsToDouble(Double.doubleToRawLongBits(Math.sqrt(Math.abs(value)))
		                               | Double.doubleToRawLongBits(value) & 0x8000000000000000L);
	}

	public static double acosh(double value) {
		return Math.log(value + (value + 1) * Math.sqrt((value - 1) / (value + 1)));
	}

	public static double cosh(double value) {
		return (Math.exp(-value) + Math.exp(value)) * 0.5;
	}

	public static double sinc(double value) {
		return value == 0 ? 1 : Math.sin(value) / value;
	}

	/**
	 * Calculates the greatest common divisor (GCD) of two integers.
	 *
	 * @param a the first number, generally the larger one.
	 * @param b the second number, generally the smaller one.
	 * @return the smallest number that divides both input numbers.
	 */
	public static int gcd(int a, int b) {
		// From Wikipedia
		if (a == 0) {
			return b;
		}

		while (b != 0) {
			if (a > b) {
				a -= b;
			} else {
				b -= a;
			}
		}

		return a;
	}

	/**
	 * Calculates the greatest common divisor (GCD) of two longs.
	 *
	 * @param a the first number, generally the larger one.
	 * @param b the second number, generally the smaller one.
	 * @return the smallest number that divides both input numbers.
	 */
	public static long gcd(long a, long b) {
		// From Wikipedia
		if (a == 0) {
			return b;
		}

		while (b != 0) {
			if (a > b) {
				a -= b;
			} else {
				b -= a;
			}
		}

		return a;
	}

	/**
	 * Returns the factorial of a value.
	 *
	 * @return value! Return values are undefined when value < 1 and for overflow conditions.
	 */
	public static long factorial(int value) {
		long out = 1;
		for (int i = 2; i <= value; i++) {
			out *= i;
		}

		return out;
	}

	public static int compareUnsigned(int lhs, int rhs) {
		if (rhs >= 0 && lhs < 0) {
			return 1;
		}
		if (rhs < 0 && lhs >= 0) {
			return -1;
		}

		return Integer.signum(lhs - rhs);
	}

	public static int compare(Number a, Number b) {
		if ((a instanceof Double || a instanceof Float) && (b instanceof Double || b instanceof Float)) {
			return Double.compare(a.doubleValue(), b.doubleValue());
		}

		// Note UnsignedLong is missing, this's because it's longValue() method is lossy.
		if ((a instanceof Long || a instanceof Integer || a instanceof Short || a instanceof Byte
		     || a instanceof UnsignedInteger || a instanceof UnsignedShort || a instanceof UnsignedByte
		     || a instanceof AtomicLong || a instanceof AtomicInteger)
		    && (b instanceof Long || b instanceof Integer || b instanceof Short || b instanceof Byte
		        || b instanceof UnsignedInteger || b instanceof UnsignedShort
		        || b instanceof UnsignedByte
		        || b instanceof AtomicLong || b instanceof AtomicInteger)) {
			return Long.compare(a.longValue(), b.longValue());
		}

		// Handle incompatible or larger types with BigDecimals.
		return toBigDecimal(a).compareTo(toBigDecimal(b));
	}

	/**
	 * Returns true if the {@link RectangularShape} has no size (surface area). This differs from {@link
	 * RectangularShape#isEmpty()} in that the latter also regards negative size as 'empty' whether this method regards
	 * such rectangles as having non-zero size. It is equivalent to the following code: {@code rect.getWidth() == 0 &&
	 * rect.getHeight() == 0}
	 * <p>
	 * Values of NaN are NOT regarded as zero-sized.
	 *
	 * @see #isDegenerate(RectangularShape)
	 */
	public static boolean isZeroSized(RectangularShape rect) {
		return rect.getWidth() == 0 || rect.getHeight() == 0;
	}

	/**
	 * Returns true if this {@link RectangularShape} doesn't specify valid, countable, coordinates. If either of the
	 * four coordinates is NaN or &plusmn;Infinite, it is degenerate. Zero-sized or negative-sized rectangles are still
	 * valid. Also, arithmetic between the coordinates must not be infinite. For example, a very positive X added to a
	 * very positive Width might result in an infinite value.
	 */
	public static boolean isDegenerate(RectangularShape rect) {
		return isDegenerate(rect.getX()) || isDegenerate(rect.getY()) ||
		       isDegenerate(rect.getWidth()) || isDegenerate(rect.getHeight()) ||
		       isDegenerate(rect.getMaxX()) || isDegenerate(rect.getMaxY());
	}

	public static BigDecimal toBigDecimal(Number num) {
		if (num instanceof Float ||
		    num instanceof Double) {
			return BigDecimal.valueOf(num.doubleValue());
		} else if (num instanceof Long ||
		           num instanceof Integer ||
		           num instanceof Short ||
		           num instanceof Byte ||
		           num instanceof UnsignedInteger ||
		           num instanceof UnsignedShort ||
		           num instanceof UnsignedByte ||
		           num instanceof AtomicLong ||
		           num instanceof AtomicInteger) {
			return new BigDecimal(num.longValue());
		} else if (num instanceof BigInteger) {
			return new BigDecimal((BigInteger)num);
		} else if (num instanceof BigDecimal) {
			return (BigDecimal)num;
		}

		try {
			return new BigDecimal(num.toString());
		} catch (NumberFormatException ex) {
			throw new IllegalArgumentException("The given Number (\"" + num + "\" of class " + num.getClass().getName()
			                                   + ") doesn't have a parsable toString() representation", ex);
		}
	}
}
