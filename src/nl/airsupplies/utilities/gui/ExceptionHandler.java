package nl.airsupplies.utilities.gui;

import java.awt.Component;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import nl.airsupplies.utilities.exception.ExceptionUtilities;
import nl.airsupplies.utilities.exception.ExceptionManager;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * @author Mark Jeronimus
 */
// Created 2018-04-24
@SuppressWarnings("FieldCanBeLocal")
public class ExceptionHandler {
	private final Component parent;

	private final ExceptionPanel exceptionPanel = new ExceptionPanel();

	public ExceptionHandler(Component parent) {
		this.parent = requireNonNull(parent, "parent");

		ExceptionManager.addUncaughtExceptionHandler(uncaughtExceptionShower);
		ExceptionManager.addWarningHandler(warningShower);
		ExceptionManager.addRetryableExceptionHandler(retryableExceptionShower);
		ExceptionManager.addUnrecoverableErrorHandler(showUnrecoverableErrorDialog);

		Thread.setDefaultUncaughtExceptionHandler(ExceptionManager.getUncaughtExceptionHandler());
	}

	private final Consumer<Throwable> uncaughtExceptionShower = new Consumer<Throwable>() {
		private boolean showing = false;

		@Override
		public void accept(Throwable th) {
			if (showing) {
				return;
			}

			showing = true;
			//noinspection OverlyBroadCatchBlock
			try {
				GraphicsUtilities.getFromEDT(() -> {
					showUncaughtExceptionDialog(th);
					return (Void)null;
				});
			} catch (Throwable th2) {
				ExceptionManager.die(th2);
			} finally {
				showing = false;
			}
		}
	};

	public void showUncaughtExceptionDialog(Throwable th) {
		boolean showDetails = false;
		while (true) {
			exceptionPanel.setTitle(th.getMessage());
			exceptionPanel.setSubtitle(th.getClass().getSimpleName());
			if (showDetails) {
				exceptionPanel.setDetailMessage(ExceptionUtilities.exceptionToString(th));
			} else {
				exceptionPanel.setDetailMessage(null);
			}

			int result = showExceptionDialog(exceptionPanel,
			                                 new String[]{(showDetails ? "Hide details" : "Details"),
			                                              "Close"}, "Close");

			if (result == 0) { // Toggle details if "Details"
				showDetails ^= true;
			} else {
				break;
			}
		}
	}

	private final BiConsumer<String, Exception> warningShower = new BiConsumer<String, Exception>() {
		private boolean showing = false;

		@Override
		public void accept(String message, Exception ex) {
			if (showing) {
				return;
			}

			showing = true;
			//noinspection OverlyBroadCatchBlock
			try {
				GraphicsUtilities.getFromEDT(() -> {
					showWarningDialog(message, ex);
					return (Void)null;
				});
			} catch (Throwable th) {
				ExceptionManager.die(th);
			} finally {
				showing = false;
			}
		}
	};

	public void showWarningDialog(String message, Exception ex) {
		boolean showDetails = false;
		while (true) {
			exceptionPanel.setTitle(message);
			exceptionPanel.setSubtitle(message.equals(ex.getMessage()) ?
			                           ex.getClass().getSimpleName() :
			                           ex.getMessage());
			if (showDetails) {
				exceptionPanel.setDetailMessage(ExceptionUtilities.exceptionToString(ex));
			} else {
				exceptionPanel.setDetailMessage(null);
			}

			int result = showExceptionDialog(exceptionPanel,
			                                 new String[]{(showDetails ? "Hide details" : "Details"),
			                                              "Close"}, "Close");

			if (result == 0) { // Toggle details if "Details"
				showDetails ^= true;
			} else {
				break;
			}
		}
	}

	private final BiPredicate<String, Exception> retryableExceptionShower = new BiPredicate<String, Exception>() {
		private boolean showing = false;

		@Override
		public boolean test(String message, Exception ex) {
			if (showing) {
				return false;
			}

			showing = true;
			//noinspection OverlyBroadCatchBlock
			try {
				//noinspection ConstantConditions
				return GraphicsUtilities.getFromEDT(() -> showRetryableExceptionDialog(message, ex));
			} catch (Throwable th) {
				ExceptionManager.die(th);
				return false;
			} finally {
				showing = false;
			}
		}
	};

	/**
	 * @return {@code shouldRetry}
	 */
	public boolean showRetryableExceptionDialog(String message, Exception ex) {
		boolean showDetails = false;
		while (true) {
			exceptionPanel.setTitle(message);
			exceptionPanel.setSubtitle(message.equals(ex.getMessage()) ?
			                           ex.getClass().getSimpleName() :
			                           ex.getMessage());
			if (showDetails) {
				exceptionPanel.setDetailMessage(ExceptionUtilities.exceptionToString(ex));
			} else {
				exceptionPanel.setDetailMessage(null);
			}

			int result = showExceptionDialog(exceptionPanel,
			                                 new String[]{(showDetails ? "Hide details" : "Details"),
			                                              "Try again", "Close"}, "Try again");

			if (result == 0) { // Toggle details if "Details"
				showDetails ^= true;
			} else {
				return result == 1; // shouldRetry if "Try again"
			}
		}
	}

	private final BiConsumer<String, Throwable> showUnrecoverableErrorDialog = new BiConsumer<String, Throwable>() {
		private boolean showing = false;

		@Override
		public void accept(String message, Throwable th) {
			if (showing) {
				return;
			}

			showing = true;
			//noinspection OverlyBroadCatchBlock
			try {
				GraphicsUtilities.getFromEDT(() -> {
					showUnrecoverableErrorDialog(message, th);
					return (Void)null;
				});
			} catch (Throwable th2) {
				ExceptionManager.die(th2);
			} finally {
				showing = false;
			}
		}
	};

	public void showUnrecoverableErrorDialog(String message, Throwable th) {
		boolean showDetails = false;
		while (true) {
			exceptionPanel.setTitle(message);
			exceptionPanel.setSubtitle(message.equals(th.getMessage()) ?
			                           th.getClass().getSimpleName() :
			                           th.getMessage());
			if (showDetails) {
				exceptionPanel.setDetailMessage(ExceptionUtilities.exceptionToString(th));
			} else {
				exceptionPanel.setDetailMessage(null);
			}

			int result = showExceptionDialog(exceptionPanel,
			                                 new String[]{"Details", "Close application"},
			                                 "Close application");

			if (result == 0) { // Toggle details if "Details"
				showDetails ^= true;
			} else {
				break;
			}
		}
	}

	private int showExceptionDialog(ExceptionPanel exceptionPanel, String[] options, String initialValue) {
		boolean isYesLast = UIManager.getDefaults().getBoolean("OptionPane.isYesLast");

		if (isYesLast) {
			Collections.reverse(Arrays.asList(options));
		}

		int result = JOptionPane.showOptionDialog(parent,
		                                          exceptionPanel,
		                                          "Error",
		                                          JOptionPane.DEFAULT_OPTION,
		                                          JOptionPane.ERROR_MESSAGE,
		                                          null,
		                                          options,
		                                          initialValue);

		if (isYesLast) {
			result = options.length - result - 1;
		}

		return result;
	}
}
