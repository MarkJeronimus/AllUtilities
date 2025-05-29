/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2024 Mark Jeronimus. All Rights Reversed.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package nl.airsupplies.utilities.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import net.jcip.annotations.ThreadSafe;

/**
 * A utility class is similar to the {@link StaticClass}, {@link ConstClass}, and singleton, except that it is (or at
 * least should be) stateless (even during the duration of a method call). Unlike {@code ConstClass} this is intended to
 * solely contain 'utilities', i.e. methods that do work, and {@code public static final} fields that contain
 * primitives, immutable or thread-bound object.
 * <p>
 * A more restrictive version is {@link ConstClass} which disallows methods.
 * <p>
 * This annotation implies {@link ThreadSafe}, but be sure to tell your annotation processor that too.
 *
 * @author Mark Jeronimus
 */
// Created 2017-02-14
@Target(ElementType.TYPE)
public @interface UtilityClass {
}
