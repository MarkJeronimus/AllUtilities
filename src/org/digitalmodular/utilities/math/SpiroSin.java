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
package org.digitalmodular.utilities.math;

import java.util.Random;

/**
 * @author Mark Jeronimus
 */
// Created 2005-10-08
public class SpiroSin {
	private static final int NUM_TERMS = 4;

	private final Random   randomNumberGenerator = new Random();
	private final double[] frequency             = new double[NUM_TERMS];
	private final double[] phase                 = new double[NUM_TERMS];
	private final double[] amplitude             = new double[NUM_TERMS];

	public SpiroSin() {
		double sum = 0;

		// Generate random parameter
		for (int i = 0; i < NUM_TERMS; i++) {
			frequency[i] = randomNumberGenerator.nextFloat();
			phase[i] = 2 * (float)Math.PI * randomNumberGenerator.nextFloat();
			amplitude[i] = randomNumberGenerator.nextFloat();
			sum += amplitude[i];
		}

		// Normalize sum(amplitude) to 1
		sum = 1 / sum;
		for (int i = 0; i < NUM_TERMS; i++) {
			amplitude[i] *= sum;
		}
	}

	public double get(double phase) {
		float sum = 0;
		for (int i = 0; i < NUM_TERMS; i++) {
			sum += Math.sin(phase * frequency[i] + this.phase[i]) * amplitude[i];
		}
		return sum;
	}
}
