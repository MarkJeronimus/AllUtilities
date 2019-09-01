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
package org.digitalmodular.utilities.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Mark Jeronimus
 */
// Created 2009-06-?6
public class BitInputStream extends FilterInputStream {
	int pos    = 8;
	int buffer = 0;

	public BitInputStream(InputStream in) {
		super(in);
	}

	@Override
	public int read() throws IOException {
		if (pos == 8) {
			buffer = in.read();

			if (buffer == -1) {
				return -1;
			}

			pos = 0;
		}

		int bit = buffer & 1;
		buffer >>>= 1;
		pos++;

		return bit;
	}
}
