package nl.airsupplies.utilities.graphics;

import java.awt.Paint;
import java.awt.Stroke;

import static nl.airsupplies.utilities.graphics.StaticStrokes.DEFAULT_ROUND_STROKE;

/**
 * @author Mark Jeronimus
 */
// Created 2016-03-24
public class GraphicsStyle {
	public final Paint   fillPaint;
	public final Paint   strokePaint;
	public final Stroke  stroke;
	public final boolean strokeFirst;

	public GraphicsStyle(Paint fillPaint) {
		this.fillPaint = fillPaint;
		strokePaint    = null;
		stroke         = null;
		strokeFirst    = false;
	}

	public GraphicsStyle(Paint fillPaint, Paint strokePaint) {
		this.fillPaint   = fillPaint;
		this.strokePaint = strokePaint;
		stroke           = DEFAULT_ROUND_STROKE;
		strokeFirst      = false;
	}

	public GraphicsStyle(Paint strokePaint, Stroke stroke) {
		fillPaint        = null;
		this.strokePaint = strokePaint;
		this.stroke      = stroke;
		strokeFirst      = false;
	}

	public GraphicsStyle(Paint fillPaint, Paint strokePaint, Stroke stroke) {
		this.fillPaint   = fillPaint;
		this.strokePaint = strokePaint;
		this.stroke      = stroke;
		strokeFirst      = false;
	}

	public GraphicsStyle(Paint fillPaint, Paint strokePaint, Stroke stroke, boolean strokeFirst) {
		this.fillPaint   = fillPaint;
		this.strokePaint = strokePaint;
		this.stroke      = stroke;
		this.strokeFirst = strokeFirst;
	}

	public GraphicsStyle setFillPaint(Paint fillPaint) {
		return new GraphicsStyle(fillPaint, strokePaint, stroke, strokeFirst);
	}

	public GraphicsStyle setStrokePaint(Paint strokePaint) {
		return new GraphicsStyle(fillPaint, strokePaint, stroke, strokeFirst);
	}

	public GraphicsStyle setStroke(Stroke stroke) {
		return new GraphicsStyle(fillPaint, strokePaint, stroke, strokeFirst);
	}

	public GraphicsStyle setStroke(Paint strokePaint, Stroke stroke) {
		return new GraphicsStyle(fillPaint, strokePaint, stroke, strokeFirst);
	}

	public GraphicsStyle setStrokeFirst(boolean strokeFirst) {
		return new GraphicsStyle(fillPaint, strokePaint, stroke, strokeFirst);
	}
}
