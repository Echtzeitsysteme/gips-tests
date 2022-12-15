package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.inheritedtypecontext.connector.InheritedTypeContextConnector;
import model.SubstrateContainer;
import model.SubstrateResourceNode;
import model.VirtualContainer;
import model.VirtualResourceNode;

public class GipslAllBuildInheritedTypeContextTest extends AGipslAllBuildTest {
		
		// Setup method

		public void callableSetUp() {
			gen.persistModel(MODEL_PATH);
			con = new InheritedTypeContextConnector(MODEL_PATH);
		}

		// Actual tests

		@Test
		public void testInfeasible() {
			gen.genSubstrateNode("s1", 1);
			callableSetUp();

			final ILPSolverOutput ret = con.run(OUTPUT_PATH);

			// Result must be infeasible because the constraint is NOT satisfied
			assertEquals(ILPSolverStatus.INFEASIBLE, ret.status());
		}
		
		@Test
		public void testFeasible() {
			gen.genSubstrateNode("s1", 1);
			gen.genVirtualNode("v1", 1);
			
			// Add vNode to guests in sNode
			final VirtualResourceNode vnode = ((VirtualResourceNode) ((VirtualContainer) gen.getContainer("virt")).getVirtualNodes().get(0));
			((SubstrateResourceNode) ((SubstrateContainer) gen.getContainer("sub")).getSubstrateNodes().get(0)).getGuests().add(vnode);
			
			callableSetUp();

			final ILPSolverOutput ret = con.run(OUTPUT_PATH);

			// Result must be feasible/optimal because the constraint IS satisfied
			assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		}
		
}
