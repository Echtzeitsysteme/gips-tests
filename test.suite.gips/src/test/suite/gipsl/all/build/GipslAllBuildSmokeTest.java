package test.suite.gipsl.all.build;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gipsl.all.build.connector.Connector;
import test.suite.gipsl.all.build.utils.AllBuildModelGenerator;

public class GipslAllBuildSmokeTest extends AGipslAllBuildTest {

	@BeforeEach
	public void setUp() {
		gen = new AllBuildModelGenerator();
		gen.persistModel("model.xmi");
		con = new Connector("model.xmi");
	}

	@Test
	public void testCreateOutput() {
		con.run("output.xmi");
		final File f = new File("output.xmi");
		assertTrue(f.exists() && !f.isDirectory());
	}

}
