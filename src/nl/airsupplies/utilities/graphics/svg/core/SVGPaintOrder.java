/*
 * This file is part of PAO.
 *
 * Copyleft 2024 Mark Jeronimus. All Rights Reversed.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

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
