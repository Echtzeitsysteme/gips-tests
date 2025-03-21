package test.suite.gipsl.string;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import stringmodel.Root;
import test.suite.gips.AbstractGipsTest;
import test.suite.gips.utils.AConnector;
import test.suite.gipsl.string.utils.StringModelGenerator;

public abstract class AGipslStringTest extends AbstractGipsTest {

	protected AConnector con;
	protected StringModelGenerator gen;
	protected final static String MODEL_PATH = "model.xmi";
	protected final static String OUTPUT_PATH = "output.xmi";

	@BeforeEach
	public void setUp() {
		gen = new StringModelGenerator();
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

	public Root loadModelAfterTest() {
		return gen.loadModel(OUTPUT_PATH);
	}

}
