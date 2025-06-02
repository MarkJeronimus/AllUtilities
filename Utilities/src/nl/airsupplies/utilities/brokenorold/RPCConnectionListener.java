package nl.airsupplies.utilities.brokenorold;

/**
 * Classes that implement this can listen for new connections.
 *
 * @author Mark Jeronimus
 */
// Created 2005-10-30
public interface RPCConnectionListener {
	void clientConnected(RPCConnection connection);
}
