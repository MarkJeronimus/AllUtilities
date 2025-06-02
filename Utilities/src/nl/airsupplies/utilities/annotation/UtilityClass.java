package nl.airsupplies.utilities.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import net.jcip.annotations.ThreadSafe;

/**
 * Marker for a class which contains utilities and is stateless.
 * <p>
 * A utility class is similar to the {@link StaticClass}, {@link ConstClass}, and singleton, except that it is (or at
 * least should be) stateless (even during the duration of a method call). Unlike {@code ConstClass} this is intended to
 * solely contain 'utilities', i.e., methods that do work, and {@code public static final} fields that contain
 * primitives, immutable or thread-bound object.
 * <p>
 * A more restrictive version is {@link ConstClass} which disallows utilities.
 * <p>
 * This annotation implies {@link ThreadSafe}, but be sure to tell your annotation processor that too.
 *
 * @author Mark Jeronimus
 */
// Created 2017-02-14
@Target(ElementType.TYPE)
public @interface UtilityClass {
}
