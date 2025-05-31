package nl.airsupplies.utilities.graphics.terminal;

import nl.airsupplies.utilities.graphics.terminal.framebuffer.AdvancedFrameBuffer;
import nl.airsupplies.utilities.graphics.terminal.terminalfont.AbstractTerminalFont;

/**
 * @author Mark Jeronimus
 */
// Created 2012-07-29
public class AdvancedTerminalPanel extends TerminalPanel {
	public AdvancedTerminalPanel(int cols, int rows, AbstractTerminalFont font) {
		super(cols, rows, font, new AdvancedFrameBuffer(cols, rows));
	}
}
