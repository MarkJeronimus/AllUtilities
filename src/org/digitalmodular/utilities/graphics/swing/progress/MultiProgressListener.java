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

package org.digitalmodular.utilities.graphics.swing.progress;

/**
 * @author Mark Jeronimus
 */
// Created 2019-08-08 Copied from ProgressListener
public interface MultiProgressListener {
	int getNumProgressBars();

	void progressUpdated(int progressBarIndex, ProgressEvent evt);

	default ProgressListener makeListenerForProgressBar(int index) {
		return evt -> progressUpdated(index, evt);
	}
}
