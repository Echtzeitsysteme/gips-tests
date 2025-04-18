package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.equals.fxeqctrafo.connector.FxeqctrafoConnector;

/**
 * This class tests the correct transformation of f(x) == c <=> s. It can be
 * used to reproduce a bug in said transformation on the currently (2024-07-30)
 * un-merged feature branch `feature/improved_BooleanExpr_transformation`.
 */
public class GipslAllBuildFxeqctrafoTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new FxeqctrafoConnector(MODEL_PATH);
	}

	// Actual tests

	/**
	 * Test to map a valid virtual node onto a substrate node. This should select
	 * the N2N mapping and, therefore, as a consequence also the MU mapping.
	 */
	@Test
	public void test1to1() {
		gen.genSubstrateNode("s1", 10);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(1, ((FxeqctrafoConnector) con).getNumberOfNonZeroN2n());
		assertEquals(1, ((FxeqctrafoConnector) con).getNumberOfNonZeroMu());
	}

	/**
	 * In this test, there is only a substrate node but no virtual node(s).
	 * Therefore, no mapping of both types must be selected but the problem must be
	 * feasible in general.
	 */
	@Test
	public void test0to1() {
		gen.genSubstrateNode("s1", 10);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(0, ((FxeqctrafoConnector) con).getNumberOfNonZeroN2n());
		assertEquals(0, ((FxeqctrafoConnector) con).getNumberOfNonZeroMu());
	}

	/**
	 * In this test, there is no node at all. As a consequence, no mapping of both
	 * types must be selected. The problem must be feasible, though.
	 */
	@Test
	public void testNoNodes() {
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(0, ((FxeqctrafoConnector) con).getNumberOfNonZeroN2n());
		assertEquals(0, ((FxeqctrafoConnector) con).getNumberOfNonZeroMu());
	}

	/**
	 * In this test, the mapping is not successful, because the resource constraint
	 * forbids the rule selection. The problem must be feasible and both mappings
	 * must not have any selected candidates.
	 */
	@Test
	public void test0to1Resources() {
		gen.genSubstrateNode("s1", 10);
		gen.genVirtualNode("v1", 11);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(0, ((FxeqctrafoConnector) con).getNumberOfNonZeroN2n());
		assertEquals(0, ((FxeqctrafoConnector) con).getNumberOfNonZeroMu());
	}

	@Override
	public Class<?> getConnectorClass() {
		return FxeqctrafoConnector.class;
	}

}
