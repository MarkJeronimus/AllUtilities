package nl.airsupplies.utilities.graphics.svg.core;

import java.io.IOException;

/**
 * @author Mark Jeronimus
 */
// Created 2023-01-23
public interface SVGFill extends SVGAttributeValue {
	/**
	 * Encodes the value for a 'fill' or 'stroke' attribute
	 * <p>
	 * Examples: "none", "#FF0000", or "url(#id)"
	 */
	@Override
	void encodeAttributeValue(Appendable out) throws IOException;
}
