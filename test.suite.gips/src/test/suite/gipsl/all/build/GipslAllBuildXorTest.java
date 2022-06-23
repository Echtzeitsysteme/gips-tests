package test.suite.gipsl.all.build;

import org.junit.jupiter.api.Test;

import gipsl.all.build.xor.connector.XorConnector;
import test.suite.gips.utils.GipsTestUtils;

public class GipslAllBuildXorTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new XorConnector(MODEL_PATH);
	}

	// Actual tests

	@Test
	public void testMap2to1() {
		// TODO: Create a test case

//		gen.genSubstrateNode("s1", 1);
////		gen.genVirtualNode("v1", 1);
//		// TODO!
////		gen.genVirtualNode("v2", 1);
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
