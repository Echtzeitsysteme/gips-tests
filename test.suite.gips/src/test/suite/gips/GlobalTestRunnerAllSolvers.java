package test.suite.gips;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.emoflon.gips.intermediate.GipsIntermediate.ILPSolverType;

import test.suite.gips.utils.GlobalTestConfig;

public class GlobalTestRunnerAllSolvers {
	
	private final static String BASE_REPORT_PATH = "report.html";
	
	private final static String GLPK_SUB_PATH = "glpk";
	private final static String GLPK_REPORT_PATH = "report_" + GLPK_SUB_PATH + ".html";
	
	private final static String GUROBI_SUB_PATH = "gurobi";
	private final static String GUROBI_REPORT_PATH = "report_" + GUROBI_SUB_PATH + ".html";
	
	private final static String CPLEX_SUB_PATH = "cplex";
	private final static String CPLEX_REPORT_PATH = "report_" + CPLEX_SUB_PATH + ".html";

	public static void main(final String[] args) {
		String pathArg = "";
		if (args != null && args.length > 0) {
			pathArg = args[0];
		}

		GlobalTestConfig.solverOverride = true;
		
		GlobalTestConfig.solverType = ILPSolverType.GLPK;
		removeFile(GLPK_REPORT_PATH);
		GlobalTestRunner.runTests(pathArg, GLPK_SUB_PATH);
		renameFile(BASE_REPORT_PATH, GLPK_REPORT_PATH);
		
		GlobalTestConfig.solverType = ILPSolverType.GUROBI;
		removeFile(GUROBI_REPORT_PATH);
		GlobalTestRunner.runTests(pathArg, GUROBI_SUB_PATH);
		renameFile(BASE_REPORT_PATH, GUROBI_REPORT_PATH);
		
		GlobalTestConfig.solverType = ILPSolverType.CPLEX;
		removeFile(CPLEX_REPORT_PATH);
		GlobalTestRunner.runTests(pathArg, CPLEX_SUB_PATH);
		renameFile(BASE_REPORT_PATH, CPLEX_REPORT_PATH);

		System.exit(0);
	}
	
	//
	// Utility methods
	//

	private static void renameFile(final String from, final String to) {
		if (from == null || from.isBlank()) {
			throw new IllegalArgumentException("First file name was null or empty.");
		}

		if (to == null || to.isBlank()) {
			throw new IllegalArgumentException("Second file name was null or empty");
		}

		if (from.equals(to)) {
			throw new IllegalArgumentException("First and second file name were equal.");
		}

		final File fromF = new File(from);
		final File toF = new File(to);

		if (!fromF.exists()) {
			throw new RuntimeException("File to rename does not exist.");
		}

		if (toF.exists()) {
			throw new RuntimeException("Target file already exists.");
		}

		final boolean success = fromF.renameTo(toF);

		if (!success) {
			throw new RuntimeException("Renamig of file <" + from + "> was not successful.");
		}
	}
	
	private static void removeFile(final String path) {
		if (path == null || path.isBlank()) {
			throw new IllegalArgumentException("File path was null or empty.");
		}
		
		try {
			Files.deleteIfExists(Paths.get(path));
		} catch (final IOException e) {
			throw new RuntimeException("File deletion of file <" + path + "> was not successful.");
		}
	}

}
