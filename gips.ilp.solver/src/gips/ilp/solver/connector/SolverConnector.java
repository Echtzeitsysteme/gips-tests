package gips.ilp.solver.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gips.ilp.solver.api.gips.SolverGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class SolverConnector extends AConnector {

	public SolverConnector() {
		api = new SolverGipsAPI();
	}

	/*
	 * Init for the actual test implementation.
	 */

	public void init(final String modelPath) {
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((SolverGipsAPI) api).getAsn().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}
