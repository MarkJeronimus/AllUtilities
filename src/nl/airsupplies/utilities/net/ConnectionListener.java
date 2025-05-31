package nl.airsupplies.utilities.net;

/**
 * Classes that implement this can listen for new connections.
 *
 * @author Mark Jeronimus
 */
// Created 2005-10-30
public abstract class ConnectionListener {
	public Connections connections = new Connections();
	public TCPListener tcpListener;

	public void addConnection(Connection connection) {
		connections.addConnection(connection);
		connectionAdded(connection);
	}

	public void removeConnection(Connection connection) {
		connections.removeConnection(connection);
		connectionRemoved(connection);
	}

	public void disconnect() {
		tcpListener.disconnect();
	}

	public abstract void connectionAdded(Connection connection);

	public abstract void connectionRemoved(Connection connection);
}
