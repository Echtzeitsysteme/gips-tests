package test.suite.gipsl.solverconfig;

import org.junit.jupiter.api.Test;

import test.suite.gips.utils.GipsTestUtils;

public class GipslSolverConfigTest {

	/**
	 * The name of the GIPS(L) project.
	 */
	private final String PROJECT_NAME = "gipsl.solverconfig";

	/**
	 * The package name used in the GIPS(L) project.
	 */
	private final String PACKAGE_NAME = "gipsl.solverconfig";

	/**
	 * Tests if the GIPS(L) project with the empty solver configuration was properly
	 * build, i.e., if the API source file and the gips-model.xmi were generated
	 * during build time.
	 */
	@Test
	public void testEmptyIlpSolverConfigSrcGen() {
		GipsTestUtils.checkIfFileGenerated(constructFilePath("SolverconfigGipsAPI.java"));
		GipsTestUtils.checkIfFileGenerated(constructFilePath("gips-model.xmi"));
	}

	//
	// Utilities
	//

	/**
	 * Constructs the complete file path containing: ../ to switch to the root of
	 * gips-examples, the project name, src-gen, the package name, api/gips, and the
	 * given file name.
	 * 
	 * @param fileName File name to construct the path for.
	 * @return Complete file path as described above.
	 */
	private String constructFilePath(final String fileName) {
		if (fileName == null || fileName.isBlank()) {
			throw new IllegalArgumentException("Given file name was null or empty.");
		}

		String filePath = "../" + PROJECT_NAME + "/src-gen/" + PACKAGE_NAME.replace(".", "/") + "/api/gips/" + fileName;
		return filePath;
	}

}
