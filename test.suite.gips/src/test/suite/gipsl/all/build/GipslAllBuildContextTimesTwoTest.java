package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.contexttimestwo.connector.ContextTimesTwoConnector;

public class GipslAllBuildContextTimesTwoTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new ContextTimesTwoConnector(MODEL_PATH);
	}

	// Actual tests

	@Test
	public void testMap1to1() {
		gen.genSubstrateNode("s1", 10);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		// All mappings must be chosen, according to the objective function
		assertEquals(1, ret.objectiveValue());
	}

	@Test
	public void testMap2to1() {
		gen.genSubstrateNode("s1", 10);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		// All mappings must be chosen, according to the objective function
		assertEquals(2, ret.objectiveValue());
	}

	@Test
	public void testMap3to1() {
		gen.genSubstrateNode("s1", 10);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		gen.genVirtualNode("v3", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		// All mappings must be chosen, according to the objective function
		assertEquals(3, ret.objectiveValue());
	}

	@Test
	public void testMap3to2() {
		gen.genSubstrateNode("s1", 10);
		gen.genSubstrateNode("s2", 10);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		gen.genVirtualNode("v3", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		// All mappings must be chosen, according to the objective function
		assertEquals(3 * 2, ret.objectiveValue());
	}

	@Test
	public void testMap3to3() {
		gen.genSubstrateNode("s1", 10);
		gen.genSubstrateNode("s2", 10);
		gen.genSubstrateNode("s3", 10);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		gen.genVirtualNode("v3", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.INFEASIBLE, ret.status());
	}

	@Test
	public void testMap1to3() {
		gen.genSubstrateNode("s1", 10);
		gen.genSubstrateNode("s2", 10);
		gen.genSubstrateNode("s3", 10);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.INFEASIBLE, ret.status());
	}

	@Override
	public Class<?> getConnectorClass() {
		return ContextTimesTwoConnector.class;
	}

}
