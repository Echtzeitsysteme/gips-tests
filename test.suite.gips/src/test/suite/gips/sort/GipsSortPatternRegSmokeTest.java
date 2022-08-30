package test.suite.gips.sort;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emoflon.smartemf.persistence.SmartEMFResourceFactoryImpl;
import org.junit.jupiter.api.Test;

import gips.sort.patternreg.connector.SortPatternRegConnector;
import listmodel.Entry;
import listmodel.ListmodelFactory;
import listmodel.Root;
import test.suite.gips.utils.AConnector;

public class GipsSortPatternRegSmokeTest {

	protected final static String MODEL_PATH = "model.xmi";
	protected final static String OUTPUT_PATH = "output.xmi";

	protected AConnector con;

	private Root root = ListmodelFactory.eINSTANCE.createRoot();

	public void genEntry(final int value, final int id) {
		final Entry entry = ListmodelFactory.eINSTANCE.createEntry();
		entry.setValue(value);
		root.getEntries().add(entry);
	}

	public Root loadModel(final String path) {
		// Workaround: Always use absolute path
		final URI absPath = URI.createFileURI(System.getProperty("user.dir") + "/" + path);

		final ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("xmi", new SmartEMFResourceFactoryImpl(null));
		// ^null is okay if all paths are absolute
		final Resource res = rs.getResource(absPath, true);
		root = (Root) res.getContents().get(0);
		return root;
	}

	public void persistModel(final String path) {
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

	public void callableSetUp() {
		persistModel(MODEL_PATH);
		con = new SortPatternRegConnector(MODEL_PATH);
	}

	@Test
	public void test10EntriesNormal() {
		genNEntries(10);
		callableSetUp();
		con.run(OUTPUT_PATH);
	}

	// Utility methods

	private void genNEntries(final int n) {
		int idCntr = 1;
		for (int i = 1; i <= n; i++) {
			genEntry(i, idCntr++);
		}
	}

}
