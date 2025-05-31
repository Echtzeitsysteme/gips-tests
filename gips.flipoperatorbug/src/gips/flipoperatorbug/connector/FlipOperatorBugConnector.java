package gips.flipoperatorbug.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gips.flipoperatorbug.api.gips.FlipoperatorbugGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class FlipOperatorBugConnector extends AConnector {

	public FlipOperatorBugConnector(final String modelPath) {
		api = new FlipoperatorbugGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((FlipoperatorbugGipsAPI) api).getN2n().applyNonZeroMappings();
		return output;
	}

}
