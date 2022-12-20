package test.suite.gipsl.all.build.resourceset;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gipsl.all.build.resourcesetinit.connector.RsinitConnector;
import test.suite.gips.utils.AResourceConnector;

public class GipslAllBuildResourceSetInitSimpleTest extends AGipslAllBuildResourceSetTest {

	final AResourceConnector con = new RsinitConnector(gen.getResourceSet());
	
	// Setup method

	@BeforeEach
	public void setUp() {
		gen.reset();
	}
	
	@Test
	public void testMap2to1() {
		gen.genSubstrateNode("s1", 2);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);

		
		final ILPSolverOutput ret = con.solve();
		con.apply();

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		// All mappings must be chosen, according to the objective function
		assertEquals(2, ret.objectiveValue());
	}

}
