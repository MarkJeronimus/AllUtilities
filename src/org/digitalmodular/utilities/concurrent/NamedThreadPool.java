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

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import static org.digitalmodular.utilities.ValidatorUtilities.requireNonNull;

/**
 * Almost identical copy of {@link Executors.DefaultThreadFactory} that allows
 * changing the prefix string.
 *
 * @author Mark Jeronimus
 */
// Created 2015-08-28
public class NamedThreadPool implements ThreadFactory {
	private final ThreadGroup   group;
	private final String        namePrefix;
	private final AtomicInteger threadNumber = new AtomicInteger(1);

	public NamedThreadPool(String prefix) {
		namePrefix = requireNonNull(prefix, "prefix");

		SecurityManager s = System.getSecurityManager();
		group = s != null ?
		        s.getThreadGroup() :
		        Thread.currentThread().getThreadGroup();
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
		if (t.isDaemon()) {
			t.setDaemon(false);
		}

		if (t.getPriority() != Thread.NORM_PRIORITY) {
			t.setPriority(Thread.NORM_PRIORITY);
		}

		return t;
	}
}
