package nl.airsupplies.utilities.nodes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Mark Jeronimus
 */
// Created 2019-08-30
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IntParam {
	String description();

	int min() default 0;

	int max() default Integer.MAX_VALUE;
}
