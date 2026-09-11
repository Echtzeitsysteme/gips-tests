package test.suite.gips.imports;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.imports.scalarattributes.setexpressions.api.gips.SetexpressionsGipsAPI;
import gipsl.imports.scalarattributes.setexpressions.connector.SetExpressionsConnector;

public class GipslImportsSetExpressionsTest extends AGipslImportsTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new SetExpressionsConnector(MODEL_PATH);
	}

	// Actual tests

	// Positive tests

	@Test
	public void testG2SG1on1Yes() {
		gen.genHost("h1", 8);
		gen.genSpecialGuest("g3", 1, 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.INFEASIBLE, ret.status());
		assertEquals(1, Math.abs(ret.objectiveValue()));

	}

	@Test
	public void testSG3on1Yes() {
		gen.genHost("h1", 8);
		gen.genSpecialGuest("g1", 4, 1);
		gen.genSpecialGuest("g2", 2, 2);
		gen.genSpecialGuest("g3", 1, 4);

//		gen.addGuestToHost("g1", "h1");
//		gen.addGuestToHost("g2", "h1");
//		gen.addGuestToHost("g3", "h1");
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(0, Math.abs(ret.objectiveValue()));
	}

	@Test
	public void testG2SG3on1Yes() {
		gen.genHost("h1", 10);
		gen.genGuest("g1", 4);
		gen.genGuest("g2", 2);
		gen.genSpecialGuest("g3", 1, 1);
		gen.genSpecialGuest("g4", 1, 2);
		gen.genSpecialGuest("g5", 1, 4);

		gen.addGuestToHost("g1", "h1");
		gen.addGuestToHost("g2", "h1");
		gen.addGuestToHost("g3", "h1");
		gen.addGuestToHost("g4", "h1");
		gen.addGuestToHost("g5", "h1");
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(0, Math.abs(ret.objectiveValue()));
	}

	@Test
	public void testG2SG2On2Yes() {
		gen.genHost("h1", 6);
		gen.genHost("h2", 6);
		gen.genSpecialGuest("g1", 4, 1);
		gen.genGuest("g2", 2);
		gen.genSpecialGuest("g3", 1, 2);
		gen.genGuest("g4", 1);

		gen.addGuestToHost("g1", "h1");
		gen.addGuestToHost("g2", "h1");
		gen.addGuestToHost("g3", "h2");
		gen.addGuestToHost("g4", "h2");
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(0, Math.abs(ret.objectiveValue()));
	}

	@Override
	public Class<?> getConnectorClass() {
		return SetExpressionsConnector.class;
	}

	private SetexpressionsGipsAPI getAPI() {
		return ((SetExpressionsConnector) con).getAPI();
	}

}
