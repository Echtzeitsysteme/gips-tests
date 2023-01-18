package test.suite.gips.nullproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gips.nullproject.connector.NullProjectConnector;
import test.suite.gips.nullproject.utils.NullModelGenerator;

public class TriggerNullBugTypeIndexerTest extends AGipsNullProjectTest {

	@BeforeEach
	public void resetModel() {
		NullModelGenerator.reset();
	}

	@Override
	protected void callableSetUp() {
		NullModelGenerator.persistModel(MODEL_PATH);
		con = new NullProjectConnector(MODEL_PATH);
	}

	@Test
	public void checkNoNpe() {
		callableSetUp();
		final ILPSolverOutput ret = con.run(OUTPUT_PATH);
		NullModelGenerator.loadModel(MODEL_PATH);
		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(0, ret.objectiveValue());
	}

}
