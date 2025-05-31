package nl.airsupplies.utilities;

import java.awt.HeadlessException;
import java.awt.Image;
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

	public static void setImage(Image image) {
		if (CLIPBOARD == null) {
			return;
		}

		CLIPBOARD.setContents(new ImageSelection(image), null);
	}

	/**
	 * From https://stackoverflow.com/a/67346282/1052284
	 */
	private static class TransferableImage implements Transferable {
		private final Image image;

		TransferableImage(Image image) {
			this.image = image;
		}

		@Override
		public Object getTransferData(DataFlavor flavor)
				throws UnsupportedFlavorException {
			if (flavor.equals(DataFlavor.imageFlavor) && image != null) {
				return image;
			} else {
				throw new UnsupportedFlavorException(flavor);
			}
		}

		@Override
		public DataFlavor[] getTransferDataFlavors() {
			DataFlavor[] flavors = new DataFlavor[1];
			flavors[0] = DataFlavor.imageFlavor;
			return flavors;
		}

		@Override
		public boolean isDataFlavorSupported(DataFlavor flavor) {
			DataFlavor[] flavors = getTransferDataFlavors();
			for (DataFlavor dataFlavor : flavors) {
				if (flavor.equals(dataFlavor)) {
					return true;
				}
			}

			return false;
		}
	}

	public static class ImageSelection implements Transferable {
		private Image image;

		public ImageSelection(Image image) {
			this.image = image;
		}

		public DataFlavor[] getTransferDataFlavors() {
			return new DataFlavor[]{DataFlavor.imageFlavor};
		}

		public boolean isDataFlavorSupported(DataFlavor flavor) {
			return DataFlavor.imageFlavor.equals(flavor);
		}

		public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
			if (!DataFlavor.imageFlavor.equals(flavor)) {
				throw new UnsupportedFlavorException(flavor);
			}

			return image;
		}
	}
}
