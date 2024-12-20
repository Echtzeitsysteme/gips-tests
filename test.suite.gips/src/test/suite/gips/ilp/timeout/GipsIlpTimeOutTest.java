package test.suite.gips.ilp.timeout;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gips.ilp.timeout.connector.TimeOutConnector;
import timeoutmodel.Source;
import timeoutmodel.Target;

/**
 * This test should trigger at least one time out with no solution found. In
 * this case, the solver's implementation must not throw an exception.
 */
public class GipsIlpTimeOutTest extends AGipsIlpTimeOutTest {

	static int missedTimeOutSolutionCntr = 0;

	@BeforeEach
	public void resetModel() {
		IlpTimeOutModelGenerator.reset();
	}

	@Override
	public void callableSetUp() {
		IlpTimeOutModelGenerator.persistModel(MODEL_PATH);
		con = new TimeOutConnector(MODEL_PATH);
	}

	@AfterAll
	public static void verifyAtLeastOneTestWithoutSolution() {
		assertNotEquals(missedTimeOutSolutionCntr, 3,
				"The ILP solver did find at least one solution for all three tests.");
	}

	@Test
	public void test10to10() {
		for (int i = 1; i <= 10; i++) {
			IlpTimeOutModelGenerator.generateTrg("t" + i, 1);
			IlpTimeOutModelGenerator.generateSrc("s" + i, 1);
		}

		callableSetUp();

		// There must not be an exception and status must be TIME_OUT
		final ILPSolverOutput ret = con.run(OUTPUT_PATH);
		IlpTimeOutModelGenerator.loadModel(OUTPUT_PATH);
		assertEquals(ILPSolverStatus.TIME_OUT, ret.status());

		if (ret.solutionCount() == 0) {
			// There must be no embedding at all -> no solution found -> no solution applied
			checkNoMapping();
		} else {
			System.err.println("=> Warning: ILP solver did find a solution.");
			missedTimeOutSolutionCntr++;
		}
	}

	@Test
	public void test100to100() {
		for (int i = 1; i <= 100; i++) {
			IlpTimeOutModelGenerator.generateTrg("t" + i, 1);
			IlpTimeOutModelGenerator.generateSrc("s" + i, 1);
		}

		callableSetUp();

		// There must not be an exception and status must be TIME_OUT
		final ILPSolverOutput ret = con.run(OUTPUT_PATH);
		IlpTimeOutModelGenerator.loadModel(OUTPUT_PATH);
		assertEquals(ILPSolverStatus.TIME_OUT, ret.status());

		if (ret.solutionCount() == 0) {
			// There must be no embedding at all -> no solution found -> no solution applied
			checkNoMapping();
		} else {
			System.err.println("=> Warning: ILP solver did find a solution.");
			missedTimeOutSolutionCntr++;
		}
	}

	@Test
	public void test200to200() {
		for (int i = 1; i <= 200; i++) {
			IlpTimeOutModelGenerator.generateTrg("t" + i, 1);
			IlpTimeOutModelGenerator.generateSrc("s" + i, 1);
		}

		callableSetUp();

		// There must not be an exception and status must be TIME_OUT
		final ILPSolverOutput ret = con.run(OUTPUT_PATH);
		IlpTimeOutModelGenerator.loadModel(OUTPUT_PATH);
		assertEquals(ILPSolverStatus.TIME_OUT, ret.status());

		if (ret.solutionCount() == 0) {
			// There must be no embedding at all -> no solution found -> no solution applied
			checkNoMapping();
		} else {
			System.err.println("=> Warning: ILP solver did find a solution.");
			missedTimeOutSolutionCntr++;
		}
	}

	private void checkNoMapping() {
		IlpTimeOutModelGenerator.getElements().forEach(e -> {
			if (e instanceof Source s) {
				assertNull(s.getHost());
			} else if (e instanceof Target t) {
				assertTrue(t.getSrcs().isEmpty());
				assertEquals(t.getVal(), t.getFree());
			}
		});
	}

	@Override
	public Class<?> getConnectorClass() {
		return TimeOutConnector.class;
	}

}
