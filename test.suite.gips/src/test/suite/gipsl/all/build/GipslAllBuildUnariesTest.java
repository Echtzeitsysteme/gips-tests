package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.unaries.api.gips.UnariesGipsAPI;
import gipsl.all.build.unaries.connector.UnariesConnector;

public class GipslAllBuildUnariesTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new UnariesConnector(MODEL_PATH);
	}

	// Actual tests

	@Test
	public void testMap1to1() {
		gen.genSubstrateNode("s1", 3);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status(), "Solver Status");
		assertEquals(1, Math.abs(ret.objectiveValue()), "Objective value");

		// Ensure that at least one mapping was created
		assertFalse(getAPI().getN2n().getMappings().isEmpty());

		getAPI().getN2n().getMappings().values().forEach(mapping -> {
			assertEquals(mapping.getVnode().getResourceDemand() * 10, mapping.getValueOfAbsolute(),
					String.format("Absolute value of mapping %s invalid", mapping.getName()));
			assertEquals(mapping.getValue() * -1, mapping.getValueOfNegation(),
					String.format("Negation value of mapping %s invalid", mapping.getName()));
		});
	}

	@Test
	public void testMap2to1() {
		gen.genSubstrateNode("s1", 3);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 2);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status(), "Solver Status");
		assertEquals(2, Math.abs(ret.objectiveValue()), "Objective value");

		// Ensure that at least one mapping was created
		assertFalse(getAPI().getN2n().getMappings().isEmpty());

		getAPI().getN2n().getMappings().values().forEach(mapping -> {
			assertEquals(mapping.getVnode().getResourceDemand() * 10, mapping.getValueOfAbsolute(),
					String.format("Absolute value of mapping %s invalid", mapping.getName()));
			assertEquals(mapping.getValue() * -1, mapping.getValueOfNegation(),
					String.format("Negation value of mapping %s invalid", mapping.getName()));
		});
	}

	@Test
	public void testMap3to1() {
		gen.genSubstrateNode("s1", 3);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 2);
		gen.genVirtualNode("v3", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status(), "Solver Status");
		assertEquals(2, Math.abs(ret.objectiveValue()), "Objective value");

		// Ensure that at least one mapping was created
		assertFalse(getAPI().getN2n().getMappings().isEmpty());

		getAPI().getN2n().getMappings().values().forEach(mapping -> {
			assertEquals(mapping.getVnode().getResourceDemand() * 10, mapping.getValueOfAbsolute(),
					String.format("Absolute value of mapping %s invalid", mapping.getName()));
			assertEquals(mapping.getValue() * -1, mapping.getValueOfNegation(),
					String.format("Negation value of mapping %s invalid", mapping.getName()));
		});
	}

	@Test
	public void testMap1to3() {
		gen.genSubstrateNode("s1", 1);
		gen.genSubstrateNode("s2", 2);
		gen.genSubstrateNode("s3", 3);
		gen.genVirtualNode("v1", 3);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status(), "Solver Status");
		assertEquals(1, Math.abs(ret.objectiveValue()), "Objective value");

		// Ensure that at least one mapping was created
		assertFalse(getAPI().getN2n().getMappings().isEmpty());

		getAPI().getN2n().getMappings().values().forEach(mapping -> {
			assertEquals(mapping.getVnode().getResourceDemand() * 10, mapping.getValueOfAbsolute(),
					String.format("Absolute value of mapping %s invalid", mapping.getName()));
			assertEquals(mapping.getValue() * -1, mapping.getValueOfNegation(),
					String.format("Negation value of mapping %s invalid", mapping.getName()));
		});
	}

	// Utility methods

	private UnariesGipsAPI getAPI() {
		return ((UnariesConnector) con).getAPI();
	}

	@Override
	public Class<?> getConnectorClass() {
		return UnariesConnector.class;
	}

}
