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

package org.digitalmodular.utilities.graphics.svg.core.fill;

import java.awt.Color;
import java.io.IOException;

import org.digitalmodular.utilities.graphics.svg.core.SVGFill;
import static org.digitalmodular.utilities.ValidatorUtilities.requireNonNull;

/**
 * @author Mark Jeronimus
 */
// Created 2023-01-23
public final class SVGSolidColor implements SVGFill {
	public static final SVGSolidColor BLACK = new SVGSolidColor(Color.BLACK);
	public static final SVGSolidColor WHITE = new SVGSolidColor(Color.WHITE);

	private final Color color;

	public SVGSolidColor(Color color) {
		this.color = requireNonNull(color, "color");
	}

	@Override
	public void encodeAttributeValue(Appendable out) throws IOException {
		out.append(String.format("#%06x", color.getRGB() & 0x00FFFFFF));
	}
}
