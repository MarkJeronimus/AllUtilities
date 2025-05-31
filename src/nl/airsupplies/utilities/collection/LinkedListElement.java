package nl.airsupplies.utilities.collection;

/**
 * @author Mark Jeronimus
 */
public class LinkedListElement {
	public Object value;

	public LinkedListElement next;
	public LinkedListElement previous;

	public LinkedListElement(Object value) {
		this.value = value;
	}
}
