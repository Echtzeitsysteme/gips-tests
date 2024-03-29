package test.suite.gips.nullproject;

import org.junit.jupiter.api.AfterEach;

import test.suite.gips.utils.AConnector;

public abstract class AGipsNullProjectTest {

	protected final static String MODEL_PATH = "model.xmi";
	protected final static String OUTPUT_PATH = "output.xmi";

	protected AConnector con;

	protected abstract void callableSetUp();
	
	@AfterEach
	protected void terminateApi() {
		if (con != null) {
			con.terminate();
		}
	}

}
