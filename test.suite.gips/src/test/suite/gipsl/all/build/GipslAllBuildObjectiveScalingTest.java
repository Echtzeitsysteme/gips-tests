package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.objective.scaling.connector.ObjectiveScalingConnector;

public class GipslAllBuildObjectiveScalingTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new ObjectiveScalingConnector(MODEL_PATH);
	}

	// Actual tests

	@Test
	public void testMapObjA() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(73 * 1, ret.objectiveValue());
	}

	@Test
	public void testMapObjB() {
		gen.genSubstrateNode("s1", 2);
		gen.genVirtualNode("v1", 2);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(100 / 5, ret.objectiveValue());
	}

	@Test
	public void testMapSumC() {
		gen.genSubstrateNode("s1", 10);
		gen.genVirtualNode("v1", 10);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(10 / 5, ret.objectiveValue());
	}

	@Test
	public void testMapSumD() {
		gen.genSubstrateNode("s1", 20);
		gen.genVirtualNode("v1", 20);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(20 / 0.1, ret.objectiveValue());
	}

	@Test
	public void testMapSumEF() {
		gen.genSubstrateNode("s1", 30);
		gen.genVirtualNode("v1", 21);
		gen.genVirtualNode("v2", 22);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals((1 + 1) / 2, ret.objectiveValue());
	}

	@Test
	public void testMapSumH() {
		gen.genSubstrateNode("s1", 33);
		gen.genVirtualNode("v1", 33);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals((33) / 3 / 11, ret.objectiveValue());
	}

	@Test
	public void testMapObjI() {
		gen.genSubstrateNode("s1", 40);
		gen.genVirtualNode("v1", 40);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals((10 + 40) / 5, ret.objectiveValue());
	}

	@Override
	public Class<?> getConnectorClass() {
		return ObjectiveScalingConnector.class;
	}

}
