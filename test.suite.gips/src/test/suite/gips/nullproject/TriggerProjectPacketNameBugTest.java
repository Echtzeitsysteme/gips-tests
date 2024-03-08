package test.suite.gips.nullproject;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gips.projectpacketnambug.connector.ProjectPacketNameBugConnector;
import test.suite.gips.nullproject.utils.NullModelGenerator;

public class TriggerProjectPacketNameBugTest extends AGipsNullProjectTest {

	@BeforeEach
	public void resetModel() {
		NullModelGenerator.reset();
	}

	@Override
	protected void callableSetUp() {
		NullModelGenerator.persistModel(MODEL_PATH);
		con = new ProjectPacketNameBugConnector(MODEL_PATH);
	}

	@Test
	public void checkNoNpe() {
		callableSetUp();

		// Pre-check: Reference in root must be null
		assertNull(NullModelGenerator.getRoot().getSubnode());

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);
		NullModelGenerator.loadModel(MODEL_PATH);
		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(0, Math.abs(ret.objectiveValue()));
	}

}
