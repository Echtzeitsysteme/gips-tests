package test.suite.gips.multilayeredinheritence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.BeforeEach;

import gips.multilayeredinheritence.connector.MultiLayeredInheritenceConnector;

public class MultiLayeredInheritenceTest extends AMultiLayeredInheritenceTest {

	@Override
	@BeforeEach
	public void resetModel() {
		gen = new MultiLayeredInheritenceModelGenerator();
		gen.reset();
	}

	public void callableSetUp() {
		((MultiLayeredInheritenceModelGenerator) gen).persistModel(MODEL_PATH);
		con = new MultiLayeredInheritenceConnector(MODEL_PATH);
	}

	@Override
	protected void runAndVerify(final int objDesVal) {
		if (objDesVal < 0) {
			throw new IllegalArgumentException("Desired objective value < 0.");
		}

		callableSetUp();
		final ILPSolverOutput ret = con.run(OUTPUT_PATH);
		((MultiLayeredInheritenceModelGenerator) gen).loadModel(OUTPUT_PATH);
		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(objDesVal, Math.abs(ret.objectiveValue()));
		assertFalse(ret.validationLog().isNotValid());
	}

	@Override
	public Class<?> getConnectorClass() {
		return MultiLayeredInheritenceConnector.class;
	}

}
