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
package org.digitalmodular.utilities.container;

import java.io.Serializable;

/**
 * @author Mark Jeronimus
 */
// Created 2005-11-29
public class Vector2l implements Comparable<Vector2l>, Serializable {
	/**
	 * The <i>x </i> coordinate. If no <i>x </i> coordinate is set it will default to 0.
	 */
	public long x;

	/**
	 * The <i>y </i> coordinate. If no <i>y </i> coordinate is set it will default to 0.
	 */
	public long y;

	/**
	 * Constructs and initializes a Vector2i at the origin (0,&nbsp;0) of the coordinate space.
	 */
	public Vector2l() {
		x = 0;
		y = 0;
	}

	/**
	 * Constructs and initializes a Vector2l with the same location as the specified {@code Vector2i} object.
	 *
	 * @param v a Vector2i
	 */
	public Vector2l(Vector2l v) {
		x = v.x;
		y = v.y;
	}

	/**
	 * Constructs and initializes a Vector2l at the specified ( <i>x </i>,&nbsp; <i>y </i>) location in the coordinate
	 * space.
	 *
	 * @param x the <i>x </i> coordinate
	 * @param y the <i>y </i> coordinate
	 */
	public Vector2l(long x, long y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int compareTo(Vector2l o) {
		int diff = Long.compare(y, o.y);
		if (diff != 0) {
			return diff;
		}
		return Long.compare(x, o.x);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Vector2l) {
			Vector2l v = (Vector2l)obj;
			return x == v.x && y == v.y;
		}
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		int hash = 0x811C9DC5;
		hash ^= Long.hashCode(x);
		hash *= 0x01000193;
		hash ^= Long.hashCode(y);
		hash *= 0x01000193;
		return hash;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "(" + x + ", " + y + ")";
	}
}
