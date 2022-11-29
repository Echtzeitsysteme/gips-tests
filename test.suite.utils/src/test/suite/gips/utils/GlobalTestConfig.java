package test.suite.gips.utils;

import org.emoflon.gips.core.api.GipsEngineAPI;
import org.emoflon.gips.core.ilp.CplexSolver;
import org.emoflon.gips.core.ilp.GlpkSolver;
import org.emoflon.gips.core.ilp.GurobiSolver;
import org.emoflon.gips.core.ilp.ILPSolver;
import org.emoflon.gips.core.ilp.ILPSolverConfig;
import org.emoflon.gips.intermediate.GipsIntermediate.ILPSolverType;

public class GlobalTestConfig {
	
	public static boolean solverOverride = false;
	public static ILPSolverType solverType = ILPSolverType.GLPK;
	
	private GlobalTestConfig() {
	}
	
	public static void overrideSolver(final GipsEngineAPI<?, ?> api) {
		if (!solverOverride) {
			return;
		}
		
		api.setILPSolver(GlobalTestConfig.getSolver(api.getSolverConfig(), api));
	}
	
	private static ILPSolver getSolver(final ILPSolverConfig config, final GipsEngineAPI<?, ?> api) {
		switch(solverType) {
		case GLPK: {
			return new GlpkSolver(api, config);
		}
		case GUROBI: {
			try {
				return new GurobiSolver(api, config);
			} catch (final Exception e) {
				e.printStackTrace();
				throw new InternalError("Gurobi initialization failed.");
			}
		}
		case CPLEX: {
			return new CplexSolver(api, config);
		}
		default: {
			throw new UnsupportedOperationException("Solver type not known.");
		}
		}
	}

}
