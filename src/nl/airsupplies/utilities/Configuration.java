package nl.airsupplies.utilities;

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

import org.jetbrains.annotations.Nullable;

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

	public synchronized String getDefault(String key) {
		@Nullable String value = defaults.getProperty(key);

		if (value == null) {
			throw new IllegalArgumentException("No default value set: " + key);
		}

		return value;
	}

	public synchronized void set(String key, @Nullable String value) {
		properties.setProperty(key, value);
	}

	public synchronized String get(String key) {
		if (properties.containsKey(key)) {
			return properties.getProperty(key);
		} else if (defaults.containsKey(key)) {
			return defaults.getProperty(key);
		}

		throw new IllegalArgumentException("No default value set: " + key);
	}

	/**
	 * Checks if the key has a default value (returns {@code false} if it only has a specific value or none at all).
	 *
	 * @see #hasValue(String)
	 * @see #hasSpecificValue(String)
	 */
	public synchronized boolean hasDefaultValue(String key) {
		return defaults.containsKey(key);
	}

	/**
	 * Checks if the key has a specific value (returns {@code false} if it only has a default value or none at all).
	 *
	 * @see #hasValue(String)
	 * @see #hasDefaultValue(String)
	 */
	public synchronized boolean hasSpecificValue(String key) {
		return properties.containsKey(key);
	}

	/**
	 * Checks if {@link #get(String)} will succeed. In other words, if the key has a default and/or a specific value.
	 * <p>
	 * Equal to: {@link #hasDefaultValue(String) hasDefaultValue}{@code (key) || }
	 * {@link #hasDefaultValue(String) hasSpecificValue}{@code (key); }
	 */
	public synchronized boolean hasValue(String key) {
		return defaults.containsKey(key) || properties.containsKey(key);
	}

	public synchronized void restoreDefault(String key) {
		properties.remove(key);
	}

	public synchronized void restoreAllDefault() {
		properties.clear();
	}

	public synchronized boolean getBoolean(String key) {
		boolean valueExists = false;

		try {
			String value = properties.getProperty(key);
			valueExists = value != null;
			if (valueExists) {
				return Boolean.parseBoolean(value);
			}
		} catch (NumberFormatException ignored) {
		}

		try {
			String value = defaults.getProperty(key);

			if (value == null) {
				if (valueExists) {
					throw new IllegalArgumentException("Value not a boolean, and no default value set, for: " + key);
				} else {
					throw new IllegalArgumentException("No default value set, for: " + key);
				}
			}

			return Boolean.parseBoolean(value);
		} catch (NumberFormatException ignored) {
		}

		if (valueExists) {
			throw new IllegalArgumentException("Value not a boolean, and default value not an integer, for: " + key);
		} else {
			throw new IllegalArgumentException("Default value not a boolean, for: " + key);
		}
	}

	public synchronized int getInt(String key) {
		boolean valueExists = false;

		try {
			String value = properties.getProperty(key);
			valueExists = value != null;
			if (valueExists) {
				return Integer.decode(value);
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

			return Integer.decode(value);
		} catch (NumberFormatException ignored) {
		}

		if (valueExists) {
			throw new IllegalArgumentException("Value not an integer, and default value not an integer, for: " + key);
		} else {
			throw new IllegalArgumentException("Default value not an integer, for: " + key);
		}
	}

	public synchronized double getDouble(String key) {
		boolean valueExists = false;

		try {
			String value = properties.getProperty(key);
			valueExists = value != null;
			if (valueExists) {
				return Double.parseDouble(value);
			}
		} catch (NumberFormatException ignored) {
		}

		try {
			String value = defaults.getProperty(key);

			if (value == null) {
				if (valueExists) {
					throw new IllegalArgumentException("Value not a double, and no default value set, for: " + key);
				} else {
					throw new IllegalArgumentException("No default value set, for: " + key);
				}
			}

			return Double.parseDouble(value);
		} catch (NumberFormatException ignored) {
		}

		if (valueExists) {
			throw new IllegalArgumentException("Value not a double, and default value not a double, for: " + key);
		} else {
			throw new IllegalArgumentException("Default value not a double, for: " + key);
		}
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
