package nl.airsupplies.utilities.graphics;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import static java.awt.BasicStroke.CAP_BUTT;
import static java.awt.BasicStroke.CAP_ROUND;
import static java.awt.BasicStroke.JOIN_MITER;
import static java.awt.BasicStroke.JOIN_ROUND;

import nl.airsupplies.utilities.annotation.ConstClass;

/**
 * @author Mark Jeronimus
 */
// Created 2017-01-31
@ConstClass
public final class StaticStrokes {
	public static final Stroke DEFAULT_SQUARE_STROKE     = new BasicStroke(1, CAP_BUTT, JOIN_MITER, 10);
	public static final Stroke DEFAULT_ROUND_STROKE      = new BasicStroke(1, CAP_ROUND, JOIN_ROUND);
	public static final Stroke DEFAULT_FAT_SQUARE_STROKE = new BasicStroke(3, CAP_BUTT, JOIN_MITER, 10);
	public static final Stroke DEFAULT_FAT_ROUND_STROKE  = new BasicStroke(3, CAP_ROUND, JOIN_ROUND);

	/**
	 * To create a text with an outline, first {@link Graphics2D#draw(Shape) draw()} the text shape
	 * with this stroke, then {@link Graphics2D#fill(Shape) fill()} the same text shape on top of it with another
	 * color.
	 */
	public static final Stroke DEFAULT_TEXT_OUTLINE_STROKE = new BasicStroke(2.5f, CAP_ROUND, JOIN_ROUND);
}
