package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.emoflon.gips.core.ilp.ILPVariable;
import org.junit.jupiter.api.Test;

import gipsl.all.build.varssum.connector.VarsSumConnector;

public class GipslAllBuildVarsSumTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new VarsSumConnector(MODEL_PATH);
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

		final ILPVariable<?> v = ((VarsSumConnector) con).getVarsOutput().freeVars().get("v");
		final ILPVariable<?> w = ((VarsSumConnector) con).getVarsOutput().freeVars().get("w");
		final ILPVariable<?> x = ((VarsSumConnector) con).getVarsOutput().freeVars().get("x");

		assertEquals(0, w.getValue().doubleValue());
		assertEquals(10, v.getValue().doubleValue() + w.getValue().doubleValue() + x.getValue().doubleValue());
	}

}
