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
 * Java's AffineTransform doesn't have any useful interface for translating individual coordinates, and the inside are
 * inaccessible when inheriting, so here's my fixed version. I only added some useful methods. If you find some missing,
 * add them yourself.
 *
 * @author Mark Jeronimus
 */
// Created 2009-10-02
public class AffineTransformFloat implements TransformFloat {
	public float x0;
	public float y0;
	public float x1;
	public float y1;
	public float x2;
	public float y2;

	// These methods define the transform.
	public AffineTransformFloat() {
		setIdentity();
	}

	public AffineTransformFloat(float x0, float y0, float x1, float y1, float x2, float y2) {
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	public AffineTransformFloat(AffineTransformFloat tr) {
		x0 = tr.x0;
		y0 = tr.y0;
		x1 = tr.x1;
		y1 = tr.y1;
		x2 = tr.x2;
		y2 = tr.y2;
	}

	@Override
	public void setIdentity() {
		x0 = 1;
		y0 = 0;
		x1 = 0;
		y1 = 1;
		x2 = 0;
		y2 = 0;
	}

	public void set(float x0, float y0, float x1, float y1, float x2, float y2) {
		this.x0 = x0;
		this.y0 = y0;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}

	public void set(AffineTransformFloat tr) {
		x0 = tr.x0;
		y0 = tr.y0;
		x1 = tr.x1;
		y1 = tr.y1;
		x2 = tr.x2;
		y2 = tr.y2;
	}

	public void translateWorld(float tx, float ty) {
		x2 += tx;
		y2 += ty;
	}

	public void translateLocal(float tx, float ty) {
		x2 += tx * x0 + ty * x1;
		y2 += tx * y0 + ty * y1;
	}

	public void scaleWorld(float sx, float sy) {
		x0 *= sx;
		y0 *= sy;
		x1 *= sx;
		y1 *= sy;
		x2 *= sx;
		y2 *= sy;
	}

	public void scaleLocal(float sx, float sy) {
		x0 *= sx;
		y0 *= sx;
		x1 *= sy;
		y1 *= sy;
	}

	public void rotateWorld(float theta) {
		float c = (float)Math.cos(theta);
		float s = (float)Math.sin(theta);
		float x = x0;
		float y = y0;
		x0 = c * x - s * y;
		y0 = s * x + c * y;
		x  = x1;
		y  = y1;
		x1 = c * x - s * y;
		y1 = s * x + c * y;
		x  = x2;
		y  = y2;
		x2 = c * x - s * y;
		y2 = s * x + c * y;
	}

	public void rotateLocal(float theta) {
		float c = (float)Math.cos(theta);
		float s = (float)Math.sin(theta);
		float x = x0;
		float u = x1;
		x0 = s * u + c * x;
		x1 = c * u - s * x;
		x  = y0;
		u  = y1;
		y0 = s * u + c * x;
		y1 = c * u - s * x;
	}

	@Deprecated
	public void translate(float dx, float dy) {
		x2 += dx;
		y2 += dy;
	}

	@Deprecated
	public void translatePost(float dx, float dy) {
		x2 += dx * x0 + dy * x1;
		y2 += dx * y0 + dy * y1;
	}

	@Deprecated
	public void scale(float sx, float sy) {
		x0 *= sx;
		y0 *= sy;
		x1 *= sx;
		y1 *= sy;
	}

	@Deprecated
	public void scalePost(float sx, float sy) {
		x0 *= sx;
		y0 *= sy;
		x1 *= sx;
		y1 *= sy;
		x2 *= sx;
		y2 *= sy;
	}

	@Deprecated
	/**Replaced with rotateLocal */
	public void rotate(float theta) {
		float cos = (float)Math.cos(theta);
		float sin = (float)Math.sin(theta);
		float x   = x0;
		float y   = y0;
		x0 = cos * x + sin * y;
		y0 = -sin * x + cos * y;
		x  = x1;
		y  = y1;
		x1 = cos * x + sin * y;
		y1 = -sin * x + cos * y;
	}

	@Deprecated
	/**Replaced with rotateWorld */
	public void rotatePost(float theta) {
		float cos = (float)Math.cos(theta);
		float sin = (float)Math.sin(theta);
		float x   = x0;
		float y   = y0;
		x0 = cos * x + sin * y;
		y0 = -sin * x + cos * y;
		x  = x1;
		y  = y1;
		x1 = cos * x + sin * y;
		y1 = -sin * x + cos * y;
		x  = x2;
		y  = y2;
		x2 = cos * x + sin * y;
		y2 = -sin * x + cos * y;
	}

	/**
	 * Transforms the object in the transformed space.
	 */
	public void concatPre(AffineTransformFloat t) {
		float x = x0;
		float y = x1;
		x0 = t.x0 * x + t.y0 * y;
		x1 = t.x1 * x + t.y1 * y;
		x2 += t.x2 * x + t.y2 * y;

		x  = y0;
		y  = y1;
		y0 = t.x0 * x + t.y0 * y;
		y1 = t.x1 * x + t.y1 * y;
		y2 += t.x2 * x + t.y2 * y;
	}

	/**
	 * Transforms the transformed space in the embedded space.
	 */
	public void concat(AffineTransformFloat t) {
		float x = x0;
		float y = y0;
		x0 = x * t.x0 + y * t.x1;
		y0 = x * t.y0 + y * t.y1;

		x  = x1;
		y  = y1;
		x1 = x * t.x0 + y * t.x1;
		y1 = x * t.y0 + y * t.y1;

		x  = x2;
		y  = y2;
		x2 = x * t.x0 + y * t.x1 + t.x2;
		y2 = x * t.y0 + y * t.y1 + t.y2;
	}

	// These methods transform coordinates.
	@Override
	public float transformX(float x, float y) {
		return x0 * x + x1 * y + x2;
	}

	@Override
	public float transformY(float x, float y) {
		return y0 * x + y1 * y + y2;
	}

	@Override
	public float transformRelativeX(float x, float y) {
		throw new UnsupportedOperationException("Not supported yet");
	}

	@Override
	public float transformRelativeY(float x, float y) {
		throw new UnsupportedOperationException("Not supported yet");
	}

	@Override
	public float transformR(float r) {
		return r * (float)Math.sqrt(x0 * x0 + x1 * x1);
	}

	@Override
	public float reverseX(float x, float y) {
		throw new UnsupportedOperationException("Not supported yet");
	}

	@Override
	public float reverseY(float x, float y) {
		throw new UnsupportedOperationException("Not supported yet");
	}

	@Override
	public float reverseRelativeX(float x, float y) {
		throw new UnsupportedOperationException("Not supported yet");
	}

	@Override
	public float reverseRelativeY(float x, float y) {
		throw new UnsupportedOperationException("Not supported yet");
	}

	@Override
	public float reverseR(float r) {
		return r / (float)Math.sqrt(x0 * x0 + x1 * x1);
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
		out.append(']');
		out.append('\n');
		out.append('[');
		out.append(y0);
		out.append(' ');
		out.append(y1);
		out.append(' ');
		out.append(y2);
		out.append(']');
		return out.toString();
	}

	@Override
	public AffineTransform getSwingTransform() {
		return new AffineTransform(x0, y0, x1, y1, x2, y2);
	}
}
