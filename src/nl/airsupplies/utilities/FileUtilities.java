package nl.airsupplies.utilities;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.stream.Stream;
import static java.nio.channels.FileChannel.MapMode;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.StandardCopyOption.ATOMIC_MOVE;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.util.Objects.requireNonNull;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jetbrains.annotations.Nullable;

import nl.airsupplies.utilities.annotation.UtilityClass;
import nl.airsupplies.utilities.function.IOOperation;
import static nl.airsupplies.utilities.validator.ArrayValidatorUtilities.requireArrayLengthAtLeast;
import static nl.airsupplies.utilities.validator.ArrayValidatorUtilities.requireArrayValuesNonNull;
import static nl.airsupplies.utilities.validator.StringValidatorUtilities.requireStringNotEmpty;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireThat;

/**
 * @author Mark Jeronimus
 */
// Created 2006-09-02
@UtilityClass
public final class FileUtilities {
	public static final int MAX_BUFFER_SIZE = 65536;

	public static void makeFileEmpty(String filename) throws IOException {
		new File(filename).createNewFile();
	}

	public static boolean allFilesExist(File[] files) {
		for (File file : files) {
			if (!file.exists()) {
				return false;
			}
		}
		return true;
	}

	public static boolean someFilesExist(File[] files) {
		for (File file : files) {
			if (file.exists()) {
				return true;
			}
		}
		return false;
	}

	public static int getIntFileSize(File file) {
		long length = file.length();
		if (length > 0x7FFFFFFFL) {
			return -1;
		}
		return (int)length;
	}

	@Deprecated
	public static void renameMoveFile(File src, File dst) throws IOException {
		// Already moved means success.
		if (src.equals(dst)) {
			return;
		}

		if (!src.canRead()) {
			throw new IOException("Move: Source file not readable.\nsrc=" + src.getPath() + "\ndst="
			                      + dst.getPath());
		}

		if (!src.canWrite()) {
			throw new IOException("Move: Source file read-only.\nsrc=" + src.getPath() + "\ndst="
			                      + dst.getPath());
		}

		if (dst.isDirectory()) {
			dst = new File(dst, src.getName());
		}

		if (dst.exists()) {
			throw new IOException("Move: Destination file already exists.\nsrc=" + src.getPath() + "\ndst="
			                      + dst.getPath());
		}

		if (!src.renameTo(dst)) {
			throw new IOException(
					"Move: Source or destination file might be in use by another application.\nsrc="
					+ src.getPath()
					+ "\ndst=" + dst.getPath());
		}
	}

	public static void renameMoveFile(Path src, Path dst) throws IOException {
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

	public static boolean mkdir(Component owner, File file) {
		if (file.isDirectory()) {
			return true;
		}

		while (file.isFile()) {
			if (JOptionPane.showConfirmDialog(owner, new String[]{"Error creating directory.",
			                                                      "Directory name points to an existing file.",
			                                                      "File: " + file.getPath(), "Retry?"},
			                                  owner.getName() != null ? owner.getName() : "Filesystem",
			                                  JOptionPane.YES_NO_OPTION,
			                                  JOptionPane.ERROR_MESSAGE) != JOptionPane.YES_OPTION) {
				return false;
			}
		}

		while (!file.mkdir()) {
			if (JOptionPane.showConfirmDialog(owner, new String[]{"Error creating directory.", "Unknown error.",
			                                                      "Directory: " + file.getPath(), "Retry?"},
			                                  owner.getName() != null ? owner.getName() : "Filesystem",
			                                  JOptionPane.YES_NO_OPTION,
			                                  JOptionPane.ERROR_MESSAGE) != JOptionPane.YES_OPTION) {
				return false;
			}
		}

		return true;
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

			// Possibly better method: test! (from BareBonesBrowserLaunch.java)
			// Class<?> fileMgr = Class.forName("com.apple.eio.FileManager");
			// Method openURL = fileMgr.getDeclaredMethod("openURL", new
			// Class[]{String.class});
			// openURL.invoke(null, new Object[]{url});
		}
	}

