package test.suite.gips.shortestpath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import genericgraphmetamodel.generator.GenericGraphMetamodelGenerator;
import shortestpath.connector.ShortestPathConnector;

public class GipsShortestPathScenarioTest extends AGipsShortestPathTest {

	@Override
	protected void callableSetUp() {
		con = new ShortestPathConnector(MODEL_PATH);
	}

	@Override
	public Class<?> getConnectorClass() {
		return ShortestPathConnector.class;
	}

	@AfterAll
	public static void clean() {
		try {
			Files.deleteIfExists(Path.of("./" + MODEL_PATH));
		} catch (final IOException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void test0to2() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(0, 2, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 0, 1, 2 });
	}

	@Test
	public void test0to3() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(0, 3, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 0, 1, 2, 3 });
	}

	@Test
	public void test0to4() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(0, 4, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 0, 7, 6, 5, 4 });
	}

	@Test
	public void test0to5() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(0, 5, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 0, 7, 6, 5 });
	}

	@Test
	public void test0to6() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(0, 6, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 0, 7, 6 });
	}

	@Test
	public void test0to7() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(0, 7, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 0, 7 });
	}

	@Test
	public void test0to8() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(0, 8, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 0, 1, 2, 8 });
	}

	@Test
	public void test1to0() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(1, 0, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 1, 0 });
	}

	@Test
	public void test1to2() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(1, 2, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 1, 2 });
	}

	@Test
	public void test1to3() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(1, 3, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 1, 2, 3 });
	}

	@Test
	public void test1to4() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(1, 4, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 1, 2, 5, 4 });
	}

	@Test
	public void test1to5() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(1, 5, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 1, 2, 5 });
	}

	@Test
	public void test1to6() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(1, 6, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 1, 7, 6 });
	}
	
	@Test
	public void test1to7() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(1, 7, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 1, 7 });
	}
	
	@Test
	public void test1to8() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(1, 8, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 1, 2, 8 });
	}

}
