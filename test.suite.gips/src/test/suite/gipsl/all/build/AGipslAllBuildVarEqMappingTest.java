package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.emoflon.gips.core.gt.GTMapping;
import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public abstract class AGipslAllBuildVarEqMappingTest extends AGipslAllBuildTest {

	/**
	 * Must initiate the respective connector with the persisted model.
	 */
	public abstract void callableSetUp();

	/**
	 * Implements the checks for the specific test implementation. If the parameter
	 * is set to true, the variable is expected to be equal to zero.
	 * 
	 * @param exptectedZero If the parameter is set to true, the variable is
	 *                      expected to be equal to zero.
	 */
	protected abstract void runChecks(final boolean exptectedZero);

	@AfterEach
	protected void terminateApi() {
		if (con != null) {
			con.terminate();
		}
	}

	// Actual tests
	// Positive tests

	@Test
	public void testMap1to1() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(1, Math.abs(ret.objectiveValue()), DELTA);

		runChecks(false);
	}

	@Test
	public void testMap2to1() {
		gen.genSubstrateNode("s1", 2);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(2, Math.abs(ret.objectiveValue()), DELTA);

		runChecks(false);
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
		assertEquals(10, Math.abs(ret.objectiveValue()), DELTA);

		runChecks(false);
	}

	// Negative tests

	@Test
	public void testMap1to1NotPoss() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 2);
		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(0, Math.abs(ret.objectiveValue()), DELTA);

		runChecks(true);
	}

	// Utility methods

	private double getVarValFromMapping(final GTMapping<?, ?> mapping, final String varName) {
		if (mapping.getBoundVariableNames().contains(varName)) {
			return mapping.getBoundVariables().get(varName).getValue().doubleValue();
		} else if (mapping.getFreeVariableNames().contains(varName)) {
			return mapping.getFreeVariables().get(varName).getValue().doubleValue();
		}

		throw new IllegalArgumentException("Var with name " + varName + " not found in mapping " + mapping);
	}

	protected void checkConstraints(final Map<String, GTMapping<?, ?>> mappings, final boolean expectedZero) {
		mappings.forEach((k, m) -> {
			if (expectedZero) {
				assertEquals(0, getVarValFromMapping(m, "v"));
			} else {
				assertTrue(getVarValFromMapping(m, "v") >= 1);
			}
		});
	}

}
