package test.suite.gips.multilayeredinheritence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gips.multilayeredinheritence.connector.MultiLayeredInheritenceConnector;

public class MultiLayeredInheritenceTest extends AMultiLayeredInheritenceTest {

	static int missedTimeOutSolutionCntr = 0;

	@BeforeEach
	public void resetModel() {
		MultiLayeredInheritenceModelGenerator.reset();
	}

	@Override
	public void callableSetUp() {
		MultiLayeredInheritenceModelGenerator.persistModel(MODEL_PATH);
		con = new MultiLayeredInheritenceConnector(MODEL_PATH);
	}

	@Test
	public void testObjectA() {
		// Setup
		MultiLayeredInheritenceModelGenerator.generateA(1);

		callableSetUp();

		final ILPSolverOutput ret = con.run(OUTPUT_PATH);
		MultiLayeredInheritenceModelGenerator.loadModel(OUTPUT_PATH);
		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(1, ret.objectiveValue());
		assertFalse(ret.validationLog().isNotValid());
	}

}
