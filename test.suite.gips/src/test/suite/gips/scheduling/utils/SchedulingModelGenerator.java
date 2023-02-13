package test.suite.gips.scheduling.utils;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emoflon.smartemf.persistence.SmartEMFResourceFactoryImpl;

import edfmodel.EdfmodelFactory;
import edfmodel.Root;
import edfmodel.Slot;
import edfmodel.Task;

public class SchedulingModelGenerator {

	private static Root root = EdfmodelFactory.eINSTANCE.createRoot();

	public static void genSlot(final int index) {
		if (index < 0) {
			throw new IllegalArgumentException("Index < 0.");
		}

		final Slot s = EdfmodelFactory.eINSTANCE.createSlot();
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

		final Task t = EdfmodelFactory.eINSTANCE.createTask();
		t.setId(id);
		t.setDuration(duration);
		t.setDeadline(deadline);
		root.getTasks().add(t);
	}

	public static Root getRoot() {
		return root;
	}

	public static void reset() {
		root = EdfmodelFactory.eINSTANCE.createRoot();
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

}
