package nl.airsupplies.utilities.graphics.svg.core;

import java.util.function.Supplier;

/**
 * @author Mark Jeronimus
 */
// Created 2023-01-23
public enum SVGFillRule implements Supplier<String> {
	EVEN_ODD("evenodd"), // Default
	NONZERO("nonzero");

	private final String name;

	SVGFillRule(String name) {
		this.name = name;
	}

	@Override
	public String get() {
		return name;
	}
}
