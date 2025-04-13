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

package org.digitalmodular.utilities;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

/**
 * @author Mark Jeronimus
 */
// Created 2011-06-20
public class ScreenCapturer {
	private final Rectangle[] screenRects;
	private final Rectangle   allRect;

	private Robot robot;

	public ScreenCapturer() {
		GraphicsDevice[] screens = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
		screenRects = new Rectangle[screens.length];
		allRect = new Rectangle();

		for (int i = 0; i < screens.length; i++) {
			screenRects[i] = screens[i].getDefaultConfiguration().getBounds();
			allRect.add(screenRects[i]);
		}

		try {
			robot = new Robot();
		} catch (AWTException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}

	public int getScreenCount() {
		return screenRects.length;
	}

	public Rectangle getAllRect() {
		return allRect;
	}

	public Rectangle getScreenRect(int screen) {
		return screenRects[screen];
	}

	public BufferedImage captureAll() {
		return robot.createScreenCapture(allRect);
	}

	public BufferedImage captureScreen(int i) {
		return robot.createScreenCapture(screenRects[i]);
	}

	public BufferedImage captureArea(Rectangle area) {
		return robot.createScreenCapture(area);
	}
}
