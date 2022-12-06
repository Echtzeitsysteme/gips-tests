package gipsl.all.build.filter.connector;

import org.emoflon.gips.core.ilp.ILPSolverOutput;

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
	public ILPSolverOutput run(final String outputPath) {
		final ILPSolverOutput output = solve();
		((FilterGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}
