package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static test.suite.gips.utils.TextFileAsserts.assertTextContains;
import static test.suite.gips.utils.TextFileAsserts.readTextFile;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.varbound.api.gips.VarboundGipsAPI;
import gipsl.all.build.varbound.api.gips.types.TypeSubstrateNodeExtension;
import gipsl.all.build.varbound.connector.VarBoundConnector;

public class GipslAllBuildVarBoundTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new VarBoundConnector(MODEL_PATH);
	}

	public Path getLPOutputPath() {
		String path = ((VarBoundConnector) con).getLPOutputPath();
		assertNotNull(path, "No LP output path set. LP output required for this test");
		return Path.of(path).normalize();
	}

	public List<String> loadLPFile() {
		return readTextFile(getLPOutputPath());
	}

	// Actual tests

	@Test
	public void testMap1to1butNotEnoughResources() {
		gen.genSubstrateNode("s1", 3); // per spec, a substrate node needs at least 5 resources
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.INFEASIBLE, ret.status());

		// Ensure that at least one mapping was created
		assertFalse(getAPI().getN2n().getMappings().isEmpty());

		List<String> lpFile = loadLPFile();
		// one for each type variable (# substrate nodes)
		assertTextContains(lpFile, Pattern.compile("1 <= SubstrateResourceNode#0->embeddedVirtualNodes <= 2"));

		// one for each mapping variable
		assertTextContains(lpFile, Pattern.compile("n2n#0->minimumResourcesRequired >= 2\\.5"));
	}

	@Test
	public void testMap2to2butNotEnoughResources() {
		gen.genSubstrateNode("s1", 10);
		gen.genSubstrateNode("s2", 2); // per spec, a substrate node needs at least 5 resources
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.INFEASIBLE, ret.status());

		// Ensure that at least one mapping was created
		assertFalse(getAPI().getN2n().getMappings().isEmpty());

		List<String> lpFile = loadLPFile();
		// one for each type variable (# substrate nodes)
		assertTextContains(lpFile, Pattern.compile("1 <= SubstrateResourceNode#0->embeddedVirtualNodes <= 2"));
		assertTextContains(lpFile, Pattern.compile("1 <= SubstrateResourceNode#1->embeddedVirtualNodes <= 2"));

		// one for each mapping variable
		assertTextContains(lpFile, Pattern.compile("n2n#0->minimumResourcesRequired >= 2\\.5"));
		assertTextContains(lpFile, Pattern.compile("n2n#1->minimumResourcesRequired >= 2\\.5"));
		assertTextContains(lpFile, Pattern.compile("n2n#2->minimumResourcesRequired >= 2\\.5"));
		assertTextContains(lpFile, Pattern.compile("n2n#3->minimumResourcesRequired >= 2\\.5"));
	}

	@Test
	public void testMap1to1butEnoughResources() {
		gen.genSubstrateNode("s1", 30); // per spec, a substrate node needs at least 5 resources
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(1, Math.abs(ret.objectiveValue()));

		// Ensure that at least one mapping was created
		assertFalse(getAPI().getN2n().getMappings().isEmpty());

		assertEquals(1, getSubstrateNodeExtension("s1").getValueOfEmbeddedVirtualNodes());
		assertEquals(15, getMappingValuesForSubstrateNode("s1")[0]);

		List<String> lpFile = loadLPFile();
		// one for each type variable (# substrate nodes)
		assertTextContains(lpFile, Pattern.compile("1 <= SubstrateResourceNode#0->embeddedVirtualNodes <= 2"));

		// one for each mapping variable
		assertTextContains(lpFile, Pattern.compile("n2n#0->minimumResourcesRequired >= 2\\.5"));
	}

	@Test
	public void testMap1to1() {
		gen.genSubstrateNode("s1", 5);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(1, Math.abs(ret.objectiveValue()));

		// Ensure that at least one mapping was created
		assertFalse(getAPI().getN2n().getMappings().isEmpty());

		assertEquals(1, // v1 should be embedded on s1
				getSubstrateNodeExtension("s1").getValueOfEmbeddedVirtualNodes());

		List<String> lpFile = loadLPFile();
		// one for each type variable (# substrate nodes)
		assertTextContains(lpFile, Pattern.compile("1 <= SubstrateResourceNode#0->embeddedVirtualNodes <= 2"));

		// one for each mapping variable
		assertTextContains(lpFile, Pattern.compile("n2n#0->minimumResourcesRequired >= 2\\.5"));
	}

	@Test
	public void testMap2to1() {
		gen.genSubstrateNode("s1", 5);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(2, Math.abs(ret.objectiveValue()));

		// Ensure that at least one mapping was created
		assertFalse(getAPI().getN2n().getMappings().isEmpty());

		assertEquals(2, // v1, v2 should be embedded on s1
				getSubstrateNodeExtension("s1").getValueOfEmbeddedVirtualNodes());

		List<String> lpFile = loadLPFile();
		// one for each type variable (# substrate nodes)
		assertTextContains(lpFile, Pattern.compile("1 <= SubstrateResourceNode#0->embeddedVirtualNodes <= 2"));

		// one for each mapping variable
		assertTextContains(lpFile, Pattern.compile("n2n#0->minimumResourcesRequired >= 2\\.5"));
		assertTextContains(lpFile, Pattern.compile("n2n#1->minimumResourcesRequired >= 2\\.5"));
	}

	@Test
	public void testMap3to1() {
		gen.genSubstrateNode("s1", 6);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		gen.genVirtualNode("v3", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(2, Math.abs(ret.objectiveValue()));

		// Ensure that at least one mapping was created
		assertFalse(getAPI().getN2n().getMappings().isEmpty());

		assertEquals(2, // only 2 nodes should be embedded on s1
				getSubstrateNodeExtension("s1").getValueOfEmbeddedVirtualNodes());

		List<String> lpFile = loadLPFile();
		// one for each type variable (# substrate nodes)
		assertTextContains(lpFile, Pattern.compile("1 <= SubstrateResourceNode#0->embeddedVirtualNodes <= 2"));

		// one for each mapping variable
		assertTextContains(lpFile, Pattern.compile("n2n#0->minimumResourcesRequired >= 2\\.5"));
		assertTextContains(lpFile, Pattern.compile("n2n#1->minimumResourcesRequired >= 2\\.5"));
		assertTextContains(lpFile, Pattern.compile("n2n#2->minimumResourcesRequired >= 2\\.5"));
	}

	@Test
	public void testMap0to1() {
		gen.genSubstrateNode("s1", 6);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		// s1 wants to have at least 1 virtual node
		assertEquals(SolverStatus.INFEASIBLE, ret.status());

		List<String> lpFile = loadLPFile();
		// one for each type variable (# substrate nodes)
		assertTextContains(lpFile, Pattern.compile("1 <= SubstrateResourceNode#0->embeddedVirtualNodes <= 2"));
	}

	@Test
	public void testMap2to3() {
		gen.genSubstrateNode("s1", 6);
		gen.genSubstrateNode("s2", 6);
		gen.genSubstrateNode("s3", 6);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		// every substrate wants to have at least 1 virtual node
		assertEquals(SolverStatus.INFEASIBLE, ret.status());

		List<String> lpFile = loadLPFile();
		// one for each type variable (# substrate nodes)
		assertTextContains(lpFile, Pattern.compile("1 <= SubstrateResourceNode#0->embeddedVirtualNodes <= 2"));
		assertTextContains(lpFile, Pattern.compile("1 <= SubstrateResourceNode#1->embeddedVirtualNodes <= 2"));
		assertTextContains(lpFile, Pattern.compile("1 <= SubstrateResourceNode#2->embeddedVirtualNodes <= 2"));
	}

	@Test
	public void testMap4to4() {
		gen.genSubstrateNode("s1", 6);
		gen.genSubstrateNode("s2", 6);
		gen.genSubstrateNode("s3", 6);
		gen.genSubstrateNode("s4", 6);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		gen.genVirtualNode("v3", 1);
		gen.genVirtualNode("v4", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(4, Math.abs(ret.objectiveValue()));

		// Ensure that at least one mapping was created
		assertFalse(getAPI().getN2n().getMappings().isEmpty());

		// Everyone gets 1 node
		assertEquals(1, getSubstrateNodeExtension("s1").getValueOfEmbeddedVirtualNodes());
		assertEquals(1, getSubstrateNodeExtension("s2").getValueOfEmbeddedVirtualNodes());
		assertEquals(1, getSubstrateNodeExtension("s3").getValueOfEmbeddedVirtualNodes());
		assertEquals(1, getSubstrateNodeExtension("s4").getValueOfEmbeddedVirtualNodes());
	}

	@Test
	public void testMap8to3() {
		gen.genSubstrateNode("s1", 6);
		gen.genSubstrateNode("s2", 6);
		gen.genSubstrateNode("s3", 6);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		gen.genVirtualNode("v3", 1);
		gen.genVirtualNode("v4", 1);
		gen.genVirtualNode("v5", 1);
		gen.genVirtualNode("v6", 1);
		gen.genVirtualNode("v7", 1);
		gen.genVirtualNode("v8", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(6, Math.abs(ret.objectiveValue()));

		// Ensure that at least one mapping was created
		assertFalse(getAPI().getN2n().getMappings().isEmpty());

		// Every substrate node can only accept maximal 2 virtual nodes
		assertEquals(2, getSubstrateNodeExtension("s1").getValueOfEmbeddedVirtualNodes());
		assertEquals(2, getSubstrateNodeExtension("s2").getValueOfEmbeddedVirtualNodes());
		assertEquals(2, getSubstrateNodeExtension("s3").getValueOfEmbeddedVirtualNodes());
	}

	// Utility methods

	private VarboundGipsAPI getAPI() {
		return ((VarBoundConnector) con).getAPI();
	}

	private Collection<TypeSubstrateNodeExtension> getSubstrateNodeExtensions() {
		return getAPI().getTypeSubstrateNode().getExtensions();
	}

	private double[] getMappingValuesForSubstrateNode(String nodeName) {
		return getAPI().getN2n().getMappings().values().stream() //
				.filter(e -> nodeName.equals(e.getSnode().getName())) //
				.mapToDouble(e -> e.getMinimumResourcesRequired().getValue()) //
				.toArray();
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
		return VarBoundConnector.class;
	}

}
