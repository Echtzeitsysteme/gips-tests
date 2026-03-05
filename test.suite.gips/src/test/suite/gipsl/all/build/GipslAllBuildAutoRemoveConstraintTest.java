package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.Test;

import gips.constraints.autoremove.connector.AutoremoveConnector;

public class GipslAllBuildAutoRemoveConstraintTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new AutoremoveConnector(MODEL_PATH);
	}

	// Actual tests

	/**
	 * Test trivial constraint removal with a single match.
	 */
	@Test
	public void testMap1to1() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(0, ret.stats().constraints);
		assertEquals(1, ret.stats().vars);
		assertEquals(1 + 1, ret.stats().getRemovedTrivialConstraints());
		assertEquals(0, ret.stats().getRemovedDuplicateConstraints());
	}

	/**
	 * Test trivial constraint removal with two matches.
	 */
	@Test
	public void testMap2to1() {
		gen.genSubstrateNode("s1", 2);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(1, ret.stats().constraints);
		assertEquals(2, ret.stats().vars);
		assertEquals(0 + 2, ret.stats().getRemovedTrivialConstraints());
		assertEquals(0, ret.stats().getRemovedDuplicateConstraints());
	}

	/**
	 * Test trivial constraint removal with three matches.
	 */
	@Test
	public void testMap3to1() {
		gen.genSubstrateNode("s1", 3);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		gen.genVirtualNode("v3", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(1, ret.stats().constraints);
		assertEquals(3, ret.stats().vars);
		assertEquals(0 + 3, ret.stats().getRemovedTrivialConstraints());
		assertEquals(0, ret.stats().getRemovedDuplicateConstraints());
	}

	@Test
	public void testMap1to1TrivialRemoval() {
		gen.genSubstrateNode("s1", 42);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(0, ret.stats().constraints);
		assertEquals(1, ret.stats().vars);
		assertEquals(0 + 3, ret.stats().getRemovedTrivialConstraints());
		assertEquals(0, ret.stats().getRemovedDuplicateConstraints());
	}

	/**
	 * This test will not trigger duplicate removal because the duplicate
	 * constraints are not of the same GIPSL constraint.
	 */
	@Test
	public void testMap2to1NoDuplicateRemoval() {
		gen.genSubstrateNode("s1", 42);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(2, ret.stats().constraints);
		assertEquals(2, ret.stats().vars);
		assertEquals(2, ret.stats().getRemovedTrivialConstraints());
		assertEquals(0, ret.stats().getRemovedDuplicateConstraints());
	}

	// TODO: Add at least one test for a successful duplicate removal within the
	// same GIPSL constraint

	@Override
	public Class<?> getConnectorClass() {
		return AutoremoveConnector.class;
	}

}
