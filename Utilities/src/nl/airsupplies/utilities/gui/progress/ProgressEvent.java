package nl.airsupplies.utilities.gui.progress;

import java.util.EventObject;

import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireAtLeast;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

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
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		} else if (!(o instanceof ProgressEvent)) {
			return false;
		}

		ProgressEvent other = (ProgressEvent)o;
		return progress == other.progress &&
		       total == other.total &&
		       text.equals(other.text);
	}

	@Override
	public int hashCode() {
		int hashCode = 0x811C9DC5;
		hashCode = 0x01000193 * (hashCode ^ progress);
		hashCode = 0x01000193 * (hashCode ^ total);
		hashCode = 0x01000193 * (hashCode ^ text.hashCode());
		return hashCode;
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
