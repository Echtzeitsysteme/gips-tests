package gipsl.all.build.multiplemappingsonpattern.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.multiplemappingsonpattern.api.gips.MultiplemappingsonpatternGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class MultipleMappingsOnPatternConnector extends AConnector {

	public MultipleMappingsOnPatternConnector(final String modelPath) {
		api = new MultiplemappingsonpatternGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		// Nothing to apply here
		return output;
	}

	public MultiplemappingsonpatternGipsAPI getApi() {
		return (MultiplemappingsonpatternGipsAPI) api;
	}

}
