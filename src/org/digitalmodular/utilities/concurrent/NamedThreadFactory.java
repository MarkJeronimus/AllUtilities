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

package org.digitalmodular.utilities.concurrent;

import java.util.concurrent.ThreadFactory;

import static org.digitalmodular.utilities.ValidatorUtilities.requireNonNull;

/**
 * Wrapper for a thread factory to give threads a meaningful name.
 *
 * @author Mark Jeronimus
 */
// Created 2015-09-09
public class NamedThreadFactory implements ThreadFactory {
	private final ThreadFactory factory;
	private final String        prefix;

	public NamedThreadFactory(ThreadFactory factory, String prefix) {
		this.factory = requireNonNull(factory, "factory");
		this.prefix  = requireNonNull(prefix, "prefix");
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread t = factory.newThread(r);
		t.setName(prefix + '-' + t.getName());
		return t;
	}
}
