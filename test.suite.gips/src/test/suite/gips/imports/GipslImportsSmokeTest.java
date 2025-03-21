package test.suite.gips.imports;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gipsl.imports.sub.connector.ImportsSubConnector;
import test.suite.gips.imports.utils.ImportsModelGenerator;

public class GipslImportsSmokeTest extends AGipslImportsTest {

	@Override
	@BeforeEach
	public void setUp() {
		gen = new ImportsModelGenerator();
		gen.persistModel(MODEL_PATH);
		con = new ImportsSubConnector(MODEL_PATH);
	}

	@Test
	public void testCreateOutput() {
		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(0, ret.objectiveValue());
	}

	@Override
	public Class<?> getConnectorClass() {
		return ImportsSubConnector.class;
	}
}
