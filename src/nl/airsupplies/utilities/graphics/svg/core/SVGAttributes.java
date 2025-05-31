package nl.airsupplies.utilities.graphics.svg.core;

import java.awt.Color;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.jetbrains.annotations.Nullable;

import nl.airsupplies.utilities.graphics.svg.core.fill.SVGNoFill;
import nl.airsupplies.utilities.graphics.svg.core.fill.SVGSolidColor;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireAtLeast;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireRange;

/**
 * @author Mark Jeronimus
 */
// Created 2018-01-25
@SuppressWarnings("SpellCheckingInspection")
public class SVGAttributes {
	//@formatter:off
	// When attributs would get this value, hide them.
	private static final Map<String, Object> DEFAULT_ATTRIBUTES;

	static {
		DEFAULT_ATTRIBUTES = new HashMap<>();
		DEFAULT_ATTRIBUTES.put("display"          , "");
		DEFAULT_ATTRIBUTES.put("fill"             , SVGSolidColor.BLACK);
		DEFAULT_ATTRIBUTES.put("fill-opacity"     , 1.0f);
		DEFAULT_ATTRIBUTES.put("fill-rule"        , SVGFillRule.EVEN_ODD);
		DEFAULT_ATTRIBUTES.put("id"               , "");
		DEFAULT_ATTRIBUTES.put("image-rendering"  , SVGImageRendering.AUTO);
		DEFAULT_ATTRIBUTES.put("opacity"          , 1.0f);
		DEFAULT_ATTRIBUTES.put("paint-order"      , SVGPaintOrder.FILL_STROKE_MARKER);
		DEFAULT_ATTRIBUTES.put("stroke"           , SVGNoFill.INSTANCE);
		DEFAULT_ATTRIBUTES.put("stroke-dasharray" , "");
		DEFAULT_ATTRIBUTES.put("stroke-dashoffset", 0.0f);
		DEFAULT_ATTRIBUTES.put("stroke-linecap"   , SVGLineCap.BUTT);
		DEFAULT_ATTRIBUTES.put("stroke-linejoin"  , SVGLineJoin.MITER);
		DEFAULT_ATTRIBUTES.put("stroke-opacity"   , 1.0f);
		DEFAULT_ATTRIBUTES.put("stroke-width"     , 1.0f);
	}
	//@formatter:on

	private final Map<String, Object> attributes = new HashMap<>(16);

	public void setVisible(boolean visible) {
		setCustomAttribute("display", visible ? "" : "none");
	}

	public boolean getVisible() {
		return !attributes.containsKey("display");
	}

	public @Nullable SVGFill getFill() {
		return (SVGFill)attributes.get("fill");
	}

	public void setFill(@Nullable SVGFill fill) {
		setCustomAttribute("fill", fill);
	}

	public void setFill(@Nullable Color fill) {
		setCustomAttribute("fill", fill == null ? null : new SVGSolidColor(fill));
	}

	public float getFillOpacity() {
		return (Float)getAttributeOrDefault("fill-opacity");
	}

	public void setFillOpacity(double fillOpacity) {
		requireRange(0.0f, 1.0f, (float)fillOpacity, "fillOpacity");

		setCustomAttribute("fill-opacity", (float)fillOpacity);
	}

	public @Nullable SVGFillRule getFillRule() {
		return (SVGFillRule)getAttributeOrDefault("fill-rule");
	}

	public void setFillRule(@Nullable SVGFillRule fillRule) {
		setCustomAttribute("fill-rule", fillRule);
	}

	public String getID() {
		return (String)getAttributeOrDefault("id");
	}

	public void setID(String id) {
		requireNonNull(id, "id");

		setCustomAttribute("id", id);
	}

	public @Nullable SVGImageRendering getImageRendering() {
		return (SVGImageRendering)getAttributeOrDefault("image-rendering");
	}

	public void setImageRendering(@Nullable SVGImageRendering imageRendering) {
		setCustomAttribute("image-rendering", imageRendering);
	}

	public float getOpacity() {
		return (Float)getAttributeOrDefault("opacity");
	}

