package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.emoflon.gips.core.ilp.ILPVariable;
import org.junit.jupiter.api.Test;

import gipsl.all.build.vars.connector.VarsConnector;

public class GipslAllBuildVarsTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new VarsConnector(MODEL_PATH);
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
		
		final ILPVariable<?> v = ((VarsConnector) con).getVarsOutput().freeVars().get("v");
		final ILPVariable<?> w = ((VarsConnector) con).getVarsOutput().freeVars().get("w");
		assertEquals(2, v.getValue().doubleValue());
		assertEquals(1.5, w.getValue().doubleValue());
	}

}
