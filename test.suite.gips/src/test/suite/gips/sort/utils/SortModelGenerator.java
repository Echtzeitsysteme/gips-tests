package test.suite.gips.sort.utils;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emoflon.smartemf.persistence.SmartEMFResourceFactoryImpl;

import listmodel.Entry;
import listmodel.ListmodelFactory;
import listmodel.Root;

public class SortModelGenerator {

	private static Root root = ListmodelFactory.eINSTANCE.createRoot();

	public static void genEntry(final int value) {
		final Entry entry = ListmodelFactory.eINSTANCE.createEntry();
		entry.setValue(value);
		root.getEntries().add(entry);
	}

	public static void genNEntries(final int n) {
		for (int i = 1; i <= n; i++) {
			genEntry(i);
		}
	}

	public static void genNEntriesReverse(final int n) {
		for (int i = n - 1; i >= 0; i--) {
			genEntry(i);
		}
	}

	public static void genEntriesFromArray(final int[] entries) {
		for (int i = 0; i < entries.length; i++) {
			genEntry(entries[i]);
		}
	}

	public static Root getRoot() {
		return root;
	}

	public static void reset() {
		root = ListmodelFactory.eINSTANCE.createRoot();
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
