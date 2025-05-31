package nl.airsupplies.utilities;

import java.lang.management.ManagementFactory;

/**
 * @author Mark Jeronimus
 */
// Created 2017-06-30
public final class DebugUtilities {
	public static boolean isDebugging() {
		return ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;
	}
}
