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

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import nl.airsupplies.utilities.annotation.ConstClass;
import nl.airsupplies.utilities.annotation.ThreadBounded;

/**
 * Re-usable {@link Shape} instances for easy painting using {@link Graphics2D}.
 *
 * @author Mark Jeronimus
 */
// Created 2017-01-31
@ConstClass
@ThreadBounded("AWT-EventQueue-0")
public final class StaticShapes {
	public static final Line2D      LINE      = new Line2D.Double();
	public static final Rectangle2D RECTANGLE = new Rectangle2D.Double();
	public static final Ellipse2D   ELLIPSE   = new Ellipse2D.Double();
	public static final Path2D      PATH      = new Path2D.Double();
	public static final TextShape   TEXT      = new TextShape();
}
