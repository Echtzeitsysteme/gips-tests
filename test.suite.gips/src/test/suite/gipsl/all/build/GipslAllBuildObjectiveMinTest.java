package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.objective.min.connector.ObjectiveMinConnector;

public class GipslAllBuildObjectiveMinTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new ObjectiveMinConnector(MODEL_PATH);
	}

	// Actual tests
	// Positive

	@Test
	public void testMap2to1() {
		gen.genSubstrateNode("s1", 10);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		// No mappings chosen according to objective goal
		assertEquals(0, ret.objectiveValue());
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
		// No mappings chosen according to objective goal
		assertEquals(0, ret.objectiveValue());
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
		// No mappings chosen according to objective goal
		assertEquals(0, ret.objectiveValue());
	}

	// Negative

	@Test
	public void testMap0to1() {
		gen.genSubstrateNode("s1", 10);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		// No mappings chosen because there are no matches
		assertEquals(0, ret.objectiveValue());
	}

	@Test
	public void testMap1to0() {
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		// No mappings chosen because there are no matches
		assertEquals(0, ret.objectiveValue());
	}

	@Override
	public Class<?> getConnectorClass() {
		return ObjectiveMinConnector.class;
	}

}
