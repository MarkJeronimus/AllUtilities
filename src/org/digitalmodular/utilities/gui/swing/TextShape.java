/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2019 Mark Jeronimus. All Rights Reversed.
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
 * along with AllUtilities. If not, see <http://www.gnu.org/licenses/>.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.digitalmodular.utilities.gui.swing;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.jetbrains.annotations.Nullable;

import org.digitalmodular.utilities.NumberUtilities;
import org.digitalmodular.utilities.container.DoubleDimension;
import static org.digitalmodular.utilities.ValidatorUtilities.requireNonNull;
import static org.digitalmodular.utilities.ValidatorUtilities.requireNotDegenerate;

/**
 * A powerful text shape factory that generates text shapes to be drawn using
 * {@link Graphics2D}.
 * <p>
 *
 * @author Mark Jeronimus
 */
// Created 2010-12-28
// Changed 2014-01-09 Converted from TextShape.
public class TextShape {
	public static final double ALIGN_LEFT   = 0.0;
	public static final double ALIGN_CENTER = 0.5;
	public static final double ALIGN_RIGHT  = 1.0;
	public static final double ALIGN_TOP    = 0.0;
	public static final double ALIGN_BOTTOM = 1.0;

	// Bean properties
	private String  text     = "";
	private Point2D coordinate;
	private double  horizontalAlignment;
	private double  verticalAlignment;
	private double  rotation = 0;

	// Generated values
	private @Nullable Rectangle2D     bounds    = null;
	private @Nullable AffineTransform transform = null;
	private @Nullable Shape           textShape = null; // Force recalculation if null

	public TextShape() {
		this("", 0, 0);
	}

	public TextShape(String text, double x, double y) {
		this(text, new Point2D.Double(x, y));
	}

	public TextShape(String text, Point2D coordinate) {
		this(text, coordinate, ALIGN_LEFT, ALIGN_BOTTOM);
	}

	public TextShape(String text, double x, double y, double horizontalAlignment, double verticalAlignment) {
		this(text, new Point2D.Double(x, y), horizontalAlignment, verticalAlignment);
	}

	public TextShape(String text, Point2D coordinate, double horizontalAlignment, double verticalAlignment) {
		setText(text);
		setCoordinate(coordinate);
		setHorizontalAlignment(horizontalAlignment);
		setVerticalAlignment(verticalAlignment);
	}

	public String getText() { return text; }

	public final TextShape setText(String text) {
		this.text = requireNonNull(text, "text");

		textShape = null; // Force recalculation

		return this;
	}

	public final Point2D getCoordinate() { return coordinate; }

	public void setCoordinate(double x, double y) {
		setCoordinate(new Point2D.Double(x, y));
	}

	public final TextShape setCoordinate(Point2D coordinate) {
		this.coordinate = requireNotDegenerate(coordinate, "coordinate");

		textShape = null; // Force recalculation

		return this;
	}

	public final double getHorizontalAlignment() { return horizontalAlignment; }

	public final TextShape setHorizontalAlignment(double horizontalAlignment) {
		this.horizontalAlignment = requireNotDegenerate(horizontalAlignment, "horizontalAlignment");

		textShape = null; // Force recalculation

		return this;
	}

	public final double getVerticalAlignment() { return verticalAlignment; }

	public final TextShape setVerticalAlignment(double verticalAlignment) {
		this.verticalAlignment = requireNotDegenerate(verticalAlignment, "verticalAlignment");

		textShape = null; // Force recalculation

		return this;
	}

	/**
	 * Returns the rotation, in radians.
	 */
	public final double getRotation() { return rotation; }

	/**
	 * Sets the rotation, in radians.
	 *
	 * @return itself, so setters can be chained
	 */
	public final TextShape setRotation(double rotation) {
		this.rotation = requireNotDegenerate(rotation, "rotation");

		textShape = null; // Force recalculation

		return this;
	}

	/**
	 * Returns true if the text is rotated by a multiple of 90 degrees.
	 * <p>
	 * To prevent round-off errors, a tolerance of 0.09 degrees (0.00157
	 * radians, 1/1000th of a quadrant) is allowed.
	 */
	private boolean isAxisAligned() {
		double axisMultiple = NumberUtilities.floorMod(rotation / Math.PI * 2000 + 0.5, 1000);
		return axisMultiple < 1;
	}

	/**
	 * Calculates the amount of pixels two labels with the same properties need
	 * to be separated horizontally <i>or</i> vertically to be exactly touching.
	 * This takes into account the text, font, and rotation, and assumes
	 * antialiasing is on.
	 * <p>
	 * While 'virtually' generated the text shape, if it has already been
	 * generated and no properties have changed, a cached version is used.
	 */
	public Dimension2D calculateNonOverlappingSpacing(Graphics2D g) {
		// Calculate bounds and transform, if not already calculated
		calculateShape(g);
		assert bounds != null;

		// Rotate the bounding box
		double cos = Math.abs(Math.cos(rotation));
		double sin = Math.abs(Math.sin(rotation));
		double xh  = bounds.getWidth() / cos;
		double xv  = bounds.getHeight() / sin;
		double yh  = bounds.getWidth() / sin;
		double yv  = bounds.getHeight() / cos;

		// Calculate for how much of the X ans Y axes intersect the rotated box
		return new DoubleDimension(Math.min(xh, xv), Math.min(yh, yv));
	}

