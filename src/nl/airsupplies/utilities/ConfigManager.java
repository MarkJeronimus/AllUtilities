package nl.airsupplies.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 * @author Mark Jeronimus
 */
// Created 2008-03-06
public final class ConfigManager {
	private static final String FILENAME = "config.ini";

	private static final TreeMap<String, String> data = new TreeMap<>();

	static {
		revert();
	}

	public static void revert() {
		data.clear();

		try (BufferedReader in = Files.newBufferedReader(Paths.get(FILENAME))) {
			String s;
			while ((s = in.readLine()) != null) {
				int    split = s.indexOf(' ');
				String key   = s.substring(0, split);
				split++;
				String value = s.substring(split);
				data.put(key, value);
			}
		} catch (FileNotFoundException ignored) {
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void save() {
		try (BufferedWriter out = Files.newBufferedWriter(Paths.get(FILENAME))) {
			for (Entry<String, String> entry : data.entrySet()) {
				out.write(entry.getKey() + ' ' + entry.getValue() + '\n');
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void setValue(String key, String value) {
		data.put(key, value);
	}

	public static void setIntValue(String key, int value) {
		data.put(key, Integer.toString(value));
	}

	public static void setLongValue(String key, long value) {
		data.put(key, Long.toString(value));
	}

	public static void setFloatValue(String key, float value) {
		data.put(key, Float.toString(value));
	}

	public static void setDoubleValue(String key, double value) {
		data.put(key, Double.toString(value));
	}

	public static void setBoolValue(String key, boolean value) {
		data.put(key, Boolean.toString(value));
	}

	public static String getValue(String key, String defaultValue) {
		String value = data.get(key);
		if (value == null) {
			setValue(key, defaultValue);
			return defaultValue;
		}
		return value;
	}

	public static int getIntValue(String key, int defaultValue) {
		String value = data.get(key);
		if (value == null) {
			setIntValue(key, defaultValue);
			return defaultValue;
		}
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException ignored) {
			return defaultValue;
		}
	}

	public static long getLongValue(String key, long defaultValue) {
		String value = data.get(key);
		if (value == null) {
			setLongValue(key, defaultValue);
			return defaultValue;
		}
		try {
			return Long.parseLong(value);
		} catch (NumberFormatException ignored) {
			return defaultValue;
		}
	}

	public static float getFloatValue(String key, float defaultValue) {
		String value = data.get(key);
		if (value == null) {
			setFloatValue(key, defaultValue);
			return defaultValue;
		}
		try {
			return Float.parseFloat(value);
		} catch (NumberFormatException ignored) {
			return defaultValue;
		}
	}

	public static double getDoubleValue(String key, double defaultValue) {
		String value = data.get(key);
		if (value == null) {
			setDoubleValue(key, defaultValue);
			return defaultValue;
		}
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException ignored) {
			return defaultValue;
		}
	}

	public static boolean getBoolValue(String key, boolean defaultValue) {
		String value = data.get(key);
		if (value == null) {
			setBoolValue(key, defaultValue);
			return defaultValue;
		}
		return Boolean.parseBoolean(value);
	}
}
