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
package org.digitalmodular.utilities.container;

import java.awt.BasicStroke;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Paint;
import java.io.File;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.URL;
import java.security.AccessController;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.imageio.ImageIO;

/**
 * Implementation and javadoc taken from {@link com.sun.istack.internal.Pool}.
 *
 * @author Mark Jeronimus
 */
// Created 2012-05-17
public class Pool<T> {
	private static final Class<?>[] IMMUTABLE_CLASSES = {Byte.class, Short.class, Character.class,
	                                                     Integer.class, Long.class, Float.class, Double.class, //
	                                                     Boolean.class, String.class, BigInteger.class,
	                                                     BigDecimal.class, //
	                                                     Locale.class, Font.class, BasicStroke.class, Paint.class,
	                                                     Cursor.class, //
	                                                     File.class, URL.class, UUID.class, Inet4Address.class};

	private static final Class<?>[] NON_INSTANTIABLE_CLASSES = {System.class, Math.class, Math.class,
	                                                            Array.class, Collections.class, Arrays.class,
	                                                            AccessController.class, ImageIO.class};

	private static final HashMap<Class<?>, Pool<?>> POOLS = new HashMap<>();

	private Class<T>                 clazz;
	private ConcurrentLinkedQueue<T> pool = new ConcurrentLinkedQueue<>();

	/**
	 * @param clazz the type of class this {@link Pool} is going to serve. Should be equal to the generic parameter to
	 *              prevent runtime errors. This is needed to because of type erasure (during runtime the generic
	 *              parameter just becomes {@link Object}). In fact, the generic parameter is just a formality to take
	 *              away the need to cast in the user code.
	 */
	public static <T> Pool<T> getPool(Class<T> clazz) {
		if (clazz.isArray()) {
			throw new IllegalAccessError("Class cannot be an array type (use the other contructor for arrays).");
		}
		if (clazz.isInterface()) {
			throw new IllegalAccessError("Class must be a concrete type.");
		}
		if (Pool.isImmutable(clazz)) {
			throw new IllegalAccessError("Class cannot be an immutable type.");
		}
		if (!Pool.isInstantiable(clazz)) {
			throw new IllegalAccessError("Class must be an instantiable type.");
		}

		Pool<T> pool = (Pool<T>)POOLS.get(clazz);
		if (pool == null) {
			pool = new Pool<>(clazz);
			POOLS.put(clazz, pool);
		}

		return pool;
	}

	private Pool(Class<T> clazz) {
		this.clazz = clazz;
	}

	/**
	 * Gets a new object from the pool.
	 * <p>
	 * If no object is available in the pool, this method creates a new one.
	 *
	 * @return always non-null.
	 */
	public T take() {
		T t = pool.poll();
		if (t == null) {
			return create();
		}
		return t;
	}

	/**
	 * Returns an object back to the pool.
	 */
	public void recycle(T t) {
		if (t.getClass() != clazz) {
			throw new IllegalArgumentException("Attempted to offer a subclass of the pool's class");
		}

		pool.offer(t);
	}

	/**
	 * Creates a new instance of object.
	 * <p>
	 * This method is used when someone wants to {@link #take() take} an object from an empty pool.
	 * <p>
	 * Also note that multiple threads may call this method concurrently.
	 */
	protected T create() {
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static <T> boolean isImmutable(Class<T> subClass) {
		for (Class<?> superClass : IMMUTABLE_CLASSES) {
			if (superClass.isAssignableFrom(subClass)) {
				return true;
			}
		}
		return false;
	}

	private static <T> boolean isInstantiable(Class<T> subClass) {
		for (Class<?> superClass : NON_INSTANTIABLE_CLASSES) {
			if (superClass.isAssignableFrom(subClass)) {
				return false;
			}
		}
		return true;
	}
}
