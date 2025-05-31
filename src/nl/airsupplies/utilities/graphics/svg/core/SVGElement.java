package nl.airsupplies.utilities.graphics.svg.core;

import java.io.IOException;

import org.jetbrains.annotations.Nullable;

import static nl.airsupplies.utilities.validator.StringValidatorUtilities.requireStringNotEmpty;
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
		this.tagName = requireStringNotEmpty(tagName, "tagName");
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
