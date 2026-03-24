package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.uppercasenames.api.UppercasenamesAPI;
import gipsl.all.build.uppercasenames.connector.UpperCaseNamesConnector;

public class GipslAllBuildUpperCaseNamesTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new UpperCaseNamesConnector(MODEL_PATH);
	}

	// Actual tests
	// Positive tests

	@Test
	public void testMap1to1() {
		gen.genSubstrateNode("s1", 10);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(2, ret.objectiveValue());

		// Check number of matches
		assertEquals(1, getEmoflonApi().mapVnode().countMatches());
		assertEquals(1, getEmoflonApi().FindVnode().countMatches());
		assertEquals(1, getEmoflonApi().FINDVNODEE().countMatches());
		assertEquals(1, getEmoflonApi().UpperCaseRuleName().countMatches());
	}

	@Test
	public void testMap2to1() {
		gen.genSubstrateNode("s1", 10);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(4, ret.objectiveValue());

		// Check number of matches
		assertEquals(2, getEmoflonApi().mapVnode().countMatches());
		assertEquals(2, getEmoflonApi().FindVnode().countMatches());
		assertEquals(2, getEmoflonApi().FINDVNODEE().countMatches());
		assertEquals(1, getEmoflonApi().UpperCaseRuleName().countMatches());
	}
	
	@Test
	public void testMap2to2() {
		gen.genSubstrateNode("s1", 10);
		gen.genSubstrateNode("s2", 10);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(8, ret.objectiveValue());

		// Check number of matches
		assertEquals(4, getEmoflonApi().mapVnode().countMatches());
		assertEquals(2, getEmoflonApi().FindVnode().countMatches());
		assertEquals(2, getEmoflonApi().FINDVNODEE().countMatches());
		assertEquals(2, getEmoflonApi().UpperCaseRuleName().countMatches());
	}

	// Negative tests

	@Test
	public void testMap0to1() {
		gen.genSubstrateNode("s1", 10);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(0, Math.abs(ret.objectiveValue()));

		// Check number of matches
		assertEquals(0, getEmoflonApi().mapVnode().countMatches());
		assertEquals(0, getEmoflonApi().FindVnode().countMatches());
		assertEquals(0, getEmoflonApi().FINDVNODEE().countMatches());
		assertEquals(1, getEmoflonApi().UpperCaseRuleName().countMatches());
	}

	@Override
	public Class<?> getConnectorClass() {
		return UpperCaseNamesConnector.class;
	}

	// Utility methods

	private UppercasenamesAPI getEmoflonApi() {
		return (UppercasenamesAPI) ((UpperCaseNamesConnector) con).getEmoflonApi();
	}

}
