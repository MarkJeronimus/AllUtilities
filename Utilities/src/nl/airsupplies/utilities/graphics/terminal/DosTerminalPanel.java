package nl.airsupplies.utilities.graphics.terminal;

import nl.airsupplies.utilities.graphics.terminal.framebuffer.DosFrameBuffer;
import nl.airsupplies.utilities.graphics.terminal.terminalfont.StandardBitmapFontCP437;

/**
 * @author Mark Jeronimus
 */
// Created 2012-07-29
public class DosTerminalPanel extends TerminalPanel {
	/**
	 * Construct a DOS terminal with an specified text resolution.
	 */
	public DosTerminalPanel(int cols, int rows, int charHeight) {
		super(cols, rows, StandardBitmapFontCP437.getFont(charHeight), new DosFrameBuffer(cols, rows));
	}
}
