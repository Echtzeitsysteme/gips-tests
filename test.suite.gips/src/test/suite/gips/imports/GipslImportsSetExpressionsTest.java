package test.suite.gips.imports;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.stream.Stream;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.imports.scalarattributes.setexpressions.api.gips.SetexpressionsGipsAPI;
import gipsl.imports.scalarattributes.setexpressions.api.gips.mapping.Guest2hostMapping;
import gipsl.imports.scalarattributes.setexpressions.api.gips.types.TypeGuestExtension;
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
	public void test_G1_H1() {
		gen.genHost("h1", 8);
		gen.genGuest("g1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(1, Math.abs(ret.objectiveValue()));

		assertEquals(1, //
				getGuestExtension("g1").getValueOfDemandSum());

		assertEquals(0, //
				getG2HMappings().filter(m -> m.getValueOfHasSpecialHost()).count());
		assertEquals(0, //
				getG2HMappings().filter(m -> m.getValueOfHasSpecialGuest()).count());
	}

	@Test
	public void test_G1_SG1_H1() {
		gen.genHost("h1", 8);
		gen.genGuest("g1", 1);
		gen.genSpecialGuest("sg1", 3, 2);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(2, Math.abs(ret.objectiveValue()));

		assertEquals(1, //
				getGuestExtension("g1").getValueOfDemandSum());
		assertEquals(3, //
				getGuestExtension("sg1").getValueOfDemandSum());

		assertEquals(0, //
				getG2HMappings().filter(m -> m.getValueOfHasSpecialHost()).count());
		assertEquals(1, //
				getG2HMappings().filter(m -> m.getValueOfHasSpecialGuest()).count());
	}

	@Test
	public void test_SG3_H1() {
		gen.genHost("h1", 9);
		gen.genSpecialGuest("sg1", 2, 2);
		gen.genSpecialGuest("sg2", 4, 2);
		gen.genSpecialGuest("sg3", 3, 2);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(3, Math.abs(ret.objectiveValue()));

		assertEquals(2, //
				getGuestExtension("sg1").getValueOfDemandSum());
		assertEquals(4, //
				getGuestExtension("sg2").getValueOfDemandSum());
		assertEquals(3, //
				getGuestExtension("sg3").getValueOfDemandSum());

		assertEquals(0, //
				getG2HMappings().filter(m -> m.getValueOfHasSpecialHost()).count());
		assertEquals(3, //
				getG2HMappings().filter(m -> m.getValueOfHasSpecialGuest()).count());
	}

	@Test
	public void test_SG3_SH1() {
		gen.genSpecialHost("sh1", 9, 4);
		gen.genSpecialGuest("sg1", 2, 2);
		gen.genSpecialGuest("sg2", 4, 2);
		gen.genSpecialGuest("sg3", 3, 2);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(3, Math.abs(ret.objectiveValue()));

		assertEquals(2, //
				getGuestExtension("sg1").getValueOfDemandSum());
		assertEquals(4, //
				getGuestExtension("sg2").getValueOfDemandSum());
		assertEquals(3, //
				getGuestExtension("sg3").getValueOfDemandSum());

		assertEquals(3, //
				getG2HMappings().filter(m -> m.getValueOfHasSpecialHost()).count());
		assertEquals(3, //
				getG2HMappings().filter(m -> m.getValueOfHasSpecialGuest()).count());
	}

	@Test
	public void test_G2_SG2_SH1() {
		gen.genSpecialHost("sh1", 9, 4);
		gen.genGuest("g1", 1);
		gen.genGuest("g2", 1);
		gen.genSpecialGuest("sg1", 2, 2);
		gen.genSpecialGuest("sg2", 4, 2);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(4, Math.abs(ret.objectiveValue()));

		assertEquals(1, //
				getGuestExtension("g1").getValueOfDemandSum());
		assertEquals(1, //
				getGuestExtension("g2").getValueOfDemandSum());
		assertEquals(2, //
				getGuestExtension("sg1").getValueOfDemandSum());
		assertEquals(4, //
				getGuestExtension("sg2").getValueOfDemandSum());

		assertEquals(4, //
				getG2HMappings().filter(m -> m.getValueOfHasSpecialHost()).count());
		assertEquals(2, //
				getG2HMappings().filter(m -> m.getValueOfHasSpecialGuest()).count());
	}

	@Test
	public void test_SG3_SH3() {
		gen.genSpecialHost("sh1", 5, 4);
		gen.genSpecialHost("sh2", 5, 4);
		gen.genSpecialHost("sh3", 5, 4);
		gen.genSpecialGuest("sg1", 2, 1);
		gen.genSpecialGuest("sg2", 4, 2);
		gen.genSpecialGuest("sg3", 3, 4);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(3, Math.abs(ret.objectiveValue()));

		assertEquals(2, //
				getGuestExtension("sg1").getValueOfDemandSum());
		assertEquals(4, //
				getGuestExtension("sg2").getValueOfDemandSum());
		assertEquals(3, //
				getGuestExtension("sg3").getValueOfDemandSum());

		assertEquals(9, //
				getG2HMappings().filter(m -> m.getValueOfHasSpecialHost()).count());
		assertEquals(9, //
				getG2HMappings().filter(m -> m.getValueOfHasSpecialGuest()).count());
	}

	@Override
	public Class<?> getConnectorClass() {
		return SetExpressionsConnector.class;
	}

	private SetexpressionsGipsAPI getAPI() {
		return ((SetExpressionsConnector) con).getAPI();
	}

	private Collection<TypeGuestExtension> getGuestExtensions() {
		return getAPI().getTypeGuest().getExtensions();
	}

	private Stream<Guest2hostMapping> getG2HMappings() {
		return getAPI().getGuest2host().getMappings().values().stream();
	}

	private TypeGuestExtension getGuestExtension(String name) {
		for (var node : getGuestExtensions()) {
			if (node.getContext().getName().equals(name)) {
				return node;
			}
		}
		return null;
	}

}
