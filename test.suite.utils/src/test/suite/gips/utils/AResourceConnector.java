package test.suite.gips.utils;

import org.emoflon.gips.core.api.GipsEngineAPI;
import org.emoflon.gips.core.milp.SolverOutput;

public abstract class AResourceConnector {

	protected GipsEngineAPI<?, ?> api;

	public SolverOutput solve() {
		// Build the ILP problem (including updates)
		api.buildProblem(true);
		return api.solveProblem();
	}

	public abstract void apply();

	public void terminate() {
		if (api != null) {
			api.terminate();
		}
	}

}
