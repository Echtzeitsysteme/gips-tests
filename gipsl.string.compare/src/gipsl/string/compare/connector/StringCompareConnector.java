package gipsl.string.compare.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.string.compare.api.gips.CompareGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class StringCompareConnector extends AConnector {

	public StringCompareConnector(final String modelPath) {
		api = new CompareGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((CompareGipsAPI) api).getN2n().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}
