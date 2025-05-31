package nl.airsupplies.utilities.broken;

import java.util.concurrent.ThreadLocalRandom;

import nl.airsupplies.utilities.graphics.image.ImageMatrixFloat;
import nl.airsupplies.utilities.graphics.image.generator.ImageGenerator;

/**
 * @author Mark Jeronimus
 */
// Created 2012-04-04
public class PerlinNoiseGenerator extends ImageGenerator {
	public float amount;
	public float scale;

	public PerlinNoiseGenerator(float amount, float scale) {
		this.amount = amount;
		this.scale  = scale;
	}

	/**
	 * Generates a pseudorandom number that is directly dependent upon a given 96-bit input, similar to a hash
	 * function. If the
	 * input is less than 96 bits (for example, a single <code>int</code>) then duplicate bits or
	 * <code>int</code>s rather than
	 * setting bits to a constant value.
	 * <p>
	 * Note: The order of the three parameters is irrelevant and swapping parameters does not improve or degrade
	 * randomness in any
	 * way.
	 * <p>
	 * node 2: When duplicating bits, duplicate them along multiples of 32 bits, otherwise cancellation effects
	 * might occur. For
	 * example, setting <code>b = a &lt;&lt; 11</code> cancels out at least 18 bits.
	 *
	 * @deprecated Fail: only returns even numbers for small int
	 */
	@Deprecated
	public static int getDirect(int a, int b, int c) {
		a += a ^ (b << 3) + (c >>> 29) + (a << 14);
		b += b ^ (c >>> 8) + (a << 16) + (b >> 11);
		c += c ^ (a << 11) + (b << 17) + (c << 10);

		int tmp = 0x811C9DC5;
		tmp = (tmp ^ c) * 0x243F6A88; // Pi
		tmp = (tmp ^ a) * 0xB7E15162; // E
		tmp = (tmp ^ b) * 0x9E3779B9; // Phi

		return tmp;
	}

	@Override
	public ImageMatrixFloat generate() {
		int cols = (int)Math.ceil(image.numColumns * scale + 1.5f);
		int rows = (int)Math.ceil(image.numRows * scale + 1.5f);

		float[][][] gradients = new float[rows][cols][2];
		int         dx        = ThreadLocalRandom.current().nextInt();
		int         dy        = ThreadLocalRandom.current().nextInt();
		int         re        = ThreadLocalRandom.current().nextInt();
		int         im        = re + (ThreadLocalRandom.current().nextInt() | 1);
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < cols; x++) {
				float u = getDirect(dy + y, dx + x, re);
				float v = getDirect(dy + y, dx + x, im);
				float d = 1f / (float)Math.sqrt(u * u + v * v);
				gradients[y][x][0] = u * d;
				gradients[y][x][1] = v * d;
			}
		}

		// Inner loop
		float   fu0; // 7
		int     u0; // 5
		float   a; // 5
		float   c; // 4
		float   u; // 3
		int     u1; // 3
		float   fu1; // 3
		float   sx; // 3
		float[] g_b00; // 3
		float[] g_b01; // 3
		float[] g_b10; // 3
		float[] g_b11; // 3
		float   b; // 2
		float   d; // 2
		float   scale  = this.scale; // 1
		float   amount = this.amount; // 1
		int     endX   = image.endX; // 1

		for (int y = image.border; y < image.endY; y++) {
			float     v    = (y + 0.5f) * scale;
			int       v0   = (int)v;
			int       v1   = v0 + 1;
			float     fv0  = v - v0;
			float     fv1  = fv0 - 1;
			float     sy   = ((6 * fv0 - 15) * fv0 + 10) * fv0 * fv0 * fv0;
			float[][] g_b0 = gradients[v0];
			float[][] g_b1 = gradients[v1];

			float[] row = image.matrix[0][y];
			for (int x = image.border; x < endX; x++) {
				u   = (x + 0.5f) * scale;
				u0  = (int)u;
				u1  = u0 + 1;
				fu0 = u - u0;
				fu1 = fu0 - 1;
				sx  = ((6 * fu0 - 15) * fu0 + 10) * fu0 * fu0 * fu0;

				g_b00 = g_b0[u0];
				g_b01 = g_b0[u1];
				g_b10 = g_b1[u0];
				g_b11 = g_b1[u1];

				a = fu0 * g_b00[0] + fv0 * g_b00[1];
				b = fu1 * g_b01[0] + fv0 * g_b01[1];
				c = fu0 * g_b10[0] + fv1 * g_b10[1];
				d = fu1 * g_b11[0] + fv1 * g_b11[1];

				a += sx * (b - a);
				c += sx * (d - c);

				row[x] = (a + sy * (c - a)) * amount;
			}
		}

		return image;
	}
}
