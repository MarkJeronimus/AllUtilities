package nl.airsupplies.utilities.graphics.svg.core;

import java.io.IOException;

import nl.airsupplies.utilities.graphics.svg.transform.SVGRotation;

/**
 * @author Mark Jeronimus
 */
// Created 2021-06-11
public interface SVGTransform {
	SVGTransform IDENTITY = new SVGRotation(0);

	SVGTransform overwrite(SVGTransform transform);

	void encode(Appendable out) throws IOException;
}
