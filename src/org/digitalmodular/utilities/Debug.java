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

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Writer;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * @author Mark Jeronimus
 */
// Created 2005-04-22
@Deprecated
public class Debug {
	public static PrintStream debugStream  = null;
	public static boolean     debuggingGUI = false;
	public static Writer      debugFile;

	public static synchronized void debugCodePoint(String debugString) {
		if (debugStream == null) {
			return;
		}

		try {
			throw new Exception();
		} catch (Exception ex) {
			debugStream.println(ex.getStackTrace()[1] + " - " + debugString);
		}
	}

	public static synchronized void debugError(String debugString) {
		try {
			throw new Exception();
		} catch (Exception ex) {
			System.err.println(ex.getStackTrace()[1] + " - " + debugString);
		}
	}

	public static void setDebugFile(String filename) {
		setDebugFile(new File(filename), false);
	}

	public static void setDebugFile(String filename, boolean append) {
		setDebugFile(new File(filename), append);
	}

	public static void setDebugFile(File file) {
		setDebugFile(file, false);
	}

	public static void setDebugFile(File file, boolean append) {
		try {
			debugFile = new FileWriter(file, append);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Debug to screen if debuggingText, and file if opened.
	 */
	public static synchronized void println() {
		if (debugStream != null) {
			debugStream.println();
		}

		if (debugFile != null) {
			try {
				debugFile.write('\n');
				debugFile.flush();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Debug to screen if debuggingText, and file if opened.
	 */
	public static synchronized void print(String string) {
		if (debugStream != null) {
			debugStream.print(string);
		}

		if (debugFile != null) {
			try {
				debugFile.write(string);
				debugFile.flush();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Debug to screen if debugStream set, and file if debugFile set.
	 */
	public static synchronized void println(String string) {
		if (debugStream != null) {
			debugStream.println(string);
		}

		if (debugFile != null) {
			try {
				debugFile.write(string);
				debugFile.write('\n');
				debugFile.flush();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static synchronized void gui(JComponent c) {
		if (debuggingGUI) {
			c.setOpaque(true);
			c.setBackground(new Color((float)Math.random(), (float)Math.random(), (float)Math.random()));
		}
	}

	public static void gui(JComponent c, String name) {
		if (debuggingGUI) {
			c.setBorder(BorderFactory.createTitledBorder(name));
		}
	}

	public static void gui(JFrame c) {
		if (debuggingGUI) {
			c.setBackground(new Color((float)Math.random(), (float)Math.random(), (float)Math.random()));
		}
	}
}
