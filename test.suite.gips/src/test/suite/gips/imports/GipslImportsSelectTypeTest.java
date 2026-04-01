package test.suite.gips.imports;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.imports.selecttype.api.gips.SelecttypeGipsAPI;
import gipsl.imports.selecttype.api.gips.types.TypeHostExtension;
import gipsl.imports.selecttype.connector.SelectTypeConnector;

public class GipslImportsSelectTypeTest extends AGipslImportsTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new SelectTypeConnector(MODEL_PATH);
	}

	// Actual tests

	// Positive tests

	@Test
	public void testG2SG1on1Yes() {
		gen.genHost("h1", 8);
		gen.genGuest("g1", 4);
		gen.genGuest("g2", 2);
		gen.genSpecialGuest("g3", 1, 1);

		gen.addGuestToHost("g2", "h1");
		gen.addGuestToHost("g3", "h1");
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(1, Math.abs(ret.objectiveValue()));

		assertEquals(3, // g1, g2, g3
				getHostExtension("h1").getValueOfTotalGuests());
		assertEquals(2, // g1, g2
				getHostExtension("h1").getValueOfRegularGuests());
		assertEquals(1, // g3
				getHostExtension("h1").getValueOfSpecialGuests());
		assertEquals(1, // g3
				getHostExtension("h1").getValueOfFamous());
	}

	@Test
	public void testSG3on1Yes() {
		gen.genHost("h1", 8);
		gen.genSpecialGuest("g1", 4, 1);
		gen.genSpecialGuest("g2", 2, 2);
		gen.genSpecialGuest("g3", 1, 4);

		gen.addGuestToHost("g1", "h1");
		gen.addGuestToHost("g2", "h1");
		gen.addGuestToHost("g3", "h1");
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(0, Math.abs(ret.objectiveValue()));

		assertEquals(3, // g1, g2, g3
				getHostExtension("h1").getValueOfTotalGuests());
		assertEquals(0, //
				getHostExtension("h1").getValueOfRegularGuests());
		assertEquals(3, // g1, g2, g3
				getHostExtension("h1").getValueOfSpecialGuests());
		assertEquals(7, // g1, g2, g3
				getHostExtension("h1").getValueOfFamous());
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

		assertEquals(5, // g1, g2, g3, g4, g5
				getHostExtension("h1").getValueOfTotalGuests());
		assertEquals(2, // g1, g2
				getHostExtension("h1").getValueOfRegularGuests());
		assertEquals(3, // g3, g4, g5
				getHostExtension("h1").getValueOfSpecialGuests());
		assertEquals(7, // g3, g4, g5
				getHostExtension("h1").getValueOfFamous());
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

		assertEquals(2, // g1, g2
				getHostExtension("h1").getValueOfTotalGuests());
		assertEquals(1, // g2
				getHostExtension("h1").getValueOfRegularGuests());
		assertEquals(1, // g1
				getHostExtension("h1").getValueOfSpecialGuests());
		assertEquals(1, // g1
				getHostExtension("h1").getValueOfFamous());

		assertEquals(2, // g3, g4
				getHostExtension("h2").getValueOfTotalGuests());
		assertEquals(1, // g4
				getHostExtension("h2").getValueOfRegularGuests());
		assertEquals(1, // g3
				getHostExtension("h2").getValueOfSpecialGuests());
		assertEquals(2, // g2
				getHostExtension("h2").getValueOfFamous());
	}

	@Override
	public Class<?> getConnectorClass() {
		return SelectTypeConnector.class;
	}

	private SelecttypeGipsAPI getAPI() {
		return ((SelectTypeConnector) con).getAPI();
	}

	private Collection<TypeHostExtension> getHostExtensions() {
		return getAPI().getTypeHost().getExtensions();
	}

	private TypeHostExtension getHostExtension(String name) {
		for (var node : getHostExtensions()) {
			if (node.getContext().getName().equals(name)) {
				return node;
			}
		}
		return null;
	}

}
