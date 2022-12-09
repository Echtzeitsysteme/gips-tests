package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.emoflon.gips.core.ilp.ILPVariable;
import org.junit.jupiter.api.Test;

import gipsl.all.build.varsmappingsum.connector.VarsMappingSumConnector;

public class GipslAllBuildVarsMappingSumTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new VarsMappingSumConnector(MODEL_PATH);
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

		final ILPVariable<?> v = ((VarsMappingSumConnector) con).getVarsOutput().freeVars().get("v");
		final ILPVariable<?> w = ((VarsMappingSumConnector) con).getVarsOutput().freeVars().get("w");
		final ILPVariable<?> x = ((VarsMappingSumConnector) con).getVarsOutput().freeVars().get("x");

		assertTrue(v.getValue().doubleValue() >= 0);
		assertTrue(w.getValue().doubleValue() >= 0);
		assertTrue(x.getValue().doubleValue() >= 0);

		assertEquals(42, v.getValue().doubleValue() + w.getValue().doubleValue() + x.getValue().doubleValue());
	}

}
