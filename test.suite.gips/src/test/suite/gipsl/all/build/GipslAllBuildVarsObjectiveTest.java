package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.varsobjective.api.gips.mapping.N2nMapping;
import gipsl.all.build.varsobjective.connector.VarsObjectiveConnector;

public class GipslAllBuildVarsObjectiveTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new VarsObjectiveConnector(MODEL_PATH);
	}

	// Actual tests

	@Test
	public void testMap1to1() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(1338, Math.abs(ret.objectiveValue()));

		checkConstraints(((VarsObjectiveConnector) con).getN2nMappings());
	}

	@Test
	public void testMap2to1() {
		gen.genSubstrateNode("s1", 2);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(1338 * 2, Math.abs(ret.objectiveValue()));

		checkConstraints(((VarsObjectiveConnector) con).getN2nMappings());
	}

	@Test
	public void testMap10to1() {
		gen.genSubstrateNode("s1", 1);
		for (int i = 1; i <= 10; i++) {
			gen.genVirtualNode("v" + i, 1);
		}
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(1338 * 10, Math.abs(ret.objectiveValue()));

		checkConstraints(((VarsObjectiveConnector) con).getN2nMappings());
	}

	@Test
	public void testMap0to1() {
		gen.genSubstrateNode("s1", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(0, Math.abs(ret.objectiveValue()));

		checkConstraints(((VarsObjectiveConnector) con).getN2nMappings());
	}

	// Utility methods

	private double getVarValFromMapping(final N2nMapping mapping, final String varName) {
		if (mapping.getBoundVariableNames().contains(varName)) {
			return mapping.getBoundVariables().get(varName).getValue().doubleValue();
		} else if (mapping.getFreeVariableNames().contains(varName)) {
			return mapping.getFreeVariables().get(varName).getValue().doubleValue();
		}

		throw new IllegalArgumentException("Var with name " + varName + " not found in mapping " + mapping);
	}

	private void checkConstraints(final Map<String, N2nMapping> mappings) {
		mappings.forEach((k, m) -> {
			final N2nMapping mapping = m;
			assertEquals(1337, getVarValFromMapping(mapping, "v"));
		});
	}

}
