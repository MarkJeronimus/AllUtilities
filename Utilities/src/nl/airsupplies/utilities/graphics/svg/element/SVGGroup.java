package nl.airsupplies.utilities.graphics.svg.element;

import nl.airsupplies.utilities.graphics.svg.core.SVGContainer;

/**
 * @author Mark Jeronimus
 */
// Created 2023-01-23
public class SVGGroup extends SVGContainer {
	public SVGGroup(int initialCapacity) {
		super("g", initialCapacity);
	}
}
