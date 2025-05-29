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

import java.util.EventObject;

import static org.digitalmodular.utilities.ValidatorUtilities.requireAtLeast;
import static org.digitalmodular.utilities.ValidatorUtilities.requireNonNull;

/**
 * @author Mark Jeronimus
 */
// Created 2015-08-15
public class ProgressEvent extends EventObject {
	private final int    progress;
	private final int    total;
	private final String text;

	public ProgressEvent(Object source, int progress, int total, String text) {
		super(source);
		this.text = requireNonNull(text, "text");

		if (total > 0) {
			requireAtLeast(0, progress, "progress");
		} else {
			requireAtLeast(-1, progress, "progress");
		}

		this.progress = progress;
		this.total    = total;

	}

	public int getProgress() {
		return progress;
	}

	public int getTotal() {
		return total;
	}

	public String getText() {
		return text;
	}

	public boolean isComplete() {
		if (Thread.currentThread().isInterrupted()) {
			return false;
		}

		if (total > 0) {
			return progress >= total;
		} else {
			return progress >= 0;
		}
	}

	@Override
	public int hashCode() {
		int hash = 0x811C9DC5;
		hash ^= Integer.hashCode(progress);
		hash *= 0x01000193;
		hash ^= Integer.hashCode(total);
		hash *= 0x01000193;
		hash ^= text.hashCode();
		hash *= 0x01000193;
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ProgressEvent other = (ProgressEvent)obj;
		return progress == other.progress && total == other.total && text.equals(other.text);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(text.isEmpty() ? 64 : 128).append("ProgressEvent(");
		sb.append("progress=").append(progress);
		sb.append(", total=").append(total);
		sb.append(", source=").append(source);

		if (!text.isEmpty()) {
			sb.append(", text=").append(text);
		}

		return sb.append(')').toString();
	}
}
