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
package org.digitalmodular.utilities.container;

import java.io.Serializable;

import static org.digitalmodular.utilities.constant.NumberConstants.LOG10;
import static org.digitalmodular.utilities.constant.NumberConstants.LOG2;
import static org.digitalmodular.utilities.constant.NumberConstants.Q05LOG2;
import static org.digitalmodular.utilities.constant.NumberConstants.Q1LOG10;
import static org.digitalmodular.utilities.constant.NumberConstants.Q1LOG2;
import static org.digitalmodular.utilities.constant.NumberConstants.Q1_3;
import static org.digitalmodular.utilities.constant.NumberConstants.Q1_6;
import static org.digitalmodular.utilities.constant.NumberConstants.TAU;
import static org.digitalmodular.utilities.constant.NumberConstants.TAU05;

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
// Unary Query Functions
// Binary Query Functions
// Unary Test Functions
// Binary Test Functions
// Unary Linear Functions
// Binary Linear Functions
// Ternary Linear Functions
// Unary Exponential Functions
// Binary Exponential Functions
// Unary Trigonometric Functions
// Unary Hyperbolic Functions
// Unary Other Functions
// Binary Other Functions
public class Complex2d implements Serializable {
	public double re;
	public double im;

	public Complex2d() {
		re = 0;
		im = 0;
	}

	public Complex2d(double zre) {
		re = zre;
		im = 0;
	}

	public Complex2d(double re, double im) {
		this.re = re;
		this.im = im;
	}

