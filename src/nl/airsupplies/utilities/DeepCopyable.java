/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2024 Mark Jeronimus. All Rights Reversed.
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package nl.airsupplies.utilities;

/**
 * @author Mark Jeronimus
 */
// Created 2016-02-21
public interface DeepCopyable {

	/**
	 * Returns a deep copy of this object.
	 * <p/>
	 * The deep copy should be functionally equivalent to the original but it's state should be completely decoupled.
	 * This allows the copies to be used with parallel processing.
	 * <p/>
	 * Any non-primitive object contained in this object should either extend {@code DeepCopyable}, be final, or be
	 * constrained to the internals of the class (<i>i.e.</i> no reference to the on-final object can leak out).
	 * Getters
	 * and setters in a {@code DeepCopyable} class that return or accept a non-final object should make a defensive
	 * deep
	 * copy.
	 * <p/>
	 * It can assumed that object initialization always follows deep copying, and never the other way around. Following
	 * this assumption, pages state that will be initialized doesn't have to be copied. More concretely, only state
	 * that can be altered by setters should need to be copied, and this copying can be carried out by executing a
	 * series of {@code setXxx(xxx)} commands, which does deep copying when necessary. (Don't execute {@code
	 * setXxx(getXxx())}, which may do deep copying twice.)
	 * <p/>
	 * Any subclass of a non-abstract {@code DeepCopyable} object should override this method, and it should NOT call
	 * the overridden implementation {@code super.makeDeepCopy()}. Instead, all non-state fields of the super class
	 * that
	 * this class relies on should be copied here.
	 */
	DeepCopyable makeDeepCopy();
}
