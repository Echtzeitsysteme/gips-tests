package test.suite.gips.imports;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import test.suite.gips.AbstractGipsTest;
import test.suite.gips.imports.utils.ImportsModelGenerator;
import test.suite.gips.utils.AConnector;

public abstract class AGipslImportsTest extends AbstractGipsTest {

	protected AConnector con;
	protected ImportsModelGenerator gen;
	protected final static String MODEL_PATH = "model.xmi";
	protected final static String OUTPUT_PATH = "output.xmi";

	@BeforeEach
	public void setUp() {
		gen = new ImportsModelGenerator();
	}

	@AfterEach
	public void tearDown() {
		final File in = new File(MODEL_PATH);
		final File out = new File(OUTPUT_PATH);
		in.delete();
		out.delete();
	}

	@AfterEach
	protected void terminateApi() {
		if (con != null) {
			con.terminate();
		}
	}

}
