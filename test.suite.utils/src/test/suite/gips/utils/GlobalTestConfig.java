package test.suite.gips.utils;

import org.emoflon.gips.core.api.GipsEngineAPI;
import org.emoflon.gips.core.ilp.CplexSolver;
import org.emoflon.gips.core.ilp.GlpkSolver;
import org.emoflon.gips.core.ilp.GurobiSolver;
import org.emoflon.gips.core.ilp.ILPSolver;
import org.emoflon.gips.core.ilp.ILPSolverConfig;
import org.emoflon.gips.core.ilp.LpSolveSolver;
import org.emoflon.gips.intermediate.GipsIntermediate.ILPSolverType;

/**
 * Test configuration for the GIPS test suite.
 */
public class GlobalTestConfig {

	/**
	 * If true, the solver configuration will be overridden.
	 */
	public static boolean solverOverride = true;

	/**
	 * Configures the used solver type (e.g., GLPK, Gurobi) if override is enabled.
	 */
	public static ILPSolverType solverType = ILPSolverType.GUROBI;

	/**
	 * Epsilon that is used in various tests to check for equality of floating point
	 * numbers.
	 */
	public static double epsilon = 0.001;

	private GlobalTestConfig() {
	}

	/**
	 * Overrides the ILP solver configuration for a given GipsEngineAPI.
	 * 
	 * @param api GipsEngineAPI for which the ILP solver should be overridden.
	 */
	public static void overrideSolver(final GipsEngineAPI<?, ?> api) {
		if (!solverOverride) {
			return;
		}

		api.setILPSolver(GlobalTestConfig.getSolver(api.getSolverConfig(), api));
	}

	/**
	 * Creates a new ILP solver instance for the given ILPSolverConfig and the given
	 * GipsEngineAPI.
	 * 
	 * @param config ILPSolverConfig which should be used by the newly instantiated
	 *               ILP solver.
	 * @param api    GipsEngineAPI to get config from.
	 * @return Newly instantiated ILP solver object.
	 */
	private static ILPSolver getSolver(final ILPSolverConfig config, final GipsEngineAPI<?, ?> api) {
		switch (solverType) {
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
		case LPSOLVE: {
			return new LpSolveSolver(api, config);
		}
		default: {
			throw new UnsupportedOperationException("Solver type not known.");
		}
		}
	}

}
