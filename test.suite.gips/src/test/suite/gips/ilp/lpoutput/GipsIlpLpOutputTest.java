package test.suite.gips.ilp.lpoutput;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gips.ilp.lpoutput.connector.LpOutputConnector;
import test.suite.gips.ilp.timeout.AGipsIlpTimeOutTest;
import test.suite.gips.ilp.timeout.IlpTimeOutModelGenerator;

public class GipsIlpLpOutputTest extends AGipsIlpTimeOutTest {

	private final String lpFilePath = "./test.lp";

	@BeforeEach
	public void resetModel() {
		IlpTimeOutModelGenerator.reset();
		deleteFile();
	}

	@AfterEach
	public void deleteGenTestFile() {
		deleteFile();
	}

	@AfterEach
	protected void terminateApi() {
		if (con != null) {
			con.terminate();
		}
	}

	@Override
	public void callableSetUp() {
		IlpTimeOutModelGenerator.persistModel(MODEL_PATH);
		con = new LpOutputConnector(MODEL_PATH);
	}

	@Test
	public void testLpFileExists() {
		IlpTimeOutModelGenerator.generateTrg("t1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		IlpTimeOutModelGenerator.loadModel(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());

		// Check if file exists
		final File testFile = new File(lpFilePath);
		assertTrue(testFile.exists());
		assertFalse(testFile.isDirectory());
	}

	// Utilities

	private void deleteFile() {
		final File testFile = new File(lpFilePath);
		testFile.delete();
	}

	@Override
	public Class<?> getConnectorClass() {
		return LpOutputConnector.class;
	}

}
