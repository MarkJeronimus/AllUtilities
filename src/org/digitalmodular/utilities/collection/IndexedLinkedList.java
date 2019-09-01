/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2019 Mark Jeronimus. All Rights Reversed.
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
 * along with AllUtilities. If not, see <http://www.gnu.org/licenses/>.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.digitalmodular.utilities.collection;

import java.util.List;
import java.util.Set;

/**
 * A circular bidirectional linked-list implementation using a 7-bit ASCII driven index table for speed. It contains
 * elements of the type IndexedLinkedListElement which have a method getName() that returns the name string. The first
 * character of the name is the position in the index table. In theory this implementation can be 128 times faster than
 * a linear linked list, but in practice, it will be around 20-50 times (Depending on the distribution of the first
 * character of the name strings) analogous to a custom hash list with a bad hash algorithm. The main advantage of this
 * collection over a HashTable is that it's possible to find elements with a name is close to a given name. The only
 * current implementation of 'close to' is finding an element with a name which alphabetically follows a given name the
 * closest, but other algorithms, like spelling check, might be implemented in the future.
 * <p>
 * It is functionally compatible with the <tt>Collection</tt> interface, although it doesn't implement it because of
 * unchecked conversion rule conflicts. (the <tt>IndexedLinkedList</tt> can only store elements of the type
 * <tt>IndexedLinkedListElements</tt> , and <tt>Collection</tt> requires it to store <tt>Object</tt>)
 *
 * @author Mark Jeronimus
 * @see IndexedLinkedListElement
 */
// Created 2006-05-18
public class IndexedLinkedList implements Iterable<IndexedLinkedListElement> {

	private final IndexedLinkedListElement[] table = new IndexedLinkedListElement[0x80];
	private       int                        elementCount;

	public IndexedLinkedList() {
		clear();
	}

	public IndexedLinkedList(IndexedLinkedList list) {
		this();
		addAll(list);
	}

	/**
	 * Returns the number of elements in this collection. If this collection contains more than
	 * <tt>Integer.MAX_VALUE</tt> elements, returns <tt>Integer.MAX_VALUE</tt>.
	 *
	 * @return the number of elements in this collection
	 */
	public int size() {
		return elementCount;
	}

	/**
	 * Returns <tt>true</tt> if this collection contains no elements.
	 *
	 * @return <tt>true</tt> if this collection contains no elements
	 */
	public boolean isEmpty() {
		return elementCount == 0;
	}

	/**
	 * Returns <tt>true</tt> if this collection contains the specified element. More formally, returns <tt>true</tt> if
	 * and only if this collection contains at least one element <tt>e</tt> such that <tt>(element==null ? e==null :
	 * o.equals(element))</tt>.
	 *
	 * @param element element whose presence in this collection is to be tested.
	 * @return <tt>true</tt> if this collection contains the specified element
	 * @throws NullPointerException if a link in the linked list is broken.
	 * @throws NullPointerException if the specified element is <tt>null</tt>.
	 */
	public boolean contains(IndexedLinkedListElement element) {
		if (elementCount == 0) {
			return false;
		}

		char                     index   = element.getIdentifier().charAt(0);
		IndexedLinkedListElement current = table[index];

		// Check if a token exists beginning with this char.
		if (current == null) {
			return false;
		}

		// Find the the first token starting with this char which is
		// alphabetically after the supplied token.
		IndexedLinkedListElement first = current;
		while (element.compareTo(current) > 0) {
			current = current.next;

			// Check if we wrapped around the beginning of the list. Rare case
			// scenario so don't bother about speed.
			if (current == first) {
				return false;
			}

			// Check if this token still starts with the same character.
			if (current.getIdentifier().charAt(0) != index) {
				return false;
			}
		}

		// Check if this token is indeed equal the supplied string.
		return current.getIdentifier().equals(element.getIdentifier());
	}

	/**
	 * Returns an iterator over the elements in this collection. There are no guarantees concerning the order in which
	 * the elements are returned (unless this collection is an instance of some class that provides a guarantee).
	 *
	 * @return an <tt>IndexedLinkedListIterator</tt> over the elements in this collection
	 * @throws NullPointerException if a link in the linked list is broken.
	 */
	@Override
	public IndexedLinkedListIterator iterator() {
		return new IndexedLinkedListIterator(this);
	}

