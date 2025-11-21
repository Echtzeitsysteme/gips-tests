package gipsl.all.build.join.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gipsl.all.build.join.api.gips.JoinGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class JoinConnector extends AConnector {

	public JoinConnector(final String modelPath) {
		api = new JoinGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((JoinGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}
