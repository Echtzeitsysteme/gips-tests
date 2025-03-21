package gipsl.all.build.count.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.count.api.gips.CountGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class CountConnector extends AConnector {

	public CountConnector(final String modelPath) {
		api = new CountGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((CountGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}
