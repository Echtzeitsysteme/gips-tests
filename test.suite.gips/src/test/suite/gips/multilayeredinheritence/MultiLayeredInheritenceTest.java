package test.suite.gips.multilayeredinheritence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gips.multilayeredinheritence.connector.MultiLayeredInheritenceConnector;

public class MultiLayeredInheritenceTest extends AMultiLayeredInheritenceTest {

	@BeforeEach
	public void resetModel() {
		MultiLayeredInheritenceModelGenerator.reset();
	}

	@Override
	public void callableSetUp() {
		MultiLayeredInheritenceModelGenerator.persistModel(MODEL_PATH);
		con = new MultiLayeredInheritenceConnector(MODEL_PATH);
	}

	//
	// Tests with no constraints
	//

	@Test
	public void testObjectANoCnstr() {
		// Setup
		MultiLayeredInheritenceModelGenerator.generateA(1);
		runAndyVerify(1);
	}

	@Test
	public void testObjectBNoCnstr() {
		// Setup
		MultiLayeredInheritenceModelGenerator.generateB(1);
		runAndyVerify(1);
	}

	@Test
	public void testObjectCNoCnstr() {
		// Setup
		MultiLayeredInheritenceModelGenerator.generateC(1);
		runAndyVerify(1);
	}

	@Test
	public void testObjectAANoCnstr() {
		// Setup
		MultiLayeredInheritenceModelGenerator.generateA(1);
		MultiLayeredInheritenceModelGenerator.generateA(2);
		runAndyVerify(2);
	}

	@Test
	public void testObjectABNoCnstr() {
		// Setup
		MultiLayeredInheritenceModelGenerator.generateA(1);
		MultiLayeredInheritenceModelGenerator.generateB(1);
		runAndyVerify(2);
	}

	@Test
	public void testObjectACNoCnstr() {
		// Setup
		MultiLayeredInheritenceModelGenerator.generateA(1);
		MultiLayeredInheritenceModelGenerator.generateC(1);
		runAndyVerify(2);
	}

	@Test
	public void testObjectBBNoCnstr() {
		// Setup
		MultiLayeredInheritenceModelGenerator.generateB(1);
		MultiLayeredInheritenceModelGenerator.generateB(1);
		runAndyVerify(2);
	}

	@Test
	public void testObjectBCNoCnstr() {
		// Setup
		MultiLayeredInheritenceModelGenerator.generateB(1);
		MultiLayeredInheritenceModelGenerator.generateC(1);
		runAndyVerify(2);
	}

	@Test
	public void testObjectABCNoCnstr() {
		// Setup
		MultiLayeredInheritenceModelGenerator.generateA(1);
		MultiLayeredInheritenceModelGenerator.generateB(1);
		MultiLayeredInheritenceModelGenerator.generateC(1);
		runAndyVerify(3);
	}

	//
	// Tests with constraint and one node
	//

	@Test
	public void testObjectACnstr() {
		// Setup
		MultiLayeredInheritenceModelGenerator.generateA(11);
		runAndyVerify(0);
	}

	@Test
	public void testObjectBCnstr() {
		// Setup
		MultiLayeredInheritenceModelGenerator.generateB(22);
		runAndyVerify(0);
	}

	@Test
	public void testObjectCCnstr() {
		// Setup
		MultiLayeredInheritenceModelGenerator.generateC(33);
		runAndyVerify(0);
	}

	//
	// Tests with one constraint and two nodes
	//

	@Test
	public void testObjectAACnstr1() {
		// Setup
		MultiLayeredInheritenceModelGenerator.generateA(1);
		MultiLayeredInheritenceModelGenerator.generateA(11);
		runAndyVerify(1);
	}

	@Test
	public void testObjectABCnstr1() {
		// Setup
		MultiLayeredInheritenceModelGenerator.generateA(11);
		MultiLayeredInheritenceModelGenerator.generateB(1);
		runAndyVerify(1);
	}

	@Test
	public void testObjectABCnstr2() {
		// Setup
		MultiLayeredInheritenceModelGenerator.generateA(1);
		MultiLayeredInheritenceModelGenerator.generateB(22);
		runAndyVerify(1);
	}

	@Test
	public void testObjectACCnstr2() {
		// Setup
		MultiLayeredInheritenceModelGenerator.generateA(1);
		MultiLayeredInheritenceModelGenerator.generateC(33);
		runAndyVerify(1);
	}

