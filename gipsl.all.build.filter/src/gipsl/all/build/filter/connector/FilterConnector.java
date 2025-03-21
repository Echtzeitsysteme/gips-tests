package gipsl.all.build.filter.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.filter.api.gips.FilterGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class FilterConnector extends AConnector {

	public FilterConnector(final String modelPath) {
		api = new FilterGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((FilterGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}
