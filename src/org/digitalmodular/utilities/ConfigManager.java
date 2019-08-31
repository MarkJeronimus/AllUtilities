/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2018 Mark Jeronimus. All Rights Reversed.
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
 * along with AllUtilities. If not, see <http://www.gnu.org/licenses/>.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.digitalmodular.utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Mark Jeronimus
 */
// Created 2008-03-06
public final class ConfigManager {
	private ConfigManager() { throw new AssertionError(); }

	private static final String FILENAME = "config.ini";

	private static final TreeMap<String, String> data = new TreeMap<>();

	static {
		revert();
	}

	public static void revert() {
		data.clear();

		try (BufferedReader in = new BufferedReader(new FileReader(FILENAME))) {
			String s;
			while ((s = in.readLine()) != null) {
				int    split = s.indexOf(' ');
				String key   = s.substring(0, split++);
				String value = s.substring(split);
				data.put(key, value);
			}
		} catch (FileNotFoundException ignored) {} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void save() {
		try (BufferedWriter out = new BufferedWriter(new FileWriter(FILENAME))) {
			Set<String> keys = data.keySet();
			for (String key : keys) {
				out.write(key + ' ' + data.get(key) + '\n');
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
		} catch (NumberFormatException e) {
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
		} catch (NumberFormatException e) {
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
		} catch (NumberFormatException e) {
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
		} catch (NumberFormatException e) {
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
