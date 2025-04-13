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

package org.digitalmodular.utilities.graphics.image;

import java.awt.geom.AffineTransform;

import org.digitalmodular.utilities.graphics.transform.AffineTransformFloat;

/**
 * {@link ImageMatrixFloat} transformation using an {@link AffineTransform}. The transformation uses Backward Mapping
 * using
 * Linear Interpolation. This means that each output pixel coordinate is multiplied with the transform matrix to obtain
 * the input coordinate. The destination color is the weighted linear average of the four closest integer coordinates.
 *
 * @author Mark Jeronimus
 */
// Created 2011-11-22
public class AffineImageTransformation {
	private final AffineTransformFloat transform;

	AffineTransformFloat appliedTransform = new AffineTransformFloat();

	private TransformationProcess processes;

	ImageMatrixFloat in;
	ImageMatrixFloat out;

	public AffineImageTransformation(AffineTransformFloat transform) {
		this.transform = new AffineTransformFloat(transform);

		selectProcess();
	}

	private void selectProcess() {
		if (transform.x1 != 0 || transform.y0 != 0) {
			processes = new TransformationProcessAffine();
		} else if (transform.x0 != 1 || transform.y1 != 1) {
			processes = new TransformationProcessScale();
		} else {
			processes = new TransformationProcessTranslate();
		}
	}

	public void setTranslation(float x, float y) {
		transform.x2 = x;
		transform.y2 = y;
		selectProcess();
	}

	public void setScale(float centerX, float centerY, float scaleX, float scaleY) {
		transform.setIdentity();
		transform.translateWorld(centerX, centerY);
		transform.scaleLocal(1 / scaleX, 1 / scaleY);
		transform.translateLocal(-centerX, -centerY);
		selectProcess();
	}

	public void setRotation(float centerX, float centerY, float rotation) {
		transform.setIdentity();
		transform.translateWorld(centerX, centerY);
		transform.rotateLocal(-rotation);
		transform.translateLocal(-centerX, -centerY);
		selectProcess();
	}

	public void setStretchRotation(float centerX, float centerY, float scaleX, float scaleY, float rotation) {
		transform.setIdentity();
		transform.translateWorld(centerX, centerY);
		transform.rotateLocal(-rotation);
		transform.scaleLocal(1 / scaleX, 1 / scaleY);
		transform.translateLocal(-centerX, -centerY);
		selectProcess();
	}

	/**
	 * The in and out images may be of different dimensions (border of input should be at least 1). Beforehand, set,
	 * extend, mirror or wrap the border as you wish. Pro tip: use image flipping to prevent copying an image every
	 * time.
	 */
	public void transform(ImageMatrixFloat in, ImageMatrixFloat out) {
		if (in.border < 1) {
			throw new IllegalArgumentException("Border too small for kernel: " + in.border + " < 1");
		}

		this.in = in;
		this.out = out;

		appliedTransform.set(transform);
		appliedTransform.translateLocal(-out.border, -out.border);
		appliedTransform.translateWorld(in.border, in.border);

		processes.transform();
	}

	private interface TransformationProcess {
		void transform();
	}

	private class TransformationProcessTranslate implements TransformationProcess {
		public TransformationProcessTranslate() {
		}

		@Override
		public void transform() {
			// Inner loop
			int       u0; // 6.5 (5~8)
			int       u1; // 6.5 (5~8)
			float     a; // 6
			float[][] plane; // 4
			float     c; // 3
			int       beforeBorder = in.border - 1; // 2.5 (1~3)
			int       afterBorderX = in.endX; // 2.5 (1~3)
			float     d; // 2
			float     u; // 2
			float     vf; // 2
			float[]   row0; // 2
			float[]   row1; // 2
			float[]   row; // 1
			float     tx           = appliedTransform.x2; // 1
			int       endX         = out.endX; // 1

			for (int z = 0; z < out.numComponents; z++) {
				plane = in.matrix[z];
				for (int y = out.border; y < out.endY; y++) {
					row = out.matrix[z][y];

					float v  = y + appliedTransform.y2;
					int   v0 = (int)v;
					int   v1 = v0 + 1;
					vf = v - v0;

					if (v1 < beforeBorder) {
						v0 = beforeBorder;
						v1 = beforeBorder;
					} else if (v0 > in.endY) {
						v0 = in.endY;
						v1 = in.endY;
					} else {
						if (v0 < beforeBorder) {
							v0 = beforeBorder;
						}
						if (v1 > in.endY) {
							v1 = in.endY;
						}
					}

					row0 = plane[v0];
					row1 = plane[v1];

					for (int x = out.border; x < endX; x++) {
						u = x + tx;
						u0 = (int)u;
						u1 = u0 + 1;
						if (u1 < beforeBorder) {
							u0 = beforeBorder;
							u1 = beforeBorder;
						} else if (u0 > afterBorderX) {
							u0 = afterBorderX;
							u1 = afterBorderX;
						} else {
							if (u0 < beforeBorder) {
								u0 = beforeBorder;
							}
							if (u1 > afterBorderX) {
								u1 = afterBorderX;
							}
						}

						a = row0[u0];
						c = a + (row1[u0] - a) * vf;

						a = row0[u1];
						d = a + (row1[u1] - a) * vf;

						row[x] = c + (d - c) * (u - u0);
					}
				}
			}
		}
	}

