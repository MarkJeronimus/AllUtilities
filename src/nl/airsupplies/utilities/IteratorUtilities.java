package nl.airsupplies.utilities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nl.airsupplies.utilities.annotation.UtilityClass;

/**
 * @author Mark Jeronimus
 */
// Created 2017-02-10
@UtilityClass
public final class IteratorUtilities {
	public static <T> List<T> toList(Iterator<T> iterator, int expectedCapacity) {
		List<T> list = new ArrayList<>(expectedCapacity);
		while (iterator.hasNext()) {
			list.add(iterator.next());
		}
		return list;
	}
}
