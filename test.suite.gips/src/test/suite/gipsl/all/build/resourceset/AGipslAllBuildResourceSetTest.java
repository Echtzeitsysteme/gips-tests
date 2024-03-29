package test.suite.gipsl.all.build.resourceset;

import org.junit.jupiter.api.AfterEach;

import test.suite.gips.utils.AResourceConnector;
import test.suite.gipsl.all.build.utils.AllBuildResourceSetModelGenerator;

public abstract class AGipslAllBuildResourceSetTest {

	protected AResourceConnector con;
	protected AllBuildResourceSetModelGenerator gen = new AllBuildResourceSetModelGenerator();
	
	@AfterEach
	protected void terminateApi() {
		if (con != null) {
			con.terminate();
		}
	}

}
