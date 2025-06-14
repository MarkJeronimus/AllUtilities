package nl.airsupplies.utilities.graphics.terminal.framebuffer;

import nl.airsupplies.utilities.graphics.color.Color3fConst;

/**
 * @author Mark Jeronimus
 */
// Created 2012-07-17
public abstract class AbstractFrameBuffer {
	public static final int FLAG_BLINK_FOREGROUND = 0x00000001;
	public static final int FLAG_BLINK_BACKGROUND = 0x00000002;
	public static final int FLAG_BLINK_INVERT     = 0x00000003;

	public static final int FLAG_BOLD                 = 0x00000008;
	public static final int FLAG_LIGHT                = 0x00000010;
	public static final int FLAG_ITALIC               = 0x00000020;
	public static final int FLAG_STRIKETHROUGH        = 0x00000100;
	public static final int FLAG_STRIKETHROUGH_DOUBLE = 0x00000200;
	public static final int FLAG_UNDERSCORE_DOUBLE    = 0x00001000;
	public static final int FLAG_UNDERSCORE           = 0x00002000;
	public static final int FLAG_UNDERWIGGLE          = 0x00004000;
	public static final int FLAG_UNDERWIGGLE_DOUBLE   = 0x00008000;
	public static final int FLAG_OVERSTRIKE           = 0x00010000;
	public static final int FLAG_WIDE                 = 0x00020000;

	protected int numCols;
	protected int numRows;

	private final int[] palette;

	protected AbstractFrameBuffer(int cols, int rows, int numColors) {
		numCols = cols;
		numRows = rows;

		if (numColors == 0) {
			palette = null;
			return;
		}

		palette = new int[numColors];
		System.arraycopy(Color3fConst.DOS_PALETTE, 0, palette, 0, Math.min(256, numColors));
	}

	public int getNumCols() {
		return numCols;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getRGBColor(int color) {
		return palette == null ? color : palette[color];
	}

	// State functions

	public abstract boolean setCursor(int cursorX, int cursorY);

	public abstract int getCursorX();

	public abstract int getCursorY();

	public abstract void setForegroundColor(int foregroundColor);

	public abstract int getForegroundColor();

	public abstract void setBackgroundColor(int backgroundColor);

	public abstract int getBackgroundColor();

	public abstract void setFlags(int flags);

	public abstract int getFlags();

	// Memory functions

	public abstract void setChar(int x, int y, int ch);

	public abstract int getChar(int x, int y);

	public abstract void setForegroundColor(int x, int y, int color);

	public abstract int getForegroundColor(int x, int y);

	public abstract void setBackgroundColor(int x, int y, int color);

	public abstract int getBackgroundColor(int x, int y);

	public abstract void setFlags(int x, int y, int flags);

	public abstract int getFlags(int x, int y);

	// Complex functions

	public abstract void typeChar(int ch);

	public abstract void crlf();

	public abstract void clear();

	public abstract void clear(int fillCh, int fillForegroundColor, int fillBackgroundColor, int fillFlag);

	public abstract void scrollUp(int fillCh, int fillForegroundColor, int fillBackgroundColor, int fillFlag);

	public abstract void scrollDown(int fillCh, int fillForegroundColor, int fillBackgroundColor, int fillFlag);

	public abstract void scrollLeft(int fillCh, int fillForegroundColor, int fillBackgroundColor, int fillFlag);

	public abstract void scrollRight(int fillCh, int fillForegroundColor, int fillBackgroundColor, int fillFlag);
}
