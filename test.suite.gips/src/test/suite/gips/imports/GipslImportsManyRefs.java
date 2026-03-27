package test.suite.gips.imports;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.imports.manyrefs.api.gips.ManyrefsGipsAPI;
import gipsl.imports.manyrefs.api.gips.types.TypeHostExtension;
import gipsl.imports.manyrefs.connector.ManyRefsConnector;

public class GipslImportsManyRefs extends AGipslImportsTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new ManyRefsConnector(MODEL_PATH);
	}

	// Actual tests

	// Positive tests

	@Test
	public void testReferenceWith1on1Yes() {
		gen.genHost("h1", 8);
		gen.genGuest("g1", 4);
		gen.genGuest("g2", 2);
		gen.genGuest("g3", 1);
		gen.addGuestToHost("g1", "h1");
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(2, ret.objectiveValue());

		assertEquals(1, // 1 -> g1
				getHostExtension("h1").getValueOfPreExistingGuests());
		assertEquals(3, // 3 -> g1, g2, g3
				getHostExtension("h1").getValueOfNumberOfGuests());
		assertEquals(4, // 4 -> g1
				getHostExtension("h1").getValueOfPreConsumedResources());
	}

	@Test
	public void testReferenceWith2on1Yes() {
		gen.genHost("h1", 8);
		gen.genGuest("g1", 4);
		gen.genGuest("g2", 2);
		gen.genGuest("g3", 1);
		gen.addGuestToHost("g1", "h1");
		gen.addGuestToHost("g2", "h1");
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(1, ret.objectiveValue());

		assertEquals(2, // 2 -> g1, g2
				getHostExtension("h1").getValueOfPreExistingGuests());
		assertEquals(3, // 3 -> g1, g2, g3
				getHostExtension("h1").getValueOfNumberOfGuests());
		assertEquals(6, // 4 + 2 -> g1, g2
				getHostExtension("h1").getValueOfPreConsumedResources());
	}

	@Test
	public void testReferenceWith3on1Yes() {
		gen.genHost("h1", 8);
		gen.genGuest("g1", 4);
		gen.genGuest("g2", 2);
		gen.genGuest("g3", 1);
		gen.addGuestToHost("g1", "h1");
		gen.addGuestToHost("g2", "h1");
		gen.addGuestToHost("g3", "h1");
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(0, Math.abs(ret.objectiveValue()));

		assertEquals(3, // 2 -> g1, g2, g3
				getHostExtension("h1").getValueOfPreExistingGuests());
		assertEquals(3, // 3 -> g1, g2, g3
				getHostExtension("h1").getValueOfNumberOfGuests());
		assertEquals(7, // 4 + 2 + 1 -> g1, g2, g3
				getHostExtension("h1").getValueOfPreConsumedResources());
	}

	@Test
	public void testReferenceWith10on1Yes() {
		gen.genHost("h1", 10);

		for (var i = 1; i < 10; ++i) {
			gen.genGuest("g" + i, 1);
			gen.addGuestToHost("g" + i, "h1");
		}

		gen.genGuest("g10", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(1, Math.abs(ret.objectiveValue()));

		assertEquals(9, // g1 .. g9
				getHostExtension("h1").getValueOfPreExistingGuests());
		assertEquals(10, // g1 .. g9 + g10
				getHostExtension("h1").getValueOfNumberOfGuests());
		assertEquals(9, // g1 + g2 .. g9
				getHostExtension("h1").getValueOfPreConsumedResources());
		assertEquals(1, // 10 - PreConsumedResources
				getHostExtension("h1").getValueOfAvailableResources());

	}

	@Test
	public void testReferenceWith4on2Yes() {
		gen.genHost("h1", 6);
		gen.genHost("h2", 6);
		gen.genGuest("g1", 4);
		gen.genGuest("g2", 2);
		gen.genGuest("g3", 1);
		gen.genGuest("g4", 1);
		gen.addGuestToHost("g1", "h1");
		gen.addGuestToHost("g2", "h1");
		gen.addGuestToHost("g3", "h2");
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(1, Math.abs(ret.objectiveValue()));

		assertEquals(2, // 2 -> g1, g2
				getHostExtension("h1").getValueOfPreExistingGuests());
		assertEquals(2, // 2 -> g1, g2
				getHostExtension("h1").getValueOfNumberOfGuests());
		assertEquals(6, // 4 + 2 -> g1, g2
				getHostExtension("h1").getValueOfPreConsumedResources());

		assertEquals(1, // 1 -> g3
				getHostExtension("h2").getValueOfPreExistingGuests());
		assertEquals(2, // 2 -> g3, g4
				getHostExtension("h2").getValueOfNumberOfGuests());
		assertEquals(1, // 1 -> g3
				getHostExtension("h2").getValueOfPreConsumedResources());
		assertEquals(1, // 1 -> g3
				getHostExtension("h2").getValueOfPreConsumedResources());
	}

	@Override
	public Class<?> getConnectorClass() {
		return ManyRefsConnector.class;
	}

	private ManyrefsGipsAPI getAPI() {
		return ((ManyRefsConnector) con).getAPI();
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
