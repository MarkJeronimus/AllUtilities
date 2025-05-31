package nl.airsupplies.utilities.graphics.svg.core.fill;

import java.io.IOException;

import nl.airsupplies.utilities.graphics.svg.core.SVGContainer;
import nl.airsupplies.utilities.graphics.svg.core.SVGDef;
import nl.airsupplies.utilities.graphics.svg.core.SVGDistance;
import nl.airsupplies.utilities.graphics.svg.core.SVGFill;
import nl.airsupplies.utilities.graphics.svg.core.SVGLength;
import static nl.airsupplies.utilities.validator.StringValidatorUtilities.requireStringNotEmpty;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

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

		getAttributes().setID(requireStringNotEmpty(id, "id"));
		setCoordinate(x, y);
		setSize(width, height);
	}

	public SVGPattern(String id, SVGDistance x, SVGDistance y, SVGDistance width, SVGDistance height) {
		super("pattern", 8);

		getAttributes().setID(requireStringNotEmpty(id, "id"));
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
