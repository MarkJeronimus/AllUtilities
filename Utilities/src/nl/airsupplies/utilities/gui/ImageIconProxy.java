package nl.airsupplies.utilities.gui;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * @author Mark Jeronimus
 */
// Created 2007-05-07
public class ImageIconProxy {
	private static final HashMap<Object, ImageIcon> icons = new HashMap<>();

	public static void put(Object key, ImageIcon icon) {
		icons.put(key, icon);
	}

	public static ImageIcon get(Object key) {
		return icons.get(key);
	}

	public static ImageIcon getIcon(String filename) {
		ImageIcon icon = icons.get(filename);

		if (icon == null) {
			icon = new ImageIcon(filename);
			icons.put(filename, icon);
		}

		return icon;
	}

	public static Icon getIcon(BufferedImage image) {
		ImageIcon icon = icons.get(image);

		if (icon == null) {
			icon = new ImageIcon(image);
			icons.put(image, icon);
		}

		return icon;
	}

	public static void remove(Object binaryImage) {
		icons.remove(binaryImage);
	}

	public static void flush() {
		icons.clear();
	}
}
