/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2018 Mark Jeronimus. All Rights Reversed.
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
package org.digitalmodular.utilities.gui.transform;

/**
 * Java's AffineTransform doesn't have any useful interface for translating individual coordinates, and the inside are
 * inaccessible when inheriting, so here's my fixed version. I only added some useful methods. If you find some missing,
 * add them yourself.
 *
 * @author Mark Jeronimus
 */
// Created 2009-10-02
public class AffineTransformFloat3D implements TransformFloat3D {
	public float x0;
	public float y0;
	public float z0;
	public float x1;
	public float y1;
	public float z1;
	public float x2;
	public float y2;
	public float z2;
	public float x3;
	public float y3;
	public float z3;

	// These methods define the transform.
	public AffineTransformFloat3D() {
		setIdentity();
	}

	public AffineTransformFloat3D(float x0, float y0, float z0, float x1, float y1, float z1, float x2, float y2,
	                              float z2,
	                              float x3, float y3, float z3) {
		this.x0 = x0;
		this.y0 = y0;
		this.z0 = z0;
		this.x1 = x1;
		this.y1 = y1;
		this.z1 = z1;
		this.x2 = x2;
		this.y2 = y2;
		this.z2 = z2;
		this.x3 = x3;
		this.y3 = y3;
		this.z3 = z3;
	}

	public AffineTransformFloat3D(AffineTransformFloat3D tr) {
		x0 = tr.x0;
		y0 = tr.y0;
		z0 = tr.z0;
		x1 = tr.x1;
		y1 = tr.y1;
		z1 = tr.z1;
		x2 = tr.x2;
		y2 = tr.y2;
		z2 = tr.z2;
		x3 = tr.x3;
		y3 = tr.y3;
		z3 = tr.z3;
	}

	@Override
	public void setIdentity() {
		x0 = 1;
		y0 = 0;
		z0 = 0;
		x1 = 0;
		y1 = 1;
		z1 = 0;
		x2 = 0;
		y2 = 0;
		z2 = 1;
		x3 = 0;
		y3 = 0;
		z3 = 0;
	}

	public void set(float x0, float y0, float z0, float x1, float y1, float z1, float x2, float y2, float z2, float x3,
	                float y3,
	                float z3) {
		this.x0 = x0;
		this.y0 = y0;
		this.z0 = z0;
		this.x1 = x1;
		this.y1 = y1;
		this.z1 = z1;
		this.x2 = x2;
		this.y2 = y2;
		this.z2 = z2;
		this.x3 = x3;
		this.y3 = y3;
		this.z3 = z3;
	}

	public void set(AffineTransformFloat3D tr) {
		x0 = tr.x0;
		y0 = tr.y0;
		z0 = tr.z0;
		x1 = tr.x1;
		y1 = tr.y1;
		z1 = tr.z1;
		x2 = tr.x2;
		y2 = tr.y2;
		z2 = tr.z2;
		x3 = tr.x3;
		y3 = tr.y3;
		z3 = tr.z3;
	}

	public void translateWorld(float tx, float ty, float tz) {
		x3 += tx;
		y3 += ty;
		z3 += tz;
	}

	public void translateLocal(float tx, float ty, float tz) {
		x3 += tx * x0 + ty * x1 + tz * x2;
		y3 += tx * y0 + ty * y1 + tz * y2;
		z3 += tx * y0 + ty * y1 + tz * z2;
	}

	public void scaleWorld(float sx, float sy, float sz) {
		x0 *= sx;
		y0 *= sy;
		z0 *= sz;
		x1 *= sx;
		y1 *= sy;
		z1 *= sz;
		x2 *= sx;
		y2 *= sy;
		z2 *= sz;
	}

	public void scaleLocal(float sx, float sy, float sz) {
		x0 *= sx;
		y0 *= sx;
		z0 *= sz;
		x1 *= sy;
		y1 *= sy;
		z1 *= sz;
	}

	public void rotateWorldX(float theta) {
		float c = (float)Math.cos(theta);
		float s = (float)Math.sin(theta);
		float y = y0;
		float z = z0;
		y0 = c * y - s * z;
		z0 = s * y + c * z;
		y = y1;
		z = z1;
		y1 = c * y - s * z;
		z1 = s * y + c * z;
		y = y2;
		z = z2;
		y2 = c * y - s * z;
		z2 = s * y + c * z;
	}

	public void rotateWorldY(float theta) {
		float c = (float)Math.cos(theta);
		float s = (float)Math.sin(theta);
		float z = z0;
		float x = x0;
		z0 = c * z - s * x;
		x0 = s * z + c * x;
		z = z1;
		x = x1;
		z1 = c * z - s * x;
		x1 = s * z + c * x;
		z = z2;
		x = x2;
		z2 = c * z - s * x;
		x2 = s * z + c * x;
	}

	public void rotateWorldZ(float theta) {
		float c = (float)Math.cos(theta);
		float s = (float)Math.sin(theta);
		float x = x0;
		float y = y0;
		x0 = c * x - s * y;
		y0 = s * x + c * y;
		x = x1;
		y = y1;
		x1 = c * x - s * y;
		y1 = s * x + c * y;
		x = x2;
		y = y2;
		x2 = c * x - s * y;
		y2 = s * x + c * y;
	}

