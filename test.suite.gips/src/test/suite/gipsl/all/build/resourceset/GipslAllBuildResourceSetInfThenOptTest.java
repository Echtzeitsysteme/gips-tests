package test.suite.gipsl.all.build.resourceset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gipsl.all.build.resourceinit.infthenopt.connector.InfThenOptConnector;
import model.Container;
import model.Root;
import model.SubstrateContainer;
import model.SubstrateResourceNode;
import test.suite.gips.utils.AResourceConnector;

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

	@Test
	public void testFirstInfSecondOpt() {
		// Set up in such a way that the ILP solver can not find a valid solution
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);

		ILPSolverOutput ret = con.solve();
		con.apply();

		assertEquals(ILPSolverStatus.INFEASIBLE, ret.status());
		assertFalse(ret.validationLog().isNotValid());

		// Reset the model
		// Delete node and add another one
		gen.deleteSubstrateNode("s1");
		gen.genSubstrateNode("s1", 2);

		ret = con.solve();
		con.apply();

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertFalse(ret.validationLog().isNotValid());
		assertEquals(2, ret.objectiveValue());
	}

	@Test
	public void testFirstInfSecondOptDirectRsManipulation() {
		// Set up in such a way that the ILP solver can not find a valid solution
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);

		ILPSolverOutput ret = con.solve();
		con.apply();

		assertEquals(ILPSolverStatus.INFEASIBLE, ret.status());
		assertFalse(ret.validationLog().isNotValid());

		// Change model
		final Resource res = ((InfThenOptConnector) con).getResourceSet().getResources().get(0);
		final List<Container> sContainers = ((Root) res.getContents().get(0)).getContainers().stream()
				.filter(c -> (c instanceof SubstrateContainer)).toList();
		final SubstrateContainer sCntr = (SubstrateContainer) sContainers.get(0);
		final SubstrateResourceNode sNode = (SubstrateResourceNode) sCntr.getSubstrateNodes().get(0);
		sNode.setResourceAmountAvailable(2);
		sNode.setResourceAmountTotal(2);

		ret = con.solve();
		con.apply();

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertFalse(ret.validationLog().isNotValid());
		assertEquals(2, ret.objectiveValue());
	}

}
