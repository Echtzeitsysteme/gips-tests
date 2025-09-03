package test.suite.gips.sequential.execution;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.Test;

import gips.sequential.execution.connector.SequentialExecutionConnector;
import model.VirtualResourceNode;
import test.suite.gipsl.all.build.AGipslAllBuildTest;

public class GipsSequentialExecutionTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new SequentialExecutionConnector(MODEL_PATH);
	}

	// Actual tests
	// Positive tests

	@Test
	public void testMapSuccessAfterSuccess() {
		gen.genSubstrateNode("s1", 10);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v3", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(2, ret.objectiveValue());

		final VirtualResourceNode v1 = ((SequentialExecutionConnector) con).getVirtualNodeByName("v1");
		assertNotNull(v1.getHost());
		final VirtualResourceNode v3 = ((SequentialExecutionConnector) con).getVirtualNodeByName("v3");
		assertNotNull(v3.getHost());

		gen.genVirtualNode("v2", 2);
		callableSetUp();

		final SolverOutput ret2 = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret2.status());
		assertEquals(3, ret2.objectiveValue());

		final VirtualResourceNode v2 = ((SequentialExecutionConnector) con).getVirtualNodeByName("v2");
		assertNotNull(v2.getHost());
	}

	@Test
	public void testMapSuccessAfterFailure() {
		gen.genSubstrateNode("s1", 10);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.INFEASIBLE, ret.status());

		final VirtualResourceNode v1 = ((SequentialExecutionConnector) con).getVirtualNodeByName("v1");
		assertNull(v1.getHost());

		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final SolverOutput ret2 = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret2.status());
		assertEquals(2, ret2.objectiveValue());

		final VirtualResourceNode v2 = ((SequentialExecutionConnector) con).getVirtualNodeByName("v2");
		assertNotNull(v2.getHost());
	}

	@Test
	public void testMapFailureAfterSuccess() {
		gen.genSubstrateNode("s1", 10);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v3", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());

		final VirtualResourceNode v1 = ((SequentialExecutionConnector) con).getVirtualNodeByName("v1");
		assertNotNull(v1.getHost());
		final VirtualResourceNode v3 = ((SequentialExecutionConnector) con).getVirtualNodeByName("v3");
		assertNotNull(v3.getHost());

		gen.genVirtualNode("v2", 1);
		gen.genVirtualNode("v4", 1);
		callableSetUp();

		final SolverOutput ret2 = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.INFEASIBLE, ret2.status());
	}

	@Test
	public void testMapFailureAfterFailure() {
		gen.genSubstrateNode("s1", 10);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.INFEASIBLE, ret.status());

		final VirtualResourceNode v1 = ((SequentialExecutionConnector) con).getVirtualNodeByName("v1");
		assertNull(v1.getHost());

		gen.genVirtualNode("v2", 1);
		gen.genVirtualNode("v3", 1);
		gen.genVirtualNode("v4", 1);
		callableSetUp();

		final SolverOutput ret2 = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.INFEASIBLE, ret2.status());
	}

	@Override
	public Class<?> getConnectorClass() {
		return SequentialExecutionConnector.class;
	}

}
