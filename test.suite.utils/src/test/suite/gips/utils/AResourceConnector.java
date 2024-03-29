package test.suite.gips.utils;

import org.emoflon.gips.core.api.GipsEngineAPI;
import org.emoflon.gips.core.ilp.ILPSolverOutput;

public abstract class AResourceConnector {

	protected GipsEngineAPI<?, ?> api;

	public ILPSolverOutput solve() {
		// Build the ILP problem (including updates)
		api.buildILPProblem(true);
		return api.solveILPProblem();
	}

	public abstract void apply();

	public void terminate() {
		if (api != null) {
			api.terminate();
		}
	}

}
