package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.gipsl.all.build.nestedattributeaccess.connector.NestedAttributeAccessConnector;
import org.junit.jupiter.api.Test;

public class GipslAllBuildNestedAttributeAccessTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new NestedAttributeAccessConnector(MODEL_PATH);
	}

	// Actual tests
	// Positive tests

	@Test
	public void testMap1to1() {
		gen.genSubstrateNode("s1", 10);
		gen.genVirtualNode("v1", 1);
		gen.embeddVnodeIntoSnode("v1", "s1");
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(2, ret.objectiveValue());
	}

	@Test
	public void testMap2to1() {
		gen.genSubstrateNode("s1", 10);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		gen.embeddVnodeIntoSnode("v1", "s1");
		gen.embeddVnodeIntoSnode("v2", "s1");
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(2 + 2 * 2, ret.objectiveValue());
	}

	@Override
	public Class<?> getConnectorClass() {
		return NestedAttributeAccessConnector.class;
	}

}
