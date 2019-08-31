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

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Mark Jeronimus
 */
// date Jun -0- 2010
public class GeneralDataInputStream extends DataInputStream {
	public GeneralDataInputStream(InputStream in) {
		super(in);
	}

	public final short readShortBigEndian() throws IOException {
		int c0 = in.read();
		int c1 = in.read();
		if ((c0 | c1) < 0) {
			throw new EOFException();
		}
		return (short)(c0 << 8 | c1);
	}

	public final short readShortLittleEndian() throws IOException {
		int c0 = in.read();
		int c1 = in.read();
		if ((c0 | c1) < 0) {
			throw new EOFException();
		}
		return (short)(c1 << 8 | c0);
	}

	public final int readIntBigEndian() throws IOException {
		int c0 = in.read();
		int c1 = in.read();
		int c2 = in.read();
		int c3 = in.read();
		if ((c0 | c1 | c2 | c3) < 0) {
			throw new EOFException();
		}
		return ((c0 << 8 | c1) << 8 | c2) << 8 | c3;
	}

	public final int readIntLittleEndian() throws IOException {
		int c0 = in.read();
		int c1 = in.read();
		int c2 = in.read();
		int c3 = in.read();
		if ((c0 | c1 | c2 | c3) < 0) {
			throw new EOFException();
		}
		return ((c3 << 8 | c2) << 8 | c1) << 8 | c0;
	}

	public final long readLongBigEndian() throws IOException {
		int c0 = in.read();
		int c1 = in.read();
		int c2 = in.read();
		int c3 = in.read();
		int c4 = in.read();
		int c5 = in.read();
		int c6 = in.read();
		int c7 = in.read();
		if ((c0 | c1 | c2 | c3 | c4 | c5 | c6 | c7) < 0) {
			throw new EOFException();
		}
		return ((((long)(((c0 << 8 | c1) << 8 | c2) << 8 | c3) << 8 | c4) << 8 | c5) << 8 | c6) << 8 | c7;
	}

	public final long readLongLittleEndian() throws IOException {
		int c0 = in.read();
		int c1 = in.read();
		int c2 = in.read();
		int c3 = in.read();
		int c4 = in.read();
		int c5 = in.read();
		int c6 = in.read();
		int c7 = in.read();
		if ((c0 | c1 | c2 | c3 | c4 | c5 | c6 | c7) < 0) {
			throw new EOFException();
		}
		return ((((long)(((c7 << 8 | c6) << 8 | c5) << 8 | c4) << 8 | c3) << 8 | c2) << 8 | c1) << 8 | c0;
	}

	public final double readFloatBigEndian() throws IOException {
		return Float.intBitsToFloat(readIntBigEndian());
	}

	public final double readFloatLittleEndian() throws IOException {
		return Float.intBitsToFloat(readIntLittleEndian());
	}

	public final double readDoubleBigEndian() throws IOException {
		return Double.longBitsToDouble(readLongBigEndian());
	}

	public final double readDoubleLittleEndian() throws IOException {
		return Double.longBitsToDouble(readLongLittleEndian());
	}
}
