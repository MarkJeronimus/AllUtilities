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
// Created 2005-12-06
public class Vector3i implements Comparable<Vector3i> {
	public int x;
	public int y;
	public int z;

	public Vector3i() {
		x = y = z = 0;
	}

	public Vector3i(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3i(Vector3i o) {
		x = o.x;
		y = o.y;
		z = o.z;
	}

	public void set(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void set(Vector3i o) {
		x = o.x;
		y = o.y;
		z = o.z;
	}

	@Override
	public int compareTo(Vector3i o) {
		int diff = Integer.compare(z, o.z);
		if (diff != 0) {
			return diff;
		}
		diff = Integer.compare(y, o.y);
		if (diff != 0) {
			return diff;
		}
		return Integer.compare(x, o.x);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Vector3i v) {
			return x == v.x && y == v.y && z == v.z;
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		int hash = 0x811C9DC5;
		hash ^= Integer.hashCode(x);
		hash *= 0x01000193;
		hash ^= Integer.hashCode(y);
		hash *= 0x01000193;
		hash ^= Integer.hashCode(z);
		hash *= 0x01000193;
		return hash;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + '(' + x + ", " + y + ", " + z + ')';
	}
}
