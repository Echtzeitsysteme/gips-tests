package test.suite.gips.scheduling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edfmodel.Slot;
import edfmodel.Task;
import gips.edf.connector.EdfConnector;
import test.suite.gips.scheduling.utils.SchedulingModelGenerator;

public class GipsEdfSimpleTest extends AGipsSchedulingTest {

	@BeforeEach
	public void resetModel() {
		SchedulingModelGenerator.reset();
	}

	@Override
	public void callableSetUp() {
		SchedulingModelGenerator.persistModel(MODEL_PATH);
		con = new EdfConnector(MODEL_PATH);
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
		verifyTaskDoneUntilDeadline(1, 1);
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
		verifyTaskDoneUntilDeadline(1, 5);
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
		verify(expectedNumberOfMappings);
	}

	private void runAndInfeasible() {
		final ILPSolverOutput ret = con.run(OUTPUT_PATH);
		SchedulingModelGenerator.loadModel(OUTPUT_PATH);
		assertEquals(ILPSolverStatus.INFEASIBLE, ret.status());
	}

	private void verify(final int expectedNumberOfMappings) {
		// Iterate over all tasks
		final Iterator<Task> taskIt = SchedulingModelGenerator.getRoot().getTasks().iterator();
		while (taskIt.hasNext()) {
			final Task t = taskIt.next();
			assertFalse(t.getRunningon().isEmpty());
			assertEquals(t.getDuration(), t.getRunningon().size());
			for (final Slot s : t.getRunningon()) {
				assertTrue(t.getDeadline() >= s.getIndex());
			}
		}

		// Iterate over all slots
		int mappingCntr = 0;
		final Iterator<Slot> slotIt = SchedulingModelGenerator.getRoot().getSlots().iterator();
		while (slotIt.hasNext()) {
			final Slot s = slotIt.next();
			assertTrue(s.getRunningtask().size() <= 1);
			mappingCntr += s.getRunningtask().size();
		}

		assertEquals(expectedNumberOfMappings, mappingCntr);
	}

	private void verifyTaskDoneUntilDeadline(final int taskId, final int deadline) {
		final Iterator<Task> taskIt = SchedulingModelGenerator.getRoot().getTasks().iterator();
		while (taskIt.hasNext()) {
			final var t = taskIt.next();
			if (t.getId() == taskId) {
				final var slotIt = t.getRunningon().iterator();
				while (slotIt.hasNext()) {
					final var slot = slotIt.next();
					assertTrue(deadline >= slot.getIndex());
				}
			}
		}
	}

}
