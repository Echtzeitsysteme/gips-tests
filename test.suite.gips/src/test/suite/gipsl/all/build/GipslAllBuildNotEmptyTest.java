package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.notempty.connector.NotEmptyConnector;

public class GipslAllBuildNotEmptyTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new NotEmptyConnector(MODEL_PATH);
	}

	// Actual tests
	// Positive tests

	@Test
	public void testMap1to1() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(1, Math.abs(ret.objectiveValue()));
	}

	@Test
	public void testMap2to1() {
		// Resources/demands are ignored in this GIPSL project
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(2, Math.abs(ret.objectiveValue()));
	}

	@Test
	public void testMap2to2() {
		// Resources/demands are ignored in this GIPSL project
		gen.genSubstrateNode("s1", 1);
		gen.genSubstrateNode("s2", 1);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		// Both virtual nodes must be mapped to both substrate nodes
		assertEquals(2 * 2, Math.abs(ret.objectiveValue()));
	}

	// Negative tests
	@Test
	public void testMap1to0() {
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.INFEASIBLE, ret.status());
	}

}
