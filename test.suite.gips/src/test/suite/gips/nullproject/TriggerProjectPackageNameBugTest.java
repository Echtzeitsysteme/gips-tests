package test.suite.gips.nullproject;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gipsprojectpackagenamebug.connector.ProjectPackageNameBugConnector;
import test.suite.gips.nullproject.utils.NullModelGenerator;

public class TriggerProjectPackageNameBugTest extends AGipsNullProjectTest {

	@BeforeEach
	public void resetModel() {
		NullModelGenerator.reset();
	}

	@Override
	protected void callableSetUp() {
		NullModelGenerator.persistModel(MODEL_PATH);
		con = new ProjectPackageNameBugConnector(MODEL_PATH);
	}

	@Test
	public void checkNoNpe() {
		callableSetUp();

		// Pre-check: Reference in root must be null
		assertNull(NullModelGenerator.getRoot().getSubnode());

		final SolverOutput ret = con.run(OUTPUT_PATH);
		NullModelGenerator.loadModel(MODEL_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(0, Math.abs(ret.objectiveValue()));
	}

	@Override
	public Class<?> getConnectorClass() {
		return ProjectPackageNameBugConnector.class;
	}

}
