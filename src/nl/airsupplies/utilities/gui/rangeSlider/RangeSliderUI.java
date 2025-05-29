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

package nl.airsupplies.utilities.gui.rangeSlider;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;

/**
 * @author Mark Jeronimus
 */
// Created 2011-08-19
public class RangeSliderUI extends BasicSliderUI {
	/**
	 * Color of selected range.
	 */
	private Paint rangePaint = Color.GREEN;

	/**
	 * Location and size of thumb for upper value.
	 */
	private Rectangle upperThumbRect;
	/**
	 * Indicator that determines whether upper thumb is selected.
	 */
	boolean upperThumbSelected;

	/**
	 * Indicator that determines whether lower thumb is being dragged.
	 */
	boolean lowerDragging;
	/**
	 * Indicator that determines whether upper thumb is being dragged.
	 */
	boolean upperDragging;

	private Icon horizontalLowerThumbIcon;
	private Icon horizontalUpperThumbIcon;
	private Icon verticalLowerThumbIcon;
	private Icon verticalUpperThumbIcon;

	/**
	 * Constructs a RangeSliderUI for the specified slider component.
	 *
	 * @param b RangeSlider
	 */
	public RangeSliderUI(RangeSlider b) {
		super(b);
	}

	private static final byte[] header = {(byte)0x47, (byte)0x49, (byte)0x46, (byte)0x38, (byte)0x39, (byte)0x61,
	                                      (byte)0x0C,
	                                      (byte)0x00, (byte)0x0C, (byte)0x00, (byte)0xB3, (byte)0x00, (byte)0x00,
	                                      (byte)0x00, (byte)0xFF,
	                                      (byte)0x00, (byte)0x31,
	                                      (byte)0x31, (byte)0x31, (byte)0xC6, (byte)0xD6, (byte)0xEF, (byte)0xC6,
	                                      (byte)0xDE, (byte)0xEF,
	                                      (byte)0xCE, (byte)0xDE,
	                                      (byte)0xF7, (byte)0xD6, (byte)0xE7, (byte)0xF7, (byte)0xE7, (byte)0xEF,
	                                      (byte)0xFF, (byte)0xF7,
	                                      (byte)0xF7, (byte)0xFF,
	                                      (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF,
	                                      (byte)0xFF, (byte)0xFF,
	                                      (byte)0xFF, (byte)0xFF,
	                                      (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF,
	                                      (byte)0xFF, (byte)0xFF,
	                                      (byte)0xFF, (byte)0xFF,
	                                      (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0x21, (byte)0xF9,
	                                      (byte)0x04, (byte)0x01,
	                                      (byte)0x00, (byte)0x00,
	                                      (byte)0x00, (byte)0x00, (byte)0x2C, (byte)0x00, (byte)0x00, (byte)0x00,
	                                      (byte)0x00, (byte)0x0C,
	                                      (byte)0x00, (byte)0x0C,
	                                      (byte)0x00, (byte)0x00, (byte)0x04};
	private static final byte[] hl     = {(byte)0x2C, (byte)0x10, (byte)0x48, (byte)0x19, (byte)0xA6, (byte)0x9D,
	                                      (byte)0xA1,
	                                      (byte)0x5E, (byte)0x3C, (byte)0xF4, (byte)0x0E, (byte)0x45, (byte)0xE1,
	                                      (byte)0x59, (byte)0x81,
	                                      (byte)0x61, (byte)0x8E,
	                                      (byte)0xD4, (byte)0xA1, (byte)0x1E, (byte)0x28, (byte)0x10, (byte)0x20,
	                                      (byte)0x30, (byte)0x92,
	                                      (byte)0xCD, (byte)0x73,
	                                      (byte)0x6C, (byte)0xC7, (byte)0xD9, (byte)0xAA, (byte)0xAF, (byte)0x99,
	                                      (byte)0xE9, (byte)0xFF,
	                                      (byte)0x86, (byte)0x4C,
	                                      (byte)0x68, (byte)0x48, (byte)0x14, (byte)0xB9, (byte)0x68, (byte)0x48,
	                                      (byte)0x40, (byte)0x04,
	                                      (byte)0x00, (byte)0x3B};
	private static final byte[] hu     = {(byte)0x2B, (byte)0x10, (byte)0xC8, (byte)0x19, (byte)0xA6, (byte)0xB5,
	                                      (byte)0xA1,
	                                      (byte)0x5E, (byte)0x3C, (byte)0xF4, (byte)0x06, (byte)0x41, (byte)0x51,
	                                      (byte)0x78, (byte)0x57,
	                                      (byte)0x60, (byte)0x9C,
	                                      (byte)0x24, (byte)0x75, (byte)0xAC, (byte)0x47, (byte)0xE6, (byte)0xBA,
	                                      (byte)0x48, (byte)0x8C,
	                                      (byte)0x64, (byte)0x72,
	                                      (byte)0x2D, (byte)0x67, (byte)0x6C, (byte)0xCE, (byte)0x66, (byte)0x67,
	                                      (byte)0xEF, (byte)0x1B,
	                                      (byte)0x19, (byte)0x91,
	                                      (byte)0x70, (byte)0x38, (byte)0x02, (byte)0xBD, (byte)0x8E, (byte)0x80,
	                                      (byte)0x08, (byte)0x00,
	                                      (byte)0x3B};
	private static final byte[] vl     = {(byte)0x2C, (byte)0x10, (byte)0x84, (byte)0x49, (byte)0x81, (byte)0xB5,
	                                      (byte)0xA1,
	                                      (byte)0x98, (byte)0x83, (byte)0xD0, (byte)0x09, (byte)0x97, (byte)0xA4,
	                                      (byte)0x71, (byte)0x9E,
	                                      (byte)0x01, (byte)0x62,
	                                      (byte)0x63, (byte)0x77, (byte)0x18, (byte)0xC5, (byte)0x99, (byte)0x6D,
	                                      (byte)0x2A, (byte)0x3B,
	                                      (byte)0x80, (byte)0x2F,
	                                      (byte)0x47, (byte)0xDD, (byte)0x35, (byte)0x72, (byte)0x86, (byte)0xF9,
	                                      (byte)0x7E, (byte)0xF5,
	                                      (byte)0x21, (byte)0x14,
	                                      (byte)0xCC, (byte)0x27, (byte)0xB4, (byte)0x05, (byte)0x31, (byte)0xB7,
	                                      (byte)0x63, (byte)0x04,
	                                      (byte)0x00, (byte)0x3B};
	private static final byte[] vu     = {(byte)0x2C, (byte)0x10, (byte)0x84, (byte)0x39, (byte)0x81, (byte)0xBD,
	                                      (byte)0xA1,
	                                      (byte)0x98, (byte)0x83, (byte)0xC2, (byte)0xC5, (byte)0x1A, (byte)0xE7,
	                                      (byte)0x7D, (byte)0x52,
	                                      (byte)0xD8, (byte)0x91,
	                                      (byte)0xE5, (byte)0x76, (byte)0x92, (byte)0x99, (byte)0x3A, (byte)0x7E,
	                                      (byte)0xAD, (byte)0x48,
	                                      (byte)0xCD, (byte)0x26,
	                                      (byte)0x72, (byte)0x18, (byte)0xC5, (byte)0xE0, (byte)0xC5, (byte)0x88,
	                                      (byte)0x8D, (byte)0x8F,
	                                      (byte)0x3C, (byte)0xDF,
	                                      (byte)0x2B, (byte)0x78, (byte)0x78, (byte)0x59, (byte)0x66, (byte)0x95,
	                                      (byte)0x4F, (byte)0x04,
	                                      (byte)0x00, (byte)0x3B};

	/**
	 * Installs this UI delegate on the specified component.
	 */
	@Override
	public void installUI(JComponent c) {
		upperThumbRect = new Rectangle();
		super.installUI(c);

		horizontalLowerThumbIcon = makeIcon(header, hl);
		horizontalUpperThumbIcon = makeIcon(header, hu);
		verticalLowerThumbIcon   = makeIcon(header, vl);
		verticalUpperThumbIcon   = makeIcon(header, vu);
	}

	private static Icon makeIcon(byte[] header, byte[] hl) {
		byte[] gif = new byte[header.length + hl.length];
		System.arraycopy(header, 0, gif, 0, header.length);
		System.arraycopy(hl, 0, gif, header.length, hl.length);
		return new ImageIcon(gif);
	}

	/**
	 * Creates a listener to handle track events in the specified slider.
	 */
	@Override
	protected TrackListener createTrackListener(JSlider slider) {
		return new RangeTrackListener();
	}

	/**
	 * Creates a listener to handle change events in the specified slider.
	 */
	@Override
	protected ChangeListener createChangeListener(JSlider slider) {
		return new ChangeHandler();
	}

	/**
	 * Updates the dimensions for both thumbs.
	 */
	@Override
	protected void calculateThumbSize() {
		// Call superclass method for lower thumb size.
		super.calculateThumbSize();

		// Set upper thumb size.
		upperThumbRect.setSize(thumbRect.width, thumbRect.height);
	}

	/**
	 * Updates the locations for both thumbs.
	 */
	@Override
	protected void calculateThumbLocation() {
		// Call superclass method for lower thumb location.
		super.calculateThumbLocation();

		// Adjust upper value to snap to ticks if necessary.
		if (slider.getSnapToTicks()) {
			int upperValue       = slider.getValue() + slider.getExtent();
			int snappedValue     = upperValue;
			int majorTickSpacing = slider.getMajorTickSpacing();
			int minorTickSpacing = slider.getMinorTickSpacing();
			int tickSpacing      = 0;

			if (minorTickSpacing > 0) {
				tickSpacing = minorTickSpacing;
			} else if (majorTickSpacing > 0) {
				tickSpacing = majorTickSpacing;
			}

			if (tickSpacing != 0) {
				// If it's not on a tick, change the value
				if ((upperValue - slider.getMinimum()) % tickSpacing != 0) {
					float temp      = (float)(upperValue - slider.getMinimum()) / tickSpacing;
					int   whichTick = Math.round(temp);
					snappedValue = slider.getMinimum() + whichTick * tickSpacing;
				}

				if (snappedValue != upperValue) {
					slider.setExtent(snappedValue - slider.getValue());
				}
			}
		}

		// Calculate upper thumb location. The thumb is centered over its
		// value on the track.
		if (slider.getOrientation() == SwingConstants.HORIZONTAL) {
			int upperPosition = xPositionForValue(slider.getValue() + slider.getExtent());
			upperThumbRect.x = upperPosition - upperThumbRect.width / 2;
			upperThumbRect.y = trackRect.y;

		} else {
			int upperPosition = yPositionForValue(slider.getValue() + slider.getExtent());
			upperThumbRect.x = trackRect.x;
			upperThumbRect.y = upperPosition - upperThumbRect.height / 2;
		}
	}

	/**
	 * Returns the size of a thumb.
	 */
	@Override
	protected Dimension getThumbSize() {
		return new Dimension(12, 12);
	}

	/**
	 * Paints the slider. The selected thumb is always painted on top of the other thumb.
	 */
	@Override
	public void paint(Graphics g, JComponent c) {
		super.paint(g, c);

		Rectangle clipRect = g.getClipBounds();
		if (upperThumbSelected) {
			// Paint lower thumb first, then upper thumb.
			if (clipRect.intersects(thumbRect)) {
				paintLowerThumb(g);
			}
			if (clipRect.intersects(upperThumbRect)) {
				paintUpperThumb(g);
			}

		} else {
			// Paint upper thumb first, then lower thumb.
			if (clipRect.intersects(upperThumbRect)) {
				paintUpperThumb(g);
			}
			if (clipRect.intersects(thumbRect)) {
				paintLowerThumb(g);
			}
		}
	}

	/**
	 * Paints the track.
	 */
	@Override
	public void paintTrack(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;

		// Draw track.
		super.paintTrack(g);

		Rectangle trackBounds = trackRect;

		if (slider.getOrientation() == SwingConstants.HORIZONTAL) {
			// Determine position of selected range by moving from the middle
			// of one thumb to the other.
			int lowerX = thumbRect.x + thumbRect.width / 2;
			int upperX = upperThumbRect.x + upperThumbRect.width / 2;

			// Determine track position.
			int cy = trackBounds.height / 2 - 2;

			// Save color and shift position.
			Paint oldColor = g2.getPaint();
			g.translate(trackBounds.x, trackBounds.y + cy);

			// Draw selected range.
			g2.setPaint(rangePaint);
			for (int y = 0; y <= 3; y++) {
				g.drawLine(lowerX - trackBounds.x, y, upperX - trackBounds.x, y);
			}

			// Restore position and color.
			g.translate(-trackBounds.x, -(trackBounds.y + cy));
			g2.setPaint(oldColor);
		} else {
			// Determine position of selected range by moving from the middle
			// of one thumb to the other.
			int lowerY = thumbRect.x + thumbRect.width / 2;
			int upperY = upperThumbRect.x + upperThumbRect.width / 2;

			// Determine track position.
			int cx = trackBounds.width / 2 - 2;

			// Save color and shift position.
			Paint oldColor = g2.getPaint();
			g.translate(trackBounds.x + cx, trackBounds.y);

			// Draw selected range.
			g2.setPaint(rangePaint);
			for (int x = 0; x <= 3; x++) {
				g.drawLine(x, lowerY - trackBounds.y, x, upperY - trackBounds.y);
			}

			// Restore position and color.
			g.translate(-(trackBounds.x + cx), -trackBounds.y);
			g2.setPaint(oldColor);
		}
	}

	/**
	 * Overrides superclass method to do nothing. Thumb painting is handled within the {@code paint()} method.
	 */
	@Override
	public void paintThumb(Graphics g) {
		// Do nothing.
	}

	/**
	 * Paints the thumb for the lower value using the specified graphics object.
	 */
	private void paintLowerThumb(Graphics g) {
		Rectangle knobBounds = thumbRect;

		g.translate(knobBounds.x, knobBounds.y);

		if (slider.getOrientation() == SwingConstants.HORIZONTAL) {
			horizontalLowerThumbIcon.paintIcon(slider, g, 0, 0);
		} else {
			verticalLowerThumbIcon.paintIcon(slider, g, 0, 0);
		}

		g.translate(-knobBounds.x, -knobBounds.y);
	}

	/**
	 * Paints the thumb for the upper value using the specified graphics object.
	 */
	private void paintUpperThumb(Graphics g) {
		Rectangle knobBounds = upperThumbRect;

		g.translate(knobBounds.x, knobBounds.y);

		if (slider.getOrientation() == SwingConstants.HORIZONTAL) {
			horizontalUpperThumbIcon.paintIcon(slider, g, 0, 0);
		} else {
			verticalUpperThumbIcon.paintIcon(slider, g, 0, 0);
		}

		g.translate(-knobBounds.x, -knobBounds.y);
	}

	/**
	 * Sets the location of the upper thumb, and repaints the slider. This is called when the upper thumb is dragged to
	 * repaint the slider. The {@code setThumbLocation()} method performs the same task for the lower thumb.
	 */
	void setUpperThumbLocation(int x, int y) {
		Rectangle upperUnionRect = new Rectangle();
		upperUnionRect.setBounds(upperThumbRect);

		upperThumbRect.setLocation(x, y);

		SwingUtilities.computeUnion(upperThumbRect.x,
		                            upperThumbRect.y,
		                            upperThumbRect.width,
		                            upperThumbRect.height,
		                            upperUnionRect);
		slider.repaint(upperUnionRect.x, upperUnionRect.y, upperUnionRect.width, upperUnionRect.height);
	}

	/**
	 * Moves the selected thumb in the specified direction by a block increment. This method is called when the user
	 * presses the Page Up or Down keys.
	 */
	@Override
	public void scrollByBlock(int direction) {
		synchronized (slider) {
			int blockIncrement = (slider.getMaximum() - slider.getMinimum()) / 10;
			if (blockIncrement <= 0 && slider.getMaximum() > slider.getMinimum()) {
				blockIncrement = 1;
			}
			int delta = blockIncrement * (direction > 0 ? POSITIVE_SCROLL : NEGATIVE_SCROLL);

			if (upperThumbSelected) {
				int oldValue = ((RangeSlider)slider).getUpperValue();
				((RangeSlider)slider).setUpperValue(oldValue + delta);
			} else {
				int oldValue = slider.getValue();
				slider.setValue(oldValue + delta);
			}
		}
	}

	/**
	 * Moves the selected thumb in the specified direction by a unit increment. This method is called when the user
	 * presses one of the arrow keys.
	 */
	@Override
	public void scrollByUnit(int direction) {
		synchronized (slider) {
			int delta = (direction > 0 ? POSITIVE_SCROLL : NEGATIVE_SCROLL);

			if (upperThumbSelected) {
				int oldValue = ((RangeSlider)slider).getUpperValue();
				((RangeSlider)slider).setUpperValue(oldValue + delta);
			} else {
				int oldValue = slider.getValue();
				slider.setValue(oldValue + delta);
			}
		}
	}

	/**
	 * Listener to handle model change events. This calculates the thumb locations and repaints the slider if the value
	 * change is not caused by dragging a thumb.
	 */
	@SuppressWarnings("synthetic-access")
	public class ChangeHandler implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent arg0) {
			if (!lowerDragging && !upperDragging) {
				calculateThumbLocation();
				slider.repaint();
			}
		}
	}

	/**
	 * Listener to handle mouse movements in the slider track.
	 */
	@SuppressWarnings("synthetic-access")
	public class RangeTrackListener extends TrackListener {
		@Override
		public void mousePressed(MouseEvent e) {
			if (!slider.isEnabled()) {
				return;
			}

			currentMouseX = e.getX();
			currentMouseY = e.getY();

			if (slider.isRequestFocusEnabled()) {
				slider.requestFocus();
			}

			// Determine which thumb is pressed. If the upper thumb is
			// selected (last one dragged), then check its position first;
			// otherwise check the position of the lower thumb first.
			boolean lowerPressed = false;
			boolean upperPressed = false;
			if (upperThumbSelected) {
				if (upperThumbRect.contains(currentMouseX, currentMouseY)) {
					upperPressed = true;
				} else if (thumbRect.contains(currentMouseX, currentMouseY)) {
					lowerPressed = true;
				}
			} else {
				if (thumbRect.contains(currentMouseX, currentMouseY)) {
					lowerPressed = true;
				} else if (upperThumbRect.contains(currentMouseX, currentMouseY)) {
					upperPressed = true;
				}
			}

			// Handle lower thumb pressed.
			if (lowerPressed) {
				switch (slider.getOrientation()) {
					case SwingConstants.VERTICAL:
						offset = currentMouseY - thumbRect.y;
						break;
					case SwingConstants.HORIZONTAL:
						offset = currentMouseX - thumbRect.x;
						break;
				}
				upperThumbSelected = false;
				lowerDragging      = true;
				return;
			}
			lowerDragging = false;

			// Handle upper thumb pressed.
			if (upperPressed) {
				switch (slider.getOrientation()) {
					case SwingConstants.VERTICAL:
						offset = currentMouseY - upperThumbRect.y;
						break;
					case SwingConstants.HORIZONTAL:
						offset = currentMouseX - upperThumbRect.x;
						break;
				}
				upperThumbSelected = true;
				upperDragging      = true;
				return;
			}
			upperDragging = false;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			lowerDragging = false;
			upperDragging = false;
			slider.setValueIsAdjusting(false);
			super.mouseReleased(e);
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (!slider.isEnabled()) {
				return;
			}

			currentMouseX = e.getX();
			currentMouseY = e.getY();

			if (lowerDragging) {
				slider.setValueIsAdjusting(true);
				moveLowerThumb();

			} else if (upperDragging) {
				slider.setValueIsAdjusting(true);
				moveUpperThumb();
			}
		}

		@Override
		public boolean shouldScroll(int direction) {
			return false;
		}

		/**
		 * Moves the location of the lower thumb, and sets its corresponding value in the slider.
		 */
		private void moveLowerThumb() {
			int thumbMiddle = 0;

			switch (slider.getOrientation()) {
				case SwingConstants.VERTICAL:
					int halfThumbHeight = thumbRect.height / 2;
					int thumbTop = currentMouseY - offset;
					int trackTop = trackRect.y;
					int trackBottom = trackRect.y + trackRect.height - 1;
					int vMax = yPositionForValue(slider.getValue() + slider.getExtent());

					// Apply bounds to thumb position.
					if (drawInverted()) {
						trackBottom = vMax;
					} else {
						trackTop = vMax;
					}
					thumbTop = Math.max(thumbTop, trackTop - halfThumbHeight);
					thumbTop = Math.min(thumbTop, trackBottom - halfThumbHeight);

					setThumbLocation(thumbRect.x, thumbTop);

					// Update slider value.
					thumbMiddle = thumbTop + halfThumbHeight;
					slider.setValue(valueForYPosition(thumbMiddle));
					break;

				case SwingConstants.HORIZONTAL:
					int halfThumbWidth = thumbRect.width / 2;
					int thumbLeft = currentMouseX - offset;
					int trackLeft = trackRect.x;
					int trackRight = trackRect.x + trackRect.width - 1;
					int hMax = xPositionForValue(slider.getValue() + slider.getExtent());

					// Apply bounds to thumb position.
					if (drawInverted()) {
						trackLeft = hMax;
					} else {
						trackRight = hMax;
					}
					thumbLeft = Math.max(thumbLeft, trackLeft - halfThumbWidth);
					thumbLeft = Math.min(thumbLeft, trackRight - halfThumbWidth);

					setThumbLocation(thumbLeft, thumbRect.y);

					// Update slider value.
					thumbMiddle = thumbLeft + halfThumbWidth;
					slider.setValue(valueForXPosition(thumbMiddle));
					break;

				default:
			}
		}

		/**
		 * Moves the location of the upper thumb, and sets its corresponding value in the slider.
		 */
		private void moveUpperThumb() {
			int thumbMiddle = 0;

			switch (slider.getOrientation()) {
				case SwingConstants.VERTICAL:
					int halfThumbHeight = thumbRect.height / 2;
					int thumbTop = currentMouseY - offset;
					int trackTop = trackRect.y;
					int trackBottom = trackRect.y + trackRect.height - 1;
					int vMin = yPositionForValue(slider.getValue());

					// Apply bounds to thumb position.
					if (drawInverted()) {
						trackTop = vMin;
					} else {
						trackBottom = vMin;
					}
					thumbTop = Math.max(thumbTop, trackTop - halfThumbHeight);
					thumbTop = Math.min(thumbTop, trackBottom - halfThumbHeight);

					setUpperThumbLocation(thumbRect.x, thumbTop);

					// Update slider extent.
					thumbMiddle = thumbTop + halfThumbHeight;
					slider.setExtent(valueForYPosition(thumbMiddle) - slider.getValue());
					break;

				case SwingConstants.HORIZONTAL:
					int halfThumbWidth = thumbRect.width / 2;
					int thumbLeft = currentMouseX - offset;
					int trackLeft = trackRect.x;
					int trackRight = trackRect.x + trackRect.width - 1;
					int hMin = xPositionForValue(slider.getValue());

					// Apply bounds to thumb position.
					if (drawInverted()) {
						trackRight = hMin;
					} else {
						trackLeft = hMin;
					}
					thumbLeft = Math.max(thumbLeft, trackLeft - halfThumbWidth);
					thumbLeft = Math.min(thumbLeft, trackRight - halfThumbWidth);

					setUpperThumbLocation(thumbLeft, thumbRect.y);

					// Update slider extent.
					thumbMiddle = thumbLeft + halfThumbWidth;
					slider.setExtent(valueForXPosition(thumbMiddle) - slider.getValue());
					break;

				default:
			}
		}
	}

	public void setRangePaint(Paint paint) {
		rangePaint = paint;
	}
}
