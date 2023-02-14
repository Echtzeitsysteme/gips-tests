package test.suite.gips.scheduling;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gips.edf.connector.EdfConnector;
import test.suite.gips.scheduling.utils.SchedulingModelGenerator;
import test.suite.gips.scheduling.utils.SchedulingValidator;

public class GipsEdfExerciseTest extends AGipsSchedulingTest {

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

	@Test
	public void testTask3_1() {
		setUpTask3_1();

		callableSetUp();
		run();
		SchedulingValidator.verify(99 + 6 - 1);
		SchedulingValidator.validateEdf();
		// TODO: Check with exercise solution
	}

	// Utility methods

	private void run() {
		final ILPSolverOutput ret = con.run(OUTPUT_PATH);
		SchedulingModelGenerator.loadModel(OUTPUT_PATH);
		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
	}

	private void setUpTask3_1() {
		// Slots
		// Task says that 99 slots should be used but to make it feasible, we give the
		// problem 150 and only check the first 99 later on
		SchedulingModelGenerator.genSlots(150);

		// Tasks
		// array[0] = deadline, array[1] = duration
		final Map<Integer, int[]> taskInput = new HashMap<>();
		// Task 1
		for (int i = 1; i <= 34; i++) {
			taskInput.put(100 + i, new int[] { (i * 3), 1 });
		}
		// Task 2
		taskInput.put(201, new int[] { 55, 4 });
		taskInput.put(202, new int[] { 55 * 2, 4 });
		// Task 3
		taskInput.put(301, new int[] { 86, 5 });
		// Task 4
		for (int i = 1; i <= 6; i++) {
			taskInput.put(400 + i, new int[] { 16 * i, 4 });
		}
		// Task 5
		taskInput.put(501, new int[] { 63, 15 });
		taskInput.put(502, new int[] { 63 * 2, 15 });
		// Task 6 - second instance will be skipped because it can't be checked in first
		// 99 slots
		taskInput.put(601, new int[] { 88, 3 });

		// Translate map to actual tasks
		taskInput.forEach((id, data) -> {
			SchedulingModelGenerator.genTask(id, data[1], data[0]);
		});
	}

}
