package test.suite.gipsl.all.build;

import gipsl.all.build.or.connector.OrConnector;
import org.junit.jupiter.api.Test;

import test.suite.gips.utils.GipsTestUtils;

public class GipslAllBuildIOrTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new OrConnector(MODEL_PATH);
	}

	// Actual tests

	@Test
	public void testMap2to1() {
		// TODO
//		gen.genSubstrateNode("s1", 1);
//		gen.genVirtualNode("v1", 1);
//		callableSetUp();
//
//		final ILPSolverOutput ret = con.run(OUTPUT_PATH);
//
//		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
//		// v1 must not be mapped because it is forbidden by a constraint
//		assertEquals(0, ret.objectiveValue());

		GipsTestUtils.failNotImplemented();
	}

}
