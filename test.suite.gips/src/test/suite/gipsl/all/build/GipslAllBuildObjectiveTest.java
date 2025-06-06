package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.objective.connector.ObjectiveConnector;

public class GipslAllBuildObjectiveTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new ObjectiveConnector(MODEL_PATH);
	}

	// Actual tests

	@Test
	public void testMap1to1() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		final double refObjA = 1 + 2 + Math.sin(3) + Math.cos(4) + Math.abs(-5) + -(6) + Math.pow(7, 3) + Math.sqrt(8)
				+ 1 - 9 + 10 / 10;
		final double refObjB = 500;
		final double refGlobal = 2 * refObjA + 73 + 2 * refObjB;
		assertEquals(refGlobal, ret.objectiveValue());
	}

	@Test
	public void testMap0to1() {
		gen.genSubstrateNode("s1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());

		// No matches -> Only static value of 73 + 2 * obj on class root
		final double refObjB = 500;
		final double refGlobal = 2 * refObjB + 73;
		assertEquals(refGlobal, ret.objectiveValue());
	}

	@Override
	public Class<?> getConnectorClass() {
		return ObjectiveConnector.class;
	}

}
