package nl.airsupplies.utilities;

import java.lang.management.ManagementFactory;

import nl.airsupplies.utilities.annotation.UtilityClass;

/**
 * @author Mark Jeronimus
 */
// Created 2017-06-30
@UtilityClass
public final class DebugUtilities {
	public static final boolean DEBUGGING =
			ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;

	public static boolean isDebugging() {
		return DEBUGGING;
	}
}
