package test.suite.gips.sort;

import org.junit.jupiter.api.Test;

import gips.sort.patternreg.connector.SortPatternRegConnector;
import test.suite.gips.sort.utils.SortModelGenerator;
import test.suite.gips.utils.AConnector;

public class GipsSortPatternRegSmokeTest {

	protected final static String MODEL_PATH = "model.xmi";
	protected final static String OUTPUT_PATH = "output.xmi";

	protected AConnector con;

	public void callableSetUp() {
		SortModelGenerator.persistModel(MODEL_PATH);
		con = new SortPatternRegConnector(MODEL_PATH);
	}

	@Test
	public void test10EntriesNormal() {
		SortModelGenerator.genNEntries(10);
		callableSetUp();
		con.run(OUTPUT_PATH);
	}

}
