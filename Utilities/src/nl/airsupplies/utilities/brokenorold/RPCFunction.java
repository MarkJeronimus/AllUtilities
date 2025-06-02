package nl.airsupplies.utilities.brokenorold;

import java.util.ArrayList;

/**
 * @author Mark Jeronimus
 */
// Created 2005-11-03
public interface RPCFunction {
	String getName();

	int getNumArguments();

	ArrayList<Object> process(ArrayList<Object> values);
}
