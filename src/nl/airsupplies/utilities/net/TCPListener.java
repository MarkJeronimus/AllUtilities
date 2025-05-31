package nl.airsupplies.utilities.net;

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
		} catch (IOException ignored) {
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
		} catch (IOException ignored) {
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
			} catch (IOException ignored) {
				serverSocket = null;
				return;
			}
		}
	}
}
