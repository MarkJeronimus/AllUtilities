/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2018 Mark Jeronimus. All Rights Reversed.
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
 * along with AllUtilities. If not, see <http://www.gnu.org/licenses/>.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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

	public synchronized static void debugCodePoint(String debugString) {
		if (Debug.debugStream == null) {
			return;
		}

		try {
			throw new Exception();
		} catch (Exception e) {
			Debug.debugStream.println(e.getStackTrace()[1].toString() + " - " + debugString);
		}
	}

	public synchronized static void debugError(String debugString) {
		try {
			throw new Exception();
		} catch (Exception e) {
			System.err.println(e.getStackTrace()[1].toString() + " - " + debugString);
		}
	}

	public static void setDebugFile(String filename) {
		Debug.setDebugFile(new File(filename), false);
	}

	public static void setDebugFile(String filename, boolean append) {
		Debug.setDebugFile(new File(filename), append);
	}

	public static void setDebugFile(File file) {
		Debug.setDebugFile(file, false);
	}

	public static void setDebugFile(File file, boolean append) {
		try {
			Debug.debugFile = new FileWriter(file, append);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Debug to screen if debuggingText, and file if opened.
	 */
	public synchronized static void println() {
		if (Debug.debugStream != null) {
			Debug.debugStream.println();
		}

		if (Debug.debugFile != null) {
			try {
				Debug.debugFile.write('\n');
				Debug.debugFile.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Debug to screen if debuggingText, and file if opened.
	 */
	public synchronized static void print(String string) {
		if (Debug.debugStream != null) {
			Debug.debugStream.print(string);
		}

		if (Debug.debugFile != null) {
			try {
				Debug.debugFile.write(string);
				Debug.debugFile.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Debug to screen if debugStream set, and file if debugFile set.
	 */
	public synchronized static void println(String string) {
		if (Debug.debugStream != null) {
			Debug.debugStream.println(string);
		}

		if (Debug.debugFile != null) {
			try {
				Debug.debugFile.write(string);
				Debug.debugFile.write('\n');
				Debug.debugFile.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized static void gui(JComponent c) {
		if (Debug.debuggingGUI) {
			c.setOpaque(true);
			c.setBackground(new Color((float)Math.random(), (float)Math.random(), (float)Math.random()));
		}
	}

	public static void gui(JComponent c, String name) {
		if (Debug.debuggingGUI) {
			c.setBorder(BorderFactory.createTitledBorder(name));
		}
	}

	public static void gui(JFrame c) {
		if (Debug.debuggingGUI) {
			c.setBackground(new Color((float)Math.random(), (float)Math.random(), (float)Math.random()));
		}
	}
}
