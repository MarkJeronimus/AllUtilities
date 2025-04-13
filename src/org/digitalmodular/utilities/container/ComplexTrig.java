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

// https://mpmath.org/gallery/

// http://mathworld.wolfram.com/topics/TrigonometricFunctions.html
// http://mathworld.wolfram.com/topics/HyperbolicFunctions.html
// http://en.wikipedia.org/wiki/Trigonometric_function
// http://en.wikipedia.org/wiki/Inverse_trigonometric_functions
// http://en.wikipedia.org/wiki/Hyperbolic_function
// http://en.wikipedia.org/wiki/Inverse_hyperbolic_function

/**
 * @author Mark Jeronimus
 */
// Created 2024-08-02 Split from Complex2d
// Unary Trigonometric Functions
// Unary Hyperbolic Functions
@UtilityClass
public final class ComplexTrig implements Serializable {

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Unary Trigonometric Functions
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * sin = sin(value.imag)*cosh(value.real) + cos(value.imag)*sinh(value.real) * i
	 * = (exp(value*i) - exp(-value*i)) / (2*i)
	 */
	public static Complex2d sin(Complex2d value) {
		double a = Math.exp(value.imag) * 0.5;
		double b = Math.exp(-value.imag) * 0.5;
		return new Complex2d(Math.cos(value.real) * (a - b),
		                     Math.sin(value.real) * (a + b));
	}

	/** cos = cos(value.real)*cosh(value.imag) - sin(value.real)*sinh(value.imag) * i = (exp(value*i) + exp(value*-i)) / 2 */
	public static Complex2d cos(Complex2d value) {
		double a = Math.exp(-value.imag) * 0.5;
		double b = Math.exp(value.imag) * 0.5;
		return new Complex2d(Math.sin(value.real) * (a - b),
		                     Math.cos(value.real) * (a + b));
	}

	/** tan = (exp(2*value*i) - 1) / (i * (exp(2*value*i) + 1)) */
	public static Complex2d tan(Complex2d z) {
		double a = z.real + z.real;
		double b = z.imag + z.imag;
		double c = Math.exp(b);
		double d = 1 / c;
		double e = Math.cos(a) + (c + d) * 0.5;
		return new Complex2d(Math.sin(a) / e,
		                     (c - d) * 0.5 / e);
	}

// /** cotan = (exp(2*a*i) + 1) / (exp(2*a*i) - 1) * i */
// public static Complex2d cot(Complex2d z) {
// double a = re + re;
// double b = im + im;
// double c = Math.exp(b) * 0.5;
// b = Math.exp(-b) * 0.5;
// double d = b + c - Math.cos(a);
// re = Math.sin(a) / d;
// im = (b - c) / d;
// }
//
// /** secant = 2 / (exp(a*i) + exp(-a*i)) */
// public static Complex2d sec(Complex2d z) {
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
// /** cosecant = 2*i / (exp(a*i) - exp(-a*i)) */
// public static Complex2d csc(Complex2d z) {
// // return this.rotCCW().exp().sub(this.rotCW().exp()).divR(0, 2);
// double a = Math.exp(-im);
// double b = Math.exp(im);
// double c = (a - b) * Math.cos(re);
// a = (a + b) * Math.sin(re);
// b = (c * c + a * a) * 0.5;
// re = a / b;
// im = c / b;
// }

	/** asin = -log(sqrt(1-value^2) + value*i)*i */
	public static Complex2d asin(Complex2d value) {
		double re2  = 1 - value.real * value.real + value.imag * value.imag;
		double im2  = -2 * value.real * value.imag;
		double magn = Math.hypot(re2, im2);
		double im3  = value.real + Math.signum(im2) * Math.sqrt((magn - re2) * 0.5);
		double re3  = Math.sqrt((magn + re2) * 0.5) - value.imag;
		return new Complex2d(Math.atan2(im3, re3),
		                     Math.log(re3 * re3 + im3 * im3) * -0.5);
	}

