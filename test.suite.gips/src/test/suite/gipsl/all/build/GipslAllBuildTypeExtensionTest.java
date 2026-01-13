package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static test.suite.gips.utils.TextFileAsserts.assertTextContains;
import static test.suite.gips.utils.TextFileAsserts.assertTextContainsExactly;
import static test.suite.gips.utils.TextFileAsserts.assertTextContainsNot;
import static test.suite.gips.utils.TextFileAsserts.deleteFile;
import static test.suite.gips.utils.TextFileAsserts.readTextFile;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.typeextension.api.gips.TypeextensionGipsAPI;
import gipsl.all.build.typeextension.api.gips.types.TypeSubstrateNodeExtension;
import gipsl.all.build.typeextension.api.gips.types.TypeSubstrateResourceNodeExtension;
import gipsl.all.build.typeextension.connector.TypeextensionConnector;
import gipsl.all.build.unusedvars.connector.UnusedvarsConnector;

public class GipslAllBuildTypeExtensionTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new TypeextensionConnector(MODEL_PATH);
	}

	public Path getLPOutputPath() {
		String path = ((TypeextensionConnector) con).getLPOutputPath();
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
		gen.genSubstrateNode("s1", 3);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(1, Math.abs(ret.objectiveValue()));

		// Ensure that at least one mapping was created
		assertFalse(getAPI().getN2n().getMappings().isEmpty());

		assertEquals(1, // 1 SubstrateNode in model -> 1 extension expected
				getSubstrateNodeExtensions().size());
		assertEquals(1, // 1 SubstrateResourceNode in model -> 1 extension expected
				getSubstrateResourceNodeExtensions().size());

		assertEquals(1, // v1 should be embedded on s1
				getSubstrateNodeExtension("s1").getValueOfEmbeddedVirtualNodes());
		assertEquals(1, // v1 needs 1 unit
				getSubstrateResourceNodeExtension("s1").getValueOfResourceAmountUsed());
		assertEquals(2, // 2 of 3 should be still available
				getSubstrateResourceNodeExtension("s1").getValueOfResourceAmountAvailable());

		List<String> lpFile = loadLPFile();
		assertTextContains(lpFile, Pattern.compile("embeddedVirtualNodes"));
		assertTextContains(lpFile, Pattern.compile("resourceAmountAvailable"));
		assertTextContains(lpFile, Pattern.compile("resourceAmountUsed"));
		assertTextContainsNot(lpFile, Pattern.compile("unusedVar"));
	}

	@Test
	public void testMap2to1() {
		gen.genSubstrateNode("s1", 3);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(2, Math.abs(ret.objectiveValue()));

		// Ensure that at least one mapping was created
		assertFalse(getAPI().getN2n().getMappings().isEmpty());

		assertEquals(1, // 1 SubstrateNode in model -> 1 extension expected
				getSubstrateNodeExtensions().size());
		assertEquals(1, // 1 SubstrateResourceNode in model -> 1 extension expected
				getSubstrateResourceNodeExtensions().size());

		assertEquals(2, // v1 and v2 should be embedded on s1
				getSubstrateNodeExtension("s1").getValueOfEmbeddedVirtualNodes());
		assertEquals(2, // v1 needs 1 unit and v2 needs 1 unit
				getSubstrateResourceNodeExtension("s1").getValueOfResourceAmountUsed());
		assertEquals(1, // 1 of 3 should be still available
				getSubstrateResourceNodeExtension("s1").getValueOfResourceAmountAvailable());

		List<String> lpFile = loadLPFile();
		assertTextContains(lpFile, Pattern.compile("embeddedVirtualNodes"));
		assertTextContains(lpFile, Pattern.compile("resourceAmountAvailable"));
		assertTextContains(lpFile, Pattern.compile("resourceAmountUsed"));
		assertTextContainsNot(lpFile, Pattern.compile("unusedVar"));
	}

//	@Test
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

	private TypeextensionGipsAPI getAPI() {
		return ((TypeextensionConnector) con).getAPI();
	}

	private Collection<TypeSubstrateResourceNodeExtension> getSubstrateResourceNodeExtensions() {
		return getAPI().getTypeSubstrateResourceNode().getExtensions();
	}

	private Collection<TypeSubstrateNodeExtension> getSubstrateNodeExtensions() {
		return getAPI().getTypeSubstrateNode().getExtensions();
	}

	private TypeSubstrateResourceNodeExtension getSubstrateResourceNodeExtension(String name) {
		for (var node : getSubstrateResourceNodeExtensions()) {
			if (node.getContext().getName().equals(name)) {
				return node;
			}
		}
		return null;
	}

	private TypeSubstrateNodeExtension getSubstrateNodeExtension(String name) {
		for (var node : getSubstrateNodeExtensions()) {
			if (node.getContext().getName().equals(name)) {
				return node;
			}
		}
		return null;
	}

	@Override
	public Class<?> getConnectorClass() {
		return TypeextensionConnector.class;
	}

}
