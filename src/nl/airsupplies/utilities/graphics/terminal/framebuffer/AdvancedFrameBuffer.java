package nl.airsupplies.utilities.graphics.terminal.framebuffer;

import nl.airsupplies.utilities.container.Vector4i;

/**
 * @author Mark Jeronimus
 */
// Created 2012-07-28
public class AdvancedFrameBuffer extends AbstractFrameBuffer {
	private final Vector4i[][] buffer;

	private int cursorX             = 0;
	private int cursorY             = 0;
	private int lastForegroundColor = 0xFFFFFF;
	private int lastBackgroundColor = 0;
	private int lastFlags           = 0;

	public AdvancedFrameBuffer(int numCols, int numRows) {
		super(numCols, numRows, 0);

		buffer = new Vector4i[numRows][numCols];
		for (int y = 0; y < numRows; y++) {
			for (int x = 0; x < numCols; x++) {
				buffer[y][x] = new Vector4i(32, lastForegroundColor, lastBackgroundColor, lastFlags);
			}
		}

		clear(32, 7, 0, 0);
	}

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
	public void setFlags(int flags) {
		lastFlags = flags;
	}

	@Override
	public int getFlags() {
		return lastFlags;
	}

	// Memory functions

	@Override
	public void setChar(int x, int y, int ch) {
		buffer[y][x].x = ch;
	}

	@Override
	public int getChar(int x, int y) {
		return buffer[y][x].x;
	}

	@Override
	public void setForegroundColor(int x, int y, int color) {
		buffer[y][x].y = color;
	}

	@Override
	public int getForegroundColor(int x, int y) {
		return buffer[y][x].y;
	}

	@Override
	public void setBackgroundColor(int x, int y, int color) {
		buffer[y][x].z = color;
	}

	@Override
	public int getBackgroundColor(int x, int y) {
		return buffer[y][x].z;
	}

	@Override
	public void setFlags(int x, int y, int flags) {
		buffer[y][x].w = flags;
	}

	@Override
	public int getFlags(int x, int y) {
		return buffer[y][x].w;
	}

	// Complex functions

	@Override
	public void typeChar(int ch) {
		buffer[cursorY][cursorX].set(ch, lastForegroundColor, lastBackgroundColor, lastFlags);
		++cursorX;
		if (cursorX >= getNumCols()) {
			crlf();
		}
	}

	@Override
	public void crlf() {
		cursorX = 0;
		cursorY++;

		if (cursorY >= getNumRows() - 1) {
			cursorY--;
			scrollUp(32, lastForegroundColor, lastBackgroundColor, lastFlags);
		}
	}

	@Override
	public void clear() {
		clear(32, lastForegroundColor, lastBackgroundColor, lastFlags);
		cursorX = 0;
		cursorY = 0;
	}

	@Override
	public void clear(int fillCh, int fillForegroundColor, int fillBackgroundColor, int fillFlag) {
		for (int y = 0; y < numRows; y++) {
			for (int x = 0; x < numCols; x++) {
				buffer[y][x].set(fillCh, fillForegroundColor, fillBackgroundColor, fillFlag);
			}
		}
	}

	@Override
	public void scrollUp(int fillCh, int fillForegroundColor, int fillBackgroundColor, int fillFlag) {
		Vector4i[] movingRow = buffer[0];
		for (int y = 1; y < numRows; y++) {
			buffer[y - 1] = buffer[y];
		}
		buffer[numRows - 1] = movingRow;

		for (int x = 0; x < numCols; x++) {
			movingRow[x].set(32, lastForegroundColor, lastBackgroundColor, lastFlags);
		}
	}

	@Override
	public void scrollDown(int fillCh, int fillForegroundColor, int fillBackgroundColor, int fillFlag) {
		Vector4i[] movingRow = buffer[numRows - 1];
		for (int y = numRows - 2; y >= 0; y++) {
			buffer[y + 1] = buffer[y];
		}
		buffer[0] = movingRow;

		for (int x = 0; x < numCols; x++) {
			movingRow[x].set(32, lastForegroundColor, lastBackgroundColor, lastFlags);
		}
	}

	@Override
	public void scrollLeft(int fillCh, int fillForegroundColor, int fillBackgroundColor, int fillFlag) {
		for (int y = 0; y < numRows; y++) {
			Vector4i[] row = buffer[y];

			Vector4i movingCol = row[0];
			for (int x = 0; x < numCols; x++) {
				row[x - 1] = row[x];
			}
			row[numCols - 1] = movingCol;

			movingCol.set(32, lastForegroundColor, lastBackgroundColor, lastFlags);
		}
	}

	@Override
	public void scrollRight(int fillCh, int fillForegroundColor, int fillBackgroundColor, int fillFlag) {
		for (int y = 0; y < numRows; y++) {
			Vector4i[] row = buffer[y];

			Vector4i movingCol = row[numCols - 1];
			for (int x = numCols - 2; y >= 0; y++) {
				row[x + 1] = row[x];
			}
			row[0] = movingCol;

			movingCol.set(32, lastForegroundColor, lastBackgroundColor, lastFlags);
		}
	}
}
