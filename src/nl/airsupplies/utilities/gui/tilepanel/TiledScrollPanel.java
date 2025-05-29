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

package nl.airsupplies.utilities.gui.tilepanel;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

import org.jetbrains.annotations.Nullable;

import nl.airsupplies.utilities.NumberUtilities;
import nl.airsupplies.utilities.gui.GUIUtilities;

/**
 * @author Mark Jeronimus
 */
// Created 2011-10-11
public class TiledScrollPanel extends JPanel
		implements MouseListener, MouseMotionListener, MouseWheelListener, ComponentListener {
	private int minZoom = -6;
	private int maxZoom = 4;

	private final TileProvider tileProvider;
	private final int          tileSize;
	private final int          tileShift;

	private int width;
	private int height;
	private int zoom = 0;

	private BufferedImage tileLayer = null;
	private BufferedImage tileLayer2;

	/**
	 * Size of the tileLayer. This is always a multiple of tileSize.
	 */
	private int virtualWidth  = 0;
	private int virtualHeight = 0;
	/**
	 * Corner of the viewport, in world coordinates.
	 */
	private int cornerX       = 0;
	private int cornerY       = 0;
	/**
	 * Center of the viewport, in world coordinates.
	 */
	private int centerX       = 0;
	private int centerY       = 0;
	/**
	 * Corner of the viewport, in tileLayer coordinates.
	 */
	private int offsetX       = 0;
	private int offsetY       = 0;
	/**
	 * Upper-left tile in the tileLayer.
	 */
	private int tileU         = 0;
	private int tileV         = 0;

	private int mouseX = 0;
	private int mouseY = 0;

	private final boolean abortRenderingFull = false;

	private final @Nullable MouseAdapter tileListener = null;

	public TiledScrollPanel(TileProvider tileProvider) {
		super(null);

		this.tileProvider = tileProvider;
		tileSize          = tileProvider.getTileSize();
		tileShift         = tileProvider.getTileShift();

		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		addComponentListener(this);

		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
	}

	public void setZoomLimits(int minZoom, int maxZoom) {
		this.minZoom = minZoom;
		this.maxZoom = maxZoom;
	}

	public int getMinZoom() {
		return minZoom;
	}

	public int getMaxZoom() {
		return maxZoom;
	}

	public void setPan(int panX, int panY) {
		centerX = panX;
		centerY = panY;
		calcCoordinates();
	}

	public void setZoom(int zoom) {
		this.zoom = NumberUtilities.clamp(zoom, minZoom, maxZoom);
	}

	public int getZoom() {
		return zoom;
	}

	public void updateTile(int u, int v) {
		int x = (u - tileU) << tileShift;
		int y = (v - tileV) << tileShift;

		if (x < 0 || y < 0 || x >= virtualWidth || y >= virtualHeight) {
			return;
		}

		Graphics2D g = tileLayer.createGraphics();
		try {
			BufferedImage img = getTile(u, v, zoom);

			g.drawImage(img, x, y, null);
		} finally {
			g.dispose();
		}

		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		GUIUtilities.setAntialiased((Graphics2D)g, false);
		g.drawImage(tileLayer,
		            0, 0, width, height,
		            -offsetX, -offsetY, width - offsetX, height - offsetY,
		            null);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int dx = mouseX - e.getX();
		int dy = mouseY - e.getY();

		centerX += dx;
		centerY += dy;
		cornerX += dx;
		cornerY += dy;

		int updates = calcCoordinates();
		if (updates != 0) {
			viewDragged(updates);
		}

		mouseX = e.getX();
		mouseY = e.getY();

		repaint();
	}

	@Override
	@SuppressWarnings("deprecation")
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();

		int mouseCoordinateX;
		int mouseCoordinateY;
		if (zoom >= 0) {
			mouseCoordinateX = mouseX + cornerX >> zoom;
			mouseCoordinateY = mouseY + cornerY >> zoom;
		} else {
			mouseCoordinateX = mouseX + cornerX << -zoom;
			mouseCoordinateY = mouseY + cornerY << -zoom;
		}

		if (tileListener != null) {
			tileListener.mouseMoved(new MouseEvent((Component)e.getSource(),
			                                       e.getID(),
			                                       e.getWhen(),
			                                       e.getModifiers() | e.getModifiersEx(),
			                                       mouseCoordinateX,
			                                       mouseCoordinateY,
			                                       e.getClickCount(),
			                                       e.isPopupTrigger()));
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentResized(@Nullable ComponentEvent e) {
		width  = getWidth();
		height = getHeight();

		int lastVW = virtualWidth;
		int lastVH = virtualHeight;

		// Round up to the nearest tile size, then expand a tile.
		virtualWidth  = width + 2 * tileSize - 1 & -tileSize;
		virtualHeight = height + 2 * tileSize - 1 & -tileSize;

		if (lastVW != virtualWidth || lastVH != virtualHeight) {
			tileLayer  = new BufferedImage(virtualWidth, virtualHeight, BufferedImage.TYPE_INT_RGB);
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
		int newZoom       = NumberUtilities.clamp(zoom - wheelRotation, minZoom, maxZoom);
		if (newZoom == zoom) {
			return;
		}

		zoom = newZoom;

		boolean zoomOut = wheelRotation < 0;
		if (zoomOut) {
			wheelRotation = -wheelRotation;
		}

		for (int i = 0; i < wheelRotation; i++) {
			int x = e.getX() - width / 2;
			int y = e.getY() - height / 2;

			int dx = x + centerX;
			int dy = y + centerY;

			if (zoomOut) {
				centerX += dx;
				centerY += dy;
			} else {
				centerX -= dx >> 1;
				centerY -= dy >> 1;
			}
		}

		calcCoordinates();
		renderFull();
	}

	private int calcCoordinates() {
		cornerX = centerX - width / 2;
		cornerY = centerY - height / 2;

		offsetX = -(cornerX & tileSize - 1);
		offsetY = -(cornerY & tileSize - 1);

		int dx      = tileU - (cornerX >> tileShift);
		int dz      = tileV - (cornerY >> tileShift);
		int updates = (dx > 0 ? 1 : dx < 0 ? 2 : 0) + (dz > 0 ? 3 : dz < 0 ? 6 : 0);

		tileU = cornerX >> tileShift;
		tileV = cornerY >> tileShift;

		return updates;
	}

	private void renderFull() {
		if (tileLayer == null) {
			return;
		}

		Graphics2D g = tileLayer.createGraphics();
		try {
			g.setPaint(getBackground());
			g.fillRect(0, 0, virtualWidth, virtualHeight);

			for (int y = 0; y < virtualHeight; y += tileSize) {
				for (int x = 0; x < virtualWidth; x += tileSize) {
					int u = tileU + (x >> tileShift);
					int v = tileV + (y >> tileShift);

					BufferedImage img = getTile(u, v, zoom);

					g.drawImage(img, x, y, null);
				}
			}
		} finally {
			g.dispose();
		}

		repaint();
	}

	private void viewDragged(int updates) {
		int updateX = updates % 3;
		int updateY = updates / 3;

		if (updateX == 1) {
			// Scroll right.
			Graphics2D g = tileLayer2.createGraphics();
			try {
				g.drawImage(tileLayer,
				            tileSize, 0, virtualWidth, virtualHeight,
				            0, 0, virtualWidth - tileSize, virtualHeight,
				            null);

				// Fill left
				for (int y = 0; y < virtualHeight; y += tileSize) {
					int rz = tileV + (y >> tileShift);

					BufferedImage img = getTile(tileU, rz, zoom);

					g.drawImage(img, 0, y, null);
				}

			} finally {
				g.dispose();
			}
			swapBuffers();
		} else if (updateX == 2) {
			// Scroll left.
			Graphics2D g = tileLayer2.createGraphics();
			try {
				g.drawImage(tileLayer,
				            0, 0, virtualWidth - tileSize, virtualHeight,
				            tileSize, 0, virtualWidth, virtualHeight,
				            null);

				// Fill right.
				int x = virtualWidth - tileSize;
				int u = tileU + (x >> tileShift);
				for (int y = 0; y < virtualHeight; y += tileSize) {
					int v = tileV + (y >> tileShift);

					BufferedImage img = getTile(u, v, zoom);

					g.drawImage(img, x, y, null);
				}

			} finally {
				g.dispose();
			}
			swapBuffers();
		}

		if (updateY == 1) {
			// Scroll down.
			Graphics2D g = tileLayer2.createGraphics();
			try {
				g.drawImage(tileLayer,
				            0, tileSize, virtualWidth, virtualHeight,
				            0, 0, virtualWidth, virtualHeight - tileSize,
				            null);

				for (int x = 0; x < virtualWidth; x += tileSize) {
					int           u   = tileU + (x >> tileShift);
					BufferedImage img = getTile(u, tileV, zoom);

					g.drawImage(img, x, 0, null);
				}

			} finally {
				g.dispose();
			}
			swapBuffers();
		} else if (updateY == 2) {
			// Scroll up.

			Graphics2D g = tileLayer2.createGraphics();
			try {
				g.drawImage(tileLayer,
				            0, 0, virtualWidth, virtualHeight - tileSize,
				            0, tileSize, virtualWidth, virtualHeight,
				            null);

				int z = virtualHeight - tileSize;
				int v = tileV + (z >> tileShift);
				for (int x = 0; x < virtualWidth; x += tileSize) {
					int u = tileU + (x >> tileShift);

					BufferedImage img = getTile(u, v, zoom);

					g.drawImage(img, x, z, null);
				}

			} finally {
				g.dispose();
			}
			swapBuffers();
		}
	}

	private void swapBuffers() {
		BufferedImage temp = tileLayer;
		tileLayer  = tileLayer2;
		tileLayer2 = temp;

		Graphics2D g = tileLayer2.createGraphics();
		try {
			g.setPaint(getBackground());
			g.fillRect(0, 0, virtualWidth, virtualHeight);
		} finally {
			g.dispose();
		}
	}

	private BufferedImage getTile(int u, int v, int zoom) {
		@Nullable BufferedImage tile = tileProvider.getTile(u, v, zoom);

		return tile != null ? tile : tileProvider.getDefaultTile(u, v, zoom);
	}
}