	public void rotateLocalX(float theta) {
		float c = (float)Math.cos(theta);
		float s = (float)Math.sin(theta);
		float y = x1;
		float z = x2;
		x1 = s * z + c * y;
		x2 = c * z - s * y;
		y = y1;
		z = y2;
		y1 = s * z + c * y;
		y2 = c * z - s * y;
	}

	public void rotateLocalY(float theta) {
		float c = (float)Math.cos(theta);
		float s = (float)Math.sin(theta);
		float z = x2;
		float x = x0;
		x2 = s * x + c * z;
		x0 = c * x - s * z;
		z = y2;
		x = y0;
		y2 = s * x + c * z;
		y0 = c * x - s * z;
	}

	public void rotateLocalZ(float theta) {
		float c = (float)Math.cos(theta);
		float s = (float)Math.sin(theta);
		float x = x0;
		float y = x1;
		x0 = s * y + c * x;
		x1 = c * y - s * x;
		x = y0;
		y = y1;
		y0 = s * y + c * x;
		y1 = c * y - s * x;
	}

	/**
	 * Transforms the object in the transformed space.
	 */
	public void concatPre(AffineTransformFloat3D t) {
		float x = x0;
		float y = x1;
		float z = x2;
		x0 = t.x0 * x + t.y0 * y + t.z0 * z;
		x1 = t.x1 * x + t.y1 * y + t.z1 * z;
		x2 += t.x2 * x + t.y2 * y + t.z2 * z;

		x = y0;
		y = y1;
		z = y2;
		y0 = t.x0 * x + t.y0 * y + t.z0 * z;
		y1 = t.x1 * x + t.y1 * y + t.z1 * z;
		y2 += t.x2 * x + t.y2 * y + t.z2 * z;

		x = z0;
		y = z1;
		z = z2;
		z0 = t.x0 * x + t.y0 * y + t.z0 * z;
		z1 = t.x1 * x + t.y1 * y + t.z1 * z;
		z2 += t.x2 * x + t.y2 * y + t.z2 * z;
	}

	/**
	 * Transforms the transformed space in the embedded space.
	 */
	public void concat(AffineTransformFloat3D t) {
		float x = x0;
		float y = y0;
		x0 = x * t.x0 + y * t.x1;
		y0 = x * t.y0 + y * t.y1;

		x = x1;
		y = y1;
		x1 = x * t.x0 + y * t.x1;
		y1 = x * t.y0 + y * t.y1;

		x = x2;
		y = y2;
		x2 = x * t.x0 + y * t.x1 + t.x2;
		y2 = x * t.y0 + y * t.y1 + t.y2;
	}

	// These methods transform coordinates.
	@Override
	public float transformX(float x, float y, float z) {
		return x0 * x + x1 * y + x2 * z + x3;
	}

	@Override
	public float transformY(float x, float y, float z) {
		return y0 * x + y1 * y + y2 * z + y3;
	}

	@Override
	public float transformZ(float x, float y, float z) {
		return z0 * x + z1 * y + z2 * z + z3;
	}

	@Override
	public float transformRelativeX(float x, float y, float z) {
		throw new UnsupportedOperationException("Not supported yet");
	}

	@Override
	public float transformRelativeY(float x, float y, float z) {
		throw new UnsupportedOperationException("Not supported yet");
	}

	@Override
	public float transformRelativeZ(float x, float y, float z) {
		throw new UnsupportedOperationException("Not supported yet");
	}

	@Override
	public float transformR(float r) {
		return r * (float)Math.sqrt(x0 * x0 + x1 * x1 + z1 * z1);
	}

	@Override
	public float reverseX(float x, float y, float z) {
		throw new UnsupportedOperationException("Not supported yet");
	}

	@Override
	public float reverseY(float x, float y, float z) {
		throw new UnsupportedOperationException("Not supported yet");
	}

	@Override
	public float reverseZ(float x, float y, float z) {
		throw new UnsupportedOperationException("Not supported yet");
	}

	@Override
	public float reverseRelativeX(float x, float y, float z) {
		throw new UnsupportedOperationException("Not supported yet");
	}

	@Override
	public float reverseRelativeY(float x, float y, float z) {
		throw new UnsupportedOperationException("Not supported yet");
	}

	@Override
	public float reverseRelativeZ(float x, float y, float z) {
		throw new UnsupportedOperationException("Not supported yet");
	}

	@Override
	public float reverseR(float r) {
		return r / (float)Math.sqrt(x0 * x0 + x1 * x1 + x2 * x2);
	}

	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		out.append('[');
		out.append(x0);
		out.append(' ');
		out.append(x1);
		out.append(' ');
		out.append(x2);
		out.append(' ');
		out.append(x3);
		out.append(']');
		out.append('\n');
		out.append('[');
		out.append(y0);
		out.append(' ');
		out.append(y1);
		out.append(' ');
		out.append(y2);
		out.append(' ');
		out.append(y3);
		out.append('\n');
		out.append('[');
		out.append(z0);
		out.append(' ');
		out.append(z1);
		out.append(' ');
		out.append(z2);
		out.append(' ');
		out.append(z3);
		out.append(']');
		return out.toString();
	}
}
