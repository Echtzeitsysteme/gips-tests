package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static test.suite.gips.utils.TextFileAsserts.assertTextContainsExactly;
import static test.suite.gips.utils.TextFileAsserts.assertTextContainsNot;
import static test.suite.gips.utils.TextFileAsserts.deleteFile;
import static test.suite.gips.utils.TextFileAsserts.readTextFile;

import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.unusedvars.connector.UnusedvarsConnector;

public class GipslAllBuildUnusedVarsTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new UnusedvarsConnector(MODEL_PATH);
	}

	public Path getLPOutputPath() {
		String path = ((UnusedvarsConnector) con).getLPOutputPath();
		assertNotNull(path, "No LP output path set. LP output required for this test");
		return Path.of(path).normalize();
	}

	public void deleteOutput() {
		deleteFile(getLPOutputPath());

	}

	public List<String> loadLPFile() {
		return readTextFile(getLPOutputPath());
	}

	// Actual tests

	@Test
	public void testMap1to1() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(1, Math.abs(ret.objectiveValue()));

		// Ensure that each relevant mapping contains at least one match
		assertFalse(((UnusedvarsConnector) con).getAPI().getN2n().getMappings().isEmpty());
		assertFalse(((UnusedvarsConnector) con).getAPI().getUnusedMapping().getMappings().isEmpty());

		List<String> lpFile = loadLPFile();
		assertTextContainsExactly(lpFile, Pattern.compile("\\bn2n#\\d+\\b(?!->)"), 3);
		assertTextContainsExactly(lpFile, Pattern.compile("\\bn2n#\\d+->usedVar\\b"), 2);
		assertTextContainsNot(lpFile, Pattern.compile("\\bn2n#\\d+->unusedVar\\b"));
		assertTextContainsNot(lpFile, Pattern.compile("unusedMapping"));
	}

	@Test
	public void testMap2to1() {
		gen.genSubstrateNode("s1", 2);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(2, Math.abs(ret.objectiveValue()));

		// Ensure that each relevant mapping contains at least one match
		assertFalse(((UnusedvarsConnector) con).getAPI().getN2n().getMappings().isEmpty());
		assertFalse(((UnusedvarsConnector) con).getAPI().getUnusedMapping().getMappings().isEmpty());

		List<String> lpFile = loadLPFile();
		assertTextContainsExactly(lpFile, Pattern.compile("\\bn2n#\\d+\\b(?!->)"), 6);
		assertTextContainsExactly(lpFile, Pattern.compile("\\bn2n#\\d+->usedVar\\b"), 4);
		assertTextContainsNot(lpFile, Pattern.compile("\\bn2n#\\d+->unusedVar\\b"));
		assertTextContainsNot(lpFile, Pattern.compile("unusedMapping"));
	}

	@Test
	public void testMap10to1() {
		gen.genSubstrateNode("s1", 10);
		for (int i = 1; i <= 10; i++) {
			gen.genVirtualNode("v" + i, 1);
		}
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(10, Math.abs(ret.objectiveValue()));

		// Ensure that each relevant mapping contains at least one match
		assertFalse(((UnusedvarsConnector) con).getAPI().getN2n().getMappings().isEmpty());
		assertFalse(((UnusedvarsConnector) con).getAPI().getUnusedMapping().getMappings().isEmpty());

		List<String> lpFile = loadLPFile();
		assertTextContainsExactly(lpFile, Pattern.compile("\\bn2n#\\d+\\b(?!->)"), 30);
		assertTextContainsExactly(lpFile, Pattern.compile("\\bn2n#\\d+->usedVar\\b"), 20);
		assertTextContainsNot(lpFile, Pattern.compile("\\bn2n#\\d+->unusedVar\\b"));
		assertTextContainsNot(lpFile, Pattern.compile("unusedMapping"));
	}

	@Override
	public Class<?> getConnectorClass() {
		return UnusedvarsConnector.class;
	}

}
