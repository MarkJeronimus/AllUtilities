package nl.airsupplies.utilities.io;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * @author Mark Jeronimus
 */
// Created 2024-09-25
public class TextFilePointerReader {
	private final List<String> lines;
	private       int          pointer = 0;

	public TextFilePointerReader(List<String> lines) {
		this.lines = requireNonNull(lines, "lines");
	}

	public int getLineNumber() {
		return pointer + 1;
	}

	public @Nullable String read() {
		if (pointer >= lines.size()) {
			return null;
		}

		return lines.get(pointer++);
	}

	public void stepBack() {
		if (pointer > 0) {
			pointer--;
		}
	}
}
