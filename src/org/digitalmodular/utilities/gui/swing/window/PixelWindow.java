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
package org.digitalmodular.utilities.gui.swing.window;

import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import javax.swing.JFrame;

import org.digitalmodular.utilities.gui.GraphicsUtilities;

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
		super();
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
		super();

		frame = new JFrame(getClass().getSimpleName());

		initialize(width, height);

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		requestFocus();
	}

	/**
	 * Creates a fullscreen PixelWindow
	 */
	protected PixelWindow(boolean exclusive) {
		this(exclusive ? GraphicsUtilities.getDisplayMode() : null);
	}

	/**
	 * Creates a fullscreen PixelWindow
	 *
	 * @param mode the fullscreen display mode
	 */
	protected PixelWindow(DisplayMode mode) {
		super();

		frame = new JFrame(getClass().getSimpleName());
		frame.invalidate();
		frame.setUndecorated(true);

		initialize(mode.getWidth(), mode.getHeight());

		GraphicsDevice displayDevice = GraphicsUtilities.getDisplayDevice();
		displayDevice.setFullScreenWindow(frame);
		displayDevice.setDisplayMode(mode);

		requestFocus();
	}

	private void initialize(int width, int height) {
		setPreferredSize(new Dimension(width, height));

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setName(this.getClass().getSimpleName());
		frame.setLayout(null);
		frame.setContentPane(this);
		frame.pack();
	}

	public void setFullscreen(boolean fullscreen) {
		setFullscreen(fullscreen ? GraphicsUtilities.getDisplayMode() : null);
	}

	public void setFullscreen(DisplayMode mode) {
		if (mode != null) {
			frame.setVisible(false);
			frame.dispose();
			frame.setUndecorated(true);
			frame.setBounds(0, 0, getWidth(), getHeight());
			GraphicsUtilities.getDisplayDevice().setFullScreenWindow(frame);
			if (mode != GraphicsUtilities.getDisplayMode()) {
				originalMode = GraphicsUtilities.getDisplayMode();
				GraphicsUtilities.getDisplayDevice().setDisplayMode(mode);
			}
		} else {
			if (originalMode != null) {
				GraphicsUtilities.getDisplayDevice().setDisplayMode(originalMode);
				originalMode = null;
			}
			GraphicsUtilities.getDisplayDevice().setFullScreenWindow(null);
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
