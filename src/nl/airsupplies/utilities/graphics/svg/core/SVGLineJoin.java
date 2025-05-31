package nl.airsupplies.utilities.graphics.svg.core;

import java.util.function.Supplier;

/**
 * @author Mark Jeronimus
 */
// Created 2018-01-25
public enum SVGLineJoin implements Supplier<String> {
	ARCS("arcs"),
	BEVEL("bevel"),
	MITER("miter"), // Default,
	MITER_CLIP(" miter-clip"),
	ROUND("round");

	private final String name;

	SVGLineJoin(String name) {
		this.name = name;
	}

	@Override
	public String get() {
		return name;
	}
}
