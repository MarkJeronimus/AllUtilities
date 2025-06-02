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

	public RPCRequest execute(RPCRequest netRequest) {
		String function = netRequest.getFunction();

		for (RPCFunction element : functions) {
			if (function.equalsIgnoreCase(element.getName())) {
				if (netRequest.getNumArguments() != element.getNumArguments()) {
					return RPCRequest.INVALID_ARGUMENT_COUNT_ERROR;
				}

				return new RPCRequest(element.process(netRequest.requests));
			}
		}

		return RPCRequest.INVALID_REQUEST_ERROR;
	}
}
