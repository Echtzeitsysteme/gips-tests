package test.suite.gips.gttermination;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.emoflon.ibex.common.operational.IMatch;
import org.emoflon.ibex.gt.engine.GraphTransformationInterpreter;
import org.emoflon.ibex.gt.hipe.runtime.HiPEGTEngine;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import gips.gttermination.connector.GtTerminationConnector;
import hipe.engine.HiPEContentAdapter;
import test.suite.gipsl.all.build.AGipslAllBuildTest;

public class GipsGtTerminationTest extends AGipslAllBuildTest {

	// Setup method/utilities

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new GtTerminationConnector(MODEL_PATH);
	}

	@Override
	protected void terminateApi() {
		// Disable the automatic termination of the GIPS API of the super class
	}

	// Actual tests

	@Timeout(value = 2, unit = TimeUnit.SECONDS)
	@Test
	public void testGtTermination() {
		gen.genSubstrateNode("s1", 2);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());

		// Get the interpreter and check number of matches before the termination
		// (must be 2)
		final GraphTransformationInterpreter interpreter = ((GtTerminationConnector) con).getEmoflonApi()
				.getInterpreter();
		final Iterator<Collection<IMatch>> it = interpreter.getMatches().values().iterator();
		assertTrue(it.hasNext());
		final var matches = it.next();
		assertEquals(2, matches.size());

		// Terminate the GIPS API; this should also terminate the GT interpreter
		con.terminate();

		// Check if actual GT interpreter (in this case HiPE) was terminated
		//
		// Unfortunately, there is no method/field we can access to check if HiPE was
		// terminated
		boolean wasHipeTerminated = false;
		try {
			// Get the eMoflon::IBeX API object from the GIPS test project connector
			final var emoflonApi = ((GtTerminationConnector) con).getEmoflonApi();

			// The IBeX API has a field called "contextPatternInterpreter" which contains
			// the HiPEGTEngine object. It is not publicly available, but we want this
			// object -> use reflection to get it's value.
			final Field contextPatternInterpreterField = GraphTransformationInterpreter.class
					.getDeclaredField("contextPatternInterpreter");
			contextPatternInterpreterField.setAccessible(true);
			final HiPEGTEngine hipeEngine = (HiPEGTEngine) contextPatternInterpreterField
					.get(emoflonApi.getInterpreter());

			// The HiPE engine object has a field called "adapter" -> reflection again
			final Field adapterField = HiPEGTEngine.class.getDeclaredField("adapter");
			adapterField.setAccessible(true);
			final HiPEContentAdapter contentAdapter = (HiPEContentAdapter) adapterField.get(hipeEngine);

			// The HiPEContentAdapter object contains a field "terminated" -> reflection
			final Field hipeTerminatedField = HiPEContentAdapter.class.getDeclaredField("terminated");
			hipeTerminatedField.setAccessible(true);
			wasHipeTerminated = hipeTerminatedField.getBoolean(contentAdapter);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			// If any of the exceptions above occurred during the test, the test must fail.
			Assert.fail(e.getMessage());
		}

		// HiPE must be properly terminated, i.e., the field must have been set to
		// `true`.
		assertTrue(wasHipeTerminated);
	}

	@Override
	public Class<?> getConnectorClass() {
		return GtTerminationConnector.class;
	}

}
