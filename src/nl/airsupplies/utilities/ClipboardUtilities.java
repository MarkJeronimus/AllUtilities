package nl.airsupplies.utilities;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import org.jetbrains.annotations.Nullable;

import nl.airsupplies.utilities.annotation.UtilityClass;

/**
 * @author Mark Jeronimus
 */
// Created 2018-01-10
@UtilityClass
public final class ClipboardUtilities {
	private static final @Nullable Clipboard CLIPBOARD;

	static {
		@Nullable Clipboard clipboard;
		try {
			clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		} catch (HeadlessException ignored) {
			clipboard = null;
		}
		CLIPBOARD = clipboard;
	}

	public static void clear(Transferable t) {
		if (CLIPBOARD != null) {
			CLIPBOARD.setContents(t, null);
		}
	}

	public static @Nullable String getAsString() {
		if (CLIPBOARD == null) {
			return null;
		}

		try {
			return (String)CLIPBOARD.getData(DataFlavor.stringFlavor);
		} catch (UnsupportedFlavorException | IOException | IllegalStateException ignored) {
		}

		return null;
	}

	public static void setString(String text) {
		if (CLIPBOARD == null) {
			return;
		}

		CLIPBOARD.setContents(new StringSelection(text), null);
	}
}
