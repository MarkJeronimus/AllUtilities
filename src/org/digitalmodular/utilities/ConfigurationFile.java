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

package org.digitalmodular.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.ATOMIC_MOVE;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Contains persistable key/value pairs, as well as run-time configurable default key/value pairs which are used in case
 * keys are not present in (or deleted from) the 'active' configuration.
 *
 * @author Mark Jeronimus
 */
// Created 2017-02-17
public class ConfigurationFile extends Configuration {
	private final String filename;

	public ConfigurationFile(String filename) {
		this.filename = filename;
	}

	public final synchronized void load() throws IOException {
		try {
			properties.load(new FileInputStream(filename));
		} catch (FileNotFoundException ignored) {
		}
	}

	public final synchronized void save() throws IOException {
		Path tempFile = Paths.get(filename + ".tmp");
		Path file     = Paths.get(filename);

		properties.store(new FileOutputStream(tempFile.toFile()), null);

		try {
			Files.move(tempFile, file, REPLACE_EXISTING, ATOMIC_MOVE);
		} catch (AtomicMoveNotSupportedException ignored) {
			moveNonAtomic(tempFile, file);
		}
	}

	/** Three-way non-atomic move with overwrite: Back-up original, rename new, delete original. */
	private void moveNonAtomic(Path tempFile, Path file) throws IOException {
		Path backup = Files.exists(file) ?
		              Paths.get(filename + ".bak") :
		              null;

		if (backup != null) {
			Files.move(file, backup, REPLACE_EXISTING);
		}

		Files.move(tempFile, file);

		if (backup != null) {
			Files.delete(backup);
		}
	}
}
