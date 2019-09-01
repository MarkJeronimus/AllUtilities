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
package org.digitalmodular.utilities.gui.transform;

import java.awt.geom.AffineTransform;

/**
 * A transform that only allows translation and scaling. It is a subset of an Affine transform with the restriction
 * staying axis-aligned.
 *
 * @author Mark Jeronimus
 */
// Created 2009-10-02
public class HomotheticTransformDouble implements TransformDouble {
	public double sx;
	public double sy;
	public double tx;
	public double ty;

	public HomotheticTransformDouble() {
		sx = 1;
		sy = 1;
		tx = 0;
		ty = 0;
	}

	public HomotheticTransformDouble(double sx, double sy, double tx, double ty) {
		this.sx = sx;
		this.sy = sy;
		this.tx = tx;
		this.ty = ty;
	}

	public HomotheticTransformDouble(HomotheticTransformDouble other) {
		sx = other.sx;
		sy = other.sy;
		tx = other.tx;
		ty = other.ty;
	}

	@Override
	public void setIdentity() {
		sx = 1;
		sy = 1;
		tx = 0;
		ty = 0;
	}

	public void set(double sx, double sy, double tx, double ty) {
		this.sx = sx;
		this.sy = sy;
		this.tx = tx;
		this.ty = ty;
	}

	public void set(HomotheticTransformDouble other) {
		sx = other.sx;
		sy = other.sy;
		tx = other.tx;
		ty = other.ty;
	}

	public void translateWorld(double tx, double ty) {
		this.tx += tx;
		this.ty += ty;
	}

	public void translateLocal(double tx, double ty) {
		this.tx += tx * sx;
		this.ty += ty * sy;
	}

	public void scaleWorld(double sx, double sy) {
		this.sx *= sx;
		this.sy *= sy;
		tx *= sx;
		ty *= sy;
	}

	public void scaleLocal(double sx, double sy) {
		this.sx *= sx;
		this.sy *= sy;
	}

	@Override
	public double transformX(double x, double y) {
		return sx * x + tx;
	}

	@Override
	public double transformY(double x, double y) {
		return sy * y + ty;
	}

	@Override
	public double transformRelativeX(double x, double y) {
		return sx * x;
	}

	@Override
	public double transformRelativeY(double x, double y) {
		return sy * y;
	}

	@Override
	public double transformR(double r) {
		return r * sx;
	}

	@Override
	public double reverseX(double x, double y) {
		return (x - tx) / sx;
	}

	@Override
	public double reverseY(double x, double y) {
		return (y - ty) / sy;
	}

	@Override
	public double reverseRelativeX(double x, double y) {
		return x / sx;
	}

	@Override
	public double reverseRelativeY(double x, double y) {
		return y / sy;
	}

	@Override
	public double reverseR(double r) {
		return r / sx;
	}

	@Override
	public AffineTransform getSwingTransform() {
		return new AffineTransform(sx, 0, 0, sy, tx, ty);
	}
}
