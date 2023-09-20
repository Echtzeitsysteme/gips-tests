package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.Test;

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
	public void testMap2to1() {
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
	
	// Utility methods

	private void checkNumberOfEmbeddedVnodes(final int expected) {
		int hostedVnodeCntr = 0;

		final Root root = gen.getRoot();
		for (final Container c : root.getContainers()) {
			if (c instanceof VirtualContainer) {
				final VirtualContainer vc = (VirtualContainer) c;
				for(final VirtualNode vn : vc.getVirtualNodes()) {
					if(vn.getHost() != null) {
						hostedVnodeCntr++;
					}
				}
			}
		}

		assertEquals(expected, hostedVnodeCntr);
	}

}
