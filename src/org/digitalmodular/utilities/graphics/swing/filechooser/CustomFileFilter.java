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

package org.digitalmodular.utilities.graphics.swing.filechooser;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 * @author Mark Jeronimus
 * @see javax.swing.filechooser
 */
// Created 2006-06-15
public class CustomFileFilter extends FileFilter implements java.io.FileFilter {
	private final String   description;
	private final String[] extensions;

	public CustomFileFilter(String description, String... extensions) {
		this.description = description;
		this.extensions  = extensions.clone();

		for (int i = 0; i < extensions.length; i++) {
			extensions[i] = extensions[i].toLowerCase();
		}
	}

	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}

		String s         = f.getName();
		int    i         = s.lastIndexOf('.');
		String extension = i > 0 ? s.substring(i + 1).toLowerCase() : "";

		for (String wantedExt : extensions) {
			if (extension.equals(wantedExt)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
