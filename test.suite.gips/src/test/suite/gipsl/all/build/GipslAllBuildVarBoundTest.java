package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Collection;

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

		assertEquals(1, // v1 should be embedded on s1
				getSubstrateNodeExtension("s1").getValueOfEmbeddedVirtualNodes());
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

		assertEquals(2, // v1, v2 should be embedded on s1
				getSubstrateNodeExtension("s1").getValueOfEmbeddedVirtualNodes());
	}

	@Test
	public void testMap3to1() {
		gen.genSubstrateNode("s1", 3);
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
	}

	@Test
	public void testMap0to1() {
		gen.genSubstrateNode("s1", 3);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		// s1 wants to have at least 1 virtual node
		assertEquals(SolverStatus.INFEASIBLE, ret.status());
	}

	@Test
	public void testMap2to3() {
		gen.genSubstrateNode("s1", 3);
		gen.genSubstrateNode("s2", 3);
		gen.genSubstrateNode("s3", 3);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		// every substrate wants to have at least 1 virtual node
		assertEquals(SolverStatus.INFEASIBLE, ret.status());
	}

	@Test
	public void testMap4to4() {
		gen.genSubstrateNode("s1", 3);
		gen.genSubstrateNode("s2", 3);
		gen.genSubstrateNode("s3", 3);
		gen.genSubstrateNode("s4", 3);
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
		gen.genSubstrateNode("s1", 3);
		gen.genSubstrateNode("s2", 3);
		gen.genSubstrateNode("s3", 3);
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
