package nl.airsupplies.utilities;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.CopyOption;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import static java.nio.channels.FileChannel.MapMode;
import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.SKIP_SUBTREE;
import static java.nio.file.StandardCopyOption.ATOMIC_MOVE;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import org.jetbrains.annotations.Nullable;

import nl.airsupplies.utilities.annotation.UtilityClass;
import nl.airsupplies.utilities.function.IOConsumer;
import nl.airsupplies.utilities.io.InputStreamWithLength;
import static nl.airsupplies.utilities.validator.StringValidatorUtilities.requireStringNotEmpty;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;

/**
 * @author Mark Jeronimus
 */
// Created 2006-09-02
@UtilityClass
public final class FileUtilities {
	private static final char[]  ILLEGAL_FILE_NAME_CHARS = {'"', '*', '/', ':', '<', '>', '?', '\\', '|'};
	private static final Pattern COLON_MATCHER_1         = Pattern.compile("([0-9]):([0-9])");
	private static final Pattern COLON_MATCHER_2         = Pattern.compile(" : ");
	private static final Pattern COLON_MATCHER_3         = Pattern.compile(": ");
	private static final Pattern COLON_MATCHER_4         = Pattern.compile(" :");

	/**
	 * Returns the path, filename, and extension of a file.
	 * <p>
	 * If the path represents a directory and doesn't end in a path separator,
	 * that directory becomes the 'file' (as it can't unambiguously be distinguished from an actual file).
	 *
	 * @throws IllegalArgumentException when the path ends in a path separator
	 */
	public static String[] splitDirFileExt(String path) {
		requireNonNull(path, "path");

		int i = Math.max(path.lastIndexOf('/'), path.lastIndexOf('\\'));

		if (i == path.length()) {
			throw new IllegalArgumentException("'path' doesn't end with a filename:" + path);
		}

		String dir      = i < 0 ? "" : path.substring(0, i + 1);
		String filename = i < 0 ? path : path.substring(i + 1);

		i = filename.lastIndexOf('.');
		String file = i <= 0 ? filename : filename.substring(0, i);
		String ext  = i <= 0 ? "" : filename.substring(i + 1);

		return new String[]{dir, file, ext};
	}

	public static File addExtensionIfMissing(File path, String extension) {
		requireNonNull(path, "path");
		requireStringNotEmpty(extension, "extension");

		String pathString = path.getName();

		if (!splitDirFileExt(pathString)[2].isEmpty()) {
			return path;
		}

		if (extension.charAt(0) != '.') {
			return new File(path.getParent(), pathString + '.' + extension);
		} else {
			return new File(path.getParent(), pathString + extension);
		}
	}

	/**
	 * Checks if a path represented by a String is a valid file, directory or link. This differs from
	 * {@link Files#exists(Path, LinkOption...)} in that the prerequisite call to {@link Paths#get(String, String...)}
	 * may throw if the String doesn't represent a valid path, and this function just returns {@code false}.
	 */
	public static boolean fileExists(String path) {
		try {
			return Files.exists(Paths.get(path));
		} catch (InvalidPathException ignored) {
			return false;
		}
	}

	public static boolean isDirectoryEmpty(Path path) throws IOException {
		if (Files.isDirectory(path)) {
			try (Stream<Path> entries = Files.list(path)) {
				return !entries.findFirst().isPresent();
			}
		}

		return false;
	}

	public static int countActualFiles(File[] files) {
		int numFiles = 0;

		for (File file : files) {
			if (file.isFile()) {
				numFiles++;
			}
		}

		return numFiles;
	}

	/**
	 * Sums the size of all files in the directory. Directories and symbolic links are counted as 0
	 * bytes. This is inevitable since this implementation relies on {@link Files#size(Path)}, which states that it's
	 * operation is defined only for regular files. Files or directories which cannot be read are silently ignored.
	 */
	public static long sizeOfFiles(Path path, boolean recursively) throws IOException {
		AtomicLong size = new AtomicLong(0);

		Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
				return recursively || dir.equals(path) ? CONTINUE : SKIP_SUBTREE;
			}

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				if (Files.isRegularFile(file)) {
					size.addAndGet(Files.size(file));
				}

