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

import java.io.IOException;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;

/**
 * @author Mark Jeronimus
 */
// Created 2009-03-??
@Deprecated
public class HttpHeader {
	private final HashMap<String, String> fields = new HashMap<>();

	private HttpHeader() {
	}

	public static HttpHeader getHeader(TCPSocket tcpConnection) throws IOException {
		HttpHeader out = new HttpHeader();

		String s;
		try {
			while ((s = tcpConnection.readLine()).length() > 0) {
				if (out.fields.isEmpty()) {
					out.parseStatusLine(s);
				} else {
					out.parseHeaderLine(s);
				}
			}
		} catch (NullPointerException ex) {
			throw new IOException("Unexpected end of stream encountered", ex);
		}
		return out;
	}

	private void parseStatusLine(String s) throws InvalidPropertiesFormatException {
		int i = s.indexOf(' ');
		if (i == -1) {
			throw new InvalidPropertiesFormatException("Invalid initial HTTP response: \"" + s + '"');
		}

		fields.put("HTTP-Version", s.substring(0, i));

		int j = s.indexOf(' ', i + 1);
		if (j != -1) {
			fields.put("Response-Code", s.substring(i + 1, j));
			fields.put("Response-Phrase", s.substring(j + 1));
		} else {
			fields.put("Response-Code", s.substring(i + 1));
		}
	}

	private void parseHeaderLine(String s) throws InvalidPropertiesFormatException {
		int i = s.indexOf(':');
		if (i == -1) {
			throw new InvalidPropertiesFormatException("Invalid initial HTTP response: \"" + s + '"');
		}

		fields.put(s.substring(0, i), s.substring(i + 2).trim());
	}

	public String getProperty(String string) {
		return fields.get(string);
	}

	public int getResponseCode() {
		return Integer.parseInt(getProperty("Response-Code"));
	}

	public int getContentSize() {
		String sizeString = getProperty("Content-Length");

		if (sizeString == null) {
			return 0;
		}

		try {
			return Integer.parseInt(sizeString);
		} catch (NumberFormatException ignored) {
			return 0;
		}
	}

	@Override
	public String toString() {
		return fields.toString();
	}
}
