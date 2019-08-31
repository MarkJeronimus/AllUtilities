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

/**
 * @author Mark Jeronimus
 */
// Created 2006-10-30
public interface DoubleDoubleFunctions {
	enum UnarySelf {
		normalize {
			@Override
			public void exec(DoubleDouble dd) {
				dd.normalizeSelf();
			}
		},
		round {
			@Override
			public void exec(DoubleDouble dd) {
				dd.roundSelf();
			}
		},
		floor {
			@Override
			public void exec(DoubleDouble dd) {
				dd.floorSelf();
			}
		},
		ceil {
			@Override
			public void exec(DoubleDouble dd) {
				dd.ceilSelf();
			}
		},
		trunc {
			@Override
			public void exec(DoubleDouble dd) {
				dd.truncSelf();
			}
		},
		neg {
			@Override
			public void exec(DoubleDouble dd) {
				dd.negSelf();
			}
		},
		abs {
			@Override
			public void exec(DoubleDouble dd) {
				dd.absSelf();
			}
		},
		recip {
			@Override
			public void exec(DoubleDouble dd) {
				dd.recipSelf();
			}
		},
		sqr {
			@Override
			public void exec(DoubleDouble dd) {
				dd.sqrSelf();
			}
		},
		sqrt {
			@Override
			public void exec(DoubleDouble dd) {
				dd.sqrtSelf();
			}
		},
		sqrtFast {
			@Override
			public void exec(DoubleDouble dd) {
				dd.sqrtSelfFast();
			}
		},
		exp {
			@Override
			public void exec(DoubleDouble dd) {
				dd.expSelf();
			}
		},
		log {
			@Override
			public void exec(DoubleDouble dd) {
				dd.logSelf();
			}
		};

		public abstract void exec(DoubleDouble dd);
	}

	enum Unary {
		clone {
			@Override
			public DoubleDouble exec(int i) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public DoubleDouble exec(DoubleDouble dd) {
				return new DoubleDouble(dd);
			}
		},
		normalize {
			@Override
			public DoubleDouble exec(int i) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public DoubleDouble exec(DoubleDouble dd) {
				return dd.normalize();
			}
		},
		intValue {
			@Override
			public DoubleDouble exec(int i) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public DoubleDouble exec(DoubleDouble dd) {
				return new DoubleDouble(dd.intValue());
			}
		},
		longValue {
			@Override
			public DoubleDouble exec(int i) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public DoubleDouble exec(DoubleDouble dd) {
				return new DoubleDouble(dd.longValue());
			}
		},
		sgn {
			@Override
			public DoubleDouble exec(int i) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public DoubleDouble exec(DoubleDouble dd) {
				return new DoubleDouble(DoubleDouble.sgn(dd.hi));
			}
		},
		round {
			@Override
			public DoubleDouble exec(int i) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public DoubleDouble exec(DoubleDouble dd) {
				return dd.round();
			}
		},
		floor {
			@Override
			public DoubleDouble exec(int i) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public DoubleDouble exec(DoubleDouble dd) {
				return dd.floor();
			}
		},
		ceil {
			@Override
			public DoubleDouble exec(int i) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public DoubleDouble exec(DoubleDouble dd) {
				return dd.ceil();
			}
		},
		trunc {
			@Override
			public DoubleDouble exec(int i) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public DoubleDouble exec(DoubleDouble dd) {
				return dd.trunc();
			}
		},
		neg {
			@Override
			public DoubleDouble exec(int i) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public DoubleDouble exec(DoubleDouble dd) {
				return dd.neg();
			}
		},
		abs {
			@Override
			public DoubleDouble exec(int i) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public DoubleDouble exec(DoubleDouble dd) {
				return dd.abs();
			}
		},
		recip {
			@Override
			public DoubleDouble exec(int i) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public DoubleDouble exec(DoubleDouble dd) {
				return dd.recip();
			}
		},
		sqr {
			@Override
			public DoubleDouble exec(int i) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public DoubleDouble exec(DoubleDouble dd) {
				return dd.sqr();
			}
		},
		sqrt {
			@Override
			public DoubleDouble exec(int i) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public DoubleDouble exec(DoubleDouble dd) {
				return dd.sqrt();
			}
		},
		sqrtFast {
			@Override
			public DoubleDouble exec(int i) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public DoubleDouble exec(DoubleDouble dd) {
				return dd.sqrtFast();
			}
		},
		exp {
			@Override
			public DoubleDouble exec(int i) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public DoubleDouble exec(DoubleDouble dd) {
				return dd.exp();
			}
		},
		log {
			@Override
			public DoubleDouble exec(int i) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public DoubleDouble exec(DoubleDouble dd) {
				return dd.log();
			}
		},
		powOf2 {
			@Override
			public DoubleDouble exec(int i) {
				return new DoubleDouble(DoubleDouble.powOf2(i));
			}

			@Override
			public DoubleDouble exec(DoubleDouble dd) {
				throw new UnsupportedOperationException("Not supported yet.");
			}
		};

