package test.suite.gips.multilayeredinheritence;

import org.junit.jupiter.api.Test;

import test.suite.gips.utils.AConnector;

public abstract class AMultiLayeredInheritenceTest {

	protected final static String MODEL_PATH = "model.xmi";
	protected final static String OUTPUT_PATH = "output.xmi";

	protected AConnector con;
	protected AMultiLayeredInheritenceModelGenerator gen;

	protected abstract void runAndVerify(final int objDesVal);

	public abstract void resetModel();

	//
	// Tests with no constraints
	//

	@Test
	public void testObjectANoCnstr() {
		// Setup
		gen.generateA(1);
		runAndVerify(1);
	}

	@Test
	public void testObjectBNoCnstr() {
		// Setup
		gen.generateB(1);
		runAndVerify(1);
	}

	@Test
	public void testObjectCNoCnstr() {
		// Setup
		gen.generateC(1);
		runAndVerify(1);
	}

	@Test
	public void testObjectAANoCnstr() {
		// Setup
		gen.generateA(1);
		gen.generateA(2);
		runAndVerify(2);
	}

	@Test
	public void testObjectABNoCnstr() {
		// Setup
		gen.generateA(1);
		gen.generateB(1);
		runAndVerify(2);
	}

	@Test
	public void testObjectACNoCnstr() {
		// Setup
		gen.generateA(1);
		gen.generateC(1);
		runAndVerify(2);
	}

	@Test
	public void testObjectBBNoCnstr() {
		// Setup
		gen.generateB(1);
		gen.generateB(1);
		runAndVerify(2);
	}

	@Test
	public void testObjectBCNoCnstr() {
		// Setup
		gen.generateB(1);
		gen.generateC(1);
		runAndVerify(2);
	}

	@Test
	public void testObjectABCNoCnstr() {
		// Setup
		gen.generateA(1);
		gen.generateB(1);
		gen.generateC(1);
		runAndVerify(3);
	}

	//
	// Tests with constraint and one node
	//

	@Test
	public void testObjectACnstr() {
		// Setup
		gen.generateA(11);
		runAndVerify(0);
	}

	@Test
	public void testObjectBCnstr() {
		// Setup
		gen.generateB(22);
		runAndVerify(0);
	}

	@Test
	public void testObjectCCnstr() {
		// Setup
		gen.generateC(33);
		runAndVerify(0);
	}

	//
	// Tests with one constraint and two nodes
	//

	@Test
	public void testObjectAACnstr1() {
		// Setup
		gen.generateA(1);
		gen.generateA(11);
		runAndVerify(1);
	}

	@Test
	public void testObjectABCnstr1() {
		// Setup
		gen.generateA(11);
		gen.generateB(1);
		runAndVerify(1);
	}

	@Test
	public void testObjectABCnstr2() {
		// Setup
		gen.generateA(1);
		gen.generateB(22);
		runAndVerify(1);
	}

	@Test
	public void testObjectACCnstr2() {
		// Setup
		gen.generateA(1);
		gen.generateC(33);
		runAndVerify(1);
	}

	@Test
	public void testObjectBCCnstr2() {
		// Setup
		gen.generateB(1);
		gen.generateC(33);
		runAndVerify(1);
	}

	//
	// Tests with one constraint and three nodes
	//

	@Test
	public void testObjectABCCnstr1() {
		// Setup
		gen.generateA(11);
		gen.generateB(1);
		gen.generateC(1);
		runAndVerify(2);
	}

	@Test
	public void testObjectABCCnstr2() {
		// Setup
		gen.generateA(1);
		gen.generateB(22);
		gen.generateC(1);
		runAndVerify(2);
	}

	@Test
	public void testObjectABCCnstr3() {
		// Setup
		gen.generateA(1);
		gen.generateB(1);
		gen.generateC(33);
		runAndVerify(2);
	}

	//
	// Tests with two constraints and three nodes
	//

	@Test
	public void testObjectABCCnstr4() {
		// Setup
		gen.generateA(11);
		gen.generateB(22);
		gen.generateC(1);
		runAndVerify(1);
	}

	@Test
	public void testObjectABCCnstr5() {
		// Setup
		gen.generateA(11);
		gen.generateB(1);
		gen.generateC(33);
		runAndVerify(1);
	}

	@Test
	public void testObjectABCCnstr6() {
		// Setup
		gen.generateA(1);
		gen.generateB(22);
		gen.generateC(33);
		runAndVerify(1);
	}

	//
	// Tests with inherited constraints: Single node
	//

	@Test
	public void testObjectBCnstrOnA() {
		// Setup
		gen.generateB(11);
		runAndVerify(0);
	}

	@Test
	public void testObjectCCnstrOnA() {
		// Setup
		gen.generateC(11);
		runAndVerify(0);
	}

	@Test
	public void testObjectCCnstrOnB() {
		// Setup
		gen.generateC(22);
		runAndVerify(0);
	}

	//
	// Tests with inherited constraints: Multi node
	//

	@Test
	public void testObjectABCCnstrB() {
		// Setup
		gen.generateA(1);
		gen.generateB(11);
		gen.generateC(1);
		runAndVerify(2);
	}

	@Test
	public void testObjectABCCnstrConA() {
		// Setup
		gen.generateA(1);
		gen.generateB(1);
		gen.generateC(11);
		runAndVerify(2);
	}

	@Test
	public void testObjectABCCnstrConB() {
		// Setup
		gen.generateA(1);
		gen.generateB(1);
		gen.generateC(22);
		runAndVerify(2);
	}

}
