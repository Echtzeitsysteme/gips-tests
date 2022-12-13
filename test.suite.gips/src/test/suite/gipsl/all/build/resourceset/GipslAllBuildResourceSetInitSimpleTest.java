package test.suite.gipsl.all.build.resourceset;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.and.connector.AndConnector;

public class GipslAllBuildResourceSetInitSimpleTest extends AGipslAllBuildResourceSetTest {
	
	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new AndConnector(MODEL_PATH);
	}
	
	@Test
	public void testMap2to1() {
		gen.genSubstrateNode("s1", 10);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		// All mappings must be chosen, according to the objective function
		assertEquals(2, ret.objectiveValue());
	}

}
