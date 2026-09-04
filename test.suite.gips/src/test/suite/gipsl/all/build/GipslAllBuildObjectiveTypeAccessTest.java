package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.objective.typeaccess.connector.ObjectiveTypeAccessConnector;

public class GipslAllBuildObjectiveTypeAccessTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new ObjectiveTypeAccessConnector(MODEL_PATH);
	}

	// Actual tests

	@Test
	public void testMap0toVal1() {
		gen.genSubstrateNode("s1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(1, ret.objectiveValue());
	}

	@Test
	public void testMap0toVal7() {
		gen.genSubstrateNode("s1", 7);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(7, ret.objectiveValue());
	}

	@Test
	public void testMap0toValNegative() {
		gen.genSubstrateNode("s1", -11);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(-11, ret.objectiveValue());
	}

	@Test
	public void testMap1toVal1() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(1 + 1, ret.objectiveValue());
	}

	@Test
	public void testMap1toValNegative() {
		gen.genSubstrateNode("s1", -3);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(1 + (-3), ret.objectiveValue());
	}

	@Test
	public void testMap2toVal100() {
		gen.genSubstrateNode("s1", 100);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(2 + 100, ret.objectiveValue());
	}

	@Test
	public void testMap20toVal100() {
		gen.genSubstrateNode("s1", 100);
		for (int i = 1; i <= 20; i++) {
			gen.genVirtualNode("v" + i, 1);
		}
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(20 + 100, ret.objectiveValue());
	}

	@Override
	public Class<?> getConnectorClass() {
		return ObjectiveTypeAccessConnector.class;
	}

}
