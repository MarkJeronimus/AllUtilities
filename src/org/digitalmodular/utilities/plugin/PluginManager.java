/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2019 Mark Jeronimus. All Rights Reversed.
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
package org.digitalmodular.utilities.plugin;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.digitalmodular.utilities.Debug;

/**
 * @author Mark Jeronimus
 */
// Created 2005-08-07
public class PluginManager<T> {
	private static ClassLoader classLoader = PluginManager.class.getClassLoader();

	private File   rootPath;
	private File   pluginsPath;
	private String pluginsPackage;

	private ArrayList<Plugin<T>> pluginEntries = new ArrayList<>();

	/**
	 * Set up a new plugin manager. The plugins will be searched inside directories within a directory called "plugins"
	 * in the same package as the supplied class. More precisely, there must be a "plugins" directory and a class (any
	 * class will do) existing side-by-side. For each plugin in the "plugins" directory, there must be one directory
	 * containing exactly one class file (not counting sub-directories) which will be the main class of the plugin
	 * (which must extend the abstract class Plugin). Plugins may also be in JAR format, in which case the same package
	 * structure must be obeyed, and the JAR file must be placed directly in the "plugins" directory. In addition a JAR
	 * may only contain one plugin.
	 * <p>
	 * The names of the directories and jar files inside the "plugins" directory are basically ignored, however they
	 * are
	 * used for ordering the plugins in ASCII order.
	 * <p>
	 * Multiple plugin directories can be managed by multiple plugin managers. The plugins are not instantiated at this
	 * point, but JAR files will be accessed and scanned for the plugin inside.
	 *
	 * @param parent a class which is in the same folder as the "plugins" folder (used for finding the absolute
	 *               path to "plugins")
	 */
	public PluginManager(Object parent, String pluginsPackage) {
		try {
			rootPath = new File(parent.getClass().getResource("/").toURI());
		} catch (URISyntaxException e) {}

		this.pluginsPackage = pluginsPackage;

		pluginsPath = new File(rootPath, pluginsPackage.replace('.', File.separatorChar));
		if (!pluginsPath.exists()) {
			return;
		}

		Debug.println("Scanning for plugins in: " + pluginsPath.getPath());

		for (File plugin : pluginsPath.listFiles()) {
			String pluginFile = null;

			if (plugin.isDirectory()) {
				pluginFile = scanFolder(plugin);
			} else if (plugin.getName().endsWith(".jar")) {
				pluginFile = scanJar(plugin);
			}

			if (pluginFile == null) {
				continue;
			}

			Debug.println("Plugin found: " + pluginFile);

			Plugin<T> entry = loadPluginEntry(pluginFile);
			if (entry != null) {
				pluginEntries.add(entry);
			}
		}
	}

	private String scanFolder(File folder) {
		for (File f : folder.listFiles()) {
			String filename = f.getName();
			if (f.isFile() && filename.equals("PluginEntry.class")) {
				return pluginsPackage + "." + folder.getName() + "." + filename.substring(0, filename.length() - 6);
			}
		}

		return null;
	}

	private String scanJar(File jarFile) {
		try {
			String pluginFile = pluginsPackage + findJARPluginClass(jarFile.getPath());

			Debug.println("Plugin JAR was found: " + pluginFile);
			PluginManager.addJARToClassPath(jarFile);

			return pluginFile;
		} catch (PluginNotFoundException e) {
			// Change error to warning.
			Debug.println("Warning: " + e.getMessage());
		} catch (IOException e) {
			// Change error to warning.
			Debug.println("Warning: " + "Can not read JAR: " + jarFile.getPath() + ". Cause: " + e.getMessage());
		}

		return null;
	}

	private static String findJARPluginClass(String path) throws PluginNotFoundException, IOException {
		String plugin = null;
		try (JarFile file = new JarFile(path)) {
			for (Enumeration<JarEntry> e = file.entries(); e.hasMoreElements(); ) {
				JarEntry f = e.nextElement();
				String   s = f.getName();

				if (s.equals("PluginEntry.class")) {
					plugin = s;
				}
			}

			if (plugin == null) {
				throw new PluginNotFoundException("Plugin JAR contains no plugin: " + path);
			}

			// Separator char inside zip is always '/'.
			return plugin.substring(0, plugin.length() - 6).replace('/', '.');
		}
	}

	private static void addJARToClassPath(File f) {
		try {
			URL[] oldURLs = ((URLClassLoader)PluginManager.classLoader).getURLs();

			URL[] newURLs = new URL[oldURLs.length + 1];

			System.arraycopy(oldURLs, 0, newURLs, 0, oldURLs.length);

			newURLs[newURLs.length - 1] = f.toURI().toURL();

			PluginManager.classLoader = new URLClassLoader(newURLs, PluginManager.classLoader);
		} catch (Exception e) {}
	}

	private Plugin<T> loadPluginEntry(String pluginFile) {
		try {
			Class<T> entryClass = (Class<T>)PluginManager.classLoader.loadClass(pluginFile);
			return (Plugin<T>)entryClass.getConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public int getNumPlugins() {
		return pluginEntries.size();
	}

	public ArrayList<Plugin<T>> getPluginEntrances() {
		return pluginEntries;
	}
}
