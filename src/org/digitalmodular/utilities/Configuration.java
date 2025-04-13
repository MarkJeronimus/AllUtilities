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

package org.digitalmodular.utilities;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * @author Mark Jeronimus
 */
// Created 2018-01-08 Split up from ConfigurationFile
public class Configuration implements Iterable<Entry<String, String>> {
	private final   Properties defaults   = new Properties();
	protected final Properties properties = new Properties(defaults);

	public synchronized void setDefault(String key, String value) {
		defaults.setProperty(key, value);
	}

	public synchronized void set(String key, String value) {
		properties.setProperty(key, value);
	}

	public synchronized String get(String key) {
		String value = properties.getProperty(key);

		if (value == null) {
			value = defaults.getProperty(key);
			if (value == null) {
				throw new IllegalArgumentException("No default value set: " + key);
			}
		}

		return value;
	}

	/**
	 * Checks if {@link #get(String)} will succeed. In other words, if the key has a default and/or a specific value.
	 * <p>
	 * Equal to: {@link #hasDefaultValue(String) hasDefaultValue}{@code (key) || }
	 * {@link #hasDefaultValue(String) hasSpecificValue}{@code (key); }
	 */
	public synchronized boolean hasValue(String key) {
		return defaults.contains(key) || properties.contains(key);
	}

	/**
	 * Checks if the key has a default value (returns {@code false} if it only has a specific value or none at all).
	 *
	 * @see #hasValue(String)
	 * @see #hasSpecificValue(String)
	 */
	public synchronized boolean hasDefaultValue(String key) {
		return defaults.contains(key);
	}

	/**
	 * Checks if the key has a specific value (returns {@code false} if it only has a default value or none at all).
	 *
	 * @see #hasValue(String)
	 * @see #hasDefaultValue(String)
	 */
	public synchronized boolean hasSpecificValue(String key) {
		return properties.contains(key);
	}

	public synchronized int getInt(String key) {
		boolean valueExists = false;

		try {
			String value = properties.getProperty(key);
			valueExists = value != null;
			if (valueExists) {
				return Integer.parseInt(value);
			}
		} catch (NumberFormatException ignored) {
		}

		try {
			String value = defaults.getProperty(key);

			if (value == null) {
				if (valueExists) {
					throw new IllegalArgumentException("Value not an integer, and no default value set, for: " + key);
				} else {
					throw new IllegalArgumentException("No default value set, for: " + key);
				}
			}

			return Integer.parseInt(value);
		} catch (NumberFormatException ignored) {
		}

		if (valueExists) {
			throw new IllegalArgumentException("Value not an integer, and default value not an integer, for: " + key);
		} else {
			throw new IllegalArgumentException("Default value not an integer, for: " + key);
		}
	}

	public synchronized void restoreDefault(String key) {
		properties.remove(key);
	}

	public synchronized void restoreAllDefault() {
		properties.clear();
	}

	public final synchronized void read(InputStream in) throws IOException {
		try {
			properties.load(in);
		} catch (FileNotFoundException ignored) {
		}
	}

	public final synchronized void write(OutputStream out) throws IOException {
		properties.store(out, null);
	}

	/**
	 * Makes a snapshot of the configuration. A {@link ConcurrentModificationException} can't occur.
	 */
	@Override
	public synchronized Iterator<Entry<String, String>> iterator() {
		Map<String, String> allProperties = new HashMap<>(defaults.size() + properties.size());

		for (Entry<Object, Object> entry : defaults.entrySet()) {
			allProperties.put((String)entry.getKey(), (String)entry.getValue());
		}

		for (Entry<Object, Object> entry : properties.entrySet()) {
			allProperties.put((String)entry.getKey(), (String)entry.getValue());
		}

		return allProperties.entrySet().iterator();
	}
}
