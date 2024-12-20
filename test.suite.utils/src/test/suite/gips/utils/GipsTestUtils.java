package test.suite.gips.utils;

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.junit.Assert;

public class GipsTestUtils {

	private GipsTestUtils() {
	}

	public static URI pathToAbsUri(final String path) {
		return pathToUri(System.getProperty("user.dir") + "/" + path);
	}

	public static URI pathToUri(final String path) {
		if (path == null || path.isBlank()) {
			throw new IllegalArgumentException("Given path is invalid!");
		}

		return URI.createFileURI(path);
	}

	public static void failNotImplemented() {
		Assert.fail("Implementation not yet finished!");
	}

	/**
	 * This method checks if a source file was generated during build time for a
	 * given file path.
	 * 
	 * @param path File path to check file existence for.
	 */
	public static void checkIfFileGenerated(final String path) {
		if (path == null || path.isBlank()) {
			throw new IllegalArgumentException("Given path is invalid!");
		}

		final File f = new File(path);
		if (!(f.exists() && !f.isDirectory())) {
			Assert.fail("Expected file could not be found.");
		}
	}

}
