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

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Mark Jeronimus
 */
// Created 2005-11-03
public class TCPListener implements Runnable {
	public ServerSocket serverSocket;
	ConnectionListener listener;

	public TCPListener(int port, ConnectionListener listener) {
		this.listener = listener;
		try {
			serverSocket = new ServerSocket(port);
			new Thread(this).start();
		} catch (IOException e) {
			serverSocket = null;
		}
	}

	public void disconnect() {
		try {
			if (serverSocket != null) {
				if (!serverSocket.isClosed()) {
					System.out.println("Stopping serverSocket");
					serverSocket.close();
					serverSocket = null;
				}
			}
		} catch (IOException e) {
			serverSocket = null;
		}
	}

	@Override
	public void run() {
		while (true) {
			System.out.println("run: " + serverSocket);
			try (Socket socket = serverSocket.accept()) {
				System.out.println("run: " + socket);
				listener.addConnection(new Connection(socket));
			} catch (IOException e) {
				serverSocket = null;
				return;
			}
		}
	}
}
