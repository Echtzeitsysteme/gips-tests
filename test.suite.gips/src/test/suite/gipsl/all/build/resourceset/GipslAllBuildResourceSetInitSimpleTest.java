package test.suite.gipsl.all.build.resourceset;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.milp.SolverOutput;
import org.emoflon.gips.core.milp.SolverStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gipsl.all.build.resourcesetinit.connector.RsinitConnector;
import test.suite.gips.utils.AResourceConnector;

public class GipslAllBuildResourceSetInitSimpleTest extends AGipslAllBuildResourceSetTest {

	/**
	 * Connector to the GIPS project with resource set initialization
	 */
	private AResourceConnector con;

	// Setup method

	@BeforeEach
	public void setUp() {
		gen.reset();
		con = new RsinitConnector(gen.getResourceSet());
	}

	@Test
	public void testMap2to1() {
		gen.genSubstrateNode("s1", 2);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);

		final SolverOutput ret = con.solve();
		con.apply();

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		// All mappings must be chosen, according to the objective function
		assertEquals(2, ret.objectiveValue());
	}

	@Test
	public void testMap3to1PartlyIncremental() {
		gen.genSubstrateNode("s1", 3);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);

		SolverOutput ret = con.solve();
		con.apply();

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		// All mappings must be chosen, according to the objective function
		assertEquals(2, ret.objectiveValue());

		gen.genVirtualNode("v3", 1);

		ret = con.solve();
		con.apply();

		assertEquals(SolverStatus.OPTIMAL, ret.status());
		// All mappings must be chosen, according to the objective function
		assertEquals(3, ret.objectiveValue());
	}

	@Test
	public void testMap10to1FullIncremental() {
		gen.genSubstrateNode("s1", 10);

		for (int i = 1; i <= 10; i++) {
			gen.genVirtualNode("v" + i, 1);

			SolverOutput ret = con.solve();
			con.apply();

			assertEquals(SolverStatus.OPTIMAL, ret.status());
			// All mappings must be chosen, according to the objective function
			assertEquals(i, ret.objectiveValue());
		}
	}

	@Override
	public Class<?> getConnectorClass() {
		return RsinitConnector.class;
	}

}
