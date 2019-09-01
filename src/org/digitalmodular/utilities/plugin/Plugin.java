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
		return this.getName().compareTo(p2.getName());
	}
}
