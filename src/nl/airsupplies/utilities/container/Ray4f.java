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

package nl.airsupplies.utilities.container;

/**
 * @author Mark Jeronimus
 */
// Created 2005-10-03
public class Ray4f {
	public Vector2f origin;
	public Vector2f direction;

	private Vector2f directionSquared = null;

	public Ray4f() {
		origin    = new Vector2f();
		direction = new Vector2f();
	}

	public Ray4f(Vector2f origin, Vector2f direction) {
		this.origin    = origin;
		this.direction = direction;
	}

	public Ray4f(Ray4f other) {
		origin           = other.origin;
		direction        = other.direction;
		directionSquared = other.directionSquared;
	}

	public Vector2f getDirectionSquared() {
		if (directionSquared == null && direction != null) {
			directionSquared = direction.scale(direction);
		}
		return directionSquared;
	}

	@Override
	public String toString() {
		return origin + "+r*" + direction;
	}
}
