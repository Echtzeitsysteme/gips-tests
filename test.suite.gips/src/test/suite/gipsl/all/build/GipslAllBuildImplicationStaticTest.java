package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.implicationstatic.connector.ImplicationStaticConnector;
import model.SubstrateContainer;
import model.SubstrateResourceNode;

public class GipslAllBuildImplicationStaticTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new ImplicationStaticConnector(MODEL_PATH);
	}

	// Actual tests

	// 1 => 1 = 1
	// 1 => 0 = 0
	// 0 => 1 = 1
	// 0 => 0 = 1

	@Test
	public void testTT() {
		gen.genSubstrateNode("s1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(0, Math.abs(ret.objectiveValue()));
	}

	@Test
	public void testTF() {
		gen.genSubstrateNode("s1", 1);
		((SubstrateResourceNode) ((SubstrateContainer) gen.getContainer("sub")).getSubstrateNodes().get(0))
				.setResourceAmountTotal(0);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.INFEASIBLE, ret.status());
	}

	@Test
	public void testFT() {
		gen.genSubstrateNode("s1", 1);
		((SubstrateResourceNode) ((SubstrateContainer) gen.getContainer("sub")).getSubstrateNodes().get(0))
				.setResourceAmountAvailable(0);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(0, Math.abs(ret.objectiveValue()));
	}

	@Test
	public void testFF() {
		gen.genSubstrateNode("s1", 0);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(0, Math.abs(ret.objectiveValue()));
	}

	@Override
	public Class<?> getConnectorClass() {
		return ImplicationStaticConnector.class;
	}

}
