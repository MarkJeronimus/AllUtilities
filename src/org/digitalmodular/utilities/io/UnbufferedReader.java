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

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 * @author Mark Jeronimus
 */
// date May -6- 2009
public class UnbufferedReader extends Reader {
	private static final int defaultExpectedLineLength = 80;

	private InputStream in;

	public UnbufferedReader(InputStream in) {
		super(in);

		if (!in.markSupported()) {
			throw new IllegalArgumentException(
					"Mark not supported on given stream. Suggest wrapping it with BufferedInputStream.");
		}

		this.in = in;
	}

	/**
	 * Checks to make sure that the stream has not been closed
	 */
	private void ensureOpen() throws IOException {
		if (in == null) {
			throw new IOException("Stream closed");
		}
	}

	@Override
	public int read(char cbuf[], int off, int len) throws IOException {
		return this.read(cbuf, off, len);
	}

	/**
	 * Reads a line of text. A line is considered to be terminated by any one of a line feed ('\n'), a carriage return
	 * ('\r'), or a carriage return followed immediately by a linefeed.
	 *
	 * @return A String containing the contents of the line, not including any line-termination characters, or null if
	 * the end of the stream has been reached
	 * @throws IOException If an I/O error occurs
	 */
	public String readLine() throws IOException {
		StringBuilder s = new StringBuilder(UnbufferedReader.defaultExpectedLineLength);

		synchronized (lock) {
			ensureOpen();

			int c;
			while (true) {
				c = in.read();
				if (c == -1) {
					return s.length() == 0 ? null : s.toString();
				}

				if (c == '\r' || c == '\n') {
					break;
				}

				s.append((char)c);
			}

			if (c == '\r') {
				in.mark(1);
				if (in.read() != '\n') {
					in.reset();
				}
			}

			return s.toString();
		}
	}

	@Override
	public void close() throws IOException {
		synchronized (lock) {
			if (in == null) {
				return;
			}
			in.close();
			in = null;
		}
	}
}
