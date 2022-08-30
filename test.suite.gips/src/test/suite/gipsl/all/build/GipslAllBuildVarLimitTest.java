package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.varlimit.connector.VarLimitConnector;
import model.Root;
import model.SubstrateContainer;
import model.VirtualContainer;

public class GipslAllBuildVarLimitTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new VarLimitConnector(MODEL_PATH);
	}

	// Actual tests

	@Test
	public void testMap1to1() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(1, Math.abs(ret.objectiveValue()));
		checkConsistency();
	}

	@Test
	public void testMap10to1() {
		gen.genSubstrateNode("s1", 10);
		for (int i = 1; i <= 10; i++) {
			gen.genVirtualNode("v" + i, 1);
		}

		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(10, Math.abs(ret.objectiveValue()));
		checkConsistency();
	}

	@Test
	public void testMap10to2() {
		gen.genSubstrateNode("s1", 7);
		gen.genSubstrateNode("s2", 7);
		for (int i = 1; i <= 10; i++) {
			gen.genVirtualNode("v" + i, 1);
		}

		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		// Every virtual node must be mapped to both substrate nodes
		assertEquals(10 * 2, Math.abs(ret.objectiveValue()));
		checkConsistency();
	}

	// Negative tests

	@Test
	public void testMap1To2() {
		gen.genSubstrateNode("s1", 7);
		gen.genSubstrateNode("s2", 1);
		gen.genVirtualNode("v1", 2);

		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		// Status must be infeasible because the one virtual node has no match for the
		// second substrate node (because its resouce demand is too high)
		assertEquals(ILPSolverStatus.INFEASIBLE, ret.status());
	}

	// Utility methods

	private void checkConsistency() {
		final Root root = gen.loadModel(OUTPUT_PATH);
		root.getContainers().forEach(c -> {
			if (c instanceof VirtualContainer vc) {
				vc.getVirtualNodes().forEach(vn -> {
					assertNotNull(vn.getHost());
				});
			} else if (c instanceof SubstrateContainer sc) {
				sc.getSubstrateNodes().forEach(sn -> {
					assertNotNull(sn.getGuests());
					assertFalse(sn.getGuests().isEmpty());
				});
			}
		});
	}

}
