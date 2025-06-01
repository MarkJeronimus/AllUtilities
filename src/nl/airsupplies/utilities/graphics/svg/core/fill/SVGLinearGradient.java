package nl.airsupplies.utilities.graphics.svg.core.fill;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.airsupplies.utilities.graphics.svg.core.SVGDef;
import nl.airsupplies.utilities.graphics.svg.core.SVGElement;
import nl.airsupplies.utilities.graphics.svg.core.SVGFill;
import static nl.airsupplies.utilities.validator.StringValidatorUtilities.requireStringNotEmpty;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireAtLeast;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireBetween;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * @author Mark Jeronimus
 */
// Created 2023-01-23
public final class SVGLinearGradient extends SVGElement implements SVGDef, SVGFill {
	private       Point2D               fromCoordinate;
	private       Point2D               toCoordinate;
	private final List<SVGGradientStop> stops  = new ArrayList<>(4);
	private       boolean               sorted = true;

	public SVGLinearGradient(String id, float fromX, float fromY, float toX, float toY) {
		super("linearGradient");

		getAttributes().setID(requireStringNotEmpty(id, "id"));
		setFromCoordinate(fromX, fromY);
		setToCoordinate(toX, toY);
	}

	public SVGLinearGradient(String id, Point2D fromCoordinate, Point2D toCoordinate) {
		super("linearGradient");

		getAttributes().setID(requireStringNotEmpty(id, "id"));
		setFromCoordinate(fromCoordinate);
		setToCoordinate(toCoordinate);
	}

	public Point2D getFromCoordinate() {
		return fromCoordinate;
	}

	public void setFromCoordinate(float x, float y) {
		requireAtLeast(0, x, "x");
		requireAtLeast(0, y, "y");

		fromCoordinate = new Point2D.Double(x, y);
	}

	public void setFromCoordinate(Point2D fromCoordinate) {
		requireNonNull(fromCoordinate, "fromCoordinate");
		requireAtLeast(0, fromCoordinate.getX(), "x");
		requireAtLeast(0, fromCoordinate.getY(), "y");

		this.fromCoordinate = fromCoordinate;
	}

	public Point2D getToCoordinate() {
		return toCoordinate;
	}

	public void setToCoordinate(float x, float y) {
		requireAtLeast(0, x, "x");
		requireAtLeast(0, y, "y");

		toCoordinate = new Point2D.Double(x, y);
	}

	public void setToCoordinate(Point2D toCoordinate) {
		requireNonNull(toCoordinate, "toCoordinate");
		requireAtLeast(0, toCoordinate.getX(), "x");
		requireAtLeast(0, toCoordinate.getY(), "y");

		this.toCoordinate = toCoordinate;
	}

	public void addStop(float offset, Color color) {
		requireBetween(0.0f, 1.0f, offset, "offset");
		requireNonNull(color, "color");
		stops.add(new SVGGradientStop(offset, color));

		sorted = stops.size() < 2;
	}

	public int size() {
		return stops.size();
	}

	public SVGGradientStop get(int index) {
		checkSorted();

		return stops.get(index);
	}

	private void checkSorted() {
		if (!sorted) {
			Collections.sort(stops);
			sorted = true;
		}
	}

	@Override
	protected void encodeExtraAttributes(Appendable out) throws IOException {
		encodeAttribute(out, "x1", (float)fromCoordinate.getX());
		encodeAttribute(out, "y1", (float)fromCoordinate.getY());
		encodeAttribute(out, "x2", (float)toCoordinate.getX());
		encodeAttribute(out, "y2", (float)toCoordinate.getY());
	}

	@Override
	protected boolean hasBody() {
		return !stops.isEmpty();
	}

	@Override
	protected void encodeBody(Appendable out, int indentation) throws IOException {
		checkSorted();

		for (SVGGradientStop stop : stops) {
			indent(out, indentation);

			out.append("<stop");
			String result;
			Color  color = stop.getColor();
			if (color.getAlpha() < 0xFF) {
				result = String.format("#%08x", color.getRGB());
			} else {
				result = String.format("#%06x", color.getRGB() & 0x00FFFFFF);
			}
			encodeAttribute(out, "stop-color", result);
			encodeAttribute(out, "offset", Float.toString(stop.getOffset()));
			encodeAttribute(out, "gradientUnits", "userSpaceOnUse");
//			encodeAttribute(out, "gradientUnits", "userSpaceOnUse|objectBoundingBox");
//			encodeAttribute(out, "spreadMethod", "pad|reflect|repeat");
			out.append("/>");

			if (indentation >= 0) {
				out.append('\n');
			}
		}
	}

	@Override
	public void encodeAttributeValue(Appendable out) throws IOException {
		String id = getAttributes().getID();

		if (id.isEmpty()) {
			throw new IllegalStateException("ID attribute not set for linearGradient (from " + fromCoordinate +
			                                " to " + toCoordinate + ", with " + size() + " stops)");
		}

		out.append("url(#").append(id).append(')');
	}

	/**
	 * @author Mark Jeronimus
	 */
	// Created 2023-01-23
	public static final class SVGGradientStop implements Comparable<SVGGradientStop> {
		private final float offset;
		private final Color color;

		private SVGGradientStop(float offset, Color color) {
			this.offset = offset;
			this.color  = color;
		}

		public float getOffset() {
			return offset;
		}

		public Color getColor() {
			return color;
		}

		@Override
		public int compareTo(SVGGradientStop other) {
			return Float.compare(offset, other.offset);
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) {
				return true;
			}

			if (o == null || getClass() != o.getClass()) {
				return false;
			}

			SVGGradientStop other = (SVGGradientStop)o;
			return Float.floatToIntBits(getOffset()) == Float.floatToIntBits(other.getOffset()) &&
			       getColor().equals(other.getColor());
		}

		@Override
		public int hashCode() {
			int hashCode = 0x811C9DC5;
			hashCode = 0x01000193 * (hashCode ^ Float.hashCode(getOffset()));
			hashCode = 0x01000193 * (hashCode ^ getColor().hashCode());
			return hashCode;
		}
	}
}
