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

package org.digitalmodular.utilities.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

/**
 * @author Mark Jeronimus
 */
// Created 2016-04-23
@Deprecated
public enum SerializationUtilities {
	;

	public static void serialize(Serializable obj, Path file) throws IOException {
		try (ObjectOutput os = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(file)))) {
			os.writeObject(obj);
		}
	}

	@SuppressWarnings("unchecked") // ClassCastException must be thrown if runtime type differs.
	public static <O extends Serializable> O deserialize(Path file) throws IOException, ClassNotFoundException {
		try (ObjectInputStream os = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(file)))) {
			return (O)os.readObject();
		}
	}

	public static String serialize(Serializable obj) throws IOException {
		try (ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		     ObjectOutput os = new ObjectOutputStream(bytes)) {
			os.writeObject(obj);
			return Base64.getEncoder().encodeToString(bytes.toByteArray());
		}
	}

	public static Object deserialize(String str) throws IOException, ClassNotFoundException {
		byte[] data = Base64.getDecoder().decode(str);
		try (ObjectInput is = new ObjectInputStream(new ByteArrayInputStream(data))) {
			return is.readObject();
		}
	}
}
