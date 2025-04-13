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

package org.digitalmodular.utilities.container;

import java.io.Serializable;

import org.digitalmodular.utilities.annotation.UtilityClass;
import static org.digitalmodular.utilities.constant.NumberConstants.FIBONACCI_PHI1;
import static org.digitalmodular.utilities.constant.NumberConstants.PHI;
import static org.digitalmodular.utilities.constant.NumberConstants.SQRT02;
import static org.digitalmodular.utilities.container.ComplexMath.exp;
import static org.digitalmodular.utilities.container.ComplexMath.log;
import static org.digitalmodular.utilities.container.ComplexMath.pow;
import static org.digitalmodular.utilities.container.ComplexMath.recip;
import static org.digitalmodular.utilities.container.ComplexMath.sqr;

// https://mpmath.org/gallery/

/**
 * @author Mark Jeronimus
 */
// Created 2024-08-03 Split from Complex2d
@UtilityClass
public final class ComplexAdvanced implements Serializable {
	/** normal(value, sigma) = 1/sigma * exp(value^2 / (-2 * sigma^2)) */
	public static Complex2d normal(Complex2d value, double sigma) {
		return exp(sqr(value).mul(sigma)).div(-0.5 / (sigma * sigma));
	}

	/** normal(value, sigma) = 1/sigma * exp(value^2 / (-2 * sigma^2)) */
	public static Complex2d normal(Complex2d value, Complex2d sigma) {
		return exp(sqr(value).mul(sigma)).div(recip(sqr(sigma).mul(-2)));
	}

	/** logNormal(value, mu) = 1/mu * exp((log(value) - mu)^2 / -2) */
	public static Complex2d logNormal(Complex2d value, double mu) {
		return exp(sqr(log(value).sub(mu)).div(-2)).div(mu);
	}

	/** logNormal(value, mu) = 1/mu * exp((log(value) - mu)^2 / -2) */
	public static Complex2d logNormal(Complex2d value, Complex2d mu) {
		return exp(sqr(log(value).sub(mu)).div(-2)).div(mu);
	}

