package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.Assert;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import gips.xmiinit.connector.XmiInitConnector;

public class GipslAllBuildXmiInitTest extends AGipslAllBuildTest {

	final String projectFolder = System.getProperty("user.dir");
	final String resourceFolder = projectFolder + "/../gips.xmiinit/resources";
	private final String gipsModelPath = resourceFolder + "/gips-model.xmi";
	private final String modelPath = resourceFolder + "/model.xmi";
	private final String ibexPatternPath = resourceFolder + "/ibex-patterns.xmi";
	private final String hipeNetworkPath = resourceFolder + "/hipe-network.xmi";
	private final String hipeEngineClassName = "gips.xmiinit.hipe.engine.HiPEEngine";

	// Setup method

	public void callableSetUp() {
		deleteDefaultXmiFiles();
		con = new XmiInitConnector(gipsModelPath, modelPath, ibexPatternPath, hipeNetworkPath, hipeEngineClassName);
	}

	// Actual tests

	@Test
	public void testMap1to1() {
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		// One mapping must be chosen, according to the objective function
		assertEquals(1, ret.objectiveValue());
	}

	// Utility methods

	private void deleteDefaultXmiFiles() {
		final String projectPath = "/../gips.xmiinit/src-gen/gips/xmiinit";
		try {
			final String gipsModelPath = projectFolder + projectPath + "/api/gips/gips-model.xmi";
			final String ibexPatternPath = projectFolder + projectPath + "/api/ibex-patterns.xmi";
			final String hipeNetworkPath = projectFolder + projectPath + "/hipe/engine/hipe-network.xmi";

			final File gipsModelFile = new File(gipsModelPath);
			if (gipsModelFile.exists()) {
				Files.delete(Paths.get(gipsModelPath));
			}

			final File ibexPatternFile = new File(ibexPatternPath);
			if (ibexPatternFile.exists()) {
				Files.delete(Paths.get(ibexPatternPath));
			}

			final File hipeNetworkFile = new File(hipeNetworkPath);
			if (hipeNetworkFile.exists()) {
				Files.delete(Paths.get(hipeNetworkPath));
			}
		} catch (final IOException e) {
			e.printStackTrace();
			Assert.fail("IOException caught.");
		}
	}

	/**
	 * This test intentionally deletes the default generated XMI files. Hence, it
	 * will always fail to check for the generated files.
	 */
	@Disabled
	@Override
	public void testGeneratedFiles() {
		// Do nothing.
	}

	@Override
	public Class<?> getConnectorClass() {
		return null;
	}

}
