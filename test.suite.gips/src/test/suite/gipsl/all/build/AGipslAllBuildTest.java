package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import test.suite.gips.AbstractGipsTest;
import test.suite.gips.utils.AConnector;
import test.suite.gipsl.all.build.utils.AllBuildModelGenerator;

public abstract class AGipslAllBuildTest extends AbstractGipsTest {

	protected AConnector con;
	protected AllBuildModelGenerator gen;
	protected final static String MODEL_PATH = "model.xmi";
	protected final static String OUTPUT_PATH = "output.xmi";
	protected final static double DELTA = 0.000_0001;

	@BeforeEach
	public void setUp() {
		gen = new AllBuildModelGenerator();
	}

	@AfterEach
	public void tearDown() {
		final File in = new File(MODEL_PATH);
		final File out = new File(OUTPUT_PATH);
		in.delete();
		out.delete();
	}

	@Deprecated
	public void checkIfFileExists() {
		final File f = new File(OUTPUT_PATH);
		assertTrue(f.exists() && !f.isDirectory());
	}

	@AfterEach
	protected void terminateApi() {
		if (con != null) {
			con.terminate();
		}
	}

}
