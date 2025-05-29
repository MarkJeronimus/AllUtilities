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

import java.io.Serializable;

/**
 * @author Mark Jeronimus
 */
// Created 2005-11-29
public class Vector2i implements Comparable<Vector2i>, Serializable {

	public int x;

	public int y;

	public Vector2i() {
		x = 0;
		y = 0;
	}

	public Vector2i(Vector2i v) {
		x = v.x;
		y = v.y;
	}

	public Vector2i(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void set(Vector2i v) {
		x = v.x;
		y = v.y;
	}

	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void add(Vector2i other) {
		x += other.x;
		y += other.y;
	}

	public void add(int x, int y) {
		this.x += x;
		this.y += y;
	}

	public void sub(Vector2i other) {
		x -= other.x;
		y -= other.y;
	}

	public void sub(int x, int y) {
		this.x -= x;
		this.y -= y;
	}

	public void subR(Vector2i other) {
		x = other.x - x;
		y = other.y - y;
	}

	public void mul(int d) {
		x *= d;
		y *= d;
	}

	public void div(int d) {
		x /= d;
		y /= d;
	}

	public void min(int x, int y) {
		this.x = Math.min(this.x, x);
		this.y = Math.min(this.y, y);
	}

	public void flip() {
		int t = x;
		x = y;
		y = t;
	}

	public double length() {
		return Math.hypot(x, y);
	}

	public int area() {
		return x * y;
	}

	public int distanceToSquared(Vector2i other) {
		int dx = x - other.x;
		int dy = y - other.y;
		return dx * dx + dy * dy;
	}

	public double distanceTo(Vector2i other) {
		return Math.hypot(x - other.x, y - other.y);
	}

	/**
	 * Rotates clockwise in a right-handed coordinate system (like graphs but unlike the screen)
	 */
	public void rotateCW() {
		int t = x;
		x = y;
		y = -t;
	}

	/**
	 * Rotates counter-clockwise in a right-handed coordinate system (like graphs but unlike the screen)
	 */
	public void rotateCCW() {
		int t = x;
		x = -y;
		y = t;
	}

	public void invert() {
		x = -x;
		y = -y;
	}

	@Override
	public int compareTo(Vector2i o) {
		int diff = Integer.compare(y, o.y);
		if (diff != 0) {
			return diff;
		}
		return Integer.compare(x, o.x);
	}

	public boolean equals(int x, int y) {
		return this.x == x && this.y == y;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Vector2i other = (Vector2i)obj;
		return y == other.y && x == other.x;
	}

	@Override
	public int hashCode() {
		int hash = 0x811C9DC5;
		hash ^= Integer.hashCode(x);
		hash *= 0x01000193;
		hash ^= Integer.hashCode(y);
		hash *= 0x01000193;
		return hash;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ')';
	}
}
