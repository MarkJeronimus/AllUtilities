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
package org.digitalmodular.utilities.gui.terminal.terminalfont;

import org.digitalmodular.utilities.collection.IntTreeMap;

/**
 * @author Mark Jeronimus
 */
// Created 2012-08-08
public abstract class AbstractTerminalFont {
	protected int gridSpacingX;
	protected int gridSpacingY;

	private IntTreeMap<AbstractGlyph> glyphs = new IntTreeMap<>(0, (1 << 21) - 1, 5, 5, 4, 7);
	private AbstractGlyph             space;

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
