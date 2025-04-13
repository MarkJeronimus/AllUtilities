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

package org.digitalmodular.utilities.annotation;

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
