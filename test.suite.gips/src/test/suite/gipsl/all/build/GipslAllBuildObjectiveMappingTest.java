package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.objectivemapping.connector.ObjectiveMappingConnector;

public class GipslAllBuildObjectiveMappingTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new ObjectiveMappingConnector(MODEL_PATH);
	}

	// Actual tests

	//
	// First mapping
	//

	@Test
	public void testMap1to1Res1() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(1, ret.objectiveValue());
	}

	@Test
	public void testMap1to1Res2() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 2);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(2, ret.objectiveValue());
	}

	@Test
	public void testMap2to1Res1() {
		gen.genSubstrateNode("s1", 2);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(2, ret.objectiveValue());
	}

	@Test
	public void testMap2to1Res3() {
		gen.genSubstrateNode("s1", 2);
		gen.genVirtualNode("v1", 3);
		gen.genVirtualNode("v2", 3);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(6, ret.objectiveValue());
	}

	//
	// Second mapping
	//

	@Test
	public void testMap1to1Res10Mapping2() {
		gen.genSubstrateNode("s1", 10);
		gen.genVirtualNode("v1", 10);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(1, ret.objectiveValue());
	}

	@Test
	public void testMap2to1Res10Mapping2() {
		gen.genSubstrateNode("s1", 10);
		gen.genVirtualNode("v1", 10);
		gen.genVirtualNode("v2", 10);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(2, ret.objectiveValue());
	}

	@Test
	public void testMap10to1Res10Mapping2() {
		gen.genSubstrateNode("s1", 10);
		for (int i = 1; i <= 10; i++) {
			gen.genVirtualNode("v" + i, 10);
		}
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(10, ret.objectiveValue());
	}

	@Override
	public Class<?> getConnectorClass() {
		return ObjectiveMappingConnector.class;
	}

}
