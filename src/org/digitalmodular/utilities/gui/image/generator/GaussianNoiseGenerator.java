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
package org.digitalmodular.utilities.gui.image.generator;

import java.util.concurrent.ThreadLocalRandom;

import org.digitalmodular.utilities.gui.image.ImageMatrixFloat;
import org.digitalmodular.utilities.math.FastRandom;
import org.digitalmodular.utilities.nodes.DoubleParam;
import org.digitalmodular.utilities.nodes.Node;
import static org.digitalmodular.utilities.ValidatorUtilities.requireAbove;

/**
 * @author Mark Jeronimus
 */
// Created 2012-04-03
// Updated 2016-04-03 Converted to Node
@Node(name = "Gaussian Noise", description = "Gaussian Noise Generator")
public class GaussianNoiseGenerator extends ImageGenerator {
	private static final ThreadLocal<FastRandom> fastRand =
			ThreadLocal.withInitial(() -> new FastRandom(ThreadLocalRandom.current().nextLong()));

	@DoubleParam(description = "", min = 0, minIsInclusive = false, maxIsInclusive = false)
	private double amount = 1.0;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = requireAbove(0, amount, "amount");
	}

	@Override
	public ImageMatrixFloat generate() {
		// Inner loop
		int        x;                               // 3
		FastRandom rnd        = fastRand.get();     // 3
		float      amount     = (float)getAmount(); // 1
		int        numColumns = image.numColumns;   // 1

		for (float[] row : image.matrix[0])
			for (x = 0; x < numColumns; x++)
				row[x] = amount * rnd.nextBell();

		return image;
	}
}
