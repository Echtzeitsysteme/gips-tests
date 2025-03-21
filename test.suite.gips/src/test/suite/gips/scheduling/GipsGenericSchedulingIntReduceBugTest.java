package test.suite.gips.scheduling;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gips.generic.scheduling.codegenintreducebug.connector.CodeGenIntReduceBugConnector;
import test.suite.gips.scheduling.utils.SchedulingModelGenerator;

public class GipsGenericSchedulingIntReduceBugTest extends AGipsSchedulingTest {

	@BeforeEach
	public void resetModel() {
		SchedulingModelGenerator.reset();
	}

	@Override
	public void callableSetUp() {
		SchedulingModelGenerator.persistModel(MODEL_PATH);
		con = new CodeGenIntReduceBugConnector(MODEL_PATH);
	}

	// Actual test
	
	@Test
	public void test1Task() {
		SchedulingModelGenerator.genTask(1, 0, 1);

		callableSetUp();
		runAndVerifyResult(0);
	}

	// Utility methods

	private void runAndVerifyResult(final int expectedObjective) {
		final SolverOutput ret = con.run(OUTPUT_PATH);
		SchedulingModelGenerator.loadModel(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(expectedObjective, ret.objectiveValue());
	}

	@Override
	public Class<?> getConnectorClass() {
		return CodeGenIntReduceBugConnector.class;
	}

}
