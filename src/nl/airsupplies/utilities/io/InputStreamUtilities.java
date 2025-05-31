package nl.airsupplies.utilities.io;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import nl.airsupplies.utilities.annotation.UtilityClass;

/**
 * @author Mark Jeronimus
 */
// Created 2017-03-05
@UtilityClass
public final class InputStreamUtilities {
	public static boolean isGzipped(BufferedInputStream in) throws IOException {
		in.mark(2);
		int lo = in.read();
		int hi = in.read();
		in.reset();

		// Check for gzip magic number
		int magic = hi << 8 | lo;
		return magic == GZIPInputStream.GZIP_MAGIC;
	}

	public static InputStream decompressIfPossible(BufferedInputStream in) throws IOException {
		if (isGzipped(in)) {
			return new GZIPInputStream(in);
		}

		return in;
	}
}
