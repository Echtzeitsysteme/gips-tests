package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.booleanbrackets.connector.BooleanBracketsConnector;

public class GipslAllBuildBoolExprTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new BooleanBracketsConnector(MODEL_PATH);
	}

	// Actual tests

	@Test
	public void testStaticVal1() {
		// Static value 1 -> Mapping must be chosen
		gen.genSubstrateNode("s1", 1);
		callableSetUp();
		
		final ILPSolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(1, Math.abs(ret.solutionCount()));
	}
	
	@Test
	public void testStaticVal0() {
		// Static value = 0 -> Mapping must not be chosen
		gen.genSubstrateNode("s1", 0);
		callableSetUp();
		
		final ILPSolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(0, Math.abs(ret.solutionCount()));
	}
	
	@Test
	public void testStaticNoNode() {
		// No SubstrateResourceNode -> No constraint generated
		callableSetUp();
		
		final ILPSolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(1, Math.abs(ret.solutionCount()));
	}

}
