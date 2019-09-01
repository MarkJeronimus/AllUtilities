/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2019 Mark Jeronimus. All Rights Reversed.
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
package org.digitalmodular.utilities.constant;

/**
 * @author Mark Jeronimus
 */
// Created 2009-04-26
public interface NumberConstants {
	/*
	 * The notation for formula constants is in Polish notation, with some symbols replaced:
	 * Addition: P.
	 * Multiplication: no symbol.
	 * Division (x/y): Q X Y.
	 * Negation: N
	 * Exponentiation: POW.
	 * Square (2): SQR.
	 * Decimal separator: remove, but leave leading zero.
	 * Named constants: upper case, no spaces.
	 * EXAMPLES:
	 *   ln(Tau*0.5) = LOGTAU05
	 *   ln(Tau)*0.5 = QLOGTAU2
	 *   EULERGAMMA
	 */

	double Q1_3      = 0.3333333333333333333333333; // (1/3)
	double Q1_6      = 0.1666666666666666666666666; // (2/3)
	double POW2_025  = 1.1892071150027210667174999; // (2^(1/4))
	double POW2Q1_12 = 1.0594630943592952645618252;    // (2^(1/12)) Chromatic base

	double SQRTQ1_12  = 0.2886751345948128822545743; // (sqrt(1/12) = 1/sqrt(12) = sqrt(12)/12 = sqrt(3)/6)
	double SQRTQ1_10  = 0.3162277660168379331998893; // (sqrt(1/10) = 1/sqrt(10) = sqrt(10)/10)
	double SQRT02     = 0.4472135954999579392818347; // (sqrt(1/5) = 1/sqrt(5) = sqrt(5)/5)
	double SQRTQ1_3   = 0.5773502691896257645091487; // (sqrt(1/3) = 1/sqrt(3) = sqrt(3)/5)
	double SQRT05     = 0.7071067811865475244008443; // (sqrt(1/2) = 1/sqrt(2) = sqrt(2)/2) = cos(Tau/8)
	double SQRT075    = 0.8660254037844386467637231; // (sqrt(3/4) = 1/sqrt(4/3) = sqrt(3)/2 = sin(Tau/3))
	double SQRT08     = 0.8944271909999158785636694; // (sqrt(4/5) = 1/sqrt(5/4) = sqrt(5)/2.5 = 2/sqrt(5))
	double SQRT2      = 1.4142135623730950488016887; // (sqrt(2))
	double SQRT3      = 1.7320508075688772935274463; // (sqrt(3))
	double SQRT5      = 2.2360679774997896964091736; // (sqrt(5))
	double SQRT10     = 3.1622776601683793319988935; // (sqrt(10))
	double SQRT12     = 3.4641016151377545870548926; // (sqrt(12) = 2*sqrt(3))
	double SQRTTAU05  = 1.7724538509055160272981674; // (sqrt(Tau/2) = gamma(1/2))
	double SQRTTAU025 = 1.2533141373155002512078826; // (sqrt(Tau/4))

	double CBRT2 = 1.2599210498948731647672106; // (cbrt(2))
	double CBRT3 = 1.4422495703074083823216383; // (cbrt(3))

	double LOGPHI     = 0.4812118250596034474977589; // (ln(Phi))
	double LOG2       = 0.6931471805599453094172321; // (ln(2))
	double LOG3       = 1.0986122886681096913952452; // (ln(3))
	double LOG10      = 2.3025850929940456840179914; // (ln(10))
	double Q1LOG10    = 0.4342944819032518276511289; // (1/ln(10) = log(e))
	double Q1LOG2     = 1.4426950408889634073599246; // (1/ln(2))
	double Q05LOG10   = 0.2171472409516259138255644; // (1/2/ln(10))
	double Q05LOG2    = 0.7213475204444817036799623; // (1/2/ln(2))
	double QLOG10LOG2 = 3.3219280948873623478703194; // (ln(10)/ln(2) = 1/log(2))
	double NLOGLOG2   = 0.3665129205816643270124391; // (-ln(ln(2)))

	double TAU0125     = 0.7853981633974483096156608; // (Tau/8)
	double TAU025      = 1.5707963267948966192313216; // (Tau/4)
	double TAU0375     = 2.3561944901923449288469825; // (Tau*3/8)
	double TAU05       = 3.1415926535897932384626433; // (Tau/2)
	double TAU075      = 4.7123889803846898576939650; // (Tau*3/4)
	double TAU         = 6.2831853071795864769252867; // (Tau)
	double TAU2        = 12.566370614359172953850573; // (Tau*2)
	double Q1TAU       = 0.1591549430918953357688837; // (1/Tau)
	double Q2TAU       = 0.3183098861837906715377675; // (2/Tau)
	double Q4TAU       = 0.6366197723675813430755350; // (4/Tau)
	double QTAU360     = 0.0174532925199432957692369; // (Tau/360)
	double Q360TAU     = 57.295779513082320876798154; // (360/Tau)
	double SQRTAU05    = 9.8696044010893586188344909; // ((Tau/2)^2)
	double Q1SQRTTAU   = 0.3989422804014326779399460; // (1/sqrt(Tau)) Used for normal distribution
	double LOGTAU05    = 1.1447298858494001741434273; // (ln(Tau/2))
	double EXP_TAU0125 = 2.1932800507380154565597696; // (e^(Tau/8))

	double E     = 2.7182818284590452353602874; // (e)
	double Q1E   = 0.3678794411714423215955237; // (1/e)
	double E_SQR = 7.3890560989306502272304274; // (e^2)

	double PHI           = 1.6180339887498948482045868; // (Phi) Golden Ratio
	double Q1LOGPHI      = 2.0780869212350275376013226; // (1/ln(Phi))
	double P1SQRT2       = 2.4142135623730950488016887; // (1+sqrt(2)) Silver Ratio
	double TRIBONACCI    = 1.8392867552141611325518525; // (Root of x^−3 + x - 2 or x^3 - x^2 - x - 1 = 0)
	double PLASTICNUMBER = 1.3247179572447460259609088; // (Root of x3 − x − 1 = 0)

	double SIN1 = 0.8414709848078965066525023; // (sin(1))
	double COS1 = 0.5403023058681397174009366; // (cos(1))

	double EULERGAMMA     = 0.5772156649015328606065120; // (Gamma) (Euler–Mascheroni constant)
	double EXP_EULERGAMMA = 1.7810724179901979852365041; // (e^Gamma)

	double CATALAN   = 0.9159655941772190150546035; // (Catalan's constant)
	double ZETA3     = 1.2020569031595942853997381; // (Apéry's constant)
	double GAMMAQ2_3 = 1.3541179394264004169452880; // (gamma(2/3))
	double GAMMAQ1_3 = 2.6789385347077476336556929; // (gamma(1/3))

	double STANDARDCONFIDENCE1 = 0.6826894921370858971704650; // (erf(1/sqrt(2)))
	double STANDARDCONFIDENCE2 = 0.9544997361036415855994347; // (erf(2/sqrt(2)))
	double STANDARDCONFIDENCE3 = 0.9973002039367398109466963; // (erf(3/sqrt(2)))
	double STANDARDCONFIDENCE4 = 0.9999366575163337601574924; // (erf(4/sqrt(2)))
	double STANDARDCONFIDENCE5 = 0.9999994266968562416121766; // (erf(5/sqrt(2)))
	double STANDARDCONFIDENCE6 = 0.9999999980268247099246037; // (erf(6/sqrt(2)))
}
