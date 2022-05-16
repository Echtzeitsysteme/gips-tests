package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.connector.Connector;

public class GipslAllBuildSimpleAddTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new Connector(MODEL_PATH);
	}

	// Actual tests

	@Test
	public void testMap1to1() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);
		checkIfFileExists();

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(1, ret.objectiveValue());
	}

	@Test
	public void testMap2to1() {
		gen.genSubstrateNode("s1", 2);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);
		checkIfFileExists();

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(2, ret.objectiveValue());
	}

	@Test
	public void testMap10to1() {
		gen.genSubstrateNode("s1", 10);
		for (int i = 1; i <= 10; i++) {
			gen.genVirtualNode("v" + i, 1);
		}
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);
		checkIfFileExists();

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(10, ret.objectiveValue());
	}

	@Test
	public void testMap2to2() {
		gen.genSubstrateNode("s1", 1);
		gen.genSubstrateNode("s2", 1);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);
		checkIfFileExists();

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(2, ret.objectiveValue());
	}

	@Test
	public void testMap2to10() {
		for (int i = 1; i <= 10; i++) {
			gen.genSubstrateNode("s" + i, 1);
		}
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);
		checkIfFileExists();

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(2, ret.objectiveValue());
	}

	@Test
	public void testMap1NotPossible() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 2);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);
		checkIfFileExists();

		assertEquals(ILPSolverStatus.INFEASIBLE, ret.status());
	}

	@Test
	public void testMap2NotPossible() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);
		checkIfFileExists();

		assertEquals(ILPSolverStatus.INFEASIBLE, ret.status());
	}

}
