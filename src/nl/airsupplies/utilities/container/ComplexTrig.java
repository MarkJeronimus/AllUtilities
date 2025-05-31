package nl.airsupplies.utilities.container;

import nl.airsupplies.utilities.annotation.UtilityClass;

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
public final class ComplexTrig {
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Unary Trigonometric Functions
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * sine = sin(z.imag)*cosh(z.real) + cos(z.imag)*sinh(z.real) * i
	 * = (exp(z*i) - exp(-z*i)) / (2*i)
	 */
	public static Complex2d sin(Complex2d z) {
		double a = Math.exp(z.imag) * 0.5;
		double b = Math.exp(-z.imag) * 0.5;
		return new Complex2d(Math.cos(z.real) * (a - b),
		                     Math.sin(z.real) * (a + b));
	}

	/** cosine = cos(z.real)*cosh(z.imag) - sin(z.real)*sinh(z.imag) * i = (exp(z*i) + exp(z*-i)) / 2 */
	public static Complex2d cos(Complex2d z) {
		double a = Math.exp(-z.imag) * 0.5;
		double b = Math.exp(z.imag) * 0.5;
		return new Complex2d(Math.sin(z.real) * (a - b),
		                     Math.cos(z.real) * (a + b));
	}

	/** tangent = (exp(2*value*i) - 1) / (i * (exp(2*value*i) + 1)) */
	public static Complex2d tan(Complex2d z) {
		double a = z.real + z.real;
		double b = z.imag + z.imag;
		double c = Math.exp(b);
		double d = 1 / c;
		double e = Math.cos(a) + (c + d) * 0.5;
		return new Complex2d(Math.sin(a) / e,
		                     (c - d) * 0.5 / e);
	}

// /** cotangent = (exp(2*a*i) + 1) / (exp(2*a*i) - 1) * i */
// public static Complex2d cot(Complex2d z) {
// double a = real + real;
// double b = imag + imag;
// double c = Math.exp(b) * 0.5;
// b = Math.exp(-b) * 0.5;
// double d = b + c - Math.cos(a);
// real = Math.sin(a) / d;
// imag = (b - c) / d;
// }
//
// /** secant = 2 / (exp(z*i) + exp(-z*i)) */
// public static Complex2d sec(Complex2d z) {
// // return this.mul(0, 1).exp().add(this.mul(0, -1).exp()).divR(2);
// double a = Math.exp(imag);
// double b = Math.exp(-imag);
// double c = (a + b) * Math.cos(real);
// a = (a - b) * Math.sin(real);
// b = (c * c + a * a) * 0.5;
// real = c / b;
// imag = a / b;
// }
//
// /** cosecant = 2*i / (exp(z*i) - exp(-z*i)) */
// public static Complex2d csc(Complex2d z) {
// // return this.rotCCW().exp().sub(this.rotCW().exp()).divR(0, 2);
// double a = Math.exp(-imag);
// double b = Math.exp(imag);
// double c = (a - b) * Math.cos(real);
// a = (a + b) * Math.sin(real);
// b = (c * c + a * a) * 0.5;
// real = a / b;
// imag = c / b;
// }

	/** arcsine = -log(sqrt(1-z^2) + z*i)*i */
	public static Complex2d asin(Complex2d z) {
		double re2  = 1 - z.real * z.real + z.imag * z.imag;
		double im2  = -2 * z.real * z.imag;
		double magn = Math.hypot(re2, im2);
		double im3  = z.real + Math.signum(im2) * Math.sqrt((magn - re2) * 0.5);
		double re3  = Math.sqrt((magn + re2) * 0.5) - z.imag;
		return new Complex2d(Math.atan2(im3, re3),
		                     Math.log(re3 * re3 + im3 * im3) * -0.5);
	}

	/** arccosine = -log(sqrt(1-z^2)*i + z)*i */
	public static Complex2d acos(Complex2d z) {
		double re2  = 1 - z.real * z.real + z.imag * z.imag;
		double im2  = -2 * z.real * z.imag;
		double magn = Math.hypot(re2, im2);
		double im3  = z.real - Math.signum(im2) * Math.sqrt((magn - re2) * 0.5);
		double re3  = Math.sqrt((magn + re2) * 0.5) + z.imag;
		return new Complex2d(Math.atan2(re3, im3),
		                     Math.log(re3 * re3 + im3 * im3) * -0.5);
	}

