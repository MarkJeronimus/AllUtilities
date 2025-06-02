package nl.airsupplies.utilities.graphics.svg.core;

import java.io.IOException;

/**
 * @author Mark Jeronimus
 */
// Created 2023-01-23
public interface SVGDistance extends SVGAttributeValue {
	/**
	 * Encodes the value for the coordinate
	 * <p>
	 * Examples: "75", "100%"
	 */
	@Override
	void encodeAttributeValue(Appendable out) throws IOException;
}
