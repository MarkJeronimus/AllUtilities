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

package org.digitalmodular.utilities.graphics.terminal.terminalfont;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author Mark Jeronimus
 */
// Created 2012-06-04
public class CPIFont {
	private final File file;

	private static class Variant {
		public final int codepage;
		public final int height;

		protected final int                  offset;
		protected       AbstractTerminalFont font = null;

		public Variant(int codepage, int height, int offset) {
			this.codepage = codepage;
			this.height = height;
			this.offset = offset;
		}
	}

	private final Variant[][] variants;

	public CPIFont(File file) throws IOException {
		this.file = file;
		try (RandomAccessFile in = new RandomAccessFile(file, "r")) {
			in.seek(0x13);
			int start_pos = in.read() | (in.read() | (in.read() | in.read() << 8) << 8) << 8;

			in.seek(start_pos);
			int numCodepages = in.read() | in.read() << 8;

			variants = new Variant[numCodepages][];

			start_pos += 4;

			for (int i = 0; i < numCodepages; i++) {
				in.seek(start_pos + 0x04);
				int deviceType = in.read() | in.read() << 8;
				in.seek(start_pos + 0x0E);
				int codepage = in.read() | in.read() << 8;
				in.seek(start_pos + 0x16);
				int pointer = in.read() | (in.read() | (in.read() | in.read() << 8) << 8) << 8;

				in.seek(pointer);
				int fontType = in.read() | in.read() << 8;

				if (deviceType == 1 && fontType == 1) {
					in.seek(pointer + 0x02);
					int numHeights = in.read() | in.read() << 8;
					in.seek(pointer + 0x04);

					int offset = pointer + 6;

					variants[i] = new Variant[numHeights];

					for (int j = 0; j < numHeights; j++) {
						in.seek(offset);
						int height = in.read();

						offset += 6;

						variants[i][j] = new Variant(codepage, height, offset);

						offset += height << 8;
					}
				}

				in.seek(start_pos);
				start_pos = in.read() | (in.read() | (in.read() | in.read() << 8) << 8) << 8;
				start_pos += 2;
			}
		}
	}

	public int[] listCodepages() {
		int[] out = new int[variants.length];
		for (int i = 0; i < out.length; i++) {
			out[i] = variants[i][0].codepage;
		}
		return out;
	}

	public int[] listSizes(int codepage) {
		Variant[] foundVariants = null;
		for (Variant[] variant : variants) {
			if (variant[0].codepage == codepage) {
				foundVariants = variant;
				break;
			}
		}

		if (foundVariants == null) {
			return null;
		}

		int[] out = new int[foundVariants.length];
		for (int i = 0; i < out.length; i++) {
			out[i] = foundVariants[i].height;
		}
		return out;
	}

	public AbstractTerminalFont makeFont(int codepage, int height) {
		Variant foundVariant = null;
		for (Variant[] variant : variants) {
			if (variant[0].codepage == codepage) {
				for (Variant element : variant) {
					if (element.height == height) {
						foundVariant = element;
						break;
					}
				}
				if (foundVariant != null) {
					break;
				}
			}
		}

		if (foundVariant == null) {
			return null;
		}

		if (foundVariant.font == null) {
			loadFont(foundVariant);
		}

		return foundVariant.font;
	}

	private void loadFont(Variant variant) {
		variant.font = new AbstractTerminalFont() { };

		variant.font.gridSpacingX = 8;
		variant.font.gridSpacingY = variant.height;

		try (RandomAccessFile in = new RandomAccessFile(file, "r")) {
			in.seek(variant.offset);
			for (int k = 0; k < 256; k++) {
				BinaryGlyph glyph = new BinaryGlyph(8, variant.height);
				for (int v = 0; v < variant.height; v++) {
					int c = in.read();
					for (int u = 0; u < 8; u++) {
						glyph.grid[v][u] = (c & 0x80) != 0;
						c <<= 1;
					}
				}
				variant.font.setGlyph(k, glyph);
			}

			variant.font.setSpace(variant.font.getGlyph(32));
		} catch (IOException ex) {
			ex.printStackTrace();
			System.exit(0);
		}
	}
}
