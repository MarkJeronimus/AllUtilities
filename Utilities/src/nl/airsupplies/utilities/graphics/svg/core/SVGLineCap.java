package nl.airsupplies.utilities.graphics.svg.core;

import java.util.function.Supplier;

/**
 * @author Mark Jeronimus
 */
// Created 2018-01-25
public enum SVGLineCap implements Supplier<String> {
	BUTT("butt"), // Default
	ROUND("round"),
	SQUARE("square");

	private final String name;

	SVGLineCap(String name) {
		this.name = name;
	}

	@Override
	public String get() {
		return name;
	}
}
