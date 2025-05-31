package nl.airsupplies.utilities.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Signifies that a configuration or property is hardcoded in the source, while it should ideally either be in a
 * configuration file or configurable in runtime. Only use this for debugging and not for production software.
 * <p>
 * Tip: If possible, make the IDE generate warnings where this annotation is used.
 *
 * @author Mark Jeronimus
 */
// Created 2017-01-05
@Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.SOURCE)
public @interface Hardcoded {
}
