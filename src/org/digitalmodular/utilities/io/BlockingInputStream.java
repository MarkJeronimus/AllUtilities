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

import org.jetbrains.annotations.NotNull;

import static org.digitalmodular.utilities.ValidatorUtilities.requireNonNull;
import static org.digitalmodular.utilities.ValidatorUtilities.requireRange;

/**
 * An input stream wrapper where even {@link #read(byte[], int, int)} blocks.
 *
 * @author Mark Jeronimus
 */
// Created 2017-07-04
public class BlockingInputStream extends FilterInputStream {
	public BlockingInputStream(InputStream in) {
		super(in);
	}

	// Copied and modified from PipedInoutStream.read(byte[], off, len)
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		requireNonNull(b, "b");
		requireRange(0, b.length - 1, off, "off");
		requireRange(0, b.length - off, len, "len");

		if (len == 0)
			return 0;

		/* possibly wait on the first character */
		int c = read();
		if (c < 0)
			return -1;

		int ptr = off;

		b[ptr++] = (byte)c;

		int end = off + len;
		while (ptr < end) {
			c = read();
			if (c < 0)
				break;

			b[ptr++] = (byte)c;
		}

		return ptr - off;
	}

	@Override
	public String toString() {
		return in.toString();
	}
}
