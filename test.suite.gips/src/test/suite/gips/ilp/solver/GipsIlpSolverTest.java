package test.suite.gips.ilp.solver;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.PrintStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gips.ilp.solver.connector.SolverConnector;
import test.suite.gips.nullproject.AGipsNullProjectTest;
import test.suite.gips.nullproject.utils.NullModelGenerator;

public class GipsIlpSolverTest extends AGipsNullProjectTest {

	@Override
	@BeforeEach
	protected void callableSetUp() {
		NullModelGenerator.persistModel(MODEL_PATH);
	}

	@Test
	public void givenMultiThreadedEnvironment_whenInit_thenOutputConsoleShouldBeKept() throws InterruptedException {
		final PrintStream out = System.out;
		final PrintStream err = System.err;

		// Using the maximum of available processors to maximize chances
		// of race conditions between the threads
		int numberOfThreads = Runtime.getRuntime().availableProcessors();
		try (ExecutorService service = Executors.newFixedThreadPool(numberOfThreads)) {
			CountDownLatch latch = new CountDownLatch(numberOfThreads);
			
			for (int i = 0; i < numberOfThreads; i++) {
				service.submit(() -> {
					SolverConnector con = new SolverConnector();
					con.init(MODEL_PATH);
					latch.countDown();
				});
			}
			latch.await();
		}
		
		assertEquals(out, System.out);
		assertEquals(err, System.err);
	}

	// Utilities

	@Override
	public Class<?> getConnectorClass() {
		return SolverConnector.class;
	}

}