	/** butterfly(lhs, rhs) = (lhs.re^2 - lhs.im^2) * sin(lhs.re+lhs.im)/rhs) / mod(lhs) */
	public static Complex2d butterfly(Complex2d lhs, Complex2d rhs) {
		double a = lhs.real * lhs.real;
		double b = lhs.imag * lhs.imag;
		a = (a - b) / (a + b) * 0.5;
		double c = (lhs.real + lhs.imag) / (rhs.real * rhs.real + rhs.imag * rhs.imag);
		b = rhs.real * c;
		c *= rhs.imag;
		double d = Math.exp(-c);
		c = Math.exp(c);
		return new Complex2d((d + c) * Math.sin(b) * a, (d - c) * Math.cos(b) * a);
	}

// public static Complex2d normalDistribution() {
// // normalDistribution = exp(a^2 * -0.5)
// double re2 = Math.exp((im * im - re * re) * 0.5);
// double im2 = -re * im;
// re = re2 * Math.cos(im2);
// im = re2 * Math.sin(im2);
// }
//
// public static Complex2d logNormalDistribution() {
// // logNormalDistribution = exp(log(a)^2 * -0.5)
// double re2 = Math.log(re * re + im * im) * 0.5;
// double im2 = Math.atan2(im, re);
// double re3 = Math.exp((im2 * im2 - re2 * re2) * 0.5);
// double im3 = re2 * -im2;
// re = re3 * Math.cos(im3);
// im = re3 * Math.sin(im3);
// }
//
// public static Complex2d tetr2() {
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
// public static Complex2d tetr3() {
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

// /**
// * butterfly(a, b) = (a^2-b^2) * sin(a+b) / (a^2 + b^2)
// *
// * @param z
// * @return
// */
// public static Complex2d complexButterfly(Complex2d value) {
// Complex2d xx = sqr();
// Complex2d yy = value.sqr();
// return xx.sub(yy).mul(this.add(value).sin()).div(xx.add(yy));
// }
//
// /**
// * carotidKundalini(a, b) = cos(a * b * arccos(a))
// *
// * @param z
// * @return
// */
// public static Complex2d carotidKundalini(Complex2d value) {
// return acos().mul(this).mul(value).cos();
// }
//
// /**
// * hemispherical(a, b) = sqrt(a - mod(b))
// *
// * @param c
// * @return
// */
// public static Complex2d hemispherical(Complex2d value) {
// return value.sub(magnSquared()).sqrt();
// }
//
// /**
// * archimedianSpiral(a, b) = a * (1, exp(|a|^b.re * b.im))
// *
// * @param c
// * @return
// */
// public static Complex2d spiral(Complex2d value) {
// Complex2d exp = new Complex2d(1, Math.pow(magn(), value.re) * valuec.im).exp();
// return this.mul(exp);
// }
//
// /**
// * swirl(a, b) = a * (1, cos(log(|a|)*b.re) * b.im)
// *
// * @param c
// * @return
// */
// public static Complex2d swirl1(Complex2d value) {
// // Complex polar = this.toPolar();
// // polar.im += Math.cos(Math.log(polar.re) * value.re) * value.im;
// // return polar.toCartesian();
// Complex2d exp = new Complex2d(1, Math.cos(Math.log(magn()) * value.re) * value.im).exp();
// return this.mul(exp);
// }
//
// /**
// * swirl(a, b) = a * (1, cos(|a|*b.re) * b.im)
// *
// * @param c
// * @return
// */
// public static Complex2d swirl2(Complex2d value) {
// // Complex polar = this.toPolar();
// // polar.im += Math.cos(polar.re * value.re) * value.im;
// // return polar.toCartesian();
// Complex2d exp = new Complex2d(1, Math.cos(magn() * value.re) * value.im).exp();
// return this.mul(exp);
// }

// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// // Unary Other Functions
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// /**
// * fibonacci(a) = (theta^a + cos(a*PI)*theta^(-a)) / sqrt(5)
// *
// * @return
// */
// public static Complex2d fibonacci() {
// Complex2d temp = this.mul(NumberConstants.PHI).exp();
//
// return temp.sub(this.mul(NumberConstants.TAU05).cos().div(temp)).mul(NumberConstants.SQRT02);
// }

