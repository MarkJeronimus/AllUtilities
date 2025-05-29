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

import java.io.IOException;

import org.jetbrains.annotations.Nullable;

import static nl.airsupplies.utilities.validator.StringValidatorUtilities.requireStringLengthAtLeast;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * @author Mark Jeronimus
 */
// Created 2018-01-25
public abstract class SVGElement {
	private final String tagName;

	private SVGAttributes attributes = new SVGAttributes();
	private SVGTransform  transform  = SVGTransform.IDENTITY;

	protected SVGElement(String tagName) {
		this.tagName = requireStringLengthAtLeast(1, tagName, "tagName");
	}

	public String getTagName() {
		return tagName;
	}

	public final SVGAttributes getAttributes() {
		return attributes;
	}

	public void setAttributes(SVGAttributes attributes) {
		this.attributes = requireNonNull(attributes, "attributes");
	}

	public SVGTransform getTransform() {
		return transform;
	}

	public void setTransform(SVGTransform transform) {
		this.transform = requireNonNull(transform, "transform");
	}

	public final void encode(Appendable out, int indentation) throws IOException {
		indent(out, indentation);
		out.append("<").append(tagName);

		getAttributes().encode(out);
		getTransform().encode(out);
		encodeExtraAttributes(out);

		if (hasBody()) {
			out.append('>');
			if (indentation >= 0) {
				out.append('\n');
			}

			encodeBody(out, indentation + 1);

			indent(out, indentation);
			out.append("</").append(tagName).append('>');
			if (indentation >= 0) {
				out.append('\n');
			}
		} else {
			out.append("/>");
			if (indentation >= 0) {
				out.append("\n");
			}
		}
	}

	protected static void indent(Appendable out, int indentation) throws IOException {
		for (int i = indentation; i > 0; i--) {
			out.append("    ");
		}
	}

	protected void encodeExtraAttributes(Appendable out) throws IOException {
	}

	/**
	 * Convenience method to optionally append an attribute
	 */
	protected static void encodeAttribute(Appendable out, String name, @Nullable Object value) throws IOException {
		if (value != null) {
			out.append(' ').append(name).append("=\"");

			if (value instanceof SVGAttributeValue) {
				((SVGAttributeValue)value).encodeAttributeValue(out);
			} else {
				out.append(value.toString());
			}

			out.append('"');
		}
	}

	/**
	 * Returns {@code true} if encoding this element generates data between the opening and closing XML tags.
	 */
	protected boolean hasBody() {
		return false;
	}

	protected void encodeBody(Appendable out, int indentation) throws IOException {
	}
}
