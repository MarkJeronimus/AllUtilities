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
package org.digitalmodular.utilities.gui.image.function;

import org.digitalmodular.utilities.container.Vector2f;
import org.digitalmodular.utilities.gui.image.ImageMatrixFloat;

/**
 * Functions of the form:<br> {@link ImageMatrixFloat} = &lt;operator&gt;(ImageMatrix)<br> {@link ImageMatrixFloat} =
 * &lt;operator&gt;(ImageMatrix, scalar)<br> {@link ImageMatrixFloat} = &lt;operator&gt;(ImageMatrix, scalar, scalar)
 * <br>
 *
 * @author Mark Jeronimus
 */
// Created 2012-04-05
public class ImageMatrixUnaryFunctions {
	public static void invert(ImageMatrixFloat img) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 2
		int     endX = img.endX; // 1

		for (int z = 0; z < img.numComponents; z++) {
			for (int y = img.border; y < img.endY; y++) {
				row = img.matrix[z][y];
				for (x = img.border; x < endX; x++) {
					row[x] = -row[x];
				}
			}
		}
	}

	public static void negative(ImageMatrixFloat img) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 2
		int     endX = img.endX; // 1

		for (int z = 0; z < img.numComponents; z++) {
			for (int y = img.border; y < img.endY; y++) {
				row = img.matrix[z][y];
				for (x = img.border; x < endX; x++) {
					row[x] = 1 - row[x];
				}
			}
		}
	}

	public static void clamp(ImageMatrixFloat img) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 2
		int     endX = img.endX; // 1

		for (int z = 0; z < img.numComponents; z++) {
			for (int y = img.border; y < img.endY; y++) {
				row = img.matrix[z][y];
				for (x = img.border; x < endX; x++) {
					if (row[x] < 0) {
						row[x] = 0;
					} else if (row[x] >= 1) {
						row[x] = 1;
					}
				}
			}
		}
	}

	public static void clamp(ImageMatrixFloat img, float black, float white) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 2
		int     endX = img.endX; // 1

		for (int z = 0; z < img.numComponents; z++) {
			for (int y = img.border; y < img.endY; y++) {
				row = img.matrix[z][y];
				for (x = img.border; x < endX; x++) {
					if (row[x] < black) {
						row[x] = black;
					} else if (row[x] >= white) {
						row[x] = white;
					}
				}
			}
		}
	}

	public static void clampLower(ImageMatrixFloat img, float black) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 2
		int     endX = img.endX; // 1

		for (int z = 0; z < img.numComponents; z++) {
			for (int y = img.border; y < img.endY; y++) {
				row = img.matrix[z][y];
				for (x = img.border; x < endX; x++) {
					if (row[x] < black) {
						row[x] = black;
					}
				}
			}
		}
	}

	public static void clampUpper(ImageMatrixFloat img, float white) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 2
		int     endX = img.endX; // 1

		for (int z = 0; z < img.numComponents; z++) {
			for (int y = img.border; y < img.endY; y++) {
				row = img.matrix[z][y];
				for (x = img.border; x < endX; x++) {
					if (row[x] >= white) {
						row[x] = white;
					}
				}
			}
		}
	}

	public static void normalize(ImageMatrixFloat img) {
		float mul = img.findMax();

		mul = mul == 0 ? Float.NaN : 1 / mul;
		img.mulUniform(mul);
	}

	public static void stretchHistogram(ImageMatrixFloat img) {
		Vector2f biasAmp = img.findBiasAmplitude();

		float mul = biasAmp.y == 0 ? Float.NaN : 0.5f / biasAmp.y;
		float add = biasAmp.y - biasAmp.x;
		img.addMulUniform(add, mul);
	}

	public static void abs(ImageMatrixFloat img) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 3
		int     endX = img.endX; // 1

		for (int z = 0; z < img.numComponents; z++) {
			for (int y = img.border; y < img.endY; y++) {
				row = img.matrix[z][y];
				for (x = img.border; x < endX; x++) {
					if (row[x] < 0) {
						row[x] = -row[x];
					}
				}
			}
		}
	}

	public static void round(ImageMatrixFloat img) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 3
		int     endX = img.endX; // 1

		for (int z = 0; z < img.numComponents; z++) {
			for (int y = img.border; y < img.endY; y++) {
				row = img.matrix[z][y];
				for (x = img.border; x < endX; x++) {
					row[x] = (float)Math.floor(row[x] + 0.5);
				}
			}
		}
	}

	public static void floor(ImageMatrixFloat img) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 3
		int     endX = img.endX; // 1

		for (int z = 0; z < img.numComponents; z++) {
			for (int y = img.border; y < img.endY; y++) {
				row = img.matrix[z][y];
				for (x = img.border; x < endX; x++) {
					row[x] = (float)Math.floor(row[x]);
				}
			}
		}
	}

	public static void ceil(ImageMatrixFloat img) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 3
		int     endX = img.endX; // 1

		for (int z = 0; z < img.numComponents; z++) {
			for (int y = img.border; y < img.endY; y++) {
				row = img.matrix[z][y];
				for (x = img.border; x < endX; x++) {
					row[x] = (float)Math.ceil(row[x]);
				}
			}
		}
	}

	public static void trunc(ImageMatrixFloat img) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 3
		int     endX = img.endX; // 1

		for (int z = 0; z < img.numComponents; z++) {
			for (int y = img.border; y < img.endY; y++) {
				row = img.matrix[z][y];
				for (x = img.border; x < endX; x++) {
					row[x] = (int)row[x];
				}
			}
		}
	}

	public static void recip(ImageMatrixFloat img) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 2
		int     endX = img.endX; // 1

		for (int z = 0; z < img.numComponents; z++) {
			for (int y = img.border; y < img.endY; y++) {
				row = img.matrix[z][y];
				for (x = img.border; x < endX; x++) {
					row[x] = 1 / row[x];
				}
			}
		}
	}

	public static void sqr(ImageMatrixFloat img) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 2
		int     endX = img.endX; // 1

		for (int z = 0; z < img.numComponents; z++) {
			for (int y = img.border; y < img.endY; y++) {
				row = img.matrix[z][y];
				for (x = img.border; x < endX; x++) {
					row[x] *= row[x];
				}
			}
		}
	}

	public static void sqrt(ImageMatrixFloat img) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 2
		int     endX = img.endX; // 1

		for (int z = 0; z < img.numComponents; z++) {
			for (int y = img.border; y < img.endY; y++) {
				row = img.matrix[z][y];
				for (x = img.border; x < endX; x++) {
					row[x] = (float)Math.sqrt(row[x]);
				}
			}
		}
	}

	public static void cube(ImageMatrixFloat img) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 3
		int     endX = img.endX; // 1

		for (int z = 0; z < img.numComponents; z++) {
			for (int y = img.border; y < img.endY; y++) {
				row = img.matrix[z][y];
				for (x = img.border; x < endX; x++) {
					row[x] *= row[x] * row[x];
				}
			}
		}
	}

	public static void cbrt(ImageMatrixFloat img) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 2
		int     endX = img.endX; // 1

		for (int z = 0; z < img.numComponents; z++) {
			for (int y = img.border; y < img.endY; y++) {
				row = img.matrix[z][y];
				for (x = img.border; x < endX; x++) {
					if (row[x] < 0) {
						row[x] = (float)Math.cbrt(row[x]);
					}
				}
			}
		}
	}

	public static void exp(ImageMatrixFloat img) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 2
		int     endX = img.endX; // 1

		for (int z = 0; z < img.numComponents; z++) {
			for (int y = img.border; y < img.endY; y++) {
				row = img.matrix[z][y];
				for (x = img.border; x < endX; x++) {
					row[x] = (float)Math.exp(row[x]);
				}
			}
		}
	}

	public static void log(ImageMatrixFloat img) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 2
		int     endX = img.endX; // 1

		for (int z = 0; z < img.numComponents; z++) {
			for (int y = img.border; y < img.endY; y++) {
				row = img.matrix[z][y];
				for (x = img.border; x < endX; x++) {
					row[x] = (float)Math.log(row[x]);
				}
			}
		}
	}

	public static void pow(ImageMatrixFloat img, float power) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 2
		int     endX = img.endX; // 1

		for (int z = 0; z < img.numComponents; z++) {
			for (int y = img.border; y < img.endY; y++) {
				row = img.matrix[z][y];
				for (x = img.border; x < endX; x++) {
					row[x] = (float)Math.pow(row[x], power);
				}
			}
		}
	}

	public static void powR(ImageMatrixFloat img, float base) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 2
		int     endX = img.endX; // 1

		for (int z = 0; z < img.numComponents; z++) {
			for (int y = img.border; y < img.endY; y++) {
				row = img.matrix[z][y];
				for (x = img.border; x < endX; x++) {
					row[x] = (float)Math.pow(base, row[x]);
				}
			}
		}
	}

	public static void sin(ImageMatrixFloat img) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 2
		int     endX = img.endX; // 1

		for (int z = 0; z < img.numComponents; z++) {
			for (int y = img.border; y < img.endY; y++) {
				row = img.matrix[z][y];
				for (x = img.border; x < endX; x++) {
					row[x] = (float)Math.sin(row[x]);
				}
			}
		}
	}

	public static void cos(ImageMatrixFloat img) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 2
		int     endX = img.endX; // 1

		for (int z = 0; z < img.numComponents; z++) {
			for (int y = img.border; y < img.endY; y++) {
				row = img.matrix[z][y];
				for (x = img.border; x < endX; x++) {
					row[x] = (float)Math.cos(row[x]);
				}
			}
		}
	}

	public static void tan(ImageMatrixFloat img) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 2
		int     endX = img.endX; // 1

		for (int z = 0; z < img.numComponents; z++) {
			for (int y = img.border; y < img.endY; y++) {
				row = img.matrix[z][y];
				for (x = img.border; x < endX; x++) {
					row[x] = (float)Math.tan(row[x]);
				}
			}
		}
	}

	public static void atan(ImageMatrixFloat img) {
		// Inner loop
		int     x; // 4 (3~5)
		float[] row; // 2
		int     endX = img.endX; // 1

		for (int z = 0; z < img.numComponents; z++) {
			for (int y = img.border; y < img.endY; y++) {
				row = img.matrix[z][y];
				for (x = img.border; x < endX; x++) {
					row[x] = (float)Math.atan(row[x]);
				}
			}
		}
	}
}
