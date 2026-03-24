package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.typeselect.api.gips.TypeselectGipsAPI;
import gipsl.all.build.typeselect.api.gips.types.TypeSubstrateNodeExtension;
import gipsl.all.build.typeselect.api.gips.types.TypeSubstrateResourceNodeExtension;
import gipsl.all.build.typeselect.connector.TypeselectConnector;
import test.suite.gips.imports.AGipslImportsTest;

public class GipslAllBuildTypeSelectTest extends AGipslImportsTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new TypeselectConnector(MODEL_PATH);
	}

	// Actual tests

	// Positive tests

	@Test
	public void testMap1to1Yes() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(1, ret.objectiveValue());

		assertEquals(1, // 0 pre-existing nodes + v1
				getSubstrateNodeExtension("s1").getValueOfEmbeddedVirtualNodes());
		assertEquals(0, // 0 pre-existing nodes
				getSubstrateResourceNodeExtension("s1").getValueOfCurrentResourceUsage());
		assertEquals(1, // 1 v1
				getSubstrateResourceNodeExtension("s1").getValueOfAdditionalResourceUsage());
		assertEquals(0, // 1 - (0 pre-existing nodes + 1 v1)
				getSubstrateResourceNodeExtension("s1").getValueOfResourceAmountAvailable());
	}

	@Test
	public void testMap2to1Yes() {
		gen.genSubstrateNode("s1", 4);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 2);
		gen.embeddVnodeIntoSnode("v1", "s1");
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(1, ret.objectiveValue());

		assertEquals(2, // 1 pre-existing nodes + v2
				getSubstrateNodeExtension("s1").getValueOfEmbeddedVirtualNodes());
		assertEquals(1, // 1 pre-existing nodes
				getSubstrateResourceNodeExtension("s1").getValueOfCurrentResourceUsage());
		assertEquals(2, // 2 v2
				getSubstrateResourceNodeExtension("s1").getValueOfAdditionalResourceUsage());
		assertEquals(1, // 4 - (1 pre-existing nodes + 2 v2)
				getSubstrateResourceNodeExtension("s1").getValueOfResourceAmountAvailable());
	}

	@Test
	public void testMap3to1Yes() {
		gen.genSubstrateNode("s1", 4);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 2);
		gen.embeddVnodeIntoSnode("v1", "s1");
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(1, ret.objectiveValue());

		assertEquals(2, // 1 pre-existing nodes + v2
				getSubstrateNodeExtension("s1").getValueOfEmbeddedVirtualNodes());
		assertEquals(1, // 1 pre-existing nodes
				getSubstrateResourceNodeExtension("s1").getValueOfCurrentResourceUsage());
		assertEquals(2, // 2 v2
				getSubstrateResourceNodeExtension("s1").getValueOfAdditionalResourceUsage());
		assertEquals(1, // 4 - (1 pre-existing nodes + 2 v2)
				getSubstrateResourceNodeExtension("s1").getValueOfResourceAmountAvailable());
	}

	@Test
	public void testMap2to2Yes() {
		gen.genSubstrateNode("s1", 1);
		gen.genSubstrateNode("s2", 2);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 2);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(2, ret.objectiveValue());

		assertEquals(2, // 1 pre-existing nodes + v2
				getSubstrateNodeExtension("s1").getValueOfEmbeddedVirtualNodes());
		assertEquals(1, // 1 pre-existing nodes
				getSubstrateResourceNodeExtension("s1").getValueOfCurrentResourceUsage());
		assertEquals(2, // 2 v2
				getSubstrateResourceNodeExtension("s1").getValueOfAdditionalResourceUsage());
		assertEquals(1, // 4 - (1 pre-existing nodes + 2 v2)
				getSubstrateResourceNodeExtension("s1").getValueOfResourceAmountAvailable());
	}

	@Test
	public void testMap3to3Yes() {
		for (int i = 1; i <= 3; i++) {
			gen.genSubstrateNode("s" + i, i);
			gen.genVirtualNode("v" + i, i);
		}
		gen.embeddVnodeIntoSnode("v1", "s1");
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(2, ret.objectiveValue());
	}

	@Test
	public void testMap10to10Yes() {
		for (int i = 1; i <= 10; i++) {
			gen.genSubstrateNode("s" + i, i);
			gen.genVirtualNode("v" + i, i);
		}
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(10, ret.objectiveValue());
	}

	// Negative tests

	@Test
	public void testMap2to4No() {
		gen.genSubstrateNode("s1", 42);
		gen.genSubstrateNode("s2", 42);
		gen.genSubstrateNode("s3", 42);
		gen.genSubstrateNode("s4", 42);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 2);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.INFEASIBLE, ret.status());
	}

	@Override
	public Class<?> getConnectorClass() {
		return TypeselectConnector.class;
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

	private TypeselectGipsAPI getAPI() {
		return ((TypeselectConnector) con).getAPI();
	}

}