	/**
	 * Returns an array containing all of the elements in this collection. If the collection makes any guarantees as to
	 * what order its elements are returned by its iterator, this method must return the elements in the same order.
	 * <p>
	 * <p>
	 * The returned array will be "safe" in that no references to it are maintained by this collection. (In other
	 * words,
	 * this method must allocate a new array even if this collection is backed by an array). The caller is thus free to
	 * modify the returned array.
	 * <p>
	 * <p>
	 * This method acts as bridge between array-based and collection-based APIs.
	 *
	 * @return an array containing all of the elements in this collection
	 * @throws NullPointerException if a link in the linked list is broken.
	 */
	public IndexedLinkedListElement[] toArray() {
		final IndexedLinkedListElement[] array = new IndexedLinkedListElement[elementCount];

		// Fill the array.
		int i = 0;
		for (IndexedLinkedListElement e : this) {
			array[i++] = e;
		}

		return array;
	}

	/**
	 * Returns an array containing all of the elements in this collection; the runtime type of the returned array is
	 * that of the specified array. If the collection fits in the specified array, it is returned therein. Otherwise, a
	 * new array is allocated with the runtime type of the specified array and the size of this collection.
	 * <p>
	 * <p>
	 * If this collection fits in the specified array with room to spare (i.e., the array has more elements than this
	 * collection), the element in the array immediately following the end of the collection is set to <tt>null</tt>.
	 * This is useful in determining the length of this collection <i>only </i> if the caller knows that this
	 * collection
	 * does not contain any <tt>null</tt> elements.)
	 * <p>
	 * <p>
	 * If this collection makes any guarantees as to what order its elements are returned by its iterator, this method
	 * must return the elements in the same order.
	 * <p>
	 * <p>
	 * Like the <tt>toArray</tt> method, this method acts as bridge between array-based and collection-based APIs.
	 * Further, this method allows precise control over the runtime type of the output array, and may, under certain
	 * circumstances, be used to save allocation costs
	 * <p>
	 * <p>
	 * Suppose <tt>list</tt> is a <tt>IndexedLinkedList</tt> known to contain only Bicycle's. The following code can be
	 * used to dump the list into a newly allocated array of <tt>Bicycle</tt>:
	 *
	 * <pre>
	 * Bicycle[]	x	= (Bicycle[])list.toArray(new Bicycle[0]);
	 * </pre>
	 * <p>
	 * <p>
	 * <p>
	 * Note that <tt>toArray(new Object[0])</tt> is identical in function to <tt>toArray()</tt>.
	 *
	 * @param array the array into which the elements of this collection are to be stored, if it is big enough;
	 *              otherwise, a new array of the same runtime type is allocated for this purpose.
	 * @return an array containing the elements of this collection
	 * @throws ArrayStoreException  the runtime type of the specified array is not a supertype of the runtime type of
	 *                              every element in this collection.
	 * @throws NullPointerException if a link in the linked list is broken.
	 * @throws NullPointerException if the specified array is <tt>null</tt>.
	 */
	public IndexedLinkedListElement[] toArray(IndexedLinkedListElement[] array) {
		// If the array is too small then reallocate it.
		if (array.length < elementCount) {
			array = (IndexedLinkedListElement[])java.lang.reflect.Array
					.newInstance(array.getClass().getComponentType(),
					             elementCount);
		}

		int i = 0;
		for (IndexedLinkedListElement e : this) {
			array[i++] = e;
		}

		// Append null if the array is too large.
		for (; i < array.length; i++) {
			array[i] = null;
		}

		return array;
	}

	/**
	 * Ensures that this collection contains the specified element (optional operation). Returns <tt>true</tt> if this
	 * collection changed as a result of the call. (Returns <tt>false</tt> if this collection does not permit
	 * duplicates
	 * and already contains the specified element.)
	 * <p>
	 * <p>
	 * Collections that support this operation may place limitations on what elements may be added to this collection.
	 * In particular, some collections will refuse to add <tt>null</tt> elements, and others will impose
	 * restrictions on
	 * the type of elements that may be added. Collection classes should clearly specify in their documentation any
	 * restrictions on what elements may be added.
	 * <p>
	 * <p>
	 * If a collection refuses to add a particular element for any reason other than that it already contains the
	 * element, it <i>must </i> throw an exception (rather than returning <tt>false</tt>). This preserves the invariant
	 * that a collection always contains the specified element after this call returns.
	 *
	 * @param newElement element whose presence in this collection is to be ensured.
	 * @return <tt>true</tt> if this collection changed as a result of the call
	 * @throws NullPointerException if a link in the linked list is broken.
	 * @throws NullPointerException if the specified element is <tt>null</tt>.
	 */
	public boolean add(IndexedLinkedListElement newElement) {
		final String                   newIdentifier = newElement.getIdentifier();
		final IndexedLinkedListElement next          = findAfter(newIdentifier);

		// Check if it was found.
		if (next != null && next.getIdentifier().equals(newIdentifier)) {
			return false;
		}

		// Insert it into the list.
		if (next != null) {
			// Find the previous of the found token.
			IndexedLinkedListElement previousElement = next.previous;

			// Get the previous token and link it to the new token and link the
			// new token to the next token.
			previousElement.next = next.previous = newElement;
			newElement.previous = previousElement;
			newElement.next = next;
		} else {
			newElement.next = newElement.previous = newElement;
		}

		// Update the index, when necessary.
		char index = newIdentifier.charAt(0);
		if (table[index] == null || newElement.compareTo(table[index]) < 0) {
			table[index] = newElement;
		}

		elementCount++;
		return true;
	}

