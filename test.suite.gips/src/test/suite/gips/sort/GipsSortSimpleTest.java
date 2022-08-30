package test.suite.gips.sort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Iterator;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import gips.sort.connector.SortConnector;
import listmodel.Entry;
import test.suite.gips.sort.utils.SortModelGenerator;

public class GipsSortSimpleTest extends AGipsSortTest {

	@BeforeEach
	public void resetModel() {
		SortModelGenerator.reset();
	}

	@Override
	public void callableSetUp() {
		SortModelGenerator.persistModel(MODEL_PATH);
		con = new SortConnector(MODEL_PATH);
	}

	private void checkOrder(final int expectedNoOfEntries) {
		// Find entry with smallest value (= start)
		int currSmallestVal = Integer.MAX_VALUE;
		Entry e = null;
		final Iterator<Entry> it = SortModelGenerator.getRoot().getEntries().iterator();
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
		SortModelGenerator.genNEntries(2);
		callableSetUp();
		runAndVerifyResult(2);
	}

	@Test
	public void test3EntriesNormal() {
		SortModelGenerator.genNEntries(3);
		callableSetUp();
		runAndVerifyResult(3);
	}

	@Test
	public void test10EntriesNormal() {
		SortModelGenerator.genNEntries(10);
		callableSetUp();
		runAndVerifyResult(10);
	}

	@Test
	public void test100EntriesNormal() {
		SortModelGenerator.genNEntries(100);
		callableSetUp();
		runAndVerifyResult(100);
	}

	@Test
	@Disabled // Runtime
	public void test1000EntriesNormal() {
		SortModelGenerator.genNEntries(1000);
		callableSetUp();
		runAndVerifyResult(1000);
	}

	// Reverse ordered

	@Test
	public void test2EntriesReverse() {
		SortModelGenerator.genNEntriesReverse(2);
		callableSetUp();
		runAndVerifyResult(2);
	}

	@Test
	public void test3EntriesReverse() {
		SortModelGenerator.genNEntriesReverse(3);
		callableSetUp();
		runAndVerifyResult(3);
	}

	@Test
	public void test10EntriesReverse() {
		SortModelGenerator.genNEntriesReverse(10);
		callableSetUp();
		runAndVerifyResult(10);
	}

	@Test
	public void test100EntriesReverse() {
		SortModelGenerator.genNEntriesReverse(100);
		callableSetUp();
		runAndVerifyResult(100);
	}

	@Test
	@Disabled // Runtime
	public void test1000EntriesReverse() {
		SortModelGenerator.genNEntriesReverse(1000);
		callableSetUp();
		runAndVerifyResult(1000);
	}

	// Non-ordered

	@Test
	public void test10EntriesNonOrdered() {
		SortModelGenerator.genEntriesFromArray(new int[] { 0, 1, 4, 6, 9, 10, 2345, 2354566, 7, 5 });
		callableSetUp();
		runAndVerifyResult(10);
	}

	@Test
	public void test20EntriesNonOrdered() {
		SortModelGenerator.genEntriesFromArray(new int[] { 411, 333, 756, 941, 776, 40, 507, 966, 862, 790, 695, 997,
				459, 439, 151, 759, 91, 652, 753, 984 });
		callableSetUp();
		runAndVerifyResult(20);
	}

	@Test
	public void test100EntriesNonOrdered() {
		SortModelGenerator.genEntriesFromArray(new int[] { 482, 566, 335, 66, 316, 230, 588, 271, 551, 884, 829, 643,
				246, 842, 564, 669, 679, 862, 528, 800, 362, 678, 982, 871, 819, 695, 752, 433, 443, 386, 728, 167, 508,
				612, 293, 545, 750, 166, 132, 311, 905, 940, 878, 758, 499, 275, 107, 92, 608, 585, 338, 244, 879, 198,
				18, 493, 991, 950, 846, 824, 16, 606, 374, 204, 400, 345, 419, 732, 666, 253, 491, 52, 601, 478, 488,
				195, 892, 266, 242, 949, 219, 69, 70, 709, 19, 441, 94, 655, 813, 227, 973, 429, 300, 128, 36, 319, 307,
				723, 567, 733 });
		callableSetUp();
		runAndVerifyResult(100);
	}

	// Others

	@Test
	@Disabled // Currently not supported
	public void test20EntriesNonOrderedWithDuplicates() {
		SortModelGenerator
				.genEntriesFromArray(new int[] { 6, 2, 1, 8, 10, 7, 9, 3, 4, 5, 1, 7, 9, 2, 8, 10, 6, 4, 5, 3 });
		callableSetUp();
		runAndVerifyResult(20);
	}

	// Utility methods

	private void runAndVerifyResult(final int expectedNoOfEntries) {
		final ILPSolverOutput ret = con.run(OUTPUT_PATH);
		SortModelGenerator.loadModel(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		checkOrder(expectedNoOfEntries);
	}

}
