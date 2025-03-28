package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.mappingpreservationb.connector.MappingPreservationBConnector;
import model.Container;
import model.Root;
import model.VirtualContainer;
import model.VirtualNode;
import model.VirtualResourceNode;

public class GipslAllBuildMappingPreservationBTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new MappingPreservationBConnector(MODEL_PATH);

		// Delete possible file of previous run
		final File output = new File(OUTPUT_PATH);
		output.delete();
	}

	// Actual tests
	// Positive tests

	@Test
	public void testOneVNodeZero() {
		gen.genVirtualNode("v1", 0);
		callableSetUp();

		final SolverOutput ret = ((MappingPreservationBConnector) con).runWithNoApplication(OUTPUT_PATH);

		// Pre-checks
		assertEquals(SolverStatus.OPTIMAL, ret.status());

		// One VNode with two applied mappings
		assertEquals(2, Math.abs(ret.objectiveValue()));

		gen.loadModel(OUTPUT_PATH);

		// Check model state (pre-first application)
		checkNumberOfZeroVnodes(1);
		assertEquals(1, ((MappingPreservationBConnector) con).getIncrMappings().size());
		assertEquals(1, ((MappingPreservationBConnector) con).getIncrIfZeroMappings().size());

		// Apply all (one) mapping of type "increment"
		((MappingPreservationBConnector) con).applyIncrMatches();

		// First mapping (of type "increment") must be present
		assertEquals(1, ((MappingPreservationBConnector) con).getIncrMappings().size());

		// Second mapping (of type "increment if zero") will be gone
		assertEquals(0, ((MappingPreservationBConnector) con).getIncrIfZeroMappings().size());
	}

	//
	// Utility methods
	//

	private void checkNumberOfZeroVnodes(final int expected) {
		int vnodeCntr = 0;

		final Root root = gen.getRoot();
		for (final Container c : root.getContainers()) {
			if (c instanceof VirtualContainer) {
				final VirtualContainer vc = (VirtualContainer) c;
				for (final VirtualNode vn : vc.getVirtualNodes()) {
					if (vn instanceof VirtualResourceNode vrn) {
						if (vrn.getResourceDemand() == 0) {
							vnodeCntr++;
						}
					}
				}
			}
		}

		assertEquals(expected, vnodeCntr);
	}

	@Override
	public Class<?> getConnectorClass() {
		return MappingPreservationBConnector.class;
	}

}
