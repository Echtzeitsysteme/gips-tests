package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.multiplemappingsonpattern.connector.MultipleMappingsOnPatternConnector;

public class GipslAllBuildMultipleMappingsOnPatternTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new MultipleMappingsOnPatternConnector(MODEL_PATH);
	}

	// Actual tests
	// Positive tests

	@Test
	public void testMap2to1() {
		gen.genSubstrateNode("s1", 10);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(0, ret.objectiveValue());

		final var api = ((MultipleMappingsOnPatternConnector) con).getApi();
		assertFalse(api.getA().getMappings().isEmpty());
		api.getA().getMappings().values().forEach(m -> {
			assertEquals(0, m.getValue());
		});
		assertFalse(api.getB().getMappings().isEmpty());
		api.getB().getMappings().values().forEach(m -> {
			assertEquals(1, m.getValue());
		});
	}

	@Override
	public Class<?> getConnectorClass() {
		return MultipleMappingsOnPatternConnector.class;
	}

}
