package nl.airsupplies.utilities.io;

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

import nl.airsupplies.utilities.annotation.UtilityClass;

/**
 * @author Mark Jeronimus
 */
// Created 2016-04-23
@Deprecated // Don't use Java serialization framework. Do it manually.
@UtilityClass
public final class SerializationUtilities {
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
