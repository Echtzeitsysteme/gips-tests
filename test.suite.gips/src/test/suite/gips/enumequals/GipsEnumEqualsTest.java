package test.suite.gips.enumequals;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import enummodel.StateContainer;
import gips.enumequals.connector.EnumEqualsConnector;

public class GipsEnumEqualsTest extends AGipsEnumEqualsTest {

	@BeforeEach
	public void resetModel() {
		EnumModelGenerator.reset();
	}

	@Override
	public void callableSetUp() {
		EnumModelGenerator.persistModel(MODEL_PATH);
		con = new EnumEqualsConnector(MODEL_PATH);
	}

	// Actual tests
	@Test
	public void test2Tasks2Slots() {
		EnumModelGenerator.generateStateContainer();
		callableSetUp();
		runAndVerifyResult();
	}

	// Utility methods

	private void runAndVerifyResult() {
		final SolverOutput ret = con.run(OUTPUT_PATH);
		EnumModelGenerator.loadModel(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());

		for (final StateContainer sc : EnumModelGenerator.getRoot().getContainers()) {
			assertTrue(sc.isVisited());
		}
	}

	@Override
	public Class<?> getConnectorClass() {
		return EnumEqualsConnector.class;
	}

}
