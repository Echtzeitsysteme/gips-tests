package test.suite.gips;

import org.emoflon.gips.intermediate.GipsIntermediate.ILPSolverType;

import test.suite.gips.utils.GlobalTestConfig;

public class GlobalTestRunnerAllSolvers {

	public static void main(final String[] args) {
		String pathArg = "";
		if (args != null && args.length > 0) {
			pathArg = args[0];
		}

		GlobalTestConfig.solverOverride = true;
		GlobalTestConfig.solverType = ILPSolverType.GLPK;
		GlobalTestRunner.runTests(pathArg, "glpk");
		GlobalTestConfig.solverType = ILPSolverType.GUROBI;
		GlobalTestRunner.runTests(pathArg, "gurobi");
		GlobalTestConfig.solverType = ILPSolverType.CPLEX;
		GlobalTestRunner.runTests(pathArg, "cplex");

		// TODO: Move 'report.html' to the respective sub directories

		System.exit(0);
	}

}
