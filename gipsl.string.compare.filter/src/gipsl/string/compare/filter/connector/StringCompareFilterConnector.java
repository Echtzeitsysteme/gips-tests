package gipsl.string.compare.filter.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.string.compare.filter.api.gips.FilterGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class StringCompareFilterConnector extends AConnector {

	public StringCompareFilterConnector(final String modelPath) {
		api = new FilterGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((FilterGipsAPI) api).getN2n().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}
