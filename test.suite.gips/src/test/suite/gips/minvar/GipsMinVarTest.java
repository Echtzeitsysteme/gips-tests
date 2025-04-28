package test.suite.gips.minvar;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gips.minvariable.connector.MinVariableConnector;
import gips.minvariablemodel.generator.MinVariableModelGenerator;

public class GipsMinVarTest extends AGipsVarMinTest {

	private MinVariableModelGenerator gen = null;

	@BeforeEach
	public void resetModel() {
		MinVariableModelGenerator.reset();
		gen = MinVariableModelGenerator.getInstance();
		gen.initModel();
	}

	@Override
	protected void callableSetUp() {
		MinVariableModelGenerator.getInstance().persistModel(MODEL_PATH);
		con = new MinVariableConnector(MODEL_PATH);
	}

	// Actual tests

	@Test
	public void testOneNodeSolvableOnly() {
		gen.genContextNode(1);
		gen.genNode(1, 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(1, ret.solutionCount());
	}

	@Test
	public void testOneNode_val1() {
		gen.genContextNode(1);
		gen.genNode(1, 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		verifyMinimum(1);
	}
	
	@Test
	public void testOneNode_val42() {
		gen.genContextNode(1);
		gen.genNode(42, 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		verifyMinimum(42);
	}
	
	@Test
	public void testTwoNodes_bothSelected() {
		gen.genContextNode(1);
		gen.genNode(42, 1);
		gen.genNode(73, 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		verifyMinimum(42);
	}

	@Test
	public void testTwoNodes_noneSelected() {
		gen.genContextNode(1);
		gen.genNode(42, 0);
		gen.genNode(73, 0);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		verifyMinimum(0);
	}
	
	@Test
	public void testTwoNodes_firstSelected() {
		gen.genContextNode(1);
		gen.genNode(42, 1);
		gen.genNode(73, 0);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		verifyMinimum(42);
	}
	
	@Test
	public void testTwoNodes_secondSelected() {
		gen.genContextNode(1);
		gen.genNode(42, 0);
		gen.genNode(73, 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		verifyMinimum(73);
	}
	
	// Utility methods

	@Override
	public Class<?> getConnectorClass() {
		return MinVariableConnector.class;
	}

	private void verifyMinimum(final int referenceMin) {
		final int min = ((MinVariableConnector) con).getMinVarValue();
		assertEquals(referenceMin, min);
	}

}
