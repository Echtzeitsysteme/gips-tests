package test.suite.gips.sort;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Iterator;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.emoflon.smartemf.persistence.SmartEMFResourceFactoryImpl;
import org.junit.jupiter.api.Test;

import gips.sort.connector.SortConnector;
import listmodel.Entry;
import listmodel.ListmodelFactory;
import listmodel.Root;
import test.suite.gips.utils.AConnector;

public class GipsSortSimpleTest {

	protected final static String MODEL_PATH = "model.xmi";
	protected final static String OUTPUT_PATH = "output.xmi";

	protected AConnector con;

	private Root root = ListmodelFactory.eINSTANCE.createRoot();

	public void genEntry(final int value) {
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
		con = new SortConnector(MODEL_PATH);
	}

	private void checkOrder() {
		// Find entry with smallest value (= start)
		int currSmallestVal = Integer.MAX_VALUE;
		Entry e = null;
		final Iterator<Entry> it = root.getEntries().iterator();
		while (it.hasNext()) {
			final Entry itEntry = it.next();
			if (itEntry.getValue() < currSmallestVal) {
				e = itEntry;
			}
		}

		// Start from first entry
		int lastVal = e.getValue();
		while (e.getNext() != e) {
			e = e.getNext();
			if (lastVal > e.getValue()) {
				throw new AssertionError("Sortation is incorrect.");
			}
			lastVal = e.getValue();
		}
	}

	// Actual tests

	@Test
	public void test2Entries() {
		genNEntries(2);
		callableSetUp();
		runAndVerifyResult();
	}

	@Test
	public void test10Entries() {
		genNEntries(10);
		callableSetUp();
		runAndVerifyResult();
	}

	@Test
	public void test100Entries() {
		genNEntries(100);
		callableSetUp();
		runAndVerifyResult();
	}

	@Test
	public void test1000Entries() {
		genNEntries(100);
		callableSetUp();
		runAndVerifyResult();
	}

	private void genNEntries(final int n) {
		for (int i = 1; i <= n; i++) {
			genEntry(i);
		}
	}

	private void runAndVerifyResult() {
		final ILPSolverOutput ret = con.run(OUTPUT_PATH);
		loadModel(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		checkOrder();
	}

}