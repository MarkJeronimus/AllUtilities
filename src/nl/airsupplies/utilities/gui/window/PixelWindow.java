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

package nl.airsupplies.utilities.gui.window;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import nl.airsupplies.utilities.gui.GUIUtilities;

/**
 * @author Mark Jeronimus
 */
// Created 2009-03-22
// Updated 2013-12-10 Split up to {@link PixelPanel}.
public abstract class PixelWindow extends PixelPanel {
	private final JFrame      frame;
	private       DisplayMode originalMode = null;

	/**
	 * Creates a PixelWindow
	 *
	 * @param x      the x-position of the upper-left corner of the window
	 * @param y      the y-position of the upper-left corner of the window
	 * @param width  the width of the drawable area of the window
	 * @param height the height of the drawable area of the window
	 */
	protected PixelWindow(int x, int y, int width, int height) {
		setPreferredSize(new Dimension(800, 400));

		frame = new JFrame(getClass().getSimpleName());

		initialize(width, height);

		frame.setLocation(x, y);
		frame.setVisible(true);

		requestFocus();
	}

	/**
	 * Creates a PixelWindow
	 *
	 * @param width  the width of the drawable area of the window
	 * @param height the height of the drawable area of the window
	 */
	protected PixelWindow(int width, int height) {
		frame = new JFrame(getClass().getSimpleName());

		initialize(width, height);

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		requestFocus();
	}

	/**
	 * Creates a full-screen PixelWindow
	 */
	protected PixelWindow(boolean exclusive) {
		this(exclusive ? GUIUtilities.getDisplayMode() : null);
	}

	/**
	 * Creates a full-screen PixelWindow
	 *
	 * @param mode the full-screen display mode
	 */
	protected PixelWindow(DisplayMode mode) {
		frame = new JFrame(getClass().getSimpleName());
		frame.invalidate();
		frame.setUndecorated(true);

		initialize(mode.getWidth(), mode.getHeight());

		GraphicsDevice displayDevice = GUIUtilities.getDisplayDevice();
		displayDevice.setFullScreenWindow(frame);
		displayDevice.setDisplayMode(mode);

		requestFocus();
	}

	private void initialize(int width, int height) {
		setPreferredSize(new Dimension(width, height));

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setName(getClass().getSimpleName());
		frame.setLayout(null);
		frame.setContentPane(this);
		frame.pack();
	}

	public void setFullscreen(boolean fullscreen) {
		setFullscreen(fullscreen ? GUIUtilities.getDisplayMode() : null);
	}

	public void setFullscreen(DisplayMode mode) {
		if (mode != null) {
			frame.setVisible(false);
			frame.dispose();
			frame.setUndecorated(true);
			frame.setBounds(0, 0, getWidth(), getHeight());
			GUIUtilities.getDisplayDevice().setFullScreenWindow(frame);
			if (mode != GUIUtilities.getDisplayMode()) {
				originalMode = GUIUtilities.getDisplayMode();
				GUIUtilities.getDisplayDevice().setDisplayMode(mode);
			}
		} else {
			if (originalMode != null) {
				GUIUtilities.getDisplayDevice().setDisplayMode(originalMode);
				originalMode = null;
			}
			GUIUtilities.getDisplayDevice().setFullScreenWindow(null);
			frame.setUndecorated(false);
			frame.setVisible(true);
		}
	}

	public void setTitle(String title) {
		frame.setTitle(title);
	}

	public void setResizable(boolean resizable) {
		frame.setResizable(resizable);
	}

	public void setDefaultCloseOperation(int operation) {
		frame.setDefaultCloseOperation(operation);
	}
}
