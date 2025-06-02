package nl.airsupplies.utilities.graphics.svg.core;

import java.io.IOException;

import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNotDegenerate;

/**
 * @author Mark Jeronimus
 */
// Created 2023-01-23
public class SVGLength implements SVGDistance {
	private final float length;

	public SVGLength(float length) {
		this.length = requireNotDegenerate(length, "length");
	}

	@Override
	public void encodeAttributeValue(Appendable out) throws IOException {
		out.append(Float.toString(length));
	}
}
