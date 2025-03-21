package test.suite.gips.scheduling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.gips.core.api.GipsEngineAPI;
import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gips.generic.scheduling.connector.GenericSchedulingConnector;
import test.suite.gips.scheduling.utils.SchedulingModelGenerator;
import test.suite.gips.scheduling.utils.SchedulingValidator;

public class GipsGenericSchedulingSaveTest extends AGipsSchedulingTest {

	@BeforeEach
	public void resetModel() {
		SchedulingModelGenerator.reset();
	}

	@Override
	public void callableSetUp() {
		SchedulingModelGenerator.persistModel(MODEL_PATH);
		con = new GenericSchedulingConnector(MODEL_PATH);
	}

	// Actual test(s)

	/**
	 * 2 tasks with 1 duration each 1 slot
	 */
	@Test
	public void test2Tasks2Slots() {
		SchedulingModelGenerator.genSlots(2);
		SchedulingModelGenerator.genTask(1, 1, 1);
		SchedulingModelGenerator.genTask(2, 1, 2);

		callableSetUp();
		runAndVerifyResult(2);

		// Now, save the model again to another location; this should be possible
		final GipsEngineAPI<?, ?> api = ((GenericSchedulingConnector) con).getApi();
		assertNotNull(api);
		try {
			api.saveResult("./test_" + OUTPUT_PATH);
			assertTrue(Files.exists(Path.of("./test_" + OUTPUT_PATH)));
		} catch (final IOException e) {
			e.printStackTrace();
			Assertions.fail("Second output file could not be written.");
		} finally {
			try {
				Files.delete(Path.of("./test" + OUTPUT_PATH));
			} catch (IOException e) {
			}
		}

		// Check the model of the eMoflon::IBeX API directly
		final var model = api.getEMoflonApp().getModel();
		assertNotNull(model);
		final Resource res = model.getResources().get(0);
		assertNotNull(res);
	}

	// Utility methods

	private void runAndVerifyResult(final int expectedNumberOfMappings) {
		final SolverOutput ret = con.run(OUTPUT_PATH);
		SchedulingModelGenerator.loadModel(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		SchedulingValidator.verify(expectedNumberOfMappings);
	}

	@Override
	public Class<?> getConnectorClass() {
		return GenericSchedulingConnector.class;
	}

}
