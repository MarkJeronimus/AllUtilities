package nl.airsupplies.utilities.complex;

import java.io.Serializable;

// http://www.mathworks.com/company/newsletters/news_notes/clevescorner/sum98cleve.html
// http://mpmath.googlecode.com/svn/gallery/gallery.html
// http://mathworld.wolfram.com/topics/TrigonometricFunctions.html
// http://mathworld.wolfram.com/topics/HyperbolicFunctions.html
// http://mathworld.wolfram.com/topics/MiscellaneousSpecialFunctions.html
// http://mathworld.wolfram.com/EntireFunction.html
// http://en.wikipedia.org/wiki/Trigonometric_function
// http://en.wikipedia.org/wiki/Inverse_trigonometric_functions
// http://en.wikipedia.org/wiki/Hyperbolic_function
// http://en.wikipedia.org/wiki/Inverse_hyperbolic_function

/**
 * @author Mark Jeronimus
 */
// Created 2005-05-07
// Updated 2009-05-08 Added lots of extra complex functions
// Setter Functions
// Query Functions
// Unary Functions
// Binary Functions
// Ternary Functions
// Testing Functions
public class Complex2d implements Serializable {
	public double real;
	public double imag;

	public Complex2d() {
		real = 0;
		imag = 0;
	}

	public Complex2d(double d) {
		real = d;
		imag = 0;
	}

	public Complex2d(double real, double imag) {
		this.real = real;
		this.imag = imag;
	}

