package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.filter.serial.connector.FilterSerialConnector;

public class GipslAllBuildFilterSerialTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new FilterSerialConnector(MODEL_PATH);
	}

	// Actual tests

	@Test
	public void testMap1to1Yes() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(1, ret.objectiveValue());
	}
	
	@Test
	public void testMap1to1No() {
		gen.genSubstrateNode("s1", 0);
		gen.genVirtualNode("v1", 0);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.INFEASIBLE, ret.status());
	}
	
	@Test
	public void testMapOnlyFirstFilterExpr() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 0);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(1, ret.objectiveValue());
	}
	
	@Test
	public void testMapOnlySecondFilterExpr() {
		gen.genSubstrateNode("s1", 0);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(1, ret.objectiveValue());
	}

}
