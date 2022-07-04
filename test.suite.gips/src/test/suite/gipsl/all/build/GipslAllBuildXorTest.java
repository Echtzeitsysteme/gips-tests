package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.xor.connector.XorConnector;

public class GipslAllBuildXorTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new XorConnector(MODEL_PATH);
	}

	// Actual tests

	@Test
	public void testMap2to1() {
		gen.genSubstrateNode("s1", 2);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		// One mapping must be chosen
		assertEquals(1, Math.abs(ret.objectiveValue()));
	}

	@Test
	public void testMap3to1() {
		gen.genSubstrateNode("s1", 3);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		gen.genVirtualNode("v3", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		// Only one of the three matches must be embedded because of the XOR constraint
		assertEquals(1, Math.abs(ret.objectiveValue()));
	}

	@Test
	public void testMap0to1() {
		gen.genSubstrateNode("s1", 2);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		// No mapping must be chosen but status must be infeasible
		// 0 matches -> both conditions are true -> XOR is always false
		assertEquals(ILPSolverStatus.INFEASIBLE, ret.status());
	}

	@Test
	public void testMap1to0() {
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		// No mapping must be chosen but status must be optimal
		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(0, Math.abs(ret.objectiveValue()));
	}

}
