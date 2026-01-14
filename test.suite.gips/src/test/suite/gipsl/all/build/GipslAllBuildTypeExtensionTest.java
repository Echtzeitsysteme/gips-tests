package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static test.suite.gips.utils.TextFileAsserts.assertTextContains;
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

		// variable transfer asserts
		assertEquals(getSubstrateNodeExtension("s1").getValueOfEmbeddedVirtualNodes(),
				getSubstrateResourceNodeExtension("s1").getValueOfEmbeddedVirtualNodes2());

		List<String> lpFile = loadLPFile();
		assertTextContains(lpFile, Pattern.compile("embeddedVirtualNodes"));
		assertTextContains(lpFile, Pattern.compile("resourceAmountAvailable"));
		assertTextContains(lpFile, Pattern.compile("resourceAmountUsed"));
		assertTextContainsNot(lpFile, Pattern.compile("unusedVar"));

		getAPI().getTypeSubstrateNode().applyAllBoundVariablesToModel();
		getAPI().getTypeSubstrateResourceNode().applyAllBoundVariablesToModel();
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

		// variable transfer asserts
		assertEquals(getSubstrateNodeExtension("s1").getValueOfEmbeddedVirtualNodes(),
				getSubstrateResourceNodeExtension("s1").getValueOfEmbeddedVirtualNodes2());

		List<String> lpFile = loadLPFile();
		assertTextContains(lpFile, Pattern.compile("embeddedVirtualNodes"));
		assertTextContains(lpFile, Pattern.compile("resourceAmountAvailable"));
		assertTextContains(lpFile, Pattern.compile("resourceAmountUsed"));
		assertTextContainsNot(lpFile, Pattern.compile("unusedVar"));

		getAPI().getTypeSubstrateNode().applyAllBoundVariablesToModel();
		getAPI().getTypeSubstrateResourceNode().applyAllBoundVariablesToModel();
	}

	@Test
	public void testMap1to3() {
		gen.genSubstrateNode("s1", 1);
		gen.genSubstrateNode("s2", 2);
		gen.genSubstrateNode("s3", 3);
		gen.genVirtualNode("v1", 3);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(1, Math.abs(ret.objectiveValue()));

		// Ensure that at least one mapping was created
		assertFalse(getAPI().getN2n().getMappings().isEmpty());

		assertEquals(3, // 3 SubstrateNode in model -> 3 extension expected
				getSubstrateNodeExtensions().size());
		assertEquals(3, // 3 SubstrateResourceNode in model -> 3 extension expected
				getSubstrateResourceNodeExtensions().size());

		assertEquals(0, // v1 can only be embedded on v3
				getSubstrateNodeExtension("s1").getValueOfEmbeddedVirtualNodes());
		assertEquals(0, // v1 can only be embedded on v3
				getSubstrateNodeExtension("s2").getValueOfEmbeddedVirtualNodes());
		assertEquals(1, // v1 can only be embedded on v3
				getSubstrateNodeExtension("s3").getValueOfEmbeddedVirtualNodes());

		assertEquals(0, // no virtual node embedded, no resources used!
				getSubstrateResourceNodeExtension("s1").getValueOfResourceAmountUsed());
		assertEquals(0, // no virtual node embedded, no resources used!
				getSubstrateResourceNodeExtension("s2").getValueOfResourceAmountUsed());
		assertEquals(3, // v1 needs 3 units
				getSubstrateResourceNodeExtension("s3").getValueOfResourceAmountUsed());

		assertEquals(1, // 1 of 1 should be still available
				getSubstrateResourceNodeExtension("s1").getValueOfResourceAmountAvailable());
		assertEquals(2, // 2 of 2 should be still available
				getSubstrateResourceNodeExtension("s2").getValueOfResourceAmountAvailable());
		assertEquals(0, // none
				getSubstrateResourceNodeExtension("s3").getValueOfResourceAmountAvailable());

		// variable transfer asserts
		assertEquals(getSubstrateNodeExtension("s1").getValueOfEmbeddedVirtualNodes(),
				getSubstrateResourceNodeExtension("s1").getValueOfEmbeddedVirtualNodes2());
		assertEquals(getSubstrateNodeExtension("s2").getValueOfEmbeddedVirtualNodes(),
				getSubstrateResourceNodeExtension("s2").getValueOfEmbeddedVirtualNodes2());
		assertEquals(getSubstrateNodeExtension("s2").getValueOfEmbeddedVirtualNodes(),
				getSubstrateResourceNodeExtension("s2").getValueOfEmbeddedVirtualNodes2());

		List<String> lpFile = loadLPFile();
		assertTextContains(lpFile, Pattern.compile("embeddedVirtualNodes"));
		assertTextContains(lpFile, Pattern.compile("resourceAmountAvailable"));
		assertTextContains(lpFile, Pattern.compile("resourceAmountUsed"));
		assertTextContainsNot(lpFile, Pattern.compile("unusedVar"));

		getAPI().getTypeSubstrateNode().applyAllBoundVariablesToModel();
		getAPI().getTypeSubstrateResourceNode().applyAllBoundVariablesToModel();
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
