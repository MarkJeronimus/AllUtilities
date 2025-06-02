package nl.airsupplies.utilities.graphics;

import java.awt.Graphics2D;
import static java.awt.RenderingHints.KEY_ALPHA_INTERPOLATION;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.KEY_COLOR_RENDERING;
import static java.awt.RenderingHints.KEY_DITHERING;
import static java.awt.RenderingHints.KEY_FRACTIONALMETRICS;
import static java.awt.RenderingHints.KEY_INTERPOLATION;
import static java.awt.RenderingHints.KEY_RENDERING;
import static java.awt.RenderingHints.KEY_STROKE_CONTROL;
import static java.awt.RenderingHints.KEY_TEXT_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY;
import static java.awt.RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_OFF;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import static java.awt.RenderingHints.VALUE_COLOR_RENDER_QUALITY;
import static java.awt.RenderingHints.VALUE_COLOR_RENDER_SPEED;
import static java.awt.RenderingHints.VALUE_DITHER_DISABLE;
import static java.awt.RenderingHints.VALUE_DITHER_ENABLE;
import static java.awt.RenderingHints.VALUE_FRACTIONALMETRICS_OFF;
import static java.awt.RenderingHints.VALUE_FRACTIONALMETRICS_ON;
import static java.awt.RenderingHints.VALUE_INTERPOLATION_BICUBIC;
import static java.awt.RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
import static java.awt.RenderingHints.VALUE_RENDER_QUALITY;
import static java.awt.RenderingHints.VALUE_RENDER_SPEED;
import static java.awt.RenderingHints.VALUE_STROKE_DEFAULT;
import static java.awt.RenderingHints.VALUE_STROKE_PURE;
import static java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB;
import static java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_OFF;

import nl.airsupplies.utilities.annotation.UtilityClass;

/**
 * @author Mark Jeronimus
 */
// Created 2025-06-01
@UtilityClass
public final class GraphicsUtilities {
	/**
	 * Enables or disables high quality rendering on a graphics context. Along with anti-aliasing, some other
	 * properties like fractional metrics, offset and interpolation are enabled.
	 * <p>
	 * The coordinate system is also offset by {@code (0.5, 0.5)} so integer coordinates become pixel centers rather
	 * than upper-left corners (makes it easier to draw nice 1px wide strokes).
	 *
	 * @param g           the {@link Graphics2D} context
	 * @param antialiased to enable or disable it
	 */
	public static void setAntialiased(Graphics2D g, boolean antialiased) {
		if (antialiased) {
			if (!isAntialiased(g)) {
				g.translate(0.5, 0.5);
			}

			g.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
			g.setRenderingHint(KEY_RENDERING, VALUE_RENDER_QUALITY);
			g.setRenderingHint(KEY_DITHERING, VALUE_DITHER_ENABLE);
			g.setRenderingHint(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_LCD_HRGB);
			g.setRenderingHint(KEY_FRACTIONALMETRICS, VALUE_FRACTIONALMETRICS_ON);
			g.setRenderingHint(KEY_INTERPOLATION, VALUE_INTERPOLATION_BICUBIC);
			g.setRenderingHint(KEY_ALPHA_INTERPOLATION, VALUE_ALPHA_INTERPOLATION_QUALITY);
			g.setRenderingHint(KEY_COLOR_RENDERING, VALUE_COLOR_RENDER_QUALITY);
			g.setRenderingHint(KEY_STROKE_CONTROL, VALUE_STROKE_PURE);
		} else {
			if (isAntialiased(g)) {
				g.translate(-0.5, -0.5);
			}

			g.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_OFF);
			g.setRenderingHint(KEY_RENDERING, VALUE_RENDER_SPEED);
			g.setRenderingHint(KEY_DITHERING, VALUE_DITHER_DISABLE);
			g.setRenderingHint(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_OFF);
			g.setRenderingHint(KEY_FRACTIONALMETRICS, VALUE_FRACTIONALMETRICS_OFF);
			g.setRenderingHint(KEY_INTERPOLATION, VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
			g.setRenderingHint(KEY_ALPHA_INTERPOLATION, VALUE_ALPHA_INTERPOLATION_SPEED);
			g.setRenderingHint(KEY_COLOR_RENDERING, VALUE_COLOR_RENDER_SPEED);
			g.setRenderingHint(KEY_STROKE_CONTROL, VALUE_STROKE_DEFAULT);
		}
	}

	@SuppressWarnings("ObjectEquality") // 'Enum' objects
	public static boolean isAntialiased(Graphics2D g) {
		return g.getRenderingHint(KEY_ANTIALIASING) == VALUE_ANTIALIAS_ON;
	}
}
