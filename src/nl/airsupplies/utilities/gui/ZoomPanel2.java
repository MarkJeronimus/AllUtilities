package nl.airsupplies.utilities.gui;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.InputEvent;
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

import nl.airsupplies.utilities.NumberUtilities;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireThat;

/**
 * @author Mark Jeronimus
 */
// Created 2014-05-30
public class ZoomPanel2 extends JPanel implements MouseListener,
                                                  MouseMotionListener,
                                                  MouseWheelListener {
	private @Nullable BufferedImage image = null;

	private double minZoom = 1.0 / 16.0;
	private double maxZoom = 16.0;
	private double zoom    = 1.0;

	private boolean centered = true;

	// The upper-left corner of the image in viewport coordinates
	private int offsetX = 0;
	private int offsetY = 0;

	private int mouseX = 0;
	private int mouseY = 0;

	private @Nullable MouseAdapter imageListener = null;

	@SuppressWarnings("OverridableMethodCallDuringObjectConstruction")
	public ZoomPanel2() {
		super(null);
		setOpaque(true);

		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
	}

	public @Nullable BufferedImage getImage() {
		return image;
	}

	public void setImage(@Nullable BufferedImage image) {
		if (Objects.equals(this.image, image)) {
			return;
		}

		boolean wasEmpty = this.image == null;

		this.image = image;

		if (wasEmpty) {
			zoomFit();
		}

		repaint();
	}

	public double getMinZoom() {
		return minZoom;
	}

	public double getMaxZoom() {
		return maxZoom;
	}

	public void setZoomLimits(double minZoom, double maxZoom) {
		requireThat(minZoom <= maxZoom, () -> minZoom + " < " + maxZoom);

		this.minZoom = minZoom;
		this.maxZoom = maxZoom;

		setZoom(zoom);
	}

	public double getZoom() {
		return zoom;
	}

	public void setZoom(double zoom) {
		this.zoom = NumberUtilities.clamp(zoom, minZoom, maxZoom);
	}

	/**
	 * Fits and centers the image to the viewport.
	 * <p>
	 * This call turns on the 'centered' flag.
	 */
	public void zoomFit() {
		if (image == null) {
			return;
		}

		int viewHeight = getHeight();
		int viewWidth  = getWidth();
		if (viewWidth == 0 || viewHeight == 0) {
			return;
		}

		int imageWidth  = image.getWidth();
		int imageHeight = image.getHeight();

		double zoomX = viewWidth / (double)imageWidth;
		double zoomY = viewHeight / (double)imageHeight;

		zoom = Math.min(zoomX, zoomY);

		center();
	}

	/**
	 * Moves the center of the image to the center of the viewport (without zooming).
	 * <p>
	 * This call turns on the 'centered' flag.
	 */
	private void center() {
		if (image == null) {
			return;
		}

		int viewWidth  = getWidth();
		int viewHeight = getHeight();
		if (viewWidth == 0 || viewHeight == 0) {
			return;
		}

		int displayWidth  = (int)Math.rint(image.getWidth() * zoom);
		int displayHeight = (int)Math.rint(image.getHeight() * zoom);

		offsetX  = viewWidth / 2 - displayWidth / 2;
		offsetY  = viewHeight / 2 - displayHeight / 2;
		centered = true;

		repaint();
	}

	/**
	 * Moves the specified image pixel to the viewport center.
	 * <p>
	 * This call turns off the 'centered' flag.
	 */
	public void setCenter(int x, int y) {
		if (image == null) {
			return;
		}

		int viewWidth  = getWidth();
		int viewHeight = getHeight();
		if (viewWidth == 0 || viewHeight == 0) {
			return;
		}

		int pixelX = (int)Math.rint(x * zoom);
		int pixelY = (int)Math.rint(y * zoom);

		offsetX  = viewWidth / 2 - pixelX;
		offsetY  = viewHeight / 2 - pixelY;
		centered = false;

		repaint();
	}

	public void dragImage(int dx, int dy) {
		offsetX += dx;
		offsetY += dy;
		centered = false;

		repaint();
	}

	/**
	 * Change the zoom in integer or reciprocals-of-integer zoom steps.
	 * <p>
	 * Valid zoom values are [..., 1/4, 1/3, 1/2, 1, 2, 3, 4, ...], but 0.9 and 2.5 would not be an integer zoom steps.
	 * If the current zoom is not an integer zoom step, it is rounded to the nearest before applying the zoom steps.
	 * <p>
	 * Some examples:
	 * <ul><li>zoom 4 + zoomStep 1 = zoom 5</li>
	 * <li>zoom 5 + zoomStep -2 = zoom 3</li>
	 * <li>zoom 3 + zoomStep -1 = zoom 2</li>
	 * <li>zoom 3 + zoomStep -2 = zoom 1</li>
	 * <li>zoom 3 + zoomStep -3 = zoom 1/2</li>
	 * <li>zoom 3 + zoomStep -4 = zoom 1/3</li>
	 * <li>zoom 1/4 + zoomStep 1 = zoom 1/3</li>
	 * <li>zoom 1/4 + zoomStep 2 = zoom 1/2</li>
	 * <li>zoom 1/4 + zoomStep 3 = zoom 1</li>
	 * <li>zoom 1/4 + zoomStep 3 = zoom 2</li>
	 * <li>zoom 2.5 + zoomStep 0 = zoom 3 (rounded to nearest)</li></ul>
	 *
	 * @param zoomSteps The amount of integer 'steps' to take, with positive steps zooming in.
	 */
	public void changeZoom(int x, int y, int zoomSteps) {
		double oldZoom = zoom;

		boolean reciprocal = zoom < 1.0;
		if (reciprocal) {
			zoom      = 1.0 / zoom;
			zoomSteps = -zoomSteps;
		}

		zoom = Math.rint(zoom);

		zoom += zoomSteps;
		if (zoom < 1.0) {
			zoom = 1.0 / (2.0 - zoom);
		}

		if (reciprocal) {
			zoom = 1.0 / zoom;
		}

		zoom = NumberUtilities.clamp(zoom, minZoom, maxZoom);

		// o -= mouse // move: mouse coordinate to origin
		// o *= zoomFactor / oldZoomFactor // scale: by zoom factor ratio
		// o += mouse // move: origin back to mouse position
		// Because of integer arithmetic: Grow before shrinking.
		if (-oldZoom > zoom) {
			offsetX = (int)Math.rint((offsetX - x) / oldZoom * zoom) + x;
			offsetY = (int)Math.rint((offsetY - y) / oldZoom * zoom) + y;
		} else {
			offsetX = (int)Math.rint((offsetX - x) * zoom / oldZoom) + x;
			offsetY = (int)Math.rint((offsetY - y) * zoom / oldZoom) + y;
		}

		centered = false;

		repaint();
	}

	public Point toImageCoordinate(int x, int y) {
		int imageX = (int)Math.floor((x - offsetX) / zoom);
		int imageY = (int)Math.floor((y - offsetY) / zoom);

		return new Point(imageX, imageY);
	}

	public Point toViewportCoordinate(int x, int y) {
		int viewX = offsetX + (int)Math.floor(x * zoom);
		int viewY = offsetY + (int)Math.floor(y * zoom);

		return new Point(viewX, viewY);
	}

	public void setImageListener(@Nullable MouseAdapter imageListener) {
		this.imageListener = imageListener;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (image == null) {
			return;
		}

		int viewWidth   = getWidth();
		int viewHeight  = getHeight();
		int imageWidth  = image.getWidth();
		int imageHeight = image.getHeight();

		int displayWidth  = (int)Math.rint(imageWidth * zoom);
		int displayHeight = (int)Math.rint(imageHeight * zoom);

		// Something goes wrong in the blit engine when enlarging a huge image, so handle it separately.
		if (zoom <= 1.0) {
			g.drawImage(image, offsetX, offsetY, displayWidth, displayHeight, this);
		} else {
			boolean leftInView   = offsetX >= 0;
			boolean topInView    = offsetY >= 0;
			boolean rightInView  = offsetX + displayWidth <= viewWidth;
			boolean bottomInView = offsetY + displayHeight <= viewHeight;

			int viewFromX;
			int viewFromY;
			int viewToX;
			int viewToY;
			int imageFromX;
			int imageFromY;
			int imageToX;
			int imageToY;

			if (!leftInView) {
				imageFromX = (int)Math.floor(-offsetX / zoom);
				viewFromX  = offsetX + (int)Math.floor(imageFromX * zoom);
			} else {
				imageFromX = 0;
				viewFromX  = offsetX;
			}

			if (!topInView) {
				imageFromY = (int)Math.floor(-offsetY / zoom);
				viewFromY  = offsetY + (int)Math.floor(imageFromY * zoom);
			} else {
				imageFromY = 0;
				viewFromY  = offsetY;
			}

			if (rightInView) {
				imageToX = imageWidth;
				viewToX  = offsetX + displayWidth;
			} else {
				imageToX = (int)Math.ceil((viewWidth - offsetX) / zoom);
				viewToX  = offsetX + (int)Math.round(imageToX * zoom);
			}

			if (bottomInView) {
				imageToY = imageHeight;
				viewToY  = offsetY + displayHeight;
			} else {
				imageToY = (int)Math.ceil((viewHeight - offsetY) / zoom);
				viewToY  = offsetY + (int)Math.round(imageToY * zoom);
			}

			g.drawImage(image,
			            viewFromX,
			            viewFromY,
			            viewToX,
			            viewToY,
			            imageFromX,
			            imageFromY,
			            imageToX,
			            imageToY,
			            this);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	@SuppressWarnings("deprecation")
	public void mousePressed(MouseEvent e) {
		requestFocus();

		if (image == null) {
			return;
		}

		if ((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) != 0) {
			mouseX = e.getX();
			mouseY = e.getY();
		}

		if (imageListener != null) {
			Point p = toImageCoordinate(e.getX(), e.getY());
			imageListener.mousePressed(new MouseEvent(
					(Component)e.getSource(),
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
	@SuppressWarnings("deprecation")
	public void mouseReleased(MouseEvent e) {
		if (image == null) {
			return;
		}

		if (imageListener != null) {
			Point p = toImageCoordinate(e.getX(), e.getY());
			imageListener.mouseReleased(new MouseEvent(
					(Component)e.getSource(),
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
	@SuppressWarnings("deprecation")
	public void mouseClicked(MouseEvent e) {
		if (image == null) {
			return;
		}

		if (imageListener != null) {
			Point p = toImageCoordinate(e.getX(), e.getY());
			imageListener.mouseClicked(new MouseEvent(
					(Component)e.getSource(),
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
	@SuppressWarnings("deprecation")
	public void mouseMoved(MouseEvent e) {
		if (image == null) {
			return;
		}

		Point imageCoordinate = toImageCoordinate(e.getX(), e.getY());

		if (imageListener != null) {
			imageListener.mouseMoved(new MouseEvent(
					(Component)e.getSource(),
					e.getID(),
					e.getWhen(),
					e.getModifiers() | e.getModifiersEx(),
					imageCoordinate.x,
					imageCoordinate.y,
					e.getClickCount(),
					e.isPopupTrigger()));
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public void mouseDragged(MouseEvent e) {
		if (image == null) {
			return;
		}

		if ((e.getModifiersEx() & InputEvent.BUTTON1_DOWN_MASK) != 0) {
			int dx = e.getX() - mouseX;
			int dy = e.getY() - mouseY;
			mouseX = e.getX();
			mouseY = e.getY();

			dragImage(dx, dy);
		}

		if (imageListener != null) {
			Point p = toImageCoordinate(e.getX(), e.getY());
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

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (image == null) {
			return;
		}

		int mouseX    = e.getX();
		int mouseY    = e.getY();
		int zoomSteps = -e.getWheelRotation();

		changeZoom(mouseX, mouseY, zoomSteps);
	}
}
