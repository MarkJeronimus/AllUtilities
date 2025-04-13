/*
 * This file is part of Utilities.
 *
 * Copyleft 2024 Mark Jeronimus. All Rights Reversed.
 *
 * This program is free software: you can redistribute it and/or modify it under the terms
 * of the GNU General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.digitalmodular.utilities;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.digitalmodular.utilities.annotation.Hardcoded;

/**
 * @author Mark Jeronimus
 */
// Created 2016-04-23
public final class LoggerUtilities {
	@Hardcoded
	public static final String LOGGER_TEMPLATE             = "[%1$tY%1$tm%1$tdT%1$tH%1$tM%1$tS.%1$tL %4$s] %5$s%6$s%n";
	public static final String LOGGER_TEMPLATE_WITH_METHOD =
			"[%1$tY%1$tm%1$tdT%1$tH%1$tM%1$tS.%1$tL %4$s] %2$s: %5$s%6$s%n";

	public static void configure(Level level) {
		configure(level, LOGGER_TEMPLATE_WITH_METHOD);
	}

	public static void configure(Level level, String format) {
		System.setProperty("java.util.logging.SimpleFormatter.format", format);
		Logger.getGlobal().setLevel(level);
		Logger.getGlobal().getParent().removeHandler(Logger.getGlobal().getParent().getHandlers()[0]);
		Logger.getGlobal().getParent().addHandler(new ConsoleHandler());
		Logger.getGlobal().getParent().getHandlers()[0].setLevel(Level.ALL);
	}

	public static void configure(Level level, Handler customHandler) {
		Logger.getGlobal().setLevel(level);
		Logger.getGlobal().getParent().removeHandler(Logger.getGlobal().getParent().getHandlers()[0]);
		Logger.getGlobal().getParent().addHandler(customHandler);
		Logger.getGlobal().getParent().getHandlers()[0].setLevel(Level.ALL);
	}
}
