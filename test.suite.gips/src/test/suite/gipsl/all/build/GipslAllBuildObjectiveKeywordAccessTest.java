package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.objective.keywordaccess.connector.ObjectiveKeywordAccessConnector;

public class GipslAllBuildObjectiveKeywordAccessTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new ObjectiveKeywordAccessConnector(MODEL_PATH);
	}

	// Actual tests

	@Test
	public void testS1V0Expected5() {
		gen.genSubstrateNode("s1", 2);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		var expected = 3 // constant
				+ 0 // objA : no match
				+ 2 // sum(resourceAmountTotal)
				+ 0 // mappings (a) : no match
				+ 0 // patterns (vnodeNotMapped) : no match
				+ 0; // rules (mapVnode) : no match
		assertEquals(expected, ret.objectiveValue());
	}

	@Test
	public void testS1V0Expected10() {
		gen.genSubstrateNode("s1", 7);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		var expected = 3 // constant
				+ 0 // objA : no match
				+ 7 // sum(resourceAmountTotal)
				+ 0 // mappings (a) : no match
				+ 0 // patterns (vnodeNotMapped) : no match
				+ 0; // rules (mapVnode) : no match
		assertEquals(expected, ret.objectiveValue());
	}

	@Test
	public void testS3V0Expected9() {
		gen.genSubstrateNode("s1", 2);
		gen.genSubstrateNode("s2", 4);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		var expected = 3 // constant
				+ 0 // objA : no match
				+ 6 // sum(resourceAmountTotal)
				+ 0 // mappings (a) : no match
				+ 0 // patterns (vnodeNotMapped) : no match
				+ 0; // rules (mapVnode) : no match
		assertEquals(expected, ret.objectiveValue());
	}

	@Test
	public void testS1V0ExpectedNeg8() {
		gen.genSubstrateNode("s1", -11);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		var expected = 3 // constant
				+ 0 // objA : no match
				- 11 // sum(resourceAmountTotal)
				+ 0 // mappings (a) : no match
				+ 0 // patterns (vnodeNotMapped) : no match
				+ 0; // rules (mapVnode) : no match
		assertEquals(expected, ret.objectiveValue());
	}

	@Test
	public void testS1V1Expected13() {
		gen.genSubstrateNode("s1", 2);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		var expected = 3 // constant
				+ 5 // objA : at least one mapping (a)
				+ 2 // sum(resourceAmountTotal)
				+ 1 // mappings (a)
				+ 1 // patterns (vnodeNotMapped)
				+ 1; // rules (mapVnode)
		assertEquals(expected, ret.objectiveValue());
	}

	@Test
	public void testS1V1Expected19() {
		gen.genSubstrateNode("s1", 8);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		var expected = 3 // constant
				+ 5 // objA : at least one mapping (a)
				+ 8 // sum(resourceAmountTotal)
				+ 1 // mappings (a)
				+ 1 // patterns (vnodeNotMapped)
				+ 1; // rules (mapVnode)
		assertEquals(expected, ret.objectiveValue());
	}

	@Test
	public void testS2V3Expected38() {
		gen.genSubstrateNode("s1", 5);
		gen.genSubstrateNode("s2", -30);
		gen.genSubstrateNode("s3", 15);

		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		gen.genVirtualNode("v3", 1);

		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		var expected = 3 // constant
				+ 5 * 6 // objA : 6 mappings (a)
				+ 5 - 30 + 15 // sum(resourceAmountTotal)
				+ 6 // mappings (a)
				+ 3 // patterns (vnodeNotMapped)
				+ 6; // rules (mapVnode)
		assertEquals(expected, ret.objectiveValue());
	}

	@Override
	public Class<?> getConnectorClass() {
		return ObjectiveKeywordAccessConnector.class;
	}

}
