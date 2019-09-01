/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2019 Mark Jeronimus. All Rights Reversed.
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
 * along with AllUtilities. If not, see <http://www.gnu.org/licenses/>.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.digitalmodular.utilities.gui.terminal.framebuffer;

import java.awt.Toolkit;

/**
 * @author Mark Jeronimus
 */
// Created 2012-07-17
public class DosFrameBuffer extends AbstractFrameBuffer {
	protected static final int[] PALETTE = {0xFF000000, 0xFF0000AA, 0xFF00AA00, 0xFF00AAAA, 0xFFAA0000, 0xFFAA00AA,
	                                        0xFFAA5500, 0xFFAAAAAA, 0xFF555555, 0xFF5555FF, 0xFF55FF55, 0xFF55FFFF,
	                                        0xFFFF5555, 0xFFFF55FF, 0xFFFFFF55,
	                                        0xFFFFFFFF};

	// 7 beep
	// 9 tab
	// 10, 13 crlf
	// 11 home
	// 12 clear
	// 28 right
	// 29 left
	// 30 up
	// 31 down
	private static final boolean[] SPECIAL_MOVES = {false, false, false, false, false, false, false, true, false, true,
	                                                true, true, true, true, false, false, false, false, false, false,
	                                                false, false, false, false, false, false,
	                                                false,
	                                                false, true, true, true, true};

	public int[] buffer;

	// Current state:
	private int cursorX             = 0;
	private int cursorY             = 0;
	private int lastForegroundColor = 7;
	private int lastBackgroundColor = 0;

	public DosFrameBuffer(int numCols, int numRows) {
		super(numCols, numRows, 16);

		buffer = new int[numCols * numRows * 2];

		clear(32, 7, 0, 0);
	}

	// Status functions

	@Override
	public boolean setCursor(int cursorX, int cursorY) {
		if (cursorX < 0 || cursorX >= numCols || cursorY < 0 || cursorY >= numRows) {
			return false;
		}

		this.cursorX = cursorX;
		this.cursorY = cursorY;
		return true;
	}

	@Override
	public int getCursorX() {
		return cursorX;
	}

	@Override
	public int getCursorY() {
		return cursorY;
	}

	@Override
	public void setForegroundColor(int foregroundColor) {
		lastForegroundColor = foregroundColor;
	}

	@Override
	public int getForegroundColor() {
		return lastForegroundColor;
	}

	@Override
	public void setBackgroundColor(int backgroundColor) {
		lastBackgroundColor = backgroundColor;
	}

	@Override
	public int getBackgroundColor() {
		return lastBackgroundColor;
	}

	@Override
	public void setFlags(int flags) {}

	@Override
	public int getFlags() {
		return 0;
	}

	// Memory functions

	@Override
	public void setChar(int x, int y, int ch) {
		buffer[(y * numCols + x) * 2] = ch;
	}

	@Override
	public int getChar(int x, int y) {
		return buffer[(y * numCols + x) * 2];
	}

	@Override
	public void setForegroundColor(int x, int y, int color) {
		buffer[(y * numCols + x) * 2 + 1] = buffer[(y * numCols + x) * 2 + 1] & 0xF0 | color & 0xF;
	}

	@Override
	public int getForegroundColor(int x, int y) {
		return buffer[(y * numCols + x) * 2 + 1] & 0x0F;
	}

	@Override
	public void setBackgroundColor(int x, int y, int color) {
		buffer[(y * numCols + x) * 2 + 1] = buffer[(y * numCols + x) * 2 + 1] & 0x0F | (color & 0xF) << 4;
	}

	@Override
	public int getBackgroundColor(int x, int y) {
		return buffer[(y * numCols + x) * 2 + 1] >> 4 & 0x7;
	}

	@Override
	public int getFlags(int x, int y) {
		return 0;
	}

	@Override
	public void setFlags(int x, int y, int flags) {}

	public void setAttribute(int x, int y, int attribute) {
		buffer[(y * numCols + x) * 2 + 1] = attribute;
	}