	private class TransformationProcessScale implements TransformationProcess {
		public TransformationProcessScale() {
		}

		@Override
		public void transform() {
			// Inner loop
			int       u0; // 6.5 (5~8)
			int       u1; // 6.5 (5~8)
			float     a; // 6
			float[][] plane; // 4
			float     c; // 3
			int       beforeBorder = in.border - 1; // 2.5 (1~3)
			int       afterBorderX = in.endX; // 2.5 (1~3)
			float     d; // 2
			float     u; // 2
			float     vf; // 2
			float[]   row0; // 2
			float[]   row1; // 2
			float[]   row; // 1
			float     sx           = appliedTransform.x0; // 1
			float     tx           = appliedTransform.x2; // 1
			int       endX         = out.endX; // 1

			for (int z = 0; z < out.numComponents; z++) {
				plane = in.matrix[z];
				for (int y = out.border; y < out.endY; y++) {
					row = out.matrix[z][y];

					float v  = y * appliedTransform.y1 + appliedTransform.y2;
					int   v0 = (int)v;
					int   v1 = v0 + 1;
					vf = v - v0;

					if (v1 < beforeBorder) {
						v0 = beforeBorder;
						v1 = beforeBorder;
					} else if (v0 > in.endY) {
						v0 = in.endY;
						v1 = in.endY;
					} else {
						if (v0 < beforeBorder) {
							v0 = beforeBorder;
						}
						if (v1 > in.endY) {
							v1 = in.endY;
						}
					}

					row0 = plane[v0];
					row1 = plane[v1];

					for (int x = out.border; x < endX; x++) {
						u = x * sx + tx;
						u0 = (int)u;
						u1 = u0 + 1;
						if (u1 < beforeBorder) {
							u0 = beforeBorder;
							u1 = beforeBorder;
						} else if (u0 > afterBorderX) {
							u0 = afterBorderX;
							u1 = afterBorderX;
						} else {
							if (u0 < beforeBorder) {
								u0 = beforeBorder;
							}
							if (u1 > afterBorderX) {
								u1 = afterBorderX;
							}
						}

						a = row0[u0];
						c = a + (row1[u0] - a) * vf;

						a = row0[u1];
						d = a + (row1[u1] - a) * vf;

						row[x] = c + (d - c) * (u - u0);
					}
				}
			}
		}
	}

	private class TransformationProcessAffine implements TransformationProcess {
		public TransformationProcessAffine() {
		}

		@Override
		public void transform() {
			// Inner loop
			int       u0; // 6.5 (5~8)
			int       u1; // 6.5 (5~8)
			int       v0; // 6.5 (5~8)
			int       v1; // 6.5 (5~8)
			float     a; // 6
			float     b; // 4
			float[][] plane; // 4
			float     c; // 3
			int       beforeBorder = in.border - 1; // 2.5 (1~3)
			int       afterBorderX = in.endX; // 2.5 (1~3)
			int       afterBorderY = in.endY; // 2.5 (1~3)
			float     d; // 2
			float     u; // 2
			float     v; // 2
			float     vf; // 2
			float[]   row; // 1
			float     sx           = appliedTransform.x0; // 1
			float     zx           = appliedTransform.x1; // 1
			float     tx           = appliedTransform.x2; // 1
			float     zy           = appliedTransform.y0; // 1
			float     sy           = appliedTransform.y1; // 1
			float     ty           = appliedTransform.y2; // 1
			int       endX         = out.endX; // 1

			for (int z = 0; z < out.numComponents; z++) {
				plane = in.matrix[z];
				for (int y = out.border; y < out.endY; y++) {
					row = out.matrix[z][y];

					for (int x = out.border; x < endX; x++) {
						v = x * zy + y * sy + ty;
						v0 = (int)v;
						v1 = v0 + 1;
						vf = v - v0;

						if (v1 < beforeBorder) {
							v0 = beforeBorder;
							v1 = beforeBorder;
						} else if (v0 > afterBorderY) {
							v0 = afterBorderY;
							v1 = afterBorderY;
						} else {
							if (v0 < beforeBorder) {
								v0 = beforeBorder;
							}
							if (v1 > afterBorderY) {
								v1 = afterBorderY;
							}
						}

						u = x * sx + y * zx + tx;
						u0 = (int)u;
						u1 = u0 + 1;
						if (u1 < beforeBorder) {
							u0 = beforeBorder;
							u1 = beforeBorder;
						} else if (u0 > afterBorderX) {
							u0 = afterBorderX;
							u1 = afterBorderX;
						} else {
							if (u0 < beforeBorder) {
								u0 = beforeBorder;
							}
							if (u1 > afterBorderX) {
								u1 = afterBorderX;
							}
						}

						a = plane[v0][u0];
						b = plane[v1][u0];
						c = a + (b - a) * vf;

						a = plane[v0][u1];
						b = plane[v1][u1];
						d = a + (b - a) * vf;

						row[x] = c + (d - c) * (u - u0);
					}
				}
			}
		}
	}
}
