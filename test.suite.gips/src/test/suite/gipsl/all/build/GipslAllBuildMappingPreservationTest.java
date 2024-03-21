package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Collection;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.mappingpreservation.api.gips.mapping.N2nMapping;
import gipsl.all.build.mappingpreservation.api.gips.mapping.ResDemMapping;
import gipsl.all.build.mappingpreservation.connector.MappingPreservationConnector;
import model.Container;
import model.Root;
import model.VirtualContainer;
import model.VirtualNode;

public class GipslAllBuildMappingPreservationTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new MappingPreservationConnector(MODEL_PATH);

		// Delete possible file of previous run
		final File output = new File(OUTPUT_PATH);
		output.delete();
	}

	// Actual tests
	// Positive tests

	@Test
	public void testMap2to1IndividualApplication() {
		gen.genSubstrateNode("s1", 2);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final ILPSolverOutput ret = ((MappingPreservationConnector) con).runWithNoApplication(OUTPUT_PATH);

		// Pre-checks
		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(2, Math.abs(ret.objectiveValue()));

		gen.loadModel(OUTPUT_PATH);

		// Check model state (pre-first application)
		checkNumberOfEmbeddedVnodes(0);

		final var appliedFirst = ((MappingPreservationConnector) con).applyMappingWithVnodeName("v1");
		assertEquals(1, appliedFirst.size());
		assertEquals("v1", appliedFirst.get(0).get().getVnode().getName());

		// Check model state (after first application)
		((MappingPreservationConnector) con).save(OUTPUT_PATH);
		gen.loadModel(OUTPUT_PATH);
		checkNumberOfEmbeddedVnodes(1);

		final var appliedSecond = ((MappingPreservationConnector) con).applyMappingWithVnodeName("v2");
		assertEquals(1, appliedSecond.size());
		assertEquals("v2", appliedSecond.get(0).get().getVnode().getName());

		// Check model state (after second application)
		((MappingPreservationConnector) con).save(OUTPUT_PATH);
		gen.loadModel(OUTPUT_PATH);
		checkNumberOfEmbeddedVnodes(2);
	}

	@Test
	public void testMap2to1ApplyNonZeroMappings() {
		gen.genSubstrateNode("s1", 2);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final ILPSolverOutput ret = ((MappingPreservationConnector) con).run(OUTPUT_PATH);
		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(2, Math.abs(ret.objectiveValue()));

		// Check model state
		gen.loadModel(OUTPUT_PATH);
		checkNumberOfEmbeddedVnodes(2);

		// Actual check: Both mappings must still have the value > 0, even if PM got
		// updated
		final Collection<N2nMapping> mappings = ((MappingPreservationConnector) con).getN2nMappings();
		// There are only two possible combinations
		assertEquals(2, mappings.size());
		for (final N2nMapping m : mappings) {
			assertTrue(m.getValue() > 0);
		}
	}

	@Test
	public void testMap10to3ApplyNonZeroMappings() {
		gen.genSubstrateNode("s1", 4);
		gen.genSubstrateNode("s2", 4);
		gen.genSubstrateNode("s3", 4);
		for (int i = 1; i <= 10; i++) {
			gen.genVirtualNode("v" + i, 1);
		}
		callableSetUp();

		final ILPSolverOutput ret = ((MappingPreservationConnector) con).run(OUTPUT_PATH);
		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(10, Math.abs(ret.objectiveValue()));

		// Check model state
		gen.loadModel(OUTPUT_PATH);
		checkNumberOfEmbeddedVnodes(10);

		// Actual check: Both mappings must still have the value > 0, even if PM got
		// updated
		final Collection<N2nMapping> mappings = ((MappingPreservationConnector) con).getN2nMappings();
		// There are 30 possible combinations
		assertEquals(30, mappings.size());
		// Check that there are exactly 10 mappings with value > 0 (one for each virtual
		// node)
		int counter = 0;
		for (final N2nMapping m : mappings) {
			if (m.getValue() > 0) {
				counter++;
			}
		}
		assertEquals(10, counter);
	}

	@Test
	public void testTwoSpecMappingsAfterEachOther() {
		gen.genSubstrateNode("s1", 100);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		gen.genVirtualNode("v100", 10);
		callableSetUp();

		// The first run application does not apply the matches for rule #2.
		// (This is intended!)
		final ILPSolverOutput ret = ((MappingPreservationConnector) con).run(OUTPUT_PATH);
		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(3, Math.abs(ret.objectiveValue()));

		// Check model state
		gen.loadModel(OUTPUT_PATH);
		checkNumberOfEmbeddedVnodes(2);

		// Check that all valid matches of rule #2 are still selected (value > 0)
		final Collection<ResDemMapping> resDemMappings = ((MappingPreservationConnector) con).getResDemMappings();
		// There is 1 possible combination
		assertEquals(1, resDemMappings.size());
		// The one mapping must also be selected
		for (final ResDemMapping m : resDemMappings) {
			assertTrue(m.getValue() > 0);
		}

		// Next step: Also apply the last mapping
		final var appliedSecond = ((MappingPreservationConnector) con).applyMappingWithVnode10Name("v100");
		assertEquals(1, appliedSecond.size());
		assertEquals("v100", appliedSecond.get(0).get().getVnode().getName());

		// Check model state (after second application)
		((MappingPreservationConnector) con).save(OUTPUT_PATH);
		gen.loadModel(OUTPUT_PATH);
		checkNumberOfEmbeddedVnodes(3);

		// Check N2N mapping afterwards
		final Collection<N2nMapping> n2nMappings = ((MappingPreservationConnector) con).getN2nMappings();
		// There are 2 possible combinations
		assertEquals(2, n2nMappings.size());
		// All two mappings must also be selected
		for (final N2nMapping m : n2nMappings) {
			assertTrue(m.getValue() > 0);
		}
	}

	// Utility methods

	private void checkNumberOfEmbeddedVnodes(final int expected) {
		int hostedVnodeCntr = 0;

		final Root root = gen.getRoot();
		for (final Container c : root.getContainers()) {
			if (c instanceof VirtualContainer) {
				final VirtualContainer vc = (VirtualContainer) c;
				for (final VirtualNode vn : vc.getVirtualNodes()) {
					if (vn.getHost() != null) {
						hostedVnodeCntr++;
					}
				}
			}
		}

		assertEquals(expected, hostedVnodeCntr);
	}

}
