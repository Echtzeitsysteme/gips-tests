package test.suite.gips.sort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.emoflon.smartemf.persistence.SmartEMFResourceFactoryImpl;
import org.junit.jupiter.api.Disabled;
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

	private void checkOrder(final int expectedNoOfEntries) {
		// Find entry with smallest value (= start)
		int currSmallestVal = Integer.MAX_VALUE;
		Entry e = null;
		final Iterator<Entry> it = root.getEntries().iterator();
		while (it.hasNext()) {
			final Entry itEntry = it.next();
			if (itEntry.getValue() < currSmallestVal) {
				e = itEntry;
				currSmallestVal = e.getValue();
			}
		}

		int entryCounter = 0;
		final HashSet<Integer> foundIds = new HashSet<Integer>();

		// Start from first entry
		int lastVal = e.getValue();
		while (e.getNext() != e) {
			e = e.getNext();
			entryCounter++;
			assertTrue(foundIds.add(e.getValue())); // Currently, no doubled values are supported
			if (lastVal > e.getValue()) {
				throw new AssertionError("Sortation is incorrect.");
			}
			lastVal = e.getValue();
		}

		assertEquals(expectedNoOfEntries - 1, entryCounter);
	}

	// Actual tests
	// Already ordered

	@Test
	public void test2EntriesNormal() {
		genNEntries(2);
		callableSetUp();
		runAndVerifyResult(2);
	}

	@Test
	public void test3EntriesNormal() {
		genNEntries(3);
		callableSetUp();
		runAndVerifyResult(3);
	}

	@Test
	public void test10EntriesNormal() {
		genNEntries(10);
		callableSetUp();
		runAndVerifyResult(10);
	}

	@Test
	public void test100EntriesNormal() {
		genNEntries(100);
		callableSetUp();
		runAndVerifyResult(100);
	}

	@Test
	@Disabled // Runtime
	public void test1000EntriesNormal() {
		genNEntries(1000);
		callableSetUp();
		runAndVerifyResult(1000);
	}

	// Reverse ordered

	@Test
	public void test2EntriesReverse() {
		genNEntriesReverse(2);
		callableSetUp();
		runAndVerifyResult(2);
	}

	@Test
	public void test3EntriesReverse() {
		genNEntriesReverse(3);
		callableSetUp();
		runAndVerifyResult(3);
	}

	@Test
	public void test10EntriesReverse() {
		genNEntriesReverse(10);
		callableSetUp();
		runAndVerifyResult(10);
	}

	@Test
	public void test100EntriesReverse() {
		genNEntriesReverse(100);
		callableSetUp();
		runAndVerifyResult(100);
	}

	@Test
	@Disabled // Runtime
	public void test1000EntriesReverse() {
		genNEntriesReverse(1000);
		callableSetUp();
		runAndVerifyResult(1000);
	}

	// Non-ordered

	@Test
	public void test10EntriesNonOrdered() {
		genEntriesFromArray(new int[] { 0, 1, 4, 6, 9, 10, 2345, 2354566, 7, 5 });
		callableSetUp();
		runAndVerifyResult(10);
	}

	@Test
	public void test20EntriesNonOrdered() {
		genEntriesFromArray(new int[] { 411, 333, 756, 941, 776, 40, 507, 966, 862, 790, 695, 997, 459, 439, 151, 759,
				91, 652, 753, 984 });
		callableSetUp();
		runAndVerifyResult(20);
	}

	@Test
	public void test100EntriesNonOrdered() {
		genEntriesFromArray(new int[] { 482, 566, 335, 66, 316, 230, 588, 271, 551, 884, 829, 643, 246, 842, 564, 669,
				679, 862, 528, 800, 362, 678, 982, 871, 819, 695, 752, 433, 443, 386, 728, 167, 508, 612, 293, 545, 750,
				166, 132, 311, 905, 940, 878, 758, 499, 275, 107, 92, 608, 585, 338, 244, 879, 198, 18, 493, 991, 950,
				846, 824, 16, 606, 374, 204, 400, 345, 419, 732, 666, 253, 491, 52, 601, 478, 488, 195, 892, 266, 242,
				949, 219, 69, 70, 709, 19, 441, 94, 655, 813, 227, 973, 429, 300, 128, 36, 319, 307, 723, 567, 733 });
		callableSetUp();
		runAndVerifyResult(100);
	}

	// Others

	@Test
	@Disabled // Currently not supported
	public void test20EntriesNonOrderedWithDuplicates() {
		genEntriesFromArray(new int[] { 6, 2, 1, 8, 10, 7, 9, 3, 4, 5, 1, 7, 9, 2, 8, 10, 6, 4, 5, 3 });
		callableSetUp();
		runAndVerifyResult(20);
	}

	// Utility methods

	private void genNEntries(final int n) {
		for (int i = 1; i <= n; i++) {
			genEntry(i);
		}
	}

	private void genNEntriesReverse(final int n) {
		for (int i = n - 1; i >= 0; i--) {
			genEntry(i);
		}
	}

	private void genEntriesFromArray(final int[] entries) {
		for (int i = 0; i < entries.length; i++) {
			genEntry(entries[i]);
		}
	}

	private void runAndVerifyResult(final int expectedNoOfEntries) {
		final ILPSolverOutput ret = con.run(OUTPUT_PATH);
		loadModel(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		checkOrder(expectedNoOfEntries);
	}

}
