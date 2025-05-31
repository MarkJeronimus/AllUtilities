package nl.airsupplies.utilities;

import java.util.HashMap;
import java.util.Map;

import nl.airsupplies.utilities.annotation.UtilityClass;

/**
 * @author Mark Jeronimus
 */
// Created 2017-05-23
@UtilityClass
public final class ClassUtilities {
	private static final Map<Class<?>, Class<?>> BOXED_TO_PRIMITIVE = new HashMap<>(8);

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
