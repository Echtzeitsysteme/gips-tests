package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.gt.GipsGTMapping;
import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.Test;

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

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
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

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(1, ret.objectiveValue());
		((VarsBooleanBugConnector) con).getN2nMappings().forEach((n, m) -> {
			assertEquals(m.getValue(), getVarValFromMapping(m, "b"), 0.0);
		});
	}

	@Test
	public void testMapRoot() {
		gen.genSubstrateNode("s1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(0, Math.abs(ret.objectiveValue()));
		((VarsBooleanBugConnector) con).getDummyMMappings().forEach((n, m) -> {
			assertEquals(m.getValue(), Math.abs(getVarValFromMapping(m, "x")), 0.0);
		});
	}

	// Utility methods

	private double getVarValFromMapping(final GipsGTMapping<?, ?> mapping, final String varName) {
		if (mapping.getBoundVariableNames().contains(varName)) {
			return mapping.getBoundVariables().get(varName).getValue().doubleValue();
		} else if (mapping.getFreeVariableNames().contains(varName)) {
			return mapping.getFreeVariables().get(varName).getValue().doubleValue();
		}

		throw new IllegalArgumentException("Var with name " + varName + " not found in mapping " + mapping);
	}

	@Override
	public Class<?> getConnectorClass() {
		return VarsBooleanBugConnector.class;
	}

}
