package test.suite.gips.sort;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gipsl.sortpatternmappingbug.connector.SortPatternMappingBugConnector;
import test.suite.gips.sort.utils.SortModelGenerator;

public class GipsSortPatternMappingBugTest extends AGipsSortTest {

	@BeforeEach
	public void resetModel() {
		SortModelGenerator.reset();
	}

	@Override
	public void callableSetUp() {
		SortModelGenerator.persistModel(MODEL_PATH);
		con = new SortPatternMappingBugConnector(MODEL_PATH);
	}

	// Actual tests

	@Test
	public void test1Entry() {
		SortModelGenerator.genNEntries(1);
		callableSetUp();
		final SolverOutput ret = con.run(OUTPUT_PATH);
		SortModelGenerator.loadModel(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(2 * 1, ret.objectiveValue());
	}

	@Test
	public void test2Entries() {
		SortModelGenerator.genNEntries(2);
		callableSetUp();
		final SolverOutput ret = con.run(OUTPUT_PATH);
		SortModelGenerator.loadModel(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(2 * 2, ret.objectiveValue());
	}

	@Test
	public void test10Entries() {
		SortModelGenerator.genNEntries(10);
		callableSetUp();
		final SolverOutput ret = con.run(OUTPUT_PATH);
		SortModelGenerator.loadModel(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(2 * 10, ret.objectiveValue());
	}

	@Override
	public Class<?> getConnectorClass() {
		return SortPatternMappingBugConnector.class;
	}

}
