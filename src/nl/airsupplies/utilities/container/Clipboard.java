package nl.airsupplies.utilities.container;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * This class is used to transfer an {@link Image} into the clipboard.
 *
 * @author Mark Jeronimus
 */
public class Clipboard implements Transferable {
	/**
	 * The image to transfer into the clipboard.
	 */
	private Image  image;
	private String text;

	// boolean isText;

	/**
	 * Transfers {@code image} into the clipboard.
	 *
	 * @param image Image to transfer into the clipboard.
	 */
	public static void setClipboard(Image image) {
		Clipboard sel = new Clipboard(image);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sel, null);
	}

	/**
	 * Transfers {@code text} into the clipboard.
	 *
	 * @param text to transfer into the clipboard.
	 */
	public static void setClipboard(String text) {
		Clipboard sel = new Clipboard(text);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sel, null);
	}

	/**
	 * Constructs a {@code ImageSelection}.
	 *
	 * @param image The real Image.
	 */
	public Clipboard(Image image) {
		this.image = image;
	}

	/**
	 * Constructs a {@code ImageSelection}.
	 *
	 * @param text The text to transfer
	 */
	public Clipboard(String text) {
		this.text = text;
	}

	/*
	 * java.awt.datatransfer.Transferable#getTransferDataFlavors()
	 */
	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return text == null ? new DataFlavor[]{DataFlavor.imageFlavor} : new DataFlavor[]{DataFlavor.stringFlavor};
	}

	/*
	 * java.awt.datatransfer.Transferable#isDataFlavorSupported (java.awt.datatransfer.DataFlavor)
	 */
	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return DataFlavor.imageFlavor.equals(flavor);
	}

	/*
	 * java.awt.datatransfer.Transferable#getTransferData(java .awt.datatransfer.DataFlavor)
	 */
	@Override
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		if (DataFlavor.imageFlavor.equals(flavor)) {
			return image;
		} else if (DataFlavor.stringFlavor.equals(flavor)) {
			return text;
		}
		throw new UnsupportedFlavorException(flavor);
	}

	/**
	 * Get the String residing on the clipboard. Or, if it is a file list, get the load command associated with that.
	 * from http://www.javapractices.com/Topic82.cjp
	 *
	 * @return any text found on the Clipboard; if none found, return an empty String.
	 */
	public static String getClipboardText() {
		String                          result    = null;
		java.awt.datatransfer.Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable                    contents  = clipboard.getContents(null);
		if (contents == null) {
			return null;
		}
		try {
			if (contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				result = (String)contents.getTransferData(DataFlavor.stringFlavor);
			} else if (contents.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
				List<File> fileList = (List<File>)contents.getTransferData(DataFlavor.javaFileListFlavor);
				int        length   = fileList.size();
				if (length == 0) {
					return null;
				}
				result = "LOAD files ";
				for (int i = 0; i < length; i++) {
					result += " \"" + fileList.get(i).getAbsolutePath().replace('\\', '/') + '"';
				}
			}
		} catch (UnsupportedFlavorException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return result;
	}
}
