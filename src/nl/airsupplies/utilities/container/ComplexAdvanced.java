package nl.airsupplies.utilities.container;

import nl.airsupplies.utilities.annotation.UtilityClass;
import static nl.airsupplies.utilities.constant.NumberConstants.FIBONACCI_PHI1;
import static nl.airsupplies.utilities.constant.NumberConstants.PHI;
import static nl.airsupplies.utilities.constant.NumberConstants.SQRT02;
import static nl.airsupplies.utilities.container.ComplexMath.exp;
import static nl.airsupplies.utilities.container.ComplexMath.log;
import static nl.airsupplies.utilities.container.ComplexMath.pow;
import static nl.airsupplies.utilities.container.ComplexMath.recip;
import static nl.airsupplies.utilities.container.ComplexMath.sqr;

// https://mpmath.org/gallery/

/**
 * @author Mark Jeronimus
 */
// Created 2024-08-03 Split from Complex2d
@UtilityClass
public final class ComplexAdvanced {
	/** normal(z, sigma) = 1/sigma * exp(z^2 / (-2 * sigma^2)) */
	public static Complex2d normal(Complex2d z, double sigma) {
		return exp(sqr(z).mul(sigma)).div(-0.5 / (sigma * sigma));
	}

	/** normal(z, sigma) = 1/sigma * exp(z^2 / (-2 * sigma^2)) */
	public static Complex2d normal(Complex2d z, Complex2d sigma) {
		return exp(sqr(z).mul(sigma)).div(recip(sqr(sigma).mul(-2)));
	}

	/** logNormal(z, mu) = 1/mu * exp((log(z) - mu)^2 / -2) */
	public static Complex2d logNormal(Complex2d z, double mu) {
		return exp(sqr(log(z).sub(mu)).div(-2)).div(mu);
	}

	/** logNormal(z, mu) = 1/mu * exp((log(z) - mu)^2 / -2) */
	public static Complex2d logNormal(Complex2d z, Complex2d mu) {
		return exp(sqr(log(z).sub(mu)).div(-2)).div(mu);
	}

	/** butterfly(lhs, rhs) = (lhs.real^2 - lhs.imag^2) * sin(lhs.real+lhs.imag)/rhs) / mod(lhs) */
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
// double re2 = Math.exp((imag * imag - real * real) * 0.5);
// double im2 = -real * imag;
// real = re2 * Math.cos(im2);
// imag = re2 * Math.sin(im2);
// }
//
// public static Complex2d logNormalDistribution() {
// // logNormalDistribution = exp(log(a)^2 * -0.5)
// double re2 = Math.log(real * real + imag * imag) * 0.5;
// double im2 = Math.atan2(imag, real);
// double re3 = Math.exp((im2 * im2 - re2 * re2) * 0.5);
// double im3 = re2 * -im2;
// real = re3 * Math.cos(im3);
// imag = re3 * Math.sin(im3);
// }
//
// public static Complex2d tetr2() {
// // tetr2(a) = a^^2 (Knuth's arrow notation) = a^a
// // Avoid exp(log(0))=0 which uses -infinite as intermediate
// if (real == 0 && imag == 0) return;
//
// double re2 = Math.log(real * real + imag * imag) * 0.5;
// double im2 = Math.atan2(imag, real);
// double re3 = Math.exp(re2 * real - im2 * imag);
// double im3 = re2 * imag + im2 * real;
// real = re3 * Math.cos(im3);
// imag = re3 * Math.sin(im3);
// }
//
// public static Complex2d tetr3() {
// // tetr3(a) = a^^3 (Knuth's arrow notation) = a^(a^a)
// // Avoid exp(log(0))=0 which uses -infinite as intermediate
// if (real == 0 && imag == 0) return;
//
// double re2 = Math.log(real * real + imag * imag) * 0.5;
// double im2 = Math.atan2(imag, real);
// double re3 = Math.exp(re2 * real - im2 * imag);
// double im3 = re2 * imag + im2 * real;
// double re4 = re3 * Math.cos(im3);
// double im4 = re3 * Math.sin(im3);
// double re5 = Math.exp(re2 * re4 - im2 * im4);
// double im5 = re2 * im4 + im2 * re4;
// real = re5 * Math.cos(im5);
// imag = re5 * Math.sin(im5);
// }

// /**
// * butterfly(a, b) = (a^2-b^2) * sin(a+b) / (a^2 + b^2)
// *
// * @param z
// * @return
// */
// public static Complex2d complexButterfly(Complex2d value) {
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
// public static Complex2d carotidKundalini(Complex2d z) {
// return acos().mul(this).mul(z).cos();
// }
//
// /**
// * hemispherical(a, b) = sqrt(a - mod(b))
// *
// * @param z
// * @return
// */
// public static Complex2d hemispherical(Complex2d z) {
// return z.sub(magnSquared()).sqrt();
// }
//
// /**
// * archimedianSpiral(a, b) = a * (1, exp(|a|^b.real * b.imag))
// *
// * @param z
// * @return
// */
// public static Complex2d spiral(Complex2d z) {
// Complex2d exp = new Complex2d(1, Math.pow(magn(), z.real) * z.imag).exp();
// return this.mul(exp);
// }
//
// /**
// * swirl(lhs, rhs) = lhs * (1, cos(log(|lhs|)*rhs.real) * rhs.imag)
// *
// * @param z
// * @return
// */
// public static Complex2d swirl1(Complex2d z) {
// // Complex polar = this.toPolar();
// // polar.imag += Math.cos(Math.log(polar.real) * z.real) * z.imag;
// // return polar.toCartesian();
// Complex2d exp = new Complex2d(1, Math.cos(Math.log(magn()) * z.real) * z.imag).exp();
// return this.mul(exp);
// }
//
// /**
// * swirl(lhs, rhs) = lhs * (1, cos(|lhs|*rhs.real) * rhs.imag)
// *
// * @param z
// * @return
// */
// public static Complex2d swirl2(Complex2d z) {
// // Complex polar = this.toPolar();
// // polar.imag += Math.cos(polar.real * z.real) * z.imag;
// // return polar.toCartesian();
// Complex2d exp = new Complex2d(1, Math.cos(magn() * z.real) * z.imag).exp();
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
	public static Complex2d nthFibonacci(Complex2d z) {
		return pow(PHI, z).sub(pow(FIBONACCI_PHI1, z)).mul(SQRT02);
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
