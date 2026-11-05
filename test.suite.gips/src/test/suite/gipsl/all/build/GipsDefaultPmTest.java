package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gipsl.all.build.simple.connector.PmDefaultConnector;
import test.suite.gipsl.all.build.utils.AllBuildModelGenerator;

public class GipsDefaultPmTest extends AGipslAllBuildTest {

	@Override
	@BeforeEach
	public void setUp() {
		gen = new AllBuildModelGenerator();
		gen.genSubstrateNode("s", 1);
		gen.genVirtualNode("v", 1);
		gen.persistModel(MODEL_PATH);
		con = new PmDefaultConnector(MODEL_PATH);
	}

	/**
	 * This tests checks if exactly one mapping was selected during the solving
	 * phase. For this to happen, the pattern matcher must be executed by GIPS,
	 * which is not explicitly configured in the corresponding connector class.
	 * Therefore, this test checks if the default behavior of GIPS is to execute the
	 * pattern matcher before building the MILP problem.
	 */
	@Test
	public void testDefaultPatternMatching() {
		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(1, ((PmDefaultConnector) con).getNumberOfSelectedMappings());
		assertEquals(3, ret.objectiveValue());
	}

	@Override
	public Class<?> getConnectorClass() {
		return PmDefaultConnector.class;
	}

}
