package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.Test;

import gipsl.all.build.varmappingsum.sumfreevarattr.api.gips.mapping.N2nMapping;
import gipsl.all.build.varsmappingsum.sumfreevarattr.connector.VarsMappingSumFreeVarAttrConnector;

public class GipslAllBuildVarsMappingSumFreeVarAttrTest extends AGipslAllBuildTest {

	// Setup method

	public void callableSetUp() {
		gen.persistModel(MODEL_PATH);
		con = new VarsMappingSumFreeVarAttrConnector(MODEL_PATH);
	}

	// Actual tests

	@Test
	public void testMap1to1() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(1, Math.abs(ret.objectiveValue()));

		assertFalse(((VarsMappingSumFreeVarAttrConnector) con).getN2nMappings().isEmpty());
		checkConstraints(((VarsMappingSumFreeVarAttrConnector) con).getN2nMappings());
	}

	@Test
	public void testMap2to1() {
		gen.genSubstrateNode("s1", 2);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(2, Math.abs(ret.objectiveValue()));

		assertFalse(((VarsMappingSumFreeVarAttrConnector) con).getN2nMappings().isEmpty());
		checkConstraints(((VarsMappingSumFreeVarAttrConnector) con).getN2nMappings());
	}

	@Test
	public void testMap10to1() {
		gen.genSubstrateNode("s1", 10);
		for (int i = 1; i <= 10; i++) {
			gen.genVirtualNode("v" + i, 1);
		}

		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(10, Math.abs(ret.objectiveValue()));

		assertFalse(((VarsMappingSumFreeVarAttrConnector) con).getN2nMappings().isEmpty());
		checkConstraints(((VarsMappingSumFreeVarAttrConnector) con).getN2nMappings());
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

	private void checkConstraints(final Map<String, N2nMapping> map) {
		map.forEach((k, m) -> {
			final N2nMapping mapping = m;
			assertTrue(getVarValFromMapping(mapping, "u") >= 0);
			assertTrue(getVarValFromMapping(mapping, "v") >= 0);
			assertTrue(getVarValFromMapping(mapping, "w") >= 0);
			assertTrue(getVarValFromMapping(mapping, "x") >= 0);
			assertTrue(getVarValFromMapping(mapping, "y") >= 0);
		});

		// Check the global constraint
		double sumVWX = 0;
		for (final N2nMapping m : map.values()) {
			sumVWX += getVarValFromMapping(m, "u");
			sumVWX += getVarValFromMapping(m, "v");
			sumVWX += getVarValFromMapping(m, "w");
			sumVWX += getVarValFromMapping(m, "x");
			sumVWX += getVarValFromMapping(m, "y");
			sumVWX += m.getMatch().getVnode().getResourceDemand();
			sumVWX += 1;
		}
		assertEquals(73, sumVWX);
	}

}
