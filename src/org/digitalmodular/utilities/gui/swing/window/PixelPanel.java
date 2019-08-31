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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.digitalmodular.utilities.gui.GraphicsUtilities;

/**
 * An extremely fast JPanel implementation to draw both vector graphics and pixel graphics. To use this, draw on the
 * panel using one of two ways, then when you want to synchronize the buffer with the screen, call {@link
 * #repaintNow()}. The size of the panel can be queried with the public fields {@link #getWidth()} and {@link
 * #getHeight()}.
 * <p>
 * To draw vector graphics, access the public {@link #g} field, which is of {@link Graphics2D}. This {@code g} is
 * already set to antialiased, and this should be turned off prior to drawing images or other non-vector elements.
 * <p>
 * To draw pixel graphics, access the public {@link #pixels} field which is of {@code int[]}.
 * <p>
 * The latter is a linear framebuffer with scanline size equal to the width and one RGB packed integer per pixel. In
 * other words, the pixel at {@code (x, y)} is stored in {@code pixels[x + y * width)}.
 * <p>
 * Also note, that this class is unsafe throughout. Public field values and returned objects should not be changed by
 * the user or the behavior will be undefined.
 * <p>
 * Update 2012-07-28 Sun API changed making things incompatible. blitting now done implicitly instead of explicitly.
 * <p>
 * Update 2013-01-25 Changed repainting method to reflect previous changes (use {@link #repaintNow()} instead of {@link
 * JPanel#repaint()}).
 * <p>
 * Update 2013-07-22 Added method to save the panel contents as a {@link BufferedImage}.
 * <p>
 * Update 2013-12-05 Changed JFrame to JPanel and removed the strategy pattern for different implementations.
 *
 * @author Mark Jeronimus
 */
// Created 2009-04-04
// Updated 2013-12-10 Split up from {@link PixelWindow}.
// Updated 2014-01-07 Split up to {@link WaveFormChart}
@Deprecated
public abstract class PixelPanel extends JComponent {
	private PixelImage image  = null;    // null means uninitialized.
	private Graphics   panelG = null;
	public  Graphics2D g      = null;
	public  int[]      pixels = null;

	protected PixelPanel() {
		setFocusable(true); // Disabled by default. Allows keyboard listeners.
	}

	/**
	 * This method should be called by the user when the contents of the buffers should be shown on the containing
	 * panel.
	 */
	public synchronized final void repaintNow() {
		// Bugfix: panels flicker when contained in a tab that's "not visible" (isVisible returns true)
		if (panelG != null) {
			// panelG.drawImage(image.image, 0, 0, null);
			//
			// Toolkit.getDefaultToolkit().sync();
			repaint();
		}
	}

	/**
	 * Clears the buffer with the specified paint or color.
	 */
	public synchronized void clear(Paint color) {
		image.clear(color);
	}

	/**
	 * Clears the buffer with the specified RGB triple.
	 */
	public synchronized void clear(int rgb) {
		image.clear(rgb);
	}

	/**
	 * Clears the buffer with the specified background image. The image will be tiled starting at the upper-left.
	 */
	public synchronized void clear(BufferedImage bgtx) {
		image.clear(bgtx);
	}

	/**
	 * Fills a rectangular area between (x0, y0) inclusive and (x1, y1) exclusive.
	 *
	 * @param x0 the left side (inclusive)
	 * @param y0 the top side (inclusive)
	 * @param x1 the right side (exclusive)
	 * @param y1 the bottom side (exclusive)
	 */
	public void fillRect(int x0, int y0, int x1, int y1, int color) {
		image.fillRect(x0, y0, x1, y1, color);
	}

	public BufferedImage getImage() {
		return image.image;
	}

	public BufferedImage getImageCopy() {
		return image.getImageCopy();
	}

	public PixelImage getPixelImage() {
		return image;
	}

	/**
	 * This method is called after the component is shown for the first time and all buffers and fields are valid (they
	 * are initialized as {@code null}).
	 */
	public abstract void initialized();

	/**
	 * This method is called every time the window is resized. Additionally this is also called one time after this
	 * component is first shown, because the Java Swing API doesn't consider that a 'resize'. Subclasses should use
	 * this
	 * to start (re)drawing the contents, as all the buffers and fields are renewed.
	 */
	public abstract void resized();

	/**
	 * Initializes this class as if it were only a {@link PixelImage}. This allows subclasses to inherit the abstract
	 * methods of {@link PixelPanel} without needing to write a separate wrapper class.
	 *
	 * @param g a non-null {@link Graphics} that the image will be drawn to instead the panel.
	 */
	public void initNonDisplayed(Graphics g) {
		panelG = g;

		initializeImage();
	}

	@Override
	public void paintComponent(Graphics g) {
		if (panelG == null) {
			panelG = getGraphics();

			// Is it displayable?
			if (panelG == null) {
				return;
			}

			initializeImage();

			// Notify subclasses.
			initialized();
			resized();
		} else if (getWidth() != image.width || getHeight() != image.height) {
			panelG.dispose();
			panelG = getGraphics();

			initializeImage();

			// Notify subclasses.
			resized();
		}

		// Not the same as repaintNow() !!
		// Uses different Graphics instance to prevent flickering.
		// Took me 3 hours to find even with my original PixelWindow.
		g.drawImage(image.image, 0, 0, this);
	}

	private Graphics initializeImage() {
		image = new PixelImage(getWidth(), getHeight());
		g = image.g;
		GraphicsUtilities.setAntialiased(g, true);
		pixels = image.pixels;
		return g;
	}
}
