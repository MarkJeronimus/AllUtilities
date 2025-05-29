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
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.digitalmodular.utilities.graphics.GraphicsUtilities;

/**
 * @author Mark Jeronimus
 */
// Created 2011-06-20
public class ScreenCapturer {
	private final List<Rectangle> screenRects = new ArrayList<>(3); // Most screens 99.9% of the people have
	private final Rectangle       allRect;

	private final Robot robot;

	public ScreenCapturer() {
		allRect = new Rectangle();

		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			Rectangle screenRect = GraphicsUtilities.getScreenRect(i);
			screenRects.add(screenRect);
			allRect.add(screenRect);
		}

		try {
			robot = new Robot();
		} catch (AWTException ex) {
			throw new IllegalStateException("Unable to create ScreenCapturer instance.", ex);
		}
	}

	public int getScreenCount() {
		return screenRects.size();
	}

	public Rectangle getAllRect() {
		return allRect;
	}

	public Rectangle getScreenRect(int screen) {
		return screenRects.get(screen);
	}

	public BufferedImage captureAll() {
		return robot.createScreenCapture(allRect);
	}

	public BufferedImage captureScreen(int i) {
		return robot.createScreenCapture(screenRects.get(i));
	}

	public BufferedImage captureArea(Rectangle area) {
		return robot.createScreenCapture(area);
	}
}
