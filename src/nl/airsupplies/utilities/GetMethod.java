package nl.airsupplies.utilities;

import java.lang.reflect.Method;

/**
 * @author Mark Jeronimus
 */
// Created 2016-03-27
public class GetMethod {
	public Method getMethod() {
		Method method = getClass().getEnclosingMethod();

		if (method == null) {
			throw new AssertionError(
					GetMethod.class.getSimpleName() + " should be instantiated anonymously.");
		}

		return method;
	}
}
