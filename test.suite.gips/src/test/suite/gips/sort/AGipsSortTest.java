package test.suite.gips.sort;

import test.suite.gips.utils.AConnector;

public abstract class AGipsSortTest {

	protected final static String MODEL_PATH = "model.xmi";
	protected final static String OUTPUT_PATH = "output.xmi";

	protected AConnector con;

	protected abstract void callableSetUp();

}
