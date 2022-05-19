package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gipsl.all.build.simple.connector.SimpleConnector;
import test.suite.gipsl.all.build.utils.AllBuildModelGenerator;

public class GipslAllBuildSmokeTest extends AGipslAllBuildTest {

	@Override
	@BeforeEach
	public void setUp() {
		gen = new AllBuildModelGenerator();
		gen.persistModel(MODEL_PATH);
		con = new SimpleConnector(MODEL_PATH);
	}

	@Test
	public void testCreateOutput() {
		final ILPSolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(0 + 1, ret.objectiveValue());
	}

}