	/**
	 * Removes a single instance of the specified element from this collection, if it is present (optional operation).
	 * More formally, removes an element <tt>e</tt> such that <tt>(o==null ? e==null : o.equals(e))</tt>, if this
	 * collection contains one or more such elements. Returns true if this collection contained the specified element
	 * (or equivalently, if this collection changed as a result of the call).
	 *
	 * @param element element to be removed from this collection, if present.
	 * @return <tt>true</tt> if this collection changed as a result of the call
	 * @throws NullPointerException if a link in the linked list is broken.
	 * @throws NullPointerException if the specified element is <tt>null</tt>.
	 */
	public boolean remove(IndexedLinkedListElement element) {
		// If the supplied element is not equal to an element in the list, get
		// it.
		element = find(element.getIdentifier());

		// Check if the elements exists.
		if (element == null) {
			return false;
		}

		// Connect the next and previous of this element with each other.
		element.previous.next = element.next;
		element.next.previous = element.previous;

		// Special case: if this element is the first at its index of the list.
		char index = element.getIdentifier().charAt(0);
		if (table[index] == element) {
			// Check if the entire list or this index of the list is getting
			// empty.
			if (element.next == element || element.next.getIdentifier().charAt(0) != index) {
				table[index] = null;
			} else {
				table[index] = element.next;
			}
		}

		// Unlink this element.
		element.previous = element.next = null;

		elementCount--;
		return true;
	}

