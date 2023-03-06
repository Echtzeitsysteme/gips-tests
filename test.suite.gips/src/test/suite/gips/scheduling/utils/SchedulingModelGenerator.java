package test.suite.gips.scheduling.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emoflon.smartemf.persistence.SmartEMFResourceFactoryImpl;

import taskmodel.Root;
import taskmodel.Slot;
import taskmodel.Task;
import taskmodel.TaskmodelFactory;

public class SchedulingModelGenerator {

	private static Root root = TaskmodelFactory.eINSTANCE.createRoot();

	public static void genSlot(final int index) {
		if (index < 0) {
			throw new IllegalArgumentException("Index < 0.");
		}

		final Slot s = TaskmodelFactory.eINSTANCE.createSlot();
		s.setIndex(index);
		root.getSlots().add(s);
	}

	public static void genSlots(final int numberOfSlots) {
		if (numberOfSlots <= 0) {
			throw new IllegalArgumentException("Number of slots was <= 0.");
		}

		for (int i = 1; i <= numberOfSlots; i++) {
			genSlot(i);
		}
	}

	public static void genTask(final int id, final int duration, final int deadline) {
		if (id < 0) {
			throw new IllegalArgumentException("Id was < 0.");
		}

		if (duration < 0 || deadline < 0) {
			throw new IllegalArgumentException("Duration or deadline was < 0.");
		}

		if (duration > deadline) {
			throw new IllegalArgumentException("Duration > deadline, which is impossible.");
		}

		final Task t = TaskmodelFactory.eINSTANCE.createTask();
		t.setId(id);
		t.setDuration(duration);
		t.setDeadline(deadline);
		root.getTasks().add(t);
	}

	public static Root getRoot() {
		return root;
	}

	public static void reset() {
		root = TaskmodelFactory.eINSTANCE.createRoot();
	}

	public static Root loadModel(final String path) {
		// Workaround: Always use absolute path
		final URI absPath = URI.createFileURI(System.getProperty("user.dir") + "/" + path);

		final ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new SmartEMFResourceFactoryImpl(null));
		// ^null is okay if all paths are absolute
		final Resource res = rs.getResource(absPath, true);
		root = (Root) res.getContents().get(0);
		return root;
	}

	public static void persistModel(final String path) {
		// Workaround: Always use absolute path
		final URI absPath = URI.createFileURI(System.getProperty("user.dir") + "/" + path);

		// Create new model for saving
		final ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new SmartEMFResourceFactoryImpl(null));
		// ^null is okay if all paths are absolute
		final Resource r = rs.createResource(absPath);
		// Fetch model contents from eMoflon
		r.getContents().add(root);
		try {
			r.save(null);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public static void setUpTask3_1() {
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
