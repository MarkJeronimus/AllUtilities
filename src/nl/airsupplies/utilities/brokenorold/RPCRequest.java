package nl.airsupplies.utilities.brokenorold;

import java.util.ArrayList;

import org.jetbrains.annotations.Nullable;

/**
 * @author Mark Jeronimus
 */
// Created 2005-11-03
public class RPCRequest {
	public static final RPCRequest INVALID_ARGUMENT_COUNT_ERROR = new RPCRequest("invalidArgumentCountError()");
	public static final RPCRequest INVALID_REQUEST_ERROR        = new RPCRequest("invalidRequestError()");
	public static final RPCRequest DISCONNECT_REQUEST           = new RPCRequest("disconnectRequest()");

	public @Nullable ArrayList<Object> requests;

	public RPCRequest(@Nullable ArrayList<Object> requests) {
		this.requests = requests;
	}

	public RPCRequest(String requests) {
		this.requests = new ArrayList<>();
		int x;

		x = requests.indexOf('(');
		if (x < 1) {
			this.requests = null;
			return;
		}
		this.requests.add(requests.substring(0, x));

		String  parameter = "";
		boolean literal   = false;
		try {
			x++;
			for (; x < requests.length(); x++) {
				char c = requests.charAt(x);
				if (literal) {
					parameter += c;
					if (c == '"') {
						this.requests.add(parameter.trim());
						parameter = "";
						literal   = false;
					}
				} else {
					if (c == ',' || c == ')') {
						parameter = parameter.trim();
						if (parameter.length() > 0) {
							this.requests.add(Double.parseDouble(parameter.trim()));
							parameter = "";
						}
						if (c == ')') {
							break;
						}
					} else {
						if (c == '"') {
							if (!parameter.isEmpty()) {
								this.requests = null;
								return;
							}
							literal = true;
						}
						parameter += c;
					}
				}
			}
		} catch (NumberFormatException ignored) {
			this.requests = null;
			return;
		}

		if (x < requests.length() - 1) {
			this.requests = null;
			return;
		}

		if (literal) {
			this.requests = null;
			return;
		}

		if (parameter.length() > 0) {
			this.requests = null;
		}
	}

	public String getFunction() {
		return (String)requests.get(0);
	}

	public int getNumArguments() {
		return requests.size() - 1;
	}

	@Override
	public String toString() {
		StringBuffer out = new StringBuffer();
		out.append(getClass().getSimpleName() + "[request=" + requests.get(0));

		out.append('(');
		if (requests.size() > 1) {
			for (int i = 1; i < requests.size(); i++) {
				if (i > 1) {
					out.append(", ");
				}
				Object value = requests.get(i);
				if (value instanceof String || value instanceof char[]) {
					out.append(value);
				} else if (value instanceof Byte) {
					out.append(String.valueOf(((Byte)value).byteValue()));
				} else if (value instanceof Character) {
					out.append(((Character)value).charValue());
				} else if (value instanceof Short) {
					out.append(String.valueOf(((Short)value).shortValue()));
				} else if (value instanceof Integer) {
					out.append(((Integer)value).intValue());
				} else if (value instanceof Long) {
					out.append(((Long)value).longValue());
				} else if (value instanceof Float) {
					out.append(((Integer)value).floatValue());
				} else if (value instanceof Double) {
					out.append(((Integer)value).doubleValue());
				} else {
					out.append(value);
				}
			}
		}
		out.append(")]");

		return out.toString();
	}
}