				return CONTINUE;
			}
		});

		return size.get();
	}

	public static long getCreationTime(Path file) throws IOException {
		BasicFileAttributes attrs = Files.readAttributes(
				file, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
		long downloadTimestamp = attrs.creationTime().to(TimeUnit.SECONDS);
		return downloadTimestamp;
	}

	public static boolean isNotOlderThan(Path file, Duration maximumAge) {
		try {
			Instant  lastModifiedTime = Files.getLastModifiedTime(file).toInstant();
			Duration age              = Duration.between(lastModifiedTime, LocalDateTime.now(ZoneOffset.UTC));
			return age.compareTo(maximumAge) <= 0;
		} catch (IOException ignored) {
			return false;
		}
	}

	/**
	 * Lists all files in a directory which match a given {@link Predicate}.
	 * <p>
	 * Files or directories which cannot be read are silently ignored.
	 * Symlinks are treated like files even if they point to directories.
	 */
	public static List<Path> findFiles(Path path, Predicate<Path> fileFilter, boolean recursive) throws IOException {
		List<Path> found = new ArrayList<>(256);

		Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
				return recursive || dir.equals(path) ? CONTINUE : SKIP_SUBTREE;
			}

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
				if (Files.isRegularFile(file) && fileFilter.test(file)) {
					found.add(file);
				}

				return CONTINUE;
			}
		});

		return found;
	}

	/**
	 * Copy a file, or all files in a directory, to a new location.
	 * <p>
	 * This works similar to {@code cp} on Linux or {@code copy} on Windows.
	 */
	public static void copy(Path src, Path dst, CopyOption... copyOptions) throws IOException {
		try {
			if (Files.isRegularFile(src)) {
				if (Files.isDirectory(dst)) {
					Files.copy(src, dst.resolve(src.getFileName()), copyOptions);
				} else {
					Files.copy(src, dst, copyOptions);
				}
			} else if (Files.isSymbolicLink(src)) {
				Path targetAbs    = src.toRealPath();
				Path targetParent = src.toAbsolutePath().getParent();
				assert targetParent != null; // absolute path of symlink path always has a parent
				Path targetRel = targetParent.relativize(targetAbs);
				Files.createSymbolicLink(dst, targetRel);
			} else {
				copyDirectory(src, dst, copyOptions);
			}
		} catch (FileAlreadyExistsException ex) {
			throw ex;
		} catch (IOException ex) {
			throw new IOException(src + " -> " + dst, ex);
		}
	}

	/**
	 * Copy all files from one directory to another, recursively. Files or directories which cannot be read are
	 * silently ignored.
	 */
	public static void copyDirectory(Path src, Path dst, CopyOption... copyOptions) throws IOException {
		Files.walkFileTree(src, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				Files.createDirectories(dst.resolve(src.relativize(dir)));
				return CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				copy(file, dst.resolve(src.relativize(file)));
				return CONTINUE;
			}
		});
	}

	public static void makeNumberedCopy(Path file, BiFunction<Path, Path, @Nullable IOException> copyFunction)
			throws IOException {
		int i = 1;
		while (true) {
			Path numberedFile = file.getParent().resolve(file.getFileName() + "." + i);
			if (!Files.exists(numberedFile)) {
				@Nullable IOException ex = copyFunction.apply(file, numberedFile);
				if (ex != null) {
					throw ex;
				}

				return;
			}
			i++;
		}
	}

	public static void moveAtomically(Path src, Path dst) throws IOException {
		Files.move(src, dst, REPLACE_EXISTING, ATOMIC_MOVE);
	}

	public static void renameMove(Path src, Path dst) throws IOException {
		requireNonNull(src, "src");
		requireNonNull(dst, "dst");

		// Already moved means success.
		if (src.equals(dst)) {
			return;
		}

		if (!Files.exists(src)) {
			throw new IOException("Move: Source file does not exist: src=" + src + ", dst=" + dst);
		}
		if (!Files.isReadable(src)) {
			throw new IOException("Move: Source file is not readable: src=" + src + ", dst=" + dst);
		}
		if (!Files.isWritable(src)) {
			throw new IOException("Move: Source file is read-only: src=" + src + ", dst=" + dst);
		}

		if (Files.isDirectory(dst)) {
			dst = dst.resolve(src.getFileName());
		}

		if (Files.exists(dst)) {
			throw new IOException("Move: Destination file already exist: src=" + src + ", dst=" + dst);
		}

		try {
			Files.move(src, dst, ATOMIC_MOVE);
		} catch (AtomicMoveNotSupportedException ignored) {
			Files.move(src, dst);
		}
	}

	public static void safeOverwrite(Path file, IOConsumer<Path> saveOperation) throws IOException {
		safeOverwrite(file, saveOperation, false);
	}

	public static void safeOverwrite(Path file, IOConsumer<Path> saveOperation, boolean leaveBakFile)
			throws IOException {
		if (!Files.exists(file)) {
			saveOperation.consume(file);
			return;
		}

		Path tmpFile = file.resolveSibling(file.getFileName() + ".tmp");
		Path bakFile = file.resolveSibling(file.getFileName() + ".bak");

		saveOperation.consume(tmpFile);

		moveAtomically(file, bakFile);
		moveAtomically(tmpFile, file);

		if (!leaveBakFile) {
			Files.delete(bakFile);
		}
	}

	/**
	 * Delete files in a directory tree, but not the tree itself.
	 * <p>
	 * If {@code recursive == false}, only files in the specified directory are deleted.
	 * Symlinks are treated like files even if they point to directories.
	 *
	 * @see #deleteAll(Path)
	 */
	public static void deleteFiles(Path path, boolean recursive) throws IOException {
		if (!Files.isDirectory(path)) {
			return;
		}

		IOException[] exception = {null};

		Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
				return recursive || dir.equals(path) ? CONTINUE : SKIP_SUBTREE;
			}

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				Files.delete(file);
				return CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				if (!dir.equals(path)) {
					Files.delete(dir);
				}

				return CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc) {
				if (exception[0] == null) {
					exception[0] = exc;
				}

				return CONTINUE;
			}
		});

		if (exception[0] != null) {
			throw exception[0];
		}
	}

	/**
	 * Delete a file or an entire directory tree (including itself).
	 *
	 * @see #deleteFiles(Path, boolean)
	 */
	public static void deleteAll(Path path) throws IOException {
		if (!Files.exists(path)) {
			return;
		}

		if (!Files.isDirectory(path)) {
			Files.delete(path);
			return;
		}

		IOException[] exception = {null};

		Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				Files.delete(file);
				return CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				Files.delete(dir);
				return CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc) {
				if (exception[0] == null) {
					exception[0] = exc;
				}

				return CONTINUE;
			}
		});

		if (exception[0] != null) {
			throw exception[0];
		}
	}

	public static ByteBuffer readToBuffer(File regionFile) throws IOException {
		try (RandomAccessFile handle = new RandomAccessFile(regionFile, "rw")) {
			try (FileChannel channel = handle.getChannel()) {
				MappedByteBuffer buffer = channel.map(MapMode.READ_ONLY, 0, regionFile.length());
				buffer.load();
				return buffer;
			}
		}
	}

	public static void streamToFile(InputStream stream, Path file) throws IOException, InterruptedException {
		int bufferSize = stream instanceof InputStreamWithLength ?
		                 ((InputStreamWithLength)stream).getLength() :
		                 65536;

		bufferSize = NumberUtilities.clamp(bufferSize, 0, 65536);

		byte[] buffer = new byte[bufferSize];

		try (OutputStream fileStream = Files.newOutputStream(file)) {
			while (true) {
				int len = stream.read(buffer);
				if (len == -1) {
					break;
				}

				fileStream.write(buffer, 0, len);

				if (Thread.currentThread().isInterrupted()) {
					throw new InterruptedException("streamToFile canceled");
				}
			}
		}
	}

	/**
	 * Equivalent to {@code InputStream.transferTo()} but with a larger buffer and cancellation support.
	 */
	@SuppressWarnings("UnusedReturnValue")
	public static void streamToStream(InputStream stream, OutputStream out) throws IOException, InterruptedException {
		requireNonNull(stream, "stream");
		requireNonNull(out, "out");

		int bufferSize = stream instanceof InputStreamWithLength ?
		                 ((InputStreamWithLength)stream).getLength() :
		                 65536;

		bufferSize = NumberUtilities.clamp(bufferSize, 0, 65536);

		byte[] buffer = new byte[bufferSize];

		while (true) {
			int len = stream.read(buffer, 0, bufferSize);
			if (len < 0) {
				break;
			}

			out.write(buffer, 0, len);

			if (Thread.currentThread().isInterrupted()) {
				throw new InterruptedException("canceled");
			}
		}
	}

	/**
	 * Replaces all illegal file name characters in a String with the specified character.
	 * <p>
	 * The filename should not contain a path, as path separators are also regarded as illegal file name characters.
	 * <p>
	 * "Illegal filename characters" are based on the Microsoft specification, and are: {@code / \ ? * : | " < >}.
	 * Additionally, trailing periods are not allowed in Windows, so these can be removed.
	 *
	 * @param c the character to substitute the illegal characters with.
	 */
	public static String replaceIllegalFilenameChars(CharSequence name, boolean replaceTrailingPeriods, char c) {
		return replaceIllegalFilenameChars(name, replaceTrailingPeriods, ignored -> c);
	}

	/**
	 * Replaces all illegal file name characters in a String using the specified replacer.
	 * <p>
	 * The filename should not contain a path, as path separators are also regarded as illegal file name characters.
	 * <p>
	 * "Illegal filename characters" are based on the Microsoft specification, and are: {@code / \ ? * : | " < >}.
	 * Additionally, trailing periods are not allowed in Windows, so these can be removed.
	 *
	 * @param replacer the function that maps any of the above mentioned 9 characters to another (sequence of)
	 *                 characters.
	 *                 There's no explicit check whether the replacement character is in fact illegal or not.
	 */
	public static String replaceIllegalFilenameChars(CharSequence name,
	                                                 boolean replaceTrailingPeriods,
	                                                 Function<Character, Character> replacer) {
		requireNonNull(name, "name");

		StringBuilder sb = new StringBuilder(name.length());

		// Replace characters
		name.codePoints()
		    .forEach(c -> {
			    int i = Arrays.binarySearch(ILLEGAL_FILE_NAME_CHARS, (char)c);
			    if (i < 0) {
				    sb.append((char)c);
			    } else {
				    sb.append(replacer.apply((char)c));
			    }
		    });

		if (replaceTrailingPeriods) {
			while (sb.length() > 0 && sb.charAt(sb.length() - 1) == '.') {
				sb.setLength(sb.length() - 1);
			}
		}

		return sb.toString();
	}

	/**
	 * Specialized instance of {#link {@link #replaceIllegalFilenameChars(CharSequence, boolean, Function)}} tailored
	 * for filenames containing titles of works (books, movies, etc.), which often includes colons.
	 */
	public static String replaceIllegalTitleFilenameChars(String name) {
		name = COLON_MATCHER_1.matcher(name).replaceAll("$1.$2");
		name = COLON_MATCHER_2.matcher(name).replaceAll(" - ");
		name = COLON_MATCHER_3.matcher(name).replaceAll(" - ");
		name = COLON_MATCHER_4.matcher(name).replaceAll(" - ");
		name = StringUtilities.collateASCII(name);

		int[] codePoints = name.codePoints().map(c -> c <= 32 || c >= 127 ? '_' : c).toArray();
		name = new String(codePoints, 0, codePoints.length);

		return replaceIllegalFilenameChars(name, true, '_');
	}

	public static void openURL(String url) throws IOException {
		String os = System.getProperty("os.name").toLowerCase();

		if (os.contains("win")) {
			Runtime.getRuntime().exec(new String[]{"rundll32", "url.dll,FileProtocolHandler", url});
		} else if (os.contains("nix") || os.contains("nux")) {
			Runtime.getRuntime().exec(new String[]{"xdg-open", url});
		} else if (os.contains("mac")) {
			// UNTESTED!
			Runtime.getRuntime().exec(new String[]{"open ", url});

			// FIXME Possibly better method: test! (from BareBonesBrowserLaunch.java)
//			Class<?> fileMgr = Class.forName("com.apple.eio.FileManager");
//			Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[]{String.class});
//			openURL.invoke(null, new Object[]{url});
		}
	}
}
