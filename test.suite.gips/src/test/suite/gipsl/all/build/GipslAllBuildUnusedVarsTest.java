package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import gipsl.all.build.unusedvars.connector.UnusedvarsConnector;
import gipsl.all.build.vars.api.gips.mapping.N2nMapping;
import gipsl.all.build.vars.connector.VarsConnector;

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

		// Check that each relevant mapping contains at least one match
//		assertFalse(((UnusedvarsConnector) con).getAPI().getN2n().getMappings().isEmpty());
//		assertFalse(((UnusedvarsConnector) con).getAPI().getUnusedMapping().getMappings().isEmpty());

//		List<String> lpFile = loadLPFile();
//		assertTextContainsExactly(lpFile, Pattern.compile("n2n#\\d+"), 1);
//		assertTextContainsNot(lpFile, Pattern.compile("n2n#\\d+->unusedVar"));
//		assertTextContainsNot(lpFile, Pattern.compile("unusedMapping"));
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

		assertFalse(((VarsConnector) con).getN2nMappings().isEmpty());
		checkConstraints(((VarsConnector) con).getN2nMappings());
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

		assertFalse(((VarsConnector) con).getN2nMappings().isEmpty());
		checkConstraints(((VarsConnector) con).getN2nMappings());
	}

	// Utility methods

	private double getVarValFromMapping(final N2nMapping mapping, final String varName) {
		if (mapping.getBoundVariableNames().contains(varName)) {
			return mapping.getBoundVariables().get(varName).getValue().doubleValue();
		} else if (mapping.getFreeVariableNames().contains(varName)) {
			return mapping.getFreeVariables().get(varName).getValue().doubleValue();
		}

		throw new IllegalArgumentException("Var with name " + varName + " not found in mapping " + mapping);
	}

	private void checkConstraints(final Map<String, N2nMapping> mappings) {
		mappings.forEach((k, m) -> {
			final N2nMapping mapping = m;
			assertEquals(2, getVarValFromMapping(mapping, "v"));
			assertEquals(1.5, getVarValFromMapping(mapping, "w"));
			assertEquals(42, getVarValFromMapping(mapping, "x"));
		});
	}

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
			if (m.find()) {
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