		public abstract DoubleDouble exec(int i);

		public abstract DoubleDouble exec(DoubleDouble dd);
	}

	enum BinarySelf {
		min {
			@Override
			public void exec(DoubleDouble x, int y) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public void exec(DoubleDouble x, double y) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public void exec(DoubleDouble x, DoubleDouble y) {
				x = DoubleDouble.min(x, y);
			}
		},
		max {
			@Override
			public void exec(DoubleDouble x, int y) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public void exec(DoubleDouble x, double y) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public void exec(DoubleDouble x, DoubleDouble y) {
				x = DoubleDouble.max(x, y);
			}
		},
		add {
			@Override
			public void exec(DoubleDouble x, int y) {
				x.addSelf(y);
			}

			@Override
			public void exec(DoubleDouble x, double y) {
				x.addSelf(y);
			}

			@Override
			public void exec(DoubleDouble x, DoubleDouble y) {
				x.addSelf(y);
			}
		},
		addFast {
			@Override
			public void exec(DoubleDouble x, int y) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public void exec(DoubleDouble x, double y) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public void exec(DoubleDouble x, DoubleDouble y) {
				x.addSelfFast(y);
			}
		},
		sub {
			@Override
			public void exec(DoubleDouble x, int y) {
				x.subSelf(y);
			}

			@Override
			public void exec(DoubleDouble x, double y) {
				x.subSelf(y);
			}

			@Override
			public void exec(DoubleDouble x, DoubleDouble y) {
				x.subSelf(y);
			}
		},
		subr {
			@Override
			public void exec(DoubleDouble x, int y) {
				x.subRSelf(y);
			}

			@Override
			public void exec(DoubleDouble x, double y) {
				x.subRSelf(y);
			}

			@Override
			public void exec(DoubleDouble x, DoubleDouble y) {
				y.subSelf(x);
			}
		},
		subFast {
			@Override
			public void exec(DoubleDouble x, int y) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public void exec(DoubleDouble x, double y) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public void exec(DoubleDouble x, DoubleDouble y) {
				x.subSelfFast(y);
			}
		},
		subrFast {
			@Override
			public void exec(DoubleDouble x, int y) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public void exec(DoubleDouble x, double y) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public void exec(DoubleDouble x, DoubleDouble y) {
				y.subSelfFast(x);
			}
		},
		mulPwrOf2 {
			@Override
			public void exec(DoubleDouble x, int y) {
				x.mulSelfPwrOf2(y);
			}

			@Override
			public void exec(DoubleDouble x, double y) {
				x.mulSelfPwrOf2(y);
			}

			@Override
			public void exec(DoubleDouble x, DoubleDouble y) {
				x.mulSelfPwrOf2(y.hi);
			}
		},
		mul {
			@Override
			public void exec(DoubleDouble x, int y) {
				x.mulSelf(y);
			}

			@Override
			public void exec(DoubleDouble x, double y) {
				x.mulSelf(y);
			}

			@Override
			public void exec(DoubleDouble x, DoubleDouble y) {
				x.mulSelf(y);
			}
		},
		divPwrOf2 {
			@Override
			public void exec(DoubleDouble x, int y) {
				x.divSelfPwrOf2(y);
			}

			@Override
			public void exec(DoubleDouble x, double y) {
				x.divSelfPwrOf2(y);
			}

			@Override
			public void exec(DoubleDouble x, DoubleDouble y) {
				x.divSelfPwrOf2(y.hi);
			}
		},
		div {
			@Override
			public void exec(DoubleDouble x, int y) {
				x.divSelf(y);
			}

			@Override
			public void exec(DoubleDouble x, double y) {
				x.divSelf(y);
			}

			@Override
			public void exec(DoubleDouble x, DoubleDouble y) {
				x.divSelf(y);
			}
		},
		divr {
			@Override
			public void exec(DoubleDouble x, int y) {
				x.divrSelf(y);
			}

			@Override
			public void exec(DoubleDouble x, double y) {
				x.divrSelf(y);
			}

			@Override
			public void exec(DoubleDouble x, DoubleDouble y) {
				y.divSelf(x);
			}
		},
		divFast {
			@Override
			public void exec(DoubleDouble x, int y) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public void exec(DoubleDouble x, double y) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public void exec(DoubleDouble x, DoubleDouble y) {
				x.divSelfFast(y);
			}
		},
		divrFast {
			@Override
			public void exec(DoubleDouble x, int y) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public void exec(DoubleDouble x, double y) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public void exec(DoubleDouble x, DoubleDouble y) {
				y.divSelfFast(x);
			}
		},
		pow {
			@Override
			public void exec(DoubleDouble x, int y) {
				x.powSelf(y);
			}

			@Override
			public void exec(DoubleDouble x, double y) {
				x.powSelf(y);
			}

			@Override
			public void exec(DoubleDouble x, DoubleDouble y) {
				x.powSelf(y);
			}
		},
		powr {
			@Override
			public void exec(DoubleDouble x, int y) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public void exec(DoubleDouble x, double y) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public void exec(DoubleDouble x, DoubleDouble y) {
				y.powSelf(x);
			}
		},
		root {
			@Override
			public void exec(DoubleDouble x, int y) {
				x.rootSelf(y);
			}

			@Override
			public void exec(DoubleDouble x, double y) {
				x.rootSelf(y);
			}

			@Override
			public void exec(DoubleDouble x, DoubleDouble y) {
				x.rootSelf(y);
			}
		},
		rootr {
			@Override
			public void exec(DoubleDouble x, int y) {
				x.rootrSelf(y);
			}

			@Override
			public void exec(DoubleDouble x, double y) {
				x.rootrSelf(y);
			}

			@Override
			public void exec(DoubleDouble x, DoubleDouble y) {
				y.rootSelf(x);
			}
		};

