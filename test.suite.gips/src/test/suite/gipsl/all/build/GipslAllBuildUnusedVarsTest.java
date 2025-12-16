package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.Assertions;
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
		Path path = getLPOutputPath();
		try {
			if (Files.exists(path)) {
				System.out.println("Deleting: " + path);
				Files.deleteIfExists(path);
			}
		} catch (IOException e) {
			Assertions.fail(e);
		}
	}

	public List<String> loadLPFile() {
		Path path = getLPOutputPath();
		try {
			List<String> allLines = Files.readAllLines(path);
			return allLines;
		} catch (IOException e) {
			Assertions.fail(e);
			return null;
		}
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

	// Utility methods

	private static void assertTextContainsNot(List<String> lines, Pattern regex) {
		for (int i = 0; i < lines.size(); ++i) {
			Matcher m = regex.matcher(lines.get(i));
			if (m.find()) {
				String msg = String.format("Found pattern '%s' in line %d, character %d as '%s'", regex, i + 1,
						m.start(), m.group());
				Assertions.fail(msg);
			}
		}
	}

	private static void assertTextContains(List<String> lines, Pattern regex) {
		for (int i = 0; i < lines.size(); ++i) {
			Matcher m = regex.matcher(lines.get(i));
			if (m.find()) {
				return;
			}
		}

		Assertions.fail(String.format("Unable to find pattern '%s'", regex));
	}

	private void assertTextContainsExactly(List<String> lines, Pattern regex, int exactly) {
		List<Integer> counter = new LinkedList<>();
		for (int i = 0; i < lines.size(); ++i) {
			Matcher m = regex.matcher(lines.get(i));
			while (m.find()) {
				counter.add(i);
			}
		}

		if (counter.size() == 0) {
			Assertions.fail(String.format("Unable to find pattern '%s'", regex));
		} else if (counter.size() != exactly) {
			String msg = String.format("Found pattern '%s' %d times, expected to find it %d times", regex,
					counter.size(), exactly);
			Assertions.fail(msg);
		}
	}

	@Override
	public Class<?> getConnectorClass() {
		return UnusedvarsConnector.class;
	}

}
