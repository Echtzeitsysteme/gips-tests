package test.suite.gips.ilp.timeout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.emoflon.gips.core.api.TimeoutException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import gips.ilp.timeout.api.gips.TimeoutGipsAPI;
import gips.ilp.timeout.connector.TimeOutConnector;

/**
 * This test should trigger at least one time out with no solution found. In
 * this case, the solver's implementation must not throw an exception.
 */
public class GipsIBuildlpTimeOutTest extends AGipsIlpTimeOutTest {

	@Override
	public void callableSetUp() {
		IlpTimeOutModelGenerator.persistModel(MODEL_PATH);
		con = new TimeOutConnector(MODEL_PATH);
	}

	@BeforeEach
	public void generateModel() {
		IlpTimeOutModelGenerator.reset();
	}

	private void buildModel(int iterations) {
		for (int i = 1; i <= iterations; i++) {
			IlpTimeOutModelGenerator.generateTrg("t" + i, 1);
			IlpTimeOutModelGenerator.generateSrc("s" + i, 1);
		}
	}

	@AfterAll
	public static void clearModel() {
		IlpTimeOutModelGenerator.reset();
	}

	@ParameterizedTest
	@ValueSource(longs = { 1, 10 })
	public void testTimeoutInMS(long timeoutInMS) {
		buildModel(5000);
		callableSetUp();

		getAPI().getConfig().setBuildTimeLimit(Duration.ofMillis(timeoutInMS));
		assertThrows(TimeoutException.class, () -> getAPI().buildProblem());
	}

	@Test
	public void testTimeLimitInSeconds() {
		callableSetUp();

		var timelimitinS = 242;

		getAPI().getConfig().setBuildTimeLimit(timelimitinS);
		assertEquals(getAPI().getConfig().getBuildTimeLimit().toMillis(), timelimitinS * 1000l, "Time limit");
	}

	@Test
	public void testEnableDisableTimeout() {
		callableSetUp();

		assertFalse(getAPI().getConfig().getBuildTimeLimit().isPositive(), "By default no time limit is set");

		getAPI().getConfig().setBuildTimeLimit(Duration.ofMillis(1));
		assertTrue(getAPI().getConfig().getBuildTimeLimit().isPositive(), "Time limit enabled");

		getAPI().getConfig().setBuildTimeLimit(Duration.ofMillis(0));
		assertFalse(getAPI().getConfig().getBuildTimeLimit().isPositive(), "Time limit disabled");
	}

	@Test
	public void testNoTimeoutWithoutLimit() {
		buildModel(5000);
		callableSetUp();

		getAPI().buildProblem();
	}

	@Test
	public void testNoTimeoutWithoutDisabledLimit() {
		buildModel(5000);
		callableSetUp();

		getAPI().getConfig().setBuildTimeLimit(Duration.ofSeconds(0));
		getAPI().buildProblem();
	}

	@Override
	public Class<?> getConnectorClass() {
		return TimeOutConnector.class;
	}

	private TimeoutGipsAPI getAPI() {
		return ((TimeOutConnector) con).getAPI();
	}

}
