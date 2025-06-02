package nl.airsupplies.utilities.plugin;

/**
 * A lightweight wrapper for plugins. Any package with a purpose that it has to be dynamically loadable should have a
 * type that extends Plugin. By loading this type (usually by the <tt>PluginManager</tt>) the actual plugin is not
 * loaded, rather a lightweight entrance to the plugin has been loaded. This class can provide plugin properties, such
 * as the name. At the time of calling the method <tt>getInstance()</tt>, the actual plugin instance should be created.
 *
 * @author Mark Jeronimus
 */
// Created 2006-01-14
public abstract class Plugin<P> implements Comparable<Plugin<P>> {
	public abstract String getName();

	public abstract P instantiate();

	@Override
	public int compareTo(Plugin<P> p2) {
		return getName().compareTo(p2.getName());
	}
}
