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

package org.digitalmodular.utilities.graphics.swing;

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
