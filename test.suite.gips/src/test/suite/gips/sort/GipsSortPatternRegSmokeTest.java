package test.suite.gips.sort;

import org.junit.jupiter.api.Test;

import gips.sort.patternreg.connector.SortPatternRegConnector;
import test.suite.gips.sort.utils.SortModelGenerator;

public class GipsSortPatternRegSmokeTest extends AGipsSortTest {

	@Override
	public void callableSetUp() {
		SortModelGenerator.persistModel(MODEL_PATH);
		con = new SortPatternRegConnector(MODEL_PATH);
	}

	@Test
	public void testTriggerPatternRegistrationException() {
		SortModelGenerator.genNEntries(3);
		callableSetUp();
		con.run(OUTPUT_PATH);
	}

}
