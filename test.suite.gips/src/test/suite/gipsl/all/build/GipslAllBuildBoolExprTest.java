package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.boolexpr.connector.BoolExprConnector;

public class GipslAllBuildBoolExprTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new BoolExprConnector(MODEL_PATH);
	}

	// Actual tests

	@Test
	public void testStaticVal1() {
		// Static value 1 -> Mapping must be chosen
		gen.genSubstrateNode("s1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(1, Math.abs(ret.solutionCount()));
	}

	@Test
	public void testStaticVal0() {
		// Static value = 0 -> Mapping must not be chosen
		gen.genSubstrateNode("s1", 0);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(0, Math.abs(ret.solutionCount()));
	}

	@Test
	public void testStaticNoNode() {
		// No SubstrateResourceNode -> No constraint generated
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(1, Math.abs(ret.solutionCount()));
	}

	@Override
	public Class<?> getConnectorClass() {
		return BoolExprConnector.class;
	}

}
