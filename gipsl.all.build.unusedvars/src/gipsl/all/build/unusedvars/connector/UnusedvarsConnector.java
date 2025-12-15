package gipsl.all.build.unusedvars.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.unusedvars.api.gips.UnusedvarsGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class UnusedvarsConnector extends AConnector {

	public UnusedvarsConnector(final String modelPath) {
		api = new UnusedvarsGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	public String getLPOutputPath() {
		return api.getSolverConfig().getLpPath();
	}

	public UnusedvarsGipsAPI getAPI() {
		return (UnusedvarsGipsAPI) api;
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((UnusedvarsGipsAPI) api).getN2n().applyNonZeroMappings();
//		save(outputPath);
		return output;
	}

}
