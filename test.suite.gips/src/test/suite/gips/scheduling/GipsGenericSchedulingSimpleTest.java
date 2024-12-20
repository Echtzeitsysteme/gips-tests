package test.suite.gips.scheduling;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gips.generic.scheduling.connector.GenericSchedulingConnector;
import test.suite.gips.scheduling.utils.SchedulingModelGenerator;
import test.suite.gips.scheduling.utils.SchedulingValidator;

public class GipsGenericSchedulingSimpleTest extends AGipsSchedulingTest {

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

	/**
	 * 2 tasks with 1 duration each 1 slot
	 */
	@Test
	public void test2Tasks2Slots() {
		SchedulingModelGenerator.genSlots(2);
		SchedulingModelGenerator.genTask(1, 1, 1);
		SchedulingModelGenerator.genTask(2, 1, 2);

		callableSetUp();
		runAndVerifyResult(2);
	}

	/**
	 * 2 tasks with 1 duration each 1 slot; deadline is not tight
	 */
	@Test
	public void test2_1Tasks2Slots_easyDeadline() {
		SchedulingModelGenerator.genSlots(2);
		SchedulingModelGenerator.genTask(1, 1, 2);
		SchedulingModelGenerator.genTask(2, 1, 2);

		callableSetUp();
		runAndVerifyResult(2);
	}

	/**
	 * Task must be finished in first slot
	 */
	@Test
	public void test1_1Task10Slots() {
		SchedulingModelGenerator.genSlots(10);
		SchedulingModelGenerator.genTask(1, 1, 1);

		callableSetUp();
		runAndVerifyResult(1);
		SchedulingValidator.verifyTaskDoneUntilDeadline(1, 1);
	}

	/**
	 * Task must be finished in first 5 slots
	 */
	@Test
	public void test1_5Task10Slots() {
		SchedulingModelGenerator.genSlots(10);
		SchedulingModelGenerator.genTask(1, 5, 5);

		callableSetUp();
		runAndVerifyResult(5);
		SchedulingValidator.verifyTaskDoneUntilDeadline(1, 5);
	}

	// Negative tests

	/**
	 * 2 Tasks 1 slot: Not possible
	 */
	@Test
	public void test2_1Tasks1Slot() {
		SchedulingModelGenerator.genSlots(1);
		SchedulingModelGenerator.genTask(1, 1, 2);
		SchedulingModelGenerator.genTask(2, 1, 2);

		callableSetUp();
		runAndInfeasible();
	}

	/**
	 * 1 Task with 10 duration 9 slots: Not possible
	 */
	@Test
	public void test10_1Tasks9Slots() {
		SchedulingModelGenerator.genSlots(9);
		for (int i = 1; i <= 10; i++) {
			SchedulingModelGenerator.genTask(i, 1, 10);
		}

		callableSetUp();
		runAndInfeasible();
	}

	/**
	 * 2 Tasks 2 slot, deadline is both on slot 1: Not possible
	 */
	@Test
	public void test2_1Tasks2Slots_tightDeadline() {
		SchedulingModelGenerator.genSlots(2);
		SchedulingModelGenerator.genTask(1, 1, 1);
		SchedulingModelGenerator.genTask(2, 1, 1);

		callableSetUp();
		runAndInfeasible();
	}

	// Utility methods

	private void runAndVerifyResult(final int expectedNumberOfMappings) {
		final ILPSolverOutput ret = con.run(OUTPUT_PATH);
		SchedulingModelGenerator.loadModel(OUTPUT_PATH);
		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		SchedulingValidator.verify(expectedNumberOfMappings);
	}

	private void runAndInfeasible() {
		final ILPSolverOutput ret = con.run(OUTPUT_PATH);
		SchedulingModelGenerator.loadModel(OUTPUT_PATH);
		assertEquals(ILPSolverStatus.INFEASIBLE, ret.status());
	}

	@Override
	public Class<?> getConnectorClass() {
		return GenericSchedulingConnector.class;
	}

}
