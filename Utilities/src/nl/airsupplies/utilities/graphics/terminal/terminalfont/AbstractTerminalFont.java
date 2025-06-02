package nl.airsupplies.utilities.graphics.terminal.terminalfont;

import nl.airsupplies.utilities.collection.IntTreeMap;

/**
 * @author Mark Jeronimus
 */
// Created 2012-08-08
public abstract class AbstractTerminalFont {
	protected int gridSpacingX;
	protected int gridSpacingY;

	private final IntTreeMap<AbstractGlyph> glyphs = new IntTreeMap<>(0, (1 << 21) - 1, 5, 5, 4, 7);
	private       AbstractGlyph             space;

	public int getGlyphWidth() {
		return gridSpacingX;
	}

	public int getGlyphHeight() {
		return gridSpacingY;
	}

	public void setGlyph(int codepoint, AbstractGlyph glyph) {
		glyphs.add(codepoint, glyph);
	}

	public void setSpace(AbstractGlyph glyph) {
		space = glyph;
	}

	public AbstractGlyph getGlyph(int codepoint) {
		AbstractGlyph glyph = glyphs.get(codepoint);
		if (glyph == null) {
			return space;
		}
		return glyph;
	}
}
