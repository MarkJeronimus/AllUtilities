package nl.airsupplies.utilities.net;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Mark Jeronimus
 */
// Created 2005-11-03
public class TCPConnecter {
	private Socket socket = null;

	public Connection connect(String host, int port) {
		try {
			socket = new Socket(host, port);

			return new Connection(socket);
		} catch (IOException ignored) {
			return null;
		}
	}
}