	/**
	 * @return the chosen file, or {@code null} if the user canceled.
	 */
	public static @Nullable File askDirectory(Component parent, @Nullable File startFolder) {
		requireNonNull(parent, "parent");

		JFileChooser chooser = new JFileChooser(startFolder == null ?
		                                        null :
		                                        startFolder.isDirectory() ?
		                                        startFolder :
		                                        startFolder.getParentFile());

		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setMultiSelectionEnabled(false);

		while (true) {
			int returnVal = chooser.showOpenDialog(parent);
			if (returnVal != JFileChooser.APPROVE_OPTION) {
				return null;
			}

			File file = chooser.getSelectedFile();
			if (file.exists()) {
				return file;
			}
		}
	}

	/**
	 * @return the chosen file, or {@code null} if the user canceled.
	 */
	public static @Nullable File askFileForLoading(Component parent,
	                                               @Nullable File startFolderOrFile,
	                                               String fileTypeDescription,
	                                               String... fileTypeExtensions) {
		if (!(startFolderOrFile == null || startFolderOrFile.exists())) {
			startFolderOrFile = new File(".");
		}

		JFileChooser chooser = prepareFileChooser(startFolderOrFile, fileTypeDescription, fileTypeExtensions);

		while (true) {
			int returnVal = chooser.showOpenDialog(parent);
			if (returnVal != JFileChooser.APPROVE_OPTION) {
				return null;
			}

			File file = chooser.getSelectedFile();
			if (file.exists()) {
				return file;
			}
		}
	}

	/**
	 * @return the chosen file, or {@code null} if the user canceled.
	 */
	public static List<File> askFilesForLoading(@Nullable Component parent,
	                                            @Nullable File startFolderOrFile,
	                                            String fileTypeDescription,
	                                            String... fileTypeExtensions) {
		if (!(startFolderOrFile == null || startFolderOrFile.exists())) {
			startFolderOrFile = new File(".");
		}

		JFileChooser chooser = prepareFileChooser(startFolderOrFile, fileTypeDescription, fileTypeExtensions);
		chooser.setMultiSelectionEnabled(true);

		while (true) {
			int returnVal = chooser.showOpenDialog(parent);
			if (returnVal != JFileChooser.APPROVE_OPTION) {
				return Collections.emptyList();
			}

			List<File> files = Arrays.asList(chooser.getSelectedFiles());
			if (files.stream().filter(file -> !file.exists()).findFirst().isEmpty()) {
				return files;
			}
		}
	}

	/**
	 * @return the specified file, or {@code null} if the user canceled.
	 */
	public static @Nullable File askFileForSaving(Component parent,
	                                              @Nullable File startFolderOrFile,
	                                              String fileTypeDescription,
	                                              String... fileTypeExtensions) {
		JFileChooser chooser = prepareFileChooser(startFolderOrFile, fileTypeDescription, fileTypeExtensions);

		while (true) {
			int returnVal = chooser.showSaveDialog(parent);
			if (returnVal != JFileChooser.APPROVE_OPTION) {
				return null;
			}

			File file = chooser.getSelectedFile();
			if (file.isDirectory()) {
				continue;
			}

			file = addExtensionIfMissing(file, fileTypeExtensions[0]);

			if (file.exists()) {
				int choice = JOptionPane.showConfirmDialog(parent, "Overwrite " + file.getName() + " ?");
				if (choice == JOptionPane.CANCEL_OPTION) {
					return null;
				} else if (choice != JOptionPane.YES_OPTION) {
					continue;
				}
			}

			return file;
		}
	}

