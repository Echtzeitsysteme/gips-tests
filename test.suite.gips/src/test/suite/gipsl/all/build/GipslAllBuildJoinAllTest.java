package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.joinall.connector.JoinAllConnector;

public class GipslAllBuildJoinAllTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new JoinAllConnector(MODEL_PATH);
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
	}

	@Test
	public void testMap2to1Yes() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(2, ret.objectiveValue());
	}

	@Test
	public void testMap4to1Yes() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		gen.genVirtualNode("v3", 1);
		gen.genVirtualNode("v4", 1);
		gen.embeddVnodeIntoSnode("v1", "s1");
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(3, ret.objectiveValue());
	}

	// Negative tests

	@Test
	public void testMap1to2No() {
		gen.genSubstrateNode("s1", 2);
		gen.genSubstrateNode("s2", 2);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.INFEASIBLE, ret.status());
	}

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
		return JoinAllConnector.class;
	}

}
