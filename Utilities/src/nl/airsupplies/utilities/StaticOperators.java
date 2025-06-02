package nl.airsupplies.utilities;

import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import nl.airsupplies.utilities.annotation.StaticClass;

/**
 * @author Mark Jeronimus
 */
// Created 2025-06-01
@StaticClass
public final class StaticOperators {
	public static final DoubleUnaryOperator  ZERO_OPERATOR       = ignored -> 0;
	public static final DoubleUnaryOperator  UNITY_OPERATOR      = ignored -> 1;
	public static final DoubleUnaryOperator  NAN_OPERATOR        = ignored -> Double.NaN;
	// public static final DoubleBinaryOperator MIN              = Double::min;
	// public static final DoubleBinaryOperator MAX              = Double::max;
	// public static final DoubleBinaryOperator ADD              = Double::sum;
	public static final DoubleBinaryOperator SUBTRACT            = (a, b) -> a - b;
	public static final DoubleBinaryOperator SUBTRACT_REVERSE    = (a, b) -> b - a;
	public static final DoubleBinaryOperator MULTIPLY            = (a, b) -> a * b;
	public static final DoubleBinaryOperator DIVIDE              = (a, b) -> a / b;
	public static final DoubleBinaryOperator DIVIDE_REVERSE      = (a, b) -> b / a;
	public static final DoubleBinaryOperator SAFE_DIVIDE         = (a, b) -> Math.abs(b) < 1.0e-8 ? 0 : a / b;
	public static final DoubleBinaryOperator SAFE_DIVIDE_REVERSE = (a, b) -> Math.abs(a) < 1.0e-8 ? 0 : b / a;
}
