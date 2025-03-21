package test.suite.gipsl.solverconfig;

import org.junit.jupiter.api.Test;

import test.suite.gips.AbstractGipsTest;
import test.suite.gips.utils.GipsTestUtils;

public class GipslSolverConfigTest extends AbstractGipsTest {

	/**
	 * The name of the GIPS(L) project.
	 */
	private final String PROJECT_NAME = "gipsl.solverconfig";

	/**
	 * The package name used in the GIPS(L) project.
	 */
	private final String PACKAGE_NAME = PROJECT_NAME;

	/**
	 * Tests if the GIPS(L) project with the empty solver configuration was properly
	 * build, i.e., if the API source file and the gips-model.xmi were generated
	 * during build time.
	 */
	@Test
	public void testEmptyIlpSolverConfigSrcGen() {
		GipsTestUtils.checkIfFileGenerated(
				GipsTestUtils.constructFilePath(PROJECT_NAME, PACKAGE_NAME, "SolverconfigGipsAPI.java"));
	}

	@Override
	public String getProjectName() {
		return PROJECT_NAME;
	}

	/**
	 * This method is not necessary for this test case, because there is no
	 * connector class and the project name gets hard-coded above, anyway.
	 */
	@Override
	public Class<?> getConnectorClass() {
		return null;
	}

}
