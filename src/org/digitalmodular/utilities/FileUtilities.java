/*
 * This file is part of AllUtilities.
 *
 * Copyleft 2019 Mark Jeronimus. All Rights Reversed.
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
 * along with AllUtilities. If not, see <http://www.gnu.org/licenses/>.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.digitalmodular.utilities;

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
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import static java.nio.channels.FileChannel.MapMode;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.StandardCopyOption.ATOMIC_MOVE;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static java.util.Objects.requireNonNull;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jetbrains.annotations.Nullable;

import org.digitalmodular.utilities.function.IOOperation;
import static org.digitalmodular.utilities.ValidatorUtilities.requireArrayLengthAtLeast;
import static org.digitalmodular.utilities.ValidatorUtilities.requireNonNullElements;
import static org.digitalmodular.utilities.ValidatorUtilities.requireStringNotEmpty;
import static org.digitalmodular.utilities.ValidatorUtilities.requireThat;

/**
 * @author Mark Jeronimus
 */
// Created 2006-09-02
public enum FileUtilities {
	;

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
		if (src.equals(dst))
			return;

		if (!src.canRead())
			throw new IOException("Move: Source file not readable.\nsrc=" + src.getPath() + "\ndst="
			                      + dst.getPath());

		if (!src.canWrite())
			throw new IOException("Move: Source file read-only.\nsrc=" + src.getPath() + "\ndst="
			                      + dst.getPath());

		if (dst.isDirectory())
			dst = new File(dst, src.getName());

		if (dst.exists())
			throw new IOException("Move: Destination file already exists.\nsrc=" + src.getPath() + "\ndst="
			                      + dst.getPath());