	@Test
	public void testObjectBCCnstr2() {
		// Setup
		MultiLayeredInheritenceModelGenerator.generateB(1);
		MultiLayeredInheritenceModelGenerator.generateC(33);
		runAndyVerify(1);
	}

	//
	// Tests with one constraint and three nodes
	//

	@Test
	public void testObjectABCCnstr1() {
		// Setup
		MultiLayeredInheritenceModelGenerator.generateA(11);
		MultiLayeredInheritenceModelGenerator.generateB(1);
		MultiLayeredInheritenceModelGenerator.generateC(1);
		runAndyVerify(2);
	}

	@Test
	public void testObjectABCCnstr2() {
		// Setup
		MultiLayeredInheritenceModelGenerator.generateA(1);
		MultiLayeredInheritenceModelGenerator.generateB(22);
		MultiLayeredInheritenceModelGenerator.generateC(1);
		runAndyVerify(2);
	}

	@Test
	public void testObjectABCCnstr3() {
		// Setup
		MultiLayeredInheritenceModelGenerator.generateA(1);
		MultiLayeredInheritenceModelGenerator.generateB(1);
		MultiLayeredInheritenceModelGenerator.generateC(33);
		runAndyVerify(2);
	}

	//
	// Tests with two constraints and three nodes
	//

	@Test
	public void testObjectABCCnstr4() {
		// Setup
		MultiLayeredInheritenceModelGenerator.generateA(11);
		MultiLayeredInheritenceModelGenerator.generateB(22);
		MultiLayeredInheritenceModelGenerator.generateC(1);
		runAndyVerify(1);
	}

	@Test
	public void testObjectABCCnstr5() {
		// Setup
		MultiLayeredInheritenceModelGenerator.generateA(11);
		MultiLayeredInheritenceModelGenerator.generateB(1);
		MultiLayeredInheritenceModelGenerator.generateC(33);
		runAndyVerify(1);
	}

	@Test
	public void testObjectABCCnstr6() {
		// Setup
		MultiLayeredInheritenceModelGenerator.generateA(1);
		MultiLayeredInheritenceModelGenerator.generateB(22);
		MultiLayeredInheritenceModelGenerator.generateC(33);
		runAndyVerify(1);
	}

	//
	// Tests with inherited constraints: Single node
	//

	@Test
	public void testObjectBCnstrOnA() {
		// Setup
		MultiLayeredInheritenceModelGenerator.generateB(11);
		runAndyVerify(0);
	}

	@Test
	public void testObjectCCnstrOnA() {
		// Setup
		MultiLayeredInheritenceModelGenerator.generateC(11);
		runAndyVerify(0);
	}

	@Test
	public void testObjectCCnstrOnB() {
		// Setup
		MultiLayeredInheritenceModelGenerator.generateC(22);
		runAndyVerify(0);
	}

	//
	// Tests with inherited constraints: Multi node
	//

	@Test
	public void testObjectABCCnstrB() {
		// Setup
		MultiLayeredInheritenceModelGenerator.generateA(1);
		MultiLayeredInheritenceModelGenerator.generateB(11);
		MultiLayeredInheritenceModelGenerator.generateC(1);
		runAndyVerify(2);
	}

	@Test
	public void testObjectABCCnstrConA() {
		// Setup
		MultiLayeredInheritenceModelGenerator.generateA(1);
		MultiLayeredInheritenceModelGenerator.generateB(1);
		MultiLayeredInheritenceModelGenerator.generateC(11);
		runAndyVerify(2);
	}

	@Test
	public void testObjectABCCnstrConB() {
		// Setup
		MultiLayeredInheritenceModelGenerator.generateA(1);
		MultiLayeredInheritenceModelGenerator.generateB(1);
		MultiLayeredInheritenceModelGenerator.generateC(22);
		runAndyVerify(2);
	}

	// Utilities

	protected void runAndyVerify(final int objDesVal) {
		if (objDesVal < 0) {
			throw new IllegalArgumentException("Desired objective value < 0.");
		}

		callableSetUp();
		final ILPSolverOutput ret = con.run(OUTPUT_PATH);
		MultiLayeredInheritenceModelGenerator.loadModel(OUTPUT_PATH);
		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(objDesVal, ret.objectiveValue());
		assertFalse(ret.validationLog().isNotValid());
	}

}
