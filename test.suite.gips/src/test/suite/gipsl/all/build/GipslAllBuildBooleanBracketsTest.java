package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.booleanbrackets.connector.BooleanBracketsConnector;

public class GipslAllBuildBooleanBracketsTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new BooleanBracketsConnector(MODEL_PATH);
	}

	// Actual tests

	@Test
	public void testStaticVal1() {
		gen.genSubstrateNode("s1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
	}

	@Test
	public void testStaticVal10() {
		gen.genSubstrateNode("s1", 10);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.INFEASIBLE, ret.status());
	}

	@Test
	public void testStaticVal0() {
		gen.genSubstrateNode("s1", 0);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
	}

	@Test
	public void testStaticValNeg() {
		gen.genSubstrateNode("s1", -1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.INFEASIBLE, ret.status());
	}

	@Test
	public void testDynamicFit() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(1, Math.abs(ret.objectiveValue()));
	}

	@Test
	public void testDynamicSmallerNegative() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", -1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.INFEASIBLE, ret.status());
	}

	@Test
	public void testDynamicSmallerZero() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 0);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(0, Math.abs(ret.objectiveValue()));
	}

	@Test
	public void testDynamicLarger() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 2);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.INFEASIBLE, ret.status());
	}

	@Override
	public Class<?> getConnectorClass() {
		return BooleanBracketsConnector.class;
	}

}
