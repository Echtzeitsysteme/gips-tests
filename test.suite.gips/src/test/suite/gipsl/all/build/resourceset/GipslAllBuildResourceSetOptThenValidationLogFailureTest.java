package test.suite.gipsl.all.build.resourceset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gipsl.all.build.resourcesinit.optthenvallog.connector.OptThenValidationLogConnector;
import model.SubstrateContainer;
import model.SubstrateResourceNode;
import test.suite.gips.utils.AResourceConnector;

/**
 * This test should trigger a bug within GIPS that makes all feasible models
 * infeasible if the validation log was invalid once.
 */
public class GipslAllBuildResourceSetOptThenValidationLogFailureTest extends AGipslAllBuildResourceSetTest {

	/**
	 * Connector to the GIPS project with resource set initialization
	 */
	private AResourceConnector con;

	// Setup method

	@BeforeEach
	public void setUp() {
		gen.reset();
		con = new OptThenValidationLogConnector(gen.getResourceSet());
	}

	@Test
	public void testMapSnodeFirstInvalid() {
		// Set up in such a way that the validation log gets triggered
		gen.genSubstrateNode("s1", 1);

		final ILPSolverOutput ret = con.solve();
		con.apply();

		assertEquals(ILPSolverStatus.INFEASIBLE, ret.status());
		assertTrue(ret.validationLog().isNotValid());

		// Lets start with the real test!
		// Reset the model
		gen.reset();
		gen.genVirtualNode("v1", 1);

		final ILPSolverOutput ret2 = con.solve();
		con.apply();

		assertEquals(ILPSolverStatus.OPTIMAL, ret2.status());
		assertEquals(0, Math.abs(ret2.objectiveValue()));
	}

	@Test
	public void testMapSnodeSecondInvalid() {
		// Set up in such a way that the validation log does not get triggered
		gen.genVirtualNode("v1", 1);

		final ILPSolverOutput ret = con.solve();
		con.apply();

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertFalse(ret.validationLog().isNotValid());

		// Reset the model
		gen.reset();
		gen.genSubstrateNode("s1", 1);

		final ILPSolverOutput ret2 = con.solve();
		con.apply();

		// Now, the validation log must be invalid again + the problem must be
		// infeasible
		assertEquals(ILPSolverStatus.INFEASIBLE, ret2.status());
		assertTrue(ret2.validationLog().isNotValid());
	}

	@Test
	public void testBothValid() {
		gen.genSubstrateNode("s1", 1);
		final SubstrateContainer sub = (SubstrateContainer) gen.getContainer("sub");
		final SubstrateResourceNode snode = (SubstrateResourceNode) sub.getSubstrateNodes().get(0);
		snode.setResourceAmountAvailable(2);

		final ILPSolverOutput ret = con.solve();
		con.apply();

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(0, Math.abs(ret.objectiveValue()));

		// Add a virtual node
		gen.genVirtualNode("v1", 1);

		final ILPSolverOutput ret2 = con.solve();
		con.apply();

		assertEquals(ILPSolverStatus.OPTIMAL, ret2.status());
		assertEquals(1, Math.abs(ret2.objectiveValue()));
	}

	@Override
	public Class<?> getConnectorClass() {
		return OptThenValidationLogConnector.class;
	}

}
