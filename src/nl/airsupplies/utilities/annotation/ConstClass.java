package nl.airsupplies.utilities.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import net.jcip.annotations.ThreadSafe;

/**
 * Marker for a class which is stateless and has no utilities.
 * <p>
 * A const class is similar to the {@link StaticClass}, {@link UtilityClass}, and singleton, except that it is (or at
 * least should be) stateless. Unlike {@code UtilityClass} this is not intended to contain 'utilities', i.e., public
 * methods that do work. It's only public members should be {@code public static final} fields that contain primitives,
 * immutable or thread-bound objects, or getters for constant data.
 * <p>
 * The {@link UtilityClass} is for classes which are also stateless but contains utilities.
 * <p>
 * The {@link StaticClass} is for classes which are also static but is stateful (similar to a singleton).
 * <p>
 * This annotation implies {@link ThreadSafe}, but be sure to tell that to your annotation processor.
 *
 * @author Mark Jeronimus
 */
// Created 2017-02-20
@Target(ElementType.TYPE)
public @interface ConstClass {
}
