package nl.airsupplies.utilities.graphics.svg.core.fill;

import java.awt.Color;
import java.io.IOException;

import nl.airsupplies.utilities.graphics.svg.core.SVGFill;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * @author Mark Jeronimus
 */
// Created 2023-01-23
public final class SVGSolidColor implements SVGFill {
	public static final SVGSolidColor BLACK = new SVGSolidColor(Color.BLACK);
	public static final SVGSolidColor WHITE = new SVGSolidColor(Color.WHITE);

	private final Color color;

	public SVGSolidColor(Color color) {
		this.color = requireNonNull(color, "color");
	}

	@Override
	public void encodeAttributeValue(Appendable out) throws IOException {
		out.append(String.format("#%06x", color.getRGB() & 0x00FFFFFF));
	}
}
