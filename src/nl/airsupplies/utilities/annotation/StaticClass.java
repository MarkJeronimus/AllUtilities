package nl.airsupplies.utilities.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import net.jcip.annotations.ThreadSafe;

/**
 * A marker for a class which is stateful but static (similar to a singleton).
 * <p>
 * A static class is similar to a singleton (i.e., stateful), except that it can't (or at least should shouldn't)
 * extend or implement other classes/interfaces. This can create a clearer picture in the developer's minds. The VM
 * overhead is slightly less since no instance is created. Usually this should be used in conjunction with either
 * {@link ThreadBounded} or {@link ThreadSafe}.
 * <p>
 * A more restrictive version is {@link ConstClass} which is stateless.
 * <p>
 * For a stateless version, see {@link UtilityClass}.
 *
 * @author Mark Jeronimus
 */
// Created 2017-02-20
@Target(ElementType.TYPE)
public @interface StaticClass {
}
