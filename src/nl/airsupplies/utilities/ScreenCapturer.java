package nl.airsupplies.utilities;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import nl.airsupplies.utilities.gui.GUIUtilities;

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
			Rectangle screenRect = GUIUtilities.getScreenRect(i);
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
