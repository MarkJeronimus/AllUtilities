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
package org.digitalmodular.utilities.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;

/**
 * @author Mark Jeronimus
 */
// Created 2005-11-03
public class TCPConnection implements Runnable {
	private Socket              socket = null;
	private NetRequestProcessor netRequestProcessor;

	private DataOutputStream dataOutputStream;
	private DataInputStream  dataInputStream;

	public boolean busy = false;

	@SuppressWarnings("resource")
	public TCPConnection(String host, int port) throws SocketException, IOException {
		this(new Socket(host, port));
	}

	public TCPConnection(Socket socket) throws SocketException {
		this.socket = socket;

		this.socket.setSoTimeout(10000);

		try {
			dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
			dataInputStream = new DataInputStream(this.socket.getInputStream());
		} catch (IOException e) {
			return;
		}
	}

	public String sendReceive(String message) throws IOException {
		// this.dataOutputStream.writeUTF(message);
		dataOutputStream.write(new String(message).getBytes());
		// this.dataOutputStream.write(new String("GET " + path + "
		// HTTP/1.0\n").getBytes());
		// this.dataOutputStream.write(new String("Host: " + host +
		// "\n").getBytes());
		// this.dataOutputStream.write(new String("Connection:
		// close\n\n").getBytes());
		// this.dataOutputStream.flush();

		// return this.dataInputStream.readUTF();

		StringBuffer b = new StringBuffer(dataInputStream.available());
		int          i;
		while ((i = dataInputStream.read()) >= 0) {
			b.append((char)i);
		}

		return b.toString();
	}

	public NetRequest sendReceive(NetRequest message) {
		try {
			busy = true;
			dataOutputStream.writeUTF(message.toString());
			message = new NetRequest(dataInputStream.readUTF());

			return message;
		} catch (IOException e) {
			e.printStackTrace();
			disconnectRequest();
			return null;
		} finally {
			busy = false;
		}
	}

	public void disconnectRequest() {
		if (busy) {
			busy = false;
		}
		// else
		// {
		try {
			dataOutputStream.writeUTF(NetRequest.DisconnectRequest.toString());
			close();
		} catch (IOException except) {}
		// }
	}

	public void close() {
		if (busy) {
			busy = false;
		}
		try {
			socket.close();
		} catch (IOException e) {}
	}

	/**
	 * Start listening for requests
	 */
	public void startResponding(NetRequestProcessor netRequestProcessor) {
		this.netRequestProcessor = netRequestProcessor;
		new Thread(this, "Connection at " + getHostName()).start();
	}

	/**
	 * Listen for requests
	 */
	@Override
	public void run() {
		try {
			busy = true;
			while (true) {
				String requestString = dataInputStream.readUTF();

				NetRequest request = new NetRequest(requestString);

				if (!busy) {
					dataOutputStream.writeUTF(NetRequest.DisconnectRequest.toString());
					break;
				}

				NetRequest result = netRequestProcessor.execute(request);

				dataOutputStream.writeUTF(result.toString());
			}
		} catch (IOException except) {
			close();
		}
	}

	public String getHostName() {
		return ((InetSocketAddress)socket.getRemoteSocketAddress()).getAddress().getHostName();
	}

	public String getIP() {
		byte[] ip = ((InetSocketAddress)socket.getRemoteSocketAddress()).getAddress().getAddress();
		return "" + ip[0] + "." + ip[1] + "." + ip[2] + "." + ip[3];
	}

	public Vector<Object> toVector() {
		Vector<Object> out = new Vector<>();
		out.add(getIP());
		out.add("" + socket.getLocalPort());
		out.add(getHostName());
		return out;
	}
}
