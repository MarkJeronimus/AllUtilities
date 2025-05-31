package nl.airsupplies.utilities.graphics.svg.element;

import java.io.IOException;

import nl.airsupplies.utilities.graphics.svg.core.SVGDistance;
import nl.airsupplies.utilities.graphics.svg.core.SVGElement;
import nl.airsupplies.utilities.graphics.svg.core.SVGLength;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * @author Mark Jeronimus
 */
// Created 2018-01-25
public final class SVGEllipse extends SVGElement {
	private SVGDistance cx;
	private SVGDistance cy;
	private SVGDistance rx;
	private SVGDistance ry;

	public SVGEllipse(float cx, float cy, float rx, float ry) {
		super("ellipse");

		setCenter(cx, cy);
		setRadii(rx, ry);
	}

	public SVGEllipse(SVGDistance cx, SVGDistance cy, SVGDistance rx, SVGDistance ry) {
		super("ellipse");

		setCenter(cx, cy);
		setRadii(rx, ry);
	}

	public SVGDistance getCX() {
		return requireNonNull(cx, "cx");
	}

	public SVGDistance getCY() {
		return requireNonNull(cy, "cy");
	}

	public void setCX(SVGDistance cx) {
		this.cx = cx;
	}

	public void setCY(SVGDistance cy) {
		this.cy = cy;
	}

	public void setCenter(float cx, float cy) {
		this.cx = new SVGLength(cx);
		this.cy = new SVGLength(cy);
	}

	public void setCenter(SVGDistance cx, SVGDistance cy) {
		this.cx = requireNonNull(cx, "cx");
		this.cy = requireNonNull(cy, "cy");
	}

	public SVGDistance getRX() {
		return rx;
	}

	public SVGDistance getRY() {
		return ry;
	}

	public void setRX(SVGDistance rx) {
		this.rx = requireNonNull(rx, "rx");
	}

	public void setRY(SVGDistance ry) {
		this.ry = requireNonNull(ry, "ry");
	}

	public void setRadii(float rx, float ry) {
		this.rx = new SVGLength(rx);
		this.ry = new SVGLength(ry);
	}

	public void setRadii(SVGDistance rx, SVGDistance ry) {
		this.rx = requireNonNull(rx, "rx");
		this.ry = requireNonNull(ry, "ry");
	}

	@Override
	protected void encodeExtraAttributes(Appendable out) throws IOException {
		encodeAttribute(out, "cx", cx);
		encodeAttribute(out, "cy", cy);
		encodeAttribute(out, "rx", rx);
		encodeAttribute(out, "ry", ry);
	}
}
