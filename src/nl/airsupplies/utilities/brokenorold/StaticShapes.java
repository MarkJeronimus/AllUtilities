package nl.airsupplies.utilities.brokenorold;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import nl.airsupplies.utilities.annotation.ConstClass;
import nl.airsupplies.utilities.annotation.ThreadBounded;
import nl.airsupplies.utilities.graphics.TextShape;

/**
 * Re-usable {@link Shape} instances for easy painting using {@link Graphics2D}.
 *
 * @author Mark Jeronimus
 */
// Created 2017-01-31
@ConstClass
@Deprecated
@ThreadBounded("AWT-EventQueue-0")
public final class StaticShapes {
	public static final Line2D      LINE      = new Line2D.Double();
	public static final Rectangle2D RECTANGLE = new Rectangle2D.Double();
	public static final Ellipse2D   ELLIPSE   = new Ellipse2D.Double();
	public static final Path2D      PATH      = new Path2D.Double();
	public static final TextShape   TEXT      = new TextShape();
}