	/**
	 * Renders the text outline, given the bean properties and the Graphics2D
	 * context. If it has already been generated and no properties have changed,
	 * a cached version is returned.
	 */
	public Shape calculateShape(Graphics2D g) {
		// Already generated?
		if (textShape != null)
			return textShape;

		// Get the context that contains DPI, hints, etc.
		FontRenderContext frc = g.getFontRenderContext();
		// Get the 'styled character data'
		TextLayout layout = new TextLayout(text, g.getFont(), frc);

		boolean isAntialiased = frc.isAntiAliased();

		// Get the metrics that determines the bounds, ascend, descend, etc.
		FontMetrics metrics = g.getFontMetrics(g.getFont());
		bounds = metrics.getStringBounds(text, g);
		assert bounds != null;

		// Rotate the text around it's anchor point, then move it to the
		// designated position. Because AffineTransform uses pre-concatenation,
		// apply the steps in reverse order. Steps are:
		// (0) Calculate anchor of text based on bounding box,
		// 1. Move anchor of text to origin,
		// 2. Rotate around origin,
		// 3. Move origin to coordinate.
		transform = new AffineTransform();

		// 3. Move origin to coordinate.
		double x = coordinate.getX();
		double y = coordinate.getY();

		// If not antialiased and rotated, round off the coordinates.
		if (!isAntialiased && isAxisAligned()) {
			x = Math.rint(x);
			y = Math.rint(y);
		}

		transform.translate(x, y);

		// 2. Rotate around origin
		transform.rotate(rotation);

		// 1. Move anchor of text to origin
		double anchorX = bounds.getX() + bounds.getWidth() * horizontalAlignment;
		double anchorY = bounds.getY() + bounds.getHeight() * verticalAlignment;

		// If not antialiased, round off the coordinates.
		if (!isAntialiased && isAxisAligned()) {
			anchorX = Math.rint(anchorX);
			anchorY = Math.rint(anchorY);
		}

		transform.translate(-anchorX, -anchorY);

		// Make a shape of the text outline.
		textShape = layout.getOutline(transform);

		return textShape;
	}

	/**
	 * Renders the (rotated) box that surrounds the text.
	 */
	public Shape calculateTextBox(Graphics2D g) {
		// Calculate bounds and transform, if not already calculated
		calculateShape(g);
		assert bounds != null;
		assert transform != null;

		Path2D.Float box   = new Path2D.Float();
		Point2D      point = transform.transform(new Point2D.Double(bounds.getMinX(), bounds.getMinY()), null);
		box.moveTo(point.getX(), point.getY());
		point = transform.transform(new Point2D.Double(bounds.getMaxX(), bounds.getMinY()), point);
		box.lineTo(point.getX(), point.getY());
		point = transform.transform(new Point2D.Double(bounds.getMaxX(), bounds.getMaxY()), point);
		box.lineTo(point.getX(), point.getY());
		point = transform.transform(new Point2D.Double(bounds.getMinX(), bounds.getMaxY()), point);
		box.lineTo(point.getX(), point.getY());
		box.closePath();

		return box;
	}

	/**
	 * Renders the (axis-aligned) bounding box that surrounds the (rotated) text box.
	 */
	public Rectangle2D calculateBoundingBox(Graphics2D g) {
		// Calculate bounds and transform, if not already calculated
		calculateShape(g);
		assert bounds != null;
		assert transform != null;

		Point2D            point = transform.transform(new Point2D.Double(bounds.getMinX(), bounds.getMinY()), null);
		Rectangle2D.Double rect  = new Rectangle2D.Double(point.getX(), point.getY(), 0, 0);
		point = transform.transform(new Point2D.Double(bounds.getMaxX(), bounds.getMinY()), point);
		rect.add(point);
		point = transform.transform(new Point2D.Double(bounds.getMaxX(), bounds.getMaxY()), point);
		rect.add(point);
		point = transform.transform(new Point2D.Double(bounds.getMinX(), bounds.getMaxY()), point);
		rect.add(point);

		return rect;
	}

	public void fill(Graphics2D g) {
		// Render shape & transform, if not already rendered
		calculateShape(g);

		if (g.getFontRenderContext().isAntiAliased()) {
			g.fill(textShape);
		} else {
			// Use old style drawString() if font antialiasing is off.
			// This is the main reason this class can't implement Shape.
			AffineTransform backupTransform = g.getTransform();
			g.transform(transform);
			g.drawString(text, 0, 0);
			g.setTransform(backupTransform);
		}
	}

	public void draw(Graphics2D g) {
		g.draw(calculateShape(g));
	}
}
