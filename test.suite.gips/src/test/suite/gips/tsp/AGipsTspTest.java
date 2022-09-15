package test.suite.gips.tsp;

import test.suite.gips.utils.AConnector;

public abstract class AGipsTspTest {

	protected final static String MODEL_PATH = "model.xmi";
	protected final static String OUTPUT_PATH = "output.xmi";

	protected AConnector con;

	protected abstract void callableSetUp();

}
