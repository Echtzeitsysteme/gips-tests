package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.implication.connector.ImplicationConnector;
import test.suite.gips.GlobalTestConfig;

public class GipslAllBuildImplicationTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new ImplicationConnector(MODEL_PATH);
	}

	// Actual tests
	// Positive tests

	@Test
	public void testMap1to1() {
		gen.genSubstrateNode("s1", 2);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		// No mapping must be chosen due to the implication constraint
		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(0, ret.objectiveValue(), GlobalTestConfig.DELTA_OBJ);
	}

	@Test
	public void testMap2to1() {
		gen.genSubstrateNode("s1", 2);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		// Both mappings must be chosen to fulfill the implication constraint
		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(2, ret.objectiveValue(), GlobalTestConfig.DELTA_OBJ);
	}

	@Test
	public void testMap3to1() {
		gen.genSubstrateNode("s1", 4);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		gen.genVirtualNode("v3", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		// Two mappings must be chosen to satisfy objective function
		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(3, ret.objectiveValue(), GlobalTestConfig.DELTA_OBJ);
	}

	@Test
	public void testMap0to1() {
		gen.genSubstrateNode("s1", 2);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		// No mapping possible, but constraint is fulfilled
		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(0, Math.abs(ret.objectiveValue()), GlobalTestConfig.DELTA_OBJ);
	}

	@Test
	public void testMap1to0() {
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		// No mapping possible, but constraint is not applied due to missing substrate
		// node
		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(0, Math.abs(ret.objectiveValue()), GlobalTestConfig.DELTA_OBJ);
	}

}
