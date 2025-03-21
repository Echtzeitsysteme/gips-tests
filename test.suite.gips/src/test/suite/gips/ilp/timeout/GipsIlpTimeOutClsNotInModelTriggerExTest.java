package test.suite.gips.ilp.timeout;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gips.ilp.timeout.clsnotinmodel.connector.TimeOutClsNotInModelConnector;

/**
 * This test should trigger the exception in the new type indexer implementation
 * if one of the types is not constrained.
 */
public class GipsIlpTimeOutClsNotInModelTriggerExTest extends AGipsIlpTimeOutTest {

	@BeforeEach
	public void resetModel() {
		IlpTimeOutModelGenerator.reset();
	}

	@Override
	public void callableSetUp() {
		IlpTimeOutModelGenerator.persistModel(MODEL_PATH);
		con = new TimeOutClsNotInModelConnector(MODEL_PATH);
	}

	@Test
	public void test10to10() {
		for (int i = 1; i <= 10; i++) {
			IlpTimeOutModelGenerator.generateTrg("t" + i, 1);
			IlpTimeOutModelGenerator.generateSrc("s" + i, 1);
		}

		callableSetUp();

		// There must not be an exception when running the test!
		assertNotNull(con.run(OUTPUT_PATH));
	}

	@Override
	public Class<?> getConnectorClass() {
		return TimeOutClsNotInModelConnector.class;
	}

}
