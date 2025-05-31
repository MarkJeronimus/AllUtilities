package nl.airsupplies.utilities.graphics.svg.core;

import java.util.function.Supplier;

/**
 * @author Mark Jeronimus
 */
// Created 2023-01-23
public enum SVGImageRendering implements Supplier<String> {
	AUTO("auto"), // Default
	OPTIMIZE_SPEED("optimizeSpeed"),
	OPTIMIZE_QUALITY("optimizeQuality");

	private final String name;

	SVGImageRendering(String name) {
		this.name = name;
	}

	@Override
	public String get() {
		return name;
	}
}
