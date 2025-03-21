package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.notequal.connector.NotEqualConnector;

public class GipslAllBuildNotEqualTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new NotEqualConnector(MODEL_PATH);
	}

	// Actual tests

	@Test
	public void testMap1to1() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		// v1 must not be mapped because it is forbidden by the constraint
		// (either more than one or 0 mappings per substrate node are allowed)
		assertEquals(0, Math.abs(ret.objectiveValue()));
	}

	@Test
	public void testMap2to1() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		// Both virtual nodes must be mapped because there is more than one mapping onto
		// the same substrate node possible
		assertEquals(2, Math.abs(ret.objectiveValue()));
	}

	@Override
	public Class<?> getConnectorClass() {
		return NotEqualConnector.class;
	}

}
