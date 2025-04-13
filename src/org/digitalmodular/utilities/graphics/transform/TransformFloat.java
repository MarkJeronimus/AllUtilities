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

package org.digitalmodular.utilities.graphics.transform;

import java.awt.geom.AffineTransform;

/**
 * @author Mark Jeronimus
 */
// Created 2009-04-21
public interface TransformFloat {
	void setIdentity();

	float transformX(float x, float y);

	float transformY(float x, float y);

	float transformRelativeX(float x, float y);

	float transformRelativeY(float x, float y);

	float transformR(float r);

	float reverseX(float x, float y);

	float reverseY(float x, float y);

	float reverseRelativeX(float x, float y);

	float reverseRelativeY(float x, float y);

	float reverseR(float r);

	AffineTransform getSwingTransform();
}
