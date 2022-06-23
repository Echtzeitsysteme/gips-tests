package test.suite.gipsl.all.build;

import org.junit.jupiter.api.Test;

import gipsl.all.build.implication.connector.ImplicationConnector;
import test.suite.gips.utils.GipsTestUtils;

public class GipslAllBuildImplicationTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new ImplicationConnector(MODEL_PATH);
	}

	// Actual tests

	@Test
	public void testMap2to1() {
		// TODO
		GipsTestUtils.failNotImplemented();
	}

}
