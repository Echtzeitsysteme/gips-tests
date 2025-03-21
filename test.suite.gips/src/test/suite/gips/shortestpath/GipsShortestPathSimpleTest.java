package test.suite.gips.shortestpath;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import shortestpath.connector.ShortestPathConnector;

public class GipsShortestPathSimpleTest extends AGipsShortestPathTest {

	@Override
	protected void callableSetUp() {
		con = new ShortestPathConnector(MODEL_PATH);
	}

	@Override
	public Class<?> getConnectorClass() {
		return ShortestPathConnector.class;
	}

	@AfterAll
	public static void resetModelPath() {
		MODEL_PATH = "model.xmi";
	}

	@Test
	public void testUnidirectional() {
		MODEL_PATH = "../genericgraphmetamodel/instances/1_unidirectional.xmi";
		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 1, 2, 3 });
	}

	@Test
	public void testBidirectional() {
		MODEL_PATH = "../genericgraphmetamodel/instances/1_bidirectional.xmi";
		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 3, 2, 1 });
	}

}
