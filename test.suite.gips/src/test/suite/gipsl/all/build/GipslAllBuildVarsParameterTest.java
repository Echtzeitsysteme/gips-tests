package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.emoflon.gips.core.ilp.ILPVariable;
import org.junit.jupiter.api.Test;

import gipsl.all.build.varsparameter.connector.VarsParameterConnector;

public class GipslAllBuildVarsParameterTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new VarsParameterConnector(MODEL_PATH);
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

		final ILPVariable<?> y = ((VarsParameterConnector) con).getVarsOutput().boundVars().get("y");

		assertEquals(3, ((VarsParameterConnector) con).getN2nMappings().values().iterator().next().getSnode().getResourceAmountAvailable());
		assertEquals(3, y.getValue().doubleValue());
	}
	
	@Test
	public void testMap0to1() {
		gen.genSubstrateNode("s1", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(0, Math.abs(ret.objectiveValue()));

		final ILPVariable<?> y = ((VarsParameterConnector) con).getVarsOutput().boundVars().get("y");
		assertNull(y);
	}

}
