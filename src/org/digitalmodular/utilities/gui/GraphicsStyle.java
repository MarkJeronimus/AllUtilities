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

import java.awt.Paint;
import java.awt.Stroke;

import static org.digitalmodular.utilities.gui.StaticStrokes.DEFAULT_ROUND_STROKE;

/**
 * @author Mark Jeronimus
 */
// Created 2016-03-24
public class GraphicsStyle {
	public final Paint   fillPaint;
	public final Paint   strokePaint;
	public final Stroke  stroke;
	public final boolean strokeFirst;

	public GraphicsStyle(Paint fillPaint) {
		this.fillPaint = fillPaint;
		this.strokePaint = null;
		this.stroke = null;
		this.strokeFirst = false;
	}

	public GraphicsStyle(Paint fillPaint, Paint strokePaint) {
		this.fillPaint = fillPaint;
		this.strokePaint = strokePaint;
		this.stroke = DEFAULT_ROUND_STROKE;
		this.strokeFirst = false;
	}

	public GraphicsStyle(Paint strokePaint, Stroke stroke) {
		this.fillPaint = null;
		this.strokePaint = strokePaint;
		this.stroke = stroke;
		this.strokeFirst = false;
	}

	public GraphicsStyle(Paint fillPaint, Paint strokePaint, Stroke stroke) {
		this.fillPaint = fillPaint;
		this.strokePaint = strokePaint;
		this.stroke = stroke;
		this.strokeFirst = false;
	}

	public GraphicsStyle(Paint fillPaint, Paint strokePaint, Stroke stroke, boolean strokeFirst) {
		this.fillPaint = fillPaint;
		this.strokePaint = strokePaint;
		this.stroke = stroke;
		this.strokeFirst = strokeFirst;
	}

	public GraphicsStyle setFillPaint(Paint fillPaint) {
		return new GraphicsStyle(fillPaint, strokePaint, stroke, strokeFirst);
	}

	public GraphicsStyle setStrokePaint(Paint strokePaint) {
		return new GraphicsStyle(fillPaint, strokePaint, stroke, strokeFirst);
	}

	public GraphicsStyle setStroke(Stroke stroke) {
		return new GraphicsStyle(fillPaint, strokePaint, stroke, strokeFirst);
	}

	public GraphicsStyle setStroke(Paint strokePaint, Stroke stroke) {
		return new GraphicsStyle(fillPaint, strokePaint, stroke, strokeFirst);
	}

	public GraphicsStyle setStrokeFirst(boolean strokeFirst) {
		return new GraphicsStyle(fillPaint, strokePaint, stroke, strokeFirst);
	}
}
