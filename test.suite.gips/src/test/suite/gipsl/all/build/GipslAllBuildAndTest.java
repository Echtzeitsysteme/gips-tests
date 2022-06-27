package test.suite.gipsl.all.build;

import org.junit.jupiter.api.Test;

import gipsl.all.build.and.connector.AndConnector;
import test.suite.gips.utils.GipsTestUtils;

public class GipslAllBuildAndTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new AndConnector(MODEL_PATH);
	}

	// Actual tests

	@Test
	public void testMap2to1() {
//		gen.genSubstrateNode("s1", 2);
//		gen.genVirtualNode("v1", 1);
//		gen.genVirtualNode("v2", 1);
//		callableSetUp();
//
//		final ILPSolverOutput ret = con.run(OUTPUT_PATH);
//
//		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
//		assertEquals(2, ret.objectiveValue());

		// TODO
		GipsTestUtils.failNotImplemented();
	}

}
