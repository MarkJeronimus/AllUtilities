package org.digitalmodular.utilities.nodes;

/**
 * @author Mark Jeronimus
 */
// Created 2014-04-23
public enum ParameterCurve {
	/**
	 * Linear range.
	 * <p>
	 * Meant to be used where positive and negative in a fixed range are used, usually offsets.
	 * Regular increments in x cause regular increments in value.
	 * <pre>value = min + (max-min) * x</pre>
	 */
	LINEAR,
	/**
	 * Logarithmic range.
	 * <p>
	 * Meant to be used where arbitrary positive values are used, usually multipliers.
	 * Regular increments in x cause regular factors in value.
	 * <pre>value = min * pow(max/min, x)</pre>
	 */
	LOGARITHMIC,
	/**
	 * Logarithmic range.
	 * <p>
	 * Meant to be used as an exponent value.
	 * Value swings between 0 and &infin; for x between 0 and 1.
	 * Value can be limited depending on min and max (full range at min=0, max=&infin;).
	 * <pre>value = 1 / (x + 1) - 1</pre>
	 */
	EXPONENT,
	/**
	 * Logarithmic range.
	 * <p>
	 * Meant to be used where arbitrary positive and negative values are used, usually multipliers.
	 * Value swings between -&infin; and &infin; for x between 0 and 1.
	 * Value can be limited depending on min sand max (full range at min=-&infin;, max=&infin;).
	 * <pre>value = tan((x - 0.5) * pi)</pre>
	 */
	UNBOUNDED
}
