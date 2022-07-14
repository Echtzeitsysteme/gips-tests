package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.and.connector.AndConnector;

public class GipslAllBuildAndTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new AndConnector(MODEL_PATH);
	}

	// Actual tests
	// Positive tests

	@Test
	public void testMap2to1() {
		gen.genSubstrateNode("s1", 10);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
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

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		// All mappings must be chosen, according to the objective function
		assertEquals(3, ret.objectiveValue());
	}

	@Test
	public void testMap4to1() {
		gen.genSubstrateNode("s1", 10);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		gen.genVirtualNode("v3", 1);
		gen.genVirtualNode("leftout", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		// Only 3 mappings must be chosen, according to the constraint
		assertEquals(3, ret.objectiveValue());
	}

	// Negative tests

	@Test
	public void testMap0to1() {
		gen.genSubstrateNode("s1", 10);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		// No match found -> Problem must be infeasible
		assertEquals(ILPSolverStatus.INFEASIBLE, ret.status());
	}

	@Test
	public void testMap1to0() {
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		// No match found -> Problem must be infeasible
		assertEquals(ILPSolverStatus.INFEASIBLE, ret.status());
	}

}
