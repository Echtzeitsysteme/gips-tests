package test.suite.gips.gttermination;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.Iterator;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.emoflon.ibex.common.operational.IMatch;
import org.junit.jupiter.api.Test;

import gips.gttermination.connector.GtTerminationConnector;
import model.Root;
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
		final var interpreter = ((GtTerminationConnector) con).getEmoflonApi().getInterpreter();
		final Iterator<Collection<IMatch>> it = interpreter.getMatches().values().iterator();
		assertTrue(it.hasNext());
		final var matches = it.next();
		assertEquals(2, matches.size());

		// Terminate the GIPS API; this should also terminate the GT interpreter
		((GtTerminationConnector) con).terminate();

		// Remove model elements such that all matches are invalid
		var res = ((GtTerminationConnector) con).getEmoflonApi().getModel().getResources().get(0);
		final Root root = (Root) res.getContents().get(0);
		root.getContainers().clear();

		// Update matches
		interpreter.updateMatches();

		// Now, **two** matches must still be present
		final Iterator<Collection<IMatch>> it2 = interpreter.getMatches().values().iterator();
		assertTrue(it2.hasNext());
		final var matches2 = it2.next();
		assertEquals(2, matches2.size(), "GT engine termination failed.");
	}

}
