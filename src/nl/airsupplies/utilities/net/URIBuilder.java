package nl.airsupplies.utilities.net;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;

import org.jetbrains.annotations.Nullable;

import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * @author Mark Jeronimus
 */
// Created 2016-04-17
public class URIBuilder {
	private final           String             host;
	private final @Nullable URI                hostURI;
	private final           Collection<String> parameters = new ArrayList<>(4);

	public URIBuilder(String host) {
		this.host = requireNonNull(host, "host");
		hostURI   = null;
	}

	public URIBuilder(URI hostURI) {
		host         = "";
		this.hostURI = requireNonNull(hostURI, "hostURI");
	}

	public URIBuilder addParameter(String parameter) {
		if (parameter.indexOf('?') != -1) {
			throw new IllegalArgumentException("parameter may not contain unescaped '?': " + parameter);
		}
		if (parameter.indexOf('&') != -1) {
			throw new IllegalArgumentException("parameter may not contain unescaped '&': " + parameter);
		}

		parameters.add(parameter);
		return this;
	}

	public URI build() {
		StringBuilder sb = new StringBuilder(256);
		sb.append(host);

		char separatorChar = '?';
		for (String parameter : parameters) {
			sb.append(separatorChar).append(parameter);
			separatorChar = '&';
		}

		try {
			if (hostURI != null) {
				return hostURI.resolve(sb.toString());
			} else {
				return new URI(sb.toString());
			}
		} catch (URISyntaxException ex) {
			throw new IllegalStateException(ex);
		}
	}
}
