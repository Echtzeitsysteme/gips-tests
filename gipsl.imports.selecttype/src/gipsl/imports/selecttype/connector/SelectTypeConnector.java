package gipsl.imports.selecttype.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.imports.selecttype.api.gips.SelecttypeGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class SelectTypeConnector extends AConnector {

	public SelectTypeConnector(final String modelPath) {
		api = new SelecttypeGipsAPI();
		GlobalTestConfig.overrideSolver(api);
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
	}

	public String getLPOutputPath() {
		return api.getSolverConfig().getLpPath();
	}

	public SelecttypeGipsAPI getAPI() {
		return (SelecttypeGipsAPI) api;
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		return output;
	}

}
