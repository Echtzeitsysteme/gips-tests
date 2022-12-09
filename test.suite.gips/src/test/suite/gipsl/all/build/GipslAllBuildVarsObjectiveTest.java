package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.emoflon.gips.core.ilp.ILPVariable;
import org.junit.jupiter.api.Test;

import gipsl.all.build.varsobjective.connector.VarsObjectiveConnector;

public class GipslAllBuildVarsObjectiveTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new VarsObjectiveConnector(MODEL_PATH);
	}

	// Actual tests

	@Test
	public void testMap1to1() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(1338, Math.abs(ret.objectiveValue()));
		
		final ILPVariable<?> v = ((VarsObjectiveConnector) con).getVarsOutput().freeVars().get("v");		
		assertEquals(1337, v.getValue().doubleValue());
	}
	
	@Test
	public void testMap0to1() {
		gen.genSubstrateNode("s1", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(0, Math.abs(ret.objectiveValue()));
	}

}