		if (!src.renameTo(dst))
			throw new IOException(
					"Move: Source or destination file might be in use by another application.\nsrc="
					+ src.getPath()
					+ "\ndst=" + dst.getPath());
	}

	public static void renameMoveFile(Path src, Path dst) throws IOException {
		requireNonNull(src, "src");
		requireNonNull(dst, "dst");

		// Already moved means success.
		if (src.equals(dst))
			return;

		if (!Files.exists(src))
			throw new IOException("Move: Source file does not exist: src=" + src + ", dst=" + dst);
		if (!Files.isReadable(src))
			throw new IOException("Move: Source file is not readable: src=" + src + ", dst=" + dst);
		if (!Files.isWritable(src))
			throw new IOException("Move: Source file is read-only: src=" + src + ", dst=" + dst);

		if (Files.isDirectory(dst))
			dst = dst.resolve(src.getFileName());

		if (Files.exists(dst))
			throw new IOException("Move: Destination file already exist: src=" + src + ", dst=" + dst);

		try {
			Files.move(src, dst, ATOMIC_MOVE);
		} catch (AtomicMoveNotSupportedException ignored) {
			Files.move(src, dst);
		}
	}

	/**
	 * @deprecated replaced by renameMoveFile(File, File)
	 */
	@Deprecated
	public static boolean renameMoveFile(Component owner, File src, File dst) {
		if (src.equals(dst)) {
			return true;
		}

		while (!src.canRead()) {
			if (JOptionPane.showConfirmDialog(
					owner,
					new String[]{"Error moving file.",
					             "Source file not readable: " + src.getPath(),
					             "(Detination: " + dst.getPath() + ")", "Retry?"},
					owner.getName() != null ? owner.getName()
					                        : "Filesystem",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.ERROR_MESSAGE) != JOptionPane.YES_OPTION) {
				return false;
			}
		}

		while (!src.canWrite()) {
			if (JOptionPane.showConfirmDialog(
					owner,
					new String[]{"Error moving file.",
					             "Source file is read-only: " + src.getPath(),
					             "(Detination: " + dst.getPath() + ")", "Retry?"},
					owner.getName() != null ? owner.getName()
					                        : "Filesystem",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.ERROR_MESSAGE) != JOptionPane.YES_OPTION) {
				return false;
			}
		}

		while (dst.exists() && dst.isFile()) {
			int i = JOptionPane.showConfirmDialog(owner, new String[]{"Error moving file.",
			                                                          "Destination file already exists: " +
			                                                          dst.getPath(), "(Source: " + src.getPath() + ")",
			                                                          "Remove destination? (press NO to retry)"},
			                                      owner.getName() != null ? owner.getName() : "Filesystem",
			                                      JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
			if (i == JOptionPane.CANCEL_OPTION) {
				return false;
			}
			if (i == JOptionPane.YES_OPTION) {
				if (!remove(owner, dst)) {
					return false;
				}
			}
		}

		while (!src.renameTo(dst)) {
			if (JOptionPane.showConfirmDialog(owner, new String[]{"Error moving file.",
			                                                      "Source might be in use by another application.",
			                                                      "Src: " + src.getPath(),
			                                                      "Dst: " + dst.getPath(), "Retry?"},
			                                  owner.getName() != null ? owner.getName() : "Filesystem",
			                                  JOptionPane.YES_NO_OPTION,
			                                  JOptionPane.ERROR_MESSAGE) != JOptionPane.YES_OPTION) {
				return false;
			}
		}

		return true;
	}

	private static boolean remove(Component owner, File file) {
		while (!file.canRead()) {
			if (JOptionPane.showConfirmDialog(owner,
			                                  new String[]{"Error deleting file.",
			                                               "File not readable: " + file.getPath(),
			                                               "Retry?"},
			                                  owner.getName() != null ? owner.getName() : "Filesystem",
			                                  JOptionPane.YES_NO_OPTION,
			                                  JOptionPane.ERROR_MESSAGE) != JOptionPane.YES_OPTION) {
				return false;
			}
		}

		while (!file.canWrite()) {
			if (JOptionPane.showConfirmDialog(owner,
			                                  new String[]{"Error deleting file.",
			                                               "File not writable: " + file.getPath(),
			                                               "Retry?"},
			                                  owner.getName() != null ? owner.getName() : "Filesystem",
			                                  JOptionPane.YES_NO_OPTION,
			                                  JOptionPane.ERROR_MESSAGE) != JOptionPane.YES_OPTION) {
				return false;
			}
		}

		while (!file.delete()) {
			if (JOptionPane.showConfirmDialog(owner, new String[]{"Error deleting file.",
			                                                      "File might be in use by another application.",
			                                                      "File: " + file.getPath(), "Retry?"},
			                                  owner.getName() != null ? owner.getName() : "Filesystem",
			                                  JOptionPane.YES_NO_OPTION,
			                                  JOptionPane.ERROR_MESSAGE) != JOptionPane.YES_OPTION) {
				return false;
			}
		}

		return true;
	}

	public static String getExtension(String path) {
		requireNonNull(path, "path");

		int i = Math.max(path.lastIndexOf('/'), path.lastIndexOf('\\'));

		if (i == path.length())
			return "";

		String filename = i < 0 ? path : path.substring(i + 1);

		i = filename.lastIndexOf('.');

		if (i <= 0)
			return "";
		else
			return filename.substring(i + 1);
	}

	public static File addExtensionIfMissing(File path, String extension) {
		requireNonNull(path, "path");
		requireStringNotEmpty(extension, "extension");

		if (!getExtension(path.getName()).isEmpty())
			return path;

		if (extension.charAt(0) != '.')
			return new File(path.getParent(), path.getName() + '.' + extension);
		else
			return new File(path.getParent(), path.getName() + extension);
	}

	public static String stripExtension(String filename) {
		requireNonNull(filename);

		int i = filename.lastIndexOf('.');
		return i <= 0 ? filename : filename.substring(0, i);
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
			return;
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
			return;
		}
	}

	public static File checkPath(String pathname) {
		File path = new File(pathname);
		if (!path.isDirectory()) {
			return null;
		}
		return path;
	}

	public static boolean checkPathTextField(Component owner, JTextField pathField) {
		File path = new File(pathField.getText());
		if (!path.isDirectory()) {
			JOptionPane.showMessageDialog(owner, "\"" + pathField.getText() + "\" is not a valid path.",
			                              owner.getName(),
			                              JOptionPane.ERROR_MESSAGE);
			return false;
		}
		pathField.setText(path.getPath());
		return true;
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
			if (returnVal != JFileChooser.APPROVE_OPTION)
				return null;

			File file = chooser.getSelectedFile();
			if (file.exists())
				return file;
		}
	}

	/**
	 * @return the chosen file, or {@code null} if the user canceled.
	 */
	public static @Nullable File askFileForLoading(Component parent,
	                                               @Nullable File startFolderOrFile,
	                                               String fileTypeDescription,
	                                               String... fileTypeExtensions) {
		if (!(startFolderOrFile == null || startFolderOrFile.exists()))
			startFolderOrFile = new File(".");

		JFileChooser chooser = prepareFileChooser(parent, startFolderOrFile, fileTypeDescription, fileTypeExtensions);

		while (true) {
			int returnVal = chooser.showOpenDialog(parent);
			if (returnVal != JFileChooser.APPROVE_OPTION)
				return null;

			File file = chooser.getSelectedFile();
			if (file.exists())
				return file;
		}
	}

	/**
	 * @return the specified file, or {@code null} if the user canceled.
	 */
	public static @Nullable File askFileForSaving(Component parent,
	                                              @Nullable File startFolderOrFile,
	                                              String fileTypeDescription,
	                                              String... fileTypeExtensions) {
		JFileChooser chooser = prepareFileChooser(parent, startFolderOrFile, fileTypeDescription, fileTypeExtensions);

		while (true) {
			int returnVal = chooser.showSaveDialog(parent);
			if (returnVal != JFileChooser.APPROVE_OPTION)
				return null;

			File file = chooser.getSelectedFile();
			if (file.isDirectory())
				continue;

			file = addExtensionIfMissing(file, fileTypeExtensions[0]);

			if (file.exists()) {
				int choice = JOptionPane.showConfirmDialog(parent, "Overwrite " + file.getName() + " ?");
				if (choice == JOptionPane.CANCEL_OPTION)
					return null;
				else if (choice != JOptionPane.YES_OPTION)
					continue;
			}

			return file;
		}
	}

	private static JFileChooser prepareFileChooser(Component parent,
	                                               @Nullable File startFolderOrFile,
	                                               String fileTypeDescription,
	                                               String[] fileTypeExtensions) {
		requireNonNull(parent, "parent");
		requireNonNull(fileTypeDescription, "fileTypeDescription");
		requireArrayLengthAtLeast(1, fileTypeExtensions, "fileTypeExtensions");
		requireNonNullElements(fileTypeExtensions, "fileTypeExtensions");

		for (int i = 0; i < fileTypeExtensions.length; i++) {
			requireThat(!fileTypeExtensions[i].isEmpty(), "fileTypeExtensions[" + i + "] is empty.");
			if (fileTypeExtensions[i].charAt(0) == '.')
				fileTypeExtensions[i] = fileTypeExtensions[i].substring(1);
		}

		JFileChooser chooser = new JFileChooser(startFolderOrFile == null ?
		                                        null :
		                                        startFolderOrFile.isDirectory() ?
		                                        startFolderOrFile :
		                                        startFolderOrFile.getParentFile());

		if (startFolderOrFile != null && !startFolderOrFile.isDirectory())
			chooser.setSelectedFile(startFolderOrFile);

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
			if (i < fileTypeExtensions.length - 1)
				sb.append(", ");
			else
				sb.append(')');
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

		if (!leaveBakFile)
			Files.delete(bakFile);
	}

	public static void deleteTree(Path dir) throws IOException {
		Files.walkFileTree(dir, DELETE_TREE_VISITOR);
	}

	private static final FileVisitor<Path> DELETE_TREE_VISITOR = new SimpleFileVisitor<Path>() {
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
			Duration age              = Duration.between(lastModifiedTime, ZonedDateTime.now());
			return age.compareTo(maximumAge) <= 0;
		} catch (IOException ignored) {
			return false;
		}
	}

	public static String readString(Path file) throws IOException {
		return new String(Files.readAllBytes(file), UTF_8);
	}
}
