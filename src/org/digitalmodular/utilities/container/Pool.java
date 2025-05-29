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

package org.digitalmodular.utilities.container;

import java.awt.BasicStroke;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Paint;
import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
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
 * Implementation and javadoc taken from {@code com.sun.istack.internal.Pool} and cleaned up a bit.
 *
 * @param <T> the type of objects in the pool.
 * @author Mark Jeronimus
 */
// Created 2012-05-17
public final class Pool<T> {
	private static final Class<?>[] IMMUTABLE_CLASSES = {
			Byte.class, Short.class, Character.class, Integer.class, Long.class, Float.class, Double.class,
			Boolean.class, String.class, BigInteger.class, BigDecimal.class,
			Locale.class, Font.class, BasicStroke.class, Paint.class, Cursor.class,
			File.class, URL.class, UUID.class, Inet4Address.class};

	@SuppressWarnings("removal")
	private static final Class<?>[] NON_INSTANTIABLE_CLASSES = {
			System.class, Math.class, Math.class,
			Array.class, Arrays.class, Collections.class,
			AccessController.class, ImageIO.class};

	private static final HashMap<Class<?>, Pool<?>> POOLS = new HashMap<>();

	private final Class<T>                 clazz;
	private final ConcurrentLinkedQueue<T> pool = new ConcurrentLinkedQueue<>();

	public static <T> Pool<T> getPool(Class<T> clazz) {
		if (clazz.isArray()) {
			throw new IllegalAccessError("'clazz' cannot be an array type (use the other constructor for arrays).");
		} else if (clazz.isInterface()) {
			throw new IllegalAccessError("'clazz' must be a concrete type.");
		} else if (isImmutable(clazz)) {
			throw new IllegalAccessError("'clazz' cannot be an immutable type.");
		} else if (!isInstantiable(clazz)) {
			throw new IllegalAccessError("'clazz' must be an instantiable type.");
		}

		@SuppressWarnings("unchecked")
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
	 * @throws Throwable if the pool was empty and the object creation fails.
	 */
	public T take() throws Throwable {
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
	@SuppressWarnings("ProhibitedExceptionThrown")
	private T create() throws Throwable {
		try {
			return clazz.getConstructor().newInstance();
		} catch (NoSuchMethodException | IllegalAccessException | InstantiationException ex) {
			throw ex.getCause() != null ? ex.getCause() : ex;
		} catch (InvocationTargetException ex) {
			throw ex.getTargetException();
		}
	}

	private static <T> boolean isImmutable(Class<T> subClass) {
		return Arrays.stream(IMMUTABLE_CLASSES)
		             .anyMatch(superClass -> superClass.isAssignableFrom(subClass));
	}

	private static <T> boolean isInstantiable(Class<T> subClass) {
		return Arrays.stream(NON_INSTANTIABLE_CLASSES)
		             .noneMatch(superClass -> superClass.isAssignableFrom(subClass));
	}
}