	/** acos = -log(sqrt(1-value^2)*i + value)*i */
	public static Complex2d acos(Complex2d value) {
		double re2  = 1 - value.real * value.real + value.imag * value.imag;
		double im2  = -2 * value.real * value.imag;
		double magn = Math.hypot(re2, im2);
		double im3  = value.real - Math.signum(im2) * Math.sqrt((magn - re2) * 0.5);
		double re3  = Math.sqrt((magn + re2) * 0.5) + value.imag;
		return new Complex2d(Math.atan2(re3, im3),
		                     Math.log(re3 * re3 + im3 * im3) * -0.5);
	}

	/** atan = (log(1-value*i) - log(1+value*i))/2*i = log((1-value*i) / (1+value*i))/2*i */
	public static Complex2d atan(Complex2d value) {
		double re2         = 1 - value.imag;
		double re3         = 1 + value.imag;
		double magnSquared = re2 * re2 + value.real * value.real;
		double re4         = (re3 * re2 - value.real * value.real) / magnSquared;
		double im4         = -(value.real * re2 + re3 * value.real) / magnSquared;
		return new Complex2d(Math.atan2(im4, re4) * -0.5,
		                     Math.log(re4 * re4 + im4 * im4) * 0.25);
	}

// /** acot = arccotan = (log((1-i/a) / (1+i/a))) * i / 2 */
// public static Complex2d acot(Complex2d z) {
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
// /** asec = arcsecant = log(sqrt(1-1/a^2)*i + 1/a) * -i */
// public static Complex2d asec(Complex2d z) {
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
// /** acsc = arccosecant = log(sqrt(1-1/a^2) + i/a) * -i */
// public static Complex2d acsc(Complex2d z) {
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
// * sinc = sinec = sin(a)/a if x!=0, (1, 0) otherwise
// *
// * @return
// */
// public static Complex2d () {
// // lim(|z|->0) sinc(z) = 1
// if (re == 0 && im == 0) return new Complex2d(1, 0);
//
// return sin().div(this);
// }
//
// /**
// * cosc = cosinec = cos(a)/a
// *
// * @return
// */
// public static Complex2d () {
// return cos().div(this);
// }
//
// /**
// * tanc = tangentc = tan(a)/a if x!=0, (1, 0) otherwise
// *
// * @return
// */
// public static Complex2d () {
// // lim(|z|->0) tanc(z) = 1
// if (re == 0 && im == 0) return new Complex2d(1, 0);
//
// return tan().div(this);
// }
//
// /**
// * cotc = cotanc = cot(a)/a
// *
// * @return
// */
// public static Complex2d () {
// return cot().div(this);
// }
//
// /**
// * secc = secantc = sec(a)/a
// *
// * @return
// */
// public static Complex2d () {
// return sec().div(this);
// }
//
// /**
// * cscc = cosecantc = csc(a)/a
// *
// * @return
// */
// public static Complex2d () {
// return csc().div(this);
// }
//
// /**
// * sinePI = sin(a*PI/2)
// *
// * @return
// */
// public static Complex2d sinPI() {
// double a = re * NumberConstants.TAU025;
// double b = im * NumberConstants.TAU025;
// double c = Math.exp(b) * 0.5;
// b = Math.exp(-b) * 0.5;
// return new Complex2d((c + b) * Math.sin(a), (c - b) * Math.cos(a));
// }
//
// /** sinePI = sin(a*PI/2) */
// public static Complex2d sinPI(Complex2d z) {
// double a = re * NumberConstants.TAU025;
// double b = im * NumberConstants.TAU025;
// double c = Math.exp(b) * 0.5;
// b = Math.exp(-b) * 0.5;
// re = (c + b) * Math.sin(a);
// im = (c - b) * Math.cos(a);
// }
//
// /**
// * cosinePI = cos(a*PI/2)
// *
// * @return
// */
// public static Complex2d cosPI() {
// double a = re * NumberConstants.TAU025;
// double b = im * NumberConstants.TAU025;
// double c = Math.exp(-b) * 0.5;
// b = Math.exp(b) * 0.5;
// return new Complex2d((c + b) * Math.cos(a), (c - b) * Math.sin(a));
// }
//
// /** cosinePI = cos(a*PI/2) */
// public static Complex2d cosPI(Complex2d z) {
// double a = re * NumberConstants.TAU025;
// double b = im * NumberConstants.TAU025;
// double c = Math.exp(-b) * 0.5;
// b = Math.exp(b) * 0.5;
// re = (c + b) * Math.cos(a);
// im = (c - b) * Math.sin(a);
// }
//
// /**
// * tangentPI = tan(a*PI/2)
// *
// * @return
// */
// public static Complex2d tanPI() {
// double a = re * NumberConstants.TAU05;
// double b = im * NumberConstants.TAU05;
// double c = Math.exp(b);
// b = Math.exp(-b);
// double d = Math.cos(a) + (c + b) * 0.5;
// return new Complex2d(Math.sin(a) / d, (c - b) * 0.5 / d);
// }
//
// /** tangentPI = tan(a*PI/2) */
// public static Complex2d tanPI(Complex2d z) {
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
// * cotanPI = cot(a*PI/2)
// *
// * @return
// */
// public static Complex2d cotPI() {
// double a = re * NumberConstants.TAU05;
// double b = im * NumberConstants.TAU05;
// double c = Math.exp(b) * 0.5;
// b = Math.exp(-b) * 0.5;
// double d = b + c - Math.cos(a);
// return new Complex2d(Math.sin(a) / d, (b - c) / d);
// }
//
// /** cotanPI = cot(a*PI/2) */
// public static Complex2d cotPI(Complex2d z) {
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
// * secantPI = sec(a*PI/2)
// *
// * @return
// */
// public static Complex2d secPI() {
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
// /** secantPI = sec(a*PI/2) */
// public static Complex2d secPI(Complex2d z) {
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
// * cosecantPI = csc(a*PI/2)
// *
// * @return
// */
// public static Complex2d cscPI() {
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
// /** cosecantPI = csc(a*PI/2) */
// public static Complex2d cscPI(Complex2d z) {
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
// * arcsinePI = asin(a)/(PI/2)
// *
// * @return
// */
// public static Complex2d asinPI() {
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
// /** arcsinePI = asin(a)/(PI/2) */
// public static Complex2d asinPI(Complex2d z) {
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
// * arccosinePI = acos(a)/(PI/2)
// *
// * @return
// */
// public static Complex2d acosPI() {
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
// /** arccosinePI = acos(a)/(PI/2) */
// public static Complex2d acosPI(Complex2d z) {
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
// * arctanPI = atan(a)/(PI/2)
// *
// * @return
// */
// public static Complex2d atanPI() {
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
// /** arctanPI = atan(a)/(PI/2) */
// public static Complex2d atanPI(Complex2d z) {
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
// * arccotanPI = acot(a)/(PI/2)
// *
// * @return
// */
// public static Complex2d acotPI() {
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
// /** arccotanPI = acot(a)/(PI/2) */
// public static Complex2d acotPI(Complex2d z) {
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
// * arcsecantPI = arcsec(a)/(PI/2)
// *
// * @return
// */
// public static Complex2d asecPI() {
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
// /** arcsecantPI = arcsec(a)/(PI/2) */
// public static Complex2d asecPI(Complex2d z) {
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
// * arccosecantPI = acsc(a)/(PI/2)
// *
// * @return
// */
// public static Complex2d acscPI() {
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
// /** arccosecantPI = acsc(a)/(PI/2) */
// public static Complex2d acscPI(Complex2d z) {
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
// * sinecPI = sin(a*PI/2)/(a*PI/2) if x!=0, (1, 0) otherwise
// *
// * @return
// */
// public static Complex2d sincPI() {
// // lim(|z|->0) sinc(z) = 1
// if (re == 0 && im == 0) return new Complex2d(1, 0);
//
// Complex2d thisPI = this.mul(NumberConstants.TAU025);
// return thisPI.sin().div(thisPI);
// }
//
// /**
// * cosinecPI = cos(a*PI/2)/(a*PI/2)
// *
// * @return
// */
// public static Complex2d coscPI() {
// Complex2d thisPI = this.mul(NumberConstants.TAU025);
// return thisPI.cos().div(thisPI);
// }
//
// /**
// * tangentcPI = tan(a*PI/2)/(a*PI/2) if x!=0, (1, 0) otherwise
// *
// * @return
// */
// public static Complex2d tancPI() {
// // lim(|z|->0) tanc(z) = 1
// if (re == 0 && im == 0) return new Complex2d(1, 0);
//
// Complex2d thisPI = this.mul(NumberConstants.TAU025);
// return thisPI.tan().div(thisPI);
// }
//
// /**
// * cotancPI = cot(a*PI/2)/(a*PI/2)
// *
// * @return
// */
// public static Complex2d cotcPI() {
// Complex2d thisPI = this.mul(NumberConstants.TAU025);
// return thisPI.cot().div(thisPI);
// }
//
// /**
// * secantcPI = sec(a*PI/2)/(a*PI/2)
// *
// * @return
// */
// public static Complex2d seccPI() {
// Complex2d thisPI = this.mul(NumberConstants.TAU025);
// return thisPI.sec().div(thisPI);
// }
//
// /**
// * cosecantcPI = csc(a*PI/2)/(a*PI/2)
// *
// * @return
// */
// public static Complex2d csccPI() {
// Complex2d thisPI = this.mul(NumberConstants.TAU025);
// return thisPI.csc().div(thisPI);
// }

// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// // Unary Hyperbolic Functions
// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// /**
// * sineh = (exp(a) - exp(-a))/2
// *
// * @return
// */
// public static Complex2d sinh() {
// double ex = Math.exp(re);
// double exn = Math.exp(-re);
//
// return new Complex2d((ex - exn) * 0.5 * Math.cos(im), (ex + exn) * 0.5 * Math.sin(im));
// }
//
// /** sineh = (exp(a) - exp(-a))/2 */
// public static Complex2d sinh(Complex2d z) {
// double ex = Math.exp(re);
// double exn = Math.exp(-re);
//
// re = (ex - exn) * 0.5 * Math.cos(im);
// im = (ex + exn) * 0.5 * Math.sin(im);
// }
//
// /**
// * arcsineh = log(sqrt(a^2+1)+a)
// *
// * @return
// */
// public static Complex2d asinh() {
// Complex2d out = sqr();
// out.re++;
// out.sqrt();
// out.add(this);
// out.log();
// return out;
// }
//
// /**
// * cosineh = (exp(a) + exp(-a))/2
// *
// * @return
// */
// public static Complex2d cosh() {
// double ep = Math.exp(re) * 0.5;
// double en = Math.exp(-re) * 0.5;
//
// return new Complex2d(Math.cos(im) * (ep + en), Math.sin(im) * (ep - en));
// }
//
// /**
// * arccosineh = log(sqrt(a-1)*sqrt(a+1)+a)
// *
// * @return
// */
// public static Complex2d acosh() {
// // https://web.archive.org/web/20121008211504/http://www.mathworks.com/company/newsletters/news_notes/clevescorner/sum98cleve.html
// // not: acosh(x) = log(x ± (x2 - 1)^0.5) but:
// // acosh(z) = 2*log(((z + 1)/2)^0.5 + ((z - 1)/2)^0.5)
// return this.add(1).sqrt().mul(sub(1).sqrt()).add(this).log();
// }
//
// /**
// * tangenth = (exp(2*a) - 1) / (exp(2*a) + 1)
// *
// * @return
// */
// public static Complex2d tanh() {
// double a = re + re;
// double b = im + im;
// double c = Math.exp(a);
// a = Math.exp(-a);
// double d = (c + a) / 2 + Math.cos(b);
// return new Complex2d((c - a) * 0.5 / d, Math.sin(b) / d);
// }
//
// /** tangenth = (exp(2*a) - 1) / (exp(2*a) + 1) */
// public static Complex2d tanh(Complex2d z) {
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
// * arctangenth = (log(1+a) - log(1-a))/2
// *
// * @return
// */
// public static Complex2d atanh() {
// return new Complex2d(1 + re, im).div(new Complex2d(1 - re, -im)).log().mul(0.5);
// }
//
// /**
// * cotanh = (exp(2*a) + 1) / (exp(2*a) - 1)
// *
// * @return
// */
// public static Complex2d coth() {
// double a = re + re;
// double b = im + im;
// double c = Math.exp(a) * 0.5;
// a = Math.exp(-a) * 0.5;
// double d = c + a - Math.cos(b);
// return new Complex2d((c - a) / d, -Math.sin(b) / d);
// }
//
// /**
// * arccotanh = (log(1+1/a) - log(1-1/a))/2
// *
// * @return
// */
// public static Complex2d acoth() {
// Complex2d inv = recip();
// return inv.add(1).log().sub(inv.subR(1).log()).mul(0.5);
// // return this.flip().conjugate().acot().flip().conjugate();
// }
//
// /**
// * secanth = 2 / (exp(a) + exp(-a))
// *
// * @return
// */
// public static Complex2d sech() {
// return exp().add(neg().exp()).divR(2);
// }
//
// /**
// * arcsecanth = log(sqrt(1/a-1)*sqrt(1/a+1)+1/a)
// *
// * @return
// */
// public static Complex2d asech() {
// Complex2d inv = recip();
// return inv.sub(1).sqrt().mul(inv.add(1).sqrt()).add(inv).log();
// // return this.flip().asec().flip();
// }
//
// /**
// * cosecanth = 2 / (exp(a) - exp(-a))
// *
// * @return
// */
// public static Complex2d csch() {
// return exp().sub(neg().exp()).divR(2);
// }
//
// /**
// * arccosecanth = log(sqrt(1/a^2+1)+1/a)
// *
// * @return
// */
// public static Complex2d acsch() {
// return sqr().recip().add(1).sqrt().add(recip()).log();
// // return this.flip().acsc().flip();
// }
//
// /**
// * sinehPI = sinh(a*PI/2)
// *
// * @return
// */
// public static Complex2d sinhPI() {
// return this.mul(NumberConstants.TAU025).sinh();
// }
//
// /**
// * arcsinehPI = asinh(a)/(PI/2)
// *
// * @return
// */
// public static Complex2d asinhPI() {
// return asinh().mul(NumberConstants.Q4TAU);
// }
//
// /**
// * cosinehPI = cosh(a*PI/2)
// *
// * @return
// */
// public static Complex2d coshPI() {
// return this.mul(NumberConstants.TAU025).cosh();
// }
//
// /**
// * arccosinehPI = acosh(a)/(PI/2)
// *
// * @return
// */
// public static Complex2d acoshPI() {
// return acosh().mul(NumberConstants.Q4TAU);
// }
//
// /**
// * tangenthPI = tanh(a*PI/2)
// *
// * @return
// */
// public static Complex2d tanhPI() {
// return this.mul(NumberConstants.TAU025).tanh();
// }
//
// /**
// * arctangenthPI = atanh(a)/(PI/2)
// *
// * @return
// */
// public static Complex2d atanhPI() {
// return atanh().mul(NumberConstants.Q4TAU);
// }
//
// /**
// * cotanhPI = coth(a*PI/2)
// *
// * @return
// */
// public static Complex2d cothPI() {
// return this.mul(NumberConstants.TAU025).coth();
// }
//
// /**
// * arccotanhPI = acoth(a)/(PI/2)
// *
// * @return
// */
// public static Complex2d acothPI() {
// return acoth().mul(NumberConstants.Q4TAU);
// }
//
// /**
// * secanthPI = sech(a*PI/2)
// *
// * @return
// */
// public static Complex2d sechPI() {
// return this.mul(NumberConstants.TAU025).sech();
// }
//
// /**
// * arcsecanthPI = asech(a)/(PI/2)
// *
// * @return
// */
// public static Complex2d asechPI() {
// return asech().mul(NumberConstants.Q4TAU);
// }
//
// /**
// * cosecanthPI = csch(a*PI/2)
// *
// * @return
// */
// public static Complex2d cschPI() {
// return this.mul(NumberConstants.TAU025).csch();
// }
//
// /**
// * arccosecanthPI = acsch(a)/(PI/2)
// *
// * @return
// */
// public static Complex2d acschPI() {
// return acsch().mul(NumberConstants.Q4TAU);
// }
//
// /**
// * sinehc = sinh(a)/a if x!=0, (1, 0) otherwise
// *
// * @return
// */
// public static Complex2d sinhc() {
// // lim(|z|->0) sinhc(z) = 1
// if (re == 0 && im == 0) return new Complex2d(1, 0);
//
// return sinh().div(this);
// }
//
// /**
// * cosinehc = cosh(a)/a
// *
// * @return
// */
// public static Complex2d coshc() {
// return cosh().div(this);
// }
//
// /**
// * tangenthc = tanhc(a)/a if x!=0, (1, 0) otherwise
// *
// * @return
// */
// public static Complex2d tanhc() {
// // lim(|z|->0) tanhc(z) = 1
// if (re == 0 && im == 0) return new Complex2d(1, 0);
//
// return tanh().div(this);
// }
//
// /**
// * cotanhc = coth(a)/a
// *
// * @return
// */
// public static Complex2d cothc() {
// return coth().div(this);
// }
//
// /**
// * secanthc = sech(a)/a
// *
// * @return
// */
// public static Complex2d sechc() {
// return sech().div(this);
// }
//
// /**
// * cosecanthc = csch(a)/a
// *
// * @return
// */
// public static Complex2d cschc() {
// return csch().div(this);
// }
//
// /**
// * sinehcPI = sinh(a*PI/2)/(a*PI/2) if x!=0, (1, 0) otherwise
// *
// * @return
// */
// public static Complex2d sinhcPI() {
// // lim(|z|->0) sinc(z) = 1
// if (re == 0 && im == 0) return new Complex2d(1, 0);
//
// Complex2d thisPI = this.mul(NumberConstants.TAU025);
// return thisPI.sinh().div(thisPI);
// }
//
// /**
// * cosinehcPI = cosh(a*PI/2)/(a*PI/2)
// *
// * @return
// */
// public static Complex2d coshcPI() {
// Complex2d thisPI = this.mul(NumberConstants.TAU025);
// return thisPI.cosh().div(thisPI);
// }
//
// /**
// * tangenthcPI = tanh(a*PI/2)/(a*PI/2) if x!=0, (1, 0) otherwise
// *
// * @return
// */
// public static Complex2d tanhcPI() {
// // lim(|z|->0) tanc(z) = 1
// if (re == 0 && im == 0) return new Complex2d(1, 0);
//
// Complex2d thisPI = this.mul(NumberConstants.TAU025);
// return thisPI.tanh().div(thisPI);
// }
//
// /**
// * cotanchPI = coth(a*PI/2)/(a*PI/2)
// *
// * @return
// */
// public static Complex2d cothcPI() {
// Complex2d thisPI = this.mul(NumberConstants.TAU025);
// return thisPI.coth().div(thisPI);
// }
//
// /**
// * secanthcPI = sech(a*PI/2)/(a*PI/2)
// *
// * @return
// */
// public static Complex2d sechcPI() {
// Complex2d thisPI = this.mul(NumberConstants.TAU025);
// return thisPI.sech().div(thisPI);
// }
//
// /**
// * cosecanthcPI = csch(a*PI/2)/(a*PI/2)
// *
// * @return
// */
// public static Complex2d cschcPI() {
// Complex2d thisPI = this.mul(NumberConstants.TAU025);
// return thisPI.csch().div(thisPI);
// }
//
// /**
// * fibonacciHyperbolicSine = 2/sqrt(5) * sinh((2*a) * log(theta))
// *
// * @return
// */
// public static Complex2d sinFh() {
// return this.mul(2).mul(NumberConstants.LOGPHI).sinh().mul(NumberConstants.SQRT08);
// }
//
// /**
// * fibonacciHyperbolicCosine = 2/sqrt(5) * cosh((2*a + 1) * log(theta))
// *
// * @return
// */
// public static Complex2d cosFh() {
// return this.mul(2).add(1).mul(NumberConstants.LOGPHI).cosh().mul(NumberConstants.SQRT08);
// }
//
// /**
// * fibonacciHyperbolicTangent = sinFh(a) / cosFh(a)
// *
// * @return
// */
// public static Complex2d tanFh() {
// return sinFh().div(cosFh());
// }
//
// /**
// * fibonacciHyperbolicCotangent = 1/tanFh(a)
// *
// * @return
// */
// public static Complex2d cotFh() {
// return cosFh().div(sinFh());
// }
//
// /**
// * fibonacciHyperbolicSecant = 1/cosFh(a)
// *
// * @return
// */
// public static Complex2d secFh() {
// return cosFh().recip();
// }
//
// /**
// * fibonacciHyperbolicCosecant = 1/sinFh(a)
// *
// * @return
// */
// public static Complex2d cscFh() {
// return sinFh().recip();
// }
//
// //
// // Unary Classic Trigonometric Fucntions
// //
// /**
// * versine = 1 - cos(a)
// *
// * @return
// */
// public static Complex2d versin() {
// return cos().subR(1);
// }
//
// /**
// * arcversine = acos(1 - a)
// *
// * @return
// */
// public static Complex2d aversin() {
// return this.subR(1).acos();
// }
//
// /**
// * vercosine = 1 - sin(a)
// *
// * @return
// */
// public static Complex2d vercos() {
// return sin().subR(1);
// }
//
// /**
// * arcvercosine = asin(1 - a)
// *
// * @return
// */
// public static Complex2d avercos() {
// return this.subR(1).asin();
// }
//
// /**
// * vertangent = 1 - cot(a)
// *
// * @return
// */
// public static Complex2d vertan() {
// return cot().subR(1);
// }
//
// /**
// * arcvertangent = acot(1 - a)
// *
// * @return
// */
// public static Complex2d avertan() {
// return this.subR(1).acot();
// }
//
// /**
// * vercotan = 1 - tan(a)
// *
// * @return
// */
// public static Complex2d vercot() {
// return tan().subR(1);
// }
//
// /**
// * arcvercotan = atan(1 - a)
// *
// * @return
// */
// public static Complex2d avercot() {
// return this.subR(1).atan();
// }
//
// /**
// * versecant = 1 - csc(a)
// *
// * @return
// */
// public static Complex2d versec() {
// return csc().subR(1);
// }
//
// /**
// * arcversecant = acsc(1 - a)
// *
// * @return
// */
// public static Complex2d aversec() {
// return this.subR(1).acsc();
// }
//
// /**
// * vercosecant = 1 - sec(a)
// *
// * @return
// */
// public static Complex2d vercsc() {
// return sec().subR(1);
// }
//
// /**
// * arcvercosecant = asec(1 - a)
// *
// * @return
// */
// public static Complex2d avercsc() {
// return this.subR(1).asec();
// }
//
// /**
// * haversine = 0.5 * (1 - cos(a))
// *
// * @return
// */
// public static Complex2d haversin() {
// return cos().subR(1).mul(0.5);
// }
//
// /**
// * archaversine = acos(1 - 2*a)
// *
// * @return
// */
// public static Complex2d ahaversin() {
// return this.mul(2).subR(1).acos();
// }
//
// /**
// * havercosine = 0.5 * (1 - sin(a))
// *
// * @return
// */
// public static Complex2d havercos() {
// return sin().subR(1).mul(0.5);
// }
//
// /**
// * archavercosine = asin(1 - 2*a)
// *
// * @return
// */
// public static Complex2d ahavercos() {
// return this.mul(2).subR(1).asin();
// }
//
// /**
// * havertangent = 0.5 * (1 - cot(a))
// *
// * @return
// */
// public static Complex2d havertan() {
// return cot().subR(1).mul(0.5);
// }
//
// /**
// * archavertangent = acot(1 - 2*a)
// *
// * @return
// */
// public static Complex2d ahavertan() {
// return this.mul(2).subR(1).acot();
// }
//
// /**
// * havercotan = 0.5 * (1 - tan(a))
// *
// * @return
// */
// public static Complex2d havercot() {
// return tan().subR(1).mul(0.5);
// }
//
// /**
// * archavercotan = atan(1 - 2*a)
// *
// * @return
// */
// public static Complex2d ahavercot() {
// return this.mul(2).subR(1).atan();
// }
//
// /**
// * haversecant = 0.5 * (1 - csc(a))
// *
// * @return
// */
// public static Complex2d haversec() {
// return csc().subR(1).mul(0.5);
// }
//
// /**
// * archaversecant = acsc(1 - 2*a)
// *
// * @return
// */
// public static Complex2d ahaversec() {
// return this.mul(2).subR(1).acsc();
// }
//
// /**
// * havercosecant = 0.5 * (1 - sec(a))
// *
// * @return
// */
// public static Complex2d havercsc() {
// return sec().subR(1).mul(0.5);
// }
//
// /**
// * archavercosecant = asec(1 - 2*a)
// *
// * @return
// */
// public static Complex2d ahavercsc() {
// return this.mul(2).subR(1).asec();
// }
//
// /**
// * exsine = sin(a) - 1
// *
// * @return
// */
// public static Complex2d exsin() {
// return sin().sub(1);
// }
//
// /**
// * arcexsine = asin(a + 1)
// *
// * @return
// */
// public static Complex2d aexsin() {
// return this.add(1).asin();
// }
//
// /**
// * excosine = cos(a) - 1
// *
// * @return
// */
// public static Complex2d excos() {
// return cos().sub(1);
// }
//
// /**
// * arcexcosine = acos(a + 1)
// *
// * @return
// */
// public static Complex2d aexcos() {
// return this.add(1).acos();
// }
//
// /**
// * extangent = tan(a) - 1
// *
// * @return
// */
// public static Complex2d extan() {
// return tan().sub(1);
// }
//
// /**
// * arcextangent = atan(a + 1)
// *
// * @return
// */
// public static Complex2d aextan() {
// return this.add(1).atan();
// }
//
// /**
// * excotan = cot(a) - 1
// *
// * @return
// */
// public static Complex2d excot() {
// return cot().sub(1);
// }
//
// /**
// * arcexcotan = acot(a + 1)
// *
// * @return
// */
// public static Complex2d aexcot() {
// return this.add(1).acot();
// }
//
// /**
// * exsecant = sec(a) - 1
// *
// * @return
// */
// public static Complex2d exsec() {
// return sec().sub(1);
// }
//
// /**
// * arcexsecant = asec(a + 1)
// *
// * @return
// */
// public static Complex2d aexsec() {
// return this.add(1).asec();
// }
//
// /**
// * excosecant = csc(a) - 1
// *
// * @return
// */
// public static Complex2d excsc() {
// return csc().sub(1);
// }
//
// /**
// * arcexcosecant = acsc(a + 1)
// *
// * @return
// */
// public static Complex2d aexcsc() {
// return this.add(1).acsc();
// }
//
// /**
// * gdsine = 2 * asin(sinh(a/2))
// *
// * @return
// */
// public static Complex2d gdsin() {
// return this.mul(0.5).sinh().asin().mul(2);
// }
//
// /**
// * arcgdsine = 2 * asinh(sin(a/2))
// *
// * @return
// */
// public static Complex2d agdsin() {
// return this.mul(0.5).sin().asinh().mul(2);
// }
//
// /**
// * gdcosine = 2 * acos(cosh(a/2))
// *
// * @return
// */
// public static Complex2d gdcos() {
// return this.mul(0.5).cosh().acos().mul(2);
// }
//
// /**
// * arcgdcosine = 2 * acosh(cos(a/2))
// *
// * @return
// */
// public static Complex2d agdcos() {
// return this.mul(0.5).cos().acosh().mul(2);
// }
//
// /**
// * gudermannian = 2 * atan(tanh(a/2))
// *
// * @return
// */
// public static Complex2d gdtan() {
// return this.mul(0.5).tanh().atan().mul(2);
// }
//
// /**
// * arcgudermannian = 2 * atanh(tan(a/2))
// *
// * @return
// */
// public static Complex2d agdtan() {
// return this.mul(0.5).tan().atanh().mul(2);
// }
//
// /**
// * gdcotan = 2 * acot(coth(a/2))
// *
// * @return
// */
// public static Complex2d gdcot() {
// return this.mul(0.5).coth().acot().mul(2);
// }
//
// /**
// * arcgdcotan = 2 * acoth(cot(a/2))
// *
// * @return
// */
// public static Complex2d agdcot() {
// return this.mul(0.5).cot().acoth().mul(2);
// }
//
// /**
// * gdsecant = 2 * asec(sech(a/2))
// *
// * @return
// */
// public static Complex2d gdsec() {
// return this.mul(0.5).sech().asec().mul(2);
// }
//
// /**
// * arcgdsece = 2 * asech(sec(a/2))
// *
// * @return
// */
// public static Complex2d agdsec() {
// return this.mul(0.5).sec().asech().mul(2);
// }
//
// /**
// * gdcosecant = 2 * acsc(csch(a/2))
// *
// * @return
// */
// public static Complex2d gdcsc() {
// return this.mul(0.5).csch().acsc().mul(2);
// }
//
// /**
// * arcgdcsce = 2 * acsch(csc(a/2))
// *
// * @return
// */
// public static Complex2d agdcsc() {
// return this.mul(0.5).csc().acsch().mul(2);
// }
}
