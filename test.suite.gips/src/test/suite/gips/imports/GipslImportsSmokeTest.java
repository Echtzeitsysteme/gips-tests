package test.suite.gips.imports;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gipsl.all.build.simple.connector.SimpleConnector;
import test.suite.gips.imports.utils.ImportsModelGenerator;

public class GipslImportsSmokeTest extends AGipslImportsTest {

	@Override
	@BeforeEach
	public void setUp() {
		gen = new ImportsModelGenerator();
		gen.persistModel(MODEL_PATH);
		con = new SimpleConnector(MODEL_PATH);
	}

	@Test
	public void testCreateOutput() {
		final ILPSolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(1, ret.objectiveValue());
	}
}
