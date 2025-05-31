package nl.airsupplies.utilities.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * A static class is similar to a singleton (i.e. stateful), except that it can't (or at least should shouldn't)
 * extend or implement other classes/interfaces. This can create a clearer picture in the developer's minds. The VM
 * overhead is slightly less since no instance is created.
 *
 * @author Mark Jeronimus
 */
// Created 2017-02-20
@Target(ElementType.TYPE)
public @interface StaticClass {
}
