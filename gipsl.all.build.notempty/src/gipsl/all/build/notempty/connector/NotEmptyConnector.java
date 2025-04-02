package gipsl.all.build.notempty.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.notempty.api.gips.NotemptyGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class NotEmptyConnector extends AConnector {

	public NotEmptyConnector(final String modelPath) {
		api = new NotemptyGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((NotemptyGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}
