package test.suite.gips.scheduling.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import edfmodel.Slot;
import edfmodel.Task;

public class SchedulingValidator {
	
	public static void verify(final int expectedNumberOfMappings) {
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

	public static void verifyTaskDoneUntilDeadline(final int taskId, final int deadline) {
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

	/**
	 * Validates the task mapping for earliest deadline first.
	 */
	public static void validateEdf() {
		// Create a map that contains the number of slots occupied by a specific task
		// This can be used to filter tasks that were finished
		final Map<Task, Integer> runnedTasks = new HashMap<Task, Integer>();
		final Iterator<Task> taskIt = SchedulingModelGenerator.getRoot().getTasks().iterator();
		while (taskIt.hasNext()) {
			final var t = taskIt.next();
			runnedTasks.put(t, 0);
		}

		// Iterate over all slots
		final Iterator<Slot> slotIt = SchedulingModelGenerator.getRoot().getSlots().iterator();
		while (slotIt.hasNext()) {
			// Get tasks that aren't finished yet
			final List<Task> notFinished = new LinkedList<Task>();
			runnedTasks.forEach((t, v) -> {
				if (t.getDuration() > v) {
					notFinished.add(t);
				}
			});

			// Break if all tasks were finished
			if (notFinished.isEmpty()) {
				slotIt.next();
				break;
			}

			// Find non-finished task with earliest deadline
			Task edfTask = notFinished.get(0);
			for (int i = 1; i < notFinished.size(); i++) {
				if (notFinished.get(i).getDeadline() < edfTask.getDeadline()) {
					edfTask = notFinished.get(i);
				}
			}

			// Check if current slot has a mapped task with the same earliest deadline
			// (Note: This must not be the identical task!)
			final var s = slotIt.next();

			// We have to check if the slot is empty because the collection of slots must
			// not be sorted
			if (!s.getRunningtask().isEmpty()) {
				assertEquals(edfTask.getDeadline(), s.getRunningtask().get(0).getDeadline());
				// Update saved value
				runnedTasks.put(edfTask, runnedTasks.remove(edfTask) + 1);
			}
		}
	}

}
