package nl.airsupplies.utilities.graphics.terminal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import nl.airsupplies.utilities.encoding.CharacterEncoding;
import nl.airsupplies.utilities.graphics.terminal.framebuffer.AbstractFrameBuffer;
import nl.airsupplies.utilities.graphics.terminal.terminalfont.AbstractGlyph;
import nl.airsupplies.utilities.graphics.terminal.terminalfont.AbstractTerminalFont;
import nl.airsupplies.utilities.gui.window.PixelWindow;

/**
 * @author Mark Jeronimus
 */
// Created 2012-06-04
public class TerminalPanel extends PixelWindow implements ActionListener {
	public final AbstractFrameBuffer  frameBuffer;
	public final AbstractTerminalFont font;

	private CharacterEncoding encoding = null;

	private int     frame         = 0;
	private boolean renderFullNow = true;
	private Timer   timer         = null;

	/**
	 * Construct a terminal with an specified text resolution, specified font, default palette.
	 */
	public TerminalPanel(int cols, int rows, AbstractTerminalFont font, AbstractFrameBuffer frameBuffer) {
		super(cols * font.getGlyphWidth(), rows * font.getGlyphHeight());

		this.font = font;

		this.frameBuffer = frameBuffer;
	}

	@Override
	public void initialized() {
		// The blink timer thread.
		timer = new Timer(18, this);
		timer.start();
	}

	@Override
	public void resized() {
	}

	public void setEncoding(CharacterEncoding encoding) {
		this.encoding = encoding;
	}

	private void renderImage() {
		int cols = frameBuffer.getNumCols();
		int rows = frameBuffer.getNumRows();

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				AbstractGlyph glyph   = font.getGlyph(frameBuffer.getChar(col, row));
				int           fgColor = 0xFF000000 | frameBuffer.getRGBColor(frameBuffer.getForegroundColor(col, row));
				int           bgColor = 0xFF000000 | frameBuffer.getRGBColor(frameBuffer.getBackgroundColor(col, row));
				int           flags   = frameBuffer.getFlags(col, row);

				renderGlyph(col, row, glyph, bgColor, fgColor, flags);
			}
		}
	}

	private void drawCursor(boolean blink) {
		int glyphWidth  = font.getGlyphWidth();
		int glyphHeight = font.getGlyphHeight();

		int col = frameBuffer.getCursorX();
		int row = frameBuffer.getCursorY();

		AbstractGlyph glyph   = font.getGlyph(frameBuffer.getChar(col, row));
		int           fgColor = 0xFF000000 | frameBuffer.getRGBColor(frameBuffer.getForegroundColor(col, row));

		if (blink) {
			int x = col * glyphWidth;
			int y = row * glyphHeight;
			int p = x + y * frameBuffer.getNumCols() * glyphWidth;

			for (y = 0; y < glyphHeight; y++) {
				if (y >= 13 && y < 15) {
					for (x = 0; x < glyphWidth; x++) {
						pixels[p] = fgColor;
						p++;
					}
				} else {
					p += glyphWidth;
				}

				p += (frameBuffer.getNumCols() - 1) * glyphWidth;
			}
		} else {
			int bgColor = 0xFF000000 | frameBuffer.getRGBColor(frameBuffer.getBackgroundColor(col, row));
			int flags   = frameBuffer.getFlags(col, row);
			renderGlyph(col, row, glyph, bgColor, fgColor, flags);
		}
	}

	private void renderGlyph(int col, int row, AbstractGlyph glyph, int bgColor, int fgColor,
	                         @SuppressWarnings("unused") int flags) {
		int width    = font.getGlyphWidth();
		int height   = font.getGlyphHeight();
		int scanline = frameBuffer.getNumCols() * width;
		int p        = col * width + row * scanline * height;

		glyph.drawGlyph(pixels, p, scanline, fgColor, bgColor);
	}

	public void putString(int x, int y, String s) {
		frameBuffer.setCursor(x, y);
		print(s);
		renderFullNow = true;
	}

	public void print(String s) {
		int len = s.codePointCount(0, s.length());
		for (int i = 0; i < len; i++) {
			int ch = s.codePointAt(i);
			if (encoding != null) {
				ch = encoding.encode(ch);
			}
			frameBuffer.typeChar(ch);
		}

		renderFullNow = true;
	}

	public void print(int ch) {
		if (encoding != null) {
			ch = encoding.encode(ch);
		}
		frameBuffer.typeChar(ch);

		renderFullNow = true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		frame = frame + 1 & 31;

		if (renderFullNow) {
			renderImage();
			repaintNow();
			renderFullNow = false;
		} else if ((frame & 7) == 0) {
			drawCursor((frame & 8) != 0);
			repaintNow();
		}
	}
}
