package nl.airsupplies.utilities.graphics.gradient;

import java.util.Arrays;

import nl.airsupplies.utilities.graphics.color.Color3f;
import nl.airsupplies.utilities.graphics.color.Color4f;
import static nl.airsupplies.utilities.validator.ArrayValidatorUtilities.requireArrayLengthAtLeast;
import static nl.airsupplies.utilities.validator.ArrayValidatorUtilities.requireArrayLengthsMatch;
import static nl.airsupplies.utilities.validator.ArrayValidatorUtilities.requireArrayValueRange;
import static nl.airsupplies.utilities.validator.ArrayValidatorUtilities.requireArrayValuesNonNull;
import static nl.airsupplies.utilities.validator.ArrayValidatorUtilities.requireStrictlyIncreasing;

/**
 * @author Mark Jeronimus
 */
// Created 2024-07-24 copied from TransparentGradient
public class ControlPointGradient extends Gradient {
	private final Color4f[] colors;
	private final double[]  fractions;

	public ControlPointGradient(Color3f[] colors, ClampingMode clampingMode) {
		this(requireArrayLengthAtLeast(2, colors, "colors"),
		     clampingMode,
		     makeEquallySpacedFractions(colors.length, clampingMode));
	}

	public ControlPointGradient(Color4f[] colors, ClampingMode clampingMode) {
		this(requireArrayLengthAtLeast(2, colors, "colors"),
		     clampingMode,
		     makeEquallySpacedFractions(colors.length, clampingMode));
	}

	public ControlPointGradient(Color3f[] colors, ClampingMode clampingMode, double[] fractions) {
		super(clampingMode, false);
		requireArrayLengthsMatch(colors, fractions, "colors", "fractions");
		this.colors = toColor4f(requireArrayValuesNonNull(colors, "colors"));
		requireArrayValueRange(0.0, 1.0, fractions, "fractions");
		this.fractions = requireStrictlyIncreasing(fractions, "fractions");
	}

	public ControlPointGradient(Color4f[] colors, ClampingMode clampingMode, double[] fractions) {
		super(clampingMode, true);
		requireArrayLengthsMatch(colors, fractions, "colors", "fractions");
		this.colors = requireArrayValuesNonNull(colors, "colors");
		requireArrayValueRange(0.0, 1.0, fractions, "fractions");
		this.fractions = requireStrictlyIncreasing(fractions, "fractions");
	}

	private static double[] makeEquallySpacedFractions(int length, ClampingMode clampingMode) {
		double[] fractions = new double[length];

		double limit = clampingMode == ClampingMode.REPEAT || clampingMode == ClampingMode.LOOP ?
		               length :    // Don't include 1.0
		               length - 1; // Include 1.0

		for (int i = 0; i < length; i++) {
			fractions[i] = i / limit;
		}

		return fractions;
	}

	private static Color4f[] toColor4f(Color3f[] colors) {
		return Arrays.stream(colors).map(c -> new Color4f(c, 1.0f)).toArray(Color4f[]::new);
	}

	@Override
	public Color4f get(double position) {
		position = applyEdgeBehavior(position);

		// index<0 means (-index-1) is the index *after* the position.
		// (-index-1) can never be 0 because position≥0.
		int index = Arrays.binarySearch(fractions, position);

		if (index >= 0) {
			return colors[index];
		} else {
			index = -index - 1;

			Color4f after;
			double  toFraction;
			if (index == colors.length) {
				after      = colors[0];
				toFraction = fractions[0];
			} else {
				after      = colors[index];
				toFraction = fractions[index];
			}

			index--;
			Color4f before       = colors[index];
			double  fromFraction = fractions[index];

			// TODO Optimize with precalculated fraction diff table
			float f = (float)((position - fromFraction) / (toFraction - fromFraction));
			return before.lerp(after, f);
		}
	}
}