	private static JFileChooser prepareFileChooser(
			@Nullable File startFolderOrFile, String fileTypeDescription, String[] fileTypeExtensions) {
		requireNonNull(fileTypeDescription, "fileTypeDescription");
		requireArrayLengthAtLeast(1, fileTypeExtensions, "fileTypeExtensions");
		requireArrayValuesNonNull(fileTypeExtensions, "fileTypeExtensions");

		for (int i = 0; i < fileTypeExtensions.length; i++) {
			int index = i;
			requireThat(!fileTypeExtensions[i].isEmpty(), () -> "fileTypeExtensions[" + index + "] is empty.");
			if (fileTypeExtensions[i].charAt(0) == '.') {
				fileTypeExtensions[i] = fileTypeExtensions[i].substring(1);
			}
		}

		JFileChooser chooser = new JFileChooser(startFolderOrFile == null ?
		                                        null :
		                                        startFolderOrFile.isDirectory() ?
		                                        startFolderOrFile :
		                                        startFolderOrFile.getParentFile());

		if (startFolderOrFile != null && !startFolderOrFile.isDirectory()) {
			chooser.setSelectedFile(startFolderOrFile);
		}

		chooser.setAcceptAllFileFilterUsed(false);
		String     description = makeFileTypeDescription(fileTypeDescription, fileTypeExtensions);
		FileFilter fileFilter  = new FileNameExtensionFilter(description, fileTypeExtensions);
		chooser.addChoosableFileFilter(fileFilter);
		chooser.setAcceptAllFileFilterUsed(true);
		chooser.setFileFilter(fileFilter);

		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setMultiSelectionEnabled(false);
		return chooser;
	}

	private static String makeFileTypeDescription(String fileTypeDescription, String[] fileTypeExtensions) {
		StringBuilder sb = new StringBuilder(fileTypeDescription.length() + fileTypeExtensions.length * 7);
		sb.append(fileTypeDescription).append(" (");
		for (int i = 0; i < fileTypeExtensions.length; i++) {
			sb.append('.').append(fileTypeExtensions[i]);
			if (i < fileTypeExtensions.length - 1) {
				sb.append(", ");
			} else {
				sb.append(')');
			}
		}
		return sb.toString();
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

	public static ByteBuffer readToBuffer(File regionFile) throws IOException {
		try (RandomAccessFile handle = new RandomAccessFile(regionFile, "rw")) {
			try (FileChannel channel = handle.getChannel()) {
				MappedByteBuffer buffer = channel.map(MapMode.READ_ONLY, 0, regionFile.length());
				buffer.load();
				return buffer;
			}
		}
	}

	public static void safeOverwrite(Path file, IOOperation<Path> saveOperation) throws IOException {
		safeOverwrite(file, saveOperation, false);
	}

	public static void safeOverwrite(Path file, IOOperation<Path> saveOperation, boolean leaveBakFile)
			throws IOException {
		if (!Files.exists(file)) {
			saveOperation.operateOn(file);
			return;
		}

		Path tmpFile = file.resolveSibling(file.getFileName() + ".tmp");
		Path bakFile = file.resolveSibling(file.getFileName() + ".bak");

		saveOperation.operateOn(tmpFile);

		Files.move(file, bakFile, REPLACE_EXISTING, ATOMIC_MOVE);
		Files.move(tmpFile, file, REPLACE_EXISTING, ATOMIC_MOVE);

		if (!leaveBakFile) {
			Files.delete(bakFile);
		}
	}

	public static boolean isEmpty(Path path) throws IOException {
		if (Files.isDirectory(path)) {
			try (Stream<Path> entries = Files.list(path)) {
				return entries.findFirst().isEmpty();
			}
		}

		return false;
	}

	public static void deleteTree(Path dir) throws IOException {
		if (!Files.exists(dir)) {
			return;
		}

		Files.walkFileTree(dir, DELETE_TREE_VISITOR);
	}

	private static final FileVisitor<Path> DELETE_TREE_VISITOR = new SimpleFileVisitor<>() {
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
	};

	public static boolean isNotOlderThan(Path file, Duration maximumAge) {
		try {
			Instant  lastModifiedTime = Files.getLastModifiedTime(file).toInstant();
			Duration age              = Duration.between(lastModifiedTime, LocalDateTime.now(ZoneOffset.UTC));
			return age.compareTo(maximumAge) <= 0;
		} catch (IOException ignored) {
			return false;
		}
	}

	public static String readString(Path file) throws IOException {
		return new String(Files.readAllBytes(file), UTF_8);
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

	public static long getCreationTime(Path file) throws IOException {
		BasicFileAttributes attrs = Files.readAttributes(
				file, BasicFileAttributes.class, LinkOption.NOFOLLOW_LINKS);
		long downloadTimestamp = attrs.creationTime().to(TimeUnit.SECONDS);
		return downloadTimestamp;
	}
}
