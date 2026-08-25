package gipsl.all.build.unaries.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.unaries.api.gips.UnariesGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class UnariesConnector extends AConnector {

	public UnariesConnector(final String modelPath) {
		api = new UnariesGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	public String getLPOutputPath() {
		return api.getSolverConfig().getLpPath();
	}

	public UnariesGipsAPI getAPI() {
		return (UnariesGipsAPI) api;
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
//		((TypeextensionGipsAPI) api).getN2n().applyNonZeroMappings();
//		save(outputPath);
		return output;
	}

}
