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
package org.digitalmodular.utilities.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;

import net.jcip.annotations.GuardedBy;
import org.jetbrains.annotations.Nullable;

import org.digitalmodular.utilities.ArrayUtilities;
import static org.digitalmodular.utilities.ValidatorUtilities.requireNonNull;
import static org.digitalmodular.utilities.ValidatorUtilities.requireRange;

/**
 * An {@link InputStream} that draws bytes from an internal circular buffer, which can be filled by
 * {@link #append(byte[])}.
 *
 * @author Mark Jeronimus
 */
// Created 2017-01-10
// Updated 2017-07-05 Fixed bugs, made blocking, implemented growing
public class CircularBufferInputStream extends InputStream {
	public static final int DEFAULT_INITIAL_CAPACITY = 1024;

	private final Object lock = new Object();

	@GuardedBy("lock")
	private byte[]  buffer;
	@GuardedBy("lock")
	private int     readPointer  = 0;
	@GuardedBy("lock")
	private int     writePointer = 0;
	@GuardedBy("lock")
	private int     size         = 0;
	@GuardedBy("lock")
	private boolean eof          = false;

	@GuardedBy("lock")
	private @Nullable OutputStream sink = null;

	public CircularBufferInputStream() {
		this(DEFAULT_INITIAL_CAPACITY);
	}

	public CircularBufferInputStream(int initialCapacity) {
		buffer = new byte[initialCapacity];
	}

	@Override
	public int read() throws IOException {
		synchronized (lock) {
			while (size == 0) {
				if (eof)
					return -1;

				try {
					lock.wait(10);
				} catch (InterruptedException ex) {
					throw new InterruptedIOException(ex.getMessage());
				}
			}

			byte b = buffer[readPointer];
			readPointer = (readPointer + 1) % buffer.length;
			assert size >= 1;
			size--;

			return b & 0xFF;
		}
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		if (len == 0)
			return 0;

		requireNonNull(b, "b");
		requireRange(0, b.length - 1, off, "off");
		requireRange(0, b.length - off, len, "len");

		synchronized (lock) {
			// Block for the first byte
			while (size == 0) {
				if (eof)
					return -1;

				try {
					lock.wait(10);
				} catch (InterruptedException ex) {
					throw new InterruptedIOException(ex.getMessage());
				}
			}

			// Try to wait for more bytes in case other threads are appending concurrently.
			int lastSize = -1;
			while (size < len && lastSize != size) {
				try {
					lock.wait(10);
				} catch (InterruptedException ex) {
					throw new InterruptedIOException(ex.getMessage());
				}

				lastSize = size;
			}

			len = Math.min(len, size);

			int lenBeforeWrap = Math.min(buffer.length - readPointer, len);
			int lenAfterWrap  = Math.max(0, readPointer + len - buffer.length);
			assert lenBeforeWrap + lenAfterWrap == len;
			System.arraycopy(buffer, readPointer, b, off, lenBeforeWrap);
			System.arraycopy(buffer, 0, b, off + lenBeforeWrap, lenAfterWrap);
			readPointer = (readPointer + len) % buffer.length;
			assert size >= len;
			size -= len;

			return len;
		}
	}

	@Override
	public int available() {
		synchronized (lock) {
			return size;
		}
	}

	public void clear() {
		synchronized (lock) {
			readPointer = 0;
			writePointer = 0;
			size = 0;
		}
	}

	public void append(int b) {
		synchronized (lock) {
			if (buffer.length - size < 1)
				grow();

			buffer[writePointer] = (byte)b;
			writePointer = (writePointer + 1) % buffer.length;
			size += 1;

			lock.notifyAll();
		}
	}

	public void append(byte[] bytes) {
		synchronized (lock) {
			int len = bytes.length;
			if (buffer.length - size < len)
				grow();

			int lenBeforeWrap = Math.min(buffer.length - writePointer, len);
			int lenAfterWrap  = Math.max(0, writePointer + len - buffer.length);
			assert lenBeforeWrap + lenAfterWrap == len;
			System.arraycopy(bytes, 0, buffer, writePointer, lenBeforeWrap);
			System.arraycopy(bytes, lenBeforeWrap, buffer, 0, lenAfterWrap);
			writePointer = (writePointer + len) % buffer.length;
			size += len;

			lock.notifyAll();
		}
	}

	public OutputStream getSink() {
		synchronized (lock) {
			if (sink == null) {
				sink = new OutputStream() {
					@Override
					public void write(int b) { append(b); }

					@Override
					public void write(byte b[]) { append(b); }
				};
			}

			return sink;
		}
	}

	@GuardedBy("lock")
	private void grow() {
		// IMPROVE Can probably optimize this by inserting blank space instead of unwrapping the buffer, but who cares.
		byte[] newBuffer     = new byte[buffer.length * 2];
		int    lenBeforeWrap = Math.min(buffer.length - readPointer, size);
		int    lenAfterWrap  = Math.max(0, readPointer + size - buffer.length);
		assert lenBeforeWrap + lenAfterWrap == size;
		System.arraycopy(buffer, readPointer, newBuffer, 0, lenBeforeWrap);
		System.arraycopy(buffer, 0, newBuffer, lenBeforeWrap, lenAfterWrap);

		buffer = newBuffer;
		readPointer = 0;
		writePointer = size;
	}

	public void markEOF() {
		synchronized (lock) {
			eof = true;
		}
	}

	@Override
	public String toString() {
		synchronized (lock) {
			byte[] peek          = new byte[size];
			int    lenBeforeWrap = Math.min(buffer.length - readPointer, size);
			int    lenAfterWrap  = Math.max(0, readPointer + size - buffer.length);
			assert lenBeforeWrap + lenAfterWrap == size;
			System.arraycopy(buffer, readPointer, peek, 0, lenBeforeWrap);
			System.arraycopy(buffer, 0, peek, lenBeforeWrap, lenAfterWrap);

			return "CircularBufferInputStream" + ArrayUtilities.toHexStringTruncated(peek, 16);
		}
	}
}