	public void setOpacity(double opacity) {
		requireRange(0.0f, 1.0f, (float)opacity, "opacity");

		setCustomAttribute("opacity", (float)opacity);
	}

	public @Nullable SVGPaintOrder getPaintOrder() {
		return (SVGPaintOrder)getAttributeOrDefault("paint-order");
	}

	public void setPaintOrder(@Nullable SVGPaintOrder paintOrder) {
		setCustomAttribute("paint-order", paintOrder);
	}

	public @Nullable SVGFill getStroke() {
		return (SVGFill)getAttributeOrDefault("stroke");
	}

	public void setStroke(@Nullable SVGFill stroke) {
		setCustomAttribute("stroke", stroke);
	}

	public void setStroke(@Nullable Color stroke) {
		setCustomAttribute("stroke", stroke == null ? null : new SVGSolidColor(stroke));
	}

	public String getDashArray() {
		return (String)getAttributeOrDefault("stroke-dasharray");
	}

	public void setDashArray(String dashArray) {
		requireNonNull(dashArray, "dashArray");

		setCustomAttribute("stroke-dasharray", dashArray);
	}

	public float getDashOffset() {
		return (Float)getAttributeOrDefault("stroke-dashoffset");
	}

	public void setDashOffset(double strokeDashOffset) {
		requireAtLeast(0.0f, (float)strokeDashOffset, "strokeDashOffset");

		setCustomAttribute("stroke-dashoffset", (float)strokeDashOffset);
	}

	public @Nullable SVGLineCap getLineCap() {
		return (SVGLineCap)getAttributeOrDefault("stroke-linecap");
	}

	public void setLineCap(@Nullable SVGLineCap lineCap) {
		setCustomAttribute("stroke-linecap", lineCap);
	}

	public @Nullable SVGLineJoin getLineJoin() {
		return (SVGLineJoin)getAttributeOrDefault("stroke-linejoin");
	}

	public void setLineJoin(@Nullable SVGLineJoin lineJoin) {
		setCustomAttribute("stroke-linejoin", lineJoin);
	}

	public float getStrokeOpacity() {
		return (Float)getAttributeOrDefault("stroke-opacity");
	}

	public void setStrokeOpacity(double strokeOpacity) {
		requireRange(0.0f, 1.0f, (float)strokeOpacity, "strokeOpacity");

		setCustomAttribute("stroke-opacity", (float)strokeOpacity);
	}

	public float getStrokeWidth() {
		return (Float)getAttributeOrDefault("stroke-width");
	}

	public void setStrokeWidth(double strokeWidth) {
		requireRange(0.0f, 100.0f, (float)strokeWidth, "strokeWidth");

		setCustomAttribute("stroke-width", (float)strokeWidth);
	}

	private Object getAttributeOrDefault(String key) {
		@Nullable Object value = attributes.get("id");

		if (value == null) {
			return DEFAULT_ATTRIBUTES.get(key);
		}

		return value;
	}

	public void setCustomAttribute(String key, @Nullable Object value) {
		@Nullable Object defaultValue = DEFAULT_ATTRIBUTES.get(key);

		if (value == null || value.equals(defaultValue)) {
			attributes.remove(key);
			return;
		}

		if (defaultValue != null && value instanceof Float) {
			float defaultFloat = (Float)defaultValue;
			float valueFloat   = (Float)value;
			if (Math.abs(defaultFloat - valueFloat) < 1.0e-6f) {
				attributes.remove(key);
				return;
			}
		}

		attributes.put(key, value);
	}

	public void encode(Appendable out) throws IOException {
		for (Map.Entry<String, Object> attribute : attributes.entrySet()) {
			String property = attribute.getKey();
			Object value    = attribute.getValue();

			out.append(' ').append(property).append("=\"");

			if (value instanceof SVGFill) {
				((SVGFill)value).encodeAttributeValue(out);
			} else if (value instanceof Supplier) {
				out.append(((Supplier<?>)value).get().toString());
			} else {
				out.append(value.toString());
			}

			out.append('"');
		}
	}
}
