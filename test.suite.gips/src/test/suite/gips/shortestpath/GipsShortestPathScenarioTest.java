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

	@Test
	public void test2to0() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(2, 0, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 2, 1, 0 });
	}

	@Test
	public void test2to1() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(2, 1, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 2, 1 });
	}

	@Test
	public void test2to3() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(2, 3, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 2, 3 });
	}

	@Test
	public void test2to4() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(2, 4, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 2, 5, 4 });
	}

	@Test
	public void test2to5() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(2, 5, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 2, 5 });
	}

	@Test
	public void test2to6() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(2, 6, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 2, 5, 6 });
	}

	@Test
	public void test2to7() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(2, 7, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 2, 5, 6, 7 });
	}

	@Test
	public void test2to8() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(2, 8, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 2, 8 });
	}

	@Test
	public void test3to0() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(3, 0, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 3, 2, 1, 0 });
	}

	@Test
	public void test3to1() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(3, 1, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 3, 2, 1 });
	}

	@Test
	public void test3to2() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(3, 2, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 3, 2 });
	}

	@Test
	public void test3to4() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(3, 4, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 3, 4 });
	}

	@Test
	public void test3to5() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(3, 5, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 3, 2, 5 });
	}

	@Test
	public void test3to6() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(3, 6, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 3, 2, 5, 6 });
	}

	@Test
	public void test3to7() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(3, 7, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 3, 2, 5, 6, 7 });
	}

	@Test
	public void test3to8() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(3, 8, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 3, 2, 8 });
	}

	@Test
	public void test4to0() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(4, 0, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 4, 5, 6, 7, 0 });
	}

	@Test
	public void test4to1() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(4, 1, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 4, 5, 2, 1 });
	}

	@Test
	public void test4to2() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(4, 2, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 4, 5, 2 });
	}

	@Test
	public void test4to3() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(4, 3, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 4, 3 });
	}

	@Test
	public void test4to5() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(4, 5, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 4, 5 });
	}

	@Test
	public void test4to6() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(4, 6, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 4, 5, 6 });
	}

	@Test
	public void test4to7() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(4, 7, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 4, 5, 6, 7 });
	}

	@Test
	public void test4to8() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(4, 8, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 4, 5, 2, 8 });
	}

	@Test
	public void test5to0() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(5, 0, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 5, 6, 7, 0 });
	}

	@Test
	public void test5to1() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(5, 1, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 5, 2, 1 });
	}

	@Test
	public void test5to2() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(5, 2, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 5, 2 });
	}

	@Test
	public void test5to3() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(5, 3, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 5, 2, 3 });
	}

	@Test
	public void test5to4() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(5, 4, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 5, 4 });
	}

	@Test
	public void test5to6() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(5, 6, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 5, 6 });
	}

	@Test
	public void test5to7() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(5, 7, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 5, 6, 7 });
	}

	@Test
	public void test5to8() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(5, 8, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 5, 2, 8 });
	}

	@Test
	public void test6to0() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(6, 0, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 6, 7, 0 });
	}

	@Test
	public void test6to1() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(6, 1, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 6, 7, 1 });
	}

	@Test
	public void test6to2() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(6, 2, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 6, 5, 2 });
	}

	@Test
	public void test6to3() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(6, 3, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 6, 5, 2, 3 });
	}

	@Test
	public void test6to4() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(6, 4, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 6, 5, 4 });
	}

	@Test
	public void test6to5() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(6, 5, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 6, 5 });
	}

	@Test
	public void test6to7() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(6, 7, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 6, 7 });
	}

	@Test
	public void test6to8() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(6, 8, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 6, 8 });
	}

	@Test
	public void test7to0() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(7, 0, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 7, 0 });
	}

	@Test
	public void test7to1() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(7, 1, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 7, 1 });
	}

	@Test
	public void test7to2() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(7, 2, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 7, 6, 5, 2 });
	}

	@Test
	public void test7to3() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(7, 3, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 7, 6, 5, 2, 3 });
	}

	@Test
	public void test7to4() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(7, 4, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 7, 6, 5, 4 });
	}

	@Test
	public void test7to5() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(7, 5, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 7, 6, 5 });
	}

	@Test
	public void test7to6() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(7, 6, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 7, 6 });
	}

	@Test
	public void test7to8() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(7, 8, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		int errorCounter = 0;
		try {
			validatePath(new Integer[] { 7, 8 });
		} catch (AssertionError err) {
			errorCounter++;
		}
		try {
			validatePath(new Integer[] { 7, 6, 8 });
		} catch (AssertionError err) {
			errorCounter++;
		}
		Assert.assertTrue(errorCounter <= 1);
	}

	@Test
	public void test8to0() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(8, 0, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 8, 2, 1, 0 });
	}

	@Test
	public void test8to1() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(8, 1, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 8, 2, 1 });
	}

	@Test
	public void test8to2() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(8, 2, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 8, 2 });
	}

	@Test
	public void test8to3() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(8, 3, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 8, 2, 3 });
	}

	@Test
	public void test8to4() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(8, 4, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 8, 2, 5, 4 });
	}

	@Test
	public void test8to5() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(8, 5, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 8, 2, 5 });
	}

	@Test
	public void test8to6() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(8, 6, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		validatePath(new Integer[] { 8, 6 });
	}

	@Test
	public void test8to7() {
		final GenericGraphMetamodelGenerator gen = new GenericGraphMetamodelGenerator();
		gen.run(8, 7, "./" + MODEL_PATH);

		callableSetUp();
		con.run(MODEL_PATH);

		int errorCounter = 0;
		try {
			validatePath(new Integer[] { 8, 7 });
		} catch (AssertionError err) {
			errorCounter++;
		}
		try {
			validatePath(new Integer[] { 8, 6, 7 });
		} catch (AssertionError err) {
			errorCounter++;
		}
		Assert.assertTrue(errorCounter <= 1);
	}

}
