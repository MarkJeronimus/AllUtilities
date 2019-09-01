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
package org.digitalmodular.utilities.gui;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import org.digitalmodular.utilities.annotation.ConstClass;
import org.digitalmodular.utilities.annotation.ThreadBounded;
import org.digitalmodular.utilities.gui.swing.TextShape;

/**
 * Re-usable {@link Shape} instances for easy painting using {@link Graphics2D}.
 *
 * @author Mark Jeronimus
 */
// Created 2017-01-31
@ConstClass
@ThreadBounded("AWT-EventQueue-0")
public final class StaticShapes {
	private StaticShapes() { throw new AssertionError(); }

	public static final Line2D      LINE      = new Line2D.Double();
	public static final Rectangle2D RECTANGLE = new Rectangle2D.Double();
	public static final Ellipse2D   ELLIPSE   = new Ellipse2D.Double();
	public static final Path2D      PATH      = new Path2D.Double();
	public static final TextShape   TEXT      = new TextShape();
}
