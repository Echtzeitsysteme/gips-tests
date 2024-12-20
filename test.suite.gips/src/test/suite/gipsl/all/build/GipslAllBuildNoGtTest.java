package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.nogt.connector.NoGtConnector;

public class GipslAllBuildNoGtTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new NoGtConnector(MODEL_PATH);
	}

	// Actual tests

	@Test
	public void testConstraintOk() {
		gen.genSubstrateNode("s1", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
	}

	@Test
	public void testConstraintNotOk() {
		gen.genSubstrateNode("s1", -1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.INFEASIBLE, ret.status());
	}

	@Override
	public Class<?> getConnectorClass() {
		return NoGtConnector.class;
	}

}
