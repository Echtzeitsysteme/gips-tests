package test.suite.gips.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import collectionmetamodel.generator.CollectionMetamodelGenerator;
import gipsl.collection.sum.connector.CollectionSumConnector;

public class GipsCollectionSumTest extends AGipsCollectionTest {

	private CollectionMetamodelGenerator gen = null;

	@BeforeEach
	public void resetModel() {
		CollectionMetamodelGenerator.reset();
		gen = CollectionMetamodelGenerator.getInstance();
		gen.initModel();
	}

	@Override
	protected void callableSetUp() {
		CollectionMetamodelGenerator.getInstance().persistModel(MODEL_PATH);
		con = new CollectionSumConnector(MODEL_PATH);
	}

	// Actual tests
	// Positive tests

	@Test
	public void testValueOne() {
		gen.genContainer();
		gen.genValue(1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(1, ret.solutionCount());
		assertEquals(1, ret.objectiveValue());
	}

	@Test
	public void testValueOneOne() {
		gen.genContainer();
		gen.genValue(1);
		gen.genValue(1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(1, ret.solutionCount());
		assertEquals(2, ret.objectiveValue());
	}

	@Test
	public void testValueOneTwo() {
		gen.genContainer();
		gen.genValue(1);
		gen.genValue(2);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.OPTIMAL, ret.status());
		assertEquals(1, ret.solutionCount());
		assertEquals(3, ret.objectiveValue());
	}

	// Negative tests

	@Test
	public void testValueSingleZero() {
		gen.genContainer();
		gen.genValue(0);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.INFEASIBLE, ret.status());
	}

	@Test
	public void testValueSingleNegative() {
		gen.genContainer();
		gen.genValue(-1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.INFEASIBLE, ret.status());
	}

	@Test
	public void testValueDoubleNegative() {
		gen.genContainer();
		gen.genValue(-1);
		gen.genValue(-1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.INFEASIBLE, ret.status());
	}

	@Test
	public void testValueOneOfTwoNegative() {
		gen.genContainer();
		gen.genValue(1);
		gen.genValue(-1);
		callableSetUp();

		final SolverOutput ret = con.run(OUTPUT_PATH);
		assertEquals(SolverStatus.INFEASIBLE, ret.status());
	}

	// Utility methods

	@Override
	public Class<?> getConnectorClass() {
		return CollectionSumConnector.class;
	}

}
