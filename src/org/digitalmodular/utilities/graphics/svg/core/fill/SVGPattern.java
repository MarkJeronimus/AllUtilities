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

import java.io.IOException;

import org.digitalmodular.utilities.graphics.svg.core.SVGContainer;
import org.digitalmodular.utilities.graphics.svg.core.SVGDef;
import org.digitalmodular.utilities.graphics.svg.core.SVGDistance;
import org.digitalmodular.utilities.graphics.svg.core.SVGFill;
import org.digitalmodular.utilities.graphics.svg.core.SVGLength;
import static org.digitalmodular.utilities.StringValidatorUtilities.requireStringLengthAtLeast;
import static org.digitalmodular.utilities.ValidatorUtilities.requireNonNull;

/**
 * @author Mark Jeronimus
 */
// Created 2023-01-23
public final class SVGPattern extends SVGContainer implements SVGDef, SVGFill {
	private SVGDistance x;
	private SVGDistance y;
	private SVGDistance width;
	private SVGDistance height;

	public SVGPattern(String id, float x, float y, float width, float height) {
		super("pattern", 8);

		getAttributes().setID(requireStringLengthAtLeast(1, id, "id"));
		setCoordinate(x, y);
		setSize(width, height);
	}

	public SVGPattern(String id, SVGDistance x, SVGDistance y, SVGDistance width, SVGDistance height) {
		super("pattern", 8);

		getAttributes().setID(requireStringLengthAtLeast(1, id, "id"));
		setCoordinate(x, y);
		setSize(width, height);
	}

	public SVGDistance getX() {
		return x;
	}

	public SVGDistance getY() {
		return y;
	}

	public void setX(SVGDistance x) {
		this.x = requireNonNull(x, "x");
	}

	public void setY(SVGDistance y) {
		this.y = requireNonNull(y, "y");
	}

	public void setCoordinate(float x, float y) {
		this.x = new SVGLength(x);
		this.y = new SVGLength(y);
	}

	public void setCoordinate(SVGDistance x, SVGDistance y) {
		this.x = requireNonNull(x, "x");
		this.y = requireNonNull(y, "y");
	}

	public SVGDistance getWidth() {
		return width;
	}

	public SVGDistance getHeight() {
		return height;
	}

	public void setWidth(SVGDistance width) {
		this.width = requireNonNull(width, "width");
	}

	public void setHeight(SVGDistance height) {
		this.height = requireNonNull(height, "height");
	}

	public void setSize(float width, float height) {
		this.width  = new SVGLength(width);
		this.height = new SVGLength(height);
	}

	public void setSize(SVGDistance width, SVGDistance height) {
		this.width  = requireNonNull(width, "width");
		this.height = requireNonNull(height, "height");
	}

	@Override
	protected void encodeExtraAttributes(Appendable out) throws IOException {
		encodeAttribute(out, "x", x);
		encodeAttribute(out, "y", y);
		encodeAttribute(out, "width", width);
		encodeAttribute(out, "height", height);
		encodeAttribute(out, "gradientUnits", "userSpaceOnUse");
//			encodeAttribute(out, "gradientUnits", "userSpaceOnUse|objectBoundingBox");
	}

	@Override
	public void encodeAttributeValue(Appendable out) throws IOException {
		String id = getAttributes().getID();

		if (id.isEmpty()) {
			throw new IllegalStateException("ID attribute not set for pattern (with " + size() + " children)");
		}

		out.append("url(#").append(id).append(')');
	}
}
