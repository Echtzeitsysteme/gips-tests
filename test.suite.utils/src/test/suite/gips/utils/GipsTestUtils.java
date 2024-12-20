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

	/**
	 * Constructs the complete file path containing: ../ to switch to the root of
	 * gips-examples, the project name, src-gen, the package name, api/gips, and the
	 * given file name.
	 * 
	 * @param projectName Project name to be used for the path generation.
	 * @param packageName Package name to be used for the path generation.
	 * @param fileName    File name to construct the path for.
	 * @return Complete file path as described above.
	 */
	public static String constructFilePath(final String projectName, final String packageName, final String fileName) {
		if (projectName == null || projectName.isBlank()) {
			throw new IllegalArgumentException("Given project name was null or empty.");
		}

		if (packageName == null || packageName.isBlank()) {
			throw new IllegalArgumentException("Given package name was null or empty.");
		}

		if (fileName == null || fileName.isBlank()) {
			throw new IllegalArgumentException("Given file name was null or empty.");
		}

		String filePath = "../" + projectName + "/src-gen/" + packageName.replace(".", "/") + "/api/gips/" + fileName;
		return filePath;
	}

}
