package test.suite.gips.multilayeredinheritence;

import test.suite.gips.utils.AConnector;

public abstract class AMultiLayeredInheritenceTest {

	protected final static String MODEL_PATH = "model.xmi";
	protected final static String OUTPUT_PATH = "output.xmi";

	protected AConnector con;

	protected abstract void callableSetUp();

}
