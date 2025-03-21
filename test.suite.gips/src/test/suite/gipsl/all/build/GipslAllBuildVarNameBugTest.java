package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.varnamebug.api.gips.mapping.N2nMapping;
import gipsl.all.build.varnamebug.connector.VarNameBugConnector;

public class GipslAllBuildVarNameBugTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new VarNameBugConnector(MODEL_PATH);
	}

	// Actual tests

	@Test
	public void testMap1to1() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(1, Math.abs(ret.objectiveValue()), DELTA);

		checkConstraints(((VarNameBugConnector) con).getN2nMappings());
	}

	@Test
	public void testMap2to1() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(2, Math.abs(ret.objectiveValue()), DELTA);

		checkConstraints(((VarNameBugConnector) con).getN2nMappings());
	}

	@Test
	public void testMap10to1() {
		gen.genSubstrateNode("s1", 1);
		for (int i = 1; i <= 10; i++) {
			gen.genVirtualNode("v" + i, 1);
		}
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(10, Math.abs(ret.objectiveValue()), DELTA);

		checkConstraints(((VarNameBugConnector) con).getN2nMappings());
	}

	@Test
	public void testMap0to1() {
		gen.genSubstrateNode("s1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(0, Math.abs(ret.objectiveValue()), DELTA);
	}

	@Test
	public void testMap1to0() {
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(SolverStatus.INFEASIBLE, ret.status());
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
			assertTrue(getVarValFromMapping(mapping, "n") >= 1);
		});
	}

	@Override
	public Class<?> getConnectorClass() {
		return VarNameBugConnector.class;
	}

}
