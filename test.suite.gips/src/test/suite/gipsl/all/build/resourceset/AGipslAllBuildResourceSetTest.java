package test.suite.gipsl.all.build.resourceset;

import org.junit.jupiter.api.BeforeEach;

import test.suite.gips.utils.AResourceConnector;
import test.suite.gipsl.all.build.utils.AllBuildModelGenerator;

public abstract class AGipslAllBuildResourceSetTest {

	protected AResourceConnector con;
	protected AllBuildModelGenerator gen;

	@BeforeEach
	public void setUp() {
		gen = new AllBuildModelGenerator();
	}

}
