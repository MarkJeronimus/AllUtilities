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

package org.digitalmodular.utilities.graphics.svg.element;

import java.io.IOException;

import org.digitalmodular.utilities.graphics.svg.core.SVGElement;
import static org.digitalmodular.utilities.ValidatorUtilities.requireNonNull;

/**
 * @author Mark Jeronimus
 */
// Created 2018-01-26
public final class SVGLayer extends SVGGroup {
	private String name;

	public SVGLayer(String name, int initialCapacity) {
		super(initialCapacity);

		this.name = requireNonNull(name, "name");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = requireNonNull(name, "name");
	}

	@Override
	@SuppressWarnings("SpellCheckingInspection")
	protected void encodeExtraAttributes(Appendable out) throws IOException {
		SVGElement.encodeAttribute(out, "inkscape:groupmode", "layer");
		SVGElement.encodeAttribute(out, "inkscape:label", name);
	}
}
