package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.Test;

import gips.all.build.objective.connector.ObjectiveConnector;

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

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		final double refObjA = 1 + 2 + Math.sin(3) + Math.cos(4) + Math.abs(-5) + -(6) + Math.pow(7, 3) + Math.sqrt(8)
				+ 1 - 9 + 10 / 10;
		final double refObjB = 500;
		final double refGlobal = 2 * refObjA + 73 + 2 * refObjB;
		assertEquals(refGlobal, ret.objectiveValue());
	}

//	objective objA -> mapping::a {
//		1 + 2 + sin(3) + cos(4) + abs(-5) + -(6) + 7^3 + sqrt(8) + self.nodes().snode.resourceAmountTotal - 9 + 10/10
//	}
//	objective objB -> class::Root {
//		500
//	}
//
//	global objective : min {
//		2 * objA + 73 + 2 * objB
//	}

}