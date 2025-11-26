package gips.all.build.joinall.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gips.all.build.joinall.api.gips.JoinallGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class JoinAllConnector extends AConnector {

	public JoinAllConnector(final String modelPath) {
		api = new JoinallGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((JoinallGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}
