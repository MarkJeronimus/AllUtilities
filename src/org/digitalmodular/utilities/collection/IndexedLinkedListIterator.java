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

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An iterator implementation that iterates over all the elements in an <tt>IndexedLinkedList</tt>.
 * <p>
 * It is functionally compatible with the <tt>Iterator</tt> interface, although it doesn't implement it because of
 * unckecked conversion rule conflicts. (the <tt>IndexedLinkedListIterator</tt> can only iterate over the types
 * <tt>IndexedLinkedListElement</tt>, and <tt>Iterator</tt> requires it to iterate over <tt>Object</tt>)
 *
 * @author Mark Jeronimus
 */
// Created 2006-05-18
public class IndexedLinkedListIterator implements Iterator<IndexedLinkedListElement> {

	private final IndexedLinkedList        list;
	private final IndexedLinkedListElement firstElement;
	private       IndexedLinkedListElement previousElement;
	private       IndexedLinkedListElement nextElement;
	private       int                      remaining;

	public IndexedLinkedListIterator(IndexedLinkedList list) {
		this.list = list;
		firstElement = list.getHead();

		previousElement = null;
		nextElement = firstElement;
		remaining = this.list.size();
	}

	/**
	 * Returns <tt>true</tt> if the iteration has more elements. (In other words, returns <tt>true</tt> if
	 * <tt>next</tt>
	 * would return an element rather than throwing an exception.)
	 *
	 * @return <tt>true</tt> if the iterator has more elements.
	 */
	@Override
	public boolean hasNext() {
		return remaining > 0;
	}

	/**
	 * Returns the next element in the iteration. Calling this method repeatedly until the {@link #hasNext()}method
	 * returns false will return each element in the underlying collection exactly once.
	 *
	 * @return the next element in the iteration.
	 * @throws NoSuchElementException iteration has no more elements.
	 */
	@Override
	public IndexedLinkedListElement next() {
		if (remaining == 0) {
			throw new NoSuchElementException();
		}
		previousElement = nextElement;
		nextElement = nextElement.next;
		remaining--;
		return previousElement;
	}

	/**
	 * Removes from the underlying collection the last element returned by the iterator (optional operation). This
	 * method can be called only once per call to <tt>next</tt>. The behavior of an iterator is unspecified if the
	 * underlying collection is modified while the iteration is in progress in any way other than by calling this
	 * method.
	 *
	 * @throws UnsupportedOperationException if the <tt>remove</tt> operation is not supported by this Iterator.
	 * @throws IllegalStateException         if the <tt>next</tt> method has not yet been called, or the
	 *                                       <tt>remove</tt>
	 *                                       method has already been called after the last call to the <tt>next</tt>
	 *                                       method.
	 */
	@Override
	public void remove() {
		if (previousElement == null) {
			throw new IllegalStateException();
		}
		list.remove(previousElement);
	}
}
