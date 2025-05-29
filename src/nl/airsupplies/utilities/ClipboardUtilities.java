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