	/**
	 * nthFibonacci = (Phi^value -(-1/Phi)^value) / (Phi - (-1/Phi))
	 *
	 * @see <a href="https://youtu.be/cCXRUHUgvLI?si=DlN7ItL6JfU8g3Dg&t=1912">https://youtu.be/cCXRUHUgvLI?si=DlN7ItL6JfU8g3Dg&t=1912</a>
	 */
	public static Complex2d nthFibonacci(Complex2d value) {
		return pow(PHI, value).sub(pow(FIBONACCI_PHI1, value)).mul(SQRT02);
	}

// /**
// * einstein1(a) = a^2 * exp(a) / (exp(a) - 1)^2
// *
// * @return
// */
// public static Complex2d einstein1() {
// Complex2d exp = exp();
// return sqr().mul(exp).div(exp.sub(1).sqr());
// }
//
// /**
// * einstein2(a) = a / (exp(a) - 1)
// *
// * @return
// */
// public static Complex2d einstein2() {
// return this.div(exp().sub(1));
// }
//
// /**
// * einstein3(a) = log(1 - exp(-a))
// *
// * @return
// */
// public static Complex2d einstein3() {
// return neg().exp().subR(1).log();
// }
//
// /**
// * einstein4(a) = a / (exp(a) - 1) - log(1 - exp(-a))
// *
// * @return
// */
// public static Complex2d einstein4() {
// return this.div(exp().sub(1)).sub(neg().exp().subR(1).log());
// }
//
// /**
// * sigmoid(a) = 1 / (1 + exp(-a))
// *
// * @return
// */
// public static Complex2d sigmoid() {
// return neg().exp().add(1).recip();
// }
//
// /**
// * gaussRemainder(a) = 1/a - trunc(1/a)
// *
// * @return
// */
// public static Complex2d gaussRemainder() {
// Complex2d recip = recip();
// return recip.sub(recip.trunc());
// }
//
// /**
// * gaussModulo(a) = 1/a - floor(1/a)
// *
// * @return
// */
// public static Complex2d gaussModulo() {
// Complex2d recip = recip();
// return recip.sub(recip.floor());
// }
//
// /**
// * gaussModC(a) = 1/a - round(1/a)
// *
// * @return
// */
// public static Complex2d gaussModC() {
// Complex2d recip = recip();
// return recip.sub(recip.round());
// }
//
// /**
// * nearIdent(a) = 3*sin(a) / (cos(a) + 2)
// *
// * @return
// */
// public static Complex2d nearIdent() {
// return sin().mul(3).div(cos().add(2));
// }
//
// /**
// * nearIdent(a) = cos(a^2 * PI)
// *
// * @return
// */
// public static Complex2d sweep1() {
// return sqr().mul(NumberConstants.TAU05).cos();
// }
//
// /**
// * nearIdent(a) = cos(exp(a*ln(2)) * PI)
// *
// * @return
// */
// public static Complex2d sweep2() {
// return this.mul(NumberConstants.LOG2).exp().mul(NumberConstants.TAU05).cos();
// }
//
// /**
// * nearIdent(a) = cos(exp(a*ln(2)) * PI)
// *
// * @return
// */
// public static Complex2d tukeysWeight() {
// return sqr().subR(1).sqr().mul(this);
// }
//
// /**
// * polynom1(a) = a^3-1
// *
// * @return
// */
// public static Complex2d polynom1() {
// return cube().sub(1);
// }
//
// /**
// * polynom1(a) = a^5 - 1
// *
// * @return
// */
// public static Complex2d polynom2() {
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
// public static Complex2d rationalA22() {
// Complex2d z2 = sqr();
// return z2.sub(1).divR(z2);
// }
//
// /**
// * rationalA23(a) = a^2 / (a^3-1)
// *
// * @return
// */
// public static Complex2d rationalA23() {
// Complex2d z2 = sqr();
// return z2.mul(this).sub(1).divR(z2);
// }
//
// /**
// * rationalA32(a) = a^3 / (a^2-1)
// *
// * @return
// */
// public static Complex2d rationalA32() {
// Complex2d z2 = sqr();
// return z2.sub(1).divR(z2.mul(this));
// }
//
// /**
// * rationalA33(a) = a^3 / (a^3-1)
// *
// * @return
// */
// public static Complex2d rationalA33() {
// Complex2d z3 = cube();
// return z3.sub(1).divR(z3);
// }
//
// /**
// * rationalB22(a) = a^2 / (a^2-1)
// *
// * @return
// */
// public static Complex2d rationalB22() {
// Complex2d z2 = sqr();
// return z2.sub(1).divR(z2);
// }
//
// /**
// * rationalB23(a) = a^2 / (a^3-1)
// *
// * @return
// */
// public static Complex2d rationalB23() {
// Complex2d z2 = sqr();
// return z2.mul(this).sub(1).divR(z2);
// }
//
// /**
// * rationalB32(a) = a^3 / (a^2+1)
// *
// * @return
// */
// public static Complex2d rationalB32() {
// Complex2d z2 = sqr();
// return z2.add(1).divR(z2.mul(this));
// }
//
// /**
// * rationalB33(a) = a^3 / (a^3-1)
// *
// * @return
// */
// public static Complex2d rationalB33() {
// Complex2d z3 = cube();
// return z3.sub(1).divR(z3);
// }
//
// /**
// * rationalC32(a) = (a^3-1) / a^2
// *
// * @return
// */
// public static Complex2d rationalC32() {
// Complex2d z2 = sqr();
// return z2.divR(z2.mul(this).sub(1));
// }
//
// /**
// * rationalD32(a) = (a^3+1) / a^2
// *
// * @return
// */
// public static Complex2d rationalD32() {
// Complex2d z2 = sqr();
// return z2.add(1).divR(z2.mul(this).add(0, 0.2));
// }
//
// /**
// * rational1(a) = (a+4) / (a^5-3*i*a^3+2)
// *
// * @return
// */
// public static Complex2d rationalPoly3() {
// Complex2d zz = sqr();
// Complex2d zzz = zz.mul(this);
// return this.add(4).div(zzz.mul(zz).add(zzz.mul(0, -3)).add(2));
// }
}
