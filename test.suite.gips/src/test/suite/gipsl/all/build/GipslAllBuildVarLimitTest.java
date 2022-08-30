package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.varlimit.connector.VarLimitConnector;

public class GipslAllBuildVarLimitTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new VarLimitConnector(MODEL_PATH);
	}

	// Actual tests

	@Test
	public void testMap1to1() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(1, Math.abs(ret.objectiveValue()));
	}

	@Test
	public void testMap10to1() {
		gen.genSubstrateNode("s1", 10);
		for (int i = 1; i <= 10; i++) {
			gen.genVirtualNode("v" + i, 1);
		}

		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(10, Math.abs(ret.objectiveValue()));
	}

}
