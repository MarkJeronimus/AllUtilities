/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2024 Mark Jeronimus. All Rights Reversed.
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.digitalmodular.utilities.container;

import java.io.Serializable;

/**
 * An immutable transform for axis-aligned linear translation and scaling.
 * <p>
 * Terminology used in method names:<ul>
 * <li>Local: The coordinate system to translate <u>from</u> (usually model
 * coordinates),</li>
 * <li>World: The coordinate system to translate <u>to</u> (usually pixel
 * coordinates),</li>
 * <li>Relative: Transform only size, not position.</li>
 * </ul>
 * <p>
 * The reverse functions use slightly less efficient math. This may be improved
 * in future releases.
 * <p>
 *
 * @author Mark Jeronimus
 */
public class HomotheticTransform implements Serializable {
	private final double sx;
	private final double tx;
	private final double sy;
	private final double ty;

	public HomotheticTransform() {
		this(1, 1, 0, 0);
	}

	public HomotheticTransform(double sx, double sy, double tx, double ty) {
		this.tx = tx;
		this.sx = sx;
		this.ty = ty;
		this.sy = sy;
	}

	public HomotheticTransform setX(double tx, double sx) {
		return new HomotheticTransform(sx, sy, tx, ty);
	}

	public HomotheticTransform setY(double ty, double sy) {
		return new HomotheticTransform(sx, sy, tx, ty);
	}

	public HomotheticTransform translateWorld(double tx, double ty) {
		return new HomotheticTransform(sx,
		                               sy,
		                               this.tx + tx,
		                               this.ty + ty);
	}

	public HomotheticTransform translateLocal(double tx, double ty) {
		return new HomotheticTransform(sx,
		                               sy,
		                               this.tx + tx * sx,
		                               this.ty + ty * sy);
	}

	public HomotheticTransform scaleWorld(double sx, double sy) {
		return new HomotheticTransform(this.sx * sx,
		                               this.sy * sy,
		                               tx * sx,
		                               ty * sy);
	}

	public HomotheticTransform scaleLocal(double sx, double sy) {
		return new HomotheticTransform(this.sx * sx,
		                               this.sy * sy,
		                               tx,
		                               ty);
	}

	public double transformX(double x, double y) {
		return sx * x + tx;
	}

	public double transformY(double x, double y) {
		return sy * y + ty;
	}

	public double transformRelativeX(double x, double y) {
		return sx * x;
	}

	public double transformRelativeY(double x, double y) {
		return sy * y;
	}

	public double reverseX(double x, double y) {
		return (x - tx) / sx;
	}

	public double reverseY(double x, double y) {
		return (y - ty) / sy;
	}

	public double reverseRelativeX(double x, double y) {
		return x / sx;
	}

	public double reverseRelativeY(double x, double y) {
		return y / sy;
	}

	public HomotheticTransform concat(HomotheticTransform transform) {
		return new HomotheticTransform(sx * transform.sx,
		                               sy * transform.sy,
		                               tx * transform.sx + transform.tx,
		                               ty * transform.sy + transform.ty);
	}

	public HomotheticTransform transpose() {
		return new HomotheticTransform(sy, sx, ty, tx);
	}

	public HomotheticTransform inverse() {
		if (sx == 0 || sy == 0) {
			throw new IllegalStateException("Transform is degenerate and therefore non-invertible: " + this);
		}

		return new HomotheticTransform(1 / sx,
		                               1 / sy,
		                               -tx / sx,
		                               -ty / sy);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		HomotheticTransform other = (HomotheticTransform)obj;
		return Double.doubleToLongBits(sx) == Double.doubleToLongBits(other.sx) &&
		       Double.doubleToLongBits(sy) == Double.doubleToLongBits(other.sy) &&
		       Double.doubleToLongBits(tx) == Double.doubleToLongBits(other.tx) &&
		       Double.doubleToLongBits(ty) == Double.doubleToLongBits(other.ty);
	}

	@Override
	public int hashCode() {
		int hash = 0x811C9DC5;
		hash ^= Double.hashCode(sx);
		hash *= 0x01000193;
		hash ^= Double.hashCode(sy);
		hash *= 0x01000193;
		hash ^= Double.hashCode(tx);
		hash *= 0x01000193;
		hash ^= Double.hashCode(ty);
		hash *= 0x01000193;
		return hash;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[sx=" + sx + ", sy=" + sy + ", tx=" + tx + ", ty=" + ty + ']';
	}
}
