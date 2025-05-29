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

package nl.airsupplies.utilities.graphics.transform;

import java.awt.geom.AffineTransform;

/**
 * @author Mark Jeronimus
 */
// Created 2009-04-21
public interface TransformDouble {
	void setIdentity();

	double transformX(double x, double y);

	double transformY(double x, double y);

	double transformRelativeX(double x, double y);

	double transformRelativeY(double x, double y);

	double transformR(double r);

	double reverseX(double x, double y);

	double reverseY(double x, double y);

	double reverseRelativeX(double x, double y);

	double reverseRelativeY(double x, double y);

	double reverseR(double r);

	AffineTransform getSwingTransform();
}
