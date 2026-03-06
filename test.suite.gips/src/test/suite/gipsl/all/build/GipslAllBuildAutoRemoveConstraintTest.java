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

	/**
	 * This test will not trigger duplicate removal because the constraint do not
	 * feature the same set of variables.
	 */
	@Test
	public void testMap2to2NoDuplicateRemoval() {
		gen.genSubstrateNode("s1", 3);
		gen.genSubstrateNode("s2", 3);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(2, ret.stats().constraints);
		assertEquals(4, ret.stats().vars);
		assertEquals(4, ret.stats().getRemovedTrivialConstraints());
		assertEquals(0, ret.stats().getRemovedDuplicateConstraints());
	}

	/**
	 * This test triggers a single duplicate removal.
	 */
	@Test
	public void testMap2to1DuplicateRemoval() {
		gen.genSubstrateNode("s1", 73);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(2, ret.stats().constraints);
		assertEquals(2, ret.stats().vars);
		assertEquals(2, ret.stats().getRemovedTrivialConstraints());
		assertEquals(1, ret.stats().getRemovedDuplicateConstraints());
	}

	/**
	 * This test triggers 7/8 duplicate removals.
	 */
	@Test
	public void testMap8to1DuplicateRemoval() {
		gen.genSubstrateNode("s1", 73);
		for (int i = 1; i <= 8; i++) {
			gen.genVirtualNode("v" + i, 1);
		}
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(2, ret.stats().constraints);
		assertEquals(8, ret.stats().vars);
		assertEquals(8, ret.stats().getRemovedTrivialConstraints());
		assertEquals(7, ret.stats().getRemovedDuplicateConstraints());
	}

	/**
	 * This test should not trigger any duplicate or trivial constraint removal,
	 * because the feature will be disabled.
	 */
	@Test
	public void testMap2to1DuplicateRemovalDisabled() {
		gen.genSubstrateNode("s1", 73);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		// Explicitly disable the GIPS feature
		((AutoremoveConnector) con).configureUselessConstraintRemoval(false);

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(2 + 2 + 1, ret.stats().constraints);
		assertEquals(2, ret.stats().vars);
		assertEquals(0, ret.stats().getRemovedTrivialConstraints());
		assertEquals(0, ret.stats().getRemovedDuplicateConstraints());
	}

	@Override
	public Class<?> getConnectorClass() {
		return AutoremoveConnector.class;
	}

}
