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

package org.digitalmodular.utilities.graphics;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import static java.awt.BasicStroke.CAP_BUTT;
import static java.awt.BasicStroke.CAP_ROUND;
import static java.awt.BasicStroke.JOIN_MITER;
import static java.awt.BasicStroke.JOIN_ROUND;

import org.digitalmodular.utilities.annotation.ConstClass;

/**
 * @author Mark Jeronimus
 */
// Created 2017-01-31
@ConstClass
public final class StaticStrokes {
	public static final Stroke DEFAULT_SQUARE_STROKE     = new BasicStroke(1, CAP_BUTT, JOIN_MITER);
	public static final Stroke DEFAULT_ROUND_STROKE      = new BasicStroke(1, CAP_ROUND, JOIN_ROUND);
	public static final Stroke DEFAULT_FAT_SQUARE_STROKE = new BasicStroke(3, CAP_BUTT, JOIN_MITER);
	public static final Stroke DEFAULT_FAT_ROUND_STROKE  = new BasicStroke(3, CAP_ROUND, JOIN_ROUND);

	/**
	 * To create a text with an outline, first {@link Graphics2D#draw(Shape) draw()} the text shape
	 * with this stroke, then {@link Graphics2D#fill(Shape) fill()} the same text shape on top of it with another
	 * color.
	 */
	public static final Stroke DEFAULT_TEXT_OUTLINE_STROKE = new BasicStroke(2.5f, CAP_ROUND, JOIN_ROUND);
}
