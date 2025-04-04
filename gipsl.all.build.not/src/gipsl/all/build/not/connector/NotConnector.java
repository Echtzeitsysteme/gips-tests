package gipsl.all.build.not.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.not.api.gips.NotGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class NotConnector extends AConnector {

	public NotConnector(final String modelPath) {
		api = new NotGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((NotGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}
