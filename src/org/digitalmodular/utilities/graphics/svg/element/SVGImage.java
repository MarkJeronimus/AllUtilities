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

import org.jetbrains.annotations.Nullable;

import org.digitalmodular.utilities.graphics.svg.core.SVGDistance;
import org.digitalmodular.utilities.graphics.svg.core.SVGElement;
import org.digitalmodular.utilities.graphics.svg.core.SVGLength;
import static org.digitalmodular.utilities.ValidatorUtilities.requireNonNull;

/**
 * @author Mark Jeronimus
 */
// Created 2018-01-25
public final class SVGImage extends SVGElement {
	private           SVGDistance x;
	private           SVGDistance y;
	private           SVGDistance width;
	private           SVGDistance height;
	private @Nullable String      href = null;

	public SVGImage(float x, float y, float width, float height) {
		super("image");

		setCoordinate(x, y);
		setSize(width, height);
	}

	public SVGImage(SVGDistance x, SVGDistance y, SVGDistance width, SVGDistance height) {
		super("image");

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

	public @Nullable String getHREF() {
		return href;
	}

	public void setHREF(@Nullable String href) {
		this.href = href;
	}

	@SuppressWarnings("SpellCheckingInspection")
	@Override
	protected void encodeExtraAttributes(Appendable out) throws IOException {
		encodeAttribute(out, "x", x);
		encodeAttribute(out, "y", y);
		encodeAttribute(out, "width", width);
		encodeAttribute(out, "height", height);
		encodeAttribute(out, "xlink:href", href);
	}
}
