package nl.airsupplies.utilities.gui;

import java.awt.Component;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jetbrains.annotations.Nullable;

import nl.airsupplies.utilities.FileUtilities;
import nl.airsupplies.utilities.annotation.UtilityClass;
import static nl.airsupplies.utilities.validator.ArrayValidatorUtilities.requireArrayLengthAtLeast;
import static nl.airsupplies.utilities.validator.ArrayValueValidatorUtilities.requireValuesNonNull;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireNonNull;
import static nl.airsupplies.utilities.validator.ValidatorUtilities.requireThat;

/**
 * @author Mark Jeronimus
 */
// Created 2025-05-31 Extracted from FileUtilities
@UtilityClass
public final class FileDialogs {
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

	/**
	 * @return the chosen file, or {@code null} if the user canceled.
	 */
	public static @Nullable File askFileForLoading(@Nullable Component parent,
	                                               @Nullable File startFolderOrFile,
	                                               String fileTypeDescription,
	                                               String... fileTypeExtensions) {
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
	 * @return the chosen files, or an empty list if the user canceled.
	 */
	public static List<File> askFilesForLoading(@Nullable Component parent,
	                                            @Nullable File startFolderOrFile,
	                                            String fileTypeDescription,
	                                            String... fileTypeExtensions) {
		JFileChooser chooser = prepareFileChooser(startFolderOrFile, fileTypeDescription, fileTypeExtensions);
		chooser.setMultiSelectionEnabled(true);

		while (true) {
			int returnVal = chooser.showOpenDialog(parent);
			if (returnVal != JFileChooser.APPROVE_OPTION) {
				return Collections.emptyList();
			}

			List<File> files = Arrays.asList(chooser.getSelectedFiles());
			if (files.stream().allMatch(File::exists)) {
				return files;
			}
		}
	}

	/**
	 * @return the specified file, or {@code null} if the user canceled.
	 */
	public static @Nullable File askFileForSaving(@Nullable Component parent,
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

			file = FileUtilities.addExtensionIfMissing(file, fileTypeExtensions[0]);

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

	/**
	 * Support method for the {@code askFileFor} family of methods.
	 */
	public static JFileChooser prepareFileChooser(@Nullable File startFolderOrFile,
	                                              String fileTypeDescription,
	                                              String[] fileTypeExtensions) {
		requireNonNull(fileTypeDescription, "fileTypeDescription");
		requireArrayLengthAtLeast(1, fileTypeExtensions, "fileTypeExtensions");
		requireValuesNonNull(fileTypeExtensions, "fileTypeExtensions");

		for (int i = 0; i < fileTypeExtensions.length; i++) {
			int index = i;
			requireThat(!fileTypeExtensions[i].isEmpty(), () -> "fileTypeExtensions[" + index + "] is empty.");
			if (fileTypeExtensions[i].charAt(0) == '.') {
				fileTypeExtensions[i] = fileTypeExtensions[i].substring(1);
			}
		}

		if (startFolderOrFile != null && !startFolderOrFile.exists()) {
			startFolderOrFile = new File(".");
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
}
