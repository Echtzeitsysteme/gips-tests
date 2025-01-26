package test.suite.gipsl.all.build.resourceset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gipsl.all.build.resourceinit.infthenoptobjective.connector.InfThenOptObjectiveConnector;
import model.Container;
import model.Root;
import model.SubstrateContainer;
import model.SubstrateResourceNode;
import model.VirtualContainer;
import model.VirtualResourceNode;
import test.suite.gips.utils.AResourceConnector;

public class GipslAllBuildResourceSetInfThenOptObjectiveTest extends AGipslAllBuildResourceSetTest {

	/**
	 * Connector to the GIPS project with resource set initialization
	 */
	private AResourceConnector con;

	// Setup method

	@BeforeEach
	public void setUp() {
		gen.reset();
		con = new InfThenOptObjectiveConnector(gen.getResourceSet());
	}

	@Test
	public void testFirstInfSecondOpt() {
		// Set up in such a way that the ILP solver can not find a valid solution
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);

		SolverOutput ret = con.solve();
		con.apply();

		assertEquals(SolverStatus.INFEASIBLE, ret.status());
		assertFalse(ret.validationLog().isNotValid());

		// Reset the model
		// Delete node and add another one
		gen.deleteSubstrateNode("s1");
		gen.genSubstrateNode("s1", 2);

		ret = con.solve();
		con.apply();

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertFalse(ret.validationLog().isNotValid());
		assertEquals(2 * 2, ret.objectiveValue());
	}

	@Test
	public void testFirstInfSecondOptDirectRsManipulation() {
		// Set up in such a way that the ILP solver can not find a valid solution
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);

		SolverOutput ret = con.solve();
		con.apply();

		assertEquals(SolverStatus.INFEASIBLE, ret.status());
		assertFalse(ret.validationLog().isNotValid());

		// Change model
		final Resource res = ((InfThenOptObjectiveConnector) con).getResourceSet().getResources().get(0);
		final List<Container> sContainers = ((Root) res.getContents().get(0)).getContainers().stream()
				.filter(c -> (c instanceof SubstrateContainer)).toList();
		final SubstrateContainer sCntr = (SubstrateContainer) sContainers.get(0);
		final SubstrateResourceNode sNode = (SubstrateResourceNode) sCntr.getSubstrateNodes().get(0);
		sNode.setResourceAmountAvailable(2);
		sNode.setResourceAmountTotal(2);

		ret = con.solve();
		con.apply();

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertFalse(ret.validationLog().isNotValid());
		assertEquals(2 * 2, ret.objectiveValue());
	}

	//

	@Test
	public void testFirstInfSecondOptWithNewValueDirectRsManipulation() {
		// Set up in such a way that the ILP solver can not find a valid solution
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);

		SolverOutput ret = con.solve();
		con.apply();

		assertEquals(SolverStatus.INFEASIBLE, ret.status());
		assertFalse(ret.validationLog().isNotValid());

		// Change model
		// Substrate node
		final Resource res = ((InfThenOptObjectiveConnector) con).getResourceSet().getResources().get(0);
		final List<Container> sContainers = ((Root) res.getContents().get(0)).getContainers().stream()
				.filter(c -> (c instanceof SubstrateContainer)).toList();
		final SubstrateContainer sCntr = (SubstrateContainer) sContainers.get(0);
		final SubstrateResourceNode sNode = (SubstrateResourceNode) sCntr.getSubstrateNodes().get(0);
		sNode.setResourceAmountAvailable(5);
		sNode.setResourceAmountTotal(5);

		// Virtual nodes
		final List<Container> vContainers = ((Root) res.getContents().get(0)).getContainers().stream()
				.filter(c -> (c instanceof VirtualContainer)).toList();
		final VirtualContainer vCntr = (VirtualContainer) vContainers.get(0);
		final VirtualResourceNode vNodeA = (VirtualResourceNode) vCntr.getVirtualNodes().get(0);
		final VirtualResourceNode vNodeB = (VirtualResourceNode) vCntr.getVirtualNodes().get(1);
		vNodeA.setResourceDemand(2);
		vNodeB.setResourceDemand(3);

		ret = con.solve();
		con.apply();

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertFalse(ret.validationLog().isNotValid());
		assertEquals(10, ret.objectiveValue());
	}

	@Test
	public void testTwoTimesOptWithNewValueDirectRsManipulation() {
		// Set up in such a way that the ILP solver can not find a valid solution
		gen.genSubstrateNode("s1", 2);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);

		SolverOutput ret = con.solve();
		con.apply();

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(4, ret.objectiveValue());
		assertFalse(ret.validationLog().isNotValid());

		// Change model
		// Substrate node
		final Resource res = ((InfThenOptObjectiveConnector) con).getResourceSet().getResources().get(0);
		final List<Container> sContainers = ((Root) res.getContents().get(0)).getContainers().stream()
				.filter(c -> (c instanceof SubstrateContainer)).toList();
		final SubstrateContainer sCntr = (SubstrateContainer) sContainers.get(0);
		final SubstrateResourceNode sNode = (SubstrateResourceNode) sCntr.getSubstrateNodes().get(0);
		sNode.setResourceAmountAvailable(5);
		sNode.setResourceAmountTotal(5);

		// Virtual nodes
		final List<Container> vContainers = ((Root) res.getContents().get(0)).getContainers().stream()
				.filter(c -> (c instanceof VirtualContainer)).toList();
		final VirtualContainer vCntr = (VirtualContainer) vContainers.get(0);
		final VirtualResourceNode vNodeA = (VirtualResourceNode) vCntr.getVirtualNodes().get(0);
		final VirtualResourceNode vNodeB = (VirtualResourceNode) vCntr.getVirtualNodes().get(1);
		vNodeA.setResourceDemand(2);
		vNodeB.setResourceDemand(3);

		ret = con.solve();
		con.apply();

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertFalse(ret.validationLog().isNotValid());
		assertEquals(10, ret.objectiveValue());
	}

	@Override
	public Class<?> getConnectorClass() {
		return InfThenOptObjectiveConnector.class;
	}

}
