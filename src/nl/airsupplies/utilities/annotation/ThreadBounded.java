package nl.airsupplies.utilities.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.jcip.annotations.ThreadSafe;

/**
 * Declares that the annotated element may only be executed from only a single thread throughout it's lifetime. The
 * thread name may optionally specified for reflective purposes and/or clarity, e.g. for the Swing EDT, the name
 * should be {@code "AWT-EventQueue-0"}.
 * <p>
 * This annotation implies {@link ThreadSafe}.
 *
 * @author Mark Jeronimus
 */
// Created 2017-02-20
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.PACKAGE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ThreadBounded {
	String value() default "Any";
}
