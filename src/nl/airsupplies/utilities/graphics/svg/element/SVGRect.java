package nl.airsupplies.utilities.graphics.svg.element;

import java.io.IOException;

import org.jetbrains.annotations.Nullable;

import nl.airsupplies.utilities.graphics.svg.core.SVGDistance;
import nl.airsupplies.utilities.graphics.svg.core.SVGElement;
import nl.airsupplies.utilities.graphics.svg.core.SVGLength;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * @author Mark Jeronimus
 */
// Created 2023-01-23
public final class SVGRect extends SVGElement {
	private           SVGDistance x;
	private           SVGDistance y;
	private           SVGDistance width;
	private           SVGDistance height;
	private @Nullable SVGDistance rx = null;
	private @Nullable SVGDistance ry = null;

	public SVGRect(float x, float y, float width, float height) {
		super("rect");

		setCoordinate(x, y);
		setSize(width, height);
	}

	public SVGRect(SVGDistance x, SVGDistance y, SVGDistance width, SVGDistance height) {
		super("rect");

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

	public @Nullable SVGDistance getRX() {
		return rx;
	}

	public @Nullable SVGDistance getRY() {
		return ry;
	}

	public void setRX(@Nullable SVGDistance rx) {
		this.rx = requireNonNull(rx, "rx");
	}

	public void setRY(@Nullable SVGDistance ry) {
		this.ry = requireNonNull(ry, "ry");
	}

	public void setCornerRadius(float rx) {
		this.rx = new SVGLength(rx);
		ry      = null;
	}

	public void setCornerRadius(SVGDistance rx) {
		this.rx = requireNonNull(rx, "rx");
		ry      = null;
	}

	public void setCornerRadii(float rx, float ry) {
		this.rx = new SVGLength(rx);
		this.ry = new SVGLength(ry);
	}

	public void setCornerRadii(SVGDistance rx, SVGDistance ry) {
		this.rx = requireNonNull(rx, "rx");
		this.ry = requireNonNull(ry, "ry");
	}

	@Override
	protected void encodeExtraAttributes(Appendable out) throws IOException {
		encodeAttribute(out, "x", x);
		encodeAttribute(out, "y", y);
		encodeAttribute(out, "width", width);
		encodeAttribute(out, "height", height);
		encodeAttribute(out, "rx", rx);
		encodeAttribute(out, "ry", ry);
	}
}
