package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.not.connector.NotConnector;
import test.suite.gips.GlobalTestConfig;

public class GipslAllBuildNotTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new NotConnector(MODEL_PATH);
	}

	// Actual tests

	@Test
	public void testMap2to1() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		// v1 must not be mapped because it is forbidden by a constraint
		assertEquals(0, Math.abs(ret.objectiveValue()), GlobalTestConfig.DELTA_OBJ);
	}

}
