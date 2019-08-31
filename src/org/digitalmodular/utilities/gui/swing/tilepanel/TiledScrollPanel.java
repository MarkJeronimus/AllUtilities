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
package org.digitalmodular.utilities.gui.swing.tilepanel;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.digitalmodular.utilities.gui.GraphicsUtilities;

/**
 * @author Mark Jeronimus
 */
// Created 2011-10-11
public class TiledScrollPanel extends JPanel
		implements MouseListener, MouseMotionListener, MouseWheelListener, ComponentListener {
	public int MIN_ZOOM = -4;
	public int MAX_ZOOM = 6;

	private TileProvider tileProvider;
	private int          tileSize;
	private int          tileShift;

	private int width;
	private int height;
	private int zoom = 0;

	private BufferedImage tileLayer = null;
	private BufferedImage tileLayer2;

	/**
	 * The size of the frame that scrolls. Multiple of TILE_SIZE.
	 */
	private int virtualWidth;
	private int virtualHeight;
	/**
	 * Corner of the viewport, in world coordinates.
	 */
	private int cornerX;
	private int cornerY;
	/**
	 * Difference between the tileLayer and the viewport corners.
	 */
	private int offsetX;
	private int offsetY;

	private int tileX = 0;
	private int tileY = 0;

	private int panX = 0;
	private int panY = 0;

	private int mouseX;
	private int mouseY;

	private boolean abortRenderingFull = false;
	private boolean renderingFull      = false;

	public int mouseCoordinateX;
	public int mouseCoordinateY;

	public TiledScrollPanel(TileProvider tileProvider) {
		this.tileProvider = tileProvider;
		tileSize = tileProvider.getTileSize();
		tileShift = tileProvider.getTileShift();

		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		addComponentListener(this);

		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
	}

	public void setPan(int panX, int panY) {
		this.panX = panX;
		this.panY = panY;
		calcCoordinates();
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
		if (zoom < MIN_ZOOM) {
			zoom = MIN_ZOOM;
		} else if (zoom > MAX_ZOOM) {
			zoom = MAX_ZOOM;
		}
	}

	public int getZoom() {
		return zoom;
	}

	private int calcCoordinates() {
		cornerX = panX - width / 2;
		cornerY = panY - height / 2;

		offsetX = -(cornerX & tileSize - 1);
		offsetY = -(cornerY & tileSize - 1);

		int dx      = tileX - (cornerX >> tileShift);
		int dz      = tileY - (cornerY >> tileShift);
		int updates = (dx > 0 ? 1 : dx < 0 ? 2 : 0) + (dz > 0 ? 3 : dz < 0 ? 6 : 0);

		tileX = cornerX >> tileShift;
		tileY = cornerY >> tileShift;

		return updates;
	}

	final Long[] lastDuration = {100000000L};

	public long renderFull() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				renderFullImpl();
			}
		});

		return lastDuration[0];
	}

	protected void renderFullImpl() {
		long t = System.nanoTime();

		abortRenderingFull = true;
		while (renderingFull) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		renderingFull = true;
		abortRenderingFull = false;

		if (tileLayer == null) {
			return;
		}

		Graphics2D g = tileLayer.createGraphics();

		for (int z = 0; z < virtualHeight; z += tileSize) {
			for (int x = 0; x < virtualWidth; x += tileSize) {
				int u = tileX + (x >> tileShift);
				int v = tileY + (z >> tileShift);

				BufferedImage img = getTile(u, v, zoom);

				g.drawImage(img, x, z, null);

				if (abortRenderingFull) {
					g.dispose();
					renderingFull = false;
					return;
				}
			}
		}

		g.dispose();
		repaint();
		renderingFull = false;
		lastDuration[0] = System.nanoTime() - t;
	}

	private void renderUpdate(int updates) {
		int updateY = updates / 3;
		int updateX = updates - 3 * updateY;

		if (updateX == 1) {
			// Scroll right.
			Graphics2D g = tileLayer2.createGraphics();
			g.drawImage(tileLayer, tileSize, 0, virtualWidth, virtualHeight, 0, 0, virtualWidth - tileSize,
			            virtualHeight, null);

			// Fill left
			for (int y = 0; y < virtualHeight; y += tileSize) {
				int rz = tileY + (y >> tileShift);

				BufferedImage img = getTile(tileX, rz, zoom);

				g.drawImage(img, 0, y, null);
			}

			g.dispose();
			swapBuffers();
		} else if (updateX == 2) {
			// Scroll left.
			Graphics2D g = tileLayer2.createGraphics();
			g.drawImage(tileLayer, 0, 0, virtualWidth - tileSize, virtualHeight, tileSize, 0, virtualWidth,
			            virtualHeight, null);

			// Fill right.
			int x = virtualWidth - tileSize;
			int u = tileX + (x >> tileShift);
			for (int y = 0; y < virtualHeight; y += tileSize) {
				int v = tileY + (y >> tileShift);

				BufferedImage img = getTile(u, v, zoom);

				g.drawImage(img, x, y, null);
			}

			g.dispose();
			swapBuffers();
		}

		if (updateY == 1) {
			// Scroll down.
			Graphics2D g = tileLayer2.createGraphics();
			g.drawImage(tileLayer, 0, tileSize, virtualWidth, virtualHeight, 0, 0, virtualWidth,
			            virtualHeight - tileSize, null);

			for (int x = 0; x < virtualWidth; x += tileSize) {
				int           u   = tileX + (x >> tileShift);
				BufferedImage img = getTile(u, tileY, zoom);

				g.drawImage(img, x, 0, null);
			}

			g.dispose();
			swapBuffers();
		} else if (updateY == 2) {
			// Scroll up.

			Graphics2D g = tileLayer2.createGraphics();
			g.drawImage(tileLayer, 0, 0, virtualWidth, virtualHeight - tileSize, 0, tileSize, virtualWidth,
			            virtualHeight, null);

			int z = virtualHeight - tileSize;
			int v = tileY + (z >> tileShift);
			for (int x = 0; x < virtualWidth; x += tileSize) {
				int u = tileX + (x >> tileShift);

				BufferedImage img = getTile(u, v, zoom);

				g.drawImage(img, x, z, null);
			}

			g.dispose();
			swapBuffers();
		}
	}

	private void swapBuffers() {
		BufferedImage temp = tileLayer;
		tileLayer = tileLayer2;
		tileLayer2 = temp;
	}

	private BufferedImage getTile(int u, int v, int zoom) {
		BufferedImage tile = tileProvider.getTile(u, v, zoom);

		return tile != null ? tile : tileProvider.getDefaultTile();
	}

	@Override
	public void paint(Graphics g) {
		GraphicsUtilities.setAntialiased((Graphics2D)g, false);
		g.drawImage(tileLayer, 0, 0, width, height, -offsetX, -offsetY, width - offsetX, height - offsetY, null);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int dx = mouseX - e.getX();
		int dy = mouseY - e.getY();

		panX += dx;
		panY += dy;
		cornerX += dx;
		cornerY += dy;

		int updates = calcCoordinates();

		if (updates != 0) {
			renderUpdate(updates);
		}

		mouseX = e.getX();
		mouseY = e.getY();

		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();

		if (zoom >= 0) {
			mouseCoordinateX = mouseX + cornerX << zoom;
			mouseCoordinateY = mouseY + cornerY << zoom;
		} else {
			mouseCoordinateX = mouseX + cornerX >> -zoom;
			mouseCoordinateY = mouseY + cornerY >> -zoom;
		}

		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
	}

	@Override
	public void componentHidden(ComponentEvent e) {}

	@Override
	public void componentMoved(ComponentEvent e) {}

	@Override
	public void componentResized(ComponentEvent e) {
		width = getWidth();
		height = getHeight();

		int lastVW = virtualWidth;
		int lastVH = virtualHeight;

		// Round up to the nearest tile size, then expand a tile.
		virtualWidth = width + 2 * tileSize - 1 & -tileSize;
		virtualHeight = height + 2 * tileSize - 1 & -tileSize;

		if (lastVW != virtualWidth || lastVH != virtualHeight) {
			tileLayer = new BufferedImage(virtualWidth, virtualHeight, BufferedImage.TYPE_INT_RGB);
			tileLayer2 = new BufferedImage(virtualWidth, virtualHeight, BufferedImage.TYPE_INT_RGB);
		}

		calcCoordinates();

		renderFull();
	}

	@Override
	public void componentShown(ComponentEvent e) {
		componentResized(null);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int wheelRotation = e.getWheelRotation();
		zoom += wheelRotation;
		if (zoom < MIN_ZOOM) {
			wheelRotation += MIN_ZOOM - zoom;
			zoom = MIN_ZOOM;
		} else if (zoom > MAX_ZOOM) {
			wheelRotation += MAX_ZOOM - zoom;
			zoom = MAX_ZOOM;
		}

		boolean zoomIn = wheelRotation < 0;
		if (zoomIn) {
			wheelRotation = -wheelRotation;
		}

		for (int i = 0; i < wheelRotation; i++) {
			int x = e.getX() - width / 2;
			int y = e.getY() - height / 2;

			int dx = x + panX;
			int dy = y + panY;

			if (zoomIn) {
				panX += dx;
				panY += dy;
			} else {
				panX -= dx >> 1;
				panY -= dy >> 1;
			}
		}
		calcCoordinates();

		renderFullImpl();
	}
}
