package gips.launchconfig.connector;

import org.emoflon.gips.core.milp.SolverOutput;

import gips.launchconfig.api.gips.LaunchconfigGipsAPI;
import test.suite.gips.utils.AConnector;
import test.suite.gips.utils.GipsTestUtils;
import test.suite.gips.utils.GlobalTestConfig;

public class LaunchConfigConnector extends AConnector {

	public LaunchConfigConnector(final String modelPath) {
		api = new LaunchconfigGipsAPI();
		api.init(GipsTestUtils.pathToAbsUri(modelPath));
		GlobalTestConfig.overrideSolver(api);
	}

	@Override
	public SolverOutput run(final String outputPath) {
		final SolverOutput output = solve();
		((LaunchconfigGipsAPI) api).getMapNode().applyNonZeroMappings();
		save(outputPath);
		return output;
	}

}
