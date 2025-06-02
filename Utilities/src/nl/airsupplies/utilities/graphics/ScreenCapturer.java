package nl.airsupplies.utilities.graphics;

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
	private final Rectangle       desktopSize;
	private final List<Rectangle> screenRects = new ArrayList<>(4);

	private final Robot robot;

	public ScreenCapturer() {
		desktopSize = new Rectangle();

		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			Rectangle screenRect = GUIUtilities.getScreenRect(i);
			desktopSize.add(screenRect);
			screenRects.add(screenRect);
		}

		try {
			robot = new Robot();
		} catch (AWTException ex) {
			throw new IllegalStateException("Unable to create ScreenCapturer instance.", ex);
		}
	}

	public Rectangle getDesktopSize() {
		return desktopSize;
	}

	public int getScreenCount() {
		return screenRects.size();
	}

	public Rectangle getScreenRect(int screen) {
		return screenRects.get(screen);
	}

	public BufferedImage captureAll() {
		return robot.createScreenCapture(desktopSize);
	}

	public BufferedImage captureScreen(int i) {
		return robot.createScreenCapture(screenRects.get(i));
	}

	public BufferedImage captureArea(Rectangle area) {
		return robot.createScreenCapture(area);
	}
}
