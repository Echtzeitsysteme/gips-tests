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
import test.suite.gipsl.all.build.AGipslAllBuildTest;
import test.suite.gipsl.all.build.utils.AllBuildModelGenerator;

public class GipslAllBuildFileInitInfThenOptTest extends AGipslAllBuildTest {

	/**
	 * Connector to the GIPS project
	 */
	private InfThenOptConnector con;

	@BeforeEach
	public void setUpOnce() {
		gen = new AllBuildModelGenerator();
	}

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new InfThenOptConnector(MODEL_PATH);
	}

	@Test
	public void testFirstInfeasibleSanityCheck() {
		// Set up in such a way that the ILP solver can not find a valid solution
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.INFEASIBLE, ret.status());

		// ILP solver itself must decide that the problem is infeasible, not the
		// validation log
		assertFalse(ret.validationLog().isNotValid());
	}

	@Test
	public void testFirstAndSecondInfeasible() {
		// Set up in such a way that the ILP solver can not find a valid solution
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.INFEASIBLE, ret.status());

		// ILP solver itself must decide that the problem is infeasible, not the
		// validation log
		assertFalse(ret.validationLog().isNotValid());

		// Reset the model
		con = new InfThenOptConnector(MODEL_PATH);
		setUpOnce();
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.INFEASIBLE, ret.status());
		assertFalse(ret.validationLog().isNotValid());
	}

	@Test
	public void testFirstInfSecondOptWithReset() {
		// Set up in such a way that the ILP solver can not find a valid solution
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.INFEASIBLE, ret.status());

		// ILP solver itself must decide that the problem is infeasible, not the
		// validation log
		assertFalse(ret.validationLog().isNotValid());

		// Reset the model
		setUpOnce();
		con = new InfThenOptConnector(MODEL_PATH);
		gen.genSubstrateNode("s1", 2);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertFalse(ret.validationLog().isNotValid());
		assertEquals(2, ret.objectiveValue());
	}

	@Test
	public void testFirstInfSecondOptWithoutReset() {
		// Set up in such a way that the ILP solver can not find a valid solution
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.INFEASIBLE, ret.status());

		// ILP solver itself must decide that the problem is infeasible, not the
		// validation log
		assertFalse(ret.validationLog().isNotValid());

		// Change model
		final Resource res = con.getResourceSet().getResources().get(0);
		final List<Container> sContainers = ((Root) res.getContents().get(0)).getContainers().stream()
				.filter(c -> (c instanceof SubstrateContainer)).toList();
		final SubstrateContainer sCntr = (SubstrateContainer) sContainers.get(0);
		final SubstrateResourceNode sNode = (SubstrateResourceNode) sCntr.getSubstrateNodes().get(0);
		sNode.setResourceAmountAvailable(2);
		sNode.setResourceAmountTotal(2);

		ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertFalse(ret.validationLog().isNotValid());
		assertEquals(2, ret.objectiveValue());
	}

	@Override
	public Class<?> getConnectorClass() {
		return InfThenOptConnector.class;
	}

}
