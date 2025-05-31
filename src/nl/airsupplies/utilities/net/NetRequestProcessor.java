package nl.airsupplies.utilities.net;

/**
 * @author Mark Jeronimus
 */
// Created 2005-11-03
public class NetRequestProcessor {
	private final NetFunction[] functions;

	public NetRequestProcessor(NetFunction[] functions) {
		this.functions = functions;
	}

	public NetRequest execute(NetRequest netRequest) {
		String function = netRequest.getFunction();

		for (NetFunction element : functions) {
			if (function.equalsIgnoreCase(element.getFunctionString())) {
				if (netRequest.getNumArguments() != element.getNumRequiredArguments()) {
					return NetRequest.InvalidArgumentCountError;
				}

				return new NetRequest(element.process(netRequest.request));
			}
		}

		return NetRequest.InvalidRequestError;
	}
}
