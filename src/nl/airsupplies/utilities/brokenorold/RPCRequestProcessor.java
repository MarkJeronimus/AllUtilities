package nl.airsupplies.utilities.brokenorold;

/**
 * @author Mark Jeronimus
 */
// Created 2005-11-03
public class RPCRequestProcessor {
	private final RPCFunction[] functions;

	public RPCRequestProcessor(RPCFunction[] functions) {
		this.functions = functions;
	}

	public NetRequest execute(NetRequest netRequest) {
		String function = netRequest.getFunction();

		for (RPCFunction element : functions) {
			if (function.equalsIgnoreCase(element.getName())) {
				if (netRequest.getNumArguments() != element.getNumArguments()) {
					return NetRequest.InvalidArgumentCountError;
				}

				return new NetRequest(element.process(netRequest.request));
			}
		}

		return NetRequest.InvalidRequestError;
	}
}
