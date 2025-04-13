package org.digitalmodular.utilities.math;

/**
 * Implementation of Jenkins' Super Fast pseudo random number generator. This is public domain.
 * <p>
 * "jsf was designed by Robert Jenkins, and he has released it to the public domain. The implementation is mostly
 * his code with a little of my own" -Chris Doty-Humphrey
 *
 * @author Mark Jeronimus
 */
// 2012-04-04
public final class FastRandom {
	private int a;
	private int b;
	private int c;

	public FastRandom(long seed) {
		seed(seed);
	}

	public void seed(long s) {
		a = (int)s;
		b = (int)(s >>> 32);
		c = 0;
		for (int i = 0; i < 9; i++) {
			nextFloat();
		}
	}

	public int nextInt() {
		int tmp = a + b + c;
		c++;
		a = b ^ b >>> 5;
		b = (b << 12 | b >>> 20) + tmp;
		return tmp;
	}

	/**
	 * Generates a uniform pseudo-random float value in the range [-0.5, 0.5).
	 */
	public float nextFloat() {
		int tmp = a + b + c;
		c++;
		a = b ^ b >>> 5;
		b = (b << 12 | b >>> 20) + tmp;
		return Float.intBitsToFloat(0x3F800000 | tmp >>> 9) - 1.5f;
	}

	/**
	 * Generates a non-uniform pseudo-random float value in the range [-3, 3) with center 0.0 and standard
	 * deviation
	 * 1.0. This is NOT a gaussian distribution but a very fast approximation.
	 */
	public float nextBell() {
		int tmp1 = a + b + c;
		c++;
		a = b ^ b >>> 5;
		b = (b << 12 | b >>> 20) + tmp1;
		int tmp2 = a + b + c;
		c++;
		a = b ^ b >>> 5;
		b = (b << 12 | b >>> 20) + tmp2;
		int tmp3 = a + b + c;
		c++;
		a = b ^ b >>> 5;
		b = (b << 12 | b >>> 20) + tmp3;
		return Float.intBitsToFloat(0x40000000 | tmp1 >>> 9) +
		       Float.intBitsToFloat(0x40000000 | tmp2 >>> 9) +
		       Float.intBitsToFloat(0x40000000 | tmp3 >>> 9) - 9.0f;
	}
}
