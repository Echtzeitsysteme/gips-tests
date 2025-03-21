package test.suite.gips.tsp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import citymodel.City;
import gips.tsp.connector.TspConnector;
import test.suite.gips.tsp.utils.TspModelGenerator;

public class GipsTspSimpleTest extends AGipsTspTest {

	@BeforeEach
	public void resetModel() {
		TspModelGenerator.reset();
	}

	@Override
	public void callableSetUp() {
		TspModelGenerator.persistModel(MODEL_PATH);
		con = new TspConnector(MODEL_PATH);
	}

	// Actual tests
	// Already ordered

	@Timeout(10)
	@Test
	public void test1Cities() {
		TspModelGenerator.genCity(0, 0);
		callableSetUp();
		runAndVerifyResult(1);
	}

	@Timeout(10)
	@Test
	public void test2Cities() {
		TspModelGenerator.genCity(0, 0);
		TspModelGenerator.genCity(1, 1);
		callableSetUp();
		runAndVerifyResult(2);
	}

	@Timeout(10)
	@Test
	public void test3Cities() {
		TspModelGenerator.genCity(0, 0);
		TspModelGenerator.genCity(1, 1);
		TspModelGenerator.genCity(2, 2);
		callableSetUp();
		runAndVerifyResult(3);
	}

	@Timeout(10)
	@Test
	public void test4Cities() {
		for (int i = 0; i < 4; i++) {
			TspModelGenerator.genCity(i, i);
		}
		callableSetUp();
		runAndVerifyResult(4);
	}

	@Timeout(10)
	@Test
	public void test10Cities() {
		for (int i = 0; i < 10; i++) {
			TspModelGenerator.genCity(i, i);
		}
		callableSetUp();
		runAndVerifyResult(10);
	}

	// Utility methods

	private void runAndVerifyResult(final int expectedNoOfCities) {
		final SolverOutput ret = con.run(OUTPUT_PATH);
		TspModelGenerator.loadModel(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());

		final City start = TspModelGenerator.getRoot().getCities().get(0);
		assertEquals(expectedNoOfCities, TspModelGenerator.getRoot().getCities().size());

		final Set<City> foundCities = new HashSet<City>();

		City act = start;
		foundCities.add(act);
		int cntr = 0;
		while (cntr < expectedNoOfCities) {
			assertNotNull(act.getNext());
			assertEquals(act, act.getNext().getPrev());
			act = act.getNext();
			// TODO:
			assertTrue(foundCities.add(act) || act.equals(start));
			cntr++;
		}

		assertEquals(expectedNoOfCities, cntr);
	}

}