		public abstract void exec(DoubleDouble x, int y);

		public abstract void exec(DoubleDouble x, double y);

		public abstract void exec(DoubleDouble x, DoubleDouble y);
	}

	enum Binary {
		min {
			@Override
			public DoubleDouble exec(DoubleDouble x, int y) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, double y) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, DoubleDouble y) {
				return new DoubleDouble(DoubleDouble.min(x, y));
			}
		},
		max {
			@Override
			public DoubleDouble exec(DoubleDouble x, int y) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, double y) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, DoubleDouble y) {
				return new DoubleDouble(DoubleDouble.max(x, y));
			}
		},
		add {
			@Override
			public DoubleDouble exec(DoubleDouble x, int y) {
				return x.add(y);
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, double y) {
				return x.add(y);
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, DoubleDouble y) {
				return x.add(y);
			}
		},
		addFast {
			@Override
			public DoubleDouble exec(DoubleDouble x, int y) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, double y) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, DoubleDouble y) {
				return x.addFast(y);
			}
		},
		sub {
			@Override
			public DoubleDouble exec(DoubleDouble x, int y) {
				return x.sub(y);
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, double y) {
				return x.sub(y);
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, DoubleDouble y) {
				return x.sub(y);
			}
		},
		subr {
			@Override
			public DoubleDouble exec(DoubleDouble x, int y) {
				return x.subR(y);
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, double y) {
				return x.subR(y);
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, DoubleDouble y) {
				return y.sub(x);
			}
		},
		subFast {
			@Override
			public DoubleDouble exec(DoubleDouble x, int y) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, double y) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, DoubleDouble y) {
				return x.subFast(y);
			}
		},
		subrFast {
			@Override
			public DoubleDouble exec(DoubleDouble x, int y) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, double y) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, DoubleDouble y) {
				return y.subFast(x);
			}
		},
		mulPwrOf2 {
			@Override
			public DoubleDouble exec(DoubleDouble x, int y) {
				return x.mulPwrOf2(y);
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, double y) {
				return x.mulPwrOf2(y);
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, DoubleDouble y) {
				return x.mulPwrOf2(y.hi);
			}
		},
		mul {
			@Override
			public DoubleDouble exec(DoubleDouble x, int y) {
				return x.mul(y);
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, double y) {
				return x.mul(y);
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, DoubleDouble y) {
				return x.mul(y);
			}
		},
		divPwrOf2 {
			@Override
			public DoubleDouble exec(DoubleDouble x, int y) {
				return x.divPwrOf2(y);
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, double y) {
				return x.divPwrOf2(y);
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, DoubleDouble y) {
				return x.divPwrOf2(y.hi);
			}
		},
		div {
			@Override
			public DoubleDouble exec(DoubleDouble x, int y) {
				return x.div(y);
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, double y) {
				return x.div(y);
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, DoubleDouble y) {
				return x.div(y);
			}
		},
		divr {
			@Override
			public DoubleDouble exec(DoubleDouble x, int y) {
				return x.divr(y);
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, double y) {
				return x.divr(y);
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, DoubleDouble y) {
				return y.div(x);
			}
		},
		divFast {
			@Override
			public DoubleDouble exec(DoubleDouble x, int y) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, double y) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, DoubleDouble y) {
				return x.divFast(y);
			}
		},
		divrFast {
			@Override
			public DoubleDouble exec(DoubleDouble x, int y) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, double y) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, DoubleDouble y) {
				return y.divFast(x);
			}
		},
		pow {
			@Override
			public DoubleDouble exec(DoubleDouble x, int y) {
				return x.pow(y);
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, double y) {
				return x.pow(y);
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, DoubleDouble y) {
				return x.pow(y);
			}
		},
		powr {
			@Override
			public DoubleDouble exec(DoubleDouble x, int y) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, double y) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, DoubleDouble y) {
				return y.pow(x);
			}
		},
		root {
			@Override
			public DoubleDouble exec(DoubleDouble x, int y) {
				return x.root(y);
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, double y) {
				return x.root(y);
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, DoubleDouble y) {
				return x.root(y);
			}
		},
		rootr {
			@Override
			public DoubleDouble exec(DoubleDouble x, int y) {
				return x.rootr(y);
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, double y) {
				return x.rootr(y);
			}

			@Override
			public DoubleDouble exec(DoubleDouble x, DoubleDouble y) {
				return y.root(x);
			}
		};

		public abstract DoubleDouble exec(DoubleDouble x, int y);

		public abstract DoubleDouble exec(DoubleDouble x, double y);

		public abstract DoubleDouble exec(DoubleDouble x, DoubleDouble y);
	}
}
