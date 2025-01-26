package test.suite.gips.scheduling;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gips.generic.scheduling.connector.GenericSchedulingConnector;
import test.suite.gips.scheduling.utils.SchedulingModelGenerator;
import test.suite.gips.scheduling.utils.SchedulingValidator;

public class GipsGenericSchedulingExerciseTest extends AGipsSchedulingTest {

	@BeforeEach
	public void resetModel() {
		SchedulingModelGenerator.reset();
	}

	@Override
	public void callableSetUp() {
		SchedulingModelGenerator.persistModel(MODEL_PATH);
		con = new GenericSchedulingConnector(MODEL_PATH);
	}

	// Actual tests
	// Positive tests

	@Test
	public void testTask3_1() {
		SchedulingModelGenerator.setUpTask3_1();

		callableSetUp();
		run();
		SchedulingValidator.verify(99 + 6 - 1);
	}

	// Utility methods

	private void run() {
		final SolverOutput ret = con.run(OUTPUT_PATH);
		SchedulingModelGenerator.loadModel(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
	}

	@Override
	public Class<?> getConnectorClass() {
		return GenericSchedulingConnector.class;
	}

}
