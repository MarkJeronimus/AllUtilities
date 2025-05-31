package nl.airsupplies.utilities.gui;

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

import nl.airsupplies.utilities.NumberUtilities;
import nl.airsupplies.utilities.math.PreferredNumbers;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireThat;

/**
 * @author Mark Jeronimus
 */
// Created 2014-05-30
public class ZoomPanel extends JPanel implements MouseListener,
                                                 MouseMotionListener,
                                                 MouseWheelListener,
                                                 KeyListener,
                                                 ComponentListener {
	public static final int MOUSE_PAN_BUTTON = InputEvent.BUTTON2_DOWN_MASK;

	private static final PreferredNumbers ZOOM_STEPS = new PreferredNumbers(2, 16, 20, 25);

	private @Nullable BufferedImage image = null;

	private int minZoom = -16;
	private int maxZoom = 16;
	private int zoom    = 0;

	private boolean centered = true;
	private int     offsetX  = 0;
	private int     offsetY  = 0;

	private int mouseX = 0;
	private int mouseY = 0;

	private @Nullable MouseAdapter imageListener = null;

	@SuppressWarnings("OverridableMethodCallDuringObjectConstruction")
	public ZoomPanel() {
		super(null);
		setOpaque(true);

		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		addKeyListener(this);
		addComponentListener(this);
		setFocusTraversalKeysEnabled(false);
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

		if (image != null) {
			image.setAccelerationPriority(0); // Required top prevent jittering when deep zooming
			setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
		}

		if (wasEmpty && getTopLevelAncestor() != null) {
			zoomFit();
		}

		repaint();
	}

	public int getMinZoom() {
		return minZoom;
	}

	public int getMaxZoom() {
		return maxZoom;
	}

	public void setZoomLimits(int minZoom, int maxZoom) {
		requireThat(minZoom <= maxZoom, () -> minZoom + " < " + maxZoom);

		this.minZoom = minZoom;
		this.maxZoom = maxZoom;

		setZoom(zoom);
	}

	public int getZoom() {
		return zoom;
	}

	/**
	 * Each step away from zero is the next integer zoom ratio. Positive numbers are zoomed in, negative zoomed out.
	 * For example, 0 = 1:1, 1 = 2:1, 2 = 3:1, -1 = 1:2, etc
	 */
	public void setZoom(int zoom) {
		zoom = NumberUtilities.clamp(zoom, minZoom, maxZoom);

		if (this.zoom == zoom) {
			return;
		}

		this.zoom = zoom;
	}

	public void setCenter(int x, int y) {
		if (image == null) {
			return;
		}

		centered = false;

		int imageWidth  = image.getWidth();
		int imageHeight = image.getHeight();

		int displayWidth  = multiplyByZoom(imageWidth);
		int displayHeight = multiplyByZoom(imageHeight);

		offsetX = displayWidth / 2 - multiplyByZoom(x);
		offsetY = displayHeight / 2 - multiplyByZoom(y);

		repaint();
	}

	public void zoomFit() {
		offsetX = 0;
		offsetY = 0;
		zoom    = 0;

		if (image == null) {
			return;
		}

		// Find ideal zoom.
		int width       = Math.max(1, getWidth());
		int height      = Math.max(1, getHeight());
		int imageWidth  = image.getWidth();
		int imageHeight = image.getHeight();
		if (width > imageWidth) {
			zoom = width / imageWidth - 1;
		} else {
			zoom = 1 - (imageWidth / width);
		}

		if (height > imageHeight) {
			zoom = Math.min(zoom, height / imageHeight - 1);
		} else {
			zoom = Math.min(zoom, 1 - (imageHeight / height));
		}

		zoom = NumberUtilities.clamp(zoom, minZoom, maxZoom);

		centered = true;

		repaint();
	}

	public int multiplyByZoom(int value) {
		return multiplyByZoom(value, zoom);
	}

	public static int multiplyByZoom(int value, int zoom) {
		// zoom>0 - (1+zoom):1
		// zoom=0 - 1:1
		// zoom<0 - 1:(1-zoom)
		if (zoom > 0) {
			return value * (1 + zoom);
		} else if (zoom < 0) {
			return (value - zoom) / (1 - zoom);
		}

		return value;
	}

	public void setImageListener(@Nullable MouseAdapter imageListener) {
		this.imageListener = imageListener;
	}

	@Override
	public void paintComponent(Graphics g) {
		// Draw background.
		super.paintComponent(g);

		if (image == null) {
			return;
		}

		int imageWidth  = image.getWidth();
		int imageHeight = image.getHeight();

		int displayWidth  = multiplyByZoom(imageWidth);
		int displayHeight = multiplyByZoom(imageHeight);

		int x = (getWidth() - displayWidth) / 2 + offsetX;
		int y = (getHeight() - displayHeight) / 2 + offsetY;

		if (zoom <= 0) {
			g.drawImage(image, x, y, displayWidth, displayHeight, this);
		} else {
			Point ul        = toImageCoordinate(0, 0);
			int   blockSize = multiplyByZoom(1);
			x += ul.x * blockSize;
			y += ul.y * blockSize;

			int visibleImageWidth  = (getWidth() + blockSize * 2 - 1) / blockSize;
			int visibleImageHeight = (getHeight() + blockSize * 2 - 1) / blockSize;

			// Clip "invisible" portion if image is partially in frame
			if (ul.x < 0) {
				x -= ul.x * blockSize;
				visibleImageWidth += ul.x;
				ul.x = 0;
			}

			if (ul.y < 0) {
				y -= ul.y * blockSize;
				visibleImageHeight += ul.y;
				ul.y = 0;
			}

			int excess = ul.x + visibleImageWidth - imageWidth;
			if (excess > 0) {
				visibleImageWidth -= excess;
			}

			excess = ul.y + visibleImageHeight - imageHeight;
			if (excess > 0) {
				visibleImageHeight -= excess;
			}
//			System.out.println((x)+"\t"+
//			                   (y)+"\t"+
//			                   (x + visibleImageWidth * blockSize)+"\t"+
//			                   (y + visibleImageHeight * blockSize)+"\t"+
//			                   (ul.x)+"\t"+
//			                   (ul.y)+"\t"+
//			                   (ul.x + visibleImageWidth)+"\t"+
//			                   (ul.y + visibleImageHeight));
			g.drawImage(image,
			            x,
			            y,
			            x + visibleImageWidth * blockSize,
			            y + visibleImageHeight * blockSize,
			            ul.x,
			            ul.y,
			            ul.x + visibleImageWidth,
			            ul.y + visibleImageHeight,
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

		if ((e.getModifiersEx() & MOUSE_PAN_BUTTON) != 0) {
			mouseX = e.getX();
			mouseY = e.getY();
		}

		if (imageListener != null) {
			Point p = toImageCoordinate(e.getX(), e.getY());
			if (insideImage(p.x, p.y)) {
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
	}

	@Override
	@SuppressWarnings("deprecation")
	public void mouseReleased(MouseEvent e) {
		if (image == null) {
			return;
		}

		if (imageListener != null) {
			Point p = toImageCoordinate(e.getX(), e.getY());
			if (insideImage(p.x, p.y)) {
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
	}

	@Override
	@SuppressWarnings("deprecation")
	public void mouseClicked(MouseEvent e) {
		if (image == null) {
			return;
		}

		if (imageListener != null) {
			Point p = toImageCoordinate(e.getX(), e.getY());
			if (insideImage(p.x, p.y)) {
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
	}

	@Override
	@SuppressWarnings("deprecation")
	public void mouseMoved(MouseEvent e) {
		if (image == null) {
			return;
		}

		if (imageListener != null) {
			Point p = toImageCoordinate(e.getX(), e.getY());
			if (insideImage(p.x, p.y)) {
				imageListener.mouseMoved(new MouseEvent(
						(Component)e.getSource(),
						e.getID(),
						e.getWhen(),
						e.getModifiers() | e.getModifiersEx(),
						p.x,
						p.y,
						e.getClickCount(),
						e.isPopupTrigger()));
			}
		}
	}

	@Override
	@SuppressWarnings("deprecation")
	public void mouseDragged(MouseEvent e) {
		if (image == null) {
			return;
		}

		if ((e.getModifiersEx() & MOUSE_PAN_BUTTON) != 0) {
			offsetX += e.getX() - mouseX;
			offsetY += e.getY() - mouseY;

			mouseX = e.getX();
			mouseY = e.getY();

			centered = false;
			repaint();
		}

		if (imageListener != null) {
			Point p = toImageCoordinate(e.getX(), e.getY());
			if (insideImage(p.x, p.y)) {
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
	}

	private Point toImageCoordinate(int x, int y) {
		assert image != null;

		int imageWidth  = image.getWidth();
		int imageHeight = image.getHeight();

		int displayWidth  = multiplyByZoom(imageWidth);
		int displayHeight = multiplyByZoom(imageHeight);

		x -= (getWidth() - displayWidth) / 2 + offsetX;
		y -= (getHeight() - displayHeight) / 2 + offsetY;

		x = multiplyByZoom(x, -zoom);
		y = multiplyByZoom(y, -zoom);

		return new Point(x, y);
	}

	private boolean insideImage(int x, int y) {
		assert image != null;

		int imageWidth  = image.getWidth();
		int imageHeight = image.getHeight();
		return x >= 0 && y >= 0 && x < imageWidth && y < imageHeight;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (image == null) {
			return;
		}

		int mouseX        = e.getX() - getWidth() / 2;
		int mouseY        = e.getY() - getHeight() / 2;
		int wheelRotation = -e.getWheelRotation();

		int newZoom = zoom;

		if (newZoom < -6) {
			int log = ZOOM_STEPS.log(2 - newZoom);
			newZoom = 1 - (int)Math.rint(ZOOM_STEPS.exp(log - wheelRotation));
		} else if (newZoom > 6) {
			int log = ZOOM_STEPS.log(2 + newZoom);
			newZoom = (int)Math.rint(ZOOM_STEPS.exp(log + wheelRotation)) - 1;
		} else {
			newZoom += wheelRotation;
		}

		newZoom = NumberUtilities.clamp(newZoom, minZoom, maxZoom);

		if (!(newZoom >= 0)) {
			System.out.println("zoom: " + newZoom + " (1:" + (1 - newZoom) + ')');
		} else {
			System.out.println("zoom: " + newZoom + " (" + (1 + newZoom) + ":1)");
		}

		int offsetX;
		int offsetY;

		// o -= mouse                      // move: mouse coordinate to origin
		// o *= zoomFactor / oldZoomFactor // scale: by zoom factor ratio
		// o += mouse                      // move: origin back to mouse position
		// Because of integer arithmetic: Grow before shrinking.
		if (-zoom > newZoom) {
			offsetX = multiplyByZoom(multiplyByZoom(this.offsetX - mouseX, -zoom), newZoom) + mouseX;
			offsetY = multiplyByZoom(multiplyByZoom(this.offsetY - mouseY, -zoom), newZoom) + mouseY;
		} else {
			offsetX = multiplyByZoom(multiplyByZoom(this.offsetX - mouseX, newZoom), -zoom) + mouseX;
			offsetY = multiplyByZoom(multiplyByZoom(this.offsetY - mouseY, newZoom), -zoom) + mouseY;
		}

		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.zoom    = zoom;

		centered = false;
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (image == null) {
			return;
		}

		if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
			// Toggle between fit and 1:1
			if (!centered) {
				zoomFit();
			} else {
				zoomFit();
				centered = zoom == 0;
				zoom     = 0;
			}

			repaint();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void componentResized(ComponentEvent e) {
		if (image == null || !centered) {
			return;
		}

		repaint();
	}

	@Override
	public void componentMoved(ComponentEvent e) {
	}

	@Override
	public void componentShown(ComponentEvent e) {
	}

	@Override
	public void componentHidden(ComponentEvent e) {
	}
}
