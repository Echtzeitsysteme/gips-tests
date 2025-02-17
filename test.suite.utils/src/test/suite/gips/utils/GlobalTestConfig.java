package test.suite.gips.utils;

import org.emoflon.gips.core.api.GipsEngineAPI;
import org.emoflon.gips.core.milp.CplexSolver;
import org.emoflon.gips.core.milp.GlpkSolver;
import org.emoflon.gips.core.milp.GurobiSolver;
import org.emoflon.gips.core.milp.Solver;
import org.emoflon.gips.core.milp.SolverConfig;
import org.emoflon.gips.intermediate.GipsIntermediate.SolverType;

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
	public static SolverType solverType = SolverType.GUROBI;

	private GlobalTestConfig() {
	}

	/**
	 * Overrides the  solver configuration for a given GipsEngineAPI.
	 * 
	 * @param api GipsEngineAPI for which the  solver should be overridden.
	 */
	public static void overrideSolver(final GipsEngineAPI<?> api) {
		if (!solverOverride) {
			return;
		}

		api.setSolver(GlobalTestConfig.getSolver(api.getSolverConfig(), api));
	}

	/**
	 * Creates a new  solver instance for the given SolverConfig and the given
	 * GipsEngineAPI.
	 * 
	 * @param config SolverConfig which should be used by the newly instantiated
	 *                solver.
	 * @param api    GipsEngineAPI to get config from.
	 * @return Newly instantiated  solver object.
	 */
	private static Solver getSolver(final SolverConfig config, final GipsEngineAPI<?> api) {
		switch (solverType) {
		case GLPK: {
			return new GlpkSolver(api, config);
		}
		case GUROBI: {
			try {
				return new GurobiSolver(api, config);
			} catch (final Exception e) {
				throw new InternalError("Gurobi initialization failed: " + e.getMessage());
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
