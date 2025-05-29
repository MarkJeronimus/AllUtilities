/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2024 Mark Jeronimus. All Rights Reversed.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
