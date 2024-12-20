package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.filter.connector.FilterConnector;

public class GipslAllBuildFilterTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new FilterConnector(MODEL_PATH);
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
		gen.genSubstrateNode("s1", 2);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.INFEASIBLE, ret.status());
	}

	@Test
	public void testMap2to1NoIsDoubled() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		System.out.println("=> Flaky test starts here:");
		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.INFEASIBLE, ret.status());
	}

	@Test
	public void testMap2to1NoOneHasNoMatch() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 2);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.INFEASIBLE, ret.status());
	}

	@Test
	public void testMap2to2Yes() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		gen.genSubstrateNode("s2", 2);
		gen.genVirtualNode("v2", 2);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(2, ret.objectiveValue());
	}

	@Test
	public void testMap10to10Yes() {
		for (int i = 1; i <= 10; i++) {
			gen.genSubstrateNode("s" + i, i);
			gen.genVirtualNode("v" + i, i);
		}
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(10, ret.objectiveValue());
	}

	@Override
	public Class<?> getConnectorClass() {
		return FilterConnector.class;
	}

}
