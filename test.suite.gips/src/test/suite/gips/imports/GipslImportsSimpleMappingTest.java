package test.suite.gips.imports;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.imports.sub.connector.ImportsSubConnector;

public class GipslImportsSimpleMappingTest extends AGipslImportsTest {

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new ImportsSubConnector(MODEL_PATH);
	}

	@Test
	public void testMap1To1() {
		gen.genHost("h1", 1);
		gen.genGuest("g1", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(1, ret.objectiveValue());
	}

	@Test
	public void testMap2To1() {
		gen.genHost("h1", 2);
		gen.genGuest("g1", 1);
		gen.genGuest("g2", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(2, ret.objectiveValue());
	}

	@Test
	public void testMap2To2() {
		gen.genHost("h1", 1);
		gen.genHost("h2", 1);
		gen.genGuest("g1", 1);
		gen.genGuest("g2", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(2, ret.objectiveValue());
	}

	@Test
	public void testMap0to1() {
		gen.genHost("h1", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(0, ret.objectiveValue());
	}

	@Test
	public void testMap1to0() {
		gen.genGuest("g1", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(ILPSolverStatus.INFEASIBLE, ret.status());
	}

	@Override
	public Class<?> getConnectorClass() {
		return ImportsSubConnector.class;
	}

}
