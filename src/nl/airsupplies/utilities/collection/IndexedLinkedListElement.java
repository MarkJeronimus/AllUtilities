package nl.airsupplies.utilities.collection;

/**
 * Classes that extend this class can be stored in a IndexedLinkedList collection. The IndexedLinkedList class uses an
 * index table with 128 entry's for all 7-bit ASCII characters.
 *
 * @author Mark Jeronimus
 */
// Created 2006-05-18
public abstract class IndexedLinkedListElement implements Comparable<IndexedLinkedListElement> {
	public IndexedLinkedListElement next     = null;
	public IndexedLinkedListElement previous = null;

	public boolean equals(IndexedLinkedListElement o) {
		return toString().equals(o.toString());
	}

	@Override
	public int compareTo(IndexedLinkedListElement o) {
		return getIdentifier().compareTo(o.getIdentifier());
	}

	public abstract String getIdentifier();
}