	public Complex2d(Complex2d z) {
		real = z.real;
		imag = z.imag;
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Setter Functions
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void set(double zre) {
		real = zre;
		imag = 0;
	}

	public void set(double real, double imag) {
		this.real = real;
		this.imag = imag;
	}

	public void set(Complex2d z) {
		real = z.real;
		imag = z.imag;
	}

	public void cis(double theta) {
		// cis = e^(i*theta)
		real = Math.cos(theta);
		imag = Math.sin(theta);
	}

	public void setPolar(double r, double theta) {
		// setPolar = r*cis(theta) = r*e^(i*theta)
		real = r * Math.cos(theta);
		imag = r * Math.sin(theta);
	}

	public void setPolar(Complex2d value) {
		// setPolar = value.real*cis(value.imag) = value.real*e^(value.imag*i)
		imag = value.real * Math.cos(value.imag);
		real = value.real * Math.sin(value.imag);
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Query Functions
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public double real() {
		return real;
	}

	public double imag() {
		return imag;
	}

	public double magnSquared() {
		// = |z|^2 = z*conjugate(z)
		// (and sometimes called cabs(z), the 'complex absolute' function)
		return real * real + imag * imag;
	}

	public double magn() {
		// = |z| = modulus(z) = sqrt(z*conjugate(z))
		return Math.sqrt(magnSquared());
	}

	/**
	 * Returns the angle from the positive real axis in counterclockwise direction, in the range of [-pi, pi].
	 * <p>
	 * The reason why both -pi and pi are possible results is because of positive and negative zero imag values.
	 */
	public double arg() {
		return Math.atan2(imag, real);
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Unary Functions
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Complex2d negate() {
		return new Complex2d(-real, -imag);
	}

	public void negateSelf() {
		real = -real;
		imag = -imag;
	}

	public Complex2d conjugate() {
		return new Complex2d(real, -imag);
	}

	public void conjugateSelf() {
		imag = -imag;
	}

	public void flipSelf() {
		double newImag = real;
		real = imag;
		imag = newImag;
	}

	public void flipRealSelf() {
		real = -real;
	}

	public void rotCWSelf() {
		double newImag = -real;
		real = imag;
		imag = newImag;
	}

	public void rotCCWSelf() {
		double temp = real;
		real = -imag;
		imag = temp;
	}

	public void normalizeSelf() {
		double magn = magn();
		real /= magn;
		imag /= magn;
	}

	public void sqrSelf() {
		double newReal = real * real - imag * imag;
		double newImag = 2 * real * imag;
		this.real = newReal;
		this.imag = newImag;
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Binary Functions
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Complex2d add(double d) {
		return new Complex2d(real + d, imag);
	}

	public void addSelf(double d) {
		real += d;
	}

	public Complex2d add(double real, double imag) {
		return new Complex2d(this.real + real, this.imag + imag);
	}

	public void addSelf(double real, double imag) {
		this.real += real;
		this.imag += imag;
	}

	public Complex2d add(Complex2d value) {
		return new Complex2d(real + value.real, imag + value.imag);
	}

	public void addSelf(Complex2d value) {
		real += value.real;
		imag += value.imag;
	}

	public Complex2d sub(double d) {
		return new Complex2d(real - d, imag);
	}

	public void subSelf(double d) {
		real -= d;
	}

	public Complex2d sub(double real, double imag) {
		return new Complex2d(this.real - real, this.imag - imag);
	}

	public void subSelf(double real, double imag) {
		this.real -= real;
		this.imag -= imag;
	}

	public Complex2d sub(Complex2d value) {
		return new Complex2d(real - value.real, imag - value.imag);
	}

	public void subSelf(Complex2d value) {
		real -= value.real;
		imag -= value.imag;
	}

	public Complex2d subR(double d) {
		return new Complex2d(d - real, imag);
	}

	public void subRSelf(double d) {
		real = d - real;
	}

	public Complex2d subR(double real, double imag) {
		return new Complex2d(real - this.real, imag - this.imag);
	}

	public void subRSelf(double real, double imag) {
		this.real = real - this.real;
		this.imag = imag - this.imag;
	}

	public Complex2d subR(Complex2d value) {
		return new Complex2d(value.real - real, value.imag - imag);
	}

	public void subRSelf(Complex2d value) {
		real = value.real - real;
		imag = value.imag - imag;
	}

	public Complex2d mul(double d) {
		return new Complex2d(real * d, imag * d);
	}

	public void mulSelf(double d) {
		real *= d;
		imag *= d;
	}

	public Complex2d mul(double real, double imag) {
		return new Complex2d(this.real * real - this.imag * imag,
		                     this.imag * real + this.real * imag);
	}

	public void mulSelf(double real, double imag) {
		double newReal = this.real * real - this.imag * imag;
		double newImag = this.imag * real + this.real * imag;
		this.real = newReal;
		this.imag = newImag;
	}

	public Complex2d mul(Complex2d value) {
		return new Complex2d(real * value.real - imag * value.imag, imag * value.real + real * value.imag);
	}

	public void mulSelf(Complex2d value) {
		double newReal = real * value.real - imag * value.imag;
		double newImag = imag * value.real + real * value.imag;
		this.real = newReal;
		this.imag = newImag;
	}

	public Complex2d mulComponent(double real, double imag) {
		return new Complex2d(this.real * real, this.imag * imag);
	}

	public void mulComponentSelf(double real, double imag) {
		this.real *= real;
		this.imag *= imag;
	}

	public Complex2d mulComponent(Complex2d value) {
		return new Complex2d(real * value.real, imag * value.imag);
	}

	public void mulComponentSelf(Complex2d value) {
		real += value.real;
		imag += value.imag;
	}

	public Complex2d div(double value) {
		return new Complex2d(real / value, imag / value);
	}

	public void divSelf(double value) {
		real /= value;
		imag /= value;
	}

	public Complex2d div(double real, double imag) {
		// a/b = a*recip(b)
		double magnSquared = real * real + imag * imag;
		return new Complex2d((this.real * real + this.imag * imag) / magnSquared,
		                     (this.imag * real - this.real * imag) / magnSquared);
	}

	public void divSelf(double real, double imag) {
		// a/b = a*recip(b)
		double magnSquared = real * real + imag * imag;
		double newReal     = (this.real * real + this.imag * imag) / magnSquared;
		double newImag     = (this.imag * real - this.real * imag) / magnSquared;
		this.real = newReal;
		this.imag = newImag;
	}

	public Complex2d div(Complex2d value) {
		// a/b = a*recip(b)
		double magnSquared = value.real * value.real + value.imag * value.imag;
		return new Complex2d((real * value.real + imag * value.imag) / magnSquared,
		                     (imag * value.real - real * value.imag) / magnSquared);
	}

	public void divSelf(Complex2d value) {
		// a/b = a*recip(b)
		double magnSquared = value.real * value.real + value.imag * value.imag;
		double newReal     = (real * value.real + imag * value.imag) / magnSquared;
		double newImag     = (imag * value.real - real * value.imag) / magnSquared;
		real = newReal;
		imag = newImag;
	}

	public Complex2d divComponent(double real, double imag) {
		return new Complex2d(this.real / real, this.imag / imag);
	}

	public void divComponentSelf(double real, double imag) {
		this.real /= real;
		this.imag /= imag;
	}

	public Complex2d divComponent(Complex2d value) {
		return new Complex2d(real / value.real, imag / value.imag);
	}

	public void divComponentSelf(Complex2d value) {
		real /= value.real;
		imag /= value.imag;
	}

	public Complex2d divR(double value) {
		return new Complex2d(value / real, value / imag);
	}

	public void divRSelf(double value) {
		real = value / real;
		imag = value / imag;
	}

	public Complex2d divR(double real, double imag) {
		// a/b = a*recip(b)
		double magnSquared = this.real * this.real + this.imag * this.imag;
		return new Complex2d((real * this.real + imag * this.imag) / magnSquared,
		                     (imag * this.real - real * this.imag) / magnSquared);
	}

	public void divRSelf(double real, double imag) {
		// a/b = a*recip(b)
		double magnSquared = this.real * this.real + this.imag * this.imag;
		double newReal     = (real * this.real + imag * this.imag) / magnSquared;
		double newImag     = (imag * this.real - real * this.imag) / magnSquared;
		this.real = newReal;
		this.imag = newImag;
	}

	public Complex2d divR(Complex2d value) {
		// a/b = a*recip(b)
		double magnSquared = real * real + imag * imag;
		return new Complex2d((value.real * real + value.imag * imag) / magnSquared,
		                     (value.imag * real - value.real * imag) / magnSquared);
	}

	public void divRSelf(Complex2d value) {
		// a/b = a*recip(b)
		double magnSquared = real * real + imag * imag;
		double newReal     = (value.real * real + value.imag * imag) / magnSquared;
		double newImag     = (value.imag * real - value.real * imag) / magnSquared;
		real = newReal;
		imag = newImag;
	}

	public Complex2d divRComponent(double real, double imag) {
		return new Complex2d(real / this.real, imag / this.imag);
	}

	public void divRComponentSelf(double real, double imag) {
		this.real = real / this.real;
		this.imag = imag / this.imag;
	}

	public Complex2d divRComponent(Complex2d value) {
		return new Complex2d(value.real / real, value.imag / imag);
	}

	public void divRComponentSelf(Complex2d value) {
		real = value.real / real;
		imag = value.imag / imag;
	}

	public Complex2d sqrAdd(double real, double imag) {
		// sqrAdd(a, b) = sqr(a)+b
		return new Complex2d(this.real * this.real - this.imag * this.imag + real,
		                     2 * this.real * this.imag + imag);
	}

	public void sqrAddSelf(double real, double imag) {
		// sqrAdd(a, b) = sqr(a)+b
		double newReal = this.real * this.real - this.imag * this.imag + real;
		double newImag = 2 * this.real * this.imag + imag;
		this.real = newReal;
		this.imag = newImag;
	}

	public Complex2d sqrAdd(Complex2d value) {
		// sqrAdd(a, b) = sqr(a)+b
		return new Complex2d(real * real - imag * imag + value.real, 2 * real * imag + value.imag);
	}

	public void sqrAddSelf(Complex2d value) {
		// sqrAdd(a, b) = sqr(a)+b
		double newReal = real * real - imag * imag + value.real;
		double newImag = 2 * real * imag + value.imag;
		real = newReal;
		imag = newImag;
	}

	public void distanceCartesian(Complex2d z) {
		real = Math.abs(real - z.real);
		imag = Math.abs(imag - z.imag);
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Ternary Functions
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Complex2d addScaled(Complex2d value, double scale) {
		return new Complex2d(real + value.real * scale,
		                     imag + value.imag * scale);
	}

	public void addScaledSelf(Complex2d value, double scale) {
		real += value.real * scale;
		imag += value.imag * scale;
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Testing Functions
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean magnEquals(Complex2d z) {
		return magnSquared() == z.magnSquared();
	}

	public boolean magnDiffers(Complex2d z) {
		return magnSquared() != z.magnSquared();
	}

	@Override
	@SuppressWarnings("NonFinalFieldReferenceInEquals")
	public boolean equals(Object other) {
		if (other instanceof Complex2d) {
			Complex2d c = (Complex2d)other;
			if (real == c.real && imag == c.imag) {
				return true;
			}
		}

		return false;
	}

	@Override
	@SuppressWarnings("NonFinalFieldReferencedInHashCode")
	public int hashCode() {
		int hash = 0x811C9DC5;
		hash ^= Double.hashCode(real);
		hash *= 0x01000193;
		hash ^= Double.hashCode(imag);
		hash *= 0x01000193;
		return hash;
	}

	@Override
	public String toString() {
		return real + "/" + imag;
	}
}
