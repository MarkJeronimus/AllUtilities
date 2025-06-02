package nl.airsupplies.utilities.gui;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jetbrains.annotations.Nullable;

import nl.airsupplies.utilities.annotation.UtilityClass;

/**
 * @author Mark Jeronimus
 */
// Created 2017-01-03
@UtilityClass
public final class GUIUtilities {
	/**
	 * Shorthand to enable a nice look and feel (Currently, Nimbus).
	 */
	public static void setNiceLookAndFeel() {
		requireOnEDT();
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			UIManager.put("ProgressBar.cycleTime", 500);
			UIManager.put("ProgressBar.repaintInterval", 20);
		} catch (ReflectiveOperationException | UnsupportedLookAndFeelException ignored) {
		}
	}

	/**
	 * Shorthand to get the default screen object.
	 * <p>
	 * Don't use this to obtain the resolution of the screen, this fails on Linux with multiple screens.
	 * Use {@link #getScreenRect(int)} instead.
	 */
	public static GraphicsDevice getDisplayDevice() {
		return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	}

	/**
	 * Shorthand to get the display mode of the default screen.
	 * <p>
	 * Don't use this to obtain the resolution of the screen, this fails on Linux with multiple screens.
	 * Use {@link #getScreenRect(int)} instead.
	 */
	public static DisplayMode getDisplayMode() {
		return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode();
	}

	/**
	 * Shorthand to get the framebuffer bounds of the specified screen.
	 * <p>
	 * To assist iteration, an empty dimension is returned if the screen index is invalid.
	 */
	public static Rectangle getScreenRect(int index) {
		GraphicsDevice[] screenDevices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();

		if (index < screenDevices.length) {
			return screenDevices[index].getDefaultConfiguration().getBounds();
		}

		return new Rectangle();
	}

	/**
	 * Get a valid display mode.
	 *
	 * @return the display mode, or null if none matching the parameters was
	 * found.
	 */
	public static @Nullable DisplayMode getDisplayMode(int width, int height) {
		GraphicsDevice displayDevice = getDisplayDevice();
		DisplayMode    currentMode   = displayDevice.getDisplayMode();
		DisplayMode[]  modes         = displayDevice.getDisplayModes();

		int bitDepth    = currentMode.getBitDepth();
		int refreshRate = currentMode.getRefreshRate();

		for (DisplayMode mode : modes) {
			//noinspection OverlyComplexBooleanExpression // Suppress IntelliJ bug
			if (mode.getHeight() == height &&
			    mode.getWidth() == width &&
			    mode.getBitDepth() == bitDepth &&
			    mode.getRefreshRate() == refreshRate) {
				return mode;
			}
		}

		refreshRate = Integer.MAX_VALUE;
		int index = -1;
		for (int i = modes.length - 1; i >= 0; i--) {
			DisplayMode mode = modes[i];
			if (mode.getHeight() == height &&
			    mode.getWidth() == width &&
			    mode.getBitDepth() == bitDepth) {
				if (refreshRate > mode.getRefreshRate()) {
					refreshRate = mode.getRefreshRate();
					index       = i;
				}
			}
		}

		return index < 0 ? null : modes[index];
	}

	/**
	 * Shorthand to make a frame full-screen on the default screen.
	 */
	public static void setFullScreenWindow(JFrame frame) {
		GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);
	}

	public static void requireOnEDT() {
		if (!SwingUtilities.isEventDispatchThread()) {
			throw new AssertionError("May only be called from the EDT");
		}
	}

	public static void requireNotOnEDT() {
		if (SwingUtilities.isEventDispatchThread()) {
			throw new AssertionError("May not be called from the EDT");
		}
	}

	/**
	 * Executes the specified {@link Runnable} on the EDT thread. If the calling thread is the EDT thread, the
	 * specified {@code runnable} is invoked directly, otherwise it's placed on Swing's event dispatch queue and the
	 * method returns immediately.
	 * <p>
	 * Like with any callback, be sure not to throw unchecked exceptions,
	 * because these will crash the EDT thread, and with it, the application.
	 */
	public static void runOnEDT(Runnable runnable) {
		if (SwingUtilities.isEventDispatchThread()) {
			runnable.run();
		} else {
			SwingUtilities.invokeLater(runnable);
		}
	}

	/**
	 * Executes the specified {@link Callable} on the EDT thread. If the calling thread is the EDT thread, the
	 * specified {@code callable} is invoked directly, otherwise it's placed on Swing's event dispatch queue and the
	 * method waits for the result.
	 *
	 * @param <V>      the result type of the {@code callable}
	 * @param callable the callable task
	 * @return computed result
	 * @throws InterruptedException if we're interrupted while waiting for the
	 *                              event dispatching thread to finish executing doRun.run()
	 */
	@SuppressWarnings("ProhibitedExceptionThrown")
	public static <V> V getFromEDT(Callable<V> callable) throws InterruptedException {
		RunnableFuture<V> f = new FutureTask<>(callable);

		if (SwingUtilities.isEventDispatchThread()) {
			f.run();
		} else {
			SwingUtilities.invokeLater(f);
		}

		try {
			return f.get();
		} catch (ExecutionException ex) {
			Throwable th = ex.getCause();

			// Launder exception
			if (th instanceof RuntimeException) {
				throw (RuntimeException)th;
			} else if (th instanceof Error) {
				throw (Error)th;
			} else {
				throw new RuntimeException(th);
			}
		}
	}
}
