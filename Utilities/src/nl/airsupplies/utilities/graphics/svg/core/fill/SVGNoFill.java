package nl.airsupplies.utilities.graphics.svg.core.fill;

import java.io.IOException;

import nl.airsupplies.utilities.graphics.svg.core.SVGFill;

/**
 * @author Mark Jeronimus
 */
// Created 2023-01-23
public final class SVGNoFill implements SVGFill {
	public static final SVGNoFill INSTANCE = new SVGNoFill();

	private SVGNoFill() {
	}

	@Override
	public void encodeAttributeValue(Appendable out) throws IOException {
		out.append("none");
	}
}
