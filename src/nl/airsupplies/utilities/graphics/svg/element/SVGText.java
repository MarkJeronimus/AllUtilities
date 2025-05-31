package nl.airsupplies.utilities.graphics.svg.element;

import java.io.IOException;

import nl.airsupplies.utilities.graphics.svg.core.SVGDistance;
import nl.airsupplies.utilities.graphics.svg.core.SVGElement;
import nl.airsupplies.utilities.graphics.svg.core.SVGLength;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * @author Mark Jeronimus
 */
// Created 2024-06-04
public final class SVGText extends SVGElement {
	private String      string;
	private SVGDistance x;
	private SVGDistance y;
	private SVGLength   fontSize;

	public SVGText(String string, float x, float y, float fontSize) {
		super("text");

		setString(string);
		setCoordinate(x, y);
		setFontSize(fontSize);
	}

	public SVGText(String string, SVGDistance x, SVGDistance y, SVGLength fontSize) {
		super("rect");

		setString(string);
		setCoordinate(x, y);
		setFontSize(fontSize);
	}

	public String getString() {
		return string;
	}

	private void setString(String string) {
		this.string = requireNonNull(string, "string");
	}

	public SVGDistance getX() {
		return x;
	}

	public void setX(SVGDistance x) {
		this.x = requireNonNull(x, "x");
	}

	public SVGDistance getY() {
		return y;
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

	public SVGDistance getFontSize() {
		return fontSize;
	}

	public void setFontSize(float fontSize) {
		this.fontSize = new SVGLength(fontSize);
	}

	public void setFontSize(SVGLength fontSize) {
		this.fontSize = requireNonNull(fontSize, "fontSize");
	}

	@Override
	protected void encodeExtraAttributes(Appendable out) throws IOException {
		encodeAttribute(out, "x", x);
		encodeAttribute(out, "y", y);
		encodeAttribute(out, "font-size", fontSize);
	}

	@Override
	protected boolean hasBody() {
		return true;
	}

	@Override
	protected void encodeBody(Appendable out, int indentation) throws IOException {
		indent(out, indentation);

		out.append("<tspan>");
		out.append(string);
		out.append("</tspan>");

		if (indentation >= 0) {
			out.append('\n');
		}
	}
}
