package test.suite.gips.ilp.timeout;

import test.suite.gips.utils.AConnector;

public abstract class AGipsIlpTimeOutTest {

	protected final static String MODEL_PATH = "model.xmi";
	protected final static String OUTPUT_PATH = "output.xmi";

	protected AConnector con;

	protected abstract void callableSetUp();

}