	/** arctangent = (log(1-z*i) - log(1+z*i))/2*i = log((1-z*i) / (1+z*i))/2*i */
	public static Complex2d atan(Complex2d z) {
		double re2         = 1 - z.imag;
		double re3         = 1 + z.imag;
		double magnSquared = re2 * re2 + z.real * z.real;
		double re4         = (re3 * re2 - z.real * z.real) / magnSquared;
		double im4         = -(z.real * re2 + re3 * z.real) / magnSquared;
		return new Complex2d(Math.atan2(im4, re4) * -0.5,
		                     Math.log(re4 * re4 + im4 * im4) * 0.25);
	}

// /** arccotangent = (log((1-i/a) / (1+i/a))) * i / 2 */
// public static Complex2d acot(Complex2d z) {
// // Complex temp = this.divR(0, 1);
// // return temp.subR(1).div(temp.add(1)).log().mul(0, 0.5);
// double a = real * real + imag * imag;
// double b = real / a;
// a = imag / a + 1;
// double c = a * a + b * b;
// double d = a / c;
// c = b / c;
// a = 2 - a;
// double e = a * d - b * c;
// a = -(b * d + a * c);
// real = Math.atan2(a, e) * -0.5;
// imag = Math.log(e * e + a * a) * 0.25;
// }
//
// /** arcsecant = log(sqrt(1-1/a^2)*i + 1/a) * -i */
// public static Complex2d asec(Complex2d z) {
// // return
// // this.sqr().recip().subR(1).sqrt().rotCCW().add(this.recip()).log().
// // rotCW();
// double a = imag * imag - real * real;
// double b = 2 * real * imag;
// double c = a * a + b * b;
// a = a / c + 1;
// b = b / c;
// c = Math.hypot(a, b);
// double d = real * real + imag * imag;
// b = (b >= 0? -Math.sqrt((c - a) * 0.5) : Math.sqrt((c - a) * 0.5)) + real / d;
// a = Math.sqrt((c + a) * 0.5) - imag / d;
// real = Math.atan2(a, b);
// imag = Math.log(a * a + b * b) * -0.5;
// }
//
// /** arccosecant = log(sqrt(1-1/a^2) + i/a) * -i */
// public static Complex2d acsc(Complex2d z) {
// // return this.sqr().recip().subR(1).sqrt().add(this.divR(0,
// // 1)).log().rotCW();
// double a = real * real - imag * imag;
// double b = 2 * real * imag;
// double c = a * a + b * b;
// a = 1 - a / c;
// b = b / c;
// c = Math.hypot(a, b);
// double d = real * real + imag * imag;
// b = (b < 0? -Math.sqrt((c - a) * 0.5) : Math.sqrt((c - a) * 0.5)) + real / d;
// a = Math.sqrt((c + a) * 0.5) + imag / d;
// real = Math.atan2(b, a);
// imag = Math.log(a * a + b * b) * -0.5;
// }

// /** sinec = sin(z)/z if x!=0, (1, 0) otherwise */
// public static Complex2d sinc(Complex2d z) {
// // lim(|z|->0) sinc(z) = 1
// if (real == 0 && imag == 0) return new Complex2d(1, 0);
//
// return sin().div(this);
// }
//
// /** cosinec = cos(z)/z */
// public static Complex2d cosc(Complex2d z) {
// return cos().div(this);
// }
//
// /** tangentc = tan(z)/z if x!=0, (1, 0) otherwise */
// public static Complex2d tanc(Complex2d z) {
// // lim(|z|->0) tanc(z) = 1
// if (real == 0 && imag == 0) return new Complex2d(1, 0);
//
// return tan().div(this);
// }
//
// /** cotangentc = cot(z)/z */
// public static Complex2d cotc(Complex2d z) {
// return cot().div(this);
// }
//
// /** secantc = sec(z)/z */
// public static Complex2d secc(Complex2d z) {
// return sec().div(this);
// }
//
// /** cosecantc = csc(z)/z */
// public static Complex2d cscc(Complex2d z) {
// return csc().div(this);
// }

// /** sinePI = sin(z*PI/2) */
// public static Complex2d sinPI(Complex2d z) {
// double a = real * NumberConstants.TAU025;
// double b = imag * NumberConstants.TAU025;
// double c = Math.exp(b) * 0.5;
// b = Math.exp(-b) * 0.5;
// return new Complex2d((c + b) * Math.sin(a), (c - b) * Math.cos(a));
// }
//
// /** cosinePI = cos(z*PI/2) */
// public static Complex2d cosPI(Complex2d z) {
// double a = real * NumberConstants.TAU025;
// double b = imag * NumberConstants.TAU025;
// double c = Math.exp(-b) * 0.5;
// b = Math.exp(b) * 0.5;
// return new Complex2d((c + b) * Math.cos(a), (c - b) * Math.sin(a));
// }
//
// /** tangentPI = tan(z*PI/2) */
// public static Complex2d tanPI(Complex2d z) {
// double a = real * NumberConstants.TAU05;
// double b = imag * NumberConstants.TAU05;
// double c = Math.exp(b);
// b = Math.exp(-b);
// double d = Math.cos(a) + (c + b) * 0.5;
// return new Complex2d(Math.sin(a) / d, (c - b) * 0.5 / d);
// }
//
// /** cotangentPI = cot(z*PI/2) */
// public static Complex2d cotPI(Complex2d z) {
// double a = real * NumberConstants.TAU05;
// double b = imag * NumberConstants.TAU05;
// double c = Math.exp(b) * 0.5;
// b = Math.exp(-b) * 0.5;
// double d = b + c - Math.cos(a);
// return new Complex2d(Math.sin(a) / d, (b - c) / d);
// }
//
// /** secantPI = sec(z*PI/2) */
// public static Complex2d secPI(Complex2d z) {
// double a = real * NumberConstants.TAU025;
// double b = imag * NumberConstants.TAU025;
// double c = Math.exp(b);
// b = Math.exp(-b);
// double d = (c + b) * Math.cos(a);
// a = (c - b) * Math.sin(a);
// b = (d * d + a * a) * 0.5;
// return new Complex2d(d / b, a / b);
// }
//
// /** cosecantPI = csc(z*PI/2) */
// public static Complex2d cscPI(Complex2d z) {
// double a = real * NumberConstants.TAU025;
// double b = imag * NumberConstants.TAU025;
// double c = Math.exp(-b);
// b = Math.exp(b);
// double d = (c - b) * Math.cos(a);
// a = (c + b) * Math.sin(a);
// b = (d * d + a * a) * 0.5;
// return new Complex2d(a / b, d / b);
// }

// /** arcsinePI = asin(z)/(PI/2) */
// public static Complex2d asinPI(Complex2d z) {
// // return this.sqr().subR(1).sqrt().add(this.rotCCW()).log().rotCW();
// double a = 1 - real * real + imag * imag;
// double b = -2 * real * imag;
// double c = Math.hypot(a, b);
// b = (b < 0? -Math.sqrt((c - a) * 0.5) : Math.sqrt((c - a) * 0.5)) + real;
// a = Math.sqrt((c + a) * 0.5) - imag;
// return new Complex2d(Math.atan2(b, a) * NumberConstants.Q4TAU,
// Math.log(a * a + b * b) * -NumberConstants.Q2TAU);
// }
//
// /** arccosinePI = acos(z)/(PI/2) */
// public static Complex2d acosPI(Complex2d z) {
// // return this.sqr().subR(1).sqrt().rotCCW().add(this).log().rotCW();
// double a = 1 - real * real + imag * imag;
// double b = -2 * real * imag;
// double c = Math.hypot(a, b);
// b = real - (b < 0? -Math.sqrt((c - a) * 0.5) : Math.sqrt((c - a) * 0.5));
// a = Math.sqrt((c + a) * 0.5) + imag;
// return new Complex2d(Math.atan2(a, b) * NumberConstants.Q4TAU,
// Math.log(a * a + b * b) * -NumberConstants.Q2TAU);
// }
//
// /** arctanPI = atan(z)/(PI/2) */
// public static Complex2d atanPI(Complex2d z) {
// double a = 1 - imag;
// double b = real;
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
// /** arccotangentPI = acot(z)/(PI/2) */
// public static Complex2d acotPI(Complex2d z) {
// double a = real * real + imag * imag;
// double b = real / a;
// a = imag / a + 1;
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
// /** arcsecantPI = arcsec(a)/(PI/2) */
// public static Complex2d asecPI(Complex2d z) {
// double a = imag * imag - real * real;
// double b = 2 * real * imag;
// double c = a * a + b * b;
// a = a / c + 1;
// b = b / c;
// c = Math.hypot(a, b);
// double d = real * real + imag * imag;
// b = (b >= 0? -Math.sqrt((c - a) * 0.5) : Math.sqrt((c - a) * 0.5)) + real / d;
// a = Math.sqrt((c + a) * 0.5) - imag / d;
// return new Complex2d(Math.atan2(a, b) * NumberConstants.Q4TAU,
// Math.log(a * a + b * b) * -NumberConstants.Q2TAU);
// }
//
// /** arccosecantPI = acsc(a)/(PI/2) */
// public static Complex2d acscPI(Complex2d z) {
// double a = real * real - imag * imag;
// double b = 2 * real * imag;
// double c = a * a + b * b;
// a = 1 - a / c;
// b = b / c;
// c = Math.hypot(a, b);
// double d = real * real + imag * imag;
// b = (b < 0? -Math.sqrt((c - a) * 0.5) : Math.sqrt((c - a) * 0.5)) + real / d;
// a = Math.sqrt((c + a) * 0.5) + imag / d;
// return new Complex2d(Math.atan2(b, a) * NumberConstants.Q4TAU,
// Math.log(a * a + b * b) * -NumberConstants.Q2TAU);
// }

// /** sinecPI = sin(z*PI/2)/(z*PI/2) if x!=0, (1, 0) otherwise */
// public static Complex2d sincPI(Complex2d z) {
// // lim(|z|->0) sinc(z) = 1
// if (real == 0 && imag == 0) return new Complex2d(1, 0);
//
// Complex2d thisPI = this.mul(NumberConstants.TAU025);
// return thisPI.sin().div(thisPI);
// }
//
// /** cosinecPI = cos(z*PI/2)/(z*PI/2) */
// public static Complex2d coscPI(Complex2d z) {
// Complex2d thisPI = this.mul(NumberConstants.TAU025);
// return thisPI.cos().div(thisPI);
// }
//
// /** tangentcPI = tan(z*PI/2)/(z*PI/2) if x!=0, (1, 0) otherwise */
// public static Complex2d tancPI(Complex2d z) {
// // lim(|z|->0) tanc(z) = 1
// if (real == 0 && imag == 0) return new Complex2d(1, 0);
//
// Complex2d thisPI = this.mul(NumberConstants.TAU025);
// return thisPI.tan().div(thisPI);
// }
//
// /** cotangentcPI = cot(z*PI/2)/(z*PI/2) */
// public static Complex2d cotcPI(Complex2d z) {
// Complex2d thisPI = this.mul(NumberConstants.TAU025);
// return thisPI.cot().div(thisPI);
// }
//
// /** secantcPI = sec(z*PI/2)/(z*PI/2) */
// public static Complex2d seccPI(Complex2d z) {
// Complex2d thisPI = this.mul(NumberConstants.TAU025);
// return thisPI.sec().div(thisPI);
// }
//
// /** cosecantcPI = csc(z*PI/2)/(z*PI/2) */
// public static Complex2d csccPI(Complex2d z) {
// Complex2d thisPI = this.mul(NumberConstants.TAU025);
// return thisPI.csc().div(thisPI);
// }

// // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// // Unary Hyperbolic Functions
// // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

// /** sineh = (exp(z) - exp(-z))/2 */
// public static Complex2d sinh(Complex2d z) {
// double ex = Math.exp(real);
// double exn = Math.exp(-real);
// return new Complex2d((ex - exn) * 0.5 * Math.cos(imag), (ex + exn) * 0.5 * Math.sin(imag));
// }
//
// /** arcsineh = log(sqrt(z^2+1)+z) */
// public static Complex2d asinh(Complex2d z) {
// Complex2d out = sqr();
// out.real++;
// out.sqrt();
// out.add(this);
// out.log();
// return out;
// }
//
// /** cosineh = (exp(z) + exp(-z))/2 */
// public static Complex2d cosh(Complex2d z) {
// double ep = Math.exp(real) * 0.5;
// double en = Math.exp(-real) * 0.5;
// return new Complex2d(Math.cos(imag) * (ep + en), Math.sin(imag) * (ep - en));
// }
//
// /** arccosineh = log(sqrt(z-1)*sqrt(z+1)+z) */
// public static Complex2d acosh(Complex2d z) {
// // https://web.archive.org/web/20121008211504/http://www.mathworks.com/company/newsletters/news_notes/clevescorner/sum98cleve.html
// // not: acosh(x) = log(x ± (x2 - 1)^0.5) but:
// // acosh(z) = 2*log(((z + 1)/2)^0.5 + ((z - 1)/2)^0.5)
// return this.add(1).sqrt().mul(sub(1).sqrt()).add(this).log();
// }
//
// /** tangenth = (exp(2*z) - 1) / (exp(2*z) + 1) */
// public static Complex2d tanh(Complex2d z) {
// double a = real + real;
// double b = imag + imag;
// double c = Math.exp(a);
// a = Math.exp(-a);
// double d = (c + a) / 2 + Math.cos(b);
// return new Complex2d((c - a) * 0.5 / d, Math.sin(b) / d);
// }
//
// /** arctangenth = (log(1+z) - log(1-z))/2 */
// public static Complex2d atanh(Complex2d z) {
// return new Complex2d(1 + real, imag).div(new Complex2d(1 - real, -imag)).log().mul(0.5);
// }
//
// /** cotangenth = (exp(2*z) + 1) / (exp(2*z) - 1) */
// public static Complex2d coth(Complex2d z) {
// double a = real + real;
// double b = imag + imag;
// double c = Math.exp(a) * 0.5;
// a = Math.exp(-a) * 0.5;
// double d = c + a - Math.cos(b);
// return new Complex2d((c - a) / d, -Math.sin(b) / d);
// }
//
// /** arccotangenth = (log(1+1/z) - log(1-1/z))/2 */
// public static Complex2d acoth(Complex2d z) {
// Complex2d inv = recip();
// return inv.add(1).log().sub(inv.subR(1).log()).mul(0.5);
// // return this.flip().conjugate().acot().flip().conjugate();
// }
//
// /** secanth = 2 / (exp(z) + exp(-z)) */
// public static Complex2d sech(Complex2d z) {
// return exp().add(neg().exp()).divR(2);
// }
//
// /** arcsecanth = log(sqrt(1/z-1)*sqrt(1/z+1)+1/z) */
// public static Complex2d asech(Complex2d z) {
// Complex2d inv = recip();
// return inv.sub(1).sqrt().mul(inv.add(1).sqrt()).add(inv).log();
// // return this.flip().asec().flip();
// }
//
// /** cosecanth = 2 / (exp(z) - exp(-z)) */
// public static Complex2d csch(Complex2d z) {
// return exp().sub(neg().exp()).divR(2);
// }
//
// /** arccosecanth = log(sqrt(1/z^2+1)+1/z) */
// public static Complex2d acsch(Complex2d z) {
// return sqr().recip().add(1).sqrt().add(recip()).log();
// // return this.flip().acsc().flip();
// }

// /** sinehPI = sinh(z*PI/2) */
// public static Complex2d sinhPI(Complex2d z) {
// return this.mul(NumberConstants.TAU025).sinh();
// }
//
// /** arcsinehPI = asinh(z)/(PI/2) */
// public static Complex2d asinhPI(Complex2d z) {
// return asinh().mul(NumberConstants.Q4TAU);
// }
//
// /** cosinehPI = cosh(z*PI/2) */
// public static Complex2d coshPI(Complex2d z) {
// return this.mul(NumberConstants.TAU025).cosh();
// }
//
// /** arccosinehPI = acosh(z)/(PI/2) */
// public static Complex2d acoshPI(Complex2d z) {
// return acosh().mul(NumberConstants.Q4TAU);
// }
//
// /** tangenthPI = tanh(z*PI/2) */
// public static Complex2d tanhPI(Complex2d z) {
// return this.mul(NumberConstants.TAU025).tanh();
// }
//
// /** arctangenthPI = atanh(z)/(PI/2) */
// public static Complex2d atanhPI(Complex2d z) {
// return atanh().mul(NumberConstants.Q4TAU);
// }
//
// /** cotangenthPI = coth(z*PI/2) */
// public static Complex2d cothPI(Complex2d z) {
// return this.mul(NumberConstants.TAU025).coth();
// }
//
// /** arccotangenthPI = acoth(z)/(PI/2) */
// public static Complex2d acothPI(Complex2d z) {
// return acoth().mul(NumberConstants.Q4TAU);
// }
//
// /** secanthPI = sech(z*PI/2) */
// public static Complex2d sechPI(Complex2d z) {
// return this.mul(NumberConstants.TAU025).sech();
// }
//
// /** arcsecanthPI = asech(z)/(PI/2) */
// public static Complex2d asechPI(Complex2d z) {
// return asech().mul(NumberConstants.Q4TAU);
// }
//
// /** cosecanthPI = csch(z*PI/2) */
// public static Complex2d cschPI(Complex2d z) {
// return this.mul(NumberConstants.TAU025).csch();
// }
//
// /** arccosecanthPI = acsch(z)/(PI/2) */
// public static Complex2d acschPI(Complex2d z) {
// return acsch().mul(NumberConstants.Q4TAU);
// }

// /** sinehc = sinh(z)/z if x!=0, (1, 0) otherwise */
// public static Complex2d sinhc(Complex2d z) {
// // lim(|z|->0) sinhc(z) = 1
// if (real == 0 && imag == 0) return new Complex2d(1, 0);
// return sinh().div(this);
// }
//
// /** cosinehc = cosh(z)/z */
// public static Complex2d coshc(Complex2d z) {
// return cosh().div(this);
// }
//
// /** tangenthc = tanhc(z)/z if x!=0, (1, 0) otherwise */
// public static Complex2d tanhc(Complex2d z) {
// // lim(|z|->0) tanhc(z) = 1
// if (real == 0 && imag == 0) return new Complex2d(1, 0);
// return tanh().div(this);
// }
//
// /** cotangenthc = coth(z)/z */
// public static Complex2d cothc(Complex2d z) {
// return coth().div(this);
// }
//
// /** secanthc = sech(z)/z */
// public static Complex2d sechc(Complex2d z) {
// return sech().div(this);
// }
//
// /** cosecanthc = csch(z)/z */
// public static Complex2d cschc(Complex2d z) {
// return csch().div(this);
// }

// /** sinehcPI = sinh(z*PI/2)/(z*PI/2) if x!=0, (1, 0) otherwise */
// public static Complex2d sinhcPI(Complex2d z) {
// // lim(|z|->0) sinc(z) = 1
// if (real == 0 && imag == 0) return new Complex2d(1, 0);
// Complex2d thisPI = this.mul(NumberConstants.TAU025);
// return thisPI.sinh().div(thisPI);
// }
//
// /** cosinehcPI = cosh(z*PI/2)/(z*PI/2) */
// public static Complex2d coshcPI(Complex2d z) {
// Complex2d thisPI = this.mul(NumberConstants.TAU025);
// return thisPI.cosh().div(thisPI);
// }
//
// /** tangenthcPI = tanh(z*PI/2)/(z*PI/2) if x!=0, (1, 0) otherwise */
// public static Complex2d tanhcPI(Complex2d z) {
// // lim(|z|->0) tanc(z) = 1
// if (real == 0 && imag == 0) return new Complex2d(1, 0);
// Complex2d thisPI = this.mul(NumberConstants.TAU025);
// return thisPI.tanh().div(thisPI);
// }
//
// /** cotangentchPI = coth(z*PI/2)/(z*PI/2) */
// public static Complex2d cothcPI(Complex2d z) {
// Complex2d thisPI = this.mul(NumberConstants.TAU025);
// return thisPI.coth().div(thisPI);
// }
//
// /** secanthcPI = sech(z*PI/2)/(z*PI/2) */
// public static Complex2d sechcPI(Complex2d z) {
// Complex2d thisPI = this.mul(NumberConstants.TAU025);
// return thisPI.sech().div(thisPI);
// }
//
// /** cosecanthcPI = csch(z*PI/2)/(z*PI/2) */
// public static Complex2d cschcPI(Complex2d z) {
// Complex2d thisPI = this.mul(NumberConstants.TAU025);
// return thisPI.csch().div(thisPI);
// }

// /** fibonacciHyperbolicSine = 2/sqrt(5) * sinh((2*z) * log(theta)) */
// public static Complex2d sinFh(Complex2d z) {
// return this.mul(2).mul(NumberConstants.LOGPHI).sinh().mul(NumberConstants.SQRT08);
// }
//
// /** fibonacciHyperbolicCosine = 2/sqrt(5) * cosh((2*z + 1) * log(theta)) */
// public static Complex2d cosFh(Complex2d z) {
// return this.mul(2).add(1).mul(NumberConstants.LOGPHI).cosh().mul(NumberConstants.SQRT08);
// }
//
// /** fibonacciHyperbolicTangent = sinFh(z) / cosFh(z) */
// public static Complex2d tanFh(Complex2d z) {
// return sinFh().div(cosFh());
// }
//
// /** fibonacciHyperbolicCotangent = 1/tanFh(z) */
// public static Complex2d cotFh(Complex2d z) {
// return cosFh().div(sinFh());
// }
//
// /** fibonacciHyperbolicSecant = 1/cosFh(z) */
// public static Complex2d secFh(Complex2d z) {
// return cosFh().recip();
// }
//
// /** fibonacciHyperbolicCosecant = 1/sinFh(z) */
// public static Complex2d cscFh(Complex2d z) {
// return sinFh().recip();
// }

// // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// // Unary Classic Trigonometric Fucntions
// // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

// /** versine = 1 - cos(z) */
// public static Complex2d versin(Complex2d z) {
// return cos().subR(1);
// }
//
// /** arcversine = acos(1 - z) */
// public static Complex2d aversin(Complex2d z) {
// return this.subR(1).acos();
// }
//
// /** vercosine = 1 - sin(z) */
// public static Complex2d vercos(Complex2d z) {
// return sin().subR(1);
// }
//
// /** arcvercosine = asin(1 - z) */
// public static Complex2d avercos(Complex2d z) {
// return this.subR(1).asin();
// }
//
// /** vertangent = 1 - cot(z) */
// public static Complex2d vertan(Complex2d z) {
// return cot().subR(1);
// }
//
// /** arcvertangent = acot(1 - z) */
// public static Complex2d avertan(Complex2d z) {
// return this.subR(1).acot();
// }
//
// /** vercotangent = 1 - tan(z) */
// public static Complex2d vercot(Complex2d z) {
// return tan().subR(1);
// }
//
// /** arcvercotangent = atan(1 - z) */
// public static Complex2d avercot(Complex2d z) {
// return this.subR(1).atan();
// }
//
// /** versecant = 1 - csc(z) */
// public static Complex2d versec(Complex2d z) {
// return csc().subR(1);
// }
//
// /** arcversecant = acsc(1 - z) */
// public static Complex2d aversec(Complex2d z) {
// return this.subR(1).acsc();
// }
//
// /** vercosecant = 1 - sec(z) */
// public static Complex2d vercsc(Complex2d z) {
// return sec().subR(1);
// }
//
// /** arcvercosecant = asec(1 - z) */
// public static Complex2d avercsc(Complex2d z) {
// return this.subR(1).asec();
// }
//
// /** haversine = 0.5 * (1 - cos(z)) */
// public static Complex2d haversin(Complex2d z) {
// return cos().subR(1).mul(0.5);
// }
//
// /** archaversine = acos(1 - 2*z) */
// public static Complex2d ahaversin(Complex2d z) {
// return this.mul(2).subR(1).acos();
// }
//
// /** havercosine = 0.5 * (1 - sin(z)) */
// public static Complex2d havercos(Complex2d z) {
// return sin().subR(1).mul(0.5);
// }
//
// /** archavercosine = asin(1 - 2*z) */
// public static Complex2d ahavercos(Complex2d z) {
// return this.mul(2).subR(1).asin();
// }
//
// /** havertangent = 0.5 * (1 - cot(z)) */
// public static Complex2d havertan(Complex2d z) {
// return cot().subR(1).mul(0.5);
// }
//
// /** archavertangent = acot(1 - 2*z) */
// public static Complex2d ahavertan(Complex2d z) {
// return this.mul(2).subR(1).acot();
// }
//
// /** havercotangent = 0.5 * (1 - tan(z)) */
// public static Complex2d havercot(Complex2d z) {
// return tan().subR(1).mul(0.5);
// }
//
// /** archavercotangent = atan(1 - 2*z) */
// public static Complex2d ahavercot(Complex2d z) {
// return this.mul(2).subR(1).atan();
// }
//
// /** haversecant = 0.5 * (1 - csc(z)) */
// public static Complex2d haversec(Complex2d z) {
// return csc().subR(1).mul(0.5);
// }
//
// /** archaversecant = acsc(1 - 2*z) */
// public static Complex2d ahaversec(Complex2d z) {
// return this.mul(2).subR(1).acsc();
// }
//
// /** havercosecant = 0.5 * (1 - sec(z)) */
// public static Complex2d havercsc(Complex2d z) {
// return sec().subR(1).mul(0.5);
// }
//
// /** archavercosecant = asec(1 - 2*z) */
// public static Complex2d ahavercsc(Complex2d z) {
// return this.mul(2).subR(1).asec();
// }
//
// /** exsine = sin(z) - 1 */
// public static Complex2d exsin(Complex2d z) {
// return sin().sub(1);
// }
//
// /** arcexsine = asin(z + 1) */
// public static Complex2d aexsin(Complex2d z) {
// return this.add(1).asin();
// }
//
// /** excosine = cos(z) - 1 */
// public static Complex2d excos(Complex2d z) {
// return cos().sub(1);
// }
//
// /** arcexcosine = acos(z + 1) */
// public static Complex2d aexcos(Complex2d z) {
// return this.add(1).acos();
// }
//
// /** extangent = tan(z) - 1 */
// public static Complex2d extan(Complex2d z) {
// return tan().sub(1);
// }
//
// /** arcextangent = atan(z + 1) */
// public static Complex2d aextan(Complex2d z) {
// return this.add(1).atan();
// }
//
// /** excotangent = cot(z) - 1 */
// public static Complex2d excot(Complex2d z) {
// return cot().sub(1);
// }
//
// /** arcexcotangent = acot(z + 1) */
// public static Complex2d aexcot(Complex2d z) {
// return this.add(1).acot();
// }
//
// /** exsecant = sec(z) - 1 */
// public static Complex2d exsec(Complex2d z) {
// return sec().sub(1);
// }
//
// /** arcexsecant = asec(z + 1) */
// public static Complex2d aexsec(Complex2d z) {
// return this.add(1).asec();
// }
//
// /** excosecant = csc(z) - 1 */
// public static Complex2d excsc(Complex2d z) {
// return csc().sub(1);
// }
//
// /** arcexcosecant = acsc(z + 1) */
// public static Complex2d aexcsc(Complex2d z) {
// return this.add(1).acsc();
// }
//
// /** gdsine = 2 * asin(sinh(z/2)) */
// public static Complex2d gdsin(Complex2d z) {
// return this.mul(0.5).sinh().asin().mul(2);
// }
//
// /** arcgdsine = 2 * asinh(sin(z/2)) */
// public static Complex2d agdsin(Complex2d z) {
// return this.mul(0.5).sin().asinh().mul(2);
// }
//
// /** gdcosine = 2 * acos(cosh(z/2)) */
// public static Complex2d gdcos(Complex2d z) {
// return this.mul(0.5).cosh().acos().mul(2);
// }
//
// /** arcgdcosine = 2 * acosh(cos(z/2)) */
// public static Complex2d agdcos(Complex2d z) {
// return this.mul(0.5).cos().acosh().mul(2);
// }
//
// /** gudermannian = 2 * atan(tanh(z/2)) */
// public static Complex2d gdtan(Complex2d z) {
// return this.mul(0.5).tanh().atan().mul(2);
// }
//
// /** arcgudermannian = 2 * atanh(tan(z/2)) */
// public static Complex2d agdtan(Complex2d z) {
// return this.mul(0.5).tan().atanh().mul(2);
// }
//
// /** gdcotangent = 2 * acot(coth(z/2)) */
// public static Complex2d gdcot(Complex2d z) {
// return this.mul(0.5).coth().acot().mul(2);
// }
//
// /** arcgdcotangent = 2 * acoth(cot(z/2)) */
// public static Complex2d agdcot(Complex2d z) {
// return this.mul(0.5).cot().acoth().mul(2);
// }
//
// /** gdsecant = 2 * asec(sech(z/2)) */
// public static Complex2d gdsec(Complex2d z) {
// return this.mul(0.5).sech().asec().mul(2);
// }
//
// /** arcgdsece = 2 * asech(sec(z/2)) */
// public static Complex2d agdsec(Complex2d z) {
// return this.mul(0.5).sec().asech().mul(2);
// }
//
// /** gdcosecant = 2 * acsc(csch(z/2)) */
// public static Complex2d gdcsc(Complex2d z) {
// return this.mul(0.5).csch().acsc().mul(2);
// }
//
// /** arcgdcsce = 2 * acsch(csc(z/2)) */
// public static Complex2d agdcsc(Complex2d z) {
// return this.mul(0.5).csc().acsch().mul(2);
// }
}
