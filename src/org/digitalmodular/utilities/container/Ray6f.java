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

/**
 * @author Mark Jeronimus
 */
// Created 2005-10-03
public class Ray6f {
	public Vector3f origin;
	public Vector3f direction;

	private Vector3f directionSquared = null;

	public Ray6f() {
		origin = null;
		direction = null;
	}

	public Ray6f(Vector3f origin, Vector3f direction) {
		this.origin = origin;
		this.direction = direction;
	}

	public Ray6f(Ray6f other) {
		origin = other.origin;
		direction = other.direction;
		directionSquared = other.directionSquared;
	}

	public Vector3f getDirectionSquared() {
		if (directionSquared == null && direction != null) {
			directionSquared.set(direction);
			directionSquared.sqr();
		}
		return directionSquared;
	}

	@Override
	public String toString() {
		return origin + "+r*" + direction;
	}
}
