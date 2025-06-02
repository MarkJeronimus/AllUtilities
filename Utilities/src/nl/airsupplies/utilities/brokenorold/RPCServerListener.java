package nl.airsupplies.utilities.brokenorold;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.jetbrains.annotations.Nullable;

/**
 * @author Mark Jeronimus
 */
// Created 2005-11-03
public class RPCServerListener implements Runnable {
	private final RPCConnectionListener listener;

	private @Nullable ServerSocket serverSocket;

	public RPCServerListener(int port, RPCConnectionListener listener) {
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
			@Nullable ServerSocket serverSocketCopy = serverSocket;
			if (serverSocketCopy == null) {
				break;
			}

			try (Socket socket = serverSocketCopy.accept()) {
				listener.clientConnected(new RPCConnection(socket));
			} catch (IOException ignored) {
				serverSocket = null;
				return;
			}
		}
	}
}
