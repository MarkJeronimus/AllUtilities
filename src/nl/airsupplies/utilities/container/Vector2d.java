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
// Created 2005-10-17
public class Vector2d implements Comparable<Vector2d>, Serializable {
	public double x;
	public double y;

	// // constructors // //
	public Vector2d() {
		x = 0;
		y = 0;
	}

	public Vector2d(Vector2d o) {
		x = o.x;
		y = o.y;
	}

	public Vector2d(double x, double y) {
		this.x = x;
		this.y = y;
	}

	// // modification methods // //
	public void set(Vector2d o) {
		x = o.x;
		y = o.y;
	}

	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void normalize() {
		double d = 1 / Math.hypot(x, y);
		x *= d;
		y *= d;
	}

	public void setLength(double f) {
		double d = f / Math.hypot(x, y);
		x *= d;
		y *= d;
	}

	public void neg() {
		x = -x;
		y = -y;
	}

	public void abs() {
		if (x > 0) {
			x = -x;
		}
		if (y > 0) {
			y = -y;
		}
	}

	public void flip() {
		double t = x;
		x = y;
		y = t;
	}

	public void rotate90() {
		double t = x;
		x = -y;
		y = t;
	}

	public void rotate180() {
		x = -x;
		y = -y;
	}

	public void rotate270() {
		double t = x;
		x = y;
		y = -t;
	}

	public void sqr() {
		x *= x;
		y *= y;
	}

	public void sqrt() {
		x = Math.sqrt(x);
		y = Math.sqrt(y);
	}

	public void cube() {
		x *= x * x;
		y *= y * y;
	}

	public void cbrt() {
		x = Math.cbrt(x);
		y = Math.cbrt(y);
	}

	/* ********************************************************************* *
	 * Binary functions.
	 */
	public void add(double v) {
		x += v;
		y += v;
	}

	public void add(double x, double y) {
		this.x += x;
		this.y += y;
	}

	public void add(Vector2d o) {
		x += o.x;
		y += o.y;
	}

	public void sub(double v) {
		x -= v;
		y -= v;
	}

	public void sub(double x, double y) {
		this.x -= x;
		this.y -= y;
	}

	public void sub(Vector2d o) {
		x -= o.x;
		y -= o.y;
	}

	public void subR(double v) {
		x = v - x;
		y = v - y;
	}

	public void subR(double x, double y) {
		this.x = x - this.x;
		this.y = y - this.y;
	}

	public void subR(Vector2d o) {
		x = o.x - x;
		y = o.y - y;
	}

	public void mul(double v) {
		x *= v;
		y *= v;
	}

	public void mul(double x, double y) {
		this.x *= x;
		this.y *= y;
	}

	public void mul(Vector2d o) {
		x *= o.x;
		y *= o.y;
	}

	public void div(double v) {
		x /= v;
		y /= v;
	}

	public void div(double x, double y) {
		this.x /= x;
		this.y /= y;
	}

	public void div(Vector2d o) {
		x /= o.x;
		y /= o.y;
	}

	public void divR(double v) {
		x = v / x;
		y = v / y;
	}

	public void divR(double x, double y) {
		this.x = x / this.x;
		this.y = y / this.y;
	}

	public void divR(Vector2d o) {
		x = o.x / x;
		y = o.y / y;
	}

	public void pow(double v) {
		x = Math.pow(x, v);
		y = Math.pow(y, v);
	}

	public void pow(double x, double y) {
		this.x = Math.pow(this.x, x);
		this.y = Math.pow(this.y, y);
	}

	public void pow(Vector2d o) {
		x = Math.pow(x, o.x);
		y = Math.pow(y, o.y);
	}

	public void powR(double v) {
		x = Math.pow(v, x);
		y = Math.pow(v, y);
	}

	public void powR(double x, double y) {
		this.x = Math.pow(x, this.x);
		this.y = Math.pow(y, this.y);
	}

	public void powR(Vector2d o) {
		x = Math.pow(o.x, x);
		y = Math.pow(o.y, y);
	}

	public void addScaled(Vector2d other, double f) {
		x += other.x * f;
		y += other.y * f;
	}

	public void addScaled(double dx, double dy, double f) {
		x += dx * f;
		y += dy * f;
	}

	/**
	 * Linear interpolation
	 */
	public void lerp(Vector2d other, double f) {
		x += (other.x - x) * f;
		y += (other.y - y) * f;
	}

	public void min(Vector2d p) {
		if (x > p.x) {
			x = p.x;
		}
		if (y > p.y) {
			y = p.y;
		}
	}

	public void max(Vector2d p) {
		if (x < p.x) {
			x = p.x;
		}
		if (y < p.y) {
			y = p.y;
		}
	}

	/* ********************************************************************* *
	 * Query functions.
	 */
	public double length() {
		return Math.hypot(x, y);
	}

	public double lengthSquared() {
		return x * x + y * y;
	}

	public double angle() {
		return Math.atan2(y, x);
	}

	public double distanceTo(double x, double y) {
		return Math.hypot(this.x - x, this.y - y);
	}

	public double distanceTo(Vector2d v) {
		return Math.hypot(x - v.x, y - v.y);
	}

	public double dotProduct(Vector2d v) {
		return x * v.x + y * v.y;
	}

	public void crossProduct(Vector2d v) {
		double x = this.x;
		this.x = x * v.x - y * v.y;
		y      = x * v.y + y * v.x;
	}

	public double angleTo(Vector2d v) {
		return Math.atan2(v.y - y, v.x - x);
	}

	public double angleBetween(Vector2d v) {
		return Math.acos((x * v.x + y * v.y) / (Math.hypot(x, y) * Math.hypot(v.x, v.y)));
	}

	public double angleFrom(Vector2d v1, Vector2d v2) {
		return Math.acos(((v1.x - x) * (v2.x - x) + (v1.y - y) * (v2.y - y))
		                 / (Math.hypot(v1.x - x, v1.y - y) * Math.hypot(v2.x - x, v2.y - y)));
	}

	public double angleBetween(Vector2d from, Vector2d to) {
		double dx1 = from.x - x;
		double dy1 = from.y - y;
		double dx2 = to.x - x;
		double dy2 = to.y - y;

		double b = dx1 * dx1 + dy1 * dy1;
		double a = dx1 / b;
		b   = dy1 / b;
		dx1 = dx2 * a + dy2 * b;
		dy1 = dy2 * a - dx2 * b;

		return Math.atan2(dy1, dx1);
	}

	public double distanceToSquared(double x, double y) {
		double dx = x - this.x;
		double dy = y - this.y;
		return dx * dx + dy * dy;
	}

	public double distanceToSquared(Vector2d o) {
		double dx = x - o.x;
		double dy = y - o.y;
		return dx * dx + dy * dy;
	}

	@Override
	public int compareTo(Vector2d o) {
		int diff = Double.compare(y, o.y);
		if (diff != 0) {
			return diff;
		}
		return Double.compare(x, o.x);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Vector2d other = (Vector2d)obj;
		return x == other.x && y == other.y;
	}

	@Override
	public int hashCode() {
		int hash = 0x811C9DC5;
		hash ^= Double.hashCode(x);
		hash *= 0x01000193;
		hash ^= Double.hashCode(y);
		hash *= 0x01000193;
		return hash;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + '(' + x + ',' + y + ')';
	}
}
