package nl.airsupplies.utilities.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Vector;

/**
 * @author Mark Jeronimus
 */
// Created 2005-11-03
public class Connection implements Runnable {
	private Socket              socket = null;
	private NetRequestProcessor netRequestProcessor;

	private DataOutputStream dataOutputStream;
	private DataInputStream  dataInputStream;

	public boolean busy = false;

	public Connection(Socket socket) {
		this.socket = socket;

		try {
			dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
			dataInputStream  = new DataInputStream(this.socket.getInputStream());
		} catch (IOException ignored) {
		}
	}

	public NetRequest sendReceive(NetRequest message) {
		try {

			busy = true;
			dataOutputStream.writeUTF(message.toString());
			message = new NetRequest(dataInputStream.readUTF());

			return message;
		} catch (IOException ignored) {
			disconnect();
			return null;
		} finally {
			busy = false;
		}
	}

	public void disconnect() {
		if (busy) {
			busy = false;
		} else {
			try {
				dataOutputStream.writeUTF(NetRequest.DisconnectRequest.toString());
			} catch (IOException ignored) {
				close();
			}
		}
	}

	private void close() {
		try {
			socket.close();
		} catch (IOException ignored) {
		}
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
		} catch (IOException ignored) {
			close();
		}
	}

	public String getHostName() {
		return ((InetSocketAddress)socket.getRemoteSocketAddress()).getAddress().getHostName();
	}

	public String getIP() {
		byte[] ip = ((InetSocketAddress)socket.getRemoteSocketAddress()).getAddress().getAddress();
		return String.valueOf(ip[0]) + '.' + ip[1] + '.' + ip[2] + '.' + ip[3];
	}

	public Vector<Object> toVector() {
		Vector<Object> out = new Vector<>();
		out.add(getIP());
		out.add(String.valueOf(socket.getLocalPort()));
		out.add(getHostName());
		return out;
	}
}
