package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.objective.max.connector.ObjectiveMaxConnector;

public class GipslAllBuildObjectiveMaxTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new ObjectiveMaxConnector(MODEL_PATH);
	}

	// Actual tests

	@Test
	public void testMap2to1() {
		gen.genSubstrateNode("s1", 10);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		// All mappings chosen according to objective goal
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
		// All mappings chosen according to objective goal
		assertEquals(3, ret.objectiveValue());
	}

	@Test
	public void testMap4to1() {
		gen.genSubstrateNode("s1", 10);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		gen.genVirtualNode("v3", 1);
		gen.genVirtualNode("v4", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		// All mappings chosen according to objective goal
		assertEquals(4, ret.objectiveValue());
	}

	@Test
	public void testMap0to1() {
		gen.genSubstrateNode("s1", 10);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		// No mappings chosen according to objective goal
		// Objective is -0.0 -> Math.asb(...) fixes this for the test
		assertEquals(0, Math.abs(ret.objectiveValue()));
	}

	@Test
	public void testMap1to0() {
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		// No mappings chosen according to objective goal
		// Objective is -0.0 -> Math.asb(...) fixes this for the test
		assertEquals(0, Math.abs(ret.objectiveValue()));
	}

}
