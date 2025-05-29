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

package nl.airsupplies.utilities;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mark Jeronimus
 */
// Created 2017-05-23
public final class ClassUtilities {
	private static final Map<Class<?>, Class<?>> BOXED_TO_PRIMITIVE = new HashMap<>();

	static {
		BOXED_TO_PRIMITIVE.put(Boolean.class, Boolean.TYPE);
		BOXED_TO_PRIMITIVE.put(Byte.class, Byte.TYPE);
		BOXED_TO_PRIMITIVE.put(Short.class, Short.TYPE);
		BOXED_TO_PRIMITIVE.put(Integer.class, Integer.TYPE);
		BOXED_TO_PRIMITIVE.put(Long.class, Long.TYPE);
		BOXED_TO_PRIMITIVE.put(Float.class, Float.TYPE);
		BOXED_TO_PRIMITIVE.put(Double.class, Double.TYPE);
		BOXED_TO_PRIMITIVE.put(Character.class, Character.TYPE);
	}

	public static Class<?> boxedToPrimitive(Class<?> type) {
		Class<?> primitive = BOXED_TO_PRIMITIVE.get(type);
		return primitive == null ? type : primitive;
	}
}
