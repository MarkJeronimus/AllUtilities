package nl.airsupplies.utilities.graphics.svg.core;

import java.io.IOException;

import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNotDegenerate;

/**
 * @author Mark Jeronimus
 */
// Created 2023-01-23
public class SVGPercentage implements SVGDistance {
	private final float percentage;

	public SVGPercentage(float percentage) {
		this.percentage = requireNotDegenerate(percentage, "percentage");
	}

	@Override
	public void encodeAttributeValue(Appendable out) throws IOException {
		out.append(Float.toString(percentage)).append('%');
	}
}
