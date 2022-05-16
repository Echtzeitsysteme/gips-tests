package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.emoflon.gips.core.ilp.ILPSolverOutput;
import org.emoflon.gips.core.ilp.ILPSolverStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gipsl.all.build.connector.Connector;
import test.suite.gipsl.all.build.utils.AllBuildModelGenerator;

public class GipslAllBuildSimpleAddTest extends AGipslAllBuildTest {

	// Setup and tear down methods

	@BeforeEach
	public void setUp() {
		gen = new AllBuildModelGenerator();
	}

	public void callableSetUp() {
		gen.persistModel("model.xmi");
		con = new Connector("model.xmi");
	}

	@AfterEach
	public void tearDown() {
		final File in = new File("model.xmi");
		final File out = new File("output.xmi");
		in.delete();
		out.delete();
	}

	// Actual tests

	@Test
	public void testMap1to1() {
		gen.genSubstrateNode("s1", 1);
		gen.genVirtualNode("v1", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run("output.xmi");
		checkIfFileExists();

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(1, ret.objectiveValue());
	}

	@Test
	public void testMap2to1() {
		gen.genSubstrateNode("s1", 2);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run("output.xmi");
		checkIfFileExists();

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(2, ret.objectiveValue());
	}

	@Test
	public void testMap10to1() {
		gen.genSubstrateNode("s1", 2);
		for (int i = 1; i <= 10; i++) {
			gen.genVirtualNode("v" + i, 1);
		}
		callableSetUp();

		final ILPSolverOutput ret = con.run("output.xmi");
		checkIfFileExists();

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(10, ret.objectiveValue());
	}

	@Test
	public void testMap2to2() {
		gen.genSubstrateNode("s1", 1);
		gen.genSubstrateNode("s2", 1);
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run("output.xmi");
		checkIfFileExists();

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(2, ret.objectiveValue());
	}

	@Test
	public void testMap2to10() {
		for (int i = 1; i <= 10; i++) {
			gen.genSubstrateNode("s" + i, 1);
		}
		gen.genVirtualNode("v1", 1);
		gen.genVirtualNode("v2", 1);
		callableSetUp();

		final ILPSolverOutput ret = con.run("output.xmi");
		checkIfFileExists();

		assertEquals(ILPSolverStatus.OPTIMAL, ret.status());
		assertEquals(2, ret.objectiveValue());
	}

	// Utility methods

	public void checkIfFileExists() {
		final File f = new File("output.xmi");
		assertTrue(f.exists() && !f.isDirectory());
	}

}