	/**
	 * Returns <tt>true</tt> if this collection contains all of the elements in the specified collection.
	 *
	 * @param list collection to be checked for containment in this collection.
	 * @return <tt>true</tt> if this collection contains all of the elements in the specified collection
	 * @throws NullPointerException if a link in the linked list is broken.
	 * @throws NullPointerException if the specified collection is <tt>null</tt>.
	 * @see #contains(org.digitalmodular.utilities.collection.containers.IndexedLinkedListElement)
	 */
	public boolean containsAll(IndexedLinkedList list) {
		for (IndexedLinkedListElement e : list) {
			if (!contains(e)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns <tt>true</tt> if this collection contains all of the elements in the specified collection.
	 *
	 * @param list collection to be checked for containment in this collection.
	 * @return <tt>true</tt> if this collection contains all of the elements in the specified collection
	 * @throws NullPointerException if a link in the linked list is broken.
	 * @throws NullPointerException if the specified collection is <tt>null</tt>.
	 * @see #contains(org.digitalmodular.utilities.collection.containers.IndexedLinkedListElement)
	 */
	public boolean addAll(IndexedLinkedList list) {
		boolean changed = false;
		for (IndexedLinkedListElement e : list) {
			changed |= add(e);
		}
		return changed;
	}

	/**
	 * Returns <tt>true</tt> if this collection contains all of the elements in the specified collection.
	 *
	 * @param list collection to be checked for containment in this collection.
	 * @return <tt>true</tt> if this collection contains all of the elements in the specified collection
	 * @throws NullPointerException if a link in the linked list is broken.
	 * @throws NullPointerException if the specified collection is <tt>null</tt>.
	 * @see #remove(org.digitalmodular.utilities.collection.containers.IndexedLinkedListElement)
	 */
	public boolean removeAll(IndexedLinkedList list) {
		boolean changed = false;
		for (IndexedLinkedListElement e : list) {
			changed |= remove(e);
		}
		return changed;
	}

	/**
	 * Retains only the elements in this collection that are contained in the specified collection (optional
	 * operation).
	 * In other words, removes from this collection all of its elements that are not contained in the specified
	 * collection.
	 *
	 * @param list elements to be retained in this collection.
	 * @return <tt>true</tt> if this collection changed as a result of the call
	 * @throws NullPointerException if a link in the linked list is broken.
	 * @throws NullPointerException if the specified collection is <tt>null</tt>.
	 * @see #remove(org.digitalmodular.utilities.collection.containers.IndexedLinkedListElement)
	 * @see #contains(org.digitalmodular.utilities.collection.containers.IndexedLinkedListElement)
	 */
	public boolean retainAll(IndexedLinkedList list) {
		boolean changed = false;
		for (IndexedLinkedListIterator i = iterator(); i.hasNext(); ) {
			IndexedLinkedListElement e = i.next();
			if (!list.contains(e)) {
				i.remove();
				changed = true;
			}
		}
		return changed;
	}

	/**
	 * Removes all of the elements from this collection (optional operation). This collection will be empty after this
	 * method returns unless it throws an exception.
	 */
	public void clear() {
		for (int i = 0; i < 0x80; i++) {
			table[i] = null;
		}
		elementCount = 0;
	}

	/**
	 * Compares the specified object with this collection for equality.
	 * <p>
	 * <p>
	 * While the <tt>Collection</tt> interface adds no stipulations to the general contract for the
	 * <tt>Object.equals</tt>, programmers who implement the <tt>Collection</tt> interface "directly" (in other words,
	 * create a class that is a <tt>Collection</tt> but is not a <tt>Set</tt> or a <tt>List</tt>) must exercise care if
	 * they choose to override the <tt>Object.equals</tt>. It is not necessary to do so, and the simplest course of
	 * action is to rely on <tt>Object</tt>'s implementation, but the implementer may wish to implement a "value
	 * comparison" in place of the default "reference comparison." (The <tt>List</tt> and <tt>Set</tt> interfaces
	 * mandate such value comparisons.)
	 * <p>
	 * <p>
	 * The general contract for the <tt>Object.equals</tt> method states that equals must be symmetric (in other words,
	 * <tt>a.equals(b)</tt> if and only if <tt>b.equals(a)</tt>). The contracts for <tt>List.equals</tt> and
	 * <tt>Set.equals</tt> state that lists are only equal to other lists, and sets to other sets. Thus, a custom
	 * <tt>equals</tt> method for a collection class that implements neither the <tt>List</tt> nor <tt>Set</tt>
	 * interface must return <tt>false</tt> when this collection is compared to any list or set. (By the same logic, it
	 * is not possible to write a class that correctly implements both the <tt>Set</tt> and <tt>List</tt> interfaces.)
	 *
	 * @param list Object to be compared for equality with this collection.
	 * @return <tt>true</tt> if the specified object is equal to this collection
	 * @throws NullPointerException if a link in either linked list is broken.
	 * @throws NullPointerException if the specified element is <tt>null</tt>.
	 * @see Object#equals(Object)
	 * @see Set#equals(Object)
	 * @see List#equals(Object)
	 */
	public boolean equals(IndexedLinkedList list) {
		// Check is the lengths are equal.
		if (elementCount != list.elementCount) {
			return false;
		}

		// Check if all elements are equal, and the order of the elements in the
		// lists are equal.
		IndexedLinkedListElement element1 = getHead();
		IndexedLinkedListElement element2 = list.getHead();
		for (int i = 0; i < elementCount; i++) {
			if (!element1.equals(element2)) {
				return false;
			}
			element1 = element1.next;
			element2 = element2.next;
		}

		return true;
	}

	// ///////////////////////////////////////////////////////// //

	/**
	 * Get the first element.
	 */
	public IndexedLinkedListElement getHead() {
		for (char c = 0; c < 0x80; c++) {
			if (table[c] != null) {
				return table[c];
			}
		}
		return null;
	}

	/**
	 * Adds an element after before a given element. The given element must have been retrieved using the findAfter
	 * method and the new element must not be part of this collection. WARNING: THIS METHOD IS UNSAFE AS IT CAN DAMAGE
	 * THE INTEGRITY OF THE LINKED LIST. See <tt>findAfter</tt> for more info on how to use it correctly.
	 *
	 * @return <tt>true</tt> if this collection changed as a result of the call
	 * @throws NullPointerException if a link in the linked list is broken.
	 * @throws NullPointerException if the specified collection is <tt>null</tt>.
	 * @see #findAfter(java.lang.String)
	 */
	public boolean addBefore(IndexedLinkedListElement element, IndexedLinkedListElement newElement) {
		IndexedLinkedListElement previous = element.previous;

		// Get the previous token and link it to the new token and link the
		// new token to the next token.
		previous.next = element.previous = newElement;
		newElement.previous = previous;
		newElement.next = element;

		// Update the index, when necessary.
		char index = newElement.getIdentifier().charAt(0);
		if (table[index] == null || newElement.compareTo(table[index]) < 0) {
			table[index] = newElement;
		}

		elementCount++;
		return true;
	}

	/**
	 * Returns the first element in this list that has a name alphabetically equal or larger than the given name. If
	 * none exists, the first element of the list is returned. If the list is empty, a <tt>null</tt> is returned. More
	 * formally, if {@code e} is a newly created element and {@code name = e.getIdentifier()} and <code>name =
	 * list.findAfter()</code>, then after this piece of user code:
	 *
	 * <pre>
	 * if (next == null)
	 * 	list.add(e);
	 * else if (name.equals(next.getIdentifier()))
	 * 	e = next;
	 * else
	 * 	list.addBefore(next, e);
	 *
	 * </pre>
	 * <p>
	 * the list will still be alphabetically sorted, without double elements, and e is conditionally replaced by next,
	 * guaranteeing that e is in the list.
	 *
	 * @param name the name string to compare to the elements' names.
	 * @return the first element in this list that has a name alphabetically larger the given name or the first element
	 * of the list if there is no such element, or <tt>null</tt> if the list is empty
	 * @throws NullPointerException if a link in the linked list is broken.
	 * @throws NullPointerException if the specified collection is <tt>null</tt>.
	 */
	public IndexedLinkedListElement findAfter(String name) {
		if (elementCount == 0) {
			return null;
		}

		char                     index   = name.charAt(0);
		IndexedLinkedListElement current = table[index];

		// Check if a token beginning with this char does not exist.
		if (current == null) {
			// Just find the next token.
			char i = index;
			do {
				i++;
				i &= 0x7F;
			} while (table[i] == null);

			return table[i];
		}

		IndexedLinkedListElement first = current;
		// Find the first token that is alphabetically not before the name, or
		// the first token when the end of the list has been reached.
		while (current.getIdentifier().compareTo(name) < 0) {
			current = current.next;
			// Check if it made a loop, meaning there are no tokens.
			// alphabetically following the name.
			if (current == first) {
				// The first token will be the one alphabetically following the
				// given name.
				return getHead();
			}
		}
		return current;
	}

	public IndexedLinkedListElement find(String name) {
		if (elementCount == 0) {
			return null;
		}

		char                     index   = name.charAt(0);
		IndexedLinkedListElement current = table[index];

		// Check if a token exists beginning with this char.
		if (current == null) {
			return null;
		}

		// Find the the first token starting with this char which is not
		// alphabetically before the new token.
		IndexedLinkedListElement first       = current;
		String                   currentName = current.getIdentifier();
		while (name.compareTo(currentName) > 0) {
			current = current.next;
			currentName = current.getIdentifier();

			// Check if we wrapped around the beginning of the list. Rare case
			// scenario so don't bother about speed.
			if (current == first) {
				return null;
			}

			// Check if this token still starts with the same character.
			if (currentName.charAt(0) != index) {
				return null;
			}
		}

		// Check if this token is indeed equal the supplied string.
		if (currentName.equals(name)) {
			return current;
		}

		return null;
	}

	/**
	 * Find the element which begins with the supplied name. If no such element exists or if more than one element
	 * exists, null is returned.
	 *
	 * @param name the first few characters of the name of the element to search
	 * @return the single element which starts with the name, or null when not exactly one such element exists
	 */
	public IndexedLinkedListElement findStartingWith(String name) {
		if (elementCount == 0) {
			return null;
		}

		char                     index;
		IndexedLinkedListElement first;
		IndexedLinkedListElement current;
		first = current = table[index = name.charAt(0)];

		// Check if a token exists beginning with this char.
		if (current == null) {
			return null;
		}

		// Find the first token that is alphabetically not before the name, or
		// the first token when the end of the list has been reached.
		String currentName = current.getIdentifier();
		while (currentName.compareTo(name) < 0) {
			current = current.next;

			// Check if we wrapped around the beginning of the list.
			if (current == first) {
				return null;
			}

			currentName = current.getIdentifier();

			// Check if this token still starts with the same character.
			if (currentName.charAt(0) != index) {
				return null;
			}
		}

		if (currentName.startsWith(name)) {
			return current;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuffer out = new StringBuffer(1000);
		out.append(super.getClass().getSimpleName() + "[");
		for (IndexedLinkedListElement e : this) {
			out.append(e.toString());
			out.append(',');
			out.append(' ');
		}
		out.deleteCharAt(out.length() - 1);
		out.deleteCharAt(out.length() - 1);
		out.append(']');
		return out.toString();
	}
}
