package test.suite.gips.utils;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.emoflon.gips.core.api.GipsEngineAPI;
import org.emoflon.gips.core.milp.SolverOutput;

public abstract class AConnector {

	protected GipsEngineAPI<?> api;

	public abstract SolverOutput run(final String outputPath);

	protected void save(final String outputPath) {
		final URI absPath = GipsTestUtils.pathToAbsUri(outputPath);
		try {
			api.saveResult(absPath.toFileString());
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	protected SolverOutput solve() {
		// Build the ILP problem (including updates)
		api.buildProblem(true);
		return api.solveProblem();
	}

	public void terminate() {
		if (api != null) {
			api.terminate();
		}
	}

}