	public int getAttribute(int x, int y) {
		return buffer[(y * numCols + x) * 2 + 1];
	}

	// Complex functions

	@Override
	public void typeChar(int ch) {
		if (ch >= 0 && ch < 32 && SPECIAL_MOVES[ch]) {
			switch (ch) {
				case 7:
					Toolkit.getDefaultToolkit().beep();
					return;
				case 9:
					cursorX = (cursorX + 7) / 8 * 8;
					if (cursorX >= getNumCols()) {
						crlf();
					}
					return;
				case 10:
				case 13:
					crlf();
					return;
				case 11:
					cursorX = 0;
					cursorY = 0;
					return;
				case 12:
					clear();
					return;
				case 28:
					if (cursorX < getNumCols() - 1) {
						cursorX++;
					} else if (cursorY < getNumRows() - 2) {
						cursorX++;
						if (cursorX >= getNumCols()) {
							cursorX = 0;
							cursorY++;
						}
					}
					return;
				case 29:
					if (cursorX > 0) {
						cursorX--;
					} else if (cursorY > 0) {
						cursorX--;
						if (cursorX < 0) {
							cursorX = getNumCols() - 1;
							cursorY--;
						}
					}
					return;
				case 30:
					if (cursorY > 0) {
						cursorY--;
					}
					return;
				case 31:
					if (cursorY < getNumRows() - 1) {
						cursorY++;
					}
					return;
			}
		}

		setChar(cursorX, cursorY, ch);
		int attribute = lastForegroundColor & 0xF | (lastBackgroundColor & 0xF) << 4;
		setAttribute(cursorX, cursorY, attribute);

		if (++cursorX >= getNumCols()) {
			crlf();
		}
	}

	@Override
	public void crlf() {
		cursorX = 0;
		cursorY++;

		if (cursorY >= getNumRows() - 1) {
			cursorY--;
			scrollUp(32, lastForegroundColor, lastBackgroundColor, 0);
		}
	}

	@Override
	public void clear() {
		clear(32, lastForegroundColor, lastBackgroundColor, 0);
		cursorX = 0;
		cursorY = 0;
	}

	@Override
	public void clear(int fillCh, int fillForegroundColor, int fillBackgroundColor, int fillFlag) {
		int attribute = fillForegroundColor & 0xF | (fillBackgroundColor & 0xF) << 4;
		for (int i = 0; i < buffer.length; ) {
			buffer[i++] = fillCh;
			buffer[i++] = attribute;
		}
	}

	@Override
	public void scrollUp(int fillCh, int fillForegroundColor, int fillBackgroundColor, int fillFlag) {
		int attribute = fillForegroundColor & 0xF | (fillBackgroundColor & 0xF) << 4;
		int scanline  = numRows * 2;
		for (int y = 1; y < numRows - 1; y++) {
			System.arraycopy(buffer, y * scanline, buffer, (y - 1) * scanline, scanline);
		}
		for (int i = (numRows - 2) * scanline; i < (numRows - 1) * scanline; ) {
			buffer[i++] = fillCh;
			buffer[i++] = attribute;
		}
	}

	@Override
	public void scrollDown(int fillCh, int fillForegroundColor, int fillBackgroundColor, int fillFlag) {
		throw new UnsupportedOperationException(
				"Not supported yet."); // To change body of generated methods, choose Tools |
		// Templates.
	}

	@Override
	public void scrollLeft(int fillCh, int fillForegroundColor, int fillBackgroundColor, int fillFlag) {
		throw new UnsupportedOperationException(
				"Not supported yet."); // To change body of generated methods, choose Tools |
		// Templates.
	}

	@Override
	public void scrollRight(int fillCh, int fillForegroundColor, int fillBackgroundColor, int fillFlag) {
		throw new UnsupportedOperationException(
				"Not supported yet."); // To change body of generated methods, choose Tools |
		// Templates.
	}
}
