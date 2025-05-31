package nl.airsupplies.utilities.net;

import java.util.ArrayList;

/**
 * @author Mark Jeronimus
 */
// Created 2005-11-03
public interface NetFunction {
	String getFunctionString();

	int getNumRequiredArguments();

	ArrayList<Object> process(ArrayList<Object> values);
}
