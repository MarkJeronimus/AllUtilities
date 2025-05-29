/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2024 Mark Jeronimus. All Rights Reversed.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.digitalmodular.utilities.net;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import static org.digitalmodular.utilities.ValidatorUtilities.requireNonNull;

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
