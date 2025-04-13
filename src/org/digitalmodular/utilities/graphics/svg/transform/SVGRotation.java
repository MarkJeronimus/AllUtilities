/*
 * This file is part of PAO.
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
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.digitalmodular.utilities.graphics.svg.transform;

import java.io.IOException;

import org.digitalmodular.utilities.NumberUtilities;
import org.digitalmodular.utilities.graphics.svg.core.SVGTransform;
import static org.digitalmodular.utilities.ValidatorUtilities.requireNotDegenerate;

/**
 * @author Mark Jeronimus
 */
// Created 2021-06-11
public final class SVGRotation implements SVGTransform {
	private final float rotation;
	private final float rotationOriginX;
	private final float rotationOriginY;

	public SVGRotation(float rotation) {
		this.rotation = requireNotDegenerate(rotation, "rotation");
		rotationOriginX = Float.NaN;
		rotationOriginY = Float.NaN;
	}

	public SVGRotation(float rotation, float rotationOriginX, float rotationOriginY) {
		this.rotation = requireNotDegenerate(rotation, "rotation");
		this.rotationOriginX = requireNotDegenerate(rotationOriginX, "rotationOriginX");
		this.rotationOriginY = requireNotDegenerate(rotationOriginY, "rotationOriginY");
	}

	public float getRotation() {
		return rotation;
	}

	public float getRotationOriginX() {
		return rotationOriginX;
	}

	public float getRotationOriginY() {
		return rotationOriginY;
	}

	@Override
	public SVGTransform overwrite(SVGTransform transform) {
		if (!(transform instanceof SVGRotation)) {
			throw new UnsupportedOperationException("Not implemented: " + getClass().getSimpleName() +
			                                        ".overwrite() for " + transform.getClass().getSimpleName());
		}

		SVGRotation other = (SVGRotation)transform;
		return new SVGRotation(other.rotation - rotation, other.rotationOriginX, other.rotationOriginY);
	}

	@Override
	public void encode(Appendable out) throws IOException {
		if (Math.abs(rotation) < 1.0e-3f) {
			return;
		}

		out.append(" transform=\"rotate(").append(Float.toString(rotation));

		if (!NumberUtilities.isDegenerate(rotationOriginX)) {
			out.append(',');
			out.append(Float.toString(rotationOriginX));
			out.append(',');
			out.append(Float.toString(rotationOriginY));
		}

		out.append(")\"");
	}
}
