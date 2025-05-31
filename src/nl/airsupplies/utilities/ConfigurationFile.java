package nl.airsupplies.utilities;

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
