package nl.airsupplies.utilities.collection;

/**
 * @author Mark Jeronimus
 */
// Created 2006-10-18
public class BidirectionalLinkedList {
	private int               size;
	private LinkedListElement head = null;
	private LinkedListElement tail = null;

	public LinkedListElement getHead() {
		return head;
	}

	public int size() {
		return size;
	}

	public void add(Object o) {
		LinkedListElement e = new LinkedListElement(o);
		if (size == 0) {
			head = tail = e.next = e.previous = e;
		} else {
			e.next     = head;
			e.previous = tail;
			tail       = tail.next = head.previous = e;
		}
		size++;
	}

	public void addBefore(LinkedListElement e, Object o) {
		LinkedListElement n = new LinkedListElement(o);
		n.next          = e;
		n.previous      = e.previous;
		e.previous.next = e.previous = n;

		size++;
	}
}
