package nl.airsupplies.utilities;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.ATOMIC_MOVE;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import net.jcip.annotations.ThreadSafe;

import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * Contains persistable key/value pairs, as well as run-time configurable default key/value pairs which are used in case
 * keys are not present in (or deleted from) the 'active' configuration.
 *
 * @author Mark Jeronimus
 */
// Created 2017-02-17
@ThreadSafe
public class ConfigurationFile extends Configuration {
	private final Path file;

	public ConfigurationFile(Path file) {
		this.file = requireNonNull(file, "file");
	}

	public Path getFile() {
		return file;
	}

	public final synchronized void load() throws IOException {
		try (Reader in = Files.newBufferedReader(file)) {
			properties.load(in);
		} catch (NoSuchFileException ignored) {
		}
	}

	public final synchronized void save() throws IOException {
		Path tempFile = Paths.get(file + ".tmp");

		try (Writer out = Files.newBufferedWriter(tempFile)) {
			properties.store(out, null);
		}

		try {
			Files.move(tempFile, file, REPLACE_EXISTING, ATOMIC_MOVE);
		} catch (AtomicMoveNotSupportedException ignored) {
			moveNonAtomic(tempFile, file);
		}
	}

	/** Three-way non-atomic move with overwrite: Back-up original, rename new, delete original. */
	private static void moveNonAtomic(Path tempFile, Path file) throws IOException {
		Path backupFile = Files.exists(file) ?
		                  Paths.get(file + ".bak") :
		                  null;

		if (backupFile != null) {
			Files.move(file, backupFile, REPLACE_EXISTING);
		}

		Files.move(tempFile, file);

		if (backupFile != null) {
			Files.delete(backupFile);
		}
	}
}
