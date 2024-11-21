package test.suite.gips.utils;

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

}
