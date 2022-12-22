package test.suite.gipsl.all.build.resourceset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gipsl.all.build.resourceinit.infthenopt.connector.InfThenOptConnector;
import test.suite.gips.utils.AResourceConnector;

/**
 * This test should trigger a bug within GIPS that makes all feasible models
 * infeasible if the validation log was invalid once.
 */
public class GipslAllBuildResourceSetInfThenOptTest extends AGipslAllBuildResourceSetTest {

	/**
	 * Connector to the GIPS project with resource set initialization
	 */
	private AResourceConnector con;

	// Setup method

	@BeforeEach
	public void setUp() {
		gen.reset();
		con = new InfThenOptConnector(gen.getResourceSet());
	}

	@Test
	public void testFirstInfeasibleSanityCheck() {
		// Set up in such a way that the ILP solver can not find a valid solution
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);

		final ILPSolverOutput ret = con.solve();
		con.apply();

		assertEquals(ILPSolverStatus.INFEASIBLE, ret.status());
		assertFalse(ret.validationLog().isNotValid());
	}

	@Test
	public void testFirstAndSecondInfeasible() {
		// Set up in such a way that the ILP solver can not find a valid solution
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);

		ILPSolverOutput ret = con.solve();
		con.apply();

		assertEquals(ILPSolverStatus.INFEASIBLE, ret.status());
		assertFalse(ret.validationLog().isNotValid());

		// Reset the model
		gen.reset();
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);

		ret = con.solve();
		con.apply();

		assertEquals(ILPSolverStatus.INFEASIBLE, ret.status());
		assertFalse(ret.validationLog().isNotValid());
	}

}
