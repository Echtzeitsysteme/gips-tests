package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.varsbooleanbug.api.gips.mapping.N2nMapping;
import gipsl.all.build.varsbooleanbug.connector.VarsBooleanBugConnector;

public class GipslAllBuildVarsBooleanBugTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new VarsBooleanBugConnector(MODEL_PATH);
	}

	// Actual tests

	//
	// First mapping
	//

	@Test
	public void testMap1to1() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(1, ret.objectiveValue());
		((VarsBooleanBugConnector) con).getN2nMappings().forEach((n, m) -> {
			assertEquals(1, getVarValFromMapping(m, "b"));
		});
	}

	@Test
	public void testMap1to2() {
		gen.genSubstrateNode("s1", 1);
		gen.genSubstrateNode("s2", 1);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(1, ret.objectiveValue());
		((VarsBooleanBugConnector) con).getN2nMappings().forEach((n, m) -> {
			assertEquals(m.getValue(), getVarValFromMapping(m, "b"), 0.0);
		});
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

}