	public Complex2d(Complex2d z) {
		re = z.re;
		im = z.im;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Setter Functions
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void set(double zre) {
		re = zre;
		im = 0;
	}

	public void set(double zre, double zim) {
		re = zre;
		im = zim;
	}

	public void set(Complex2d z) {
		re = z.re;
		im = z.im;
	}

	public void cis(double arg) {
		// this = cis(φ) = exp(i*φ) = e^(i*φ)
		im = Math.cos(arg);
		re = Math.sin(arg);
	}

	public void setPolar(double abs, double arg) {
		// this = r*cis(φ) = r*exp(i*φ) = r*e^(i*φ)
		im = abs * Math.cos(arg);
		re = abs * Math.sin(arg);
	}

	public void setPolar(Complex2d z) {
		// this = r*cis(φ) = r*exp(i*φ) = r*e^(i*φ)
		im = z.re * Math.cos(z.im);
		re = z.re * Math.sin(z.im);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Unary Query Functions
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public double real() {
		return re;
	}

	public double imag() {
		return im;
	}

	public double sumComponent() {
		return re + im;
	}

	public double diffComponent() {
		return re - im;
	}

	public double prodComponent() {
		return re * im;
	}

	public double magnSquared() {
		// = |z|^2 = z*conjugate(z)
		// (and sometimes called cabs(z), the 'complex absolute' function)
		return re * re + im * im;
	}

	public double magn() {
		// = |z| = modulus(z) = sqrt(z*conjugate(z))
		return Math.hypot(re, im);
	}

	public double manhattanNorm() {
		return Math.abs(re) + Math.abs(im);
	}

	public double harmonicNorm() {
		// harmonicDistance = 1/(1/x + 1/y) = x*y/(x+y)
		double dx = Math.abs(re);
		double dy = Math.abs(im);
		return dx * dy / (dx + dy);
	}

	public double multiplicativeNorm() {
		// multiplicativeDistance = sqrt(|x*y|)
		return Math.sqrt(Math.abs(re * im));
	}

	public double minNorm() {
		// minDistance = min(|x|,|y|)
		return Math.min(Math.abs(re), Math.abs(im));
	}

	public double maxNorm() {
		// maxDistance = max(|x|,|y|)
		return Math.max(Math.abs(re), Math.abs(im));
	}

	public double arg() {
		return Math.atan2(im, re);
	}

	public double decibel() {
		// deciBel = log10(|a|)*20
		return Math.log10(magn()) * 20;
	}

	/**
	 * rosenbrock(a, b) = (a-x)^2 + b*(y-x^2)^2 where a=1, b=100
	 */
	public double rosenbrock() {
		double ax  = 1 - re;
		double yxx = im - re * re;
		return ax * ax + 100 * yxx * yxx;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Binary Query Functions
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public double distance(Complex2d z) {
		// = magn(this - z)
		double dx = re - z.re;
		double dy = im - z.im;
		return Math.hypot(dx, dy);
	}

	public double manhattanDistance(Complex2d z) {
		// = manhattanNorm(this - z)
		double dx = Math.abs(re - z.re);
		double dy = Math.abs(im - z.im);
		return dx + dy;
	}

	public double harmonicDistance(Complex2d z) {
		// harmonicDistance = harmonicNorm(this - z) = 1/(1/x + 1/y) = x*y/(x+y)
		double dx = Math.abs(re - z.re);
		double dy = Math.abs(im - z.im);
		return dx * dy / (dx + dy);
	}

	public double multiplicativeDistance(Complex2d z) {
		// multiplicativeDistance = multiplicativeNorm(this - z)
		return Math.sqrt(Math.abs((re - z.re) * (im - z.im)));
	}

	public double minDistance(Complex2d z) {
		// minDistance = minNorm(this - z)
		double dx = Math.abs(re - z.re);
		double dy = Math.abs(im - z.im);
		return Math.min(dx, dy);
	}

	public double maxDistance(Complex2d z) {
		// minDistance = maxNorm(this - z)
		double dx = Math.abs(re - z.re);
		double dy = Math.abs(im - z.im);
		return Math.max(dx, dy);
	}

	public double dotProduct(Complex2d z) {
		return re * z.re + im * z.im;
	}

	public int compare(Complex2d z) {
		return Double.compare(magnSquared(), z.magnSquared());
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Unary Test Functions
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean isZero() {
		return re == 0 && im == 0;
	}

	public boolean isOnlyReal() {
		return im == 0;
	}

	public boolean isOnlyImaginary() {
		return re == 0;
	}

	public boolean isPositiveOne() {
		return re == 1 && im == 0;
	}

	public boolean isPositiveI() {
		return re == 0 && im == 1;
	}

	public boolean isNegativeOne() {
		return re == -1 && im == 0;
	}

	public boolean isNegativeI() {
		return re == 0 && im == -1;
	}

	public boolean isInfinite() {
		return Double.isInfinite(re) || Double.isInfinite(im);
	}

	public boolean isNaN() {
		return Double.isNaN(re) || Double.isNaN(im);
	}

	public boolean isDegenerate() {
		return Double.isInfinite(re) || Double.isNaN(re) || Double.isInfinite(im) || Double.isNaN(im);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Binary Test Functions
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public boolean equals(Complex2d z) {
		return re == z.re && im == z.im;
	}

	public boolean differs(Complex2d z) {
		return !(re == z.re) || !(im == z.im);
	}

	public boolean magnEquals(Complex2d z) {
		return re * re + im * im < z.re * z.re + z.im * z.im;
	}

	public boolean magnDiffers(Complex2d z) {
		return re * re + im * im < z.re * z.re + z.im * z.im;
	}

	public boolean magnLessThan(Complex2d z) {
		return re * re + im * im < z.re * z.re + z.im * z.im;
	}

	public boolean magnLessThanOrEqual(Complex2d z) {
		return re * re + im * im <= z.re * z.re + z.im * z.im;
	}

	public boolean magnGreaterThan(Complex2d z) {
		return re * re + im * im > z.re * z.re + z.im * z.im;
	}

	public boolean magnGreaterThanOrEqual(Complex2d z) {
		return re * re + im * im >= z.re * z.re + z.im * z.im;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Unary Linear Functions
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void increment() {
		re++;
	}

	public void decrement() {
		re++;
	}

	public void sqr() {
		double re2 = re * re - im * im;
		double im2 = 2 * re * im;
		re = re2;
		im = im2;
	}

	public void sqrComponent() {
		re *= re;
		im *= im;
	}

	public void sqrt() {
		// sqrt(a) = sqrt((|a|+a.re)/2) + sqrt((|a|-a.re)/2)*i
		double magn = Math.hypot(re, im);
		im = Math.signum(im) * Math.sqrt((magn - re) * 0.5);
		re = Math.sqrt((magn + re) * 0.5);
	}

	public void cube() {
		double xx = re * re;
		double yy = im * im;
		re *= xx - 3 * yy;
		im *= 3 * xx - yy;
	}

	public void cbrt() {
		// cbrt(a) = exp(log(a)/3)
		// Exception for exp(log(0))=0 which uses -infinite as intermediate
		if (re == 0 && im == 0)
			return;

		double a = Math.pow(re * re + im * im, Q1_6);
		double b = Math.atan2(im, re) * Q1_3;
		re = a * Math.cos(b);
		im = a * Math.sin(b);
	}

	public void recip() {
		// recip = 1/z = z.re/magnSquared(z) - z.im/magnSquared(z)*i
		double magnSquared = re * re + im * im;
		re /= magnSquared;
		im /= -magnSquared;
	}

	public void abs() {
		re = Math.abs(re);
		im = Math.abs(im);
	}

	public void sign() {
		re = Math.signum(re);
		im = Math.signum(im);
	}

	public void mirror() {
		re = -re;
	}

	public void conjugate() {
		im = -im;
	}

	public void flip() {
		double re2 = im;
		double im2 = re;
		re = re2;
		im = im2;
	}

	public void rotCCW() {
		double re2 = -im;
		double im2 = re;
		re = re2;
		im = im2;
	}

	public void negate() {
		re = -re;
		im = -im;
	}

	public void rotCW() {
		double re2 = im;
		double im2 = -re;
		re = re2;
		im = im2;
	}

	public void normalize() {
		double magn = Math.hypot(re, im);
		re /= magn;
		im /= magn;
	}

	public void trunc() {
		re = (int)re;
		im = (int)im;
	}

	public void round() {
		re = Math.rint(re);
		im = Math.rint(im);
	}

	public void west() {
		re = Math.floor(re);
		im = Math.rint(im);
	}

	public void southWest() {
		re = Math.floor(re);
		im = Math.floor(im);
	}

	public void south() {
		re = Math.rint(re);
		im = Math.floor(im);
	}

	public void southEast() {
		re = Math.ceil(re);
		im = Math.floor(im);
	}

	public void east() {
		re = Math.ceil(re);
		im = Math.rint(im);
	}

	public void northEast() {
		re = Math.ceil(re);
		im = Math.ceil(im);
	}

	public void north() {
		re = Math.rint(re);
		im = Math.ceil(im);
	}

	public void northWest() {
		re = Math.floor(re);
		im = Math.ceil(im);
	}

	public void roundLeft() {
		re = Math.floor(re);
	}

	public void roundDown() {
		im = Math.floor(im);
	}

	public void roundRight() {
		re = Math.ceil(re);
	}

	public void roundup() {
		im = Math.ceil(im);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Binary Linear Functions
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void add(double zre) {
		re += zre;
	}

	public void add(double zre, double zim) {
		re += zre;
		im += zim;
	}

	public void add(Complex2d z) {
		re += z.re;
		im += z.im;
	}

	public void sub(double zre) {
		re -= zre;
	}

	public void sub(double zre, double zim) {
		re -= zre;
		im -= zim;
	}

	public void sub(Complex2d z) {
		re -= z.re;
		im -= z.im;
	}

	public void subR(double zre) {
		re = zre - re;
		im = -im;
	}

	public void subR(double zre, double zim) {
		re = zre - re;
		im = zim - im;
	}

	public void subR(Complex2d z) {
		re = z.re - re;
		im = z.im - im;
	}

	public void mul(double zre) {
		re *= zre;
		im *= zre;
	}

	public void mul(double zre, double zim) {
		double re2 = re * zre - im * zim;
		double im2 = im * zre + re * zim;
		re = re2;
		im = im2;
	}

	public void mul(Complex2d z) {
		double re2 = re * z.re - im * z.im;
		double im2 = im * z.re + re * z.im;
		re = re2;
		im = im2;
	}

	public void mulComponent(double zre, double zim) {
		re *= zre;
		im *= zim;
	}

	public void mulComponent(Complex2d z) {
		re *= z.re;
		im *= z.im;
	}

	public void div(double zre) {
		re /= zre;
		im /= zre;
	}

	public void div(double zre, double zim) {
		// a/b = a*recip(b)
		double magnSquared = zre * zre + zim * zim;
		double re2         = (re * zre + im * zim) / magnSquared;
		double im2         = (im * zre - re * zim) / magnSquared;
		re = re2;
		im = im2;
	}

	public void div(Complex2d z) {
		// a/b = a*recip(b)
		double magnSquared = z.re * z.re + z.im * z.im;
		double re2         = (re * z.re + im * z.im) / magnSquared;
		double im2         = (im * z.re - re * z.im) / magnSquared;
		re = re2;
		im = im2;
	}

	public void divR(double zre) {
		// a/b = a*recip(b)
		double magnSquared = re * re + im * im;
		re = re * zre / magnSquared;
		im = -im * zre / magnSquared;
	}

	public void divR(double zre, double zim) {
		// a/b = a*recip(b)
		double magnSquared = re * re + im * im;
		double re2         = (zre * re + zim * im) / magnSquared;
		double im2         = (zim * re - zre * im) / magnSquared;
		re = re2;
		im = im2;
	}

	public void divR(Complex2d z) {
		// a/b = a*recip(b)
		double magnSquared = re * re + im * im;
		double re2         = (z.re * re + z.im * im) / magnSquared;
		double im2         = (z.im * re - z.re * im) / magnSquared;
		re = re2;
		im = im2;
	}

	public void divComponent(double zre, double zim) {
		re /= zre;
		im /= zim;
	}

	public void divComponent(Complex2d z) {
		re /= z.re;
		im /= z.im;
	}

	public void divComponentR(double zre, double zim) {
		re = zre / re;
		im = zim / im;
	}

	public void divComponentR(Complex2d z) {
		re = z.re / re;
		im = z.im / im;
	}

	public void remainder(Complex2d z) {
		// a%b = a - trunc(a/b)*b
		double magnSquared = z.re * z.re + z.im * z.im;
		int    re2         = (int)((re * z.re + im * z.im) / magnSquared);
		int    im2         = (int)((im * z.re - re * z.im) / magnSquared);
		re -= re2 * z.re - im2 * z.im;
		im -= im2 * z.re + re2 * z.im;
	}

	public void modulo(Complex2d z) {
		// modulo(a, b) = a - floor(a/b)*b
		double magnSquared = z.re * z.re + z.im * z.im;
		double re2         = Math.floor((re * z.re + im * z.im) / magnSquared);
		double im2         = Math.floor((im * z.re - re * z.im) / magnSquared);
		re -= re2 * z.re - im2 * z.im;
		im -= im2 * z.re + re2 * z.im;
	}

	public void moduloCentered(Complex2d z) {
		// moduloCentered(a, b) = a - round(a/b)*b
		double magnSquared = z.re * z.re + z.im * z.im;
		double c           = Math.rint((re * z.re + im * z.im) / magnSquared);
		double d           = Math.rint((im * z.re - re * z.im) / magnSquared);
		re -= c * z.re - d * z.im;
		im -= d * z.re + c * z.im;
	}

	public void sqrAdd(double zre, double zim) {
		// sqrAdd(a, b) = a^2 + b
		double re2 = re * re - im * im;
		double im2 = 2 * re * im;
		re = re2 + zre;
		im = im2 + zim;
	}

	public void sqrAdd(Complex2d z) {
		// sqrAdd(a, b) = a^2 + b
		double re2 = re * re - im * im;
		double im2 = 2 * re * im;
		re = re2 + z.re;
		im = im2 + z.im;
	}

	public void nthRoot(double zre) {
		// nthRoot(a) = exp(log(a)/b)

		// Avoid exp(log(0))=0 which uses -infinite as intermediate
		if (re == 0 && im == 0)
			return;

		double re2 = Math.pow(zre * zre + im * im, 0.5 / zre);
		double im2 = Math.atan2(im, zre) / zre;
		zre = re2 * Math.cos(im2);
		im = re2 * Math.sin(im2);
	}

	public void nthRoot(Complex2d z) {
		// nthRoot(a) = exp(log(a)/b)

		// Avoid exp(log(0))=0 which uses -infinite as intermediate
		if (re == 0 && im == 0)
			return;

		double re2         = Math.log(re * re + im * im) * 0.5;
		double im2         = Math.atan2(im, re);
		double magnSquared = z.re * z.re + z.im * z.im;
		double re3         = (re2 * z.re + im2 * z.im) / magnSquared;
		double im3         = (im2 * z.re - re2 * z.im) / magnSquared;
		double a           = Math.exp(re3);
		re = a * Math.cos(im3);
		im = a * Math.sin(im3);
	}

	public void mean(Complex2d z) {
		// = lerp(z, 0.5)
		re = (re + z.re) * 0.5;
		im = (im + z.im) * 0.5;
	}

	public void geometricMean(Complex2d z) {
		// geometricMean = sqrt(a * b)
		double a = (re * z.re - im * z.im) * 0.5;
		double b = (re * z.im + im * z.re) * 0.5;
		double c = Math.hypot(a, b);
		re = Math.sqrt(c + a);
		im = b < 0 ? -Math.sqrt(c - a) : Math.sqrt(c - a);
	}

	public void harmonicMean(Complex2d z) {
		// harmonicMean = 1/(1/a + 1/b) = a*b/(a+b)
		double a = re + z.re;
		double b = im + z.im;
		double c = a * a + b * b;
		a = a / c;
		b = b / c;
		c = re * z.re - im * z.im;
		double d = re * z.im + im * z.re;
		re = c * a + d * b;
		im = d * a - c * b;
	}

	public void quadraticMean(Complex2d z) {
		// quadraticMean = sqrt(a^2 + b^2)
		double a = re * re + z.re * z.re - (z.im * z.im + im * im);
		double b = 2 * (re * im + z.re * z.im);
		double c = Math.hypot(a, b);
		re = Math.sqrt((c + a) * 0.5);
		im = b < 0 ? -Math.sqrt((c - a) * 0.5) : Math.sqrt((c - a) * 0.5);
	}

	public void min(Complex2d z) {
		re = Math.min(re, z.re);
		im = Math.min(im, z.im);
	}

	public void max(Complex2d z) {
		re = Math.max(re, z.re);
		im = Math.max(im, z.im);
	}

	public void smallest(Complex2d z) {
		// smallest = |a|>|b|? b : a
		if (re * re + im * im > z.re * z.re + z.im * z.im) {
			re = z.re;
			im = z.im;
		}
	}

	public void largest(Complex2d z) {
		// largest = |a|<|b|? b : a
		if (re * re + im * im < z.re * z.re + z.im * z.im) {
			re = z.re;
			im = z.im;
		}
	}

	public void copyMagn(Complex2d z) {
		// copyMagn = magn(b)*e^(i*arg(a)) = magn(b) * normalize(a)

		double ratio = Math.sqrt((z.re * z.re + z.im * z.im) / (re * re + im * im));
		re /= ratio;
		im /= ratio;
	}

	public void copySign(Complex2d z) {
		// copySign = (copySign(a.re, b.re), copySign(a.im, b.im))
		re = Math.copySign(re, z.re);
		im = Math.copySign(im, z.im);
	}

	public void distanceCartesian(Complex2d z) {
		re = Math.abs(re - z.re);
		im = Math.abs(im - z.im);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Ternary Linear Functions
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void addScaled(Complex2d z, double scale) {
		re += z.re * scale;
		im += z.im * scale;
	}

	public void addScaled(Complex2d z, Complex2d scale) {
		re += z.re * scale.re - z.im * scale.im;
		im += z.im * scale.re + z.re * scale.im;
	}

	public void lerp(Complex2d z, double f) {
		// lerp = linear interpolation
		re += (z.re - re) * f;
		im += (z.im - im) * f;
	}

	public void clerp(Complex2d z, double f) {
		// clerp = circular linear interpolation
		Complex2d z2 = new Complex2d(z);
		toPolar();
		z2.toPolar();

		re += (z2.re - re) * f;

		double angleDiff = im - z2.im;
		if (angleDiff > TAU05) {
			im += (z2.im - im + TAU) * f;
		} else if (angleDiff <= -TAU05) {
			im += (z2.im - im - TAU) * f;
		} else {
			im += (z2.im - im) * f;
		}

		fromPolar();
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Unary Exponential Functions
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void log() {
		// log(a) = log(|a|) + arg(a)*i = log(magnSquared(a))/2 + arg(a)*i
		double re2 = Math.log(re * re + im * im) * 0.5;
		double im2 = Math.atan2(im, re);
		re = re2;
		im = im2;
	}

	public void log2() {
		double re2 = Math.log(re * re + im * im) * Q05LOG2;
		double im2 = Math.atan2(im, re) * Q1LOG2;
		re = re2;
		im = im2;
	}

	public void log10() {
		double re2 = Math.log10(re * re + im * im) * 0.5;
		double im2 = Math.atan2(im, re) * Q1LOG10;
		re = re2;
		im = im2;
	}

	public void log1p() {
		double re2 = re + 1;
		double re3 = Math.log(re2 * re2 + im * im) * 0.5;
		double im3 = Math.atan2(im, re2);
		re = re3;
		im = im3;
	}

	public void loglog() {
		double re2 = Math.log(re * re + im * im) * 0.5;
		double im2 = Math.atan2(im, re);
		re = Math.log(re2 * re2 + im2 * im2) * 0.5;
		im = Math.atan2(im2, re2);
	}

	public void exp() {
		double re2 = Math.exp(re);
		re = re2 * Math.cos(im);
		im = re2 * Math.sin(im);
	}

	public void exp2() {
		double re2 = Math.exp(re * LOG2);
		double im2 = im * LOG2;
		re = re2 * Math.cos(im2);
		im = re2 * Math.sin(im2);
	}

	public void exp10() {
		double re2 = Math.exp(re * LOG10);
		double im2 = im * LOG10;
		re = re2 * Math.cos(im2);
		im = re2 * Math.sin(im2);
	}

	public void exp1n() {
		double re2 = Math.exp(re);
		re = re2 * Math.cos(im) - 1;
		im = re2 * Math.sin(im);
	}

	public void expexp() {
		double re2 = Math.exp(re);
		double re3 = re2 * Math.cos(im);
		double im3 = re2 * Math.sin(im);
		double re4 = Math.exp(re3);
		re = re4 * Math.cos(im3);
		im = re4 * Math.sin(im3);
	}

	public void expRamp() {
		// expRamp = 1 - exp(a)
		double re2 = Math.exp(re);
		re = 1 - re2 * Math.cos(im);
		im = 0 - re2 * Math.sin(im);
	}

	public void toPolar() {
		double re2 = Math.hypot(re, im);
		double im2 = Math.atan2(im, re);
		re = re2;
		im = im2;
	}

	public void fromPolar() {
		double re2 = re * Math.cos(im);
		double im2 = re * Math.sin(im);
		re = re2;
		im = im2;
	}

// public void normalDistribution() {
// // normalDistribution = exp(a^2 * -0.5)
// double re2 = Math.exp((im * im - re * re) * 0.5);
// double im2 = -re * im;
// re = re2 * Math.cos(im2);
// im = re2 * Math.sin(im2);
// }
//
// public void logNormalDistribution() {
// // logNormalDistribution = exp(log(a)^2 * -0.5)
// double re2 = Math.log(re * re + im * im) * 0.5;
// double im2 = Math.atan2(im, re);
// double re3 = Math.exp((im2 * im2 - re2 * re2) * 0.5);
// double im3 = re2 * -im2;
// re = re3 * Math.cos(im3);
// im = re3 * Math.sin(im3);
// }
//
// public void tetr2() {
// // tetr2(a) = a^^2 (Knuth's arrow notation) = a^a
// // Avoid exp(log(0))=0 which uses -infinite as intermediate
// if (re == 0 && im == 0) return;
//
// double re2 = Math.log(re * re + im * im) * 0.5;
// double im2 = Math.atan2(im, re);
// double re3 = Math.exp(re2 * re - im2 * im);
// double im3 = re2 * im + im2 * re;
// re = re3 * Math.cos(im3);
// im = re3 * Math.sin(im3);
// }
//
// public void tetr3() {
// // tetr3(a) = a^^3 (Knuth's arrow notation) = a^(a^a)
// // Avoid exp(log(0))=0 which uses -infinite as intermediate
// if (re == 0 && im == 0) return;
//
// double re2 = Math.log(re * re + im * im) * 0.5;
// double im2 = Math.atan2(im, re);
// double re3 = Math.exp(re2 * re - im2 * im);
// double im3 = re2 * im + im2 * re;
// double re4 = re3 * Math.cos(im3);
// double im4 = re3 * Math.sin(im3);
// double re5 = Math.exp(re2 * re4 - im2 * im4);
// double im5 = re2 * im4 + im2 * re4;
// re = re5 * Math.cos(im5);
// im = re5 * Math.sin(im5);
// }

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Binary Exponential Functions
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void pow(double zre) {
		// Avoid exp(log(0))=0 which uses -infinite as intermediate
		if (re == 0 && im == 0)
			return;

		double re2 = Math.log(re * re + im * im) * 0.5;
		double im2 = Math.atan2(im, re);
		double re3 = Math.exp(re2 * zre);
		double im3 = im2 * zre;
		re = re3 * Math.cos(im3);
		im = re3 * Math.sin(im3);
	}

	public void pow(Complex2d z) {
		// Avoid exp(log(0))=0 which uses -infinite as intermediate
		if (re == 0 && im == 0)
			return;

		double re2 = Math.log(re * re + im * im) * 0.5;
		double im2 = Math.atan2(im, re);
		double re3 = Math.exp(re2 * z.re - im2 * z.im);
		double im3 = im2 * z.re + re2 * z.im;
		re = re3 * Math.cos(im3);
		im = re3 * Math.sin(im3);
	}

	public void logBase(Complex2d z) {
		// logBase = log(b) / log(a)
		double re2         = Math.log(z.re * z.re + z.im * z.im) * 0.5;
		double im2         = Math.atan2(z.im, z.re);
		double re3         = Math.log(re * re + im * im) * 0.5;
		double im3         = Math.atan2(im, re);
		double magnSquared = re2 * re2 + im2 * im2;
		double re4         = re2 / magnSquared;
		double im4         = im2 / magnSquared;
		re = re3 * re4 + im3 * im4;
		im = im3 * re4 - re3 * im4;
	}

	public void expRamp(Complex2d z) {
		// expRamp = 1 - exp(a*b)
		double re2 = Math.exp(re * z.re - im * z.im);
		double im2 = re * z.im + im * z.re;
		re = 1 - re2 * Math.cos(im2);
		im = 0 - re2 * Math.sin(im2);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Unary Trigonometric Functions
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void sin() {
		// sin = sin(real(a))*cosh(imag(a)) + cos(real(a))*sinh(imag(a)) * i
		// sin = (exp(a*i) - exp(a*-i)) / (2*i)
		double a = Math.exp(im) * 0.5;
		double b = Math.exp(-im) * 0.5;
		im = Math.cos(re) * (a - b);
		re = Math.sin(re) * (a + b);
	}

	public void cos() {
		// cos = cos(real(a))*cosh(imag(a)) - sin(real(a))*sinh(imag(a)) * i
		// cos = (exp(a*i) + exp(a*-i)) / 2
		double a = Math.exp(-im) * 0.5;
		double b = Math.exp(im) * 0.5;
		im = Math.sin(re) * (a - b);
		re = Math.cos(re) * (a + b);
	}

	public void tan() {
		// tan = (exp(2*a*i) - 1) / (i * (exp(2*a*i) + 1))
		double a = re + re;
		double b = im + im;
		double c = Math.exp(b);
		double e = Math.exp(-b);
		double d = Math.cos(a) + (c + e) * 0.5;
		re = Math.sin(a) / d;
		im = (c - e) * 0.5 / d;
	}

// /** cotangent(a) = (exp(2*a*i) + 1) / (exp(2*a*i) - 1) * i */
// public void cot() {
// double a = re + re;
// double b = im + im;
// double c = Math.exp(b) * 0.5;
// b = Math.exp(-b) * 0.5;
// double d = b + c - Math.cos(a);
// re = Math.sin(a) / d;
// im = (b - c) / d;
// }
//
// /** secant(a) = 2 / (exp(a*i) + exp(-a*i)) */
// public void sec() {
// // return this.mul(0, 1).exp().add(this.mul(0, -1).exp()).divR(2);
// double a = Math.exp(im);
// double b = Math.exp(-im);
// double c = (a + b) * Math.cos(re);
// a = (a - b) * Math.sin(re);
// b = (c * c + a * a) * 0.5;
// re = c / b;
// im = a / b;
// }
//
// /** cosecant(a) = 2*i / (exp(a*i) - exp(-a*i)) */
// public void csc() {
// // return this.rotCCW().exp().sub(this.rotCW().exp()).divR(0, 2);
// double a = Math.exp(-im);
// double b = Math.exp(im);
// double c = (a - b) * Math.cos(re);
// a = (a + b) * Math.sin(re);
// b = (c * c + a * a) * 0.5;
// re = a / b;
// im = c / b;
// }

	public void asin() {
		// asin = log(sqrt(1-a^2) + a*i) * -i
		double re2  = 1 - re * re + im * im;
		double im2  = -2 * re * im;
		double magn = Math.hypot(re2, im2);
		double im3  = re + Math.signum(im2) * Math.sqrt((magn - re2) * 0.5);
		double re3  = Math.sqrt((magn + re2) * 0.5) - im;
		re = Math.atan2(im3, re3);
		im = Math.log(re3 * re3 + im3 * im3) * -0.5;
	}

	public void acos() {
		// acos = log(sqrt(1-a^2)*i + a) * -i
		double re2  = 1 - re * re + im * im;
		double im2  = -2 * re * im;
		double magn = Math.hypot(re2, im2);
		double im3  = re - Math.signum(im2) * Math.sqrt((magn - re2) * 0.5);
		double re3  = Math.sqrt((magn + re2) * 0.5) + im;
		re = Math.atan2(re3, im3);
		im = Math.log(re3 * re3 + im3 * im3) * -0.5;
	}

	public void atan() {
		// atan = (log(1-a*i) - log(1+a*i)) / 2 * i.
		// atan = log((1-a*i) / (1+a*i)) / 2 * i
		double re2         = 1 - im;
		double re3         = 1 + im;
		double magnSquared = re2 * re2 + re * re;
		double re4         = (re3 * re2 - re * re) / magnSquared;
		double im4         = -(re * re2 + re3 * re) / magnSquared;
		re = Math.atan2(im4, re4) * -0.5;
		im = Math.log(re4 * re4 + im4 * im4) * 0.25;
	}

// /** arccotangent(a) = (log((1-i/a) / (1+i/a))) * i / 2 */
// public void acot() {
// // Complex temp = this.divR(0, 1);
// // return temp.subR(1).div(temp.add(1)).log().mul(0, 0.5);
// double a = re * re + im * im;
// double b = re / a;
// a = im / a + 1;
// double c = a * a + b * b;
// double d = a / c;
// c = b / c;
// a = 2 - a;
// double e = a * d - b * c;
// a = -(b * d + a * c);
// re = Math.atan2(a, e) * -0.5;
// im = Math.log(e * e + a * a) * 0.25;
// }
//
// /** arcsecant(a) = log(sqrt(1-1/a^2)*i + 1/a) * -i */
// public void asec() {
// // return
// // this.sqr().recip().subR(1).sqrt().rotCCW().add(this.recip()).log().
// // rotCW();
// double a = im * im - re * re;
// double b = 2 * re * im;
// double c = a * a + b * b;
// a = a / c + 1;
// b = b / c;
// c = Math.hypot(a, b);
// double d = re * re + im * im;
// b = (b >= 0? -Math.sqrt((c - a) * 0.5) : Math.sqrt((c - a) * 0.5)) + re / d;
// a = Math.sqrt((c + a) * 0.5) - im / d;
// re = Math.atan2(a, b);
// im = Math.log(a * a + b * b) * -0.5;
// }
//
// /** arccosecant(a) = log(sqrt(1-1/a^2) + i/a) * -i */
// public void acsc() {
// // return this.sqr().recip().subR(1).sqrt().add(this.divR(0,
// // 1)).log().rotCW();
// double a = re * re - im * im;
// double b = 2 * re * im;
// double c = a * a + b * b;
// a = 1 - a / c;
// b = b / c;
// c = Math.hypot(a, b);
// double d = re * re + im * im;
// b = (b < 0? -Math.sqrt((c - a) * 0.5) : Math.sqrt((c - a) * 0.5)) + re / d;
// a = Math.sqrt((c + a) * 0.5) + im / d;
// re = Math.atan2(b, a);
// im = Math.log(a * a + b * b) * -0.5;
// }
//
// /**
// * sinec(a) = sin(a)/a if x!=0, (1, 0) otherwise
// *
// * @return
// */
// public Complex2d sinc() {
// // lim(|z|->0) sinc(z) = 1
// if (re == 0 && im == 0) return new Complex2d(1, 0);
//
// return sin().div(this);
// }
//
// /**
// * cosinec(a) = cos(a)/a
// *
// * @return
// */
// public Complex2d cosc() {
// return cos().div(this);
// }
//
// /**
// * tangentc(a) = tan(a)/a if x!=0, (1, 0) otherwise
// *
// * @return
// */
// public Complex2d tanc() {
// // lim(|z|->0) tanc(z) = 1
// if (re == 0 && im == 0) return new Complex2d(1, 0);
//
// return tan().div(this);
// }
//
// /**
// * cotangentc(a) = cot(a)/a
// *
// * @return
// */
// public Complex2d cotc() {
// return cot().div(this);
// }
//
// /**
// * secantc(a) = sec(a)/a
// *
// * @return
// */
// public Complex2d secc() {
// return sec().div(this);
// }
//
// /**
// * cosecantc(a) = csc(a)/a
// *
// * @return
// */
// public Complex2d cscc() {
// return csc().div(this);
// }
//
// /**
// * sinePI(a) = sin(a*PI/2)
// *
// * @return
// */
// public Complex2d sinPI() {
// double a = re * NumberConstants.TAU025;
// double b = im * NumberConstants.TAU025;
// double c = Math.exp(b) * 0.5;
// b = Math.exp(-b) * 0.5;
// return new Complex2d((c + b) * Math.sin(a), (c - b) * Math.cos(a));
// }
//
// /** sinePI(a) = sin(a*PI/2) */
// public void sinPI() {
// double a = re * NumberConstants.TAU025;
// double b = im * NumberConstants.TAU025;
// double c = Math.exp(b) * 0.5;
// b = Math.exp(-b) * 0.5;
// re = (c + b) * Math.sin(a);
// im = (c - b) * Math.cos(a);
// }
//
// /**
// * cosinePI(a) = cos(a*PI/2)
// *
// * @return
// */
// public Complex2d cosPI() {
// double a = re * NumberConstants.TAU025;
// double b = im * NumberConstants.TAU025;
// double c = Math.exp(-b) * 0.5;
// b = Math.exp(b) * 0.5;
// return new Complex2d((c + b) * Math.cos(a), (c - b) * Math.sin(a));
// }
//
// /** cosinePI(a) = cos(a*PI/2) */
// public void cosPI() {
// double a = re * NumberConstants.TAU025;
// double b = im * NumberConstants.TAU025;
// double c = Math.exp(-b) * 0.5;
// b = Math.exp(b) * 0.5;
// re = (c + b) * Math.cos(a);
// im = (c - b) * Math.sin(a);
// }
//
// /**
// * tangentPI(a) = tan(a*PI/2)
// *
// * @return
// */
// public Complex2d tanPI() {
// double a = re * NumberConstants.TAU05;
// double b = im * NumberConstants.TAU05;
// double c = Math.exp(b);
// b = Math.exp(-b);
// double d = Math.cos(a) + (c + b) * 0.5;
// return new Complex2d(Math.sin(a) / d, (c - b) * 0.5 / d);
// }
//
// /** tangentPI(a) = tan(a*PI/2) */
// public void tanPI() {
// double a = re * NumberConstants.TAU05;
// double b = im * NumberConstants.TAU05;
// double c = Math.exp(b);
// b = Math.exp(-b);
// double d = Math.cos(a) + (c + b) * 0.5;
// re = Math.sin(a) / d;
// im = (c - b) * 0.5 / d;
// }
//
// /**
// * cotangentPI(a) = cot(a*PI/2)
// *
// * @return
// */
// public Complex2d cotPI() {
// double a = re * NumberConstants.TAU05;
// double b = im * NumberConstants.TAU05;
// double c = Math.exp(b) * 0.5;
// b = Math.exp(-b) * 0.5;
// double d = b + c - Math.cos(a);
// return new Complex2d(Math.sin(a) / d, (b - c) / d);
// }
//
// /** cotangentPI(a) = cot(a*PI/2) */
// public void cotPI() {
// double a = re * NumberConstants.TAU05;
// double b = im * NumberConstants.TAU05;
// double c = Math.exp(b) * 0.5;
// b = Math.exp(-b) * 0.5;
// double d = b + c - Math.cos(a);
// re = Math.sin(a) / d;
// im = (b - c) / d;
// }
//
// /**
// * secantPI(a) = sec(a*PI/2)
// *
// * @return
// */
// public Complex2d secPI() {
// double a = re * NumberConstants.TAU025;
// double b = im * NumberConstants.TAU025;
// double c = Math.exp(b);
// b = Math.exp(-b);
// double d = (c + b) * Math.cos(a);
// a = (c - b) * Math.sin(a);
// b = (d * d + a * a) * 0.5;
// return new Complex2d(d / b, a / b);
// }
//
// /** secantPI(a) = sec(a*PI/2) */
// public void secPI() {
// double a = re * NumberConstants.TAU025;
// double b = im * NumberConstants.TAU025;
// double c = Math.exp(b);
// b = Math.exp(-b);
// double d = (c + b) * Math.cos(a);
// a = (c - b) * Math.sin(a);
// b = (d * d + a * a) * 0.5;
// re = d / b;
// im = a / b;
// }
//
// /**
// * cosecantPI(a) = csc(a*PI/2)
// *
// * @return
// */
// public Complex2d cscPI() {
// double a = re * NumberConstants.TAU025;
// double b = im * NumberConstants.TAU025;
// double c = Math.exp(-b);
// b = Math.exp(b);
// double d = (c - b) * Math.cos(a);
// a = (c + b) * Math.sin(a);
// b = (d * d + a * a) * 0.5;
// return new Complex2d(a / b, d / b);
// }
//
// /** cosecantPI(a) = csc(a*PI/2) */
// public void cscPI() {
// double a = re * NumberConstants.TAU025;
// double b = im * NumberConstants.TAU025;
// double c = Math.exp(-b);
// b = Math.exp(b);
// double d = (c - b) * Math.cos(a);
// a = (c + b) * Math.sin(a);
// b = (d * d + a * a) * 0.5;
// re = a / b;
// im = d / b;
// }
//
// /**
// * arcsinePI(a) = asin(a)/(PI/2)
// *
// * @return
// */
// public Complex2d asinPI() {
// // return this.sqr().subR(1).sqrt().add(this.rotCCW()).log().rotCW();
// double a = 1 - re * re + im * im;
// double b = -2 * re * im;
// double c = Math.hypot(a, b);
// b = (b < 0? -Math.sqrt((c - a) * 0.5) : Math.sqrt((c - a) * 0.5)) + re;
// a = Math.sqrt((c + a) * 0.5) - im;
// return new Complex2d(Math.atan2(b, a) * NumberConstants.Q4TAU,
// Math.log(a * a + b * b) * -NumberConstants.Q2TAU);
// }
//
// /** arcsinePI(a) = asin(a)/(PI/2) */
// public void asinPI() {
// // return this.sqr().subR(1).sqrt().add(this.rotCCW()).log().rotCW();
// double a = 1 - re * re + im * im;
// double b = -2 * re * im;
// double c = Math.hypot(a, b);
// b = (b < 0? -Math.sqrt((c - a) * 0.5) : Math.sqrt((c - a) * 0.5)) + re;
// a = Math.sqrt((c + a) * 0.5) - im;
// re = Math.atan2(b, a) * NumberConstants.Q4TAU;
// im = Math.log(a * a + b * b) * -NumberConstants.Q2TAU;
// }
//
// /**
// * arccosinePI(a) = acos(a)/(PI/2)
// *
// * @return
// */
// public Complex2d acosPI() {
// // return this.sqr().subR(1).sqrt().rotCCW().add(this).log().rotCW();
// double a = 1 - re * re + im * im;
// double b = -2 * re * im;
// double c = Math.hypot(a, b);
// b = re - (b < 0? -Math.sqrt((c - a) * 0.5) : Math.sqrt((c - a) * 0.5));
// a = Math.sqrt((c + a) * 0.5) + im;
// return new Complex2d(Math.atan2(a, b) * NumberConstants.Q4TAU,
// Math.log(a * a + b * b) * -NumberConstants.Q2TAU);
// }
//
// /** arccosinePI(a) = acos(a)/(PI/2) */
// public void acosPI() {
// // return this.sqr().subR(1).sqrt().rotCCW().add(this).log().rotCW();
// double a = 1 - re * re + im * im;
// double b = -2 * re * im;
// double c = Math.hypot(a, b);
// b = re - (b < 0? -Math.sqrt((c - a) * 0.5) : Math.sqrt((c - a) * 0.5));
// a = Math.sqrt((c + a) * 0.5) + im;
// re = Math.atan2(a, b) * NumberConstants.Q4TAU;
// im = Math.log(a * a + b * b) * -NumberConstants.Q2TAU;
// }
//
// /**
// * arctanPI(a) = atan(a)/(PI/2)
// *
// * @return
// */
// public Complex2d atanPI() {
// double a = 1 - im;
// double b = re;
// double c = a * a + b * b;
// double d = a / c;
// c = b / c;
// a = 2 - a;
// double e = a * d - b * c;
// b = -(b * d + a * c);
// return new Complex2d(Math.atan2(b, e) * -NumberConstants.Q2TAU,
// Math.log(e * e + b * b) * NumberConstants.Q1TAU);
// }
//
// /** arctanPI(a) = atan(a)/(PI/2) */
// public void atanPI() {
// double a = 1 - im;
// double b = re;
// double c = a * a + b * b;
// double d = a / c;
// c = b / c;
// a = 2 - a;
// double e = a * d - b * c;
// b = -(b * d + a * c);
// re = Math.atan2(b, e) * -NumberConstants.Q2TAU;
// im = Math.log(e * e + b * b) * NumberConstants.Q1TAU;
// }
//
// /**
// * arccotangentPI(a) = acot(a)/(PI/2)
// *
// * @return
// */
// public Complex2d acotPI() {
// double a = re * re + im * im;
// double b = re / a;
// a = im / a + 1;
// double c = a * a + b * b;
// double d = a / c;
// c = b / c;
// a = 2 - a;
// double e = a * d - b * c;
// a = -(b * d + a * c);
// return new Complex2d(Math.atan2(a, e) * -NumberConstants.Q2TAU,
// Math.log(e * e + a * a) * NumberConstants.Q1TAU);
// }
//
// /** arccotangentPI(a) = acot(a)/(PI/2) */
// public void acotPI() {
// double a = re * re + im * im;
// double b = re / a;
// a = im / a + 1;
// double c = a * a + b * b;
// double d = a / c;
// c = b / c;
// a = 2 - a;
// double e = a * d - b * c;
// a = -(b * d + a * c);
// re = Math.atan2(a, e) * -NumberConstants.Q2TAU;
// im = Math.log(e * e + a * a) * NumberConstants.Q1TAU;
// }
//
// /**
// * arcsecantPI(a) = arcsec(a)/(PI/2)
// *
// * @return
// */
// public Complex2d asecPI() {
// double a = im * im - re * re;
// double b = 2 * re * im;
// double c = a * a + b * b;
// a = a / c + 1;
// b = b / c;
// c = Math.hypot(a, b);
// double d = re * re + im * im;
// b = (b >= 0? -Math.sqrt((c - a) * 0.5) : Math.sqrt((c - a) * 0.5)) + re / d;
// a = Math.sqrt((c + a) * 0.5) - im / d;
// return new Complex2d(Math.atan2(a, b) * NumberConstants.Q4TAU,
// Math.log(a * a + b * b) * -NumberConstants.Q2TAU);
// }
//
// /** arcsecantPI(a) = arcsec(a)/(PI/2) */
// public void asecPI() {
// double a = im * im - re * re;
// double b = 2 * re * im;
// double c = a * a + b * b;
// a = a / c + 1;
// b = b / c;
// c = Math.hypot(a, b);
// double d = re * re + im * im;
// b = (b >= 0? -Math.sqrt((c - a) * 0.5) : Math.sqrt((c - a) * 0.5)) + re / d;
// a = Math.sqrt((c + a) * 0.5) - im / d;
// re = Math.atan2(a, b) * NumberConstants.Q4TAU;
// im = Math.log(a * a + b * b) * -NumberConstants.Q2TAU;
// }
//
// /**
// * arccosecantPI(a) = acsc(a)/(PI/2)
// *
// * @return
// */
// public Complex2d acscPI() {
// double a = re * re - im * im;
// double b = 2 * re * im;
// double c = a * a + b * b;
// a = 1 - a / c;
// b = b / c;
// c = Math.hypot(a, b);
// double d = re * re + im * im;
// b = (b < 0? -Math.sqrt((c - a) * 0.5) : Math.sqrt((c - a) * 0.5)) + re / d;
// a = Math.sqrt((c + a) * 0.5) + im / d;
// return new Complex2d(Math.atan2(b, a) * NumberConstants.Q4TAU,
// Math.log(a * a + b * b) * -NumberConstants.Q2TAU);
// }
//
// /** arccosecantPI(a) = acsc(a)/(PI/2) */
// public void acscPI() {
// double a = re * re - im * im;
// double b = 2 * re * im;
// double c = a * a + b * b;
// a = 1 - a / c;
// b = b / c;
// c = Math.hypot(a, b);
// double d = re * re + im * im;
// b = (b < 0? -Math.sqrt((c - a) * 0.5) : Math.sqrt((c - a) * 0.5)) + re / d;
// a = Math.sqrt((c + a) * 0.5) + im / d;
// re = Math.atan2(b, a) * NumberConstants.Q4TAU;
// im = Math.log(a * a + b * b) * -NumberConstants.Q2TAU;
// }
//
// /**
// * sinecPI(a) = sin(a*PI/2)/(a*PI/2) if x!=0, (1, 0) otherwise
// *
// * @return
// */
// public Complex2d sincPI() {
// // lim(|z|->0) sinc(z) = 1
// if (re == 0 && im == 0) return new Complex2d(1, 0);
//
// Complex2d thisPI = this.mul(NumberConstants.TAU025);
// return thisPI.sin().div(thisPI);
// }
//
// /**
// * cosinecPI(a) = cos(a*PI/2)/(a*PI/2)
// *
// * @return
// */
// public Complex2d coscPI() {
// Complex2d thisPI = this.mul(NumberConstants.TAU025);
// return thisPI.cos().div(thisPI);
// }
//
// /**
// * tangentcPI(a) = tan(a*PI/2)/(a*PI/2) if x!=0, (1, 0) otherwise
// *
// * @return
// */
// public Complex2d tancPI() {
// // lim(|z|->0) tanc(z) = 1
// if (re == 0 && im == 0) return new Complex2d(1, 0);
//
// Complex2d thisPI = this.mul(NumberConstants.TAU025);
// return thisPI.tan().div(thisPI);
// }
//
// /**
// * cotangentcPI(a) = cot(a*PI/2)/(a*PI/2)
// *
// * @return
// */
// public Complex2d cotcPI() {
// Complex2d thisPI = this.mul(NumberConstants.TAU025);
// return thisPI.cot().div(thisPI);
// }
//
// /**
// * secantcPI(a) = sec(a*PI/2)/(a*PI/2)
// *
// * @return
// */
// public Complex2d seccPI() {
// Complex2d thisPI = this.mul(NumberConstants.TAU025);
// return thisPI.sec().div(thisPI);
// }
//
// /**
// * cosecantcPI(a) = csc(a*PI/2)/(a*PI/2)
// *
// * @return
// */
// public Complex2d csccPI() {
// Complex2d thisPI = this.mul(NumberConstants.TAU025);
// return thisPI.csc().div(thisPI);
// }
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// // Unary Hyperbolic Functions
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// /**
// * sineh(a) = (exp(a) - exp(-a))/2
// *
// * @return
// */
// public Complex2d sinh() {
// double ex = Math.exp(re);
// double exn = Math.exp(-re);
//
// return new Complex2d((ex - exn) * 0.5 * Math.cos(im), (ex + exn) * 0.5 * Math.sin(im));
// }
//
// /** sineh(a) = (exp(a) - exp(-a))/2 */
// public void sinh() {
// double ex = Math.exp(re);
// double exn = Math.exp(-re);
//
// re = (ex - exn) * 0.5 * Math.cos(im);
// im = (ex + exn) * 0.5 * Math.sin(im);
// }
//
// /**
// * arcsineh(a) = log(sqrt(a^2+1)+a)
// *
// * @return
// */
// public Complex2d asinh() {
// Complex2d out = sqr();
// out.re++;
// out.sqrt();
// out.add(this);
// out.log();
// return out;
// }
//
// /**
// * cosineh(a) = (exp(a) + exp(-a))/2
// *
// * @return
// */
// public Complex2d cosh() {
// double ep = Math.exp(re) * 0.5;
// double en = Math.exp(-re) * 0.5;
//
// return new Complex2d(Math.cos(im) * (ep + en), Math.sin(im) * (ep - en));
// }
//
// /**
// * arccosineh(a) = log(sqrt(a-1)*sqrt(a+1)+a)
// *
// * @return
// */
// public Complex2d acosh() {
// return this.add(1).sqrt().mul(sub(1).sqrt()).add(this).log();
// }
//
// /**
// * tangenth(a) = (exp(2*a) - 1) / (exp(2*a) + 1)
// *
// * @return
// */
// public Complex2d tanh() {
// double a = re + re;
// double b = im + im;
// double c = Math.exp(a);
// a = Math.exp(-a);
// double d = (c + a) / 2 + Math.cos(b);
// return new Complex2d((c - a) * 0.5 / d, Math.sin(b) / d);
// }
//
// /** tangenth(a) = (exp(2*a) - 1) / (exp(2*a) + 1) */
// public void tanh() {
// double a = re + re;
// double b = im + im;
// double c = Math.exp(a);
// a = Math.exp(-a);
// double d = (c + a) / 2 + Math.cos(b);
// re = (c - a) * 0.5 / d;
// im = Math.sin(b) / d;
// }
//
// /**
// * arctangenth(a) = (log(1+a) - log(1-a))/2
// *
// * @return
// */
// public Complex2d atanh() {
// return new Complex2d(1 + re, im).div(new Complex2d(1 - re, -im)).log().mul(0.5);
// }
//
// /**
// * cotangenth(a) = (exp(2*a) + 1) / (exp(2*a) - 1)
// *
// * @return
// */
// public Complex2d coth() {
// double a = re + re;
// double b = im + im;
// double c = Math.exp(a) * 0.5;
// a = Math.exp(-a) * 0.5;
// double d = c + a - Math.cos(b);
// return new Complex2d((c - a) / d, -Math.sin(b) / d);
// }
//
// /**
// * arccotangenth(a) = (log(1+1/a) - log(1-1/a))/2
// *
// * @return
// */
// public Complex2d acoth() {
// Complex2d inv = recip();
// return inv.add(1).log().sub(inv.subR(1).log()).mul(0.5);
// // return this.flip().conjugate().acot().flip().conjugate();
// }
//
// /**
// * secanth(a) = 2 / (exp(a) + exp(-a))
// *
// * @return
// */
// public Complex2d sech() {
// return exp().add(neg().exp()).divR(2);
// }
//
// /**
// * arcsecanth(a) = log(sqrt(1/a-1)*sqrt(1/a+1)+1/a)
// *
// * @return
// */
// public Complex2d asech() {
// Complex2d inv = recip();
// return inv.sub(1).sqrt().mul(inv.add(1).sqrt()).add(inv).log();
// // return this.flip().asec().flip();
// }
//
// /**
// * cosecanth(a) = 2 / (exp(a) - exp(-a))
// *
// * @return
// */
// public Complex2d csch() {
// return exp().sub(neg().exp()).divR(2);
// }
//
// /**
// * arccosecanth(a) = log(sqrt(1/a^2+1)+1/a)
// *
// * @return
// */
// public Complex2d acsch() {
// return sqr().recip().add(1).sqrt().add(recip()).log();
// // return this.flip().acsc().flip();
// }
//
// /**
// * sinehPI(a) = sinh(a*PI/2)
// *
// * @return
// */
// public Complex2d sinhPI() {
// return this.mul(NumberConstants.TAU025).sinh();
// }
//
// /**
// * arcsinehPI(a) = asinh(a)/(PI/2)
// *
// * @return
// */
// public Complex2d asinhPI() {
// return asinh().mul(NumberConstants.Q4TAU);
// }
//
// /**
// * cosinehPI(a) = cosh(a*PI/2)
// *
// * @return
// */
// public Complex2d coshPI() {
// return this.mul(NumberConstants.TAU025).cosh();
// }
//
// /**
// * arccosinehPI(a) = acosh(a)/(PI/2)
// *
// * @return
// */
// public Complex2d acoshPI() {
// return acosh().mul(NumberConstants.Q4TAU);
// }
//
// /**
// * tangenthPI(a) = tanh(a*PI/2)
// *
// * @return
// */
// public Complex2d tanhPI() {
// return this.mul(NumberConstants.TAU025).tanh();
// }
//
// /**
// * arctangenthPI(a) = atanh(a)/(PI/2)
// *
// * @return
// */
// public Complex2d atanhPI() {
// return atanh().mul(NumberConstants.Q4TAU);
// }
//
// /**
// * cotangenthPI(a) = coth(a*PI/2)
// *
// * @return
// */
// public Complex2d cothPI() {
// return this.mul(NumberConstants.TAU025).coth();
// }
//
// /**
// * arccotangenthPI(a) = acoth(a)/(PI/2)
// *
// * @return
// */
// public Complex2d acothPI() {
// return acoth().mul(NumberConstants.Q4TAU);
// }
//
// /**
// * secanthPI(a) = sech(a*PI/2)
// *
// * @return
// */
// public Complex2d sechPI() {
// return this.mul(NumberConstants.TAU025).sech();
// }
//
// /**
// * arcsecanthPI(a) = asech(a)/(PI/2)
// *
// * @return
// */
// public Complex2d asechPI() {
// return asech().mul(NumberConstants.Q4TAU);
// }
//
// /**
// * cosecanthPI(a) = csch(a*PI/2)
// *
// * @return
// */
// public Complex2d cschPI() {
// return this.mul(NumberConstants.TAU025).csch();
// }
//
// /**
// * arccosecanthPI(a) = acsch(a)/(PI/2)
// *
// * @return
// */
// public Complex2d acschPI() {
// return acsch().mul(NumberConstants.Q4TAU);
// }
//
// /**
// * sinehc(a) = sinh(a)/a if x!=0, (1, 0) otherwise
// *
// * @return
// */
// public Complex2d sinhc() {
// // lim(|z|->0) sinhc(z) = 1
// if (re == 0 && im == 0) return new Complex2d(1, 0);
//
// return sinh().div(this);
// }
//
// /**
// * cosinehc(a) = cosh(a)/a
// *
// * @return
// */
// public Complex2d coshc() {
// return cosh().div(this);
// }
//
// /**
// * tangenthc(a) = tanhc(a)/a if x!=0, (1, 0) otherwise
// *
// * @return
// */
// public Complex2d tanhc() {
// // lim(|z|->0) tanhc(z) = 1
// if (re == 0 && im == 0) return new Complex2d(1, 0);
//
// return tanh().div(this);
// }
//
// /**
// * cotangenthc(a) = coth(a)/a
// *
// * @return
// */
// public Complex2d cothc() {
// return coth().div(this);
// }
//
// /**
// * secanthc(a) = sech(a)/a
// *
// * @return
// */
// public Complex2d sechc() {
// return sech().div(this);
// }
//
// /**
// * cosecanthc(a) = csch(a)/a
// *
// * @return
// */
// public Complex2d cschc() {
// return csch().div(this);
// }
//
// /**
// * sinehcPI(a) = sinh(a*PI/2)/(a*PI/2) if x!=0, (1, 0) otherwise
// *
// * @return
// */
// public Complex2d sinhcPI() {
// // lim(|z|->0) sinc(z) = 1
// if (re == 0 && im == 0) return new Complex2d(1, 0);
//
// Complex2d thisPI = this.mul(NumberConstants.TAU025);
// return thisPI.sinh().div(thisPI);
// }
//
// /**
// * cosinehcPI(a) = cosh(a*PI/2)/(a*PI/2)
// *
// * @return
// */
// public Complex2d coshcPI() {
// Complex2d thisPI = this.mul(NumberConstants.TAU025);
// return thisPI.cosh().div(thisPI);
// }
//
// /**
// * tangenthcPI(a) = tanh(a*PI/2)/(a*PI/2) if x!=0, (1, 0) otherwise
// *
// * @return
// */
// public Complex2d tanhcPI() {
// // lim(|z|->0) tanc(z) = 1
// if (re == 0 && im == 0) return new Complex2d(1, 0);
//
// Complex2d thisPI = this.mul(NumberConstants.TAU025);
// return thisPI.tanh().div(thisPI);
// }
//
// /**
// * cotangentchPI(a) = coth(a*PI/2)/(a*PI/2)
// *
// * @return
// */
// public Complex2d cothcPI() {
// Complex2d thisPI = this.mul(NumberConstants.TAU025);
// return thisPI.coth().div(thisPI);
// }
//
// /**
// * secanthcPI(a) = sech(a*PI/2)/(a*PI/2)
// *
// * @return
// */
// public Complex2d sechcPI() {
// Complex2d thisPI = this.mul(NumberConstants.TAU025);
// return thisPI.sech().div(thisPI);
// }
//
// /**
// * cosecanthcPI(a) = csch(a*PI/2)/(a*PI/2)
// *
// * @return
// */
// public Complex2d cschcPI() {
// Complex2d thisPI = this.mul(NumberConstants.TAU025);
// return thisPI.csch().div(thisPI);
// }
//
// /**
// * fibonacciHyperbolicSine(a) = 2/sqrt(5) * sinh((2*a) * log(ϕ))
// *
// * @return
// */
// public Complex2d sinFh() {
// return this.mul(2).mul(NumberConstants.LOGPHI).sinh().mul(NumberConstants.SQRT08);
// }
//
// /**
// * fibonacciHyperbolicCosine(a) = 2/sqrt(5) * cosh((2*a + 1) * log(ϕ))
// *
// * @return
// */
// public Complex2d cosFh() {
// return this.mul(2).add(1).mul(NumberConstants.LOGPHI).cosh().mul(NumberConstants.SQRT08);
// }
//
// /**
// * fibonacciHyperbolicTangent(a) = sinFh(a) / cosFh(a)
// *
// * @return
// */
// public Complex2d tanFh() {
// return sinFh().div(cosFh());
// }
//
// /**
// * fibonacciHyperbolicCotangent(a) = 1/tanFh(a)
// *
// * @return
// */
// public Complex2d cotFh() {
// return cosFh().div(sinFh());
// }
//
// /**
// * fibonacciHyperbolicSecant(a) = 1/cosFh(a)
// *
// * @return
// */
// public Complex2d secFh() {
// return cosFh().recip();
// }
//
// /**
// * fibonacciHyperbolicCosecant(a) = 1/sinFh(a)
// *
// * @return
// */
// public Complex2d cscFh() {
// return sinFh().recip();
// }
//
// //
// // Unary Classic Trigonometric Fucntions
// //
// /**
// * versine(a) = 1 - cos(a)
// *
// * @return
// */
// public Complex2d versin() {
// return cos().subR(1);
// }
//
// /**
// * arcversine(a) = acos(1 - a)
// *
// * @return
// */
// public Complex2d aversin() {
// return this.subR(1).acos();
// }
//
// /**
// * vercosine(a) = 1 - sin(a)
// *
// * @return
// */
// public Complex2d vercos() {
// return sin().subR(1);
// }
//
// /**
// * arcvercosine(a) = asin(1 - a)
// *
// * @return
// */
// public Complex2d avercos() {
// return this.subR(1).asin();
// }
//
// /**
// * vertangent(a) = 1 - cot(a)
// *
// * @return
// */
// public Complex2d vertan() {
// return cot().subR(1);
// }
//
// /**
// * arcvertangent(a) = acot(1 - a)
// *
// * @return
// */
// public Complex2d avertan() {
// return this.subR(1).acot();
// }
//
// /**
// * vercotangent(a) = 1 - tan(a)
// *
// * @return
// */
// public Complex2d vercot() {
// return tan().subR(1);
// }
//
// /**
// * arcvercotangent(a) = atan(1 - a)
// *
// * @return
// */
// public Complex2d avercot() {
// return this.subR(1).atan();
// }
//
// /**
// * versecant(a) = 1 - csc(a)
// *
// * @return
// */
// public Complex2d versec() {
// return csc().subR(1);
// }
//
// /**
// * arcversecant(a) = acsc(1 - a)
// *
// * @return
// */
// public Complex2d aversec() {
// return this.subR(1).acsc();
// }
//
// /**
// * vercosecant(a) = 1 - sec(a)
// *
// * @return
// */
// public Complex2d vercsc() {
// return sec().subR(1);
// }
//
// /**
// * arcvercosecant(a) = asec(1 - a)
// *
// * @return
// */
// public Complex2d avercsc() {
// return this.subR(1).asec();
// }
//
// /**
// * haversine(a) = 0.5 * (1 - cos(a))
// *
// * @return
// */
// public Complex2d haversin() {
// return cos().subR(1).mul(0.5);
// }
//
// /**
// * archaversine(a) = acos(1 - 2*a)
// *
// * @return
// */
// public Complex2d ahaversin() {
// return this.mul(2).subR(1).acos();
// }
//
// /**
// * havercosine(a) = 0.5 * (1 - sin(a))
// *
// * @return
// */
// public Complex2d havercos() {
// return sin().subR(1).mul(0.5);
// }
//
// /**
// * archavercosine(a) = asin(1 - 2*a)
// *
// * @return
// */
// public Complex2d ahavercos() {
// return this.mul(2).subR(1).asin();
// }
//
// /**
// * havertangent(a) = 0.5 * (1 - cot(a))
// *
// * @return
// */
// public Complex2d havertan() {
// return cot().subR(1).mul(0.5);
// }
//
// /**
// * archavertangent(a) = acot(1 - 2*a)
// *
// * @return
// */
// public Complex2d ahavertan() {
// return this.mul(2).subR(1).acot();
// }
//
// /**
// * havercotangent(a) = 0.5 * (1 - tan(a))
// *
// * @return
// */
// public Complex2d havercot() {
// return tan().subR(1).mul(0.5);
// }
//
// /**
// * archavercotangent(a) = atan(1 - 2*a)
// *
// * @return
// */
// public Complex2d ahavercot() {
// return this.mul(2).subR(1).atan();
// }
//
// /**
// * haversecant(a) = 0.5 * (1 - csc(a))
// *
// * @return
// */
// public Complex2d haversec() {
// return csc().subR(1).mul(0.5);
// }
//
// /**
// * archaversecant(a) = acsc(1 - 2*a)
// *
// * @return
// */
// public Complex2d ahaversec() {
// return this.mul(2).subR(1).acsc();
// }
//
// /**
// * havercosecant(a) = 0.5 * (1 - sec(a))
// *
// * @return
// */
// public Complex2d havercsc() {
// return sec().subR(1).mul(0.5);
// }
//
// /**
// * archavercosecant(a) = asec(1 - 2*a)
// *
// * @return
// */
// public Complex2d ahavercsc() {
// return this.mul(2).subR(1).asec();
// }
//
// /**
// * exsine(a) = sin(a) - 1
// *
// * @return
// */
// public Complex2d exsin() {
// return sin().sub(1);
// }
//
// /**
// * arcexsine(a) = asin(a + 1)
// *
// * @return
// */
// public Complex2d aexsin() {
// return this.add(1).asin();
// }
//
// /**
// * excosine(a) = cos(a) - 1
// *
// * @return
// */
// public Complex2d excos() {
// return cos().sub(1);
// }
//
// /**
// * arcexcosine(a) = acos(a + 1)
// *
// * @return
// */
// public Complex2d aexcos() {
// return this.add(1).acos();
// }
//
// /**
// * extangent(a) = tan(a) - 1
// *
// * @return
// */
// public Complex2d extan() {
// return tan().sub(1);
// }
//
// /**
// * arcextangent(a) = atan(a + 1)
// *
// * @return
// */
// public Complex2d aextan() {
// return this.add(1).atan();
// }
//
// /**
// * excotangent(a) = cot(a) - 1
// *
// * @return
// */
// public Complex2d excot() {
// return cot().sub(1);
// }
//
// /**
// * arcexcotangent(a) = acot(a + 1)
// *
// * @return
// */
// public Complex2d aexcot() {
// return this.add(1).acot();
// }
//
// /**
// * exsecant(a) = sec(a) - 1
// *
// * @return
// */
// public Complex2d exsec() {
// return sec().sub(1);
// }
//
// /**
// * arcexsecant(a) = asec(a + 1)
// *
// * @return
// */
// public Complex2d aexsec() {
// return this.add(1).asec();
// }
//
// /**
// * excosecant(a) = csc(a) - 1
// *
// * @return
// */
// public Complex2d excsc() {
// return csc().sub(1);
// }
//
// /**
// * arcexcosecant(a) = acsc(a + 1)
// *
// * @return
// */
// public Complex2d aexcsc() {
// return this.add(1).acsc();
// }
//
// /**
// * gdsine(a) = 2 * asin(sinh(a/2))
// *
// * @return
// */
// public Complex2d gdsin() {
// return this.mul(0.5).sinh().asin().mul(2);
// }
//
// /**
// * arcgdsine(a) = 2 * asinh(sin(a/2))
// *
// * @return
// */
// public Complex2d agdsin() {
// return this.mul(0.5).sin().asinh().mul(2);
// }
//
// /**
// * gdcosine(a) = 2 * acos(cosh(a/2))
// *
// * @return
// */
// public Complex2d gdcos() {
// return this.mul(0.5).cosh().acos().mul(2);
// }
//
// /**
// * arcgdcosine(a) = 2 * acosh(cos(a/2))
// *
// * @return
// */
// public Complex2d agdcos() {
// return this.mul(0.5).cos().acosh().mul(2);
// }
//
// /**
// * gudermannian(a) = 2 * atan(tanh(a/2))
// *
// * @return
// */
// public Complex2d gdtan() {
// return this.mul(0.5).tanh().atan().mul(2);
// }
//
// /**
// * arcgudermannian(a) = 2 * atanh(tan(a/2))
// *
// * @return
// */
// public Complex2d agdtan() {
// return this.mul(0.5).tan().atanh().mul(2);
// }
//
// /**
// * gdcotangent(a) = 2 * acot(coth(a/2))
// *
// * @return
// */
// public Complex2d gdcot() {
// return this.mul(0.5).coth().acot().mul(2);
// }
//
// /**
// * arcgdcotangent(a) = 2 * acoth(cot(a/2))
// *
// * @return
// */
// public Complex2d agdcot() {
// return this.mul(0.5).cot().acoth().mul(2);
// }
//
// /**
// * gdsecant(a) = 2 * asec(sech(a/2))
// *
// * @return
// */
// public Complex2d gdsec() {
// return this.mul(0.5).sech().asec().mul(2);
// }
//
// /**
// * arcgdsece(a) = 2 * asech(sec(a/2))
// *
// * @return
// */
// public Complex2d agdsec() {
// return this.mul(0.5).sec().asech().mul(2);
// }
//
// /**
// * gdcosecant(a) = 2 * acsc(csch(a/2))
// *
// * @return
// */
// public Complex2d gdcsc() {
// return this.mul(0.5).csch().acsc().mul(2);
// }
//
// /**
// * arcgdcsce(a) = 2 * acsch(csc(a/2))
// *
// * @return
// */
// public Complex2d agdcsc() {
// return this.mul(0.5).csc().acsch().mul(2);
// }
//
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// // Unary Other Functions
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// /**
// * fibonacci(a) = (ϕ^a + cos(a*PI)*ϕ^(-a)) / sqrt(5)
// *
// * @return
// */
// public Complex2d fibonacci() {
// Complex2d temp = this.mul(NumberConstants.PHI).exp();
//
// return temp.sub(this.mul(NumberConstants.TAU05).cos().div(temp)).mul(NumberConstants.SQRT02);
// }
//
// /**
// * einstein1(a) = a^2 * exp(a) / (exp(a) - 1)^2
// *
// * @return
// */
// public Complex2d einstein1() {
// Complex2d exp = exp();
// return sqr().mul(exp).div(exp.sub(1).sqr());
// }
//
// /**
// * einstein2(a) = a / (exp(a) - 1)
// *
// * @return
// */
// public Complex2d einstein2() {
// return this.div(exp().sub(1));
// }
//
// /**
// * einstein3(a) = log(1 - exp(-a))
// *
// * @return
// */
// public Complex2d einstein3() {
// return neg().exp().subR(1).log();
// }
//
// /**
// * einstein4(a) = a / (exp(a) - 1) - log(1 - exp(-a))
// *
// * @return
// */
// public Complex2d einstein4() {
// return this.div(exp().sub(1)).sub(neg().exp().subR(1).log());
// }
//
// /**
// * sigmoid(a) = 1 / (1 + exp(-a))
// *
// * @return
// */
// public Complex2d sigmoid() {
// return neg().exp().add(1).recip();
// }
//
// /**
// * gaussRemainder(a) = 1/a - trunc(1/a)
// *
// * @return
// */
// public Complex2d gaussRemainder() {
// Complex2d recip = recip();
// return recip.sub(recip.trunc());
// }
//
// /**
// * gaussModulo(a) = 1/a - floor(1/a)
// *
// * @return
// */
// public Complex2d gaussModulo() {
// Complex2d recip = recip();
// return recip.sub(recip.floor());
// }
//
// /**
// * gaussModC(a) = 1/a - round(1/a)
// *
// * @return
// */
// public Complex2d gaussModC() {
// Complex2d recip = recip();
// return recip.sub(recip.round());
// }
//
// /**
// * nearIdent(a) = 3*sin(a) / (cos(a) + 2)
// *
// * @return
// */
// public Complex2d nearIdent() {
// return sin().mul(3).div(cos().add(2));
// }
//
// /**
// * nearIdent(a) = cos(a^2 * PI)
// *
// * @return
// */
// public Complex2d sweep1() {
// return sqr().mul(NumberConstants.TAU05).cos();
// }
//
// /**
// * nearIdent(a) = cos(exp(a*ln(2)) * PI)
// *
// * @return
// */
// public Complex2d sweep2() {
// return this.mul(NumberConstants.LOG2).exp().mul(NumberConstants.TAU05).cos();
// }
//
// /**
// * nearIdent(a) = cos(exp(a*ln(2)) * PI)
// *
// * @return
// */
// public Complex2d tukeysWeight() {
// return sqr().subR(1).sqr().mul(this);
// }
//
// /**
// * polynom1(a) = a^3-1
// *
// * @return
// */
// public Complex2d polynom1() {
// return cube().sub(1);
// }
//
// /**
// * polynom1(a) = a^5 - 1
// *
// * @return
// */
// public Complex2d polynom2() {
// Complex2d zz = sqr();
// Complex2d zzz = zz.mul(this);
// return zzz.mul(zz).sub(1);
// }
//
// /**
// * rationalA22(a) = a^2 / (a^2-1)
// *
// * @return
// */
// public Complex2d rationalA22() {
// Complex2d z2 = sqr();
// return z2.sub(1).divR(z2);
// }
//
// /**
// * rationalA23(a) = a^2 / (a^3-1)
// *
// * @return
// */
// public Complex2d rationalA23() {
// Complex2d z2 = sqr();
// return z2.mul(this).sub(1).divR(z2);
// }
//
// /**
// * rationalA32(a) = a^3 / (a^2-1)
// *
// * @return
// */
// public Complex2d rationalA32() {
// Complex2d z2 = sqr();
// return z2.sub(1).divR(z2.mul(this));
// }
//
// /**
// * rationalA33(a) = a^3 / (a^3-1)
// *
// * @return
// */
// public Complex2d rationalA33() {
// Complex2d z3 = cube();
// return z3.sub(1).divR(z3);
// }
//
// /**
// * rationalB22(a) = a^2 / (a^2-1)
// *
// * @return
// */
// public Complex2d rationalB22() {
// Complex2d z2 = sqr();
// return z2.sub(1).divR(z2);
// }
//
// /**
// * rationalB23(a) = a^2 / (a^3-1)
// *
// * @return
// */
// public Complex2d rationalB23() {
// Complex2d z2 = sqr();
// return z2.mul(this).sub(1).divR(z2);
// }
//
// /**
// * rationalB32(a) = a^3 / (a^2+1)
// *
// * @return
// */
// public Complex2d rationalB32() {
// Complex2d z2 = sqr();
// return z2.add(1).divR(z2.mul(this));
// }
//
// /**
// * rationalB33(a) = a^3 / (a^3-1)
// *
// * @return
// */
// public Complex2d rationalB33() {
// Complex2d z3 = cube();
// return z3.sub(1).divR(z3);
// }
//
// /**
// * rationalC32(a) = (a^3-1) / a^2
// *
// * @return
// */
// public Complex2d rationalC32() {
// Complex2d z2 = sqr();
// return z2.divR(z2.mul(this).sub(1));
// }
//
// /**
// * rationalD32(a) = (a^3+1) / a^2
// *
// * @return
// */
// public Complex2d rationalD32() {
// Complex2d z2 = sqr();
// return z2.add(1).divR(z2.mul(this).add(0, 0.2));
// }
//
// /**
// * rational1(a) = (a+4) / (a^5-3*i*a^3+2)
// *
// * @return
// */
// public Complex2d rationalPoly3() {
// Complex2d zz = sqr();
// Complex2d zzz = zz.mul(this);
// return this.add(4).div(zzz.mul(zz).add(zzz.mul(0, -3)).add(2));
// }

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Binary Other Functions
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * normal(a, sigma) = 1/sigma * exp(a^2 / (-2 * sigma^2))
	 */
	public void normal(Complex2d sigma) {
		double a = (sigma.re * sigma.re - sigma.im * sigma.im) * -2;
		double b = -4 * sigma.re * sigma.im;
		double c = a * a + b * b;
		a /= c;
		b /= c;
		c = re * re - im * im;
		double d = 2 * re * im;
		double e = d * a - c * b;
		a = Math.exp(c * a + d * b);
		b = a * Math.sin(e);
		a = a * Math.cos(e);
		c = sigma.re * sigma.re + sigma.im * sigma.im;
		d = sigma.im / c;
		c = sigma.re / c;
		re = a * c + b * d;
		im = b * c - a * d;
	}

	/**
	 * logNormal(a, mu) = 1/mu * exp((log(a) - mu)^2 / -2)
	 */
	public void logNormal(Complex2d mu) {
		double a = re;
		double b = im;
		double c = mu.im - Math.atan2(b, a);
		double d = Math.log(a * a + b * b) * 0.5 - mu.re;
		double e = Math.exp((d * d - c * c) * -0.5);
		d = d * c;
		c = e * Math.cos(d);
		e = e * Math.sin(d);
		d = a * a + b * b;
		a /= d;
		b /= d;
		re = c * a + e * b;
		im = e * a - c * b;
	}

	/**
	 * butterfly(a, b) = (a.re^2-a.im^2) * sin(a.re+a.im)/b) / mod(a)
	 */
	public void butterfly(Complex2d z) {
		double a = re * re;
		double b = im * im;
		a = (a - b) / (a + b) * 0.5;
		double c = (re + im) / (z.re * z.re + z.im * z.im);
		b = z.re * c;
		c *= z.im;
		double d = Math.exp(-c);
		c = Math.exp(c);
		re = (d + c) * Math.sin(b) * a;
		im = (d - c) * Math.cos(b) * a;
	}

// /**
// * butterfly(a, b) = (a^2-b^2) * sin(a+b) / (a^2 + b^2)
// *
// * @param z
// * @return
// */
// public Complex2d complexButterfly(Complex2d z) {
// Complex2d xx = sqr();
// Complex2d yy = z.sqr();
// return xx.sub(yy).mul(this.add(z).sin()).div(xx.add(yy));
// }
//
// /**
// * carotidKundalini(a, b) = cos(a * b * arccos(a))
// *
// * @param z
// * @return
// */
// public Complex2d carotidKundalini(Complex2d z) {
// return acos().mul(this).mul(z).cos();
// }
//
// /**
// * hemispherical(a, b) = sqrt(a - mod(b))
// *
// * @param c
// * @return
// */
// public Complex2d hemispherical(Complex2d c) {
// return c.sub(magnSquared()).sqrt();
// }
//
// /**
// * archimedianSpiral(a, b) = a * (1, exp(|a|^b.re * b.im))
// *
// * @param c
// * @return
// */
// public Complex2d spiral(Complex2d c) {
// Complex2d exp = new Complex2d(1, Math.pow(magn(), c.re) * c.im).exp();
// return this.mul(exp);
// }
//
// /**
// * swirl(a, b) = a * (1, cos(log(|a|)*b.re) * b.im)
// *
// * @param c
// * @return
// */
// public Complex2d swirl1(Complex2d c) {
// // Complex polar = this.toPolar();
// // polar.im += Math.cos(Math.log(polar.re) * c.re) * c.im;
// // return polar.toCartesian();
// Complex2d exp = new Complex2d(1, Math.cos(Math.log(magn()) * c.re) * c.im).exp();
// return this.mul(exp);
// }
//
// /**
// * swirl(a, b) = a * (1, cos(|a|*b.re) * b.im)
// *
// * @param c
// * @return
// */
// public Complex2d swirl2(Complex2d c) {
// // Complex polar = this.toPolar();
// // polar.im += Math.cos(polar.re * c.re) * c.im;
// // return polar.toCartesian();
// Complex2d exp = new Complex2d(1, Math.cos(magn() * c.re) * c.im).exp();
// return this.mul(exp);
// }

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Complex2d))
			return false;

		Complex2d c = (Complex2d)o;

		return re == c.re && im == c.im;
	}

	@Override
	public int hashCode() {
		int hash = 0x811C9DC5;
		hash ^= Double.hashCode(re);
		hash *= 0x01000193;
		hash ^= Double.hashCode(im);
		hash *= 0x01000193;
		return hash;
	}

	@Override
	public String toString() {
		return String.format("%+.16e/%+.16e\t", re, im);
// return NumberUtilities.toBinaryString(Double.doubleToLongBits(re)) + "/"
// + NumberUtilities.toBinaryString(Double.doubleToLongBits(im));
	}
}
