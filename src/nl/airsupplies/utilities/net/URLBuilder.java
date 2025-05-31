package nl.airsupplies.utilities.net;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * @author Mark Jeronimus
 */
// Created 2016-04-17
public class URLBuilder {
	private final String             host;
	private final URL                hostURL;
	private final Collection<String> parameters = new ArrayList<>(4);

	public URLBuilder(String host) {
		this.host = requireNonNull(host, "host");
		hostURL   = null;
	}

	public URLBuilder(URL hostURL) {
		host         = "";
		this.hostURL = requireNonNull(hostURL, "hostURL");
	}

	public URLBuilder addParameter(String parameter) {
		if (parameter.indexOf('?') != -1) {
			throw new IllegalArgumentException("parameter may not contain unescaped '?': " + parameter);
		}
		if (parameter.indexOf('&') != -1) {
			throw new IllegalArgumentException("parameter may not contain unescaped '&': " + parameter);
		}

		parameters.add(parameter);
		return this;
	}

	public URL build() {
		StringBuilder sb = new StringBuilder(256);
		sb.append(host);

		char separatorChar = '?';
		for (String parameter : parameters) {
			sb.append(separatorChar).append(parameter);
			separatorChar = '&';
		}

		try {
			if (hostURL != null) {
				return new URL(hostURL, sb.toString());
			} else {
				return new URL(sb.toString());
			}
		} catch (MalformedURLException ex) {
			throw new IllegalStateException(ex);
		}
	}
}
