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
package org.digitalmodular.utilities.gui.swing;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.Objects;
import javax.swing.JPanel;

import org.jetbrains.annotations.Nullable;

import org.digitalmodular.utilities.NumberUtilities;
import static org.digitalmodular.utilities.ValidatorUtilities.requireThat;

/**
 * @author Mark Jeronimus
 */
// Created 2014-05-30
public class ZoomPanel extends JPanel implements MouseListener,
                                                 MouseMotionListener,
                                                 MouseWheelListener,
                                                 KeyListener,
                                                 ComponentListener {
	private @Nullable BufferedImage image = null;

	private int minZoom = -16;
	private int maxZoom = 16;
	private int zoom    = 0;

	private boolean centered = true;
	private int     offsetX  = 0;
	private int     offsetY  = 0;

	private int mouseX;
	private int mouseY;

	private @Nullable MouseAdapter imageListener = null;

	public ZoomPanel() {
		super(null);
		setPreferredSize(new Dimension(640, 640));
		setOpaque(false);

		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		addKeyListener(this);
		addComponentListener(this);
	}

	public @Nullable BufferedImage getImage() {
		return image;
	}

	public void setImage(@Nullable BufferedImage image) {
		if (Objects.equals(this.image, image))
			return;

		this.image = image;

		if (image != null)
			setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));

		resetZoom();
		repaint();
	}

	public int getMinZoom() { return minZoom; }

	public int getMaxZoom() { return maxZoom; }

	public void setZoomLimits(int minZoom, int maxZoom) {
		requireThat(minZoom <= maxZoom, minZoom + " < " + maxZoom);

		this.minZoom = minZoom;
		this.maxZoom = maxZoom;
	}

	public int getZoom() { return zoom; }

	/*
	 * The allowed range to zoom the image. Negative values are zoomed out, positive zoom in. Each unit further from
	 * zero is the next integer zoom ratio: 0 = 1:1, 1 = 2:1, 2 = 3:1, -1 = 1:2, etc
	 */
	public void setZoom(int zoom) {
		this.zoom = NumberUtilities.clamp(minZoom, maxZoom, zoom);
	}

	public void resetZoom() {
		offsetX = 0;
		offsetY = 0;
		zoom = 0;

		if (image == null)
			return;

		// Find ideal zoom.
		int width       = Math.max(1, getWidth());
		int height      = Math.max(1, getHeight());
		int imageWidth  = image.getWidth();
		int imageHeight = image.getHeight();
		if (width > imageWidth)
			zoom = width / imageWidth - 1;
		else
			zoom = -(imageWidth / width);

		if (height > imageHeight)
			zoom = Math.min(zoom, height / imageHeight - 1);
		else
			zoom = Math.min(zoom, -(imageHeight / height));

		zoom = NumberUtilities.clamp(minZoom, maxZoom, zoom);

		centered = true;

		repaint();
	}

	public void setCenter(int x, int y) {
		if (image == null)
			return;

		centered = false;

		int imageWidth  = image.getWidth();
		int imageHeight = image.getHeight();

		int displayWidth  = multiplyByZoom(imageWidth, zoom);
		int displayHeight = multiplyByZoom(imageHeight, zoom);

		offsetX = displayWidth / 2 - multiplyByZoom(x, zoom);
		offsetY = displayHeight / 2 - multiplyByZoom(y, zoom);

		repaint();
	}

	private static int multiplyByZoom(int value, int zoom) {
		// zoom>0 - (1+zoom):1
		// zoom=0 - 1:1
		// zoom<0 - 1:(1-zoom)
		if (zoom > 0)
			return value * (1 + zoom);
		else if (zoom < 0)
			return value / (1 - zoom);

		return value;
	}

	private static int divideByZoom(int value, int zoom) {
		// zoom>0 - (1+zoom):1
		// zoom=0 - 1:1
		// zoom<0 - 1:(1-zoom)
		if (zoom > 0)
			return value / (1 + zoom);
		else if (zoom < 0)
			return value * (1 - zoom);

		return value;
	}

	public void setImageListener(@Nullable MouseAdapter imageListener) {
		this.imageListener = imageListener;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// JPanel methods
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void paintComponent(Graphics g) {
		// Draw background.
		super.paintComponent(g);

		if (image == null)
			return;

		int imageWidth  = image.getWidth();
		int imageHeight = image.getHeight();

		int displayWidth  = multiplyByZoom(imageWidth, zoom);
		int displayHeight = multiplyByZoom(imageHeight, zoom);

		int x = (getWidth() - displayWidth) / 2 + offsetX;
		int y = (getHeight() - displayHeight) / 2 + offsetY;

		g.drawImage(image, x, y, displayWidth, displayHeight, this);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// MouseListener methods
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		requestFocus();

		if (image == null)
			return;

		if ((e.getModifiersEx() & InputEvent.BUTTON2_DOWN_MASK) != 0) {
			mouseX = e.getX();
			mouseY = e.getY();
		}

		if (imageListener != null) {
			Point p = toImageCoordinate(e);
			if (p != null)
				imageListener.mousePressed(new MouseEvent((Component)e.getSource(),
				                                          e.getID(),
				                                          e.getWhen(),
				                                          e.getModifiers() | e.getModifiersEx(),
				                                          p.x,
				                                          p.y,
				                                          e.getClickCount(),
				                                          e.isPopupTrigger(),
				                                          e.getButton()));
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (image == null)
			return;

		if (imageListener != null) {
			Point p = toImageCoordinate(e);
			if (p != null)
				imageListener.mouseReleased(new MouseEvent((Component)e.getSource(),
				                                           e.getID(),
				                                           e.getWhen(),
				                                           e.getModifiers() | e.getModifiersEx(),
				                                           p.x,
				                                           p.y,
				                                           e.getClickCount(),
				                                           e.isPopupTrigger(),
				                                           e.getButton()));
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (image == null)
			return;

		if (imageListener != null) {
			Point p = toImageCoordinate(e);
			if (p != null)
				imageListener.mouseClicked(new MouseEvent((Component)e.getSource(),
				                                          e.getID(),
				                                          e.getWhen(),
				                                          e.getModifiers() | e.getModifiersEx(),
				                                          p.x,
				                                          p.y,
				                                          e.getClickCount(),
				                                          e.isPopupTrigger(),
				                                          e.getButton()));

		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (image == null)
			return;

		if (imageListener != null) {
			Point p = toImageCoordinate(e);
			if (p != null)
				imageListener.mouseMoved(new MouseEvent((Component)e.getSource(),
				                                        e.getID(),
				                                        e.getWhen(),
				                                        e.getModifiers() | e.getModifiersEx(),
				                                        p.x,
				                                        p.y,
				                                        e.getClickCount(),
				                                        e.isPopupTrigger()));
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (image == null)
			return;

		if ((e.getModifiersEx() & InputEvent.BUTTON2_DOWN_MASK) != 0) {
			offsetX += e.getX() - mouseX;
			offsetY += e.getY() - mouseY;

			mouseX = e.getX();
			mouseY = e.getY();

			centered = false;
			repaint();
		}

		if (imageListener != null) {
			Point p = toImageCoordinate(e);
			if (p != null)
				imageListener.mouseDragged(new MouseEvent((Component)e.getSource(),
				                                          e.getID(),
				                                          e.getWhen(),
				                                          e.getModifiers() | e.getModifiersEx(),
				                                          p.x,
				                                          p.y,
				                                          e.getClickCount(),
				                                          e.isPopupTrigger()));
		}
	}

	private @Nullable Point toImageCoordinate(MouseEvent e) {
		assert image != null;

		int imageWidth  = image.getWidth();
		int imageHeight = image.getHeight();

		int displayWidth  = multiplyByZoom(imageWidth, zoom);
		int displayHeight = multiplyByZoom(imageHeight, zoom);

		int x = e.getX() - (getWidth() - displayWidth) / 2 - offsetX;
		int y = e.getY() - (getHeight() - displayHeight) / 2 - offsetY;

		x = divideByZoom(x, zoom);
		y = divideByZoom(y, zoom);

		if (x < 0 || y < 0 || x >= imageWidth || y >= imageHeight)
			return null;

		return new Point(x, y);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// MouseWheelListener methods
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (image == null)
			return;

		int mouseX        = e.getX() - getWidth() / 2;
		int mouseY        = e.getY() - getHeight() / 2;
		int wheelRotation = -e.getWheelRotation();

		int oldZoom = zoom;

		zoom += wheelRotation;
		zoom = NumberUtilities.clamp(minZoom, maxZoom, zoom);

		// o -= mouse // move: mouse coordinate to origin
		// o *= zoomfactor / oldzoomfactor // scale: by zoom factor ratio
		// o += mouse // move: origin back to mouse position
		// Because of integer arithmetic: Grow before shrinking.
		if (-oldZoom > zoom) {
			offsetX = multiplyByZoom(multiplyByZoom(offsetX - mouseX, -oldZoom), zoom) + mouseX;
			offsetY = multiplyByZoom(multiplyByZoom(offsetY - mouseY, -oldZoom), zoom) + mouseY;
		} else {
			offsetX = multiplyByZoom(multiplyByZoom(offsetX - mouseX, zoom), -oldZoom) + mouseX;
			offsetY = multiplyByZoom(multiplyByZoom(offsetY - mouseY, zoom), -oldZoom) + mouseY;
		}

		centered = false;
		repaint();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// KeyListener methods
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void keyTyped(KeyEvent e) {
		if (image == null)
			return;

		if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
			// Toggle between fit and 1:1
			if (!centered) {
				resetZoom();
			} else {
				resetZoom();
				centered = zoom == 0;
				zoom = 0;
			}
			repaint();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// ComponentListener methods
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void componentResized(ComponentEvent e) {
		if (image == null || !centered)
			return;

		repaint();
	}

	@Override
	public void componentMoved(ComponentEvent e) {}

	@Override
	public void componentShown(ComponentEvent e) {}

	@Override
	public void componentHidden(ComponentEvent e) {}
}
