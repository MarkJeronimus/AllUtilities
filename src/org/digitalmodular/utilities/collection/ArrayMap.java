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
package org.digitalmodular.utilities.collection;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Mark Jeronimus
 */
// Created 2008-01-23
public class ArrayMap<K, V> extends AbstractMap<K, V> implements Cloneable, Serializable {
	ArrayList<K> keys   = new ArrayList<>();
	ArrayList<V> values = new ArrayList<>();

	@Override
	public void clear() {
		this.keys.clear();
		this.values.clear();
	}

	@Override
	public boolean containsKey(Object key) {
		return this.keys.contains(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return this.values.contains(value);
	}

	public V getValue(int i) {
		return this.values.get(i);
	}

	public K getKey(int i) {
		return this.keys.get(i);
	}

	@Override
	public V get(Object key) {
		return this.values.get(this.keys.indexOf(key));
	}

	public V remove(int i) {
		this.keys.remove(i);
		return this.values.remove(i);
	}

	@Override
	public boolean isEmpty() {
		return this.keys.isEmpty();
	}

	@Override
	public V put(K key, V value) {
		int i = this.keys.indexOf(key);
		if (i != -1) {
			return this.values.set(i, value);
		}

		this.keys.add(key);
		this.values.add(value);
		return null;
	}

	@Override
	public V remove(Object key) {
		int i = this.keys.indexOf(key);
		if (i != -1) {
			this.keys.remove(i);
			return this.values.remove(i);
		}

		return null;
	}

	@Override
	public int size() {
		return this.keys.size();
	}

	@Override
	public Collection<V> values() {
		return this.values;
	}

	public Collection<K> keys() {
		return this.keys;
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		return new AbstractSet<Entry<K, V>>() {
			@Override
			public Iterator<Entry<K, V>> iterator() {
				return new Iterator<Entry<K, V>>() {
					int i = 0;

					@Override
					public boolean hasNext() {
						return this.i < ArrayMap.this.keys.size();
					}

					@Override
					public Entry<K, V> next() {
						return new SimpleImmutableEntry<>(ArrayMap.this.keys.get(this.i), ArrayMap.this.values.get(this.i++));
					}

					@Override
					public void remove() {
						ArrayMap.this.keys.remove(--this.i);
						ArrayMap.this.values.remove(this.i);
					}
				};
			}

			@Override
			public int size() {
				return ArrayMap.this.keys.size();
			}
		};
	}
}
