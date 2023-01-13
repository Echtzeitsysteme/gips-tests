package test.suite.gipsl.all.build.resourceset;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gipsl.all.build.resourcesetinit.connector.RsinitConnector;
import test.suite.gips.utils.AResourceConnector;

public class GipslAllBuildResourceSetInitMediumTest extends AGipslAllBuildResourceSetTest {

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
	public void testMap10to3BatchesIncremental() {
		gen.genSubstrateNode("s1", 10);
		gen.genSubstrateNode("s2", 10);
		gen.genSubstrateNode("s3", 10);

		int vNodeCntr = 0;

		for (int i = 1; i <= 10; i++) {

			for (int j = i; j <= i; j++) {
				gen.genVirtualNode("v" + i + "-" + j, 1);
				vNodeCntr++;
			}

			ILPSolverOutput ret = con.solve();
			con.apply();

			assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
			// All mappings must be chosen, according to the objective function
			assertEquals(vNodeCntr, ret.objectiveValue());
		}
	}

	@Test
	public void testMap5to10BatchesIncremental() {
		for (int i = 1; i <= 10; i++) {
			gen.genSubstrateNode("s" + i, 100);
		}

		int vNodeCntr = 0;

		for (int i = 1; i <= 5; i++) {

			for (int j = i; j <= i; j++) {
				gen.genVirtualNode("v" + i + "-" + j, 1);
				vNodeCntr++;
			}

			ILPSolverOutput ret = con.solve();
			con.apply();

			assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
			// All mappings must be chosen, according to the objective function
			assertEquals(vNodeCntr, ret.objectiveValue());
		}
	}

}
