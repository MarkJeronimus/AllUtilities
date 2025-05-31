package nl.airsupplies.utilities.graphics.svg.core;

import java.util.function.Supplier;

/**
 * @author Mark Jeronimus
 */
// Created 2023-01-23
public enum SVGPaintOrder implements Supplier<String> {
	FILL_MARKER_STROKE("fill marker stroke"),
	FILL_STROKE_MARKER("fill stroke marker"), // Default
	MARKER_FILL_STROKE("marker fill stroke"),
	MARKER_STROKE_FILL("marker stroke fill"),
	STROKE_FILL_MARKER("stroke fill marker"),
	STROKE_MARKER_FILL("stroke marker fill");

	private final String name;

	SVGPaintOrder(String name) {
		this.name = name;
	}

	@Override
	public String get() {
		return name;
	}
}
