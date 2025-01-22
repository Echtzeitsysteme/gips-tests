package test.suite.gips.ilp.threads;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gips.ilp.threads.connector.ThreadsConnector;
import test.suite.gips.ilp.timeout.AGipsIlpTimeOutTest;
import test.suite.gips.ilp.timeout.IlpTimeOutModelGenerator;

public class GipsIlpThreadsTest extends AGipsIlpTimeOutTest {

	@BeforeEach
	public void resetModel() {
		IlpTimeOutModelGenerator.reset();
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
		con = new ThreadsConnector(MODEL_PATH);
	}

	@Test
	public void testDefaultSpecNumberOfThreads() {
		// Setup
		IlpTimeOutModelGenerator.generateTrg("t1", 1);
		callableSetUp();
		// No overwriting of the configured number of threads within the GIPSL
		// specification

		assertTrue(((ThreadsConnector) con).getSolverConfigThreadsEnabled());
		assertEquals(4, ((ThreadsConnector) con).getSolverConfigThreads());

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
	}

	@Test
	public void testOverwriteNumberOfThreads() {
		// Setup
		IlpTimeOutModelGenerator.generateTrg("t1", 1);
		callableSetUp();

		// Overwrite the number of threads
		((ThreadsConnector) con).setSolverConfigThreads(7);

		// Actual tests
		assertTrue(((ThreadsConnector) con).getSolverConfigThreadsEnabled());
		assertEquals(7, ((ThreadsConnector) con).getSolverConfigThreads());

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
	}

	// Utilities

	@Override
	public Class<?> getConnectorClass() {
		return ThreadsConnector.class;
	}

}
