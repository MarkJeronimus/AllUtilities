package org.digitalmodular.utilities.nodes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Zom-B
 */
// Created 2019-08-30
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DoubleListParam {
	String description();

	int minLength() default 0;

	int maxLength() default Integer.MAX_VALUE;

	double min() default Double.NEGATIVE_INFINITY;

	double max() default Double.POSITIVE_INFINITY;

	boolean minIsInclusive() default true;

	boolean maxIsInclusive() default true;
}
