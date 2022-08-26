package test.suite.gipsl.string;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.and.connector.AndConnector;

public class GipslStringCompareTest extends AGipslStringTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new AndConnector(MODEL_PATH);
	}

	// Actual tests

	@Test
	public void testMap2to1NoDuplicates() {
		gen.genHost("a");
		gen.genGuest("a");
		gen.genGuest("b");
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(1, ret.objectiveValue());
	}

	@Test
	public void testMap3to1NoDuplicates() {
		gen.genHost("a");
		gen.genGuest("a");
		gen.genGuest("b");
		gen.genGuest("c");
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(1, ret.objectiveValue());
	}

	@Test
	public void testMap4to1NoDuplicates() {
		gen.genHost("a");
		gen.genGuest("a");
		gen.genGuest("b");
		gen.genGuest("c");
		gen.genGuest("d");
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(1, ret.objectiveValue());
	}

	@Test
	public void testMap1to2NoDuplicates() {
		gen.genHost("a");
		gen.genHost("b");
		gen.genGuest("a");
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(1, ret.objectiveValue());
	}

	@Test
	public void testMap2to2NoDuplicates() {
		gen.genHost("a");
		gen.genHost("b");
		gen.genGuest("a");
		gen.genGuest("b");
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(2, ret.objectiveValue());
	}

	@Test
	public void testMap10to10NoDuplicates() {
		for (int i = 1; i <= 10; i++) {
			gen.genHost(String.valueOf(i));
			gen.genGuest(String.valueOf(i));
		}
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(10, ret.objectiveValue());
	}

	@Test
	public void testMap1to10NoDuplicates() {
		for (int i = 1; i <= 10; i++) {
			gen.genHost("1");
		}
		gen.genGuest("1");
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(10, ret.objectiveValue());
	}

	@Test
	public void testMap0to1() {
		gen.genHost("a");
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(0, ret.objectiveValue());
	}

	@Test
	public void testMap1to0() {
		gen.genGuest("a");
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(0, ret.objectiveValue());
	}

}
