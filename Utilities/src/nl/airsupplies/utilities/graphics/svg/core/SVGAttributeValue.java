package nl.airsupplies.utilities.graphics.svg.core;

import java.io.IOException;

/**
 * @author Mark Jeronimus
 */
// Created 2023-01-23
public interface SVGAttributeValue {
	void encodeAttributeValue(Appendable out) throws IOException;
}
